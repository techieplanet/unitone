package com.tp.neo.model;

import com.tp.neo.model.User;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-09-24T23:49:33")
@StaticMetamodel(Role.class)
public class Role_ { 

    public static volatile SingularAttribute<Role, Date> createdDate;
    public static volatile SingularAttribute<Role, Long> createdBy;
    public static volatile SingularAttribute<Role, Integer> roleId;
    public static volatile SingularAttribute<Role, String> permissions;
    public static volatile CollectionAttribute<Role, User> userCollection;
    public static volatile SingularAttribute<Role, Date> modifiedDate;
    public static volatile SingularAttribute<Role, String> description;
    public static volatile SingularAttribute<Role, Short> active;
    public static volatile SingularAttribute<Role, Long> modifiedBy;
    public static volatile SingularAttribute<Role, String> title;

}