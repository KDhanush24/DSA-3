import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { Database, RefreshCw, FileText, Globe, Plus, CheckCircle, Search } from 'lucide-react';

export default function Corpus() {
  const [documents, setDocuments] = useState<any[]>([]);
  const [loading, setLoading] = useState(false);
  const [reindexing, setReindexing] = useState(false);
  const [selectedDoc, setSelectedDoc] = useState<any>(null);
  const [filterLang, setFilterLang] = useState('ALL');
  const [reindexStats, setReindexStats] = useState<any>(null);

  // New Document modal state
  const [showAddModal, setShowAddModal] = useState(false);
  const [newTitle, setNewTitle] = useState('');
  const [newLanguage, setNewLanguage] = useState('English');
  const [newContent, setNewContent] = useState('');

  const fetchDocuments = async () => {
    setLoading(true);
    try {
      const res = await axios.get('http://localhost:8080/api/corpus');
      setDocuments(res.data);
      if (res.data.length > 0 && !selectedDoc) {
        setSelectedDoc(res.data[0]);
      }
    } catch (err) {
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchDocuments();
  }, []);

  const handleReindex = async () => {
    setReindexing(true);
    try {
      const res = await axios.post('http://localhost:8080/api/corpus/reindex');
      setReindexStats(res.data);
      fetchDocuments();
    } catch (err) {
      console.error(err);
    } finally {
      setReindexing(false);
    }
  };

  const handleAddDocument = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!newTitle.trim() || !newContent.trim()) return;

    try {
      const res = await axios.post('http://localhost:8080/api/corpus/add', {
        title: newTitle,
        language: newLanguage,
        content: newContent
      });
      setShowAddModal(false);
      setNewTitle('');
      setNewContent('');
      fetchDocuments();
      setSelectedDoc(res.data);
    } catch (err) {
      console.error(err);
    }
  };

  const filteredDocs = documents.filter(d => 
    filterLang === 'ALL' || d.language.toLowerCase() === filterLang.toLowerCase()
  );

  return (
    <div className="p-8 max-w-7xl mx-auto space-y-8">
      <div className="flex flex-col md:flex-row md:items-center justify-between gap-4 border-b border-slate-800 pb-6">
        <div>
          <h1 className="text-3xl font-bold text-slate-100 flex items-center gap-3">
            <Database className="w-8 h-8 text-cyan-400" />
            Corpus Management & Inverted Index
          </h1>
          <p className="text-slate-400 mt-2">
            Multilingual textual dataset supporting Indian-language Unicode (Hindi, Telugu, Tamil, Sanskrit) and real inverted index postings.
          </p>
        </div>

        <div className="flex items-center gap-3">
          <button
            onClick={() => setShowAddModal(true)}
            className="flex items-center gap-2 bg-slate-800 hover:bg-slate-700 text-slate-200 px-4 py-2 rounded-lg text-sm font-semibold transition-colors border border-slate-700"
          >
            <Plus className="w-4 h-4 text-cyan-400" />
            Add Document
          </button>
          <button
            onClick={handleReindex}
            disabled={reindexing}
            className="flex items-center gap-2 bg-cyan-600 hover:bg-cyan-500 disabled:opacity-50 text-slate-900 px-4 py-2 rounded-lg text-sm font-semibold transition-colors shadow-lg shadow-cyan-950"
          >
            <RefreshCw className={`w-4 h-4 ${reindexing ? 'animate-spin' : ''}`} />
            {reindexing ? 'Reindexing...' : 'Rebuild Index'}
          </button>
        </div>
      </div>

      {reindexStats && (
        <div className="bg-emerald-950/40 border border-emerald-800/60 p-4 rounded-xl flex items-center gap-4 text-emerald-300">
          <CheckCircle className="w-5 h-5 text-emerald-400 shrink-0" />
          <div className="text-sm">
            <span className="font-semibold">Search Index Rebuilt:</span> Indexed {reindexStats.indexedDocuments} documents, extracting {reindexStats.uniqueTerms} unique dictionary terms with {reindexStats.totalOccurrences} inverted index postings using custom hash chaining.
          </div>
        </div>
      )}

      {/* Language filter pills */}
      <div className="flex flex-wrap items-center gap-2">
        <span className="text-xs font-semibold text-slate-400 uppercase mr-2 flex items-center gap-1">
          <Globe className="w-3.5 h-3.5" /> Language:
        </span>
        {['ALL', 'English', 'Hindi', 'Telugu', 'Tamil', 'Sanskrit'].map(lang => (
          <button
            key={lang}
            onClick={() => setFilterLang(lang)}
            className={`px-3 py-1 rounded-full text-xs font-medium transition-colors ${
              filterLang === lang
                ? 'bg-cyan-500 text-slate-950 font-bold'
                : 'bg-slate-900 text-slate-400 hover:bg-slate-800 border border-slate-800'
            }`}
          >
            {lang}
          </button>
        ))}
      </div>

      <div className="grid grid-cols-1 lg:grid-cols-3 gap-8">
        {/* Document List */}
        <div className="lg:col-span-1 space-y-3">
          <div className="text-xs font-semibold text-slate-400 uppercase tracking-wider mb-2">
            Available Documents ({filteredDocs.length})
          </div>

          <div className="space-y-2 max-h-[600px] overflow-y-auto pr-2">
            {filteredDocs.map(doc => (
              <div
                key={doc.id}
                onClick={() => setSelectedDoc(doc)}
                className={`p-4 rounded-xl border cursor-pointer transition-all ${
                  selectedDoc?.id === doc.id
                    ? 'bg-slate-900 border-cyan-500/70 shadow-lg shadow-cyan-950/40'
                    : 'bg-slate-900/60 border-slate-800/80 hover:bg-slate-900 hover:border-slate-700'
                }`}
              >
                <div className="flex items-start justify-between gap-2 mb-1.5">
                  <h3 className="font-semibold text-sm text-slate-200 line-clamp-1">{doc.title}</h3>
                  <span className="text-[10px] uppercase font-bold tracking-wider px-2 py-0.5 rounded bg-slate-800 text-cyan-400 border border-slate-700">
                    {doc.language}
                  </span>
                </div>
                <div className="flex items-center gap-4 text-xs text-slate-400">
                  <span>ID: <code className="text-slate-300">{doc.id}</code></span>
                  <span>{doc.wordCount} words</span>
                  <span>{(doc.sizeBytes / 1024).toFixed(1)} KB</span>
                </div>
              </div>
            ))}
          </div>
        </div>

        {/* Selected Document Content */}
        <div className="lg:col-span-2">
          {selectedDoc ? (
            <div className="bg-slate-900 rounded-xl border border-slate-800 p-6 space-y-6">
              <div className="flex flex-col sm:flex-row sm:items-center justify-between pb-4 border-b border-slate-800 gap-2">
                <div>
                  <h2 className="text-xl font-bold text-slate-100">{selectedDoc.title}</h2>
                  <div className="flex items-center gap-4 text-xs text-slate-400 mt-1">
                    <span>Language: <strong className="text-cyan-400">{selectedDoc.language}</strong></span>
                    <span>Document ID: <code className="text-slate-300">{selectedDoc.id}</code></span>
                    <span>Word Count: <strong className="text-slate-300">{selectedDoc.wordCount}</strong></span>
                    <span>Size: <strong className="text-slate-300">{selectedDoc.sizeBytes} bytes</strong></span>
                  </div>
                </div>
                <span className="self-start sm:self-center px-2.5 py-1 rounded-full text-xs font-semibold bg-emerald-950 text-emerald-400 border border-emerald-800 flex items-center gap-1.5">
                  <span className="w-2 h-2 rounded-full bg-emerald-400"></span>
                  Indexed
                </span>
              </div>

              <div>
                <h4 className="text-xs font-semibold uppercase text-slate-400 tracking-wider mb-2">
                  Document Content (Unicode Preserved)
                </h4>
                <div className="bg-slate-950 p-4 rounded-lg border border-slate-800 text-slate-300 leading-relaxed text-sm whitespace-pre-wrap max-h-96 overflow-y-auto">
                  {selectedDoc.content}
                </div>
              </div>
            </div>
          ) : (
            <div className="bg-slate-900 rounded-xl border border-slate-800 p-12 text-center text-slate-500">
              Select a document to inspect its raw Unicode contents and metadata.
            </div>
          )}
        </div>
      </div>

      {/* Add Document Modal */}
      {showAddModal && (
        <div className="fixed inset-0 bg-slate-950/80 backdrop-blur-sm z-50 flex items-center justify-center p-4">
          <div className="bg-slate-900 border border-slate-800 rounded-2xl p-6 max-w-xl w-full shadow-2xl space-y-4">
            <h3 className="text-lg font-bold text-slate-100">Add New Document to Corpus</h3>
            <form onSubmit={handleAddDocument} className="space-y-4">
              <div>
                <label className="block text-xs font-semibold text-slate-400 uppercase mb-1">Title</label>
                <input
                  type="text"
                  required
                  value={newTitle}
                  onChange={e => setNewTitle(e.target.value)}
                  placeholder="e.g. Graph Algorithms in Computer Science"
                  className="w-full bg-slate-950 border border-slate-800 rounded-lg p-2.5 text-sm text-slate-200 focus:outline-none focus:border-cyan-500"
                />
              </div>

              <div>
                <label className="block text-xs font-semibold text-slate-400 uppercase mb-1">Language</label>
                <select
                  value={newLanguage}
                  onChange={e => setNewLanguage(e.target.value)}
                  className="w-full bg-slate-950 border border-slate-800 rounded-lg p-2.5 text-sm text-slate-200 focus:outline-none focus:border-cyan-500"
                >
                  <option value="English">English</option>
                  <option value="Hindi">Hindi</option>
                  <option value="Telugu">Telugu</option>
                  <option value="Tamil">Tamil</option>
                  <option value="Sanskrit">Sanskrit</option>
                </select>
              </div>

              <div>
                <label className="block text-xs font-semibold text-slate-400 uppercase mb-1">Text Content</label>
                <textarea
                  required
                  rows={6}
                  value={newContent}
                  onChange={e => setNewContent(e.target.value)}
                  placeholder="Enter or paste text (Unicode fully supported)..."
                  className="w-full bg-slate-950 border border-slate-800 rounded-lg p-2.5 text-sm text-slate-200 focus:outline-none focus:border-cyan-500 leading-relaxed resize-none"
                />
              </div>

              <div className="flex justify-end gap-3 pt-2">
                <button
                  type="button"
                  onClick={() => setShowAddModal(false)}
                  className="px-4 py-2 rounded-lg text-sm text-slate-400 hover:text-slate-200 hover:bg-slate-800"
                >
                  Cancel
                </button>
                <button
                  type="submit"
                  className="bg-cyan-600 hover:bg-cyan-500 text-slate-900 font-bold px-4 py-2 rounded-lg text-sm"
                >
                  Add & Index
                </button>
              </div>
            </form>
          </div>
        </div>
      )}
    </div>
  );
}
