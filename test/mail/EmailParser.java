/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mail;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author SWEDGE
 */
public class EmailParser {
    static String message = " Hello Am Here", title = "Welcome!", link = "Site.com", buttonText = "Login" ,start = "#LinkBegin", end = "#LinkEnd";
    static boolean linkStart = false, isLink = true, doneDealingWithLink = false, hasWrittenMessage = false , hasWrittenTitle = false , buttonTextWritten = false;
    
    public static void main(String... args) throws FileNotFoundException, IOException {
        System.out.println(prepareEmail("Order Approval" , "An Order have been by agent This and need approval" , "http://site.com" , "Approve order"));
    }

    public static String parse(String tS){
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
    
    public static StringBuilder prepareEmail(String title , String message , String link , String textButton ){
        EmailParser.title = title;
        EmailParser.message = message;
        if(link.trim().isEmpty())
            EmailParser.isLink = false;
        else
        {
            EmailParser.isLink = true;
            EmailParser.link = link;
            EmailParser.buttonText = textButton;
        }
        
        //do Default Settings
        EmailParser.linkStart = false;
        EmailParser.doneDealingWithLink = false;
        EmailParser.hasWrittenMessage = false;
        EmailParser.hasWrittenTitle = false;
        EmailParser.buttonTextWritten = false;
        
        try
        {
        File file = new File("web/views/email/design.jsp");

        FileReader fileReader = new FileReader(file);

        StringBuilder text = new StringBuilder();

        char buff[] = new char[1024];

        int i = fileReader.read(buff);

        //temp string
        // tS = ""
        while (i != -1)
        {
           String tS = new String(buff);
            text.append(parse(tS));
            buff =  new char[1024];
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
