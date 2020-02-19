/*
    Will Ellsworth and Matt Erlichson

    Algorithms pd2
    2/2020

    Percolation API

 */



public class Percolation {
    private boolean [][] grid;
    private edu.princeton.cs.algs4.WeightedQuickUnionUF unionFinder;
    private int numOpen;
    // create N-by-N grid, with all sites initially blocked
    public Percolation(int N)
    {
        grid = new boolean[N][N];
        unionFinder = new edu.princeton.cs.algs4.WeightedQuickUnionUF(N*N + 2);
        numOpen = 0;
    }


    // open the site (row, col) if it is not open already
    public void open(int row, int col)
    {
        if(!grid[row][col])
        {
            grid[row][col] = true;
            numOpen++;
            if (row - 1 >= 0 && grid[row-1][col])
            {
                unionFinder.union(grid.length*row + (col+1), grid.length*(row-1) + (col+1));
            }
            else if(row - 1 < 0)
            {
                unionFinder.union(0,grid.length*row + (col+1));
            }
            if (row + 1 < grid.length && grid[row+1][col])
            {
                unionFinder.union(grid.length*row + (col+1), grid.length*(row+1) + (col+1));
            }
            else if(row + 1 >= grid.length)
            {
                unionFinder.union(grid.length*grid.length+1, grid.length*row + (col+1));
            }
            if (col-1 >= 0 && grid[row][col-1])
            {
                unionFinder.union(grid.length*row + (col+1), grid.length*(row) + (col));
            }
            if (col+1 < grid.length && grid[row][col+1])
            {
                unionFinder.union(grid.length*row + (col+1), grid.length*(row) + (col+2));
            }
        }


    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col)
    {
        return grid[row][col];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col)
    {
        return unionFinder.find(0) == unionFinder.find(grid.length*row + (col+1));
    }

    // number of open sites
    public int numberOfOpenSites()
    {
       return numOpen;
    }

    // does the system percolate?
    public boolean percolates()
    {
        return unionFinder.find(0) == unionFinder.find((grid.length*grid.length) + 1);
    }

    //test the stuff
    public static void main(String[] args)
    {
        Percolation percolater = new Percolation(Integer.parseInt(args[0]));


        int row;
        int col;
        while(!percolater.percolates())
        {
            //System.out.println(percolater.numberOfOpenSites());
            row = (int)(Math.random()*(Integer.parseInt(args[0])));
            col = (int)(Math.random()*(Integer.parseInt(args[0])));
            //System.out.println(row + " " +col);
            percolater.open(row,col);
            PercolationVisualizer.draw(percolater,Integer.parseInt(args[0]));
        }
        System.out.println(percolater.numberOfOpenSites());

    }
}
