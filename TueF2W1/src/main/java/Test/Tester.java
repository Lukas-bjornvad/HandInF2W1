/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Test;

import entities.Customer;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

/**
 *
 * @author Lukas Bjornvad
 */
public class Tester {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");
        EntityManager em = emf.createEntityManager();
        
        
      
        Customer cust;
        em.getTransaction().begin();
        cust = new Customer("Lars", "Larsen");
        cust.addHobby("Jogging");
        cust.addHobby("Swimming");
        em.persist(cust);
        em.getTransaction().commit();
        em.getTransaction().begin();
        cust = new Customer("Karl", "Larsen");
        cust.addHobby("Gaming");
        cust.addHobby("MtnDew Drinking");
        em.persist(cust);
        em.getTransaction().commit();
        em.getTransaction().begin();
        cust = new Customer("Isabella", "Korsten");
        cust.addHobby("Tennis");
        cust.addHobby("Squash");
        em.persist(cust);
        em.getTransaction().commit();
        em.getTransaction().begin();
        cust = new Customer("Frederikke", "Persen");
        cust.addHobby("Chess");
        cust.addHobby("Stargazing");
        em.persist(cust);
        em.getTransaction().commit();
        cust = new Customer("Frederikke", "Persen");
        cust.addHobby("Chess");
        cust.addHobby("Stargazing");
        try {
            TypedQuery<Customer> query
                    = em.createQuery("Select c from Customer c", Customer.class);
             System.out.println(query.getResultList().get(0).getHobbies());
        } finally {
            em.close();
        }
    }
}
