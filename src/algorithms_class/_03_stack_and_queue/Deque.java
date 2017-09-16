package algorithms_class._03_stack_and_queue;

import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {
    /*public Deque()                           // construct an empty deque
    public boolean isEmpty()                 // is the deque empty?
    public int size()                        // return the number of items on the deque
    public void addFirst(Item item)          // add the item to the front
    public void addLast(Item item)           // add the item to the end
    public Item removeFirst()                // remove and return the item from the front
    public Item removeLast()                 // remove and return the item from the end
    public Iterator<Item> iterator()         // return an iterator over items in order from front to end
    public static void main(String[] args)   // unit testing (optional)*/

    private class Node {
        Item item;
        Node next;
        Node prev;
    }

    private Node first = null, last = null;
    private int size = 0;

    // return the number of items on the deque
    public boolean isEmpty() {
        return this.size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return this.size;
    }

    // add the item to the front
    public void addFirst(Item item) {

        if (item == null) {
            throw new java.lang.IllegalArgumentException();
        }

        Node node = new Node();
        node.item = item;
        node.next = this.first;
        node.prev = null;

        if (this.first == null) {
            this.first = node;
        }
        else {
            this.first.prev = node;
            this.first = node;
        }

        if (this.last == null) {
            this.last = node;
        }

        this.size++;
    }

    // add the item to the end
    public void addLast(Item item) {

        if (item == null) {
            throw new java.lang.IllegalArgumentException();
        }

        Node node = new Node();
        node.item = item;
        node.next = null;
        node.prev = this.last;

        if (this.last == null) {
            this.last = node;
        }
        else {
            this.last.next = node;
            this.last = node;
        }

        if (this.first == null) {
            this.first = node;
        }

        this.size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {

        if (this.size() == 0) {
            throw new java.util.NoSuchElementException();
        }

        Node node = this.first;
        this.first = this.first.next;
        this.first.prev = null;
        this.size--;

        return node.item;
    }

    // remove and return the item from the end
    public Item removeLast() {

        if (this.size() == 0) {
            throw new java.util.NoSuchElementException();
        }

        Node node = this.last;
        this.last = this.last.prev;
        this.last.next = null;
        this.size--;

        return node.item;
    }

    private class DequeIterator implements Iterator<Item>  {

        Node currentNode = null;

        public DequeIterator() {
            this.currentNode = first;
        }

        public boolean hasNext() {
            if (this.currentNode == null) {
                return false;
            }

            return true;
        }

        public void remove() {
            throw new java.lang.UnsupportedOperationException();
        }

        public Item next() {
            if (this.currentNode == null) {
                throw new java.util.NoSuchElementException();
            }

            Item currentItem = this.currentNode.item;
            this.currentNode = this.currentNode.next;
            return currentItem;
        }

    }

    // return an iterator over items in order from front to end
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    // unit testing (optional)
    public static void main(String[] args) {
        Deque<String> deque = new Deque<String>();
        deque.addFirst("1");
        deque.addFirst("2");
        deque.addLast("3");
        deque.addLast("4");
        deque.addFirst("5");
        deque.addLast("6");
        Iterator<String> iterator = deque.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }

    }

}
