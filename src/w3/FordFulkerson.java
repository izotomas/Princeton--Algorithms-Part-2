package w3;

import edu.princeton.cs.algs4.Queue;

import java.util.DoubleSummaryStatistics;

/**
 * Author:  tomasizo
 * Contact: tomas@izo.sk
 * Date:    06/02/2017
 * Description :
 */

public class FordFulkerson {
    private boolean[] marked;   // true if s-> v path in residual network
    private FlowEdge[] edgeTo;  // last ede on s->v path
    private double value;       // value of flow

    public FordFulkerson(FlowNetwork G, int s, int t)
    {
        value = 0.0;
        while (hasAugmentingPath(G, s, t))
        {
            double bottle = Double.POSITIVE_INFINITY;

            // compute bottleneck capacity
            for (int v = t; v != s ; v = edgeTo[v].other(v))
                bottle = Math.min(bottle, edgeTo[v].residualCapacityTo(v));

            // augment flow
            for (int v = t; v != s; v = edgeTo[v].other(v))
                edgeTo[v].addResidualFlowTo(v, bottle);

            value += bottle;
        }
    }

    private boolean hasAugmentingPath(FlowNetwork G, int s, int t)
    {
        edgeTo = new FlowEdge[G.V()];
        marked = new boolean[G.V()];

        // breadth-first search
        Queue<Integer> queue = new Queue<Integer>();
        queue.enqueue(s);
        marked[s] = true;
        while (!queue.isEmpty() && !marked[t]) {
            int v = queue.dequeue();

            for (FlowEdge e : G.adj(v)) {
                int w = e.other(v);

                // if residual capacity from v to w
                if (e.residualCapacityTo(w) > 0 && !marked[w]) {
                    edgeTo[w] = e;
                    marked[w] = true;
                    queue.enqueue(w);
                }
            }
        }

        // is there an augmenting path?
        return marked[t];
    }

    public double value() {
        return value;
    }

    // is v reachable from s in residual network?
    public boolean inCut(int v)
    {
        return marked[v];
    }
}
