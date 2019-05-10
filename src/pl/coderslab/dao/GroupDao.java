package pl.coderslab.dao;

import pl.coderslab.tables.Group;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GroupDao {
    private static final String CREATE_QUERY =
            "INSERT INTO user_group(name) VALUES (?)";
    private static final String READ_QUERY =
            "SELECT * FROM user_group where id = ?";
    private static final String UPDATE_QUERY =
            "UPDATE user_group SET name = ? where id = ?";
    private static final String DELETE_QUERY =
            "DELETE FROM user_group WHERE id = ?";
    private static final String FIND_ALL_QUERY =
            "SELECT * FROM user_group";

    public Group create(Group group) {
        try (Connection conn = ConnectionCreator.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(CREATE_QUERY, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, group.getName());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                group.setId(resultSet.getInt(1));
            }
            return group;
        } catch (SQLException e) {
            System.err.println("Nie utworzono grupy.");
            System.err.println(e.getMessage());
        }
        return null;
    }

    public Group read(int groupId) {
        try (Connection conn = ConnectionCreator.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(READ_QUERY);
            statement.setInt(1, groupId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Group group = new Group();
                group.setId(resultSet.getInt("id"));
                group.setName(resultSet.getString("name"));
                return group;
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    public void update(Group group) {
        try (Connection conn = ConnectionCreator.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(UPDATE_QUERY);
            statement.setString(1, group.getName());
            statement.setInt(2, group.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Nie zaktualizowano grupy.");
            System.err.println(e.getMessage());
        }
    }

    public void delete(int groupId) {
        try (Connection conn = ConnectionCreator.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(DELETE_QUERY);
            statement.setInt(1, groupId);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Nie usunięto grupy.");
            System.err.println(e.getMessage());
        }
    }

    public Group[] findAll() {
        try (Connection conn = ConnectionCreator.getConnection()) {
            List<Group> groups = new ArrayList<>();
            PreparedStatement statement = conn.prepareStatement(FIND_ALL_QUERY);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Group group = new Group();
                group.setId(resultSet.getInt("id"));
                group.setName(resultSet.getString("name"));
                groups.add(group);
            }
            return groups.toArray(new Group[0]);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }
}
