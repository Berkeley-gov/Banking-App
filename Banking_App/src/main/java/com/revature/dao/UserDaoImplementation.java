package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.models.User;
import com.revature.models.Role;
import com.revature.utility.JDBConnection;
import org.apache.log4j.Logger;

/**
 * User Dao Implementation class is responsible for dealing with all logic that will
 * be used to interact with the database. Also used to abstract the complexity of the logic used
 * to perform CRUD operations on the database.
 */
public class UserDaoImplementation implements UserDao {
    // Logger object created to track statistics regarding sql statements and their execution
    private static final Logger log = Logger.getLogger(UserDaoImplementation.class);

    /**
     * Method will insert a new User object into the database.
     * If the operation was unsuccessful, then a 0 will be returned.
     * @param newUser
     * @return int
     */
    @Override
    public int insert(User newUser) {

        try {
            // Step 1.) Get connection to database using JDBConnection
            Connection connected = JDBConnection.getConnection();

            // Step 2.) Defined Prepared SQL statement to perform insert action on database
            String sqlStatement = "INSERT INTO users (username, pass, first_name, last_name, email, role_id) VALUES (?, ?, ?, ?, ?, ?)";

            // Step 3.) Obtained Statement Object and use PreparedStatement sub interface
            PreparedStatement statement = connected.prepareStatement(sqlStatement);

            // Injects values into prepared SQL statement
            statement.setString(1, newUser.getUserName());
            statement.setString(2, newUser.getPassword());
            statement.setString(3, newUser.getFirstName());
            statement.setString(4, newUser.getLastName());
            statement.setString(5, newUser.getEmail());
            statement.setInt(6, newUser.getUserRole().getId());

            // Step 4.) Executes the prepared sql statement to the database
            return statement.executeUpdate();

            // Step 5.) Catches any possible exceptions and handles it and logs the event
        } catch (SQLException e) {
            e.printStackTrace();
            log.error("\nError (Insert Operation): Insert SQL statement failed to execute.");
        }

        // Method will return 0 if the operation was not successful
        return 0;
    }

    /**
     * Method will find all User records from users table based on their respective id
     * (Primary key) and then return all result set data. If the operation was not
     * successful, than the method will return an empty ArrayList
     * @return List<User>
     */
    @Override
    public List<User> findAll() {

        // A list of User objects created to hold all retrieved Users from SQL result set
        List<User> userList = new ArrayList<User>();
        Statement statement = null;
        ResultSet resultSet = null;
        Connection connected = null;

        try {
            connected = JDBConnection.getConnection();
            String sqlStatement = "SELECT * FROM users INNER JOIN roles ON users.role_id = roles.id";

            statement = connected.createStatement();

            resultSet = statement.executeQuery(sqlStatement);

            // Iterates over every record that's obtained from the database;
            while (resultSet.next()) {

                int userId = resultSet.getInt("id");
                String username = resultSet.getString("username");
                String password = resultSet.getString("pass");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String email = resultSet.getString("email");
                int roleId = resultSet.getInt("role_id");
                String roleName = resultSet.getString("role_name");

                // Uses data pulled from database to create Java User Objects
                Role userRole = new Role(roleId, roleName);
                User newUser = new User(userId, username, password, firstName, lastName, email, userRole);
                userList.add(newUser);
            }

            // Closing all connection resources to avoid a JDBCConnectionException exception
            connected.close();
            statement.close();
            resultSet.close();

        } catch (SQLException e) {
            e.printStackTrace();
            log.warn("Error (SELECT ALL): Failed to complete SELECT ALL SQL statement.");

            // If exceptions occurs, than return an empty ArrayList
            return new ArrayList<User>();
        }

        return userList;
    }

    /**
     * When in invoked, the method will find a user by their
     * primary key and return the User object. If not successful,
     * the method returns null.
     * @param id
     * @return User
     */
    @Override
    public User findById(int id) {

        User userToFind = new User();

        Connection connected = JDBConnection.getConnection();
        String sqlStatement = "SELECT * FROM users INNER JOIN ON users.role_id = roles.id WHERE users.id = " + id + "";

        // Returns a User's information and their role in the WorldBank
        try{

            Statement statement = connected.createStatement();
            ResultSet sqlResults = statement.executeQuery(sqlStatement);

            while(sqlResults.next()) {

                userToFind.setId(sqlResults.getInt("id"));
                userToFind.setUserName(sqlResults.getString("username"));
                userToFind.setPassword(sqlResults.getString("password")); //TODO: Double check name desktop db
                userToFind.setFirstName(sqlResults.getString("first_name"));
                userToFind.setLastName(sqlResults.getString("last_name"));
                userToFind.setEmail(sqlResults.getString("email"));
                userToFind.setUserRole(new Role(sqlResults.getInt("role_id"), sqlResults.getString("role_name")));
            }
        } catch(SQLException e) {

            e.printStackTrace();
            log.warn("Error (Find By ID): SQL query for finding a user via ID failed.");
            return null;
        }

        return userToFind;
    }

    /**
     * The method will search for a User object via their username.
     * If the query was unsuccessful, the method will return null.
     * @param username
     * @return User
     */
    @Override
    public User findByUsername(String username) {

        User user = new User();
        Connection connected = JDBConnection.getConnection();
        String sqlStatement = "SELECT * FROM users INNER JOIN roles ON users.role_id = roles.id WHERE users.username = '" + username + "'";

        try {

            Statement statement = connected.createStatement();
            ResultSet results = statement.executeQuery(sqlStatement);

            while(results.next()) {

                user.setId(results.getInt("id"));
                user.setUserName(results.getString("username"));
                user.setPassword(results.getString("pass"));
                user.setFirstName(results.getString("first_name"));
                user.setLastName(results.getString("last_name"));
                user.setEmail(results.getString("email"));
                user.setUserRole(new Role(results.getInt("role_id"),results.getString("role_name")));
            }

        } catch(SQLException e) {

            e.printStackTrace();
            log.warn("Error (Find By Username): SQL query failed to find user by username.");
            return null;
        }

        return user;
    }

    /**
     *
     * @param updatedUser
     * @return int
     */
    @Override
    public int updateUserEmail(User updatedUser) {

        try {
            Connection connected = JDBConnection.getConnection();
            String sqlStatement = "UPDATE users SET email = ? WHERE id = " + updatedUser.getId() + "";

            PreparedStatement statement = connected.prepareStatement(sqlStatement);
            log.info("Successful: You email was changed to: " + updatedUser.getEmail());
            statement.setString(1, updatedUser.getEmail());
            return statement.executeUpdate();

        } catch (SQLException e) {
            log.warn("Error (SQL USER): The query to change user's email failed.");
            e.printStackTrace();
            return 0;
        }
    }

    /**
     *
     * @param deletedUser
     * @return int
     */
    @Override
    public int delete(User deletedUser) {

        int idDeleting = deletedUser.getId();

        Connection connected = JDBConnection.getConnection();
        String sqlStatement = "DELETE FROM users WHERE id = " + idDeleting + "";


        try {

            Statement statement = connected.createStatement();
            ResultSet results = statement.executeQuery(sqlStatement);

            log.info("SQL: User was successfully deleted from WorldBank records.");

        } catch(SQLException e) {

            e.printStackTrace();
            log.warn("Error (Deleting User By ID): SQL Query to delete a User failed.");
            return 0;
        }

        return 1;
    }
}
