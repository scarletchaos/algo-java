/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] items;
    private int size = 0;

    // construct an empty randomized queue
    public RandomizedQueue() {
        items = (Item[]) new Object[1];
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    private void resize() {
        if ((double) size / items.length <= 0.25) {
            Item[] newItems = (Item[]) new Object[size];
            for (int i = 0; i < size; i++) newItems[i] = items[i];
            items = newItems;
        }
        else {
            Item[] newItems = (Item[]) new Object[size * 2];
            for (int i = 0; i < size; i++) newItems[i] = items[i];
            items = newItems;
        }
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    // add the item
    public void enqueue(Item item) {
        if (size == items.length) resize();
        items[size++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        int index = StdRandom.uniformInt(size);
        Item item = items[index];
        items[index] = items[--size];
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        return items[StdRandom.uniformInt(size)];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new ListIterator(size);
    }

    private class ListIterator implements Iterator<Item> {
        int[] indexes;
        int curr = 0;
        int max;

        public ListIterator(int size) {
            indexes = new int[size];
            max = size;
            for (int i = 0; i < size; i++) indexes[i] = i;
            StdRandom.shuffle(indexes);
        }

        public boolean hasNext() {
            return curr < max;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (curr >= max) throw new NoSuchElementException();
            return items[indexes[curr++]];
        }


    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> rq = new RandomizedQueue<>();
        rq.enqueue(1);
        rq.enqueue(2);
        rq.enqueue(3);
        rq.enqueue(4);
        for (int item : rq) System.out.println(item);
        System.out.println(rq.dequeue());
        System.out.println(rq.dequeue());
        System.out.println(rq.dequeue());
        System.out.println(rq.dequeue());
    }

}
