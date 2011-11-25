/**
 * 
 */
package nl.sogeti.jdc.demo.jee6.banking.exception;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.sql.SQLIntegrityConstraintViolationException;

import org.junit.Test;

/**
 * @author kanteriv
 * 
 */
public class TransactionRollbackExceptionTest {

   @Test
   public void test() {
      String testMessage = "message";
      String sqlTestMessage = "sql-testmessage";

      TransactionRollbackException e = new TransactionRollbackException(null);
      assertNull(e.getMessage());

      e = new TransactionRollbackException(new RuntimeException(testMessage));
      assertEquals(testMessage, e.getMessage());

      e = new TransactionRollbackException(new RuntimeException(new RuntimeException(testMessage)));
      assertEquals(testMessage, e.getMessage());

      e = new TransactionRollbackException(new RuntimeException("other", new RuntimeException(testMessage)));
      assertEquals(testMessage, e.getMessage());

      e = new TransactionRollbackException(new SQLIntegrityConstraintViolationException(sqlTestMessage, new RuntimeException(testMessage)));
      assertEquals(TransactionRollbackException.DUPLICATE_KEY_DETECTED, e.getMessage());
   }
}
