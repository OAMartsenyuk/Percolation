package com.marts;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
/**
 * Created by marts on 16.09.2016.
 */
public class PercolationStats {
    private Percolation percolation;
    private int trialsAmount;
    private double[] rezults;

    public PercolationStats(int n, int t) {
        if (n <= 0 || t <= 0) {
            throw new java.lang.IllegalArgumentException("parameters must be > 0");
        }

        trialsAmount = t;
        rezults = new double[trialsAmount];
        for (int currentTrial = 0; currentTrial < trialsAmount; currentTrial++) {
            percolation = new Percolation(n);
            int openedSites = 0;
            while (!percolation.percolates()) {
                int i = StdRandom.uniform(1, n + 1);
                int j = StdRandom.uniform(1, n + 1);
                if (!percolation.isOpen(i, j)) {
                    percolation.open(i, j);
                    openedSites++;
                }
            }
            double fraction = (double) openedSites / (n * n);
            rezults[currentTrial] = fraction;
        }
    }

    public double mean() {
        return StdStats.mean(rezults);
    }

    public double stddev() {
        return StdStats.stddev(rezults);
    }

    public double confidenceLo() {
        return mean() - ((1.96 * stddev()) / Math.sqrt(trialsAmount));
    }

    public double confidenceHi() {
        return mean() + ((1.96 * stddev()) / Math.sqrt(trialsAmount));
    }

    public static void main(String[] args) {
        int n = 1000;
        int t = 1000;
        long start = System.currentTimeMillis();
        PercolationStats percolationStats = new PercolationStats(n,t);

        String confidence = percolationStats.confidenceLo() + ", " + percolationStats.confidenceHi();
        System.out.println("mean                    = " + percolationStats.mean());
        System.out.println("stddev                  = " + percolationStats.stddev());
        System.out.println("95% confidence interval = " + confidence);
        long end = System.currentTimeMillis();
        System.out.println("Duration is:" + (end-start));
    }
}
