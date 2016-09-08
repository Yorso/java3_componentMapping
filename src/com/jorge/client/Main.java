package com.jorge.client;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.jorge.component.Address;
import com.jorge.entity.Person;
import com.jorge.util.HibernateUtil;

/**
 * Component Mapping (more classes than tables)
 * 
 * Aggregation
 * 		Indicates a relationship between the whole and its parts
 *  	If the whole is destroyed, its parts are not destroyed with it
 * 		Example: relationship between a music band and its artist
 * 			     If the music band is broken, artist are not broken (they could join some other bands)
 * 
 * Composition:
 * 		It is a strong form of aggregation
 * 		The difference is that if the whole is destroyed, all its parts are also destroyed with it
 * 		Example: a house (whole) has rooms (parts). If the house is destroyed, the rooms are destroyed too
 * 				 Also each part must belong to only one whole, NO SHARING (your bedroom only belongs to your house, it is not part of your neighbors' house)
 * 		
 * Component:
 * 		It is a part of a whole in such a way that if the whole is destroyed, all its parts are also destroyed with it. 
 * 	    It is a contained object that is persisted as a value type
 * 
 */
public class Main {

	public static void main(String[] args) {
		BasicConfigurator.configure(); // Necessary for configure log4j. It must be the first line in main method
								       // log4j.properties must be in /src directory
		
		Logger  logger = Logger.getLogger(Main.class.getName());
		logger.debug("log4j configured correctly and logger set");

		logger.debug("getting session factory form HibernateUtil.java");
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction txn = session.getTransaction();
		
		try {
			
			logger.debug("beginning transaction");
			txn.begin(); // Beginning transaction

			logger.debug("setting data in Person object");
			Address homeAddress = new Address("742 Evergreen Terrace", "Springfield", "80085");
			Address billingAddress = new Address("57 Walnut Street", "Springfield", "80085");
			Person person = new Person("Homer", homeAddress, billingAddress);
			
			logger.debug("saving data in DB");
			session.save(person);

			logger.debug("making commit of transactions");
			txn.commit(); // Making commit
			
		} catch (Exception e) {
			if (txn != null) {
				logger.error("something was wrong, making rollback of transactions");
				txn.rollback(); // If something was wrong, we make rollback
			}
			logger.error("Exception: " + e.getMessage().toString());
		} finally {
			if (session != null) {
				logger.debug("close session");
				session.close();
			}
		}
	}

}
