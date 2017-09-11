package algorithms_class._01_union_find;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import java.lang.Math;

public class PercolationStats {

    private int[] results;
    private int size, trials, totalSize;

    // perform trials independent experiments on an n-by-n grid
    public PercolationStats(int n, int trials) {

        System.out.println("Start percolation problem size " + String.valueOf(n) + ", number of trials: " + String.valueOf(trials));
        this.results = new int[trials];
        this.totalSize = n*n;
        this.size = n;
        this.trials = trials;

        // start the trial
        for (int i=0; i<trials; i++) {
            this.results[i] = 0;
            int time = 0;


            // generate percolation for this run
            Percolation trial = new Percolation(n);
            int[] unchosenCells = new int[totalSize];
            for (int j=0; j<totalSize; j++) {
                unchosenCells[j] = j;
            }

            while (!trial.percolates()) {
                time ++;

                // choose a random cell in the percolation problem
                int randomCell = -1;
                while(randomCell < 0) {
                    int random = StdRandom.uniform(totalSize);
                    if (unchosenCells[random] >= 0) {
                        randomCell = random;
                    }
                }
                unchosenCells[randomCell] = -1;

                // open a cell in the percolation problem
                int row = (int)randomCell/n;
                int col = randomCell%n;
                trial.open(row, col);
            }
            this.results[i] = time;
        }
    }
    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(this.results)/this.totalSize;
    }
    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(this.results)/this.totalSize;
    }
    // low  endpoint of 95% confidence interval
    public double confidenceLo() {
        return this.mean() - (1.96*Math.sqrt(this.stddev()))/Math.sqrt(this.trials);
    }
    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return this.mean() + (1.96*Math.sqrt(this.stddev()))/Math.sqrt(this.trials);
    }

    // test client (described below)
    public static void main(String[] args) {

        int size = 0, trials = 0;
        try {
            size = Integer.parseInt(args[0]);
            trials = Integer.parseInt(args[1]);
        }
        catch(Exception e) {
            throw new IllegalArgumentException();
        }
        if (size <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }

        PercolationStats stat = new PercolationStats(size, trials);
        System.out.println("Mean                        = " + String.valueOf(stat.mean()));
        System.out.println("stddev                      = " + String.valueOf(stat.stddev()));
        System.out.println("95% confidence interval     = [" + stat.confidenceLo() + ", " + stat.confidenceHi() + "]");
    }
}
