package com.texthack.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class AlgorithmsController {

    public static class AlgorithmInfo {
        public String name;
        public String category;
        public String problemSolved;
        public String input;
        public String output;
        public String coreIdea;
        public String timeComplexity;
        public String spaceComplexity;
        public String advantages;
        public String limitations;
        public String texthackUsage;

        public AlgorithmInfo(String name, String category, String problemSolved, String input, String output,
                             String coreIdea, String timeComplexity, String spaceComplexity,
                             String advantages, String limitations, String texthackUsage) {
            this.name = name;
            this.category = category;
            this.problemSolved = problemSolved;
            this.input = input;
            this.output = output;
            this.coreIdea = coreIdea;
            this.timeComplexity = timeComplexity;
            this.spaceComplexity = spaceComplexity;
            this.advantages = advantages;
            this.limitations = limitations;
            this.texthackUsage = texthackUsage;
        }
    }

    @GetMapping("/algorithms")
    public List<AlgorithmInfo> getAlgorithms() {
        List<AlgorithmInfo> list = new ArrayList<>();

        list.add(new AlgorithmInfo(
            "Knuth-Morris-Pratt (KMP)",
            "String Algorithms",
            "Exact Pattern Matching",
            "Text string T (len n), Pattern string P (len m)",
            "All starting indices of P in T",
            "Precomputes Longest Proper Prefix which is also Suffix (LPS) array to skip redundant comparisons without backtracking in text.",
            "O(n + m)",
            "O(m)",
            "Optimal worst-case linear time, never backtracks in input text stream.",
            "Requires preprocessing space O(m) and state transition handling.",
            "Pattern Search engine core matching."
        ));

        list.add(new AlgorithmInfo(
            "Z Algorithm",
            "String Algorithms",
            "Exact Pattern Matching",
            "String S (pattern + separator + text)",
            "Z-array where Z[i] is the length of longest substring starting at i matching prefix of S",
            "Maintains an active match interval [L, R] to compute prefix matches in linear time using previously computed values.",
            "O(n + m)",
            "O(n + m)",
            "Extremely clean linear pattern matching, versatile for substring palindromes and periodicity.",
            "Requires building concatenated string with special delimiter.",
            "Alternative pattern matching engine in search lab."
        ));

        list.add(new AlgorithmInfo(
            "Rabin-Karp",
            "String Algorithms",
            "Exact Pattern Matching",
            "Text T, Pattern P",
            "All occurrences of P in T",
            "Uses polynomial rolling hash in O(1) per window shift, followed by explicit character verification upon hash match.",
            "Average: O(n + m), Worst: O(n * m)",
            "O(1)",
            "Easily generalizes to multiple pattern search and 2D pattern matching.",
            "Worst-case O(nm) on pathological hash collisions.",
            "Fast sliding window search in corpus documents."
        ));

        list.add(new AlgorithmInfo(
            "Wagner-Fischer Levenshtein",
            "Dynamic Programming",
            "Fuzzy String Matching / Edit Distance",
            "Two strings s1 and s2",
            "Minimum number of insertions, deletions, substitutions to transform s1 into s2",
            "Constructs a (m+1)x(n+1) dynamic programming table tracking optimal substructure of prefix transformations.",
            "O(m * n)",
            "O(m * n) (matrix display) or O(min(m,n))",
            "Globally optimal metric distance satisfying triangle inequality.",
            "Quadratic in string lengths.",
            "Fuzzy search query suggestion and spelling correction."
        ));

        list.add(new AlgorithmInfo(
            "Damerau-Levenshtein",
            "Dynamic Programming",
            "Fuzzy String Matching with Transpositions",
            "Two strings s1 and s2",
            "Edit distance allowing insertions, deletions, substitutions, and adjacent transpositions",
            "Extends Wagner-Fischer recursion with a 2-step lookback condition when adjacent characters are swapped.",
            "O(m * n)",
            "O(m * n)",
            "Accurately captures common human typing slip-ups (e.g. 'teh' -> 'the').",
            "Slightly higher constant factor overhead than standard Levenshtein.",
            "Advanced spelling suggestion in Fuzzy Search."
        ));

        list.add(new AlgorithmInfo(
            "Suffix Array (Prefix Doubling)",
            "Suffix Structures",
            "Full-Text Indexing & Substring Analysis",
            "String S of length n",
            "Lexicographically sorted array of all suffix indices",
            "Prefix doubling algorithm sorts 2^k length prefixes iteratively using previous rank pairs.",
            "O(n log^2 n) practical construction",
            "O(n)",
            "Much more space-efficient than suffix trees; cache-friendly representation.",
            "O(n log^2 n) construction vs complex linear-time SA-IS.",
            "Document similarity foundation and full-text index."
        ));

        list.add(new AlgorithmInfo(
            "Kasai's Algorithm",
            "Suffix Structures",
            "Longest Common Prefix (LCP) Array",
            "String S and its Suffix Array SA",
            "LCP array where LCP[i] = length of common prefix between SA[i] and SA[i-1]",
            "Uses the key observation that LCP of successive text positions decreases by at most 1.",
            "O(n)",
            "O(n)",
            "Linear time; transforms suffix array into full equivalent power of a suffix tree.",
            "Requires suffix array and rank array pre-computed.",
            "Document similarity common-substring overlap metric."
        ));

        list.add(new AlgorithmInfo(
            "Edmonds-Karp",
            "Network Flow",
            "Maximum Network Flow",
            "Flow network (V, E) with capacities, source s, sink t",
            "Maximum flow value and residual flow graph",
            "Specialization of Ford-Fulkerson that always selects the shortest augmenting path via BFS.",
            "O(V * E^2)",
            "O(V + E)",
            "Polynomial bound independent of edge capacity magnitudes; simple and reliable.",
            "Slower than Dinic or push-relabel on dense networks.",
            "Interactive network flow lab with step-by-step path inspection."
        ));

        list.add(new AlgorithmInfo(
            "Dinic's Algorithm",
            "Network Flow",
            "Maximum Network Flow",
            "Flow network (V, E) with capacities, source s, sink t",
            "Maximum flow value and residual capacities",
            "BFS builds level graph of shortest distances; DFS pushes blocking flows along admissible edges.",
            "O(V^2 * E) general, O(E * sqrt(V)) unit networks",
            "O(V + E)",
            "Extremely fast in practice; exceptional performance on bipartite matching graphs.",
            "More implementation complexity than Edmonds-Karp.",
            "High-performance flow solver in Algorithm Lab."
        ));

        list.add(new AlgorithmInfo(
            "Vertex Cover 2-Approximation",
            "Approximation Algorithms",
            "Minimum Vertex Cover (NP-Hard)",
            "Undirected graph G = (V, E)",
            "Subset of vertices C covering every edge, |C| <= 2 * |OPT|",
            "Computes a maximal matching M by greedily picking independent edges and including both endpoints in C.",
            "O(V + E)",
            "O(V + E)",
            "Guaranteed 2-approximation factor; linear time execution.",
            "Does not find optimal minimum vertex cover in general.",
            "Project Scheduling and NP-Complete approximation demo."
        ));

        list.add(new AlgorithmInfo(
            "Miller-Rabin Primality Test",
            "Randomized Algorithms",
            "Primality Testing for Large Integers",
            "Integer n, number of rounds k",
            "Probably Prime or Definitely Composite",
            "Decomposes n-1 = 2^s * d. Checks non-trivial square roots of 1 modulo n using random Fermat bases.",
            "O(k * log^3 n)",
            "O(log n)",
            "Handles arbitrarily large integers rapidly; false positive probability < (1/4)^k.",
            "Probabilistic (Monte Carlo): cannot certify primality with 100% certainty without deterministic basis sets.",
            "Cryptographic key generation and prime verification lab."
        ));

        return list;
    }
}
