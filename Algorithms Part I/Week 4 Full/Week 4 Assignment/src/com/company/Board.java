package com.company;

import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdIn;

import java.util.LinkedList;
import java.util.List;

public class Board {

    private final int[][] all;
    private final int n;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        n = tiles.length;
        all = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                all[i][j] = tiles[i][j];
            }
        }
    }

    // string representation of this board
    public String toString() {

        StringBuilder s = new StringBuilder();
        s.append(n).append("\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(String.format("%2d ", all[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    // board dimension n
    public int dimension() {
        return n;
    }

    // number of tiles out of place
    public int hamming() {
        int k = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (all[i][j] != 0 && all[i][j] != (n * i + j + 1)) {
                    k++;
                }
            }
        }
        return k;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int value;
        int s = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (all[i][j] != 0 && all[i][j] != (n * i + j + 1)) {
                    value = all[i][j];
                    value -= 1;
                    int x = (value / n);
                    int y = (value - x * n);   //or,y=value%n;
                    s += Math.abs(i - x) + Math.abs(j - y);
                }
            }
        }
        return s;
    }

    // is this board the goal board?
    public boolean isGoal() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (all[i][j] != 0 && all[i][j] != (n * i + j + 1)) {
                    return false;
                }
            }
        }
        return true;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (this == y) {
            return true;
        }
        if ((y == null) || (y.getClass() != this.getClass())) {
            return false;
        }

        Board board = (Board) y;
        if (this.dimension() != ((Board) y).dimension()) {
            return false;
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (all[i][j] != board.all[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {

        List<Board> neighbours = new LinkedList<>();
        int[][] neighboursarray = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                neighboursarray[i][j] = all[i][j];
            }
        }

        int c = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (all[i][j] == 0 && i - 1 >= 0) {
                    neighboursarray[i][j] = neighboursarray[i - 1][j];
                    neighboursarray[i - 1][j] = 0;
                    neighbours.add(new Board(neighboursarray));
                    neighboursarray[i][j] = 0;
                    neighboursarray[i - 1][j] = all[i - 1][j];
                    c = 1;
                }
                if (all[i][j] == 0 && i + 1 < n) {
                    neighboursarray[i][j] = neighboursarray[i + 1][j];
                    neighboursarray[i + 1][j] = 0;
                    neighbours.add(new Board(neighboursarray));
                    neighboursarray[i][j] = 0;
                    neighboursarray[i + 1][j] = all[i + 1][j];
                    c = 1;
                }
                if (all[i][j] == 0 && j - 1 >= 0) {
                    neighboursarray[i][j] = neighboursarray[i][j - 1];
                    neighboursarray[i][j - 1] = 0;
                    neighbours.add(new Board(neighboursarray));
                    neighboursarray[i][j] = 0;
                    neighboursarray[i][j - 1] = all[i][j - 1];
                    c = 1;
                }
                if (all[i][j] == 0 && j + 1 < n) {
                    neighboursarray[i][j] = neighboursarray[i][j + 1];
                    neighboursarray[i][j + 1] = 0;
                    neighbours.add(new Board(neighboursarray));
                    neighboursarray[i][j] = 0;
                    neighboursarray[i][j + 1] = all[i][j + 1];
                    c = 1;
                }
                if (c == 1) {
                    break;
                }
            }
            if (c == 1) {
                break;
            }
        }

        return neighbours;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {

        int[][] twinall = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                twinall[i][j] = all[i][j];
            }
        }
        if (all[0][0] != 0 && all[0][1] != 0) {
            twinall[0][0] = all[0][1];
            twinall[0][1] = all[0][0];
        } else {
            twinall[1][0] = all[1][1];
            twinall[1][1] = all[1][0];
        }
        return new Board(twinall);
    }

    //extra method to visualize(n added in the assignment)
    public void draw(double x,double y){
        StdDraw.text(x-.03*n,y,String.valueOf(n));
        for (int i = 0; i < n; i++) {
            y-=.03;
            x-=.03*n;
            for (int j = 0; j < n; j++) {
                StdDraw.text(x,y,String.valueOf(all[i][j]));
                x+=0.03;
            }
        }
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        int n = StdIn.readInt();
        int[][] set = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                set[i][j] = StdIn.readInt();
            }
        }
        Board board = new Board(set);
        System.out.println(board.dimension());
        System.out.println(board.hamming());
        System.out.println(board.isGoal());
        System.out.println(board.manhattan());
        for (Board k : board.neighbors()) {
            System.out.println(k);
        }
        System.out.println(board.twin());
    }

}
