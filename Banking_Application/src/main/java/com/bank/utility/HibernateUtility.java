package com.bank.utility;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * HibernateUtility creates a hibernate helper objects that facilitates connection start up
 * and obtain session objects, allowing the for database persistence.
 */
public class HibernateUtility {
    private static Session session;
    private static final SessionFactory sessFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();

    /**
     * This method will return a session that will establish persistence with the database
     * It is to be called and used in the DAO layer to perform an action to the database
     * @return Session
     */
    public static Session getSession() {
        // Checking to see if we have a session and it is not null
        if(session == null) {
            session = sessFactory.openSession();
        }

        // Otherwise, return the established connection from the Session Factory
        return session;
    }

    /**
     * Useful method that close factory session resources to free up connection from the connections pool
     */
    public static void closeSession() {
        session.close();
        sessFactory.close();
    }
}
