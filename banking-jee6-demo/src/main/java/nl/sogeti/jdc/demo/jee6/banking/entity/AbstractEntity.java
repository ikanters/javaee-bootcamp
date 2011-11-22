/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.sogeti.jdc.demo.jee6.banking.entity;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;

/**
 * @author kanteriv
 */
@MappedSuperclass
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class AbstractEntity implements Serializable
{
   private static final long serialVersionUID = -5009482244229344305L;
   @Id
   @GeneratedValue
   @XmlTransient
   private Long id;
   
   public AbstractEntity()
   {
      super();
   }
   
   public AbstractEntity(AbstractEntity entity)
   {
      super();
      this.id = entity.getId();
   }
   
   public Long getId()
   {
      return this.id;
   }
   
   public void setId(Long id)
   {
      this.id = id;
   }
}
