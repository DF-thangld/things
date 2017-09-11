package algorithms_class._01_union_find;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private WeightedQuickUnionUF uf;
    private int size, totalSize, openedSite;
    private boolean[] isOpened;

    public Percolation(int n) { // create n-by-n grid, with all sites blocked

        // initialize a new weighted quick union graph
        this.uf = new WeightedQuickUnionUF((n * n) + 2);
        this.size = n;
        this.totalSize = n*n;
        this.openedSite = 0;
        this.isOpened = new boolean[this.totalSize];
        for (int i=0; i<this.totalSize; i++) {
            this.isOpened[i] = false;
        }

        // create 2 virtual points to connect with top and bottom rows
        int first_node = n*n;
        int last_node = (n*n) + 1;
        for (int i=0; i < n; i++) {
            uf.union(first_node, i);
            uf.union(last_node, (n*(n-1)) + i);
        }
    }

    // open site (row, col) if it is not open already
    public void open(int row, int col) {

        if (this.isOpen(row, col)) {
            return;
        }

        int position = row * this.size + col;
        if (row > 0 && this.isOpen(row - 1, col)) {
            this.uf.union(position, (row - 1)*this.size + col);
        }
        if (row < this.size - 1 && this.isOpen(row + 1, col)) {
            this.uf.union(position, (row+1)*this.size + col);
        }
        if (col > 0 && this.isOpen(row, col - 1)) {
            this.uf.union(position, row*this.size + col - 1);
        }
        if (col < this.size - 1 && this.isOpen(row, col + 1)) {
            this.uf.union(position, row*this.size + col + 1);
        }

        this.openedSite++;
        this.isOpened[row*this.size + col] = true;
    }

    // is site (row, col) open?
    public boolean isOpen(int row, int col) {
        return this.isOpened[row*this.size + col];
    }

    // is site (row, col) full?
    public boolean isFull(int row, int col) {
        return this.uf.connected(row * this.size + col, this.totalSize);
    }

    // number of open sites
    public int numberOfOpenSites() {
        return this.openedSite;
    }

    // does the system percolate?
    public boolean percolates() {
        return this.uf.connected(this.totalSize, this.totalSize + 1);
    }

    // test client (optional)
    public static void main(String[] args) {

    }
}

