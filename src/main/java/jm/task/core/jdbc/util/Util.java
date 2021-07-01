package jm.task.core.jdbc.util;


import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.SQLException;


public class Util {
    private final static String DRIVER_NAME = "com.mysql.cj.jdbc.Driver";
    private final static String USERNAME = "root";
    private final static String PASSWORD = "babilich26";
    private final static String URL = "jdbc:mysql://localhost:3306/mysql";


    public static Connection getConnectionJDBC() {
        Connection connectionMySQL = null;
        try {
            Class.forName(DRIVER_NAME);
            connectionMySQL = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            connectionMySQL.setAutoCommit(false);
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Ошибка при попытке подключения к БД и соединения с БД");
            e.printStackTrace();
        }
        return connectionMySQL;
    }
}
