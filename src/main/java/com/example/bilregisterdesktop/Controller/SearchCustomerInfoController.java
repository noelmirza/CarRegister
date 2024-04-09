package com.example.bilregisterdesktop.Controller;

import com.example.bilregisterdesktop.Database.DatabaseConnector;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SearchCustomerInfoController {
    @FXML
    private TextField regNumTextField;
    @FXML
    private TextArea customerInfoTextArea;
    @FXML
    private VBox root;  // Assuming the root element is a VBox

    @FXML
    private void searchCustomerInfo() {
        String regNum = regNumTextField.getText();

        if (regNum.isEmpty()) {
            showAlert("Feil", "Skriv inn registreringsnummeret før du søker.");
            return;
        }

        String[] customerInfo = searchCustomerInfoInDatabase(regNum);

        if (customerInfo != null) {
            displayCustomerInfo(customerInfo[0], customerInfo[1], customerInfo[2]);
        } else {
            showAlert("Feil", "Ingen kundeinformasjon funnet for registreringsnummeret: " + regNum);
        }
    }
    private String[] searchCustomerInfoInDatabase(String regNum) {
        try (Connection connection = DatabaseConnector.getConnection()) {
            String query = "SELECT ci.customer_name, ci.customer_number, ci.customer_email " +
                    "FROM customer_information ci " +
                    "JOIN car c ON ci.customer_id = c.customer_id " +
                    "WHERE c.regNum = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, regNum);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        String customerName = resultSet.getString("customer_name");
                        String customerNumber = resultSet.getString("customer_number");
                        String customerEmail = resultSet.getString("customer_email");

                        return new String[]{customerName, customerNumber, customerEmail};
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Feil", "Databasefeil: " + e.getMessage());
        }

        return null;
    }


    private void displayCustomerInfo(String name, String number, String email) {
        String customerInfoText = "Navn: " + name + "\nNummer: " + number + "\nE-post: " + email;
        customerInfoTextArea.setText(customerInfoText);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
