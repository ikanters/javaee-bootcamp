/**
 * 
 */
package nl.sogeti.jdc.demo.jee6.banking.test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.junit.Before;
import org.junit.BeforeClass;

/**
 * @author kanteriv
 * 
 */
public class AbstractIT {

   private static EntityManagerFactory emf;
   private EntityManager entityManager;
   private EntityTransaction transaction;

   public AbstractIT() {
      super();
   }

   @BeforeClass
   public static void oneTimeSetUp() throws Exception {
      emf = Persistence.createEntityManagerFactory("testPU");
   }

   @Before
   public final void transactionSetUp() throws Exception {
      this.entityManager = emf.createEntityManager();
      this.transaction = this.entityManager.getTransaction();
   }

   public EntityManager getEntityManager() {
      return this.entityManager;
   }

   public EntityTransaction getTransaction() {
      return this.transaction;
   }

}