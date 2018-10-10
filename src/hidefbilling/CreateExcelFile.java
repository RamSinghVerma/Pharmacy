package hidefbilling;

import  java.io.*;  
import  java.sql.*;
import java.util.Calendar;
import javax.swing.JOptionPane;
import  org.apache.poi.hssf.usermodel.HSSFWorkbook; 
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;

public class CreateExcelFile{
  static Connection con =DBConnection.dbConnection("hidefDB");
   int row_no=0;;

    public CreateExcelFile(Connection con) {
        CreateExcelFile.con=con;
    }
    public static void main(String[]args) throws SQLException{ //SELECT INVOICE_NO,ENROLLMENT_NO,STUDENT_NAME,INVOICE_DATE,COURSE_NAME,COURSE_FEE,PAYMENT_TAX FROM STUDENT_INVOICE
    Calendar cal=Calendar.getInstance();
    new CreateExcelFile(con).createFile("SELECT ROW_NUMBER() OVER() AS \"S.No\",INVOICE_NO,INVOICE_DATE,ENROLLMENT_NO,STUDENT_NAME,PAID_FEE*(1-PAYMENT_TAX*0.01)   AS \"PAID WITHOUT TAX\",PAYMENT_TAX/2 AS \"CGST(%)\",PAID_FEE*PAYMENT_TAX/2*0.01 AS \"CGST AMOUNT\",PAYMENT_TAX/2 AS \"SGST(%)\",PAID_FEE*PAYMENT_TAX/2*0.01 AS \"SGST AMOUNT\",0 AS \"IGST(%)\",0 AS \"IGST AMOUNT\",PAYMENT_TAX AS \"TOTAL TAX(%)\", PAID_FEE*PAYMENT_TAX*0.01 AS \"TOTAL TAXABLE VALUE\",PAID_FEE FROM STUDENT_INVOICE","LINGWAL CLASSES\nBy Lingwal Sir","Lingwal_Classes_INVOICE_"+cal.get(Calendar.DATE)+"-"+(cal.get(Calendar.MONTH)+1)+"-"+cal.get(Calendar.YEAR)+"H_"+cal.get(Calendar.HOUR)+".xls","Invoice_Sheet" );
   // new CreateExcelFile(con).updateExcel("Lingwal_Classes_INVOICE_"+cal.get(Calendar.DATE)+"-"+(cal.get(Calendar.MONTH)+1)+"-"+cal.get(Calendar.YEAR)+"H_"+cal.get(Calendar.HOUR)+".xls", "SELECT 'Total','','','','',SUM(PAID_FEE*(1-PAYMENT_TAX*0.01)),'',SUM(PAID_FEE*PAYMENT_TAX/2*0.01) AS \"CGST AMOUNT\",'',SUM(PAID_FEE*PAYMENT_TAX/2*0.01) AS \"SGST AMOUNT\",'',SUM(0) AS \"IGST AMOUNT\",'', SUM(PAID_FEE*PAYMENT_TAX*0.01) AS \"TOTAL TAXABLE VALUE\",SUM(PAID_FEE) FROM STUDENT_INVOICE", "Invoice_Sheet");
      
    
    }
    
public void createFile(String query,String query2,String fileName,String sheetName){
   // String passQ=query.substring(query.indexOf("WHERE"),query.indexOf("order")-1);
    //System.out.println("filename=="+fileName);
try{ 
//String filename="INVOICE_"+cal.get(Calendar.DATE)+"-"+(cal.get(Calendar.MONTH)+1)+"-"+cal.get(Calendar.YEAR)+"H_"+cal.get(Calendar.HOUR)+"M_"+cal.get(Calendar.MINUTE)+".xls" ;
HSSFWorkbook hwb=new HSSFWorkbook();
Sheet sheet =  hwb.createSheet(sheetName);
 Row row = sheet.createRow(row_no);  
 PreparedStatement ps=con.prepareStatement(query);
ResultSet rs=ps.executeQuery();
ResultSetMetaData rsmd=rs.getMetaData();
 if(!query2.isEmpty()){ 
    company_profile(query2, hwb, sheet, row, rsmd.getColumnCount());}
    //System.out.println("row="+row_no);
 row=sheet.createRow((short)++row_no); 
 PreparedStatement ps2=con.prepareStatement(query);
 ResultSet rs2=ps2.executeQuery();
 rsmd=rs2.getMetaData();
 // Creating Heading row
if(rs2.next())
   for(int col=1;col<=rsmd.getColumnCount();col++){
    cellCreate(hwb, row, col-1, rsmd.getColumnName(col),hwb.createFont());
   } 
//System.out.println("row="+row_no);
int count =0;
 PreparedStatement ps3=con.prepareStatement(query);
 ResultSet rs3=ps3.executeQuery();rsmd=rs.getMetaData();
 while(rs3.next())
     count++;
 if(count!=0){
 PreparedStatement ps4=con.prepareStatement(query);
 ResultSet rs4=ps4.executeQuery();
 while(rs4.next()){ //System.out.println("row="+row_no+" "+rs.getString(1));
    row=sheet.createRow((short)++row_no);//System.out.println("row="+row_no);
    for(int col=0;col<rsmd.getColumnCount();col++){
//        System.out.print(rs.getString(col+1)+"\t");
        if(rsmd.getColumnType(col+1)==4||rsmd.getColumnType(col+1)==5||rsmd.getColumnType(col+1)==-5||rsmd.getColumnType(col+1)==-6)
        cellCreate(hwb, row, col, rs4.getInt(col+1));
        else if(rsmd.getColumnType(col+1)==12||rsmd.getColumnType(col+1)==-1||rsmd.getColumnType(col+1)==0)
            cellCreate(hwb, row, col, rs4.getString(col+1));
        else  if(rsmd.getColumnType(col+1)==6||rsmd.getColumnType(col+1)==8||rsmd.getColumnType(col+1)==7||rsmd.getColumnType(col+1)==2)
            cellCreate(hwb, row, col, rs4.getFloat(col+1));
        else  if(rsmd.getColumnType(col+1)==91)
            cellCreate(hwb, row, col, rs4.getDate(col+1));
        
    } //System.out.println("");
 }
 // Auto adjusting Column width
  for(int col=1;col<=rsmd.getColumnCount();col++){
   sheet.autoSizeColumn(col-1);
   } 
  // Writing File
FileOutputStream fileOut =  new FileOutputStream(fileName);
hwb.write(fileOut);
fileOut.close();

System.out.println("Your excel file has been generated! ");
//  updateExcel(fileName, "SELECT 'Total','','','','',roundOf(SUM(PAID_FEE*(1-PAYMENT_TAX/(PAYMENT_TAX+100))),2),'',roundOf(SUM(PAID_FEE*PAYMENT_TAX/(2*(PAYMENT_TAX+100))),2) AS \"CGST AMOUNT\",'',roundOf(SUM(PAID_FEE*PAYMENT_TAX/(2*(PAYMENT_TAX+100))),2) AS \"SGST AMOUNT\",'',SUM(0) AS \"IGST AMOUNT\",'', roundOf(SUM(PAID_FEE*PAYMENT_TAX/(PAYMENT_TAX+100)),2) AS \"TOTAL TAXABLE VALUE\",roundOf(SUM(PAID_FEE),2) FROM STUDENT_INVOICE "+passQ, "Invoice_Sheet");
 }
 else JOptionPane.showMessageDialog(null, "No record found !", "Warning", 0);
//  JOptionPane.showMessageDialog(null, "Your excel file has been generated!", "Success", 1);
} catch ( Exception ex ) {
    //System.out.println(ex);
JOptionPane.showMessageDialog(null, ex, "Warning", 0);
}

    }
void cellCreate(HSSFWorkbook workbook, Row row,int col,int value){
         Cell cell=row.createCell((short) col);cell.setCellType(Cell.CELL_TYPE_NUMERIC);cell.setCellValue(value);
         HSSFCellStyle my_style_1 = workbook.createCellStyle();
         my_style_1.setAlignment(HSSFCellStyle.ALIGN_CENTER);
         my_style_1.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
         cell.setCellStyle(my_style_1);
}
void cellCreate(HSSFWorkbook workbook, Row row,int col,String value){
         Cell cell=row.createCell((short) col);
         if(isDouble(value)){  cell.setCellType(Cell.CELL_TYPE_NUMERIC);
         if(isInteger(value))cell.setCellValue(Integer.parseInt(value));
         else cell.setCellValue(DBFunctions.roundOf(Double.parseDouble(value),2));}
         else{    cell.setCellType(Cell.CELL_TYPE_STRING);cell.setCellValue(value);}
         HSSFCellStyle my_style_1 = workbook.createCellStyle();
         my_style_1.setAlignment(HSSFCellStyle.ALIGN_CENTER);
         my_style_1.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
         cell.setCellStyle(my_style_1);
        
}
void cellCreate(HSSFWorkbook workbook, Row row,int col,String value,Font font){ //System.out.println("row="+row);
         Cell cell=row.createCell((short) col);
         font.setBold(true);
         if(isDouble(value)){  cell.setCellType(Cell.CELL_TYPE_NUMERIC);cell.setCellValue(Integer.parseInt(value));}
         else{    cell.setCellType(Cell.CELL_TYPE_STRING);cell.setCellValue(value);}
         HSSFCellStyle my_style_1 = workbook.createCellStyle();
         my_style_1.setAlignment(HSSFCellStyle.ALIGN_CENTER);
         my_style_1.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
         my_style_1.setFont(font);
         cell.setCellStyle(my_style_1);
        
}
void cellCreate(HSSFWorkbook workbook, Row row,int col,float value){
         Cell cell=row.createCell((short) col);cell.setCellType(Cell.CELL_TYPE_NUMERIC);cell.setCellValue(DBFunctions.roundOf(value,2));
         HSSFCellStyle my_style_1 = workbook.createCellStyle();
         my_style_1.setAlignment(HSSFCellStyle.ALIGN_CENTER);
         my_style_1.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
         cell.setCellStyle(my_style_1);
}
void cellCreate(HSSFWorkbook workbook, Row row,int col,Date value){
         Cell cell=row.createCell((short) col);
         if(value!=null){
         cell.setCellType(Cell.CELL_TYPE_NUMERIC);cell.setCellValue(value);}
         else
             cell.setCellType(Cell.CELL_TYPE_BLANK);
             
         HSSFCellStyle my_style_1 = workbook.createCellStyle();
         my_style_1.setAlignment(HSSFCellStyle.ALIGN_CENTER);
         my_style_1.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
         CreationHelper createHelper = workbook.getCreationHelper();
         my_style_1.setDataFormat(createHelper.createDataFormat().getFormat("dd/MM/yyyy"));
         cell.setCellStyle(my_style_1);
}
void company_profile(String query,HSSFWorkbook hwb,Sheet sheet,Row row,int column){
    try { Cell cell=null;String title="",address="";
    if(query.contains("SELECT")){
    PreparedStatement ps=con.prepareStatement(query);
          ResultSet rs=ps.executeQuery();
          if(rs.next()){
          title=rs.getString(1)+" (GST No.: "+rs.getString(2)+")";}
    }else{ 
          title=query.substring(0, query.indexOf("\n"));
          address=query.substring(query.indexOf("\n"));}
    Font font = hwb.createFont();
    font.setBold(true);
    font.setFontHeightInPoints((short)16);
    cell =  row.createCell(1);
    cell.setCellValue(new HSSFRichTextString(title));
    HSSFCellStyle my_style_0 = hwb.createCellStyle();
    my_style_0.setAlignment(HorizontalAlignment.CENTER);
    my_style_0.setFont(font);
    cell.setCellStyle(my_style_0);
    sheet.addMergedRegion(new CellRangeAddress(0, 0, 1, column-1));
    row = sheet.createRow(++row_no);  
    cell =  row.createCell(1);
    cell.setCellValue(new HSSFRichTextString(address));
    cell.setCellStyle(my_style_0);
    sheet.addMergedRegion(new CellRangeAddress(1, 1, 1, column-1));      
    } catch (Exception e) {
        e.printStackTrace();
    }
}
private boolean isInteger(String num){
    try{ Integer.parseInt(num);    
    }catch(Exception e){return false;}
    return true;
}
private boolean isFloat(String num){
    try{ Float.parseFloat(num);    
    }catch(Exception e){return false;}
    return true;
}
private boolean isDouble(String num){
    try{ Double.parseDouble(num);    
    }catch(Exception e){return false;}
    return true;
}
public void updateExcel(String filePath,String query,String sheetName){
    //System.out.println("path=="+filePath);
    
    try { if(new File(filePath).exists()){
            FileInputStream inputStream = new FileInputStream(new File(filePath).getAbsolutePath());
            //System.out.println(""+new File(filePath).getAbsolutePath());
            HSSFWorkbook workbook = (HSSFWorkbook) WorkbookFactory.create(inputStream);
 
            Sheet sheet = workbook.getSheet(sheetName);
            int rowCount = sheet.getLastRowNum();
 
           
                Row row = sheet.createRow(++rowCount);
                PreparedStatement ps=con.prepareStatement(query);
                ResultSet rs=ps.executeQuery();
                ResultSetMetaData rsmd=rs.getMetaData();
                if(rs.next())
                    for(int columnCount = 0;columnCount<rsmd.getColumnCount();columnCount++){
                       // cellCreate(workbook, row, columnCount, rs.getString(columnCount+1));
                        Font font = workbook.createFont();
                        font.setBold(true);
                 Cell cell=row.createCell((short) columnCount);
         if(isDouble(rs.getString(columnCount+1))){  cell.setCellType(Cell.CELL_TYPE_NUMERIC);
         if(isInteger(rs.getString(columnCount+1)))cell.setCellValue(Integer.parseInt(rs.getString(columnCount+1)));
         else cell.setCellValue(DBFunctions.roundOf(Double.parseDouble(rs.getString(columnCount+1)),2));}
         else{    cell.setCellType(Cell.CELL_TYPE_STRING);cell.setCellValue(rs.getString(columnCount+1));}
         HSSFCellStyle my_style_1 = workbook.createCellStyle();
         my_style_1.setAlignment(HSSFCellStyle.ALIGN_CENTER);
         my_style_1.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
         my_style_1.setFont(font);
         cell.setCellStyle(my_style_1);
         
                    }
            inputStream.close();
 
            FileOutputStream outputStream = new FileOutputStream(filePath);
            workbook.write(outputStream);
            workbook.close();
            outputStream.close();
          JOptionPane.showMessageDialog(null, "Excel file generated !");  } 
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null,ex, "Warning", 0);
        }
}
}