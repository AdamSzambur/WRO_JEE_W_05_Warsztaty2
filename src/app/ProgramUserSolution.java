package app;

import dao.ExerciseDao;
import dao.GroupDao;
import dao.SolutionDao;
import dao.UserDao;
import tables.Exercise;
import tables.Solution;
import tables.User;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class ProgramUserSolution {
    public static void main(String[] args) {
        UserDao userDao = new UserDao();
        User user;
        String option;
        Scanner sc = new Scanner(System.in);

        if (args.length > 0) {
            if ((user = userDao.read(Integer.parseInt(args[0]))) != null) {
                do {
                    System.out.println("Aktywny uzytkownik : id " + user.getId() + ", name : " + user.getUserName() + "\n");
                    printOptions(user);
                    option = sc.nextLine();
                    switch (option) {
                        case "add":
                            addSolution(user);
                            break;
                        case "view":
                            viewSolution(user);
                            break;
                        case "update":
                            updateSolution(user);
                            break;
                        case "view_all":
                            view_all();
                            break;
                        case "rating_comment":
                            ratingComment();
                            break;
                        case "quit":
                            break;
                        default:
                            System.err.println("Nie ma takiej opcji, wprowadź ponownie.");
                    }
                } while (!option.equals("quit"));


            } else {
                System.err.println("Nie znaleziono uzytkownika o podanym indeksie.");
            }
        } else {
            System.err.println("Nie podałeś indeksu użytkownika.");
        }
    }

    private static void ratingComment() {
        view_all();
        System.out.println();
        int solutionId = getIntValue("Podaj numer indeksu rozwiazania ktore chcesz ocenic :");
        SolutionDao solutionDao = new SolutionDao();
        Solution solution = solutionDao.read(solutionId);
        solution.setRating(getIntValue("Podaj ocenę dla wybranego zadania : "));
        System.out.println("Podaj komentarz do rozwiązania : ");
        solution.setComment(new Scanner(System.in).nextLine());
        solutionDao.update(solution);
        System.out.println();
    }

    private static void view_all() {
        System.out.println("Tablica wszystkich rozwiazan dla wszystkich uzytkowników");
        SolutionDao solutionDao = new SolutionDao();
        for (Solution solution : solutionDao.findAll()) {
            System.out.println(solution);
        }
        System.out.println();
    }

    private static void updateSolution(User user) {
        viewSolution(user);
        Scanner sc = new Scanner(System.in);
        int solutionId = getIntValue("Podaj indeks rozwiazania ktore chcesz zaktualizaowac : ");
        SolutionDao solutionDao = new SolutionDao();
        Solution solution = solutionDao.read(solutionId);
        if ((solution != null) && (solution.getUsers_id() == user.getId())) {
            System.out.println("Podaj nowy opis rozwiazania :");
            solution.setDescription(sc.nextLine());
            solution.setUpdated(currentDate());
            solutionDao.update(solution);
        } else {
            System.err.println("Rozwiazanie o podanym indeksie nie istnieje lub nie nalezy do uzytkownika");
        }
        System.out.println();
    }

    private static void viewSolution(User user) {
        SolutionDao solutionDao = new SolutionDao();
        System.out.println("Rozwiązania dla podanego uzytkownika");
        for (Solution solution : solutionDao.findAllByUserId(user.getId())) {
            System.out.println(solution);
        }
        System.out.println();
    }

    private static void addSolution(User user) {
        ExerciseDao exerciseDao = new ExerciseDao();
        Scanner sc = new Scanner(System.in);
        Exercise[] userExercisesNotSolved = exerciseDao.findAllNotSolvedByUserId(user.getId());
        System.out.println("Tablica zadań nie wykonanych przez uzytkownika :");
        for (Exercise exercise : userExercisesNotSolved) {
            System.out.println(exercise);
        }
        System.out.println();

        int exerciseId = getIntValue("Podaj indeks zadania dla ktorego chcesz dodac rozwiązanie : ");
        // sprawdzamy czy podany numer zadania znajduje sie na liscie zadan nierozwiazanych
        boolean torf = false;
        for (Exercise exercise : userExercisesNotSolved) {
            if (exercise.getId() == exerciseId) torf = true;
        }

        if (torf) {
            SolutionDao solutionDao = new SolutionDao();
            System.out.println("Podaj opis rozwiązania :");
            Solution solution = new Solution();
            solution.setCreated(currentDate());
            solution.setUsers_id(user.getId());
            solution.setExercise_id(exerciseId);
            solution.setDescription(sc.nextLine());

            if (solutionDao.create(solution) != null) {
                System.out.println("Dodano rozwiazanie dla podanego uzytkownika i zadania.\n");
            }

        } else {
            System.err.println("Podany numer zadania nie znajduje sie na liscie zadan nierozwiazanych.");
        }
        System.out.println();
    }

    private static String currentDate() {
        String pattern = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(new Date());
    }

    private static void printOptions(User user) {
        GroupDao groupDao = new GroupDao();

        System.out.println("Wybierz jedną z opcji:");
        // "dostep" tylko dla uzytkownikow dla ktorych grupa ma solution_access =1
        if (groupDao.read(user.getGroupId()).getSolutionAccess() == 1) {
            System.out.println("add – dodawanie rozwiązania");
            System.out.println("view – przeglądanie swoich rozwiązań.");
            System.out.println("udate – aktualizacja rozwiązań.");
        }
        // "dostep" tylko dla uzytkowników dla ktorych grupa ma rating_access = 1
        if (groupDao.read(user.getGroupId()).getRatingAccess() == 1) {
            System.out.println("view_all – przeglądanie wszystkich rozwiązań.");
            System.out.println("rating_comment– ocena i komentarz do rozwiązania.");
        }

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
