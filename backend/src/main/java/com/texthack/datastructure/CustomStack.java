package com.texthack.datastructure;

import java.util.EmptyStackException;

public class CustomStack<T> {
    private Object[] data;
    private int top;
    private static final int DEFAULT_CAPACITY = 10;

    public CustomStack() {
        data = new Object[DEFAULT_CAPACITY];
        top = -1;
    }

    public void push(T item) {
        if (top == data.length - 1) {
            resize();
        }
        data[++top] = item;
    }

    @SuppressWarnings("unchecked")
    public T pop() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        T item = (T) data[top];
        data[top--] = null; // Help GC
        return item;
    }

    @SuppressWarnings("unchecked")
    public T peek() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        return (T) data[top];
    }

    public boolean isEmpty() {
        return top == -1;
    }

    public int size() {
        return top + 1;
    }

    private void resize() {
        int newCapacity = data.length * 2;
        Object[] newData = new Object[newCapacity];
        System.arraycopy(data, 0, newData, 0, data.length);
        data = newData;
    }
}
