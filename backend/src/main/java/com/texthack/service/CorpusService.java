package com.texthack.service;

import com.texthack.model.Document;
import com.texthack.indexing.InvertedIndex;
import com.texthack.datastructure.CustomArrayList;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

@Service
public class CorpusService {

    private final CustomArrayList<Document> documents;
    private final InvertedIndex invertedIndex;
    private final AtomicInteger searchCounter;

    public CorpusService() {
        this.documents = new CustomArrayList<>();
        this.invertedIndex = new InvertedIndex();
        this.searchCounter = new AtomicInteger(0);
        seedDevelopmentCorpus();
    }

    private void seedDevelopmentCorpus() {
        // Document 1: English - Algorithms and Computational Complexity
        addDocument(new Document(
            "doc-1",
            "Algorithms and Computational Complexity",
            "English",
            "An algorithm is a finite sequence of rigorous instructions, typically used to solve a class of specific problems or to perform a computation. Algorithms are used as specifications for performing calculations and data processing. More advanced algorithms can perform automated deductions (referred to as automated reasoning) and use mathematical and logical tests to direct the code execution through various routes. In computational complexity theory, problems are classified into complexity classes such as P, NP, NP-complete, and NP-hard. String searching algorithms like Knuth-Morris-Pratt and Rabin-Karp provide linear time pattern detection."
        ));

        // Document 2: English - Graph Theory and Network Flow
        addDocument(new Document(
            "doc-2",
            "Network Flow and Maximum Flow Minimum Cut",
            "English",
            "In graph theory, a flow network is a directed graph where each edge has a capacity and receives a flow. The amount of flow on an edge cannot exceed its capacity. In operations research, maximum flow problems involve finding a feasible flow through a flow network that obtains the maximum possible flow rate. The max-flow min-cut theorem states that in a flow network, the maximum amount of flow passing from the source to the sink is equal to the total weight of the edges in the minimum cut. Algorithms for solving maximum flow include the Ford-Fulkerson method, Edmonds-Karp algorithm, and Dinic algorithm."
        ));

        // Document 3: English - Dynamic Programming and Edit Distance
        addDocument(new Document(
            "doc-3",
            "Dynamic Programming and String Distance",
            "English",
            "Dynamic programming is both a mathematical optimization method and an algorithmic paradigm. It addresses complex problems by breaking them down into simpler subproblems recursively. Levenshtein distance is a string metric for measuring the difference between two sequences. Informally, the Levenshtein distance between two words is the minimum number of single-character edits (insertions, deletions, or substitutions) required to change one word into the other. Wagner-Fischer algorithm computes this edit distance in quadratic time using a dynamic programming matrix."
        ));

        // Document 4: Hindi (Indian Language Wikipedia sample) - कलन विधि (Algorithm)
        addDocument(new Document(
            "doc-4",
            "कलन विधि (Algorithm in Hindi)",
            "Hindi",
            "कलन विधि (Algorithm) गणित और संगणक विज्ञान में किसी कार्य को संपन्न करने हेतु सुपरिभाषित चरणों का एक परिमित क्रम है। यह किसी विशिष्ट समस्या का समाधान निकालने या गणना करने की स्पष्ट कार्यविधि प्रदान करती है। संगणक प्रोग्रामिंग में एल्गोरिद्म का अत्यंत महत्वपूर्ण स्थान है। स्ट्रिंग मिलान और डेटाबेस खोज के लिए विभिन्न उन्नत एल्गोरिदम जैसे केएमपी (KMP) और रॉबिन-कार्प का प्रयोग किया जाता है। भारतीय गणितज्ञों जैसे ब्रह्मगुप्त और आर्यभट ने प्राचीन काल में बीजगणितीय कलन विधियों का विकास किया था।"
        ));

        // Document 5: Telugu (Indian Language Wikipedia sample) - అల్గారిథమ్ (Algorithm)
        addDocument(new Document(
            "doc-5",
            "అల్గారిథమ్ మరియు కంప్యూటర్ సైన్స్ (Telugu)",
            "Telugu",
            "అల్గారిథమ్ లేదా గణన క్రమ విధానం అనేది ఒక నిర్దిష్ట సమస్యను పరిష్కరించడానికి లేదా ఒక నిర్దిష్ట గణనను పూర్తి చేయడానికి నిర్దేశించిన దశల క్రమం. కంప్యూటర్ సైన్స్ మరియు డేటా స్ట్రక్చర్లలో సమాచార శోధన (Information Retrieval) మరియు శోధన ఇంజిన్లలో సరైన ఫలితాలను అందించడానికి అధునాతన అల్గారిథమ్‌లు ఉపయోగించబడతాయి. స్ట్రింగ్ శోధన, నెట్‌వర్క్ ప్రవాహం మరియు డైనమిక్ ప్రోగ్రామింగ్ ద్వారా వేగవంతమైన సమాచార మార్పిడి సాధ్యమవుతుంది."
        ));

        // Document 6: Tamil (Indian Language Wikipedia sample) - படிமுறைத் தீர்வு (Algorithm)
        addDocument(new Document(
            "doc-6",
            "படிமுறைத் தீர்வு (Algorithm in Tamil)",
            "Tamil",
            "படிமுறைத் தீர்வு (Algorithm) என்பது கணிப்பொறி அறிவியலிலும் கணிதத்திலும் ஒரு குறிப்பிட்ட சிக்கலைத் தீர்ப்பதற்கு அல்லது ஒரு கணக்கீட்டைச் செய்வதற்குக் கொடுக்கப்படும் முறையான வழிமுறைகளின் வரிசையாகும். தரவு கட்டமைப்புகள் மற்றும் தகவல் மீட்டெடுப்பு அமைப்புகளில் உரை தேடல், சரம் ஒப்பீடு மற்றும் வரைபட நெட்வொர்க் ஓட்டம் ஆகியவற்றிற்கு மேம்பட்ட வழிமுறைகள் பயன்படுத்தப்படுகின்றன."
        ));

        // Document 7: Sanskrit / Philosophy of Mathematics
        addDocument(new Document(
            "doc-7",
            "गणितशास्त्रम् एवं कलनविधिः (Sanskrit)",
            "Sanskrit",
            "प्राचीनभारते गणितशास्त्रस्य विकासः अत्यंतं समृद्धः आसीत्। आर्यभटः, ब्रह्मगुप्तः, भास्कराचार्यः च विविधानां समीकरणानि समाधातुं कलनविधीनां रचनां कृतवन्तः। सङ्गणकविज्ञाने आधुनिक-अल्गोरिदम-सिद्धान्ताः प्राचीनतार्किकपद्धतीनां सह सम्बद्धाः सन्ति।"
        ));

        // Document 8: English - Suffix Structures and Text Indexing
        addDocument(new Document(
            "doc-8",
            "Suffix Arrays and Kasai Longest Common Prefix",
            "English",
            "A suffix array is a sorted array of all suffixes of a string. It is a space-efficient data structure widely used in full-text indices, data compression algorithms, and bioinformatics. When paired with the Longest Common Prefix (LCP) array, suffix arrays can answer pattern matching and substring queries in optimal time. Kasai's algorithm computes the LCP array in linear O(n) time from the suffix array and original text. Document similarity metrics leverage LCP values to identify maximal shared substring components between textual corpora."
        ));

        // Document 9: English - Randomized Algorithms and Primality
        addDocument(new Document(
            "doc-9",
            "Randomized Algorithms and Miller-Rabin Primality",
            "English",
            "A randomized algorithm employs a degree of randomness as part of its logic. In public-key cryptography such as RSA, generating large primes requires rapid primality testing. The Miller-Rabin primality test is an efficient probabilistic algorithm that determines whether a given number is composite or probably prime. By testing multiple independent random witness bases, the probability of mistakenly labeling a composite number as prime diminishes exponentially to less than (1/4)^k."
        ));

        // Build the inverted search index on startup
        rebuildIndex();
    }

    public synchronized void addDocument(Document doc) {
        documents.add(doc);
        invertedIndex.indexDocument(doc);
    }

    public synchronized void rebuildIndex() {
        invertedIndex.clear();
        for (int i = 0; i < documents.size(); i++) {
            Document d = documents.get(i);
            invertedIndex.indexDocument(d);
        }
    }

    public CustomArrayList<Document> getAllDocuments() {
        return documents;
    }

    public Document getDocumentById(String id) {
        if (id == null) return null;
        for (int i = 0; i < documents.size(); i++) {
            if (documents.get(i).getId().equals(id)) {
                return documents.get(i);
            }
        }
        return null;
    }

    public InvertedIndex getInvertedIndex() {
        return invertedIndex;
    }

    public void incrementSearchCount() {
        searchCounter.incrementAndGet();
    }

    public int getSearchCount() {
        return searchCounter.get();
    }

    public long getTotalCorpusSizeBytes() {
        long total = 0;
        for (int i = 0; i < documents.size(); i++) {
            total += documents.get(i).getSizeBytes();
        }
        return total;
    }
}
