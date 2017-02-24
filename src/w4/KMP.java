package w4;

/**
 * Author:  tomasizo
 * Contact: tomas@izo.sk
 * Date:    24/02/2017
 * Description :
 */

public class KMP {
    private final static int R = 26; // characters of alphabet
    private String pat;
    private int[][] dfa;
    private int M;

    public KMP(String pat) {
        this.pat = pat;
        M = pat.length();
        dfa = new int[R][M];
        dfa[pat.charAt(0)][0] = 1;
        for (int X = 0, j = 1; j < M; j++)
        {
            for (int c = 0; c < R; c++)
                dfa[c][j] = dfa[c][X];  // copy mismatch cases
            dfa[pat.charAt(j)][j] = j+1;// set match case
            X = dfa[pat.charAt(j)][X];  // update restart state
        }
    }

    public int search(String txt)
    {
        int i, j, N = txt.length();
        for (i = 0, j = 0; i < N && j < M; i++)
            j = dfa[txt.charAt(i)][j];  // no backup
        if (j == M) return i - M;
        else        return N;
    }
}
