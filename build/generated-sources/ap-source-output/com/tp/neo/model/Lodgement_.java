package com.tp.neo.model;

import com.tp.neo.model.SaleItem;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-09-26T09:53:44")
@StaticMetamodel(Lodgement.class)
public class Lodgement_ { 

    public static volatile SingularAttribute<Lodgement, Double> amount;
    public static volatile SingularAttribute<Lodgement, Long> orderId;
    public static volatile SingularAttribute<Lodgement, Short> paymentMode;
    public static volatile SingularAttribute<Lodgement, Short> verificationStatus;
    public static volatile SingularAttribute<Lodgement, String> gatewayTransId;
    public static volatile SingularAttribute<Lodgement, String> bankName;
    public static volatile SingularAttribute<Lodgement, SaleItem> sale;
    public static volatile SingularAttribute<Lodgement, Date> lodgmentDate;
    public static volatile SingularAttribute<Lodgement, Date> createdDate;
    public static volatile SingularAttribute<Lodgement, Long> createdBy;
    public static volatile SingularAttribute<Lodgement, String> tellerNo;
    public static volatile SingularAttribute<Lodgement, Long> lodgementId;
    public static volatile SingularAttribute<Lodgement, Date> modifiedDate;
    public static volatile SingularAttribute<Lodgement, Long> modifiedBy;
    public static volatile SingularAttribute<Lodgement, String> depositorsName;

}