package com.tp.neo.model;

import com.tp.neo.model.Agent;
import com.tp.neo.model.Customer;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-09-24T23:49:33")
@StaticMetamodel(CustomerAgent.class)
public class CustomerAgent_ { 

    public static volatile SingularAttribute<CustomerAgent, Agent> agentId;
    public static volatile SingularAttribute<CustomerAgent, Long> caId;
    public static volatile SingularAttribute<CustomerAgent, Customer> customerId;

}