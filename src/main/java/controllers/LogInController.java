package controllers;

import classes.Database;
import classes.Main;
import classes.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

public class LogInController
{
    @FXML
    private TextField loginField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button loginButton;
    @FXML
    private Button signUpButton;
    @FXML
    private Text notCorrectInfo;
    @FXML
    private Text registrationSucceed;

    @FXML
    void initialize()
    {
        loginButton.setOnAction(actionEvent ->
        {
            try
            {
                String username = loginField.getText(), password = passwordField.getText();
                if (Database.authUser(username, password))
                {
                    User user = new User(username);
                    user.saveUserToFile();
                    Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                    Scene scene = new Scene(FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("menu.fxml"))));
                    stage.setScene(scene);
                }
                else
                {
                    notCorrectInfo.setVisible(true);
                }
            }
            catch (IOException | ClassNotFoundException | SQLException e)
            {
                e.printStackTrace();
            }
        });

        signUpButton.setOnAction(actionEvent ->
        {
            try
            {
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("signUp.fxml"))));
                stage.setScene(scene);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        });
    }

    public Text getRegistrationSucceed()
    {
        return registrationSucceed;
    }
}