# TextHack – Mini Search Engine & Advanced Information Retrieval System
**Data Structures and Algorithms-3 (DSA-3) Capstone Project**

---

## 📌 1. Project Overview
**TextHack** is a mini search engine and advanced text-analytics platform designed for academic demonstration and viva. It solves real-world information retrieval, text similarity, spelling correction, and pattern matching problems through real, custom-engineered algorithm implementations.

Unlike generic search demos with mock outputs, TextHack performs **real-time algorithm execution and mathematical processing** on a multilingual corpus (supporting English, Hindi, Telugu, Tamil, and Sanskrit Unicode).

---

## 🏛 2. Core Academic Requirement
> **"java.util.* is forbidden inside the engine."**

Inside the core algorithm engine, **all Java Collections Framework classes are strictly prohibited**:
* ❌ No `java.util.HashMap`
* ❌ No `java.util.ArrayList`
* ❌ No `java.util.LinkedList`
* ❌ No `java.util.HashSet`
* ❌ No `java.util.Stack`
* ❌ No `java.util.Queue`
* ❌ No `java.util.PriorityQueue`
* ❌ No `java.util.Collections.sort`

### Custom Data Structures Implemented:
* `CustomArrayList<T>`: Dynamic resizable array with bounds checking and index shifting.
* `CustomHashTable<K, V>`: Separate chaining hash table with dynamic rehashing at 0.75 load factor.
* `CustomQueue<T>`: Linked FIFO queue used in BFS and graph traversals.
* `CustomStack<T>`: LIFO array stack with dynamic expansion.
* `CustomLinkedList<T>`: Singly linked list with node-level pointers.
* `CustomTrie`: Unicode-compatible prefix tree for vocabulary management.
* `SuffixArray.customSort()`: In-place recursive quicksort implementation on primitive arrays for suffix tuples.

---

## 🔬 3. Algorithms & Complexity Matrix

| Module | Algorithm | Time Complexity | Space Complexity | Implementation Detail |
| :--- | :--- | :--- | :--- | :--- |
| **Pattern Search** | **Knuth-Morris-Pratt (KMP)** | $O(n + m)$ | $O(m)$ | Precomputes LPS array (Longest Proper Prefix which is Suffix); skips redundant comparisons without text backtracking. |
| **Pattern Search** | **Z-Algorithm** | $O(n + m)$ | $O(n + m)$ | Builds the Z-array on `pattern$text` using an active matching interval $[L, R]$. |
| **Pattern Search** | **Rabin-Karp** | Avg: $O(n + m)$, Worst: $O(nm)$ | $O(1)$ | Base-256 polynomial rolling hash modulo $10^9+7$ with character verification to handle hash collisions. |
| **Fuzzy Search** | **Wagner-Fischer Levenshtein** | $O(mn)$ | $O(mn)$ | $(m+1) \times (n+1)$ Dynamic Programming matrix tracking optimal insertions, deletions, substitutions. |
| **Fuzzy Search** | **Damerau-Levenshtein** | $O(mn)$ | $O(mn)$ | Extends Wagner-Fischer recursion with adjacent two-character transpositions. |
| **Document Similarity** | **Suffix Array (Prefix Doubling)** | $O(n \log^2 n)$ | $O(n)$ | Prefix doubling construction on generalized string `DocA#DocB$` using custom sorting on primitive arrays. |
| **Document Similarity** | **Kasai's Algorithm** | $O(n)$ | $O(n)$ | Computes Longest Common Prefix (LCP) array in linear time using inverted suffix rank array. |
| **Network Flow** | **Edmonds-Karp** | $O(VE^2)$ | $O(V + E)$ | BFS shortest augmenting paths in residual capacity network with step-by-step path history. |
| **Network Flow** | **Dinic's Algorithm** | $O(V^2E)$ | $O(V + E)$ | Level graph constructed via BFS followed by blocking flows pushed via DFS with pointer pruning. |
| **Project Scheduling** | **Vertex Cover 2-Approximation** | $O(V + E)$ | $O(V + E)$ | Maximal Matching greedy independent edge selection with guaranteed approximation factor $\le 2.0\times |OPT|$. |
| **Randomized Testing** | **Miller-Rabin Primality** | $O(k \log^3 n)$ | $O(\log n)$ | Probabilistic test decomposing $n-1 = 2^s \cdot d$; tests $k$ independent random Fermat bases with error bound $\le (1/4)^k$. |

---

## 📁 4. Project Directory Structure
```text
texthack/
├── backend/
│   ├── pom.xml
│   └── src/
│       ├── main/
│       │   ├── java/com/texthack/
│       │   │   ├── TexthackApplication.java
│       │   │   ├── algorithm/
│       │   │   │   ├── string/          (KMP, ZAlgorithm, RabinKarp)
│       │   │   │   ├── dp/              (Levenshtein, DamerauLevenshtein)
│       │   │   │   ├── suffix/          (SuffixArray, KasaiLCP, DocumentSimilarity)
│       │   │   │   ├── flow/            (CustomFlowGraph, EdmondsKarp, Dinic)
│       │   │   │   ├── approximation/   (VertexCoverApproximation)
│       │   │   │   └── randomized/      (MillerRabin)
│       │   │   ├── datastructure/       (CustomArrayList, CustomHashTable, CustomQueue, CustomStack, CustomLinkedList, CustomTrie)
│       │   │   ├── indexing/            (InvertedIndex)
│       │   │   ├── preprocessing/       (TextPreprocessor)
│       │   │   ├── model/               (Document)
│       │   │   ├── service/             (CorpusService)
│       │   │   ├── controller/          (SearchController, SimilarityController, FlowController, SchedulingController, PrimalityController, CorpusController, AlgorithmsController)
│       │   │   └── dto/                 (Request & Response DTOs)
│       │   └── resources/
│       │       └── application.properties
│       └── test/
│           └── java/com/texthack/
│               └── AlgorithmEngineTest.java
│
└── frontend/
    ├── package.json
    ├── vite.config.ts
    ├── tailwind.config.js
    ├── postcss.config.js
    ├── index.html
    └── src/
        ├── App.tsx
        ├── main.tsx
        ├── index.css
        └── pages/
            ├── Dashboard.tsx
            ├── PatternSearch.tsx
            ├── FuzzySearch.tsx
            ├── DocumentSimilarity.tsx
            ├── NetworkFlow.tsx
            ├── ProjectScheduling.tsx
            ├── PrimeTesting.tsx
            ├── Corpus.tsx
            ├── AlgorithmExplorer.tsx
            └── AboutProject.tsx
```

---

## ⚡ 5. Setup & Execution Instructions

### Prerequisites:
* **Java 17+** (JDK)
* **Maven 3.8+**
* **Node.js 18+** & **npm**

### Step 1: Start Backend (Spring Boot REST API)
```bash
cd backend
mvn clean compile
mvn spring-boot:run
```
* Backend runs on: `http://localhost:8080`

### Step 2: Run Automated Unit Tests
```bash
cd backend
mvn test
```
* Verifies all custom data structures, string algorithms, dynamic programming matrices, suffix arrays, flow algorithms, and randomized primality tests.

### Step 3: Start Frontend (React + TypeScript Dashboard)
```bash
cd frontend
npm install --legacy-peer-deps
npm run dev
```
* Frontend runs on: `http://localhost:5173`

---

## 📡 6. REST API Endpoints

| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `GET` | `/api/dashboard/stats` | Returns real-time corpus telemetry (documents, unique terms, size, searches). |
| `POST` | `/api/search/pattern` | Runs KMP, Z-Algorithm, or Rabin-Karp on text or corpus. |
| `POST` | `/api/search/fuzzy` | Runs Levenshtein / Damerau-Levenshtein; returns DP matrix and suggestions. |
| `POST` | `/api/search/inverted` | Queries inverted index postings (Document IDs & token positions). |
| `POST` | `/api/similarity` | Suffix Array + Kasai LCP cross-document similarity and LCS extraction. |
| `POST` | `/api/flow/max` | Edmonds-Karp and Dinic max-flow, augmenting steps, and min-cut partitions. |
| `POST` | `/api/scheduling/vertex-cover` | Vertex Cover 2-Approximation via Maximal Matching with exact comparison. |
| `POST` | `/api/primality/miller-rabin` | Probabilistic Miller-Rabin primality test with random witness bases. |
| `GET` | `/api/corpus` | Lists all documents with metadata (size, word count, language). |
| `POST` | `/api/corpus/reindex` | Triggers complete inverted index rebuilding. |
| `GET` | `/api/algorithms` | Returns full theoretical specifications and complexities for all 11 algorithms. |

---

## 🎓 University Viva Checklist
1. **How does KMP avoid backtracking?** Explain the LPS array computation and how mismatched characters jump to `lps[j-1]`.
2. **Why Wagner-Fischer DP over Naive Recursion?** Naive edit distance takes exponential $O(3^{n+m})$ time; dynamic programming solves it in $O(mn)$ using overlapping subproblems.
3. **How is Document Similarity derived using Suffix Arrays?** By concatenating $DocA + \# + DocB + \$$ and running Kasai's algorithm, cross-boundary suffixes reveal maximal shared substrings via LCP heights.
4. **Why is Vertex Cover 2-Approximation guaranteed within factor 2?** Every edge in a maximal matching is disjoint. Any vertex cover must cover each matched edge by picking at least one endpoint ($|OPT| \ge |M|$). Picking both endpoints gives $|C| = 2|M| \le 2|OPT|$.
5. **Why can't Miller-Rabin declare 100% prime?** It is a Monte Carlo randomized algorithm. Passing $k$ rounds guarantees error probability $\le (1/4)^k$, but only testing with deterministic bases for specific bounds provides a formal proof.
