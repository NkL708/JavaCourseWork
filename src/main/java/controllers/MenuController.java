package controllers;

import classes.Database;
import classes.Main;
import classes.Vote;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
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
    private GridPane gridPane;
    @FXML
    private ImageView backToMenuButton;

    void createVotesGUI() throws SQLException, ClassNotFoundException
    {
        // Set grid size
        Vector<Vote> votes = Database.loadVotes();
        while (gridPane.getRowCount() * gridPane.getColumnCount() < votes.size())
        {
            RowConstraints rowConstraints = new RowConstraints();
            gridPane.getRowConstraints().add(0, rowConstraints);
        }
        int x = 0, y = 0;
        // Не забудь проверить компоновку, когда много голосований
        for (Vote vote: votes)
        {
            if (x == 3)
            {
                y += 2;
            }
            Label label = new Label(vote.getName());
            label.getStyleClass().add("blueLabel");
            Button button = new Button("Открыть");
            GridPane.setHalignment(button, HPos.CENTER);
            GridPane.setValignment(button, VPos.CENTER);
            GridPane.setHalignment(label, HPos.CENTER);
            GridPane.setValignment(label, VPos.CENTER);
            button.getStyleClass().add("blueButton");
            button.setOnAction(actionEvent ->
            {
                try
                {
                    Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                    FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(Main.class.getResource("vote.fxml")));
                    Parent parent = loader.load();
                    VoteController voteController = loader.getController();
                    voteController.setVoteName(vote.getName());
                    voteController.createGUI();
                    stage.setScene(new Scene(parent));
                }
                catch (IOException | ClassNotFoundException | SQLException e)
                {
                   e.printStackTrace();
                }
            });
            gridPane.add(label, x, y);
            gridPane.add(button, x, y + 1);
            x++;
        }
    }

    @FXML
    void initialize() throws SQLException, ClassNotFoundException
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