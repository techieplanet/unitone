package com.tp.neo.model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-08-15T21:22:49")
@StaticMetamodel(Auditlog.class)
public class Auditlog_ { 

    public static volatile SingularAttribute<Auditlog, String> note;
    public static volatile SingularAttribute<Auditlog, Date> logDate;
    public static volatile SingularAttribute<Auditlog, Integer> usertype;
    public static volatile SingularAttribute<Auditlog, Long> id;
    public static volatile SingularAttribute<Auditlog, Long> userId;
    public static volatile SingularAttribute<Auditlog, String> actionName;

}