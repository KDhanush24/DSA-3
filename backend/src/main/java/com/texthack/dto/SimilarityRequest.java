package com.texthack.dto;

public class SimilarityRequest {
    private String textA;
    private String textB;
    private String docIdA;
    private String docIdB;

    public String getTextA() { return textA; }
    public void setTextA(String textA) { this.textA = textA; }

    public String getTextB() { return textB; }
    public void setTextB(String textB) { this.textB = textB; }

    public String getDocIdA() { return docIdA; }
    public void setDocIdA(String docIdA) { this.docIdA = docIdA; }

    public String getDocIdB() { return docIdB; }
    public void setDocIdB(String docIdB) { this.docIdB = docIdB; }
}
