/*------------------------------------------------------------------------------
 **     Ident: Delivery Center Java
 **    Author: kanteriv
 ** Copyright: (c) 6 nov. 2011 Sogeti Nederland B.V. All Rights Reserved.
 **------------------------------------------------------------------------------
 ** Sogeti Nederland B.V.            |  No part of this file may be reproduced  
 ** Distributed Software Engineering |  or transmitted in any form or by any        
 ** Lange Dreef 17                   |  means, electronic or mechanical, for the      
 ** 4131 NJ Vianen                   |  purpose, without the express written    
 ** The Netherlands                  |  permission of the copyright holder.
 *------------------------------------------------------------------------------
 */
package nl.sogeti.jdc.demo.jee6.banking.config;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TODO Type description.
 * 
 * @version $Id:$
 * @author kanteriv (c) 6 nov. 2011, Sogeti Nederland B.V.
 */
public class LoggerConfigurator
{
   @Produces
   public Logger get(InjectionPoint ip)
   {
      Class< ? > requestingClass = ip.getMember().getDeclaringClass();
      return LoggerFactory.getLogger(requestingClass.getName());
   }
   
}
