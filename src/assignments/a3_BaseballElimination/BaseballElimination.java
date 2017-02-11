package assignments.a3_BaseballElimination;

import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FordFulkerson;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.ST;

/**
 * Author:  tomasizo
 * Contact: tomas@izo.sk
 * Date:    08/02/2017
 * Description :
 */

public class BaseballElimination {

    private ST<String, SET<String>> cache; // cache: teams and elimination certificates
    private ST<String, Integer> teamIDs; // team to id
    private String[] idTeams; //id to team
    private int N; //number of teams
    private int[] w; //wins
    private int[] l; //losses
    private int[] r; //remaining plays
    private int[][] g; //against
    private boolean[] marked; //evaluated
    private boolean[] e; //eliminated

    // create a baseball division from given filename in format specified below
    public BaseballElimination(String filename) {
        In inFile = new In(filename);
        N = inFile.readInt();
        if (N <= 0)
            throw new java.lang.IllegalArgumentException();

        w = new int[N];
        l = new int[N];
        r = new int[N];
        g = new int[N][N];
        e = new boolean[N];
        marked = new boolean[N];
        teamIDs = new ST<>();
        idTeams = new String[N];
        cache = new ST<>();

        for (int i = 0; i < N; i++) {
            String name = inFile.readString();
            teamIDs.put(name, i);
            idTeams[i] = name;
            w[i] = inFile.readInt();
            l[i] = inFile.readInt();
            r[i] = inFile.readInt();

            for (int j = 0; j < N; j++)
                g[i][j] = inFile.readInt();
        }
    }

    // number of teams
    public int numberOfTeams() {
       return N;
    }

    // all teams
    public Iterable<String> teams() {
        return teamIDs.keys();
    }

    // number of wins for given team
    public int wins(String team) {
        if (!teamIDs.contains(team))
            throw new java.lang.IllegalArgumentException();
        return w[teamIDs.get(team)];
    }

    // number of losses for given team
    public int losses(String team) {
        if (!teamIDs.contains(team))
            throw new java.lang.IllegalArgumentException();
        return l[teamIDs.get(team)];
    }
    // number of remaining games for given team
    public int remaining(String team) {
        if (!teamIDs.contains(team))
            throw new java.lang.IllegalArgumentException();
        return r[teamIDs.get(team)];
    }

    // number of remaining games between team1 and team2
    public int against(String team1, String team2) {
        if (!teamIDs.contains(team1))
            throw new java.lang.IllegalArgumentException();
        if (!teamIDs.contains(team2))
            throw new java.lang.IllegalArgumentException();

        return g[teamIDs.get(team1)][teamIDs.get(team2)];
    }

    // build a flow network
    private FlowNetwork buildFlowNetwork(int id) {
        int gameV = (N-1)*(N-2)/2; // team pair. id is excluded
        int teamV = N-1; // team id. id is excluded
        int s = gameV + teamV;
        int t = gameV + teamV + 1;
        FlowNetwork G = new FlowNetwork(gameV + teamV + 2);

        int counter = 0; // index of game vertices
        for (int i = 0; i < N; i++)
            for (int j = i+1; j < N; j++) {
                if (i != id && j != id) {
                    int v1 = counter; //game vertex in network
                    int v2; // team vertex in network
                    if (i < id)
                        v2 = gameV + i;
                    else
                        v2 = gameV + i - 1;

                    int v3; // team vertex in network
                    if (j < id)
                        v3 = gameV + j;
                    else
                        v3 = gameV + j - 1;
                    G.addEdge(new FlowEdge(v1, v2, Double.POSITIVE_INFINITY));
                    G.addEdge(new FlowEdge(v1, v3, Double.POSITIVE_INFINITY));
                    G.addEdge(new FlowEdge(s, v1, g[i][j]));
                    counter++;
                }
            }

        for (int i = 0; i < N; i++) {
            if (i != id) {
                int v;
                if (i < id)
                    v = gameV + i;
                else
                    v = gameV + i - 1;
                G.addEdge(new FlowEdge(v, t, w[id] + r[id] - w[i]));
            }
        }

        return G;
    }

    // trivial elimination
    private boolean trivial(String team, int id) {
        marked[id] = true;
        e[id] = false;
        for (int i = 0; i < N; i++) {
            // no need to compare with self
            if (i != id) {
                // find a certificate
                if (w[id] + r[id] < w[i]) {
                    if (!cache.contains(team))
                        cache.put(team, new SET<String>());
                    SET<String> set = cache.get(team);
                    set.add(idTeams[i]);
                    // find one certificate is enough for trivial elimination
                    e[id] = true; //can be eliminated
                }
            }
        }

        return e[id]; //can not eliminated
    }

    // nontrivial elimination
    private boolean nontrivial(String team, int id) {
        marked[id] = true;

        // build a flow network
        FlowNetwork G =  buildFlowNetwork(id);

        int s = G.V() - 2;
        int t = G.V() - 1;

        // run ford-fulkerson algorithm to find a maximum flow
        FordFulkerson maxflow = new FordFulkerson(G, s, t);

        e[id] = false;
        for (FlowEdge edge : G.adj(s)) {
            if (edge.flow() != edge.capacity()) {
                e[id] = true; // eliminated
                break;
            }
        }

        int gameV = (N-1)*(N-2)/2;
        if (e[id] == true) {
            if (!cache.contains(team))
                cache.put(team, new SET<String>());
            SET<String> set = cache.get(team);
            for (int i = 0; i < N; i++) {
                if (i != id) {
                    int v;
                    if (i < id)
                        v = gameV + i;
                    else
                        v = gameV + i - 1;
                    if (maxflow.inCut(v))
                        set.add(idTeams[i]);
                }
            }
        }

        return e[id];
    }

    // is given team eliminated?
    public boolean isEliminated(String team) {
        // in cache
        if (!teamIDs.contains(team))
            throw new java.lang.IllegalArgumentException();
        int id = teamIDs.get(team);
        if (marked[id]) {
            return e[id];
        }

        // trivial elimination
        if (trivial(team, id))
            return true;

        // nontrivial elimination
        if (nontrivial(team, id))
            return true;

        return false;
    }
    // subset R of teams that eliminates given team; null if not eliminated
    public Iterable<String> certificateOfElimination(String team) {
        if (!teamIDs.contains(team))
            throw new java.lang.IllegalArgumentException();
        int id = teamIDs.get(team);
        if (marked[id]) {
            return cache.get(team);
        }

        // trivial elimination
        if (trivial(team, id))
            return cache.get(team);

        // nontrivial elimination
        if (nontrivial(team, id))
            return cache.get(team);

        return null;
    }

}
