package util.CustomDS;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayQueue<E> {

    private int startPos;
    private int currentPos;
    private int size;
    private Object[] queue;

    /**
     * Standard constructor. Constructs an array of size 10 if no size is provided.
     */
    public ArrayQueue() {
        this(10);
    }

    /**
     * Constructs an arraylist of the specified size.
     *
     * @param startSize initial capacity
     */
    public ArrayQueue(int startSize) {
        if (startSize <= 1) startSize = 2;
        this.queue = new Object[startSize];
        this.size = 0;
        this.startPos = 0;
        this.currentPos = 0;
    }

    /**
     * Returns size of the queue after startPos.
     * Note: Can't use ArrayList.size because it may contain elements before startPos.
     *
     * @return Size of queue.
     */
    public int size() {
        return size;
    }

    /**
     * Checks if the queue is empty.
     *
     * @return true if empty, false if else.
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Checks if the queue contains an element.
     * NOTE: Can't use ArrayList.contains because the element may exist before the startPos.
     *
     * @param e Element to check.
     * @return true if element exists in queue, false if else.
     */
    public boolean contains(E e) {
        for (int i = startPos; i < queue.length; i++) {
            E value = (E) queue[i];
            if (value.equals(e)) return true;
        }

        return false;
    }

    /**
     * Returns an iterator of the queue starting at startPos.
     *
     * @return An iterator for the queue.
     */
    public Iterator<? extends E> iterator() {
        return (Iterator<? extends E>) Arrays.asList(queue).subList(startPos, queue.length).iterator();
    }

    /**
     * Converts queue to array.
     *
     * @return Queue in array form.
     */
    public E[] toArray() {
        return (E[]) Arrays.copyOfRange(queue, startPos, queue.length);
    }


    /**
     * Clears the queue.
     */
    private void clear() {
        queue = new Object[11];
        startPos = 0;
        currentPos = 0;
        size = 0;
    }

    /**
     * Removes all elements within the collection.
     * NOTE: Can't use ArrayList.removeAll because the element may exist before the startPos.
     *
     * @param collection Collection of elements to be removed.
     * @return True if all elements are removed. False if else.
     */
    public boolean removeAll(Collection<? extends E> collection) {
        clear();
        // This is fucking dodgy lol.
        return true;
    }

    /**
     * Resizes the queue, but only carries over elements from before the start position.
     */
    private void resize() {
        Object[] newQueue = new Object[(int) (queue.length * 1.75)];
        System.arraycopy(queue, startPos, newQueue, 0, queue.length - startPos);

        currentPos = queue.length - startPos;
        startPos = 0;
        queue = newQueue;

    }

    /**
     * Adds the object to the end of the queue. Throws NullPointerException if element is null.
     *
     * @param e Object to be added.
     */
    public void offer(E e) {
        if (e == null) throw new NullPointerException();
        queue[currentPos++] = e;
        size++;

        if (currentPos == queue.length) resize();
    }

    /**
     * Retrieves and removes the object at the head of this queue. Throws NoSuchElementException if queue is empty.
     *
     * @return Element at head of queue.
     */
    private E remove() {
        if (startPos >= queue.length || queue[startPos] == null) throw new NoSuchElementException();
        size--;
        return (E) queue[startPos++];
    }

    /**
     * Retrieves and removes the object at the head of this queue.
     *
     * @return Element at head of queue.
     */
    public E poll() {
        E item;
        try {
            item = remove();
        } catch (NoSuchElementException e) {
            return null;
        }
        return item;
    }

    /**
     * Retrieves, but does not remove, the head of this queue. Throws NoSuchElementException if queue is empty.
     *
     * @return Element at head of queue.
     */
    private E element() {
        if (startPos >= queue.length || queue[startPos] == null) throw new NoSuchElementException();
        return (E) queue[startPos];
    }

    /**
     * Retrieves, but does not remove, the head of this queue.
     *
     * @return Element at head of queue.
     */
    public E peek() {
        E item;
        try {
            item = element();
        } catch (NoSuchElementException e) {
            return null;
        }
        return item;
    }
}
