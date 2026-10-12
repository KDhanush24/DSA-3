package com.texthack.dto;

import java.util.List;

public class PrimalityResponse {
    private String number;
    private boolean isPrime;
    private int roundsTested;
    private List<String> witnessesTested;
    private String compositeWitness;
    private double confidencePercentage;
    private String statusMessage;
    private String mathematicalExplanation;
    private long executionTimeNs;

    public String getNumber() { return number; }
    public void setNumber(String number) { this.number = number; }

    public boolean isPrime() { return isPrime; }
    public void setPrime(boolean prime) { isPrime = prime; }

    public int getRoundsTested() { return roundsTested; }
    public void setRoundsTested(int roundsTested) { this.roundsTested = roundsTested; }

    public List<String> getWitnessesTested() { return witnessesTested; }
    public void setWitnessesTested(List<String> witnessesTested) { this.witnessesTested = witnessesTested; }

    public String getCompositeWitness() { return compositeWitness; }
    public void setCompositeWitness(String compositeWitness) { this.compositeWitness = compositeWitness; }

    public double getConfidencePercentage() { return confidencePercentage; }
    public void setConfidencePercentage(double confidencePercentage) { this.confidencePercentage = confidencePercentage; }

    public String getStatusMessage() { return statusMessage; }
    public void setStatusMessage(String statusMessage) { this.statusMessage = statusMessage; }

    public String getMathematicalExplanation() { return mathematicalExplanation; }
    public void setMathematicalExplanation(String mathematicalExplanation) { this.mathematicalExplanation = mathematicalExplanation; }

    public long getExecutionTimeNs() { return executionTimeNs; }
    public void setExecutionTimeNs(long executionTimeNs) { this.executionTimeNs = executionTimeNs; }
}
