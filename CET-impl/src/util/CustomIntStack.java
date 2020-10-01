package util;

import java.util.Arrays;

public class CustomIntStack {

    private int top;
    private int stack[];


    public CustomIntStack(int size) {
        top = -1;
        size = 0;
        stack = new int[size];
        Arrays.fill(stack, -1);
    }

    public CustomIntStack() {
        this(10);
    }


    public boolean isEmpty() {
        return (top < 0);
    }


    public void push(int x) {

        if (top >= stack.length - 1) {
            int[] newStack = new int[(int) (1.75 * stack.length) + 1];
            System.arraycopy(stack, 0, newStack, 0, stack.length);
            stack = newStack;
        }
        stack[++top] = x;


    }

    public int pop() {
        if (top < 0)
            throw new NumberFormatException();

        return stack[top--];

    }

    public int peek() {
        if (top < 0)
            throw new NumberFormatException();

        return stack[top];

    }

    public int[]getAllElements(){
        return Arrays.copyOfRange(stack,0, top + 1);
    }
}
