package com.revature.dao;

import com.revature.models.User;

import java.util.List;

/**
 * The Admin Data access object interface is used to perform CRUD operations
 * on Admin accounts. Admin class has database privileges that allow them to delete,
 * update tables as well as view or remove user(s) and their respective bank accounts
 */
public interface AdminDao {

    /**
     * Admin interface function will allow admin users to
     * delete all users in the bank database. Method returns
     * a false value if the query was unsuccessful
     * @return boolean
     */
    public boolean deleteAllUser();

    /**
     * Admin will delete a individual user from the database table Users.
     * Returns false if the query was unsuccessful.
     * @param userToDrop
     * @return User
     */
    public User deleteSingleUser(User userToDrop);

    /**
     * Admin will return a list of all Users in the Users table.
     * Returns false if query was unsuccessful.
     * @return List<User>
     */
    public List<User> returnAllUsers();

    /**
     * Admin will return an individual user.
     * @param userToReturn
     * @return User
     */
    public User returnSingleUser(User userToReturn);

    /**
     * Admin will be able to access a specific user and update their respective information
     * @param userToUpdate
     * @return
     */
    public User updateUserInfo(User userToUpdate);
}
