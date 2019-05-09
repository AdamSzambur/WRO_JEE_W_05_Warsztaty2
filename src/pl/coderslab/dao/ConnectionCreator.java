package pl.coderslab.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionCreator {
    private static final String DB_NAME = "warsztaty_2";
    private static final String URL = "jdbc:mysql://localhost:3306/"+DB_NAME+"?useSSL=false&characterEncoding=utf8";
    private static final String USER = "root";
    private static final String PASS = "coderslab";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER,PASS);
    }

}
