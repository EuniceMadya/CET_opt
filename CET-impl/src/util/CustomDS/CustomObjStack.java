package util.CustomDS;

import java.util.Arrays;

public class CustomObjStack<E> {
    private int top;
    private Object[] stack;


    public CustomObjStack(int size) {
        top = -1;
        stack = new Object[size];
        Arrays.fill(stack, -1);
    }

    public CustomObjStack() {
        this(10);
    }


    public boolean isEmpty() {
        return (top < 0);
    }


    public void push(E x) {

        if (top >= stack.length - 1) {
            Object[] newStack = new Object[(int) (1.75 * stack.length) + 1];
            System.arraycopy(stack, 0, newStack, 0, stack.length);
            stack = newStack;
        }
        stack[++top] = x;


    }

    public int size(){
        return top+1;
    }

    public E pop() {
        if (top < 0)
            throw new NumberFormatException();

        return (E) stack[top--];

    }

    public E peek() {
        if (top < 0)
            throw new ArrayIndexOutOfBoundsException();

        return (E) stack[top];

    }

    public Object firstElement() {
        if (top < 0)
            throw new NumberFormatException();
        return stack[0];
    }

    public Object[] getAllElements() {
        return Arrays.copyOfRange(stack, 0, top + 1);
    }

}
