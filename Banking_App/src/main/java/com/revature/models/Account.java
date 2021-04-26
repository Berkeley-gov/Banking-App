package com.revature.models;

import com.revature.exceptions.InsufficientFundsException;
import com.revature.exceptions.InvalidAccountInputException;
import java.util.Objects;

public class Account {
    private int Id;
    private int userId;
    private double accountBalance;
    private int activeId;
    private int accountTypeId;

    // ******** Constructors ********
    /**
     * Default No-args constructor for Account object
     */
    public Account() {

    }

    /**
     * Parameterized constructor for Account object
     * @param accountId
     * @param userId
     * @param accountType
     * @param accountBalance
     * @param active
     */
    public Account(int accountId, int userId, double accountBalance, int active,  int accountType) {
        super();
        this.Id = accountId;
        this.userId = userId;
        this.accountBalance = accountBalance;
        this.activeId = active;
        this.accountTypeId = accountType;
    }

    public Account(int userId, double accountBalance, int active,  int accountType) {
        super();
        this.userId = userId;
        this.accountBalance = accountBalance;
        this.activeId = active;
        this.accountTypeId = accountType;
    }

    // ******** Getter And Setter Methods ********

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public double getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(double accountBalance) {
        this.accountBalance = accountBalance;
    }

    public int getActiveId() {
        return activeId;
    }

    public int getAccountTypeId() {
        return accountTypeId;
    }

    public void setAccountTypeId(int accountTypeId) {
        this.accountTypeId = accountTypeId;
    }

    public void setActiveId(int activeId) {
        this.activeId = activeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Id == account.Id && userId == account.userId && Double.compare(account.accountBalance, accountBalance) == 0 && activeId == account.activeId && accountTypeId == account.accountTypeId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(Id, userId, accountBalance, activeId, accountTypeId);
    }

    @Override
    public String toString() {
        return "Account { " +
                "Id = " + Id +
                ", userId = " + userId +
                ", accountBalance = " + accountBalance +
                ", activeId = " + activeId +
                ", accountTypeId = " + accountTypeId +
                " }";
    }

    // ******** Account Class Functionality ********
    /**
     * Withdraw method will perform the arithmetic necessary to take money from a user's account
     * @param amount
     * @throws InsufficientFundsException
     * @throws InvalidAccountInputException
     */
    public void withdraw(double amount) throws InsufficientFundsException, InvalidAccountInputException {

        if(amount < 0.0) {

            throw new InvalidAccountInputException();

        } else if(amount > this.accountBalance) {

                throw new InsufficientFundsException();

        } else if((this.accountBalance - amount) < 50) {

                System.out.println("\nAccount message (Alert): You have less than $50 in your current account.");

        } else {
            this.accountBalance = this.accountBalance - amount;
        }
    }

    /**
2
     * Deposit method will perform the necessary arithmetic to add money to a user's account
     * @param amountToDeposit
     * @return double 
     * @throws InvalidAccountInputException
     */
    public void deposit(double amountToDeposit) throws InvalidAccountInputException {

        if(amountToDeposit < 0.0) {
            System.out.println("\nEnter a positive number for deposit.");
            throw new InvalidAccountInputException();

        } else {
            this.accountBalance += amountToDeposit;
        }
    }
}
