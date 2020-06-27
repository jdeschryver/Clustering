package algorithms;

import clusters.ClusterContext;
import datapoints.InputDataPoint;

import java.util.List;

public interface ClusterAlgorithm {

    ClusterContext fit(List<InputDataPoint> dataPoints);
}
