/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

/**
 *
 * @author Lukas Bjornvad
 */
public class CustomerFacade {

    private static CustomerFacade instance;
    private static EntityManagerFactory emf;

    public static CustomerFacade getFacadeExample(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new CustomerFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public Customer1 getCustomer1(int id) {
        EntityManager em = getEntityManager();
        try {
            Customer1 item = em.find(Customer1.class, id);
            return item;
        } finally {
            em.close();
        }
    }

    public List<Customer1> getCustomer1s() {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Customer1> query
                    = em.createQuery("Select c from Customer c", Customer1.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public Customer1 addCustomer1(Customer1 cust) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        em.persist(cust);
        em.getTransaction().commit();
        em.close();
        return cust;
    }

    public Customer1 deleteCustomer1(int id) {
         EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Customer1 cust = em.find(Customer1.class, id);
            em.remove(cust);
            em.getTransaction().commit();
            return cust;
        } finally {
            em.close();
        }
    }

    public Customer1 editCustomer1(Customer1 cust1) {
         EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Customer1 cust = em.find(Customer1.class, cust1.getId());
            cust.setFirstName(cust1.getFirstName());
            cust.setLastName(cust1.getLastName());
            em.merge(cust);
            em.getTransaction().commit();
            return cust;
        } finally {
            em.close();
        }
    }

}
