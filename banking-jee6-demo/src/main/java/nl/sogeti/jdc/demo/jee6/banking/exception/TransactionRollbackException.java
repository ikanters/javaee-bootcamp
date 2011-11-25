package nl.sogeti.jdc.demo.jee6.banking.exception;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author kanteriv
 */
public class TransactionRollbackException extends Exception {

   static final String DUPLICATE_KEY_DETECTED = "Duplicate key detected...";

   private static final long serialVersionUID = -5553562094583710913L;

   private static final Map<Class<? extends Throwable>, String> MESSAGES = new HashMap<Class<? extends Throwable>, String>();
   static {
      MESSAGES.put(SQLIntegrityConstraintViolationException.class, DUPLICATE_KEY_DETECTED);
   }

   private String type;
   private String message;

   public TransactionRollbackException(Throwable t) {
      Throwable current = t;

      while (current != null && this.message == null) {

         Class<? extends Throwable> currentClass = current.getClass();

         if (MESSAGES.containsKey(currentClass)) {
            this.type = currentClass.getSimpleName();
            this.message = MESSAGES.get(currentClass);

         } else if (current.getCause() == null) {
            this.type = currentClass.getSimpleName();
            this.message = current.getMessage();
         }
         current = current.getCause();
      }
   }

   public String getType() {
      return this.type;
   }

   @Override
   public String getMessage() {
      return this.message;
   }

}
