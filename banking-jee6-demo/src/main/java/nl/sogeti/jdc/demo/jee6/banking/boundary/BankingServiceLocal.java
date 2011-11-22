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

import nl.sogeti.jdc.demo.jee6.banking.entity.Account;
import nl.sogeti.jdc.demo.jee6.banking.entity.Person;

/**
 * TODO Type description.
 * 
 * @version $Id:$
 * @author kanteriv (c) 3 nov. 2011, Sogeti Nederland B.V.
 */
public interface BankingServiceLocal extends BankingServiceRemote
{
   List<Person> findAllPersons();
   
   int countPersons();
   
   Person createPerson(Person person);
   
   Person updatePerson(Person person);
   
   Account findAccountByNumber(String accountNumber);
   
   boolean transfer(String accountNumber, String toAccountNumber, BigDecimal amount);
   
   List<Account> findAccountsFor(Person owner);
   
   Account createAccount(Account selected);
   
   Account updateAccount(Account selected);
   
   void deposit(Account account, BigDecimal amount);
   
   boolean withdraw(Account account, BigDecimal amount);
   
}
