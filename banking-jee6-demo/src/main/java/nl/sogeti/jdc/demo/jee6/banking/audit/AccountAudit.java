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
import javax.interceptor.InvocationContext;

import nl.sogeti.jdc.demo.jee6.banking.constants.DebetCreditEnum;
import nl.sogeti.jdc.demo.jee6.banking.control.AccountLogService;
import nl.sogeti.jdc.demo.jee6.banking.entity.Account;
import nl.sogeti.jdc.demo.jee6.banking.entity.AccountLog;
import nl.sogeti.jdc.demo.jee6.banking.exception.InvalidAuditParameterException;

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
   public Object log(InvocationContext ic) throws Exception {

      this.logger.debug("#" + (ic.getMethod() == null ? null : ic.getMethod().getName()) + " is called!!!!");

      List<AccountLog> accountLogList = new ArrayList<AccountLog>();

      // Prepare the accountLogList
      addAccountLogsFromInvocationContext(accountLogList, ic);

      Object result = ic.proceed();

      // After successful proceed, save the accountLog entities to the database.
      for (AccountLog accountLog : accountLogList) {
         this.accountLogService.persist(accountLog);
      }
      return result;
   }

   private void addAccountLogsFromInvocationContext(List<AccountLog> accountLogList, InvocationContext ic) {
      try {
         Parameters parameters = new Parameters(ic.getParameters());

         if (parameters.toAccount == null) {
            DebetCreditEnum debCred = ic.getMethod().getName().equals("deposit") ? DebetCreditEnum.DEBET : DebetCreditEnum.CREDIT;
            AccountLog accountLog = newAccountLog(parameters.fromAccount, parameters.amount, debCred);
            accountLogList.add(accountLog);

         } else {
            accountLogList.add(newAccountLog(parameters.fromAccount, parameters.toAccount, parameters.amount, DebetCreditEnum.CREDIT));
            accountLogList.add(newAccountLog(parameters.toAccount, parameters.fromAccount, parameters.amount, DebetCreditEnum.DEBET));
         }
      } catch (InvalidAuditParameterException e) {
         this.logger.info("Unable to log the account change for the parameters in the method cannot parsed: " + e.getMessage());
         // continue with the normal method.
      }
   }

   private AccountLog newAccountLog(Account account, BigDecimal amount, DebetCreditEnum aspect) {
      this.logger.info("new AccountLog(" + account.getNumber() + "," + amount + "," + aspect + ")");
      return new AccountLog(account, amount, aspect);
   }

   private AccountLog newAccountLog(Account account, Account other, BigDecimal amount, DebetCreditEnum aspect) {
      this.logger.info("new AccountLog(" + account.getNumber() + "," + other.getNumber() + "," + amount + "," + aspect + ")");
      return new AccountLog(account, other, amount, aspect);
   }

   class Parameters {
      Account fromAccount;
      Account toAccount;
      BigDecimal amount;

      /**
       * Parses the object array into the expected parameters. Pretty simple check if the parameters are valid to 'log'. We expect: - 2 of 3 parameters
       * last is BigDecimal, the rest are Account
       * 
       * The parameters are not expected to be null... (all mandatory).
       * 
       * @param parameters
       *           the parameters to parse.
       */
      public Parameters(Object[] parameters) throws InvalidAuditParameterException {
         if (parameters == null) {
            throw new InvalidAuditParameterException("parameters cannot be empty.");
         }
         // 2 or 3 parameters
         if (parameters.length != 2 && parameters.length != 3) {
            throw new InvalidAuditParameterException("2 or 3 parameters expected, not " + parameters.length);
         }
         for (int i = 0; i < parameters.length - 1; i++) {
            if (parameters[i] == null) {
               throw new InvalidAuditParameterException("Parameter " + i + " should not be empty.");
            }
         }

         // The last is Bigdecimal;
         if (!(parameters[parameters.length - 1] instanceof BigDecimal)) {
            throw new InvalidAuditParameterException("Last parameters should be of type BigDecimal, not "
                  + (parameters[parameters.length - 1].getClass().getSimpleName()));
         }
         this.amount = (BigDecimal) parameters[parameters.length - 1];

         // all except the last is Account
         for (int i = 0; i < parameters.length - 1; i++) {
            if (!(parameters[i] instanceof Account)) {
               throw new InvalidAuditParameterException("Parameters " + i + " should be of type Account, not "
                     + (parameters[i].getClass().getSimpleName()));
            }
            if (i == 0) {
               this.fromAccount = (Account) parameters[i];
            } else {
               this.toAccount = (Account) parameters[i];
            }
         }
      }

   }
}
