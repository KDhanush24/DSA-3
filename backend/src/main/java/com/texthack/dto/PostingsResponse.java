package com.texthack.dto;

import java.util.List;

public class PostingsResponse {
    public static class DocPostingDto {
        public String docId;
        public String docTitle;
        public List<Integer> positions;

        public DocPostingDto() {}
        public DocPostingDto(String docId, String docTitle, List<Integer> positions) {
            this.docId = docId;
            this.docTitle = docTitle;
            this.positions = positions;
        }
    }

    private String term;
    private int documentCount;
    private List<DocPostingDto> postings;

    public String getTerm() { return term; }
    public void setTerm(String term) { this.term = term; }

    public int getDocumentCount() { return documentCount; }
    public void setDocumentCount(int documentCount) { this.documentCount = documentCount; }

    public List<DocPostingDto> getPostings() { return postings; }
    public void setPostings(List<DocPostingDto> postings) { this.postings = postings; }
}
