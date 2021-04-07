package com.bank.dao;

import com.bank.models.Role;
import com.bank.utility.HibernateUtility;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class RoleDao {
    private static final Logger log = Logger.getLogger(RoleDao.class);

    public void insert(Role userRole) {
        if(userRole == null) {
            log.warn("WARNING: Unable to insert Role to the database because Role object is null.");
        } else {
            Session session = HibernateUtility.getSession();
            Transaction trans = session.beginTransaction();

            log.info("SUCCESS: Role was successfully inserted to the database. User: " + userRole);
            session.save(userRole);
            trans.commit();
        }
    }

    public void updateRole(Role userRole) {
        if(userRole == null) {
            log.warn("WARNING: Unable to update the Role to the database because Role object is null.");
        } else {
            Session session = HibernateUtility.getSession();
            Transaction trans = session.beginTransaction();

            log.info("SUCCESS: Role was successfully updated to the database. User: " + userRole);
            session.update(userRole);
            trans.commit();
        }
    }
}
