package w2;

import edu.princeton.cs.algs4.Bag;

/**
 * Author:  tomasizo
 * Contact: tomas@izo.sk
 * Date:    30/01/2017
 * Description :
 */

public class EdgeWeightedDigraph {
    private final int V;
    private final Bag<DirectedEdge>[] adj;
    private int E;

    public EdgeWeightedDigraph(int V) {
        this.V = V;
        E = 0;
        adj = (Bag<DirectedEdge>[]) new Bag[V];
        for (int v = 0; v < V; v++)
            adj[v] = new Bag<>();
    }

    public void addEdge(DirectedEdge e) {
        int v = e.from();
        adj[v].add(e);
        E++;
    }

    public Iterable<DirectedEdge> adj(int v) {
        return adj[v];
    }

    public int V() {
        return V;
    }

    public int E() {
        return E;
    }
}
