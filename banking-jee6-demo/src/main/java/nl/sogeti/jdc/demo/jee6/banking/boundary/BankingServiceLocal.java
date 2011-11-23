/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.sogeti.jdc.demo.jee6.banking.boundary;

import java.math.BigDecimal;
import java.util.List;

import nl.sogeti.jdc.demo.jee6.banking.entity.Account;
import nl.sogeti.jdc.demo.jee6.banking.entity.Person;
import nl.sogeti.jdc.demo.jee6.banking.exception.TransactionRollbackException;

/**
 * @author kanteriv
 */
public interface BankingServiceLocal extends BankingServiceRemote {

   List<Person> findAllPersons();

   int countPersons();

   Person createPerson(Person person) throws TransactionRollbackException;

   Person updatePerson(Person person);

   Account findAccountByNumber(String accountNumber);

   boolean transfer(String accountNumber, String toAccountNumber, BigDecimal amount);

   List<Account> findAccountsFor(Person owner);

   Account createAccount(Account selected);

   Account updateAccount(Account selected);

   void deposit(Account account, BigDecimal amount);

   boolean withdraw(Account account, BigDecimal amount);

}
