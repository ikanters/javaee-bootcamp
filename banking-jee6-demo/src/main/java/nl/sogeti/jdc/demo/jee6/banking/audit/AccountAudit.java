/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.sogeti.jdc.demo.jee6.banking.audit;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

import nl.sogeti.jdc.demo.jee6.banking.constants.DebetCreditEnum;
import nl.sogeti.jdc.demo.jee6.banking.control.AccountLogService;
import nl.sogeti.jdc.demo.jee6.banking.entity.Account;
import nl.sogeti.jdc.demo.jee6.banking.entity.AccountLog;

import org.slf4j.Logger;

/**
 * @author kanteriv
 */
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class AccountAudit {

   @Inject
   Logger logger;

   @EJB
   AccountLogService accountLogService;

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

      List<AccountLog> logs = new ArrayList<AccountLog>();

      Object[] parameters = ic.getParameters();
      if (parametersValid(parameters)) {
         if (parameters.length == 2) {
            Account account = (Account) parameters[0];
            BigDecimal amount = getAmount(parameters);
            logs.add(newAccountLog(account, amount, ic.getMethod().getName().equals("deposit") ? DebetCreditEnum.DEBET : DebetCreditEnum.CREDIT));
         } else {
            Account fromAccount = (Account) parameters[0];
            Account toAccount = (Account) parameters[1];
            BigDecimal amount = getAmount(parameters);
            logs.add(newAccountLog(fromAccount, toAccount, amount, DebetCreditEnum.CREDIT));
            logs.add(newAccountLog(toAccount, fromAccount, amount, DebetCreditEnum.DEBET));
         }
      }
      Object result = ic.proceed();

      for (AccountLog accountLog : logs) {
         this.accountLogService.create(accountLog);
      }
      return result;
   }

   private AccountLog newAccountLog(Account account, BigDecimal amount, DebetCreditEnum aspect) {
      this.logger.info("new AccountLog(" + account.getNumber() + "," + amount + "," + aspect + ")");
      return new AccountLog(account, amount, aspect);
   }

   private AccountLog newAccountLog(Account account, Account other, BigDecimal amount, DebetCreditEnum aspect) {
      this.logger.info("new AccountLog(" + account.getNumber() + "," + other.getNumber() + "," + amount + "," + aspect + ")");
      return new AccountLog(account, other, amount, aspect);
   }

   /**
    * Pretty simple check if the parameters are valid to 'log'. We expect: - 3 parameters - first 2 are String - last is BigDecimal. The parameters are not
    * expected to be null... (all mandatory).
    * 
    * @param parameters
    *           the parameters to validate.
    * @return true if the parameters are valid, false otherwise.
    */
   private boolean parametersValid(Object[] parameters) {
      if (parameters == null) {
         return false;
      }
      if (parameters.length != 2 && parameters.length != 3) {
         return false;
      }

      int i = 0;
      for (Object parameter : parameters) {
         if (parameter == null) {
            return false;
         }
         if (i < (parameters.length - 1) && !(parameter instanceof Account)) {
            return false;
         }

         if (i == (parameters.length - 1) && !(parameter instanceof BigDecimal)) {
            return false;
         }
         i++;
      }

      return true;
   }

   private BigDecimal getAmount(Object[] parameters) {
      return (BigDecimal) parameters[parameters.length - 1];
   }

}
