package algorithms;

import clusters.Cluster;
import clusters.MedoidCluster;
import datapoints.InputDataPoint;
import datapoints.ClusteredDataPoint;

import java.util.ArrayList;
import java.util.List;

public class KMeans implements ClusterAlgorithm {

    private final int k;

    public KMeans(int k) {
        this.k = k;
    }

    @Override
    public List<Cluster> fit(List<InputDataPoint> inputDataPoints) {
        List<ClusteredDataPoint> clusteredDataPoints = getOutputDataPoints(inputDataPoints);
        List<MedoidCluster> clusters = setupClusters(clusteredDataPoints);

        while (clusterDataPoints(clusteredDataPoints, clusters)) {
            updateMedoids(clusters);
        }

        return new ArrayList<>(clusters);
    }

    private List<ClusteredDataPoint> getOutputDataPoints(List<InputDataPoint> inputDataPoints) {
        List<ClusteredDataPoint> clusteredDataPoints = new ArrayList<>();
        for (InputDataPoint inputDataPoint : inputDataPoints) {
            ClusteredDataPoint clusteredDataPoint = new ClusteredDataPoint(inputDataPoint);
            clusteredDataPoints.add(clusteredDataPoint);
        }
        return clusteredDataPoints;
    }

    private void updateMedoids(List<MedoidCluster> clusters) {
        for (MedoidCluster cluster : clusters) {
            cluster.updateMedoid();
        }
    }

    private boolean clusterDataPoints(List<ClusteredDataPoint> dataPoints, List<MedoidCluster> clusters) {
        boolean pointsMoved = false;
        List<ClusteredDataPoint> medoids = new ArrayList<>();
        for (MedoidCluster medoidCluster : clusters) {
            ClusteredDataPoint medoid = medoidCluster.getMedoid();
            medoids.add(medoid);
        }

        for (ClusteredDataPoint dataPoint : dataPoints) {
            if (!medoids.contains(dataPoint)) {
                MedoidCluster closestCluster = findClosestCluster(clusters, dataPoint);
                pointsMoved |= dataPoint.moveToCluster(closestCluster);
            }
        }
        return pointsMoved;
    }

    private List<MedoidCluster> setupClusters(List<ClusteredDataPoint> dataPoints) {
        List<MedoidCluster> clusters = new ArrayList<>();

        for (int index = 0; index < k; index++) {
            ClusteredDataPoint dataPoint = dataPoints.get(index);
            MedoidCluster cluster = new MedoidCluster();
            dataPoint.moveToCluster(cluster);
            cluster.updateMedoid();
            clusters.add(cluster);
        }
        return clusters;
    }

    private MedoidCluster findClosestCluster(List<MedoidCluster> clusters, ClusteredDataPoint dataPoint) {
        double currentSmallestMedoidDistance = Double.MAX_VALUE;
        MedoidCluster currentClosestMedoid = null;
        for (MedoidCluster medoidCluster : clusters) {
            double distanceToMedoid = medoidCluster.distanceToMedoid(dataPoint);
            if (distanceToMedoid < currentSmallestMedoidDistance) {
                currentSmallestMedoidDistance = distanceToMedoid;
                currentClosestMedoid = medoidCluster;
            }
        }
        return currentClosestMedoid;
    }
}
