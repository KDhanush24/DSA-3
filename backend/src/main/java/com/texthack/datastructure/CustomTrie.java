package com.texthack.datastructure;

public class CustomTrie {
    public static class TrieNode {
        public CustomHashTable<Character, TrieNode> children;
        public boolean isEndOfWord;
        public int frequency;

        public TrieNode() {
            children = new CustomHashTable<>();
            isEndOfWord = false;
            frequency = 0;
        }
    }

    private final TrieNode root;
    private int wordCount;

    public CustomTrie() {
        root = new TrieNode();
        wordCount = 0;
    }

    public void insert(String word) {
        if (word == null || word.isEmpty()) return;
        TrieNode current = root;
        for (int i = 0; i < word.length(); i++) {
            char ch = word.charAt(i);
            TrieNode child = current.children.get(ch);
            if (child == null) {
                child = new TrieNode();
                current.children.put(ch, child);
            }
            current = child;
        }
        if (!current.isEndOfWord) {
            current.isEndOfWord = true;
            wordCount++;
        }
        current.frequency++;
    }

    public boolean search(String word) {
        if (word == null || word.isEmpty()) return false;
        TrieNode node = getNode(word);
        return node != null && node.isEndOfWord;
    }

    public boolean startsWith(String prefix) {
        if (prefix == null || prefix.isEmpty()) return true;
        return getNode(prefix) != null;
    }

    private TrieNode getNode(String str) {
        TrieNode current = root;
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            current = current.children.get(ch);
            if (current == null) return null;
        }
        return current;
    }

    public int getWordCount() {
        return wordCount;
    }
}
