import React, { useState } from 'react';
import axios from 'axios';
import { Network, Play, Plus, Trash2, ArrowRight, ShieldCheck, Activity, Layers } from 'lucide-react';

interface EdgeInput {
  from: number;
  to: number;
  capacity: number;
}

export default function NetworkFlow() {
  const [nodes, setNodes] = useState<string[]>(['S', 'A', 'B', 'C', 'T']);
  const [edges, setEdges] = useState<EdgeInput[]>([
    { from: 0, to: 1, capacity: 10 },
    { from: 0, to: 2, capacity: 5 },
    { from: 1, to: 3, capacity: 8 },
    { from: 2, to: 3, capacity: 4 },
    { from: 3, to: 4, capacity: 12 },
    { from: 1, to: 2, capacity: 2 },
  ]);
  const [source, setSource] = useState(0);
  const [sink, setSink] = useState(4);
  const [algorithm, setAlgorithm] = useState<'EDMONDS_KARP' | 'DINIC'>('EDMONDS_KARP');

  // New Edge input state
  const [newFrom, setNewFrom] = useState(0);
  const [newTo, setNewTo] = useState(4);
  const [newCap, setNewCap] = useState(5);
  const [newNodeName, setNewNodeName] = useState('');

  const [result, setResult] = useState<any>(null);
  const [loading, setLoading] = useState(false);
  const [stepIndex, setStepIndex] = useState(0);

  const handleAddNode = () => {
    if (!newNodeName.trim()) return;
    setNodes([...nodes, newNodeName.trim()]);
    setNewNodeName('');
  };

  const handleAddEdge = () => {
    if (newFrom === newTo || newCap <= 0) return;
    setEdges([...edges, { from: newFrom, to: newTo, capacity: newCap }]);
  };

  const handleRemoveEdge = (idx: number) => {
    setEdges(edges.filter((_, i) => i !== idx));
  };

  const handleRunFlow = async () => {
    setLoading(true);
    try {
      const res = await axios.post('http://localhost:8080/api/flow/max', {
        numVertices: nodes.length,
        nodeLabels: nodes,
        edges,
        source,
        sink,
        algorithm
      });
      setResult(res.data);
      setStepIndex(res.data.steps?.length ? res.data.steps.length - 1 : 0);
    } catch (err) {
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  // Pre-load default sample graphs
  const loadPreset = (presetName: string) => {
    if (presetName === 'Standard') {
      setNodes(['S', 'A', 'B', 'C', 'T']);
      setEdges([
        { from: 0, to: 1, capacity: 10 },
        { from: 0, to: 2, capacity: 5 },
        { from: 1, to: 3, capacity: 8 },
        { from: 2, to: 3, capacity: 4 },
        { from: 3, to: 4, capacity: 12 },
        { from: 1, to: 2, capacity: 2 },
      ]);
      setSource(0);
      setSink(4);
    } else if (presetName === 'DenseBipartite') {
      setNodes(['S', 'U1', 'U2', 'V1', 'V2', 'T']);
      setEdges([
        { from: 0, to: 1, capacity: 1 },
        { from: 0, to: 2, capacity: 1 },
        { from: 1, to: 3, capacity: 1 },
        { from: 1, to: 4, capacity: 1 },
        { from: 2, to: 3, capacity: 1 },
        { from: 3, to: 5, capacity: 1 },
        { from: 4, to: 5, capacity: 1 },
      ]);
      setSource(0);
      setSink(5);
    }
    setResult(null);
  };

  const activeStep = result?.steps && result.steps.length > 0 ? result.steps[stepIndex] : null;

  return (
    <div className="p-8 max-w-7xl mx-auto space-y-8">
      <div className="flex flex-col md:flex-row md:items-center justify-between gap-4 border-b border-slate-800 pb-6">
        <div>
          <h1 className="text-3xl font-bold text-slate-100 flex items-center gap-3">
            <Network className="w-8 h-8 text-cyan-400" />
            Network Flow & Maximum Flow Laboratory
          </h1>
          <p className="text-slate-400 mt-2">
            Interactive residual network simulation with Edmonds-Karp (BFS Shortest Augmenting Paths), Dinic's Algorithm, and Max-Flow Min-Cut Theorem verification.
          </p>
        </div>

        <div className="flex items-center gap-2">
          <button
            onClick={() => loadPreset('Standard')}
            className="px-3 py-1.5 rounded-lg text-xs font-semibold bg-slate-800 hover:bg-slate-700 text-slate-200 border border-slate-700"
          >
            Preset: Syllabus Graph
          </button>
          <button
            onClick={() => loadPreset('DenseBipartite')}
            className="px-3 py-1.5 rounded-lg text-xs font-semibold bg-slate-800 hover:bg-slate-700 text-slate-200 border border-slate-700"
          >
            Preset: Bipartite Matching
          </button>
        </div>
      </div>

      <div className="grid grid-cols-1 lg:grid-cols-3 gap-8">
        {/* Graph Editor Controls */}
        <div className="lg:col-span-1 space-y-6">
          <div className="bg-slate-900 p-6 rounded-xl border border-slate-800 shadow-lg space-y-4">
            <h2 className="text-sm font-bold uppercase tracking-wider text-slate-300">
              Flow Configuration
            </h2>

            <div className="grid grid-cols-2 gap-3">
              <div>
                <label className="block text-xs font-semibold text-slate-400 uppercase mb-1">Source (s)</label>
                <select
                  value={source}
                  onChange={e => setSource(Number(e.target.value))}
                  className="w-full bg-slate-950 border border-slate-800 text-sm text-slate-200 rounded-lg p-2 focus:outline-none focus:border-cyan-500"
                >
                  {nodes.map((n, i) => (
                    <option key={i} value={i}>{n} (idx {i})</option>
                  ))}
                </select>
              </div>

              <div>
                <label className="block text-xs font-semibold text-slate-400 uppercase mb-1">Sink (t)</label>
                <select
                  value={sink}
                  onChange={e => setSink(Number(e.target.value))}
                  className="w-full bg-slate-950 border border-slate-800 text-sm text-slate-200 rounded-lg p-2 focus:outline-none focus:border-cyan-500"
                >
                  {nodes.map((n, i) => (
                    <option key={i} value={i}>{n} (idx {i})</option>
                  ))}
                </select>
              </div>
            </div>

            <div>
              <label className="block text-xs font-semibold text-slate-400 uppercase mb-1">Max-Flow Algorithm</label>
              <select
                value={algorithm}
                onChange={e => setAlgorithm(e.target.value as any)}
                className="w-full bg-slate-950 border border-slate-800 text-sm text-slate-200 rounded-lg p-2 focus:outline-none focus:border-cyan-500"
              >
                <option value="EDMONDS_KARP">Edmonds-Karp (BFS Augmenting Paths) O(V·E²)</option>
                <option value="DINIC">Dinic's Algorithm (Level Graph + Blocking Flow) O(V²·E)</option>
              </select>
            </div>

            <button
              onClick={handleRunFlow}
              disabled={loading}
              className="w-full bg-cyan-600 hover:bg-cyan-500 disabled:opacity-50 text-slate-950 font-bold py-2.5 px-4 rounded-lg transition-colors shadow-lg shadow-cyan-950 flex items-center justify-center gap-2"
            >
              <Play className="w-4 h-4 fill-current" />
              {loading ? 'Computing Flow...' : 'Execute Max Flow'}
            </button>
          </div>

          {/* Add Edge form */}
          <div className="bg-slate-900 p-6 rounded-xl border border-slate-800 shadow-lg space-y-4">
            <h3 className="text-xs font-bold uppercase tracking-wider text-slate-400">
              Add Directed Edge
            </h3>
            <div className="grid grid-cols-3 gap-2">
              <div>
                <label className="text-[10px] text-slate-500 uppercase font-semibold">From</label>
                <select
                  value={newFrom}
                  onChange={e => setNewFrom(Number(e.target.value))}
                  className="w-full bg-slate-950 border border-slate-800 text-xs text-slate-200 rounded p-1.5 focus:outline-none focus:border-cyan-500"
                >
                  {nodes.map((n, i) => (
                    <option key={i} value={i}>{n}</option>
                  ))}
                </select>
              </div>
              <div>
                <label className="text-[10px] text-slate-500 uppercase font-semibold">To</label>
                <select
                  value={newTo}
                  onChange={e => setNewTo(Number(e.target.value))}
                  className="w-full bg-slate-950 border border-slate-800 text-xs text-slate-200 rounded p-1.5 focus:outline-none focus:border-cyan-500"
                >
                  {nodes.map((n, i) => (
                    <option key={i} value={i}>{n}</option>
                  ))}
                </select>
              </div>
              <div>
                <label className="text-[10px] text-slate-500 uppercase font-semibold">Capacity</label>
                <input
                  type="number"
                  min="1"
                  value={newCap}
                  onChange={e => setNewCap(Math.max(1, Number(e.target.value)))}
                  className="w-full bg-slate-950 border border-slate-800 text-xs text-slate-200 rounded p-1.5 focus:outline-none focus:border-cyan-500"
                />
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

          {/* Add Node form */}
          <div className="bg-slate-900 p-6 rounded-xl border border-slate-800 shadow-lg space-y-3">
            <h3 className="text-xs font-bold uppercase tracking-wider text-slate-400">
              Add Vertex
            </h3>
            <div className="flex gap-2">
              <input
                type="text"
                placeholder="e.g. D"
                value={newNodeName}
                onChange={e => setNewNodeName(e.target.value)}
                className="flex-1 bg-slate-950 border border-slate-800 text-xs text-slate-200 rounded-lg p-2 focus:outline-none focus:border-cyan-500"
              />
              <button
                onClick={handleAddNode}
                className="bg-slate-800 hover:bg-slate-700 text-slate-200 text-xs font-semibold px-4 rounded-lg border border-slate-700"
              >
                Add Node
              </button>
            </div>
          </div>
        </div>

        {/* Visualizer & Results */}
        <div className="lg:col-span-2 space-y-6">
          {/* Edge table representation */}
          <div className="bg-slate-900 p-6 rounded-xl border border-slate-800 shadow-lg space-y-4">
            <h3 className="text-sm font-bold uppercase tracking-wider text-slate-300 flex items-center justify-between">
              <span>Network Edges & Capacities ({edges.length} edges)</span>
              <span className="text-xs font-mono text-cyan-400">
                Source: {nodes[source]} → Sink: {nodes[sink]}
              </span>
            </h3>

            <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 gap-3 max-h-56 overflow-y-auto pr-1">
              {edges.map((e, idx) => {
                const edgeResult = result?.edgeResults?.find((er: any) => er.from === e.from && er.to === e.to);
                const flow = edgeResult ? edgeResult.flow : 0;
                const residual = e.capacity - flow;
                const isSaturated = edgeResult && flow === e.capacity;

                return (
                  <div
                    key={idx}
                    className={`p-3 rounded-lg border flex items-center justify-between transition-colors ${
                      isSaturated
                        ? 'bg-amber-950/20 border-amber-800/50'
                        : flow > 0
                        ? 'bg-cyan-950/20 border-cyan-800/50'
                        : 'bg-slate-950 border-slate-800'
                    }`}
                  >
                    <div>
                      <div className="flex items-center gap-1.5 font-bold text-sm text-slate-200">
                        <span className="text-cyan-400">{nodes[e.from]}</span>
                        <ArrowRight className="w-3.5 h-3.5 text-slate-500" />
                        <span className="text-blue-400">{nodes[e.to]}</span>
                      </div>
                      <div className="text-xs text-slate-400 mt-1">
                        Flow / Cap: <strong className={flow > 0 ? 'text-cyan-300' : 'text-slate-300'}>{flow} / {e.capacity}</strong>
                      </div>
                      {result && (
                        <div className="text-[10px] text-slate-500">
                          Residual: {residual} {isSaturated && <span className="text-amber-400 font-bold">(Saturated)</span>}
                        </div>
                      )}
                    </div>

                    <button
                      onClick={() => handleRemoveEdge(idx)}
                      className="text-slate-600 hover:text-rose-400 transition-colors p-1"
                      title="Remove edge"
                    >
                      <Trash2 className="w-4 h-4" />
                    </button>
                  </div>
                );
              })}
            </div>
          </div>

          {/* Results Summary */}
          {result && (
            <div className="space-y-6">
              <div className="grid grid-cols-1 md:grid-cols-3 gap-4">
                <div className="bg-slate-900 p-6 rounded-xl border border-slate-800">
                  <span className="text-xs font-semibold text-slate-400 uppercase">Maximum Flow Value</span>
                  <div className="text-4xl font-extrabold text-transparent bg-clip-text bg-gradient-to-r from-cyan-400 to-emerald-400 mt-2">
                    {result.maxFlow}
                  </div>
                  <p className="text-[11px] text-slate-500 mt-1">Units per time unit</p>
                </div>

                <div className="bg-slate-900 p-6 rounded-xl border border-slate-800">
                  <span className="text-xs font-semibold text-slate-400 uppercase">Algorithm & Complexity</span>
                  <div className="text-xl font-bold text-slate-100 mt-2">
                    {result.algorithmName}
                  </div>
                  <p className="text-xs font-mono text-cyan-400 mt-1">{result.timeComplexity}</p>
                </div>

                <div className="bg-slate-900 p-6 rounded-xl border border-slate-800">
                  <span className="text-xs font-semibold text-slate-400 uppercase">Execution Time</span>
                  <div className="text-xl font-bold text-slate-100 mt-2">
                    {(result.executionTimeNs / 1000000).toFixed(3)} <span className="text-sm font-normal text-slate-500">ms</span>
                  </div>
                  <p className="text-[11px] text-emerald-400 mt-1">Real Java execution (no java.util)</p>
                </div>
              </div>

              {/* Min-Cut Partitions Theorem */}
              <div className="bg-slate-900 p-6 rounded-xl border border-slate-800 space-y-3">
                <div className="flex items-center gap-2 text-slate-200 font-bold text-sm">
                  <ShieldCheck className="w-5 h-5 text-emerald-400" />
                  Max-Flow Min-Cut Theorem (Residual Partitions)
                </div>
                <p className="text-xs text-slate-400 leading-relaxed">
                  By the Max-Flow Min-Cut theorem, the capacity of the minimum cut equals the maximum flow ({result.maxFlow}). The source-side cut contains all nodes reachable from {nodes[source]} in the final residual network.
                </p>
                <div className="grid grid-cols-2 gap-4 pt-2">
                  <div className="bg-slate-950 p-3 rounded-lg border border-slate-800">
                    <span className="text-xs font-semibold text-cyan-400 uppercase">Source Partition (S-Cut)</span>
                    <div className="flex flex-wrap gap-1.5 mt-2">
                      {result.sourcePartition?.map((idx: number) => (
                        <span key={idx} className="bg-cyan-950 text-cyan-300 border border-cyan-800 px-2 py-0.5 rounded text-xs font-bold font-mono">
                          {nodes[idx]}
                        </span>
                      ))}
                    </div>
                  </div>

                  <div className="bg-slate-950 p-3 rounded-lg border border-slate-800">
                    <span className="text-xs font-semibold text-blue-400 uppercase">Sink Partition (T-Cut)</span>
                    <div className="flex flex-wrap gap-1.5 mt-2">
                      {result.sinkPartition?.map((idx: number) => (
                        <span key={idx} className="bg-blue-950 text-blue-300 border border-blue-800 px-2 py-0.5 rounded text-xs font-bold font-mono">
                          {nodes[idx]}
                        </span>
                      ))}
                    </div>
                  </div>
                </div>
              </div>

              {/* Step-by-Step Augmenting Paths Slider (Edmonds-Karp) */}
              {result.steps && result.steps.length > 0 && (
                <div className="bg-slate-900 p-6 rounded-xl border border-slate-800 space-y-4">
                  <div className="flex items-center justify-between">
                    <h3 className="text-sm font-bold uppercase tracking-wider text-slate-300 flex items-center gap-2">
                      <Layers className="w-4 h-4 text-cyan-400" />
                      Augmenting Path Iterations ({result.steps.length} paths found)
                    </h3>
                    <span className="text-xs text-slate-400">
                      Step {stepIndex + 1} of {result.steps.length}
                    </span>
                  </div>

                  <input
                    type="range"
                    min="0"
                    max={result.steps.length - 1}
                    value={stepIndex}
                    onChange={e => setStepIndex(Number(e.target.value))}
                    className="w-full accent-cyan-500 cursor-pointer"
                  />

                  {activeStep && (
                    <div className="bg-slate-950 p-4 rounded-lg border border-slate-800 flex flex-col sm:flex-row sm:items-center justify-between gap-3">
                      <div>
                        <span className="text-xs font-semibold text-slate-500 uppercase">Augmenting Path</span>
                        <div className="flex items-center gap-2 font-mono font-bold text-sm text-cyan-300 mt-1">
                          {activeStep.path.map((nodeIdx: number, pIdx: number) => (
                            <React.Fragment key={pIdx}>
                              <span className="bg-slate-800 px-2 py-0.5 rounded text-slate-200">
                                {nodes[nodeIdx]}
                              </span>
                              {pIdx < activeStep.path.length - 1 && (
                                <ArrowRight className="w-3.5 h-3.5 text-slate-600" />
                              )}
                            </React.Fragment>
                          ))}
                        </div>
                      </div>

                      <div className="flex items-center gap-4 text-xs font-semibold">
                        <div>
                          <span className="text-slate-500 block">Bottleneck Capacity:</span>
                          <span className="text-amber-400 text-sm">+{activeStep.bottleneck}</span>
                        </div>
                        <div>
                          <span className="text-slate-500 block">Cumulative Flow:</span>
                          <span className="text-emerald-400 text-sm">{activeStep.cumulativeFlow}</span>
                        </div>
                      </div>
                    </div>
                  )}
                </div>
              )}
            </div>
          )}
        </div>
      </div>
    </div>
  );
}
