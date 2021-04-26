package com.revature.dao;

import com.revature.models.Role;
import com.revature.models.User;
import com.revature.utility.JDBConnection;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdminDaoImplementation implements AdminDao {

    // Logger for tracking events during runtime. Also used to debug a problems that occur
    private static final Logger log = Logger.getLogger(AdminDaoImplementation.class);


    /**
     * Admin interface function will allow admin users to
     * delete all users in the bank database. Method returns
     * a false value if the query was unsuccessful
     * @return boolean
     */
    @Override
    public boolean deleteAllUser() {

        try {
            Connection connected = JDBConnection.getConnection();
            // This sql statement will delete all rows in users table
            String sqlStatement = "TRUNCATE TABLE users CASCADE";

            Statement statement = connected.createStatement();
            statement.executeUpdate(sqlStatement);

            log.info("WORLD BANK: ALL USERS FROM THE DATA BASE HAVE BEEN DELETED.");
            return true;
        } catch (SQLException e) {

            e.printStackTrace();
            log.warn("Error (SQL Admin DAO): Failed to truncate table and delete all users");
            return false;
        }
    }


    /**
     * Admin will delete a individual user from the database table Users.
     * Returns deleted User if the query was successful. The method will delete using
     * a User's username
     * @param userToDrop
     * @return boolean
     */
    @Override
    public User deleteSingleUser(User userToDrop) {
        // User object will hold and return the user object that was deleted in database
        User deletedUser = new User();

        try {

            Connection connected = JDBConnection.getConnection();
            String sqlStatement = "DELETE FROM users WHERE id = ? RETURNING *";

            PreparedStatement statement = connected.prepareStatement(sqlStatement);
            // Will use the username from the given User object
            statement.setInt(1, userToDrop.getId());
            ResultSet results = statement.executeQuery();

            while(results.next()) {
                deletedUser.setId(results.getInt(1));
                deletedUser.setUserName(results.getString(2));
                deletedUser.setPassword(results.getString(3));
                deletedUser.setFirstName(results.getString(4));
                deletedUser.setLastName(results.getString(5));
                deletedUser.setEmail(results.getString(6));

                deletedUser.setUserRole(new Role(results.getInt("role_id"), results.getString("role_name")));
            }

            return deletedUser;

        } catch (SQLException e) {

            e.printStackTrace();
            log.warn("Error (SQL Admin): Failed to delete an individual user form the database.");
            return null;
        }
    }


    /**
     * Admin will return a list of all Users in the Users table.
     * Returns false if query was unsuccessful.
     * @return List<User>
     */
    @Override
    public List<User> returnAllUsers() {
        // This List of User object is used to store the result set from the return all query
        List<User> users = new ArrayList<User>();

        try {

            Connection connected = JDBConnection.getConnection();
            //TODO: Test this method and makes sure the entire user and their account info is returned
            String sqlStatement = "SELECT * FROM users INNER JOIN roles ON users.role_id = roles.id";

            Statement statement = connected.createStatement();
            ResultSet results = statement.executeQuery(sqlStatement);

            while(results.next()) {

                int userId = results.getInt("id");
                String username = results.getString("username");
                String pass = results.getString("password");
                String firstName = results.getString("first_name");
                String lastName = results.getString("last_name");
                String email = results.getString("email");
                int roleId = results.getInt("role_id");
                String roleName = results.getString("role_name");

                Role userRole = new Role(roleId, roleName);
                User user = new User(userId, username, pass, firstName, lastName, email, userRole);

                users.add(user);
            }
        } catch(SQLException e) {

            e.printStackTrace();
            log.warn("Error (SQL Admin): Failed to retrieve all users from the database.");
            return new ArrayList<User>();
        }
        return users;
    }


    /**
     * Admin will return an individual user.
     * @param userToReturn
     * @return User
     */
    @Override
    public User returnSingleUser(User userToReturn) {
        User userFound = new User();

        try {

            Connection connected = JDBConnection.getConnection();
            String sqlQuery = "SELECT FROM users WHERE username = ?";

            PreparedStatement statement = connected.prepareStatement(sqlQuery);
            statement.setString(1, userToReturn.getUserName());
            ResultSet results = statement.executeQuery();

            while(results.next()) {

                userFound.setId(results.getInt(1));
                userFound.setUserName(results.getString(2));
                userFound.setPassword(results.getString(3));
                userFound.setFirstName(results.getString(4));
                userFound.setLastName(results.getString(4));
                userFound.setEmail(results.getString(4));
                userFound.setUserRole(new Role(results.getInt("role_id"), results.getString("role_name")));

            }
        } catch(SQLException e) {

            e.printStackTrace();
            log.warn("Error (SQL Admin): Failed to retrieve a user.");
            return null;
        }

        return userFound;
    }

    /**
     * Admin will be able to access a specific user and update their respective information
     * @param userToUpdate
     * @return
     */
    @Override
    public User updateUserInfo(User userToUpdate) {
        //TODO: Finish updating the user update method for the admin dao
        return null;
    }
}
