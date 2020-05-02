import java.util.Collection;

public interface Cluster {

    Collection<Coordinate> getElements();

    void removeElement(Coordinate dataPoint);

    void addElement(Coordinate dataPoint);
}
