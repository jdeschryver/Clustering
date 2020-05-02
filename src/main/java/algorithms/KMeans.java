package algorithms;

import clusters.ClusterContext;
import clusters.MedoidCluster;
import datapoints.DataPoint;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class KMeans<D extends DataPoint<D>> implements ClusterAlgorithm<D> {

    private final int k;

    public KMeans(int k) {
        this.k = k;
    }

    @Override
    public ClusterContext fit(List<D> dataPoints) {
        List<DataClusterPair<D, MedoidCluster<D>>> dataClusterPairs = setupDataPoints(dataPoints);
        List<MedoidCluster<D>> clusters = setupClusters(dataClusterPairs);

        ClusterContext clusterContext = new ClusterContext(clusters);

        boolean pointsMoved = true;
        clusterContext.start();
        while (pointsMoved) {
            clusterContext.iterate();
            pointsMoved = clusterDataPoints(dataClusterPairs, clusters);
            updateMedoids(clusters);
        }
        clusterContext.stop();

        return clusterContext;
    }

    private void updateMedoids(List<MedoidCluster<D>> clusters) {
        for (MedoidCluster<D> cluster : clusters) {
            cluster.updateMedoid();
        }
    }

    private boolean clusterDataPoints(List<DataClusterPair<D, MedoidCluster<D>>> dataClusterPairs, List<MedoidCluster<D>> clusters) {
        boolean pointsMoved = false;
        for (DataClusterPair<D, MedoidCluster<D>> pair : dataClusterPairs) {
            D dataPoint = pair.getDataPoint();
            MedoidCluster<D> closestCluster = findClosestCluster(clusters, dataPoint);
            pointsMoved |= pair.moveToCluster(closestCluster);
        }
        return pointsMoved;
    }

    private List<MedoidCluster<D>> setupClusters(List<DataClusterPair<D, MedoidCluster<D>>> dataClusterPairs) {
        List<MedoidCluster<D>> clusters = new ArrayList<>();

        for (int index = 0; index < k; index++) {
            DataClusterPair<D, MedoidCluster<D>> pair = dataClusterPairs.get(index);
            MedoidCluster<D> cluster = new MedoidCluster<>(pair.getDataPoint());
            pair.setCluster(cluster);
            clusters.add(cluster);
        }
        return clusters;
    }

    private List<DataClusterPair<D, MedoidCluster<D>>> setupDataPoints(List<D> dataPoints) {
        List<DataClusterPair<D, MedoidCluster<D>>> dataClusterPairs = new ArrayList<>();
        for (D dataPoint : dataPoints) {
            DataClusterPair<D, MedoidCluster<D>> pair = new DataClusterPair<>(dataPoint);
            dataClusterPairs.add(pair);
        }

        Collections.shuffle(dataClusterPairs);
        return dataClusterPairs;
    }

    private MedoidCluster<D> findClosestCluster(List<MedoidCluster<D>> clusters, D dataPoint) {
        double currentSmallestMedoidDistance = Double.MAX_VALUE;
        MedoidCluster<D> currentClosestMedoid = null;
        for (MedoidCluster<D> medoidCluster : clusters) {
            double distanceToMedoid = medoidCluster.distanceToMedoid(dataPoint);
            if (distanceToMedoid < currentSmallestMedoidDistance) {
                currentSmallestMedoidDistance = distanceToMedoid;
                currentClosestMedoid = medoidCluster;
            }
        }
        return currentClosestMedoid;
    }
}
