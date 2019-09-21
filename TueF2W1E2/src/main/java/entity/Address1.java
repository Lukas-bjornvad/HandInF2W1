/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

/**
 *
 * @author Lukas Bjornvad
 */
@Entity
public class Address1 implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

   private String street;
    private String city;
   // @OneToOne(mappedBy = "address1")
    //Ser ikke ud til det ændre sig, nok fordi at Costumer er owner
    //@ManyToOne
    
    //private Customer1 customer;
    @ManyToMany(mappedBy = "addresses", fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private List<Customer1> customers = new ArrayList();

  

    public Address1() {
    }

    public Address1(String street, String city) {
        this.street = street;
        this.city = city;
    }
    
  public List<Customer1> getCustomers() {
        return customers;
    }

    public void addCustomers(Customer1 customer) {
        this.customers.add(customer);
    }
//    public Customer1 getCustomer() {
//        return customer;
//    }
//
//    public void setCustomer(Customer1 customer) {
//        this.customer = customer;
//    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Address1)) {
            return false;
        }
        Address1 other = (Address1) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Address1[ id=" + id + " ]";
    }
    
}
