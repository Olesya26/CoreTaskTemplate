package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        Connection connection = Util.getConnectionJDBC();
        try (Statement statement = connection.createStatement()) {
            statement.execute("CREATE TABLE IF NOT EXISTS users " +
                    "(id BIGINT NOT NULL AUTO_INCREMENT," +
                    "name VARCHAR (45) NOT NULL ," +
                    "lastName VARCHAR (45) NOT NULL," +
                    "age TINYINT NOT NULL," +
                    "PRIMARY KEY (id))");
            connection.commit();
        } catch (SQLException e) {
            System.err.println("Ошибка создания таблицы");
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
                System.err.println("Попытка отменить изменения, " +
                        "при получении ошибки во время создания таблицы, не удалась");
            }
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                System.err.println("Ошибка закрытия соединения с БД во время создания таблицы");
                e.printStackTrace();
            }
        }
    }

    public void dropUsersTable() {
        Connection connection = Util.getConnectionJDBC();
        try (Statement statement = connection.createStatement()) {
            statement.execute("DROP TABLE IF EXISTS users");
            connection.commit();
        } catch (SQLException e) {
            System.err.println("Ошибка при удалении таблицы");
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException r) {
                System.err.println("Попытка отменить изменения, при получении ошибки во время удаления таблицы, не удалась");
                r.printStackTrace();
            }
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                System.err.println("Ошибка при закрытие соединения с БД при удалении таблицы");
                e.printStackTrace();
            }
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        Connection connection = Util.getConnectionJDBC();
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO users " +
                "(name, lastName, age) VALUES (?,?,?)")) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();

            connection.commit();
        } catch (SQLException e) {
            System.err.printf("Ошибка при добавлении User - %s, в таблицу ", name);
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException e1) {
                System.err.println("Попытка отменить изменения при получении ошибки," +
                        " во время добавлении User в таблицу, не удалась");
                e1.printStackTrace();
            }
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                System.err.println("Ошибка закрытия соединения с БД при добавлении User в таблицу");
                e.printStackTrace();
            }
        }
    }

    public void removeUserById(long id) {
        Connection connection = Util.getConnectionJDBC();
        try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM users WHERE id = ?")) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            System.err.printf("Попытка удаления User c id: %d не удалась", id);
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException r) {
                System.err.println("Попытка отменить изменения, при получении ошибки" +
                        " во время удаления User из таблицы, не удалась");
                r.printStackTrace();
            }
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                System.err.println("Ошибка закрытия соединения с БД при удалении User");
                e.printStackTrace();
            }
        }
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        Connection connection = Util.getConnectionJDBC();
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM users")) {
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                userList.add(user);
            }
            connection.commit();
        } catch (SQLException e) {
            System.err.println("Ошибка при получении Users из таблицы");
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException e1) {
                System.err.println("Попытка отменить изменения, при получении ошибки" +
                        " во время получения всех Users из таблицы, не удалась");
                e1.printStackTrace();
            }
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                System.err.println("Ошибка закрытия соединения с БД при получении Users из таблицы");
                e.printStackTrace();
            }
        }
        return userList;
    }

    public void cleanUsersTable() {
        Connection connection = Util.getConnectionJDBC();
        try (Statement statement = connection.createStatement();) {
            statement.execute("TRUNCATE TABLE users");
            connection.commit();
        } catch (SQLException e) {
            System.err.println("Ошибка при очищении таблицы");
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException r) {
                System.err.println("Попытка отменить изменения при получении ошибки," +
                        " во время очищения таблицы не удалась");
                r.printStackTrace();
            }
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                System.err.println("Ошибка при закрытии соединения с БД при очищении таблицы");
                e.printStackTrace();
            }
        }
    }
}
