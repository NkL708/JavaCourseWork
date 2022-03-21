package controllers;

import classes.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class CreateController
{
    @FXML
    private ImageView backToMenuButton;

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