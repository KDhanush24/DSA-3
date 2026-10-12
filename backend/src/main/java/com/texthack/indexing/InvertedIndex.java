package com.texthack.indexing;

import com.texthack.datastructure.CustomArrayList;
import com.texthack.datastructure.CustomHashTable;
import com.texthack.preprocessing.TextPreprocessor;
import com.texthack.model.Document;

public class InvertedIndex {

    public static class Posting {
        public String docId;
        public CustomArrayList<Integer> positions;

        public Posting(String docId) {
            this.docId = docId;
            this.positions = new CustomArrayList<>();
        }
    }

    public static class PostingsList {
        public String term;
        public CustomArrayList<Posting> postings;

        public PostingsList(String term) {
            this.term = term;
            this.postings = new CustomArrayList<>();
        }

        public void addOccurrence(String docId, int position) {
            Posting target = null;
            for (int i = 0; i < postings.size(); i++) {
                if (postings.get(i).docId.equals(docId)) {
                    target = postings.get(i);
                    break;
                }
            }
            if (target == null) {
                target = new Posting(docId);
                postings.add(target);
            }
            target.positions.add(position);
        }
    }

    private final CustomHashTable<String, PostingsList> indexTable;
    private int totalOccurrences;
    private int documentCount;

    public InvertedIndex() {
        this.indexTable = new CustomHashTable<>();
        this.totalOccurrences = 0;
        this.documentCount = 0;
    }

    public synchronized void indexDocument(Document doc) {
        if (doc == null || doc.getContent() == null) return;
        CustomArrayList<TextPreprocessor.Token> tokens = TextPreprocessor.process(doc.getContent());

        for (int i = 0; i < tokens.size(); i++) {
            TextPreprocessor.Token token = tokens.get(i);
            PostingsList list = indexTable.get(token.text);
            if (list == null) {
                list = new PostingsList(token.text);
                indexTable.put(token.text, list);
            }
            list.addOccurrence(doc.getId(), token.position);
            totalOccurrences++;
        }
        documentCount++;
        doc.setIndexed(true);
    }

    public PostingsList search(String term) {
        if (term == null) return null;
        String clean = term.trim().toLowerCase();
        return indexTable.get(clean);
    }

    public CustomArrayList<String> getAllTerms() {
        return indexTable.keys();
    }

    public int getUniqueTermsCount() {
        return indexTable.size();
    }

    public int getTotalOccurrences() {
        return totalOccurrences;
    }

    public int getDocumentCount() {
        return documentCount;
    }

    public void clear() {
        CustomArrayList<String> keys = indexTable.keys();
        for (int i = 0; i < keys.size(); i++) {
            indexTable.remove(keys.get(i));
        }
        totalOccurrences = 0;
        documentCount = 0;
    }
}
