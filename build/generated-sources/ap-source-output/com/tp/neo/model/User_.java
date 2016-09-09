package com.tp.neo.model;

import com.tp.neo.model.Role;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-09-07T20:59:21")
@StaticMetamodel(User.class)
public class User_ { 

    public static volatile SingularAttribute<User, String> firstname;
    public static volatile SingularAttribute<User, Role> role;
    public static volatile SingularAttribute<User, String> middlename;
    public static volatile SingularAttribute<User, Short> active;
    public static volatile SingularAttribute<User, Long> userId;
    public static volatile SingularAttribute<User, String> lastname;
    public static volatile SingularAttribute<User, String> password;
    public static volatile SingularAttribute<User, Short> deleted;
    public static volatile SingularAttribute<User, Date> createdDate;
    public static volatile SingularAttribute<User, String> phone;
    public static volatile SingularAttribute<User, Long> createdBy;
    public static volatile SingularAttribute<User, String> permissions;
    public static volatile SingularAttribute<User, Date> modifiedDate;
    public static volatile SingularAttribute<User, Long> modifiedBy;
    public static volatile SingularAttribute<User, String> email;
    public static volatile SingularAttribute<User, String> username;

}