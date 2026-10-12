import React from 'react';
import { Info, Code2, Server, Layout, CheckCircle, GraduationCap, Cpu, Layers } from 'lucide-react';

export default function AboutProject() {
  return (
    <div className="p-8 max-w-5xl mx-auto space-y-8">
      <div className="border-b border-slate-800 pb-6">
        <div className="flex items-center gap-2 text-cyan-400 text-xs font-bold uppercase tracking-wider mb-1">
          <GraduationCap className="w-4 h-4" />
          Data Structures and Algorithms-3 (DSA-3) Capstone Project
        </div>
        <h1 className="text-3xl font-bold text-slate-100">
          Mini Search Engine / Information-Retrieval System – TextHack
        </h1>
        <p className="text-slate-400 mt-2">
          An educational advanced information retrieval and algorithmic analytics platform demonstrating real mathematical and computational solutions without Java collection wrappers.
        </p>
      </div>

      <div className="bg-gradient-to-r from-cyan-950/40 via-blue-950/40 to-slate-900 border border-cyan-800/40 p-6 rounded-2xl space-y-2 text-sm text-cyan-200">
        <h2 className="text-lg font-bold text-white flex items-center gap-2">
          <CheckCircle className="w-5 h-5 text-cyan-400" />
          Strict Academic Compliance: Zero java.util Collections in Engine
        </h2>
        <p className="text-xs text-slate-300 leading-relaxed">
          In strict accordance with the academic requirements of university course DSA-3, the entire core algorithm engine is engineered from scratch without relying on <code className="text-cyan-300 bg-slate-950 px-1 py-0.5 rounded">java.util.*</code> collections.
          All dynamic arrays (<code className="text-cyan-300">CustomArrayList</code>), queues (<code className="text-cyan-300">CustomQueue</code>), stacks (<code className="text-cyan-300">CustomStack</code>), hash tables (<code className="text-cyan-300">CustomHashTable</code>), linked lists (<code className="text-cyan-300">CustomLinkedList</code>), and suffix sort algorithms are manually implemented.
        </p>
      </div>

      {/* Syllabus Mapping Grid */}
      <div className="space-y-4">
        <h2 className="text-lg font-bold text-slate-100 flex items-center gap-2">
          <Layers className="w-5 h-5 text-cyan-400" />
          Syllabus & Query Class Mapping
        </h2>

        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4 text-xs">
          <div className="bg-slate-900 p-4 rounded-xl border border-slate-800 space-y-1.5">
            <span className="font-bold text-cyan-400 uppercase tracking-wider block">1. String Algorithms</span>
            <div className="text-slate-200 font-semibold text-sm">Pattern Search Engine</div>
            <p className="text-slate-400">KMP (LPS table), Z-Algorithm (Z-array), Rabin-Karp (rolling polynomial hash with collision verification).</p>
          </div>

          <div className="bg-slate-900 p-4 rounded-xl border border-slate-800 space-y-1.5">
            <span className="font-bold text-cyan-400 uppercase tracking-wider block">2. Dynamic Programming</span>
            <div className="text-slate-200 font-semibold text-sm">Fuzzy Spelling Search</div>
            <p className="text-slate-400">Wagner-Fischer Levenshtein distance & Damerau-Levenshtein with full DP matrix visualization.</p>
          </div>

          <div className="bg-slate-900 p-4 rounded-xl border border-slate-800 space-y-1.5">
            <span className="font-bold text-cyan-400 uppercase tracking-wider block">3. Suffix Structures</span>
            <div className="text-slate-200 font-semibold text-sm">Document Similarity</div>
            <p className="text-slate-400">Suffix Array via O(n log² n) prefix doubling + Kasai's O(n) LCP algorithm deriving academic common substring coverage.</p>
          </div>

          <div className="bg-slate-900 p-4 rounded-xl border border-slate-800 space-y-1.5">
            <span className="font-bold text-cyan-400 uppercase tracking-wider block">4. Network Flow</span>
            <div className="text-slate-200 font-semibold text-sm">Max-Flow / Min-Cut Lab</div>
            <p className="text-slate-400">Edmonds-Karp (BFS augmenting paths) and Dinic's blocking flow algorithm with Max-Flow Min-Cut theorem partitions.</p>
          </div>

          <div className="bg-slate-900 p-4 rounded-xl border border-slate-800 space-y-1.5">
            <span className="font-bold text-cyan-400 uppercase tracking-wider block">5. NP-Completeness / Approx</span>
            <div className="text-slate-200 font-semibold text-sm">Project Scheduling</div>
            <p className="text-slate-400">Vertex Cover 2-Approximation using maximal matching with brute-force comparison on exponential small instances.</p>
          </div>

          <div className="bg-slate-900 p-4 rounded-xl border border-slate-800 space-y-1.5">
            <span className="font-bold text-cyan-400 uppercase tracking-wider block">6. Randomized Algorithms</span>
            <div className="text-slate-200 font-semibold text-sm">Prime Testing Lab</div>
            <p className="text-slate-400">Miller-Rabin probabilistic test on large integers with random witness bases and (1/4)^k error bound calculations.</p>
          </div>
        </div>
      </div>

      {/* Full-Stack Architecture */}
      <div className="space-y-4">
        <h2 className="text-lg font-bold text-slate-100 flex items-center gap-2">
          <Cpu className="w-5 h-5 text-cyan-400" />
          Full-Stack Architectural Flow
        </h2>

        <div className="bg-slate-900 p-6 rounded-xl border border-slate-800 font-mono text-xs text-slate-300 leading-relaxed overflow-x-auto">
          <pre>{`                    TEXTHACK CLIENT (React 18 + TypeScript + Tailwind)
                                         |
                                         | HTTP / JSON REST APIs (port 8080)
                                         v
                    SPRING BOOT BACKEND CONTROLLER LAYER
                                         |
                                         v
                    TEXTHACK CORE ENGINE (Zero java.util.*)
     +-----------------------------------+-----------------------------------+
     |                                   |                                   |
  CUSTOM DATA STRUCTURES           ALGORITHMIC MODULES               CORPUS & INDEXING
  - CustomArrayList                 - KMP & Z-Algorithm              - TextPreprocessor
  - CustomQueue & CustomStack       - Rabin-Karp Polynomial Hash       (Unicode Normalization)
  - CustomHashTable (Rehashed)      - Levenshtein & Damerau DP       - Multilingual Corpus
  - CustomLinkedList & CustomTrie   - Suffix Array & Kasai LCP         (Hindi, Telugu, etc.)
  - CustomFlowGraph                 - Edmonds-Karp & Dinic Flow      - Inverted Search Index
                                    - Vertex Cover 2-Approx            (Postings: DocID+Positions)
                                    - Miller-Rabin Primality`}</pre>
        </div>
      </div>

      <div className="pt-4 border-t border-slate-800 text-xs text-slate-500 text-center">
        Developed as part of Data Structures and Algorithms-3 (DSA-3) Project Demonstration.
      </div>
    </div>
  );
}
