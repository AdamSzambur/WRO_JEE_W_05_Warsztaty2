package dao;

import tables.Group;

import java.sql.*;
import java.util.Arrays;

public class GroupDao {
    private static final String CREATE_QUERY =
            "INSERT INTO user_group(name,rating_access, solution_access) VALUES (?,?,?)";
    private static final String READ_QUERY =
            "SELECT * FROM user_group where id = ?";
    private static final String UPDATE_QUERY =
            "UPDATE user_group SET name = ?, rating_access = ?, solution_access = ?  where id = ?";
    private static final String DELETE_QUERY =
            "DELETE FROM user_group WHERE id = ?";
    private static final String FIND_ALL_QUERY =
            "SELECT * FROM user_group";

    public Group create(Group group) {
        try (Connection conn = ConnectionCreator.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(CREATE_QUERY, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, group.getName());
            statement.setInt(2, group.getRatingAccess());
            statement.setInt(3, group.getSolutionAccess());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                group.setId(resultSet.getInt(1));
            }
            return group;
        } catch (SQLException e) {
            e.printStackTrace();
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
                group.setRatingAccess(resultSet.getInt("rating_access"));
                group.setSolutionAccess(resultSet.getInt("solution_access"));
                return group;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void update(Group group) {
        try (Connection conn = ConnectionCreator.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(UPDATE_QUERY);
            statement.setString(1, group.getName());
            statement.setInt(2, group.getRatingAccess());
            statement.setInt(3, group.getSolutionAccess());
            statement.setInt(4, group.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int groupId) {
        try (Connection conn = ConnectionCreator.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(DELETE_QUERY);
            statement.setInt(1, groupId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Group[] addToArray(Group u, Group[] groups) {
        Group[] tmpGroup = Arrays.copyOf(groups, groups.length + 1);
        tmpGroup[groups.length] = u;
        return tmpGroup;
    }

    public Group[] findAll() {
        try (Connection conn = ConnectionCreator.getConnection()) {
            Group[] groups = new Group[0];
            PreparedStatement statement = conn.prepareStatement(FIND_ALL_QUERY);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Group group = new Group();
                group.setId(resultSet.getInt("id"));
                group.setName(resultSet.getString("name"));
                group.setSolutionAccess(resultSet.getInt("solution_access"));
                group.setRatingAccess(resultSet.getInt("rating_access"));
                groups = addToArray(group, groups);
            }
            return groups;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
