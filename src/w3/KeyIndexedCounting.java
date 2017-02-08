package w3;

/**
 * Author:  tomasizo
 * Contact: tomas@izo.sk
 * Date:    07/02/2017
 * Description :
 */

public class KeyIndexedCounting {

    public static void sort(char[] a, int R) {
       int N = a.length;
       int[] count = new int[R+1];
       char[] aux = new char[N];

        for (int i = 0; i < N; i++)
            count[a[i]+1]++;

        for (int r = 0; r < R; r++)
            count[r+1] += count[r];

        for (int i = 0; i < N; i++)
            aux[count[a[i]]++] = a[i];

        for (int i = 0; i < N; i++)
           a[i] = aux[i];
    }

}
