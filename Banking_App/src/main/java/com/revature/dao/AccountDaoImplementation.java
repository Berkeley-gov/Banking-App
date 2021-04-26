package com.revature.dao;

import com.revature.models.Account;
import com.revature.utility.JDBConnection;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.*;

public class AccountDaoImplementation implements AccountDao {

    private static Logger log = Logger.getLogger(AccountDaoImplementation.class);


    /**
     * Insert method will CREATE a new Account for a user and insert it into the database
     * @param newAccount
     * @return int
     */
    @Override
    public int insert(Account newAccount) {
        try {

            Connection connected = JDBConnection.getConnection();
            String sqlQuery = "INSERT INTO accounts (user_id, balance, status_id, type_id) VALUES (?, ?, ?, ?)";

            PreparedStatement statement = connected.prepareStatement(sqlQuery);

            statement.setInt(1, newAccount.getUserId());
            statement.setDouble(2, newAccount.getAccountBalance());
            statement.setInt(3, newAccount.getActiveId());
            statement.setInt(4, newAccount.getAccountTypeId());

            // Will return the number of rows updated; should be 1 if successful
            return statement.executeUpdate();

        } catch (SQLException e) {

            e.printStackTrace();
            log.warn("Error (SQL Account): Account information failed to insert data into the database.");
        }

        return 0;
    }


    /**
     * Find all method will query the table for all users accounts and their data
     * and will return the entire table.
     * @return List<Account>
     */
    @Override
    public List<Account> findAll() {
        // List instantiated to hold all records returned from the user table
        List<Account> accounts = new ArrayList<Account>();

        try {

            Connection connected = JDBConnection.getConnection();
            String sqlStatement = "SELECT * FROM accounts";

            Statement statement = connected.createStatement();
            ResultSet results = statement.executeQuery(sqlStatement);

            while(results.next()) {

                int id = results.getInt("id");
                int userId = results.getInt("user_id");
                double balance = results.getDouble("balance");
                int statusId = results.getInt("status_id");
                int typeId = results.getInt("type_id");

                Account account = new Account(id, userId, balance, statusId, typeId);
                accounts.add(account);
            }

        } catch(SQLException e) {

            e.printStackTrace();
            log.warn("Error (SQL Account): Failed to execute the find all from accounts query.");
            return new ArrayList<Account>();
        }

        return accounts;
    }


    /**
     * Method will search an Account by using the account's primary key
     * @param id
     * @return Account
     */
    @Override
    public Account findById(int id) {

        Account account = new Account();

        try {

            Connection connected = JDBConnection.getConnection();
            String sqlStatement = "SELECT * FROM accounts WHERE id = ?";

            PreparedStatement statement = connected.prepareStatement(sqlStatement);
            statement.setInt(1, id);

            ResultSet results = statement.executeQuery();

            while(results.next()) {

                account.setId(results.getInt("id"));
                account.setUserId(results.getInt("user_id"));
                account.setAccountBalance(results.getDouble("balance"));
                account.setActiveId(results.getInt("status_id"));
                account.setAccountTypeId(results.getInt("type_id"));

            }

        } catch(SQLException e) {

            e.printStackTrace();
            log.warn("Error (SQL Account): Failed to execute the find an account using the primary key.");
            return null;
        }

        return account;
    }


    /**
     * Method will find an Account by their inputted userId
     *
     * @param userId
     * @return Account
     */
    @Override
    public Account findUserID(int userId) {

        Account account = null;

        try {

            Connection connected = JDBConnection.getConnection();
            String sqlQuery = "SELECT * FROM accounts WHERE user_id = " + userId + "";

            Statement statement = connected.createStatement();
            ResultSet results = statement.executeQuery(sqlQuery);

            int id = 0;
            int user_Id = 0;
            double balance = 0;
            int statusId = 0;
            int typeId = 0;

            while (results.next()) {

                id = results.getInt(1);
                user_Id = results.getInt(2);
                balance = results.getDouble(3);
                statusId = results.getInt(4);
                typeId = results.getInt(5);

                account = new Account(id, user_Id, balance, statusId, typeId);
            }

        } catch (SQLException e) {

            e.printStackTrace();
            log.warn("Error (SQL ACCOUNT): Failed to query and return user account information.");
            return new Account();

        }

        return account;
    }


    /**
     * Method will update a user's Account status and type of account they have
     * Should have updated values already inserted into the object before passing it as an argument.
     * @param updateAccount
     * @return int
     */
    @Override
    public int update(Account updateAccount) {

        try {

            Connection connected = JDBConnection.getConnection();
            String sqlStatement = "UPDATE accounts " +
                                   "SET status_id = ?," +
                                       "type_id = ?" +
                                   "WHERE user_id = ?";

            PreparedStatement statement = connected.prepareStatement(sqlStatement);
            statement.setDouble(1, updateAccount.getActiveId());
            statement.setInt(2, updateAccount.getAccountTypeId());

            return statement.executeUpdate();

        } catch (SQLException e) {

            e.printStackTrace();
            log.warn("Error (Account SQL): Failed to update account status id and type id to the database.");
            return 0;
        }

    }


    /**
     * Method will delete an account by user id
     * @param deleteAccount
     * @return int
     */
    @Override
    public int deleteAccount(Account deleteAccount) {

        try {

            Connection connected = JDBConnection.getConnection();
            String sqlStatement = "DELETE FROM accounts WHERE user_id = ?";

            PreparedStatement statement = connected.prepareStatement(sqlStatement);
            statement.setDouble(1, deleteAccount.getUserId());

            log.info("\nUser account was successfully deleted.");
            return statement.executeUpdate();

        } catch (SQLException e) {

            e.printStackTrace();
            log.warn("Error (Account SQL): Failed to delete an account from the table using user's id.");
            return 0;
        }

    }


    /**
     * Updates an account balance and it is used to update account amount
     * after a user has withdrawn or deposited money.
     *
     * @param updateAccount
     * @return int
     */
    @Override
    public int updateBalance(Account updateAccount) {

        try {
            Connection connected = JDBConnection.getConnection();
            String sqlStatement = "UPDATE accounts SET BALANCE = " + updateAccount.getAccountBalance() + " WHERE user_id = " + updateAccount.getUserId() + "";

            Statement statement = connected.createStatement();

            log.info("Successful: Account balance has been updated");
            return statement.executeUpdate(sqlStatement);

        } catch (SQLException e) {

            e.printStackTrace();
            log.warn("Error (Account SQL): Failed to update account balance to the database.");
            return 0;
        }

    }


    /**
     * Updates a user's account status to either active and deactivated.
     * Method should be used by admin and employee user's only.
     * @param account
     * @return int
     */
    @Override
    public int updateAccountStatus(Account account) {

        try {
            Connection connected = JDBConnection.getConnection();
            String sqlStatement = "UPDATE accounts SET status_id = ? WHERE user_id = ?";

            PreparedStatement statement = connected.prepareStatement(sqlStatement);
            statement.setDouble(1, account.getActiveId());
            statement.setInt(2, account.getUserId());

            log.warn("\nAccount has been deactivated.");
            return statement.executeUpdate();

        } catch (SQLException e) {

            e.printStackTrace();
            log.warn("Error (Account SQL): Failed to update account status to the database.");
            return 0;

        }
    }


    /**
     * Updates a user's account type to either checking
     * or savings account. Should be used by employees and admins only
     *
     * @param account
     * @return int
     */
    @Override
    public int updateAccountType(Account account) {

        try {
            Connection connected = JDBConnection.getConnection();
            String sqlStatement = "UPDATE accounts SET type_id = ?";

            PreparedStatement statement = connected.prepareStatement(sqlStatement);
            statement.setDouble(1, account.getAccountTypeId());

            log.warn("\nAccount type has been changed.");
            return statement.executeUpdate();

        } catch (SQLException e) {

            e.printStackTrace();
            log.warn("Error (Account SQL): Failed to update account type to the database.");
            return 0;
        }
    }


    /**
     * Checks the account's current balance
     * @param id
     * @return double
     */
    @Override
    public double checkBalance(int id) {

        double currentBalance = 0.0;

        try {

            Connection connected = JDBConnection.getConnection();
            String sqlStatement = "SELECT balance FROM accounts WHERE user_id = " + id + "";

            Statement statement = connected.createStatement();
            ResultSet results = statement.executeQuery(sqlStatement);

            while(results.next()) {
                currentBalance = results.getDouble("balance");
            }


            log.info("Successful: User balance retrieved");
            return currentBalance;

        } catch(SQLException e) {

            e.printStackTrace();
            log.warn("Error (SQL Account): Failed to retrieve account balance information.");
            return 0;
        }
    }

    /**
     * Method deletes an account based on user id primary key
     * @param userId
     * @return int
     */
    @Override
    public int deleteByUserId(int userId) {

        try {

            Connection connected = JDBConnection.getConnection();
            String sqlStatement = "DELETE FROM accounts WHERE user_id = ?";

            PreparedStatement statement = connected.prepareStatement(sqlStatement);
            statement.setDouble(1, userId);

            log.info("\nUser account was successfully deleted.");
            return statement.executeUpdate();

        } catch (SQLException e) {

            e.printStackTrace();
            log.warn("Error (Account SQL): Failed to delete an account from the table using user's id.");
            return 0;

        }

    }

}
