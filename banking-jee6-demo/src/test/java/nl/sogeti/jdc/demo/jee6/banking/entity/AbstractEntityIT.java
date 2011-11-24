/**
 * 
 */
package nl.sogeti.jdc.demo.jee6.banking.entity;

import nl.sogeti.jdc.demo.jee6.banking.test.AbstractIT;



/**
 * @author kanteriv
 */
public abstract class AbstractEntityIT extends AbstractIT
{
   private static String usedClientId = null;
   private static int nextClientId = 10000;
   
   public AbstractEntityIT()
   {
      super();
   }
   
   protected Person insertUniquePerson()
   {
      Person person = createDefaultPerson(getNextUniqueClientId());
      getTransaction().begin();
      getEntityManager().persist(person);
      getTransaction().commit();
      usedClientId = person.getClientId();
      return person;
   }
   
   protected static String getUsedClientId()
   {
      return usedClientId;
   }
   
   protected static String getNextUniqueClientId()
   {
      return "" + nextClientId++;
   }
   
   protected static Person createDefaultPerson(String clientId)
   {
      return createPerson(clientId, "Ivar", "Kanters", "Carolina MacGillavrylaan 374", "Amsterdam");
   }
   
   protected static Person createPerson(String clientId, String firstname, String lastname)
   {
      return createPerson(clientId, firstname, lastname, null, null);
   }
   
   protected static Person createPerson(String clientId, String firstname, String lastname, String street, String city)
   {
      Person person = new Person(clientId, lastname, firstname);
      person.setStreet(street);
      person.setCity(city);
      return person;
   }
}
