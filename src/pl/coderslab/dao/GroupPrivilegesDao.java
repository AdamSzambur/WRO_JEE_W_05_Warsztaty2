package pl.coderslab.dao;
import java.sql.*;

import pl.coderslab.tables.GroupPrivileges;

public class GroupPrivilegesDao {
    private static final String CREATE_QUERY =
            "INSERT INTO group_privileges(group_id, solution, rating) VALUES (?,?,?)";
    private static final String READ_QUERY =
            "SELECT * FROM group_privileges where id = ?";
    private static final String UPDATE_QUERY =
            "UPDATE group_privileges SET group_id = ?, solution = ?, rating = ?  where id = ?";
    private static final String DELETE_QUERY =
            "DELETE FROM group_privileges WHERE id = ?";
    private static final String FIND_BY_GROUP_ID_QUERY =
            "SELECT * FROM group_privileges WHERE group_id = ?";




    public GroupPrivileges create(GroupPrivileges groupPrivileges) {
        try (Connection conn = ConnectionCreator.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(CREATE_QUERY, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, groupPrivileges.getGroupId());
            statement.setInt(2, groupPrivileges.getSolution());
            statement.setInt(3, groupPrivileges.getRating());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                groupPrivileges.setId(resultSet.getInt(1));
            }
            return groupPrivileges;
        } catch (SQLException e) {
            System.err.println("Nie utworzono tablicy dostepu grupy.");
            System.err.println(e.getMessage());
        }
        return null;
    }

    public GroupPrivileges read(int groupPermissionId) {
        try (Connection conn = ConnectionCreator.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(READ_QUERY);
            statement.setInt(1, groupPermissionId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                GroupPrivileges groupPrivileges = new GroupPrivileges();
                groupPrivileges.setId(resultSet.getInt("id"));
                groupPrivileges.setGroupId(resultSet.getInt("group_id"));
                groupPrivileges.setRating(resultSet.getInt("rating"));
                groupPrivileges.setSolution(resultSet.getInt("solution"));
                return groupPrivileges;
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    public void update(GroupPrivileges groupPrivileges) {
        try (Connection conn = ConnectionCreator.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(UPDATE_QUERY);
            statement.setInt(1, groupPrivileges.getGroupId());
            statement.setInt(2, groupPrivileges.getSolution());
            statement.setInt(3, groupPrivileges.getRating());
            statement.setInt(4, groupPrivileges.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Nie zaktualizowano tablicy dostepu grupy.");
            System.err.println(e.getMessage());
        }
    }

    public void delete(int groupPrivilegesId) {
        try (Connection conn = ConnectionCreator.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(DELETE_QUERY);
            statement.setInt(1, groupPrivilegesId);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Nie usunięto  zmiennej opisującej dostęp.");
            System.err.println(e.getMessage());
        }
    }

    public GroupPrivileges findByIdGroup(int groupPrivilegesId) {
        try (Connection conn = ConnectionCreator.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(FIND_BY_GROUP_ID_QUERY);
            statement.setInt(1,groupPrivilegesId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                GroupPrivileges groupPrivileges = new GroupPrivileges();
                groupPrivileges.setId(resultSet.getInt("id"));
                groupPrivileges.setGroupId(resultSet.getInt("group_id"));
                groupPrivileges.setSolution(resultSet.getInt("solution"));
                groupPrivileges.setRating(resultSet.getInt("rating"));
                return groupPrivileges;
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }
}
