/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.sogeti.jdc.demo.jee6.banking.timer;

import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.inject.Inject;

import nl.sogeti.jdc.demo.jee6.banking.boundary.BankingServiceLocal;

import org.slf4j.Logger;

// Make it a session bean
@Singleton
public class TimerService {

   // add logger
   @Inject
   Logger logger;
   // add BankingService
   @EJB
   BankingServiceLocal bankingService;

   // Add the schedule
   @Schedule(second = "0", minute = "*", hour = "*", persistent = false)
   public void logCountPersons() {
      // call the count and log it
      this.logger.info("# of persons: " + this.bankingService.countPersons());
   }
}
