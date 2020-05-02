import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MedoidCluster implements Cluster {

    private Coordinate medoid;
    private final List<Coordinate> dataPoints;

    public MedoidCluster(Coordinate medoid) {
        this.medoid = medoid;
        this.dataPoints = new ArrayList<>();
        dataPoints.add(medoid);
    }

    @Override
    public Collection<Coordinate> getElements() {
        return dataPoints;
    }

    @Override
    public void addElement(Coordinate dataPoint) {
        dataPoints.add(dataPoint);
    }

    @Override
    public void removeElement(Coordinate dataPoint) {
        dataPoints.remove(dataPoint);
    }

    void updateMedoid() {
        double smallestDistanceSum = Double.MAX_VALUE;
        Coordinate currentBestMedoid = null;
        for (Coordinate medoidCandidate : dataPoints) {
            double totalDistanceToMedoidCandidate = 0d;
            for (Coordinate neighbor : dataPoints) {
                totalDistanceToMedoidCandidate += medoidCandidate.distanceTo(neighbor);
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

    double distanceToMedoid(Coordinate dataPoint) {
        return medoid.distanceTo(dataPoint);
    }

    @Override
    public String toString() {
        return "MedoidCluster{" +
            "medoid=" + medoid + ", dataPoints=" + dataPoints +
            '}';
    }
}
