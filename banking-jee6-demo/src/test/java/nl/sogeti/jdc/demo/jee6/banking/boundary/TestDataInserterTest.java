/**
 * 
 */
package nl.sogeti.jdc.demo.jee6.banking.boundary;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import nl.sogeti.jdc.demo.jee6.banking.boundary.TestDataInserter;
import nl.sogeti.jdc.demo.jee6.banking.control.AccountService;
import nl.sogeti.jdc.demo.jee6.banking.control.PersonService;
import nl.sogeti.jdc.demo.jee6.banking.entity.Account;
import nl.sogeti.jdc.demo.jee6.banking.entity.Person;

import org.junit.Before;
import org.junit.Test;

/**
 * @author kanteriv
 */
public class TestDataInserterTest {
   private TestDataInserter testDataInserter;

   /**
    * @throws java.lang.Exception
    */
   @Before
   public void setUp() throws Exception {
      this.testDataInserter = new TestDataInserter();
      this.testDataInserter.personService = mock(PersonService.class);
      this.testDataInserter.accountService = mock(AccountService.class);
   }

   /**
    * Test method for {@link nl.sogeti.jdc.demo.jee6.banking.control.PersonService#findAll()}.
    */
   @Test
   public void testPostConstructEmpty() {
      when(this.testDataInserter.personService.findByClientId(anyString())).thenReturn(null);
      when(this.testDataInserter.personService.persist((Person) anyObject())).thenReturn(null);
      when(this.testDataInserter.accountService.findAccount(anyString())).thenReturn(null);
      when(this.testDataInserter.accountService.persist((Account) anyObject())).thenReturn(null);

      this.testDataInserter.insertTestData();
      verify(this.testDataInserter.personService, times(5)).findByClientId(anyString());
      verify(this.testDataInserter.personService, times(5)).persist((Person) anyObject());
      verify(this.testDataInserter.accountService, times(5 * 3)).findAccount(anyString());
      verify(this.testDataInserter.accountService, times(5 * 3)).persist((Account) anyObject());
   }
}
