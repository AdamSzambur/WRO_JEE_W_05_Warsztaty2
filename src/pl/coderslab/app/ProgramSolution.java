package pl.coderslab.app;

import pl.coderslab.dao.ExerciseDao;
import pl.coderslab.dao.SolutionDao;
import pl.coderslab.dao.UserDao;
import pl.coderslab.tables.Exercise;
import pl.coderslab.tables.Solution;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import static pl.coderslab.app.ProgramUser.printAllUsers;

public class ProgramSolution {
    public static void main(String[] args) {
        SolutionDao solutionDao = new SolutionDao();
        String option;
        Scanner sc = new Scanner(System.in);

        do {
            printOptions();
            option = sc.nextLine();
            switch (option) {
                case "add":
                    addSolutionToUser(solutionDao);
                    break;
                case "view":
                    viewSolution(solutionDao);
                    break;
                case "view_all":
                    viewSolutionExercise(solutionDao);
                    break;
                case "quit":
                    break;
                default:
                    System.err.println("Nie ma takiej opcji, wprowadź ponownie.");
            }
        } while (!option.equals("quit"));

    }

    private static void viewSolutionExercise(SolutionDao solutionDao) {
        printAllExercises();
        Scanner sc = new Scanner(System.in);
        int exerciseId = getIntValue("Podaj id zadania dla ktorego chcesz wyswietlic rozwiazania : ");
        System.out.println();

        System.out.println("Tablica Rozwiązań :");
        for (Solution solution : solutionDao.findAllByExerciseId(exerciseId)) {
            System.out.println(solution);
        }
        System.out.println();
    }

    private static void viewSolution(SolutionDao solutionDao) {
        printAllUsers(new UserDao());
        Scanner sc = new Scanner(System.in);
        int userId = getIntValue("Podaj id uzytkownika dla ktorego chcesz wyswietlic rozwiazania : ");
        System.out.println();
        System.out.println("Tablica Rozwiązań :");
        for (Solution solution : solutionDao.findAllByUserId(userId)) {
            System.out.println(solution);
        }
        System.out.println();
    }


    private static void addSolutionToUser(SolutionDao solutionDao) {
        printAllUsers(new UserDao());
        int userId = getIntValue("Podaj id uzytkownika : ");
        System.out.println();
        printAllExercises();
        int exerciseId = getIntValue("Podaj id zadania : ");
        Solution solution = new Solution();
        solution.setCreated(currentDate());
        solution.setUsers_id(userId);
        solution.setExercise_id(exerciseId);
        if (solutionDao.create(solution) != null) {
            System.out.println("Dodano rozwiazanie dla podanego uzytkownika i zadania.\n");
        }
    }

    private static void printOptions() {
        System.out.println("Wybierz jedną z opcji:");
        System.out.println("add – dodaj rozwiązanie zadania dla użytkownika,");
        System.out.println("view – podgląd rozwiazan dla uzytkownika,");
        System.out.println("view_all – podgląd rozwiazan dla zadania,");
        System.out.println("quit – zakończenie programu.");
    }

    public static void printAllExercises() {
        ExerciseDao exerciseDao = new ExerciseDao();
        System.out.println("Tablica Zadania :");
        for (Exercise exercise : exerciseDao.findAll()) {
            System.out.println(exercise);
        }
        System.out.println();
    }

    private static String currentDate() {
        String pattern = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(new Date());
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
