import java.util.List;

public class ClusterContext {

    private final List<MedoidCluster> clusters;

    public ClusterContext(List<MedoidCluster> clusters) {
        this.clusters = clusters;
    }

    public List<MedoidCluster> getClusters() {
        return clusters;
    }

    public int getNumberOfClusters() {
        return clusters.size();
    }
}
