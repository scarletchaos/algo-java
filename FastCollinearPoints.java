/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class FastCollinearPoints {
    private LineSegment[] segments;
    private int sz = 0;

    public FastCollinearPoints(Point[] points) {
        if (points == null) throw new IllegalArgumentException();
        for (int i = 0; i < points.length; i++)
            if (points[i] == null) throw new IllegalArgumentException();
        Arrays.sort(points);
        for (int i = 0; i < points.length - 1; i++) {
            if (points[i].slopeTo(points[i + 1]) == Double.NEGATIVE_INFINITY)
                throw new IllegalArgumentException();
        }
        segments = new LineSegment[points.length * points.length];
        for (int i = 0; i < points.length - 3; i++) {
            Point[] ptsNoI = new Point[points.length - 1];
            int szP = 0;
            for (int j = 0; j < points.length; j++)
                if (i != j) ptsNoI[szP++] = points[j];
            Arrays.sort(ptsNoI, 0, ptsNoI.length);
            Arrays.sort(ptsNoI, 0, ptsNoI.length, points[i].slopeOrder());
            // for (Point pt : points) System.out.println(pt);
            double currSlope = points[i].slopeTo(ptsNoI[0]);
            int start = 0;
            int end = 0;
            for (int j = 1; j < ptsNoI.length; j++) {
                double slope = points[i].slopeTo(ptsNoI[j]);
                // System.out.println(points[j]);
                // System.out.printf("%f %d %d %d %d %f", currSlope, i, j, start, end,
                //                   points[i].slopeTo(points[j]));
                if (j == ptsNoI.length - 1 && currSlope == slope) end++;
                if (slope != currSlope || j == ptsNoI.length - 1) {
                    if (end - start + 1 >= 3) {
                        Point min, max;
                        if (points[i].compareTo(ptsNoI[start]) < 0) min = points[i];
                        else min = ptsNoI[start];

                        if (points[i].compareTo(ptsNoI[end]) > 0) max = points[i];
                        else max = ptsNoI[end];

                        // System.out.printf("%s %s %s\n", points[i], min, max);
                        if (points[i] == min)
                            segments[sz++] = new LineSegment(min, max);
                    }
                    start = j;
                    end = j;
                    currSlope = points[i].slopeTo(points[j]);
                }
                else end++;
            }
        }
        LineSegment[] resSegments = new LineSegment[sz];
        for (int i = 0; i < sz; i++) resSegments[i] = segments[i];
        segments = resSegments;
        // for (Point pt : points) System.out.println(pt);
    }    // finds all line segments containing 4 or more points

    public int numberOfSegments() {
        return sz;
    }       // the number of line segments

    public LineSegment[] segments() {
        LineSegment[] ret = new LineSegment[sz];
        for (int i = 0; i < sz; i++) ret[i] = segments[i];
        return ret;
    }               // the line segments

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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
