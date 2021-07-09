package jm.task.core.jdbc;


import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();

        userService.saveUser("Olesya", "Kolyadina", (byte) 30);
        System.out.println("User с именем – Olesya добавлен в базу данных");
        userService.saveUser("Kristina", "Ponomareva", (byte) 27);
        System.out.println("User с именем – Kristina добавлен в базу данных");
        userService.saveUser("Julia", "Sclyanova", (byte) 28);
        System.out.println("User с именем – Julia добавлен в базу данных");
        userService.saveUser("Liana", "Lagazidze", (byte) 28);
        System.out.println("User с именем – Liana добавлен в базу данных");

        List<User> allUsers = userService.getAllUsers();
        System.out.println(allUsers);
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
