/**
 * 
 */
package nl.sogeti.jdc.demo.jee6.banking.control;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.junit.Before;
import org.junit.Test;

import nl.sogeti.jdc.demo.jee6.banking.entity.Person;

/**
 * @author kanteriv
 */
public class PersonServiceTest
{
   private PersonService personService;
   
   /**
    * @throws java.lang.Exception
    */
   @Before
   public void setUp() throws Exception
   {
      this.personService = new PersonService();
      this.personService.entityManager = mock(EntityManager.class);
   }
   
   /**
    * Test method for {@link nl.sogeti.jdc.demo.jee6.banking.control.PersonService#findAll()}.
    */
   @Test
   public void testFindAll()
   {
      Query mockQuery = mock(Query.class);
      when(this.personService.entityManager.createNamedQuery(anyString())).thenReturn(mockQuery);
      when(mockQuery.getResultList()).thenReturn(new ArrayList<Person>());
      List<Person> all = this.personService.findAll();
      
      assertTrue(all.isEmpty());
   }
   
   /**
    * Test method for {@link nl.sogeti.jdc.demo.jee6.banking.control.PersonService#findAll()}.
    */
   @Test
   public void testFindByClientId()
   {
      Query mockQuery = mock(Query.class);
      when(this.personService.entityManager.createNamedQuery(anyString())).thenReturn(mockQuery);
      when(mockQuery.getResultList()).thenReturn(null);
      Person person = this.personService.findByClientId("12345");
      
      assertNull(person);
   }
   
   /**
    * Test method for {@link nl.sogeti.jdc.demo.jee6.banking.control.PersonService#count()}.
    */
   @Test
   public void testCountPersons()
   {
      Query mockQuery = mock(Query.class);
      when(this.personService.entityManager.createNamedQuery(anyString())).thenReturn(mockQuery);
      when(mockQuery.getSingleResult()).thenReturn(Integer.valueOf(99));
      int countPersons = this.personService.count();
      
      assertEquals(99, countPersons);
   }
}
