package metrics;

import algorithms.KMeans;
import clusters.ClusterContext;
import datapoints.InputDataPoint;

import java.util.List;

public class Elbow {

    public static ClusterContext findBestCluster(List<InputDataPoint> dataPoints, int minK, int maxK) {
        KMeans firstKmeans = new KMeans(minK);
        ClusterContext firstClusterContext = firstKmeans.fit(dataPoints);

        double previousScore = Silhouette.score(firstClusterContext.getClusters());
        ClusterContext currentStartNoProgress = firstClusterContext;
        int noProgress = 0;

        for (int numberOfClusters = minK + 1; numberOfClusters < maxK; numberOfClusters++) {
            KMeans kMeans = new KMeans(numberOfClusters);
            ClusterContext clusterContext = kMeans.fit(dataPoints);
            double currentScore = Silhouette.score(clusterContext.getClusters());

            if (Math.abs(currentScore - previousScore) < 0.05) {
                if (++noProgress == 50) {
                    return currentStartNoProgress;
                }
            } else {
                noProgress = 0;
                currentStartNoProgress = clusterContext;
            }
            previousScore = currentScore;
        }
        return currentStartNoProgress;
    }

}
