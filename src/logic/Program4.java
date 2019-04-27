package logic;

import dao.ExerciseDao;
import dao.SolutionDao;
import dao.UserDao;
import tables.Solution;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import static logic.Program1.printAllUsers;
import static logic.Program2.printAllExercises;

public class Program4 {
    public static void main(String[] args) {
        SolutionDao solutionDao = new SolutionDao();
        String option;
        Scanner sc = new Scanner(System.in);

        do {
            printOptions();
            option = sc.nextLine();
            switch (option) {
                case "add":
                    addExerciseToUser();
                    break;
                case "view":
                    viewSolution(solutionDao);
                    break;
                case "quit":
                    break;
                default:
                    System.err.println("Nie ma takiej opcji, wprowadź ponownie.");
            }
        } while (!option.equals("quit"));

    }

    private static void viewSolution(SolutionDao solutionDao) {
        printAllUsers(new UserDao());
        Scanner sc = new Scanner(System.in);
        int userId = 0;
        System.out.print("Podaj id uzytkownika dla ktorego chcesz wyswietlic rozwiazania : ");

        while (!sc.hasNextInt()) {
            sc.nextLine();
            System.err.print("Podana wartość nie jest liczbą. Jeszcze raz podaj indeks uzytkownika : ");
        }
        userId = sc.nextInt();
        sc.nextLine();
        System.out.println();

        System.out.println("Tablica Zadań :");
        for (Solution solution : solutionDao.findAllByUserId(userId)) {
            System.out.println(solution);
        }
        System.out.println();
    }


    private static void addExerciseToUser() {
        printAllUsers(new UserDao());
        Scanner sc = new Scanner(System.in);

        int userId = 0;
        System.out.print("Podaj id uzytkownika : ");

        while (!sc.hasNextInt()) {
            sc.nextLine();
            System.err.print("Podana wartość nie jest liczbą. Jeszcze raz podaj indeks uzytkownika : ");
        }
        userId = sc.nextInt();
        sc.nextLine();
        System.out.println();
        printAllExercises(new ExerciseDao());

        int exerciseId = 0;
        System.out.print("Podaj id zadania : ");

        while (!sc.hasNextInt()) {
            sc.nextLine();
            System.err.print("Podana wartość nie jest liczbą. Jeszcze raz podaj indeks zadania : ");
        }
        exerciseId = sc.nextInt();
        sc.nextLine();

        String pattern = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        String date = simpleDateFormat.format(new Date());

        Solution solution = new Solution();
        solution.setCreated(simpleDateFormat.format(new Date()));
        solution.setUsers_id(userId);
        solution.setExercise_id(exerciseId);

        SolutionDao solutionDao = new SolutionDao();
        if (solutionDao.create(solution) != null) {
            System.out.println("Dodano rozwiazanie dla podanego uzytkownika i zadania.\n");
        }
    }

    private static void printOptions() {
        System.out.println("Wybierz jedną z opcji:");
        System.out.println("add – dodanie zadania do użytkownika,");
        System.out.println("view – podgląd rozwiazan dla danego uzytkownika,");
        System.out.println("quit – zakończenie programu.");
    }
}
