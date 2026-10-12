import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { Link } from 'react-router-dom';
import { Search, SpellCheck, GitCompare, ArrowRight, ShieldCheck } from 'lucide-react';

export default function Dashboard() {
  const [stats, setStats] = useState<any>(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    axios.get('http://localhost:8080/api/dashboard/stats')
      .then(res => setStats(res.data))
      .catch(err => {
        console.error("Backend telemetry error:", err);
      })
      .finally(() => setLoading(false));
  }, []);

  return (
    <div className="p-8 max-w-6xl mx-auto space-y-10">
      {/* Hero Section */}
      <div className="text-center py-14 px-8 bg-slate-900 rounded-3xl border border-slate-800 shadow-2xl relative overflow-hidden">
        <div className="absolute inset-0 bg-gradient-to-br from-cyan-950/30 via-slate-900 to-blue-950/30 pointer-events-none"></div>
        <div className="relative z-10 space-y-4 max-w-3xl mx-auto">
          <div className="inline-flex items-center gap-2 px-3 py-1 rounded-full bg-cyan-950 text-cyan-400 border border-cyan-800 text-xs font-semibold uppercase tracking-wider">
            <ShieldCheck className="w-3.5 h-3.5" />
            University DSA-3 Search Engine
          </div>
          <h1 className="text-5xl font-black tracking-tight text-transparent bg-clip-text bg-gradient-to-r from-cyan-400 via-blue-400 to-indigo-400">
            TEXT HACK
          </h1>
          <p className="text-lg text-slate-200 font-medium">
            Mini Search Engine & Information Retrieval System
          </p>
          <p className="text-sm text-slate-400 italic">
            "Search. Analyze. Understand Algorithms."
          </p>
          <p className="text-xs text-slate-400 max-w-xl mx-auto leading-relaxed pt-2">
            TextHack is an advanced information retrieval platform demonstrating real algorithm processing in pure Java—strictly without <code className="text-cyan-300">java.util.*</code> collections inside the core engine.
          </p>

          <div className="flex flex-wrap items-center justify-center gap-4 pt-4">
            <Link
              to="/search/pattern"
              className="bg-cyan-500 hover:bg-cyan-400 text-slate-950 font-bold px-6 py-2.5 rounded-xl text-sm transition-all shadow-lg shadow-cyan-950 flex items-center gap-2"
            >
              Start Searching <ArrowRight className="w-4 h-4" />
            </Link>
            <Link
              to="/about"
              className="bg-slate-800 hover:bg-slate-700 text-slate-200 font-semibold px-6 py-2.5 rounded-xl text-sm transition-all border border-slate-700"
            >
              About Project
            </Link>
          </div>
        </div>
      </div>

      {/* Real Statistics from Backend */}
      <div>
        <h2 className="text-xs font-bold uppercase tracking-wider text-slate-500 mb-3">
          Live Backend Engine Telemetry
        </h2>
        <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-4">
          <div className="bg-slate-900 p-5 rounded-2xl border border-slate-800 shadow-lg">
            <span className="text-xs font-semibold text-slate-400 uppercase">Documents Indexed</span>
            <div className="text-3xl font-extrabold text-slate-100 mt-2">
              {stats ? stats.documentsIndexed : (loading ? '...' : '0')}
            </div>
            <p className="text-[10px] text-cyan-400 mt-1">Multilingual corpus</p>
          </div>

          <div className="bg-slate-900 p-5 rounded-2xl border border-slate-800 shadow-lg">
            <span className="text-xs font-semibold text-slate-400 uppercase">Unique Terms</span>
            <div className="text-3xl font-extrabold text-cyan-400 mt-2">
              {stats ? stats.uniqueTerms : (loading ? '...' : '0')}
            </div>
            <p className="text-[10px] text-slate-500 mt-1">Inverted Index Hash Table</p>
          </div>

          <div className="bg-slate-900 p-5 rounded-2xl border border-slate-800 shadow-lg">
            <span className="text-xs font-semibold text-slate-400 uppercase">Corpus Size</span>
            <div className="text-2xl font-bold text-slate-100 mt-2">
              {stats ? (stats.totalCorpusSizeBytes / 1024).toFixed(1) : (loading ? '...' : '0')} <span className="text-sm font-normal text-slate-500">KB</span>
            </div>
            <p className="text-[10px] text-slate-500 mt-1">Unicode text storage</p>
          </div>

          <div className="bg-slate-900 p-5 rounded-2xl border border-slate-800 shadow-lg">
            <span className="text-xs font-semibold text-slate-400 uppercase">Searches Run</span>
            <div className="text-3xl font-extrabold text-blue-400 mt-2">
              {stats ? stats.searchesPerformed : (loading ? '...' : '0')}
            </div>
            <p className="text-[10px] text-slate-500 mt-1">Real-time session count</p>
          </div>
        </div>
      </div>

      {/* Feature Cards Grid: Pattern Search, Fuzzy Search, Document Similarity */}
      <div className="space-y-4">
        <h2 className="text-xs font-bold uppercase tracking-wider text-slate-500">
          Core Search Engines
        </h2>

        <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
          <Link
            to="/search/pattern"
            className="group bg-slate-900 hover:bg-slate-800/80 p-6 rounded-2xl border border-slate-800 hover:border-cyan-500/50 transition-all shadow-lg hover:shadow-cyan-950/20 flex flex-col justify-between"
          >
            <div className="space-y-3">
              <div className="w-10 h-10 rounded-xl bg-cyan-950/60 border border-cyan-800/60 flex items-center justify-center text-cyan-400 group-hover:scale-110 transition-transform">
                <Search className="w-5 h-5" />
              </div>
              <h3 className="text-lg font-bold text-slate-100 group-hover:text-cyan-400 transition-colors">
                Pattern Search
              </h3>
              <p className="text-xs text-slate-400 leading-relaxed">
                Exact string matching with KMP, Z-Algorithm, and Rabin-Karp polynomial rolling hash. Visualizes LPS tables and match indices.
              </p>
            </div>
            <div className="mt-4 pt-3 border-t border-slate-800/80 flex items-center justify-between text-xs font-semibold text-cyan-400">
              <span>Open Pattern Search</span>
              <ArrowRight className="w-4 h-4 group-hover:translate-x-1 transition-transform" />
            </div>
          </Link>

          <Link
            to="/search/fuzzy"
            className="group bg-slate-900 hover:bg-slate-800/80 p-6 rounded-2xl border border-slate-800 hover:border-cyan-500/50 transition-all shadow-lg hover:shadow-cyan-950/20 flex flex-col justify-between"
          >
            <div className="space-y-3">
              <div className="w-10 h-10 rounded-xl bg-blue-950/60 border border-blue-800/60 flex items-center justify-center text-blue-400 group-hover:scale-110 transition-transform">
                <SpellCheck className="w-5 h-5" />
              </div>
              <h3 className="text-lg font-bold text-slate-100 group-hover:text-cyan-400 transition-colors">
                Fuzzy Search
              </h3>
              <p className="text-xs text-slate-400 leading-relaxed">
                Spelling correction and edit distance using Wagner-Fischer Dynamic Programming and Damerau-Levenshtein transposition matrix.
              </p>
            </div>
            <div className="mt-4 pt-3 border-t border-slate-800/80 flex items-center justify-between text-xs font-semibold text-cyan-400">
              <span>Open Fuzzy Search</span>
              <ArrowRight className="w-4 h-4 group-hover:translate-x-1 transition-transform" />
            </div>
          </Link>

          <Link
            to="/search/similarity"
            className="group bg-slate-900 hover:bg-slate-800/80 p-6 rounded-2xl border border-slate-800 hover:border-cyan-500/50 transition-all shadow-lg hover:shadow-cyan-950/20 flex flex-col justify-between"
          >
            <div className="space-y-3">
              <div className="w-10 h-10 rounded-xl bg-indigo-950/60 border border-indigo-800/60 flex items-center justify-center text-indigo-400 group-hover:scale-110 transition-transform">
                <GitCompare className="w-5 h-5" />
              </div>
              <h3 className="text-lg font-bold text-slate-100 group-hover:text-cyan-400 transition-colors">
                Document Similarity
              </h3>
              <p className="text-xs text-slate-400 leading-relaxed">
                Analyzes shared text patterns via Suffix Array (O(n log² n) prefix doubling) and Kasai's O(n) LCP array, computing common substring coverage.
              </p>
            </div>
            <div className="mt-4 pt-3 border-t border-slate-800/80 flex items-center justify-between text-xs font-semibold text-cyan-400">
              <span>Open Similarity Search</span>
              <ArrowRight className="w-4 h-4 group-hover:translate-x-1 transition-transform" />
            </div>
          </Link>
        </div>
      </div>

      {/* Built With Algorithms Badges */}
      <div className="bg-slate-900 p-6 rounded-2xl border border-slate-800 space-y-4">
        <h3 className="text-xs font-bold uppercase tracking-wider text-slate-400">
          Core Search Algorithms
        </h3>
        <div className="flex flex-wrap gap-2 text-xs font-mono">
          {[
            'KMP', 'Rabin-Karp', 'Z Algorithm', 'Levenshtein DP', 'Damerau-Levenshtein',
            'Suffix Array O(n log² n)', 'Kasai LCP O(n)'
          ].map((alg, i) => (
            <span key={i} className="px-3 py-1 rounded-lg bg-slate-950 text-cyan-300 border border-slate-800 font-medium">
              {alg}
            </span>
          ))}
        </div>
      </div>
    </div>
  );
}
