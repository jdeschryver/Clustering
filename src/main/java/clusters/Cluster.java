package clusters;

import datapoints.DataPoint;

import java.util.Collection;

public interface Cluster<D extends DataPoint<D>> {

    Collection<D> getElements();

    void removeElement(D dataPoint);

    void addElement(D dataPoint);
}
