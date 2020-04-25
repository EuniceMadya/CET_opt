package util;

import java.util.*;

public class ArrayQueue<E> {

    private int startPos;
    private int size;
    private ArrayList<E> queue;

    /**
     * Standard constructor. Constructs an arraylist of size 11 if no size is provided.
     */
    public ArrayQueue() {
        this.queue = new ArrayList<>();
        this.size = 0;
        this.startPos = 0;
    }

    /**
     * Constructs an arraylist of the specified size.
     * @param startSize
     */
    public ArrayQueue(int startSize) {
        this.queue = new ArrayList<>(startSize);
        this.size = 0;
        this.startPos = 0;
    }

    /**
     * Returns size of the queue after startPos.
     * Note: Can't use ArrayList.size because it may contain elements before startPos.
     * @return Size of queue.
     */
    public int size() {
        return size;
    }

    /**
     * Checks if the queue is empty.
     * @return true if empty, false if else.
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Checks if the queue contains an element.
     * NOTE: Can't use ArrayList.contains because the element may exist before the startPos.
     * @param e Element to check.
     * @return true if element exists in queue, false if else.
     */
    public boolean contains(E e) {
        for (int i = startPos; i < queue.size(); i++) {
            if (queue.get(i).equals(e)) return true;
        }

        return false;
    }

    /**
     * Returns an iterator of the queue starting at startPos.
     * @return An iterator for the queue.
     */
    public Iterator<? extends E> iterator() {
        return queue.subList(startPos, queue.size()).iterator();
    }

    /**
     * Converts queue to array.
     * @return Queue in array form.
     */
    public E[] toArray() {
        return (E[]) queue.subList(startPos, queue.size()).toArray();
    }

    /**
     * Removes an element from the queue.
     * NOTE: Can't use ArrayList.remove because the element may exist before the startPos.
     * @param e Element to remove.
     * @return True if successfully removed, false if else.
     */
    public boolean remove(E e) {
        for (int i = startPos; i < queue.size(); i++) {
            if (queue.get(i).equals(e)) {
                queue.remove(i);
                size--;
                return true;
            }
        }
        return false;
    }

    /**
     * Clears the queue.
     */
    public void clear() {
        queue.clear();
    }

    /**
     * Removes all elements within the collection.
     * NOTE: Can't use ArrayList.removeAll because the element may exist before the startPos.
     * @param collection Collection of elements to be removed.
     * @return True if all elements are removed. False if else.
     */
    public boolean removeAll(Collection<? extends E> collection) {
        int numRemoved = 0;
        for (int i = startPos; i < queue.size(); i++) {
            if (collection.contains(queue.get(i))) {
                queue.remove(i);
                i--;
                numRemoved++;
                size--;
            }
        }
        return numRemoved == collection.size();
    }

    /**
     * Adds the object to the end of the queue. Throws NullPointerException if element is null.
     * @param e Object to be added.
     */
    public void offer(E e) {
        if (e == null) throw new NullPointerException();
        queue.add(e);
        size++;
    }

    /**
     * Retrieves and removes the object at the head of this queue. Throws NoSuchElementException if queue is empty.
     * @return Element at head of queue.
     */
    public E remove() {
        if (queue.get(startPos) == null) throw new NoSuchElementException();
        size--;
        return queue.get(startPos++);
    }

    /**
     * Retrieves and removes the object at the head of this queue.
     * @return Element at head of queue.
     */
    public E poll() {
        if (queue.get(startPos) == null) return null;
        size--;
        return queue.get(startPos++);
    }

    /**
     * Retrieves, but does not remove, the head of this queue. Throws NoSuchElementException if queue is empty.
     * @return Element at head of queue.
     */
    public E element() {
        if (queue.get(startPos) == null) throw new NoSuchElementException();
        return queue.get(startPos);
    }

    /**
     * Retrieves, but does not remove, the head of this queue.
     * @return Element at head of queue.
     */
    public E peek() {
        return queue.get(startPos);
    }
}
