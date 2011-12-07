/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.sogeti.jdc.demo.jee6.banking.entity;

import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrePersist;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * @author kanteriv
 */
@Entity
@NamedQueries({
      @NamedQuery(name = Account.FIND_ACCOUNTS_FOR_PERSON_NAMEDQUERY, query = "select account from Account account where account.owner = :"
            + Account.OWNER_PARAM),
      @NamedQuery(name = Account.FIND_ACCOUNT_BY_NUMBER_NAMEDQUERY, query = "select account from Account account where account.number = :"
            + Account.NUMBER_PARAM) })
public class Account extends AbstractEntity {
   private static final long serialVersionUID = -1957693851717081899L;
   private static final int MIN_CREDITLIMIT = 0;
   private static final int MAX_CREDITLIMIT = 10000;
   public static final String FIND_ACCOUNTS_FOR_PERSON_NAMEDQUERY = "findAccountsForPerson";
   public static final String FIND_ACCOUNT_BY_NUMBER_NAMEDQUERY = "findAccountByNumber";
   public static final String OWNER_PARAM = "owner";
   public static final String NUMBER_PARAM = "number";

   @Column(length = 25, nullable = false, unique = true)
   private String number;
   @ManyToOne(optional = false)
   private Person owner;
   @Column(nullable = false, precision = 12, scale = 2)
   private BigDecimal balance = BigDecimal.ZERO;
   @Column(nullable = false, precision = 12, scale = 2)
   @Min(value = MIN_CREDITLIMIT, message = MIN_CREDITLIMIT + " is the minimum")
   @Max(value = MAX_CREDITLIMIT, message = MAX_CREDITLIMIT + " is the maximum")
   private BigDecimal creditLimit = BigDecimal.ZERO;

   protected Account() {
      super();
   }

   public Account(Person owner) {
      super();
      this.owner = owner;
   }

   @PrePersist
   protected void prePersist() {
      if (this.creditLimit != null && this.creditLimit.scale() != 2) {
         this.creditLimit = this.creditLimit.setScale(2, RoundingMode.HALF_UP);
      }
      if (this.balance != null && this.balance.scale() != 2) {
         this.balance = this.balance.setScale(2, RoundingMode.HALF_UP);
      }
   }

   public BigDecimal getCreditLimit() {
      return this.creditLimit;
   }

   public void setCreditLimit(BigDecimal creditLimit) {
      this.creditLimit = creditLimit;
   }

   public String getNumber() {
      return this.number;
   }

   public void setNumber(String number) {
      this.number = number;
   }

   public Person getOwner() {
      return this.owner;
   }

   public void setOwner(Person owner) {
      this.owner = owner;
   }

   public BigDecimal getBalance() {
      return this.balance;
   }

   public void addAmount(BigDecimal amount) {
      if (amount == null) {
         throw new NullPointerException();
      }
      if (amount.compareTo(BigDecimal.ZERO) < 0) {
         throw new RuntimeException("Cannot add a negative amount");
      }
      this.balance = this.balance.add(amount);
   }

   public boolean substractAmount(BigDecimal amount) {
      if (amount == null) {
         throw new NullPointerException();
      }
      if (amount.compareTo(BigDecimal.ZERO) < 0) {
         throw new RuntimeException("Cannot substract a negative amount");
      }
      BigDecimal newBalance = this.balance.subtract(amount);

      if (newBalance.compareTo(getCreditLimit().negate()) < 0) {

         return false;
      }
      this.balance = newBalance;
      return true;
   }
}
