/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.sogeti.jdc.demo.jee6.banking.rs;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import nl.sogeti.jdc.demo.jee6.banking.boundary.BankingServiceLocal;
import nl.sogeti.jdc.demo.jee6.banking.entity.Person;
import nl.sogeti.jdc.demo.jee6.banking.exception.TransactionRollbackException;

import org.slf4j.Logger;

/**
 * @author kanteriv
 */
@Stateless
@Path("person")
public class PersonRestService {
   @Inject
   Logger logger;

   @EJB
   BankingServiceLocal bankingService;

   @GET
   @Path("{clientId}")
   @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
   public Person getPerson(@PathParam("clientId") String clientId) {
      Person person = this.bankingService.findPersonByClientId(clientId);

      return person;
   }

   @POST
   @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
   @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
   public Person insertPerson(Person person) {
      Person insertedPerson;
      try {
         insertedPerson = this.bankingService.createPerson(person);
      } catch (TransactionRollbackException e) {
         logger.error("person not created because:" + e.getMessage(), e);
         return null;
      }

      return insertedPerson;
   }

   @PUT
   @Path("{clientId}")
   @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
   public Response updatePerson(@PathParam("clientId") String clientId, Person person) {
      if (clientId.equals(person.getClientId())) {
         this.bankingService.updatePerson(person);
         return Response.ok().build();
      }
      return Response.notModified(clientId).build();

   }

}