package app;

import dao.GroupDao;
import dao.UserDao;
import tables.Group;
import tables.User;

import java.util.Scanner;

public class ProgramGroup {
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
                case "show_all":
                    showAllUsersInGroup(groupDao);
                    break;
                case "quit":
                    break;
                default:
                    System.err.println("Nie ma takiej opcji, wprowadź ponownie.");
            }
        } while (!option.equals("quit"));

    }

    private static void showAllUsersInGroup(GroupDao groupDao) {
        printAllGroups(groupDao);
        int groupId;
        groupId = getIntValue("Podaj id grupy : ");
        printAllUsersForGroupId(groupId);
        System.out.println();
    }

    private static void deleteGroup(GroupDao groupDao) {
        printAllGroups(groupDao);
        int groupId;
        groupId = getIntValue("Podaj id grupy : ");
        groupDao.delete(groupId);
        System.out.println();
        printAllGroups(groupDao);
    }

    private static void editGroup(GroupDao groupDao) {
        printAllGroups(groupDao);
        String name;
        int groupId;
        Scanner sc = new Scanner(System.in);
        groupId = getIntValue("Podaj id grupy : ");
        System.out.print("Podaj nazwę grupy : ");
        name = sc.nextLine();
        int ratingAccess = getIntValue("Podaj dostep do wystawiania ocen [0/1] : ");
        int solutionAccess = getIntValue("Podaj dostep do dodawania i edycji rozwiązań [0/1] : ");
        Group group = new Group(name, ratingAccess,solutionAccess);
        group.setId(groupId);
        groupDao.update(group);
        System.out.println();
        printAllGroups(groupDao);
    }

    private static void addGroup(GroupDao groupDao) {
        String name;
        Scanner sc = new Scanner(System.in);
        System.out.print("Podaj nazwę grupy : ");
        name = sc.nextLine();
        int ratingAccess = getIntValue("Podaj dostep do wystawiania ocen [0/1] : ");
        int solutionAccess = getIntValue("Podaj dostep do dodawania i edycji rozwiązań [0/1] : ");
        Group group = new Group(name,ratingAccess,solutionAccess);

        if (groupDao.create(group) != null) {
            System.out.println("Dodano nową grupę do listy.\n");
        }
        printAllGroups(groupDao);
    }

    private static void printAllGroups(GroupDao groupDao) {
        System.out.println("Tablica Grup :");
        for (Group group : groupDao.findAll()) {
            System.out.println(group);
        }
        System.out.println();
    }

    private static void printAllUsersForGroupId(int groupId) {
        UserDao userDao = new UserDao();

        System.out.println("Lista użytkowników w grupie '"+ new GroupDao().read(groupId).getName()+"'");
        for (User user : userDao.findAllByGroupId(groupId)) {
            System.out.println(user);
        }
        System.out.println();
    }

    private static void printOptions() {
        System.out.println("Wybierz jedną z opcji:");
        System.out.println("add – dodanie grupy,");
        System.out.println("edit – edycja grupy,");
        System.out.println("delete – usunięcie grupy,");
        System.out.println("show_all - pokaz liste uzytkowników dla grupy");
        System.out.println("quit – zakończenie programu.");
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
}
