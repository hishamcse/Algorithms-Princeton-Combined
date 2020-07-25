import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BruteCollinearPoints {

    private final LineSegment[] all;

    public BruteCollinearPoints(Point[] points) {  // finds all line segments containing 4 points

        if (points == null) {
            throw new IllegalArgumentException("illegal");
        }

        for (Point p : points) {
            if (p == null) {
                throw new IllegalArgumentException();
            }
        }

        Point[] copy = Arrays.copyOf(points, points.length);

        List<LineSegment> arr = new ArrayList<>();
        Arrays.sort(copy);

        for (int i = 1; i < copy.length; i++) {
            if (copy[i].compareTo(copy[i - 1]) == 0) {
                throw new IllegalArgumentException();
            }
        }

        for (int i = 0; i < copy.length - 3; i++) {
            for (int j = i + 1; j < copy.length - 2; j++) {
                for (int k = j + 1; k < copy.length - 1; k++) {
                    for (int r = k + 1; r < copy.length; r++) {
                        if (copy[i].slopeTo(copy[j]) == copy[i].slopeTo(copy[k])
                                && copy[i].slopeTo(copy[j]) == copy[i].slopeTo(copy[r])) {
                            arr.add(new LineSegment(copy[i], copy[r]));
                        }
                    }
                }
            }
        }

        all = arr.toArray(new LineSegment[0]);

    }

    public int numberOfSegments() {             // the number of line segments
        return all.length;
    }

    public LineSegment[] segments() {             // the line segments
        return all.clone();
    }
}
