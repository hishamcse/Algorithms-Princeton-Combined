import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class BoggleSolver {

    private final Node root;
    private final int[] rowLabel = {-1, 0, 1, -1, 1, -1, 0, 1};  // must maintain serial
    private final int[] colLabel = {-1, -1, -1, 0, 0, 1, 1, 1};  // must maintain serial

    private static class Node {
        Node[] next = new Node[26];
        //        boolean end = false;   // if it is true,then the word is complete(at the end) and the word belongs to the dictionary
        int value = -1;        // or we can handle the end of string with value,any value except -1 will indicate the end  of string
    }

    // Initializes the data structure using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
    public BoggleSolver(String[] dictionary) {
        if (dictionary == null) {
            throw new IllegalArgumentException("argument is null");
        }
        root = new Node();

        for (String s : dictionary) {
            createNode(s);
        }
    }

    private void createNode(String s) {
        Node x = root;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (x.next[c - 'A'] == null) {
                x.next[c - 'A'] = new Node();
            }
            x = x.next[c - 'A'];
        }
//        x.end = true;
        x.value=s.length();
    }

    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board) {
        if (board == null) {
            throw new IllegalArgumentException("argument is null");
        }
        int rows = board.rows();
        int cols = board.cols();
        SET<String> validWords = new SET<>();
        boolean[][] visited = new boolean[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                dfs(board, root, i, j, "", validWords, visited);
            }
        }
        return validWords;
    }

    private void dfs(BoggleBoard board, Node x, int i, int j, String word, SET<String> validWords, boolean[][] visited) {
        char c = board.getLetter(i, j);
        if (x == null || x.next[c - 'A'] == null) {
            return;
        }
        String s;
        if (c == 'Q') {
            s = word + "QU";
            x = x.next['Q' - 'A'];
            if (x.next['U' - 'A'] == null) {
                return;
            }
            x = x.next['U' - 'A'];
        } else {
            s = word + c;
            x = x.next[c - 'A'];
        }
//        if (s.length() >= 3 && x.end) {
//            validWords.add(s);
//        }
        if (s.length() >= 3 && x.value!=-1) {
            validWords.add(s);
        }
        visited[i][j] = true;
        int m = board.rows();
        int n = board.cols();
        for (int p = 0; p < rowLabel.length; p++) {
            int xi = i + rowLabel[p];
            int yj = j + colLabel[p];
            if (xi < 0 || xi >= m || yj < 0 || yj >= n || visited[xi][yj]) {
                continue;
            }
            dfs(board, x, xi, yj, s, validWords, visited);
        }
        visited[i][j] = false;
    }

    // Returns the score of the given word if it is in the dictionary, zero otherwise.
    // (You can assume the word contains only the uppercase letters A through Z.)
    public int scoreOf(String word) {
        if (word == null) {
            return 0;
        }
        if (contains(word)) {
            int length = word.length();
            if (length <= 2) {
                return 0;
            }
            if (length == 3 || length == 4) {
                return 1;
            }
            if (length == 5) {
                return 2;
            }
            if (length == 6) {
                return 3;
            }
            if (length == 7) {
                return 5;
            }
            return 11;
        }
        return 0;
    }

    private boolean contains(String s) {
        Node x = root;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (x.next[c - 'A'] == null) {
                return false;
            }
            x = x.next[c - 'A'];
        }
//        return x.end;
        return x.value!=-1;
    }

    public static void main(String[] args) {
        In in = new In(StdIn.readLine());
        String[] dictionary = in.readAllStrings();
        BoggleSolver solver = new BoggleSolver(dictionary);
        BoggleBoard board = new BoggleBoard(StdIn.readLine());
        int score = 0;
        for (String word : solver.getAllValidWords(board)) {
            StdOut.println(word);
            score += solver.scoreOf(word);
        }
        StdOut.println("Score = " + score);
    }
}