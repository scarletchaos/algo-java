/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private int size = 0;
    private Node first = null;
    private Node last = null;

    private class Node {
        Item item;
        Node next = null;
        Node prev = null;
    }

    // construct an empty deque
    public Deque() {

    }

    // is the deque empty?
    public boolean isEmpty() {
        return first == null;
    }


    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) throw new IllegalArgumentException();
        size++;
        Node oldFirst = first;
        first = new Node();
        first.item = item;
        if (last == null) last = first;
        else {
            first.next = oldFirst;
            oldFirst.prev = first;
        }
    }


    // add the item to the back
    public void addLast(Item item) {
        if (item == null) throw new IllegalArgumentException();
        size++;
        Node oldLast = last;
        last = new Node();
        last.item = item;
        if (isEmpty()) first = last;
        else {
            oldLast.next = last;
            last.prev = oldLast;
        }
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException("Can't push/pop from empty deque");
        size--;
        Item item = first.item;
        first = first.next;
        if (isEmpty()) last = null;
        else first.prev = null;
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException("Can't push/pop from empty deque");
        size--;
        Item item = last.item;
        last = last.prev;
        if (last == null) first = null;
        else last.next = null;
        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) throw new java.util.NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<Integer> deck = new Deque<Integer>();
        System.out.println(deck.isEmpty());
        deck.addFirst(1);
        System.out.println(deck.isEmpty());
        System.out.println(deck.removeLast());
        System.out.println(deck.isEmpty());
        deck.addLast(1);
        System.out.println(deck.isEmpty());
        System.out.println(deck.removeFirst());
        System.out.println(deck.isEmpty());
        deck.addLast(1);
        deck.addLast(2);
        deck.addFirst(0);
        deck.addLast(3);
        deck.addFirst(-1);
        for (int item : deck) {
            System.out.println(item);
        }
    }

}
