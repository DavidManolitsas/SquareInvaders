package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author David Manolitsas
 * @project SquareInvaders
 * @date 2020-01-30
 */
public class HighScoreDatabase {
    private static final String DB_NAME = "HighScoreDB";
    private static final String TABLE_NAME = "HighScore";

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

    public void createHighScoreTable() {
        try (Connection con = getConnection(DB_NAME);
             Statement stmt = con.createStatement();
        ) {
            int result = stmt.executeUpdate( "CREATE TABLE " + TABLE_NAME + " ("
                                                     + "score int)" );

            if(result == 0) {
                System.out.println("Table " + TABLE_NAME +  " has been created successfully");
            } else {
                System.out.println("Table " + TABLE_NAME + " is not created");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    public void newHighScore(int score) {
        //UPDATE
        try (Connection con = getConnection(DB_NAME);
             Statement stmt = con.createStatement();
        ) {
            int result = 0;

            String query = "UPDATE " + TABLE_NAME + " SET score = '" + score + "'";

            result += stmt.executeUpdate(query);

            System.out.println("Update table " + TABLE_NAME + " executed successfully");
            System.out.println(result + " row(s) affected");

            if (result > 0){
                return;
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }


        //NEW
        try (Connection con = getConnection(DB_NAME);
             Statement stmt = con.createStatement();
        ) {
            String query = "INSERT INTO " + TABLE_NAME + " VALUES ('" + score + "')";
            int result = stmt.executeUpdate(query);

            con.commit();

            System.out.println("Insert into table " + TABLE_NAME + " executed successfully");
            System.out.println(result + " row(s) affected");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }


    public int getHighScore() {
        try (Connection con = getConnection(DB_NAME);
             Statement stmt = con.createStatement();) {

            String query = "SELECT score FROM " + TABLE_NAME;

            try (ResultSet resultSet = stmt.executeQuery(query)) {
                while(resultSet.next()){
                    String scoreS = resultSet.getString("score");
                    int score = Integer.parseInt(scoreS);

                    return score;
                }

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }


    /*
     * The following method is used to reset the High Score if database needs changes
     * Current High Score: 1250 (2/02/2020)
     */
    public void insertHighScore(int highScore){
        try (Connection con = getConnection(DB_NAME);
             Statement stmt = con.createStatement();
        ) {

            String query = "INSERT INTO " + TABLE_NAME + " VALUES ('"+ highScore +"')";

            int result = stmt.executeUpdate(query);
            con.commit();

            System.out.println("Insert into table " + TABLE_NAME + " executed successfully");
            System.out.println(result + " row(s) affected");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void dropTable(){
        try (Connection con = getConnection(DB_NAME);
             Statement stmt = con.createStatement();
        ) {
            stmt.executeUpdate("DROP TABLE "+ TABLE_NAME);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }






}
