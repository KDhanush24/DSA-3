import React, { useState } from 'react';
import axios from 'axios';
import { Sparkles, ShieldCheck, ShieldX, Clock, Cpu, HelpCircle, CheckCircle2 } from 'lucide-react';

export default function PrimeTesting() {
  const [number, setNumber] = useState('9999999967');
  const [rounds, setRounds] = useState(10);
  const [result, setResult] = useState<any>(null);
  const [loading, setLoading] = useState(false);

  const handleTest = async () => {
    if (!number.trim()) return;
    setLoading(true);
    try {
      const res = await axios.post('http://localhost:8080/api/primality/miller-rabin', {
        number: number.trim(),
        rounds
      });
      setResult(res.data);
    } catch (err) {
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  const handleSelectPreset = (num: string, r: number = 10) => {
    setNumber(num);
    setRounds(r);
    setResult(null);
  };

  return (
    <div className="p-8 max-w-7xl mx-auto space-y-8">
      <div className="flex flex-col md:flex-row md:items-center justify-between gap-4 border-b border-slate-800 pb-6">
        <div>
          <h1 className="text-3xl font-bold text-slate-100 flex items-center gap-3">
            <Sparkles className="w-8 h-8 text-cyan-400" />
            Randomized Algorithms: Miller-Rabin Primality Lab
          </h1>
          <p className="text-slate-400 mt-2">
            Probabilistic primality testing for arbitrarily large integers with customizable witness rounds and error-probability bounds.
          </p>
        </div>

        <div className="flex flex-wrap gap-2">
          <button
            onClick={() => handleSelectPreset('9999999967')}
            className="px-3 py-1.5 rounded-lg text-xs font-semibold bg-slate-800 hover:bg-slate-700 text-slate-200 border border-slate-700"
          >
            Prime: 9,999,999,967
          </button>
          <button
            onClick={() => handleSelectPreset('9999999969')}
            className="px-3 py-1.5 rounded-lg text-xs font-semibold bg-slate-800 hover:bg-slate-700 text-slate-200 border border-slate-700"
          >
            Composite: 9,999,999,969
          </button>
          <button
            onClick={() => handleSelectPreset('561')}
            className="px-3 py-1.5 rounded-lg text-xs font-semibold bg-slate-800 hover:bg-slate-700 text-slate-200 border border-slate-700"
          >
            Carmichael: 561
          </button>
        </div>
      </div>

      <div className="grid grid-cols-1 lg:grid-cols-3 gap-8">
        {/* Input Parameters */}
        <div className="lg:col-span-1 space-y-6">
          <div className="bg-slate-900 p-6 rounded-xl border border-slate-800 shadow-lg space-y-4">
            <h2 className="text-sm font-bold uppercase tracking-wider text-slate-300">
              Test Parameters
            </h2>

            <div>
              <label className="block text-xs font-semibold text-slate-400 uppercase mb-1">
                Integer to Test (n)
              </label>
              <textarea
                rows={3}
                value={number}
                onChange={e => setNumber(e.target.value.replace(/\D/g, ''))}
                placeholder="Enter any large integer..."
                className="w-full bg-slate-950 border border-slate-800 text-sm font-mono text-slate-200 rounded-lg p-2.5 focus:outline-none focus:border-cyan-500 resize-none leading-relaxed"
              />
              <span className="text-[11px] text-slate-500 mt-1 block">Arbitrary precision supported (BigInteger)</span>
            </div>

            <div>
              <label className="block text-xs font-semibold text-slate-400 uppercase mb-1">
                Independent Random Witness Rounds (k): <strong className="text-cyan-400">{rounds}</strong>
              </label>
              <input
                type="range"
                min="1"
                max="50"
                value={rounds}
                onChange={e => setRounds(Number(e.target.value))}
                className="w-full accent-cyan-500 cursor-pointer"
              />
              <div className="flex justify-between text-[11px] text-slate-500 mt-1">
                <span>Fast (k=1)</span>
                <span>Cryptographic (k=40+)</span>
              </div>
            </div>

            <button
              onClick={handleTest}
              disabled={loading || !number.trim()}
              className="w-full bg-cyan-600 hover:bg-cyan-500 disabled:opacity-50 text-slate-950 font-bold py-2.5 px-4 rounded-lg transition-colors shadow-lg shadow-cyan-950 flex items-center justify-center gap-2 mt-4"
            >
              <Sparkles className="w-4 h-4" />
              {loading ? 'Evaluating Witnesses...' : 'Run Miller-Rabin Test'}
            </button>
          </div>

          {/* Mathematical Card */}
          <div className="bg-slate-900 p-6 rounded-xl border border-slate-800 shadow-lg space-y-3 text-xs text-slate-400 leading-relaxed">
            <span className="font-bold text-slate-200 text-sm block flex items-center gap-1.5">
              <HelpCircle className="w-4 h-4 text-cyan-400" />
              How Miller-Rabin Works
            </span>
            <p>
              1. Decomposes <code className="text-cyan-300">n - 1 = 2^s · d</code> with <code className="text-cyan-300">d</code> odd.
            </p>
            <p>
              2. For each random base <code className="text-cyan-300">a</code>, computes <code className="text-cyan-300">x = a^d mod n</code>.
            </p>
            <p>
              3. If <code className="text-cyan-300">x ≡ 1</code> or <code className="text-cyan-300">x ≡ n - 1</code>, passes round. Repeatedly squares <code className="text-cyan-300">s - 1</code> times checking for non-trivial square roots of 1.
            </p>
            <p>
              4. Error bound: A composite number fails each round with probability ≥ 3/4. False positive probability ≤ <strong className="text-cyan-400">(1/4)^k</strong>.
            </p>
          </div>
        </div>

        {/* Results */}
        <div className="lg:col-span-2 space-y-6">
          {result ? (
            <div className="space-y-6">
              {/* Verdict Banner */}
              <div
                className={`p-6 rounded-xl border flex items-center justify-between gap-4 ${
                  result.prime
                    ? 'bg-emerald-950/30 border-emerald-800/60 text-emerald-300'
                    : 'bg-rose-950/30 border-rose-800/60 text-rose-300'
                }`}
              >
                <div className="flex items-center gap-4">
                  {result.prime ? (
                    <ShieldCheck className="w-12 h-12 text-emerald-400 shrink-0" />
                  ) : (
                    <ShieldX className="w-12 h-12 text-rose-400 shrink-0" />
                  )}
                  <div>
                    <h2 className="text-2xl font-black tracking-wide">
                      {result.prime ? 'PROBABLY PRIME' : 'DEFINITELY COMPOSITE'}
                    </h2>
                    <p className="text-sm opacity-90 mt-1">{result.statusMessage}</p>
                  </div>
                </div>

                <div className="text-right">
                  <span className="text-xs uppercase tracking-wider block opacity-75 font-semibold">Confidence</span>
                  <span className="text-3xl font-extrabold">{result.confidencePercentage}%</span>
                </div>
              </div>

              {/* Metrics */}
              <div className="grid grid-cols-1 md:grid-cols-3 gap-4">
                <div className="bg-slate-900 p-6 rounded-xl border border-slate-800">
                  <span className="text-xs font-semibold text-slate-400 uppercase">Tested Integer</span>
                  <div className="text-lg font-mono font-bold text-slate-200 mt-2 truncate">
                    {result.number}
                  </div>
                  <p className="text-[11px] text-slate-500 mt-1">{result.number.length} digits</p>
                </div>

                <div className="bg-slate-900 p-6 rounded-xl border border-slate-800">
                  <span className="text-xs font-semibold text-slate-400 uppercase">Independent Rounds</span>
                  <div className="text-2xl font-bold text-cyan-400 mt-2">
                    {result.roundsTested} <span className="text-sm text-slate-500 font-normal">witnesses</span>
                  </div>
                  <p className="text-[11px] text-slate-500 mt-1">Error bound ≤ 4^(-{result.roundsTested})</p>
                </div>

                <div className="bg-slate-900 p-6 rounded-xl border border-slate-800">
                  <span className="text-xs font-semibold text-slate-400 uppercase">Execution Time</span>
                  <div className="text-2xl font-bold text-slate-100 mt-2">
                    {(result.executionTimeNs / 1000000).toFixed(3)} <span className="text-sm text-slate-500 font-normal">ms</span>
                  </div>
                  <p className="text-[11px] text-emerald-400 mt-1">O(k · log³ n)</p>
                </div>
              </div>

              {/* Witnesses Tested */}
              <div className="bg-slate-900 p-6 rounded-xl border border-slate-800 space-y-4">
                <h3 className="text-sm font-bold uppercase tracking-wider text-slate-300">
                  Witness Bases Tested ({result.witnessesTested?.length})
                </h3>
                <div className="flex flex-wrap gap-2 max-h-44 overflow-y-auto">
                  {result.witnessesTested?.map((w: string, idx: number) => (
                    <span
                      key={idx}
                      className="bg-slate-950 text-cyan-300 border border-slate-800 px-3 py-1 rounded-lg text-xs font-mono"
                    >
                      Base #{idx + 1}: <strong className="text-slate-100">{w}</strong>
                    </span>
                  ))}
                </div>
              </div>

              {/* Mathematical Explanation */}
              <div className="bg-slate-900 p-6 rounded-xl border border-slate-800 space-y-2 text-sm text-slate-300 leading-relaxed">
                <div className="flex items-center gap-2 font-bold text-slate-200">
                  <CheckCircle2 className="w-5 h-5 text-cyan-400" />
                  Algorithm Explanation & Theoretical Rationale
                </div>
                <p className="text-slate-400 text-xs pt-1">
                  {result.mathematicalExplanation}
                </p>
              </div>
            </div>
          ) : (
            <div className="bg-slate-900 rounded-xl border border-slate-800 p-16 text-center text-slate-500 space-y-3">
              <Sparkles className="w-12 h-12 mx-auto text-slate-700" />
              <p className="text-sm">
                Enter an integer and click "Run Miller-Rabin Test" to execute real randomized primality checking.
              </p>
            </div>
          )}
        </div>
      </div>
    </div>
  );
}
