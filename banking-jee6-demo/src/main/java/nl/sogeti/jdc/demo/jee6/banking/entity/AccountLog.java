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
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import nl.sogeti.jdc.demo.jee6.banking.constants.DebetCreditEnum;

/**
 * @author kanteriv
 */
@Entity
public class AccountLog extends AbstractEntity {
   private static final long serialVersionUID = -1957693851717081899L;

   @ManyToOne(optional = false)
   @SuppressWarnings("unused")
   private Account account;
   @ManyToOne(optional = true)
   @SuppressWarnings("unused")
   private Account other;
   @Column(nullable = false)
   @Enumerated(EnumType.STRING)
   @SuppressWarnings("unused")
   private DebetCreditEnum aspect;
   @Column(nullable = false)
   private BigDecimal amount;
   @Temporal(TemporalType.TIMESTAMP)
   @SuppressWarnings("unused")
   private Date logTimestamp;

   protected AccountLog() {
      super();
   }

   public AccountLog(Account account, BigDecimal amount, DebetCreditEnum aspect) {
      super();
      this.account = account;
      this.amount = amount;
      this.aspect = aspect;
      this.logTimestamp = new Date();

   }

   public AccountLog(Account account, Account other, BigDecimal amount, DebetCreditEnum aspect) {
      this(account, amount, aspect);
      this.other = other;
   }

   @PrePersist
   protected void prePersist() {
      if (this.amount != null && this.amount.scale() != 2) {
         this.amount = this.amount.setScale(2, RoundingMode.HALF_UP);
      }
   }
}
