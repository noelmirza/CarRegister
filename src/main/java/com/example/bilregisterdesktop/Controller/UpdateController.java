package com.example.bilregisterdesktop.Controller;

import com.example.bilregisterdesktop.Database.DatabaseConnector;
import com.example.bilregisterdesktop.Model.Car;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.ListCell; // Legg til denne importen
import javafx.util.StringConverter; // Legg til denne importen
import com.example.bilregisterdesktop.Controller.DashboardController;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateController {

    @FXML
    private ComboBox<Car> carComboBox;

    @FXML
    private TextField statusTextField;

    @FXML
    private TextField priceTextField;

    @FXML
    private TextField kmTextField;

    @FXML
    private Button updateStatusButton;

    @FXML
    private Button updatePriceButton;

    @FXML
    private Button updateKMButton;

    private CarTableController carTableController;
    private DashboardController dashboardController;

    public void setDashboardController(DashboardController dashboardController) {
        this.dashboardController = dashboardController;
    }

    public void setCarTableController(CarTableController carTableController) {
        this.carTableController = carTableController;
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


                        Car car = new Car(id, model, name, "", regNumber, 0, 0, 0, 0);
                        carList.add(car);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            //exception
        }

        return carList;
    }

    @FXML
    private void updateCarStatus() {
        Car selectedCar = carComboBox.getValue();
        int carId = selectedCar.getId();
        String newStatus = statusTextField.getText();

        updateCarStatusInDatabase(selectedCar, newStatus);

        // update car data
        if (carTableController != null) {
            carTableController.refreshCarData();
        }
    }
    @FXML
    private void updateCarPrice() {
        Car selectedCar = carComboBox.getValue();
        double newPrice = Double.parseDouble(priceTextField.getText());

        updateCarPriceInDatabase(selectedCar, newPrice);


        if (carTableController != null) {
            carTableController.refreshCarData();
        }
    }

    @FXML
    private void updateCarKM() {
        Car selectedCar = carComboBox.getValue();
        double newKM = Double.parseDouble(kmTextField.getText());

        updateCarKMinDatabase(selectedCar, newKM);


        if (carTableController != null) {
            carTableController.refreshCarData();
        }
    }

    private void updateCarStatusInDatabase(Car car, String newStatus) {
        try (Connection connection = DatabaseConnector.getConnection()) {
            String query = "UPDATE Car SET status = ? WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, newStatus);
                statement.setInt(2, car.getId());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateCarPriceInDatabase(Car car, double newPrice) {
        try (Connection connection = DatabaseConnector.getConnection()) {
            String query = "UPDATE Car SET price = ? WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setDouble(1, newPrice);
                statement.setInt(2, car.getId());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateCarKMinDatabase(Car car, double newKM) {
        try (Connection connection = DatabaseConnector.getConnection()) {
            String query = "UPDATE Car SET km = ? WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setDouble(1, newKM);
                statement.setInt(2, car.getId());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
