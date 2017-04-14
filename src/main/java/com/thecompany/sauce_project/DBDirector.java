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
    static final String DB_URL = "jdbc:derby://localhost:1527/Project Database";

    // Database credentials
    static final String USER = "";
    static final String PASS = "";

    private static Connection conn = null;
    private static Statement stmt = null;

    //Table Names
    private static String sauce = "SAUCE";
    private static String user = "USERS";

    public static void main(String[] args) {
        connect();
        insertSauce("Tapitio", 10000, null);
        insertUser("jhiggz60@gmail.com", "Jon", "Higgins", "current timestamp", 0);
        select(sauce);
        select(user);
        disConnect();
    }

    private static void connect() {
        try {
            //Step 2: Register JDBC Driver
            Class.forName(JDBC_DRIVER);

            //Step 3: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void disConnect() {
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
            sql = "SELECT * from " + table;

            //  RESULT SET IS WHERE YOU PUT YOUR QUERY
            ResultSet rs = stmt.executeQuery(sql);

            if (table.equals("SAUCE")) {
                //Step 5: Extract data from result set
                while (rs.next()) {
                    String TITLE = rs.getString("TITLE");
                    int SCOVILLE_RATING = rs.getInt("SCOVILLE_RATING");
                    Blob b = rs.getBlob("IMAGE");

                    //DISPLAY VALUES
                    System.out.println("Title: " + TITLE);
                    System.out.println("Scoville Rating: " + SCOVILLE_RATING);
                    System.out.println("Blob: " + b);
                    System.out.println();
                }
            }else if(table.equals("USERS")){
                while (rs.next()) {
                    String email = rs.getString("EMAIL");
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
                    System.out.println();
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
