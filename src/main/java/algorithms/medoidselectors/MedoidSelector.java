package algorithms.medoidselectors;

import datapoints.ClusteredDataPoint;

import java.util.List;

public interface MedoidSelector {

    List<ClusteredDataPoint> calculateInitialMedoids(List<ClusteredDataPoint> locations, int  numberOfClusters);

    static MedoidSelector farthestPointMedoidSelector() {
        return new FarthestPointMedoidSelector();
    }

    static MedoidSelector random() {
        return new RandomMedoidSelector();
    }
}
