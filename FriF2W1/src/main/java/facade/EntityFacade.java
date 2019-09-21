/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entity.Customer;
import entity.ItemType;
import entity.OrderLine;
import entity.OrderNew;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

/**
 *
 * @author Lukas Bjornvad
 */
public class EntityFacade {

    private static EntityFacade instance;
    private static EntityManagerFactory emf;

    public static EntityFacade getFacadeExample(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new EntityFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void createCustomer(String name, String email) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        em.persist(new Customer(name, email));
        em.getTransaction().commit();
        em.close();
    }

    public List<Customer> getAll() {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Customer> query
                    = em.createQuery("Select c from Customer c", Customer.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public Customer getCustomer(long id) {
        EntityManager em = getEntityManager();
        try {
            Customer cust = em.find(Customer.class, id);
            return cust;
        } finally {
            em.close();
        }
    }

    public void createItemType(String iname, String desc, double price) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        em.persist(new ItemType(iname, desc, price));
        em.getTransaction().commit();
        em.close();
    }

    public ItemType getItemType(long id) {
        EntityManager em = getEntityManager();
        try {
            ItemType item = em.find(ItemType.class, id);
            return item;
        } finally {
            em.close();
        }
    }

    public void createOrder(long id) {
        EntityManager em = getEntityManager();
        Customer g = getCustomer(id);
        OrderNew ord = new OrderNew();
        em.getTransaction().begin();
        g.addOrder(ord);
        em.merge(g);
        em.getTransaction().commit();
        em.close();
    }

    public void createOrderLine(long itemid, long orderid, int qnt) {
        EntityManager em = getEntityManager();
        ItemType g = getItemType(itemid);
        OrderNew o;
        OrderLine ord = new OrderLine(qnt);
        em.getTransaction().begin();
        o=em.find(OrderNew.class, orderid);
        ord.setItemtype(g);
        ord.setOrder(o);
        g.addOrderLine(ord);
        o.addOrderline(ord);
        em.merge(g);
        em.merge(o);
        em.getTransaction().commit();
        em.close();
    }

    public List<OrderNew> getCustomerOrders(long id) {
        EntityManager em = getEntityManager();
        try {
            Customer cust = em.find(Customer.class, id);
            List<OrderNew> out = cust.getOrder();
            return out;
        } finally {
            em.close();
        }
    }
    
    public double totalPriceOrder(long id){
        double price=0;
        EntityManager em = getEntityManager();
        try {
            OrderNew ord = em.find(OrderNew.class, id);
            List<OrderLine> out = ord.getOrderline();
            for(OrderLine ol : out){
             price += ol.getItemtype().getPrice()*ol.getQuantity();
            }
            return price;
        } finally {
            em.close();
        }
        
    }
    
}
