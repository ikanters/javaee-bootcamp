/**
 * 
 */
package nl.sogeti.jdc.demo.jee6.banking.monitor;

import java.util.Map;

import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.slf4j.Logger;

/**
 * @author kanteriv
 * 
 */
public class MonitoringResource {

   @Inject
   Logger logger;

   public void onNewWatchdog(@Observes WatchDog watchDog) {
      Map<String, String> map = watchDog.asMap();
      if (map != null) {
         for (Map.Entry<String, String> element : map.entrySet()) {
            logger.info(element.getKey() + ": " + element.getValue());
         }
      }
   }
}
