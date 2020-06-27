package clusters;

import datapoints.OutputDataPoint;

import java.util.Collection;

public interface Cluster {

    Collection<OutputDataPoint> getElements();

    void removeElement(OutputDataPoint dataPoint);

    void addElement(OutputDataPoint dataPoint);
}
