package w2;

/**
 * Author:  tomasizo
 * Contact: tomas@izo.sk
 * Date:    30/01/2017
 * Description :
 */

public class DirectedEdge {
    private int v, w;
    private double weight;

    public DirectedEdge(int v, int w, double weight) {
        this.v = v;
        this.w = w;
        this.weight = weight;
    }

    public int from() {
        return v;
    }

    public int to(){
        return w;
    }

    public double weight() {
        return weight;
    }
}
