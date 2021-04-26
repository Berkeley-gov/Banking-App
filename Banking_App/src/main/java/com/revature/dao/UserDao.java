package com.revature.dao;

import com.revature.models.User;

import java.sql.SQLException;
import java.util.List;

/**
 * Data access object for User object.
 * The User DAO interfaces defines all the methods required
 * to make the necessary CRUD operations
 * (Create, Remove, Update, Delete)
 */
public interface UserDao {
    /**
     * Insert method will CREATE a new User object and insert it into the database
     * @param newUser
     * @return int
     */
    public int insert(User newUser);

    /**
     * Find all method will query the table for all users and their data
     * and will return the entire table
     * @return List<User>
     */
    public List<User> findAll();

    /**
     * Method will search a User by their User id
     * @param id
     * @return User
     */
    public User findById(int id);

    /**
     * Method will find a user by their inputted username
     * @param username
     * @return User
     */
    public User findByUsername(String username);

    /**
     * Method will update a user's information
     * @param updatedUser
     * @return int
     */
    public int updateUserEmail(User updatedUser);

    /**
     * Method will deleted a user from the table Users
     * @param deletedUser
     * @return int
     */
    public int delete(User deletedUser);
}
