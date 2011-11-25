/**
 * 
 */
package nl.sogeti.jdc.demo.jee6.banking.exception;

import static org.junit.Assert.assertNull;

import org.junit.Test;

/**
 * @author kanteriv
 * 
 */
public class TransactionRollbackExceptionTest {

   @Test
   public void test() {
      TransactionRollbackException e = new TransactionRollbackException(null);

      assertNull(e.getMessage());
   }
}
