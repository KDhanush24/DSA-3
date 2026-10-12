import React, { useState } from 'react';
import axios from 'axios';
import { CalendarClock, CheckCircle, Plus, Trash2, ArrowRight, ShieldAlert, Cpu } from 'lucide-react';

interface Edge {
  u: number;
  v: number;
}

export default function ProjectScheduling() {
  const [numVertices, setNumVertices] = useState(6);
  const [edges, setEdges] = useState<Edge[]>([
    { u: 0, v: 1 },
    { u: 1, v: 2 },
    { u: 2, v: 3 },
    { u: 3, v: 4 },
    { u: 4, v: 5 },
    { u: 1, v: 4 },
    { u: 2, v: 4 },
  ]);

  const [newU, setNewU] = useState(0);
  const [newV, setNewV] = useState(1);
  const [result, setResult] = useState<any>(null);
  const [loading, setLoading] = useState(false);

  const handleAddEdge = () => {
    if (newU === newV) return;
    setEdges([...edges, { u: newU, v: newV }]);
  };

  const handleRemoveEdge = (idx: number) => {
    setEdges(edges.filter((_, i) => i !== idx));
  };

  const handleRunApproximation = async () => {
    setLoading(true);
    try {
      const res = await axios.post('http://localhost:8080/api/scheduling/vertex-cover', {
        numVertices,
        edges
      });
      setResult(res.data);
    } catch (err) {
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  const loadPreset = (preset: string) => {
    if (preset === 'Star') {
      setNumVertices(5);
      setEdges([
        { u: 0, v: 1 },
        { u: 0, v: 2 },
        { u: 0, v: 3 },
        { u: 0, v: 4 },
      ]);
    } else if (preset === 'Cycle') {
      setNumVertices(6);
      setEdges([
        { u: 0, v: 1 },
        { u: 1, v: 2 },
        { u: 2, v: 3 },
        { u: 3, v: 4 },
        { u: 4, v: 5 },
        { u: 5, v: 0 },
      ]);
    } else if (preset === 'ProjectDependencies') {
      setNumVertices(6);
      setEdges([
        { u: 0, v: 1 },
        { u: 1, v: 2 },
        { u: 2, v: 3 },
        { u: 3, v: 4 },
        { u: 4, v: 5 },
        { u: 1, v: 4 },
        { u: 2, v: 4 },
      ]);
    }
    setResult(null);
  };

  return (
    <div className="p-8 max-w-7xl mx-auto space-y-8">
      <div className="flex flex-col md:flex-row md:items-center justify-between gap-4 border-b border-slate-800 pb-6">
        <div>
          <h1 className="text-3xl font-bold text-slate-100 flex items-center gap-3">
            <CalendarClock className="w-8 h-8 text-cyan-400" />
            Project Scheduling & NP-Hard Approximation Lab
          </h1>
          <p className="text-slate-400 mt-2">
            Demonstration of Vertex Cover 2-Approximation using Maximal Matching, comparing against exponential exact branch solutions.
          </p>
        </div>

        <div className="flex items-center gap-2">
          <button
            onClick={() => loadPreset('ProjectDependencies')}
            className="px-3 py-1.5 rounded-lg text-xs font-semibold bg-slate-800 hover:bg-slate-700 text-slate-200 border border-slate-700"
          >
            Dependencies Graph
          </button>
          <button
            onClick={() => loadPreset('Star')}
            className="px-3 py-1.5 rounded-lg text-xs font-semibold bg-slate-800 hover:bg-slate-700 text-slate-200 border border-slate-700"
          >
            Star Graph (Worst-Case 2x)
          </button>
          <button
            onClick={() => loadPreset('Cycle')}
            className="px-3 py-1.5 rounded-lg text-xs font-semibold bg-slate-800 hover:bg-slate-700 text-slate-200 border border-slate-700"
          >
            C6 Cycle
          </button>
        </div>
      </div>

      <div className="grid grid-cols-1 lg:grid-cols-3 gap-8">
        {/* Graph Input */}
        <div className="lg:col-span-1 space-y-6">
          <div className="bg-slate-900 p-6 rounded-xl border border-slate-800 shadow-lg space-y-4">
            <h2 className="text-sm font-bold uppercase tracking-wider text-slate-300">
              Graph Configuration
            </h2>

            <div>
              <label className="block text-xs font-semibold text-slate-400 uppercase mb-1">
                Number of Task Vertices (|V|)
              </label>
              <input
                type="number"
                min="2"
                max="20"
                value={numVertices}
                onChange={e => setNumVertices(Math.max(2, Math.min(20, Number(e.target.value))))}
                className="w-full bg-slate-950 border border-slate-800 text-sm text-slate-200 rounded-lg p-2 focus:outline-none focus:border-cyan-500"
              />
              <span className="text-[11px] text-slate-500 mt-1 block">Vertices labeled: 0 to {numVertices - 1}</span>
            </div>

            {/* Add Edge */}
            <div className="pt-2 border-t border-slate-800 space-y-2">
              <span className="text-xs font-semibold text-slate-400 uppercase block">Add Conflict Edge</span>
              <div className="grid grid-cols-2 gap-2">
                <div>
                  <label className="text-[10px] text-slate-500 uppercase font-semibold">Vertex U</label>
                  <select
                    value={newU}
                    onChange={e => setNewU(Number(e.target.value))}
                    className="w-full bg-slate-950 border border-slate-800 text-xs text-slate-200 rounded p-1.5 focus:outline-none focus:border-cyan-500"
                  >
                    {Array.from({ length: numVertices }).map((_, i) => (
                      <option key={i} value={i}>Vertex {i}</option>
                    ))}
                  </select>
                </div>
                <div>
                  <label className="text-[10px] text-slate-500 uppercase font-semibold">Vertex V</label>
                  <select
                    value={newV}
                    onChange={e => setNewV(Number(e.target.value))}
                    className="w-full bg-slate-950 border border-slate-800 text-xs text-slate-200 rounded p-1.5 focus:outline-none focus:border-cyan-500"
                  >
                    {Array.from({ length: numVertices }).map((_, i) => (
                      <option key={i} value={i}>Vertex {i}</option>
                    ))}
                  </select>
                </div>
              </div>
              <button
                onClick={handleAddEdge}
                className="w-full bg-slate-800 hover:bg-slate-700 text-slate-300 font-semibold py-2 rounded-lg text-xs border border-slate-700 flex items-center justify-center gap-1.5"
              >
                <Plus className="w-3.5 h-3.5 text-cyan-400" />
                Add Edge
              </button>
            </div>

            <button
              onClick={handleRunApproximation}
              disabled={loading}
              className="w-full bg-cyan-600 hover:bg-cyan-500 disabled:opacity-50 text-slate-950 font-bold py-2.5 px-4 rounded-lg transition-colors shadow-lg shadow-cyan-950 flex items-center justify-center gap-2 mt-4"
            >
              <Cpu className="w-4 h-4" />
              {loading ? 'Solving...' : 'Run 2-Approximation'}
            </button>
          </div>

          {/* Theoretical Guarantee Card */}
          <div className="bg-slate-900 p-6 rounded-xl border border-slate-800 shadow-lg space-y-2 text-xs text-slate-400 leading-relaxed">
            <span className="font-bold text-slate-200 text-sm block flex items-center gap-1.5">
              <ShieldAlert className="w-4 h-4 text-cyan-400" />
              Academic Theoretical Guarantee
            </span>
            <p>
              Minimum Vertex Cover is NP-Hard (Karp's 21 NP-complete problems).
            </p>
            <p>
              The maximal matching algorithm guarantees a factor <strong className="text-cyan-400">2-approximation</strong>:
              <br />
              <code className="text-cyan-300 font-mono text-[11px]">|C_approx| ≤ 2 · |C_OPT|</code>
            </p>
            <p className="text-[11px] text-slate-500">
              Because every edge in matching M is independent, OPT must pick at least 1 vertex per edge.
            </p>
          </div>
        </div>

        {/* Results & Visualizer */}
        <div className="lg:col-span-2 space-y-6">
          {/* Edges list */}
          <div className="bg-slate-900 p-6 rounded-xl border border-slate-800 shadow-lg space-y-4">
            <h3 className="text-sm font-bold uppercase tracking-wider text-slate-300 flex items-center justify-between">
              <span>Conflict Edges ({edges.length})</span>
              <span className="text-xs text-slate-500">Every edge must have ≥ 1 covered vertex</span>
            </h3>

            <div className="flex flex-wrap gap-2 max-h-48 overflow-y-auto">
              {edges.map((e, idx) => {
                const isMatched = result?.maximalMatching?.some((me: any) =>
                  (me.u === e.u && me.v === e.v) || (me.u === e.v && me.v === e.u)
                );

                return (
                  <div
                    key={idx}
                    className={`px-3 py-1.5 rounded-lg border flex items-center gap-2 text-xs font-mono transition-colors ${
                      isMatched
                        ? 'bg-amber-950/40 border-amber-500/70 text-amber-300 font-bold'
                        : 'bg-slate-950 border-slate-800 text-slate-300'
                    }`}
                  >
                    <span>({e.u}, {e.v})</span>
                    {isMatched && <span className="text-[10px] bg-amber-900/60 text-amber-200 px-1 rounded">MATCHED</span>}
                    <button
                      onClick={() => handleRemoveEdge(idx)}
                      className="text-slate-600 hover:text-rose-400 ml-1"
                    >
                      <Trash2 className="w-3 h-3" />
                    </button>
                  </div>
                );
              })}
            </div>
          </div>

          {result && (
            <div className="space-y-6">
              {/* Metrics */}
              <div className="grid grid-cols-1 md:grid-cols-4 gap-4">
                <div className="bg-slate-900 p-6 rounded-xl border border-slate-800">
                  <span className="text-xs font-semibold text-slate-400 uppercase">2-Approx Cover Size</span>
                  <div className="text-3xl font-extrabold text-cyan-400 mt-2">
                    {result.approxSize}
                  </div>
                  <p className="text-[11px] text-slate-500 mt-1">2 · |Maximal Matching|</p>
                </div>

                <div className="bg-slate-900 p-6 rounded-xl border border-slate-800">
                  <span className="text-xs font-semibold text-slate-400 uppercase">Optimal Cover Size</span>
                  <div className="text-3xl font-extrabold text-emerald-400 mt-2">
                    {result.optimalSize}
                  </div>
                  <p className="text-[11px] text-slate-500 mt-1">Exact brute-force check</p>
                </div>

                <div className="bg-slate-900 p-6 rounded-xl border border-slate-800">
                  <span className="text-xs font-semibold text-slate-400 uppercase">Actual Approx Ratio</span>
                  <div className="text-3xl font-extrabold text-transparent bg-clip-text bg-gradient-to-r from-amber-400 to-cyan-400 mt-2">
                    {result.approximationRatio.toFixed(2)}x
                  </div>
                  <p className="text-[11px] text-slate-500 mt-1">Guaranteed ≤ 2.0x</p>
                </div>

                <div className="bg-slate-900 p-6 rounded-xl border border-slate-800">
                  <span className="text-xs font-semibold text-slate-400 uppercase">Execution Time</span>
                  <div className="text-2xl font-bold text-slate-100 mt-2">
                    {(result.executionTimeNs / 1000000).toFixed(3)} <span className="text-xs text-slate-500 font-normal">ms</span>
                  </div>
                  <p className="text-[11px] text-emerald-400 mt-1">Linear O(V + E)</p>
                </div>
              </div>

              {/* Vertex Cover Comparison */}
              <div className="bg-slate-900 p-6 rounded-xl border border-slate-800 space-y-4">
                <h3 className="text-sm font-bold uppercase tracking-wider text-slate-300">
                  Selected Vertex Sets Comparison
                </h3>

                <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                  <div className="bg-slate-950 p-4 rounded-xl border border-slate-800 space-y-2">
                    <span className="text-xs font-bold text-cyan-400 uppercase">
                      Approximate Vertex Cover (Size {result.approxSize})
                    </span>
                    <div className="flex flex-wrap gap-2 pt-1">
                      {result.approximateCover?.map((v: number) => (
                        <span key={v} className="bg-cyan-950 text-cyan-300 border border-cyan-800 px-3 py-1 rounded-lg text-sm font-bold font-mono">
                          Vertex {v}
                        </span>
                      ))}
                    </div>
                  </div>

                  <div className="bg-slate-950 p-4 rounded-xl border border-slate-800 space-y-2">
                    <span className="text-xs font-bold text-emerald-400 uppercase">
                      Exact Optimal Vertex Cover (Size {result.optimalSize})
                    </span>
                    <div className="flex flex-wrap gap-2 pt-1">
                      {result.optimalCover?.map((v: number) => (
                        <span key={v} className="bg-emerald-950 text-emerald-300 border border-emerald-800 px-3 py-1 rounded-lg text-sm font-bold font-mono">
                          Vertex {v}
                        </span>
                      ))}
                    </div>
                  </div>
                </div>

                <div className="bg-slate-950 p-4 rounded-xl border border-slate-800 text-xs text-slate-400 leading-relaxed flex items-start gap-2">
                  <CheckCircle className="w-4 h-4 text-emerald-400 shrink-0 mt-0.5" />
                  <div>
                    <strong className="text-slate-200">Formal Guarantee Verification: </strong>
                    {result.guaranteeExplanation}
                  </div>
                </div>
              </div>
            </div>
          )}
        </div>
      </div>
    </div>
  );
}
