package com.tp.neo.model;

import com.tp.neo.model.Agent;
import com.tp.neo.model.Customer;
import com.tp.neo.model.SaleItem;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-09-27T21:43:02")
@StaticMetamodel(Order1.class)
public class Order1_ { 

    public static volatile SingularAttribute<Order1, Agent> agentId;
    public static volatile CollectionAttribute<Order1, SaleItem> saleItemCollection;
    public static volatile SingularAttribute<Order1, Customer> customerId;
    public static volatile SingularAttribute<Order1, Long> id;

}