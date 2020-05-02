package algorithms;

import clusters.ClusterContext;
import datapoints.DataPoint;

import java.util.List;

public interface ClusterAlgorithm<D extends DataPoint<D>> {

    ClusterContext fit(List<D> dataPoints);
}
