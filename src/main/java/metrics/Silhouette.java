package metrics;

import clusters.Cluster;
import datapoints.ClusteredDataPoint;

import java.util.List;

public class Silhouette {

    public static double score(List<Cluster> clusters) {
        int numberOfDataPoints = 0;
        double summedSilhouette = 0d;
        for (Cluster cluster : clusters) {
            for (ClusteredDataPoint dataPoint : cluster.getElements()) {
                summedSilhouette += score(dataPoint, cluster, clusters);
                numberOfDataPoints++;
            }
        }
        return summedSilhouette / numberOfDataPoints;
    }

    private static double score(ClusteredDataPoint dataPoint, Cluster clusterDataPoint, List<Cluster> clusters) {
        double similarity = similarity(dataPoint, clusterDataPoint);
        double dissimilarity = dissimilarity(dataPoint, clusterDataPoint, clusters);

        return similarity == dissimilarity ? 0 : (dissimilarity - similarity) / Math.max(dissimilarity, similarity);
    }

    private static double dissimilarity(ClusteredDataPoint dataPoint, Cluster clusterDataPoint, List<Cluster> clusters) {
        double smallestDissimilarity = Double.MAX_VALUE;

        for (Cluster cluster : clusters) {
            if (!clusterDataPoint.equals(cluster)) {
                double summedDistance = 0d;
                for (ClusteredDataPoint otherPoint : cluster.getElements()) {
                    summedDistance += dataPoint.distanceTo(otherPoint);
                }
                double averageDistance = summedDistance / cluster.getElements().size();
                if (averageDistance < smallestDissimilarity) {
                    smallestDissimilarity = averageDistance;
                }
            }
        }

        return smallestDissimilarity;
    }

    private static double similarity(ClusteredDataPoint dataPoint, Cluster cluster) {
        double summedDistance = 0d;
        for (ClusteredDataPoint otherPoint : cluster.getElements()) {
            if (!dataPoint.equals(otherPoint)) {
                summedDistance += dataPoint.distanceTo(otherPoint);
            }
        }
        return summedDistance == 0 ? 0 : summedDistance / (cluster.getElements().size() - 1);
    }
}
