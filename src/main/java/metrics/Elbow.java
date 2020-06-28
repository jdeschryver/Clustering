package metrics;

import algorithms.KMeans;
import clusters.Cluster;
import datapoints.InputDataPoint;

import java.util.List;

public class Elbow {

    public static List<Cluster> findBestCluster(List<InputDataPoint> dataPoints, int minK, int maxK) {
        KMeans firstKmeans = new KMeans(minK);
        List<Cluster> clustersKmin = firstKmeans.fit(dataPoints);

        double previousScore = Silhouette.score(clustersKmin);
        List<Cluster> currentStartNoProgress = clustersKmin;
        int noProgress = 0;

        for (int numberOfClusters = minK + 1; numberOfClusters < maxK; numberOfClusters++) {
            KMeans kMeans = new KMeans(numberOfClusters);
            List<Cluster> clusters = kMeans.fit(dataPoints);
            double currentScore = Silhouette.score(clusters);

            if (Math.abs(currentScore - previousScore) < 0.05) {
                if (++noProgress == 50) {
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
