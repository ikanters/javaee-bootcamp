/**
 * 
 */
package nl.sogeti.jdc.demo.jee6.banking.test;

import nl.sogeti.jdc.demo.jee6.banking.entity.Account;
import nl.sogeti.jdc.demo.jee6.banking.entity.Person;

/**
 * @author kanteriv
 * 
 */
public class TestHelper {

   public static Account createAccount(String number, String owner) {
      return createAccount(number, owner, owner, owner);
   }

   public static Account createAccount(String number, String owner, String firstName, String lastName) {
      Account account = new Account(new Person(owner, firstName, lastName));
      account.setNumber(number);
      return account;
   }

   public static Person createPerson(String identification) {
      return new Person(identification, identification, identification);
   }

}
