package w1;

import edu.princeton.cs.algs4.Bag;

/**
 * Created by tomasizo on 27/01/2017.
 */
public class DiGraph {
    private final int V;
    private final Bag<Integer>[] adj;
    private int E;

    public DiGraph(int V){
        this.V = V;
        this.E = 0;
        adj = (Bag<Integer>[]) new Bag[V];
        for (int v = 0; v < V; v++)
            adj[v] = new Bag<Integer>();
    }

    public void addEdge(int v, int w){
        adj[v].add(w);
        E++;
    }

    public int V(){
        return V;
    }

    public int E(){
        return E;
    }

    public DiGraph reverse(){
        return null;
    }


    public Iterable<Integer> adj(int v)
    { return adj[v]; }
}
