package com.Hisham.Trie;

import edu.princeton.cs.algs4.*;

public class TernaryST<Value> {

    private Node<Value> root;
    private int n;

    private static class Node<Value> {
        private Value value;
        private char c;
        private Node<Value> left, mid, right;
    }

    public void put(String key, Value value) {
        root = put(root, key, value, 0);
    }

    private Node<Value> put(Node<Value> x, String key, Value value, int d) {
        char c = key.charAt(d);
        if (x == null) {
            x = new Node<>();
            x.c = c;
        }
        if (c < x.c) {
            x.left = put(x.left, key, value, d);
        } else if (c > x.c) {
            x.right = put(x.right, key, value, d);
        } else if (d < key.length() - 1) {
            x.mid = put(x.mid, key, value, d + 1);
        } else {
            if (x.value != null) {
                n++;
            }
            x.value = value;
        }
        return x;
    }

    public boolean contains(String key) {
        return get(key) != null;
    }

    public Value get(String key) {
        Node<Value> x = get(root, key, 0);
        if (x == null) {
            return null;
        }
        return x.value;
    }

    private Node<Value> get(Node<Value> x, String key, int d) {
        if (x == null) {
            return null;
        }
        char c = key.charAt(d);
        if (c < x.c) {
            return get(x.left, key, d);
        } else if (c > x.c) {
            return get(x.right, key, d);
        } else if (d < key.length() - 1) {
            return get(x.mid, key, d + 1);
        } else {
            return x;
        }
    }

    public int size() {
        return n;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public String longestPrefixOf(String query) {
        if (query.length() == 0) {
            return null;
        }
        int len = 0;
        Node<Value> x = root;
        int i = 0;
        while (x != null && i < query.length()) {
            char c = query.charAt(i);
            if (c < x.c) {
                x = x.left;
            } else if (c > x.c) {
                x = x.right;
            } else {
                i++;
                if (x.value != null) {
                    len = i;
                }
                x = x.mid;
            }
        }
        return query.substring(0, len);
    }

    public Iterable<String> keys() {
        Queue<String> queue = new Queue<>();
        collect(root, new StringBuilder(), queue);
        return queue;
    }

    public Iterable<String> keysWithPrefix(String prefix) {
        Queue<String> queue = new Queue<>();
        Node<Value> x = get(root, prefix, 0);
        if (x == null) {
            return queue;
        }
        if (x.value != null) {
            queue.enqueue(prefix);
        }
        collect(x.mid, new StringBuilder(prefix), queue);
        return queue;
    }

    private void collect(Node<Value> x, StringBuilder prefix, Queue<String> queue) {
        if (x == null) {
            return;
        }
        collect(x.left, prefix, queue);
        if (x.value != null) {
            queue.enqueue(prefix.toString() + x.c);
        }
        collect(x.mid, prefix.append(x.c), queue);
        prefix.deleteCharAt(prefix.length() - 1);
        collect(x.right, prefix, queue);
    }

    public Iterable<String> keysThatMatch(String pattern) {
        Queue<String> queue = new Queue<>();
        collect(root, new StringBuilder(), 0, pattern, queue);
        return queue;
    }

    private void collect(Node<Value> x, StringBuilder prefix, int i, String pattern, Queue<String> queue) {
        if (x == null) return;
        char c = pattern.charAt(i);
        if (c == '.' || c < x.c) collect(x.left, prefix, i, pattern, queue);
        if (c == '.' || c == x.c) {
            if (i == pattern.length() - 1 && x.value != null) queue.enqueue(prefix.toString() + x.c);
            if (i < pattern.length() - 1) {
                collect(x.mid, prefix.append(x.c), i + 1, pattern, queue);
                prefix.deleteCharAt(prefix.length() - 1);
            }
        }
        if (c == '.' || c > x.c) collect(x.right, prefix, i, pattern, queue);
    }

    public static void main(String[] args) {

        // build symbol table from standard input
        TernaryST<Integer> st = new TernaryST<>();
        In in = new In(StdIn.readLine());
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

        StdOut.println("longestPrefixOf(\"shell\"):");
        StdOut.println(st.longestPrefixOf("shell"));
        StdOut.println();

        StdOut.println("keysWithPrefix(\"shor\"):");
        for (String s : st.keysWithPrefix("shor"))
            StdOut.println(s);
        StdOut.println();

        StdOut.println("keysThatMatch(\".he.l.\"):");
        for (String s : st.keysThatMatch(".he.l."))
            StdOut.println(s);

        System.out.println(st.isEmpty());
        System.out.println(st.contains("hisham"));
    }

}
