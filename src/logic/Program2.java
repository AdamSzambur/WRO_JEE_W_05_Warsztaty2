package logic;

import dao.ExerciseDao;
import tables.Exercise;

import java.util.Scanner;


public class Program2 {
    public static void main(String[] args) {
        ExerciseDao exerciseDao = new ExerciseDao();
        String option;
        Scanner sc = new Scanner(System.in);

        printAllExercises(exerciseDao);

        do {
            printOptions();
            option = sc.nextLine();
            switch (option) {
                case "add":
                    addExercise(exerciseDao);
                    break;
                case "edit":
                    editExercise(exerciseDao);
                    break;
                case "delete":
                    deleteExercise(exerciseDao);
                    break;
                case "quit":
                    break;
                default:
                    System.err.println("Nie ma takiej opcji, wprowadź ponownie.");
            }
        } while (!option.equals("quit"));

    }

    private static void deleteExercise(ExerciseDao exerciseDao) {
        int exerciseId = 0;
        Scanner sc = new Scanner(System.in);
        System.out.print("Podaj id zadania : ");

        while (!sc.hasNextInt()) {
            sc.nextLine();
            System.err.print("Podana wartość nie jest liczbą. Jeszcze raz podaj indeks zadania : ");
        }
        exerciseId = sc.nextInt();
        sc.nextLine();

        exerciseDao.delete(exerciseId);
        System.out.println();
        printAllExercises(exerciseDao);
    }

    private static void editExercise(ExerciseDao exerciseDao) {
        String title = "";
        String description = "";
        int exerciseId = 0;
        Scanner sc = new Scanner(System.in);
        System.out.print("Podaj id zadania : ");

        while (!sc.hasNextInt()) {
            sc.nextLine();
            System.err.print("Podana wartość nie jest liczbą. Jeszcze raz podaj indeks zadania : ");
        }
        exerciseId = sc.nextInt();
        sc.nextLine();
        System.out.print("Podaj tutuł zadania : ");
        title = sc.nextLine();
        System.out.print("Podaj opis zadania : ");
        description = sc.nextLine();

        Exercise exercise = new Exercise(title, description);
        exercise.setId(exerciseId);

        exerciseDao.update(exercise);
        System.out.println();
        printAllExercises(exerciseDao);
    }

    private static void addExercise(ExerciseDao exerciseDao) {
        String title = "";
        String description = "";

        Scanner sc = new Scanner(System.in);
        System.out.print("Podaj tutuł zadania : ");
        title = sc.nextLine();
        System.out.print("Podaj opis zadania : ");
        description = sc.nextLine();

        Exercise exercise = new Exercise(title, description);

        if (exerciseDao.create(exercise) != null) {
            System.out.println("Dodano nowe zadanie do listy.\n");
        }
        printAllExercises(exerciseDao);
    }

    public static void printAllExercises(ExerciseDao exerciseDao) {
        System.out.println("Tablica Zadań :");
        for (Exercise exercise : exerciseDao.findAll()) {
            System.out.println(exercise);
        }
        System.out.println();
    }

    private static void printOptions() {
        System.out.println("Wybierz jedną z opcji:");
        System.out.println("add – dodanie zadania,");
        System.out.println("edit – edycja zadania,");
        System.out.println("delete – usunięcie zadania,");
        System.out.println("quit – zakończenie programu.");
    }
}
