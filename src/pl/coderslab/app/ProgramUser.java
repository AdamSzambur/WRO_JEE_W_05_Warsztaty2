package pl.coderslab.app;

import pl.coderslab.dao.GroupDao;
import pl.coderslab.dao.UserDao;
import pl.coderslab.tables.Group;
import pl.coderslab.tables.User;

import java.io.Console;
import java.util.Scanner;

public class ProgramUser {
    public static void main(String[] args) {

        UserDao userDao = new UserDao();
        String option;
        Scanner sc = new Scanner(System.in);

        printAllUsers(userDao);

        do {
            printOptions();
            option = sc.nextLine();
            switch (option) {
                case "add":
                    addUser(userDao);
                    break;
                case "edit":
                    editUser(userDao);
                    break;
                case "delete":
                    deleteUser(userDao);
                    break;
                case "quit":
                    break;
                default:
                    System.err.println("Nie ma takiej opcji, wprowadź ponownie.");
            }
        } while (!option.equals("quit"));
    }

    public static void printAllUsers(UserDao userDao) {
        System.out.println("Tablica Users :");
        for (User user : userDao.findAll()) {
            System.out.println(user);
        }
        System.out.println();
    }

    public static void printAllGroups() {
        GroupDao groupDao = new GroupDao();
        System.out.println("Tablica Grupy :");
        for (Group group : groupDao.findAll()) {
            System.out.println(group);
        }
        System.out.println();
    }


    private static void addUser(UserDao userDao) {
        String userName;
        String email;
        String password;
        int groupId;

        Scanner sc = new Scanner(System.in);
        System.out.print("Podaj nazwę uzytkownika : ");
        userName = sc.nextLine();
        System.out.print("Podaj adres email : ");
        email = sc.nextLine();
        password = getPassword();
        System.out.println();
        printAllGroups();
        groupId = getIntValue("Podaj id grupy : ");
        User user = new User(userName, email, password, groupId);
        if (userDao.create(user) != null) {
            System.out.println("Dodano nowego klienta do listy.\n");
        }
        printAllUsers(userDao);
    }

    private static void editUser(UserDao userDao) {
        String userName;
        String email;
        String password;
        int groupId;
        int userId;
        Scanner sc = new Scanner(System.in);
        userId = getIntValue("Podaj id uzytkownika : ");
        System.out.print("Podaj nazwę uzytkownika : ");
        userName = sc.nextLine();
        System.out.print("Podaj adres email : ");
        email = sc.nextLine();
        password = getPassword();
        printAllGroups();
        groupId = getIntValue("Podaj id grupy : ");
        User user = new User(userName, email, password, groupId);
        user.setId(userId);
        userDao.update(user);
        System.out.println();
        printAllUsers(userDao);
    }

    private static String getPassword() {
        String password = "1";
        String password2 = "2";

        while (!password.equals(password2)) {
            Console console = System.console();
            if (console == null) {
                Scanner sc = new Scanner(System.in);
                System.out.print("Podaj hasło : ");
                password = sc.nextLine();
                System.out.print("Podaj jeszcze raz hasło : ");
                password2 = sc.nextLine();
            } else {
                password = String.valueOf(console.readPassword("Podaj hasło : "));
                password2 = String.valueOf(console.readPassword("Podaj jeszcze raz hasło : "));
            }

            if (!password.equals(password2)) {
                System.err.println("Podane hasla nie sa takie same.");
            }
        }
        return password;
    }


    private static void deleteUser(UserDao userDao) {
        int userId;
        userId = getIntValue("Podaj id uzytkownika : ");
        userDao.delete(userId);
        System.out.println();
        printAllUsers(userDao);
    }

    private static int getIntValue(String title) {
        Scanner sc = new Scanner(System.in);
        System.out.print(title);
        while (!sc.hasNextInt()) {
            sc.nextLine();
            System.err.print("Podana wartość nie jest liczbą. Jeszcze raz podaj indeks : ");
        }
        return sc.nextInt();
    }

    private static void printOptions() {
        System.out.println("Wybierz jedną z opcji:");
        System.out.println("add – dodanie użytkownika,");
        System.out.println("edit – edycja użytkownika,");
        System.out.println("delete – usunięcie użytkownika,");
        System.out.println("quit – zakończenie programu.");
    }
}
