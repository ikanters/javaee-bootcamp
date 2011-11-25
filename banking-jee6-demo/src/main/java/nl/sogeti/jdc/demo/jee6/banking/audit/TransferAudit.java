/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.sogeti.jdc.demo.jee6.banking.audit;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import nl.sogeti.jdc.demo.jee6.banking.control.TransferService;
import nl.sogeti.jdc.demo.jee6.banking.entity.Account;
import nl.sogeti.jdc.demo.jee6.banking.entity.Transfer;

import org.slf4j.Logger;

/**
 * @author kanteriv
 */
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class TransferAudit {

   @Inject
   Logger logger;
   @EJB
   TransferService transferService;

   @Resource(name = "jms/jee6-demo/ConnectionFactory")
   private ConnectionFactory connectionFactory;
   @Resource(name = "jms/jee6-demo/Destination")
   private Destination destination;

   /**
    * Logs a transfer to the database (and logfile).
    * 
    * @param ic
    *           the invocationContext this is invoked on
    * @return The result of the proceeded invocationContext.
    * @throws Exception
    */
   @AroundInvoke
   public Object log(InvocationContext ic) throws Exception {
      this.logger.debug("#" + (ic.getMethod() == null ? null : ic.getMethod().getName()) + " is called!!!!");

      Transfer transfer = null;

      Object[] parameters = ic.getParameters();
      if (parametersValid(parameters)) {
         Account from = (Account) parameters[0];
         Account to = (Account) parameters[1];
         BigDecimal amount = (BigDecimal) parameters[2];

         this.logger.info("new Transfer(" + from.getNumber() + ", " + to.getNumber() + ", " + amount + ")");
         transfer = new Transfer(from.getNumber(), to.getNumber(), amount);
      }
      Object result = ic.proceed();

      // After successful proceed, save the transfer entity to the database.
      if (transfer != null) {
         this.transferService.persist(transfer);

         // And also send this log over JMS.
         sendOverJMS(transfer);
      }
      return result;
   }

   /**
    * @param transfer
    */
   private void sendOverJMS(Transfer transfer) {

      if (this.connectionFactory != null) {

         Connection connection = null;
         Session session = null;
         MessageProducer producer = null;
         try {
            connection = this.connectionFactory.createConnection();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            producer = session.createProducer(this.destination);

            final TextMessage message = session.createTextMessage(transfer.toString());
            // put the message on the queue
            producer.send(message);

         } catch (JMSException e) {
            this.logger.error("Unable to put the transfer on the queue....", e);
         } finally {
            close(producer);
            close(session);
            close(connection);
         }
      }
   }

   /**
    * Pretty simple check if the paramaters are valid to 'log'. We expect: - 3 parameters - first 2 are String - last is BigDecimal. Th parameters are not
    * expected to be null... (all mandatory).
    * 
    * @param parameters
    *           the parameters to validate.
    * @return true if the parameters are valid, false otherwise.
    */
   private boolean parametersValid(Object[] parameters) {
      if (parameters == null || parameters.length != 3) {
         return false;
      }

      int i = 0;
      for (Object parameter : parameters) {
         if (parameter == null) {
            return false;
         }
         if (i < 2 && !(parameter instanceof Account)) {
            return false;
         }

         if (i == 2 && !(parameter instanceof BigDecimal)) {
            return false;
         }
         i++;
      }

      return true;
   }

   private void close(Object object) {
      if (object != null) {
         Method method = null;
         try {
            method = object.getClass().getMethod("close");
         } catch (SecurityException e) {
            throw new RuntimeException("Cannot occur!!!", e);
         } catch (NoSuchMethodException e) {
            // No close method om this object. So we dont close is... Just log that a close is called on a not closable object...
            this.logger.warn("close is called on an object of class " + object.getClass().getName() + ", but no close method is found.");
         }

         if (method != null) {
            try {
               method.invoke(object);
            } catch (IllegalArgumentException e) {
               throw new RuntimeException("Cannot occur!!!", e);
            } catch (IllegalAccessException e) {
               throw new RuntimeException("Cannot occur!!!", e);
            } catch (InvocationTargetException e) {
               // Close failed. That´s too bad, but not a real problem. We´ll just continue. (after a small log)
               this.logger.warn("close failed on object " + object + "." + e.getCause());
            }
         }
      }
   }
}
