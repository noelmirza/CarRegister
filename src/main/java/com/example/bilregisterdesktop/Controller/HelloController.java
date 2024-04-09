package com.example.bilregisterdesktop.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    private int count = 0;

    @FXML
    protected void onHelloButtonClick() {
        count++;
        welcomeText.setText("Button clicked " + count + " times!");
    }
    @FXML
    public void onLoginButtonClick(ActionEvent actionEvent) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.equals("babygang") && password.equals("123")) {
            welcomeText.setText("Logget inn som " + username);
            showDashboard(); // Kall denne metoden for å vise dashbordet
        } else {
            welcomeText.setText("Feil brukernavn eller passord");
        }
    }

    private void showDashboard() {
        System.out.println("Switching to dashboard");
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/bilregisterdesktop/dashboard-view.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = (Stage) welcomeText.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
