package algorithms_class._03_stack_and_queue;
import java.util.Iterator;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    /*public RandomizedQueue()                 // construct an empty randomized queue
    public boolean isEmpty()                 // is the queue empty?
    public int size()                        // return the number of items on the queue
    public void enqueue(Item item)           // add the item
    public Item dequeue()                    // remove and return a random item
    public Item sample()                     // return (but do not remove) a random item
    public Iterator<Item> iterator()         // return an independent iterator over items in random order
    public static void main(String[] args)   // unit testing (optional)*/

    private Item[] items;
    private int size = 0;
    private int queueSize = 0;

    // construct an empty randomized queue
    public RandomizedQueue(int size) {
        this.items = (Item[])(new Object[size]);
        this.queueSize = size;
    }
    public RandomizedQueue() {
        this(10);
    }

    // is the queue empty?
    public boolean isEmpty(){
        return this.size == 0;
    }

    // return the number of items on the queue
    public int size() {
        return this.size;
    }

    // enlarge the current queue
    private void enlargeQueue() {
        this.queueSize*=2;
        Item[] newQueue = (Item[])(new Object[this.queueSize]);
        for (int i=0; i<this.size; i++) {
            newQueue[i] = this.items[i];
        }
        this.items = newQueue;
    }

    // add the item
    public void enqueue(Item item) {

        if (item == null) {
            throw new java.lang.IllegalArgumentException();
        }

        if (this.size == this.queueSize) {
            this.enlargeQueue();
        }

        this.items[this.size] = item;
        this.size ++;
    }

    // shrink the current queue
    private void shrinkQueue() {
        this.queueSize/=2;
        Item[] newQueue = (Item[])(new Object[this.queueSize]);
        for (int i=0; i<this.size; i++) {
            newQueue[i] = this.items[i];
        }
        this.items = newQueue;
    }

    // remove and return a random item
    public Item dequeue() {

        if (this.size() == 0) {
            throw new java.util.NoSuchElementException();
        }

        if (this.size < this.queueSize/4) {
            this.shrinkQueue();
        }
        this.size--;

        int randomPosition = 0;
        if (this.size > 0) {
            randomPosition = StdRandom.uniform(this.size);
        }
        Item item = this.items[randomPosition];
        this.items[randomPosition] = this.items[this.size];
        this.items[this.size] = null;

        return item;
    }

    // return (but do not remove) a random item
    public Item sample() {

        if (this.size() == 0) {
            throw new java.util.NoSuchElementException();
        }

        int randomPosition = StdRandom.uniform(this.size - 1);
        return this.items[randomPosition];
    }

    private class Node {
        Item item;
        Node next;
    }

    private class RandomizedQueueIterator implements Iterator<Item>  {

        private RandomizedQueue<Item> iteratorItems;
        public RandomizedQueueIterator() {
            iteratorItems = new RandomizedQueue<>(size);
            iteratorItems.size = size;
            for (int i=0; i<size; i++) {
                iteratorItems.items[i] = items[i];
            }
        }

        public boolean hasNext() {
            if (this.iteratorItems.size == 0) {
                return false;
            }

            return true;
        }

        public void remove() {
            throw new java.lang.UnsupportedOperationException();
        }

        public Item next() {
            if (this.iteratorItems.size == 0) {
                throw new java.util.NoSuchElementException();
            }

            return this.iteratorItems.dequeue();
        }

    }

    // return an iterator over items in order from front to end
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    // unit testing (optional)
    public static void main(String[] args) {
        RandomizedQueue<String> queue = new RandomizedQueue<String>();
        queue.enqueue("1");
        queue.enqueue("2");
        queue.enqueue("3");
        queue.enqueue("4");
        queue.enqueue("5");
        queue.enqueue("6");
        Iterator<String> iterator = queue.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }

    }

}