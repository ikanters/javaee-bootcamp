/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.sogeti.jdc.demo.jee6.banking.constants;

import static nl.sogeti.jdc.demo.jee6.banking.constants.DebetCreditEnum.CREDIT;
import static nl.sogeti.jdc.demo.jee6.banking.constants.DebetCreditEnum.DEBET;

import java.beans.PropertyEditorSupport;

/**
 * @author kanteriv
 */
public class DebetCreditEnumEditor extends PropertyEditorSupport {
   /**
    * @see java.beans.PropertyEditorSupport#setAsText(java.lang.String)
    */
   @Override
   public void setAsText(String text) throws IllegalArgumentException {
      if (text.trim().equalsIgnoreCase("D"))
         setValue(DEBET);
      else if (text.trim().equalsIgnoreCase("C"))
         setValue(CREDIT);
      else
         throw new IllegalStateException("D and C are the only supported DebitCredit aliases");
   }

   /**
    * @see java.beans.PropertyEditorSupport#getAsText()
    */
   @Override
   public String getAsText() {
      DebetCreditEnum aspect = (DebetCreditEnum) getValue();
      return aspect.equals(DEBET) ? "D" : "C";
   }
}
