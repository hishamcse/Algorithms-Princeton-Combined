package com.Hisham.UndirectedGraph;

import edu.princeton.cs.algs4.ST;

public class IndexSET<Key extends Comparable<Key>> {  // a special symbol table where key and value both can be easily accessible
    private int n;
    private final ST<Key, Integer> st = new ST<Key, Integer>();
    private final ST<Integer, Key> ts = new ST<Integer, Key>();

    public void add(Key key) {
        if (!st.contains(key)) {
            st.put(key, n);
            ts.put(n, key);
            n++;
        }
    }

    public int indexOf(Key key) {
        return st.get(key);
    }

    public Key keyOf(int index) {
        return ts.get(index);
    }

    public boolean contains(Key key) { return st.contains(key);  }
    public int size()                { return st.size();         }
    public Iterable<Key> keys()      { return st.keys();         }
}
