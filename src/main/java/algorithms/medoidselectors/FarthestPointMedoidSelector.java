package algorithms.medoidselectors;

import datapoints.ClusteredDataPoint;

import java.util.ArrayList;
import java.util.List;

class FarthestPointMedoidSelector implements MedoidSelector {

    public List<ClusteredDataPoint> calculateInitialMedoids(List<ClusteredDataPoint> locations, int numberOfClusters) {
        int numberOfMedoids = Math.min(numberOfClusters, locations.size());
        Selector selector = new Selector(locations, numberOfMedoids);

        return selector.collectRandomBestMedoids();
    }

    private static class Selector {
        private final List<ClusteredDataPoint> medoids;
        private final List<ClusteredDataPoint> remainingLocations;
        private final int numberOfClusters;

        private Selector(List<ClusteredDataPoint> remainingLocations, int numberOfClusters) {
            this.remainingLocations = remainingLocations;
            this.numberOfClusters = numberOfClusters;
            this.medoids = new ArrayList<>();

            ClusteredDataPoint firstMedoid = remainingLocations.get(0);
            medoids.add(firstMedoid);
            remainingLocations.remove(firstMedoid);
        }

        private List<ClusteredDataPoint> collectRandomBestMedoids() {
            while (medoids.size() < numberOfClusters) {
                double[] distances = initArrayOnInfinity(remainingLocations.size());

                for (int locationIndex = 0; locationIndex < remainingLocations.size(); locationIndex++) {
                    ClusteredDataPoint location = remainingLocations.get(locationIndex);
                    for (ClusteredDataPoint medoid : medoids) {
                        double currentDistance = location.distanceTo(medoid);
                        distances[locationIndex] = Math.min(distances[locationIndex], currentDistance);
                    }
                }

                ClusteredDataPoint farthestAwayPoint = getFarthestAwayLocation(distances);
                remainingLocations.remove(farthestAwayPoint);
                medoids.add(farthestAwayPoint);
            }

            return new ArrayList<>(medoids.subList(0, numberOfClusters));
        }

        private ClusteredDataPoint getFarthestAwayLocation(double[] distances) {
            double maxDistance = Double.NEGATIVE_INFINITY;
            ClusteredDataPoint farthestAwayPoint = null;
            for (int distanceIndex = 0; distanceIndex < distances.length; distanceIndex++) {
                if (distances[distanceIndex] > maxDistance) {
                    maxDistance = distances[distanceIndex];
                    farthestAwayPoint = remainingLocations.get(distanceIndex);
                }
            }
            return farthestAwayPoint;
        }

        private double[] initArrayOnInfinity(int size) {
            double[] distances = new double[size];

            for (int locationIndex = 0; locationIndex < size; locationIndex++) {
                distances[locationIndex] = Double.MAX_VALUE;
            }
            return distances;
        }
    }
}
