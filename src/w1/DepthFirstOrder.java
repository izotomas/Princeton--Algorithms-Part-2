package w1;

import edu.princeton.cs.algs4.Stack;

/**
 * Author:  tomasizo
 * Contact: tomas@izo.sk
 * Date:    25/01/2017
 * Description : Topological order algorithm
 */

public class DepthFirstOrder {

    private boolean[] marked;
    private Stack<Integer> reversePost;

    public DepthFirstOrder(DiGraph G)
    {
        reversePost = new Stack<>();
        marked = new boolean[G.V()];
        for (int v = 0; v < G.V(); v++)
            if (!marked[v]) dfs(G, v);
    }

    private void dfs(DiGraph G, int v)
    {
        marked[v] = true;
        for (int w : G.adj(v))
            if (!marked[w]) dfs(G, w);
        reversePost.push(v);
    }

    public boolean hasPathTo(int v)
    {
        return marked[v];
    }

    public Iterable<Integer> reversePost(int v)
    {
        return reversePost;
    }
}
