package nl.sogeti.jdc.demo.jee6.banking.exception;

/**
 * @author kanteriv
 */
public class PersistException extends Exception
{
   private static final long serialVersionUID = -5553562094583710913L;

   public PersistException(String message, Throwable cause)
   {
      super(message, cause);
   }
}
