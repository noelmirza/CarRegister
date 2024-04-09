package com.example.bilregisterdesktop.Controller;

import com.example.bilregisterdesktop.Model.Car;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;



import java.io.IOException;

public class DashboardController {

    @FXML
    private TableView<Car> carTable;

    @FXML
    private TableColumn<Car, String> regNumColumn;

    private CarTableController carTableController;

    public void setCarTableController(CarTableController carTableController) {
        this.carTableController = carTableController;
    }

    @FXML
    private void showCars() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/bilregisterdesktop/carTableView.fxml"));
            VBox root = loader.load();

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Car Table View");
            stage.setScene(new Scene(root));

            // Set the CarTableController reference
            CarTableController carTableController = loader.getController();
            carTableController.setDashboardController(this);

            // Show the new window
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void showAddCarForm() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/bilregisterdesktop/addCar.fxml"));
            Parent root = loader.load();

            AddCarController addCarController = loader.getController();
            addCarController.setCarTable(carTable);
            addCarController.setDashboardController(this);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void addCustomerInfo() {
        if (carTable != null) {
            Car selectedCar = carTable.getSelectionModel().getSelectedItem();

            if (selectedCar != null) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/bilregisterdesktop/addCustomerInfo.fxml"));
                    Parent root = loader.load();

                    AddCustomerInfoController addCustomerInfoController = loader.getController();
                    addCustomerInfoController.setCarTable(carTable);
                    addCustomerInfoController.setDashboardController(this);

                    Stage stage = new Stage();
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.setTitle("Legg til kundeinformasjon");
                    stage.setScene(new Scene(root));
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("Error loading addCustomerInfo.fxml: " + e.getMessage());
                }
            } else {
                System.out.println("Please select a car from the table before adding customer information.");
            }
        } else {
            System.out.println("Car table is not initialized.");
        }
    }

    @FXML
    private void searchByRegNum() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/bilregisterdesktop/SearchCar.fxml"));
            VBox root = loader.load();

            SearchCarController searchCarController = loader.getController();
            // Set necessary references to other controllers or components here

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Søk etter bil");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void ModelOrName() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/bilregisterdesktop/nameModel.fxml"));
            VBox root = loader.load();

            SearchCarController searchCarController = loader.getController();
            // Set necessary references to other controllers or components here

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Søk etter bil");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void updateRegisteredCar() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/bilregisterdesktop/updateCar.fxml"));
            VBox root = loader.load();

            UpdateController updateController = loader.getController();

                // Sett nødvendige referanser til andre kontrollere eller komponenter her

                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setTitle("Oppdater Registrert Bil");
                stage.setScene(new Scene(root));

                // Her kan du sette opp referansen til carTableController eller andre nødvendige objekter i UpdateController
                updateController.setDashboardController(this);
                updateController.setCarTableController(carTableController);

                // Show the new window
                stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void addCustomer() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/bilregisterdesktop/addCustomerInfo.fxml"));
            VBox root = loader.load();

            AddCustomerInfoController addCustomerInfoController = loader.getController();
            // Set necessary references to other controllers or components here

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Legg til kunde");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void searchCustomer() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/bilregisterdesktop/searchCustomer.fxml"));
            VBox root = loader.load();

            SearchCustomerInfoController searchCustomerInfoController = loader.getController();
            // Set necessary references to other controllers or components here

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Søk etter kunde");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void updateCustomer() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/bilregisterdesktop/updateCustomer.fxml"));
            Parent root = loader.load();

            UpdateCustomerController updateCustomerController = loader.getController();
            // Set necessary references to other controllers or components here

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Oppdater kunde");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void deleteCustomer() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/bilregisterdesktop/deleteCar.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Slett Bil og Kundens Informasjon");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public void retrieveCarData() {
            // Implementasjon av retrieveCarData-metoden
            // For eksempel:
            if (carTableController != null) {
                carTableController.retrieveData();
            }
        }
    }
