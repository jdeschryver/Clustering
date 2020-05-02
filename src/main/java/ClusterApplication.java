import clusters.ClusterContext;
import algorithms.KMeans;
import datapoints.Coordinate;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ClusterApplication {

    private static final String PATH = "/Users/jandeschryver/projects/personal/Clustering/src/main/resources/";

    public static void main(String[] args) {
        List<Coordinate> coordinates = getCoordinates();

        System.out.println("Number of coordinates: " + coordinates.size());
        for (int numberOfClusters = 300; numberOfClusters < 500; numberOfClusters++) {
            KMeans<Coordinate> kMeans = new KMeans<>(numberOfClusters);
            ClusterContext clusterContext = kMeans.fit(coordinates);
            System.out.println(clusterContext);
        }
    }

    private static List<Coordinate> getCoordinates() {
        List<Coordinate> coordinates = new ArrayList<>();
        File coordinatesFile = new File(PATH + "coordinates");

        try (BufferedReader reader = new BufferedReader(new FileReader(coordinatesFile))) {
            String line = reader.readLine();
            int counter = 0;
            while (line != null && counter++ < 30000) {
                String[] latLons = line.split(",");
                Coordinate coordinate = new Coordinate(Double.parseDouble(latLons[0]), Double.parseDouble(latLons[1]));
                coordinates.add(coordinate);
                line = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return coordinates;
    }
}
