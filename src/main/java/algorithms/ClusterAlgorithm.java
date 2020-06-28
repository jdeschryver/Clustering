package algorithms;

import clusters.Cluster;
import datapoints.InputDataPoint;

import java.util.List;

public interface ClusterAlgorithm {

    List<Cluster> fit(List<InputDataPoint> dataPoints);
}
