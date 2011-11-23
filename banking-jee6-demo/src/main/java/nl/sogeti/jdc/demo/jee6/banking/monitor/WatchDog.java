/**
 * 
 */
package nl.sogeti.jdc.demo.jee6.banking.monitor;

import java.util.HashMap;
import java.util.Map;

/**
 * @author kanteriv
 * 
 */
public class WatchDog {

   private final Map<String, String> parameters;

   /**
    * @param name
    * @param value
    */
   public WatchDog(String name, Object value) {
      this.parameters = new HashMap<>();
      this.parameters.put(name, String.valueOf(value));
   }

   public static WatchDog with(String name, Object value) {
      return new WatchDog(name, value);
   }

   public WatchDog and(String name, Object value) {
      this.parameters.put(name, String.valueOf(value));
      return this;
   }

   public Map<String, String> asMap() {
      return this.parameters;
   }
}
