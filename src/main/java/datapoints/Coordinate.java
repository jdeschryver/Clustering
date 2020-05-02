package datapoints;

public class Coordinate implements DataPoint<Coordinate> {

    private final double latitude;
    private final double longitude;

    public Coordinate(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    public double distanceTo(Coordinate coordinate) {
        if (this == coordinate) {
            return 0d;
        }
        double earthRadius = 6371000; //meters
        double lat2 = coordinate.latitude;

        double dLat = Math.toRadians(lat2 - latitude);
        double dLng = Math.toRadians(coordinate.longitude - longitude);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
            Math.cos(Math.toRadians(latitude)) * Math.cos(Math.toRadians(lat2)) *
                Math.sin(dLng / 2) * Math.sin(dLng / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return earthRadius * c;
    }
}
