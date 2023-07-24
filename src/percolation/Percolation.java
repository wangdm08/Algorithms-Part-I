package percolation;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[] status;
    private int length;
    private WeightedQuickUnionUF uf; // WeightedQuickUnionUF for method percolates
    private WeightedQuickUnionUF ufNew; // WeightedQuickUnionUF for method isFull
    private  int count;
    private boolean mark;


    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0)
            throw new IllegalArgumentException();
        status = new boolean[n * n + 2];
        length = n;
        this.count = 0;
        this.mark = false;
        uf = new WeightedQuickUnionUF(n * n + 2);
        ufNew = new WeightedQuickUnionUF(n * n + 1);
        for (int i = 0; i < n * n + 2; i++) {
            status[i] = false;
            if (i > 0 && i <= n) {
                uf.union(i, 0);
                ufNew.union(i, 0);
            }
            if (i > n * n - n && i <= n * n)
                uf.union(i, n * n + 1);
        }
    }

    // validate the row and column indices outside prescribed range
    private void validate(int row, int col) {
        if (row < 1 || row > length || col < 1 || col > length)
            throw new IllegalArgumentException();
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        validate(row, col);
        if (!isOpen(row, col)) {
            int position = (row - 1) * length + col;
            status[position] = true;
            if (position > 0 && position <= length)
                status[0] = true;
            if (position > length * (length - 1) && position <= length * length)
                status[length * length + 1] = true;
            if (col > 1 && isOpen(row, col - 1)) {
                uf.union(position, position - 1);
                ufNew.union(position, position - 1);
            }
            if (col < length && isOpen(row, col + 1)) {
                uf.union(position, position + 1);
                ufNew.union(position, position + 1);
            }
            if (row > 1 && isOpen(row - 1, col)) {
                uf.union(position, position - length);
                ufNew.union(position, position - length);
            }
            if (row < length && isOpen(row + 1, col)) {
                uf.union(position, position + length);
                ufNew.union(position, position + length);
            }
            count += 1;
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        validate(row, col);
        return status[(row - 1) * length + col];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        validate(row, col);
        int position = (row - 1) * length + col;
        return (isOpen(row, col) && status[0]) && ufNew.find(position) == ufNew.find(0);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return count;
    }

    // does the system percolate?
    public boolean percolates() {
        if (!mark)
            if (status[0] && status[length * length + 1])
                if (uf.find(length * length + 1) == uf.find(0))
                    mark = true;
        return mark;
    }

    // test client (optional)
    public static void main(String[] args) {
    }
}