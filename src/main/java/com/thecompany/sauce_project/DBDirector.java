/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thecompany.sauce_project;

import java.sql.*;

/**
 *
 * @author craig
 */
public class DBDirector {

    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "org.apache.derby.jdbc.ClientDriver";
    static final String DB_URL = "jdbc:derby://localhost:1527/Sauce";

    // Database credentials
    static final String USER = "sample";
    static final String PASS = "sample";

    private static Connection conn = null;
    private static Statement stmt = null;

    //Table Names
    private static String sauce = "SAUCE";
    private static String user = "USER";

    public static void main(String[] args) {
        connect();
        insertSauce("The Best Sauce", 10000, null);
        insertUser("gardneca195@gmail.com", "Craig", "Gardner", "current timestamp", 0);
        select(sauce);
        select(user);
        disConnect();
    }

    public static void connect() {
        try {
            //Step 2: Register JDBC Driver
            Class.forName(JDBC_DRIVER);

            //Step 3: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void disConnect() {
        try {
            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException se2) {
        }// nothing to do
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
        System.out.println("GOODBYE!");
    }

    private static void select(String table) {
        try {
            System.out.println("Creating statement");
            stmt = conn.createStatement();

            // QUERY GOES IN SIMPLE STRING
            String sql;
            sql = "SELECT * from " + table + "\"";

            //  RESULT SET IS WHERE YOU PUT YOUR QUERY
            ResultSet rs = stmt.executeQuery(sql);

            if (table.equals("user")) {
                //Step 5: Extract data from result set
                while (rs.next()) {
                    String TITLE = rs.getString("TITLE");
                    int SCOVILLE_RATING = rs.getInt("SCOVILLE_RATING");
                    Blob b = rs.getBlob("IMAGE");

                    //DISPLAY VALUES
                    System.out.println("Title: " + TITLE);
                    System.out.println("Scoville Rating: " + SCOVILLE_RATING);
                    System.out.println("Blob: " + b);
                }
            }else if(table.equals("sauce")){
                while (rs.next()) {
                    String email = rs.getString("Email");
                    String firstName = rs.getString("First_name");
                    String lastName = rs.getString("last_Name");
                    String userSince = rs.getString("User_Since");
                    int ratingCount = rs.getInt("rating_Count");

                    //DISPLAY VALUES
                    System.out.println("Email: " + email);
                    System.out.println("First Name: " + firstName);
                    System.out.println("Last Name: " + lastName);
                    System.out.println("User Since: " +userSince);
                    System.out.println("Rating Count: " + ratingCount);
                }
            }

        } catch (SQLException se) {
            se.printStackTrace();
        } finally {

        }
    }

    private static void insertUser(String email, String firstName, String lastName, String timeStamp, int rateCount) {
        try {
            stmt = conn.createStatement();
            stmt.execute("insert into " + user + " values ('"
                    + email + "','" + firstName + "','" + lastName + "'," + timeStamp + "," + rateCount + ")");
            stmt.close();
        } catch (SQLException sqlExcept) {
            sqlExcept.printStackTrace();
        }
    }

    private static void insertSauce(String id, int scoville, Blob picture) {
        try {
            stmt = conn.createStatement();
            stmt.execute("insert into " + sauce + " values ('"
                    + id + "'," + scoville + "," + picture + ")");
            stmt.close();
        } catch (SQLException sqlExcept) {
            sqlExcept.printStackTrace();
        }
    }

}
