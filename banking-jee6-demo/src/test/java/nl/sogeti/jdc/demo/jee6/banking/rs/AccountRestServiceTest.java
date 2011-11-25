/**
 * 
 */
package nl.sogeti.jdc.demo.jee6.banking.rs;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import nl.sogeti.jdc.demo.jee6.banking.boundary.BankingServiceLocal;
import nl.sogeti.jdc.demo.jee6.banking.entity.Account;
import nl.sogeti.jdc.demo.jee6.banking.test.TestHelper;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.LoggerFactory;

/**
 * @author kanteriv
 * 
 */
public class AccountRestServiceTest {
   AccountRestService accountRestService = new AccountRestService();

   @Before
   public void setup() {
      this.accountRestService.logger = LoggerFactory.getLogger(AccountRestServiceTest.class);
      this.accountRestService.bankingService = mock(BankingServiceLocal.class);

   }

   @Test
   public void testGetBalance() {

      Account account = TestHelper.createAccount("112", "bla");
      when(this.accountRestService.bankingService.findAccountByNumber("112")).thenReturn(account);
      assertEquals("", this.accountRestService.getBalance("111"));
      assertEquals("0", this.accountRestService.getBalance("112"));
   }

}
