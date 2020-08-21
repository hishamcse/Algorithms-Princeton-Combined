import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.ST;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.StdIn;

public class WordNet {

    private final Digraph digraph;
    private final SAP sap;
    private final ST<Integer, SET<String>> netNouns;
    private final ST<String, SET<Integer>> ids;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        if (synsets == null || hypernyms == null) {
            throw new IllegalArgumentException("argument is null");
        }
        netNouns = new ST<>();
        ids = new ST<>();
        In in = new In(synsets);
        while (in.hasNextLine()) {
            SET<String> inside = new SET<>();
            String[] strings = in.readLine().split(",");
            String[] str = strings[1].split(" ");
            for (String s : str) {
                inside.add(s);
                SET<Integer> ints = ids.get(s);
                if (ints == null) {
                    ints = new SET<>();
                    ids.put(s, ints);
                }
                ints.add(Integer.parseInt(strings[0]));
            }
            netNouns.put(Integer.parseInt(strings[0]), inside);
        }

        digraph = new Digraph(netNouns.size());
        in = new In(hypernyms);
        while (in.hasNextLine()) {
            String[] strings = in.readLine().split(",");
            for (int i = 1; i < strings.length; i++) {
                digraph.addEdge(Integer.parseInt(strings[0]), Integer.parseInt(strings[i]));
            }
        }

        DirectedCycle d = new DirectedCycle(digraph);
        if (d.hasCycle()) {
            throw new IllegalArgumentException("Is not a DAG");
        } else if (!rootedDAG()) {
            throw new IllegalArgumentException("Is not a rooted DAG");
        }

        sap = new SAP(digraph);
    }

    private boolean rootedDAG() {
        int count = 0;
        for (int i = 0; i < digraph.V(); i++) {
            if (digraph.indegree(i) != 0 && digraph.outdegree(i) == 0) {
                count++;
            }
        }
        return count == 1;
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return ids.keys();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        if (word == null) {
            throw new IllegalArgumentException("argument is null");
        }
        return ids.get(word) != null;
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        if (nounA == null || nounB == null) {
            throw new IllegalArgumentException("argument is null");
        }
        if (!isNoun(nounA) || !isNoun(nounB)) {
            throw new IllegalArgumentException("is not wordnet noun");
        }
        return sap.length(ids.get(nounA), ids.get(nounB));
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        if (nounA == null || nounB == null) {
            throw new IllegalArgumentException("argument is null");
        }
        if (!isNoun(nounA) || !isNoun(nounB)) {
            throw new IllegalArgumentException("is not wordnet noun");
        }
        int s = sap.ancestor(ids.get(nounA), ids.get(nounB));
        SET<String> res = netNouns.get(s);
        String outcome = "";
        for (String t : res) {
            outcome = outcome.concat(t + " ");
        }
        return outcome;
    }

    // do unit testing of this class
    public static void main(String[] args) {
        WordNet wordNet = new WordNet(StdIn.readString(), StdIn.readString());
        System.out.println(wordNet.distance("a", "c"));
        System.out.println(wordNet.sap("a", "c"));
        System.out.println(wordNet.nouns());
        System.out.println(wordNet.isNoun("d"));
        System.out.println(wordNet.isNoun("i"));
    }
}
