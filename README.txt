# Car Registry Desktop Application

This project is a JavaFX-based desktop application for managing a car registry. The application allows the user to add, edit, and delete cars and associated customer information in a car registry.

## Features

- Add a new car with information such as model, name, registration number, mileage, price, and year of manufacture.
- Add customer information for an existing car, including the customer's name, phone number, and email address.
- Display an overview of cars in a tabular format with relevant information.
- Delete cars and associated customer information.

## Technologies

- JavaFX: Used for developing the desktop application's interface.
- MySQL Database: Used for storing car and customer information.
- Maven: Used as a build tool and for dependency management.

## Database Setup

1. Install MySQL on your machine if not already installed.
2. Create a new database named `car_registry`.
3. Update the `DatabaseConnector` class with your MySQL username and password.

## Structure

The project is divided into several Java classes for better organization:

- `AddCarController`: Controller class for adding a new car.
- `AddCustomerInfoController`: Controller class for adding customer information.
- `CarTableController`: Controller class for displaying and managing the car table.
- `DatabaseConnector`: Class for creating and managing the database connection.
- ... (and more)

## Setup and Run

1. Clone the project to your local machine.
2. Open the project folder in a Java IDE (e.g., IntelliJ, Eclipse).
3. Run the main application class `HelloApplication` to start the application.

## Dependencies

The project us Maven for dependency management. Maven will download necessary libraries and dependencies when the project is built.

## Contributions

Contributions are welcome! If you'd like to contribute to the project.

## License

This project is licensed under the [MIT License](LICENSE).



