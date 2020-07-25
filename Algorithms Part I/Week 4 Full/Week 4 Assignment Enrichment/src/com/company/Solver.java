package com.company;

import edu.princeton.cs.algs4.*;

import java.awt.*;
import java.util.Deque;
import java.util.LinkedList;

public class Solver {

    private final Searchnode node;

    private static class Searchnode implements Comparable<Searchnode> {
        public final Board board;
        public final Searchnode previous;
        public final int moves;
        public final int priority;

        public Searchnode(Board board, Searchnode previous) {
            this.board = board;
            this.previous = previous;
            if (previous != null) {
                this.moves = previous.moves + 1;
            } else {
                this.moves = 0;
            }
            this.priority = this.board.manhattan() + this.moves;
        }

        @Override
        public int compareTo(Searchnode that) {
            return Integer.compare(this.priority, that.priority);
        }
    }

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException("board is null");
        }
        MinPQ<Searchnode> pqoriginal = new MinPQ<>();
        MinPQ<Searchnode> pqtwin = new MinPQ<>();

        pqoriginal.insert(new Searchnode(initial, null));
        pqtwin.insert(new Searchnode(initial.twin(), null));

        while (true) {
            Searchnode originalnode = pqoriginal.delMin();
            Searchnode twinnode = pqtwin.delMin();

            if (originalnode.board.isGoal()) {
                this.node = originalnode;
                return;
            } else if (twinnode.board.isGoal()) {
                this.node=null;
                return;
            }

            for (Board neighbour : originalnode.board.neighbors()) {
                if (originalnode.previous != null && originalnode.previous.board.equals(neighbour)) {
                    continue;
                }
                pqoriginal.insert(new Searchnode(neighbour, originalnode));
            }

            for (Board neighbour : twinnode.board.neighbors()) {
                if (twinnode.previous != null && twinnode.previous.board.equals(neighbour)) {
                    continue;
                }
                pqtwin.insert(new Searchnode(neighbour, twinnode));
            }
        }
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return node != null;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (!isSolvable()) {
            return -1;
        }
        return node.moves;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (!isSolvable()) {
            return null;
        }
        Deque<Board> solution = new LinkedList<>();
        Searchnode solutionnode=node;   //to make node immutable,we use an extra searchnode here
        while (solutionnode != null) {
            solution.addFirst(solutionnode.board);
            solutionnode = solutionnode.previous;
        }
        return solution;
    }

    // test client (see below)
    public static void main(String[] args) {

        // create initial board from file
        In in = new In(StdIn.readString());
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        StdDraw.setCanvasSize(600,600);
        StdDraw.clear();

        double x=0;
        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution()) {
                StdOut.println(board);
                StdDraw.setPenColor(Color.red);
                board.draw(.5,.9-5*x);
                StdDraw.show();
                StdDraw.pause(20);
                x+=.03;
            }
        }
    }

}