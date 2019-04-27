package dao;

import java.sql.*;
import java.util.Arrays;
import tables.Exercise;



public class ExerciseDao {
    private static final String CREATE_QUERY =
            "INSERT INTO exercise(title, description) VALUES (?,?)";
    private static final String READ_QUERY =
            "SELECT * FROM exercise where id = ?";
    private static final String UPDATE_QUERY =
            "UPDATE exercise SET title = ?, description = ?  where id = ?";
    private static final String DELETE_QUERY =
            "DELETE FROM exercise WHERE id = ?";
    private static final String FIND_ALL_QUERY =
            "SELECT * FROM exercise";

    public Exercise create(Exercise exercise) {
        try(Connection conn = ConnectionCreator.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(CREATE_QUERY, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, exercise.getTitle());
            statement.setString(2, exercise.getDescription());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                exercise.setId(resultSet.getInt(1));
            }
            return exercise;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Exercise read(int exerciseId) {
        try (Connection conn = ConnectionCreator.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(READ_QUERY);
            statement.setInt(1, exerciseId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Exercise exercise = new Exercise();
                exercise.setId(resultSet.getInt("id"));
                exercise.setTitle(resultSet.getString("title"));
                exercise.setDescription(resultSet.getString("description"));
                return exercise;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public void update(Exercise exercise) {
        try (Connection conn = ConnectionCreator.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(UPDATE_QUERY);
            statement.setString(1, exercise.getTitle());
            statement.setString(2, exercise.getDescription());
            statement.setInt(3, exercise.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void delete(int exerciseId) {
        try (Connection conn = ConnectionCreator.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(DELETE_QUERY);
            statement.setInt(1, exerciseId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Exercise[] addToArray(Exercise u, Exercise[] exercises) {
        Exercise[] tmpExercises = Arrays.copyOf(exercises, exercises.length + 1);
        tmpExercises[exercises.length] = u;
        return tmpExercises;
    }

    public Exercise[] findAll() {
        try (Connection conn = ConnectionCreator.getConnection()) {
            Exercise[] exercises = new Exercise[0];
            PreparedStatement statement = conn.prepareStatement(FIND_ALL_QUERY);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Exercise exercise = new Exercise();
                exercise.setId(resultSet.getInt("id"));
                exercise.setTitle(resultSet.getString("title"));
                exercise.setDescription(resultSet.getString("description"));
                exercises = addToArray(exercise, exercises);
            }
            return exercises;
        } catch (SQLException e) {
            e.printStackTrace(); return null;
        }
    }
}
