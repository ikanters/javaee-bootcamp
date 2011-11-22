/**
 * 
 */
package nl.sogeti.jdc.demo.jee6.banking.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;

import org.junit.Test;

/**
 * @author kanteriv
 */
public class AccountIT extends AbstractEntityTest
{
   @Test
   public void testPersist()
   {
      Person newPerson = createDefaultPerson(getNextUniqueClientId());
      Account account = createDefaultAccount(newPerson, "1111", new BigDecimal("500"));
      getTransaction().begin();
      getEntityManager().persist(account.getOwner());
      getTransaction().commit();

      getTransaction().begin();
      getEntityManager().persist(account);
      getTransaction().commit();

      getTransaction().begin();
      assertNotNull(account.getId());
      assertNotNull(account.getOwner().getId());
      assertEquals(account, getEntityManager().find(Account.class, account.getId()));
      getTransaction().commit();
   }

   private static Account createDefaultAccount(Person person, String accountNumber, BigDecimal creditLimit)
   {
      Account account = new Account();
      account.setOwner(person);
      account.setNumber(accountNumber);
      account.setCreditLimit(creditLimit);
      return account;
   }
}
