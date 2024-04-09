package com.example.bilregisterdesktop.Controller;

import com.example.bilregisterdesktop.Database.DatabaseConnector;
import com.example.bilregisterdesktop.Model.Car;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SearchCarController {

    @FXML
    private TextField regNumSearchField;

    @FXML
    private TextField searchModelOrNameField;


    @FXML
    private TableView<Car> searchResultTable;

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
    private TableColumn<Car, Integer> customer_idColumn;

    @FXML
    private TableColumn<Car, Integer> year_modelColumn;

    @FXML
    private TableColumn<Car, Integer> priceColumn;

    @FXML
    private TableColumn<Car, Integer> kmColumn;



    public void initialize() {
        // Set up cell value factories for columns
        idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        modelColumn.setCellValueFactory(cellData -> cellData.getValue().modelProperty());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        statusColumn.setCellValueFactory(cellData -> cellData.getValue().statusProperty());
        regNumColumn.setCellValueFactory(cellData -> cellData.getValue().regNumProperty());
        customer_idColumn.setCellValueFactory(cellData -> cellData.getValue().customer_idProperty().asObject());
        priceColumn.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());
        kmColumn.setCellValueFactory(cellData -> cellData.getValue().kmProperty().asObject());


        // Set up cell value factory for integer column
        year_modelColumn.setCellValueFactory(cellData -> cellData.getValue().yearModelProperty().asObject());


    }

    @FXML
    private void searchByRegNum() {
        String regNum = regNumSearchField.getText();

        ObservableList<Car> searchResults = FXCollections.observableArrayList();

        try (Connection connection = DatabaseConnector.getConnection()) {
            String query = "SELECT * FROM Car WHERE regNum LIKE ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, "%" + regNum + "%");

                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        int id = resultSet.getInt("id");
                        String model = resultSet.getString("model");
                        String name = resultSet.getString("name");
                        String status = resultSet.getString("status");
                        String regNumber = resultSet.getString("regNum");
                        int customerId = resultSet.getInt("customer_id");
                        int price = resultSet.getInt("price");
                        int km = resultSet.getInt("km");

                        Car car = new Car(id, model, name, status, regNumber, price, 0, km, customerId);
                        searchResults.add(car);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Feil", "Databasefeil: " + e.getMessage());
        }

        searchResultTable.setItems(searchResults);
    }

    @FXML
    private void searchByModelOrName() {
        System.out.println("searchByModelOrName() called");

        String searchText = searchModelOrNameField.getText();

        ObservableList<Car> searchResults = FXCollections.observableArrayList();

        try (Connection connection = DatabaseConnector.getConnection()) {
            String query = "SELECT * FROM Car WHERE model LIKE ? OR name LIKE ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, "%" + searchText + "%");
                statement.setString(2, "%" + searchText + "%");

                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        int id = resultSet.getInt("id");
                        String model = resultSet.getString("model");
                        String name = resultSet.getString("name");
                        String status = resultSet.getString("status");
                        int customerId = resultSet.getInt("customer_id");
                        String regNumber = resultSet.getString("regNum");
                        int price = resultSet.getInt("price");
                        int km = resultSet.getInt("km");



                        Car car = new Car(id, model, name, status, regNumber, 0, 0, 0, customerId);
                        searchResults.add(car);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Feil", "Databasefeil: " + e.getMessage());
        }

        searchResultTable.setItems(searchResults);


    }


    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
