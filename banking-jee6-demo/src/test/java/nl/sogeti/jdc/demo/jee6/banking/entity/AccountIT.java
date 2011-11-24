/**
 * 
 */
package nl.sogeti.jdc.demo.jee6.banking.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author kanteriv
 */
public class AccountIT extends AbstractEntityIT {

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
      Person newPerson = createDefaultPerson(getNextUniqueClientId());
      Account account = createDefaultAccount(newPerson, "11111", new BigDecimal("500"));
      getEntityManager().persist(account.getOwner());
      getEntityManager().persist(account);

      assertNotNull(account.getId());
      assertNotNull(account.getOwner().getId());
      assertEquals(account, getEntityManager().find(Account.class, account.getId()));
   }

   private static Account createDefaultAccount(Person person, String accountNumber, BigDecimal creditLimit) {
      Account account = new Account();
      account.setOwner(person);
      account.setNumber(accountNumber);
      account.setCreditLimit(creditLimit);
      return account;
   }
}
