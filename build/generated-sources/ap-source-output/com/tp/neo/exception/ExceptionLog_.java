package com.tp.neo.exception;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-08-15T21:22:49")
@StaticMetamodel(ExceptionLog.class)
public class ExceptionLog_ { 

    public static volatile SingularAttribute<ExceptionLog, Date> date;
    public static volatile SingularAttribute<ExceptionLog, String> inputvalues;
    public static volatile SingularAttribute<ExceptionLog, String> errorMessage;
    public static volatile SingularAttribute<ExceptionLog, Long> id;
    public static volatile SingularAttribute<ExceptionLog, String> entity;

}