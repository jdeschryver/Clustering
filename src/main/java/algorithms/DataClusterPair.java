package algorithms;

import clusters.Cluster;
import datapoints.DataPoint;

public class DataClusterPair<D extends DataPoint<D>, C extends Cluster<D>> {

    private final D dataPoint;
    private C cluster;

    public DataClusterPair(D dataPoint) {
        this.dataPoint = dataPoint;
    }

    public D getDataPoint() {
        return dataPoint;
    }

    public boolean isClustered() {
        return cluster != null;
    }

    public C getCluster() {
        return cluster;
    }

    public void setCluster(C cluster) {
        this.cluster = cluster;
    }

    public boolean moveToCluster(C newCluster) {
        if (newCluster != cluster) {
            if (cluster != null) {
                cluster.removeElement(dataPoint);
            }
            newCluster.addElement(dataPoint);
            cluster = newCluster;
            return true;
        }
        return false;
    }
}
