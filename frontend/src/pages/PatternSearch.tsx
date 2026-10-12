import React, { useState } from 'react';
import axios from 'axios';
import { Search, Clock, Cpu, FileText } from 'lucide-react';

export default function PatternSearch() {
  const [query, setQuery] = useState('algorithm');
  const [text, setText] = useState('An algorithm is a finite sequence of rigorous instructions, typically used to solve a class of specific problems or to perform a computation. Algorithms are used as specifications for performing calculations and data processing. More advanced algorithms can perform automated deductions (referred to as automated reasoning) and use mathematical and logical tests to direct the code execution through various routes.');
  const [algorithm, setAlgorithm] = useState('KMP');
  const [result, setResult] = useState<any>(null);
  const [loading, setLoading] = useState(false);

  const handleSearch = async () => {
    if (!query) return;
    setLoading(true);
    try {
      const res = await axios.post('http://localhost:8080/api/search/pattern', {
        query,
        algorithm,
        text,
        searchCorpus: false
      });
      setResult(res.data);
    } catch (error) {
      console.error(error);
    } finally {
      setLoading(false);
    }
  };

  const highlightMatches = (content: string, matches: number[], patternLength: number) => {
    if (!matches || matches.length === 0) return <span>{content}</span>;

    let elements = [];
    let lastIndex = 0;

    for (let i = 0; i < matches.length; i++) {
      const matchIndex = matches[i];
      if (matchIndex > lastIndex) {
        elements.push(<span key={`text-${i}`}>{content.substring(lastIndex, matchIndex)}</span>);
      }
      elements.push(
        <span key={`match-${i}`} className="bg-cyan-500 text-slate-950 font-bold px-1 rounded">
          {content.substring(matchIndex, matchIndex + patternLength)}
        </span>
      );
      lastIndex = matchIndex + patternLength;
    }

    if (lastIndex < content.length) {
      elements.push(<span key={`text-end`}>{content.substring(lastIndex)}</span>);
    }

    return elements;
  };

  return (
    <div className="p-8 max-w-7xl mx-auto space-y-8">
      <div className="border-b border-slate-800 pb-6">
        <h1 className="text-3xl font-bold text-slate-100 flex items-center gap-3">
          <Search className="w-8 h-8 text-cyan-400" />
          Pattern Search Lab
        </h1>
        <p className="text-slate-400 mt-2">
          Exact string matching comparing Knuth-Morris-Pratt (KMP), Z-Algorithm, and Rabin-Karp polynomial rolling hash.
        </p>
      </div>

      <div className="grid grid-cols-1 lg:grid-cols-3 gap-8">
        {/* Controls */}
        <div className="lg:col-span-1 space-y-6">
          <div className="bg-slate-900 p-6 rounded-xl border border-slate-800 shadow-lg space-y-4">
            <h2 className="text-sm font-bold uppercase tracking-wider text-slate-300">Search Configuration</h2>

            <div>
              <label className="block text-xs font-semibold text-slate-400 uppercase mb-1">Algorithm</label>
              <select
                value={algorithm}
                onChange={e => setAlgorithm(e.target.value)}
                className="w-full bg-slate-950 border border-slate-800 rounded-lg p-2.5 text-sm text-slate-200 focus:outline-none focus:border-cyan-500"
              >
                <option value="KMP">Knuth-Morris-Pratt (KMP)</option>
                <option value="Z">Z Algorithm</option>
                <option value="RABIN_KARP">Rabin-Karp</option>
              </select>
            </div>

            <div>
              <label className="block text-xs font-semibold text-slate-400 uppercase mb-1">Pattern Query</label>
              <input
                type="text"
                value={query}
                onChange={e => setQuery(e.target.value)}
                placeholder="e.g. algorithm"
                className="w-full bg-slate-950 border border-slate-800 rounded-lg p-2.5 text-sm text-slate-200 focus:outline-none focus:border-cyan-500"
              />
            </div>

            <button
              onClick={handleSearch}
              disabled={loading || !query}
              className="w-full bg-cyan-600 hover:bg-cyan-500 disabled:opacity-50 text-slate-950 font-bold py-2.5 px-4 rounded-lg transition-colors shadow-lg shadow-cyan-950 flex items-center justify-center gap-2 mt-4"
            >
              <Search className="w-4 h-4" />
              {loading ? 'Searching...' : 'Run Search'}
            </button>
          </div>

          {/* MEASURED TELEMETRY */}
          {result && (
            <div className="bg-slate-900 p-6 rounded-xl border border-slate-800 shadow-lg space-y-4">
              <h3 className="text-xs font-bold uppercase tracking-wider text-slate-400">Measured Telemetry</h3>

              <div className="grid grid-cols-2 gap-3">
                <div className="bg-slate-950 p-3 rounded-lg border border-slate-800">
                  <div className="flex items-center gap-1.5 text-slate-400 mb-1">
                    <FileText className="w-3.5 h-3.5" /> <span className="text-[11px] uppercase">Matches</span>
                  </div>
                  <div className="text-2xl font-bold text-slate-100">{result.matchCount}</div>
                </div>

                <div className="bg-slate-950 p-3 rounded-lg border border-slate-800">
                  <div className="flex items-center gap-1.5 text-slate-400 mb-1">
                    <Clock className="w-3.5 h-3.5" /> <span className="text-[11px] uppercase">Time</span>
                  </div>
                  <div className="text-xl font-bold text-slate-100">
                    {(result.executionTimeNs / 1000000).toFixed(3)} <span className="text-xs text-slate-500 font-normal">ms</span>
                  </div>
                </div>
              </div>

              <div className="bg-slate-950 p-3.5 rounded-lg border border-slate-800 space-y-1">
                <div className="flex items-center gap-1.5 text-slate-400">
                  <Cpu className="w-3.5 h-3.5" /> <span className="text-[11px] uppercase">Complexity</span>
                </div>
                <div className="text-xs font-mono text-cyan-400 font-bold">
                  {algorithm === 'RABIN_KARP' ? 'Avg: O(n+m), Worst: O(nm)' : 'O(n + m)'}
                </div>
              </div>
            </div>
          )}
        </div>

        {/* Text View & MATCH VISUALIZATION */}
        <div className="lg:col-span-2 space-y-6">
          <div className="bg-slate-900 p-6 rounded-xl border border-slate-800 shadow-lg space-y-3">
            <h2 className="text-sm font-bold uppercase tracking-wider text-slate-300">
              Text Corpus (Simulation)
            </h2>
            <textarea
              rows={5}
              value={text}
              onChange={e => setText(e.target.value)}
              className="w-full bg-slate-950 border border-slate-800 rounded-lg p-3 text-slate-300 text-sm focus:outline-none focus:border-cyan-500 leading-relaxed resize-none"
            />
          </div>

          {/* MATCH VISUALIZATION */}
          {result && (
            <div className="bg-slate-900 p-6 rounded-xl border border-slate-800 shadow-lg space-y-4">
              <h2 className="text-sm font-bold uppercase tracking-wider text-slate-300">
                Match Visualization
              </h2>
              <div className="bg-slate-950 p-4 rounded-lg border border-slate-800 text-slate-300 leading-relaxed font-mono text-sm whitespace-pre-wrap">
                {highlightMatches(text, result.matches, query.length)}
              </div>
              {result.matches?.length > 0 && (
                <div className="pt-2 border-t border-slate-800/80">
                  <h3 className="text-xs text-slate-500 uppercase font-semibold mb-2">Match Indices</h3>
                  <div className="flex flex-wrap gap-2">
                    {result.matches.map((idx: number, i: number) => (
                      <span key={i} className="bg-slate-800 text-cyan-400 px-2.5 py-1 rounded text-xs font-mono">
                        {idx}
                      </span>
                    ))}
                  </div>
                </div>
              )}
            </div>
          )}
        </div>
      </div>
    </div>
  );
}
