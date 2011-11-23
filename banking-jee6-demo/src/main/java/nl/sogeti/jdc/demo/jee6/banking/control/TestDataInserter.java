/**
 * 
 */
package nl.sogeti.jdc.demo.jee6.banking.control;

import java.math.BigDecimal;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

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

   @EJB
   PersonService personService;
   @EJB
   AccountService accountService;

   @PostConstruct
   public void insertTestData() {
      for (int i = 0; i < 5; i++) {

         String clientId = "" + (90000 + i);

         Person person = createPersonIfNotExists(clientId, i);

         for (int j = 0; j < 3; j++) {
            String accountNumber = "800.00" + i + ".00" + j;
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

         person = new Person(clientId, "Testperson 0" + (i + 1), "__" + (char) ('A' + i));
         this.personService.persist(person);
      }
      return person;
   }

}
