/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.sogeti.jdc.demo.jee6.banking.control;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.Query;

import nl.sogeti.jdc.demo.jee6.banking.entity.Person;

/**
 * @author kanteriv
 */
@ControlService
@Stateless
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class PersonService extends AbstractCrudService<Person>
{
   
   @Override
   protected Class<Person> getEntityClass()
   {
      return Person.class;
   }
   
   @SuppressWarnings("unchecked")
   //   @RolesAllowed({ "admin", "employee" })
   public List<Person> findAll()
   {
      final Query namedQuery = createNamedQuery(Person.FIND_ALL_NAMEDQUERY);
      return namedQuery.getResultList();
   }
   
   public int count()
   {
      return ((Number) createNamedQuery(Person.COUNT_ALL_NAMEDQUERY).getSingleResult()).intValue();
   }
   
   /**
    * @see nl.sogeti.jdc.demo.jee6.banking.control.AbstractCrudService#update(nl.sogeti.jdc.demo.jee6.banking.entity.AbstractEntity)
    */
   @Override
   //   @RolesAllowed({ "ADMIN", "MANAGER", "OWNER" })
   public Person update(Person entity)
   {
      return super.update(entity);
   }
   
   /**
    * @param clientId
    * @return the found person (or null if not found).
    */
   //   @RolesAllowed({ "ADMIN", "MANAGER" })
   public Person findByClientId(String clientId)
   {
      final Query namedQuery = createNamedQuery(Person.FIND_BY_CLIENTID);
      namedQuery.setParameter(Person.CLIENTID_PARAMNAME, clientId);
      return (Person) namedQuery.getSingleResult();
   }
}
