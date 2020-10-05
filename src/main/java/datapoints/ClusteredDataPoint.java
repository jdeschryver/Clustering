package datapoints;

import clusters.Cluster;

import java.util.Optional;

public class ClusteredDataPoint {

    private final InputDataPoint inputDataPoint;
    private Cluster cluster;

    public ClusteredDataPoint(InputDataPoint inputDataPoint) {
        this.inputDataPoint = inputDataPoint;
    }

    public Optional<Cluster> getCluster() {
        return Optional.ofNullable(cluster);
    }

    public boolean moveToCluster(Cluster newCluster) {
        if (newCluster != cluster) {
            if (cluster != null) {
                cluster.removeElement(this);
            }
            cluster = newCluster;
            newCluster.addElement(this);
            return true;
        }
        return false;
    }

    public InputDataPoint getInputDataPoint() {
        return inputDataPoint;
    }

    public double distanceTo(ClusteredDataPoint clusteredDataPoint) {
        return inputDataPoint.distanceTo(clusteredDataPoint.inputDataPoint);
    }
}
