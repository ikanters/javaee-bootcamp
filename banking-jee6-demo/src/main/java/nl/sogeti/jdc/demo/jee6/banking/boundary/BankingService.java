/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.sogeti.jdc.demo.jee6.banking.boundary;

import java.math.BigDecimal;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import nl.sogeti.jdc.demo.jee6.banking.control.AccountService;
import nl.sogeti.jdc.demo.jee6.banking.control.PersonService;
import nl.sogeti.jdc.demo.jee6.banking.entity.Account;
import nl.sogeti.jdc.demo.jee6.banking.entity.Person;
import nl.sogeti.jdc.demo.jee6.banking.exception.TransactionRollbackException;

/**
 * @author kanteriv
 */
@ServiceFacade
@Stateless
@Remote(BankingServiceRemote.class)
@Local(BankingServiceLocal.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class BankingService implements BankingServiceLocal {
   @EJB
   PersonService personService;
   @EJB
   AccountService accountService;

   /**
    * Constructor: create a new BankingService.
    */
   public BankingService() {
      super();
   }

   @Override
   public List<Person> findAllPersons() {
      return this.personService.findAll();
   }

   @Override
   public int countPersons() {
      return this.personService.count();
   }

   @Override
   public Person findPersonByClientId(String clientId) {
      return this.personService.findByClientId(clientId);
   }

   @Override
   public Person createPerson(Person person) throws TransactionRollbackException {
      // Make sure the id is not pre-set!
      person.setId(null);
      try {
         Person saved = this.personService.persist(person);
         this.personService.flush();
         return saved;
      } catch (RuntimeException e) {
         // In case the Local interface is used the object is refered to as a refference. During the persist the id is set,
         // and during the flush the exception is thrown. Therefore the id must be reset to null.
         person.setId(null);
         throw new TransactionRollbackException(e);
      }
   }

   @Override
   public Person updatePerson(Person person) throws TransactionRollbackException {
      // Set the id by searching the correct entity based on the logical key.
      try {
         String clientId = person.getClientId();
         person.setId(this.personService.findByClientId(clientId).getId());
         Person saved = this.personService.merge(person);
         this.personService.flush();
         return saved;
      } catch (RuntimeException e) {
         throw new TransactionRollbackException(e);
      }
   }

   @Override
   public Account findAccountByNumber(String accountNumber) {
      return this.accountService.findAccount(accountNumber);
   }

   @Override
   public boolean transfer(String accountNumber, String toAccountNumber, BigDecimal amount) {
      final Account attachedFrom = this.accountService.findAccount(accountNumber);
      final Account attachedTo = this.accountService.findAccount(toAccountNumber);
      return this.accountService.transfer(attachedFrom, attachedTo, amount);
   }

   @Override
   public List<Account> findAccountsFor(Person owner) {
      return this.accountService.findAccountsFor(owner);
   }

   @Override
   public void deposit(Account account, BigDecimal amount) {
      final Account attachedAccount = this.accountService.findAccount(account.getNumber());
      this.accountService.deposit(attachedAccount, amount);
   }

   @Override
   public boolean withdraw(Account account, BigDecimal amount) {
      final Account attachedAccount = this.accountService.findAccount(account.getNumber());
      return this.accountService.withdraw(attachedAccount, amount);
   }

   @Override
   public Account createAccount(Account account) {
      // Make sure the id is not pre-set!
      account.setId(null);
      return this.accountService.persist(account);
   }

   @Override
   public Account updateAccount(Account account) {
      return this.accountService.merge(account);
   }
}
