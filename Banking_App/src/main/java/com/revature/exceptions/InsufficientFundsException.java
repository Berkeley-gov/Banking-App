package com.revature.exceptions;

/**
 * Exception class: This class will throw an exception when instantiated
 * Exception will be throw if user's request to withdraw an invalid ammount
 * from their accounts.
 *
 */
public class InsufficientFundsException extends Exception{
    Exception e;

    /**
     * Constructor for the insufficient funds exception class wll
     * print to the console "Insufficient funds" and the stack trace
     * if unexpected values are inputted to the program
     */
    public InsufficientFundsException() {
        System.out.println("\n Error: Insufficient funds");
        e.printStackTrace();
    }

}
