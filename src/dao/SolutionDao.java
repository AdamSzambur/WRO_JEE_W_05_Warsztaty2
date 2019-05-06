package dao;

import tables.Solution;

import java.sql.*;
import java.util.Arrays;


public class SolutionDao {
    private static final String CREATE_QUERY =
            "INSERT INTO solution(created, updated, description, exercise_id, users_id) VALUES (?,?,?,?,?)";
    private static final String READ_QUERY =
            "SELECT * FROM solution where id = ?";
    private static final String UPDATE_QUERY =
            "UPDATE solution SET created=?, updated=?,description=?,exercise_id=?,users_id=?, rating=?, comment=? where id = ?";
    private static final String DELETE_QUERY =
            "DELETE FROM solution WHERE id = ?";
    private static final String FIND_ALL_QUERY =
            "SELECT * FROM solution";
    private static final String FIND_ALL_BY_USER_ID_QUERY =
            "SELECT solution.*, users.username, exercise.title FROM users join solution on users.id=solution.users_id join exercise on exercise.id=solution.exercise_id AND solution.users_id=?";
    private static final String FIND_ALL_BY_EXERCISE_ID_QUERY =
            "SELECT solution.*, users.username, exercise.title FROM users join solution on users.id=solution.users_id join exercise on exercise.id=solution.exercise_id AND exercise.id=? ORDER BY solution.created DESC";

    public Solution create(Solution solution) {
        try (Connection conn = ConnectionCreator.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(CREATE_QUERY, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, solution.getCreated());
            statement.setString(2, solution.getUpdated());
            statement.setString(3, solution.getDescription());
            statement.setInt(4, solution.getExercise_id());
            statement.setInt(5, solution.getUsers_id());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                solution.setId(resultSet.getInt(1));
            }
            return solution;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Solution read(int solutionId) {
        Solution[] result = setSolution(READ_QUERY, solutionId);
        if (result.length > 0) {
            return setSolution(READ_QUERY, solutionId)[0];
        } else return null;
    }

    public void update(Solution solution) {
        try (Connection conn = ConnectionCreator.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(UPDATE_QUERY);
            statement.setString(1, solution.getCreated());
            statement.setString(2, solution.getUpdated());
            statement.setString(3, solution.getDescription());
            statement.setInt(4, solution.getExercise_id());
            statement.setInt(5, solution.getUsers_id());
            statement.setInt(6, solution.getRating());
            statement.setString(7, solution.getComment());
            statement.setInt(8, solution.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int solutionId) {
        try (Connection conn = ConnectionCreator.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(DELETE_QUERY);
            statement.setInt(1, solutionId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Solution[] findAll() {
        return setSolution(FIND_ALL_QUERY, 0);
    }

    public Solution[] findAllByExerciseId(int exerciseId) {
        return setSolution(FIND_ALL_BY_EXERCISE_ID_QUERY, exerciseId);
    }

    public Solution[] findAllByUserId(int userId) {
        return setSolution(FIND_ALL_BY_USER_ID_QUERY, userId);
    }

    private Solution[] addToArray(Solution u, Solution[] solutions) {
        Solution[] tmpGroup = Arrays.copyOf(solutions, solutions.length + 1);
        tmpGroup[solutions.length] = u;
        return tmpGroup;
    }

    private Solution[] setSolution(String query, int setId) {
        try (Connection conn = ConnectionCreator.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(query);
            if (setId > 0) statement.setInt(1, setId);
            ResultSet resultSet = statement.executeQuery();
            Solution[] solutions = new Solution[0];
            while (resultSet.next()) {
                Solution solution = new Solution();
                solution.setId(resultSet.getInt("id"));
                solution.setCreated(resultSet.getString("created"));
                solution.setUpdated(resultSet.getString("updated"));
                solution.setDescription(resultSet.getString("description"));
                solution.setExercise_id(resultSet.getInt("exercise_id"));
                solution.setUsers_id(resultSet.getInt("users_id"));
                solution.setRating(resultSet.getInt("rating"));
                solution.setComment(resultSet.getString("comment"));
                solutions = addToArray(solution, solutions);
            }
            return solutions;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

}
