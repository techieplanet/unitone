/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.neo.service;

import com.tp.neo.model.Role;
import java.util.List;

/**
 *
 * @author SWEDGE
 */
public class testRoleService {
    public static void main(String ... arg){
        List<Role> roles = RoleService.findAllRolesLowerThan(2);
        
        for(Role role : roles)
        {
            System.out.println(role.getTitle());
        }
        
        roles = RoleService.findAllRolesHigherThan(2);
        
        for(Role role : roles)
        {
            System.out.println(role.getTitle());
        }
    }
}
