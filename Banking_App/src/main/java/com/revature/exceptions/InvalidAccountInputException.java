package com.revature.exceptions;

/**
 * Invalid Account Input Exception will notify the user of invalid inputs when creating an account
 */
public class InvalidAccountInputException extends Exception{
    Exception e;

    /**
     * Constructor for the invalid exception class wll
     * print to the console "Invalid input" and the stack trace
     * if unexpected values are inputted to the program
     */
    public InvalidAccountInputException() {
        System.out.println("\nError: Invalid input");
        e.printStackTrace();
    }
}
