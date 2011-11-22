/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.sogeti.jdc.demo.jee6.banking.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author kanteriv
 */
@Entity
@NamedQueries({
      @NamedQuery(name = Person.FIND_ALL_NAMEDQUERY, query = "select person from Person person order by person.clientId, person.lastname, person.firstname, person.city, person.street"),
      @NamedQuery(name = Person.COUNT_ALL_NAMEDQUERY, query = "select count(person) from Person person"),
      @NamedQuery(name = Person.FIND_BY_CLIENTID, query = "select person from Person person where person.clientId = :" + Person.CLIENTID_PARAMNAME) })
@XmlRootElement
public class Person extends AbstractEntity {
   private static final long serialVersionUID = 982374691237864L;
   public static final String FIND_ALL_NAMEDQUERY = "findAll";
   public static final String COUNT_ALL_NAMEDQUERY = "countAll";
   public static final String FIND_BY_CLIENTID = "findByClient";
   public static final String CLIENTID_PARAMNAME = "clientId";

   @NotNull
   @Size(min = 5, max = 9)
   @Column(unique = true, nullable = false)
   private String clientId;

   @NotNull
   @Size(min = 3, max = 255)
   private String lastname;

   @NotNull
   @Size(min = 3, max = 255)
   private String firstname;
   private String street;
   private String city;

   public Person() {
      super();
   }

   public Person(String clientId, String lastname, String firstname) {
      super();
      this.clientId = clientId;
      this.lastname = lastname;
      this.firstname = firstname;
   }

   public String getClientId() {
      return this.clientId;
   }

   public void setClientId(String clientId) {
      this.clientId = clientId;
   }

   public String getCity() {
      return this.city;
   }

   public void setCity(String city) {
      this.city = city;
   }

   public String getFirstname() {
      return this.firstname;
   }

   public void setFirstname(String firstname) {
      this.firstname = firstname;
   }

   public String getLastname() {
      return this.lastname;
   }

   public void setLastname(String lastname) {
      this.lastname = lastname;
   }

   public String getStreet() {
      return this.street;
   }

   public void setStreet(String street) {
      this.street = street;
   }

   public String getName() {
      if (this.firstname == null || this.firstname.trim().length() == 0) {
         return this.lastname;
      }
      return this.firstname + " " + this.lastname;
   }

   @Override
   public String toString() {
      return "Person{" + "id=" + getId() + ", clientId=" + this.clientId + ", name=" + getName() + ", street=" + this.street + ", city=" + this.city + '}';
   }
}
