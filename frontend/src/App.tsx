import React from 'react';
import { BrowserRouter, Routes, Route, Link, useLocation } from 'react-router-dom';
import {
  LayoutDashboard,
  Search,
  SpellCheck,
  GitCompare,
  Info
} from 'lucide-react';

import Dashboard from './pages/Dashboard';
import PatternSearch from './pages/PatternSearch';
import FuzzySearch from './pages/FuzzySearch';
import DocumentSimilarity from './pages/DocumentSimilarity';
import AboutProject from './pages/AboutProject';

export default function App() {
  return (
    <BrowserRouter>
      <AppContent />
    </BrowserRouter>
  );
}

function AppContent() {
  const location = useLocation();
  const isActive = (path: string) => location.pathname === path;

  return (
    <div className="flex h-screen bg-slate-950 text-slate-100 font-sans antialiased overflow-hidden">
      {/* Sidebar Navigation */}
      <aside className="w-64 bg-slate-900 border-r border-slate-800 flex flex-col shrink-0 select-none">
        {/* Brand Header */}
        <div className="p-5 border-b border-slate-800 flex items-center justify-between">
          <div>
            <h1 className="text-xl font-black tracking-wider text-transparent bg-clip-text bg-gradient-to-r from-cyan-400 to-blue-400">
              TEXT HACK
            </h1>
            <p className="text-[11px] font-semibold text-slate-400 tracking-wide">
              Mini Search Engine & Information Retrieval
            </p>
          </div>
        </div>

        {/* Navigation Links */}
        <nav className="flex-1 overflow-y-auto p-3 space-y-1 text-xs font-medium">
          <Link
            to="/"
            className={`flex items-center gap-2.5 px-3 py-2 rounded-lg transition-colors ${
              isActive('/') ? 'bg-cyan-950 text-cyan-400 font-bold border border-cyan-800/60' : 'text-slate-300 hover:bg-slate-800/80'
            }`}
          >
            <LayoutDashboard className="w-4 h-4 text-cyan-400" />
            <span>Dashboard</span>
          </Link>

          {/* Search section */}
          <div className="pt-3 pb-1 px-3 text-[10px] font-bold text-slate-500 uppercase tracking-wider">
            Search
          </div>
          <Link
            to="/search/pattern"
            className={`flex items-center gap-2.5 px-3 py-2 rounded-lg transition-colors pl-4 ${
              isActive('/search/pattern') ? 'bg-cyan-950 text-cyan-400 font-bold border border-cyan-800/60' : 'text-slate-300 hover:bg-slate-800/80'
            }`}
          >
            <Search className="w-4 h-4 text-cyan-400" />
            <span>Pattern Search</span>
          </Link>
          <Link
            to="/search/fuzzy"
            className={`flex items-center gap-2.5 px-3 py-2 rounded-lg transition-colors pl-4 ${
              isActive('/search/fuzzy') ? 'bg-cyan-950 text-cyan-400 font-bold border border-cyan-800/60' : 'text-slate-300 hover:bg-slate-800/80'
            }`}
          >
            <SpellCheck className="w-4 h-4 text-blue-400" />
            <span>Fuzzy Search</span>
          </Link>
          <Link
            to="/search/similarity"
            className={`flex items-center gap-2.5 px-3 py-2 rounded-lg transition-colors pl-4 ${
              isActive('/search/similarity') ? 'bg-cyan-950 text-cyan-400 font-bold border border-cyan-800/60' : 'text-slate-300 hover:bg-slate-800/80'
            }`}
          >
            <GitCompare className="w-4 h-4 text-indigo-400" />
            <span>Document Similarity</span>
          </Link>

          {/* About section */}
          <div className="pt-4 pb-1 px-3 text-[10px] font-bold text-slate-500 uppercase tracking-wider">
            Project Information
          </div>
          <Link
            to="/about"
            className={`flex items-center gap-2.5 px-3 py-2 rounded-lg transition-colors ${
              isActive('/about') ? 'bg-cyan-950 text-cyan-400 font-bold border border-cyan-800/60' : 'text-slate-300 hover:bg-slate-800/80'
            }`}
          >
            <Info className="w-4 h-4 text-slate-400" />
            <span>About Project</span>
          </Link>
        </nav>

        {/* Footer info */}
        <div className="p-3 border-t border-slate-800 bg-slate-900/60 text-[10px] text-slate-500 text-center">
          DSA-3 Information Retrieval System
        </div>
      </aside>

      {/* Main View Area */}
      <main className="flex-1 overflow-y-auto bg-slate-950">
        <Routes>
          <Route path="/" element={<Dashboard />} />
          <Route path="/search/pattern" element={<PatternSearch />} />
          <Route path="/search/fuzzy" element={<FuzzySearch />} />
          <Route path="/search/similarity" element={<DocumentSimilarity />} />
          <Route path="/about" element={<AboutProject />} />
          <Route path="*" element={<Dashboard />} />
        </Routes>
      </main>
    </div>
  );
}
