package algorithms;

import clusters.ClusterContext;
import clusters.ClusterLabel;
import datapoints.DataPoint;

import java.util.List;

public interface ClusterAlgorithm<D extends DataPoint<D>> {

    ClusterContext fit(List<D> dataPoints);

    List<ClusterLabel> predict(ClusterContext clusterContext, List<D> dataPoints);
}
