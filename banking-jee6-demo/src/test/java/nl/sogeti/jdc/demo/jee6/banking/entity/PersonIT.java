/**
 * 
 */
package nl.sogeti.jdc.demo.jee6.banking.entity;

import static org.junit.Assert.assertNotNull;

import javax.persistence.PersistenceException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author kanteriv
 */
public class PersonIT extends AbstractEntityIT {

   @Before
   public void setUp() {
      getTransaction().begin();
   }

   @After
   public void teardown() {
      if (getTransaction().isActive()) {
         getTransaction().rollback();
      }
   }

   @Test
   public void testPersist() {
      Person person = insertUniquePerson();

      assertNotNull(person.getId());
      assertNotNull(getEntityManager().find(Person.class, person.getId()));
   }

   @Test(expected = PersistenceException.class)
   public void testPersistDuplKey() {
      insertUniquePerson();
      Person person = createPerson(getUsedClientId(), "Anders", "anders");
      getEntityManager().flush();
      getEntityManager().persist(person);
      getEntityManager().flush();
   }
}
