
package hidefbilling;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;


public class DBFunctions {
    
    
    public static double roundOf(double num,int n){
     return Math.round(num*Math.pow(10, n))/Math.pow(10, n);
    }
    public static String formatDate(java.util.Date date){ 
    return (date!=null)?new SimpleDateFormat("dd-MM-yyyy").format(date):"00-00-0000";    
    }
    public static String formatDate(java.sql.Date date){ 
    return (date!=null)?new SimpleDateFormat("dd-MM-yyyy").format(date):"00-00-0000";
    }
    public static String monthYear(java.sql.Date date){ 
    return new SimpleDateFormat("MM-yy").format(date);    
    }
    public static int intnotnull(Integer value){
        return (value==null)?0:value;
    }
    public static float floatnotnull(Float value){
        return (value==null)?0:value;
    }
    public static double doublenotnull(Double value){
        return (value==null)?0:value;
    }
    
    public static void main(String[] args) { //\u2245
       
    }
    public void search(File file){
        if(file.isDirectory()){
            for(File temp:file.listFiles()){
                if(temp.isDirectory())
                    search(temp);
            }
        }
        
    }
}
