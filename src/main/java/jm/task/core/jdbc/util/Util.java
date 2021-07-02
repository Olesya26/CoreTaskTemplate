package jm.task.core.jdbc.util;


import jm.task.core.jdbc.model.User;

import org.hibernate.SessionFactory;

import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class Util {
    private final static String DRIVER_NAME = "com.mysql.cj.jdbc.Driver";
    private final static String USERNAME = "root";
    private final static String PASSWORD = "babilich26";
    private final static String URL = "jdbc:mysql://localhost:3306/mysql";
    private final static String DIALECT = "org.hibernate.dialect.MySQL8Dialect";

    public static SessionFactory getConnectorHibernate() {
        Configuration configuration = new Configuration();
        Properties properties = new Properties();
        properties.put(Environment.DRIVER, DRIVER_NAME);
        properties.put(Environment.USER, USERNAME);
        properties.put(Environment.PASS, PASSWORD);
        properties.put(Environment.URL, URL);
        properties.put(Environment.DIALECT, DIALECT);
        properties.put(Environment.HBM2DDL_AUTO, "create-drop");
        configuration.setProperties(properties).addAnnotatedClass(User.class);
        return configuration.buildSessionFactory();

    }


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
