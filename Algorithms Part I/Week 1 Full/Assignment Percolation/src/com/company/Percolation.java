package com.company;

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
        totalopened=0;
        this.all = n;
        uf = new WeightedQuickUnionUF(n*n + 2);
        extrauf=new WeightedQuickUnionUF(n*n+1);
        opened = new boolean[n][n];
    }

    private void validate(int row,int col){
        if(row<1 || row>all || col<1 || col>all) {
            throw new IllegalArgumentException("illegal");
        }
    }

    private int getID(int row,int col){
        return (row-1)*all+(col-1);
    }

    public void open(int row, int col) {
        if(isOpen(row, col)){return;}
        opened[row-1][col-1] = true;
        totalopened++;
        if(row==1){
            uf.union(all*all,getID(row,col));
            extrauf.union(all*all,getID(row, col));
        }else if(isOpen(row - 1, col)) {
            uf.union(getID(row, col), getID(row - 1, col));
            extrauf.union(getID(row, col), getID(row - 1, col));
        }
        if(row==all){
            uf.union(all*all+1,getID(row,col));
        }else if(isOpen(row + 1, col)){
            uf.union(getID(row, col), getID(row + 1, col));
            uf.union(getID(row, col), getID(row + 1, col));
        }
        if (col>1 && isOpen(row, col - 1)) {
            uf.union(getID(row, col), getID(row, col - 1));
            extrauf.union(getID(row, col), getID(row, col - 1));
        }
        if (col < all && isOpen(row, col + 1)) {
            uf.union(getID(row, col), getID(row, col + 1));
            extrauf.union(getID(row, col), getID(row, col + 1));
        }
    }

    public boolean isOpen(int row, int col) {
        validate(row,col);
        return opened[row-1][col-1];
    }

    public boolean isFull(int row, int col) {
        validate(row, col);
        return (extrauf.find(getID(row,col))==extrauf.find(all*all));
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return totalopened;
    }

    // does the system percolate?
    public boolean percolates(){
        return uf.find(all*all)==uf.find(all*all+1);
    }
}
//import edu.princeton.cs.algs4.WeightedQuickUnionUF;
//
//public class Percolation {
//    private final int topRoot;
//    private final int bottomRoot;
//    private int openSites = 0;
//
//    private final int all;
//    private final WeightedQuickUnionUF unionFind;
//    private final WeightedQuickUnionUF fullSites;
//    private final boolean[][] isopened;
//
//    public Percolation(int n) {
//        if (n < 1) {
//            throw new IllegalArgumentException();
//        }
//
//        all = n;
//        unionFind = new WeightedQuickUnionUF(n * n + 2);
//        fullSites = new WeightedQuickUnionUF(n * n + 1);
//        topRoot = n * n;
//        bottomRoot = n * n + 1;
//        isopened = new boolean[n][n];
//    }
//
//    private int getID(int row, int col) {
//        return all * (row - 1) + (col - 1);
//    }
//
//    private void unionIfOpen(int row, int col, int indexToUnion) {
//        if (isOpen(row, col)) {
//            int index = getID(row, col);
//            unionFind.union(index, indexToUnion);
//            fullSites.union(index, indexToUnion);
//        }
//    }
//
//    public void open(int row, int col) {
//        check(row, col);
//
//        if (isOpen(row, col)) return;
//        isopened[row - 1][col - 1] = true;
//        openSites++;
//
//        int index = getID(row, col);
//
//        // Top
//        if (row == 1) {
//            unionFind.union(index, topRoot);
//            fullSites.union(index, topRoot);
//        } else {
//            int topRow = row - 1;
//            unionIfOpen(topRow, col, index);
//        }
//
//        // Right
//        if (col < all) {
//            int rightCol = col + 1;
//            unionIfOpen(row, rightCol, index);
//        }
//
//        // Bottom
//        if (row == all) {
//            unionFind.union(index, bottomRoot);
//        } else {
//            int bottomRow = row + 1;
//            unionIfOpen(bottomRow, col, index);
//        }
//
//        // Left
//        if (col > 1) {
//            int leftCol = col - 1;
//            unionIfOpen(row, leftCol, index);
//        }
//
//    }
//
//    public boolean isOpen(int row, int col) {
//        check(row, col);
//
//        return isopened[row - 1][col - 1];
//    }
//
//    public boolean isFull(int row, int col) {
//        check(row, col);
//
//        int index = getID(row, col);
//        return (fullSites.find(index)==fullSites.find(topRoot));
//    }
//
//    public int numberOfOpenSites() {
//        return openSites;
//    }
//
//    public boolean percolates() {
//        return (unionFind.find(topRoot)==unionFind.find(bottomRoot));
//    }
//
//    private void check(int row, int col) {
//        if (row < 1 || row > all || col < 1 || col > all) {
//            throw new IllegalArgumentException();
//        }
//    }
//}

