package com.tp.neo.model;

import com.tp.neo.model.Order1;
import com.tp.neo.model.ProjectUnit;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-09-30T15:36:09")
@StaticMetamodel(SaleItem.class)
public class SaleItem_ { 

    public static volatile SingularAttribute<SaleItem, Double> initialDep;
    public static volatile SingularAttribute<SaleItem, Double> discountAmt;
    public static volatile SingularAttribute<SaleItem, Integer> quantity;
    public static volatile SingularAttribute<SaleItem, Long> saleId;
    public static volatile SingularAttribute<SaleItem, Order1> orderId;
    public static volatile SingularAttribute<SaleItem, Double> discountPercentage;
    public static volatile SingularAttribute<SaleItem, Date> datetime;
    public static volatile SingularAttribute<SaleItem, Short> deleted;
    public static volatile SingularAttribute<SaleItem, Date> createdDate;
    public static volatile SingularAttribute<SaleItem, Long> createdBy;
    public static volatile SingularAttribute<SaleItem, Date> modifiedDate;
    public static volatile SingularAttribute<SaleItem, ProjectUnit> unitId;
    public static volatile SingularAttribute<SaleItem, Long> modifiedBy;

}