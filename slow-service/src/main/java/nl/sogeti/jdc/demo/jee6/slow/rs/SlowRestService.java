/**
 * 
 */
package nl.sogeti.jdc.demo.jee6.slow.rs;

import java.util.Random;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;

/**
 * @author kanteriv
 */
@Stateless
@Path("slow")
public class SlowRestService {

   @Inject
   Logger logger;

   private static Random random = new Random(System.currentTimeMillis());

   @GET
   @Produces(MediaType.TEXT_PLAIN)
   public String methodTakesLong(@QueryParam("time") int time) {

      long start = System.currentTimeMillis();
      try {
         int millisToWait = time <= 0 ? getRandomTime() : time;
         this.logger.info("Sleeping for " + millisToWait + " millis.");
         Thread.sleep(millisToWait);
      } catch (InterruptedException e) {
         e.printStackTrace();
      }
      long end = System.currentTimeMillis();

      return "" + (end - start);
   }

   /**
    * @return
    */
   private int getRandomTime() {
      return random.nextInt(1500);
   }
}
