package com.company;

import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;

public class BinarySearchDeluxe {

    // Returns the index of the first key in the sorted array a[]
    // that is equal to the search key, or -1 if no such key.
    public static <Key> int firstIndexOf(Key[] a, Key key, Comparator<Key> comparator) {
        if (a == null || key == null || comparator == null) {
            throw new IllegalArgumentException("illegal argument");
        }
        int lo = 0;
        int hi = a.length - 1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if (comparator.compare(a[mid], key) < 0) {
                lo = mid + 1;
            } else if (comparator.compare(a[mid], key) > 0) {
                hi = mid - 1;
            } else if (mid == lo) {
                return mid;
            } else {
                hi = mid;
            }
        }
        return -1;
    }

    // Returns the index of the last key in the sorted array a[]
    // that is equal to the search key, or -1 if no such key.
    public static <Key> int lastIndexOf(Key[] a, Key key, Comparator<Key> comparator) {
        if (a == null || key == null || comparator == null) {
            throw new IllegalArgumentException("illegal argument");
        }
        int lo = 0;
        int hi = a.length - 1;
        while (lo <= hi) {
            int mid = lo + (hi - lo + 1) / 2;
            if (comparator.compare(a[mid], key) < 0) {
                lo = mid + 1;
            } else if (comparator.compare(a[mid], key) > 0) {
                hi = mid - 1;
            } else if (mid == hi) {
                return mid;
            } else {
                lo = mid;
            }
        }
        return -1;
    }

    // unit testing (required)
    public static void main(String[] args) {
        String[] s = {"a", "b", "c", "c", "d", "d", "d", "e", "f", "f"};
        StdOut.println(firstIndexOf(s, "d", String::compareTo));
        StdOut.println(lastIndexOf(s, "d", String::compareTo));
        StdOut.println(firstIndexOf(s, "c", Comparator.naturalOrder()));
        StdOut.println(lastIndexOf(s, "f", Comparator.naturalOrder()));

        int size = 10;
        Integer[] a = new Integer[size];
        for (int i = 0; i < a.length; i++) {
            a[i] = i;
        }

        //firstIndexOf() Testing
        for (int i = 0; i < a.length; i++) {
            int index = firstIndexOf(a, i, Comparator.naturalOrder());
            if (!a[index].equals(i)) {
                StdOut.printf("firstIndexOf found %d at %d.", i, index);
            }
        }

        //lastIndexOf() Testing
        for (int i = 0; i < a.length; i++) {
            int index = lastIndexOf(a, i, Comparator.naturalOrder());
            if (!a[index].equals(i)) {
                StdOut.printf("lastIndexOf found %d at %d/", i, index);
            }
        }

        size = 12;
        a = new Integer[size];
        for (int i = 0; i < a.length; i++) {
            a[i] = i / 4;
        }

        //lastIndexOf() Testing
        for (int i = 0; i < a.length; i++) {
            int index = lastIndexOf(a, i / 4,
                    Comparator.naturalOrder());
            if (!a[index].equals(i / 4)) {
                StdOut.printf("lastIndexOf found %d at %d/", i / 4, index);
            }
        }

        //lastIndexOf() Testing
        for (int i = 0; i < a.length; i++) {
            int index = lastIndexOf(a, i / 4,
                    Comparator.naturalOrder());
            if (!a[index].equals(i / 4)) {
                StdOut.printf("lastIndexOf found %d at %d/", i / 4, index);
            }
        }
    }
}
