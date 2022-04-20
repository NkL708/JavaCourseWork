package classes;

import java.util.Vector;

/**
 * @param pointsVotes private String creatorName;
 */
public record Vote(String name, Vector<Integer> pointsVotes, Vector<String> pointsNames, Vector<String> votedUsers)
{

}