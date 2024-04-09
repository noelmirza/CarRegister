package com.example.bilregisterdesktop.Database;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseQuery {
    public static void retrieveData() {
        try (Connection connection = DatabaseConnector.getConnection()) {
            String query = "SELECT * FROM Car";
            try (PreparedStatement statement = connection.prepareStatement(query);
                 ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    // Håndter resultatene her
                    String model = resultSet.getString("model");
                    // Gjør noe med verdien, for eksempel skriv ut den
                    System.out.println("model: " + model);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
