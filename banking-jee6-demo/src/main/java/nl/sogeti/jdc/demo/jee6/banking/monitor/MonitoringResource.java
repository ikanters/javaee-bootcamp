/**
 * 
 */
package nl.sogeti.jdc.demo.jee6.banking.monitor;

import java.util.Map;

import javax.inject.Inject;

import org.slf4j.Logger;

/**
 * @author kanteriv
 * 
 */
public class MonitoringResource {

   @Inject
   Logger logger;

   public void onNewWatchdog(WatchDog watchDog) {

      Map<String, String> map = watchDog.asMap();
      if (map != null) {
         for (Map.Entry<String, String> element : map.entrySet()) {
            this.logger.info(element.getKey() + ": " + element.getValue());
         }
      }
   }
}
