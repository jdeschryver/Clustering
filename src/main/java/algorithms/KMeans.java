package algorithms;

import clusters.ClusterContext;
import clusters.MedoidCluster;
import datapoints.InputDataPoint;
import datapoints.OutputDataPoint;

import java.util.ArrayList;
import java.util.List;

public class KMeans implements ClusterAlgorithm {

    private final int k;

    public KMeans(int k) {
        this.k = k;
    }

    @Override
    public ClusterContext fit(List<InputDataPoint> inputDataPoints) {
        List<OutputDataPoint> outputDataPoints = new ArrayList<>();
        for (InputDataPoint inputDataPoint : inputDataPoints) {
            OutputDataPoint outputDataPoint = new OutputDataPoint(inputDataPoint);
            outputDataPoints.add(outputDataPoint);
        }

        List<MedoidCluster> clusters = setupClusters(outputDataPoints);
        ClusterContext clusterContext = new ClusterContext(clusters);

        boolean pointsMoved = true;
        clusterContext.start();
        while (pointsMoved) {
            clusterContext.iterate();
            pointsMoved = clusterDataPoints(outputDataPoints, clusters);
            updateMedoids(clusters);
        }
        clusterContext.stop();

        return clusterContext;
    }

    private void updateMedoids(List<MedoidCluster> clusters) {
        for (MedoidCluster cluster : clusters) {
            cluster.updateMedoid();
        }
    }

    private boolean clusterDataPoints(List<OutputDataPoint> dataPoints, List<MedoidCluster> clusters) {
        boolean pointsMoved = false;
        List<OutputDataPoint> medoids = new ArrayList<>();
        for (MedoidCluster medoidCluster : clusters) {
            OutputDataPoint medoid = medoidCluster.getMedoid();
            medoids.add(medoid);
        }

        assert clusters.stream().allMatch(cluster -> cluster.getMedoid().getCluster().get().equals(cluster));

        for (OutputDataPoint dataPoint : dataPoints) {
            if (!medoids.contains(dataPoint)) {
                MedoidCluster closestCluster = findClosestCluster(clusters, dataPoint);
                pointsMoved |= dataPoint.moveToCluster(closestCluster);
            }
        }
        return pointsMoved;
    }

    private List<MedoidCluster> setupClusters(List<OutputDataPoint> dataPoints) {
        List<MedoidCluster> clusters = new ArrayList<>();

        for (int index = 0; index < k; index++) {
            OutputDataPoint dataPoint = dataPoints.get(index);
            MedoidCluster cluster = new MedoidCluster();
            dataPoint.moveToCluster(cluster);
            cluster.updateMedoid();
            clusters.add(cluster);
        }
        return clusters;
    }

    private MedoidCluster findClosestCluster(List<MedoidCluster> clusters, OutputDataPoint dataPoint) {
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
