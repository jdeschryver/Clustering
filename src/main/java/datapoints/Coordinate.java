package datapoints;

public class Coordinate implements InputDataPoint {

    private final double latitude;
    private final double longitude;

    public Coordinate(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    public <D extends InputDataPoint> double distanceTo(D d) {
        if (d instanceof Coordinate) {
            Coordinate coordinate = (Coordinate) d;
            if (this == coordinate) {
                return 0d;
            }
            return Math.sqrt(
                    (coordinate.latitude - latitude) * (coordinate.latitude - latitude)
                            + (coordinate.longitude - longitude) * (coordinate.longitude - longitude)
            );
        }
        throw new IllegalArgumentException("Incomparable data points: Coordinate and " + d.getClass());
    }
}
