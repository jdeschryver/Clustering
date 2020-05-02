package clusters;

import datapoints.DataPoint;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MedoidCluster<D extends DataPoint<D>> implements Cluster<D> {

    private D medoid;
    private final List<D> dataPoints;

    public MedoidCluster(D medoid) {
        this.medoid = medoid;
        this.dataPoints = new ArrayList<>();
        dataPoints.add(medoid);
    }

    @Override
    public Collection<D> getElements() {
        return dataPoints;
    }

    @Override
    public void addElement(D dataPoint) {
        dataPoints.add(dataPoint);
    }

    @Override
    public void removeElement(D dataPoint) {
        dataPoints.remove(dataPoint);
    }

    public void updateMedoid() {
        double smallestDistanceSum = Double.MAX_VALUE;
        D currentBestMedoid = null;
        for (D medoidCandidate : dataPoints) {
            double totalDistanceToMedoidCandidate = 0d;
            for (D neighbor : dataPoints) {
                if (medoidCandidate != neighbor) {
                    totalDistanceToMedoidCandidate += medoidCandidate.distanceTo(neighbor);
                }
            }

            if (totalDistanceToMedoidCandidate < smallestDistanceSum) {
                currentBestMedoid = medoidCandidate;
                smallestDistanceSum = totalDistanceToMedoidCandidate;
            }
        }

        if (currentBestMedoid != null) {
            medoid = currentBestMedoid;
        }
    }

    public double distanceToMedoid(D dataPoint) {
        return medoid.distanceTo(dataPoint);
    }
}
