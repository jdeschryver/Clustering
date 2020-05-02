package datapoints;

public class ContinentCoordinate implements DataPoint<ContinentCoordinate> {

    private final Coordinate coordinate;
    private final int continent;

    public ContinentCoordinate(double latitude, double longitude, int continent) {
        this.coordinate = new Coordinate(latitude, longitude);
        this.continent = continent;
    }

    @Override
    public double distanceTo(ContinentCoordinate continentCoordinate) {
        if (continent == continentCoordinate.continent) {
            return coordinate.distanceTo(continentCoordinate.coordinate);
        }
        return Double.MAX_VALUE;
    }
}
