package nl.sogeti.jdc.demo.jee6.banking.exception;

/**
 * @author kanteriv
 */
public class TransactionRollbackException extends Exception {
   private static final long serialVersionUID = -5553562094583710913L;

   private String type;
   private String message;

   public TransactionRollbackException(Throwable t) {
      Throwable current = t;

      do {
         if (current.getCause() == null) {
            this.type = current.getClass().getSimpleName();
            this.message = current.getMessage();
         }
         current = current.getCause();
      } while (current != null);
   }

   public String getType() {
      return this.type;
   }

   @Override
   public String getMessage() {
      return this.message;
   }
}
