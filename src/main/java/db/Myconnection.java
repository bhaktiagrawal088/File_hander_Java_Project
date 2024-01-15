package db;

import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;

public class Myconnection {
    public static Connection connection = null;

    // Declare a method for opened the connection
    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/filehanderproject?useSSL=false",
                    "root", "bhaktiagrawal@06");

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Connection Successfully!");
        return connection;
    }

    // Declare a method for a closed the connection
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
