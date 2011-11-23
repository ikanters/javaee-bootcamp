/**
 * 
 */
package nl.sogeti.jdc.demo.jee6.banking.entity;

import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;

import org.junit.Test;

/**
 * @author kanteriv
 */
public class TransferIT extends AbstractEntityIT
{
   @Test
   public void testPersist()
   {
      Transfer transfer = new Transfer("11", "22", new BigDecimal("12.345"));
      getTransaction().begin();
      getEntityManager().persist(transfer);
      getTransaction().commit();

      assertNotNull(transfer.getId());
   }
}
