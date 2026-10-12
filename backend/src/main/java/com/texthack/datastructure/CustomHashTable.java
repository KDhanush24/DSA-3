package com.texthack.datastructure;

public class CustomHashTable<K, V> {
    private static final int INITIAL_CAPACITY = 16;
    private static final float LOAD_FACTOR = 0.75f;
    private Entry<K, V>[] table;
    private int size;

    @SuppressWarnings("unchecked")
    public CustomHashTable() {
        table = new Entry[INITIAL_CAPACITY];
        size = 0;
    }

    @SuppressWarnings("unchecked")
    public CustomHashTable(int initialCapacity) {
        int cap = 1;
        while (cap < initialCapacity) cap <<= 1;
        table = new Entry[cap];
        size = 0;
    }

    public static class Entry<K, V> {
        public final K key;
        public V value;
        public Entry<K, V> next;

        public Entry(K key, V value, Entry<K, V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }

    public void put(K key, V value) {
        if (key == null) return;

        if ((float) (size + 1) / table.length > LOAD_FACTOR) {
            resize();
        }

        int hash = getHash(key, table.length);
        Entry<K, V> current = table[hash];
        while (current != null) {
            if (current.key.equals(key)) {
                current.value = value;
                return;
            }
            current = current.next;
        }

        table[hash] = new Entry<>(key, value, table[hash]);
        size++;
    }

    public V get(K key) {
        if (key == null) return null;
        int hash = getHash(key, table.length);
        Entry<K, V> current = table[hash];
        while (current != null) {
            if (current.key.equals(key)) {
                return current.value;
            }
            current = current.next;
        }
        return null;
    }

    public boolean containsKey(K key) {
        return get(key) != null;
    }

    public boolean remove(K key) {
        if (key == null) return false;
        int hash = getHash(key, table.length);
        Entry<K, V> current = table[hash];
        Entry<K, V> prev = null;
        while (current != null) {
            if (current.key.equals(key)) {
                if (prev == null) {
                    table[hash] = current.next;
                } else {
                    prev.next = current.next;
                }
                size--;
                return true;
            }
            prev = current;
            current = current.next;
        }
        return false;
    }

    public CustomArrayList<K> keys() {
        CustomArrayList<K> keyList = new CustomArrayList<>(size > 0 ? size : 10);
        for (int i = 0; i < table.length; i++) {
            Entry<K, V> current = table[i];
            while (current != null) {
                keyList.add(current.key);
                current = current.next;
            }
        }
        return keyList;
    }

    public CustomArrayList<Entry<K, V>> entries() {
        CustomArrayList<Entry<K, V>> entryList = new CustomArrayList<>(size > 0 ? size : 10);
        for (int i = 0; i < table.length; i++) {
            Entry<K, V> current = table[i];
            while (current != null) {
                entryList.add(current);
                current = current.next;
            }
        }
        return entryList;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    @SuppressWarnings("unchecked")
    private void resize() {
        int newCap = table.length * 2;
        Entry<K, V>[] newTable = new Entry[newCap];
        for (int i = 0; i < table.length; i++) {
            Entry<K, V> current = table[i];
            while (current != null) {
                Entry<K, V> next = current.next;
                int newHash = getHash(current.key, newCap);
                current.next = newTable[newHash];
                newTable[newHash] = current;
                current = next;
            }
        }
        table = newTable;
    }

    private int getHash(K key, int capacity) {
        int h = key.hashCode();
        h ^= (h >>> 16);
        return (h & 0x7fffffff) % capacity;
    }
}
