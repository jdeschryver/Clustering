package clusters;

import datapoints.OutputDataPoint;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class MedoidCluster implements Cluster {

    private OutputDataPoint medoid;
    private final List<OutputDataPoint> dataPoints;

    public MedoidCluster() {
        this.dataPoints = new ArrayList<>();
    }

    @Override
    public Collection<OutputDataPoint> getElements() {
        return dataPoints;
    }

    @Override
    public void addElement(OutputDataPoint dataPoint) {
        dataPoints.add(dataPoint);
        assert isValid();
    }

    @Override
    public void removeElement(OutputDataPoint dataPoint) {
        dataPoints.remove(dataPoint);
        assert isValid();
    }

    public void updateMedoid() {
        assert isValid();
        double smallestDistanceSum = Double.MAX_VALUE;
        for (OutputDataPoint medoidCandidate : dataPoints) {
            double totalDistanceToMedoidCandidate = 0d;
            for (OutputDataPoint neighbor : dataPoints) {
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

    public double distanceToMedoid(OutputDataPoint dataPoint) {
        return medoid.distanceTo(dataPoint);
    }

    private boolean isValid() {
        for (OutputDataPoint dataPoint : dataPoints) {
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

    public OutputDataPoint getMedoid() {
        return medoid;
    }
}
