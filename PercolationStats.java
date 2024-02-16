import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */
public class PercolationStats {
    private double[] results;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        results = new double[trials];
        for (int i = 0; i < trials; i++) {
            Percolation percolation = new Percolation(n);
            while (!percolation.percolates()) {
                percolation.open(1 + StdRandom.uniformInt(n), 1 + StdRandom.uniformInt(n));
            }
            results[i] = percolation.numberOfOpenSites() / Math.pow(n, 2);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        // double res = 0;
        // int n = results.length;
        // for (int i = 0; i < n; i++) {
        //     res += results[i] / n;
        // }
        return StdStats.mean(results);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        // double sPow2 = 0;
        // double mean = mean();
        // int n = results.length;
        // for (int i = 0; i < n; i++) {
        //     sPow2 += Math.pow((results[i] - mean), 2) / (n - 1);
        // }
        return StdStats.stddev(results);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - (1.96 * stddev()) / Math.sqrt(results.length);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + (1.96 * stddev()) / Math.sqrt(results.length);
    }

    // test client (see below)
    public static void main(String[] args) {
        PercolationStats ps = new PercolationStats(Integer.parseInt(args[0]),
                                                   Integer.parseInt(args[1]));
        System.out.printf("mean = %f\nstddev = %f\n95 confidence interval = [%f, %f]\n",
                          ps.mean(), ps.stddev(), ps.confidenceLo(), ps.confidenceHi());
    }

}
