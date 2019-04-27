package logic;

import dao.GroupDao;
import tables.Group;

import java.util.Scanner;

public class Program3 {
    public static void main(String[] args) {
        GroupDao groupDao = new GroupDao();
        String option;
        Scanner sc = new Scanner(System.in);

        printAllGroups(groupDao);

        do {
            printOptions();
            option = sc.nextLine();
            switch (option) {
                case "add":
                    addGroup(groupDao);
                    break;
                case "edit":
                    editGroup(groupDao);
                    break;
                case "delete":
                    deleteGroup(groupDao);
                    break;
                case "quit":
                    break;
                default:
                    System.err.println("Nie ma takiej opcji, wprowadź ponownie.");
            }
        } while (!option.equals("quit"));

    }

    private static void deleteGroup(GroupDao groupDao) {
        int groupId = 0;
        Scanner sc = new Scanner(System.in);
        System.out.print("Podaj id grupy : ");

        while (!sc.hasNextInt()) {
            sc.nextLine();
            System.err.print("Podana wartość nie jest liczbą. Jeszcze raz podaj indeks grupy : ");
        }
        groupId = sc.nextInt();
        sc.nextLine();

        groupDao.delete(groupId);
        System.out.println();
        printAllGroups(groupDao);
    }

    private static void editGroup(GroupDao groupDao) {
        String name = "";
        int groupId = 0;
        Scanner sc = new Scanner(System.in);
        System.out.print("Podaj id grupy : ");

        while (!sc.hasNextInt()) {
            sc.nextLine();
            System.err.print("Podana wartość nie jest liczbą. Jeszcze raz podaj indeks grupy : ");
        }
        groupId = sc.nextInt();
        sc.nextLine();
        System.out.print("Podaj nazwę grupy : ");
        name = sc.nextLine();

        Group group = new Group(name);
        group.setId(groupId);

        groupDao.update(group);
        System.out.println();
        printAllGroups(groupDao);
    }

    private static void addGroup(GroupDao groupDao) {
        String name = "";

        Scanner sc = new Scanner(System.in);
        System.out.print("Podaj nazwę grupy : ");
        name = sc.nextLine();

        Group group = new Group(name);

        if (groupDao.create(group) != null) {
            System.out.println("Dodano nową grupę do listy.\n");
        }
        printAllGroups(groupDao);
    }

    private static void printAllGroups(GroupDao groupDao) {
        System.out.println("Tablica Zadań :");
        for (Group group : groupDao.findAll()) {
            System.out.println(group);
        }
        System.out.println();
    }

    private static void printOptions() {
        System.out.println("Wybierz jedną z opcji:");
        System.out.println("add – dodanie grupy,");
        System.out.println("edit – edycja grupy,");
        System.out.println("delete – usunięcie grupy,");
        System.out.println("quit – zakończenie programu.");
    }
}
