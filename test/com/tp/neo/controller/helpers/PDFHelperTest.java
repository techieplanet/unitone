/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.neo.controller.helpers;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
/**
 *
 * @author SWEDGE
 */
public class PDFHelperTest {

	public static void main(String[] args)
	{
            long ids[] = {106 , 107, 108 , 111 , 112 , 113};
            for( long id  : ids)
                processPdf(id);
            }
	
	public void exportToPdfBox(String url, String out)
	{
            OutputStream os = null;
       
              try {
               os = new FileOutputStream(out);
       
               try {
                     // There are more options on the builder than shown below.
                     PdfRendererBuilder builder = new PdfRendererBuilder();

                     builder.withUri(url);
                     builder.toStream(os);
                     builder.run();
                     
               } catch (Exception e) {
                     e.printStackTrace();
                     // LOG exception
               } finally {
                     try {
                            os.close();
                     } catch (IOException e) {
                            // swallow
                     }
               }
              }
              catch (IOException e) {
                     e.printStackTrace();
                     // LOG exception.
              }
	}
        
        public static void processPdf(Long id){
            new PDFHelper().processPDFDoc(id, "output"+id+".pdf");
            
        }
}
