package percolation;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class PercolationStats {
    private Percolation percolation;
    private int trials;
    private double[] samples;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }
        percolation = new Percolation(n);
        trials = trials;
        samples = new double[trials];
        while (!percolation.percolates()) {
            percolation.open(StdRandom.uniformInt(1, n + 1),
                    StdRandom.uniformInt(1, n + 1));
        }
        for (double sample : samples) {
            sample = (double) percolation.numberOfOpenSites() / (n * n);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(samples);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(samples);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - 1.96 * stddev() / Math.sqrt(trials);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + 1.96 * stddev() / Math.sqrt(trials);
    }

   // test client (see below)
   public static void main(String[] args) {
        PercolationStats percolationStats = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        System.out.println("mean                    = "
                + percolationStats.mean());
        System.out.println("stddev                  = "
                + percolationStats.stddev());
        System.out.println("95% confidence interval = ["
               + percolationStats.confidenceLo() + ", "
               + percolationStats.confidenceHi() + "]");
   }
}