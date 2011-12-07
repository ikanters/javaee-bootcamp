/**
 * 
 */
package nl.sogeti.jdc.demo.jee6.banking.rs.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.Future;

import javax.ejb.AsyncResult;
import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.inject.Inject;

import nl.sogeti.jdc.demo.jee6.banking.exception.ApplicationException;

import org.slf4j.Logger;

/**
 * @author kanteriv
 * 
 */
@Stateless
public class SlowRestClient {

   static String protocol = "http";
   static String host = "localhost";
   static int port = 8080;
   static final String contextRoot = "/slow-service";
   static final String applicationPath = "resources";
   static final String restPath = "slow";

   @Inject
   Logger logger;

   /**
    * 
    */
   public SlowRestClient() {
      super();
   }

   /**
    * Calls a method on a rest service which might be very slow.
    * 
    * @return the time spend on this call.
    */
   @Asynchronous
   public Future<Integer> callSlowMethod() {
      return new AsyncResult<Integer>(callSlowMethod(0));
   }

   Integer callSlowMethod(int time) {

      long begin = System.currentTimeMillis();
      String urlString = protocol + "://" + host + ":" + port + contextRoot + "/" + applicationPath + "/" + restPath + "?time=" + time;

      callRestService(urlString);

      int timeSpend = (int) (System.currentTimeMillis() - begin);
      return timeSpend;
   }

   /**
    * @param urlString
    */
   private void callRestService(String urlString) {
      try {
         URL url = new URL(urlString);
         HttpURLConnection conn = (HttpURLConnection) url.openConnection();
         conn.setRequestMethod("GET");

         if (conn.getResponseCode() != 200) {
            throw new ApplicationException("Failed : HTTP error code : " + conn.getResponseCode());
         }

         BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

         String output;
         this.logger.info("Output from Server: ");

         while ((output = br.readLine()) != null) {
            this.logger.info(output);
         }

         conn.disconnect();

      } catch (MalformedURLException e) {
         throw new RuntimeException("Url is wrong", e);
      } catch (IOException e) {
         throw new RuntimeException("Cannot connect???!!!", e);
      }
   }
}
