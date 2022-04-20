package controllers;

import classes.Database;
import classes.Main;
import classes.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Vector;

public class CreateController
{
    @FXML
    private ImageView backToMenuButton;
    @FXML
    private ImageView addPointButton;
    @FXML
    private VBox pointsPane;
    @FXML
    private Button createVote;
    @FXML
    private TextField titleField;
    private final Vector<TextField> pointsNames = new Vector<>();
    private final Vector<Integer> pointsVotes = new Vector<>();

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

        addPointButton.setOnMouseClicked(mouseEvent ->
        {
            TextField textField = new TextField();
            pointsPane.getChildren().add(textField);
            addPointName(textField);
            addPointsVote(0);
        });

        createVote.setOnAction(actionEvent ->
        {
            try
            {
                Database.createVote(titleField.getText(), getPointsVotes(), getPointsNames(), Database.getUserId(User.readUserFromFile().getUsername()));
            }
            catch (IOException | ClassNotFoundException | SQLException e)
            {
                e.printStackTrace();
            }
        });
    }

    public void addPointsVote(int count)
    {
        pointsVotes.add(count);
    }
    public void addPointName(TextField textField)
    {
        pointsNames.add(textField);
    }
    public Vector<Integer> getPointsVotes()
    {
        return pointsVotes;
    }
    public Vector<TextField> getPointsNames()
    {
        return pointsNames;
    }
}