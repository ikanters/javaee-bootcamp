/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.sogeti.jdc.demo.jee6.banking.control;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import nl.sogeti.jdc.demo.jee6.banking.entity.AccountLog;

/**
 * @author kanteriv
 */
@ControlService
@Stateless
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class AccountLogService extends AbstractCrudService<AccountLog> {

   @Override
   protected Class<AccountLog> getEntityClass() {
      return AccountLog.class;
   }

}
