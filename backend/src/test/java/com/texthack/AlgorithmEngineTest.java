package com.texthack;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import com.texthack.datastructure.*;
import com.texthack.algorithm.string.*;
import com.texthack.algorithm.dp.*;
import com.texthack.algorithm.suffix.*;
import com.texthack.algorithm.flow.*;
import com.texthack.algorithm.approximation.*;
import com.texthack.algorithm.randomized.*;
import com.texthack.preprocessing.TextPreprocessor;
import com.texthack.indexing.InvertedIndex;
import com.texthack.model.Document;

public class AlgorithmEngineTest {

    @Test
    public void testCustomDataStructures() {
        CustomArrayList<String> list = new CustomArrayList<>();
        list.add("Alpha");
        list.add("Beta");
        assertEquals(2, list.size());
        assertEquals("Alpha", list.get(0));

        CustomQueue<Integer> q = new CustomQueue<>();
        q.enqueue(10);
        q.enqueue(20);
        assertEquals(10, q.dequeue());
        assertEquals(20, q.dequeue());

        CustomStack<String> s = new CustomStack<>();
        s.push("First");
        s.push("Second");
        assertEquals("Second", s.pop());
        assertEquals("First", s.pop());

        CustomHashTable<String, Integer> map = new CustomHashTable<>();
        map.put("key1", 100);
        map.put("key2", 200);
        assertEquals(100, map.get("key1"));
        assertEquals(200, map.get("key2"));
        assertTrue(map.containsKey("key1"));
        assertNull(map.get("nonexistent"));
    }

    @Test
    public void testStringAlgorithms() {
        String text = "ABABDABACDABABCABAB";
        String pattern = "ABABCABAB";

        CustomArrayList<Integer> kmp = KMP.search(text, pattern);
        assertEquals(1, kmp.size());
        assertEquals(10, kmp.get(0));

        CustomArrayList<Integer> z = ZAlgorithm.search(text, pattern);
        assertEquals(1, z.size());
        assertEquals(10, z.get(0));

        CustomArrayList<Integer> rk = RabinKarp.search(text, pattern);
        assertEquals(1, rk.size());
        assertEquals(10, rk.get(0));
    }

    @Test
    public void testDynamicProgrammingEditDistance() {
        int lev = Levenshtein.calculate("kitten", "sitting");
        assertEquals(3, lev);

        int dam = DamerauLevenshtein.calculate("teh", "the");
        assertEquals(1, dam); // transposition of e and h
    }

    @Test
    public void testSuffixArrayAndKasaiLCP() {
        String s = "banana$";
        int[] sa = SuffixArray.buildSuffixArray(s);
        int[] lcp = KasaiLCP.buildLCPArray(s, sa);

        assertEquals(s.length(), sa.length);
        assertEquals(s.length(), lcp.length);
    }

    @Test
    public void testNetworkFlow() {
        CustomFlowGraph g = new CustomFlowGraph(4);
        g.addEdge(0, 1, 3);
        g.addEdge(0, 2, 2);
        g.addEdge(1, 2, 5);
        g.addEdge(1, 3, 2);
        g.addEdge(2, 3, 3);

        EdmondsKarp.FlowResult ek = EdmondsKarp.computeMaxFlow(g, 0, 3);
        assertEquals(5, ek.maxFlow);
    }

    @Test
    public void testVertexCoverApproximation() {
        CustomArrayList<VertexCoverApproximation.Edge> edges = new CustomArrayList<>();
        edges.add(new VertexCoverApproximation.Edge(0, 1));
        edges.add(new VertexCoverApproximation.Edge(1, 2));
        edges.add(new VertexCoverApproximation.Edge(2, 3));

        VertexCoverApproximation.ApproximationResult res = VertexCoverApproximation.solve(4, edges);
        assertTrue(res.approxSize <= 2 * res.optimalSize);
    }

    @Test
    public void testMillerRabinPrimality() {
        MillerRabin.PrimalityResult primeRes = MillerRabin.testPrimality("9999999967", 10);
        assertTrue(primeRes.isPrime);

        MillerRabin.PrimalityResult compRes = MillerRabin.testPrimality("9999999969", 10);
        assertFalse(compRes.isPrime); // 9999999969 is divisible by 3
    }

    @Test
    public void testMultilingualPreprocessingAndInvertedIndex() {
        String hindi = "कलन विधि गणित और संगणक विज्ञान";
        CustomArrayList<TextPreprocessor.Token> tokens = TextPreprocessor.process(hindi);
        assertTrue(tokens.size() > 0);

        InvertedIndex index = new InvertedIndex();
        Document doc = new Document("d1", "Hindi Doc", "Hindi", hindi);
        index.indexDocument(doc);
        assertTrue(index.getUniqueTermsCount() > 0);
    }
}
