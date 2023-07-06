package com.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Properties;

public class Helper {


    public void JSONReader(){
         String json=new File("/Users/bhaskar/IdeaProjects/ApiAutomation/src/test/resources/TestDataJSON/AddBook.json").toString();
         System.out.println(json);
     }

    public String fileReader(String path){
        File json=new File(System.getProperty("user.dir")+path);
        FileInputStream fStream= null;
        String data = null;
        try {
            fStream = new FileInputStream(json);
            data = new String(fStream.readAllBytes(), StandardCharsets.UTF_8);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(data);
        System.out.println(System.getProperty("user.dir"));
        return data;
    }

    public Properties properReader(String path){
        try {
            File file=new File(System.getProperty("user.dir")+path);
            FileInputStream ip=new FileInputStream(file);
            Properties prop=new Properties();
            prop.load(ip);
//            return prop.get(key).toString();
            return prop;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getCurrentTimestamp(){
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date());
        return timeStamp;
    }

    public String CreateFolder(String direc){
        String reportFolder=System.getProperty("user.dir")+"/src/test/resources/test-reports/"+direc;
        File f=new File(reportFolder);
        if(!f.exists()){
            System.out.println(f.mkdirs());
        }
        return reportFolder;
    }
}
