import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { BookOpen, Table, Search, Sparkles, Filter } from 'lucide-react';

export default function AlgorithmExplorer() {
  const [algorithms, setAlgorithms] = useState<any[]>([]);
  const [selectedAlgo, setSelectedAlgo] = useState<any>(null);
  const [filterCategory, setFilterCategory] = useState('ALL');
  const [searchQuery, setSearchQuery] = useState('');

  useEffect(() => {
    axios.get('http://localhost:8080/api/algorithms')
      .then(res => {
        setAlgorithms(res.data);
        if (res.data.length > 0) setSelectedAlgo(res.data[0]);
      })
      .catch(console.error);
  }, []);

  const categories = ['ALL', 'String Algorithms', 'Dynamic Programming', 'Suffix Structures', 'Network Flow', 'Approximation Algorithms', 'Randomized Algorithms'];

  const filtered = algorithms.filter(a => {
    const matchesCat = filterCategory === 'ALL' || a.category === filterCategory;
    const matchesSearch = a.name.toLowerCase().includes(searchQuery.toLowerCase()) ||
                          a.problemSolved.toLowerCase().includes(searchQuery.toLowerCase());
    return matchesCat && matchesSearch;
  });

  return (
    <div className="p-8 max-w-7xl mx-auto space-y-8">
      <div className="border-b border-slate-800 pb-6">
        <h1 className="text-3xl font-bold text-slate-100 flex items-center gap-3">
          <BookOpen className="w-8 h-8 text-cyan-400" />
          Algorithm Explorer & Complexity Matrix
        </h1>
        <p className="text-slate-400 mt-2">
          Comprehensive university DSA-3 viva reference guide detailing theoretical properties, time/space complexities, and implementation rationale.
        </p>
      </div>

      {/* Complexity Comparison Matrix Table */}
      <div className="bg-slate-900 rounded-xl border border-slate-800 p-6 space-y-4 shadow-xl">
        <h2 className="text-sm font-bold uppercase tracking-wider text-slate-300 flex items-center gap-2">
          <Table className="w-4 h-4 text-cyan-400" />
          Master Complexity Comparison Matrix
        </h2>

        <div className="overflow-x-auto">
          <table className="w-full text-left text-xs text-slate-300">
            <thead className="bg-slate-950 uppercase text-slate-500 border-b border-slate-800">
              <tr>
                <th className="p-3">Algorithm</th>
                <th className="p-3">Category</th>
                <th className="p-3">Problem Solved</th>
                <th className="p-3">Time Complexity</th>
                <th className="p-3">Space Complexity</th>
              </tr>
            </thead>
            <tbody className="divide-y divide-slate-800 font-mono">
              {algorithms.map((algo, i) => (
                <tr
                  key={i}
                  onClick={() => setSelectedAlgo(algo)}
                  className={`cursor-pointer transition-colors ${
                    selectedAlgo?.name === algo.name ? 'bg-cyan-950/40 text-cyan-200' : 'hover:bg-slate-800/50'
                  }`}
                >
                  <td className="p-3 font-sans font-bold text-slate-100">{algo.name}</td>
                  <td className="p-3 font-sans text-slate-400">{algo.category}</td>
                  <td className="p-3 font-sans text-slate-300">{algo.problemSolved}</td>
                  <td className="p-3 text-cyan-400 font-semibold">{algo.timeComplexity}</td>
                  <td className="p-3 text-blue-400">{algo.spaceComplexity}</td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>

      {/* Detailed Explorer View */}
      <div className="grid grid-cols-1 lg:grid-cols-3 gap-8">
        {/* Left Column: Filter & Selector */}
        <div className="lg:col-span-1 space-y-4">
          <div className="bg-slate-900 p-4 rounded-xl border border-slate-800 space-y-3">
            <div className="relative">
              <Search className="w-4 h-4 text-slate-500 absolute left-3 top-3" />
              <input
                type="text"
                placeholder="Search algorithms..."
                value={searchQuery}
                onChange={e => setSearchQuery(e.target.value)}
                className="w-full bg-slate-950 border border-slate-800 text-xs rounded-lg pl-9 pr-3 py-2 text-slate-200 focus:outline-none focus:border-cyan-500"
              />
            </div>

            <div className="space-y-1">
              <span className="text-[10px] uppercase font-bold text-slate-500 tracking-wider">Filter Category</span>
              <div className="flex flex-wrap gap-1">
                {categories.map(cat => (
                  <button
                    key={cat}
                    onClick={() => setFilterCategory(cat)}
                    className={`px-2.5 py-1 rounded text-[11px] transition-colors ${
                      filterCategory === cat
                        ? 'bg-cyan-500 text-slate-950 font-bold'
                        : 'bg-slate-950 text-slate-400 hover:bg-slate-800'
                    }`}
                  >
                    {cat}
                  </button>
                ))}
              </div>
            </div>
          </div>

          <div className="space-y-2 max-h-[500px] overflow-y-auto pr-1">
            {filtered.map(algo => (
              <div
                key={algo.name}
                onClick={() => setSelectedAlgo(algo)}
                className={`p-3.5 rounded-xl border cursor-pointer transition-all ${
                  selectedAlgo?.name === algo.name
                    ? 'bg-slate-900 border-cyan-500 shadow-lg shadow-cyan-950/30'
                    : 'bg-slate-900/60 border-slate-800 hover:bg-slate-900'
                }`}
              >
                <div className="font-bold text-sm text-slate-100">{algo.name}</div>
                <div className="text-xs text-slate-400 mt-0.5">{algo.category}</div>
                <div className="text-[11px] font-mono text-cyan-400 mt-1">{algo.timeComplexity}</div>
              </div>
            ))}
          </div>
        </div>

        {/* Right Column: Deep-Dive Card */}
        <div className="lg:col-span-2">
          {selectedAlgo ? (
            <div className="bg-slate-900 rounded-xl border border-slate-800 p-6 space-y-6 shadow-xl">
              <div className="border-b border-slate-800 pb-4">
                <span className="text-xs uppercase font-bold tracking-wider px-2 py-0.5 rounded bg-slate-800 text-cyan-400">
                  {selectedAlgo.category}
                </span>
                <h2 className="text-2xl font-bold text-slate-100 mt-2">{selectedAlgo.name}</h2>
                <p className="text-sm text-slate-400 mt-1">{selectedAlgo.problemSolved}</p>
              </div>

              <div className="grid grid-cols-2 gap-4">
                <div className="bg-slate-950 p-4 rounded-xl border border-slate-800">
                  <span className="text-xs font-semibold uppercase text-slate-500">Time Complexity</span>
                  <div className="text-lg font-mono font-bold text-cyan-400 mt-1">
                    {selectedAlgo.timeComplexity}
                  </div>
                </div>

                <div className="bg-slate-950 p-4 rounded-xl border border-slate-800">
                  <span className="text-xs font-semibold uppercase text-slate-500">Space Complexity</span>
                  <div className="text-lg font-mono font-bold text-blue-400 mt-1">
                    {selectedAlgo.spaceComplexity}
                  </div>
                </div>
              </div>

              <div className="space-y-4 text-sm text-slate-300">
                <div>
                  <h3 className="text-xs font-bold uppercase tracking-wider text-slate-400 mb-1">Core Theoretical Idea</h3>
                  <p className="bg-slate-950 p-3 rounded-lg border border-slate-800 leading-relaxed text-xs">
                    {selectedAlgo.coreIdea}
                  </p>
                </div>

                <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                  <div>
                    <h3 className="text-xs font-bold uppercase tracking-wider text-slate-400 mb-1">Standard Input</h3>
                    <div className="bg-slate-950 p-3 rounded-lg border border-slate-800 text-xs font-mono text-slate-400">
                      {selectedAlgo.input}
                    </div>
                  </div>
                  <div>
                    <h3 className="text-xs font-bold uppercase tracking-wider text-slate-400 mb-1">Algorithm Output</h3>
                    <div className="bg-slate-950 p-3 rounded-lg border border-slate-800 text-xs font-mono text-slate-400">
                      {selectedAlgo.output}
                    </div>
                  </div>
                </div>

                <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                  <div className="bg-slate-950 p-3.5 rounded-lg border border-slate-800">
                    <span className="text-xs font-bold text-emerald-400 uppercase block mb-1">Key Advantages</span>
                    <p className="text-xs text-slate-400 leading-relaxed">{selectedAlgo.advantages}</p>
                  </div>
                  <div className="bg-slate-950 p-3.5 rounded-lg border border-slate-800">
                    <span className="text-xs font-bold text-amber-400 uppercase block mb-1">Limitations</span>
                    <p className="text-xs text-slate-400 leading-relaxed">{selectedAlgo.limitations}</p>
                  </div>
                </div>

                <div className="bg-cyan-950/30 border border-cyan-800/60 p-4 rounded-lg text-cyan-200 text-xs flex items-center gap-3">
                  <Sparkles className="w-5 h-5 text-cyan-400 shrink-0" />
                  <div>
                    <strong className="text-white">TextHack Usage: </strong>
                    {selectedAlgo.texthackUsage}
                  </div>
                </div>
              </div>
            </div>
          ) : (
            <div className="bg-slate-900 rounded-xl border border-slate-800 p-12 text-center text-slate-500">
              Select an algorithm to view theoretical breakdown.
            </div>
          )}
        </div>
      </div>
    </div>
  );
}
