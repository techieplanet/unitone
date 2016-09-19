package com.tp.neo.model;

import com.tp.neo.model.Agent;
import com.tp.neo.model.Customer;
import com.tp.neo.model.Lodgement;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-09-18T08:59:33")
@StaticMetamodel(Sale.class)
public class Sale_ { 

    public static volatile SingularAttribute<Sale, Double> initialDep;
    public static volatile SingularAttribute<Sale, Double> discountAmt;
    public static volatile SingularAttribute<Sale, Agent> agentId;
    public static volatile SingularAttribute<Sale, Integer> quantity;
    public static volatile SingularAttribute<Sale, Long> saleId;
    public static volatile CollectionAttribute<Sale, Lodgement> lodgementCollection;
    public static volatile SingularAttribute<Sale, Double> discountPercentage;
    public static volatile SingularAttribute<Sale, Date> datetime;
    public static volatile SingularAttribute<Sale, Boolean> deleted;
    public static volatile SingularAttribute<Sale, Date> createdDate;
    public static volatile SingularAttribute<Sale, Integer> createdBy;
    public static volatile SingularAttribute<Sale, Date> modifiedDate;
    public static volatile SingularAttribute<Sale, Customer> customerId;
    public static volatile SingularAttribute<Sale, Integer> modifiedBy;

}