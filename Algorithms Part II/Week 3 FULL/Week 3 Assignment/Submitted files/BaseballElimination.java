import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FordFulkerson;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class BaseballElimination {

    private final Map<String, Integer> allTeams;
    private final String[] teamNames;
    private final int[] wins;
    private final int[] losses;
    private final int[] remaining;
    private int maxWin;
    private int maxWinningTeamId;
    private final int[][] g;
    private final int n;
    private List<String> list;

    // create a baseball division from given filename in format specified below
    public BaseballElimination(String filename) {
        if (filename == null) {
            throw new IllegalArgumentException("argument is null");
        }
        In in = new In(filename);
        n = Integer.parseInt(in.readLine());
        allTeams = new LinkedHashMap<>();
        teamNames = new String[n];
        wins = new int[n];
        losses = new int[n];
        remaining = new int[n];
        g = new int[n][n];

        int i = 0;
        maxWin = 0;
        while (!in.isEmpty()) {
            String line = in.readString();
            allTeams.put(line, i);
            teamNames[i] = line;
            wins[i] = in.readInt();
            losses[i] = in.readInt();
            remaining[i] = in.readInt();
            for (int j = 0; j < n; j++) {
                g[i][j] = in.readInt();
            }
            if (wins[i] > maxWin) {
                maxWin = wins[i];
                maxWinningTeamId = i;
            }
            i++;
        }
    }

    // number of teams
    public int numberOfTeams() {
        return teamNames.length;
    }

    // all teams
    public Iterable<String> teams() {
        return allTeams.keySet();
    }

    // number of wins for given team
    public int wins(String team) {
        validate(team);
        return wins[allTeams.get(team)];
    }

    // number of losses for given team
    public int losses(String team) {
        validate(team);
        return losses[allTeams.get(team)];
    }

    // number of remaining games for given team
    public int remaining(String team) {
        validate(team);
        return remaining[allTeams.get(team)];
    }

    // number of remaining games between team1 and team2
    public int against(String team1, String team2) {
        validate(team1);
        validate(team2);
        return g[allTeams.get(team1)][allTeams.get(team2)];
    }

    // is given team eliminated?
    public boolean isEliminated(String team) {
        validate(team);
        if (trivialElimination(team)) {
            return true;
        }
        return nonTrivialElimination(team);
    }

    // subset R of teams that eliminates given team; null if not eliminated
    public Iterable<String> certificateOfElimination(String team) {
        validate(team);
        if (!isEliminated(team)) {
            return null;
        }
        if (trivialElimination(team)) {
            list = new LinkedList<>();
            list.add(teamNames[maxWinningTeamId]);
        } else {
            nonTrivialElimination(team);
        }
        return list;
    }

    // helper methods
    private void validate(String name) {
        if (name == null) {
            throw new IllegalArgumentException("argument is null");
        }
        for (String str : teamNames) {
            if (name.equals(str)) {
                return;
            }
        }
        throw new IllegalArgumentException("argument is illegal");
    }

    private boolean trivialElimination(String team) {
        return wins[allTeams.get(team)] + remaining[allTeams.get(team)] < maxWin;
    }

    private boolean nonTrivialElimination(String team) {
        int teamID = allTeams.get(team);
        int size = (n * (n - 1)) / 2;
        // creating flow network
        FlowNetwork flowNetwork = new FlowNetwork(size + 2);
        int s = size;
        int t = size + 1;

        int gameIndex = n - 1, firstTeamId, secondTeamId, totalRemaining = 0;
        for (int i = 0; i < n; i++) {
            if (i == teamID) {
                continue;
            }
            if (i > teamID) {
                firstTeamId = i - 1;    // as teamId is absent.so,greater than teamId will get 1 less
            } else {
                firstTeamId = i;
            }
            for (int j = i + 1; j < n; j++) {
                if (j == teamID) {
                    continue;
                }
                if (j > teamID) {
                    secondTeamId = j - 1;
                } else {
                    secondTeamId = j;
                }
                // source to gameId
                flowNetwork.addEdge(new FlowEdge(s, gameIndex, g[i][j]));
                totalRemaining += g[i][j];
                // gameId to teams Id
                flowNetwork.addEdge(new FlowEdge(gameIndex, firstTeamId, Double.POSITIVE_INFINITY));
                flowNetwork.addEdge(new FlowEdge(gameIndex++, secondTeamId, Double.POSITIVE_INFINITY));
            }
        }

        for (int i = 0; i < n; i++) {
            if (i == teamID) {
                continue;
            }
            if (i > teamID) {
                firstTeamId = i - 1;
            } else {
                firstTeamId = i;
            }
            int capacityToTarget = wins[teamID] + remaining[teamID] - wins[i];
            if (capacityToTarget < 0) {
                continue;
            }
            flowNetwork.addEdge(new FlowEdge(firstTeamId, t, capacityToTarget));
        }

        FordFulkerson fordFulkerson = new FordFulkerson(flowNetwork, s, t);

        // creating R
        list = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            if (i == teamID) {
                continue;
            }
            if (i > teamID) {
                firstTeamId = i - 1;
            } else {
                firstTeamId = i;
            }
            if (fordFulkerson.inCut(firstTeamId)) {
                list.add(teamNames[i]);
            }
        }

        return (int) fordFulkerson.value() != totalRemaining;
    }

    public static void main(String[] args) {
        BaseballElimination division = new BaseballElimination(StdIn.readLine());
        for (String team : division.teams()) {
            if (division.isEliminated(team)) {
                StdOut.print(team + " is eliminated by the subset R = { ");
                for (String t : division.certificateOfElimination(team)) {
                    StdOut.print(t + " ");
                }
                StdOut.println("}");
            } else {
                StdOut.println(team + " is not eliminated");
            }
        }
        System.out.println(division.teams());
        System.out.println(division.against("Toronto", "Detroit"));
        System.out.println(division.wins("Boston"));
        System.out.println(division.losses("Boston"));
        System.out.println(division.numberOfTeams());
        System.out.println(division.remaining("Baltimore"));
    }
}
