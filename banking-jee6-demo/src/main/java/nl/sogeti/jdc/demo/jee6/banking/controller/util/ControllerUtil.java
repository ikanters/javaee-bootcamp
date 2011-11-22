package nl.sogeti.jdc.demo.jee6.banking.controller.util;

import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

/**
 * @author kanteriv
 */
@Singleton
@Startup
public class ControllerUtil {

   public ControllerUtil() {
      super();
   }

   public HttpSession getSession(boolean create) {
      return (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(create);
   }

   public void addCallBackParam(String callbackParamName, Object value) {
      RequestContext requestContext = RequestContext.getCurrentInstance();
      requestContext.addCallbackParam(callbackParamName, value);
   }

   public void addMessage(String message) {
      FacesContext context = FacesContext.getCurrentInstance();
      context.addMessage(null, new FacesMessage(message));
   }

}
