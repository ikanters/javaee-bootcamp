/*------------------------------------------------------------------------------
 **     Ident: Delivery Center Java
 **    Author: kanteriv
 ** Copyright: (c) 20 sep. 2011 Sogeti Nederland B.V. All Rights Reserved.
 **------------------------------------------------------------------------------
 ** Sogeti Nederland B.V.            |  No part of this file may be reproduced  
 ** Distributed Software Engineering |  or transmitted in any form or by any        
 ** Lange Dreef 17                   |  means, electronic or mechanical, for the      
 ** 4131 NJ Vianen                   |  purpose, without the express written    
 ** The Netherlands                  |  permission of the copyright holder.
 *------------------------------------------------------------------------------
 */
package nl.sogeti.jdc.demo.jee6.banking.timer;

import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.inject.Inject;

import org.slf4j.Logger;

import nl.sogeti.jdc.demo.jee6.banking.boundary.BankingServiceLocal;

/**
 * TODO Type description.
 * 
 * @version $Id:$
 * @author kanteriv (c) 20 sep. 2011, Sogeti Nederland B.V.
 */
@Singleton
public class TimerService
{
   @Inject
   Logger logger;
   @EJB
   BankingServiceLocal bankingService;
   
   @Schedule(second = "0", minute = "*/10", hour = "*", persistent = false)
   public void logCountPersons()
   {
      this.logger.debug("# of registrated persons: " + this.bankingService.countPersons());
   }
}
