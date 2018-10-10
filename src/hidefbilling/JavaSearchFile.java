/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hidefbilling;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

class JavaSearchFile
{
static String path;
 
 public static boolean isValidDB(String db_name){
    try{  
        if(new File("DBPath.txt").exists()){
         if(new File(readTextFile()).exists())
          return true;
         else return false;
     }else return false;
         
     }catch(Exception e){e.printStackTrace();}
         
   return false; 
 }
 public static String getDBPath(String db_name){
    try{  if(!isValidDB(db_name)){
           getDirPath(db_name);
           writeToTextFile(path);
           }
            if(isValidDB(db_name)){path=readTextFile();
              }
 
     }catch(Exception e){e.printStackTrace();}
     return path;
 }

 public static void search(File file,String name)
 {

  if(file.isDirectory())
  {
   File[] files=file.listFiles();
   for(File fileObj:files)
   {
   try
   {
    if(fileObj.isDirectory())
    {
    // Go search for files if dir
        if(fileObj.getName().equalsIgnoreCase(name)){
           
            if(fileObj.getAbsolutePath().contains("Database")){
            path=fileObj.getAbsolutePath();  }
           
        }
            else
            search(fileObj,name);
    }
    
   }catch(Exception e){}
   
   }

  }
 
 }
 
 public static void getDirPath(String dirName){
     System.out.println("\nStarting search....\n-------------------------\n");
     File files[]=File.listRoots();
     for(File file:files)
        search(file, dirName);
     }
 
 public static String readTextFile() throws IOException {
        String content = new String(Files.readAllBytes(Paths.get("DBPath.txt")));
        return content;
    }

    public static List<String> readTextFileByLines(String fileName) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(fileName));
        return lines;
    }

    public static void writeToTextFile(String content) throws IOException {
        Files.write(Paths.get("DBPath.txt"), content.getBytes(), StandardOpenOption.CREATE);
    }
}
