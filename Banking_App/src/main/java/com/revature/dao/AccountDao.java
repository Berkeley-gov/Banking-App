package com.revature.dao;

import com.revature.models.Account;

import java.util.List;

public interface AccountDao {

    /**
     * Insert method will CREATE a new Account for a user and insert it into the database
     * @param newAccount
     * @return int
     */
    public int insert(Account newAccount);

    /**
     * Find all method will query the table for all users accounts and their data
     * and will return the entire table
     * @return List<Account>
     */
    public List<Account> findAll();

    /**
     * Method will search an Account by their using the account primary key
     * @param id
     * @return Account
     */
    public Account findById(int id);

    /**
     * Method will find an Account by their inputted userId
     * @param userId
     * @return Account
     */
    public Account findUserID(int userId);

    /**
     * Method will update a user's Account information
     * @param updateAccount
     * @return int
     */
    public int update(Account updateAccount);

    /**
     * Method will deleted an account from the Account table
     * @param deleteAccount
     * @return int
     */
    public int deleteAccount(Account deleteAccount);

    /**
     * Updates an account balance and it is used to update account amount
     * after a user has withdrawn or deposited money.
     * @param account
     * @return double
     */
    public int updateBalance(Account account);

    /**
     * Updates a user's account status to either active and deactivated.
     * Method should be used by admin and employee user's only
     * @param account
     * @return int
     */
    public int updateAccountStatus(Account account);

    /**
     * Updates a user's account type to either checking
     * or savings account. Should be used by employees and admins only
     * @param account
     * @return int
     */
    public int updateAccountType(Account account);

    /**
     * Checks the account current balance
     * @param id
     * @return double
     */
    public double checkBalance(int id);

    /**
     * Method deletes an account based on user id primary key
     * @param userId
     * @return int
     */
    public int deleteByUserId(int userId);
}
