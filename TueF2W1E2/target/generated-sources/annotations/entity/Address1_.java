package entity;

import entity.Customer1;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-09-21T18:55:34")
@StaticMetamodel(Address1.class)
public class Address1_ { 

    public static volatile SingularAttribute<Address1, String> city;
    public static volatile SingularAttribute<Address1, String> street;
    public static volatile SingularAttribute<Address1, Long> id;
    public static volatile ListAttribute<Address1, Customer1> customers;

}