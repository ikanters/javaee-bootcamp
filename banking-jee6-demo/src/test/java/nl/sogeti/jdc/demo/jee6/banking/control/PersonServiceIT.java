/**
 * 
 */
package nl.sogeti.jdc.demo.jee6.banking.control;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import nl.sogeti.jdc.demo.jee6.banking.entity.Person;
import nl.sogeti.jdc.demo.jee6.banking.test.AbstractIT;
import nl.sogeti.jdc.demo.jee6.banking.test.TestHelper;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author kanteriv
 */
public class PersonServiceIT extends AbstractIT {

   Logger logger = LoggerFactory.getLogger(PersonServiceIT.class);

   private PersonService personService;

   private final static String BASECLIENTID = "PersonServiceIT_";

   private static int sequence = 1;

   @Before
   public void setUp() {
      this.personService = new PersonService();
      this.personService.entityManager = getEntityManager();
      getTransaction().begin();

   }

   @After
   public void teardown() {
      if (getTransaction().isActive()) {
         getTransaction().rollback();
      }

   }

   /**
    * Test method for {@link nl.sogeti.jdc.demo.jee6.banking.control.PersonService#findAll()}.
    */
   @Test
   public void testFindByClientId() {
      // find a person with a clientid that can not exist...
      Person person = this.personService.findByClientId("1");
      assertNull(person);

      String clientId = BASECLIENTID + nextSequence();
      this.personService.persist(TestHelper.createPerson(clientId));
      person = this.personService.findByClientId(clientId);
      assertNotNull(person);
      assertNotNull(person.getId());
      assertEquals(clientId, person.getClientId());

   }

   /**
    * Test method for {@link nl.sogeti.jdc.demo.jee6.banking.control.PersonService#count()}.
    */
   @Test
   public void testCountPersons() {
      List<Person> findAll = this.personService.findAll();
      int countPersons = this.personService.count();
      this.logger.info("# of persons: " + countPersons);
      assertEquals(findAll.size(), countPersons);

   }

   private int nextSequence() {
      return sequence++;
   }
}
