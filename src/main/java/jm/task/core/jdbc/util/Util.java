package jm.task.core.jdbc.util;


import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Util {
    private final static String USERNAME = "root";
    private final static String PASSWORD = "babilich26";
    private final static String URL = "jdbc:mysql://localhost:3306/mysql";

    public static Connection getConnection() {
        Connection connectionMySQL = null;
        Driver driverMySQL;

        try {
            driverMySQL = new com.mysql.cj.jdbc.Driver();
            DriverManager.registerDriver(driverMySQL);
            connectionMySQL = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connectionMySQL;
    }
}
