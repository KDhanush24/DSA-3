package com.texthack.preprocessing;

import com.texthack.datastructure.CustomArrayList;
import java.text.Normalizer;

public class TextPreprocessor {

    public static class Token {
        public String text;
        public int position; // token sequence index (0-based)
        public int charStart;
        public int charEnd;

        public Token(String text, int position, int charStart, int charEnd) {
            this.text = text;
            this.position = position;
            this.charStart = charStart;
            this.charEnd = charEnd;
        }
    }

    /**
     * Modular preprocessing pipeline preserving Unicode glyphs for Indian languages
     * (Devanagari, Telugu, Tamil, Bengali, Kannada, Malayalam, etc.) as well as Latin.
     */
    public static CustomArrayList<Token> process(String rawText) {
        CustomArrayList<Token> tokens = new CustomArrayList<>();
        if (rawText == null || rawText.isEmpty()) return tokens;

        // Step 1: Unicode Normalization (NFC standard for canonical composition)
        String normalized = Normalizer.normalize(rawText, Normalizer.Form.NFC);

        // Step 2 & 3: Unicode-aware Tokenization and Punctuation handling
        int len = normalized.length();
        int tokenIndex = 0;
        int i = 0;

        while (i < len) {
            // Skip whitespace and punctuation separators
            while (i < len && isSeparator(normalized.charAt(i))) {
                i++;
            }
            if (i >= len) break;

            int start = i;
            while (i < len && !isSeparator(normalized.charAt(i))) {
                i++;
            }
            int end = i;

            String word = normalized.substring(start, end);
            // Case normalization for scripts with case (Latin, Cyrillic, Greek), while harmless for Indian scripts
            String cleanWord = word.toLowerCase();

            tokens.add(new Token(cleanWord, tokenIndex++, start, end));
        }

        return tokens;
    }

    private static boolean isSeparator(char c) {
        if (Character.isWhitespace(c)) return true;
        // Punctuation characters in ASCII and common Unicode punctuation (Danda, Double Danda, etc.)
        int type = Character.getType(c);
        return type == Character.CONNECTOR_PUNCTUATION ||
               type == Character.DASH_PUNCTUATION ||
               type == Character.START_PUNCTUATION ||
               type == Character.END_PUNCTUATION ||
               type == Character.INITIAL_QUOTE_PUNCTUATION ||
               type == Character.FINAL_QUOTE_PUNCTUATION ||
               type == Character.OTHER_PUNCTUATION ||
               c == '|' || c == '।' || c == '॥'; // Devanagari Danda and Double Danda
    }
}
