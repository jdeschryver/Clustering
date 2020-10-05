package metrics;

import algorithms.KMeans;
import algorithms.medoidselectors.MedoidSelector;
import clusters.Cluster;
import datapoints.InputDataPoint;

import java.util.List;
import java.util.logging.Logger;

public class Elbow {

    private static final int ITERATIONS_WITHOUT_PROGRESS = 50;
    private static final double PROGRESS_THRESHOLD = 0.05;
    private static final Logger LOGGER = Logger.getLogger(Elbow.class.getName());

    public static List<Cluster> findBestCluster(List<InputDataPoint> dataPoints, int minK, int maxK) {
        KMeans firstKmeans = new KMeans(minK, MedoidSelector.farthestPointMedoidSelector());
        List<Cluster> clustersKmin = firstKmeans.fit(dataPoints);

        double previousScore = Silhouette.score(clustersKmin);
        List<Cluster> currentStartNoProgress = clustersKmin;
        int noProgress = 0;

        for (int numberOfClusters = minK + 1; numberOfClusters < maxK; numberOfClusters++) {
            KMeans kMeans = new KMeans(numberOfClusters, MedoidSelector.farthestPointMedoidSelector());
            List<Cluster> clusters = kMeans.fit(dataPoints);
            double currentScore = Silhouette.score(clusters);

            LOGGER.info("Clustering found: " + clusters.size() + " clusters, with score " + currentScore);

            if (Math.abs(currentScore - previousScore) < PROGRESS_THRESHOLD) {
                if (++noProgress == ITERATIONS_WITHOUT_PROGRESS) {
                    return currentStartNoProgress;
                }
            } else {
                noProgress = 0;
                currentStartNoProgress = clusters;
            }
            previousScore = currentScore;
        }
        return currentStartNoProgress;
    }

}
