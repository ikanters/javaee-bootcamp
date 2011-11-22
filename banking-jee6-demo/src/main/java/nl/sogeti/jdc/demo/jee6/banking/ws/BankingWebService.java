/**
 * 
 */
package nl.sogeti.jdc.demo.jee6.banking.ws;

import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import nl.sogeti.jdc.demo.jee6.banking.boundary.BankingServiceLocal;
import nl.sogeti.jdc.demo.jee6.banking.entity.Person;

/**
 * @author kanteriv
 */
@WebService(name = "BankingWebService", serviceName = "BankingWebService")
public class BankingWebService
{
   @EJB
   BankingServiceLocal BankingService;
   
   @WebMethod
   public Person getPerson(@WebParam(name = "clientId") String clientId)
   {
      return this.BankingService.findPersonByClientId(clientId);
   }
}
