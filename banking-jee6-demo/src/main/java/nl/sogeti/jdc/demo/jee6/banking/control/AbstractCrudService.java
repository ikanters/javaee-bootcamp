/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.sogeti.jdc.demo.jee6.banking.control;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import nl.sogeti.jdc.demo.jee6.banking.entity.AbstractEntity;

/**
 * @author kanteriv
 * @param <ENTITY>
 *           The entity to use the CRUD on.
 */
public abstract class AbstractCrudService<ENTITY extends AbstractEntity> {
   @PersistenceContext(unitName = "sample")
   EntityManager entityManager;

   protected abstract Class<ENTITY> getEntityClass();

   public ENTITY persist(ENTITY entity) {
      this.entityManager.persist(entity);
      return entity;
   }

   public ENTITY merge(ENTITY entity) {
      return this.entityManager.merge(entity);
   }

   public void remove(ENTITY entity) {
      ENTITY attached = this.entityManager.merge(entity);
      this.entityManager.remove(attached);
   }

   public void flush() {
      this.entityManager.flush();
   }

   public ENTITY find(Long key) {
      return this.entityManager.find(getEntityClass(), key);
   }

   protected Query createNamedQuery(final String queryName) {
      return this.entityManager.createNamedQuery(queryName);
   }

   protected EntityManager getEntityManager() {
      return this.entityManager;
   }
}
