/*------------------------------------------------------------------------------
 **     Ident: Delivery Center Java
 **    Author: kanteriv
 ** Copyright: (c) 2 sep. 2011 Sogeti Nederland B.V. All Rights Reserved.
 **------------------------------------------------------------------------------
 ** Sogeti Nederland B.V.            |  No part of this file may be reproduced  
 ** Distributed Software Engineering |  or transmitted in any form or by any        
 ** Lange Dreef 17                   |  means, electronic or mechanical, for the      
 ** 4131 NJ Vianen                   |  purpose, without the express written    
 ** The Netherlands                  |  permission of the copyright holder.
 *------------------------------------------------------------------------------
 */
package nl.sogeti.jdc.demo.jee6.banking.constants;

import static nl.sogeti.jdc.demo.jee6.banking.constants.DebetCreditEnum.CREDIT;
import static nl.sogeti.jdc.demo.jee6.banking.constants.DebetCreditEnum.DEBET;

import java.beans.PropertyEditorSupport;

/**
 * TODO Type description.
 * 
 * @version $Id:$
 * @author kanteriv (c) 2 sep. 2011, Sogeti Nederland B.V.
 */
public class DebetCreditEnumEditor extends PropertyEditorSupport
{
   /**
    * @see java.beans.PropertyEditorSupport#setAsText(java.lang.String)
    */
   @Override
   public void setAsText(String text) throws IllegalArgumentException
   {
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
   public String getAsText()
   {
      DebetCreditEnum aspect = (DebetCreditEnum) getValue();
      return aspect.equals(DEBET) ? "D" : "C";
   }
}
