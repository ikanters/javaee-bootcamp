/**
 * 
 */
package nl.sogeti.jdc.demo.jee6.banking.boundary;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import nl.sogeti.jdc.demo.jee6.banking.control.AccountService;
import nl.sogeti.jdc.demo.jee6.banking.control.PersonService;
import nl.sogeti.jdc.demo.jee6.banking.entity.Person;
import nl.sogeti.jdc.demo.jee6.banking.exception.TransactionRollbackException;
import nl.sogeti.jdc.demo.jee6.banking.test.TestHelper;

import org.junit.Before;
import org.junit.Test;

/**
 * @author kanteriv
 * 
 */
public class BankingServiceTest {

   private static final Person TEST_PERSON = TestHelper.createPerson("testPerson");
   private final BankingService bankingService = new BankingService();

   @Before
   public void setUp() {
      this.bankingService.personService = mock(PersonService.class);
      this.bankingService.accountService = mock(AccountService.class);

   }

   @Test
   public void testCreatePersonOk() throws TransactionRollbackException {
      this.bankingService.createPerson(TEST_PERSON);
   }

   @Test(expected = TransactionRollbackException.class)
   public void testCreatePersonNOk() throws TransactionRollbackException {

      when(this.bankingService.personService.persist(TEST_PERSON)).thenThrow(new RuntimeException("duplicate key..."));
      this.bankingService.createPerson(TEST_PERSON);
   }

}
