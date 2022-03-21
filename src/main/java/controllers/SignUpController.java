package controllers;

import classes.Database;
import classes.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

public class SignUpController
{
    @FXML
    private Text mismatchText;
    @FXML
    private Text loginExistsText;
    @FXML
    private PasswordField confirmPasswordField;
    @FXML
    private TextField loginField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button signUpButton;
    @FXML
    private ImageView backToLoginButton;

    @FXML
    void initialize()
    {
        signUpButton.setOnAction(actionEvent ->
        {
            try
            {
                if (!Database.isUserUnique(loginField.getText()))
                {
                    loginExistsText.setVisible(true);
                    mismatchText.setVisible(false);
                }
                else if (!Objects.equals(passwordField.getText(), confirmPasswordField.getText()))
                {
                    loginExistsText.setVisible(false);
                    mismatchText.setVisible(true);
                }
                else if (!Objects.equals(loginField.getText(), "") || !Objects.equals(passwordField.getText(), ""))
                {
                    Database.addUser(loginField.getText(), passwordField.getText());
                    Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                    FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(Main.class.getResource("login.fxml")));
                    Parent parent = loader.load();
                    LogInController logInController = loader.getController();
                    logInController.getRegistrationSucceed().setVisible(true);
                    Scene scene = new Scene(parent);
                    stage.setScene(scene);
                }
            }
            catch (SQLException | ClassNotFoundException | IOException e)
            {
                e.printStackTrace();
            }
        });

        backToLoginButton.setOnMouseClicked(mouseEvent ->
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