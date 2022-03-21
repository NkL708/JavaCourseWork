package classes;

import java.util.Vector;

public class Vote
{
    private final String name;
    //private String creatorName;
    private final Vector<Integer> pointsVotes;
    private final Vector<String> pointsNames;
    private final Vector<String> votedUsers;

    public Vote(String name, Vector<Integer> pointsVotes, Vector<String> pointsNames, Vector<String> votedUsers)
    {
        this.name = name;
        this.pointsVotes = pointsVotes;
        this.pointsNames = pointsNames;
        this.votedUsers = votedUsers;
    }

    public String getName()
    {
        return name;
    }

    public Vector<Integer> getPointsVotes()
    {
        return pointsVotes;
    }

    public Vector<String> getPointsNames()
    {
        return pointsNames;
    }

    public Vector<String> getVotedUsers()
    {
        return votedUsers;
    }
}