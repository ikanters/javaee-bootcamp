/**
 * 
 */
package nl.sogeti.jdc.demo.jee6.banking.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import org.junit.Test;

/**
 * @author kanteriv
 * 
 */
public class AccountTest {

   @Test
   public void testSubstract() {
      Account account = new Account(new Person("99999", "kajsdfak", "asjkdh"));
      account.setCreditLimit(new BigDecimal("10"));
      assertTrue(account.substractAmount(new BigDecimal("7")));
      assertEquals(new BigDecimal("-7"), account.getBalance());
      assertFalse("Creditlimit reached", account.substractAmount(new BigDecimal("6")));
      assertEquals(new BigDecimal("-7"), account.getBalance());
   }
}
