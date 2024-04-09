package com.example.bilregisterdesktop.Controller;

import com.example.bilregisterdesktop.Database.DatabaseConnector;
import com.example.bilregisterdesktop.Model.CustomerInformation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UpdateCustomerController {

    @FXML
    private ComboBox<CustomerInformation> customerComboBox;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField numberTextField;

    @FXML
    private TextField emailTextField;

    @FXML
    private TextField newInfoTextField;

    @FXML
    private ComboBox<String> infoTypeComboBox;

    @FXML
    private void initialize() {
        // Set up the ComboBox to display customer name, number, and email
        customerComboBox.setCellFactory(param -> new ListCell<CustomerInformation>() {
            @Override
            protected void updateItem(CustomerInformation item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getName() + " " + item.getNumber() + " " + item.getEmail());
                }
            }
        });

        // Set up the StringConverter to convert Customer object to a string representation
        customerComboBox.setConverter(new StringConverter<CustomerInformation>() {
            @Override
            public String toString(CustomerInformation object) {
                if (object == null) {
                    return null;
                } else {
                    return object.getName() + " " + object.getNumber() + " " + object.getEmail();
                }
            }

            @Override
            public CustomerInformation fromString(String string) {
                // You might need to implement this method if needed
                return null;
            }
        });

        // Populate customerComboBox with customers from the database
        ObservableList<CustomerInformation> customerList = fetchCustomerDataFromDatabase();
        customerComboBox.setItems(customerList);

        // Populate infoTypeComboBox with information types
        ObservableList<String> infoTypes = FXCollections.observableArrayList("Name", "Number", "Email");
        infoTypeComboBox.setItems(infoTypes);
    }

    private ObservableList<CustomerInformation> fetchCustomerDataFromDatabase() {
        ObservableList<CustomerInformation> customerList = FXCollections.observableArrayList();

        try (Connection connection = DatabaseConnector.getConnection()) {
            String query = "SELECT * FROM customer_information";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        int id = resultSet.getInt("customer_id");
                        String name = resultSet.getString("customer_name");
                        String number = resultSet.getString("customer_number");
                        String email = resultSet.getString("customer_email");

                        CustomerInformation customer = new CustomerInformation(id, name, 0, email);
                        customerList.add(customer);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Feil", "Databasefeil: " + e.getMessage());
        }

        return customerList;
    }

    @FXML
    private void updateCustomerInfo() {
        CustomerInformation selectedCustomer = customerComboBox.getValue();
        if (selectedCustomer == null) {
            showAlert("Feil", "Velg en kunde før du oppdaterer informasjonen.");
            return;
        }

        String newInfoType = infoTypeComboBox.getValue();
        String newInfo = newInfoTextField.getText();

        updateCustomerInfoInDatabase(selectedCustomer, newInfoType, newInfo);
    }

    private void updateCustomerInfoInDatabase(CustomerInformation customer, String infoType, String newInfo) {
        try (Connection connection = DatabaseConnector.getConnection()) {
            String updateQuery = null;

            switch (infoType) {
                case "Name":
                    updateQuery = "UPDATE customer_information SET customer_name=? WHERE customer_id=?";
                    break;
                case "Number":
                    updateQuery = "UPDATE customer_information SET customer_number=? WHERE customer_id=?";
                    break;
                case "Email":
                    updateQuery = "UPDATE customer_information SET customer_email=? WHERE customer_id=?";
                    break;
            }

            if (updateQuery != null) {
                try (PreparedStatement statement = connection.prepareStatement(updateQuery)) {
                    statement.setString(1, newInfo);
                    statement.setInt(2, customer.getId());
                    int rowsUpdated = statement.executeUpdate();

                    if (rowsUpdated > 0) {
                        // Oppdater kundeinformasjonen etter vellykket oppdatering
                        switch (infoType) {
                            case "Name":
                                customer.setCustomerName(newInfo);
                                break;
                            case "Number":
                                customer.setCustomerNumber(Integer.parseInt(newInfo));
                                break;
                            case "Email":
                                customer.setCustomerEmail(newInfo);
                                break;
                        }

                        showAlert("Suksess", "Kundeinformasjon oppdatert!");
                    } else {
                        showAlert("Feil", "Ingen oppdatering ble utført. Sjekk informasjonen og prøv igjen.");
                    }
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
