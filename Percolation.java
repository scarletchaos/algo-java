/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    WeightedQuickUnionUF unionFind;
    boolean[] opened;
    int N;
    int openSites;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException("N is too low");
        N = n;
        openSites = 0;
        unionFind = new WeightedQuickUnionUF(n * n + 2);
        opened = new boolean[N * N + 2];
        for (int i = 0; i < N * N + 2; i++) opened[i] = false;
        opened[0] = true;
        opened[N * N + 1] = true;
    }

    public int rowColToIndex(int row, int col) {
        return row * N + col + 1;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row < 0 || row >= N)
            throw new IllegalArgumentException("Row should be between 0 and N-1");
        if (col < 0 || col >= N)
            throw new IllegalArgumentException("Row should be between 0 and N-1");
        if (isOpen(row, col)) return;
        opened[rowColToIndex(row, col)] = true;
        openSites += 1;
        if (row == 0) unionFind.union(0, rowColToIndex(row, col));
        if (row == N - 1) unionFind.union(rowColToIndex(row, col), N * N + 1);
        if (row > 0 && isOpen(row - 1, col)) {
            unionFind.union(rowColToIndex(row, col),
                            rowColToIndex(row - 1, col));
        }
        if (row < N - 1 && isOpen(row + 1, col)) {
            unionFind.union(rowColToIndex(row, col),
                            rowColToIndex(row + 1, col));
        }
        if (col > 0 && isOpen(row, col - 1)) {
            unionFind.union(rowColToIndex(row, col),
                            rowColToIndex(row, col - 1));
        }
        if (col < N - 1 && isOpen(row, col + 1)) {
            unionFind.union(rowColToIndex(row, col),
                            rowColToIndex(row, col + 1));
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row < 0 || row >= N)
            throw new IllegalArgumentException("Row should be between 0 and N-1");
        if (col < 0 || col >= N)
            throw new IllegalArgumentException("Row should be between 0 and N-1");
        return opened[rowColToIndex(row, col)];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row < 0 || row >= N)
            throw new IllegalArgumentException("Row should be between 0 and N-1");
        if (col < 0 || col >= N)
            throw new IllegalArgumentException("Row should be between 0 and N-1");
        return unionFind.connected(0, rowColToIndex(row, col));
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return unionFind.connected(0, N * N + 1);
    }

    // test client (optional)
    public static void main(String[] args) {
        // Percolation per = new Percolation(2);
        // System.out.println(per.percolates());
        // per.open(1, 1);
        // System.out.println(per.percolates());
        // System.out.println(Arrays.toString(per.opened));
        // per.open(0, 0);
        // System.out.println(per.percolates());
        // System.out.println(Arrays.toString(per.opened));
        // per.open(0, 1);
        // System.out.println(per.percolates());
        // System.out.println(Arrays.toString(per.opened));
    }
}
