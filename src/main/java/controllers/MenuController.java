package controllers;

import classes.Database;
import classes.Main;
import classes.Vote;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Vector;

public class MenuController
{
    @FXML
    private Button createVoteButton;
    @FXML
    private VBox votesPane;
    @FXML
    private ImageView backToMenuButton;

    void createVotesGUI() throws SQLException
    {
        // Set grid size
        Vector<Vote> votes = Database.loadVotes();
        for (Vote vote: votes)
        {
            Label label = new Label(vote.name());
            label.getStyleClass().add("blueLabel");
            Button button = new Button("Открыть");
            button.setPrefSize(200,30);
            button.getStyleClass().add("blueButton");
            button.setOnAction(actionEvent ->
            {
                try
                {
                    Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                    FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(Main.class.getResource("vote.fxml")));
                    Parent parent = loader.load();
                    VoteController voteController = loader.getController();
                    voteController.setVoteName(vote.name());
                    voteController.createGUI();
                    stage.setScene(new Scene(parent));
                }
                catch (IOException | ClassNotFoundException | SQLException e)
                {
                   e.printStackTrace();
                }
            });
            votesPane.getChildren().add(label);
            votesPane.getChildren().add(button);
        }
    }

    @FXML
    void initialize() throws SQLException
    {
        createVotesGUI();
        createVoteButton.setOnAction(actionEvent ->
        {
            try
            {
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("create.fxml"))));
                stage.setScene(scene);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        });
        backToMenuButton.setOnMouseClicked(mouseEvent ->
        {
            try
            {
                Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("login.fxml"))));
                stage.setScene(scene);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        });
    }
}