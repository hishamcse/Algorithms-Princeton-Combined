import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class MoveToFront {

    private final static int R = 256;

    // apply move-to-front encoding, reading from standard input and writing to standard output
    public static void encode() {
        char[] arr = new char[R];
        for (char i = 0; i < R; i++) {
            arr[i] = i;
        }
        int i;
        while (!BinaryStdIn.isEmpty()) {
            char c = BinaryStdIn.readChar();
            for (i = 0; i < R; i++) {
                if (c == arr[i]) {
                    break;
                }
            }
            BinaryStdOut.write((char) i);
            for (int j = i; j > 0; j--) {
                arr[j] = arr[j - 1];
            }
            arr[0] = c;
        }
        BinaryStdOut.close();
    }

    // apply move-to-front decoding, reading from standard input and writing to standard output
    public static void decode() {
        char[] arr = new char[R];
        for (char i = 0; i < R; i++) {
            arr[i] = i;
        }
        while (!BinaryStdIn.isEmpty()) {
            int x = BinaryStdIn.readChar();
            char c = arr[x];
            BinaryStdOut.write(c);
            for (int j = x; j > 0; j--) {
                arr[j] = arr[j - 1];
            }
            arr[0] = c;
        }
        BinaryStdOut.close();
    }

    // if args[0] is "-", apply move-to-front encoding
    // if args[0] is "+", apply move-to-front decoding
    public static void main(String[] args) {
        if (args[0].equals("-")) encode();
        else if (args[0].equals("+")) decode();
        else throw new IllegalArgumentException("Illegal command line argument");
//        String s = StdIn.readLine();
//        if (s.equals("-")) encode();
//        else if (s.equals("+")) decode();
//        else throw new IllegalArgumentException("Illegal command line argument");
    }

}
