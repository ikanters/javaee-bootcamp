/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.sogeti.jdc.demo.jee6.banking.audit;

import java.math.BigDecimal;

import javax.ejb.EJB;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

import nl.sogeti.jdc.demo.jee6.banking.control.TransferService;
import nl.sogeti.jdc.demo.jee6.banking.entity.Account;
import nl.sogeti.jdc.demo.jee6.banking.entity.Transfer;

import org.slf4j.Logger;

/**
 * @author kanteriv
 */
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class TransferAudit {

   @Inject
   Logger logger;
   @EJB
   TransferService auditService;

   /**
    * Logs a transfer to the database (and logfile).
    * 
    * @param ic
    *           the invocationContext this is invoked on
    * @return The result of the proceeded invocationContext.
    * @throws Exception
    */
   @AroundInvoke
   public Object log(InvocationContext ic) throws Exception {
      this.logger.debug("#" + (ic.getMethod() == null ? null : ic.getMethod().getName()) + " is called!!!!");

      Transfer transfer = null;

      Object[] parameters = ic.getParameters();
      if (parametersValid(parameters)) {
         Account from = (Account) parameters[0];
         Account to = (Account) parameters[1];
         BigDecimal amount = (BigDecimal) parameters[2];

         this.logger.info("new Transfer(" + from.getNumber() + ", " + to.getNumber() + ", " + amount + ")");
         transfer = new Transfer(from.getNumber(), to.getNumber(), amount);
      }
      Object result = ic.proceed();

      if (transfer != null) {
         this.auditService.persist(transfer);
      }
      return result;
   }

   /**
    * Pretty simple check if the paramaters are valid to 'log'. We expect: - 3 parameters - first 2 are String - last is BigDecimal. Th parameters are not
    * expected to be null... (all mandatory).
    * 
    * @param parameters
    *           the parameters to validate.
    * @return true if the parameters are valid, false otherwise.
    */
   private boolean parametersValid(Object[] parameters) {
      if (parameters == null || parameters.length != 3) {
         return false;
      }

      int i = 0;
      for (Object parameter : parameters) {
         if (parameter == null) {
            return false;
         }
         if (i < 2 && !(parameter instanceof Account)) {
            return false;
         }

         if (i == 2 && !(parameter instanceof BigDecimal)) {
            return false;
         }
         i++;
      }

      return true;
   }
}
