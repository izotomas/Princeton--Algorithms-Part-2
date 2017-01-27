package w1;

import edu.princeton.cs.algs4.Stack;

/**
 * Created by tomasizo on 25/01/2017.
 */

public class DirectedDFS {
    private boolean[] marked;

    public DirectedDFS (DiGraph G, int s)
    {
        marked = new boolean[G.V()];
        dfs(G, s);
    }

    private void dfs(DiGraph G, int v)
    {
        marked[v] = true;
        for (int w : G.adj(v))
            if (!marked[w]) dfs(G, w);
    }

    public boolean visited(int v)
    { return marked[v]; }
}
