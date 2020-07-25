import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final int all;
    private final boolean[][] opened;
    private int totalopened;
    private final WeightedQuickUnionUF uf;
    private final WeightedQuickUnionUF extrauf;

    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("illegal");
        }
        totalopened = 0;
        this.all = n;
        uf = new WeightedQuickUnionUF(n * n + 2);
        extrauf = new WeightedQuickUnionUF(n * n + 1);
        opened = new boolean[n][n];
    }

    private void validate(int row, int col) {
        if (row < 1 || row > all || col < 1 || col > all) {
            throw new IllegalArgumentException("illegal");
        }
    }

    private int getID(int row, int col) {
        return (row - 1) * all + (col - 1);
    }

    public void open(int row, int col) {
        if (isOpen(row, col)) {
            return;
        }
        opened[row - 1][col - 1] = true;
        totalopened++;
        if (row == 1) {
            uf.union(all * all, getID(row, col));
            extrauf.union(all * all, getID(row, col));
        } else if (isOpen(row - 1, col)) {
            uf.union(getID(row, col), getID(row - 1, col));
            extrauf.union(getID(row, col), getID(row - 1, col));
        }
        if (row == all) {
            uf.union(all * all + 1, getID(row, col));
        } else if (isOpen(row + 1, col)) {
            uf.union(getID(row, col), getID(row + 1, col));
            extrauf.union(getID(row, col), getID(row + 1, col));
        }
        if (col > 1 && isOpen(row, col - 1)) {
            uf.union(getID(row, col), getID(row, col - 1));
            extrauf.union(getID(row, col), getID(row, col - 1));
        }
        if (col < all && isOpen(row, col + 1)) {
            uf.union(getID(row, col), getID(row, col + 1));
            extrauf.union(getID(row, col), getID(row, col + 1));
        }
    }

    public boolean isOpen(int row, int col) {
        validate(row, col);
        return opened[row - 1][col - 1];
    }

    public boolean isFull(int row, int col) {
        validate(row, col);
        return (extrauf.find(getID(row, col)) == extrauf.find(all * all));
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return totalopened;
    }

    // does the system percolate?
    public boolean percolates() {
        return uf.find(all * all) == uf.find(all * all + 1);
    }
}