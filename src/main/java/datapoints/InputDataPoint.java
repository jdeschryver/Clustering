package datapoints;

public interface InputDataPoint {

    <D extends InputDataPoint> double distanceTo(D d);

}
