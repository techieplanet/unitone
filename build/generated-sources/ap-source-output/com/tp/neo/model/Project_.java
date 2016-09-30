package com.tp.neo.model;

import com.tp.neo.model.ProjectUnit;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-09-30T15:36:09")
@StaticMetamodel(Project.class)
public class Project_ { 

    public static volatile SingularAttribute<Project, String> projectManager;
    public static volatile SingularAttribute<Project, Short> active;
    public static volatile SingularAttribute<Project, String> description;
    public static volatile SingularAttribute<Project, Integer> targetSalesUnits;
    public static volatile CollectionAttribute<Project, ProjectUnit> projectUnitCollection;
    public static volatile SingularAttribute<Project, Short> deleted;
    public static volatile SingularAttribute<Project, Date> createdDate;
    public static volatile SingularAttribute<Project, Long> createdBy;
    public static volatile SingularAttribute<Project, String> name;
    public static volatile SingularAttribute<Project, Date> modifiedDate;
    public static volatile SingularAttribute<Project, Long> modifiedBy;
    public static volatile SingularAttribute<Project, String> location;
    public static volatile SingularAttribute<Project, Integer> id;

}