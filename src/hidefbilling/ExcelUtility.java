/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hidefbilling;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import org.apache.derby.drda.NetworkServerControl;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Keshav
 */
public class ExcelUtility {
    PreparedStatement ps;
    Connection con;
    ResultSet rs;
     static XSSFRow row; 
    ExcelUtility(){
       try{// Class.forName("com.mysql.jdbc.Driver");
//            con=DriverManager.getConnection("jdbc:mysql://localhost:3306/demo","root","root");
           NetworkServerControl nsc=new NetworkServerControl();
            nsc.start(null);
        System.out.println("Server Started!");
        Class.forName("org.apache.derby.jdbc.ClientDriver");
        con=DriverManager.getConnection("jdbc:derby://localhost:1527/hidefDB;create=true","system","manager");
        if(con!=null)
            System.out.println("Connection Successful!");
        else
            System.out.println("Connection not Successful!");
        }catch(Exception e){e.printStackTrace();
        }
       // con=new MainFrame().dbConnection();
    }
    public static void main(String[] args) {
        // TODO code application logic here
        ExcelUtility eu=new ExcelUtility();
        eu.uploadExcelInDatabase("SAC_TABLE","SAC_CODE,SAC_CODE_DESC","C:\\Users\\pc1\\Documents\\Book1.xlsx","Sheet2");
    }
    public void uploadExcelInDatabase(String TABLE_NAME,String FIELD_NAMES,String url,String SHEET_NAME){
        try{  FileInputStream fis = new FileInputStream(new File(url));  
        XSSFWorkbook workbook = new XSSFWorkbook(fis);  
        XSSFSheet spreadsheet = workbook.getSheet(SHEET_NAME);  
        Iterator < Row > rowIterator = spreadsheet.iterator(); 
            DataFormatter formatter=new DataFormatter();
        while (rowIterator.hasNext())    {  
            String query="INSERT INTO "+TABLE_NAME+"("+FIELD_NAMES+") VALUES(";
            row = (XSSFRow) rowIterator.next();  
            Iterator < Cell > cellIterator = row.cellIterator();  
            while ( cellIterator.hasNext())     {    
                Cell cell = cellIterator.next(); // System.out.println("cell= "+cell);  
                switch (cell.getCellType())      {    
                    case Cell.CELL_TYPE_NUMERIC:      
                        System.out.print(       formatter.formatCellValue(cell) + " \t\t " );  
//                        formatter.formatCellValue(cell);
                        query+=(DateUtil.isCellDateFormatted(cell))?"'"+new SimpleDateFormat("yyyy-MM-dd").format(cell.getDateCellValue())+"',":formatter.formatCellValue(cell)+",";
                        break;                        
                    case Cell.CELL_TYPE_STRING:           
                        String changedUserString = cell.getStringCellValue().replace("'","''");
                        System.out.print(                changedUserString + " \t\t " ); 
                        
                        query+="'"+changedUserString+"',";
                        break; 
                    case Cell.CELL_TYPE_BLANK:
                        System.out.print(                formatter.formatCellValue(cell) + " \t\t " );  
                        query+="'"+"',";
                }
            } 
            query=query.substring(0, query.length()-1)+")";
            System.out.println(); 
        System.out.println(query); 
        ps=con.prepareStatement(query);
        ps.executeUpdate();
        }            
        fis.close(); 
         
            
        }catch(Exception e){e.printStackTrace();}
    }
    
}
