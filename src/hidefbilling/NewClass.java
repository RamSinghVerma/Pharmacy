/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hidefbilling;

import java.io.File;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.filechooser.FileSystemView;


/**
 *
 * @author Keshav
 */
public class NewClass {
    public static void main(String[] args) throws ParseException {
       String date="welcometojava";
       int end=0,start=0;
      float num=4.69999999f;
        System.out.println(getSmallestAndLargest(date, 3));
        
    }
 protected void createFolder(String folderName){
    try{ if(!new File(folderName).exists())new File(folderName).mkdir();
        
    }catch(Exception e){e.printStackTrace();}
}
 public static String getSmallestAndLargest(String s, int k) {
        String smallest = "";
        String largest = "";
        
        // Complete the function
        // 'smallest' must be the lexicographically smallest substring of length 'k'
        // 'largest' must be the lexicographically largest substring of length 'k'
     
//        for(int i=0,j=97;i<s.length();i++){
//            
//            if(s.charAt(i)==(char)j){
//                smallest=s.substring(i+1, i+k+1);
//                break;}
//        j++;}
        for(int i=0;i<s.length();i++){
            System.out.print(s.charAt(i)+"");
            for(int j=122;j>96;j--)
            if(s.charAt(i)==(char)j){
                largest=s.substring(i, i+k);
                break;}
       }
        
        
        return smallest + "\n" + largest;
    }
}
