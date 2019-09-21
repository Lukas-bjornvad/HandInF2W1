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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

/**
 *
 * @author Lukas Bjornvad
 */
@Entity
public class Customer1 implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String firstName;
    private String lastName;  
      //@OneToOne
    //@OneToOne
    
     // private Address1 address;
    // oneToMany laver et seprart table der forbinder de forskellige 
//     @JoinColumn
//     @OneToMany
    //mapped by bliver nødvendig
    //@OneToMany(mappedBy = "customer")
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "Customer_Address",
            joinColumns = {@JoinColumn(name = "customer1_id")},
            inverseJoinColumns = {@JoinColumn(name = "address1_id")}
    )
    private List<Address1> addresses = new ArrayList();
    
    public Customer1() {
    }

    public Customer1(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public List<Address1> getAddresses() {
        return addresses;
    }

    public void addAddresses(Address1 addresses) {
        this.addresses.add(addresses);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        if (!(object instanceof Customer1)) {
            return false;
        }
        Customer1 other = (Customer1) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Customer1[ id=" + id + " ]";
    }
    
}
