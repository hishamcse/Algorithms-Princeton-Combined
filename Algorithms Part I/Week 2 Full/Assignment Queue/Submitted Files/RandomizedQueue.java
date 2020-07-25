import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] a;
    private int n;

    // construct an empty randomized queue
    public RandomizedQueue() {
        a = (Item[]) new Object[2];
        n = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return n == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return n;
    }

    private void resize(int capacity) {
        assert capacity >= n;

        // textbook implementation
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < n; i++) {
            copy[i] = a[i];
        }
        a = copy;

        // alternative implementation
        // a = java.util.Arrays.copyOf(a, capacity);
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("illegal");
        }
        if (n == a.length) resize(2 * a.length);    // double size of array if necessary
        a[n++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException("Stack underflow");
        int p = StdRandom.uniform(n);
        Item item = a[p];
        a[p] = a[n - 1];
        n--;
        // shrink size of array if necessary
        if (n > 0 && n == a.length / 4) resize(a.length / 2);
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException("Stack underflow");
        return a[StdRandom.uniform(n)];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomArrayIterator();
    }

    private class RandomArrayIterator implements Iterator<Item> {
        private final int[] b = new int[n];
        private int j;

        public RandomArrayIterator() {
            for (int i = 0; i < n; i++) {
                b[i] = i;
            }
            StdRandom.shuffle(b);
        }

        public boolean hasNext() {
            return j < n;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            return a[b[j++]];
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<String> stack = new RandomizedQueue<>();
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            if (!item.equals("-")) stack.enqueue(item);
            else if (!stack.isEmpty()) StdOut.print(stack.dequeue() + " ");
        }
        StdOut.println("(" + stack.size() + " left on stack)");
    }
}

