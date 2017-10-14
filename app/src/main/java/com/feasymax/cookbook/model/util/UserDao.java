package com.feasymax.cookbook.model.util;

/**
 * Created by kristine042 on 2017-10-09.
 */

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserDao {

    private Connection conn = null;
    private static final String DBMS = "postgresql";
    private static final String SERVER_NAME = "db.cs.usask.ca";
    private static final int PORT_NUMBER = 5432;
    private static final String DB_NAME = "cmpt370_feasymax";
    private static final String PASSWORD = "v0vAecsxvf0gzgCIlFPN";
    private static final String USERNAME = "cmpt370_feasymax";
    private volatile int userID = 0;
    private volatile String updateRes = "";
    private String email;

    /**
     * Get a connection to database
     * @return Connection object
     */
    public void connect() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("No Postgre driver.");
            e.printStackTrace();
        }
        try  {

            conn = DriverManager.getConnection("jdbc:" + DBMS + "://" + SERVER_NAME
                    + ":" + PORT_NUMBER + "/" + DB_NAME, USERNAME, PASSWORD);

        } catch (SQLException ex) {
            throw new RuntimeException("Error connecting to the database", ex);

        }
    }

    public int getUserID(final String user, final String password) {
        userID = 0;
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try  {
                    connect();
                    Statement stmt = null;
                    ResultSet rs = null;
                    try {
                        String query = "SELECT * FROM users u WHERE u.username = '"
                                + user + "' and u.passwords = " + "'" + password + "'";
                        stmt = conn.createStatement();
                        rs = stmt.executeQuery(query);
                        if (rs.next()) {
                            userID = rs.getInt("id");
                        }
                    } catch(SQLException e) {
                        System.out.println("SQL error");
                        e.printStackTrace();
                    } finally {
                        try {
                            if (conn != null)
                                conn.close();
                        }
                        catch(SQLException e) {

                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
        try {
            Thread.sleep(1800);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return userID;
    }

    public int regUser(final String user, final String password) {
        userID = 0;
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try  {
                    connect();
                    Statement stmt = null;
                    ResultSet rs = null;
                    try {
                        String query = "SELECT * FROM users u WHERE u.username = '"
                                + user + "'";
                        stmt = conn.createStatement();
                        rs = stmt.executeQuery(query);
                        // if the username is not already taken
                        if (!rs.next()) {
                            rs = stmt.executeQuery("SELECT MAX(id) AS id FROM users");
                            int autoID = 1;
                            if (rs.next()) {
                                autoID = rs.getInt("id") + 1;
                            }
                            query = "INSERT INTO users (id, username, passwords) VALUES ("
                                    + autoID + ", '" + user + "', '" + password + "')";
                            stmt = conn.createStatement();
                            stmt.execute(query);
                            userID = autoID;
                        }
                    } catch(SQLException e) {
                        System.out.println("SQL error");
                        e.printStackTrace();
                    } finally {
                        try {
                            if (conn != null)
                                conn.close();
                        }
                        catch(SQLException e) {

                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
        try {
            Thread.sleep(1800);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return userID;
    }

    public String update(final int userID, final String user, final String userEmail,
                      final String oldPassword, final String newPassword) {
        updateRes = "";
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try  {
                    connect();
                    Statement stmt = null;
                    ResultSet rs = null;

                    try {
                        String query = "SELECT * FROM users u WHERE u.id = " + userID;
                        stmt = conn.createStatement();
                        rs = stmt.executeQuery(query);
                        if (rs.next()) {
                            // if oldPassword and newPassword are being changed
                            if (!oldPassword.isEmpty() && !newPassword.isEmpty()) {
                                String password = rs.getString("passwords");
                                if (password.equals(oldPassword)) {
                                    query = "UPDATE users SET passwords = '" + newPassword + "' " +
                                            "WHERE id = " + userID;
                                    stmt = conn.createStatement();
                                    stmt.execute(query);
                                    updateRes += "PASS_";
                                } else {
                                    // old password is incorrect

                                }
                            }
                            query = "SELECT * FROM users u WHERE u.username = '"
                                    + user + "'";
                            stmt = conn.createStatement();
                            rs = stmt.executeQuery(query);
                            if (rs.next()) {
                                int foundID = rs.getInt("id");
                                if (foundID != userID && foundID != 0) {
                                    // the username is already taken

                                } else {
                                    updateRes += "USERNAME_";
                                }
                            } else {
                                query = "UPDATE users SET username = '" + user + "' " +
                                        "WHERE id = " + userID;
                                stmt = conn.createStatement();
                                stmt.execute(query);
                                updateRes += "USERNAME_";
                            }
                            query = "UPDATE users SET email_add = '" + userEmail + "' " +
                                    "WHERE id = " + userID;
                            stmt = conn.createStatement();
                            stmt.execute(query);
                        }

                    } catch(SQLException e) {
                        System.out.println("SQL error");
                        e.printStackTrace();
                    } finally {
                        try {
                            if (conn != null)
                                conn.close();
                        }
                        catch(SQLException e) {

                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
        try {
            Thread.sleep(1800);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return updateRes;
    }

    public String getEmail(final int userID) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try  {
                    connect();
                    Statement stmt = null;
                    ResultSet rs = null;
                    try {
                        String query = "SELECT * FROM users u WHERE u.id = " + userID;
                        stmt = conn.createStatement();
                        rs = stmt.executeQuery(query);
                        if (rs.next()) {
                            email = rs.getString("email_add");
                        }
                    } catch(SQLException e) {
                        System.out.println("SQL error");
                        e.printStackTrace();
                    } finally {
                        try {
                            if (conn != null)
                                conn.close();
                        }
                        catch(SQLException e) {

                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
        try {
            Thread.sleep(1800);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return email;
    }

}
