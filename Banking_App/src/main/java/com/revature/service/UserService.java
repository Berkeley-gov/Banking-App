package com.revature.service;

import com.revature.dao.*;
import com.revature.exceptions.InsufficientFundsException;
import com.revature.exceptions.InvalidAccountInputException;
import com.revature.models.Account;
import com.revature.models.Role;
import com.revature.models.User;
import org.apache.log4j.Logger;

import java.util.*;


public class UserService {

    private static final UserDao userDao = new UserDaoImplementation();
    private static final AdminDao adminDao = new AdminDaoImplementation();
    private static final AccountDao accountDao = new AccountDaoImplementation();

    private static final Logger log = Logger.getLogger(UserService.class);
    // Logger object created to track statistics regarding sql statements and their execution


    /**
     * @param username
     * @param password
     */
    public static void login(String username, String password) throws InvalidAccountInputException, InsufficientFundsException {

        // username is matched against the data base to retrieve all user information including their account password
        User userSignedIn = userDao.findByUsername(username);
        // Retrieves the user's account by providing the user's primary key and stores into new account object

        // password passed into the method will be compared against the user database object password
        if (password.equals(userSignedIn.getPassword())) {

            Account accountSignedInTo = accountDao.findUserID(userSignedIn.getId());

            log.info("Authorized: Account Sign In Successful.");

            switch (userSignedIn.getUserRole().getId()) {

                case 1:
                    System.out.println("\nWORLD BANK \n\nLOGGED IN AS ADMINISTRATOR: " + userSignedIn.getFirstName() + " " + userSignedIn.getLastName());
                    adminMenu(userSignedIn, accountSignedInTo);
                    break;

                case 2:
                    System.out.println("\nWORLD BANK \n\nLOGGED IN AS WORLD BANK EMPLOYEE: " + userSignedIn.getFirstName() + " " + userSignedIn.getLastName());
                    employeeMenu(userSignedIn, accountSignedInTo);
                    break;

                case 3:
                    System.out.println("\nWORLD BANK \n\nLOGGED IN AS: " + userSignedIn.getFirstName() + " " + userSignedIn.getLastName());
                    try {
                        customerMenu(userSignedIn, accountSignedInTo);
                    } catch (InvalidAccountInputException | InsufficientFundsException e) {
                        e.printStackTrace();
                    }
                    break;
            }

        } else {

            System.out.println("\nLogin in failed: Password is incorrect.");
            log.info("User Failed To Login Into Their Account.");
        }
    }


    /**
     *
     */
    public static void register() throws InvalidAccountInputException, InsufficientFundsException {

        UserDao userDao = new UserDaoImplementation();
        Scanner scan = new Scanner(System.in);
        Role userRole = new Role();

        System.out.println("\nWELCOME TO THE WORLD BANK!\nEnter a username for your World Bank account: ");
        String username = scan.nextLine();

        System.out.println("\nEnter a password for the account: ");
        String password = scan.nextLine();

        System.out.println("\nEnter first name: ");
        String firstName = scan.nextLine();

        System.out.println("\nEnter last name: ");
        String lastName = scan.nextLine();

        System.out.println("\nEnter an email address for the account: ");
        String userEmail = scan.nextLine();

        System.out.println("\nChoose the account type: Admin, Employee, or Customer?\n\nEnter account type: ");
        String userInput = scan.nextLine();


        if (userInput.equalsIgnoreCase("admin") || userInput.equalsIgnoreCase("administrator")) {

            userRole.setId(1);
            userRole.setRoleName("Administrator");

        } else if (userInput.equalsIgnoreCase("employee")) {

            userRole.setId(2);
            userRole.setRoleName("Employee");

        } else if (userInput.equalsIgnoreCase("customer")) {

            userRole.setId(3);
            userRole.setRoleName("Customer");

        } else {

            System.out.println("\nInvalid input. Please try again.");
            log.warn("The user did not choose a valid account type.");

        }

        User user = new User(username, password, firstName, lastName, userEmail, userRole);
        System.out.println(userDao.insert(user));

        //*****************************************
        createAccount(userDao.findByUsername(user.getUserName()));
        // System.out.println(service.returnAllUsers());
    }


    /**
     * @param newUser
     */
    public static void createAccount(User newUser) throws InvalidAccountInputException, InsufficientFundsException {

        Scanner scan = new Scanner(System.in);
        Account newAccount = new Account();
        int userInput;
        double balance = 500.0;
        int statusId = 1;
        int typeId;

        System.out.println("WORLD BANK USER ACCOUNTS \n\nPlease choose an account type you would like to open.");

        do {

            System.out.println("[1] Checking Account");
            System.out.println("[2] Savings Account");
            System.out.println("\nEnter the number for account type you want: ");
            userInput = scan.nextInt();

            if (userInput == 1) {

                typeId = 1;

                System.out.println("\nWORLD BANK CHECKING ACCOUNT CREATED...\n");

                newAccount.setUserId(newUser.getId());
                newAccount.setAccountBalance(balance);
                newAccount.setAccountTypeId(statusId);
                newAccount.setActiveId(typeId);

                System.out.println("Checking Account Status: Active");
                System.out.println("Checking Account Owner: " + newUser.getFirstName() + " " + newUser.getLastName());
                System.out.println("Checking Account Balance: $" + balance);

                accountDao.insert(newAccount);
                customerMenu(newUser, newAccount);

            } else if (userInput == 2) {

                typeId = 2;

                System.out.println("\nWORLD BANK SAVINGS ACCOUNT CREATED...\n");

                newAccount.setUserId(newUser.getId());
                newAccount.setAccountBalance(balance);
                newAccount.setAccountTypeId(typeId);
                newAccount.setActiveId(1);

                System.out.println("Savings Account Status: Active");
                System.out.println("Savings Account Owner: " + newUser.getFirstName() + " " + newUser.getLastName());
                System.out.println("Savings Account Balance: $" + balance);

                accountDao.insert(newAccount);

            } else {
                System.out.println("Invalid input: Not a choice. Try again.");
                log.warn("Error: user did not enter a numeric type used to pick an account type.");
            }

        } while (userInput != 1 && userInput != 2);
    }


    /**
     * @param customer
     * @param customerAccount
     * @throws InvalidAccountInputException
     * @throws InsufficientFundsException
     */
    public static void customerMenu(User customer, Account customerAccount) throws InvalidAccountInputException, InsufficientFundsException {

        Scanner scan = new Scanner(System.in);
        int userInput;

        do {

            System.out.println("\nWORLD BANK ACCOUNT MENU\n");

            System.out.println("[1] - Deposit");
            System.out.println("[2] - Withdraw");
            System.out.println("[3] - Check Balance");
            System.out.println("[4] - Change email address");
            System.out.println("[5] - Deactivate Account");
            System.out.println("[6] - Exit Menu\n");

            System.out.println("\nEnter an option: ");
            userInput = scan.nextInt();

            // Checks if the user's input was a valid menu option
            if (userInput < 1 || userInput > 6) {

                System.out.println("\nInvalid option. Please enter numerical option displayed in the menu");
                log.warn("Error (Invalid Option Menu): User entered an invalid option in the user menu.");

            } else {

                switch (userInput) {

                    case 1:
                        System.out.println("\nEnter amount to deposit into your account: ");
                        double deposit = scan.nextDouble();

                        customerAccount.deposit(deposit);
                        System.out.println(customerAccount.getAccountBalance());
                        accountDao.updateBalance(customerAccount);

                        log.info("Successful: User has deposited money into their account.");
                        break;

                    case 2:
                        System.out.println("\nEnter the amount you would like to withdraw: ");
                        double amountToWithdraw = scan.nextDouble();

                        customerAccount.withdraw(amountToWithdraw);
                        accountDao.updateBalance(customerAccount);
                        break;

                    case 3:
                        double currentBalance = accountDao.checkBalance(customer.getId());
                        System.out.println("\nCurrent account balance: $" + currentBalance);
                        break;

                    case 4:
                        System.out.println("\nCurrent email address: " + customer.getEmail());
                        System.out.println("Enter your new email address: ");
                        scan.nextLine();

                        String email = scan.nextLine();
                        customer.setEmail(email);

                        userDao.updateUserEmail(customer);
                        break;

                    case 5:
                        System.out.println("Are you sure you want deactivate your account? \nEnter yes or no: ");
                        scan.nextLine();
                        String deactivate = scan.nextLine();

                        if(deactivate.equalsIgnoreCase("yes")) {
                            customerAccount.setActiveId(2);
                            System.out.println("\nWe're sorry to see you go. Your account has been deactivated.");
                            accountDao.updateAccountStatus(customerAccount);
                        }
                        break;

                    case 6:
                        System.out.println("\nLOGGING OFF \nGoodbye " + customer.getFirstName() + " " + customer.getLastName());
                        break;
                }
            }

        } while (userInput != 6);

    }


    public static void adminMenu(User admin, Account adminAccount) {

        Scanner scan = new Scanner(System.in);
        // Copying the ArrayList retrieved query all account to the ArrayList accounts
        List<Account> accounts = accountDao.findAll();
        // Copying the ArrayList retrieved query all users to the ArrayList users
        List<User> users = userDao.findAll();

        int userInput;

        do {

            System.out.println("\nWORLD BANK: ADMINISTRATOR MENU\n");

            System.out.println("[1] - Delete all users");
            System.out.println("[2] - Display all users");
            System.out.println("[3] - Check Balance");
            System.out.println("[4] - Cancel Account");
            System.out.println("[5] - Exit Menu\n");

            System.out.println("\nEnter an option: ");
            userInput = scan.nextInt();

            // Checks if the user's input was a valid menu option
            if (userInput < 1 || userInput > 6) {

                System.out.println("\nInvalid option. Please enter numerical option displayed in the menu");
                log.warn("Error (Invalid Option Menu): User entered an invalid option in the user menu.");

            } else {

                switch (userInput) {

                    case 1:
                        System.out.println("\nWarning: Are you sure you want to delete all users from the World Bank database?");
                        System.out.println("\nEnter a choice [Y]es/[N]o");
                        scan.nextLine();
                        String input = scan.nextLine();

                        if (input.equalsIgnoreCase("y") || input.equalsIgnoreCase("yes")) {
                            log.info("Successful: All users and their accounts have been deleted.");
                            adminDao.deleteAllUser();
                        } else {
                            break;
                        }
                        break;

                    case 2:
                        log.info("Successful: Retrieved all users from World Bank database.");
                        System.out.println("\nWORLD BANK ALL USERS:\n");

                        for (int i = 0; i < accounts.size(); i++) {
                            System.out.println("\nCustomer name: " + users.get(i).getFirstName() + " " + users.get(i).getLastName() + " " + users.get(i).toString() + "\nAccount Details: " + accounts.get(i).toString());
                        }
                        break;

                    case 3:
                        System.out.println("\nEnter the name of the user to view their account balance: ");
                        scan.nextLine();
                        String username = scan.nextLine();

                        for(int i = 0; i < accounts.size(); i++) {
                            if(users.get(i).getUserName().equalsIgnoreCase(username)) {
                                System.out.println("\nUser: " + users.get(i).getFirstName() + " " + users.get(i).getLastName() + " " + "\nAccount balance: $" + accounts.get(i).getAccountBalance());
                            }
                        }
                        break;

                    case 4:
                        System.out.println("\nEnter the username of user's account you would like to cancel: ");
                        scan.nextLine();
                        String username2 = scan.nextLine();

                        for(int i = 0; i < accounts.size(); i++) {
                            if(users.get(i).getUserName().equalsIgnoreCase(username2)) {
                                    System.out.println("\n" + adminDao.deleteSingleUser(users.get(i)));
                            }
                        }
                        break;

                    case 5:
                        System.out.println("\nLOGGING OFF \nGoodbye " + admin.getFirstName() + " " + admin.getLastName());
                        break;
                }
            }

        } while (userInput != 5);
    }


    public static void employeeMenu(User employee, Account employeeAccount) throws InvalidAccountInputException, InsufficientFundsException {

        Scanner scan = new Scanner(System.in);
        // Copying the ArrayList retrieved query all account to the ArrayList accounts
        List<Account> accounts = accountDao.findAll();
        // Copying the ArrayList retrieved query all users to the ArrayList users
        List<User> users = userDao.findAll();

        int userInput;

        do {

            System.out.println("\nWORLD BANK: WB STAFF MENU\n");

            System.out.println("[1] - Add a customer");
            System.out.println("[2] - Retrieve all customer accounts");
            System.out.println("[3] - Search for customer");
            System.out.println("[4] - Retrieve active user accounts");
            System.out.println("[5] - Deactivate user account");
            System.out.println("[6] - Change customer information");
            System.out.println("[7] - Exit Menu\n");

            System.out.println("\nEnter an option: ");
            userInput = scan.nextInt();

            // Checks if the user's input was a valid menu option
            if (userInput < 1 || userInput > 7) {

                System.out.println("\nInvalid option. Please enter numerical option displayed in the menu");
                log.warn("Error (Invalid Option Menu): User entered an invalid option in the user menu.");

            } else {

                switch (userInput) {

                    case 1:
                        System.out.println("CREATING WORLD BANK USER");
                        register();
                        break;

                    case 2:
                        log.info("Successful: Retrieved all users from World Bank database.");
                        System.out.println("\nWORLD BANK ALL USERS:\n");

                        for (int i = 0; i < users.size(); i++) {
                            if(users.get(i).getUserRole().getId() == 3) {
                                System.out.println("\nCustomer name: " + users.get(i).getFirstName() + " " + users.get(i).getLastName() + " " + users.get(i).toString() + "\nAccount Details: " + accounts.get(i).toString());
                            }
                        }
                        break;

                    case 3:
                        System.out.println("\nEnter the customer's username: ");
                        scan.nextLine();
                        String searchName = scan.nextLine();
                        log.info("Searching for customer in World Bank database");

                        for (int i = 0; i < users.size(); i++) {
                            if(users.get(i).getUserName().equalsIgnoreCase(searchName)) {
                                System.out.println("\nCustomer Found: " + users.get(i).getFirstName() + " " + users.get(i).getLastName() + " " + users.get(i).toString() + "\nAccount Details: " + accounts.get(i).toString());
                            }
                        }

                        break;

                    case 4:
                        log.info("Retrieving all active customer accounts from World Bank database.");

                        for (int i = 0; i < accounts.size(); i++) {
                            if(accounts.get(i).getActiveId() == 1) {
                                System.out.println("\nActive Customer: " + users.get(i).getFirstName() + " " + users.get(i).getLastName() + " " + users.get(i).toString() + "\nAccount Details: " + accounts.get(i).toString());
                            }
                        }
                        break;

                    case 5:
                        System.out.println("\nEnter the username of the customer's account you would to deactivate: ");
                        scan.nextLine();
                        String username = scan.nextLine();

                        for (int i = 0; i < accounts.size(); i++) {
                            if(users.get(i).getUserName().equalsIgnoreCase(username)) {
                                accounts.get(i).setActiveId(2);
                                accountDao.updateAccountStatus(accounts.get(i));
                            }
                        }

                        break;

                    case 6:
                        System.out.println("\nEnter the customer's username to change their information: ");
                        scan.nextLine();
                        String username2 = scan.nextLine();

                        for (int i = 0; i < users.size(); i++) {
                            if(users.get(i).getUserName().equalsIgnoreCase(username2)) {
                                changeUserInformation(users.get(i));
                            }
                        }
                        break;

                    case 7:
                        System.out.println("\nLOGGING OFF \nGOODBYE " + employee.getFirstName() + " " + employee.getLastName());
                        break;
                }
            }

        } while (userInput != 6);

    }


    public static User searchForUser(List<User> usersList, String username) {

        User user = new User();

        if (usersList.isEmpty()) {

            log.warn("Error: No list of users found");
            return null;

        } else {

            for (int i = 0; i < usersList.size(); i++) {

                if (usersList.get(i).getUserName().equals(username)) {
                    user = usersList.get(i);
                    break;
                }

            }

            log.info("Success: User found in the World Bank database.");
            return user;
        }

    }

    public static void changeUserInformation(User userToChange) {

        Scanner scan = new Scanner(System.in);

        System.out.println("\nUpdate username: ");
        userToChange.setUserName(scan.nextLine());

        System.out.println("\nUpdate user's password: ");
        userToChange.setPassword(scan.nextLine());

        System.out.println("\nUpdate user's first name: ");
        userToChange.setFirstName(scan.nextLine());

        System.out.println("\nUpdate user's last name: ");
        userToChange.setLastName(scan.nextLine());

        System.out.println("\nUpdate user's email address: ");
        userToChange.setEmail(scan.nextLine());

        System.out.println("\nChoose user's role in the organization: Admin, Employee, or Customer?\n\nEnter account type: ");
        String userInput = scan.nextLine();


        if (userInput.equalsIgnoreCase("admin") || userInput.equalsIgnoreCase("administrator")) {

            userToChange.getUserRole().setId(1);
            userToChange.getUserRole().setRoleName("Administrator");

        } else if (userInput.equalsIgnoreCase("employee")) {

            userToChange.getUserRole().setId(2);
            userToChange.getUserRole().setRoleName("Employee");

        } else if (userInput.equalsIgnoreCase("customer")) {

            userToChange.getUserRole().setId(3);
            userToChange.getUserRole().setRoleName("Customer");

        } else {

            System.out.println("\nInvalid input. Please try again.");
            log.warn("The user did not choose a valid account type.");

        }

        log.info("Successful: User's information was successfully updated to the World Bank database.");
        userDao.insert(userToChange);
    }
}
