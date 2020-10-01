package util;

import util.CustomDS.ArrayQueue;

public class TestDriver {

    public static void main(String[] args) {
        ArrayQueue<String> queue = new ArrayQueue<>(4);
        queue.offer("Swoon");
        queue.offer("Yoon");
        queue.offer("Butt");
        queue.offer("Hole");
        queue.offer("Test");
        queue.offer("Swoon");
        queue.offer("Yoon");
        queue.offer("Butt");
        queue.offer("Hole");
        queue.offer("Test");
        System.out.println(queue.size());
        System.out.println(queue.poll());
        System.out.println(queue.size());
        System.out.println(queue.poll());
        System.out.println(queue.size());
        System.out.println(queue.poll());
        System.out.println(queue.size());
        System.out.println(queue.poll());
        System.out.println(queue.size());
        System.out.println(queue.poll());
        System.out.println(queue.size());
        System.out.println(queue.poll());
        System.out.println(queue.size());
        System.out.println(queue.poll());
        System.out.println(queue.size());
        System.out.println(queue.poll());
        System.out.println(queue.size());
        System.out.println(queue.poll());
        System.out.println(queue.size());
        queue.offer("Test2");
        System.out.println(queue.size());
        System.out.println(queue.poll());
        System.out.println(queue.size());
        System.out.println(queue.poll());
        System.out.println(queue.size());
    }
}
