/**
 * 
 */
package nl.sogeti.jdc.demo.jee6.banking.rs.client;

import org.junit.Test;

/**
 * @author kanteriv
 * 
 */
public class SlowRestClientIT {

   @Test(expected = RuntimeException.class)
   public void test() {

      SlowRestClient.host = "xxx";

      SlowRestClient slowRestClient = new SlowRestClient();
      slowRestClient.callSlowMethod(10);
   }

   @Test(expected = RuntimeException.class)
   public void test2() {

      SlowRestClient.host = "www.google.nl";
      SlowRestClient.port = 80;
      SlowRestClient.protocol = "http";

      SlowRestClient slowRestClient = new SlowRestClient();
      slowRestClient.callSlowMethod(10);
   }
}
