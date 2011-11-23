/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.sogeti.jdc.demo.jee6.banking.control;

import java.math.BigDecimal;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.persistence.Query;

import org.slf4j.Logger;

import nl.sogeti.jdc.demo.jee6.banking.audit.AccountAudit;
import nl.sogeti.jdc.demo.jee6.banking.audit.TransferAudit;
import nl.sogeti.jdc.demo.jee6.banking.entity.Account;
import nl.sogeti.jdc.demo.jee6.banking.entity.Person;

/**
 * @author kanteriv
 */
@ControlService
@Stateless
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class AccountService extends AbstractCrudService<Account> {
   @Inject
   Logger logger;

   @Override
   protected Class<Account> getEntityClass() {
      return Account.class;
   }

   @Interceptors({ TransferAudit.class, AccountAudit.class })
   public boolean transfer(Account from, Account to, BigDecimal amount) {
      if (from != null && to != null && amount != null) {
         from.substractAmount(amount);
         to.addAmount(amount);
         return true;
      }
      return false;
   }

   @Interceptors(AccountAudit.class)
   public void deposit(Account account, BigDecimal amount) {
      this.logger.debug("deposit(" + account.getNumber() + ", " + amount + ")");

      account.addAmount(amount);
      merge(account);
   }

   @Interceptors(AccountAudit.class)
   public boolean withdraw(Account account, BigDecimal amount) {
      if (account.substractAmount(amount)) {
         merge(account);
         return true;
      }
      return false;
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