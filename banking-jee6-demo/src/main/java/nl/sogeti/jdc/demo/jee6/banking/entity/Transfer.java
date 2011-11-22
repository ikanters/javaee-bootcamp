/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.sogeti.jdc.demo.jee6.banking.entity;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author kanteriv
 */
@Entity
public class Transfer extends AbstractEntity
{
   private static final long serialVersionUID = -574131230870694154L;
   @Column(nullable = false)
   private String owner;
   @Column(nullable = false)
   private String other;
   @Column(nullable = false, precision = 12, scale = 2)
   private BigDecimal amount;
   @Column(nullable = false)
   @Temporal(TemporalType.DATE)
   private Date transferDate;
   
   protected Transfer()
   {
      super();
   }
   
   public Transfer(String owner, String other, BigDecimal amount)
   {
      super();
      this.owner = owner;
      this.other = other;
      this.amount = amount;
      this.transferDate = new Date();
   }
   
   @PrePersist
   protected void prePersist()
   {
      if (this.amount != null && this.amount.scale() != 2)
      {
         this.amount = this.amount.setScale(2, RoundingMode.HALF_UP);
      }
   }
   
   @Override
   public String toString()
   {
      return "Transfer{" + "owner=" + this.owner + ", other=" + this.other + ", amount=" + this.amount + ", transferDate=" + this.transferDate + '}';
   }
   
}
