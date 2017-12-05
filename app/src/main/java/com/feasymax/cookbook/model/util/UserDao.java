package com.feasymax.cookbook.model.util;

/**
 * Created by kristine042 on 2017-10-09.
 * Utility class to manage database access and all functionality related to it.
 */

import android.graphics.Bitmap;
import android.util.Log;
import com.feasymax.cookbook.model.entity.Ingredient;
import com.feasymax.cookbook.model.entity.Recipe;
import com.feasymax.cookbook.model.entity.UserAccount;
import com.feasymax.cookbook.model.entity.WebpageInfo;
import com.feasymax.cookbook.util.DbBitmapUtility;
import com.feasymax.cookbook.util.Graphics;
import com.feasymax.cookbook.model.entity.RecipeShortInfo;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;


public class UserDao {

    private Connection conn = null;
    private static final String DBMS = "postgresql";
    private static final String SERVER_NAME = "db.cs.usask.ca";
    private static final int PORT_NUMBER = 5432;
    private static final String DB_NAME = "cmpt370_feasymax";
    private static final String PASSWORD = "v0vAecsxvf0gzgCIlFPN";
    private static final String USERNAME = "cmpt370_feasymax";
    private static final int ADMIN_ID = 1;
    private volatile int userID = 0;
    private volatile int recipeID = -1;
    private volatile int linkID = -1;
    private volatile String updateRes = "";
    private String email;
    private List<RecipeShortInfo> list = null;
    private List<WebpageInfo> links = null;

    /**
     * Get a connection to database
     * @return Connection object
     */
    private void connect() throws SQLException{
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("No Postgre driver.");
            e.printStackTrace();
        }
        conn = DriverManager.getConnection("jdbc:" + DBMS + "://" + SERVER_NAME
                + ":" + PORT_NUMBER + "/" + DB_NAME, USERNAME, PASSWORD);
    }

    /**
     * Sign in user
     * @param user
     * @param password
     * @return
     */
    public int signInUser(final UserAccount user, final String password) throws SQLException{
        userID = 0;
        connect();
        Statement stmt = null;
        ResultSet rs = null;
        try {
            String query = "SELECT id, email_add FROM users u WHERE u.username = '" +
                    user.getUsername() + "' AND passwords = '" + password + "'";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);
            // if the username is not already taken
            if (rs.next()) {
                userID = rs.getInt("id");
                user.setUserID(userID);
                user.setEmail(rs.getString("email_add"));
            }
        } finally {
            if (conn != null)
                conn.close();
        }
        return userID;
    }

    /**
     * Register new user
     * @param user
     * @param password
     * @param email
     * @param firstName
     * @param lastName
     * @return
     */
    public int registerUser(final String user, final String password, final String email, final String firstName, final String lastName) throws SQLException {
        userID = 0;
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
        } finally {
            if (conn != null)
                conn.close();
        }
        return userID;
    }

    /**
     * Update user's account info
     * @param userID
     * @param user
     * @param userEmail
     * @param oldPassword
     * @param newPassword
     * @return
     */
    public String updateUserAccount(final int userID, final String user, final String userEmail,
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
                                Log.d("oldPassword", oldPassword);
                                Log.d("newPassword", newPassword);
                                String password = rs.getString("passwords");
                                Log.d("password", password);
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

    /**
     * Get username and email address from user id
     * @param user
     * @return
     */
    public void getUserInfo(final UserAccount user) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try  {
                    connect();
                    Statement stmt = null;
                    ResultSet rs = null;
                    try {
                        String query = "SELECT username, email_add FROM users u WHERE u.id = " +
                                user.getUserID();
                        stmt = conn.createStatement();
                        rs = stmt.executeQuery(query);
                        if (rs.next()) {
                            user.setUsername(rs.getString("username"));
                            user.setEmail(rs.getString("email_add"));
                        }
                    } catch(SQLException e) {
                        System.out.println("SQL error");
                        e.printStackTrace();
                    } finally {
                        try {
                            if (conn != null)
                                conn.close();
                        }
                        catch(SQLException e) {}
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
    }


    /**
     * Get recipes in a category
     * @param isUserCollection
     * @param userID
     * @param category
     * @return result code
     */
    public List<RecipeShortInfo> updateRecipeCollection(final boolean isUserCollection, final int userID, final int category) {
        list = null;

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try  {
                    connect();
                    PreparedStatement stmt = null;
                    ResultSet rs = null;

                    // do not commit after every query
                    conn.setAutoCommit(false);

                    try {
                        int id = -1;
                        String title = null;
                        int duration = 0;
                        byte[] image_icon = null;
                        Bitmap image = null;

                        RecipeShortInfo recipeShortInfo = null;
                        list = new LinkedList<>();

                        // insert all recipe info into recipes table
                        String query = "SELECT id, title, durtion_min, image_icon FROM recipes r WHERE ";
                        if (isUserCollection) {
                            query += " r.id IN (SELECT recipe_id FROM user_recipe " +
                                    "WHERE user_id = " + userID + ") AND ";
                        }
                        query += "r.category_name = '" + String.valueOf(category) + "'";

                        stmt = conn.prepareStatement(query);
                        rs = stmt.executeQuery();
                        if (!rs.isBeforeFirst() ) {
                            throw new SQLException("No data found");
                        }
                        while (rs.next()) {
                            id = rs.getInt("id");
                            title = rs.getString("title");
                            duration = rs.getInt("durtion_min");
                            if (rs.getObject("image_icon") != null && !rs.wasNull()) {
                                image_icon = rs.getBytes("image_icon");
                                image = DbBitmapUtility.getImage(image_icon);
                            }

                            recipeShortInfo = new RecipeShortInfo(id, title, image, duration);
                            Log.println(Log.INFO, "updateRecipeCollection", recipeShortInfo.toString());
                            list.add(recipeShortInfo);

                            image_icon = null;
                        }

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

        return list;
    }


    /**
     * Get full recipe from short recipe info
     * @param recipe incomplete recipe to be filled
     * @return result code
     */
    public int getFullRecipe(final Recipe recipe, final int userID) {
        recipeID = recipe.getId();

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try  {
                    connect();
                    PreparedStatement stmt = null;
                    ResultSet rs = null;

                    // do not commit after every query
                    conn.setAutoCommit(false);

                    try {
                        List<Ingredient> ingredients = null;
                        List<String> tags = null;
                        String directions = null;
                        byte[] image_ba = null;
                        Bitmap image = null;

                        // insert all recipe info into recipes table
                        String query = "SELECT recipe_description, image FROM recipes r " +
                                "WHERE r.id = " + recipeID;

                        stmt = conn.prepareStatement(query);
                        rs = stmt.executeQuery();
                        if (rs.next()) {
                            directions = rs.getString("recipe_description");
                            if (rs.getObject("image") != null && !rs.wasNull()) {
                                image_ba = rs.getBytes("image");
                                image = DbBitmapUtility.getImage(image_ba);
                            }

                            recipe.setDirections(directions);
                            recipe.setImage(image);
                            Log.println(Log.INFO, "getFullRecipe", recipe.toString());
                        }

                        // get ingredients
                        query = "SELECT name, quantity, unit FROM ingredients i " +
                                "WHERE i.recipe_id = " + recipeID;

                        stmt = conn.prepareStatement(query);
                        rs = stmt.executeQuery();
                        if (rs.isBeforeFirst() ) {
                            ingredients = new LinkedList<>();
                            String name = null;
                            double quantity = 0.0;
                            int unit = 0;
                            while (rs.next()) {
                                name = rs.getString("name");
                                quantity = Double.valueOf(rs.getString("quantity"));
                                unit = Integer.valueOf(rs.getString("unit"));

                                ingredients.add(new Ingredient(name, quantity, unit));
                                Log.println(Log.INFO, "getFullRecipe", ingredients.toString());
                            }
                        }
                        recipe.setIngredients(ingredients);

                        // get tags
                        query = "SELECT tag_name FROM tag t " +
                                "WHERE t.recipe_id = " + recipeID;

                        stmt = conn.prepareStatement(query);
                        rs = stmt.executeQuery();
                        if (rs.isBeforeFirst() ) {
                            tags = new LinkedList<>();
                            String tag_name = null;
                            while (rs.next()) {
                                tag_name = rs.getString("tag_name");

                                tags.add(tag_name);
                                Log.println(Log.INFO, "getFullRecipe", tags.toString());
                            }
                        }
                        recipe.setTags(tags);

                        if (userID != -1) {
                            // get isOwner flag
                            query = "SELECT states FROM user_recipe " +
                                    "WHERE recipe_id = " + recipeID + " AND user_id = " + userID;

                            stmt = conn.prepareStatement(query);
                            rs = stmt.executeQuery();
                            boolean isOwner = false;
                            if (rs.next()) {
                                int state = rs.getInt("states");
                                if (state == 1) {
                                    isOwner = true;
                                }
                            }
                            recipe.setOwner(isOwner);
                        }

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

        Log.println(Log.INFO, "getFullRecipe", recipe.toString());
        return 0;
    }

    /**
     * Delete recipe from user's collection: if it's was not originally the user's recipe, just
     * remove the reference in user_recipes table, otherwise make the recipe belong to admin so that
     * it stays in discover collection
     * @param userID
     * @param recipeID
     * @return result code
     */
    public List<RecipeShortInfo> deleteRecipe(final int userID, final int recipeID) {

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try  {
                    connect();
                    PreparedStatement stmt = null;
                    ResultSet rs = null;

                    // do not commit after every query
                    conn.setAutoCommit(false);

                    try {
                        Log.println(Log.INFO, "deleting recipe", "user " + userID + " recipe " + recipeID);

                        // insert all recipe info into recipes table
                        String query = "SELECT id, states FROM user_recipe r WHERE r.user_id = " +
                                userID + " AND r.recipe_id = " + recipeID + "";
                        stmt = conn.prepareStatement(query);
                        rs = stmt.executeQuery();
                        if (!rs.next() ) {
                            throw new SQLException("No data found");
                        }
                        int id = rs.getInt("id");
                        int owner = rs.getInt("states");

                        Log.println(Log.INFO, "deleting recipe", "id " + id + " owner " + owner);

                        if (owner == 1) {
                            if (userID != ADMIN_ID) {
                                query = "UPDATE user_recipe SET user_id = 1 WHERE id = " + id;
                                stmt = conn.prepareStatement(query);
                                if (stmt.executeUpdate() <= 0) {
                                    throw new SQLException("No data updated");
                                }
                            }
                            else {
                                query = "DELETE FROM user_recipe WHERE user_id = " + userID + " AND " +
                                        "recipe_id = " + recipeID;
                                stmt = conn.prepareStatement(query);
                                if (stmt.executeUpdate() <= 0) {
                                    throw new SQLException("No data deleted");
                                }

                                query = "DELETE FROM ingredients WHERE recipe_id = " + recipeID;
                                stmt = conn.prepareStatement(query);
                                stmt.executeUpdate();

                                query = "DELETE FROM tag WHERE recipe_id = " + recipeID;
                                stmt = conn.prepareStatement(query);
                                stmt.executeUpdate();

                                query = "DELETE FROM recipes WHERE id = " + recipeID;
                                stmt = conn.prepareStatement(query);
                                if (stmt.executeUpdate() <= 0) {
                                    throw new SQLException("No data deleted");
                                }
                            }

                        }
                        if (owner == 0) {

                            query = "DELETE FROM user_recipe WHERE user_id = " + userID + " AND " +
                                    "recipe_id = " + recipeID;
                            stmt = conn.prepareStatement(query);
                            if (stmt.executeUpdate() <= 0) {
                                throw new SQLException("No data deleted");
                            }
                        }

                        removeDuplicates("user_recipe", new String[]{"user_id", "recipe_id"});

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

        return list;
    }



    /**
     * Add new recipe to the database, add the recipeID to the user_recipe table with state 1 and
     * return recipeID. If the user does not own the recipe, simply add the recipeID to the
     * user_recipe table with state 0
     * @param isNewRecipe
     * @param recipe
     * @param userID
     * @param isOwner does the user own the recipe
     * @param prevRecipeID
     * @return recipeID
     */
    public int addNewRecipe(final boolean isNewRecipe, final Recipe recipe, final int userID,
                            final boolean isOwner, final int prevRecipeID) {
        recipeID = -1;

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try  {
                    connect();
                    PreparedStatement stmt = null;
                    ResultSet rs = null;

                    // do not commit after every query
                    conn.setAutoCommit(false);

                    try {
                        if (isOwner) {

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

                            String query;
                            if (isNewRecipe) {
                                query = "INSERT INTO recipes (title, category_name, durtion_min, " +
                                        "recipe_description, image, image_icon) VALUES (?, ?, ?, ?, ?, ?) RETURNING id";
                            }
                            else {
                                query = "UPDATE recipes SET title = ?, category_name = ?, " +
                                        "durtion_min = ?, recipe_description = ?, image = ?, " +
                                        "image_icon = ? WHERE id = " + recipe.getId() + " RETURNING id";

                            }
                            // insert all recipe info into recipes table
                            stmt = conn.prepareStatement(query);
                            //stmt.setInt(1, autoID);
                            stmt.setString(1, title);
                            stmt.setString(2, category);
                            stmt.setInt(3, duration);
                            stmt.setString(4, description);
                            stmt.setBytes(5, image);
                            stmt.setBytes(6, image_icon);
                            rs = stmt.executeQuery();
                            if (!rs.next()) {
                                throw new SQLException("No recipe info was inserted");
                            }
                            recipeID = rs.getInt("id");
                            Log.println(Log.INFO, "recipeID", recipeID+"");

                            Log.println(Log.INFO, "recipe ingredients", recipe.getIngredients().toString());

                            // if editing the recipe, remove all ingredients to add them all again
                            if (!isNewRecipe) {
                                query = "DELETE FROM ingredients WHERE recipe_id = " + recipeID;
                                stmt = conn.prepareStatement(query);
                                stmt.executeUpdate();
                            }

                            // insert all recipe's ingredients
                            for (Ingredient ingr: recipe.getIngredients()) {

                                // insert an ingredient
                                query = "INSERT INTO ingredients (name, quantity, unit, " +
                                        "recipe_id) VALUES (?, ?, ?, ?)";
                                stmt = conn.prepareStatement(query);
                                //stmt.setInt(1, autoID);
                                stmt.setString(1, ingr.getName());
                                stmt.setDouble(2, ingr.getQuantity());
                                stmt.setInt(3, ingr.getUnit());
                                stmt.setInt(4, recipeID);
                                stmt.executeUpdate();
                                if (stmt.executeUpdate() <= 0) {
                                    throw new SQLException("No ingredient info was inserted");
                                }
                            }
                            removeDuplicates("ingredients", new String[]{"name", "quantity", "unit", "recipe_id"});

                            Log.println(Log.INFO, "recipe tags", recipe.getTags().toString());
                            // if editing the recipe, remove all tags to add them all again
                            if (!isNewRecipe) {
                                query = "DELETE FROM tag WHERE recipe_id = " + recipeID;
                                stmt = conn.prepareStatement(query);
                                stmt.executeUpdate();
                            }

                            // insert all recipe's tags
                            for (String tag: recipe.getTags()) {

                                // insert a tag
                                query = "INSERT INTO tag (tag_name, recipe_id) " +
                                        "VALUES (?, ?)";
                                stmt = conn.prepareStatement(query);
                                stmt.setString(1, tag);
                                stmt.setInt(2, recipeID);
                                stmt.executeUpdate();
                                if (stmt.executeUpdate() <= 0) {
                                    throw new SQLException("No tag info was inserted");
                                }
                            }
                            removeDuplicates("tag", new String[]{"tag_name", "recipe_id"});

                        }
                        else {
                            recipeID = recipe.getId();
                        }

                        if (isNewRecipe) {
                            if (prevRecipeID != -1) {
                                String query = "DELETE FROM user_recipe WHERE user_id = " +
                                        userID + " AND recipe_id = " + prevRecipeID;
                                stmt = conn.prepareStatement(query);
                                stmt.executeUpdate();
                            }

                            String query = "INSERT INTO user_recipe (user_id, recipe_id, states) " +
                                    "VALUES (?, ?, ?)";
                            stmt = conn.prepareStatement(query);
                            //stmt.setInt(1, autoID);
                            stmt.setInt(1, userID);
                            stmt.setInt(2, recipeID);
                            stmt.setInt(3, ((isOwner) ? 1 : 0));
                            stmt.executeUpdate();
                            if (stmt.executeUpdate() <= 0) {
                                throw new SQLException("No recipe id was inserted");
                            }
                            removeDuplicates("user_recipe", new String[]{"user_id", "recipe_id"});
                        }

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

    /**
     * Searching recipes' titles and directions by list of keywords
     * @param isUserCollection is it user or discover collection
     * @param userID
     * @param tokens list of keywords
     * @return
     */
    public List<RecipeShortInfo> searchRecipes(final boolean isUserCollection, final int userID,
                                               final List<String> tokens) {
        list = null;

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                //don't need to create a new list at the beginning since we will be
                //adding searching for everything then adding up.
                RecipeShortInfo recipeShortInfo = null;

                try {
                    connect();
                    PreparedStatement stmt = null;
                    ResultSet rs = null;

                    try {

                        list = new LinkedList<>();

                        //no user id since we can't discover the ones saved by users
                        int id = -1;
                        String title = null;
                        int duration = 0;
                        byte[] image_icon = null;
                        Bitmap image = null;

                        //discover recipes has  to display title,duration and image
                        String searchQuery = "~* '("+tokens.get(0);

                        for (int i = 1; i < tokens.size(); i++)
                        {
                            searchQuery += "|"+tokens.get(i);
                        }
                        searchQuery += ")'";

                        String query = "SELECT id, title, durtion_min, image_icon FROM recipes r WHERE ";

                        if (isUserCollection) {
                            query += " r.id IN (SELECT recipe_id FROM user_recipe " +
                                    "WHERE user_id = " + userID + ") AND ";
                        }

                        query+="(r.title " + searchQuery + " OR r.recipe_description " + searchQuery + ")";
                        Log.println(Log.INFO, "query", query);

                        stmt = conn.prepareStatement(query);
                        rs = stmt.executeQuery();
                        if (!rs.isBeforeFirst()) {
                            throw new SQLException("No data found");
                        }
                        while (rs.next()) {

                            id = rs.getInt("id");
                            title = rs.getString("title");
                            duration = rs.getInt("durtion_min");
                            if (rs.getObject("image_icon") != null && !rs.wasNull()) {
                                image_icon = rs.getBytes("image_icon");
                                image = DbBitmapUtility.getImage(image_icon);
                            }

                            recipeShortInfo = new RecipeShortInfo(id, title, image, duration);
                            Log.println(Log.INFO, "discover Recipes", recipeShortInfo.toString());
                            list.add(recipeShortInfo);
                            image_icon = null;
                        }
                        stmt.close();

                    } catch (SQLException e) {
                        System.out.println("SQL ERROR for discover recipes");
                        e.printStackTrace();
                    } finally {
                        try {
                            if (conn != null)
                                conn.close();
                        } catch (SQLException e) {}
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        //start the thread
        thread.start();

        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        return list;

    }

    /**
     * Search recipe collection by all recipe attributes
     * @param isUserCollection
     * @param userID
     * @param titles
     * @param directions
     * @param ingredients
     * @param tags
     * @param category
     * @param isIncludingAllAttributes
     * @return
     */
    public List<RecipeShortInfo> advancedSearchRecipes(final boolean isUserCollection,
                                                       final int userID,
                                                       final List<String> titles,
                                                       final List<String> directions,
                                                       final List<String> ingredients,
                                                       final List<String> tags,
                                                       final int category,
                                                       final boolean isIncludingAllAttributes) {
        list = null;

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                //don't need to create a new list at the beginning since we will be
                //adding searching for everything then adding up.
                RecipeShortInfo recipeShortInfo = null;

                try {
                    connect();
                    PreparedStatement stmt = null;
                    ResultSet rs = null;

                    try {
                        //global var list
                        list = new LinkedList<>();


                        //declare
                        String title;
                        int id;
                        int duration;
                        byte[] image_icon;
                        Bitmap image = null;

                        String logicalSeparator;
                        if (isIncludingAllAttributes) {
                            logicalSeparator = " AND";
                        }
                        else {
                            logicalSeparator = " OR";
                        }


                        String query = "SELECT id, title, durtion_min, image_icon FROM recipes r WHERE ";

                        if (isUserCollection) {
                            query += " r.id IN (SELECT recipe_id FROM user_recipe " +
                                    "WHERE user_id = " + userID + ") AND";
                        }

                        if (titles.size() > 0) {
                            String titleQuery = "~* '("+titles.get(0);
                            for (int i = 1; i < titles.size(); i++)
                            {
                                titleQuery += "|"+titles.get(i);
                            }
                            titleQuery += ")'";
                            query+=" r.title " + titleQuery + logicalSeparator;
                        }

                        if (directions.size() > 0) {
                            String directionsQuery = "~* '("+directions.get(0);
                            for (int i = 1; i < directions.size(); i++)
                            {
                                directionsQuery += "|"+directions.get(i);
                            }
                            directionsQuery += ")'";
                            query+=" r.recipe_description " + directionsQuery + logicalSeparator;
                        }

                        if (ingredients.size() > 0) {
                            String ingredientsQuery = "~* '("+ingredients.get(0);
                            for (int i = 1; i < ingredients.size(); i++)
                            {
                                ingredientsQuery += "|"+ingredients.get(i);
                            }
                            ingredientsQuery += ")'";
                            query+=" r.id IN (SELECT recipe_id FROM ingredients i WHERE i.name " +
                                    ingredientsQuery + " AND i.recipe_id = r.id)" + logicalSeparator;
                        }

                        if (tags.size() > 0) {
                            String tagsQuery = "~* '("+tags.get(0);
                            for (int i = 1; i < tags.size(); i++)
                            {
                                tagsQuery += "|"+tags.get(i);
                            }
                            tagsQuery += ")'";
                            query+=" r.id IN (SELECT recipe_id FROM tag t WHERE t.tag_name " +
                                    tagsQuery + " AND t.recipe_id = r.id)" + logicalSeparator;
                        }

                        query = query.substring(0, query.lastIndexOf(' '));

                        if (category >= 0) {
                            // if at least one of titles, directions, ingredients or tags list is
                            // not empty
                            if (!query.substring(query.lastIndexOf(" ")+1).equals("WHERE")) {
                                query += " AND";
                            }

                            query+=" r.category_name = '" + String.valueOf(category) + "'";
                        }

                        // if all of the attributes are empty, remove "WHERE" from the query
                        if (query.substring(query.lastIndexOf(" ")+1).equals("WHERE")) {
                            query = query.substring(0, query.lastIndexOf(' '));
                        }

                        Log.println(Log.INFO, "query", query);

                        stmt = conn.prepareStatement(query);
                        rs = stmt.executeQuery();
                        if (!rs.isBeforeFirst()) {
                            throw new SQLException("No data found");
                        }

                        while (rs.next()) {

                            id = rs.getInt("id");
                            title = rs.getString("title");
                            duration= rs.getInt("durtion_min");
                            if (rs.getObject("image_icon") != null && !rs.wasNull()) {
                                image_icon = rs.getBytes("image_icon");
                                image = DbBitmapUtility.getImage(image_icon);
                            }

                            recipeShortInfo = new RecipeShortInfo(id, title, image, duration);
                            Log.println(Log.INFO, "discover Recipes", recipeShortInfo.toString());
                            list.add(recipeShortInfo);
                        }
                        stmt.close();

                    }
                    catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        //start the thread
        thread.start();

        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return list;
    }


    /**
     * Remove duplicate rows in a table with filelds
     * @param table
     * @param fields
     */
    private void removeDuplicates(final String table, final String[] fields) {
        try {
            String query = "DELETE FROM " + table + " a USING " + table + " b WHERE a.id < b.id";
            for (String field: fields) {
                query += " AND a." + field + " = b." + field;
            }
            Log.println(Log.INFO, "removeDuplicates", query);
            PreparedStatement stmt = conn.prepareStatement(query);
            if (stmt.executeUpdate() <= 0) {
                Log.println(Log.INFO, "removeDuplicates", "No duplicate data deleted from " + table);
            }
        }
        catch (SQLException e){
            System.out.println("SQL error");
            e.printStackTrace();
        }
    }

    /**
     * Get all links saved in user's collection
     * @param userID
     * @return linked list of links
     */
    public List<WebpageInfo> getLinks(final int userID) {

        links = null;

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    connect();
                    PreparedStatement stmt = null;
                    ResultSet rs = null;

                    try {

                        links = new LinkedList<>();
                        WebpageInfo webpageInfo = null;

                        int id = -1;
                        String title = null;
                        String url = null;
                        String website = null;
                        byte[] image_icon = null;
                        Bitmap image = null;

                        String query = "SELECT id, title, web_add, website, image FROM links WHERE " +
                                "user_id = " + userID;
                        Log.println(Log.INFO, "query", query);

                        stmt = conn.prepareStatement(query);
                        rs = stmt.executeQuery();
                        if (!rs.isBeforeFirst()) {
                            throw new SQLException("No data found");
                        }
                        while (rs.next()) {
                            id = rs.getInt("id");
                            title = rs.getString("title");
                            url = rs.getString("web_add");
                            website = rs.getString("website");
                            if (rs.getObject("image") != null && !rs.wasNull()) {
                                image_icon = rs.getBytes("image");
                                image = DbBitmapUtility.getImage(image_icon);
                            }

                            webpageInfo = new WebpageInfo(id, title,url,website, "", image);
                            Log.println(Log.INFO, "getLinks", webpageInfo.toString());
                            links.add(webpageInfo);
                        }
                        stmt.close();

                    } catch (SQLException e) {
                        System.out.println("SQL ERROR for discover recipes");
                        e.printStackTrace();
                    } finally {
                        try {
                            if (conn != null)
                                conn.close();
                        } catch (SQLException e) {}
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        //start the thread
        thread.start();

        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return links;
    }

    /**
     * Add a link to user's collection
     * @param userID
     * @param webpageInfo
     * @return
     */
    public int addLink(final int userID, final WebpageInfo webpageInfo) {

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    connect();
                    PreparedStatement stmt = null;
                    ResultSet rs = null;

                    try {

                        String title = webpageInfo.getTitle();
                        String url = webpageInfo.getUrl();
                        String website = webpageInfo.getWebsiteName();
                        Bitmap image = webpageInfo.getImage();
                        byte[] image_icon = null;

                        if (image != null) {
                            image_icon = DbBitmapUtility.getBytes(image);
                        }

                        String query;
                        query = "INSERT INTO links (title, web_add, website, image, user_id) VALUES " +
                                "(?, ?, ?, ?, ?)  RETURNING id";
                        // insert all recipe info into recipes table
                        stmt = conn.prepareStatement(query);
                        stmt.setString(1, title);
                        stmt.setString(2, url);
                        stmt.setString(3, website);
                        stmt.setBytes(4, image_icon);
                        stmt.setInt(5, userID);

                        rs = stmt.executeQuery();
                        if (rs.next()) {
                            linkID = rs.getInt("id");
                        }

                        stmt.close();

                    } catch (SQLException e) {
                        System.out.println("SQL ERROR for discover recipes");
                        e.printStackTrace();
                    } finally {
                        try {
                            if (conn != null)
                                conn.close();
                        } catch (SQLException e) {}
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        //start the thread
        thread.start();

        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return this.linkID;
    }

    /**
     * Delete specified link from user's collection
     * @param linkID
     */
    public void deleteLink(final int linkID) {

        links = null;

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    connect();
                    PreparedStatement stmt = null;
                    ResultSet rs = null;

                    try {

                        String query = "DELETE FROM links WHERE id = " + linkID;
                        Log.println(Log.INFO, "query", query);

                        stmt = conn.prepareStatement(query);
                        if (stmt.executeUpdate() <= 0) {
                            throw new SQLException("No link deleted");
                        }
                        stmt.close();

                    } catch (SQLException e) {
                        System.out.println("SQL ERROR for discover recipes");
                        e.printStackTrace();
                    } finally {
                        try {
                            if (conn != null)
                                conn.close();
                        } catch (SQLException e) {}
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        //start the thread
        thread.start();

        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
