/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.sogeti.jdc.demo.jee6.banking.control;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import nl.sogeti.jdc.demo.jee6.banking.entity.Transfer;

/**
 * @author kanteriv
 */
@ControlService
@Stateless
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class TransferService extends AbstractCrudService<Transfer>
{
   @Override
   protected Class<Transfer> getEntityClass()
   {
      return Transfer.class;
   }
   
}
