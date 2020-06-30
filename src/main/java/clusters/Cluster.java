package clusters;

import datapoints.ClusteredDataPoint;

import java.util.Collection;

public interface Cluster {

    Collection<ClusteredDataPoint> getElements();

    void removeElement(ClusteredDataPoint dataPoint);

    void addElement(ClusteredDataPoint dataPoint);
}
