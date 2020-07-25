import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private Node<Item> first;    // beginning of queue
    private Node<Item> last;     // end of queue
    private int n;

    private static class Node<Item> {
        private Item item;
        private Node<Item> next;
        private Node<Item> prev;
    }

    // construct an empty deque
    public Deque() {
        first = null;
        last = null;
        n = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return first == null;
    }

    // return the number of items on the deque
    public int size() {
        return n;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("null item");
        }

        Node<Item> oldlast = new Node<>();
        if (isEmpty()) {
            oldlast.item = item;
            first = oldlast;
            last = oldlast;
        } else {
            oldlast = last;
            last = new Node<>();
            last.item = item;
            last.next = null;
            oldlast.next = last;
            last.prev = oldlast;
        }
        n++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("null item");
        }

        Node<Item> oldfirst = new Node<>();
        if (isEmpty()) {
            oldfirst.item = item;
            first = oldfirst;
            last = oldfirst;

        } else {
            oldfirst = first;
            first = new Node<>();
            first.item = item;
            first.next = oldfirst;
            oldfirst.prev = first;
        }
        n++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException("Queue underflow");

        Item item = last.item;
        if (n == 1) {
            first = null;
            last = null;
        } else {
            last = last.prev;
            last.next = null;
            if (n == 2) first = last;   // to avoid loitering
        }
        n--;
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException("Queue underflow");

        Item item = first.item;
        if (n == 1) {
            first = null;
            last = null;
        } else {
            first = first.next;
            first.prev = null;
            if (n == 2) first = last;   // to avoid loitering
        }
        n--;
        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new LinkedIterator();
    }

    private class LinkedIterator implements Iterator<Item> {

        private Node<Item> current;

        public LinkedIterator() {
            current = last;   //as we declare last as front
        }

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException("operation unsupported");
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException("no next");
            Item item = current.item;
            current = current.prev;
            return item;
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<String> queue = new Deque<>();

        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            String item1 = StdIn.readString();
            if (!item.equals("-")) {
                queue.addFirst(item);
                queue.addLast(item1);
            } else if (!queue.isEmpty()) {
                StdOut.print(queue.removeFirst() + " ");
                StdOut.print(queue.removeLast() + " ");
            }
        }
        StdOut.println("(" + queue.size() + " left on queue)");
    }

}
