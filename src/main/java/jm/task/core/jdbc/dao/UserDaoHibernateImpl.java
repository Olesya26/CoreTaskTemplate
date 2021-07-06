package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    SessionFactory sessionFactory = Util.getConnectorHibernate();

    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.createSQLQuery("CREATE TABLE IF NOT EXISTS users " +
                    "(id BIGINT NOT NULL AUTO_INCREMENT," +
                    "name VARCHAR (255) NOT NULL ," +
                    "lastName VARCHAR (255) NOT NULL," +
                    "age TINYINT NOT NULL," +
                    "PRIMARY KEY (id))")
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            System.err.println("Ошибка при создании таблицы");
            session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public void dropUsersTable() {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS users").executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            System.err.println("Ошибка при удалении таблицы");
            session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            User user = new User(name, lastName, age);
            session.save(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.err.printf("Ошибка при добавлении User - %s в таблицу\n", name);
            session.getTransaction().commit();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public void removeUserById(long id) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            User user = session.get(User.class, id);
            session.delete(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.err.printf("Ошибка при удалении User с id: %d\n", id);
            session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> usersList = new ArrayList<>();
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            usersList = session.createQuery("FROM User",User.class).getResultList();
            session.getTransaction().commit();
        } catch (Exception e) {
            System.err.println("Ошибка при получении все Users из таблицы");
            session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return usersList;
    }

    @Override
    public void cleanUsersTable() {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.createQuery("DELETE FROM User ").executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            System.err.println("Ошибка во время удаления всех данных из таблицы");
            session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}
