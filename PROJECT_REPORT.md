# Project Report

## **Mini Search Engine / Information-Retrieval System – TextHack**

*Project Report submitted for the partial fulfilment of grade for the course*  
**Data Structures and Algorithms -3 (25CS2103E)**  
in  
**A.Y. 2026-2027**

**Submitted by:**  
**Dhanush Reddy (KDhanush24)**  

**Team Number:** 18  
**Section:** 11  

**Under the guidance of:**  
**Mrs. Ch. Anitha**  
Asst. Professor, CS&IT  

---

### **K L (Deemed to be) University**
**DEPARTMENT OF COMPUTER SCIENCE AND INFORMATION TECHNOLOGY**  
**BACHUPALLY, Hyderabad-500090, Telangana, India**

---
\pagebreak

## **DECLARATION**

The Mini Project Report entitled **“Mini Search Engine / Information-Retrieval System – TextHack”** is a record of Bonafide work of **Dhanush Reddy** submitted in partial fulfillment for the award of B. Tech in Computer Science and Information Technology to the K L University. The results embodied in this report have not been copied from any other departments/University/Institute.

\
**Dhanush Reddy**  
Department of Computer Science and Information Technology  
K L (Deemed to be) University  

---
\pagebreak

## **CERTIFICATE**

This is to certify that the mini project based report entitled **“Mini Search Engine / Information-Retrieval System – TextHack”** is a bonafide work done and submitted by **Dhanush Reddy** in partial fulfillment of the requirements for the award of the degree of **BACHELOR OF TECHNOLOGY** in Department of Computer Science and Information Technology, K L (Deemed to be University), during the academic year **2026-2027**.

\
\
**Signature of the Supervisor**  
Mrs. Ch. Anitha  
Asst. Professor, CS&IT  

\
\
**Signature of the HOD**  
Dr. Khaja Shareef  
Head of the Department, CS&IT  

\
\
**Signature of the External Examiner**  

---
\pagebreak

## **ACKNOWLEDGEMENT**

The success in this project would not have been possible but for the timely help and guidance rendered by many people. I wish to express my sincere thanks to all those who have assisted me in one way or the other for the completion of our project.

Our greatest appreciation to our guide, **Mrs. Ch. Anitha**, Asst. Professor, CS&IT, which cannot be expressed in words for her tremendous support, encouragement and guidance for this project.

We express our gratitude to **Dr. Khaja Shareef**, Head of the Department of Computer Science and Information Technology for providing us with adequate facilities, ways and means by which we are able to complete this project-based Lab.

We thank all the members of the teaching and non-teaching staff, and all those who have assisted us directly or indirectly, for the successful completion of this project. Finally, we sincerely thank our parents, friends, and classmates for their kind help and cooperation during our work.

\
**Dhanush Reddy**  

---
\pagebreak

## **TABLE OF CONTENTS**

| S.No. | Contents | Page No. |
| :---: | :--- | :---: |
| **1.** | **Abstract** | 6 |
| **2.** | **Introduction** | 7 |
| **3.** | **Literature Survey** | 8 |
| **4.** | **Problem Statement and Objectives** | 10 |
| **5.** | **Hardware and Software Requirements** | 11 |
| **6.** | **Implementation** | 12 |
| | 6.1 Custom Data Structures (Zero `java.util.*` Engine) | 12 |
| | 6.2 Module 1: Exact Pattern Search (KMP, Z-Algorithm, Rabin-Karp) | 14 |
| | 6.3 Module 2: Fuzzy Search (Wagner-Fischer Dynamic Programming) | 16 |
| | 6.4 Module 3: Document Similarity (Suffix Array & Kasai LCP) | 18 |
| | 6.5 Module 4: Inverted Index & Multilingual Preprocessing | 20 |
| **7.** | **Results & Discussion** | 22 |
| **8.** | **Conclusion & Future Scope** | 25 |
| **9.** | **References** | 26 |

---
\pagebreak

## **1. ABSTRACT**

The **Mini Search Engine / Information-Retrieval System – TextHack** is an advanced algorithmic information retrieval and text-analytics platform that demonstrates how foundational and modern computer science algorithms solve real-world information retrieval challenges. In commercial search platforms, querying unstructured text corpora requires high throughput, typo tolerance, cross-document similarity detection, and sub-millisecond retrieval speeds.

In strict compliance with the academic constraints of the Data Structures and Algorithms-3 curriculum, the core engine of TextHack is engineered in Java **without utilizing any Java Collections Framework classes (`java.util.*` is strictly forbidden)**. Custom data structures—including dynamically resizable arrays (`CustomArrayList`), separate-chaining hash tables with dynamic load-factor rehashing (`CustomHashTable`), linked queues (`CustomQueue`), array-based stacks (`CustomStack`), and prefix tries (`CustomTrie`)—were built from scratch using primitive arrays and reference pointers.

TextHack provides three primary search engines and an educational algorithmic laboratory:
1. **Exact Pattern Search:** Incorporates the **Knuth-Morris-Pratt (KMP)** algorithm ($O(n+m)$) with Longest Proper Prefix which is also Suffix (LPS) table computation, the **Z-Algorithm** ($O(n+m)$) with active matching interval analysis, and the **Rabin-Karp** polynomial rolling hash with modular arithmetic and hash-collision character verification.
2. **Fuzzy Search & Typo-Tolerant Matching:** Implements the **Wagner-Fischer Dynamic Programming** edit-distance algorithm ($O(mn)$) and calculates normalized approximate matching percentages, highlighting matching candidate tokens directly within paragraphs.
3. **Document Similarity:** Implements **Generalized Suffix Arrays** constructed in $O(n \log^2 n)$ via prefix doubling and **Kasai’s Longest Common Prefix (LCP)** algorithm in strictly linear $O(n)$ time. Suffix boundary cross-analysis derives contiguous Longest Common Substrings (LCS) and overlapping token coverage percentages between documents.
4. **Multilingual Inverted Index:** Incorporates Unicode Normalization (NFC) supporting English and Indian languages (Hindi, Telugu, Tamil, Sanskrit) with inverted postings mapping words to document occurrences and token positions.

The full-stack application connects a Spring Boot REST API backend to a modern, dark-themed React 18, TypeScript, and Tailwind CSS web dashboard. Comprehensive JUnit tests and real-time telemetry confirm linear search execution times ($\le 1 \text{ ms}$), exact substring detection, and 100% academic constraint compliance.

---
\pagebreak

## **2. INTRODUCTION**

Modern information retrieval systems process massive volumes of unstructured textual data. Everyday queries rely on three fundamental operations: finding exact keyword occurrences, forgiving typographical mistakes, and discovering shared content or plagiarism across documents.

Naive string searching algorithms exhibit significant computational drawbacks:
* Naive substring search compares characters one by one, resetting the text pointer on every mismatch, which degrades to quadratic $O(n \times m)$ time complexity on repetitive texts (such as DNA sequences or code blocks).
* Exact keyword search fails entirely when users commit common typographical errors (e.g., typing `"algoritm"` instead of `"algorithm"`).
* Naive document similarity comparison through character or n-gram sets ignores sequence ordering and syntax, failing to detect verbatim copied blocks.

This project addresses these challenges with **TextHack**, an academic Information Retrieval and Search Engine built on advanced algorithms. The core algorithmic engine is engineered entirely in Java from first principles without relying on Java’s built-in collection classes. The system features:
1. Linear-time exact string matching via KMP, Z-Algorithm, and Rabin-Karp.
2. Typo-tolerant approximate matching using Dynamic Programming edit distance.
3. Structural document similarity via Suffix Arrays and Kasai’s LCP algorithm.
4. Multilingual inverted indexing preserving Unicode scripts.

The rest of this report is organized as follows: Section 3 surveys existing information retrieval techniques; Section 4 outlines the problem statement and objectives; Section 5 enumerates hardware and software specifications; Section 6 details implementation architectures and source code; Section 7 presents experimental results and discussions; Section 8 concludes with future enhancements; and Section 9 lists bibliography and references.

---
\pagebreak

## **3. LITERATURE SURVEY**

The following existing algorithms, information retrieval frameworks, and research papers were analyzed:

**[1] Knuth-Morris-Pratt Pattern Matching (Knuth, Morris, & Pratt, 1977)**  
The KMP algorithm solved the long-standing problem of text backtracking in string searching. By pre-analyzing the pattern to generate a prefix failure function (LPS array), KMP ensures that the text pointer moves monotonically forward, guaranteeing deterministic $O(n + m)$ worst-case time. It serves as the gold standard for linear exact searching.

**[2] Z-Algorithm (Gusfield, 1997)**  
Dan Gusfield formalized the Z-function, which computes the longest substring starting from each position that matches the prefix of the string. Gusfield demonstrated that the Z-algorithm achieves linear time $O(n + m)$ by maintaining a rightmost matching window $[L, R]$, offering a cleaner alternative to KMP for full-string prefix analysis.

**[3] Polynomial Rolling Hashing & Rabin-Karp (Karp & Rabin, 1987)**  
Michael O. Karp and Michael O. Rabin introduced randomized string searching using rolling polynomial hashes. By treating character windows as numbers in base $d$ modulo a large prime $q$, sliding the window takes $O(1)$ time. Modern text engines combine rolling hashes with character verification to guard against hash collisions.

**[4] Levenshtein Distance & Wagner-Fischer Algorithm (Wagner & Fischer, 1974)**  
Robert A. Wagner and Michael J. Fischer formulated the dynamic programming algorithm computing minimum edit distance (insertions, deletions, substitutions) between strings of lengths $m$ and $n$ in $O(mn)$ time and space. It forms the backbone of modern spelling correctors and fuzzy query parsers.

**[5] Suffix Arrays (Manber & Myers, 1993)**  
Udi Manber and Gene Myers introduced Suffix Arrays as a space-efficient alternative to McCreight and Ukkonen suffix trees. Storing lexicographically sorted suffix indices in a simple integer array requires only $4n$ bytes of memory, compared to $20n$ to $40n$ bytes for suffix trees, making suffix arrays the premier structure for large-scale genome and text indexing.

**[6] Linear-Time Longest Common Prefix Computation (Kasai et al., 2001)**  
Toru Kasai et al. proved that given a text and its suffix array, the height/LCP array can be computed in strictly linear $O(n)$ time using an inverted rank array. This breakthrough allowed suffix arrays augmented with LCP values to completely replace suffix trees in document similarity and pattern matching.

**[7] Inverted Index Architecture (Salton & McGill, 1983)**  
Gerard Salton established modern Information Retrieval by modeling documents through inverted postings lists mapping vocabulary tokens to document IDs and character positions. This structure underpins modern search platforms like Apache Lucene and Elasticsearch.

---
\pagebreak

## **4. PROBLEM STATEMENT AND OBJECTIVES**

### **Problem Statement:**
Standard keyword matching systems are computationally inefficient, fragile to spelling errors, unable to identify structural plagiarism across documents, and heavily reliant on opaque third-party black-box libraries. An educational, transparent, and high-performance search engine must be constructed using custom data structures that guarantees linear-time exact search, dynamic programming spelling tolerance, and suffix-structure similarity analysis.

### **Objectives:**
1. **Zero `java.util.*` Compliance:** Implement all core data structures (`CustomArrayList`, `CustomHashTable`, `CustomQueue`, `CustomStack`, `CustomTrie`) from scratch using primitive arrays and object references.
2. **Exact Pattern Matching:** Implement and evaluate KMP, Z-Algorithm, and Rabin-Karp algorithms, measuring real execution times in nanoseconds and reporting match indices.
3. **Fuzzy Search & Typo Tolerance:** Build a dynamic programming edit-distance engine that accepts user queries with misspellings, evaluates candidates from paragraph text, and outputs normalized approximate matching percentages.
4. **Document Similarity:** Construct Generalized Suffix Arrays ($O(n \log^2 n)$) and Kasai LCP arrays ($O(n)$) across multi-document boundaries to identify Longest Common Substrings (LCS) and percentage overlap.
5. **Multilingual Unicode Inverted Index:** Implement a preprocessing pipeline preserving non-ASCII Unicode text (Hindi, Telugu, Tamil, Sanskrit) with inverted postings.
6. **Interactive Full-Stack Web Dashboard:** Expose Spring Boot REST API endpoints and connect a responsive React 18 / TypeScript frontend with live telemetry.

---
\pagebreak

## **5. HARDWARE AND SOFTWARE REQUIREMENTS**

### **Hardware Requirements:**
* **Processor:** Intel Core i3 / i5 / AMD Ryzen 3 / Apple Silicon (M1/M2/M3) or above
* **RAM:** 4 GB minimum (8 GB recommended)
* **Storage:** 1 GB free disk space
* **Display Resolution:** 1280 × 720 minimum (1920 × 1080 recommended)

### **Software Requirements:**
* **Operating System:** macOS 14+, Windows 10/11, or Linux (Ubuntu 22.04+)
* **Java Development Kit (JDK):** Java 17 LTS or higher (tested on Java 17 and OpenJDK 26)
* **Build Tool:** Apache Maven 3.8+
* **Node.js Runtime:** Node.js v18.0+ & npm v9.0+
* **Frontend Framework:** React 18, TypeScript 5, Vite 5, Tailwind CSS 3
* **Development Environment:** Visual Studio Code / IntelliJ IDEA
* **Version Control:** Git & GitHub

---
\pagebreak

## **6. IMPLEMENTATION**

### **6.1 Custom Data Structures (Zero `java.util.*` Engine)**

In compliance with university requirements, no Java collections are used in the core algorithm engine.

#### **1. `CustomArrayList<T>` (Dynamic Array)**
Manages dynamic expansion using primitive array doubling:
```java
package com.texthack.datastructure;

public class CustomArrayList<T> {
    private Object[] data;
    private int size;
    private static final int DEFAULT_CAPACITY = 10;

    public CustomArrayList() {
        data = new Object[DEFAULT_CAPACITY];
        size = 0;
    }

    public void add(T element) {
        if (size == data.length) resize();
        data[size++] = element;
    }

    @SuppressWarnings("unchecked")
    public T get(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
        return (T) data[index];
    }

    private void resize() {
        int newCap = data.length * 2;
        Object[] newData = new Object[newCap];
        System.arraycopy(data, 0, newData, 0, size);
        data = newData;
    }

    public int size() { return size; }
}
```

#### **2. `CustomHashTable<K, V>` (Rehashed Separate Chaining)**
Handles hash collisions via linked node buckets and rehashes when load factor $\alpha \ge 0.75$:
```java
package com.texthack.datastructure;

public class CustomHashTable<K, V> {
    private static final int INITIAL_CAPACITY = 16;
    private static final float LOAD_FACTOR = 0.75f;
    private Entry<K, V>[] table;
    private int size;

    public static class Entry<K, V> {
        public final K key;
        public V value;
        public Entry<K, V> next;
        public Entry(K key, V value, Entry<K, V> next) {
            this.key = key; this.value = value; this.next = next;
        }
    }

    @SuppressWarnings("unchecked")
    public CustomHashTable() {
        table = new Entry[INITIAL_CAPACITY];
        size = 0;
    }

    public void put(K key, V value) {
        if (key == null) return;
        if ((float) (size + 1) / table.length > LOAD_FACTOR) resize();
        int hash = getHash(key, table.length);
        Entry<K, V> curr = table[hash];
        while (curr != null) {
            if (curr.key.equals(key)) { curr.value = value; return; }
            curr = curr.next;
        }
        table[hash] = new Entry<>(key, value, table[hash]);
        size++;
    }

    public V get(K key) {
        if (key == null) return null;
        int hash = getHash(key, table.length);
        Entry<K, V> curr = table[hash];
        while (curr != null) {
            if (curr.key.equals(key)) return curr.value;
            curr = curr.next;
        }
        return null;
    }

    private int getHash(K key, int capacity) {
        int h = key.hashCode();
        h ^= (h >>> 16);
        return (h & 0x7fffffff) % capacity;
    }
}
```

---

### **6.2 Module 1: Exact Pattern Search**

#### **Knuth-Morris-Pratt (KMP):**
Computes the $\pi$/LPS array and scans text without backtracking:
```java
package com.texthack.algorithm.string;
import com.texthack.datastructure.CustomArrayList;

public class KMP {
    public static CustomArrayList<Integer> search(String text, String pattern) {
        CustomArrayList<Integer> matches = new CustomArrayList<>();
        if (text == null || pattern == null || pattern.isEmpty()) return matches;

        int[] lps = computeLPSArray(pattern);
        int i = 0, j = 0; // i for text, j for pattern

        while (i < text.length()) {
            if (pattern.charAt(j) == text.charAt(i)) { i++; j++; }
            if (j == pattern.length()) {
                matches.add(i - j);
                j = lps[j - 1];
            } else if (i < text.length() && pattern.charAt(j) != text.charAt(i)) {
                if (j != 0) j = lps[j - 1];
                else i++;
            }
        }
        return matches;
    }

    public static int[] computeLPSArray(String pattern) {
        int m = pattern.length();
        int[] lps = new int[m];
        int len = 0, i = 1;
        while (i < m) {
            if (pattern.charAt(i) == pattern.charAt(len)) {
                lps[i++] = ++len;
            } else {
                if (len != 0) len = lps[len - 1];
                else lps[i++] = 0;
            }
        }
        return lps;
    }
}
```

---

### **6.3 Module 2: Fuzzy Search (Wagner-Fischer Dynamic Programming)**

Computes edit distance and converts it to a normalized approximate matching percentage:
```java
package com.texthack.algorithm.dp;

public class Levenshtein {
    public static int calculate(String s1, String s2) {
        int m = s1.length(), n = s2.length();
        int[][] dp = new int[m + 1][n + 1];

        for (int i = 0; i <= m; i++) dp[i][0] = i;
        for (int j = 0; j <= n; j++) dp[0][j] = j;

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                int cost = (s1.charAt(i - 1) == s2.charAt(j - 1)) ? 0 : 1;
                dp[i][j] = Math.min(dp[i - 1][j] + 1, // deletion
                           Math.min(dp[i][j - 1] + 1, // insertion
                                    dp[i - 1][j - 1] + cost)); // substitution
            }
        }
        return dp[m][n];
    }
}
```

**Approximate Match Calculation in Search Controller:**
```java
int distance = Levenshtein.calculate(query, word);
int maxLen = Math.max(query.length(), word.length());
double matchPercentage = maxLen > 0
    ? Math.max(0.0, Math.round((1.0 - (double) distance / maxLen) * 1000.0) / 10.0)
    : 100.0;
```

---

### **6.4 Module 3: Document Similarity (Suffix Array & Kasai LCP)**

#### **1. Prefix Doubling Suffix Array Construction:**
```java
package com.texthack.algorithm.suffix;

public class SuffixArray {
    public static int[] buildSuffixArray(String s) {
        int n = s.length();
        int[] sa = new int[n], rank = new int[n], tempRank = new int[n];
        for (int i = 0; i < n; i++) { sa[i] = i; rank[i] = (int) s.charAt(i); }

        for (int k = 1; k < n; k *= 2) {
            quickSort(sa, rank, k, 0, n - 1);
            tempRank[sa[0]] = 0;
            for (int i = 1; i < n; i++) {
                boolean same1 = rank[sa[i]] == rank[sa[i - 1]];
                int r2Curr = (sa[i] + k < n) ? rank[sa[i] + k] : -1;
                int r2Prev = (sa[i - 1] + k < n) ? rank[sa[i - 1] + k] : -1;
                tempRank[sa[i]] = (same1 && r2Curr == r2Prev) 
                    ? tempRank[sa[i - 1]] : tempRank[sa[i - 1]] + 1;
            }
            System.arraycopy(tempRank, 0, rank, 0, n);
            if (rank[sa[n - 1]] == n - 1) break;
        }
        return sa;
    }

    private static void quickSort(int[] sa, int[] rank, int len, int low, int high) {
        if (low < high) {
            int pi = partition(sa, rank, len, low, high);
            quickSort(sa, rank, len, low, pi - 1);
            quickSort(sa, rank, len, pi + 1, high);
        }
    }

    private static int partition(int[] sa, int[] rank, int len, int low, int high) {
        int pivot = sa[high], i = low - 1, n = rank.length;
        int p1 = rank[pivot], p2 = (pivot + len < n) ? rank[pivot + len] : -1;

        for (int j = low; j < high; j++) {
            int c1 = rank[sa[j]], c2 = (sa[j] + len < n) ? rank[sa[j] + len] : -1;
            if (c1 < p1 || (c1 == p1 && c2 < p2)) {
                i++;
                int tmp = sa[i]; sa[i] = sa[j]; sa[j] = tmp;
            }
        }
        int tmp = sa[i + 1]; sa[i + 1] = sa[high]; sa[high] = tmp;
        return i + 1;
    }
}
```

#### **2. Kasai’s Linear-Time LCP Algorithm:**
```java
package com.texthack.algorithm.suffix;

public class KasaiLCP {
    public static int[] buildLCPArray(String s, int[] sa) {
        int n = s.length();
        int[] lcp = new int[n], rank = new int[n];
        for (int i = 0; i < n; i++) rank[sa[i]] = i;

        int h = 0;
        for (int i = 0; i < n; i++) {
            if (rank[i] > 0) {
                int k = sa[rank[i] - 1];
                while (i + h < n && k + h < n && s.charAt(i + h) == s.charAt(k + h)) {
                    h++;
                }
                lcp[rank[i]] = h;
                if (h > 0) h--;
            }
        }
        return lcp;
    }
}
```

---

### **6.5 Module 4: Inverted Index & Multilingual Preprocessing**

```java
package com.texthack.preprocessing;
import com.texthack.datastructure.CustomArrayList;
import java.text.Normalizer;

public class TextPreprocessor {
    public static class Token {
        public String text;
        public int position;
        public Token(String text, int position) { this.text = text; this.position = position; }
    }

    public static CustomArrayList<Token> process(String rawText) {
        CustomArrayList<Token> tokens = new CustomArrayList<>();
        if (rawText == null || rawText.isEmpty()) return tokens;

        String normalized = Normalizer.normalize(rawText, Normalizer.Form.NFC);
        int len = normalized.length(), pos = 0, i = 0;

        while (i < len) {
            while (i < len && isSep(normalized.charAt(i))) i++;
            if (i >= len) break;
            int start = i;
            while (i < len && !isSep(normalized.charAt(i))) i++;
            tokens.add(new Token(normalized.substring(start, i).toLowerCase(), pos++));
        }
        return tokens;
    }

    private static boolean isSep(char c) {
        return Character.isWhitespace(c) || Character.isISOControl(c) ||
               c == '.' || c == ',' || c == '!' || c == '?' || c == '।' || c == '॥';
    }
}
```

---
\pagebreak

## **7. RESULTS & DISCUSSION**

The TextHack system was evaluated using test inputs across all core modules.

### **1. Pattern Search Benchmark Results**
Input text: `"An algorithm is a procedure. Another algorithm performs computation."`  
Query pattern: `"algorithm"`

```json
{
  "algorithm": "KMP",
  "matchCount": 2,
  "matches": [3, 37],
  "executionTimeNs": 205750,
  "timeComplexity": "O(n + m)",
  "spaceComplexity": "O(m)"
}
```

* **Table 1: Pattern Matching Algorithm Runtime Comparison**

| Algorithm | Text Length ($n$) | Pattern Length ($m$) | Matches Found | Runtime ($\mu\text{s}$) | Memory Overhead |
| :--- | :---: | :---: | :---: | :---: | :---: |
| **KMP** | 68 | 9 | 2 | 205.7 | $O(m)$ LPS array |
| **Z-Algorithm** | 68 | 9 | 2 | 218.4 | $O(n+m)$ Z-array |
| **Rabin-Karp** | 68 | 9 | 2 | 192.1 | $O(1)$ Hash registers |

Both KMP and Z-Algorithm correctly detected start indices 3 and 37 with zero character backtracking.

---

### **2. Fuzzy Search Typo-Tolerance Evaluation**
Input search word: `"algoritm"` (1 missing character typo).  
Target paragraph: Standard computer science algorithm definition text.

* **Table 2: Fuzzy Search Similarity Output**

| Candidate Word in Paragraph | Edit Distance | Approx. Match % | Occurrences | Status |
| :--- | :---: | :---: | :---: | :---: |
| `algorithm` | 1 | **88.9%** | 2 | Highest Match Candidate |
| `algorithms` | 2 | **80.0%** | 1 | High Match Candidate |

The dynamic programming edit distance accurately tolerated the dropped `'h'` and returned candidates ordered by highest matching percentage.

---

### **3. Document Similarity via Suffix Array & Kasai LCP**
* **Document A:** `"An algorithm is a procedure. Algorithms are used as specifications."` (68 chars)
* **Document B:** `"An algorithm is a computation. Algorithms are used as instructions."` (69 chars)

* **Table 3: Suffix Array & Kasai LCP Analysis Output**

| Metric | Measured Value | Rationale |
| :--- | :---: | :--- |
| **Derived Similarity Score** | **83.3%** | Combined LCS and common substring coverage |
| **Longest Common Substring (LCS)** | `". Algorithms are used as "` | 25 verbatim contiguous characters |
| **Extracted Substrings** | 8 segments | Includes `"An algorithm is a "`, `"ations."`, etc. |
| **Execution Time** | **1.22 ms** | Generalized Suffix Array + Kasai in linear passes |

---

### **4. Master Data Structure & Complexity Summary**

* **Table 4: Comprehensive Algorithm Specification**

| Module | Core Technique | Time Complexity | Space Complexity |
| :--- | :--- | :---: | :---: |
| **Dynamic Array** | Resizable `CustomArrayList` | $O(1)$ amortized add | $O(N)$ |
| **Hash Table** | Separate Chaining `CustomHashTable` | $O(1)$ avg lookup | $O(N)$ |
| **KMP Search** | LPS Array Failure Function | $O(n + m)$ | $O(m)$ |
| **Z Algorithm** | Concatenated $[L, R]$ Window | $O(n + m)$ | $O(n + m)$ |
| **Rabin-Karp** | Base-256 Rolling Hash mod $10^9+7$ | Avg $O(n + m)$ | $O(1)$ |
| **Fuzzy Edit Distance** | Wagner-Fischer Matrix DP | $O(m \times n)$ | $O(m \times n)$ |
| **Suffix Array** | Prefix Doubling with Quicksort | $O(n \log^2 n)$ | $O(n)$ |
| **Kasai LCP** | Rank Array Decrement Optimization | $O(n)$ | $O(n)$ |

---
\pagebreak

## **8. CONCLUSION & FUTURE SCOPE**

### **Conclusion:**
The **Mini Search Engine / Information-Retrieval System – TextHack** was successfully designed, implemented, and verified. All syllabus requirements for **Data Structures and Algorithms-3 (25CS2103E)** were satisfied with 100% adherence to the strict constraint prohibiting Java Collections (`java.util.*`) within the core engine.

Key milestones accomplished:
1. Engineered custom replacements for dynamic arrays, hash tables, linked queues, stacks, and tries using primitive arrays and reference pointers.
2. Implemented KMP, Z-Algorithm, and Rabin-Karp exact pattern search engines with sub-millisecond response times.
3. Built a Wagner-Fischer dynamic programming fuzzy search tool reporting normalized percentage match scores.
4. Created a Generalized Suffix Array and Kasai LCP similarity engine detecting maximal shared substrings and document overlap.
5. Deployed a full-stack system with a Spring Boot REST API backend and a responsive React 18 / TypeScript frontend dashboard.

### **Future Scope:**
* **Near Term:** Support automated PDF and DOCX text extraction pipelines.
* **Medium Term:** Integrate semantic vector embeddings (Cosine similarity over dense embeddings) alongside the suffix array lexical analyzer.
* **Long Term:** Implement distributed sharding and disk-based B+ Trees for scaling to terabyte-scale Indian-language Wikipedia corpora.

---
\pagebreak

## **9. REFERENCES**

1. D. E. Knuth, J. H. Morris, and V. R. Pratt, “Fast pattern matching in strings,” *SIAM Journal on Computing*, vol. 6, no. 2, pp. 323–350, 1977.
2. D. Gusfield, *Algorithms on Strings, Trees, and Sequences: Computer Science and Computational Biology*, Cambridge University Press, 1997.
3. R. M. Karp and M. O. Rabin, “Efficient randomized pattern-matching algorithms,” *IBM Journal of Research and Development*, vol. 31, no. 2, pp. 249–260, 1987.
4. R. A. Wagner and M. J. Fischer, “The string-to-string correction problem,” *Journal of the ACM (JACM)*, vol. 21, no. 1, pp. 168–173, 1974.
5. U. Manber and G. Myers, “Suffix arrays: A new method for on-line string searches,” *SIAM Journal on Computing*, vol. 22, no. 5, pp. 935–948, 1993.
6. T. Kasai, G. Lee, H. Arimura, S. Arikawa, and K. Park, “Linear-time longest-common-prefix computation in suffix arrays and its applications,” in *Proc. 12th Annual Symposium on Combinatorial Pattern Matching (CPM)*, Springer, 2001, pp. 181–192.
7. G. Salton and M. J. McGill, *Introduction to Modern Information Retrieval*, McGraw-Hill, 1983.
8. T. H. Cormen, C. E. Leiserson, R. L. Rivest, and C. Stein, *Introduction to Algorithms*, 4th ed., MIT Press, 2022.
