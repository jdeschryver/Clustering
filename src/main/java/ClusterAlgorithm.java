import java.util.List;

public interface ClusterAlgorithm {

    ClusterContext fit(List<Coordinate> dataPoints);

    List<ClusterLabel> predict(ClusterContext clusterContext, List<DataPoint> dataPoints);
}
