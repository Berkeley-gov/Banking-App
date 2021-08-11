# Banking Application

## Introduction
By leveraging Java 8, I developed an application that simulates simple banking transactions through the terminal/console. Users of the application can access the application by downloading the source code and running the program locally. The program only takes input from the terminal and displays output from the terminal.

## Banking Application Functionality
*	Built the application using Java 8
*	All interaction with the user is done through the console using the `Scanner` class
*	Customers of the bank are able to register with a username and password, and apply to open an account. 
*	Customers are able to apply for joint accounts

*	Once the account is open, customers are able to withdraw, deposit, and transfer funds between accounts
    * All basic validation is done, such as trying to input negative amounts, overdrawing from accounts etc.
    
*	Employees of the bank are able to view all of their customers information. This includes:
    * Account information
    * Account balances
    * Personal information
    
*	Employees are able to approve/deny open applications for accounts

*	Bank admins are able to view and edit all accounts. This includes:
    * Approving/denying accounts
    * withdrawing, depositing, transferring from all accounts
    * canceling accounts
    
*	Reasonable test coveragg was conducted using JUnit.
    * TDD is encouraged.
    
*	Logging is accomplished using Log4J
    * All transactions should be logged


## Technologies Used to Build Project
* Java 8
* SQL
* Postgres Database
* JDBC
* AWS Relation Database Instance 
* L4j Logging
* JUnit (Testing)
* Mockito (Database testing)
* Maven
* GitHub


## Software Tools Used
* Eclipse
* DBeaver
