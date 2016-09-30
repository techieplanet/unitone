/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tp.neo.controller.components;

/**
 *
 * @author LEKE
 */

import com.google.gson.JsonArray;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import javax.net.ssl.HttpsURLConnection;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;


public class SMSSender extends Thread{

    String server = "http://developers.cloudsms.com.ng/api.php";
    String username = "sewejeolaleke@gmail.com";
    String password = "kullovan20";
    int type= 0;
    String dlr = "1";
    String destination = "";
    String sender = "NeoForce";
    String message = "";
    public boolean messageSent = false;

    public SMSSender(){}


    public SMSSender(String dest, String message)
    {
        this.destination = dest;
        this.message = message;
    }

   
    public void run() {
        try
        {
            testHttp();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    
    public void testHttp(){
        try{
            URL url = new URL("http://developers.cloudsms.com.ng/api.php?userid=18012676&password=damilaregrace&type=5" + 
                              "&destination=" + this.destination + 
                              "&sender=" + this.sender + "&message=" + URLEncoder.encode(this.message,"UTF-8"));
            InputStream is = url.openConnection().getInputStream();

            BufferedReader reader = new BufferedReader( new InputStreamReader( is )  );

            String line = null;
            while( ( line = reader.readLine() ) != null )  {
                System.out.println(line);
            }
            reader.close();

        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    
    private void testHttps(){
      
      try {
	    URL url = new URL("https://www.interswitchgroup.com/");
	    HttpsURLConnection con = (HttpsURLConnection)url.openConnection();
             
            BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
				
            String line;

            while ((line = reader.readLine()) != null){
               System.out.println(line);
            }
            reader.close();
             
      } catch (MalformedURLException e) {
	     e.printStackTrace();
      } catch (IOException e) {
	     e.printStackTrace();
      }

   }

    /**
     * Starts the program
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String message ="hi, testing autoresponse";
        String destination = "2348126877714";
        new SMSSender(destination,message).start();
    }

    
}//end class
