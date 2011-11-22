/*------------------------------------------------------------------------------
 **     Ident: Delivery Center Java
 **    Author: kanteriv
 ** Copyright: (c) 8 sep. 2011 Sogeti Nederland B.V. All Rights Reserved.
 **------------------------------------------------------------------------------
 ** Sogeti Nederland B.V.            |  No part of this file may be reproduced  
 ** Distributed Software Engineering |  or transmitted in any form or by any        
 ** Lange Dreef 17                   |  means, electronic or mechanical, for the      
 ** 4131 NJ Vianen                   |  purpose, without the express written    
 ** The Netherlands                  |  permission of the copyright holder.
 *------------------------------------------------------------------------------
 */
package nl.sogeti.jdc.demo.jee6.banking.config;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.inject.spi.InjectionPoint;

/**
 * TODO Type description.
 * 
 * @version $Id:$
 * @author kanteriv (c) 8 sep. 2011, Sogeti Nederland B.V.
 */
@Startup
@Singleton
public class Configuration
{
   private Map<String, String> configuration;
   private Set<String> unconfiguredFields;
   
   @PostConstruct
   public void fetchConfiguration()
   {
      //default values...
      this.configuration = new HashMap<String, String>();
      this.configuration.put("numberOfRows", "10");
      
      this.unconfiguredFields = new HashSet<String>();
      mergeWithCustomConfiguration();
   }
   
   private void mergeWithCustomConfiguration()
   {
      // TODO Auto-generated method stub
   }
   
   @javax.enterprise.inject.Produces
   public String getString(InjectionPoint point)
   {
      String fieldName = point.getMember().getName();
      String valueForFieldName = this.configuration.get(fieldName);
      if (valueForFieldName == null)
      {
         this.unconfiguredFields.add(fieldName);
      }
      return valueForFieldName;
   }
   
   @javax.enterprise.inject.Produces
   public int getInteger(InjectionPoint point)
   {
      String stringValue = getString(point);
      if (stringValue == null)
      {
         return 0;
      }
      return Integer.parseInt(stringValue);
   }
   
}
