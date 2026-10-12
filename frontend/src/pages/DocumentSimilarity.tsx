import React, { useState } from 'react';
import axios from 'axios';
import { GitCompare, Clock, Cpu, CheckCircle2, ListOrdered } from 'lucide-react';

export default function DocumentSimilarity() {
  const [customTextA, setCustomTextA] = useState('An algorithm is a finite sequence of rigorous instructions, typically used to solve a class of specific problems or to perform a computation. Algorithms are used as specifications for calculations.');
  const [customTextB, setCustomTextB] = useState('An algorithm is a finite sequence of instructions used to solve specific problems or to perform calculations and data processing.');
  const [result, setResult] = useState<any>(null);
  const [loading, setLoading] = useState(false);
  const [showArrays, setShowArrays] = useState(false);

  const handleCompare = async () => {
    if (!customTextA.trim() || !customTextB.trim()) return;
    setLoading(true);
    try {
      const res = await axios.post('http://localhost:8080/api/similarity', {
        textA: customTextA,
        textB: customTextB
      });
      setResult(res.data);
    } catch (err) {
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="p-8 max-w-7xl mx-auto space-y-8">
      {/* Header */}
      <div className="border-b border-slate-800 pb-6">
        <h1 className="text-3xl font-bold text-slate-100 flex items-center gap-3">
          <GitCompare className="w-8 h-8 text-cyan-400" />
          Document Similarity Lab
        </h1>
        <p className="text-slate-400 mt-2">
          Compare two text documents using Generalized Suffix Arrays (Prefix Doubling) and Kasai's Longest Common Prefix (LCP) algorithm.
        </p>
      </div>

      {/* Two Text Inputs */}
      <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
        {/* Document A input */}
        <div className="bg-slate-900 p-6 rounded-2xl border border-slate-800 shadow-xl space-y-3">
          <h2 className="text-sm font-bold uppercase tracking-wider text-slate-300">
            Document A
          </h2>
          <textarea
            rows={6}
            value={customTextA}
            onChange={e => setCustomTextA(e.target.value)}
            placeholder="Enter text for Document A..."
            className="w-full bg-slate-950 border border-slate-800 rounded-xl p-3 text-slate-300 text-sm focus:outline-none focus:border-cyan-500 leading-relaxed resize-none"
          />
        </div>

        {/* Document B input */}
        <div className="bg-slate-900 p-6 rounded-2xl border border-slate-800 shadow-xl space-y-3">
          <h2 className="text-sm font-bold uppercase tracking-wider text-slate-300">
            Document B
          </h2>
          <textarea
            rows={6}
            value={customTextB}
            onChange={e => setCustomTextB(e.target.value)}
            placeholder="Enter text for Document B..."
            className="w-full bg-slate-950 border border-slate-800 rounded-xl p-3 text-slate-300 text-sm focus:outline-none focus:border-cyan-500 leading-relaxed resize-none"
          />
        </div>
      </div>

      {/* Action Button */}
      <div className="flex justify-center">
        <button
          onClick={handleCompare}
          disabled={loading || !customTextA.trim() || !customTextB.trim()}
          className="bg-cyan-600 hover:bg-cyan-500 disabled:opacity-50 text-slate-950 font-bold px-8 py-3 rounded-xl transition-colors shadow-lg shadow-cyan-950 flex items-center gap-2"
        >
          <Cpu className="w-5 h-5" />
          {loading ? 'Analyzing Suffix Structures...' : 'Compute Suffix Array & Kasai LCP Similarity'}
        </button>
      </div>

      {/* Results Section */}
      {result && (
        <div className="space-y-6">
          {/* Key Metrics */}
          <div className="grid grid-cols-1 md:grid-cols-4 gap-4">
            <div className="bg-slate-900 p-6 rounded-2xl border border-slate-800 shadow-lg">
              <span className="text-xs font-semibold text-slate-400 uppercase">Similarity Score</span>
              <div className="text-4xl font-extrabold text-transparent bg-clip-text bg-gradient-to-r from-cyan-400 to-blue-400 mt-2">
                {result.similarityScore}%
              </div>
              <p className="text-[11px] text-slate-500 mt-1">Common substring overlap coverage</p>
            </div>

            <div className="bg-slate-900 p-6 rounded-2xl border border-slate-800 shadow-lg">
              <span className="text-xs font-semibold text-slate-400 uppercase">Longest Common Substring</span>
              <div className="text-2xl font-bold text-slate-100 mt-2 truncate">
                {result.longestCommonSubstringLength} <span className="text-sm text-slate-500 font-normal">chars</span>
              </div>
              <p className="text-[11px] text-slate-400 mt-1 truncate">"{result.longestCommonSubstring}"</p>
            </div>

            <div className="bg-slate-900 p-6 rounded-2xl border border-slate-800 shadow-lg">
              <span className="text-xs font-semibold text-slate-400 uppercase">Execution Time</span>
              <div className="text-2xl font-bold text-slate-100 mt-2">
                {(result.executionTimeNs / 1000000).toFixed(3)} <span className="text-sm text-slate-500 font-normal">ms</span>
              </div>
              <p className="text-[11px] text-emerald-400 mt-1">Suffix Array + Kasai O(n)</p>
            </div>

            <div className="bg-slate-900 p-6 rounded-2xl border border-slate-800 shadow-lg">
              <span className="text-xs font-semibold text-slate-400 uppercase">Algorithm Stack</span>
              <div className="text-sm font-semibold text-cyan-400 mt-2">
                SA: O(n log² n)
              </div>
              <div className="text-sm font-semibold text-blue-400">
                Kasai LCP: O(n)
              </div>
              <p className="text-[10px] text-slate-500 mt-1">Zero java.util collections used</p>
            </div>
          </div>

          {/* Explanation Banner */}
          <div className="bg-slate-900/80 border border-slate-800 p-4 rounded-xl text-slate-300 text-sm leading-relaxed flex items-start gap-3 shadow-md">
            <CheckCircle2 className="w-5 h-5 text-cyan-400 shrink-0 mt-0.5" />
            <div>
              <strong className="text-slate-100">Metric Formulation: </strong>
              {result.explanation}
            </div>
          </div>

          {/* Common Substrings Table */}
          <div className="bg-slate-900 rounded-2xl border border-slate-800 p-6 space-y-4 shadow-lg">
            <h3 className="text-sm font-bold uppercase tracking-wider text-slate-300">
              Extracted Common Substrings (via LCP Cross-Boundary Peaks)
            </h3>
            {result.commonSubstrings && result.commonSubstrings.length > 0 ? (
              <div className="overflow-x-auto">
                <table className="w-full text-left text-sm text-slate-300">
                  <thead className="bg-slate-950 text-xs uppercase text-slate-500 border-b border-slate-800">
                    <tr>
                      <th className="p-3">#</th>
                      <th className="p-3">Shared Substring Text</th>
                      <th className="p-3">Match Length</th>
                      <th className="p-3">Doc A Pos</th>
                      <th className="p-3">Doc B Pos</th>
                    </tr>
                  </thead>
                  <tbody className="divide-y divide-slate-800">
                    {result.commonSubstrings.map((sub: any, i: number) => (
                      <tr key={i} className="hover:bg-slate-800/40">
                        <td className="p-3 text-slate-500 font-mono text-xs">{i + 1}</td>
                        <td className="p-3 font-mono text-cyan-300 font-medium">"{sub.substring}"</td>
                        <td className="p-3 font-semibold text-slate-200">{sub.length}</td>
                        <td className="p-3 text-slate-400 font-mono text-xs">{sub.posA}</td>
                        <td className="p-3 text-slate-400 font-mono text-xs">{sub.posB}</td>
                      </tr>
                    ))}
                  </tbody>
                </table>
              </div>
            ) : (
              <p className="text-slate-500 text-sm">No significant common substrings detected.</p>
            )}
          </div>

          {/* Interactive Toggle for Suffix Array & LCP Array display */}
          <div className="bg-slate-900 rounded-2xl border border-slate-800 p-6 space-y-4 shadow-lg">
            <div className="flex items-center justify-between">
              <div>
                <h3 className="text-sm font-bold uppercase tracking-wider text-slate-300">
                  Suffix Array & Kasai LCP Array Visualizer
                </h3>
                <p className="text-xs text-slate-500">
                  Educational inspection of the computed SA permutation and LCP heights.
                </p>
              </div>
              <button
                onClick={() => setShowArrays(!showArrays)}
                className="flex items-center gap-2 bg-slate-800 hover:bg-slate-700 text-slate-200 text-xs font-semibold px-3 py-1.5 rounded-lg border border-slate-700"
              >
                <ListOrdered className="w-4 h-4 text-cyan-400" />
                {showArrays ? 'Hide Arrays' : 'View Full Suffix & LCP Arrays'}
              </button>
            </div>

            {showArrays && result.suffixArray && (
              <div className="max-h-72 overflow-y-auto border border-slate-800 rounded-lg">
                <table className="w-full text-left text-xs font-mono text-slate-300">
                  <thead className="bg-slate-950 text-slate-500 border-b border-slate-800 sticky top-0">
                    <tr>
                      <th className="p-2.5">i</th>
                      <th className="p-2.5">Suffix Array SA[i]</th>
                      <th className="p-2.5">Kasai LCP[i]</th>
                    </tr>
                  </thead>
                  <tbody className="divide-y divide-slate-800/60 bg-slate-950/40">
                    {result.suffixArray.slice(0, 50).map((saVal: number, idx: number) => (
                      <tr key={idx} className="hover:bg-slate-800/40">
                        <td className="p-2 text-slate-500">{idx}</td>
                        <td className="p-2 text-cyan-400">{saVal}</td>
                        <td className="p-2 text-emerald-400 font-bold">{result.lcpArray ? result.lcpArray[idx] : 0}</td>
                      </tr>
                    ))}
                  </tbody>
                </table>
                {result.suffixArray.length > 50 && (
                  <div className="p-2 text-center text-xs text-slate-500 bg-slate-950 border-t border-slate-800">
                    Showing first 50 of {result.suffixArray.length} suffixes...
                  </div>
                )}
              </div>
            )}
          </div>
        </div>
      )}
    </div>
  );
}
