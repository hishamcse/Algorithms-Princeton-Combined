package com.Hisham.Trie;

import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class PatriciaST<Value> {   // implementation from booksite

    private final Node head;
    private int count;

    /* An inner Node class specifies the objects that hold each key-value pair.
     * The b value indicates the relevant bit position.
     */
    private class Node {
        private Node left, right;
        private final String key;
        private Value val;
        private int b;

        public Node(String key, Value val, int b) {
            this.key = key;
            this.val = val;
            this.b = b;
        }
    }

    /**
     * Initializes an empty PATRICIA-based symbol table.
     */
    /* The constructor creates a head (sentinel) node that contains a
     * zero-length string.
     */
    public PatriciaST() {
        head = new Node("", null, 0);
        head.left = head;
        head.right = head;
        count = 0;
    }

    /**
     * Places a key-value pair into the symbol table. If the table already
     * contains the specified key, then its associated value becomes updated.
     * If the value provided is {@code null}, then the key becomes removed
     * from the symbol table.
     *
     * @param key the key
     * @param val the value
     * @throws IllegalArgumentException if {@code key} is {@code null}
     * @throws IllegalArgumentException if {@code key} is the empty string.
     */
    public void put(String key, Value val) {
        if (key == null) throw new IllegalArgumentException("called put(null)");
        if (key.length() == 0) throw new IllegalArgumentException("invalid key");
        if (val == null) delete(key);
        Node p;
        Node x = head;
        do {
            p = x;
            if (safeBitTest(key, x.b)) x = x.right;
            else x = x.left;
        } while (p.b < x.b);
        if (!x.key.equals(key)) {
            int b = firstDifferingBit(x.key, key);
            x = head;
            do {
                p = x;
                if (safeBitTest(key, x.b)) x = x.right;
                else x = x.left;
            } while (p.b < x.b && x.b < b);
            Node t = new Node(key, val, b);
            if (safeBitTest(key, b)) {
                t.left = x;
                t.right = t;
            } else {
                t.left = t;
                t.right = x;
            }
            if (safeBitTest(key, p.b)) p.right = t;
            else p.left = t;
            count++;
        } else x.val = val;
    }

    /**
     * Retrieves the value associated with the given key.
     *
     * @param key the key
     * @return the value associated with the given key if the key is in the
     * symbol table and {@code null} if the key is not in the symbol table
     * @throws IllegalArgumentException if {@code key} is {@code null}
     * @throws IllegalArgumentException if {@code key} is the empty string.
     */
    public Value get(String key) {
        if (key == null) throw new IllegalArgumentException("called get(null)");
        if (key.length() == 0) throw new IllegalArgumentException("invalid key");
        Node p;
        Node x = head;
        do {
            p = x;
            if (safeBitTest(key, x.b)) x = x.right;
            else x = x.left;
        } while (p.b < x.b);
        if (x.key.equals(key)) return x.val;
        else return null;
    }

    /**
     * Removes a key and its associated value from the symbol table, if it
     * exists.
     *
     * @param key the key
     * @throws IllegalArgumentException if {@code key} is {@code null}
     * @throws IllegalArgumentException if {@code key} is the empty string.
     */
    public void delete(String key) {
        if (key == null) throw new IllegalArgumentException("called delete(null)");
        if (key.length() == 0) throw new IllegalArgumentException("invalid key");
        Node g;             // previous previous (grandparent)
        Node p = head;      // previous (parent)
        Node x = head;      // node to delete
        do {
            g = p;
            p = x;
            if (safeBitTest(key, x.b)) x = x.right;
            else x = x.left;
        } while (p.b < x.b);
        if (x.key.equals(key)) {
            Node z;
            Node y = head;
            do {            // find the true parent (z) of x
                z = y;
                if (safeBitTest(key, y.b)) y = y.right;
                else y = y.left;
            } while (y != x);
            if (x == p) {   // case 1: remove (leaf node) x
                Node c;     // child of x
                if (safeBitTest(key, x.b)) c = x.left;
                else c = x.right;
                if (safeBitTest(key, z.b)) z.right = c;
                else z.left = c;
            } else {          // case 2: p replaces (internal node) x
                Node c;     // child of p
                if (safeBitTest(key, p.b)) c = p.left;
                else c = p.right;
                if (safeBitTest(key, g.b)) g.right = c;
                else g.left = c;
                if (safeBitTest(key, z.b)) z.right = p;
                else z.left = p;
                p.left = x.left;
                p.right = x.right;
                p.b = x.b;
            }
            count--;
        }
    }

    /**
     * Returns {@code true} if the key-value pair, specified by the given
     * key, exists within the symbol table.
     *
     * @param key the key
     * @return {@code true} if this symbol table contains the given
     * {@code key} and {@code false} otherwise
     * @throws IllegalArgumentException if {@code key} is {@code null}
     * @throws IllegalArgumentException if {@code key} is the empty string.
     */
    public boolean contains(String key) {
        return get(key) != null;
    }

    /**
     * Returns {@code true} if the symbol table is empty.
     *
     * @return {@code true} if this symbol table is empty and
     * {@code false} otherwise
     */
    boolean isEmpty() {
        return count == 0;
    }

    /**
     * Returns the number of key-value pairs within the symbol table.
     *
     * @return the number of key-value pairs within this symbol table
     */
    int size() {
        return count;
    }

    /**
     * Returns all keys in the symbol table as an {@code Iterable}.
     * To iterate over all of the keys in the symbol table named
     * {@code st}, use the foreach notation:
     * {@code for (Key key : st.keys())}.
     *
     * @return all keys in the symbol table as an {@code Iterable}
     */
    public Iterable<String> keys() {
        Queue<String> queue = new Queue<>();
        if (head.left != head) keys(head.left, 0, queue);
        if (head.right != head) keys(head.right, 0, queue);
        return queue;
    }

    private void keys(Node x, int b, Queue<String> queue) {
        if (x.b > b) {
            keys(x.left, x.b, queue);
            queue.enqueue(x.key);
            keys(x.right, x.b, queue);
        }
    }

    /* The safeBitTest function logically appends a terminating sequence (when
     * required) to extend (logically) the string beyond its length.
     *
     * The inner loops of the get and put methods flow much better when they
     * are not concerned with the lengths of strings, so a trick is employed to
     * allow the get and put methods to view every string as an "infinite"
     * sequence of bits. Logically, every string gets a '\uffff' character,
     * followed by an "infinite" sequence of '\u0000' characters, appended to
     * the end.
     *
     * Note that the '\uffff' character serves to mark the end of the string,
     * and it is necessary. Simply padding with '\u0000' is insufficient to
     * make all unique Unicode strings "look" unique to the get and put methods
     * (because these methods do not regard string lengths).
     */
    private static boolean safeBitTest(String key, int b) {
        if (b < key.length() * 16) return bitTest(key, b) != 0;
        if (b > key.length() * 16 + 15) return false;   // padding
        /* 16 bits of 0xffff */
        return true;    // end marker
    }

    private static int bitTest(String key, int b) {
        return (key.charAt(b >>> 4) >>> (b & 0xf)) & 1;
    }

    /* Like the safeBitTest function, the safeCharAt function makes every
     * string look like an "infinite" sequence of characters. Logically, every
     * string gets a '\uffff' character, followed by an "infinite" sequence of
     * '\u0000' characters, appended to the end.
     */
    private static int safeCharAt(String key, int i) {
        if (i < key.length()) return key.charAt(i);
        if (i > key.length()) return 0x0000;            // padding
        else return 0xffff;            // end marker
    }

    /* For efficiency's sake, the firstDifferingBit function compares entire
     * characters first, and then considers the individual bits (once it finds
     * two characters that do not match). Also, the least significant bits of
     * an individual character are examined first. There are many Unicode
     * alphabets where most (if not all) of the "action" occurs in the least
     * significant bits.
     *
     * Notice that the very first character comparison excludes the
     * least-significant bit. The firstDifferingBit function must never return
     * zero; otherwise, a node would become created as a child to the head
     * (sentinel) node that matches the bit-index value (zero) stored in the
     * head node. This would violate the invariant that bit-index values
     * increase as you descend into the trie.
     */
    private static int firstDifferingBit(String k1, String k2) {
        int i = 0;
        int c1 = safeCharAt(k1, 0) & ~1;
        int c2 = safeCharAt(k2, 0) & ~1;
        if (c1 == c2) {
            i = 1;
            while (safeCharAt(k1, i) == safeCharAt(k2, i)) i++;
            c1 = safeCharAt(k1, i);
            c2 = safeCharAt(k2, i);
        }
        int b = 0;
        while (((c1 >>> b) & 1) == ((c2 >>> b) & 1)) b++;
        return i * 16 + b;
    }

    public static void main(String[] args) {
        PatriciaST<Integer> st = new PatriciaST<>();
        int limitItem = 1000000;
        int limitPass = 1;
        int countPass = 0;
        boolean ok = true;

        if (args.length > 0) limitItem = Integer.parseInt(args[0]);
        if (args.length > 1) limitPass = Integer.parseInt(args[1]);

        do {
            String[] a = new String[limitItem];
            int[] v = new int[limitItem];

            StdOut.printf("Creating dataset (%d items)...\n", limitItem);
            for (int i = 0; i < limitItem; i++) {
                a[i] = Integer.toString(i, 16);
                v[i] = i;
            }

            StdOut.printf("Shuffling...\n");
            StdRandom.shuffle(v);

            StdOut.printf("Adding (%d items)...\n", limitItem);
            for (int i = 0; i < limitItem; i++)
                st.put(a[v[i]], v[i]);

            int countKeys = 0;
            StdOut.printf("Iterating...\n");
            for (String ignored : st.keys()) countKeys++;
            StdOut.printf("%d items iterated\n", countKeys);
            if (countKeys != limitItem) ok = false;
            if (countKeys != st.size()) ok = false;

            StdOut.printf("Shuffling...\n");
            StdRandom.shuffle(v);

            int limitDelete = limitItem / 2;
            StdOut.printf("Deleting (%d items)...\n", limitDelete);
            for (int i = 0; i < limitDelete; i++)
                st.delete(a[v[i]]);

            countKeys = 0;
            StdOut.printf("Iterating...\n");
            for (String ignored : st.keys()) countKeys++;
            StdOut.printf("%d items iterated\n", countKeys);
            if (countKeys != limitItem - limitDelete) ok = false;
            if (countKeys != st.size()) ok = false;

            int countDelete = 0;
            int countRemain = 0;
            StdOut.printf("Checking...\n");
            for (int i = 0; i < limitItem; i++) {
                if (i < limitDelete) {
                    if (!st.contains(a[v[i]])) countDelete++;
                } else {
                    int val = st.get(a[v[i]]);
                    if (val == v[i]) countRemain++;
                }
            }
            StdOut.printf("%d items found and %d (deleted) items missing\n",
                    countRemain, countDelete);
            if (countRemain + countDelete != limitItem) ok = false;
            if (countRemain != st.size()) ok = false;
            if (st.isEmpty()) ok = false;

            StdOut.printf("Deleting the rest (%d items)...\n",
                    limitItem - countDelete);
            for (int i = countDelete; i < limitItem; i++)
                st.delete(a[v[i]]);
            if (!st.isEmpty()) ok = false;

            countPass++;
            if (ok) StdOut.printf("PASS %d TESTS SUCCEEDED\n", countPass);
            else StdOut.printf("PASS %d TESTS FAILED\n", countPass);
        } while (ok && countPass < limitPass);

        if (!ok) throw new java.lang.RuntimeException("TESTS FAILED");
    }
}

