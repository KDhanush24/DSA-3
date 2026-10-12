import React, { useState } from 'react';
import axios from 'axios';
import { SpellCheck, Clock, FileText, CheckCircle2, Search } from 'lucide-react';

export default function FuzzySearch() {
  const [query, setQuery] = useState('algoritm');
  const [text, setText] = useState('An algorithm is a finite sequence of rigorous instructions, typically used to solve a class of specific problems or to perform a computation. Algorithms are used as specifications for performing calculations and data processing. More advanced algorithms can perform automated deductions (referred to as automated reasoning) and use mathematical and logical tests to direct the code execution through various routes.');
  const [result, setResult] = useState<any>(null);
  const [loading, setLoading] = useState(false);

  const handleSearch = async () => {
    if (!query.trim()) return;
    setLoading(true);
    try {
      const res = await axios.post('http://localhost:8080/api/search/fuzzy', {
        query: query.trim(),
        text,
        searchCorpus: false,
        maxDistance: 4
      });
      setResult(res.data);
    } catch (error) {
      console.error(error);
    } finally {
      setLoading(false);
    }
  };

  const getMatchedWordsSet = () => {
    if (!result || !result.suggestions) return new Set<string>();
    return new Set<string>(result.suggestions.map((s: any) => s.word.toLowerCase()));
  };

  const matchedSet = getMatchedWordsSet();

  const renderHighlightedParagraph = () => {
    if (!matchedSet.size) {
      return <span>{text}</span>;
    }

    const tokens = text.split(/(\s+|[.,;!?()[\]{}"'])/);
    return tokens.map((token, idx) => {
      const clean = token.toLowerCase();
      if (matchedSet.has(clean)) {
        return (
          <span
            key={idx}
            className="bg-cyan-500/30 text-cyan-200 border-b-2 border-cyan-400 font-semibold px-1 rounded mx-0.5"
            title={`Similar match for: "${query}"`}
          >
            {token}
          </span>
        );
      }
      return <span key={idx}>{token}</span>;
    });
  };

  return (
    <div className="p-8 max-w-6xl mx-auto space-y-8">
      {/* Header */}
      <div className="border-b border-slate-800 pb-6">
        <h1 className="text-3xl font-bold text-slate-100 flex items-center gap-3">
          <SpellCheck className="w-8 h-8 text-cyan-400" />
          Fuzzy Search
        </h1>
        <p className="text-slate-400 mt-2">
          Search for words in a paragraph even with spelling mistakes, and find the closest matching words with approximate match percentages.
        </p>
      </div>

      {/* Inputs Section */}
      <div className="grid grid-cols-1 lg:grid-cols-3 gap-8">
        {/* Search Word Input */}
        <div className="lg:col-span-1 space-y-6">
          <div className="bg-slate-900 p-6 rounded-2xl border border-slate-800 shadow-xl space-y-4">
            <h2 className="text-sm font-bold uppercase tracking-wider text-slate-300">
              Search Word
            </h2>

            <div>
              <label className="block text-xs font-semibold text-slate-400 uppercase mb-1">
                Enter Word (Misspellings Allowed)
              </label>
              <input
                type="text"
                value={query}
                onChange={e => setQuery(e.target.value)}
                placeholder="e.g. algoritm, computatn"
                className="w-full bg-slate-950 border border-slate-800 rounded-xl p-3 text-sm text-slate-200 focus:outline-none focus:border-cyan-500 transition-colors"
              />
              <span className="text-[11px] text-slate-500 mt-1 block">
                Try: <code className="text-cyan-400 cursor-pointer" onClick={() => setQuery('algoritm')}>algoritm</code>, <code className="text-cyan-400 cursor-pointer" onClick={() => setQuery('computatn')}>computatn</code>, <code className="text-cyan-400 cursor-pointer" onClick={() => setQuery('prosessing')}>prosessing</code>
              </span>
            </div>

            <button
              onClick={handleSearch}
              disabled={loading || !query.trim()}
              className="w-full bg-cyan-600 hover:bg-cyan-500 disabled:opacity-50 text-slate-950 font-bold py-3 px-4 rounded-xl transition-colors shadow-lg shadow-cyan-950 flex items-center justify-center gap-2"
            >
              <Search className="w-4 h-4" />
              {loading ? 'Searching Paragraph...' : 'Find Similar Words'}
            </button>
          </div>

          {/* Quick Telemetry */}
          {result && (
            <div className="bg-slate-900 p-6 rounded-2xl border border-slate-800 shadow-xl space-y-3">
              <h3 className="text-xs font-bold uppercase tracking-wider text-slate-400">Search Summary</h3>
              <div className="grid grid-cols-2 gap-3">
                <div className="bg-slate-950 p-3 rounded-xl border border-slate-800">
                  <span className="text-[10px] uppercase font-semibold text-slate-500 block">Matches Found</span>
                  <div className="text-2xl font-bold text-slate-100 mt-0.5">
                    {result.suggestions?.length || 0}
                  </div>
                </div>

                <div className="bg-slate-950 p-3 rounded-xl border border-slate-800">
                  <span className="text-[10px] uppercase font-semibold text-slate-500 block">Response Time</span>
                  <div className="text-xl font-bold text-slate-100 mt-0.5">
                    {(result.executionTimeNs / 1000000).toFixed(3)} <span className="text-xs text-slate-500 font-normal">ms</span>
                  </div>
                </div>
              </div>
            </div>
          )}
        </div>

        {/* Paragraph Textarea */}
        <div className="lg:col-span-2 space-y-6">
          <div className="bg-slate-900 p-6 rounded-2xl border border-slate-800 shadow-xl space-y-3">
            <h2 className="text-sm font-bold uppercase tracking-wider text-slate-300">
              Paragraph (Search Target)
            </h2>
            <textarea
              rows={6}
              value={text}
              onChange={e => setText(e.target.value)}
              placeholder="Paste or write your paragraph here..."
              className="w-full bg-slate-950 border border-slate-800 rounded-xl p-4 text-slate-300 text-sm focus:outline-none focus:border-cyan-500 resize-none leading-relaxed"
            />
          </div>

          {/* Highlighted Paragraph Output */}
          {result && (
            <div className="bg-slate-900 p-6 rounded-2xl border border-slate-800 shadow-xl space-y-3">
              <h2 className="text-sm font-bold uppercase tracking-wider text-slate-300">
                Paragraph with Highlighted Similar Words
              </h2>
              <div className="bg-slate-950 p-4 rounded-xl border border-slate-800 text-slate-300 text-sm leading-relaxed whitespace-pre-wrap">
                {renderHighlightedParagraph()}
              </div>
            </div>
          )}
        </div>
      </div>

      {/* Output List: Similar Words & Matching Percentages */}
      {result && (
        <div className="bg-slate-900 p-6 rounded-2xl border border-slate-800 shadow-xl space-y-4">
          <div className="flex items-center justify-between">
            <h2 className="text-lg font-bold text-slate-100 flex items-center gap-2">
              <CheckCircle2 className="w-5 h-5 text-cyan-400" />
              Similar Words Found in Paragraph
            </h2>
            <span className="text-xs text-slate-400">
              Sorted by highest match percentage
            </span>
          </div>

          {result.suggestions && result.suggestions.length > 0 ? (
            <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
              {result.suggestions.map((item: any, idx: number) => {
                const matchPct = item.similarityPercentage ?? Math.round((1 - item.distance / Math.max(query.length, item.word.length)) * 100);
                const isExact = item.distance === 0;

                return (
                  <div
                    key={idx}
                    className="bg-slate-950 p-4 rounded-xl border border-slate-800 hover:border-cyan-500/60 transition-all flex flex-col justify-between space-y-3 shadow-md"
                  >
                    <div className="flex items-start justify-between gap-2">
                      <div>
                        <span className="text-lg font-bold text-slate-100 font-mono">
                          {item.word}
                        </span>
                        {item.count > 1 && (
                          <span className="text-[11px] text-slate-500 ml-2">
                            ({item.count}× in para)
                          </span>
                        )}
                      </div>

                      {/* Approximate Matching Percentage Badge */}
                      <span
                        className={`px-2.5 py-1 rounded-full text-xs font-black font-mono tracking-wide ${
                          matchPct >= 90
                            ? 'bg-emerald-950 text-emerald-400 border border-emerald-800'
                            : matchPct >= 70
                            ? 'bg-cyan-950 text-cyan-400 border border-cyan-800'
                            : 'bg-amber-950 text-amber-400 border border-amber-800'
                        }`}
                      >
                        {matchPct}% Match
                      </span>
                    </div>

                    <div className="flex items-center justify-between text-xs text-slate-400 pt-2 border-t border-slate-900">
                      <span>
                        {isExact ? (
                          <strong className="text-emerald-400">Exact match</strong>
                        ) : (
                          <span>Difference: <strong className="text-slate-200">{item.distance} character{item.distance > 1 ? 's' : ''}</strong></span>
                        )}
                      </span>
                      <span className="text-[11px] text-slate-500">
                        {item.word.length} letters
                      </span>
                    </div>
                  </div>
                );
              })}
            </div>
          ) : (
            <div className="p-8 text-center text-slate-500 text-sm bg-slate-950 rounded-xl border border-slate-800">
              No sufficiently similar words found in this paragraph for "{query}". Try another word or lower similarity threshold.
            </div>
          )}
        </div>
      )}
    </div>
  );
}
