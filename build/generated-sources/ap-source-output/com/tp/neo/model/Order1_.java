package com.tp.neo.model;

import com.tp.neo.model.Agent;
import com.tp.neo.model.Customer;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-09-29T14:26:42")
@StaticMetamodel(Order1.class)
public class Order1_ { 

    public static volatile SingularAttribute<Order1, Short> approvalStatus;
    public static volatile SingularAttribute<Order1, Date> approvedDate;
    public static volatile SingularAttribute<Order1, Agent> agentId;
    public static volatile SingularAttribute<Order1, Integer> modifierUserType;
    public static volatile SingularAttribute<Order1, Date> createdDate;
    public static volatile SingularAttribute<Order1, Long> createdBy;
    public static volatile SingularAttribute<Order1, Integer> approvedBy;
    public static volatile SingularAttribute<Order1, Date> modifiedDate;
    public static volatile SingularAttribute<Order1, Customer> customerId;
    public static volatile SingularAttribute<Order1, Long> modifiedBy;
    public static volatile SingularAttribute<Order1, Long> id;
    public static volatile SingularAttribute<Order1, Integer> creatorUserType;

}