package percolation;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private int trials;
    private double[] samples;
    private double constant = 1.96;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0)
            throw new IllegalArgumentException();
        this.trials = trials;
        samples = new double[trials];
        for (int i = 0; i < trials; i++) {
            Percolation percolation = new Percolation(n);
            while (!percolation.percolates())
                percolation.open(StdRandom.uniformInt(1, n + 1),
                        StdRandom.uniformInt(1, n + 1));
            samples[i] = (double) percolation.numberOfOpenSites() / (n * n);
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
        return mean() - constant * stddev() / Math.sqrt(trials);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + constant * stddev() / Math.sqrt(trials);
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