package jm.task.core.jdbc;


import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args)  {
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();

        User user1 = new User("Olesya", "Kolyadina", (byte) 30);
        User user2 = new User("Kristina", "Ponomareva", (byte) 27);
        User user3 = new User("Julia", "Sclyanova", (byte) 28);
        User user4 = new User("Liana", "Lagazidze", (byte) 28);

        List<User> userList = new ArrayList<>();
        userList.add(user1);
        userList.add(user2);
        userList.add(user3);
        userList.add(user4);

        for (User user : userList) {
            userService.saveUser(user.getName(),user.getLastName(),user.getAge());
            printUser(user);
        }

        List<User> allUsers = userService.getAllUsers();
        for (User user : allUsers) {
            System.out.println(user);
        }

        userService.getAllUsers();

        for (User user : allUsers) {
            userService.removeUserById(user.getId());
        }

        userService.dropUsersTable();
    }

    private static void printUser(User user) {
        System.out.printf("User с именем – %s добавлен в базу данных\n", user.getName());

    }
}
