import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Util {

    public static List<DataPoint> selectRandomK(int k, List<DataPoint> dataPoints) {
        List<DataPoint> copiedDataPoints = new ArrayList<>(dataPoints);
        Collections.shuffle(copiedDataPoints);
        List<DataPoint> randomKElements = new ArrayList<>();
        for (int index = 0; index < k; index++) {
            DataPoint randomElement = copiedDataPoints.get(index);
            randomKElements.add(randomElement);
        }
        return randomKElements;
    }
}
