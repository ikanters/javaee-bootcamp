/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.sogeti.jdc.demo.jee6.banking.controller;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.slf4j.Logger;

import nl.sogeti.jdc.demo.jee6.banking.boundary.BankingServiceLocal;
import nl.sogeti.jdc.demo.jee6.banking.controller.util.ControllerUtil;
import nl.sogeti.jdc.demo.jee6.banking.entity.Account;
import nl.sogeti.jdc.demo.jee6.banking.entity.Person;

/**
 * @author kanteriv
 */
/**
 * A controller (Managed)bean for the account(s) of a person. This controllers is used by the accounts.xhtml page to manage the account(s).
 * 
 * @version $Id:$
 * @author kanteriv (c) 2 sep. 2011, Sogeti Nederland B.V.
 */
@ManagedBean
@ViewScoped
public class AccountController {
   @Inject
   Logger logger;
   @EJB
   BankingServiceLocal bankingService;
   @EJB
   ControllerUtil controllerUtil;

   private UserData userData;
   private List<Account> allAccounts;
   private Account selected;
   private BigDecimal transferAmount = BigDecimal.ZERO.setScale(2);

   /**
    * Based on the userData that is stored in the session the accounts for the selected person is retrieved (on construct).
    */
   @PostConstruct
   public void init() {
      this.userData = (UserData) this.controllerUtil.getSession(true).getAttribute("userData");
      findAllAccounts();
   }

   /**
    * Get all the (found) accounts.
    * 
    * @return a list of accounts
    */
   public List<Account> getAllAccounts() {
      return this.allAccounts;
   }

   /**
    * Saves an account to the database. If it is a new account, it will be inserted, otherwise an update will be done.
    */
   public void save() {
      // try
      // {
      this.logger.debug("save :: " + this.selected);
      if (this.selected.getId() == null) {
         this.selected = this.bankingService.createAccount(this.selected);
      } else {
         this.selected = this.bankingService.updateAccount(this.selected);
      }
      findAllAccounts();
      // }
      // catch (PersistException e)
      // {
      // this.controllerUtil.addMessage("not saved!!! beacause: " + e.getMessage());
      // this.controllerUtil.addCallBackParam(Constants.CALLBACK_PARAM_SAVED_FAILED, Boolean.TRUE);
      // }
   }

   /**
    * Deposit an amount to the account.
    */
   public void deposit() {
      this.bankingService.deposit(this.selected, this.transferAmount);
      setTransferAmount(BigDecimal.ZERO.setScale(2));
   }

   /**
    * Withdraw an amount from the account.
    */
   public void withdraw() {
      this.bankingService.withdraw(this.selected, this.transferAmount);
      setTransferAmount(BigDecimal.ZERO.setScale(2));
   }

   /**
    * Create a new account object. (just the object. do not store it!)
    */
   public void newAccount() {
      this.logger.debug("newAccount() :: " + this.selected);
      Account account = new Account(getOwner());
      account.setOwner(getOwner());
      setSelected(account);
   }

   public BigDecimal getTransferAmount() {
      return this.transferAmount;
   }

   public void setTransferAmount(BigDecimal transferAmount) {
      this.transferAmount = transferAmount;
   }

   public Account getSelected() {
      return this.selected;
   }

   public void setSelected(Account selected) {
      this.selected = selected;
   }

   public void rowEvent() {
      this.logger.debug("rowSelected() :: " + this.selected);
   }

   private void findAllAccounts() {
      this.allAccounts = this.bankingService.findAccountsFor(getOwner());
   }

   public Person getOwner() {
      return this.userData.getSelectedPerson();
   }
}
