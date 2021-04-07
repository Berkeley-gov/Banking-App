package com.bank.dao;

import com.bank.models.User;
import com.bank.utility.HibernateUtility;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
//TODO: Finish adding loggers to all UserDao methods
public class UserDao {
    private static final Logger log = Logger.getLogger(UserDao.class);

    public void insert(User user) {
        if(user == null) {
            log.warn("WARNING: User was not inserted to the database because the user object is null.");
        } else {
            Session session = HibernateUtility.getSession();
            Transaction trans = session.beginTransaction();

            log.info("SUCCESS: User was successfully inserted to the database. User: " + user);
            session.save(user);
            trans.commit();
        }
    }


    public void updateUser(User user) {
        Session session = HibernateUtility.getSession();
        Transaction trans = session.beginTransaction();

        session.update(user);
        trans.commit();
    }

    public User selectById(int id) {
        Session session = HibernateUtility.getSession();

        List<User> userList = session.createQuery("from User where id='"+ id +"'", User.class).list();

        if(userList.size() == 0) {
            System.out.println("Panic -- No Villains found");
            return null;
        } else {
            return userList.get(0);
        }
    }

    public User selectById(String username) {
        Session session = HibernateUtility.getSession();

        List<User> userList = session.createQuery("from User where username='"+ username +"'", User.class).list();

        if(userList.size() == 0) {
            System.out.println("Panic -- No Villains found");
            return null;
        } else {
            return userList.get(0);
        }
    }

    public List<User> selectAll() {
        Session session = HibernateUtility.getSession();

        List<User> userList = session.createQuery("from User", User.class).list();

        return userList;
    }

    public void deleteUser(User user) {
        Session session = HibernateUtility.getSession();
        Transaction trans = session.beginTransaction();

        session.delete(user);
        trans.commit();
    }
}
