/**
 * 
 */
package nl.sogeti.jdc.demo.jee6.banking.controller;

import javax.annotation.PostConstruct;
import javax.ejb.Stateful;
import javax.inject.Inject;

import org.slf4j.Logger;

import nl.sogeti.jdc.demo.jee6.banking.entity.Person;

/**
 * @author kanteriv
 */
@Stateful
public class UserData
{
   @Inject
   Logger logger;
   
   private Person selectedPerson;
   
   public UserData()
   {
      super();
   }
   
   @PostConstruct
   public void init()
   {
      this.logger.debug("UserData:init()");
   }
   
   /**
    * @return the selectedPerson
    */
   public Person getSelectedPerson()
   {
      return this.selectedPerson;
   }
   
   /**
    * @param selectedPerson the selectedPerson to set
    */
   public void setSelectedPerson(Person selectedPerson)
   {
      this.logger.debug("setSelectedPerson(" + selectedPerson + ")");
      this.selectedPerson = selectedPerson;
   }
   
}
