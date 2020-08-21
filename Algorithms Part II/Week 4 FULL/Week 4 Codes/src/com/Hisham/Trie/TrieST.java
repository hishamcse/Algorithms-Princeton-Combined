package com.Hisham.Trie;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class TrieST<Value> {   // R-Way trie

    private static final int R = 256;   // 256-way trie
    private Node root;
    private int n;

    private static class Node {
        private Object value;
        private final Node[] next = new Node[R];
    }

    public void put(String key, Value val) {
        root = put(root, key, val, 0);
    }

    private Node put(Node x, String key, Value val, int d) {
        if (x == null) {
            x = new Node();
        }
        if (d == key.length()) {
            if (x.value == null) {
                n++;
            }
            x.value = val;
            return x;
        }
        char c = key.charAt(d);
        x.next[c] = put(x.next[c], key, val, d + 1);
        return x;
    }

    public boolean contains(String key) {
        return get(key) != null;
    }

    public Value get(String key) {
        Node x = get(root, key, 0);
        if (x == null) {
            return null;
        }
        return (Value) x.value;
    }

    private Node get(Node x, String key, int d) {
        if (x == null) {
            return null;
        }
        if (d == key.length()) {
            return x;
        }
        char c = key.charAt(d);
        return get(x.next[c], key, d + 1);
    }

    public int size() {
        return n;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public void delete(String key) {
        root = delete(root, key, 0);
    }

    private Node delete(Node x, String key, int d) {
        if (x == null) {
            return null;
        }
        if (d == key.length()) {
            if (x.value != null) {
                n--;
            }
            x.value = null;
        } else {
            char c = key.charAt(d);
            x.next[c] = delete(x.next[c], key, d + 1);
        }
        if (x.value != null) {
            return x;
        }
        for (int i = 0; i < R; i++) {
            if (x.next[i] != null) {
                return x;
            }
        }
        return null;
    }

    public Iterable<String> keys() {
        return keysWithPrefix("");
    }

    public Iterable<String> keysWithPrefix(String prefix) {
        Queue<String> queue = new Queue<>();
        Node x = get(root, prefix, 0);
        collect(x, new StringBuilder(prefix), queue);
        return queue;
    }

    private void collect(Node x, StringBuilder builder, Queue<String> queue) {
        if (x == null) {
            return;
        }
        if (x.value != null) {
            queue.enqueue(builder.toString());
        }
        for (char c = 0; c < R; c++) {
            builder.append(c);
            collect(x.next[c], builder, queue);
            builder.deleteCharAt(builder.length() - 1);
        }
    }

    public Iterable<String> keysThatMatch(String query) {
        Queue<String> queue = new Queue<>();
        collect(root, new StringBuilder(), query, queue);
        return queue;
    }

    private void collect(Node x, StringBuilder prefix, String query, Queue<String> queue) {
        if (x == null) {
            return;
        }
        int d = prefix.length();
        if (d == query.length() && x.value != null) {
            queue.enqueue(prefix.toString());
        }
        if (d == query.length()) {
            return;
        }
        char c = query.charAt(d);
        if (c == '.') {
            for (char ch = 0; ch < R; ch++) {
                prefix.append(ch);
                collect(x.next[ch], prefix, query, queue);
                prefix.deleteCharAt(prefix.length() - 1);
            }
        } else {
            prefix.append(c);
            collect(x.next[c], prefix, query, queue);
            prefix.deleteCharAt(prefix.length() - 1);
        }
    }

    public String longestPrefixOf(String query) {
        int length = longestPrefixOf(root, query, 0, -1);
        if (length == -1) {
            return null;
        }
        return query.substring(0, length);
    }

    private int longestPrefixOf(Node x, String query, int d, int length) {
        if (x == null) {
            return length;
        }
        if (x.value != null) {
            length = d;
        }
        if (d == query.length()) {
            return length;
        }
        char c = query.charAt(d);
        return longestPrefixOf(x.next[c], query, d + 1, length);
    }

    public static void main(String[] args) {

        // build symbol table from standard input
        TrieST<Integer> st = new TrieST<>();
        In in=new In(StdIn.readLine());
        for (int i = 0; !in.isEmpty(); i++) {
            String key = in.readString();
            st.put(key, i);
        }

        // print results
        if (st.size() < 100) {
            StdOut.println("keys(\"\"):");
            for (String key : st.keys()) {
                StdOut.println(key + " " + st.get(key));
            }
            StdOut.println();
        }

        StdOut.println("longestPrefixOf(\"shellsort\"):");
        StdOut.println(st.longestPrefixOf("shellsort"));
        StdOut.println();

        StdOut.println("longestPrefixOf(\"quicksort\"):");
        StdOut.println(st.longestPrefixOf("quicksort"));
        StdOut.println();

        StdOut.println("keysWithPrefix(\"shor\"):");
        for (String s : st.keysWithPrefix("shor"))
            StdOut.println(s);
        StdOut.println();

        StdOut.println("keysThatMatch(\".he.l.\"):");
        for (String s : st.keysThatMatch(".he.l."))
            StdOut.println(s);

        System.out.println();
        System.out.println(st.contains("Hisham"));
        st.delete("sea");
        System.out.println(st.isEmpty());
    }
}
