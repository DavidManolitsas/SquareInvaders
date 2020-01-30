package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author David Manolitsas
 * @project SquareInvaders
 * @date 2020-01-30
 */
public class HighScoreDatabase {
    private static final String DB_NAME = "HighScoreDB";


    public void runConnection() {
        try (Connection con = getConnection(DB_NAME)) {

            System.out.println("Connection to database " + DB_NAME + " created successfully");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    public static Connection getConnection(String dbName) throws SQLException, ClassNotFoundException {
        Class.forName("org.hsqldb.jdbc.JDBCDriver");
        Connection con = DriverManager.getConnection("jdbc:hsqldb:file:database/" + dbName, "SA", "");
        return con;
    }






}
