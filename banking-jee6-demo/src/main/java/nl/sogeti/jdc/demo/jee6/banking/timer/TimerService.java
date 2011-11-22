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

/**
 * TODO Type description.
 * 
 * @version $Id:$
 * @author kanteriv (c) 20 sep. 2011, Sogeti Nederland B.V.
 */
@Singleton
public class TimerService {
   @Inject
   Logger logger;
   @EJB
   BankingServiceLocal bankingService;

   @Schedule(second = "0", minute = "*/10", hour = "*", persistent = false)
   public void logCountPersons() {
      this.logger.debug("# of registrated persons: " + this.bankingService.countPersons());
   }
}
