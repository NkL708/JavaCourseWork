package classes;

import java.sql.*;
import java.util.Arrays;
import java.util.Objects;
import java.util.Vector;

public class Database
{
    private final static String userName = "postgres";
    private final static String password = "hugelong123";
    private final static String connectionUrl = "jdbc:postgresql://localhost:5432/JavaCourse";

    static public void addUser(String username, String password) throws SQLException, ClassNotFoundException
    {
        try (Connection connection = DriverManager.getConnection(Database.connectionUrl, Database.userName, Database.password))
        {
            PreparedStatement preparedStatement = connection.prepareStatement("""
                            INSERT INTO users(username, password)
                            VALUES(?,?);
                            """);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.executeUpdate();
        }
    }

    static public boolean authUser(String username, String password) throws SQLException, ClassNotFoundException
    {
        try (Connection connection = DriverManager.getConnection(Database.connectionUrl, Database.userName, Database.password))
        {
            ResultSet resultSet = connection.createStatement().executeQuery("""
                            SELECT username, password
                            FROM users;
                            """);
            while (resultSet.next())
            {
                if (Objects.equals(resultSet.getString("username"), username)
                        && Objects.equals(resultSet.getString("password"), password))
                {
                    return true;
                }
            }
            return false;
        }
    }

    static public boolean isUserUnique(String username) throws SQLException, ClassNotFoundException
    {
        try (Connection connection = DriverManager.getConnection(Database.connectionUrl, Database.userName, Database.password))
        {
            ResultSet resultSet = connection.createStatement().executeQuery("""
                            SELECT username
                            FROM users;
                            """);
            while (resultSet.next())
            {
                if (Objects.equals(username, resultSet.getString("username")))
                {
                    return false;
                }
            }
            return true;
        }
    }

    static public Vector<Vote> loadVotes() throws SQLException
    {
        Vector<Vote> result = new Vector<>();
        try (Connection connection = DriverManager.getConnection(Database.connectionUrl, Database.userName, Database.password))
        {
            ResultSet resultSet = connection.createStatement().executeQuery("""
                            SELECT name, username AS creator_name,
                            points_votes, points_names, voted_users
                            FROM votes_table
                            JOIN users ON votes_table.creator = users.id;
                            """);
            while (resultSet.next())
            {
                // sample.Vote data
                String name = resultSet.getString("name");
                Vector<Integer> pointsVotes = new Vector<>(Arrays.asList((Integer[])resultSet.getArray("points_votes").getArray()));
                Vector<String> pointsNames = new Vector<>(Arrays.asList((String[])resultSet.getArray("points_names").getArray()));
                Vector<String> votedUsers = new Vector<>(Arrays.asList((String[])resultSet.getArray("voted_users").getArray()));
                Vote vote = new Vote(name, pointsVotes, pointsNames, votedUsers);
                result.add(vote);
            }
        }
        return result;
    }

    // Unique user only!
    static public void vote(String voteName, String userName, int voteIndex, int userIndex) throws SQLException, ClassNotFoundException
    {
        try (Connection connection = DriverManager.getConnection(Database.connectionUrl, Database.userName, Database.password))
        {
            // Update votes
            PreparedStatement preparedStatement = connection.prepareStatement("""
                    UPDATE votes_table
                    SET points_votes[?] = points_votes[?] + 1,
                    voted_users[?] = ?
                    WHERE name = ?;
                    """);
            preparedStatement.setInt(1, voteIndex);
            preparedStatement.setInt(2, voteIndex);
            preparedStatement.setInt(3, userIndex);
            preparedStatement.setString(4, userName);
            preparedStatement.setString(5, voteName);
            preparedStatement.executeUpdate();
        }
    }

    static public Vote getVote(String voteName) throws SQLException
    {
        try (Connection connection = DriverManager.getConnection(Database.connectionUrl, Database.userName, Database.password))
        {
            PreparedStatement preparedStatement = connection.prepareStatement("""
                    SELECT name, points_votes, points_names, voted_users
                    FROM votes_table
                    WHERE name = ?;
                    """);
            preparedStatement.setString(1, voteName);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            String name = resultSet.getString("name");
            Vector<Integer> pointsVotes = new Vector<>(Arrays.asList((Integer[])resultSet.getArray("points_votes").getArray()));
            Vector<String> pointsNames = new Vector<>(Arrays.asList((String[])resultSet.getArray("points_names").getArray()));
            Vector<String> votedUsers = new Vector<>(Arrays.asList((String[])resultSet.getArray("voted_users").getArray()));
            return (new Vote(name, pointsVotes, pointsNames, votedUsers));
        }
    }
}