package percolation;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[] status;
    private int length;
    private WeightedQuickUnionUF uf;


    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        status = new boolean[n * n];
        length = n;
        uf = new WeightedQuickUnionUF(n * n);
        for (int i = 0; i < n * n; i++)
            status[i] = false;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row < 0 || row > length || col < 0 || col > length) {
            throw new IllegalArgumentException();
        }
        if (!isOpen(row, col)) {
            int position = (row - 1) * length + col - 1;
            status[position] = true;
            if (isOpen(row, col - 1))
                uf.union(position, position - 1);
            if (isOpen(row, col + 1))
                uf.union(position, position + 1);
            if (isOpen(row - 1, col))
                uf.union(position, position - length);
            if (isOpen(row + 1, col))
                uf.union(position, position + length);
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row < 0 || row > length || col < 0 || col > length) {
            throw new IllegalArgumentException();
        }
        return status[(row - 1) * length + col - 1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row < 0 || row > length || col < 0 || col > length) {
            throw new IllegalArgumentException();
        }
        boolean mark = false;
        for (int i = 0; i < length; i++) {
            if (isOpen(1, i+1)) {
                int position = (row - 1) * length + col - 1;
                if (uf.connected(position, i))
                    mark = true;
            }
        }
        return mark;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        int count = 0;
        for (int i = 1; i <= length; i++) {
            for (int j = 1; j <= length; j++) {
                if (isOpen(i, j))
                    count++;
            }
        }
        return count;
    }

    // does the system percolate?
    public boolean percolates() {
        boolean mark = false;
        for (int i = 1; i <= length; i++) {
            if (isFull(length, i)) {
                mark = true;
                break;
            }
        }
        return mark;
    }

    // test client (optional)
    public static void main(String[] args) {
    }
}
