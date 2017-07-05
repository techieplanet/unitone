/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.neo.model.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;
import javax.servlet.http.Part;

/**
 *
 * @author Swedge
 */
public class FileUploader {
    
    Properties properties;
    final String windowsUploadDir = "windows.upload.directory";
    final String windowsAccessDir = "windows.file.access.path";
    
    final String macUploadDir = "mac.upload.directory";
    final String macAccessDir = "mac.file.access.path";
    
    final String linuxUploadDir = "linux.upload.directory";
    final String linuxAccessDir = "mac.file.access.path";
    
    private String fileTypeUploadDirectory = "";
    private String fileTypeAccessDirectory = "";
    private String fileSeparator = "";
    
    public enum fileTypesEnum {
        IMAGE, PDF, EXCEL
    }
    
    public FileUploader(String fileType, boolean uploadMode) throws IOException{
        this.loadPropertiesFile();
        if(uploadMode){
            fileTypeUploadDirectory = this.getFileUploadDirectory(fileType);
        }
        else{
            fileTypeAccessDirectory = this.getFileAccessDirectory(fileType);
        }
    }
    
    public static String getSubmittedFileName(Part filePart) {
        //String fileName = filePart.getSubmittedFileName();
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString(); // Use this instead because of MSIE.
        return fileName;
    }
    
    public static String getSubmittedFileExtension(String fileName){
        return fileName.substring(fileName.lastIndexOf(".")+1);
    }
    
    public static String getSubmittedFileExtension(Part filePart){
        String fileName = getSubmittedFileName(filePart);
        return fileName.substring(fileName.lastIndexOf(".")+1);
    }
    
    
    /**
     * Uploads filePart into the default file upload directory specified in application properties file
     * @param filePart the file content sent from input type file sent from browser
     * @throws IOException 
     */
     public void uploadFile(Part filePart) throws IOException{
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString(); // Use this instead because of MSIE.
        InputStream fileContent = filePart.getInputStream();
            
        System.out.println("The uploaded file name: " + fileName);
        
        File file = new File(fileTypeUploadDirectory, fileName);

        try (InputStream input = fileContent){
            Files.copy(input, file.toPath());
        }
    }
     
    /**
     * Uploads filePart into the default file upload directory specified in application properties file 
     * and save as fileSaveName
     * @param filePart the file content sent from input type file sent from browser
     * @param fileSaveName the name to save the file in. The class will figure the extension
     * @param nameHasExtension specifies if the destination file extension is included in String fileSaveName i.e. whether filename.jpg or just filename
     * @throws IOException 
     */
    public void uploadFile(Part filePart, String fileSaveName, boolean nameHasExtension) throws IOException{
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString(); // Use this instead because of MSIE.
        InputStream fileContent = filePart.getInputStream();
        
        String extension = "";
        if(!nameHasExtension) {
            extension = getSubmittedFileExtension(fileName);
            fileSaveName += "." + extension;
        }
        
        File file = new File(fileTypeUploadDirectory, fileSaveName);
        
        
        try (InputStream input = fileContent) {
            Files.copy(input, file.toPath());
        }        
    }
     
    /**
     * Uploads filePart into the subdirectory under the default file upload directory specified in application properties file 
     * and save as fileSaveName
     * @param filePart the file content sent from input type file sent from browser
     * @param subDirectory a subdirectory in the file type default uploads directory where the image should be uploaded e.g. c:/dev/images/mysubdir
     * @param fileSaveName the name to save the file in without file extension. The class will figure the extension
     * @param nameHasExtension specifies if the destination file extension is included in String fileSaveName i.e. whether filename.jpg or just filename
     * @throws IOException 
     */
    public void uploadFile(Part filePart, String subDirectory, String fileSaveName, boolean nameHasExtension) throws IOException{
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString(); // Use this instead because of MSIE.
        InputStream fileContent = filePart.getInputStream();
        
        String extension = "";
        if(!nameHasExtension) {
            extension = getSubmittedFileExtension(fileName);
            fileSaveName += "." + extension;
        }
        
        String filedir = fileTypeUploadDirectory + fileSeparator + subDirectory;
        
        File file = new File(filedir, fileSaveName);
       
        System.out.println("fileSaveName: " + fileSaveName);
        try (InputStream input = fileContent) {
            Files.copy(input, file.toPath());
        } catch(Exception e){
            e.getMessage();
            e.printStackTrace();
        }
        
    }
     
    
     
    private void loadPropertiesFile() throws IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream input = classLoader.getResourceAsStream("com/tp/neo/properties/neo.properties"); // you could do com/tp/neo/foo.properties if your properties file is in a package folder
        properties = new Properties();
        properties.load(input);
    }
    
    private String getFileUploadDirectory(String fileType){
        OSChecker checker = new OSChecker();
        String dir =""; 
        
        if(checker.isWindows()){
            dir = properties.getProperty(this.windowsUploadDir);
            fileSeparator = "\\";
        }
        
        if(checker.isMac()){
            dir = properties.getProperty(this.macUploadDir);
            fileSeparator = "/";
        }
        
        if(checker.isUnix()){
            dir = properties.getProperty(this.linuxUploadDir);
            fileSeparator = "/";
        }
        
        if(fileType.equalsIgnoreCase(fileTypesEnum.IMAGE.toString())){
                return dir + fileSeparator + "images";
        }
                
        return "";
    }
    
    private String getFileAccessDirectory(String fileType){
        OSChecker checker = new OSChecker();
        String dir =""; 
        
        if(checker.isWindows()){
            dir = properties.getProperty(this.windowsAccessDir);
        }
        
        if(checker.isMac()){
            dir = properties.getProperty(this.macAccessDir);
        }
        
        if(checker.isUnix()){
            dir = properties.getProperty(this.linuxAccessDir);
        }
        
        if(fileType.equalsIgnoreCase(fileTypesEnum.IMAGE.toString())){
                return dir + "/images";
        }
                
        return "";
    }
    
    public String getAccessDirectoryString(){
        return fileTypeAccessDirectory;
    }
    
    public String getFileSeparator(){
        return fileSeparator;
    }
}