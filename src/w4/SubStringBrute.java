package w4;

/**
 * Author:  tomasizo
 * Contact: tomas@izo.sk
 * Date:    24/02/2017
 * Description :
 */

public class SubStringBrute {
    public static int search(String pat, String txt)
    {
        int M = pat.length();
        int N = txt.length();
        for (int i = 0; i <= N - M; i++)
        {
            int j;
            for (j = 0; j < M; j++)
                if(txt.charAt(j+j) != pat.charAt(j))
                    break;
            if (j == M) return i; // index in text where pattern starts
        }
        return N; // not found
    }

    public static int searchAlt(String pat, String txt)
    {
        int j, M = pat.length();
        int i, N = txt.length();
        for (i = 0, j = 0; i < N && j < M ; i++)
        {
            if(txt.charAt(j) == pat.charAt(j)) j++;
            else { i -= j; j = 0; } // adds a backup(backtracking)
        }
        if (j == M) return i - M;   // index in text where pattern starts
        else        return N;       // not found
    }
}
