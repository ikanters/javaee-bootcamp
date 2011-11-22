/*------------------------------------------------------------------------------
 **     Ident: Delivery Center Java
 **    Author: kanteriv
 ** Copyright: (c) 20 sep. 2011 Sogeti Nederland B.V. All Rights Reserved.
 **------------------------------------------------------------------------------
 ** Sogeti Nederland B.V.            |  No part of this file may be reproduced  
 ** Distributed Software Engineering |  or transmitted in any form or by any        
 ** Lange Dreef 17                   |  means, electronic or mechanical, for the      
 ** 4131 NJ Vianen                   |  purpose, without the express written    
 ** The Netherlands                  |  permission of the copyright holder.
 *------------------------------------------------------------------------------
 */
package nl.sogeti.jdc.demo.jee6.banking.exception;

/**
 * TODO Type description.
 * 
 * @version $Id:$
 * @author kanteriv (c) 20 sep. 2011, Sogeti Nederland B.V.
 */
public class ApplicationException extends RuntimeException
{
   
   /**
    * Constructor: create a new ApplicationException.
    */
   public ApplicationException()
   {
      super();
   }
   
   /**
    * Constructor: create a new ApplicationException.
    * 
    * @param message
    */
   public ApplicationException(String message)
   {
      super(message);
   }
   
   /**
    * Constructor: create a new ApplicationException.
    * 
    * @param cause
    */
   public ApplicationException(Throwable cause)
   {
      super(cause);
   }
   
   /**
    * Constructor: create a new ApplicationException.
    * 
    * @param message
    * @param cause
    */
   public ApplicationException(String message, Throwable cause)
   {
      super(message, cause);
   }
   
}
