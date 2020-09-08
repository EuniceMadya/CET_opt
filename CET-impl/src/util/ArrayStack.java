package util;

// Reference from https://www.geeksforgeeks.org/stack-data-structure-introduction-program/

import java.util.Arrays;

public class ArrayStack  {
    int [] stack;
    int top;
    int capacity;

    public ArrayStack(int capacity){
        stack = new int[capacity];
        Arrays.fill( stack, -1);
        top = -1;
        this.capacity = capacity;
    }

    public ArrayStack(){
        stack = new int[Integer.MAX_VALUE];
        Arrays.fill(stack, -1);
        top = -1;
        this.capacity = Integer.MAX_VALUE;
    }


    public boolean isEmpty()
    {
        return (top < 0);
    }

    public boolean push(int x)
    {
        if (top >= (capacity - 1)) {
            System.out.println("ERROR<Array Stack>: Stack Overflow");
            return false;
        }
        else {
            stack[++top] = x;
            System.out.println(x + " pushed into stack");
            return true;
        }
    }

    public int pop()
    {
        if (top < 0) {
            System.out.println("Stack Underflow");
            return 0;
        }
        else {
            int x = stack[top--];
            return x;
        }
    }

    public int peek()
    {
        if (top < 0) {
            System.out.println("ERROR<Array Stack>: Stack Underflow");
            return 0;
        }
        else {
            int x = stack[top];
            return x;
        }
    }

    public int[] getStack(){
        return stack;
    }

    public void addAll(ArrayStack stack){
        for(int i: stack.getStack()) push(i);
    }

    public void clear(){
        Arrays.fill(stack, -1);
    }

    public int size(){
        return top;
    }

}
