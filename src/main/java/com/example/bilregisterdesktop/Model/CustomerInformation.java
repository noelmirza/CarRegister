package com.example.bilregisterdesktop.Model;

import javafx.beans.property.*;

public class CustomerInformation {
    private final IntegerProperty customerId;
    private final StringProperty customerName;
    private final IntegerProperty customerNumber;
    private final StringProperty customerEmail;

    public CustomerInformation(int customerId, String customerName, int customerNumber, String customerEmail) {
        this.customerId = new SimpleIntegerProperty(customerId);
        this.customerName = new SimpleStringProperty(customerName);
        this.customerNumber = new SimpleIntegerProperty(customerNumber);
        this.customerEmail = new SimpleStringProperty(customerEmail);
    }

    public String getName() {
        return customerName.get();
    }

    public int getId() {
        return customerId.get();
    }

    public IntegerProperty customerIdProperty() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId.set(customerId);
    }

    public StringProperty customerNameProperty() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName.set(customerName);
    }

    public int getNumber() {
        return customerNumber.get();
    }

    public IntegerProperty customerNumberProperty() {
        return customerNumber;
    }

    public void setCustomerNumber(int customerNumber) {
        this.customerNumber.set(customerNumber);
    }

    public String getEmail() {
        return customerEmail.get();
    }

    public StringProperty customerEmailProperty() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail.set(customerEmail);
    }
}
