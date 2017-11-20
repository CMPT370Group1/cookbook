package com.feasymax.cookbook.model.util;

/**
 * Created by kristine042 on 2017-10-09.
 */

import android.graphics.Bitmap;
import android.util.Log;

import com.feasymax.cookbook.model.entity.Ingredient;
import com.feasymax.cookbook.model.entity.Recipe;
import com.feasymax.cookbook.util.DbBitmapUtility;
import com.feasymax.cookbook.util.Graphics;
import com.feasymax.cookbook.view.list.RecipeListModel;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.sql.*;

import static android.R.attr.description;
import static android.R.attr.id;
import static android.R.attr.password;
import static java.util.jar.Pack200.Packer.PASS;

public class UserDao {

    private Connection conn = null;
    private static final String DBMS = "postgresql";
    private static final String SERVER_NAME = "db.cs.usask.ca";
    private static final int PORT_NUMBER = 5432;
    private static final String DB_NAME = "cmpt370_feasymax";
    private static final String PASSWORD = "v0vAecsxvf0gzgCIlFPN";
    private static final String USERNAME = "cmpt370_feasymax";
    private volatile int userID = 0;
    private volatile int recipeID = -1;
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

    public int regUser(final String user, final String password, final String email, final String firstName, final String lastName) {
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
                            query = "INSERT INTO users (id, username, passwords, email_add, first_name, last_name) VALUES ("
                                    + autoID + ", '" + user + "', '" + password + "', '"
                                    + email + "', '" + firstName + "', '" + lastName + "')";
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

    public int addNewRecipe(final Recipe recipe, final int userID, final boolean owner) {
        recipeID = -1;

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try  {
                    connect();
                    PreparedStatement stmt = null;
                    ResultSet rs = null;

                    String title = recipe.getTitle();
                    String category = String.valueOf(recipe.getCategory());
                    String description = recipe.getDirections();
                    int duration = recipe.getDuration();
                    byte[] image = null;
                    byte[] image_icon = null;
                    if (recipe.getImage() != null) {
                        image = DbBitmapUtility.getBytes(recipe.getImage());
                        image_icon = DbBitmapUtility.getBytes(Graphics.resize(recipe.getImage(), 200, 200));
                    }

                    try {
                        // do not commit after every query
                        conn.setAutoCommit(false);
                        // select new id for the new recipe
                        stmt = conn.prepareStatement("SELECT MAX(id) AS id FROM recipes");
                        rs = stmt.executeQuery();
                        int autoID = 1;
                        if (rs.next()) {
                            autoID = rs.getInt("id") + 1;
                        }
                        Log.println(Log.INFO, "addNewRecipe", "new id = " + autoID);
                        recipeID = autoID;

                        // insert all recipe info into recipes table
                        String query = "INSERT INTO recipes (id, title, category_name, durtion_min, " +
                                "recipe_description, image, image_icon) VALUES (?, ?, ?, ?, ?, ?, ?)";
                        stmt = conn.prepareStatement(query);
                        stmt.setInt(1, autoID);
                        stmt.setString(2, title);
                        stmt.setString(3, category);
                        stmt.setInt(4, duration);
                        stmt.setString(5, description);
                        stmt.setBytes(6, image);
                        stmt.setBytes(7, image_icon);
                        stmt.executeUpdate();

                        // insert all recipe's ingredients
                        for (Ingredient ingr: recipe.getIngredients()) {
                            // select new id for the new ingredient
                            stmt = conn.prepareStatement("SELECT MAX(id) AS id FROM ingredients");
                            rs = stmt.executeQuery();
                            autoID = 1;
                            if (rs.next()) {
                                autoID = rs.getInt("id") + 1;
                            }
                            Log.println(Log.INFO, "addNewRecipe", "new id = " + autoID);

                            // insert an ingredient
                            query = "INSERT INTO ingredients (id, name, quantity, unit, recipe_id) " +
                                    "VALUES (?, ?, ?, ?, ?)";
                            stmt = conn.prepareStatement(query);
                            stmt.setInt(1, autoID);
                            stmt.setString(2, ingr.getName());
                            stmt.setDouble(3, ingr.getQuantity());
                            stmt.setInt(4, ingr.getUnit());
                            stmt.setInt(5, recipeID);
                            stmt.executeUpdate();
                        }
                        // insert all recipe's tags
                        for (String tag: recipe.getTags()) {
                            // select new id for the new ingredient
                            stmt = conn.prepareStatement("SELECT MAX(id) AS id FROM tag");
                            rs = stmt.executeQuery();
                            autoID = 1;
                            if (rs.next()) {
                                autoID = rs.getInt("id") + 1;
                            }
                            Log.println(Log.INFO, "addNewRecipe", "new id = " + autoID);

                            // insert an ingredient
                            query = "INSERT INTO tag (id, tag_name, recipe_id) " +
                                    "VALUES (?, ?, ?)";
                            stmt = conn.prepareStatement(query);
                            stmt.setInt(1, autoID);
                            stmt.setString(2, tag);
                            stmt.setInt(3, recipeID);
                            stmt.executeUpdate();
                        }

                        // finally, insert recipe id and user id into user_recipe table to indicate
                        // that the user has the recipe in the collection
                        stmt = conn.prepareStatement("SELECT MAX(id) AS id FROM user_recipe");
                        rs = stmt.executeQuery();
                        autoID = 1;
                        if (rs.next()) {
                            autoID = rs.getInt("id") + 1;
                        }
                        Log.println(Log.INFO, "addNewRecipe", "new id = " + autoID);

                        query = "INSERT INTO user_recipe (id, user_id, recipe_id, states) " +
                                "VALUES (?, ?, ?, ?)";
                        stmt = conn.prepareStatement(query);
                        stmt.setInt(1, autoID);
                        stmt.setInt(2, userID);
                        stmt.setInt(3, recipeID);
                        stmt.setInt(4, 0);
                        stmt.executeUpdate();

                        // close the statement and commit all changes
                        stmt.close();
                        conn.commit();

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
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return recipeID;
    }

    public List<RecipeListModel> searchUserRecipes(int userId, List<String> input) {
        ResultSet rs1,rs2,rs3,rs4;
        Statement sql;
        RecipeListModel rlm = new RecipeListModel();
        LinkedList<RecipeListModel> lk = new LinkedList<>();
        try {
            sql = conn.createStatement();
            for (int i = 0; i < input.size(); i++) {
                rs1 = sql.executeQuery("SELECT id FROM users u WHERE u.id = "+userId);
                rs2 = sql.executeQuery("SELECT title FROM recipes r WHERE r.title = " +input.get(i));
                rs3 = sql.executeQuery("SELECT image FROM recipes r WHERE r.title = " +input.get(i));
                rs4 = sql.executeQuery("SELECT duration FROM recipes r WHERE r.title = "+input.get(i));
                rlm.setRecipeTitle(rs2.toString());
                rlm.setRecipeId(rs1.getInt(rs1.toString()));
                //rlm.setRecipeImage(rs3.);
                lk.add(rlm);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return lk;







        /**ry{
            while (input.get(){

            }
            p = conn.prepareStatement();
        }


        /**
        private String doExecutionWithReturn(String input){
            try{
                p = conn.prepareStatement(input);
                temp = p.executeQuery();
            }
            catch (SQLException e){
                e.printStackTrace();
            }
            return printResultSet();
        }
         */


    }

    public List<RecipeListModel> searchDiscoverRecipes(List<String> input) {

        return null;
    }

}
