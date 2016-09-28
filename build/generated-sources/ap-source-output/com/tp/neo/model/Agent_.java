package com.tp.neo.model;

import com.tp.neo.model.Customer;
import com.tp.neo.model.Order1;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-09-27T21:43:02")
@StaticMetamodel(Agent.class)
public class Agent_ { 

    public static volatile SingularAttribute<Agent, Long> agentId;
    public static volatile SingularAttribute<Agent, String> firstname;
    public static volatile SingularAttribute<Agent, String> city;
    public static volatile SingularAttribute<Agent, String> photoPath;
    public static volatile SingularAttribute<Agent, String> kinPhotoPath;
    public static volatile SingularAttribute<Agent, Short> agreementStatus;
    public static volatile SingularAttribute<Agent, String> bankName;
    public static volatile SingularAttribute<Agent, Long> genericId;
    public static volatile SingularAttribute<Agent, String> kinAddress;
    public static volatile SingularAttribute<Agent, String> password;
    public static volatile CollectionAttribute<Agent, Order1> order1Collection;
    public static volatile SingularAttribute<Agent, String> street;
    public static volatile SingularAttribute<Agent, Long> modifiedBy;
    public static volatile SingularAttribute<Agent, String> state;
    public static volatile SingularAttribute<Agent, String> email;
    public static volatile SingularAttribute<Agent, Short> approvalStatus;
    public static volatile SingularAttribute<Agent, String> kinName;
    public static volatile SingularAttribute<Agent, String> bankAcctNumber;
    public static volatile SingularAttribute<Agent, String> bankAcctName;
    public static volatile SingularAttribute<Agent, String> kinPhone;
    public static volatile SingularAttribute<Agent, String> middlename;
    public static volatile SingularAttribute<Agent, Short> active;
    public static volatile SingularAttribute<Agent, String> lastname;
    public static volatile SingularAttribute<Agent, Short> deleted;
    public static volatile SingularAttribute<Agent, Date> createdDate;
    public static volatile SingularAttribute<Agent, Long> createdBy;
    public static volatile CollectionAttribute<Agent, Customer> customerCollection;
    public static volatile SingularAttribute<Agent, String> phone;
    public static volatile SingularAttribute<Agent, Date> modifiedDate;

}