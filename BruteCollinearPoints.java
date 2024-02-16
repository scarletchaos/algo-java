/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class BruteCollinearPoints {
    private LineSegment[] lineSegments;
    private int sz = 0;

    public BruteCollinearPoints(Point[] points) {
        if (points == null) throw new IllegalArgumentException();
        for (int i = 0; i < points.length; i++)
            if (points[i] == null) throw new IllegalArgumentException();
        Arrays.sort(points);
        for (int i = 0; i < points.length - 1; i++) {
            if (points[i].slopeTo(points[i + 1]) == Double.NEGATIVE_INFINITY)
                throw new IllegalArgumentException();
        }
        lineSegments = new LineSegment[points.length];
        for (int a = 0; a < points.length; a++)
            for (int b = a + 1; b < points.length; b++)
                for (int c = b + 1; c < points.length; c++)
                    for (int d = c + 1; d < points.length; d++) {
                        Point u = points[a];
                        Point v = points[b];
                        Point x = points[c];
                        Point y = points[d];
                        // System.out.println("pts" + sz);
                        // System.out.println(u);
                        // System.out.println(v);
                        // System.out.println(x);
                        // System.out.println(y);
                        Point max, min;
                        if (u == v || u == x || u == y || v == x || v == y ||
                                x == y || u == null || v == null || x == null || y == null) {
                            throw new IllegalArgumentException("");
                        }
                        if (u.slopeTo(v) == u.slopeTo(x)
                                && u.slopeTo(x) == u.slopeTo(y)) {

                            if (u.compareTo(v) > 0 && u.compareTo(x) > 0 && u.compareTo(y) > 0)
                                max = u;
                            else if (v.compareTo(x) > 0 && v.compareTo(y) > 0) {
                                max = v;
                            }
                            else if (x.compareTo(y) > 0) max = x;
                            else max = y;

                            if (u.compareTo(v) < 0 && u.compareTo(x) < 0 && u.compareTo(y) < 0)
                                min = u;
                            else if (v.compareTo(x) < 0 && v.compareTo(y) < 0) {
                                min = v;
                            }
                            else if (x.compareTo(y) < 0) min = x;
                            else min = y;
                            lineSegments[sz++] = new LineSegment(min, max);
                        }
                    }
        LineSegment[] ls = new LineSegment[sz];
        for (int i = 0; i < sz; i++) ls[i] = lineSegments[i];
        lineSegments = ls;
    }    // finds all line segments containing 4 points {}

    public int numberOfSegments() {
        return sz;
    }       // the number of line segments

    public LineSegment[] segments() {
        LineSegment[] ret = new LineSegment[sz];
        for (int i = 0; i < sz; i++) ret[i] = lineSegments[i];
        return ret;
    }

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }       // the line segments
}
