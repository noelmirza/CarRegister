package com.example.bilregisterdesktop.Controller;

import com.example.bilregisterdesktop.Database.DatabaseConnector;
import com.example.bilregisterdesktop.Model.Car;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.StringConverter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AddCustomerInfoController {
    @FXML
    private TextField customerNameTextField;
    @FXML
    private TextField customerNumberTextField;
    @FXML
    private TextField customerEmailTextField;
    @FXML
    private TableView<Car> carTable;
    @FXML
    private ComboBox<Car> carComboBox;

    public void setCarTable(TableView<Car> carTable) {
        this.carTable = carTable;
    }

    private DashboardController dashboardController;

    public void setDashboardController(DashboardController dashboardController) {
        this.dashboardController = dashboardController;
    }

    // Populate carComboBox with cars from the database
    @FXML
    public void initialize() {
        // Set up the ComboBox to display name, model, and regNum
        carComboBox.setCellFactory(param -> new ListCell<Car>() {
            @Override
            protected void updateItem(Car item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getName() + " " + item.getModel() + " " + item.getRegNum());
                }
            }
        });

        // Set up the StringConverter to convert Car object to a string representation
        carComboBox.setConverter(new StringConverter<Car>() {
            @Override
            public String toString(Car object) {
                if (object == null) {
                    return null;
                } else {
                    return object.getName() + " " + object.getModel() + " " + object.getRegNum();
                }
            }

            @Override
            public Car fromString(String string) {
                // You might need to implement this method if needed
                return null;
            }
        });

        // Populate carComboBox with cars from the database
        ObservableList<Car> carList = fetchCarDataFromDatabase();
        carComboBox.setItems(carList);
    }

    private ObservableList<Car> fetchCarDataFromDatabase() {
        ObservableList<Car> carList = FXCollections.observableArrayList();

        try (Connection connection = DatabaseConnector.getConnection()) {
            String query = "SELECT id, name, model, regNum FROM Car"; // Legg til 'id' i spørringen
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                try (var resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        int id = resultSet.getInt("id"); // Hent id fra resultatsettet
                        String name = resultSet.getString("name");
                        String model = resultSet.getString("model");
                        String regNumber = resultSet.getString("regNum");

                        // Opprett bilobjekt med id og de ønskede kolonnene
                        Car car = new Car(id, model, name, "", regNumber, 0, 0, 0, 0);
                        carList.add(car);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Behandle unntaket på en hensiktsmessig måte
        }

        return carList;
    }

    @FXML
    private void saveCustomerInfo() {
        Car selectedCar = carComboBox.getValue(); // Endret denne linjen
        if (selectedCar == null) {
            showAlert("Feil", "Vennligst velg en bil fra listen.");
            return;
        }

        int carId = selectedCar.getId();
        String customerName = customerNameTextField.getText();
        String customerNumber = customerNumberTextField.getText();
        String customerEmail = customerEmailTextField.getText();

        if (customerName.isEmpty() || customerNumber.isEmpty() || customerEmail.isEmpty()) {
            showAlert("Feil", "Vennligst fyll ut all nødvendig informasjon.");
            return;
        }

        try (Connection connection = DatabaseConnector.getConnection()) {
            String insertCustomerInfoQuery = "INSERT INTO customer_information (customer_id, customer_name, customer_number, customer_email) VALUES (?, ?, ?, ?)";
            try (PreparedStatement insertCustomerInfoStatement = connection.prepareStatement(insertCustomerInfoQuery)) {
                insertCustomerInfoStatement.setInt(1, carId);
                insertCustomerInfoStatement.setString(2, customerName);
                insertCustomerInfoStatement.setString(3, customerNumber);
                insertCustomerInfoStatement.setString(4, customerEmail);
                insertCustomerInfoStatement.executeUpdate();
            }

            // Oppdater car-tabellen med customer_id
            String updateCarQuery = "UPDATE car SET customer_id = ? WHERE id = ?";
            try (PreparedStatement updateCarStatement = connection.prepareStatement(updateCarQuery)) {
                updateCarStatement.setInt(1, carId);
                updateCarStatement.setInt(2, carId);
                updateCarStatement.executeUpdate();
            }

            System.out.println("Kundeinformasjon oppdatert på bilen.");
            showAlert("Suksess", "Kundeinformasjon lagt til vellykket.");
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Feil", "Databasefeil: " + e.getMessage());
        }
        System.out.println("Valgt bil: " + selectedCar.getName() + " " + selectedCar.getModel() + " " + selectedCar.getRegNum());
    }


    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
