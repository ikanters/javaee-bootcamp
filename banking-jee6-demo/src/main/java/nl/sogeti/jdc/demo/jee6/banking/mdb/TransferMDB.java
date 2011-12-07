/**
 * 
 */
package nl.sogeti.jdc.demo.jee6.banking.mdb;

import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;

import org.slf4j.Logger;

/**
 * @author kanteriv
 * 
 */
@MessageDriven(mappedName = "jms/jee6-demo/Destination")
public class TransferMDB implements MessageListener {

   @Inject
   Logger logger;

   @Override
   public void onMessage(Message message) {

      if (message instanceof TextMessage) {
         logMessage((TextMessage) message);
      } else if (message instanceof ObjectMessage) {
         logMessage((ObjectMessage) message);
      }
   }

   private void logMessage(TextMessage msg) {
      try {
         this.logger.info("Message received: " + msg.getText());
      } catch (JMSException e) {
         throw new RuntimeException("TODO (kanteriv) handle this Auto-generated exception", e);
      }
   }

   private void logMessage(ObjectMessage msg) {
      try {
         this.logger.info("Message received: " + msg.getObject());
      } catch (JMSException e) {
         throw new RuntimeException("TODO (kanteriv) handle this Auto-generated exception", e);
      }
   }
}
