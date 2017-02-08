package assignments.a3_BaseballElimination;

/**
 * Author:  tomasizo
 * Contact: tomas@izo.sk
 * Date:    08/02/2017
 * Description :
 */

public class BaseballElimination {

    // create a baseball division from given filename in format specified below
    public BaseballElimination(String filename) {

    }

    // number of teams
    public int numberOfTeams() {
       return -1;
    }

    // all teams
    public Iterable<String> teams() {
        return null;
    }

    // number of wins for given team
    public int wins(String team) {
       return -1;
    }

    // number of losses for given team
    public int losses(String team) {
       return -1;
    }
    // number of remaining games for given team
    public int remaining(String team) {
       return -1;
    }

    // number of remaining games between team1 and team2
    public int against(String team1, String team2) {
       return -1;
    }
    // is given team eliminated?
    public boolean isEliminated(String team) {
       return false;
    }
    // subset R of teams that eliminates given team; null if not eliminated
    public Iterable<String> certificateOfElimination(String team) {
       return null;
    }

}
