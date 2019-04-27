package logic;

import dao.UserDao;
import tables.User;
import java.util.Scanner;

public class Program1 {
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

    private static void addUser(UserDao userDao) {
        String userName = "";
        String email = "";
        String password = "";

        Scanner sc = new Scanner(System.in);
        System.out.print("Podaj nazwę uzytkownika : ");
        userName = sc.nextLine();
        System.out.print("Podaj adres email : ");
        email = sc.nextLine();
        System.out.print("Podaj hasło : ");
        password = sc.nextLine();
        User user = new User(userName, email, password, 0);
        if (userDao.create(user) != null) {
            System.out.println("Dodano nowego klienta do listy.\n");
        }
        printAllUsers(userDao);
    }


    private static void editUser(UserDao userDao) {
        String userName = "";
        String email = "";
        String password = "";
        int userId = 0;
        Scanner sc = new Scanner(System.in);

        System.out.print("Podaj id uzytkownika : ");

        while (!sc.hasNextInt()){
            sc.nextLine();
            System.err.print("Podana wartość nie jest liczbą. Jeszcze raz podaj indeks uzytkownika : ");
        }
        userId = sc.nextInt();
        sc.nextLine();

        System.out.print("Podaj nazwę uzytkownika : ");
        userName = sc.nextLine();
        System.out.print("Podaj adres email : ");
        email = sc.nextLine();
        System.out.print("Podaj hasło : ");
        password = sc.nextLine();
        User user = new User(userName, email, password, 0);
        user.setId(userId);
        userDao.update(user);
        System.out.println();
        printAllUsers(userDao);
    }


    private static void deleteUser(UserDao userDao) {
        int userId = 0;
        Scanner sc = new Scanner(System.in);
        System.out.print("Podaj id uzytkownika : ");

        while (!sc.hasNextInt()){
            sc.nextLine();
            System.err.print("Podana wartość nie jest liczbą. Jeszcze raz podaj indeks uzytkownika : ");
        }
        userId = sc.nextInt();
        sc.nextLine();

        userDao.delete(userId);
        System.out.println();
        printAllUsers(userDao);
    }

    private static void printOptions() {
        System.out.println("Wybierz jedną z opcji:");
        System.out.println("add – dodanie użytkownika,");
        System.out.println("edit – edycja użytkownika,");
        System.out.println("delete – usunięcie użytkownika,");
        System.out.println("quit – zakończenie programu.");
    }
}
