package datapoints;

import clusters.Cluster;

import java.util.Optional;

public class OutputDataPoint {

    private final InputDataPoint inputDataPoint;
    private Cluster cluster;

    public OutputDataPoint(InputDataPoint inputDataPoint) {
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


    public double distanceTo(OutputDataPoint outputDataPoint) {
        return inputDataPoint.distanceTo(outputDataPoint.inputDataPoint);
    }
}
