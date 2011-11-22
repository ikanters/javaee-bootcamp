/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.sogeti.jdc.demo.jee6.banking.exception;

/**
 * TODO Type description.
 * 
 * @version $Id:$
 * @author kanteriv (c) 20 sep. 2011, Sogeti Nederland B.V.
 */
public class ApplicationException extends RuntimeException {

   private static final long serialVersionUID = -7094731954973105186L;

   /**
    * Constructor: create a new ApplicationException.
    */
   public ApplicationException() {
      super();
   }

   /**
    * Constructor: create a new ApplicationException.
    * 
    * @param message
    */
   public ApplicationException(String message) {
      super(message);
   }

   /**
    * Constructor: create a new ApplicationException.
    * 
    * @param cause
    */
   public ApplicationException(Throwable cause) {
      super(cause);
   }

   /**
    * Constructor: create a new ApplicationException.
    * 
    * @param message
    * @param cause
    */
   public ApplicationException(String message, Throwable cause) {
      super(message, cause);
   }

}
