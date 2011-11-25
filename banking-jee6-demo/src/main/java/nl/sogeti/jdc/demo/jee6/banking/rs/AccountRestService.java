/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.sogeti.jdc.demo.jee6.banking.rs;

import static javax.ws.rs.core.Response.ok;

import java.math.BigDecimal;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import nl.sogeti.jdc.demo.jee6.banking.boundary.BankingServiceLocal;
import nl.sogeti.jdc.demo.jee6.banking.entity.Account;

import org.slf4j.Logger;

/**
 * @author kanteriv
 */
@Stateless
@Path("account")
public class AccountRestService {
   @Inject
   Logger logger;

   @EJB
   BankingServiceLocal bankingService;

   @GET
   @Path("{accountNumber}")
   @Produces(MediaType.TEXT_PLAIN)
   public String getBalance(@PathParam("accountNumber") String accountNumber) {
      Account account = this.bankingService.findAccountByNumber(accountNumber);
      if (account != null) {
         return account.getBalance().toPlainString();
      }
      return "";
   }

   @POST
   @Path("{accountNumber}")
   public Response transfer(@PathParam("accountNumber") String accountNumber, @QueryParam("toAccountNumber") String toAccountNumber,
         @QueryParam("amount") BigDecimal amount) {
      if (this.bankingService.transfer(accountNumber, toAccountNumber, amount)) {
         return ok().build();
      }

      return Response.notModified(accountNumber).build();
   }

}