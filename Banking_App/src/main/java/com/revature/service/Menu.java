package com.revature.service;

import com.revature.exceptions.InsufficientFundsException;
import com.revature.exceptions.InvalidAccountInputException;
import org.apache.log4j.Logger;

import java.util.Scanner;

/**
 * The Menu class will instantiate a Menu interface
 */
public class Menu {

    private static final Logger log = Logger.getLogger(Menu.class);


    public static void loginMenu() throws InvalidAccountInputException, InsufficientFundsException {

        Scanner scan = new Scanner(System.in);

        int userInput;

        do {

            System.out.println("\nWELCOME TO THE WORLD BANK\n\n[1] - Register an account\n[2] - Sign in\n[3] - Log Off");

            System.out.println("\nEnter option: ");
            userInput = scan.nextInt();

            if(userInput == 1) {

                UserService.register();

            } else if(userInput == 2) {

                System.out.println("\nWORLD BANK: ACCOUNT SIGN IN");
                scan.nextLine();
                System.out.println("\nEnter account username: ");
                String username = scan.nextLine();

                System.out.println("\nEnter password: ");
                String password = scan.nextLine();

                UserService.login(username, password);

            } else if(userInput == 3) {

                log.info("Logged off world banking application.");
                System.out.println("\nWORLD BANK: LOGGED OFF");
                // System exit will terminate the infinite loop and end the program
                System.exit(0);

            } else {
                log.warn("Error: Invalid input");
                System.out.println("Please enter a valid menu option");
            }

        } while(userInput != 1 && userInput != 2);

    }
}
