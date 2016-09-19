package com.tp.neo.model;

import com.tp.neo.model.Agent;
import com.tp.neo.model.CustomerAgent;
import com.tp.neo.model.Order1;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-09-18T08:59:33")
@StaticMetamodel(Customer.class)
public class Customer_ { 

    public static volatile SingularAttribute<Customer, Agent> agentId;
    public static volatile SingularAttribute<Customer, String> firstname;
    public static volatile SingularAttribute<Customer, String> city;
    public static volatile SingularAttribute<Customer, String> photoPath;
    public static volatile SingularAttribute<Customer, String> kinPhotoPath;
    public static volatile SingularAttribute<Customer, String> kinAddress;
    public static volatile SingularAttribute<Customer, String> password;
    public static volatile CollectionAttribute<Customer, Order1> order1Collection;
    public static volatile SingularAttribute<Customer, String> street;
    public static volatile SingularAttribute<Customer, Long> customerId;
    public static volatile SingularAttribute<Customer, Long> modifiedBy;
    public static volatile SingularAttribute<Customer, String> state;
    public static volatile SingularAttribute<Customer, String> email;
    public static volatile SingularAttribute<Customer, String> kinName;
    public static volatile SingularAttribute<Customer, Short> verificationStatus;
    public static volatile SingularAttribute<Customer, String> kinPhone;
    public static volatile SingularAttribute<Customer, String> middlename;
    public static volatile SingularAttribute<Customer, Short> active;
    public static volatile SingularAttribute<Customer, String> lastname;
    public static volatile SingularAttribute<Customer, Short> deleted;
    public static volatile SingularAttribute<Customer, Date> createdDate;
    public static volatile SingularAttribute<Customer, Long> createdBy;
    public static volatile SingularAttribute<Customer, String> phone;
    public static volatile CollectionAttribute<Customer, CustomerAgent> customerAgentCollection;
    public static volatile SingularAttribute<Customer, Date> modifiedDate;

}