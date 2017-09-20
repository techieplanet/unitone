/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.neo.service;

import com.tp.neo.model.Document;
import java.util.List;

/**
 *
 * @author SWEDGE
 */
public class testDocumentService {
    
    public static void main(String... args){
        List<Document> documents = DocumentService.getCustomerDocuments(54);
        
        documents.forEach( d -> {
            //System.out.println(d.getId() + " " + d.getDocTypeId().getTitle() + " " + d.getPath() + " "  + d.getOwnerId() + " "+ d.getOwnerTypeId());
        });
    }
}
