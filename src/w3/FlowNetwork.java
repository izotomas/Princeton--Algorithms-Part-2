package w3;

import edu.princeton.cs.algs4.Bag;

/**
 * Author:  tomasizo
 * Contact: tomas@izo.sk
 * Date:    06/02/2017
 * Description :
 */

public class FlowNetwork {

    private final int V;
    private int E;
    private Bag<FlowEdge>[] adj;

    public FlowNetwork(int V) {
        this.V = V;
        E = 0;
        adj = (Bag<FlowEdge>[]) new Bag[V];
        for (int v = 0; v < V; v++) {
            adj[v] = new Bag<>();
        }
    }

    public void addEdge(FlowEdge e) {
        int v = e.from();
        int w = e.to();
        adj[v].add(e); //add forward edge
        adj[w].add(e); //add backward edge
        E++;
    }

    public Iterable<FlowEdge> adj(int v) {
        return adj[v];
    }

    public Iterable<FlowEdge> edges() {
        Bag<FlowEdge> list = new Bag<>();
        for (int v = 0; v < V; v++)
            for (FlowEdge e : adj(v)) {
                if (e.to() != v)
                    list.add(e);
            }
        return list;
    }

    public int V() {
        return V;
    }

    public int E() {
        return E;
    }
}
