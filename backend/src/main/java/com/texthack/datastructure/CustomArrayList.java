package com.texthack.datastructure;

public class CustomArrayList<T> {
    private Object[] data;
    private int size;
    private static final int DEFAULT_CAPACITY = 10;

    public CustomArrayList() {
        data = new Object[DEFAULT_CAPACITY];
        size = 0;
    }

    public CustomArrayList(int capacity) {
        if (capacity < 0) {
            throw new IllegalArgumentException("Capacity cannot be negative");
        }
        data = new Object[Math.max(capacity, 1)];
        size = 0;
    }

    public void add(T element) {
        if (size == data.length) {
            resize();
        }
        data[size++] = element;
    }

    @SuppressWarnings("unchecked")
    public T get(int index) {
        checkIndex(index);
        return (T) data[index];
    }

    public void set(int index, T element) {
        checkIndex(index);
        data[index] = element;
    }

    public boolean contains(T element) {
        return indexOf(element) >= 0;
    }

    public int indexOf(T element) {
        if (element == null) {
            for (int i = 0; i < size; i++) {
                if (data[i] == null) return i;
            }
        } else {
            for (int i = 0; i < size; i++) {
                if (element.equals(data[i])) return i;
            }
        }
        return -1;
    }

    @SuppressWarnings("unchecked")
    public T remove(int index) {
        checkIndex(index);
        T oldValue = (T) data[index];
        int numMoved = size - index - 1;
        if (numMoved > 0) {
            System.arraycopy(data, index + 1, data, index, numMoved);
        }
        data[--size] = null;
        return oldValue;
    }

    public boolean removeElement(T element) {
        int idx = indexOf(element);
        if (idx >= 0) {
            remove(idx);
            return true;
        }
        return false;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void clear() {
        for (int i = 0; i < size; i++) {
            data[i] = null;
        }
        size = 0;
    }

    private void resize() {
        int newCapacity = data.length == 0 ? 1 : data.length * 2;
        Object[] newData = new Object[newCapacity];
        System.arraycopy(data, 0, newData, 0, size);
        data = newData;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }
}
