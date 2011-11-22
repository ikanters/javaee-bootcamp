/**
 * 
 */
package nl.sogeti.jdc.demo.jee6.banking.entity;

import static org.junit.Assert.assertNotNull;

import javax.persistence.PersistenceException;

import org.junit.Test;

/**
 * @author kanteriv
 */
public class PersonIT extends AbstractEntityTest
{
   @Test
   public void testPersist()
   {
      Person person = insertUniquePerson();
      
      assertNotNull(person.getId());
      assertNotNull(getEntityManager().find(Person.class, person.getId()));
   }
   
   @Test(expected = PersistenceException.class)
   public void testPersistDuplKey()
   {
      getTransaction().begin();
      Person person = createPerson(getUsedClientId(), "Anders", "anders");
      getEntityManager().persist(person);
      getTransaction().commit();
   }
}
