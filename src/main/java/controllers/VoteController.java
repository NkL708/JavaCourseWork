package controllers;

import classes.Database;
import classes.Main;
import classes.User;
import classes.Vote;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

public class VoteController
{
    @FXML
    private ImageView backToMenuButton;
    @FXML
    private PieChart pieChart;
    @FXML
    private FlowPane voteButtonsPane;

    private String voteName;
    private Vote vote;
    private final User user = User.readUserFromFile();

    public VoteController() throws IOException, ClassNotFoundException
    {

    }

    public void createGUI() throws ClassNotFoundException, IOException, SQLException
    {
        updateVote();
        for (int i = 0; i < vote.getPointsVotes().size(); i++)
        {
            String name = vote.getPointsNames().elementAt(i) + " : " + vote.getPointsVotes().elementAt(i);
            PieChart.Data slice = new PieChart.Data(name, vote.getPointsVotes().elementAt(i));
            pieChart.getData().add(slice);
            Button button = new Button(vote.getPointsNames().elementAt(i));
            button.getStyleClass().add("blueButton");
            voteButtonsPane.getChildren().add(button);
            // If user voted - disable buttons
            if (vote.getVotedUsers().contains(user.getUsername()))
            {
                button.setDisable(true);
            }
            int voteIndex = i + 1, userIndex = vote.getVotedUsers().size() + 1;
            button.setOnAction(actionEvent ->
            {
                try
                {
                    Database.vote(vote.getName(), user.getUsername(), voteIndex, userIndex);
                    updateGUI();
                }
                catch (SQLException | ClassNotFoundException e)
                {
                    e.printStackTrace();
                }
            });
        }
    }

    public void setVoteName(String voteName)
    {
        this.voteName = voteName;
    }

    public void updateVote() throws SQLException
    {
        vote = Database.getVote(voteName);
    }

    public void updateGUI() throws SQLException
    {
        updateVote();
        for (int i = 0; i < vote.getPointsVotes().size(); i++)
        {
            // Update pie chart values
            String name = vote.getPointsNames().elementAt(i) + " : " + vote.getPointsVotes().elementAt(i);
            pieChart.getData().get(i).setPieValue(vote.getPointsVotes().elementAt(i));
            pieChart.getData().get(i).setName(name);
            // Update buttons
            if (vote.getVotedUsers().contains(user.getUsername()))
            {
                voteButtonsPane.getChildren().get(i).setDisable(true);
            }
        }
    }

    @FXML
    void initialize()
    {
        backToMenuButton.setOnMouseClicked(mouseEvent ->
        {
            try
            {
                Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("menu.fxml"))));
                stage.setScene(scene);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        });
    }
}