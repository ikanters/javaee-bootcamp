/**
 * 
 */
package nl.sogeti.jdc.demo.jee6.banking.control;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import nl.sogeti.jdc.demo.jee6.banking.entity.Account;
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
public class AccountServiceIT extends AbstractIT {

   Logger logger = LoggerFactory.getLogger(AccountServiceIT.class);

   private AccountService accountService;
   private PersonService personService;

   private final static String BASECLIENTID = "AccountServiceIT_";
   private final static String BASEACCOUNTNR = "AccountServiceIT_";

   private static int sequence = 1;

   @Before
   public void setUp() {
      this.accountService = new AccountService();
      this.accountService.entityManager = getEntityManager();
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
      Account account = this.accountService.findAccount("1");
      assertNull(account);

      String clientId = BASECLIENTID + nextSequence();
      String accountNumber = BASEACCOUNTNR + nextSequence();

      Person person = this.personService.persist(TestHelper.createPerson(clientId));
      account = this.accountService.persist(TestHelper.createAccount(accountNumber, person));

      assertNotNull(account);
      assertNotNull(account.getId());
      assertEquals(accountNumber, account.getNumber());
      assertEquals(clientId, account.getOwner().getClientId());

   }

   private int nextSequence() {
      return sequence++;
   }
}
