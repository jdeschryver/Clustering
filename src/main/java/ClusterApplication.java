import clusters.ClusterContext;
import datapoints.Coordinate;
import datapoints.InputDataPoint;
import metrics.Elbow;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ClusterApplication {

    public static void main(String[] args) {
        new ClusterApplication().runTest();
    }

    private void runTest() {
        List<InputDataPoint> coordinates = getCoordinates();
        System.out.println("Number of coordinates: " + coordinates.size());
        long startTime = System.currentTimeMillis();
        ClusterContext clusterContext = Elbow.findBestCluster(coordinates, 4, 400);
        long stopTime = System.currentTimeMillis();
        System.out.println("Time: " + (stopTime - startTime) / 1000 +"s");
        System.out.println(clusterContext);
    }

    private List<InputDataPoint> getCoordinates() {
        List<InputDataPoint> coordinates = new ArrayList<>();
        URL res = getClass().getClassLoader().getResource("coordinates");

        assert res != null;
        try (BufferedReader reader = new BufferedReader(new FileReader(Paths.get(res.toURI()).toFile()))) {
            String line = reader.readLine();
            int counter = 0;
            while (line != null && counter++ < 30000) {
                String[] latLons = line.split(",");
                Coordinate coordinate = new Coordinate(Double.parseDouble(latLons[0]), Double.parseDouble(latLons[1]));
                coordinates.add(coordinate);
                line = reader.readLine();
            }
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        return coordinates;
    }
}
