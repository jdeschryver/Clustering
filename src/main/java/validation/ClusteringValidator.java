package validation;

import clusters.Cluster;
import datapoints.ClusteredDataPoint;
import datapoints.InputDataPoint;

import java.util.*;
import java.util.stream.Collectors;

public class ClusteringValidator {

    public static <T extends Cluster> boolean isValidClustering(List<T> clusters) {
        Set<ClusteredDataPoint> clusteredDataPoints = new HashSet<>();

        for (Cluster cluster : clusters) {
            for (ClusteredDataPoint element : cluster.getElements()) {
                if (!clusteredDataPoints.add(element)) {
                    return false;
                }
            }
        }
        return true;
    }


    public static <T extends Cluster> boolean isValidCompleteClustering(List<T> clusters, List<InputDataPoint> dataPoints) {
        boolean validClustering = isValidClustering(clusters);

        if (validClustering) {
            List<InputDataPoint> clusteredDataPoints = clusters.stream()
                    .map(Cluster::getElements)
                    .flatMap(Collection::stream)
                    .map(ClusteredDataPoint::getInputDataPoint)
                    .collect(Collectors.toList());

            for (InputDataPoint dataPoint : dataPoints) {
                if (!clusteredDataPoints.contains(dataPoint)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

}
