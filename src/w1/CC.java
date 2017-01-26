package w1;

/**
 * Created by tomasizo on 26/01/2017.
 */
public class CC {

    private boolean marked[];
    private int id[];
    private int count;


    public CC(Graph G) {
        marked = new boolean[G.V()];
        id = new int[G.V()];
        for (int v = 0; v < G.V(); v++) {
            if (!marked[v]){
                dfs(G,v);
                count++;
            }
        }
    }

    private void dfs(Graph G, int v){
        marked[v] = true;
        id[v] = count;
        for (int w : G.adj(v))
            if (!marked[w])
                dfs(G, w);
    }

    /**
     *
     * @param v
     * @param w
     * @return are v and w connected?
     */
    boolean connected(int v, int w){

        return false;
    }

    /**
     *
     * @return number of connected components
     */
    public int count(){
        return count;
    }

    /**
     *
     * @param v
     * @return component identifier for v
     */
    public int id(int v){
        return id[v];
    }
}
