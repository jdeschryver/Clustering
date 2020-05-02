import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class KMeans implements ClusterAlgorithm {

    private int k;

    public KMeans(int k) {
        this.k = k;
    }

    @Override
    public ClusterContext fit(List<Coordinate> dataPoints) {
        List<DataPointClusterPair<MedoidCluster>> dataPointClusterPairs = new ArrayList<>();
        for (Coordinate dataPoint : dataPoints) {
            DataPointClusterPair<MedoidCluster> pair = new DataPointClusterPair<>(dataPoint);
            dataPointClusterPairs.add(pair);
        }

        Collections.shuffle(dataPointClusterPairs);

        List<MedoidCluster> clusters = new ArrayList<>();

        for (int index = 0; index < k; index++) {
            DataPointClusterPair<MedoidCluster> pair = dataPointClusterPairs.get(index);
            MedoidCluster cluster = new MedoidCluster(pair.getDataPoint());
            pair.setCluster(cluster);
            clusters.add(cluster);
        }

        int i = 0;
        boolean pointsMoved;
        long start = System.currentTimeMillis();
        do {
            i++;
            pointsMoved = false;
            for (DataPointClusterPair<MedoidCluster> pair : dataPointClusterPairs) {
                Coordinate dataPoint = pair.getDataPoint();
                MedoidCluster closestCluster = findClosestCluster(clusters, dataPoint);
                pointsMoved |= pair.moveToCluster(closestCluster);
            }
            for (MedoidCluster cluster : clusters) {
                cluster.updateMedoid();
            }
        } while (pointsMoved);
        long end = System.currentTimeMillis();
        System.out.println("Duration: " + (end - start) + "ms, iterations: " + i);

        return new ClusterContext(clusters);
    }

    private MedoidCluster findClosestCluster(List<MedoidCluster> clusters, Coordinate dataPoint) {
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

    @Override
    public List<ClusterLabel> predict(ClusterContext clusterContext, List<DataPoint> dataPoints) {
        return null;
    }
}
