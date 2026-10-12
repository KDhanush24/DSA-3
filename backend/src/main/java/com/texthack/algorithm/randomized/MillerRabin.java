package com.texthack.algorithm.randomized;

import com.texthack.datastructure.CustomArrayList;
import java.math.BigInteger;
import java.util.Random;

public class MillerRabin {

    public static class PrimalityResult {
        public String number;
        public boolean isPrime; // false = definitely composite, true = probably prime
        public int roundsTested;
        public CustomArrayList<String> witnessesTested;
        public String compositeWitness;
        public double confidencePercentage;
        public String statusMessage;
        public String mathematicalExplanation;
        public long executionTimeNs;

        public PrimalityResult() {
            witnessesTested = new CustomArrayList<>();
        }
    }

    public static PrimalityResult testPrimality(String numberStr, int rounds) {
        PrimalityResult result = new PrimalityResult();
        result.number = numberStr;
        result.roundsTested = rounds;
        long startTime = System.nanoTime();

        BigInteger n;
        try {
            n = new BigInteger(numberStr.trim());
        } catch (NumberFormatException e) {
            result.statusMessage = "Invalid integer format.";
            result.isPrime = false;
            return result;
        }

        // Trivial edge cases
        if (n.compareTo(BigInteger.ONE) <= 0) {
            result.isPrime = false;
            result.confidencePercentage = 100.0;
            result.statusMessage = "Numbers <= 1 are neither prime nor composite.";
            result.executionTimeNs = System.nanoTime() - startTime;
            return result;
        }
        if (n.equals(BigInteger.valueOf(2)) || n.equals(BigInteger.valueOf(3))) {
            result.isPrime = true;
            result.confidencePercentage = 100.0;
            result.statusMessage = "Definite Prime (Smallest prime cases).";
            result.executionTimeNs = System.nanoTime() - startTime;
            return result;
        }
        if (!n.testBit(0)) { // even number > 2
            result.isPrime = false;
            result.confidencePercentage = 100.0;
            result.compositeWitness = "2 (Even number > 2 is composite)";
            result.statusMessage = "Definitely Composite (Even number).";
            result.executionTimeNs = System.nanoTime() - startTime;
            return result;
        }

        // Write n - 1 as 2^s * d with d odd
        BigInteger d = n.subtract(BigInteger.ONE);
        int s = 0;
        while (!d.testBit(0)) {
            d = d.shiftRight(1);
            s++;
        }

        Random rand = new Random(42); // deterministic seed for reproducibility or random
        boolean compositeFound = false;

        for (int r = 0; r < rounds; r++) {
            // Pick a random base 'a' in range [2, n - 2]
            BigInteger a;
            if (n.compareTo(BigInteger.valueOf(1000)) < 0) {
                int maxVal = n.intValue() - 3;
                int aVal = 2 + (maxVal > 0 ? rand.nextInt(maxVal) : 0);
                a = BigInteger.valueOf(aVal);
            } else {
                BigInteger nMinus4 = n.subtract(BigInteger.valueOf(4));
                BigInteger rnd = new BigInteger(n.bitLength(), rand);
                a = rnd.mod(nMinus4).add(BigInteger.valueOf(2));
            }

            result.witnessesTested.add(a.toString());

            // Compute x = a^d mod n
            BigInteger x = a.modPow(d, n);

            if (x.equals(BigInteger.ONE) || x.equals(n.subtract(BigInteger.ONE))) {
                continue; // Passes this round
            }

            boolean compositeInRound = true;
            for (int i = 0; i < s - 1; i++) {
                x = x.modPow(BigInteger.valueOf(2), n);
                if (x.equals(n.subtract(BigInteger.ONE))) {
                    compositeInRound = false;
                    break;
                }
            }

            if (compositeInRound) {
                compositeFound = true;
                result.compositeWitness = a.toString();
                break;
            }
        }

        if (compositeFound) {
            result.isPrime = false;
            result.confidencePercentage = 100.0;
            result.statusMessage = "Definitely Composite (Fermat witness found).";
            result.mathematicalExplanation = "Found witness a = " + result.compositeWitness + " showing a^(d*2^r) != -1 (mod n). Therefore n is definitively not a prime.";
        } else {
            result.isPrime = true;
            // Miller-Rabin error probability bound is at most (1/4)^rounds
            double errorProb = Math.pow(0.25, rounds);
            result.confidencePercentage = Math.round((1.0 - errorProb) * 1000000.0) / 10000.0;
            result.statusMessage = "Probably Prime (" + result.confidencePercentage + "% confidence).";
            result.mathematicalExplanation = "Passed " + rounds + " independent Miller-Rabin rounds with no witness found. The probability of a false positive composite is at most (1/4)^" + rounds + " ≈ " + String.format("%.2e", errorProb) + ".";
        }

        result.executionTimeNs = System.nanoTime() - startTime;
        return result;
    }
}
