/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.neo.controller.helpers;

import java.io.File;
import java.io.FileReader;
import java.io.InputStream;

/**
 *
 * @author SWEDGE
 */
public class EmailParser {
     private String message = "", title = "", link = "", buttonText = "" ,start = "#LinkBegin", end = "#LinkEnd";
     private boolean linkStart = false, isLink = true, doneDealingWithLink = false, hasWrittenMessage = false , hasWrittenTitle = false , buttonTextWritten = false;
    
    private String parse(String tS){
        //setUp the message parameter
        
        if(!hasWrittenMessage)
        if (tS.contains("#message#"))
        {
            tS = tS.replace("#message#", message);
            hasWrittenMessage = true;
        }
        
        if(!hasWrittenTitle)
        if (tS.contains("#title#"))
        {
            tS = tS.replace("#title#", title);
            hasWrittenTitle = true;
        }

        //if link is not present
        //Remove all string that start from start to end
        if (!isLink && !doneDealingWithLink)
        {
            if (!doneDealingWithLink)
            {
                if (!linkStart)
                {
                    if (tS.contains(start))
                    {
                        int index = tS.indexOf(start);
                        tS = tS.substring(0, index); // remove the tag
                        linkStart = true;
                        return tS;
                    }
                }

                if (linkStart)
                {
                    //if we have get the first link we can continue to remove all the 
                    //the string we will meet on our way until we get the end statement
                    if (!tS.contains(end))
                    {
                        //lets free all this string
                        return "";
                    }
                    else
                    {
                        //lets just remove all the string up to end
                        int index = tS.indexOf(end);
                        tS = tS.substring(index + end.length());
                        linkStart = false;
                        doneDealingWithLink = true;
                        return tS;
                    }
                }
            }
            else
            {
                if (!doneDealingWithLink)
                {
                    if (!linkStart)
                    {
                        if (tS.contains(start))
                        {
                            tS = tS.replace(start, "");
                            linkStart = true;
                        }
                    }
                    else
                    {
                        if (tS.contains(start))
                        {
                            tS = tS.replace(end, "");
                            linkStart = false;
                            doneDealingWithLink = true;
                        }
                    }
                }
            }
        }
        else if( !doneDealingWithLink ) // Link is enable So we are just removing the start and end tag
            // just replace the $link$ with the link
        {
            if(!buttonTextWritten)
            if(tS.contains("#buttonText#"))
            {
            tS = tS.replace("#buttonText#" , buttonText);
            buttonTextWritten = true;
            }
            if(tS.contains("#link#"))
                tS = tS.replaceAll("#link#", link);
            if(!linkStart && !doneDealingWithLink)
            {
                if(tS.contains(start))
                {
                    tS = tS.replace(start, "");
                    linkStart = true;
                }
            }
            else if(linkStart && !doneDealingWithLink)
            {
                if(tS.contains(end))
                {
                    tS = tS.replace(end, "");
                    linkStart = false;
                    doneDealingWithLink = true;
                }
            }
            
        }
        
        return tS;
    }
    
    public  StringBuilder prepareEmail(String title , String message , String link , String textButton ){
        this.title = title;
        this.message = message;
        if(link.trim().isEmpty())
            this.isLink = false;
        else
        {
            this.isLink = true;
            this.link = link;
            this.buttonText = textButton;
        }
        
        //do Default Settings
        this.linkStart = false;
        this.doneDealingWithLink = false;
        this.hasWrittenMessage = false;
        this.hasWrittenTitle = false;
        this.buttonTextWritten = false;
        
        try
        {
        
        InputStream fileReader = EmailParser.class.getResourceAsStream("design.jsp");

        StringBuilder text = new StringBuilder();

        byte buff[] = new byte[1024];

        int i = fileReader.read(buff);

        //temp string
        // tS = ""
        while (i != -1)
        {
           String tS = new String(buff);
            text.append(parse(tS));
            buff =  new byte[1024];
            i = fileReader.read(buff);
        }

        return text;
        }
        catch (Exception  e)
        {
            e.printStackTrace();
            return new StringBuilder("Sorry An Error Occured While Processing Your Request");
        }
    }
}
