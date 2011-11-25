/**
 * 
 */
package nl.sogeti.jdc.demo.jee6.banking.boundary;

import java.math.BigDecimal;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import nl.sogeti.jdc.demo.jee6.banking.control.AccountService;
import nl.sogeti.jdc.demo.jee6.banking.control.PersonService;
import nl.sogeti.jdc.demo.jee6.banking.entity.Account;
import nl.sogeti.jdc.demo.jee6.banking.entity.Person;

/**
 * @author kanteriv
 * 
 */
@Singleton
@Startup
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class TestDataInserter {

   static final int NUMBER_OF_CLIENTS = 20;
   static final int NUMBER_OF_ACCOUNTS_PER_CLIENTS = 3;
   @EJB
   PersonService personService;
   @EJB
   AccountService accountService;

   @PostConstruct
   public void insertTestData() {
      for (int i = 0; i < NUMBER_OF_CLIENTS; i++) {

         String clientId = "123." + (100 + i);
         Person person = createPersonIfNotExists(clientId, i);

         for (int j = 0; j < NUMBER_OF_ACCOUNTS_PER_CLIENTS; j++) {
            String accountNumber = "800.000" + i + "." + (100 + j);
            createAccountIfNotExists(person, clientId, accountNumber);
         }
      }
   }

   /**
    * @param person
    * @param clientId
    * @param accountNumber
    */
   private void createAccountIfNotExists(Person person, String clientId, String accountNumber) {

      Account account = this.accountService.findAccount(accountNumber);

      if (account == null) {
         account = new Account(person);
         account.setCreditLimit(new BigDecimal("1000"));
         account.setNumber(accountNumber);
         this.accountService.persist(account);
      }
   }

   /**
    * @param clientId
    * @param i
    * @return
    */
   private Person createPersonIfNotExists(String clientId, int i) {

      Person person = this.personService.findByClientId(clientId);
      if (person == null) {

         person = new Person(clientId, "Testperson " + ("" + (101 + i)).substring(1), "__" + (char) ('A' + i));
         this.personService.persist(person);
      }
      return person;
   }

}
