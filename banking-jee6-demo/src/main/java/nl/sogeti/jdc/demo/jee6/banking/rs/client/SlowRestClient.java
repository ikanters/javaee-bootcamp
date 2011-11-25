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
import javax.ejb.Stateful;
import javax.inject.Inject;

import nl.sogeti.jdc.demo.jee6.banking.exception.ApplicationException;

import org.slf4j.Logger;

/**
 * @author kanteriv
 * 
 */
@Stateful
public class SlowRestClient {

   @Inject
   Logger logger;

   /**
    * 
    */
   public SlowRestClient() {
      super();
   }

   @Asynchronous
   public Future<Integer> callSlowMethod(int millis) {

      int result = 0;
      try {

         URL url = new URL("http://localhost:8080/slow-service/resources/slow?time=" + millis);
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
            result = Integer.parseInt(output);
         }

         conn.disconnect();

      } catch (MalformedURLException e) {
         throw new RuntimeException("Url is wrong", e);
      } catch (IOException e) {
         throw new RuntimeException("Cannot connect???!!!", e);
      }
      return new AsyncResult<Integer>(result);
   }
}
