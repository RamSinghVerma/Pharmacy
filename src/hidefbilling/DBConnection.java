package hidefbilling;


import java.sql.Connection;
import java.sql.DriverManager;
import org.apache.derby.drda.NetworkServerControl;

/**
 *
 * @author Keshav
 */
public class DBConnection {
   protected static Connection con;

    public static void main(String[] args) {
        dbConnection("hidefDB");
    }
  
   
   
   protected static Connection dbConnection(String dbName){
    try{ 
//        if(JavaSearchFile.getDBPath(dbName)!=null){
//            System.out.println("JavaSearchFile.path="+JavaSearchFile.getDBPath(dbName));
         NetworkServerControl nsc=new NetworkServerControl();
         nsc.start(null);
        System.out.println("Server Started!");
        Class.forName("org.apache.derby.jdbc.ClientDriver");
        //con=DriverManager.getConnection("jdbc:derby://localhost:1527/"+JavaSearchFile.getDBPath(dbName)+";create=true","system","manager");
         con=DriverManager.getConnection("jdbc:derby://localhost:1527/hidefDB;create=true","system","manager");
    //}
      
         if(con!=null)
            System.out.println("Connection Successful!");
        else
            System.out.println("Connection not Successful!");
//        con.setSchema("SYSTEM");
//        createTable("CREATE TABLE \"COMPANY\"\n" +
//"(\n" +
//"   COM_ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY(START WITH 1,INCREMENT BY 1),\n" +
//"   REG_NO varchar(15),\n" +
//"   COM_ADDRESS varchar(50),\n" +
//"   COM_CONTACT varchar(11),\n" +
//"   COM_EMAIL varchar(50),\n" +
//"   COM_WEBSITE varchar(50),\n" +
//"   COMPANY_NAME varchar(50),\n" +
//"   CLIENT_NAME varchar(50)\n" +
//")", "COMPANY");
//        createTable("CREATE TABLE \"COMPANY_PROFILE\"\n" +
//"(\n" +
//"   COMPANY_ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY(START WITH 1,INCREMENT BY 1),\n" +
//"   COMPANY_NAME varchar(50),\n" +
//"   COMPANY_SLOGAN varchar(100),\n" +
//"   COMPANY_GST_NO varchar(50),\n" +
//"   COMPANY_CONTACT_NO varchar(12),\n" +
//"   COMPANY_MAIL varchar(50),\n" +
//"   COMPANY_ADDRESS varchar(150),\n" +
//"   COMPANY_LOGO blob(16777216),\n" +
//"   COMPANY_QR_CODE blob(16777216),\n" +
//"   COMPANY_BANK_NAME varchar(100),\n" +
//"   COMPANY_BANK_ACC_NO varchar(100),\n" +
//"   COMPANY_IFS_CODE varchar(16),\n" +
//"   COMPANY_BILL_TITLE varchar(100),\n" +
//"   COMPANY_INVOICE_FORMAT varchar(14),\n" +
//"   COMPANY_TNC varchar(255)\n" +
//")", "COMPANY_PROFILE");
//        createTable("CREATE TABLE \"INVOICE\"\n" +
//"(\n" +
//"   INVOICE_DATE date,\n" +
//"   INV_COM_NAME varchar(50),\n" +
//"   PRODUCT_NAME varchar(50),\n" +
//"   QUANTITY int,\n" +
//"   UNIT_PRICE float(52),\n" +
//"   PRODUCT_DESCRIPTION varchar(100),\n" +
//"   INV_CLIENT_NAME varchar(50),\n" +
//"   INVOICE_NO varchar(50),\n" +
//"   TAX float(52)\n" +
//")", "INVOICE");
//        createTable("CREATE TABLE \"INVOICE_PAYMENT\"\n" +
//"(\n" +
//"   PAYMENT_MODE varchar(10),\n" +
//"   DISCOUNT float(52),\n" +
//"   TAX float(52),\n" +
//"   SUB_TOTAL float(52),\n" +
//"   ADVANCE float(52),\n" +
//"   INVOICE_NO varchar(50),\n" +
//"   INVOICE_DATE date\n" +
//")", "INVOICE_PAYMENT");
//        createTable("CREATE TABLE \"TEMP\"\n" +
//"(\n" +
//"   INVOICE_DATE date,\n" +
//"   INV_COM_NAME varchar(50),\n" +
//"   PRODUCT_NAME varchar(50),\n" +
//"   QUANTITY int,\n" +
//"   UNIT_PRICE float(52),\n" +
//"   PRODUCT_DESCRIPTION varchar(100),\n" +
//"   INV_CLIENT_NAME varchar(50),\n" +
//"   INVOICE_NO varchar(50),\n" +
//"   TAX float(52)\n" +
//")", "TEMP");
//        
    }catch(Exception e){e.printStackTrace();}

    return con;
}
   

}
