/**
 * 
 */
package nl.sogeti.jdc.demo.jee6.slow.rs;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.LoggerFactory;

/**
 * @author kanteriv
 * 
 */
public class SlowRestServiceTest {

   private SlowRestService slowRestService;

   @Before
   public void setup() {

      this.slowRestService = new SlowRestService();
      this.slowRestService.logger = LoggerFactory.getLogger(SlowRestServiceTest.class);
   }

   /**
    * Test method for {@link nl.sogeti.jdc.demo.jee6.slow.rs.SlowRestService#methodTakesLong(int)}.
    */
   @Test
   public void testMethodTakesLong() {
      int timeInMillis = 50;
      long before = System.currentTimeMillis();
      this.slowRestService.methodTakesLong(timeInMillis);
      long took = System.currentTimeMillis() - before;
      assertTrue("It took " + took + ", but should be minimal " + timeInMillis, took >= timeInMillis);
   }

}
