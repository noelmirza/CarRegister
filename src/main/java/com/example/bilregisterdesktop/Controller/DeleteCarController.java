package com.example.bilregisterdesktop.Controller;

import com.example.bilregisterdesktop.Database.DatabaseConnector;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteCarController {

    @FXML
    private TextField regNumTextField;

    @FXML
    private void searchAndDelete() {
        String regNum = regNumTextField.getText();

        if (regNum.isEmpty()) {
            showAlert("Feil", "Skriv inn registreringsnummer før du søker og sletter.");
            return;
        }

        try (Connection connection = DatabaseConnector.getConnection()) {
            // search for carRegistration
            String carQuery = "SELECT id FROM Car WHERE regNum = ?";
            try (PreparedStatement carStatement = connection.prepareStatement(carQuery)) {
                carStatement.setString(1, regNum);
                var carResultSet = carStatement.executeQuery();

                if (carResultSet.next()) {
                    int carId = carResultSet.getInt("id");

                    // Delete car information
                    String deleteCarQuery = "DELETE FROM Car WHERE id = ?";
                    try (PreparedStatement deleteCarStatement = connection.prepareStatement(deleteCarQuery)) {
                        deleteCarStatement.setInt(1, carId);
                        int carRowsDeleted = deleteCarStatement.executeUpdate();

                        if (carRowsDeleted > 0) {
                            // Slett kundeinformasjon basert på bilens id
                            String deleteCustomerQuery = "DELETE FROM customer_information WHERE customer_id = ?";
                            try (PreparedStatement deleteCustomerStatement = connection.prepareStatement(deleteCustomerQuery)) {
                                deleteCustomerStatement.setInt(1, carId);
                                int customerRowsDeleted = deleteCustomerStatement.executeUpdate();

                                if (customerRowsDeleted > 0) {
                                    showAlert("Suksess", "Bil og kundeinformasjon slettet!");
                                }
                            }
                        } else {
                            showAlert("Feil", "Kunne ikke slette bilen.");
                        }
                    }
                } else {
                    showAlert("Feil", "Ingen bil funnet med registreringsnummer: " + regNum);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Feil", "Databasefeil: " + e.getMessage());
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
