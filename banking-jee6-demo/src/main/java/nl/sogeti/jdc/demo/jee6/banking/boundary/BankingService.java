/*------------------------------------------------------------------------------
 **     Ident: Delivery Center Java
 **    Author: kanteriv
 ** Copyright: (c) 3 nov. 2011 Sogeti Nederland B.V. All Rights Reserved.
 **------------------------------------------------------------------------------
 ** Sogeti Nederland B.V.            |  No part of this file may be reproduced  
 ** Distributed Software Engineering |  or transmitted in any form or by any        
 ** Lange Dreef 17                   |  means, electronic or mechanical, for the      
 ** 4131 NJ Vianen                   |  purpose, without the express written    
 ** The Netherlands                  |  permission of the copyright holder.
 *------------------------------------------------------------------------------
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

/**
 * TODO Type description.
 * 
 * @version $Id:$
 * @author kanteriv (c) 3 nov. 2011, Sogeti Nederland B.V.
 */
@ServiceFacade
@Stateless
@Remote(BankingServiceRemote.class)
@Local(BankingServiceLocal.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class BankingService implements BankingServiceLocal
{
   @EJB
   PersonService personService;
   @EJB
   AccountService accountService;
   
   /**
    * Constructor: create a new BankingService.
    */
   public BankingService()
   {
      super();
   }
   
   @Override
   public List<Person> findAllPersons()
   {
      return this.personService.findAll();
   }
   
   @Override
   public int countPersons()
   {
      return this.personService.count();
   }
   
   @Override
   public Person findPersonByClientId(String clientId)
   {
      return this.personService.findByClientId(clientId);
   }
   
   @Override
   public Person createPerson(Person person)
   {
      // Make sure the id is not pre-set!
      person.setId(null);
      return this.personService.create(person);
   }
   
   @Override
   public Person updatePerson(Person person)
   {
      // Set the id by searching the correct entity based on the logical key. 
      person.setId(this.personService.findByClientId(person.getClientId()).getId());
      return this.personService.update(person);
   }
   
   @Override
   public Account findAccountByNumber(String accountNumber)
   {
      return this.accountService.findAccount(accountNumber);
   }
   
   @Override
   public boolean transfer(String accountNumber, String toAccountNumber, BigDecimal amount)
   {
      final Account from = this.accountService.findAccount(accountNumber);
      final Account to = this.accountService.findAccount(toAccountNumber);
      return this.accountService.transfer(from, to, amount);
   }
   
   @Override
   public List<Account> findAccountsFor(Person owner)
   {
      return this.accountService.findAccountsFor(owner);
   }
   
   @Override
   public void deposit(Account account, BigDecimal amount)
   {
      this.accountService.deposit(account, amount);
   }
   
   @Override
   public boolean withdraw(Account account, BigDecimal amount)
   {
      return this.accountService.withdraw(account, amount);
   }
   
   @Override
   public Account createAccount(Account account)
   {
      // Make sure the id is not pre-set!
      account.setId(null);
      return this.accountService.create(account);
   }
   
   @Override
   public Account updateAccount(Account account)
   {
      return this.accountService.update(account);
   }
}