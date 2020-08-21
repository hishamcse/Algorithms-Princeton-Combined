import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class BurrowsWheeler {

    private static final int R = 256;

    // apply Burrows-Wheeler transform,
    // reading from standard input and writing to standard output
    public static void transform() {
        String s = BinaryStdIn.readString();
        CircularSuffixArray suffixArray = new CircularSuffixArray(s);
        int n = suffixArray.length();
        for (int i = 0; i < n; i++) {
            if (suffixArray.index(i) == 0) {
                BinaryStdOut.write(i);
            }
        }
        for (int i = 0; i < n; i++) {
            int j = suffixArray.index(i);
            if (j - 1 >= 0) {
                BinaryStdOut.write(s.charAt(j - 1));
            } else {
                BinaryStdOut.write(s.charAt(n - 1));
            }
        }
        BinaryStdOut.close();
    }

    // apply Burrows-Wheeler inverse transform,
    // reading from standard input and writing to standard output
    public static void inverseTransform() {
        int first = BinaryStdIn.readInt();
        String st = BinaryStdIn.readString();
        char[] chars = st.toCharArray();
        int n = chars.length;

        int[] count = new int[R + 1];
        int[] next = new int[n];
        for (char c : chars) {
            count[c + 1]++;
        }
        for (int i = 0; i < R; i++) {
            count[i + 1] += count[i];
        }
        for (int i = 0; i < n; i++) {
            next[count[chars[i]]++] = i;
        }
        int index = next[first];
        for (int i = 0; i < n; i++, index = next[index]) {
            BinaryStdOut.write(chars[index]);
        }
        BinaryStdOut.close();
    }

    // if args[0] is "-", apply Burrows-Wheeler transform
    // if args[0] is "+", apply Burrows-Wheeler inverse transform
    public static void main(String[] args) {
        if (args[0].equals("-")) transform();
        else if (args[0].equals("+")) inverseTransform();
        else throw new IllegalArgumentException("Illegal command line argument");
//        String s = StdIn.readLine();
//        if (s.equals("-")) transform();
//        else if (s.equals("+")) inverseTransform();
//        else throw new IllegalArgumentException("Illegal command line argument");
    }

}
