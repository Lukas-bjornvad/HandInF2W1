/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Test;

import entity.Address1;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import utils.EMF_Creator;
import entity.Customer1;

/**
 *
 * @author Lukas Bjornvad
 */
public class Tester {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory(
            "pu",
            "jdbc:mysql://localhost:3307/data",
            "dev",
            "ax2",
            EMF_Creator.Strategy.DROP_AND_CREATE);

    public static void main(String[] args) {
        EntityManager em = EMF.createEntityManager();
        //Persistence.generateSchema("pu", null);
        //Exercise address1, customer1 er som de andre gange, customer1_address1 holder forbindene id
        //jeg havde et problem med at få den til at persiste fra address som jeg ikke kunne løse
        Customer1 cust = new Customer1("Jackson", "Peppers");
        Customer1 cust2 = new Customer1("Percy", "Jackal");
        Customer1 cust3 = new Customer1("Derryl", "Mints");
        Address1 addr = new Address1("Strat 12", "Caty");
        Address1 addr2 = new Address1("Treet 45", "Ceety"); 
        Address1 addr3 = new Address1("Troot 12", "Cooty");
        addr.addCustomers(cust);
        em.getTransaction().begin();
        cust.addAddresses(addr);
        cust.addAddresses(addr2);
        em.persist(cust);
        em.getTransaction().commit();
    
        em.getTransaction().begin();
        addr3.addCustomers(cust2);
        addr3.addCustomers(cust3);
        em.persist(addr3);
        em.getTransaction().commit();
        em.close();
        // Exercise 3-4 Der var ikke forskel på min kode der indsatte data med bidirectional og unidirctional OneToMany 
//        Customer1 cust = new Customer1("Jackson", "Peppers");
//        Address1 addr = new Address1("Strat 12", "Caty");
//        addr.setCustomer(cust);
//        em.getTransaction().begin();
//        cust.addAddresses(addr);
//        addr = new Address1("Treet 45", "Ceety");
//        addr.setCustomer(cust);
//        cust.addAddresses(addr);
//        em.merge(cust);
//        em.getTransaction().commit();
//        cust = new Customer1("Percy", "Jackal");
//
//        em.getTransaction().begin();
//        addr = new Address1("Olympus street 20", "Greece");
//        addr.setCustomer(cust);
//        cust.addAddresses(addr);
//        addr = new Address1("Street 21", "City");
//        addr.setCustomer(cust);
//        cust.addAddresses(addr);
//        em.merge(cust);
//        em.getTransaction().commit();
//
//        em.getTransaction().begin();
//        cust = new Customer1("Derryl", "Mints");
//        addr = new Address1("Cheap Street 60", "San Jose");
//        addr.setCustomer(cust);
//        cust.addAddresses(addr);
//        addr = new Address1("Cheap Streat -21", "Cty");
//        addr.setCustomer(cust);
//        cust.addAddresses(addr);
//        em.merge(cust);
//        em.getTransaction().commit();
//        em.close();
    }
}
