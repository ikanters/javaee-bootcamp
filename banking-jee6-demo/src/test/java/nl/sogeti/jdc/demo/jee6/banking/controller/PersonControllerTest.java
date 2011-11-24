package nl.sogeti.jdc.demo.jee6.banking.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import nl.sogeti.jdc.demo.jee6.banking.boundary.BankingService;
import nl.sogeti.jdc.demo.jee6.banking.controller.util.ControllerUtil;
import nl.sogeti.jdc.demo.jee6.banking.entity.Person;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.LoggerFactory;

/**
 * @author kanteriv
 */
public class PersonControllerTest {
   private PersonController personController;

   /**
 * 
 */
   public PersonControllerTest() {
      super();
   }

   /**
    * @throws java.lang.Exception
    */
   @Before
   public void setUp() {
      this.personController = new PersonController();
      this.personController.logger = LoggerFactory.getLogger(PersonControllerTest.class);

   }

   /**
    * Test method for {@link nl.sogeti.jdc.demo.jee6.banking.controller.PersonController#init()}.
    * 
    * @throws Exception
    */
   @Test
   public void testInit() {
      ControllerUtil controllerUtilMock = mock(ControllerUtil.class);
      BankingService bankingService = mock(BankingService.class);
      HttpSession httpSessionMock = mock(HttpSession.class);

      this.personController.controllerUtil = controllerUtilMock;
      this.personController.bankingService = bankingService;

      ArrayList<Person> personList = new ArrayList<Person>();
      personList.add(new Person("12345", "lastname", "firstname"));

      when(bankingService.findAllPersons()).thenReturn(personList);
      when(controllerUtilMock.getSession(true)).thenReturn(httpSessionMock);

      this.personController.init();

      List<Person> result = this.personController.getAllPersons();
      assertEquals(personList, result);
   }

   /**
    * Test method for {@link nl.sogeti.jdc.demo.jee6.banking.controller.PersonController#save()}.
    */
   @Test
   @Ignore
   public void testSave() {
      fail("Not yet implemented");
   }

   /**
    * Test method for {@link nl.sogeti.jdc.demo.jee6.banking.controller.PersonController#newPerson()}.
    */
   @Test
   @Ignore
   public void testNewPerson() {
      fail("Not yet implemented");
   }

   /**
    * Test method for {@link nl.sogeti.jdc.demo.jee6.banking.controller.PersonController#rowEvent()}.
    */
   @Test
   @Ignore
   public void testRowEvent() {
      fail("Not yet implemented");
   }

   /**
    * Test method for {@link nl.sogeti.jdc.demo.jee6.banking.controller.PersonController#getAllPersons()}.
    */
   @Test
   @Ignore
   public void testGetAllPersons() {
      fail("Not yet implemented");
   }

}
