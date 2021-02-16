package main.java.com.ideas2it.connection;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * @description: Class that provide session factory
 * @author     : Sivabhagya Jawahar
 */
public class HibernateUtil {

    public static SessionFactory sessionFactory;

    private HibernateUtil() {
    	
    }
    
    /**
     * @description : Method to Create the SessionFactory from hibernate.cfg.xml and get session factory
     * @author      : Sivabhagya Jawahar
     * @return      : sessionFactory
     */
    public static synchronized SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
        	try {
        		sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
        	} catch (Exception ex) {
                System.out.println("Initial SessionFactory creation failed." + ex.getMessage());
                ex.printStackTrace();
            } 
        }
        return sessionFactory;
    }
}