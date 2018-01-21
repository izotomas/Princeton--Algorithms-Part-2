package assignments.a4_Boggle;

/**
 * Author:  tomasizo
 * Contact: tomas@izo.sk
 * Date:    24/02/2017
 * Description :
 */
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;
import java.util.HashSet;
public class BoggleSolver {
    private TwentySixTrieST<Integer> dict;
    private HashSet<String> words;

    private class Node {
        private Object val;
        private int depth;
        private boolean hasChild;
        private Node[] next;

        Node(int R, int d) {
            next = new Node[R];
            depth = d;
            hasChild = false;
        }
    }

    private class TwentySixTrieST<Value> {
        private static final int R = 26;
        private Node root = new Node(R, 0);

        public boolean contains(String key, Node node) {
            return get(key, node) != null;
        }

        public Node searchPrefix(String prefix, Node node) {
            if (node == null) {
                return get(root, prefix, 0);
            }
            else {
                return get(node, prefix, node.depth);
            }
        }

        public Value get(String key, Node node) {
            Node x;
            if (node == null) {
                x = get(root, key, 0);
            }
            else {
                x = get(node, key, node.depth);
            }
            if (x == null) return null;
            return (Value) x.val;
        }

        private Node get(Node x, String key, int d) {
            if (x == null) return null;
            if (d == key.length()) return x;
            char c = key.charAt(d);
            return get(x.next[c - 'A'], key, d + 1);
        }

        public void put(String key, Value val) {
            if (root == null) {
                root = new Node(R, 0);
            }
            root.hasChild = true;
            root = put(root, key, val, 0);
        }

        private Node put(Node x, String key, Value val, int d) {
            if (d == key.length()) {
                x.val = val;
                return x;
            }
            char c = key.charAt(d);
            if (x.next[c - 'A'] == null) {
                x.next[c - 'A'] = new Node(R, d + 1);
            }
            x.hasChild = true;
            x.next[c - 'A'] = put(x.next[c - 'A'], key, val, d + 1);
            return x;
        }
    }

    // Initializes the data structure using the given array of strings as the dictionary
    // You can assume each word in the dictionary contains only the uppercase letters A through Z
    public BoggleSolver(String[] dictionary) {
        dict = new TwentySixTrieST<Integer>();
        for (int i = 0; i < dictionary.length; ++i) {
            dict.put(dictionary[i], i);
        }
        words = new HashSet<String>();
    }

    // Returns the set of all valid words in the given Boggle board, as an Iterable
    public Iterable<String> getAllValidWords(BoggleBoard board) {
        words.clear();
        Queue<String> queue = new Queue<String>();
        boolean[][] marked = new boolean[board.rows()][board.cols()];
        for (int i = 0; i < board.rows(); ++i) {
            for (int j = 0; j < board.cols(); ++j) {
                boardDFS(new StringBuilder(), null, i, j, marked, queue, board);
            }
        }
        return queue;
    }

    private void boardDFS(StringBuilder word, Node node, int i, int j, boolean[][] marked, Queue<String> queue, BoggleBoard board) {
        if (!marked[i][j]) {
            marked[i][j] = true;
            word.append(board.getLetter(i, j));
            if (board.getLetter(i, j) == 'Q') {
                word.append("U");
            }
            String str = word.toString();
            if (str.length() >= 3 && dict.contains(str, node) && !words.contains(str)) {
                words.add(str);
                queue.enqueue(str);
            }
            Node childNode = dict.searchPrefix(str, node);
            if (childNode != null && childNode.hasChild) {
                if (i != 0) {
                    boardDFS(word, childNode, i - 1, j, marked, queue, board);
                    if (j != 0) {
                        boardDFS(word, childNode, i - 1, j - 1, marked, queue, board);
                    }
                    if (j != board.cols() - 1) {
                        boardDFS(word, childNode, i - 1, j + 1, marked, queue, board);
                    }
                }
                if (i != board.rows() - 1) {
                    boardDFS(word, childNode, i + 1, j, marked, queue, board);
                    if (j != 0) {
                        boardDFS(word, childNode, i + 1, j - 1, marked, queue, board);
                    }
                    if (j != board.cols() - 1) {
                        boardDFS(word, childNode, i + 1, j + 1, marked, queue, board);
                    }
                }
                if (j != 0) {
                    boardDFS(word, childNode, i, j - 1, marked, queue, board);
                }
                if (j != board.cols() - 1) {
                    boardDFS(word, childNode, i, j + 1, marked, queue, board);
                }
            }
            if (word.toString().endsWith("QU")) {
                word.delete(word.length() - 2, word.length());
            }
            else {
                word.deleteCharAt(word.length() - 1);
            }
            marked[i][j] = false;
        }
        return;
    }

    // Returns the score of the given word if it is in the dictionary, zero otherwise
    // You can assume the word contains only the uppercase letters A through Z
    public int scoreOf(String word) {
        if (dict.contains(word, null)) {
            switch (word.length()) {
                case 0:
                case 1:
                case 2:
                    return 0;
                case 3:
                case 4:
                    return 1;
                case 5:
                    return 2;
                case 6:
                    return 3;
                case 7:
                    return 5;
                default:
                    return 11;
            }
        }
        else {
            return 0;
        }
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        String[] dictionary = in.readAllStrings();
        BoggleSolver solver = new BoggleSolver(dictionary);
        BoggleBoard board = new BoggleBoard(args[1]);
        int score = 0;
        for (String word : solver.getAllValidWords(board)) {
            StdOut.println(word);
            score += solver.scoreOf(word);
        }
        StdOut.println("Score =  " + score);
    }
}

