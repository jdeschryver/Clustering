package algorithms;

import algorithms.medoidselectors.MedoidSelector;
import clusters.Cluster;
import clusters.MedoidCluster;
import datapoints.InputDataPoint;
import datapoints.ClusteredDataPoint;
import validation.ClusteringValidator;

import java.util.ArrayList;
import java.util.List;

public class KMeans implements ClusterAlgorithm {

    private final int k;
    private final MedoidSelector medoidSelector;

    public KMeans(int k, MedoidSelector medoidSelector) {
        this.k = k;
        this.medoidSelector = medoidSelector;
    }

    @Override
    public List<Cluster> fit(List<InputDataPoint> inputDataPoints) {
        List<ClusteredDataPoint> clusteredDataPoints = getOutputDataPoints(inputDataPoints);
        List<ClusteredDataPoint> medoids = medoidSelector.calculateInitialMedoids(clusteredDataPoints, k);
        List<MedoidCluster> clusters = setupClusters(medoids);

        while (clusterDataPoints(clusteredDataPoints, clusters)) {
            updateMedoids(clusters);
        }

        assert ClusteringValidator.isValidCompleteClustering(clusters, inputDataPoints);

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

    private List<MedoidCluster> setupClusters(List<ClusteredDataPoint> medoids) {
        List<MedoidCluster> clusters = new ArrayList<>();

        for (ClusteredDataPoint medoid : medoids) {
            MedoidCluster cluster = new MedoidCluster();
            medoid.moveToCluster(cluster);
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
