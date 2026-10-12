package com.texthack.dto;

import java.util.List;

public class PrimalityRequest {
    private String number;
    private int rounds = 10;

    public String getNumber() { return number; }
    public void setNumber(String number) { this.number = number; }

    public int getRounds() { return rounds; }
    public void setRounds(int rounds) { this.rounds = rounds; }
}
