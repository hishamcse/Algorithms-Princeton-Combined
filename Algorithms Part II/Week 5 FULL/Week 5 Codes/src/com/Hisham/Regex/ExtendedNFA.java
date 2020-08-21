package com.Hisham.Regex;

import edu.princeton.cs.algs4.*;

public class ExtendedNFA {

    // doesnt allow:
    // Metacharacters in the text
    // Character classes.

    private final int M;
    private final Digraph g;
    private final char[] re;

    public ExtendedNFA(String regex) {
        M = regex.length();
        re = regex.toCharArray();
        g = new Digraph(buildEpsilonTransitionGraph());
    }

    private Digraph buildEpsilonTransitionGraph() {

        Digraph g = new Digraph(M + 1);
        Stack<Integer> ops = new Stack<>();

        for (int i = 0; i < M; i++) {

            int lp = i;

            if (re[i] == '(' || re[i] == '|') {
                ops.push(i);
            } else if (re[i] == ')') {

                int or = ops.pop();

                while (re[or] == '|') {
                    g.addEdge(lp, or + 1);
                    g.addEdge(or, i);
                    or = ops.pop();
                }
                if (re[or] == '(') {
                    lp = or;
                }
            }

            if (i < M - 1 && re[i + 1] == '*') {
                g.addEdge(lp, i + 1);
                g.addEdge(i + 1, lp);
            } else if (i < M - 1 && re[i + 1] == '+') {
                g.addEdge(i + 1, lp);
            }

            if (re[i] == '(' || re[i] == '*' || re[i] == ')' || re[i] == '+') {
                g.addEdge(i, i + 1);
            }
        }
        return g;
    }

    public boolean recognizes(String text) {
        DirectedDFS dfs = new DirectedDFS(g, 0);
        Bag<Integer> pc = new Bag<>();
        for (int v = 0; v < g.V(); v++) {
            if (dfs.marked(v)) {
                pc.add(v);
            }
        }

        for (int i = 0; i < text.length(); i++) {
            Bag<Integer> match = new Bag<>();
            for (int v : pc) {
                if (v == M) {
                    continue;
                }
                if (re[v] == text.charAt(i) || re[v] == '.') {
                    match.add(v + 1);
                }
            }
            dfs = new DirectedDFS(g, match);
            pc = new Bag<>();
            for (int v = 0; v < g.V(); v++) {
                if (dfs.marked(v)) {
                    pc.add(v);
                }
            }
        }

        for (int v : pc) {
            if (v == M) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        String regexp = "(" + StdIn.readLine() + ")";
        String txt = StdIn.readLine();
        ExtendedNFA nfa = new ExtendedNFA(regexp);
        StdOut.println(nfa.recognizes(txt));
    }

}
