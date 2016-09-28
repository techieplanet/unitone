/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.neo.model;

import com.tp.neo.interfaces.IRestricted;
import com.tp.neo.interfaces.ITrailable;
import com.tp.neo.interfaces.SystemUser;
import java.io.Serializable;

/**
 *
 * @author Swedge
 */
public abstract class BaseModel implements Serializable, ITrailable, IRestricted{
    
    /************************************************************************************************
    * FROM THE INTERFACES                                                                           *
    * Some of the methods are declared default and so have a default implementation                 *
    * Others are by default abstract (since they are inside an interface) and so are inherited      *
    * into this class as abstract methods. Subclasses will have to implement them                   *
    *                                                                                               *
    * **********************************************************************************************/
    
}
