/**
 * 
 */
package nl.sogeti.jdc.demo.jee6.banking.exception;

/**
 * @author kanteriv
 * 
 */
public class InvalidAuditParameterException extends Exception {

   private static final long serialVersionUID = -3812336422146453858L;

   /**
    * @param message
    */
   public InvalidAuditParameterException(String message) {
      super(message);
   }

   /**
    * @param cause
    */
   public InvalidAuditParameterException(Throwable cause) {
      super(cause);
   }

   /**
    * @param message
    * @param cause
    */
   public InvalidAuditParameterException(String message, Throwable cause) {
      super(message, cause);
   }

}
