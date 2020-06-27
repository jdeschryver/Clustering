package clusters;

import metrics.Silhouette;

import java.util.ArrayList;
import java.util.List;

public class ClusterContext {

    private final List<Cluster> clusters;
    private long startTime;
    private long endTime;
    private int iterations;

    public ClusterContext(List<? extends Cluster> clusters) {
        this.clusters = new ArrayList<>(clusters);
    }

    public void start() {
        startTime = System.currentTimeMillis();
    }

    public void stop() {
        endTime = System.currentTimeMillis();
    }

    public long getDurationInMillis() {
        return endTime - startTime;
    }

    public int getIterations() {
        return iterations;
    }

    public void iterate() {
        iterations++;
    }

    public List<Cluster> getClusters() {
        return clusters;
    }

    public int getNumberOfClusters() {
        return clusters.size();
    }

    @Override
    public String toString() {
        return "Number of clusters: " + getNumberOfClusters()
                + ", Duration: " + getDurationInMillis() + "ms"
                + ", Total iterations: " + getIterations();
    }
}
