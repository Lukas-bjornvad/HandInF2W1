package entity;

import entity.Address1;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-09-21T18:55:34")
@StaticMetamodel(Customer1.class)
public class Customer1_ { 

    public static volatile SingularAttribute<Customer1, String> firstName;
    public static volatile SingularAttribute<Customer1, String> lastName;
    public static volatile ListAttribute<Customer1, Address1> addresses;
    public static volatile SingularAttribute<Customer1, Long> id;

}