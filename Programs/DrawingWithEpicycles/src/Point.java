import java.util.ArrayList;

public record Point(double x, double y) {
    public static ArrayList<Point> sortPointsByDistance(ArrayList<Point> points) {
        var res = new ArrayList<Point>();
        Point cur = points.remove(0);
        res.add(cur);

        while (!points.isEmpty()) {
            double minDistance = Double.MAX_VALUE;
            int trgIdx = 0;

            for (int i = 0; i < points.size(); ++i) {
                double curDistance = findDistance(cur, points.get(i));

                if (curDistance < minDistance) {
                    minDistance = curDistance;
                    trgIdx = i;
                }
            }
            cur = points.remove(trgIdx);
            res.add(cur);
        }
        return res;
    }

    public static double findDistance(Point p1, Point p2) {
        return Math.sqrt(Math.pow(p1.x() - p2.x(), 2) + Math.pow(p1.y() - p2.y(), 2));
    }
}
