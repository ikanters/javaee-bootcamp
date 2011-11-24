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

import nl.sogeti.jdc.demo.jee6.banking.control.AccountLogService;
import nl.sogeti.jdc.demo.jee6.banking.entity.Account;
import nl.sogeti.jdc.demo.jee6.banking.entity.AccountLog;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.LoggerFactory;

/**
 * @author kanteriv
 */
public class AccountAuditTest {
   private static final Account DUMMY_ACCOUNT1 = createAccount("1", "dummy1");
   private static final Account DUMMY_ACCOUNT2 = createAccount("2", "dummy2");

   private final AccountAudit accountAudit = new AccountAudit();
   private InvocationContext icMock;
   private AccountLogService accountLogServiceMock;

   @Before
   public void setUp() {
      this.icMock = mock(InvocationContext.class);
      this.accountLogServiceMock = mock(AccountLogService.class);
      this.accountAudit.accountLogService = this.accountLogServiceMock;
      this.accountAudit.logger = LoggerFactory.getLogger(AccountAuditTest.class);
   }

   @Test
   public void testAllNull() throws Exception {
      testInvocation(null, null, null);

      verify(this.accountLogServiceMock, never()).persist((AccountLog) anyObject());
   }

   @Test
   public void testWrongNumberofParameters() throws Exception {
      Method method = this.getClass().getDeclaredMethod("testWrongNumberofParameters");

      testInvocation(method, new Object[1], null);
      testInvocation(method, new Object[4], null);

      verify(this.accountLogServiceMock, never()).persist((AccountLog) anyObject());
   }

   @Test
   public void testEmptyParameters() throws Exception {
      Method method = this.getClass().getDeclaredMethod("testEmptyParameters");

      testInvocation(method, new Object[2], null);
      testInvocation(method, new Object[3], null);

      verify(this.accountLogServiceMock, never()).persist((AccountLog) anyObject());
   }

   @Test
   public void testWrongFilledParameters1() throws Exception {
      Method method = this.getClass().getDeclaredMethod("testWrongFilledParameters1");

      testInvocation(method, new Object[] { 1, 2, 3 }, null);

      verify(this.accountLogServiceMock, never()).persist((AccountLog) anyObject());
   }

   @Test
   public void testWrongFilledParameters2() throws Exception {
      Method method = this.getClass().getDeclaredMethod("testWrongFilledParameters2");

      testInvocation(method, new Object[] { "1", "2", 3 }, null);

      verify(this.accountLogServiceMock, never()).persist((AccountLog) anyObject());
   }

   @Test
   public void testWrongFilledParameters3() throws Exception {
      Method method = this.getClass().getDeclaredMethod("testWrongFilledParameters3");

      testInvocation(method, new Object[] { null, "2", BigDecimal.TEN }, null);

      verify(this.accountLogServiceMock, never()).persist((AccountLog) anyObject());
   }

   @Test
   public void testAccountLogging() throws Exception {
      Method method = this.getClass().getDeclaredMethod("testAccountLogging");

      Object[] parameters = new Object[] { DUMMY_ACCOUNT1, DUMMY_ACCOUNT2, BigDecimal.TEN };

      testInvocation(method, parameters, null);

      // this one is correct so a persist on the accountLogServiceMock is expected.(both accounts)
      verify(this.accountLogServiceMock, times(2)).persist((AccountLog) anyObject());
   }

   private void testInvocation(Method method, Object[] parameters, Object result) throws Exception {
      when(this.icMock.getMethod()).thenReturn(method);
      when(this.icMock.getParameters()).thenReturn(parameters);
      when(this.icMock.proceed()).thenReturn(result);

      assertEquals(result, this.accountAudit.log(this.icMock));
   }
}
