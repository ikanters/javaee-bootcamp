/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.sogeti.jdc.demo.jee6.banking.constants;

import java.beans.PropertyEditorManager;

/**
 * @author kanteriv
 */
public enum DebetCreditEnum
{
   DEBET, CREDIT;
   static
   {
      PropertyEditorManager.registerEditor(DebetCreditEnum.class, DebetCreditEnumEditor.class);
   }
   
}
