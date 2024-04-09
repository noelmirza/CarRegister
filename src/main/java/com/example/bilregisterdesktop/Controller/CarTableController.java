package com.example.bilregisterdesktop.Controller;

import com.example.bilregisterdesktop.Database.DatabaseConnector;
import com.example.bilregisterdesktop.Model.Car;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CarTableController {

    @FXML
    private TableView<Car> carTable;

    @FXML
    private TableColumn<Car, Integer> idColumn;

    @FXML
    private TableColumn<Car, String> modelColumn;

    @FXML
    private TableColumn<Car, String> nameColumn;

    @FXML
    private TableColumn<Car, String> statusColumn;

    @FXML
    private TableColumn<Car, String> regNumColumn;

    @FXML
    private TableColumn<Car, Integer> year_modelColumn;

    @FXML
    private TableColumn<Car, Integer> priceColumn;

    @FXML
    private TableColumn<Car, Integer> kmColumn;

    @FXML
    private TableColumn<Car, Integer> customer_idColumn;

    private DashboardController dashboardController;

    public void setDashboardController(DashboardController dashboardController) {
        this.dashboardController = dashboardController;
    }

    public void initialize() {
        // Set up cell value factories for columns
        idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        modelColumn.setCellValueFactory(cellData -> cellData.getValue().modelProperty());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        statusColumn.setCellValueFactory(cellData -> cellData.getValue().statusProperty());
        regNumColumn.setCellValueFactory(cellData -> cellData.getValue().regNumProperty());
        customer_idColumn.setCellValueFactory(cellData -> cellData.getValue().customer_idProperty().asObject());

        // Set up cell value factories for decimal columns
        priceColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getPrice()));
        kmColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getKm()));

        // Set up cell value factory for integer column
        year_modelColumn.setCellValueFactory(cellData -> cellData.getValue().yearModelProperty().asObject());

        // Retrieve data when the view is initialized
        retrieveData();
    }

    public void retrieveData() {
        ObservableList<Car> carList = FXCollections.observableArrayList();

        try (Connection connection = DatabaseConnector.getConnection()) {
            String query = "SELECT * FROM Car";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        int id = resultSet.getInt("id");
                        String model = resultSet.getString("model");
                        String name = resultSet.getString("name");
                        String status = resultSet.getString("status");
                        int customerId = resultSet.getInt("customer_id");
                        String regNumber = resultSet.getString("regNum");
                        int year = resultSet.getInt("year_model");
                        int price = resultSet.getInt("price");
                        int km = resultSet.getInt("km");

                        Car car = new Car(id, model, name, status, regNumber, km, price, year, customerId);
                        carList.add(car);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Feil", "Databasefeil: " + e.getMessage());
        }

        carTable.setItems(carList);
    }

    public void refreshCarData() {
        retrieveData();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
