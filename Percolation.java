/* *****************************************************************************
 *  name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private WeightedQuickUnionUF unionFind;
    private boolean[] opened;
    private int n;
    private int openSites;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int num) {
        if (num <= 0) throw new IllegalArgumentException("n is too low");
        n = num;
        openSites = 0;
        unionFind = new WeightedQuickUnionUF(n * n + 2);
        opened = new boolean[n * n + 2];
        for (int i = 0; i < n * n + 2; i++) opened[i] = false;
        opened[0] = true;
        opened[n * n + 1] = true;
    }

    private int rowColToIndex(int row, int col) {
        return (row - 1) * n + col;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        checkArgs(row, col);
        if (isOpen(row, col)) return;
        opened[rowColToIndex(row, col)] = true;
        openSites += 1;
        if (row == 1) unionFind.union(0, rowColToIndex(row, col));
        if (row == n) unionFind.union(rowColToIndex(row, col), n * n + 1);
        if (row > 1 && isOpen(row - 1, col)) {
            unionFind.union(rowColToIndex(row, col),
                            rowColToIndex(row - 1, col));
        }
        if (row < n && isOpen(row + 1, col)) {
            unionFind.union(rowColToIndex(row, col),
                            rowColToIndex(row + 1, col));
        }
        if (col > 1 && isOpen(row, col - 1)) {
            unionFind.union(rowColToIndex(row, col),
                            rowColToIndex(row, col - 1));
        }
        if (col < n && isOpen(row, col + 1)) {
            unionFind.union(rowColToIndex(row, col),
                            rowColToIndex(row, col + 1));
        }
    }

    private void checkArgs(int r, int c) {
        if (r <= 0 || r > n)
            throw new IllegalArgumentException(
                    "Row should be between 1 and n, got " + Integer.toString(r));
        if (c <= 0 || c > n)
            throw new IllegalArgumentException(
                    "Col should be between 1 and n, got " + Integer.toString(c));
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        checkArgs(row, col);
        return opened[rowColToIndex(row, col)];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        checkArgs(row, col);
        return unionFind.find(0) == unionFind.find(rowColToIndex(row, col));
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return unionFind.find(0) == unionFind.find(n * n + 1);
    }


}
