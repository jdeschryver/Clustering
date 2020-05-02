public class DataPointClusterPair<C extends Cluster> {

    private final Coordinate dataPoint;
    private C cluster;

    public DataPointClusterPair(Coordinate dataPoint) {
        this.dataPoint = dataPoint;
    }

    public Coordinate getDataPoint() {
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
