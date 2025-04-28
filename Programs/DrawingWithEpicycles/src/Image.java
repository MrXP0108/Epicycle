import java.util.*;

public class Image {
    private ArrayList<Point> points = new ArrayList<>();
    public Image(Scanner s) {
        while (s.hasNextLine()) {
            String[] temp = s.nextLine().split(",");
            points.add(new Point(Double.parseDouble(temp[0]), Double.parseDouble(temp[1])));
        }
        points = Point.sortPointsByDistance(points);
    }

    public Double[] getAllX() {
        var xVal = new Double[points.size()];
        for (int i = 0; i < points.size(); ++i) {
            xVal[i] = points.get(i).x();
        }
        return xVal;
    }

    public Double[] getAllY() {
        var yVal = new Double[points.size()];
        for (int i = 0; i < points.size(); ++i) {
            yVal[i] = points.get(i).y();
        }
        return yVal;
    }

    static Random rand = new Random();
    static int[] randIdx;

    public Double[] getSamplesX(double proportion) {
        int n_samples = (int)(points.size() * proportion);
        var xVal = new Double[n_samples];

        randIdx = new int[n_samples];
        for (int i = 0; i < n_samples; ++i) {
            randIdx[i] = rand.nextInt(points.size() - 1);
        }
        Arrays.sort(randIdx);

        for (int i = 0; i < n_samples; ++i) {
            xVal[i] = points.get(randIdx[i]).x();
        }
        return xVal;
    }

    public Double[] getSamplesY(double proportion) {
        int n_samples = (int)(points.size() * proportion);
        var yVal = new Double[n_samples];

        for (int i = 0; i < n_samples; ++i) {
            yVal[i] = points.get(randIdx[i]).y();
        }
        return yVal;
    }
}
