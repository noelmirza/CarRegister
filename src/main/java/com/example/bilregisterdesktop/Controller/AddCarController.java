package com.example.bilregisterdesktop.Controller;

import com.example.bilregisterdesktop.Database.DatabaseConnector;
import com.example.bilregisterdesktop.Model.Car;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.TableView;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddCarController {

    @FXML
    private TextField modelTextField;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField regNumTextField;

    @FXML
    private TextField kmTextField;

    @FXML
    private TextField priceTextField;

    @FXML
    private TextField yearModelTextField;

    private TableView<Car> carTable;
    private DashboardController dashboardController;

    public void setCarTable(TableView<Car> carTable) {
        this.carTable = carTable;
    }

    public void setDashboardController(DashboardController dashboardController) {
        this.dashboardController = dashboardController;
    }

    @FXML
    private void saveCar() {
        try {
            // Hent verdier fra tekstfeltene
            String model = modelTextField.getText();
            String name = nameTextField.getText();
            String regNum = regNumTextField.getText();
            int km = Integer.parseInt(kmTextField.getText());
            int price = Integer.parseInt(priceTextField.getText());
            int yearModel = Integer.parseInt(yearModelTextField.getText());

            // Konfigurer databaseforbindelse
            try (Connection connection = DatabaseConnector.getConnection()) {
                // Forbered SQL-setning for å sette inn bildata
                String insertSql = "INSERT INTO car (model, name, status, regNum, km, price, year_model) VALUES (?, ?, 'tilgjengelig', ?, ?, ?, ?)";
                try (PreparedStatement preparedStatement = connection.prepareStatement(insertSql)) {
                    preparedStatement.setString(1, model);
                    preparedStatement.setString(2, name);
                    preparedStatement.setString(3, regNum);
                    preparedStatement.setInt(4, km);
                    preparedStatement.setInt(5, price);
                    preparedStatement.setInt(6, yearModel);

                    // Utfør SQL-setning
                    preparedStatement.executeUpdate();
                }
            }

            // Oppdater carTable etter å ha lagt til en ny bil
            if (carTable != null) {
                carTable.getItems().add(new Car(0, model, name, "tilgjengelig", regNum, 0, 0, yearModel, 0));
            }

            // Oppdater også dashboardController
            if (dashboardController != null) {
                dashboardController.retrieveCarData();
            }

            // Lukk vinduet etter at bilen er lagt til
            modelTextField.getScene().getWindow().hide();

        } catch (NumberFormatException | SQLException e) {
            e.printStackTrace();
            // Legg til eventuell feilhåndtering her
        }
    }
}
