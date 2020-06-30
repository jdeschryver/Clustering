package clusters;

import datapoints.ClusteredDataPoint;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class MedoidCluster implements Cluster {

    private ClusteredDataPoint medoid;
    private final List<ClusteredDataPoint> dataPoints;

    public MedoidCluster() {
        this.dataPoints = new ArrayList<>();
    }

    @Override
    public Collection<ClusteredDataPoint> getElements() {
        return dataPoints;
    }

    @Override
    public void addElement(ClusteredDataPoint dataPoint) {
        dataPoints.add(dataPoint);
        assert isValid();
    }

    @Override
    public void removeElement(ClusteredDataPoint dataPoint) {
        dataPoints.remove(dataPoint);
        assert isValid();
    }

    public void updateMedoid() {
        assert isValid();
        double smallestDistanceSum = Double.MAX_VALUE;
        for (ClusteredDataPoint medoidCandidate : dataPoints) {
            double totalDistanceToMedoidCandidate = 0d;
            for (ClusteredDataPoint neighbor : dataPoints) {
                if (medoidCandidate != neighbor) {
                    totalDistanceToMedoidCandidate += medoidCandidate.distanceTo(neighbor);
                }
            }

            if (totalDistanceToMedoidCandidate < smallestDistanceSum) {
                medoid = medoidCandidate;
                smallestDistanceSum = totalDistanceToMedoidCandidate;
            }
        }
        assert medoid.getCluster().map(cluster -> cluster.equals(this)).orElse(false);
    }

    public double distanceToMedoid(ClusteredDataPoint dataPoint) {
        return medoid.distanceTo(dataPoint);
    }

    private boolean isValid() {
        for (ClusteredDataPoint dataPoint : dataPoints) {
            Optional<Cluster> cluster = dataPoint.getCluster();
            if (!cluster.isPresent()) {
                return false;
            }
            Cluster c = cluster.get();
            if (!c.equals(this)) {
                return false;
            }
        }
        return true;
    }

    public ClusteredDataPoint getMedoid() {
        return medoid;
    }
}
