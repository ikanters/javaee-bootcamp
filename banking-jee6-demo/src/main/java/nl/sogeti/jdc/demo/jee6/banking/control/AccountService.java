/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.sogeti.jdc.demo.jee6.banking.control;

import java.math.BigDecimal;
import java.util.List;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.persistence.Query;

import nl.sogeti.jdc.demo.jee6.banking.audit.TransferAudit;
import nl.sogeti.jdc.demo.jee6.banking.entity.Account;
import nl.sogeti.jdc.demo.jee6.banking.entity.Person;
import nl.sogeti.jdc.demo.jee6.banking.monitor.WatchDog;
import nl.sogeti.jdc.demo.jee6.banking.stereotype.ControlService;

import org.slf4j.Logger;

/**
 * @author kanteriv
 */
@ControlService
@Stateless
public class AccountService extends AbstractCrudService<Account> {
   @Inject
   Logger logger;

   @Inject
   Event<WatchDog> monitoring;

   @Override
   protected Class<Account> getEntityClass() {
      return Account.class;
   }

   @Interceptors({ TransferAudit.class })
   public boolean transfer(Account from, Account to, BigDecimal amount) {
      if (from != null && to != null && amount != null) {
         if (!from.substractAmount(amount)) {
            createWatchdog(from, amount);
            return false;
         }
         to.addAmount(amount);
         return true;
      }
      return false;
   }

   public void deposit(Account account, BigDecimal amount) {
      this.logger.debug("deposit(" + account.getNumber() + ", " + amount + ")");

      account.addAmount(amount);
   }

   public boolean withdraw(Account account, BigDecimal amount) {

      if (account.substractAmount(amount)) {
         return true;
      }

      // Substract failed... Create a watchdog
      WatchDog watchdog = createWatchdog(account, amount);
      // Fire the watchdog to all its observers.

      return false;
   }

   private WatchDog createWatchdog(Account account, BigDecimal amount) {
      WatchDog watchdog = new WatchDog("account", account.getNumber());
      watchdog.and("clientid", account.getOwner().getClientId());
      watchdog.and("balance", account.getBalance());
      watchdog.and("creditlimit", account.getCreditLimit());
      watchdog.and("substract", amount);
      return watchdog;

   }

   @SuppressWarnings("unchecked")
   public List<Account> findAccountsFor(Person selectedPerson) {
      final Query query = createNamedQuery(Account.FIND_ACCOUNTS_FOR_PERSON_NAMEDQUERY);
      query.setParameter(Account.OWNER_PARAM, selectedPerson);
      return query.getResultList();
   }

   @SuppressWarnings("unchecked")
   public Account findAccount(String accountNumber) {
      final Query query = createNamedQuery(Account.FIND_ACCOUNT_BY_NUMBER_NAMEDQUERY);
      query.setParameter(Account.NUMBER_PARAM, accountNumber);
      List<Account> resultList = query.getResultList();
      if (resultList.size() == 1) {
         return resultList.get(0);
      }
      return null;
   }
}