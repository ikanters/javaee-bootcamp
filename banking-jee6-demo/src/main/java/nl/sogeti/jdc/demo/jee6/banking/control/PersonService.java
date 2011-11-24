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
public class PersonService extends AbstractCrudService<Person> {

   @Override
   protected Class<Person> getEntityClass() {
      return Person.class;
   }

   @SuppressWarnings("unchecked")
   public List<Person> findAll() {
      final Query namedQuery = createNamedQuery(Person.FIND_ALL_NAMEDQUERY);
      return namedQuery.getResultList();
   }

   public int count() {
      return ((Number) createNamedQuery(Person.COUNT_ALL_NAMEDQUERY).getSingleResult()).intValue();
   }

   /**
    * @param clientId
    * @return the found person (or null if not found).
    */
   @SuppressWarnings("unchecked")
   public Person findByClientId(String clientId) {
      final Query namedQuery = createNamedQuery(Person.FIND_BY_CLIENTID);
      namedQuery.setParameter(Person.CLIENTID_PARAMNAME, clientId);
      List<Person> resultList = namedQuery.getResultList();
      if (resultList.size() == 1) {
         return resultList.get(0);
      }
      return null;
   }
}
