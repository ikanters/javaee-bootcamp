/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.sogeti.jdc.demo.jee6.banking.config;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author kanteriv
 */
public class LoggerConfigurator {

   @Produces
   public Logger get(InjectionPoint ip) {

      Class<?> requestingClass = ip.getMember().getDeclaringClass();
      return LoggerFactory.getLogger(requestingClass.getName());
   }

}
