/**
 * 
 */
package nl.sogeti.jdc.demo.jee6.banking.entity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.junit.Before;
import org.junit.BeforeClass;

/**
 * @author kanteriv
 */
public abstract class AbstractEntityIT
{
   private static EntityManagerFactory emf;
   private EntityManager entityManager;
   private EntityTransaction transaction;
   
   private static String usedClientId = null;
   private static int nextClientId = 10000;
   
   public AbstractEntityIT()
   {
      super();
   }
   
   public EntityManager getEntityManager()
   {
      return this.entityManager;
   }
   
   public EntityTransaction getTransaction()
   {
      return this.transaction;
   }
   
   @BeforeClass
   public static void oneTimeSetUp() throws Exception
   {
      emf = Persistence.createEntityManagerFactory("testPU");
   }
   
   @Before
   public void setUp() throws Exception
   {
      this.entityManager = emf.createEntityManager();
      this.transaction = this.entityManager.getTransaction();
   }
   
   protected Person insertUniquePerson()
   {
      Person person = createDefaultPerson(getNextUniqueClientId());
      getTransaction().begin();
      getEntityManager().persist(person);
      getTransaction().commit();
      usedClientId = person.getClientId();
      return person;
   }
   
   protected static String getUsedClientId()
   {
      return usedClientId;
   }
   
   protected static String getNextUniqueClientId()
   {
      return "" + nextClientId++;
   }
   
   protected static Person createDefaultPerson(String clientId)
   {
      return createPerson(clientId, "Ivar", "Kanters", "Carolina MacGillavrylaan 374", "Amsterdam");
   }
   
   protected static Person createPerson(String clientId, String firstname, String lastname)
   {
      return createPerson(clientId, firstname, lastname, null, null);
   }
   
   protected static Person createPerson(String clientId, String firstname, String lastname, String street, String city)
   {
      Person person = new Person(clientId, lastname, firstname);
      person.setStreet(street);
      person.setCity(city);
      return person;
   }
}
