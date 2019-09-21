/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import facade.EntityFacade;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import utils.EMF_Creator;

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

        //Persistence.generateSchema("pu", null);
        EntityFacade fac = new EntityFacade().getFacadeExample(EMF);
        fac.createCustomer("Johan", "emailen");
        fac.createCustomer("asd", "emadsadasilen");
        fac.createCustomer("Joasdashan", "asddadaeg");
        List<Customer> out = fac.getAll();
        for (Customer c : out) {
            System.out.println(c.getName());
        }
        System.out.println(fac.getCustomer(1).getName());
        fac.createItemType("Bolt", "Used for attaching things to other things", 134);
        fac.createItemType("Saw", "Used for removing mass form things", 500);
        fac.createItemType("Hammer", "Used for hitting things", 320);
        System.out.println(fac.getItemType(1).getName());
        fac.createOrder(1);
        fac.createOrder(1);
        fac.createOrder(1);
        System.out.println(fac.getCustomer(1).getOrder().size());
        fac.createOrderLine(1, 1, 16);
        System.out.println("ItemType orderline: "+fac.getItemType(1).getOrderlines().size());
        System.out.println("Orders orderline: "+fac.getCustomer(1).getOrder().get(0).getOrderline().size());
        List<OrderNew> oout = fac.getCustomerOrders(1);
        for(OrderNew o : oout){
            System.out.println(o.getId());
        }
        fac.createOrderLine(2, 1, 3);
        System.out.println(fac.totalPriceOrder(1));
        
    }
}
