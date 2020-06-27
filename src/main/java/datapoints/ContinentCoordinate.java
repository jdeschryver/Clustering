package datapoints;

public class ContinentCoordinate implements InputDataPoint {

    private final Coordinate coordinate;
    private final int continent;

    public ContinentCoordinate(double latitude, double longitude, int continent) {
        this.coordinate = new Coordinate(latitude, longitude);
        this.continent = continent;
    }


    @Override
    public <D extends InputDataPoint> double distanceTo(D d) {
        if (d instanceof ContinentCoordinate) {
            ContinentCoordinate continentCoordinate = (ContinentCoordinate) d;
            if (continent == continentCoordinate.continent) {
                return coordinate.distanceTo(continentCoordinate.coordinate);
            }
            return Double.MAX_VALUE;
        }
        throw new IllegalArgumentException("Incomparable data points: ContinentCoordinate and " + d.getClass());
    }

}
