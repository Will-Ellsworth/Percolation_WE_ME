/*
    Will Ellsworth and Matt Erlichson

    Algorithms pd2
    2/2020

    Stats for Percolation

 */

public class PercolationStats {

    private double mean;
    private double stdDev;
    private double lowCon;
    private double highCon;
    private Percolation percolator;
    private double [] percThresh;


    // perform T independent experiments on an N-by-N grid
    public PercolationStats(int N, int T)
    {
        percThresh = new double[T];
        for(int i =0; i<T;i++)
        {
            percolator = new Percolation(N);
            int row;
            int col;
            while(!percolator.percolates())
            {
                row = (int)(Math.random()*(N));
                col = (int)(Math.random()*(N));
                percolator.open(row,col);
                //PercolationVisualizer.draw(percolator,Integer.parseInt(args[0]));
            }
            percThresh[i] = (double)percolator.numberOfOpenSites()/(N*N);
        }
    }

    // sample mean of percolation threshold
    public double mean()
    {
        mean = 0;
        for(int i = 0; i < percThresh.length;i++)
        {
            mean += percThresh[i];
        }
        mean/=percThresh.length;
        return mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev()
    {
        stdDev = 0;
        double avg = mean();
        for (int i = 0; i < percThresh.length; i++)
        {
            stdDev += (percThresh[i] - avg)*(percThresh[i] - avg);
        }
        stdDev /= (percThresh.length - 1);
        stdDev = Math.sqrt(stdDev);
        return stdDev;
    }

    // low  endpoint of 95% confidence interval
    public double confidenceLow()
    {
        lowCon = 0;
        double avg = mean();
        double SD = stddev();
        lowCon = mean - (1.96*SD)/Math.sqrt(percThresh.length);
        return lowCon;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHigh()
    {
        highCon = 0;
        double avg = mean();
        double SD = stddev();
        highCon = mean + (1.96*SD)/Math.sqrt(percThresh.length);
        return highCon;
    }

    public static void main(String[] args)
    {
        PercolationStats percolator = new PercolationStats(Integer.parseInt(args[0]),Integer.parseInt(args[1]));
        System.out.println("Example values after creating PercolationStats(" + args[0] + "," + args[1] + ")");
        System.out.println("mean " + percolator.mean());
        System.out.println("stddev " + percolator.stddev());
        System.out.println("ConfidenceLow " + percolator.confidenceLow());
        System.out.println("ConfienceHigh " + percolator.confidenceHigh());



    }
}
