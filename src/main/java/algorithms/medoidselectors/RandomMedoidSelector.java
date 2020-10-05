package algorithms.medoidselectors;

import datapoints.ClusteredDataPoint;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class RandomMedoidSelector implements MedoidSelector {

    @Override
    public List<ClusteredDataPoint> calculateInitialMedoids(List<ClusteredDataPoint> locations, int numberOfClusters) {
        int numberOfMedoids = Math.min(numberOfClusters, locations.size());
        List<ClusteredDataPoint> shuffledLocations = new ArrayList<>(locations);
        Collections.shuffle(shuffledLocations);

        return new ArrayList<>(shuffledLocations.subList(0, numberOfMedoids));
    }
}
