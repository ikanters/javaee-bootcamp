package nl.sogeti.jdc.demo.jee6.banking.audit;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.lang.reflect.Method;
import java.math.BigDecimal;

import javax.interceptor.InvocationContext;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.slf4j.LoggerFactory;

import nl.sogeti.jdc.demo.jee6.banking.control.TransferService;
import nl.sogeti.jdc.demo.jee6.banking.entity.Transfer;

/**
 * @author kanteriv 
 */
public class TransferAuditTest
{
   private final TransferAudit transferAudit = new TransferAudit();
   private InvocationContext icMock;
   private TransferService transferServiceMock;
   
   /**
    * @throws java.lang.Exception
    */
   @Before
   public void setUp() throws Exception
   {
      this.icMock = mock(InvocationContext.class);
      this.transferServiceMock = mock(TransferService.class);
      this.transferAudit.auditService = this.transferServiceMock;
      this.transferAudit.logger = LoggerFactory.getLogger(TransferAuditTest.class);
      
   }
   
   @Test
   public void testAllNull() throws Exception
   {
      testInvocation(null, null, null);
   }
   
   @Test
   public void testWrongNumberofParameters() throws Exception
   {
      String methodName = "testWrongNumberofParameters";
      Method method = this.getClass().getDeclaredMethod(methodName);
      Object[] parameters = new Object[2];
      testInvocation(method, parameters, methodName);
   }
   
   @Test
   public void testEmptyParameters() throws Exception
   {
      String methodName = "testEmptyParameters";
      Method method = this.getClass().getDeclaredMethod(methodName);
      Object[] parameters = new Object[3];
      testInvocation(method, parameters, methodName);
   }
   
   @Test
   public void testWrongFilledParameters1() throws Exception
   {
      String methodName = "testWrongFilledParameters1";
      Method method = this.getClass().getDeclaredMethod(methodName);
      Object[] parameters = new Object[] { 1, 2, 3 };
      testInvocation(method, parameters, methodName);
   }
   
   @Test
   public void testWrongFilledParameters2() throws Exception
   {
      String methodName = "testWrongFilledParameters2";
      Method method = this.getClass().getDeclaredMethod(methodName);
      Object[] parameters = new Object[] { "1", "2", 3 };
      testInvocation(method, parameters, methodName);
   }
   
   @Test
   public void testWrongFilledParameters3() throws Exception
   {
      String methodName = "testWrongFilledParameters3";
      Method method = this.getClass().getDeclaredMethod(methodName);
      Object[] parameters = new Object[] { null, "2", BigDecimal.TEN };
      testInvocation(method, parameters, methodName);
   }
   
   @Test
   public void testTransferLogging() throws Exception
   {
      String methodName = "testTransferLogging";
      Method method = this.getClass().getDeclaredMethod(methodName);
      Object[] parameters = new Object[] { "1", "2", BigDecimal.TEN };
      // this one is correct so a create on the transferservice is expected.
      this.transferServiceMock.persist((Transfer) Matchers.anyObject());
      testInvocation(method, parameters, methodName);
   }
   
   /**
    * @param method
    * @param parameters
    * @throws Exception
    */
   private void testInvocation(Method method, Object[] parameters, Object result) throws Exception
   {
      when(this.icMock.getMethod()).thenReturn(method);
      when(this.icMock.getParameters()).thenReturn(parameters);
      when(this.icMock.proceed()).thenReturn(result);
      assertEquals(result, this.transferAudit.log(this.icMock));
   }
}
