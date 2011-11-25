package nl.sogeti.jdc.demo.jee6.banking.audit;

import static nl.sogeti.jdc.demo.jee6.banking.test.TestHelper.createAccount;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.lang.reflect.Method;
import java.math.BigDecimal;

import javax.interceptor.InvocationContext;

import nl.sogeti.jdc.demo.jee6.banking.control.TransferService;
import nl.sogeti.jdc.demo.jee6.banking.entity.Account;
import nl.sogeti.jdc.demo.jee6.banking.entity.Transfer;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.LoggerFactory;

/**
 * @author kanteriv
 */
public class TransferAuditTest {
   private static final Account DUMMY_ACCOUNT1 = createAccount("1", "dummy1");
   private static final Account DUMMY_ACCOUNT2 = createAccount("2", "dummy2");

   private final TransferAudit transferAudit = new TransferAudit();
   private InvocationContext icMock;
   private TransferService transferServiceMock;

   /**
    * @throws java.lang.Exception
    */
   @Before
   public void setUp() throws Exception {
      this.icMock = mock(InvocationContext.class);
      this.transferServiceMock = mock(TransferService.class);
      this.transferAudit.transferService = this.transferServiceMock;
      this.transferAudit.logger = LoggerFactory.getLogger(TransferAuditTest.class);

   }

   @Test
   public void testAllNull() throws Exception {
      testInvocation(null, null, null);

      verify(this.transferServiceMock, never()).persist((Transfer) anyObject());
   }

   @Test
   public void testWrongNumberofParameters() throws Exception {
      Method method = this.getClass().getDeclaredMethod("testWrongNumberofParameters");

      testInvocation(method, new Object[2], null);

      verify(this.transferServiceMock, never()).persist((Transfer) anyObject());
   }

   @Test
   public void testEmptyParameters() throws Exception {
      Method method = this.getClass().getDeclaredMethod("testEmptyParameters");

      testInvocation(method, new Object[3], null);

      verify(this.transferServiceMock, never()).persist((Transfer) anyObject());
   }

   @Test
   public void testWrongFilledParameters() throws Exception {
      Method method = this.getClass().getDeclaredMethod("testWrongFilledParameters");

      testInvocation(method, new Object[] { DUMMY_ACCOUNT1, 2, 3 }, null);
      testInvocation(method, new Object[] { DUMMY_ACCOUNT1, DUMMY_ACCOUNT2, 3 }, null);
      testInvocation(method, new Object[] { 1, DUMMY_ACCOUNT2, BigDecimal.TEN }, null);

      verify(this.transferServiceMock, never()).persist((Transfer) anyObject());
   }

   @Test
   public void testTransferLogging() throws Exception {
      Method method = this.getClass().getDeclaredMethod("testTransferLogging");

      testInvocation(method, new Object[] { DUMMY_ACCOUNT1, DUMMY_ACCOUNT2, BigDecimal.TEN }, null);

      // this one is correct so a create on the transferservice is expected.
      verify(this.transferServiceMock, times(1)).persist((Transfer) anyObject());
   }

   private void testInvocation(Method method, Object[] parameters, Object result) throws Exception {
      when(this.icMock.getMethod()).thenReturn(method);
      when(this.icMock.getParameters()).thenReturn(parameters);
      when(this.icMock.proceed()).thenReturn(result);
      assertEquals(result, this.transferAudit.log(this.icMock));
   }
}
