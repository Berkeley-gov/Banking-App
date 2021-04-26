package com.revature.utility;

import org.apache.log4j.Logger;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * The JDBConnection class is used to establish a connection to a Postgres Database
 * Includes functionality from the JDBC framework
 */
public class JDBConnection {
    /**
     * Instantiation of Logger object that will be used to record successful connection
     * to the database.
     */
    private static final Logger log = Logger.getLogger(JDBConnection.class);

    /**
     * Method establishes a connection to the Postgres database
     * @return Connection
     */
    public static Connection getConnection() {
        Connection connected = null;
        Properties credentials = new Properties();

        try {

            // Windows URL: C:\Users\Alchemist\OneDrive\Desktop\project-0-Berkeley-gov\src\main\resources\application.properties
            // Mac URL: /Users/juanramirez/Desktop/project-0-Berkeley-gov/src/main/resources/application.properties
            credentials.load(new FileReader("/Users/juanramirez/Desktop/project-0-Berkeley-gov/src/main/resources/application.properties"));
            connected = DriverManager.getConnection(
                            credentials.getProperty("url"),
                            credentials.getProperty("username"),
                            credentials.getProperty("password"));

        } catch (FileNotFoundException e) {

            e.printStackTrace();
            log.error("\nError (File Not Found): The application did not connect to the Postgres database.");

        } catch (IOException e) {

            e.printStackTrace();
            log.error("\nError (Input / Output): The credentials to the Postgres database are incorrect.");

        } catch(SQLException e) {

            e.printStackTrace();
            log.error("\nError (SQL Exception): SQL query was not completed successfully.");
        }

        log.info("Successful: Connection to WORLD BANK database established.");
        return connected;
    }
}
