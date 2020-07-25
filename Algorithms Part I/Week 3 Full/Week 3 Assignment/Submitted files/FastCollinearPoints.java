import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class FastCollinearPoints {

    private final LineSegment[] all;

    public FastCollinearPoints(
            Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException("illegal array");
        }

        for (Point p : points) {
            if (p == null) {
                throw new IllegalArgumentException("illegal point");
            }
        }

        List<LineSegment> arr = new LinkedList<>();
        Point[] copy = points.clone();
        Arrays.sort(copy);

        for (int i = 1; i < copy.length; i++) {
            if (copy[i].compareTo(copy[i - 1]) == 0) {
                throw new IllegalArgumentException("duplicate");
            }
        }

        int d;

        for (int i = 0; i < copy.length; i++) {

            Point[] all = copy.clone();

            Arrays.sort(all, copy[i].slopeOrder());
            d = 1;

            double previous = Double.NEGATIVE_INFINITY;
            int start = 0;

            for (int j = 0; j < all.length; j++) {
                double current = copy[i].slopeTo(all[j]);

                if (Double.compare(current, previous) != 0) {
                    if (d >= 4 && copy[i].compareTo(all[start]) <= 0) {
                        arr.add(new LineSegment(copy[i], all[j - 1]));
                    }
                    d = 1;
                    start = j;
                }
                else if (j == all.length - 1) {
                    if (d >= 3 && copy[i].compareTo(all[start]) <= 0) {
                        arr.add(new LineSegment(copy[i], all[j]));
                    }
                    d = 1;
                }
                d++;
                previous = current;
            }
        }

        all = arr.toArray(new LineSegment[0]);
    }

    public int numberOfSegments() {
        return all.length;
    }

    public LineSegment[] segments() {
        return all.clone();
    }
}

