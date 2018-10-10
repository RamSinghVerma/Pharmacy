package hidefbilling;



import java.awt.Color;
import java.awt.Component;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;



public class MainFrame extends javax.swing.JFrame {
protected static Connection con;
private PreparedStatement ps;
private ResultSet rs;
private Date exp_date;
boolean check=false;
private int result=5;
protected static String COMPANY_NAME,STATE_NAME;static String COMPANYNAME;
TransportDetails transportDetails;
    private int PT_ID;
    private int MC_ID;
    private int P_ID,T_ID;
    private int STATE_ID;
    private int HSN_ID;
    private int SAC_ID;
    protected static String MC_NAME,P_NAME;
    JList list=new JList();
    private static String LOST_NAME;
    private int VISIBLITY;
    protected String path;//=System.getProperty("user.home")+File.separator;
   
    public MainFrame() {
         path=System.getProperty("user.dir")+File.separator;
        initComponents();
        con=DBConnection.dbConnection("hidefDB");
        updateUser();
        hide_panel();reset_button_color();
        required_panel.setVisible(true);//reset_sales_order();
        required_btn.setBackground(Color.GRAY);
        disable_panel();
        autoComplete();
        focusLost10();
        focusLost11();
        focusLost6();
        focusLost8();
        focusLost13();
        setTitle("Leeman Life Sciences Pvt Ltd.");
        
  
        //        create_DBComponent("DROP FUNCTION roundOf");
//create_DBComponent("CREATE FUNCTION roundOf(value double,power integer)\n" +
//"returns double\n" +
//"language java\n" +
//"parameter style java\n" +
//"no sql\n" +
//"external name 'hidefbilling.DBFunctions.roundOf'");
//        System.out.println("1 created");
//         create_DBComponent("DROP FUNCTION doublenotnull");
//         create_DBComponent("CREATE FUNCTION doublenotnull(value FLOAT)\n" +
//"returns FLOAT \n" +
//"language java \n" +
//"parameter style java \n" +
//"no sql\n" +
//"external name 'hidefbilling.DBFunctions.doublenotnull'");
        //   create_DBComponent("DROP FUNCTION formatDate");
//          create_DBComponent("CREATE FUNCTION formatDate(value Date)\n" +
//"returns varchar \n" +
//"no sql language java \n" +
//"parameter style java \n" +
//"external name 'hidefbilling.DBFunctions.formatDate'");
          try {
        setIconImage(new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/images/logo.jpg"))).getImage());  
    } catch (IOException ex) {
        Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
    }
        getTableData("SELECT C.P_NAME,C.MC_NAME,A.PO_BATCH_ID,A.PS-B.SS,C.DATEVALUE FROM (SELECT PO_BATCH_ID,SUM(PO_BOX_QTY) AS PS FROM PURCHASE_ORDER INNER JOIN PRODUCT ON PO_P_ID=P_ID AND {fn timestampdiff(SQL_TSI_DAY, PO_EXP_DATE, CURRENT_DATE)} BETWEEN 0 AND 180 GROUP BY PO_BATCH_ID) AS A,(SELECT SO_BATCH_ID,SUM(SO_QTY) AS SS FROM SALES_ORDER INNER JOIN PRODUCT ON SO_P_ID=P_ID GROUP BY SO_BATCH_ID) AS B,(SELECT P_NAME,MC_NAME,PO_BATCH_ID,formatDate(PO_EXP_DATE) AS DATEVALUE FROM PURCHASE_ORDER INNER JOIN PRODUCT ON PO_P_ID=P_ID INNER JOIN MANUFACTURER_CUSTOMER ON PO_MC_ID=MC_ID) AS C WHERE A.PO_BATCH_ID=B.SO_BATCH_ID AND A.PO_BATCH_ID=C.PO_BATCH_ID ORDER BY C.DATEVALUE", jTable15);
        getTableData("SELECT B.P_NAME AS PRODUCT_NAME,A.PS-A.SS AS QUANTITY,B.PO_BATCH_ID AS BATCH_ID FROM (SELECT PO_P_ID,PO_BATCH_ID,SUM(PO_BOX_QTY) AS PS,SUM(SO_QTY) AS SS FROM SALES_ORDER INNER JOIN PURCHASE_ORDER ON SO_P_ID=PO_P_ID GROUP BY PO_P_ID,PO_BATCH_ID) AS A,(SELECT PO_P_ID,P_NAME,PO_BATCH_ID FROM SALES_ORDER INNER JOIN PURCHASE_ORDER ON SO_P_ID=PO_P_ID INNER JOIN PRODUCT ON PO_P_ID=P_ID AND PO_BATCH_ID=SO_BATCH_ID) AS B WHERE A.PO_BATCH_ID=B.PO_BATCH_ID AND  (A.PS-A.SS)*100/A.PS BETWEEN 0 AND 50", jTable16);
        getTableData("SELECT ROW_NUMBER() OVER() AS R,C.P_NAME AS PRODUCT_NAME,A.PS-intnotnull(B.SS) AS QUANTITY,A.PO_BATCH_ID AS BATCH_ID FROM (SELECT PO_BATCH_ID,SUM(PO_BOX_QTY) AS PS FROM PURCHASE_ORDER GROUP BY PO_BATCH_ID) AS A LEFT OUTER JOIN (SELECT SO_BATCH_ID,SUM(SO_QTY) AS SS FROM SALES_ORDER GROUP BY SO_BATCH_ID) AS B ON A.PO_BATCH_ID=B.SO_BATCH_ID LEFT OUTER JOIN (SELECT P_NAME,PO_BATCH_ID FROM PURCHASE_ORDER INNER JOIN PRODUCT ON PO_P_ID=P_ID) AS C ON C.PO_BATCH_ID=A.PO_BATCH_ID", jTable17);
        getTableData("SELECT ROW_NUMBER() OVER() AS R,P_NAME,SUM(SO_QTY),SO_BATCH_ID FROM SALES_ORDER INNER JOIN PRODUCT ON SO_P_ID=P_ID GROUP BY SO_BATCH_ID,P_NAME", jTable5);
    }
    public MainFrame(String text){
        
    }
    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jLabel26 = new javax.swing.JLabel();
        jTextField14 = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jDateChooser3 = new com.toedter.calendar.JDateChooser();
        jDateChooser4 = new com.toedter.calendar.JDateChooser();
        jButton29 = new javax.swing.JButton();
        jButton30 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jTextField7 = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        jTextField8 = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        jTextField9 = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        jTextField10 = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        jTextField11 = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        jTextField12 = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jTextField13 = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        jButton8 = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTable4 = new javax.swing.JTable();
        jTextField23 = new javax.swing.JTextField();
        jComboBox2 = new javax.swing.JComboBox();
        sale_to_customer = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jTextField17 = new javax.swing.JTextField();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jTextField22 = new javax.swing.JTextField();
        jLabel44 = new javax.swing.JLabel();
        jTextField35 = new javax.swing.JTextField();
        jLabel45 = new javax.swing.JLabel();
        jTextField36 = new javax.swing.JTextField();
        jButton24 = new javax.swing.JButton();
        jButton25 = new javax.swing.JButton();
        jButton26 = new javax.swing.JButton();
        jButton27 = new javax.swing.JButton();
        jLabel52 = new javax.swing.JLabel();
        jTextField20 = new javax.swing.JTextField();
        jLabel57 = new javax.swing.JLabel();
        jButton31 = new javax.swing.JButton();
        jDateChooser7 = new com.toedter.calendar.JDateChooser();
        jTextField67 = new javax.swing.JTextField();
        jLabel97 = new javax.swing.JLabel();
        jScrollPane8 = new javax.swing.JScrollPane();
        jTable6 = new javax.swing.JTable();
        jComboBox6 = new javax.swing.JComboBox();
        jComboBox8 = new javax.swing.JComboBox();
        jLabel100 = new javax.swing.JLabel();
        jLabel102 = new javax.swing.JLabel();
        jLabel103 = new javax.swing.JLabel();
        jComboBox20 = new javax.swing.JComboBox();
        jLabel147 = new javax.swing.JLabel();
        jLabel148 = new javax.swing.JLabel();
        jLabel149 = new javax.swing.JLabel();
        jLabel59 = new javax.swing.JLabel();
        jComboBox21 = new javax.swing.JComboBox();
        jButton2 = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jButton28 = new javax.swing.JButton();
        jLabel53 = new javax.swing.JLabel();
        jTextField42 = new javax.swing.JTextField();
        jLabel56 = new javax.swing.JLabel();
        jButton51 = new javax.swing.JButton();
        jLabel46 = new javax.swing.JLabel();
        jTextField68 = new javax.swing.JTextField();
        jLabel98 = new javax.swing.JLabel();
        jLabel99 = new javax.swing.JLabel();
        jLabel104 = new javax.swing.JLabel();
        jLabel105 = new javax.swing.JLabel();
        jLabel106 = new javax.swing.JLabel();
        jLabel107 = new javax.swing.JLabel();
        jComboBox7 = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        user_name = new javax.swing.JTextField();
        purchase_order = new javax.swing.JPanel();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        jTextField19 = new javax.swing.JTextField();
        jComboBox10 = new javax.swing.JComboBox<String>();
        jComboBox11 = new javax.swing.JComboBox();
        jTextField37 = new javax.swing.JTextField();
        jLabel49 = new javax.swing.JLabel();
        jTextField38 = new javax.swing.JTextField();
        jLabel50 = new javax.swing.JLabel();
        jTextField39 = new javax.swing.JTextField();
        jLabel51 = new javax.swing.JLabel();
        jTextField40 = new javax.swing.JTextField();
        jLabel54 = new javax.swing.JLabel();
        jTextField41 = new javax.swing.JTextField();
        jLabel55 = new javax.swing.JLabel();
        jTextField43 = new javax.swing.JTextField();
        jLabel60 = new javax.swing.JLabel();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jLabel62 = new javax.swing.JLabel();
        jTextField46 = new javax.swing.JTextField();
        jLabel63 = new javax.swing.JLabel();
        jTextField47 = new javax.swing.JTextField();
        jLabel64 = new javax.swing.JLabel();
        jMonthChooser1 = new com.toedter.calendar.JMonthChooser();
        jComboBox12 = new javax.swing.JComboBox();
        jScrollPane9 = new javax.swing.JScrollPane();
        jTable7 = new javax.swing.JTable();
        jButton33 = new javax.swing.JButton();
        jButton34 = new javax.swing.JButton();
        jButton35 = new javax.swing.JButton();
        jButton36 = new javax.swing.JButton();
        jLabel65 = new javax.swing.JLabel();
        jLabel66 = new javax.swing.JLabel();
        jTextField49 = new javax.swing.JTextField();
        total_purchase_amount = new javax.swing.JLabel();
        purchase_grand_total = new javax.swing.JLabel();
        jButton37 = new javax.swing.JButton();
        pur_expence_name_lbl = new javax.swing.JLabel();
        pur_expence_name_txt = new javax.swing.JTextField();
        jButton50 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        pur_expence_amount_txt = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        purchase_hsn_code = new javax.swing.JTextField();
        pur_insurance_name_lbl = new javax.swing.JLabel();
        purchase_insurance_name = new javax.swing.JTextField();
        purchase_insurance_charge_lbl = new javax.swing.JLabel();
        purchase_insurance_charge = new javax.swing.JTextField();
        product_details = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        jLabel72 = new javax.swing.JLabel();
        jTextField51 = new javax.swing.JTextField();
        jLabel73 = new javax.swing.JLabel();
        jTextField52 = new javax.swing.JTextField();
        jLabel74 = new javax.swing.JLabel();
        jTextField53 = new javax.swing.JTextField();
        jScrollPane10 = new javax.swing.JScrollPane();
        jTable8 = new javax.swing.JTable();
        jButton38 = new javax.swing.JButton();
        jButton39 = new javax.swing.JButton();
        jButton40 = new javax.swing.JButton();
        jLabel75 = new javax.swing.JLabel();
        jTextField54 = new javax.swing.JTextField();
        jButton48 = new javax.swing.JButton();
        manufacturerOrCustomer = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        jLabel76 = new javax.swing.JLabel();
        jTextField55 = new javax.swing.JTextField();
        jLabel77 = new javax.swing.JLabel();
        jTextField56 = new javax.swing.JTextField();
        jLabel78 = new javax.swing.JLabel();
        jTextField57 = new javax.swing.JTextField();
        jLabel79 = new javax.swing.JLabel();
        jScrollPane11 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel80 = new javax.swing.JLabel();
        jTextField58 = new javax.swing.JTextField();
        jLabel81 = new javax.swing.JLabel();
        jTextField59 = new javax.swing.JTextField();
        jLabel82 = new javax.swing.JLabel();
        jTextField60 = new javax.swing.JTextField();
        jLabel83 = new javax.swing.JLabel();
        jTextField61 = new javax.swing.JTextField();
        jScrollPane12 = new javax.swing.JScrollPane();
        jTable9 = new javax.swing.JTable();
        jButton41 = new javax.swing.JButton();
        jButton42 = new javax.swing.JButton();
        jButton43 = new javax.swing.JButton();
        jLabel84 = new javax.swing.JLabel();
        jTextField62 = new javax.swing.JTextField();
        jButton49 = new javax.swing.JButton();
        jLabel96 = new javax.swing.JLabel();
        jComboBox17 = new javax.swing.JComboBox();
        jLabel153 = new javax.swing.JLabel();
        jTextField18 = new javax.swing.JTextField();
        jLabel154 = new javax.swing.JLabel();
        jTextField21 = new javax.swing.JTextField();
        jLabel155 = new javax.swing.JLabel();
        jTextField44 = new javax.swing.JTextField();
        sales_return = new javax.swing.JPanel();
        jPanel16 = new javax.swing.JPanel();
        jLabel85 = new javax.swing.JLabel();
        jComboBox13 = new javax.swing.JComboBox();
        jLabel86 = new javax.swing.JLabel();
        jComboBox14 = new javax.swing.JComboBox();
        jLabel87 = new javax.swing.JLabel();
        jComboBox15 = new javax.swing.JComboBox();
        jLabel88 = new javax.swing.JLabel();
        jDateChooser8 = new com.toedter.calendar.JDateChooser();
        jLabel89 = new javax.swing.JLabel();
        jTextField63 = new javax.swing.JTextField();
        jLabel90 = new javax.swing.JLabel();
        jTextField64 = new javax.swing.JTextField();
        jLabel91 = new javax.swing.JLabel();
        jTextField65 = new javax.swing.JTextField();
        jScrollPane13 = new javax.swing.JScrollPane();
        jTable10 = new javax.swing.JTable();
        jLabel92 = new javax.swing.JLabel();
        jTextField66 = new javax.swing.JTextField();
        jButton45 = new javax.swing.JButton();
        jButton46 = new javax.swing.JButton();
        jButton47 = new javax.swing.JButton();
        jLabel93 = new javax.swing.JLabel();
        jLabel94 = new javax.swing.JLabel();
        jLabel95 = new javax.swing.JLabel();
        jButton63 = new javax.swing.JButton();
        jLabel61 = new javax.swing.JLabel();
        jLabel151 = new javax.swing.JLabel();
        jLabel152 = new javax.swing.JLabel();
        party_management_panel = new javax.swing.JPanel();
        jPanel17 = new javax.swing.JPanel();
        jLabel110 = new javax.swing.JLabel();
        jComboBox18 = new javax.swing.JComboBox();
        jLabel111 = new javax.swing.JLabel();
        jTextField69 = new javax.swing.JTextField();
        jLabel112 = new javax.swing.JLabel();
        jLabel114 = new javax.swing.JLabel();
        jLabel116 = new javax.swing.JLabel();
        jLabel119 = new javax.swing.JLabel();
        jLabel133 = new javax.swing.JLabel();
        jLabel134 = new javax.swing.JLabel();
        jLabel135 = new javax.swing.JLabel();
        jTextField70 = new javax.swing.JTextField();
        jButton62 = new javax.swing.JButton();
        jButton64 = new javax.swing.JButton();
        jScrollPane14 = new javax.swing.JScrollPane();
        jTable11 = new javax.swing.JTable();
        jLabel136 = new javax.swing.JLabel();
        jDateChooser10 = new com.toedter.calendar.JDateChooser();
        jLabel137 = new javax.swing.JLabel();
        jDateChooser11 = new com.toedter.calendar.JDateChooser();
        jLabel138 = new javax.swing.JLabel();
        jTextField71 = new javax.swing.JTextField();
        jButton72 = new javax.swing.JButton();
        jButton73 = new javax.swing.JButton();
        jLabel146 = new javax.swing.JLabel();
        jComboBox19 = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        reports_panel = new javax.swing.JPanel();
        jLabel108 = new javax.swing.JLabel();
        jDateChooser6 = new com.toedter.calendar.JDateChooser();
        jLabel109 = new javax.swing.JLabel();
        jDateChooser9 = new com.toedter.calendar.JDateChooser();
        jButton54 = new javax.swing.JButton();
        jButton55 = new javax.swing.JButton();
        jButton56 = new javax.swing.JButton();
        jButton57 = new javax.swing.JButton();
        jButton58 = new javax.swing.JButton();
        jButton59 = new javax.swing.JButton();
        jButton60 = new javax.swing.JButton();
        jButton61 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        purchase_panel = new javax.swing.JPanel();
        jButton65 = new javax.swing.JButton();
        jButton66 = new javax.swing.JButton();
        jLabel139 = new javax.swing.JLabel();
        jDateChooser12 = new com.toedter.calendar.JDateChooser();
        jLabel140 = new javax.swing.JLabel();
        jDateChooser13 = new com.toedter.calendar.JDateChooser();
        jButton67 = new javax.swing.JButton();
        jScrollPane15 = new javax.swing.JScrollPane();
        jTable12 = new javax.swing.JTable();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        puchase_search_invoice = new javax.swing.JTextField();
        purchase_invoice_delete_btn = new javax.swing.JButton();
        sale_panel = new javax.swing.JPanel();
        jButton68 = new javax.swing.JButton();
        jLabel141 = new javax.swing.JLabel();
        jDateChooser14 = new com.toedter.calendar.JDateChooser();
        jLabel142 = new javax.swing.JLabel();
        jDateChooser15 = new com.toedter.calendar.JDateChooser();
        jButton71 = new javax.swing.JButton();
        jScrollPane16 = new javax.swing.JScrollPane();
        jTable13 = new javax.swing.JTable();
        search_by_user_name = new javax.swing.JTextField();
        user_panel = new javax.swing.JPanel();
        jPanel18 = new javax.swing.JPanel();
        jLabel143 = new javax.swing.JLabel();
        jLabel144 = new javax.swing.JLabel();
        jLabel145 = new javax.swing.JLabel();
        jTextField72 = new javax.swing.JTextField();
        jPasswordField1 = new javax.swing.JPasswordField();
        jPasswordField2 = new javax.swing.JPasswordField();
        jButton75 = new javax.swing.JButton();
        jButton76 = new javax.swing.JButton();
        jButton77 = new javax.swing.JButton();
        jButton78 = new javax.swing.JButton();
        jScrollPane19 = new javax.swing.JScrollPane();
        jTable14 = new javax.swing.JTable();
        jPanel11 = new javax.swing.JPanel();
        jCheckBox1 = new javax.swing.JCheckBox();
        jCheckBox2 = new javax.swing.JCheckBox();
        jCheckBox3 = new javax.swing.JCheckBox();
        jCheckBox4 = new javax.swing.JCheckBox();
        jCheckBox5 = new javax.swing.JCheckBox();
        jButton1 = new javax.swing.JButton();
        jCheckBox6 = new javax.swing.JCheckBox();
        jCheckBox7 = new javax.swing.JCheckBox();
        jCheckBox8 = new javax.swing.JCheckBox();
        jCheckBox9 = new javax.swing.JCheckBox();
        required_panel = new javax.swing.JPanel();
        jScrollPane20 = new javax.swing.JScrollPane();
        jTable15 = new javax.swing.JTable();
        jScrollPane21 = new javax.swing.JScrollPane();
        jTable16 = new javax.swing.JTable();
        jLabel101 = new javax.swing.JLabel();
        jLabel150 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable5 = new javax.swing.JTable();
        jScrollPane7 = new javax.swing.JScrollPane();
        jTable17 = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        company_profile = new javax.swing.JPanel();
        jLabel113 = new javax.swing.JLabel();
        jTextField25 = new javax.swing.JTextField();
        jLabel115 = new javax.swing.JLabel();
        jTextField26 = new javax.swing.JTextField();
        jLabel117 = new javax.swing.JLabel();
        jTextField27 = new javax.swing.JTextField();
        jLabel118 = new javax.swing.JLabel();
        jTextField28 = new javax.swing.JTextField();
        jLabel120 = new javax.swing.JLabel();
        jTextField29 = new javax.swing.JTextField();
        jLabel121 = new javax.swing.JLabel();
        jScrollPane17 = new javax.swing.JScrollPane();
        jTextArea5 = new javax.swing.JTextArea();
        jLabel122 = new javax.swing.JLabel();
        jButton13 = new javax.swing.JButton();
        jLabel123 = new javax.swing.JLabel();
        jButton14 = new javax.swing.JButton();
        jLabel124 = new javax.swing.JLabel();
        jTextField30 = new javax.swing.JTextField();
        jLabel125 = new javax.swing.JLabel();
        jTextField31 = new javax.swing.JTextField();
        jLabel126 = new javax.swing.JLabel();
        jTextField32 = new javax.swing.JTextField();
        jLabel127 = new javax.swing.JLabel();
        jTextField33 = new javax.swing.JTextField();
        jLabel128 = new javax.swing.JLabel();
        jScrollPane18 = new javax.swing.JScrollPane();
        jTextArea6 = new javax.swing.JTextArea();
        jLabel129 = new javax.swing.JLabel();
        jButton15 = new javax.swing.JButton();
        jLabel131 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jTextField34 = new javax.swing.JTextField();
        jButton11 = new javax.swing.JButton();
        jLabel30 = new javax.swing.JLabel();
        jTextField15 = new javax.swing.JTextField();
        jLabel31 = new javax.swing.JLabel();
        jDateChooser2 = new com.toedter.calendar.JDateChooser();
        jLabel6 = new javax.swing.JLabel();
        jComboBox5 = new javax.swing.JComboBox();
        jLabel130 = new javax.swing.JLabel();
        jLabel132 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        company_profile_btn = new javax.swing.JButton();
        sale_btn = new javax.swing.JButton();
        exit_btn = new javax.swing.JButton();
        logout_btn = new javax.swing.JButton();
        purchase_btn = new javax.swing.JButton();
        report_btn = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        sales_rtn_btn = new javax.swing.JButton();
        party_mng_btn = new javax.swing.JButton();
        add_company_btn = new javax.swing.JButton();
        productntype_btn = new javax.swing.JButton();
        hsn_sac_btn = new javax.swing.JButton();
        manage_user_btn = new javax.swing.JButton();
        view_invoice_btn = new javax.swing.JButton();
        required_btn = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenuItem7 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setExtendedState(MAXIMIZED_BOTH);

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jPanel9.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel9.setLayout(new java.awt.CardLayout());

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Invoice No", "Company Name", "Total Quantity Sold", "Total Price"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable3MouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(jTable3);

        jLabel26.setText("Search By Invoice No or Company Name");

        jTextField14.setText("jTextField14");
        jTextField14.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField14KeyReleased(evt);
            }
        });

        jLabel27.setText("Date From");

        jLabel28.setText("To");

        jDateChooser3.setDateFormatString("dd-MMM-yyyy");
        jDateChooser3.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jDateChooser3PropertyChange(evt);
            }
        });

        jDateChooser4.setDateFormatString("dd-MMM-yyyy");
        jDateChooser4.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jDateChooser4PropertyChange(evt);
            }
        });

        jButton29.setText("View Report");
        jButton29.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton29ActionPerformed(evt);
            }
        });

        jButton30.setText("Delete");
        jButton30.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton30ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane5)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField14, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(38, 38, 38)
                                .addComponent(jLabel27)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jDateChooser3, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jDateChooser4, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(jButton29)
                                .addGap(29, 29, 29)
                                .addComponent(jButton30)))
                        .addGap(0, 145, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(52, 52, 52)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel26)
                            .addComponent(jTextField14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel27))
                        .addComponent(jDateChooser3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jDateChooser4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton29)
                    .addComponent(jButton30))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel9.add(jPanel6, "card4");

        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel18.setText("Client Name");

        jTextField7.setText("jTextField7");

        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel19.setText("Company Name");

        jTextField8.setText("jTextField8");

        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel20.setText("Registration No");

        jTextField9.setText("jTextField9");

        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel21.setText("Contact No");

        jTextField10.setText("jTextField10");

        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel22.setText("E-mail");

        jTextField11.setText("jTextField11");

        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel23.setText("Website");

        jTextField12.setText("jTextField12");

        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel24.setText("Address");

        jTextArea2.setColumns(20);
        jTextArea2.setRows(5);
        jScrollPane3.setViewportView(jTextArea2);

        jButton5.setText("Submit");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setText("Update");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton7.setText("Delete");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Register No", "Company Name", "Email", "Website"
            }
        ));
        jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable2MouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(jTable2);

        jTextField13.setText("jTextField13");
        jTextField13.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField13KeyReleased(evt);
            }
        });

        jLabel25.setText("Search By Registration no or Company");

        jButton8.setText("Reset");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, 112, Short.MAX_VALUE)
                            .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField7)
                            .addComponent(jTextField8)
                            .addComponent(jTextField9)
                            .addComponent(jTextField10, javax.swing.GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE)
                            .addComponent(jTextField11)
                            .addComponent(jTextField12)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jButton7)
                            .addComponent(jButton5))
                        .addGap(48, 48, 48)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButton6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(121, 121, 121)))
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(jTextField13, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(94, 94, 94))
        );

        jPanel4Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jScrollPane3, jTextField10, jTextField11, jTextField12, jTextField7, jTextField8, jTextField9});

        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel18)
                        .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel19)
                            .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel20)
                            .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel21)
                            .addComponent(jTextField10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel22)
                            .addComponent(jTextField11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel23)
                            .addComponent(jTextField12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel24)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(30, 30, 30)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton5)
                            .addComponent(jButton6))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton7)
                            .addComponent(jButton8)))
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 331, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(155, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1066, Short.MAX_VALUE)
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel5Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 564, Short.MAX_VALUE)
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel5Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        jPanel9.add(jPanel5, "card3");

        jTable4.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "HSN/SAC Code", "Description"
            }
        ));
        jScrollPane6.setViewportView(jTable4);
        if (jTable4.getColumnModel().getColumnCount() > 0) {
            jTable4.getColumnModel().getColumn(0).setPreferredWidth(100);
            jTable4.getColumnModel().getColumn(0).setMaxWidth(100);
        }

        jTextField23.setText("jTextField23");
        jTextField23.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField23KeyReleased(evt);
            }
        });

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Goods", "Services" }));

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane6)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField23, javax.swing.GroupLayout.PREFERRED_SIZE, 490, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 381, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 394, Short.MAX_VALUE)
                .addGap(96, 96, 96))
        );

        jPanel9.add(jPanel8, "card6");

        jTextField17.setText("jTextField17");
        jTextField17.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField17KeyReleased(evt);
            }
        });

        jLabel34.setText("Invoice No");

        jLabel35.setText("Product");

        jLabel36.setText("Batch ID");

        jLabel37.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel37.setText("Expiry");

        jLabel40.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel40.setText("MRP");

        jLabel43.setText("Rate");

        jTextField22.setText("jTextField22");
        jTextField22.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField22FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField22FocusLost(evt);
            }
        });

        jLabel44.setText("Quantity Sold");

        jTextField35.setText("jTextField35");
        jTextField35.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField35FocusLost(evt);
            }
        });
        jTextField35.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField35KeyTyped(evt);
            }
        });

        jLabel45.setText("Quantity Free");

        jTextField36.setText("jTextField36");
        jTextField36.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField36FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField36FocusLost(evt);
            }
        });
        jTextField36.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField36KeyTyped(evt);
            }
        });

        jButton24.setText("Update");
        jButton24.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton24ActionPerformed(evt);
            }
        });
        jButton24.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jButton24KeyPressed(evt);
            }
        });

        jButton25.setText("Delete");
        jButton25.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jButton25ItemStateChanged(evt);
            }
        });
        jButton25.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton25ActionPerformed(evt);
            }
        });
        jButton25.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jButton25KeyPressed(evt);
            }
        });

        jButton26.setText("Transport");
        jButton26.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton26ActionPerformed(evt);
            }
        });
        jButton26.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jButton26KeyPressed(evt);
            }
        });

        jButton27.setText("Reset");
        jButton27.setNextFocusableComponent(jTextField42);
        jButton27.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton27ActionPerformed(evt);
            }
        });
        jButton27.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jButton27KeyPressed(evt);
            }
        });

        jLabel52.setText("GST");

        jTextField20.setText("0");
        jTextField20.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField20FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField20FocusLost(evt);
            }
        });

        jLabel57.setText("Invoice Date");

        jButton31.setText("Add");
        jButton31.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton31ActionPerformed(evt);
            }
        });
        jButton31.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jButton31KeyPressed(evt);
            }
        });

        jDateChooser7.setDateFormatString("dd/MM/yyyy");
        jDateChooser7.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jDateChooser7FocusLost(evt);
            }
        });

        jTextField67.setText("jTextField67");
        jTextField67.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField67FocusLost(evt);
            }
        });

        jLabel97.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel97.setText("Customer Details");

        jTable6.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "S.No", "Product", "Batch ID", "Expiry", "MRP", "Rate", "Qty Sold", "Qty Free", "Tax", "Amount"
            }
        ));
        jTable6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable6MouseClicked(evt);
            }
        });
        jScrollPane8.setViewportView(jTable6);
        if (jTable6.getColumnModel().getColumnCount() > 0) {
            jTable6.getColumnModel().getColumn(0).setPreferredWidth(40);
            jTable6.getColumnModel().getColumn(0).setMaxWidth(40);
            jTable6.getColumnModel().getColumn(6).setPreferredWidth(60);
            jTable6.getColumnModel().getColumn(6).setMaxWidth(60);
            jTable6.getColumnModel().getColumn(7).setPreferredWidth(60);
            jTable6.getColumnModel().getColumn(7).setMaxWidth(60);
        }

        jComboBox6.setEditable(true);
        jComboBox6.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Type Name" }));
        jComboBox6.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox6ItemStateChanged(evt);
            }
        });

        jComboBox8.setEditable(true);
        jComboBox8.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Type Product Name" }));
        jComboBox8.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox8ItemStateChanged(evt);
            }
        });

        jLabel100.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel100.setText("0");

        jLabel102.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel102.setText("0");

        jLabel103.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel103.setText("Qty");

        jComboBox20.setEditable(true);
        jComboBox20.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select Batch ID" }));
        jComboBox20.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox20ItemStateChanged(evt);
            }
        });

        jLabel147.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel147.setText("jLabel147");

        jLabel148.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel148.setText("Rate");

        jLabel149.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel149.setText("0");

        jLabel59.setText("Product Code");

        jComboBox21.setEditable(true);
        jComboBox21.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Type Code" }));
        jComboBox21.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox21ItemStateChanged(evt);
            }
        });

        jButton2.setText("Import Invoice");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel8.setText("HSN/SAC");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane8)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel12Layout.createSequentialGroup()
                                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(10, 10, 10)
                                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextField17)
                                    .addComponent(jComboBox8, 0, 188, Short.MAX_VALUE))
                                .addGap(6, 6, 6)
                                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel12Layout.createSequentialGroup()
                                        .addComponent(jLabel36, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jComboBox20, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel12Layout.createSequentialGroup()
                                        .addComponent(jLabel97)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jComboBox6, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel12Layout.createSequentialGroup()
                                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jTextField67, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel12Layout.createSequentialGroup()
                                        .addComponent(jLabel57, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jDateChooser7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel12Layout.createSequentialGroup()
                                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                                        .addComponent(jLabel44, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jTextField35, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(118, 118, 118))
                                    .addGroup(jPanel12Layout.createSequentialGroup()
                                        .addComponent(jLabel52, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jTextField20, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                                .addComponent(jLabel45, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField36, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel43, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jTextField22, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel37, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 79, Short.MAX_VALUE)
                            .addComponent(jLabel40, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel148, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel103, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel59, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(jButton31, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton24)
                        .addGap(18, 18, 18)
                        .addComponent(jButton25, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton26)
                        .addGap(18, 18, 18)
                        .addComponent(jButton27, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(17, 17, 17)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel147, javax.swing.GroupLayout.DEFAULT_SIZE, 153, Short.MAX_VALUE)
                    .addComponent(jLabel100, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel149, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel102, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jComboBox21, 0, 153, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel12Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel34, jLabel35, jLabel44, jLabel52});

        jPanel12Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jTextField20, jTextField35});

        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTextField17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel34)
                        .addComponent(jLabel57, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel97)
                        .addComponent(jComboBox6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jDateChooser7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel59, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jComboBox21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel36)
                        .addComponent(jTextField67, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel35)
                        .addComponent(jComboBox8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jComboBox20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel147)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel44)
                        .addComponent(jTextField35, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel45)
                        .addComponent(jTextField36, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel43)
                        .addComponent(jTextField22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel40)
                        .addComponent(jLabel100)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel52)
                    .addComponent(jTextField20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel148)
                    .addComponent(jLabel149))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel103)
                    .addComponent(jLabel102))
                .addGap(1, 1, 1)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton24)
                    .addComponent(jButton25)
                    .addComponent(jButton26)
                    .addComponent(jButton27)
                    .addComponent(jButton31)
                    .addComponent(jButton2))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jButton28.setText("Submit");
        jButton28.setNextFocusableComponent(jTextField17);
        jButton28.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton28ActionPerformed(evt);
            }
        });
        jButton28.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jButton28KeyPressed(evt);
            }
        });

        jLabel53.setText("Total Amount");

        jTextField42.setText("jTextField42");
        jTextField42.setNextFocusableComponent(jTextField68);
        jTextField42.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField42FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField42FocusLost(evt);
            }
        });
        jTextField42.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField42KeyReleased(evt);
            }
        });

        jLabel56.setText("0");

        jButton51.setText("Preview");
        jButton51.setNextFocusableComponent(jButton28);
        jButton51.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton51ActionPerformed(evt);
            }
        });
        jButton51.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jButton51KeyPressed(evt);
            }
        });

        jLabel46.setText("Discount");

        jTextField68.setText("0");
        jTextField68.setNextFocusableComponent(jButton51);
        jTextField68.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField68FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField68FocusLost(evt);
            }
        });
        jTextField68.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField68KeyReleased(evt);
            }
        });

        jLabel98.setText("Remaining");

        jLabel99.setText("0");

        jLabel104.setText("Balance");

        jLabel105.setText("0");

        jLabel106.setText("Current Amount");

        jLabel107.setText("0");

        jComboBox7.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Payment Mode", "Cash", "Check", "CREDIT", "DEBIT" }));

        jLabel3.setText("Seller Name");

        javax.swing.GroupLayout sale_to_customerLayout = new javax.swing.GroupLayout(sale_to_customer);
        sale_to_customer.setLayout(sale_to_customerLayout);
        sale_to_customerLayout.setHorizontalGroup(
            sale_to_customerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sale_to_customerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(sale_to_customerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel12, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(sale_to_customerLayout.createSequentialGroup()
                        .addGroup(sale_to_customerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel104, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel53, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(sale_to_customerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel56, javax.swing.GroupLayout.DEFAULT_SIZE, 77, Short.MAX_VALUE)
                            .addComponent(jLabel105, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(sale_to_customerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextField42, javax.swing.GroupLayout.DEFAULT_SIZE, 128, Short.MAX_VALUE)
                            .addComponent(jLabel106, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(sale_to_customerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(sale_to_customerLayout.createSequentialGroup()
                                .addComponent(jLabel46, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTextField68, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(sale_to_customerLayout.createSequentialGroup()
                                .addComponent(jLabel107, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jComboBox7, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(sale_to_customerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(sale_to_customerLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel98, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel99, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(40, 40, 40)
                                .addComponent(jButton51, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButton28, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(sale_to_customerLayout.createSequentialGroup()
                                .addGap(17, 17, 17)
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(user_name, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        sale_to_customerLayout.setVerticalGroup(
            sale_to_customerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sale_to_customerLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(sale_to_customerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel104)
                    .addComponent(jLabel105)
                    .addComponent(jLabel106)
                    .addComponent(jLabel107)
                    .addComponent(jComboBox7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(user_name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(sale_to_customerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton28)
                    .addComponent(jLabel53)
                    .addComponent(jTextField42, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel56)
                    .addComponent(jButton51)
                    .addComponent(jLabel46)
                    .addComponent(jTextField68, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel98)
                    .addComponent(jLabel99, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel9.add(sale_to_customer, "card7");

        jLabel38.setText("Invoice No");

        jLabel39.setText("Manufacturer/com.");

        jLabel47.setText("Product");

        jLabel48.setText("Batch ID");

        jTextField19.setText("jTextField19");

        jComboBox10.setEditable(true);
        jComboBox10.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select Manufacturer" }));
        jComboBox10.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox10ItemStateChanged(evt);
            }
        });
        jComboBox10.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jComboBox10FocusLost(evt);
            }
        });
        jComboBox10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox10ActionPerformed(evt);
            }
        });

        jComboBox11.setEditable(true);
        jComboBox11.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select Product" }));
        jComboBox11.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox11ItemStateChanged(evt);
            }
        });

        jTextField37.setText("jTextField37");

        jLabel49.setText("Strip Qty.");

        jTextField38.setText("jTextField38");
        jTextField38.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField38KeyTyped(evt);
            }
        });

        jLabel50.setText("Free S Qty.");

        jTextField39.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField39FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField39FocusLost(evt);
            }
        });
        jTextField39.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField39KeyTyped(evt);
            }
        });

        jLabel51.setText("Strip Rate");

        jTextField40.setText("jTextField40");
        jTextField40.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField40FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField40FocusLost(evt);
            }
        });

        jLabel54.setText("Strip MRP");

        jTextField41.setText("jTextField41");
        jTextField41.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField41FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField41FocusLost(evt);
            }
        });

        jLabel55.setText("Pack");

        jTextField43.setText("jTextField43");
        jTextField43.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField43FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField43FocusLost(evt);
            }
        });
        jTextField43.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField43KeyReleased(evt);
            }
        });

        jLabel60.setText("Invoice Date");

        jDateChooser1.setDateFormatString("dd/MM/yyyy");

        jLabel62.setText("GST");

        jTextField46.setText("jTextField46");

        jLabel63.setText("Discount");

        jLabel64.setText("Exp. Date");

        jComboBox12.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "2025", "2024", "2023", "2022", "2021", "2020", "2019", "2018", "2017" }));

        jTable7.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "S.No", "Product Name", "Batch ID", "Strip Qty", "Strip Rate", "Strip MRP", "Free Qty", "Exp. Date", "GST(%)", "Amount(Ex GST)", "Amount(In GST)"
            }
        ));
        jTable7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable7MouseClicked(evt);
            }
        });
        jScrollPane9.setViewportView(jTable7);
        if (jTable7.getColumnModel().getColumnCount() > 0) {
            jTable7.getColumnModel().getColumn(0).setPreferredWidth(40);
            jTable7.getColumnModel().getColumn(0).setMaxWidth(40);
            jTable7.getColumnModel().getColumn(2).setPreferredWidth(80);
            jTable7.getColumnModel().getColumn(2).setMaxWidth(80);
            jTable7.getColumnModel().getColumn(3).setPreferredWidth(60);
            jTable7.getColumnModel().getColumn(3).setMaxWidth(60);
            jTable7.getColumnModel().getColumn(4).setPreferredWidth(80);
            jTable7.getColumnModel().getColumn(4).setMaxWidth(80);
            jTable7.getColumnModel().getColumn(5).setPreferredWidth(80);
            jTable7.getColumnModel().getColumn(5).setMaxWidth(80);
            jTable7.getColumnModel().getColumn(6).setPreferredWidth(60);
            jTable7.getColumnModel().getColumn(6).setMaxWidth(60);
            jTable7.getColumnModel().getColumn(7).setPreferredWidth(80);
            jTable7.getColumnModel().getColumn(7).setMaxWidth(80);
            jTable7.getColumnModel().getColumn(8).setPreferredWidth(60);
            jTable7.getColumnModel().getColumn(8).setMaxWidth(60);
            jTable7.getColumnModel().getColumn(9).setPreferredWidth(100);
            jTable7.getColumnModel().getColumn(9).setMaxWidth(100);
            jTable7.getColumnModel().getColumn(10).setPreferredWidth(100);
            jTable7.getColumnModel().getColumn(10).setMaxWidth(100);
        }

        jButton33.setText("Add To Invoice");
        jButton33.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton33ActionPerformed(evt);
            }
        });
        jButton33.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jButton33KeyReleased(evt);
            }
        });

        jButton34.setText("Update");
        jButton34.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton34ActionPerformed(evt);
            }
        });
        jButton34.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jButton34KeyReleased(evt);
            }
        });

        jButton35.setText("Remove");
        jButton35.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton35ActionPerformed(evt);
            }
        });
        jButton35.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jButton35KeyReleased(evt);
            }
        });

        jButton36.setText("Reset");
        jButton36.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton36ActionPerformed(evt);
            }
        });
        jButton36.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jButton36KeyReleased(evt);
            }
        });

        jLabel65.setText("Total Pur. Amount");

        jLabel66.setText("Submitted By");

        jTextField49.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField49KeyReleased(evt);
            }
        });

        total_purchase_amount.setText("0");

        purchase_grand_total.setText("Grand Total : ");

        jButton37.setText("Submit");
        jButton37.setNextFocusableComponent(jTextField19);
        jButton37.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton37ActionPerformed(evt);
            }
        });
        jButton37.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jButton37KeyPressed(evt);
            }
        });

        pur_expence_name_lbl.setText("Other Expence Name");

        pur_expence_name_txt.setText("N/A");
        pur_expence_name_txt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                pur_expence_name_txtFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                pur_expence_name_txtFocusLost(evt);
            }
        });

        jButton50.setText("Import Records");
        jButton50.setNextFocusableComponent(jButton37);
        jButton50.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton50ActionPerformed(evt);
            }
        });

        jLabel4.setText("Expence Amount");

        pur_expence_amount_txt.setText("0");
        pur_expence_amount_txt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                pur_expence_amount_txtFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                pur_expence_amount_txtFocusLost(evt);
            }
        });
        pur_expence_amount_txt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                pur_expence_amount_txtKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                pur_expence_amount_txtKeyTyped(evt);
            }
        });

        jLabel5.setText("HSN Code");

        purchase_hsn_code.setText("jTextField2");

        pur_insurance_name_lbl.setText("Insurance Name");

        purchase_insurance_name.setText("N/A");
        purchase_insurance_name.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                purchase_insurance_nameFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                purchase_insurance_nameFocusLost(evt);
            }
        });

        purchase_insurance_charge_lbl.setText("Insurance Amount");

        purchase_insurance_charge.setText("0");
        purchase_insurance_charge.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                purchase_insurance_chargeFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                purchase_insurance_chargeFocusLost(evt);
            }
        });
        purchase_insurance_charge.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                purchase_insurance_chargeKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                purchase_insurance_chargeKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout purchase_orderLayout = new javax.swing.GroupLayout(purchase_order);
        purchase_order.setLayout(purchase_orderLayout);
        purchase_orderLayout.setHorizontalGroup(
            purchase_orderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(purchase_orderLayout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(purchase_orderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(purchase_orderLayout.createSequentialGroup()
                        .addGroup(purchase_orderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(purchase_orderLayout.createSequentialGroup()
                                .addComponent(pur_expence_name_lbl)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(pur_expence_name_txt, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(pur_expence_amount_txt, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, purchase_orderLayout.createSequentialGroup()
                                .addComponent(jLabel65, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(total_purchase_amount, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(purchase_grand_total, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel66, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField49, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton37, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(229, 229, 229))
                    .addGroup(purchase_orderLayout.createSequentialGroup()
                        .addGroup(purchase_orderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane9)
                            .addGroup(purchase_orderLayout.createSequentialGroup()
                                .addGroup(purchase_orderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel64, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel48, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel38, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 77, Short.MAX_VALUE)
                                    .addComponent(jLabel62, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(purchase_orderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextField37)
                                    .addComponent(jTextField19, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(purchase_orderLayout.createSequentialGroup()
                                        .addGroup(purchase_orderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(jTextField46, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jMonthChooser1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                                        .addComponent(jComboBox12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(18, 18, 18)
                                .addGroup(purchase_orderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel55, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel39, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel49, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel63, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(purchase_orderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(purchase_orderLayout.createSequentialGroup()
                                        .addGroup(purchase_orderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jComboBox10, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(purchase_orderLayout.createSequentialGroup()
                                                .addGroup(purchase_orderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                    .addComponent(jTextField38, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 52, Short.MAX_VALUE)
                                                    .addComponent(jTextField47, javax.swing.GroupLayout.Alignment.LEADING))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jLabel50)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jTextField39)))
                                        .addGap(18, 18, 18))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, purchase_orderLayout.createSequentialGroup()
                                        .addComponent(jTextField43, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)))
                                .addGroup(purchase_orderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel51, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 65, Short.MAX_VALUE)
                                    .addComponent(jLabel47, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(purchase_orderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jComboBox11, 0, 150, Short.MAX_VALUE)
                                    .addComponent(jTextField40, javax.swing.GroupLayout.DEFAULT_SIZE, 152, Short.MAX_VALUE)
                                    .addComponent(purchase_hsn_code))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, 12, Short.MAX_VALUE)
                                .addGroup(purchase_orderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel60, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel54, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(purchase_orderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTextField41)
                                    .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(39, 39, 39))
                    .addGroup(purchase_orderLayout.createSequentialGroup()
                        .addGroup(purchase_orderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(purchase_orderLayout.createSequentialGroup()
                                .addComponent(jButton33)
                                .addGap(30, 30, 30)
                                .addComponent(jButton34)
                                .addGap(27, 27, 27)
                                .addComponent(jButton35)
                                .addGap(32, 32, 32)
                                .addComponent(jButton36, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(35, 35, 35)
                                .addComponent(jButton50, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(purchase_orderLayout.createSequentialGroup()
                                .addComponent(pur_insurance_name_lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(purchase_insurance_name, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(purchase_insurance_charge_lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(purchase_insurance_charge, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );

        purchase_orderLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jComboBox11, jTextField40});

        purchase_orderLayout.setVerticalGroup(
            purchase_orderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(purchase_orderLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(purchase_orderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(purchase_orderLayout.createSequentialGroup()
                        .addGroup(purchase_orderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel38)
                            .addComponent(jTextField19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel39)
                            .addComponent(jComboBox10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel47))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(purchase_orderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel48)
                            .addComponent(jTextField37, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel49)
                            .addComponent(jTextField38, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel50)
                            .addComponent(jTextField39, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel51))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(purchase_orderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(purchase_orderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel55)
                                .addComponent(jTextField43, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel64)
                                .addComponent(jComboBox12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel5)
                                .addComponent(purchase_hsn_code, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jMonthChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(purchase_orderLayout.createSequentialGroup()
                        .addGroup(purchase_orderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(purchase_orderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jComboBox11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel60))
                            .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(purchase_orderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField40, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel54)
                            .addComponent(jTextField41, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(31, 31, 31)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(purchase_orderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel62)
                    .addComponent(jTextField46, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel63)
                    .addComponent(jTextField47, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(purchase_orderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton33)
                    .addComponent(jButton34)
                    .addComponent(jButton35)
                    .addComponent(jButton36)
                    .addComponent(jButton50))
                .addGap(12, 12, 12)
                .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(purchase_orderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(pur_expence_name_lbl)
                    .addComponent(pur_expence_name_txt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(pur_expence_amount_txt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(4, 4, 4)
                .addGroup(purchase_orderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(pur_insurance_name_lbl)
                    .addComponent(purchase_insurance_name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(purchase_insurance_charge_lbl)
                    .addComponent(purchase_insurance_charge, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7)
                .addGroup(purchase_orderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton37)
                    .addComponent(jLabel65)
                    .addComponent(jLabel66)
                    .addComponent(jTextField49, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(total_purchase_amount)
                    .addComponent(purchase_grand_total, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(51, Short.MAX_VALUE))
        );

        purchase_orderLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jComboBox10, jComboBox11, jDateChooser1, jLabel38, jLabel39, jLabel47, jLabel60, jTextField19});

        purchase_orderLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel48, jLabel49, jLabel5, jLabel50, jLabel51, jLabel54, jTextField37, jTextField38, jTextField39, jTextField40, jTextField41, purchase_hsn_code});

        purchase_orderLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jComboBox12, jLabel55, jLabel64, jMonthChooser1, jTextField43});

        purchase_orderLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel62, jLabel63, jTextField46, jTextField47});

        jPanel9.add(purchase_order, "card8");

        jPanel14.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel72.setText("Product Type");

        jTextField51.setText("jTextField51");
        jTextField51.setNextFocusableComponent(jTextField52);

        jLabel73.setText("Product Name");

        jTextField52.setText("jTextField52");
        jTextField52.setNextFocusableComponent(jTextField53);

        jLabel74.setText("Product Code");

        jTextField53.setText("jTextField53");
        jTextField53.setNextFocusableComponent(jButton38);

        jTable8.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "S.No.", "Product Name", "Product Code", "Product Type"
            }
        ));
        jTable8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable8MouseClicked(evt);
            }
        });
        jScrollPane10.setViewportView(jTable8);
        if (jTable8.getColumnModel().getColumnCount() > 0) {
            jTable8.getColumnModel().getColumn(0).setPreferredWidth(40);
            jTable8.getColumnModel().getColumn(0).setMaxWidth(40);
        }

        jButton38.setText("Add");
        jButton38.setNextFocusableComponent(jButton39);
        jButton38.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton38ActionPerformed(evt);
            }
        });
        jButton38.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jButton38KeyPressed(evt);
            }
        });

        jButton39.setText("Update");
        jButton39.setNextFocusableComponent(jButton40);
        jButton39.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton39ActionPerformed(evt);
            }
        });
        jButton39.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jButton39KeyPressed(evt);
            }
        });

        jButton40.setText("Delete");
        jButton40.setNextFocusableComponent(jButton48);
        jButton40.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton40ActionPerformed(evt);
            }
        });
        jButton40.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jButton40KeyPressed(evt);
            }
        });

        jLabel75.setText("Search");

        jTextField54.setText("jTextField54");
        jTextField54.setNextFocusableComponent(jTextField51);
        jTextField54.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField54KeyReleased(evt);
            }
        });

        jButton48.setText("Reset");
        jButton48.setNextFocusableComponent(jTextField54);
        jButton48.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton48ActionPerformed(evt);
            }
        });
        jButton48.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jButton48KeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel74, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel73, javax.swing.GroupLayout.DEFAULT_SIZE, 96, Short.MAX_VALUE)
                            .addComponent(jLabel72, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextField52, javax.swing.GroupLayout.DEFAULT_SIZE, 236, Short.MAX_VALUE)
                            .addComponent(jTextField51)
                            .addComponent(jTextField53)))
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addGap(52, 52, 52)
                        .addComponent(jButton38, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton48)
                        .addGap(19, 19, 19)
                        .addComponent(jButton39)
                        .addGap(18, 18, 18)
                        .addComponent(jButton40)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addComponent(jLabel75, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextField54)))
                .addGap(23, 23, 23))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel72)
                    .addComponent(jTextField51, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel75)
                    .addComponent(jTextField54, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel73)
                            .addComponent(jTextField52, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel74)
                            .addComponent(jTextField53, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(50, 50, 50)
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jButton39)
                                .addComponent(jButton40))
                            .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jButton38)
                                .addComponent(jButton48))))
                    .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(158, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(69, 69, 69)
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(111, Short.MAX_VALUE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(37, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout product_detailsLayout = new javax.swing.GroupLayout(product_details);
        product_details.setLayout(product_detailsLayout);
        product_detailsLayout.setHorizontalGroup(
            product_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(product_detailsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        product_detailsLayout.setVerticalGroup(
            product_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(product_detailsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel9.add(product_details, "card9");

        jLabel76.setText("Supplier/Customer");

        jTextField55.setText("jTextField55");
        jTextField55.setNextFocusableComponent(jTextField56);

        jLabel77.setText("Contact No");

        jTextField56.setText("jTextField56");
        jTextField56.setNextFocusableComponent(jTextField57);
        jTextField56.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField56KeyTyped(evt);
            }
        });

        jLabel78.setText("E-Mail");

        jTextField57.setText("jTextField57");
        jTextField57.setNextFocusableComponent(jTextArea1);
        jTextField57.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField57FocusLost(evt);
            }
        });

        jLabel79.setText("Address");

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jTextArea1.setNextFocusableComponent(jTextField58);
        jTextArea1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextArea1KeyReleased(evt);
            }
        });
        jScrollPane11.setViewportView(jTextArea1);

        jLabel80.setText("GST No");

        jTextField58.setText("jTextField58");
        jTextField58.setNextFocusableComponent(jTextField59);

        jLabel81.setText("Lic 1");

        jTextField59.setText("jTextField59");
        jTextField59.setNextFocusableComponent(jTextField60);

        jLabel82.setText("Lic 2");

        jTextField60.setText("jTextField60");
        jTextField60.setNextFocusableComponent(jTextField61);

        jLabel83.setText("Doctor License no");

        jTextField61.setText("jTextField61");
        jTextField61.setNextFocusableComponent(jTextField21);

        jTable9.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "S.No", "Party Name", "Contact No", "GST No"
            }
        ));
        jTable9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable9MouseClicked(evt);
            }
        });
        jScrollPane12.setViewportView(jTable9);
        if (jTable9.getColumnModel().getColumnCount() > 0) {
            jTable9.getColumnModel().getColumn(0).setPreferredWidth(40);
            jTable9.getColumnModel().getColumn(0).setMaxWidth(40);
        }

        jButton41.setText("Add");
        jButton41.setNextFocusableComponent(jButton42);
        jButton41.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton41ActionPerformed(evt);
            }
        });
        jButton41.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jButton41KeyPressed(evt);
            }
        });

        jButton42.setText("Update");
        jButton42.setNextFocusableComponent(jButton43);
        jButton42.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton42ActionPerformed(evt);
            }
        });
        jButton42.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jButton42KeyPressed(evt);
            }
        });

        jButton43.setText("Delete");
        jButton43.setNextFocusableComponent(jButton49);
        jButton43.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton43ActionPerformed(evt);
            }
        });
        jButton43.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jButton43KeyPressed(evt);
            }
        });

        jLabel84.setText("Search");

        jTextField62.setText("jTextField62");
        jTextField62.setNextFocusableComponent(jTextField55);
        jTextField62.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField62KeyReleased(evt);
            }
        });

        jButton49.setText("Reset");
        jButton49.setNextFocusableComponent(jTextField62);
        jButton49.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton49ActionPerformed(evt);
            }
        });
        jButton49.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jButton49KeyPressed(evt);
            }
        });

        jLabel96.setText("State Name");

        jComboBox17.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select State Name", "Andaman and Nicobar Islands", "Andhra Pradesh", "Andhra Pradesh (New)", "Arunachal Pradesh", "Assam", "Bihar", "Chandigarh", "Chattisgarh", "Dadra and Nagar Haveli", "Daman and Diu", "Delhi", "Goa", "Gujrat", "Haryana", "Himachal Pradesh", "Jammu and Kashmir", "Jharkhand", "Karnataka", "Kerala", "Lakshadweep Islands", "Madhya Pradesh", "Maharashtra", "Manipur", "Meghalaya", "Mizoram", "Nagaland", "Odisha", "Pondicherry", "Punjab", "Rajasthan", "Sikkim", "Tamil Nadu", "Telangana", "Tripura", "Uttar Pradesh", "Uttarakhand", "West Bengal" }));
        jComboBox17.setNextFocusableComponent(jButton41);
        jComboBox17.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox17ItemStateChanged(evt);
            }
        });

        jLabel153.setText("FSSAI");

        jTextField18.setText("jTextField18");
        jTextField18.setNextFocusableComponent(jTextField44);

        jLabel154.setText("COS. LIC No");

        jTextField21.setText("jTextField21");
        jTextField21.setNextFocusableComponent(jTextField18);

        jLabel155.setText("PAN No");

        jTextField44.setText("jTextField44");
        jTextField44.setNextFocusableComponent(jComboBox17);

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel155, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel153, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton41, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel83, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel82, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel81, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel80, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel79, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel78, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel77, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel76, javax.swing.GroupLayout.DEFAULT_SIZE, 118, Short.MAX_VALUE)
                    .addComponent(jLabel96, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel154, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addComponent(jButton42, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton43)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                        .addComponent(jButton49))
                    .addComponent(jComboBox17, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jTextField44, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField21, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField18, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField55, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField56, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField57, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane11, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 249, Short.MAX_VALUE)
                            .addComponent(jTextField58, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField59, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField60, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField61, javax.swing.GroupLayout.Alignment.LEADING))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, 494, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addComponent(jLabel84, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jTextField62)))
                .addGap(38, 38, 38))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel76)
                    .addComponent(jTextField55, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel84)
                    .addComponent(jTextField62, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel77)
                            .addComponent(jTextField56, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel78)
                            .addComponent(jTextField57, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel79)
                            .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(8, 8, 8)
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel80)
                            .addComponent(jTextField58, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel81)
                            .addComponent(jTextField59, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel82)
                            .addComponent(jTextField60, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel83)
                            .addComponent(jTextField61, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel154)
                            .addComponent(jTextField21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel153)
                            .addComponent(jTextField18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(7, 7, 7)
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel155)
                            .addComponent(jTextField44, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel96)
                            .addComponent(jComboBox17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, 387, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton41, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton42, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton43, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton49, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(45, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout manufacturerOrCustomerLayout = new javax.swing.GroupLayout(manufacturerOrCustomer);
        manufacturerOrCustomer.setLayout(manufacturerOrCustomerLayout);
        manufacturerOrCustomerLayout.setHorizontalGroup(
            manufacturerOrCustomerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(manufacturerOrCustomerLayout.createSequentialGroup()
                .addGap(65, 65, 65)
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(45, Short.MAX_VALUE))
        );
        manufacturerOrCustomerLayout.setVerticalGroup(
            manufacturerOrCustomerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(manufacturerOrCustomerLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel9.add(manufacturerOrCustomer, "card10");

        jPanel16.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel85.setText("Invoice No");

        jComboBox13.setEditable(true);
        jComboBox13.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select Invoice No" }));
        jComboBox13.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox13ItemStateChanged(evt);
            }
        });

        jLabel86.setText("Product Name");

        jComboBox14.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select Product Name" }));
        jComboBox14.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox14ItemStateChanged(evt);
            }
        });

        jLabel87.setText("From");

        jComboBox15.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select Party" }));
        jComboBox15.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox15ItemStateChanged(evt);
            }
        });

        jLabel88.setText("Date");

        jDateChooser8.setDateFormatString("dd-MM-yyyy");

        jLabel89.setText("Batch ID");

        jTextField63.setText("jTextField63");
        jTextField63.setNextFocusableComponent(jComboBox14);
        jTextField63.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField63KeyReleased(evt);
            }
        });

        jLabel90.setText("Box Qty");

        jTextField64.setText("jTextField64");
        jTextField64.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField64KeyTyped(evt);
            }
        });

        jLabel91.setText("Strip Qty");

        jTextField65.setText("jTextField65");
        jTextField65.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField65KeyTyped(evt);
            }
        });

        jTable10.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Invoice No", "Batch ID", "Party Name", "Invoice Date", "Total Qty"
            }
        ));
        jTable10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable10MouseClicked(evt);
            }
        });
        jScrollPane13.setViewportView(jTable10);

        jLabel92.setText("Search");

        jTextField66.setText("jTextField66");
        jTextField66.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField66KeyReleased(evt);
            }
        });

        jButton45.setText("Add");
        jButton45.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton45ActionPerformed(evt);
            }
        });
        jButton45.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jButton45KeyPressed(evt);
            }
        });

        jButton46.setText("Update");
        jButton46.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton46ActionPerformed(evt);
            }
        });
        jButton46.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jButton46KeyPressed(evt);
            }
        });

        jButton47.setText("Delete");
        jButton47.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton47ActionPerformed(evt);
            }
        });
        jButton47.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jButton47KeyPressed(evt);
            }
        });

        jLabel93.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel93.setText("Sales Stock Amount");

        jLabel94.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel94.setText("Sales Return Amount");

        jLabel95.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel95.setText("Remaining Amount");

        jButton63.setText("Reset");
        jButton63.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton63ActionPerformed(evt);
            }
        });
        jButton63.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jButton63KeyPressed(evt);
            }
        });

        jLabel61.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel61.setText("0");

        jLabel151.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel151.setText("0");

        jLabel152.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel152.setText("0");

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel16Layout.createSequentialGroup()
                                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jLabel91, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel90, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel88, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel87, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel86, javax.swing.GroupLayout.DEFAULT_SIZE, 76, Short.MAX_VALUE)
                                        .addComponent(jLabel85, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel89, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addComponent(jButton45, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel16Layout.createSequentialGroup()
                                        .addComponent(jButton46)
                                        .addGap(18, 18, 18)
                                        .addComponent(jButton47))
                                    .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jComboBox13, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jComboBox14, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jComboBox15, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jDateChooser8, javax.swing.GroupLayout.DEFAULT_SIZE, 169, Short.MAX_VALUE)
                                        .addComponent(jTextField64)
                                        .addComponent(jTextField65)
                                        .addComponent(jTextField63, javax.swing.GroupLayout.Alignment.TRAILING))))
                            .addComponent(jButton63))
                        .addGap(27, 27, 27)
                        .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane13, javax.swing.GroupLayout.PREFERRED_SIZE, 572, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel16Layout.createSequentialGroup()
                                .addComponent(jLabel92, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTextField66))))
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addGap(341, 341, 341)
                        .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel93, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel94, javax.swing.GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE)
                            .addComponent(jLabel95, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel61, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel151, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel152, javax.swing.GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE))))
                .addContainerGap(35, Short.MAX_VALUE))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jDateChooser8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel88, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextField66)
                    .addComponent(jLabel92, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel85)
                            .addComponent(jComboBox13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel89)
                            .addComponent(jTextField63, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel86)
                            .addComponent(jComboBox14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel87)
                            .addComponent(jComboBox15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel90)
                            .addComponent(jTextField64, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel91)
                            .addComponent(jTextField65, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(32, 32, 32)
                        .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton45)
                            .addComponent(jButton46)
                            .addComponent(jButton47))
                        .addGap(18, 18, 18)
                        .addComponent(jButton63))
                    .addComponent(jScrollPane13, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel93)
                    .addComponent(jLabel61))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel94)
                    .addComponent(jLabel151))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel95)
                    .addComponent(jLabel152))
                .addContainerGap(70, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout sales_returnLayout = new javax.swing.GroupLayout(sales_return);
        sales_return.setLayout(sales_returnLayout);
        sales_returnLayout.setHorizontalGroup(
            sales_returnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sales_returnLayout.createSequentialGroup()
                .addGap(74, 74, 74)
                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(69, Short.MAX_VALUE))
        );
        sales_returnLayout.setVerticalGroup(
            sales_returnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sales_returnLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(21, Short.MAX_VALUE))
        );

        jPanel9.add(sales_return, "card11");

        jPanel17.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel110.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel110.setText("Party Name");

        jComboBox18.setEditable(true);
        jComboBox18.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select Party" }));
        jComboBox18.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox18ItemStateChanged(evt);
            }
        });

        jLabel111.setText("Invoice No");

        jTextField69.setText("jTextField69");

        jLabel112.setText("Total Amount");

        jLabel114.setText("jLabel114");

        jLabel116.setText("Amount Paid");

        jLabel119.setText("jLabel119");

        jLabel133.setText("Due Amount");

        jLabel134.setText("jLabel134");

        jLabel135.setText("Adjustment Amt");

        jTextField70.setText("jTextField70");

        jButton62.setText("Add");
        jButton62.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton62ActionPerformed(evt);
            }
        });
        jButton62.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jButton62KeyPressed(evt);
            }
        });

        jButton64.setText("Delete");
        jButton64.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton64ActionPerformed(evt);
            }
        });
        jButton64.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jButton64KeyPressed(evt);
            }
        });

        jTable11.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "", "Invoice No", "Invoice Date", "Amount", "Paid", "Due Amount", "Updated On"
            }
        ));
        jTable11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable11MouseClicked(evt);
            }
        });
        jScrollPane14.setViewportView(jTable11);
        if (jTable11.getColumnModel().getColumnCount() > 0) {
            jTable11.getColumnModel().getColumn(0).setPreferredWidth(0);
            jTable11.getColumnModel().getColumn(0).setMaxWidth(0);
            jTable11.getColumnModel().getColumn(2).setPreferredWidth(80);
            jTable11.getColumnModel().getColumn(2).setMaxWidth(80);
            jTable11.getColumnModel().getColumn(3).setPreferredWidth(80);
            jTable11.getColumnModel().getColumn(3).setMaxWidth(80);
            jTable11.getColumnModel().getColumn(4).setPreferredWidth(80);
            jTable11.getColumnModel().getColumn(4).setMaxWidth(80);
            jTable11.getColumnModel().getColumn(5).setPreferredWidth(80);
            jTable11.getColumnModel().getColumn(5).setMaxWidth(80);
            jTable11.getColumnModel().getColumn(6).setPreferredWidth(80);
            jTable11.getColumnModel().getColumn(6).setMaxWidth(80);
        }

        jLabel136.setText("Start Date");

        jDateChooser10.setDateFormatString("dd-MM-yyyy");
        jDateChooser10.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jDateChooser10PropertyChange(evt);
            }
        });

        jLabel137.setText("End Date");

        jDateChooser11.setDateFormatString("dd-MM-yyyy");
        jDateChooser11.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jDateChooser11PropertyChange(evt);
            }
        });

        jLabel138.setText("Search By Invoice No");

        jTextField71.setText("jTextField71");
        jTextField71.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField71KeyReleased(evt);
            }
        });

        jButton72.setText("Invoice Statement");
        jButton72.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton72ActionPerformed(evt);
            }
        });
        jButton72.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jButton72KeyPressed(evt);
            }
        });

        jButton73.setText("Party Due Balance Sheet");
        jButton73.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton73ActionPerformed(evt);
            }
        });
        jButton73.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jButton73KeyPressed(evt);
            }
        });

        jLabel146.setText("Payment Mode");

        jComboBox19.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select Payment Mode", "Cash", "Check", "Others" }));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setText("Total Amount : 0");

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel11.setText("Total Paid Amount : 0");

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel12.setText("Total Due Amount : 0");

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel13.setText("Total Invoices : 0");

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel17Layout.createSequentialGroup()
                        .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel17Layout.createSequentialGroup()
                                    .addGap(96, 96, 96)
                                    .addComponent(jButton62, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(15, 15, 15)
                                    .addComponent(jButton64))
                                .addGroup(jPanel17Layout.createSequentialGroup()
                                    .addGap(64, 64, 64)
                                    .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(jPanel17Layout.createSequentialGroup()
                                            .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                .addComponent(jLabel135, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 83, Short.MAX_VALUE)
                                                .addComponent(jLabel133, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jLabel116, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jLabel112, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jLabel111, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jLabel146, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addComponent(jTextField70, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                                .addComponent(jLabel134, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                .addComponent(jComboBox19, 0, 166, Short.MAX_VALUE)))
                                                        .addComponent(jLabel119, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                    .addComponent(jLabel114, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addComponent(jTextField69, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                            .addGroup(jPanel17Layout.createSequentialGroup()
                                .addGap(26, 26, 26)
                                .addComponent(jButton72, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane14))
                    .addGroup(jPanel17Layout.createSequentialGroup()
                        .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel17Layout.createSequentialGroup()
                                .addGap(337, 337, 337)
                                .addComponent(jButton73, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(117, 117, 117))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel17Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel110, javax.swing.GroupLayout.DEFAULT_SIZE, 97, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jComboBox18, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel136)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jDateChooser10, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel137)
                                .addGap(18, 18, 18)
                                .addComponent(jDateChooser11, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                        .addComponent(jLabel138, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextField71, javax.swing.GroupLayout.DEFAULT_SIZE, 233, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel137)
                    .addGroup(jPanel17Layout.createSequentialGroup()
                        .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel136)
                                    .addComponent(jLabel110)
                                    .addComponent(jComboBox18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(jDateChooser10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jDateChooser11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel138)
                                .addComponent(jTextField71, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane14, javax.swing.GroupLayout.PREFERRED_SIZE, 355, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel17Layout.createSequentialGroup()
                                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel111)
                                    .addComponent(jTextField69, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel112)
                                    .addComponent(jLabel114))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel116)
                                    .addComponent(jLabel119))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel133)
                                    .addComponent(jLabel134))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel146)
                                    .addComponent(jComboBox19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel135)
                                    .addComponent(jTextField70, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(32, 32, 32)
                                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jButton62)
                                    .addComponent(jButton64))
                                .addGap(26, 26, 26)
                                .addComponent(jLabel13)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel1)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel11)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel12)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 71, Short.MAX_VALUE)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton72, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton73, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel17Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jDateChooser10, jDateChooser11, jLabel136, jLabel137});

        javax.swing.GroupLayout party_management_panelLayout = new javax.swing.GroupLayout(party_management_panel);
        party_management_panel.setLayout(party_management_panelLayout);
        party_management_panelLayout.setHorizontalGroup(
            party_management_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, party_management_panelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        party_management_panelLayout.setVerticalGroup(
            party_management_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(party_management_panelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel9.add(party_management_panel, "card12");

        jLabel108.setText("Start Date");

        jDateChooser6.setDateFormatString("dd-MM-yyyy");

        jLabel109.setText("End Date");

        jDateChooser9.setDateFormatString("dd-MM-yyyy");

        jButton54.setText("Stock Report");
        jButton54.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton54ActionPerformed(evt);
            }
        });

        jButton55.setText("Free Stock Report");
        jButton55.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton55ActionPerformed(evt);
            }
        });

        jButton56.setText("Purchase Report");
        jButton56.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton56ActionPerformed(evt);
            }
        });

        jButton57.setText("Expiry Report");
        jButton57.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton57ActionPerformed(evt);
            }
        });

        jButton58.setText("Required Report");
        jButton58.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton58ActionPerformed(evt);
            }
        });

        jButton59.setText("Sales Return Report");
        jButton59.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton59ActionPerformed(evt);
            }
        });

        jButton60.setText("GST Report By Sales");
        jButton60.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton60ActionPerformed(evt);
            }
        });

        jButton61.setText("Profit/Loss");
        jButton61.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton61ActionPerformed(evt);
            }
        });

        jButton3.setText("Other Expences");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("By User");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout reports_panelLayout = new javax.swing.GroupLayout(reports_panel);
        reports_panel.setLayout(reports_panelLayout);
        reports_panelLayout.setHorizontalGroup(
            reports_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(reports_panelLayout.createSequentialGroup()
                .addGroup(reports_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(reports_panelLayout.createSequentialGroup()
                        .addGap(205, 205, 205)
                        .addComponent(jLabel108, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jDateChooser6, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addComponent(jLabel109, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jDateChooser9, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(reports_panelLayout.createSequentialGroup()
                        .addGap(124, 124, 124)
                        .addGroup(reports_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, 171, Short.MAX_VALUE)
                            .addComponent(jButton61, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton57, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton54, javax.swing.GroupLayout.DEFAULT_SIZE, 171, Short.MAX_VALUE))
                        .addGap(91, 91, 91)
                        .addGroup(reports_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jButton55, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton58, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton60, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(91, 91, 91)
                        .addGroup(reports_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(reports_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jButton56, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jButton59, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jButton3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(247, Short.MAX_VALUE))
        );
        reports_panelLayout.setVerticalGroup(
            reports_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(reports_panelLayout.createSequentialGroup()
                .addGap(59, 59, 59)
                .addGroup(reports_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jDateChooser9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel109)
                    .addComponent(jDateChooser6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel108))
                .addGap(43, 43, 43)
                .addGroup(reports_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, reports_panelLayout.createSequentialGroup()
                        .addGroup(reports_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton54, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton56, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(33, 33, 33)
                        .addGroup(reports_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton57, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton59, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(48, 48, 48)
                        .addGroup(reports_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButton61, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(reports_panelLayout.createSequentialGroup()
                        .addComponent(jButton55, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(33, 33, 33)
                        .addComponent(jButton58, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(48, 48, 48)
                        .addComponent(jButton60, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(48, 48, 48)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(47, Short.MAX_VALUE))
        );

        reports_panelLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jDateChooser6, jDateChooser9, jLabel108, jLabel109});

        jPanel9.add(reports_panel, "card13");

        jButton65.setText("Purchase Entry");
        jButton65.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton65ActionPerformed(evt);
            }
        });

        jButton66.setText("Purchase Report");
        jButton66.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton66ActionPerformed(evt);
            }
        });

        jLabel139.setText("Start Date");

        jDateChooser12.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jDateChooser12PropertyChange(evt);
            }
        });

        jLabel140.setText("End Date");

        jDateChooser13.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jDateChooser13PropertyChange(evt);
            }
        });

        jButton67.setText("Show Purchase Stock");
        jButton67.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton67ActionPerformed(evt);
            }
        });

        jTable12.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "S.No.", "Product Name", "Batch ID", "Exp Date", "Qty", "Free Qty", "Rate", "MRP", "Tax", "Amount(Tax Ex.)", "Amount (Tax IN.)"
            }
        ));
        jScrollPane15.setViewportView(jTable12);
        if (jTable12.getColumnModel().getColumnCount() > 0) {
            jTable12.getColumnModel().getColumn(0).setPreferredWidth(40);
            jTable12.getColumnModel().getColumn(0).setMaxWidth(40);
            jTable12.getColumnModel().getColumn(2).setPreferredWidth(80);
            jTable12.getColumnModel().getColumn(2).setMaxWidth(80);
            jTable12.getColumnModel().getColumn(3).setPreferredWidth(60);
            jTable12.getColumnModel().getColumn(3).setMaxWidth(60);
            jTable12.getColumnModel().getColumn(4).setPreferredWidth(40);
            jTable12.getColumnModel().getColumn(4).setMaxWidth(40);
            jTable12.getColumnModel().getColumn(5).setPreferredWidth(40);
            jTable12.getColumnModel().getColumn(5).setMaxWidth(40);
            jTable12.getColumnModel().getColumn(6).setPreferredWidth(100);
            jTable12.getColumnModel().getColumn(6).setMaxWidth(100);
            jTable12.getColumnModel().getColumn(7).setPreferredWidth(100);
            jTable12.getColumnModel().getColumn(7).setMaxWidth(100);
            jTable12.getColumnModel().getColumn(8).setPreferredWidth(40);
            jTable12.getColumnModel().getColumn(8).setMaxWidth(40);
        }

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "S.No", "Invoice No", "Total Qty", "Insurance Amt", "Other Expence", "Total Amount"
            }
        ));
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setPreferredWidth(40);
            jTable1.getColumnModel().getColumn(0).setMaxWidth(40);
            jTable1.getColumnModel().getColumn(2).setPreferredWidth(60);
            jTable1.getColumnModel().getColumn(2).setMaxWidth(60);
        }

        puchase_search_invoice.setText("jTextField1");
        puchase_search_invoice.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                puchase_search_invoiceFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                puchase_search_invoiceFocusLost(evt);
            }
        });
        puchase_search_invoice.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                puchase_search_invoiceKeyReleased(evt);
            }
        });

        purchase_invoice_delete_btn.setText("Delete Invoice");
        purchase_invoice_delete_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                purchase_invoice_delete_btnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout purchase_panelLayout = new javax.swing.GroupLayout(purchase_panel);
        purchase_panel.setLayout(purchase_panelLayout);
        purchase_panelLayout.setHorizontalGroup(
            purchase_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(purchase_panelLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(purchase_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(purchase_panelLayout.createSequentialGroup()
                        .addGroup(purchase_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(purchase_panelLayout.createSequentialGroup()
                                .addComponent(jButton66, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButton67, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(purchase_panelLayout.createSequentialGroup()
                                .addComponent(jLabel139)
                                .addGap(18, 18, 18)
                                .addComponent(jDateChooser12, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel140, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jDateChooser13, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jButton65, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(purchase_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1)
                            .addGroup(purchase_panelLayout.createSequentialGroup()
                                .addComponent(purchase_invoice_delete_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 106, Short.MAX_VALUE)
                                .addComponent(puchase_search_invoice, javax.swing.GroupLayout.PREFERRED_SIZE, 384, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jScrollPane15))
                .addContainerGap())
        );
        purchase_panelLayout.setVerticalGroup(
            purchase_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(purchase_panelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(purchase_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(purchase_panelLayout.createSequentialGroup()
                        .addComponent(jButton65)
                        .addGap(69, 69, 69)
                        .addGroup(purchase_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel139)
                            .addComponent(jDateChooser12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel140)
                            .addComponent(jDateChooser13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(purchase_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton66)
                    .addComponent(jButton67)
                    .addComponent(puchase_search_invoice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(purchase_invoice_delete_btn))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane15, javax.swing.GroupLayout.DEFAULT_SIZE, 339, Short.MAX_VALUE)
                .addContainerGap())
        );

        purchase_panelLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jDateChooser12, jDateChooser13, jLabel139, jLabel140});

        jPanel9.add(purchase_panel, "card14");

        jButton68.setMnemonic('S');
        jButton68.setText("Sale Entry");
        jButton68.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton68ActionPerformed(evt);
            }
        });

        jLabel141.setText("Start Date");

        jDateChooser14.setDateFormatString("dd-MM-yyyy");

        jLabel142.setText("End Date");

        jDateChooser15.setDateFormatString("dd-MM-yyyy");

        jButton71.setText("Show Sale Invoices");
        jButton71.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton71ActionPerformed(evt);
            }
        });

        jTable13.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "S.No", "Invoice No", "Invoice Date", "Product Name", "Qty", "Free Qty", "Rate", "MRP", "Tax", "Amount"
            }
        ));
        jScrollPane16.setViewportView(jTable13);
        if (jTable13.getColumnModel().getColumnCount() > 0) {
            jTable13.getColumnModel().getColumn(0).setPreferredWidth(40);
            jTable13.getColumnModel().getColumn(0).setMaxWidth(40);
            jTable13.getColumnModel().getColumn(4).setPreferredWidth(40);
            jTable13.getColumnModel().getColumn(4).setMaxWidth(40);
            jTable13.getColumnModel().getColumn(5).setPreferredWidth(60);
            jTable13.getColumnModel().getColumn(5).setMaxWidth(60);
            jTable13.getColumnModel().getColumn(8).setPreferredWidth(40);
            jTable13.getColumnModel().getColumn(8).setMaxWidth(40);
        }

        search_by_user_name.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                search_by_user_nameFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                search_by_user_nameFocusLost(evt);
            }
        });
        search_by_user_name.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                search_by_user_nameKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout sale_panelLayout = new javax.swing.GroupLayout(sale_panel);
        sale_panel.setLayout(sale_panelLayout);
        sale_panelLayout.setHorizontalGroup(
            sale_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sale_panelLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(sale_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(sale_panelLayout.createSequentialGroup()
                        .addComponent(jScrollPane16)
                        .addContainerGap())
                    .addGroup(sale_panelLayout.createSequentialGroup()
                        .addComponent(jButton68, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(jLabel142, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jDateChooser15, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(607, 608, Short.MAX_VALUE))
                    .addGroup(sale_panelLayout.createSequentialGroup()
                        .addGroup(sale_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(sale_panelLayout.createSequentialGroup()
                                .addComponent(jLabel141, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jDateChooser14, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(249, 249, 249)
                                .addComponent(search_by_user_name, javax.swing.GroupLayout.PREFERRED_SIZE, 347, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jButton71, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        sale_panelLayout.setVerticalGroup(
            sale_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sale_panelLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jButton68)
                .addGap(43, 43, 43)
                .addGroup(sale_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jDateChooser15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel141)
                    .addComponent(jDateChooser14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel142)
                    .addComponent(search_by_user_name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jButton71)
                .addGap(42, 42, 42)
                .addComponent(jScrollPane16, javax.swing.GroupLayout.PREFERRED_SIZE, 333, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        sale_panelLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jDateChooser14, jDateChooser15, jLabel141, jLabel142});

        jPanel9.add(sale_panel, "card15");

        jPanel18.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel143.setText("User Name");

        jLabel144.setText("Password");

        jLabel145.setText("Confirm Password");

        jTextField72.setText("jTextField72");

        jPasswordField1.setText("jPasswordField1");

        jPasswordField2.setText("jPasswordField2");

        jButton75.setText("Add");
        jButton75.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton75ActionPerformed(evt);
            }
        });
        jButton75.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jButton75KeyPressed(evt);
            }
        });

        jButton76.setText("Update");
        jButton76.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton76ActionPerformed(evt);
            }
        });
        jButton76.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jButton76KeyPressed(evt);
            }
        });

        jButton77.setText("Delete");
        jButton77.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton77ActionPerformed(evt);
            }
        });
        jButton77.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jButton77KeyPressed(evt);
            }
        });

        jButton78.setText("Reset");
        jButton78.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton78ActionPerformed(evt);
            }
        });
        jButton78.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jButton78KeyPressed(evt);
            }
        });

        jTable14.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "User", "Created On", "Created By"
            }
        ));
        jTable14.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable14MouseClicked(evt);
            }
        });
        jScrollPane19.setViewportView(jTable14);

        jPanel11.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jCheckBox1.setText("Purchase Entry");

        jCheckBox2.setText("Sales Entry");

        jCheckBox3.setText("Report");

        jCheckBox4.setText("Sales Return");

        jCheckBox5.setText("Party Management");

        jButton1.setText("Allow");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jButton1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jButton1KeyPressed(evt);
            }
        });

        jCheckBox6.setText("Purchase Update");

        jCheckBox7.setText("Sale Update");

        jCheckBox8.setText("+Company");

        jCheckBox9.setText("+Product");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jCheckBox5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jCheckBox4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jCheckBox3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jCheckBox2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jCheckBox1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jCheckBox6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jCheckBox7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jCheckBox8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jCheckBox9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jCheckBox1)
                .addGap(0, 0, 0)
                .addComponent(jCheckBox2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox5, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox9)
                .addGap(18, 18, 18)
                .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addGap(49, 49, 49)
                        .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel143, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel144, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel145, javax.swing.GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextField72)
                            .addComponent(jPasswordField1, javax.swing.GroupLayout.DEFAULT_SIZE, 152, Short.MAX_VALUE)
                            .addComponent(jPasswordField2)))
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jButton78, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jButton75, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(51, 51, 51)
                        .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButton76, javax.swing.GroupLayout.DEFAULT_SIZE, 84, Short.MAX_VALUE)
                            .addComponent(jButton77, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane19, javax.swing.GroupLayout.PREFERRED_SIZE, 511, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(28, Short.MAX_VALUE))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel143)
                            .addComponent(jTextField72, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel144)
                            .addComponent(jPasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel145)
                            .addComponent(jPasswordField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(34, 34, 34)
                        .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButton75, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
                            .addComponent(jButton76, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton78, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton77, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addComponent(jScrollPane19, javax.swing.GroupLayout.PREFERRED_SIZE, 413, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        javax.swing.GroupLayout user_panelLayout = new javax.swing.GroupLayout(user_panel);
        user_panel.setLayout(user_panelLayout);
        user_panelLayout.setHorizontalGroup(
            user_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(user_panelLayout.createSequentialGroup()
                .addGap(104, 104, 104)
                .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(96, Short.MAX_VALUE))
        );
        user_panelLayout.setVerticalGroup(
            user_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(user_panelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel9.add(user_panel, "card16");

        jTable15.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Product Name", "Manufacturer", "Batch ID", "Qty", "Expiry Date"
            }
        ));
        jScrollPane20.setViewportView(jTable15);

        jTable16.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Product Name", "Qty", "Batch ID"
            }
        ));
        jScrollPane21.setViewportView(jTable16);

        jLabel101.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel101.setText("To be Expire");

        jLabel150.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel150.setText("Remaining 50% quantity");

        jTable5.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "S.No", "Product Name", "Quantity", "Batch Id"
            }
        ));
        jScrollPane2.setViewportView(jTable5);
        if (jTable5.getColumnModel().getColumnCount() > 0) {
            jTable5.getColumnModel().getColumn(0).setPreferredWidth(40);
            jTable5.getColumnModel().getColumn(0).setMaxWidth(40);
        }

        jTable17.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "S.No", "Product Name", "Quantity", "Batch ID"
            }
        ));
        jScrollPane7.setViewportView(jTable17);
        if (jTable17.getColumnModel().getColumnCount() > 0) {
            jTable17.getColumnModel().getColumn(0).setPreferredWidth(40);
            jTable17.getColumnModel().getColumn(0).setMaxWidth(40);
        }

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setText("Purchase Stock");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel7.setText("Sale Stock");

        javax.swing.GroupLayout required_panelLayout = new javax.swing.GroupLayout(required_panel);
        required_panel.setLayout(required_panelLayout);
        required_panelLayout.setHorizontalGroup(
            required_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(required_panelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(required_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane20, javax.swing.GroupLayout.DEFAULT_SIZE, 535, Short.MAX_VALUE)
                    .addGroup(required_panelLayout.createSequentialGroup()
                        .addGroup(required_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel101, javax.swing.GroupLayout.PREFERRED_SIZE, 452, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(required_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(required_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jScrollPane2)
                        .addComponent(jScrollPane21, javax.swing.GroupLayout.DEFAULT_SIZE, 501, Short.MAX_VALUE)
                        .addComponent(jLabel150, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
            .addGroup(required_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(required_panelLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 535, Short.MAX_VALUE)
                    .addGap(521, 521, 521)))
        );
        required_panelLayout.setVerticalGroup(
            required_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, required_panelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(required_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel101, javax.swing.GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE)
                    .addComponent(jLabel150, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(required_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane20, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
                    .addComponent(jScrollPane21, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(required_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(required_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, required_panelLayout.createSequentialGroup()
                    .addGap(308, 308, 308)
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 222, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        jPanel9.add(required_panel, "card17");

        jLabel113.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel113.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel113.setText("Company Name");

        jTextField25.setText("jTextField4");
        jTextField25.setNextFocusableComponent(jTextArea6);

        jLabel115.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel115.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel115.setText("Company Slogan");

        jTextField26.setText("jTextField5");
        jTextField26.setNextFocusableComponent(jTextField27);

        jLabel117.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel117.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel117.setText("Company GST No");

        jTextField27.setText("jTextField6");
        jTextField27.setNextFocusableComponent(jTextField28);

        jLabel118.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel118.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel118.setText("Company Contact No");

        jTextField28.setText("jTextField7");
        jTextField28.setNextFocusableComponent(jTextField29);

        jLabel120.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel120.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel120.setText("Company Mail");

        jTextField29.setText("jTextField8");
        jTextField29.setNextFocusableComponent(jTextArea5);

        jLabel121.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel121.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel121.setText("Company Address");

        jTextArea5.setColumns(20);
        jTextArea5.setRows(5);
        jTextArea5.setNextFocusableComponent(jButton13);
        jTextArea5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextArea5KeyPressed(evt);
            }
        });
        jScrollPane17.setViewportView(jTextArea5);

        jLabel122.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel122.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel122.setText("Company Logo");

        jButton13.setText("Select");
        jButton13.setNextFocusableComponent(jButton14);
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });

        jLabel123.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel123.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel123.setText("Company QR Code");

        jButton14.setText("Select");
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });

        jLabel124.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel124.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel124.setText("Company Bank Name");

        jTextField30.setText("jTextField9");

        jLabel125.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel125.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel125.setText("Company Bank A/C No");

        jTextField31.setText("jTextField10");

        jLabel126.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel126.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel126.setText("Bank IFSC Code");

        jTextField32.setText("jTextField11");

        jLabel127.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel127.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel127.setText("Bill Title");

        jTextField33.setText("jTextField12");

        jLabel128.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel128.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel128.setText("Company T&C");

        jTextArea6.setColumns(20);
        jTextArea6.setRows(5);
        jTextArea6.setNextFocusableComponent(jTextField26);
        jTextArea6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextArea6KeyPressed(evt);
            }
        });
        jScrollPane18.setViewportView(jTextArea6);

        jLabel129.setText("No Logo");
        jLabel129.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jButton15.setText("Submit");
        jButton15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton15ActionPerformed(evt);
            }
        });

        jLabel131.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel131.setText("jLabel131");

        jLabel29.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel29.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel29.setText("Invoice Pattern");

        jTextField34.setText("jTextField34");
        jTextField34.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField34FocusLost(evt);
            }
        });
        jTextField34.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField34KeyReleased(evt);
            }
        });

        jButton11.setText("Reset");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        jLabel30.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel30.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel30.setText("S.no");

        jTextField15.setText("jTextField15");

        jLabel31.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel31.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel31.setText("Validity Date");

        jDateChooser2.setDateFormatString("dd/MM/yyyy");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("State Name");

        jComboBox5.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Andaman and Nicobar Islands", "Andhra Pradesh", "Andhra Pradesh (New)", "Arunachal Pradesh", "Assam", "Bihar", "Chandigarh", "Chattisgarh", "Dadra and Nagar Haveli", "Daman and Diu", "Delhi", "Goa", "Gujrat", "Haryana", "Himachal Pradesh", "Jammu and Kashmir", "Jharkhand", "Karnataka", "Kerala", "Lakshadweep Islands", "Madhya Pradesh", "Maharashtra", "Manipur", "Meghalaya", "Mizoram", "Nagaland", "Odisha", "Pondicherry", "Punjab", "Rajasthan", "Sikkim", "Tamil Nadu", "Telangana", "Tripura", "Uttar Pradesh", "Uttarakhand", "West Bengal" }));

        jLabel130.setText("No Logo");
        jLabel130.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel132.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel132.setText("jLabel132");

        javax.swing.GroupLayout company_profileLayout = new javax.swing.GroupLayout(company_profile);
        company_profile.setLayout(company_profileLayout);
        company_profileLayout.setHorizontalGroup(
            company_profileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(company_profileLayout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(company_profileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(company_profileLayout.createSequentialGroup()
                        .addGroup(company_profileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel117, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel118, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(company_profileLayout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addGroup(company_profileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel113, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel115, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(9, 9, 9)
                        .addGroup(company_profileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField25, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField26, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField27, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField28, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(10, 10, 10)
                        .addComponent(jLabel128, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(jScrollPane18, javax.swing.GroupLayout.PREFERRED_SIZE, 374, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(company_profileLayout.createSequentialGroup()
                        .addComponent(jLabel127, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(jTextField33, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(company_profileLayout.createSequentialGroup()
                        .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(jTextField34, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(4, 4, 4)
                        .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(4, 4, 4)
                        .addComponent(jTextField15, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(125, 125, 125)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jComboBox5, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(company_profileLayout.createSequentialGroup()
                        .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(jDateChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(270, 270, 270)
                        .addComponent(jButton15, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(25, 25, 25)
                        .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(company_profileLayout.createSequentialGroup()
                        .addComponent(jLabel126, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(jTextField32, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(company_profileLayout.createSequentialGroup()
                        .addGroup(company_profileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(company_profileLayout.createSequentialGroup()
                                .addComponent(jLabel120, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(jTextField29, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(company_profileLayout.createSequentialGroup()
                                .addGroup(company_profileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(company_profileLayout.createSequentialGroup()
                                        .addComponent(jLabel121, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(10, 10, 10))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, company_profileLayout.createSequentialGroup()
                                        .addGroup(company_profileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel122, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel123, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                                .addGroup(company_profileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane17, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton13)
                                    .addComponent(jButton14)))
                            .addGroup(company_profileLayout.createSequentialGroup()
                                .addGroup(company_profileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel124, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel125, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(10, 10, 10)
                                .addGroup(company_profileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextField30, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextField31, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(125, 125, 125)
                        .addGroup(company_profileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel131, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel129, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(company_profileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel130, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel132, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(582, 582, 582))
        );

        company_profileLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel129, jLabel130});

        company_profileLayout.setVerticalGroup(
            company_profileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(company_profileLayout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(company_profileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(company_profileLayout.createSequentialGroup()
                        .addComponent(jLabel113, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(jLabel115, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(jLabel117, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(jLabel118, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(company_profileLayout.createSequentialGroup()
                        .addComponent(jTextField25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(jTextField26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(jTextField27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(jTextField28, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(company_profileLayout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel128))
                    .addComponent(jScrollPane18, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(company_profileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(company_profileLayout.createSequentialGroup()
                        .addGroup(company_profileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel120, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField29, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(company_profileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel121)
                            .addComponent(jScrollPane17, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(company_profileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(company_profileLayout.createSequentialGroup()
                                .addComponent(jLabel122, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel123, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(company_profileLayout.createSequentialGroup()
                                .addComponent(jButton13)
                                .addGap(6, 6, 6)
                                .addComponent(jButton14)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(company_profileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(company_profileLayout.createSequentialGroup()
                                .addComponent(jLabel124, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(11, 11, 11)
                                .addComponent(jLabel125, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(company_profileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel126, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextField32, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(6, 6, 6)
                                .addGroup(company_profileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel127, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextField33, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(company_profileLayout.createSequentialGroup()
                                .addComponent(jTextField30, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(11, 11, 11)
                                .addComponent(jTextField31, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(company_profileLayout.createSequentialGroup()
                        .addGroup(company_profileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel129, javax.swing.GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE)
                            .addComponent(jLabel130, javax.swing.GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(company_profileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel131, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel132))
                        .addGap(50, 50, 50)))
                .addGap(6, 6, 6)
                .addGroup(company_profileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField34, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(company_profileLayout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel6))
                    .addComponent(jComboBox5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(company_profileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jDateChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton15)
                    .addComponent(jButton11))
                .addContainerGap(97, Short.MAX_VALUE))
        );

        company_profileLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jComboBox5, jDateChooser2, jLabel113, jLabel115, jLabel117, jLabel118, jLabel120, jLabel121, jLabel124, jLabel125, jLabel126, jLabel127, jLabel29, jLabel30, jLabel31, jLabel6, jTextField15, jTextField25, jTextField26, jTextField27, jTextField28, jTextField29, jTextField30, jTextField31, jTextField32, jTextField33, jTextField34});

        company_profileLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jButton13, jButton14, jLabel122, jLabel123});

        jPanel9.add(company_profile, "card24");

        jPanel10.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        company_profile_btn.setText("Company Profile");
        company_profile_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                company_profile_btnActionPerformed(evt);
            }
        });

        sale_btn.setText("Sale");
        sale_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sale_btnActionPerformed(evt);
            }
        });

        exit_btn.setText("Exit");
        exit_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exit_btnActionPerformed(evt);
            }
        });

        logout_btn.setText("Log Out");
        logout_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logout_btnActionPerformed(evt);
            }
        });

        purchase_btn.setText("Purchase");
        purchase_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                purchase_btnActionPerformed(evt);
            }
        });

        report_btn.setText("Report");
        report_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                report_btnActionPerformed(evt);
            }
        });

        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/leema logo.png"))); // NOI18N

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 0, 255));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText(" ");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(company_profile_btn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(sale_btn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(exit_btn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(logout_btn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(purchase_btn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(report_btn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(6, 6, 6))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(company_profile_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(purchase_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sale_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(report_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 155, Short.MAX_VALUE)
                .addComponent(logout_btn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(exit_btn)
                .addContainerGap())
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        sales_rtn_btn.setText("Sales Return");
        sales_rtn_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sales_rtn_btnActionPerformed(evt);
            }
        });

        party_mng_btn.setText("Party Management");
        party_mng_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                party_mng_btnActionPerformed(evt);
            }
        });

        add_company_btn.setText("Add Company");
        add_company_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                add_company_btnActionPerformed(evt);
            }
        });

        productntype_btn.setText("Add Product & Type");
        productntype_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                productntype_btnActionPerformed(evt);
            }
        });

        hsn_sac_btn.setText("HSN/SAC Code");
        hsn_sac_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hsn_sac_btnActionPerformed(evt);
            }
        });

        manage_user_btn.setText("Manage User");
        manage_user_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                manage_user_btnActionPerformed(evt);
            }
        });

        view_invoice_btn.setText("View Invoice");
        view_invoice_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                view_invoice_btnActionPerformed(evt);
            }
        });

        required_btn.setText("Home");
        required_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                required_btnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(sales_rtn_btn)
                .addGap(18, 18, 18)
                .addComponent(party_mng_btn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(productntype_btn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(add_company_btn, javax.swing.GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(hsn_sac_btn, javax.swing.GroupLayout.DEFAULT_SIZE, 147, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(manage_user_btn, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(view_invoice_btn, javax.swing.GroupLayout.DEFAULT_SIZE, 115, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(required_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sales_rtn_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(party_mng_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(add_company_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(productntype_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(hsn_sac_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(manage_user_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(view_invoice_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(required_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, 1070, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addGap(5, 5, 5)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jMenu1.setText("File");

        jMenuItem1.setText("Add Company");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem2.setText("Gen Invoice");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuItem3.setText("View Invoice Detail");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem3);

        jMenuItem4.setText("Company Profile");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem4);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        jMenu3.setText("Help");

        jMenuItem5.setText("About");
        jMenu3.add(jMenuItem5);

        jMenuItem6.setText("Content");
        jMenu3.add(jMenuItem6);

        jMenuItem7.setText("For Software Customization");
        jMenuItem7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenuItem7MouseClicked(evt);
            }
        });
        jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem7ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem7);

        jMenuBar1.add(jMenu3);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        //add company
      hide_panel();
    //  jPanel5.setVisible(true);
      manufacturerOrCustomer.setVisible(true);
      jTextField7.setText(null);
        jTextField8.setText(null);
        jTextField9.setText(null);
        jTextField10.setText(null);
        jTextField11.setText(null);
        jTextField12.setText(null);
        jTextField13.setText(null);  
        jTextArea2.setText(null);
        reset_maufacturer_customer();
        
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // TODO add your handling code here:
       hide_panel();
      sale_panel.setVisible(true);
      reset_sales_order();
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        try{ int company_id=0;
              ps=con.prepareStatement("select com_id from company where company_name='"+jTextField8.getText()+"'");
              rs=ps.executeQuery();
             if(rs.next())
             company_id=rs.getInt(1);
            if(company_id==0){
            ps=con.prepareStatement("insert into COMPANY(reg_no,com_contact,com_email,com_website,com_address,client_name,company_name) values(?,?,?,?,?,?,?)");
            ps.setString(1,jTextField9.getText());
            ps.setString(2,jTextField10.getText());
            ps.setString(3,jTextField11.getText());
            ps.setString(4,jTextField12.getText());
            ps.setString(5,jTextArea2.getText());
            ps.setString(6,jTextField7.getText());
            ps.setString(7,jTextField8.getText());
            ps.executeUpdate();
            getTableData("select reg_no,company_name,com_email,com_website from company", jTable2);
            JOptionPane.showMessageDialog(null," Record Inserted ");
            }
        }catch(Exception e){}
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        try{
        ps=con.prepareStatement("UPDATE COMPANY SET com_contact=?,com_email=?,com_website=?,com_address=?,client_name=?,company_name=? WHERE reg_no=?");
           
            ps.setString(1,jTextField10.getText());
            ps.setString(2,jTextField11.getText());
            ps.setString(3,jTextField12.getText());
            ps.setString(4,jTextArea2.getText());
            ps.setString(5,jTextField7.getText());
            ps.setString(6,jTextField8.getText());
            ps.setString(7,jTextField9.getText());
            ps.executeUpdate();
            getTableData("select reg_no,company_name,com_email,com_website from company", jTable2);
            JOptionPane.showMessageDialog(null," Record Updated! ");
        }catch(Exception e){}
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
        try{ ps=con.prepareStatement("DELETE FROM COMPANY WHERE REG_NO=?");
        ps.setString(1,jTextField9.getText());
        ps.executeUpdate();
        getTableData("select reg_no,company_name,com_email,com_website from company", jTable2);
        JOptionPane.showMessageDialog(null," Record Deleted! ");    
        }catch(Exception e){}
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseClicked
        // TODO add your handling code here:
        try{ ps=con.prepareStatement("select reg_no,com_contact,com_email,com_website,com_address,client_name,company_name from company where reg_no='"+jTable2.getModel().getValueAt(jTable2.getSelectedRow(), 0).toString()+"'");
       rs= ps.executeQuery();
        if(rs.next()){
        jTextField9.setText(rs.getString(1));
            jTextField10.setText(rs.getString(2));
            jTextField11.setText(rs.getString(3));
            jTextField12.setText(rs.getString(4));
            jTextArea2.setText(rs.getString(5));
            jTextField7.setText(rs.getString(6));
            jTextField8.setText(rs.getString(7));
        }
            
        }catch(Exception e){}
     
    }//GEN-LAST:event_jTable2MouseClicked

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // TODO add your handling code here:
        jTextField7.setText(null);
        jTextField8.setText(null);
        jTextField9.setText(null);
        jTextField10.setText(null);
        jTextField11.setText(null);
        jTextField12.setText(null);
        jTextField13.setText(null);  
        jTextArea2.setText(null);
        getTableData("select reg_no,company_name,com_email,com_website from company", jTable2);
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jTextField13KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField13KeyReleased
        // TODO add your handling code here:
        getTableData("select reg_no,company_name,com_email,com_website from company where reg_no like '%"+jTextField13.getText()+"%' or lower(company_name) like lower('%"+jTextField13.getText()+"%')", jTable2);
    }//GEN-LAST:event_jTextField13KeyReleased

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        // TODO add your handling code here:
       hide_panel();
        jPanel6.setVisible(true);
        jTextField14.setText(null);
        getTableData("select SO_INVOICE_NO,MC_NAME,sum(SO_QTY),sum(SO_QTY*SO_RATE*(1+(SO_CGST+SO_SGST+SO_IGST)*0.01)) from SALES_ORDER INNER JOIN MANUFACTURER_CUSTOMER ON SO_MC_ID=MC_ID group by SO_INVOICE_NO,MC_NAME", jTable3);
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jDateChooser3PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jDateChooser3PropertyChange
        // TODO add your handling code here:
     
        try{
         if(jDateChooser3.getDate()!=null && jDateChooser4.getDate()!=null){
            System.out.println("select invoice_no,inv_com_name,sum(I_QTY_SOLD),sum(I_QTY_SOLD*I_PTS*(1+(I_CGST+I_SGST+I_IGST)*0.01)) from invoice where invoice_date between '"+new SimpleDateFormat("yyyy-MM-dd").format(jDateChooser3.getDate())+"' and '"+new SimpleDateFormat("yyyy-MM-dd").format(jDateChooser4.getDate())+"' group by invoice_no,inv_com_name");
            getTableData("select SO_INVOICE_NO,MC_NAME,sum(SO_QTY),roundOf(sum(SO_QTY*SO_RATE*(1+(SO_CGST+SO_SGST+SO_IGST)*0.01)),2) from SALES_ORDER INNER JOIN MANUFACTURER_CUSTOMER ON SO_MC_ID=MC_ID where SO_invoice_date between '"+new SimpleDateFormat("yyyy-MM-dd").format(jDateChooser3.getDate())+"' and '"+new SimpleDateFormat("yyyy-MM-dd").format(jDateChooser4.getDate())+"' group by SO_invoice_no,MC_NAME", jTable3); 
        }  
        }catch(Exception e){e.printStackTrace();}
        //  select SO_INVOICE_NAME,MC_NAME,sum(SO_QTY),sum(SO_QTY*SO_RATE*(1+(SO_CGST+SO_SGST+SO_IGST)*0.01)) from SALES_ORDER INNER JOIN MANUFACTURER_CUSTOMER ON SO_MC_ID=MC_ID
    }//GEN-LAST:event_jDateChooser3PropertyChange

    private void jDateChooser4PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jDateChooser4PropertyChange
        // TODO add your handling code here:
  
        try{
         if(jDateChooser3.getDate()!=null && jDateChooser4.getDate()!=null){
           // System.out.println("select  invoice_no,inv_com_name,sum(quantity),sum(quantity*unit_price) from invoice where invoice_date between '"+new SimpleDateFormat("yyyy-MM-dd").format(jDateChooser3.getDate())+"' and '"+new SimpleDateFormat("yyyy-MM-dd").format(jDateChooser4.getDate())+"' group by invoice_no,inv_com_name");
          getTableData("select SO_INVOICE_NO,MC_NAME,sum(SO_QTY),roundOf(sum(SO_QTY*SO_RATE*(1+(SO_CGST+SO_SGST+SO_IGST)*0.01)),2) from SALES_ORDER INNER JOIN MANUFACTURER_CUSTOMER ON SO_MC_ID=MC_ID where SO_invoice_date between '"+new SimpleDateFormat("yyyy-MM-dd").format(jDateChooser3.getDate())+"' and '"+new SimpleDateFormat("yyyy-MM-dd").format(jDateChooser4.getDate())+"' group by SO_invoice_no,MC_NAME", jTable3); 
            
        }  
        }catch(Exception e){e.printStackTrace();}
    }//GEN-LAST:event_jDateChooser4PropertyChange

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        // TODO add your handling code here:
        JFileChooser ch=new JFileChooser();
        int result=ch.showOpenDialog(this);
        if(result==JFileChooser.APPROVE_OPTION){
            jLabel131.setText(ch.getSelectedFile().getAbsolutePath());
            jLabel129.setIcon(new ImageIcon(new ImageIcon(jLabel131.getText()).getImage().getScaledInstance(jLabel129.getWidth(), jLabel129.getHeight(), java.awt.Image.SCALE_SMOOTH)));
        }
        else{
        jLabel131.setText("");jLabel129.setIcon(null);}
    }//GEN-LAST:event_jButton13ActionPerformed

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
        // TODO add your handling code here:
        JFileChooser ch=new JFileChooser();
        int result=ch.showOpenDialog(this);
        if(result==JFileChooser.APPROVE_OPTION){
            jLabel132.setText(ch.getSelectedFile().getAbsolutePath());
             jLabel130.setIcon(new ImageIcon(new ImageIcon(jLabel132.getText()).getImage().getScaledInstance(jLabel129.getWidth(), jLabel129.getHeight(), java.awt.Image.SCALE_SMOOTH)));
        }
        else{
        jLabel132.setText("");jLabel130.setIcon(null);}
    }//GEN-LAST:event_jButton14ActionPerformed

    private void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton15ActionPerformed
        // TODO add your handling code here:
        try{  PreparedStatement pst=con.prepareStatement("TRUNCATE TABLE COMPANY_PROFILE");
                                pst.executeUpdate();
                                pst.close();
            
                pst=con.prepareStatement("INSERT INTO COMPANY_PROFILE(COMPANY_NAME,COMPANY_SLOGAN,COMPANY_GST_NO,COMPANY_CONTACT_NO,COMPANY_MAIL,COMPANY_ADDRESS,COMPANY_LOGO,COMPANY_BANK_NAME,COMPANY_BANK_ACC_NO,COMPANY_IFS_CODE,COMPANY_BILL_TITLE,COMPANY_INVOICE_FORMAT,COMPANY_TNC,COMPANY_START_DATE,COMPANY_END_DATE,COMPANY_STATE_NAME) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
                pst.setString(1, jTextField25.getText());
                pst.setString(2, jTextField26.getText());
                pst.setString(3, jTextField27.getText());
                pst.setString(4, jTextField28.getText());
                pst.setString(5, jTextField29.getText());
                pst.setString(6, jTextArea5.getText());
                if(!jLabel131.getText().isEmpty()){
                FileInputStream fis=new FileInputStream(new File(jLabel131.getText()));
                pst.setBinaryStream(7, (InputStream)fis, (int)(new File(jLabel131.getText()).length()));
                }else
                {
                   pst.setBinaryStream(7,null); 
                }
//                 if(!jLabel132.getText().isEmpty()){
//                 FileInputStream fis=new FileInputStream(new File(jLabel132.getText()));
//                 pst.setBinaryStream(8, (InputStream)fis, (int)(new File(jLabel132.getText()).length()));
//                 }
//                 else{
//                pst.setBinaryStream(8,null);  }
                pst.setString(8, jTextField30.getText());
                pst.setString(9, jTextField31.getText());
                pst.setString(10, jTextField32.getText());
                pst.setString(11, jTextField33.getText());
                pst.setString(12, jTextField34.getText()+jTextField15.getText());
                pst.setString(13, jTextArea6.getText());
                pst.setDate(14, new java.sql.Date(new Date().getTime()));
                pst.setDate(15, new java.sql.Date(jDateChooser2.getDate().getTime()));
                pst.setString(16, jComboBox5.getSelectedItem().toString());
                pst.executeUpdate();
           JOptionPane.showMessageDialog(null, "Company profile Updated!");
           company_profile_reset();
        }catch(Exception e){e.printStackTrace();}
    }//GEN-LAST:event_jButton15ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        // TODO add your handling code here:
        hide_panel();
        company_profile.setVisible(true);
       // company_profile_reset();
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        // TODO add your handling code here:
        company_profile_reset();
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jTextArea6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextArea6KeyPressed
        // TODO add your handling code here:
//        if(evt.getKeyCode()==KeyEvent.VK_TAB){
//           jTextField26.requestFocusInWindow();
//        }

    }//GEN-LAST:event_jTextArea6KeyPressed

    private void jTextArea5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextArea5KeyPressed
        // TODO add your handling code here:
//        if(evt.getKeyCode()==KeyEvent.VK_TAB){
//           jButton13.requestFocusInWindow();
//        }
    }//GEN-LAST:event_jTextArea5KeyPressed

    private void add_company_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_add_company_btnActionPerformed
        // TODO add your handling code here:
     hide_panel();
    //  jPanel5.setVisible(true);
      manufacturerOrCustomer.setVisible(true);
      reset_button_color();
      add_company_btn.setBackground(Color.GRAY);
      
        reset_maufacturer_customer();
      //  getTableData("select reg_no,company_name,com_email,com_website from company", jTable2);
    }//GEN-LAST:event_add_company_btnActionPerformed

    private void exit_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exit_btnActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_exit_btnActionPerformed

    private void hsn_sac_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hsn_sac_btnActionPerformed
        // TODO add your handling code here:
        hide_panel();
        jPanel8.setVisible(true);
        VISIBLITY=1;
        reset_button_color();
        hsn_sac_btn.setBackground(Color.GRAY);
        jTextField23.setText("");
        getTableData("SELECT HSN_CODE,HSN_CODE_DESC FROM HSN_TABLE ", jTable4);  
    }//GEN-LAST:event_hsn_sac_btnActionPerformed

    private void jTextField23KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField23KeyReleased
        // TODO add your handling code here:
        if(jComboBox2.getSelectedIndex()==0)
       getTableData("SELECT HSN_CODE,HSN_CODE_DESC FROM HSN_TABLE WHERE CAST(HSN_CODE AS CHAR(10)) LIKE '%"+jTextField23.getText()+"%' OR UPPER(HSN_CODE_DESC) LIKE UPPER('%"+jTextField23.getText()+"%')", jTable4);  
        else
       getTableData("SELECT SAC_CODE,SAC_CODE_DESC FROM SAC_TABLE WHERE CAST(SAC_CODE AS CHAR(10)) LIKE '%"+jTextField23.getText()+"%' OR UPPER(SAC_CODE_DESC) LIKE UPPER('%"+jTextField23.getText()+"%')", jTable4);  
    }//GEN-LAST:event_jTextField23KeyReleased

    private void jMenuItem7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenuItem7MouseClicked
        // TODO add your handling code here:
      
    }//GEN-LAST:event_jMenuItem7MouseClicked

    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem7ActionPerformed
        // TODO add your handling code here:
        String message="<HTML><b>FOR WATERMARK,</b></HTML>\n" +
"<HTML><b>INTERFACE CHANGE,</b></HTML>\n" +
"<HTML><b>HSN/SAC CODE FINDER IN SALES PANEL DYNAMICALLY,</b></HTML>\n" +
"<HTML><b>Or user Required Customization</b></HTML>\n" +
"<HTML><b>FOR SUPPORT QUERY 9519008222</b></HTML>\n" +
"<HTML><b>For Software customization charges will be apply</b></HTML>\n" +
"<HTML><b>At least Starting from 500/- per day</b></HTML>";
          JOptionPane.showMessageDialog(null,message);
    }//GEN-LAST:event_jMenuItem7ActionPerformed

    private void logout_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logout_btnActionPerformed
        // TODO add your handling code here:
        dispose();
        new LoginPanel().setVisible(true);
    }//GEN-LAST:event_logout_btnActionPerformed

    private void jTextField34KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField34KeyReleased
        // TODO add your handling code here:
        
    }//GEN-LAST:event_jTextField34KeyReleased

    private void jTextField34FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField34FocusLost
        // TODO add your handling code here:
        Calendar c=Calendar.getInstance();
        int year=c.get(Calendar.YEAR)%100;
        //jTextField34.setText(jTextField34.getText().substring(0, jTextField34.getText().indexOf("/"))+"/"+year+"-"+(year+1)+"/");
        jTextField34.setText(jTextField34.getText()+"/"+year+"-"+(year+1)+"/");
    }//GEN-LAST:event_jTextField34FocusLost

    private void jButton28ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton28ActionPerformed
        // TODO add your handling code here:
      sale_order_insert();
    }//GEN-LAST:event_jButton28ActionPerformed

    private void jButton25ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton25ActionPerformed
        // TODO add your handling code here:try{
       sale_cart_remove();
    }//GEN-LAST:event_jButton25ActionPerformed

    private void jTable6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable6MouseClicked
        // TODO add your handling code here:
       
     try{PreparedStatement ps=con.prepareStatement("SELECT SC_INVOICE_NO,SC_HSN_CODE,SC_INVOICE_DATE,SC_RATE ,SC_MRP,SC_QTY,SC_QTY_FREE,SC_CGST,SC_SGST,SC_IGST,SC_BATCH_ID,P_NAME,MC_NAME,PO_PACK FROM SALES_CART INNER JOIN PRODUCT ON SC_P_ID=P_ID INNER JOIN MANUFACTURER_CUSTOMER ON SC_MC_ID=MC_ID INNER JOIN PURCHASE_ORDER ON SC_BATCH_ID=PO_BATCH_ID WHERE SC_ID="+Integer.parseInt(jTable6.getModel().getValueAt(jTable6.getSelectedRow(), 0).toString()));
         ResultSet rs=ps.executeQuery();
          if(rs.next()){
            jTextField17.setText(rs.getString("SC_INVOICE_NO")); System.out.println("1");
            jTextField67.setText(rs.getString("SC_HSN_CODE"));  System.out.println("2");
             
            jDateChooser7.setDate(rs.getDate("SC_INVOICE_DATE")); System.out.println("4");
            jLabel100.setText(rs.getFloat("SC_MRP")+""); System.out.println("5");
            jTextField22.setText(rs.getFloat("SC_RATE")+""); System.out.println("6");
            
            jTextField35.setText(rs.getInt("SC_QTY")+"");  System.out.println("7");
            jTextField36.setText(rs.getInt("SC_QTY_FREE")+"");  System.out.println("8");
            jTextField20.setText(rs.getFloat("SC_CGST")+rs.getFloat("SC_SGST")+rs.getFloat("SC_IGST")+"");System.out.println("9");
          
              jComboBox6.setSelectedItem(rs.getString("MC_NAME")); System.out.println("12");
               jComboBox8.setSelectedItem(rs.getString("P_NAME")); System.out.println("11");
            jComboBox20.setSelectedItem(rs.getString("SC_BATCH_ID")+" - ["+rs.getString("PO_PACK")+"]"); System.out.println("10");
           
            
          }
          ps=con.prepareStatement("SELECT roundOf(SUM(SC_RATE*SC_QTY*(1+(SC_CGST+SC_SGST+SC_IGST)*0.01)),2) FROM SALES_CART");
          rs=ps.executeQuery();
          if(rs.next()){
              jLabel107.setText(rs.getFloat(1)+"");
          }  
           jButton24.setVisible(true);jButton25.setVisible(true);
        }catch(Exception e){ e.printStackTrace();
            JOptionPane.showMessageDialog(null, e);}
    }//GEN-LAST:event_jTable6MouseClicked

    private void jButton24ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton24ActionPerformed
        // TODO add your handling code here:
       validate_sale_cart_update();
    }//GEN-LAST:event_jButton24ActionPerformed

    private void jButton26ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton26ActionPerformed
        // TODO add your handling code here:
        new TransportDetails(this, true, con).setVisible(true);
    }//GEN-LAST:event_jButton26ActionPerformed

    private void jTextField22FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField22FocusLost
        // TODO add your handling code here:
         if(jTextField22.getText().isEmpty())
            jTextField22.setText("0");
         
    }//GEN-LAST:event_jTextField22FocusLost

    private void jTextField22FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField22FocusGained
        // TODO add your handling code here:
         if(jTextField22.getText().equals("0"))
            jTextField22.setText("");
    }//GEN-LAST:event_jTextField22FocusGained

    private void jTextField36FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField36FocusGained
        // TODO add your handling code here:
         if(jTextField36.getText().equals("0"))
            jTextField36.setText("");
    }//GEN-LAST:event_jTextField36FocusGained

    private void jTextField36FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField36FocusLost
        // TODO add your handling code here:
         if(jTextField36.getText().isEmpty())
            jTextField36.setText("0");
          
    }//GEN-LAST:event_jTextField36FocusLost

    private void jTextField20FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField20FocusGained
        // TODO add your handling code here:
          if(jTextField20.getText().equals("0"))
            jTextField20.setText("");
    }//GEN-LAST:event_jTextField20FocusGained

    private void jTextField20FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField20FocusLost
        // TODO add your handling code here:
        if(jTextField20.getText().isEmpty())
            jTextField20.setText("0");
         
    }//GEN-LAST:event_jTextField20FocusLost

    private void jTextField35FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField35FocusLost
        // TODO add your handling code here:
       
    }//GEN-LAST:event_jTextField35FocusLost

    private void jTextField14KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField14KeyReleased
        // TODO add your handling code here:
        // or lower(inv_com_name) like  '%"+jTextField14.getText()+"%' or lower(inv_com_name) like in(select company_name from company where lower(client_name) like lower('%"+jTextField14.getText()+"%'))
   //     if(!jTextField14.getText().isEmpty())
        getTableData("select SO_INVOICE_NO,MC_NAME,sum(SO_QTY),roundOf(sum(SO_QTY*SO_RATE*(1+(SO_CGST+SO_SGST+SO_IGST)*0.01)),2) from SALES_ORDER INNER JOIN MANUFACTURER_CUSTOMER ON SO_MC_ID=MC_ID WHERE UPPER(SO_INVOICE_NO) LIKE UPPER('%"+jTextField14.getText()+"%') OR UPPER(MC_NAME) LIKE UPPER('%"+jTextField14.getText()+"%') group by SO_invoice_no,MC_NAME", jTable3);
   
//getTableData("select SO_INVOICE_NAME,MC_NAME,sum(SO_QTY),sum(SO_QTY*SO_RATE*(1+(SO_CGST+SO_SGST+SO_IGST)*0.01)) from SALES_ORDER INNER JOIN MANUFACTURER_CUSTOMER ON SO_MC_ID=MC_ID group by SO_INVOICE_NO,MC_NAME", jTable3);
    }//GEN-LAST:event_jTextField14KeyReleased

    private void jButton29ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton29ActionPerformed
        // TODO add your handling code here:
        if(jTable3.getSelectedRow()!=-1){
          // report("SELECT ROW_NUMBER() OVER() AS R,SO_INVOICE_NO,SO_INVOICE_DATE,P_NAME,SO_BATCH_ID,SO_EXP_DATE,SO_MRP,SO_RATE,SO_QTY,SO_QTY_FREE,SO_QTY*SO_RATE,SO_CGST,SO_SGST,SO_IGST,SO_QTY*SO_RATE*(1+(SO_CGST+SO_SGST+SO_IGST)*0.01),STATE_NAME,STATE_CODE,MC_NAME,MC_ADDRESS,MC_GST_NO,MC_PAN_NO,MC_LIC1,MC_LIC2,MC_DOC_LIC,MC_EMAIL,MC_CONTACT_NO,T_MODE,T_V_NO,T_CGST,T_SGST,T_IGST,T_AMOUNT,T_STATE_NC_ID,T_CONSIGNEE_NAME,T_CONSIGNEE_CONTACT,T_CONSIGNEE_ADDRESS,STATE_NAME,STATE_CODE FROM SALES_ORDER INNER JOIN PRODUCT ON SO_P_ID=P_ID INNER JOIN MANUFACTURER_CUSTOMER ON SO_MC_ID=MC_ID LEFT OUTER JOIN TRANSPORT ON SO_INVOICE_NO=T_INVOICE_NO INNER JOIN STATE_NC ON MC_STATE_NC_ID=STATE_NC_ID INNER JOIN SALEPURCHASE_ACCOUNT ON SPA_INVOICE_NO=SO_INVOICE_NO AND SO_INVOICE_NO='"+jTable3.getModel().getValueAt(jTable3.getSelectedRow(), 0).toString()+"'","reports\\invoice.jrxml");
           
            report("SELECT ROW_NUMBER() OVER() AS R,SO_INVOICE_NO,SO_INVOICE_DATE,P_NAME,SO_HSN_CODE,SO_BATCH_ID,SO_EXP_DATE,roundOf(SO_MRP,2) AS MRP,roundOf(SO_RATE,2) as RATE,SO_QTY,SO_QTY_FREE,roundOf(SO_QTY*SO_RATE,2) as AMOUNT,SO_CGST,SO_SGST,SO_IGST,roundOf(SO_QTY*SO_RATE*(1+(SO_CGST+SO_SGST+SO_IGST)*0.01),2) as TOTAL_AMOUNT,STATE_NC.STATE_NAME,STATE_NC.STATE_CODE,MC_NAME,MC_ADDRESS,MC_GST_NO,MC_PAN_NO,MC_LIC1,MC_LIC2,MC_DOC_LIC,MC_EMAIL,MC_CONTACT_NO,MC_FSSAI_CODE,MC_COS_LIC,T_MODE,T_V_NO,T_CGST,T_SGST,T_IGST,T_AMOUNT,T_STATE_NC_ID,T_CONSIGNEE_NAME,T_CONSIGNEE_CONTACT,T_CONSIGNEE_ADDRESS,STATE2.STATE_NAME AS S2,STATE2.STATE_CODE AS SC FROM SALES_ORDER INNER JOIN PRODUCT ON SO_P_ID=P_ID INNER JOIN MANUFACTURER_CUSTOMER ON SO_MC_ID=MC_ID INNER JOIN STATE_NC ON MC_STATE_NC_ID=STATE_NC.STATE_NC_ID INNER JOIN SALEPURCHASE_ACCOUNT ON SPA_INVOICE_NO=SO_INVOICE_NO  AND SO_INVOICE_NO='"+jTable3.getModel().getValueAt(jTable3.getSelectedRow(), 0).toString()+"' LEFT OUTER JOIN TRANSPORT ON SPA_INVOICE_NO=T_INVOICE_NO LEFT OUTER JOIN STATE_NC AS STATE2 ON STATE2.STATE_NC_ID=T_STATE_NC_ID","/reports/invoice_1.jrxml");
        }
    }//GEN-LAST:event_jButton29ActionPerformed

    private void view_invoice_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_view_invoice_btnActionPerformed
        // TODO add your handling code here:
        hide_panel();
        jPanel6.setVisible(true);
        reset_button_color();
        view_invoice_btn.setBackground(Color.GRAY);
        jTextField14.setText(null);
        getTableData("SELECT SO_INVOICE_NO,MC_NAME,sum(SO_QTY),roundOf(sum(SO_QTY*SO_RATE*(1+(SO_CGST+SO_SGST+SO_IGST)*0.01)),2) from SALES_ORDER INNER JOIN MANUFACTURER_CUSTOMER ON SO_MC_ID=MC_ID group by SO_INVOICE_NO,MC_NAME", jTable3);
    }//GEN-LAST:event_view_invoice_btnActionPerformed

    private void jButton31ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton31ActionPerformed
        // TODO add your handling code here:
    validate_sale_cart_insert();
    }//GEN-LAST:event_jButton31ActionPerformed

    private void jDateChooser7FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jDateChooser7FocusLost
        // TODO add your handling code here:
//        jTextField40.requestFocusInWindow();
    }//GEN-LAST:event_jDateChooser7FocusLost

    private void purchase_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_purchase_btnActionPerformed
        // TODO add your handling code here:
        hide_panel();
        purchase_panel.setVisible(true);
        reset_button_color();
        purchase_btn.setBackground(Color.GRAY);
       puchase_search_invoice.setText("Search By Invoice Number");
       puchase_search_invoice.setForeground(Color.LIGHT_GRAY);
       getTableData("SELECT ROW_NUMBER() OVER() AS R,A.SPA_INVOICE_NO,B.TOT_QTY,A.SPA_INSURANCE_AMOUNT,A.SPA_OTHER_EXPENCE,A.AMOUNT_PAID FROM (SELECT SPA_INVOICE_NO,SPA_INSURANCE_AMOUNT,SPA_OTHER_EXPENCE,roundOf(SPA_SALE_PURCHASE_AMOUNT,2) AS AMOUNT_PAID FROM SALEPURCHASE_ACCOUNT WHERE SPA_INVOICE_NO NOT IN(SELECT DISTINCT SO_INVOICE_NO FROM SALES_ORDER)) AS A, (SELECT PO_INVOICE_NO,SUM(PO_BOX_QTY) as TOT_QTY FROM PURCHASE_ORDER GROUP BY PO_INVOICE_NO) AS B WHERE B.PO_INVOICE_NO=A.SPA_INVOICE_NO", jTable1);
       getTableData("SELECT ROW_NUMBER() OVER() AS R,P_NAME,PO_BATCH_ID,monthYear(PO_EXP_DATE),PO_BOX_QTY,PO_FREE_BOX_QTY,roundOf(PO_BOX_RATE,2),PO_BOX_MRP,PO_TAX,roundOf(PO_BOX_QTY*PO_BOX_RATE,2),roundOf(PO_BOX_QTY*PO_BOX_RATE*(1+PO_TAX*0.01),2) FROM PURCHASE_ORDER INNER JOIN PRODUCT ON PO_P_ID=P_ID", jTable12);
    }//GEN-LAST:event_purchase_btnActionPerformed

    private void productntype_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_productntype_btnActionPerformed
        // TODO add your handling code here:
        hide_panel();
        product_details.setVisible(true);
        reset_button_color();
        productntype_btn.setBackground(Color.GRAY);
         reset_product();
    }//GEN-LAST:event_productntype_btnActionPerformed

    private void sales_rtn_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sales_rtn_btnActionPerformed
        // TODO add your handling code here:
        hide_panel();
        sales_return.setVisible(true);
        reset_button_color();
        sales_rtn_btn.setBackground(Color.GRAY);
        reset_sales_return();
        
    }//GEN-LAST:event_sales_rtn_btnActionPerformed

    private void jButton38ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton38ActionPerformed
        // TODO add your handling code here:
      product_insert();
    }//GEN-LAST:event_jButton38ActionPerformed

    private void jButton48ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton48ActionPerformed
        // TODO add your handling code here:
        reset_product();
    }//GEN-LAST:event_jButton48ActionPerformed

    private void jTextField54KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField54KeyReleased
        // TODO add your handling code here:
        getTableData("SELECT P_ID,P_NAME,P_CODE,PT_NAME FROM PRODUCT INNER JOIN PRODUCT_TYPE ON P_PT_ID=PT_ID WHERE P_NAME LIKE '%"+jTextField54.getText()+"%'", jTable8);
    }//GEN-LAST:event_jTextField54KeyReleased

    private void jButton41ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton41ActionPerformed
        // TODO add your handling code here:
       mc_insert();
    }//GEN-LAST:event_jButton41ActionPerformed

    private void jTextField62KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField62KeyReleased
        // TODO add your handling code here:
        getTableData("SELECT MC_ID,MC_NAME, MC_CONTACT_NO, MC_GST_NO FROM MANUFACTURER_CUSTOMER WHERE MC_NAME LIKE '%"+jTextField62.getText()+"%'", jTable9);
    }//GEN-LAST:event_jTextField62KeyReleased

    private void jButton33ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton33ActionPerformed
        // TODO add your handling code here:
        validate_purchase_cart_insert();
      
       
    }//GEN-LAST:event_jButton33ActionPerformed

    private void jComboBox10ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox10ItemStateChanged
        // TODO add your handling code here:
        MC_ID=0;STATE_ID=0;LOST_NAME=jComboBox10.getSelectedItem().toString();
        try{  
             ps=con.prepareStatement("SELECT MC_ID,MC_STATE_NC_ID FROM MANUFACTURER_CUSTOMER WHERE MC_NAME=?");
             ps.setString(1, jComboBox10.getSelectedItem().toString());
             rs=ps.executeQuery();
             if(rs.next()){MC_ID=rs.getInt(1);
             STATE_ID=rs.getInt(2);
            System.out.println(MC_ID+"\t"+STATE_ID);}
        }catch(Exception e){    e.printStackTrace(); }
        System.out.println("lost name= "+LOST_NAME);
    }//GEN-LAST:event_jComboBox10ItemStateChanged

    private void jComboBox11ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox11ItemStateChanged
        // TODO add your handling code here:
        P_ID=0;LOST_NAME=jComboBox11.getSelectedItem().toString();
        try{         
            
             ps=con.prepareStatement("SELECT P_ID FROM PRODUCT WHERE P_NAME='"+jComboBox11.getSelectedItem().toString()+"'");
             rs=ps.executeQuery();
             if(rs.next())P_ID=rs.getInt(1);
            
        }catch(Exception e){e.printStackTrace();}
    }//GEN-LAST:event_jComboBox11ItemStateChanged

    private void jButton37ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton37ActionPerformed
        // TODO add your handling code here:
     validate_submit_into_purchase_order();
    }//GEN-LAST:event_jButton37ActionPerformed

    private void jButton50ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton50ActionPerformed
        // IMPORT PURCHASE INVOICE
      Date date=null;
        try{  ps=con.prepareStatement("SELECT * FROM PURCHASE_CART");
              rs=ps.executeQuery();
              if(rs.next()){
                 ps=con.prepareStatement("SELECT * FROM PURCHASE_ORDER WHERE PO_INVOICE_NO='"+jTextField19.getText()+"'");
                 rs=ps.executeQuery();
                 if(rs.next()){date=rs.getDate("PO_DATE");}
                 
               if(JOptionPane.showConfirmDialog (null, "Previous Temporary Record exist. Do you want to remove ?","Warning",JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
                ps=con.prepareStatement("INSERT INTO PURCHASE_CART(PC_INVOICE_NO,PC_INVOICE_DATE,PC_P_ID,PC_MC_ID,PC_BATCH_ID,PC_BOX_QTY,PC_FREE_BOX_QTY,PC_BOX_RATE,PC_BOX_MRP,PC_PACK,PC_TAX,PC_DISCOUNT,PC_EXP_DATE,PC_DATE,PC_HSN_CODE) SELECT PO_INVOICE_NO,PO_INVOICE_DATE,PO_P_ID,PO_MC_ID,PO_BATCH_ID,PO_BOX_QTY,PO_FREE_BOX_QTY,PO_BOX_RATE,PO_BOX_MRP,PO_PACK,PO_TAX,PO_DISCOUNT,PO_EXP_DATE,PO_DATE,PO_HSN_CODE FROM PURCHASE_ORDER WHERE PO_INVOICE_NO='"+jTextField19.getText()+"'");
                ps.executeUpdate();
                }  
              }
              else{
                ps=con.prepareStatement("INSERT INTO PURCHASE_CART(PC_INVOICE_NO,PC_INVOICE_DATE,PC_P_ID,PC_MC_ID,PC_BATCH_ID,PC_BOX_QTY,PC_FREE_BOX_QTY,PC_BOX_RATE,PC_BOX_MRP,PC_PACK,PC_TAX,PC_DISCOUNT,PC_EXP_DATE,PC_DATE,PC_HSN_CODE) SELECT PO_INVOICE_NO,PO_INVOICE_DATE,PO_P_ID,PO_MC_ID,PO_BATCH_ID,PO_BOX_QTY,PO_FREE_BOX_QTY,PO_BOX_RATE,PO_BOX_MRP,PO_PACK,PO_TAX,PO_DISCOUNT,PO_EXP_DATE,PO_DATE,PO_HSN_CODE FROM PURCHASE_ORDER WHERE PO_INVOICE_NO='"+jTextField19.getText()+"'");
                ps.executeUpdate();
                if(date!=null){
                ps=con.prepareStatement("SELECT * FROM SALEPURCHASE_ACCOUNT WHERE SPA_INVOICE_NO='"+jTextField19.getText()+"' AND SPA_DATE='"+new SimpleDateFormat("yyyy-MM-dd").format(date)+"'");
                rs=ps.executeQuery();
                if(rs.next()){
                    pur_expence_name_txt.setText(rs.getString("SPA_EXPENCE_NAME"));pur_expence_amount_txt.setText(rs.getFloat("SPA_OTHER_EXPENCE")+"");
                    purchase_insurance_name.setText(rs.getString("SPA_INSURANCE_NAME"));purchase_insurance_charge.setText(rs.getFloat("SPA_INSURANCE_AMOUNT")+"");
                    jTextField49.setText(rs.getString("SPA_SUBMIT_BY"));
                    purchase_grand_total.setText("Grand Total : "+(Float.parseFloat(purchase_insurance_charge.getText())+Float.parseFloat(total_purchase_amount.getText())));
                }
                } // date is not null
                
              }
               reset_purchase_cart();  
               
        }catch(Exception e){ e.printStackTrace();
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_jButton50ActionPerformed

    private void jComboBox17ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox17ItemStateChanged
        // TODO add your handling code here:
        STATE_ID=0;
        try{ ps=con.prepareStatement("SELECT STATE_NC_ID FROM STATE_NC WHERE STATE_NAME='"+jComboBox17.getSelectedItem().toString()+"'");
             rs=ps.executeQuery();
             if(rs.next())STATE_ID=rs.getInt(1);
        }catch(Exception e){JOptionPane.showMessageDialog(null, e);}
    }//GEN-LAST:event_jComboBox17ItemStateChanged

    private void jTextField67FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField67FocusLost
        // TODO add your handling code here:
        HSN_ID=0;SAC_ID=0;
        if(!jTextField67.getText().isEmpty())
        try{ ps=con.prepareStatement("SELECT HSN_ID FROM HSN_TABLE WHERE HSN_CODE="+Integer.parseInt(jTextField67.getText()));
             rs=ps.executeQuery();
             if(rs.next()){
                HSN_ID=rs.getInt(1);
             }
             ps=con.prepareStatement("SELECT SAC_ID FROM SAC_TABLE WHERE SAC_CODE="+Integer.parseInt(jTextField67.getText()));
             rs=ps.executeQuery();
             if(rs.next()){
                SAC_ID=rs.getInt(1);

             }
             
             System.out.println(HSN_ID+"\tHSNSAC\t"+SAC_ID);
            
        }catch(Exception e){JOptionPane.showMessageDialog(null, e);}
    }//GEN-LAST:event_jTextField67FocusLost

    private void jTextField68KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField68KeyReleased
        // TODO add your handling code here:
       if(!jTextField68.getText().isEmpty()&& !jLabel107.getText().isEmpty() && !jTextField42.getText().isEmpty()){
            if(jTextField68.getText().contains("%")){
              jLabel99.setText(Float.parseFloat(jLabel107.getText())-Float.parseFloat(jTextField42.getText())-Float.parseFloat(jLabel107.getText())*Float.parseFloat(jTextField68.getText().substring(0, jTextField68.getText().indexOf("%")))*0.01+Float.parseFloat(jLabel105.getText())+""); 
            }else{
              jLabel99.setText(Float.parseFloat(jLabel107.getText())-Float.parseFloat(jTextField42.getText())-Float.parseFloat(jTextField68.getText())+Float.parseFloat(jLabel105.getText())+"");
            }
        }
    }//GEN-LAST:event_jTextField68KeyReleased

    private void jTextField42KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField42KeyReleased
        // TODO add your handling code here:        
         if(!jTextField68.getText().isEmpty()&& !jLabel107.getText().isEmpty() && !jTextField42.getText().isEmpty()){
            if(jTextField68.getText().contains("%")){
              jLabel99.setText(Float.parseFloat(jLabel107.getText())-Float.parseFloat(jTextField42.getText())-Float.parseFloat(jLabel107.getText())*Float.parseFloat(jTextField68.getText().substring(0, jTextField68.getText().indexOf("%")))*0.01+Float.parseFloat(jLabel105.getText())+""); 
            }else{
              jLabel99.setText(Float.parseFloat(jLabel107.getText())-Float.parseFloat(jTextField42.getText())-Float.parseFloat(jTextField68.getText())+Float.parseFloat(jLabel105.getText())+"");
            }
        }
    }//GEN-LAST:event_jTextField42KeyReleased

    private void jTextArea1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextArea1KeyReleased
        // TODO add your handling code here:
         if(evt.getKeyCode()==KeyEvent.VK_TAB){
           jTextField58.requestFocusInWindow();
        }
    }//GEN-LAST:event_jTextArea1KeyReleased

    private void jTextField57FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField57FocusLost
        // TODO add your handling code here:
        if(jTextArea1.getText().trim().isEmpty())
            jTextArea1.setText(null);
    }//GEN-LAST:event_jTextField57FocusLost

    private void jTextField39FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField39FocusGained
        // TODO add your handling code here:
        if(jTextField39.getText().equals("0"))
            jTextField39.setText("");
    }//GEN-LAST:event_jTextField39FocusGained

    private void jTextField40FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField40FocusGained
        // TODO add your handling code here:
        if(jTextField40.getText().equals("0"))
            jTextField40.setText("");
    }//GEN-LAST:event_jTextField40FocusGained

    private void jTextField43FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField43FocusGained
        // TODO add your handling code here:
//        if(jTextField43.getText().equals("0"))
//            jTextField43.setText("");
    }//GEN-LAST:event_jTextField43FocusGained

    private void jTextField41FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField41FocusGained
        // TODO add your handling code here:
        if(jTextField41.getText().equals("0"))
            jTextField41.setText("");
    }//GEN-LAST:event_jTextField41FocusGained

    private void jTextField39FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField39FocusLost
        // TODO add your handling code here:
        if(jTextField39.getText().isEmpty())
            jTextField39.setText("0");
    }//GEN-LAST:event_jTextField39FocusLost

    private void jTextField40FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField40FocusLost
        // TODO add your handling code here:
         if(jTextField40.getText().isEmpty())
            jTextField40.setText("0");
    }//GEN-LAST:event_jTextField40FocusLost

    private void jTextField41FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField41FocusLost
        // TODO add your handling code here:
         if(jTextField41.getText().isEmpty())
            jTextField41.setText("0");
    }//GEN-LAST:event_jTextField41FocusLost

    private void jTextField43FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField43FocusLost
        // TODO add your handling code here:
         if(jTextField43.getText().isEmpty())
            jTextField43.setText("0");
    }//GEN-LAST:event_jTextField43FocusLost

    private void jTextField49KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField49KeyReleased
        // TODO add your handling code here:
//         if(!jLabel67.getText().isEmpty() && !jTextField48.getText().isEmpty() && !jTextField49.getText().isEmpty()){
//            if(jTextField49.getText().contains("%")){
//                jLabel70.setText(Float.parseFloat(jLabel67.getText())-Float.parseFloat(jTextField48.getText())-Float.parseFloat(jLabel67.getText())*Float.parseFloat(jTextField49.getText().substring(0, jTextField49.getText().indexOf("%")))*0.01+"");
//            }else{
//                jLabel70.setText(Float.parseFloat(jLabel67.getText())-Float.parseFloat(jTextField48.getText())-Float.parseFloat(jTextField49.getText())+"");
//            }
//        }
    }//GEN-LAST:event_jTextField49KeyReleased

    private void jButton35ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton35ActionPerformed
        // TODO add your handling code here:
       purchase_cart_remove();
    }//GEN-LAST:event_jButton35ActionPerformed

    private void jComboBox10FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jComboBox10FocusLost
        // TODO add your handling code here:
       
    }//GEN-LAST:event_jComboBox10FocusLost

    private void jTextField17KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField17KeyReleased
        // TODO add your handling code here:
       
    }//GEN-LAST:event_jTextField17KeyReleased

    private void jComboBox6ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox6ItemStateChanged
        // TODO add your handling code here:
        MC_ID=0;STATE_ID=0;
       try{  ps=con.prepareStatement("SELECT MC_ID,MC_STATE_NC_ID FROM MANUFACTURER_CUSTOMER WHERE MC_NAME=?");
             ps.setString(1, jComboBox6.getSelectedItem().toString());
             rs=ps.executeQuery();
             if(rs.next()){MC_ID=rs.getInt(1);
             STATE_ID=rs.getInt(2);
           //  STATE_NAME=rs.getString(3);
            System.out.println(MC_ID);
             System.out.println("STATE_ID="+STATE_ID);}
           
       }catch(Exception e){}
    }//GEN-LAST:event_jComboBox6ItemStateChanged

    private void jComboBox8ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox8ItemStateChanged
        // TODO add your handling code here:
        P_ID=0;
        try{ ps=con.prepareStatement("SELECT P_ID FROM PRODUCT WHERE P_NAME='"+jComboBox8.getSelectedItem().toString()+"'");
            rs=ps.executeQuery();
            if(rs.next())
                P_ID=rs.getInt(1);
            System.out.println("P_ID="+P_ID);  
            getDataInCombo("SELECT DISTINCT PO_BATCH_ID || ' - [' || PO_PACK ||']' FROM PURCHASE_ORDER INNER JOIN PRODUCT ON PO_P_ID=P_ID AND P_NAME='"+jComboBox8.getSelectedItem().toString()+"'", jComboBox20);
        }catch(Exception e){}
    }//GEN-LAST:event_jComboBox8ItemStateChanged

    private void jButton51ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton51ActionPerformed
        // TODO add your handling code here:
        System.out.println(evt.getSource()+"\t"+evt.getActionCommand());
        //report("SELECT ROW_NUMBER() OVER() AS R,SC_INVOICE_NO,SC_INVOICE_DATE,P_NAME,SC_BATCH_ID,SC_EXP_DATE,SC_MRP,SC_RATE,SC_QTY,SC_QTY_FREE,SC_QTY*SC_RATE,SC_CGST,SC_SGST,SC_IGST,SC_QTY*SC_RATE*(1+(SC_CGST+SC_SGST+SC_IGST)*0.01),STATE_NAME,STATE_CODE,MC_NAME,MC_ADDRESS,MC_GST_NO,MC_PAN_NO,MC_LIC1,MC_LIC2,MC_DOC_LIC,MC_EMAIL,MC_CONTACT_NO,T_MODE,T_V_NO,T_CGST,T_SGST,T_IGST,T_AMOUNT,T_STATE_NC_ID,T_CONSIGNEE_NAME,T_CONSIGNEE_CONTACT,T_CONSIGNEE_ADDRESS,STATE_NAME,STATE_CODE FROM SALES_CART INNER JOIN PRODUCT ON SC_P_ID=P_ID INNER JOIN MANUFACTURER_CUSTOMER ON SC_MC_ID=MC_ID LEFT OUTER JOIN TRANSPORT ON SC_INVOICE_NO=T_INVOICE_NO INNER JOIN STATE_NC ON MC_STATE_NC_ID=STATE_NC_ID LEFT OUTER JOIN SALEPURCHASE_ACCOUNT ON SPA_INVOICE_NO=SC_INVOICE_NO AND SC_INVOICE_NO='"+jTextField17.getText()+"'","reports\\preview_invoice.jrxml");
        report("SELECT ROW_NUMBER() OVER() AS R,SC_INVOICE_NO,SC_INVOICE_DATE,P_NAME,SC_HSN_CODE,SC_BATCH_ID,SC_EXP_DATE,roundOf(SC_MRP,2) as MRP,roundOf(SC_RATE,2) as RATE,SC_QTY,SC_QTY_FREE,roundOf(SC_QTY*SC_RATE,2) AS AMOUNT,SC_CGST,SC_SGST,SC_IGST,roundOf(SC_QTY*SC_RATE*(1+(SC_CGST+SC_SGST+SC_IGST)*0.01),2) AS TOTAL_AMOUNT,STATE_NC.STATE_NAME,STATE_NC.STATE_CODE,MC_NAME,MC_ADDRESS,MC_GST_NO,MC_PAN_NO,MC_LIC1,MC_LIC2,MC_DOC_LIC,MC_EMAIL,MC_CONTACT_NO,MC_FSSAI_CODE,MC_COS_LIC,T_MODE,T_V_NO,T_CGST,T_SGST,T_IGST,T_AMOUNT,T_STATE_NC_ID,T_CONSIGNEE_NAME,T_CONSIGNEE_CONTACT,T_CONSIGNEE_ADDRESS,STATE2.STATE_NAME AS S2,STATE2.STATE_CODE AS SC FROM SALES_CART INNER JOIN PRODUCT ON SC_P_ID=P_ID INNER JOIN MANUFACTURER_CUSTOMER ON SC_MC_ID=MC_ID INNER JOIN STATE_NC ON MC_STATE_NC_ID=STATE_NC.STATE_NC_ID LEFT OUTER JOIN SALEPURCHASE_ACCOUNT ON SPA_INVOICE_NO=SC_INVOICE_NO  AND SC_INVOICE_NO='"+jTextField17.getText()+"' LEFT OUTER JOIN TRANSPORT ON SPA_INVOICE_NO=T_INVOICE_NO LEFT OUTER JOIN STATE_NC AS STATE2 ON STATE2.STATE_NC_ID=T_STATE_NC_ID","/reports/invoice_1_1.jrxml");
    }//GEN-LAST:event_jButton51ActionPerformed

    private void party_mng_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_party_mng_btnActionPerformed
        // TODO add your handling code here:
        hide_panel();
        party_management_panel.setVisible(true);
        reset_button_color();
        party_mng_btn.setBackground(Color.GRAY);
        reset_party();
        
    }//GEN-LAST:event_party_mng_btnActionPerformed

    private void report_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_report_btnActionPerformed
        // TODO add your handling code here:
        hide_panel();
        reports_panel.setVisible(true);
        reset_button_color();
        report_btn.setBackground(Color.GRAY);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);
        Date beforeone_month = cal.getTime();
        jDateChooser6.setDate(beforeone_month);jDateChooser9.setDate(new Date());
    }//GEN-LAST:event_report_btnActionPerformed

    private void jButton65ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton65ActionPerformed
        // TODO add your handling code here:
        hide_panel();
        purchase_order.setVisible(true);
        reset_purchase_order();
    }//GEN-LAST:event_jButton65ActionPerformed

    private void jButton68ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton68ActionPerformed
        // TODO add your handling code here:
        if(VISIBLITY==1){
        hide_panel();VISIBLITY=1;}else{hide_panel();}
        sale_to_customer.setVisible(true);
         jTextField17.requestFocusInWindow();
        // jTextField17.setText(getId());
         if(VISIBLITY==0)
         reset_sales_order();
    }//GEN-LAST:event_jButton68ActionPerformed

    private void manage_user_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_manage_user_btnActionPerformed
        // TODO add your handling code here:
        hide_panel();
        user_panel.setVisible(true);
        reset_button_color();
        manage_user_btn.setBackground(Color.GRAY);
        reset_user();
       
    }//GEN-LAST:event_manage_user_btnActionPerformed

    private void jComboBox10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox10ActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_jComboBox10ActionPerformed

    private void jButton78ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton78ActionPerformed
        // TODO add your handling code here:
        reset_user();
    }//GEN-LAST:event_jButton78ActionPerformed

    private void jButton43ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton43ActionPerformed
        // TODO add your handling code here:
       mc_remove();
    }//GEN-LAST:event_jButton43ActionPerformed

    private void jComboBox20ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox20ItemStateChanged
        // TODO add your handling code here:
        if(jComboBox20.getSelectedIndex()!=0 && !jComboBox20.getSelectedItem().toString().isEmpty())
        try{ int rem_qty=0,sale_qty=0;
        String s=jComboBox20.getSelectedItem().toString();
        String arr[]=s.split(s.substring(s.lastIndexOf("-"), s.indexOf("[")));
            ps=con.prepareStatement("SELECT SUM(SO_QTY) FROM SALES_ORDER WHERE SO_BATCH_ID='"+arr[0]+"'");
            rs=ps.executeQuery();
            if(rs.next())sale_qty=rs.getInt(1);
            ps=con.prepareStatement("SELECT PO_BOX_QTY,PO_BOX_MRP,PO_EXP_DATE,PO_BOX_RATE,PO_TAX,PO_HSN_CODE FROM PURCHASE_ORDER WHERE PO_BATCH_ID='"+arr[0]+"'");
            rs=ps.executeQuery();
            if(rs.next()){
                rem_qty=rs.getInt(1)-sale_qty;jLabel100.setText(rs.getFloat(2)+"");
                jLabel147.setText(new SimpleDateFormat("MM-yy").format(rs.getDate(3)));jLabel149.setText(rs.getFloat(4)+"");
                exp_date=rs.getDate(3);
                jTextField20.setText(rs.getFloat(5)+"");
                jTextField67.setText(rs.getString(6));
            }
            else
            {
                jLabel147.setText("");jLabel100.setText("0");jLabel149.setText("0");
                jTextField20.setText("0");jTextField67.setText("0");
            }
            jLabel102.setText(rem_qty+"");
            
        }catch(Exception e){JOptionPane.showMessageDialog(null, e);}
        else{ jLabel147.setText("");jLabel100.setText("0");jLabel149.setText("0"); jLabel102.setText("0");
        }
    }//GEN-LAST:event_jComboBox20ItemStateChanged

    private void jComboBox18ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox18ItemStateChanged
        // TODO add your handling code here:
        MC_ID=0;
        try{ ps=con.prepareStatement("SELECT MC_ID FROM MANUFACTURER_CUSTOMER WHERE MC_NAME='"+jComboBox18.getSelectedItem().toString()+"'");
             rs=ps.executeQuery();
             if(rs.next()){
                MC_ID=rs.getInt(1);
             }
             
            float total_amt=0,paid_amt=0,total_balance=0;int spa_invoice_qty=0;
            
            ps=con.prepareStatement("SELECT DISTINCT SPA_INVOICE_NO FROM SALEPURCHASE_ACCOUNT INNER JOIN MANUFACTURER_CUSTOMER ON SPA_MC_ID=MC_ID AND MC_NAME='"+jComboBox18.getSelectedItem().toString()+"'");
            rs=ps.executeQuery();
            while(rs.next())spa_invoice_qty++;
                 
             ps=con.prepareStatement("SELECT SPA_SALE_PURCHASE_AMOUNT,SPA_PAID_AMOUNT,SPA_REMAINING_BAL FROM SALEPURCHASE_ACCOUNT WHERE SPA_INVOICE_NO IN(SELECT SPA_INVOICE_NO FROM SALEPURCHASE_ACCOUNT INNER JOIN MANUFACTURER_CUSTOMER ON SPA_MC_ID=MC_ID AND MC_NAME='"+jComboBox18.getSelectedItem().toString()+"') AND SPA_DATE IN(SELECT MAX(SPA_DATE) FROM SALEPURCHASE_ACCOUNT WHERE SPA_INVOICE_NO IN(SELECT SPA_INVOICE_NO FROM SALEPURCHASE_ACCOUNT INNER JOIN MANUFACTURER_CUSTOMER ON SPA_MC_ID=MC_ID AND MC_NAME='"+jComboBox18.getSelectedItem().toString()+"') GROUP BY SPA_INVOICE_NO)");
             rs=ps.executeQuery();
             while(rs.next()){
             total_amt=rs.getFloat(1); paid_amt+=rs.getFloat(2); total_balance=rs.getFloat(3);
             }
             System.out.println(total_amt+"\t"+paid_amt+"\t"+total_balance);
             if(jComboBox18.getSelectedIndex()!=0){jLabel13.setText("Total Invoices : "+spa_invoice_qty);
            jLabel1.setText("Total Amount : "+DBFunctions.roundOf(total_amt, 2));jLabel11.setText("Total Paid Amount : "+DBFunctions.roundOf(paid_amt, 2));jLabel12.setText("Total Due Amount : "+DBFunctions.roundOf(total_balance, 2));
             }else{
             jLabel1.setText("Total Amount : "+0.0);jLabel11.setText("Total Paid Amount : "+0.0);jLabel12.setText("Total Due Amount : "+0.0);    jLabel13.setText("Total Invoices : "+0);
             }
            jLabel114.setText("");
            jLabel119.setText("");
            jLabel134.setText("");
            jTextField69.setText("");
            jTextField70.setText("");
            jTextField71.setText("");
            jComboBox19.setSelectedIndex(0);
      getTableData("SELECT SPA_ID,SPA_INVOICE_NO,formatDate(SPA_INVOICE_DATE),roundOf(doublenotnull(SPA_SALE_PURCHASE_AMOUNT),2), roundOf(doublenotnull(SPA_PAID_AMOUNT),2), roundOf(doublenotnull(SPA_REMAINING_BAL),2), formatDate(SPA_DATE) FROM SALEPURCHASE_ACCOUNT WHERE SPA_MC_ID="+MC_ID+" ORDER BY SPA_INVOICE_NO DESC,SPA_DATE DESC", jTable11);
        
    }catch(Exception e){JOptionPane.showMessageDialog(null, e);}
    }//GEN-LAST:event_jComboBox18ItemStateChanged

    private void jDateChooser10PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jDateChooser10PropertyChange
        // TODO add your handling code here:
        if(jDateChooser10.getDate()!=null && jDateChooser11.getDate()!=null)
           if(jComboBox18.getSelectedIndex()!=0)
           getTableData("SELECT SPA_ID,SPA_INVOICE_NO,formatDate(SPA_INVOICE_DATE),roundOf(doublenotnull(SPA_SALE_PURCHASE_AMOUNT),2), roundOf(doublenotnull(SPA_PAID_AMOUNT),2), roundOf(doublenotnull(SPA_REMAINING_BAL),2), formatDate(SPA_DATE) FROM SALEPURCHASE_ACCOUNT WHERE LOCATE('LEE',SPA_INVOICE_NO)!=0 AND SPA_MC_ID="+MC_ID+" AND SPA_INVOICE_DATE BETWEEN '"+new SimpleDateFormat("yyyy-MM-dd").format(jDateChooser10.getDate())+"' AND '"+new SimpleDateFormat("yyyy-MM-dd").format(jDateChooser11.getDate())+"' ORDER BY SPA_INVOICE_NO DESC,SPA_DATE DESC", jTable11);  
           else
           getTableData("SELECT SPA_ID,SPA_INVOICE_NO,formatDate(SPA_INVOICE_DATE),roundOf(doublenotnull(SPA_SALE_PURCHASE_AMOUNT),2), roundOf(doublenotnull(SPA_PAID_AMOUNT),2), roundOf(doublenotnull(SPA_REMAINING_BAL),2), formatDate(SPA_DATE) FROM SALEPURCHASE_ACCOUNT WHERE LOCATE('LEE',SPA_INVOICE_NO)!=0 AND SPA_INVOICE_DATE BETWEEN '"+new SimpleDateFormat("yyyy-MM-dd").format(jDateChooser10.getDate())+"' AND '"+new SimpleDateFormat("yyyy-MM-dd").format(jDateChooser11.getDate())+"' ORDER BY SPA_INVOICE_NO DESC,SPA_DATE DESC", jTable11);    
    }//GEN-LAST:event_jDateChooser10PropertyChange

    private void jTable11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable11MouseClicked
        // TODO add your handling code here:
        try{ ps=con.prepareStatement("SELECT SPA_INVOICE_NO,SPA_SALE_PURCHASE_AMOUNT,SPA_PAID_AMOUNT,SPA_REMAINING_BAL,SPA_PAYMENT_MODE FROM SALEPURCHASE_ACCOUNT WHERE SPA_INVOICE_NO='"+jTable11.getModel().getValueAt(jTable11.getSelectedRow(), 1).toString()+"' AND SPA_ID=(SELECT MAX(SPA_ID) FROM SALEPURCHASE_ACCOUNT WHERE SPA_INVOICE_NO='"+jTable11.getModel().getValueAt(jTable11.getSelectedRow(), 1).toString()+"')");
             rs=ps.executeQuery();
             if(rs.next()){
                 jTextField69.setText(rs.getString(1));
                 jLabel114.setText(rs.getFloat(2)+"");
                 jLabel119.setText(rs.getFloat(3)+"");
                 jLabel134.setText(rs.getFloat(4)+"");
                 jComboBox19.setSelectedItem(rs.getString(5));
             }
            
        }catch(Exception e){JOptionPane.showMessageDialog(null, e);}
    }//GEN-LAST:event_jTable11MouseClicked

    private void jButton62ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton62ActionPerformed
        // TODO add your handling code here:
        party_insert();
    }//GEN-LAST:event_jButton62ActionPerformed

    private void jDateChooser11PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jDateChooser11PropertyChange
        // TODO add your handling code here:
       if(jDateChooser10.getDate()!=null && jDateChooser11.getDate()!=null )
           if(jComboBox18.getSelectedIndex()!=0)
          getTableData("SELECT SPA_ID,SPA_INVOICE_NO,formatDate(SPA_INVOICE_DATE),roundOf(doublenotnull(SPA_SALE_PURCHASE_AMOUNT),2), roundOf(doublenotnull(SPA_PAID_AMOUNT),2), roundOf(doublenotnull(SPA_REMAINING_BAL),2), formatDate(SPA_DATE) FROM SALEPURCHASE_ACCOUNT WHERE LOCATE('LEE',SPA_INVOICE_NO)!=0 AND SPA_MC_ID="+MC_ID+" AND SPA_INVOICE_DATE BETWEEN '"+new SimpleDateFormat("yyyy-MM-dd").format(jDateChooser10.getDate())+"' AND '"+new SimpleDateFormat("yyyy-MM-dd").format(jDateChooser11.getDate())+"' ORDER BY SPA_INVOICE_NO DESC,SPA_DATE DESC", jTable11);  
        else
          getTableData("SELECT SPA_ID,SPA_INVOICE_NO,formatDate(SPA_INVOICE_DATE),roundOf(doublenotnull(SPA_SALE_PURCHASE_AMOUNT),2), roundOf(doublenotnull(SPA_PAID_AMOUNT),2), roundOf(doublenotnull(SPA_REMAINING_BAL),2), formatDate(SPA_DATE) FROM SALEPURCHASE_ACCOUNT WHERE LOCATE('LEE',SPA_INVOICE_NO)!=0 AND SPA_INVOICE_DATE BETWEEN '"+new SimpleDateFormat("yyyy-MM-dd").format(jDateChooser10.getDate())+"' AND '"+new SimpleDateFormat("yyyy-MM-dd").format(jDateChooser11.getDate())+"' ORDER BY SPA_INVOICE_NO DESC,SPA_DATE DESC", jTable11);    
    }//GEN-LAST:event_jDateChooser11PropertyChange

    private void jTextField71KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField71KeyReleased
        // TODO add your handling code here:
        getTableData("SELECT SPA_ID,SPA_INVOICE_NO,formatDate(SPA_INVOICE_DATE),roundOf(doublenotnull(SPA_SALE_PURCHASE_AMOUNT),2), roundOf(doublenotnull(SPA_PAID_AMOUNT),2), roundOf(doublenotnull(SPA_REMAINING_BAL),2), formatDate(SPA_DATE) FROM SALEPURCHASE_ACCOUNT WHERE LOCATE('LEE',SPA_INVOICE_NO)!=0 AND SPA_INVOICE_NO LIKE '%"+jTextField71.getText()+"%' ORDER BY SPA_INVOICE_NO DESC,SPA_DATE DESC", jTable11);
    }//GEN-LAST:event_jTextField71KeyReleased

    private void jTextField42FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField42FocusGained
        // TODO add your handling code here:
        if(jTextField42.getText().equals("0"))
            jTextField42.setText("");
    }//GEN-LAST:event_jTextField42FocusGained

    private void jTextField68FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField68FocusGained
        // TODO add your handling code here:
         if(jTextField68.getText().equals("0"))
            jTextField68.setText("");
    }//GEN-LAST:event_jTextField68FocusGained

    private void jTextField42FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField42FocusLost
        // TODO add your handling code here:
        if(jTextField42.getText().isEmpty())
            jTextField42.setText("0");
    }//GEN-LAST:event_jTextField42FocusLost

    private void jTextField68FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField68FocusLost
        // TODO add your handling code here:
        if(jTextField68.getText().isEmpty())
            jTextField68.setText("0");
    }//GEN-LAST:event_jTextField68FocusLost

    private void jButton34ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton34ActionPerformed
        // TODO add your handling code here:
        validate_purchase_cart_update();
        
    }//GEN-LAST:event_jButton34ActionPerformed

    private void jButton45ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton45ActionPerformed
        // TODO add your handling code here:
       sale_return_insert();
    }//GEN-LAST:event_jButton45ActionPerformed

    private void jTable7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable7MouseClicked
        // TODO add your handling code here:
        try{PreparedStatement ps=con.prepareStatement("SELECT * FROM PURCHASE_CART INNER JOIN MANUFACTURER_CUSTOMER ON PC_MC_ID=MC_ID INNER JOIN PRODUCT ON PC_P_ID=P_ID AND PC_ID="+Integer.parseInt(jTable7.getModel().getValueAt(jTable7.getSelectedRow(), 0).toString()));
            ResultSet rs=ps.executeQuery();
             if(rs.next()){
                jDateChooser1.setDate(rs.getDate("PC_INVOICE_DATE")); System.out.println("3");
                jTextField37.setText(rs.getString("PC_BATCH_ID")); System.out.println("4");
                jTextField38.setText(rs.getInt("PC_BOX_QTY")+""); System.out.println("5");
                jTextField39.setText(rs.getInt("PC_FREE_BOX_QTY")+""); System.out.println("6");
                jTextField40.setText(rs.getFloat("PC_BOX_RATE")+""); System.out.println("7");
                jTextField41.setText(rs.getFloat("PC_BOX_MRP")+""); System.out.println("8");
                jTextField43.setText(rs.getString("PC_PACK")); System.out.println("9");
               
                jTextField46.setText(rs.getFloat("PC_TAX")+""); System.out.println("12");
                jTextField47.setText(rs.getFloat("PC_DISCOUNT")+""); System.out.println("13");
                jComboBox10.setSelectedItem(rs.getString("MC_NAME")); System.out.println("1");
                jComboBox11.setSelectedItem(rs.getString("P_NAME")); System.out.println("2");
                jComboBox12.setSelectedItem(new SimpleDateFormat("yyyy").format(rs.getDate("PC_EXP_DATE"))); System.out.println("2");
                Calendar cal=Calendar.getInstance();
                cal.setTime(rs.getDate("PC_EXP_DATE"));
                jMonthChooser1.setMonth(cal.get(Calendar.MONTH));
                purchase_hsn_code.setText(rs.getString("PC_HSN_CODE"));
             }
            jButton34.setVisible(true); jButton35.setVisible(true);
        }catch(Exception e){JOptionPane.showMessageDialog(null, e);}
    }//GEN-LAST:event_jTable7MouseClicked

    private void jButton33KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton33KeyReleased
        // TODO add your handling code here:
        if(evt.getKeyCode()==KeyEvent.VK_ENTER)
            validate_purchase_cart_insert();
    }//GEN-LAST:event_jButton33KeyReleased

    private void jButton34KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton34KeyReleased
        // TODO add your handling code here:
        if(evt.getKeyCode()==KeyEvent.VK_ENTER)
           validate_purchase_cart_update();
    }//GEN-LAST:event_jButton34KeyReleased

    private void jButton35KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton35KeyReleased
        // TODO add your handling code here:
        if(evt.getKeyCode()==KeyEvent.VK_ENTER)
        purchase_cart_remove();
    }//GEN-LAST:event_jButton35KeyReleased

    private void jButton31KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton31KeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==KeyEvent.VK_ENTER)
            validate_sale_cart_insert();
    }//GEN-LAST:event_jButton31KeyPressed

    private void jButton24KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton24KeyPressed
        // TODO add your handling code here:
         if(evt.getKeyCode()==KeyEvent.VK_ENTER)
            validate_sale_cart_update();
    }//GEN-LAST:event_jButton24KeyPressed

    private void jButton25ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jButton25ItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton25ItemStateChanged

    private void jButton25KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton25KeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==KeyEvent.VK_ENTER)
            sale_cart_remove();
    }//GEN-LAST:event_jButton25KeyPressed

    private void jButton26KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton26KeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==KeyEvent.VK_ENTER)
             new TransportDetails(this, true, con).setVisible(true);
    }//GEN-LAST:event_jButton26KeyPressed

    private void jButton51KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton51KeyPressed
        // TODO add your handling code here:
         if(evt.getKeyCode()==KeyEvent.VK_ENTER)
        report("SELECT ROW_NUMBER() OVER() AS R,SC_INVOICE_NO,SC_INVOICE_DATE,P_NAME,SC_BATCH_ID,SC_EXP_DATE,SC_MRP,SC_RATE,SC_QTY,SC_QTY_FREE,SC_QTY*SC_RATE,SC_CGST,SC_SGST,SC_IGST,SC_QTY*SC_RATE*(1+(SC_CGST+SC_SGST+SC_IGST)*0.01),STATE_NAME,STATE_CODE,MC_NAME,MC_ADDRESS,MC_GST_NO,MC_PAN_NO,MC_LIC1,MC_LIC2,MC_DOC_LIC,MC_EMAIL,MC_CONTACT_NO,T_MODE,T_V_NO,T_CGST,T_SGST,T_IGST,T_AMOUNT,T_STATE_NC_ID,T_CONSIGNEE_NAME,T_CONSIGNEE_CONTACT,T_CONSIGNEE_ADDRESS,STATE_NAME,STATE_CODE FROM SALES_CART INNER JOIN PRODUCT ON SC_P_ID=P_ID INNER JOIN MANUFACTURER_CUSTOMER ON SC_MC_ID=MC_ID LEFT OUTER JOIN TRANSPORT ON SC_INVOICE_NO=T_INVOICE_NO INNER JOIN STATE_NC ON MC_STATE_NC_ID=STATE_NC_ID LEFT OUTER JOIN SALEPURCHASE_ACCOUNT ON SPA_INVOICE_NO=SC_INVOICE_NO AND SC_INVOICE_NO='"+jTextField17.getText()+"'","reports\\preview_invoice.jrxml");
    }//GEN-LAST:event_jButton51KeyPressed

    private void jButton28KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton28KeyPressed
        // TODO add your handling code here:
         if(evt.getKeyCode()==KeyEvent.VK_ENTER)
             sale_order_insert();
    }//GEN-LAST:event_jButton28KeyPressed

    private void jTextField43KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField43KeyReleased
        // TODO add your handling code here:
//        if(!jTextField43.getText().isEmpty() && !jTextField39.getText().isEmpty()){
//            jTextField44.setText(Integer.parseInt(jTextField39.getText())*Integer.parseInt(jTextField43.getText())+"");
//        }
    }//GEN-LAST:event_jTextField43KeyReleased

    private void jButton56ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton56ActionPerformed
        // TODO add your handling code here:
        if(jDateChooser6.getDate()!=null && jDateChooser9.getDate()!=null){
        createFolder("Purchase");
        String fileName=path+"Purchase\\PURCHASE_"+new SimpleDateFormat("dd-MM-yyyy").format(new Date())+".xls";
        new CreateExcelFile(con).createFile("SELECT PO_INVOICE_NO AS INVOICE_NO,PO_INVOICE_DATE AS INVOICE_DATE,MC_NAME AS MANUFACTURER_COMPANY,P_NAME AS PRODUCT_NAME,PO_BATCH_ID AS BATCH_ID,PO_EXP_DATE AS EXPIRY_DATE,PO_BOX_QTY AS QTY,PO_FREE_BOX_QTY AS FREE_QTY,PO_PACK AS PACK,PO_BOX_RATE AS RATE,PO_BOX_MRP AS MRP,PO_TAX AS TAX,PO_DISCOUNT AS DISCOUNT,roundOf(PO_BOX_QTY*PO_BOX_RATE*(1+PO_TAX*0.01)-PO_BOX_QTY*PO_BOX_RATE*PO_DISCOUNT*0.01,2) AS AMOUNT FROM PURCHASE_ORDER INNER JOIN PRODUCT ON PO_P_ID=P_ID INNER JOIN MANUFACTURER_CUSTOMER ON PO_MC_ID=MC_ID AND PO_INVOICE_DATE BETWEEN '"+new SimpleDateFormat("yyyy-MM-dd").format(jDateChooser6.getDate())+"' AND '"+new SimpleDateFormat("yyyy-MM-dd").format(jDateChooser9.getDate())+"'","",fileName,"Invoice_Sheet" );
        new CreateExcelFile(con).updateExcel(fileName, "SELECT 'TOTAL ' ,'','','','','','','','','','','','',SUM(A.AMOUNT) FROM (SELECT PO_INVOICE_NO AS INVOICE_NO,PO_INVOICE_DATE AS INVOICE_DATE,MC_NAME AS MANUFACTURER_COMPANY,P_NAME AS PRODUCT_NAME,PO_BATCH_ID AS BATCH_ID,PO_EXP_DATE AS EXPIRY_DATE,PO_BOX_QTY AS QTY,PO_FREE_BOX_QTY AS FREE_QTY,PO_PACK AS PACK,PO_BOX_RATE AS RATE,PO_BOX_MRP AS MRP,PO_TAX AS TAX,PO_DISCOUNT AS DISCOUNT,roundOf(PO_BOX_QTY*PO_BOX_RATE*(1+PO_TAX*0.01)-PO_BOX_QTY*PO_BOX_RATE*PO_DISCOUNT*0.01,2) AS AMOUNT FROM PURCHASE_ORDER INNER JOIN PRODUCT ON PO_P_ID=P_ID INNER JOIN MANUFACTURER_CUSTOMER ON PO_MC_ID=MC_ID AND PO_INVOICE_DATE BETWEEN '"+new SimpleDateFormat("yyyy-MM-dd").format(jDateChooser6.getDate())+"' AND '"+new SimpleDateFormat("yyyy-MM-dd").format(jDateChooser9.getDate())+"') AS A", "Invoice_Sheet");
//        if(new File(fileName).exists())
//        JOptionPane.showMessageDialog(null, "Excel file generated !");
        }
    }//GEN-LAST:event_jButton56ActionPerformed

    private void jComboBox14ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox14ItemStateChanged
        // TODO add your handling code here:
        P_ID=0;
        try{ ps=con.prepareStatement("SELECT P_ID FROM PRODUCT WHERE P_NAME='"+jComboBox14.getSelectedItem().toString()+"'");
        rs=ps.executeQuery();
        if(rs.next())
            P_ID=rs.getInt(1);
        }catch(Exception e){JOptionPane.showMessageDialog(null, e);}
    }//GEN-LAST:event_jComboBox14ItemStateChanged

    private void jComboBox15ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox15ItemStateChanged
        // TODO add your handling code here:
        MC_ID=0;
        try{ ps=con.prepareStatement("SELECT MC_ID FROM MANUFACTURER_CUSTOMER WHERE MC_NAME='"+jComboBox15.getSelectedItem().toString()+"'");
        rs=ps.executeQuery();
        if(rs.next())
            MC_ID=rs.getInt(1);
        }catch(Exception e){JOptionPane.showMessageDialog(null, e);}
    }//GEN-LAST:event_jComboBox15ItemStateChanged

    private void jButton46ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton46ActionPerformed
        // TODO add your handling code here:
      sale_return_update();
    }//GEN-LAST:event_jButton46ActionPerformed

    private void jTable10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable10MouseClicked
        // TODO add your handling code here:
//        try{ ps=con.prepareStatement("SELECT * FROM SALES_RETURN INNER JOIN PRODUCT ON SR_P_ID=P_ID INNER JOIN MANUFACTURER_CUSTOMER ON SR_MC_ID=MC_ID WHERE SR_INVOICE_NO='"+jTable10.getModel().getValueAt(jTable10.getSelectedRow(), 0).toString()+"'");
//                rs=ps.executeQuery();
//                if(rs.next()){
//                    jDateChooser8.setDate(rs.getDate("SR_DATE"));
//                    jTextField63.setText(rs.getString("sr_batch_id"));
//                   jTextField64.setText(rs.getString("sr_BOX_QTY"));
//                   jTextField65.setText(rs.getString("SR_STRIP_QTY"));
//                   jComboBox13.setSelectedItem(rs.getString("SR_INVOICE_NO"));
//                   jComboBox15.setSelectedItem(rs.getString("MC_NAME"));  
//                  //  jComboBox14.setSelectedItem(rs.getString("P_NAME"));
//                   jButton46.setVisible(true);
//                   jButton47.setVisible(true);
//                }
//           // reset_sales_return();
//        }catch(Exception e){JOptionPane.showMessageDialog(null, e);}
        if(jTable10.getSelectedRow()!=-1){
        jTextField63.setText(jTable10.getValueAt(jTable10.getSelectedRow(),1).toString());
        jComboBox14.setSelectedItem(jTable10.getValueAt(jTable10.getSelectedRow(),2).toString());
        }
        
        
    }//GEN-LAST:event_jTable10MouseClicked

    private void jTextField66KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField66KeyReleased
        // TODO add your handling code here:
        getTableData("SELECT SR_INVOICE_NO, SR_BATCH_ID, MC_NAME, SUM(SR_BOX_QTY) FROM SALES_RETURN INNER JOIN MANUFACTURER_CUSTOMER ON SR_MC_ID=MC_ID WHERE MC_NAME LIKE'%"+jTextField66.getText()+"%' GROUP BY SR_INVOICE_NO,SR_BATCH_ID,MC_NAME", jTable10);
    }//GEN-LAST:event_jTextField66KeyReleased

    private void jButton42ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton42ActionPerformed
        // TODO add your handling code here:
        mc_update();
    }//GEN-LAST:event_jButton42ActionPerformed

    private void jTable9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable9MouseClicked
        // TODO add your handling code here:
        MC_ID=0;
        try{ ps=con.prepareStatement("SELECT MC_ID,MC_NAME,MC_CONTACT_NO,MC_EMAIL,MC_ADDRESS,MC_GST_NO,MC_LIC1,MC_LIC2,MC_DOC_LIC,STATE_NAME,MC_PAN_NO,MC_COS_LIC,MC_FSSAI_CODE FROM MANUFACTURER_CUSTOMER INNER JOIN STATE_NC ON MC_STATE_NC_ID=STATE_NC_ID AND MC_ID="+Integer.parseInt(jTable9.getModel().getValueAt(jTable9.getSelectedRow(), 0).toString()));
             rs=ps.executeQuery();
         if(rs.next()){ MC_ID=rs.getInt(1);
         jTextField55.setText(rs.getString("MC_NAME"));
         jTextField56.setText(rs.getString("MC_CONTACT_NO"));
         jTextField57.setText(rs.getString("MC_EMAIL"));
         jTextArea1.setText(rs.getString("MC_ADDRESS"));
         jTextField58.setText(rs.getString("MC_GST_NO"));
         jTextField59.setText(rs.getString("MC_LIC1"));
         jTextField60.setText(rs.getString("MC_LIC2"));
         jTextField61.setText(rs.getString("MC_DOC_LIC"));
         jTextField18.setText(rs.getString("MC_PAN_NO"));
         jTextField21.setText(rs.getString("MC_COS_LIC"));
         jTextField18.setText(rs.getString("MC_FSSAI_CODE"));
         jTextField44.setText(rs.getString("MC_PAN_NO"));
         jComboBox17.setSelectedItem(rs.getString("STATE_NAME")); //MC_PAN_NO
         jButton42.setVisible(true);
         jButton43.setVisible(true);
         
         }
        }catch(Exception e){JOptionPane.showMessageDialog(null, e);}
    }//GEN-LAST:event_jTable9MouseClicked

    private void jButton39ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton39ActionPerformed
        // TODO add your handling code here:
        product_update();
    }//GEN-LAST:event_jButton39ActionPerformed

    private void jTable8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable8MouseClicked
        // TODO add your handling code here:
        if(jTable8.getSelectedRow()!=-1){
            P_ID=Integer.parseInt(jTable8.getValueAt(jTable8.getSelectedRow(), 0).toString());
        }
        try{ ps=con.prepareStatement("SELECT PT_NAME,P_NAME,P_CODE FROM PRODUCT INNER JOIN PRODUCT_TYPE ON P_PT_ID=PT_ID AND P_ID="+P_ID);
        rs=ps.executeQuery();
        if(rs.next()){
            jTextField51.setText(rs.getString(1));
            jTextField52.setText(rs.getString(2));
            jTextField53.setText(rs.getString(3));
            jButton39.setVisible(true);
            jButton40.setVisible(true); 
        }
            
        }catch(Exception e){e.printStackTrace();}
           
    }//GEN-LAST:event_jTable8MouseClicked

    private void jButton40ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton40ActionPerformed
        // TODO add your handling code here:
        product_remove();
    }//GEN-LAST:event_jButton40ActionPerformed

    
    private void jButton63ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton63ActionPerformed
        // TODO add your handling code here:
        reset_sales_return();
    }//GEN-LAST:event_jButton63ActionPerformed

    private void jButton45KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton45KeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==KeyEvent.VK_ENTER)
            sale_return_insert();
    }//GEN-LAST:event_jButton45KeyPressed

    private void jButton46KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton46KeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==KeyEvent.VK_ENTER)
            sale_return_update();
    }//GEN-LAST:event_jButton46KeyPressed

    private void jButton63KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton63KeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==KeyEvent.VK_ENTER)
           reset_sales_return();
    }//GEN-LAST:event_jButton63KeyPressed

    private void jButton41KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton41KeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==KeyEvent.VK_ENTER)
            mc_insert();
    }//GEN-LAST:event_jButton41KeyPressed

    private void jButton42KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton42KeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==KeyEvent.VK_ENTER)
            mc_update();
    }//GEN-LAST:event_jButton42KeyPressed

    private void jButton43KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton43KeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==KeyEvent.VK_ENTER)
            mc_remove();
    }//GEN-LAST:event_jButton43KeyPressed

    private void jButton38KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton38KeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==KeyEvent.VK_ENTER)
            product_insert();
    }//GEN-LAST:event_jButton38KeyPressed

    private void jButton39KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton39KeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==KeyEvent.VK_ENTER)
            product_update();
    }//GEN-LAST:event_jButton39KeyPressed

    private void jButton40KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton40KeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==KeyEvent.VK_ENTER)
            product_remove();
    }//GEN-LAST:event_jButton40KeyPressed

    private void jButton48KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton48KeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==KeyEvent.VK_ENTER)
          reset_product();
    }//GEN-LAST:event_jButton48KeyPressed

    private void jButton66ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton66ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton66ActionPerformed

    private void jButton57ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton57ActionPerformed
        // TODO add your handling code here:
        if(jDateChooser6.getDate()!=null && jDateChooser9.getDate()!=null){
            createFolder("Expiry");
            String fileName=path+"Expiry\\EXPIRY"+new SimpleDateFormat("dd-MM-yyyy").format(new Date())+".xls";
            new CreateExcelFile(con).createFile("SELECT C.P_NAME AS PRODUCT_NAME ,C.MC_NAME AS LEETY_NAME,A.PO_BATCH_ID AS BATCH_ID,A.PS-B.SS AS QUANTITY,C.DATEVALUE AS EXPIRY_DATE FROM (SELECT PO_BATCH_ID,SUM(PO_BOX_QTY) AS PS FROM PURCHASE_ORDER INNER JOIN PRODUCT ON PO_P_ID=P_ID AND {fn timestampdiff(SQL_TSI_DAY, PO_EXP_DATE, CURRENT_DATE)} BETWEEN 0 AND 90 GROUP BY PO_BATCH_ID) AS A,(SELECT SO_BATCH_ID,SUM(SO_QTY) AS SS FROM SALES_ORDER INNER JOIN PRODUCT ON SO_P_ID=P_ID GROUP BY SO_BATCH_ID) AS B,(SELECT P_NAME,MC_NAME,PO_BATCH_ID,formatDate(PO_EXP_DATE) AS DATEVALUE FROM PURCHASE_ORDER INNER JOIN PRODUCT ON PO_P_ID=P_ID INNER JOIN MANUFACTURER_CUSTOMER ON PO_MC_ID=MC_ID) AS C WHERE A.PO_BATCH_ID=B.SO_BATCH_ID AND A.PO_BATCH_ID=C.PO_BATCH_ID AND C.DATEVALUE BETWEEN '"+new SimpleDateFormat().format(jDateChooser6.getDate())+"' AND '"+new SimpleDateFormat().format(jDateChooser9.getDate())+"' ORDER BY C.DATEVALUE ", "", fileName, "Expiry_sheet");
            if(new File(fileName).exists())
            JOptionPane.showMessageDialog(null, "Excel File generated !");
        }
    }//GEN-LAST:event_jButton57ActionPerformed

    private void jButton37KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton37KeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==KeyEvent.VK_ENTER)validate_submit_into_purchase_order();
    }//GEN-LAST:event_jButton37KeyPressed

    private void jDateChooser12PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jDateChooser12PropertyChange
        // TODO add your handling code here:
        if(jDateChooser12.getDate()!=null && jDateChooser13.getDate()!=null)
            getTableData("SELECT PO_ID,P_NAME,PO_BATCH_ID,monthYear(PO_EXP_DATE),PO_BOX_QTY,PO_FREE_BOX_QTY,PO_BOX_RATE,PO_BOX_MRP,PO_TAX,PO_BOX_QTY*PO_BOX_RATE,roundOf(PO_BOX_QTY*PO_BOX_RATE*(1+PO_TAX*0.01),2) FROM PURCHASE_ORDER INNER JOIN PRODUCT ON PO_P_ID=P_ID AND PO_INVOICE_DATE BETWEEN '"+new SimpleDateFormat("yyyy-MM-dd").format(jDateChooser12.getDate())+"' AND '"+new SimpleDateFormat("yyyy-MM-dd").format(jDateChooser13.getDate())+"'", jTable12);
    }//GEN-LAST:event_jDateChooser12PropertyChange

    private void jDateChooser13PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jDateChooser13PropertyChange
        // TODO add your handling code here:
         if(jDateChooser12.getDate()!=null && jDateChooser13.getDate()!=null)
            getTableData("SELECT PO_ID,P_NAME,PO_BATCH_ID,monthYear(PO_EXP_DATE),PO_BOX_QTY,PO_FREE_BOX_QTY,PO_BOX_RATE,PO_BOX_MRP,PO_TAX,PO_BOX_QTY*PO_BOX_RATE,roundOf(PO_BOX_QTY*PO_BOX_RATE*(1+PO_TAX*0.01),2) FROM PURCHASE_ORDER INNER JOIN PRODUCT ON PO_P_ID=P_ID AND PO_INVOICE_DATE BETWEEN '"+new SimpleDateFormat("yyyy-MM-dd").format(jDateChooser12.getDate())+"' AND '"+new SimpleDateFormat("yyyy-MM-dd").format(jDateChooser13.getDate())+"'", jTable12);
    }//GEN-LAST:event_jDateChooser13PropertyChange

    private void jButton67ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton67ActionPerformed
        // TODO add your handling code here:
        getTableData("SELECT ROW_NUMBER() OVER() AS R,P_NAME,PO_BATCH_ID,monthYear(PO_EXP_DATE),PO_BOX_QTY,PO_FREE_BOX_QTY,roundOf(PO_BOX_RATE,2),PO_BOX_MRP,PO_TAX,roundOf(PO_BOX_QTY*PO_BOX_RATE,2),roundOf(PO_BOX_QTY*PO_BOX_RATE*(1+PO_TAX*0.01),2) FROM PURCHASE_ORDER INNER JOIN PRODUCT ON PO_P_ID=P_ID", jTable12);
    }//GEN-LAST:event_jButton67ActionPerformed

    private void jButton58ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton58ActionPerformed
        // TODO add your handling code here:
        //getTableData("SELECT B.P_NAME,A.PS-A.SS,B.PO_BATCH_ID FROM (SELECT PO_P_ID,SUM(PO_BOX_QTY) AS PS,SUM(SO_QTY) AS SS FROM SALES_ORDER INNER JOIN PURCHASE_ORDER ON SO_P_ID=PO_P_ID GROUP BY PO_P_ID) AS A,(SELECT PO_P_ID,P_NAME,PO_BATCH_ID FROM SALES_ORDER INNER JOIN PURCHASE_ORDER ON SO_P_ID=PO_P_ID INNER JOIN PRODUCT ON PO_P_ID=P_ID ) AS B WHERE A.PO_P_ID=B.PO_P_ID AND  (A.PS-A.SS)*100/A.PS>=50", jTable16);
         //if(jDateChooser6.getDate()!=null && jDateChooser9.getDate()!=null)
        createFolder("Required");
        String fileName=path+"Required\\REQUIRED_"+new SimpleDateFormat("dd-MM-yyyy").format(new Date())+".xls";
             new CreateExcelFile(con).createFile("SELECT B.P_NAME AS PRODUCT_NAME,A.PS-A.SS AS QUANTITY,B.PO_BATCH_ID AS BATCH_ID FROM (SELECT PO_P_ID,PO_BATCH_ID,SUM(PO_BOX_QTY) AS PS,SUM(SO_QTY) AS SS FROM SALES_ORDER INNER JOIN PURCHASE_ORDER ON SO_P_ID=PO_P_ID GROUP BY PO_P_ID,PO_BATCH_ID) AS A, (SELECT PO_P_ID,P_NAME,PO_BATCH_ID FROM SALES_ORDER INNER JOIN PURCHASE_ORDER ON SO_P_ID=PO_P_ID INNER JOIN PRODUCT ON PO_P_ID=P_ID AND PO_BATCH_ID=SO_BATCH_ID) AS B WHERE A.PO_BATCH_ID=B.PO_BATCH_ID AND  (A.PS-A.SS)*100/A.PS BETWEEN 0 AND 50", "", fileName, "req_sheet");
             if(new File(fileName).exists())
              JOptionPane.showMessageDialog(null, "Excel file Generate");
    }//GEN-LAST:event_jButton58ActionPerformed

    private void jButton59ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton59ActionPerformed
        // TODO add your handling code here:
        createFolder("SalesReturn");
        String fileName=path+"SalesReturn\\SALES_RETURN_"+new SimpleDateFormat("dd-MM-yyyy").format(new Date())+".xls";
        if(jDateChooser6.getDate()!=null && jDateChooser9.getDate()!=null)
            new CreateExcelFile(con).createFile("SELECT SR_INVOICE_NO AS INVOICE_NO,SR_INVOICE_DATE AS INVOICE_DATE,MC_NAME AS LEETY,SR_BATCH_ID AS BATCH_ID,SR_BOX_QTY AS QTY,SR_STRIP_QTY AS STRIP_QTY FROM SALES_RETURN INNER JOIN MANUFACTURER_CUSTOMER ON SR_MC_ID=MC_ID WHERE SR_INVOICE_DATE BETWEEN '"+new SimpleDateFormat("yyyy-MM-dd").format(jDateChooser6.getDate())+"' AND '"+new SimpleDateFormat("yyyy-MM-dd").format(jDateChooser9.getDate())+"'", "",fileName, "SALES_RETURN");
        if(new File(fileName).exists())
          JOptionPane.showMessageDialog(null, "Excel file Generate");
    }//GEN-LAST:event_jButton59ActionPerformed

    private void jComboBox21ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox21ItemStateChanged
        // TODO add your handling code here:
        try{ ps=con.prepareStatement("SELECT p_name FROM PRODUCT WHERE P_CODE='"+jComboBox21.getSelectedItem().toString()+"'");
             rs=ps.executeQuery();
             if(rs.next()){
                 jComboBox8.setSelectedItem(rs.getString(1));
             }
            
        }catch(Exception e){e.printStackTrace();}
    }//GEN-LAST:event_jComboBox21ItemStateChanged

    private void jComboBox13ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox13ItemStateChanged
        // TODO add your handling code here:
        getDataInCombo("SELECT DISTINCT P_NAME FROM SALES_ORDER INNER JOIN PRODUCT ON SO_P_ID=P_ID AND SO_INVOICE_NO='"+jComboBox13.getSelectedItem().toString()+"'", jComboBox14);
      //  getDataInCombo("SELECT DISTINCT MC_NAME FROM SALEPURCHASE_ACCOUNT INNER JOIN MANUFACTURER_CUSTOMER ON SPA_MC_ID=MC_ID AND SPA_INVOICE_NO='"+jComboBox13.getSelectedItem().toString()+"'", jComboBox15);
        try{ ps=con.prepareStatement("SELECT DISTINCT MC_NAME FROM SALEPURCHASE_ACCOUNT INNER JOIN MANUFACTURER_CUSTOMER ON SPA_MC_ID=MC_ID AND SPA_INVOICE_NO='"+jComboBox13.getSelectedItem().toString()+"'");
             rs=ps.executeQuery();
             if(rs.next())
                 jComboBox15.setSelectedItem(rs.getString(1));
            getTableData("SELECT SO_INVOICE_NO, SO_BATCH_ID, P_NAME,formatDate(SO_INVOICE_DATE), SUM(SO_QTY) FROM SALES_ORDER INNER JOIN PRODUCT ON SO_P_ID=P_ID GROUP BY SO_INVOICE_NO,SO_BATCH_ID,P_NAME,SO_INVOICE_DATE", jTable10);
        }catch(Exception e){e.printStackTrace();}
    }//GEN-LAST:event_jComboBox13ItemStateChanged

    private void jButton54ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton54ActionPerformed
        // TODO add your handling code here: STOCK
        createFolder("Stock");
        String fileName=path+"Stock\\STOCK_"+new SimpleDateFormat("dd-MM-yyyy").format(new Date())+".xls";
        new CreateExcelFile(con).createFile("SELECT C.P_NAME AS PRODUCT_NAME,A.PS AS PURCHASE_QTY,intnotnull(B.SS) AS SALE_QTY,A.PS-intnotnull(B.SS) AS REM_QTY,A.PO_BATCH_ID AS BATCH_ID FROM (SELECT PO_BATCH_ID,SUM(PO_BOX_QTY) AS PS FROM PURCHASE_ORDER GROUP BY PO_BATCH_ID) AS A LEFT OUTER JOIN (SELECT SO_BATCH_ID,SUM(SO_QTY) AS SS FROM SALES_ORDER GROUP BY SO_BATCH_ID) AS B ON A.PO_BATCH_ID=B.SO_BATCH_ID LEFT OUTER JOIN (SELECT P_NAME,PO_BATCH_ID FROM PURCHASE_ORDER INNER JOIN PRODUCT ON PO_P_ID=P_ID) AS C ON C.PO_BATCH_ID=A.PO_BATCH_ID", "", fileName, "stock_sheet");
        if(new File(fileName).exists())
        JOptionPane.showMessageDialog(null, "Excel file generated !");
    }//GEN-LAST:event_jButton54ActionPerformed

    private void jButton55ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton55ActionPerformed
        // TODO add your handling code here:FREE STOCK
         createFolder("FreeStock");
         String fileName=path+"FreeStock\\FreeStock_"+new SimpleDateFormat("dd-MM-yyyy").format(new Date())+".xls";
        new CreateExcelFile(con).createFile("SELECT C.P_NAME AS PRODUCT_NAME,A.PS AS PURCHASE_FREE_QTY,intnotnull(B.SS) AS SALE_FREE_QTY,A.PS-intnotnull(B.SS) AS REM_FREE_QTY,A.PO_BATCH_ID AS BATCH_ID FROM (SELECT PO_BATCH_ID,SUM(PO_FREE_BOX_QTY) AS PS FROM PURCHASE_ORDER GROUP BY PO_BATCH_ID) AS A LEFT OUTER JOIN (SELECT SO_BATCH_ID,SUM(SO_QTY_FREE) AS SS FROM SALES_ORDER GROUP BY SO_BATCH_ID) AS B ON A.PO_BATCH_ID=B.SO_BATCH_ID LEFT OUTER JOIN (SELECT P_NAME,PO_BATCH_ID FROM PURCHASE_ORDER INNER JOIN PRODUCT ON PO_P_ID=P_ID) AS C ON C.PO_BATCH_ID=A.PO_BATCH_ID", "", fileName, "free_stock_sheet");
        if(new File(fileName).exists())
        JOptionPane.showMessageDialog(null, "Excel file generated !");
    }//GEN-LAST:event_jButton55ActionPerformed

    private void jTable14MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable14MouseClicked
        // TODO add your handling code here:
        if(LoginPanel.USER_NAME.equals("admin")||LoginPanel.USER_NAME.equals("mujeeb")){
        jButton76.setVisible(true);
        jButton77.setVisible(true);
         jPanel11.setVisible(true);
         int user_id=0;
         try{ ps=con.prepareStatement("SELECT USER_ID,USER_NAME FROM LOGIN_TABLE WHERE USER_NAME='"+jTable14.getValueAt(jTable14.getSelectedRow(), 0).toString()+"'");
        rs=ps.executeQuery();
        if(rs.next()){
            jTextField72.setText(rs.getString("USER_NAME"));
            user_id=rs.getInt("USER_ID");
        }
            
        }catch(Exception e){e.printStackTrace();}
            fetch_permission(user_id);
        }
       
    }//GEN-LAST:event_jTable14MouseClicked

    private void sale_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sale_btnActionPerformed
        // TODO add your handling code here:
        if(VISIBLITY==1){
        hide_panel();
        VISIBLITY=1;}
        else
        {   hide_panel();}
        sale_panel.setVisible(true);
        reset_button_color();
        sale_btn.setBackground(Color.GRAY);
        getTableData("SELECT ROW_NUMBER() OVER() AS S_NO,SO_INVOICE_NO,formatDate(SO_INVOICE_DATE),P_NAME,SO_QTY,SO_QTY_FREE,roundOf(SO_RATE,2),SO_MRP,(SO_CGST+SO_SGST+SO_IGST),roundOf(SO_QTY*SO_RATE*(1+(SO_CGST+SO_SGST+SO_IGST)*0.01),2) FROM SALES_ORDER INNER JOIN PRODUCT ON SO_P_ID=P_ID", jTable13);
        search_by_user_name.setText("Search By User Name or Invoice No");
        search_by_user_name.setForeground(Color.LIGHT_GRAY);
        

    }//GEN-LAST:event_sale_btnActionPerformed

    private void jButton61ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton61ActionPerformed
        // TODO add your handling code here:PROFIT_LOSS
        if(jDateChooser6.getDate()!=null && jDateChooser9.getDate()!=null){ 
            createFolder("ProfitLoss");
        String fileName=path+"ProfitLoss\\PROFIT_LOSS_"+new SimpleDateFormat("dd-MM-yyyy").format(new Date())+".xls";
            new CreateExcelFile(con).createFile("SELECT A.PO_INVOICE_NO,A.PO_INVOICE_DATE,A.P_NAME,A.PO_BATCH_ID,B.SO_INVOICE_NO,B.SO_INVOICE_DATE,A.PUR_RATE,B.SALE_RATE,B.SALE_RATE-A.PUR_RATE AS PROFIT_LOSS FROM (SELECT PO_INVOICE_NO,PO_INVOICE_DATE,P_NAME,PO_BATCH_ID,SUM(PO_BOX_RATE) AS PUR_RATE FROM PURCHASE_ORDER INNER JOIN PRODUCT ON PO_P_ID=P_ID WHERE PO_INVOICE_DATE BETWEEN '"+new SimpleDateFormat("yyyy-MM-dd").format(jDateChooser6.getDate())+"' AND '"+new SimpleDateFormat("yyyy-MM-dd").format(jDateChooser9.getDate())+"' GROUP BY PO_INVOICE_NO,PO_INVOICE_DATE,PO_BATCH_ID,P_NAME) AS A, (SELECT SO_INVOICE_NO,SO_INVOICE_DATE,P_NAME,SO_BATCH_ID,SUM(SO_RATE) AS SALE_RATE FROM SALES_ORDER INNER JOIN PRODUCT ON SO_P_ID=P_ID WHERE SO_INVOICE_DATE BETWEEN '"+new SimpleDateFormat("yyyy-MM-dd").format(jDateChooser6.getDate())+"' AND '"+new SimpleDateFormat("yyyy-MM-dd").format(jDateChooser9.getDate())+"' GROUP BY SO_INVOICE_NO,SO_INVOICE_DATE,SO_BATCH_ID,P_NAME) AS B WHERE A.PO_BATCH_ID=B.SO_BATCH_ID ", "", fileName, "PROFIT_LOSS");
            new CreateExcelFile(con).updateExcel(fileName, "SELECT 'TOTAL ','','','','','',SUM(A.PUR_RATE),SUM(B.SALE_RATE),SUM(B.SALE_RATE-A.PUR_RATE) AS PROFIT_LOSS FROM (SELECT PO_INVOICE_NO,PO_INVOICE_DATE,P_NAME,PO_BATCH_ID,SUM(PO_BOX_RATE) AS PUR_RATE FROM PURCHASE_ORDER INNER JOIN PRODUCT ON PO_P_ID=P_ID WHERE PO_INVOICE_DATE BETWEEN '"+new SimpleDateFormat("yyyy-MM-dd").format(jDateChooser6.getDate())+"' AND '"+new SimpleDateFormat("yyyy-MM-dd").format(jDateChooser9.getDate())+"' GROUP BY PO_INVOICE_NO,PO_INVOICE_DATE,PO_BATCH_ID,P_NAME) AS A, (SELECT SO_INVOICE_NO,SO_INVOICE_DATE,P_NAME,SO_BATCH_ID,SUM(SO_RATE) AS SALE_RATE FROM SALES_ORDER INNER JOIN PRODUCT ON SO_P_ID=P_ID WHERE SO_INVOICE_DATE BETWEEN '"+new SimpleDateFormat("yyyy-MM-dd").format(jDateChooser6.getDate())+"' AND '"+new SimpleDateFormat("yyyy-MM-dd").format(jDateChooser9.getDate())+"' GROUP BY SO_INVOICE_NO,SO_INVOICE_DATE,SO_BATCH_ID,P_NAME) AS B WHERE A.PO_BATCH_ID=B.SO_BATCH_ID ", "PROFIT_LOSS");
        }
    }//GEN-LAST:event_jButton61ActionPerformed

    private void jButton75ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton75ActionPerformed
        // TODO add your handling code here:
       add_user();
    }//GEN-LAST:event_jButton75ActionPerformed

    private void jTextField63KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField63KeyReleased
        // TODO add your handling code here:
//        try{ ps=con.prepareStatement("SELECT DISTINCT P_NAME FROM SALES_ORDER INNER JOIN PRODUCT ON SO_P_ID=P_ID AND P_NAME='"+jComboBox13.getSelectedItem().toString()+"'");
//             rs=ps.executeQuery();
//             if(rs.next()){
//                 jComboBox14.setSelectedItem(rs.getString(1));
//             }
//            
//        }catch(Exception e){JOptionPane.showMessageDialog(null, e);}
    }//GEN-LAST:event_jTextField63KeyReleased

    private void jButton47KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton47KeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==KeyEvent.VK_ENTER)
        sale_return_remove();
    }//GEN-LAST:event_jButton47KeyPressed

    private void jButton47ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton47ActionPerformed
        // TODO add your handling code here:
        sale_return_remove();
    }//GEN-LAST:event_jButton47ActionPerformed

    private void required_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_required_btnActionPerformed
        // TODO add your handling code here:
        hide_panel();
        required_panel.setVisible(true);
        reset_button_color();
        required_btn.setBackground(Color.GRAY);
        getTableData("SELECT C.P_NAME,C.MC_NAME,A.PO_BATCH_ID,A.PS-B.SS,C.DATEVALUE FROM (SELECT PO_BATCH_ID,SUM(PO_BOX_QTY) AS PS FROM PURCHASE_ORDER INNER JOIN PRODUCT ON PO_P_ID=P_ID AND {fn timestampdiff(SQL_TSI_DAY, PO_EXP_DATE, CURRENT_DATE)} BETWEEN 0 AND 180 GROUP BY PO_BATCH_ID) AS A,(SELECT SO_BATCH_ID,SUM(SO_QTY) AS SS FROM SALES_ORDER INNER JOIN PRODUCT ON SO_P_ID=P_ID GROUP BY SO_BATCH_ID) AS B,(SELECT P_NAME,MC_NAME,PO_BATCH_ID,formatDate(PO_EXP_DATE) AS DATEVALUE FROM PURCHASE_ORDER INNER JOIN PRODUCT ON PO_P_ID=P_ID INNER JOIN MANUFACTURER_CUSTOMER ON PO_MC_ID=MC_ID) AS C WHERE A.PO_BATCH_ID=B.SO_BATCH_ID AND A.PO_BATCH_ID=C.PO_BATCH_ID ORDER BY C.DATEVALUE", jTable15);
        getTableData("SELECT B.P_NAME AS PRODUCT_NAME,A.PS-A.SS AS QUANTITY,B.PO_BATCH_ID AS BATCH_ID FROM (SELECT PO_P_ID,PO_BATCH_ID,SUM(PO_BOX_QTY) AS PS,SUM(SO_QTY) AS SS FROM SALES_ORDER INNER JOIN PURCHASE_ORDER ON SO_P_ID=PO_P_ID GROUP BY PO_P_ID,PO_BATCH_ID) AS A,(SELECT PO_P_ID,P_NAME,PO_BATCH_ID FROM SALES_ORDER INNER JOIN PURCHASE_ORDER ON SO_P_ID=PO_P_ID INNER JOIN PRODUCT ON PO_P_ID=P_ID AND PO_BATCH_ID=SO_BATCH_ID) AS B WHERE A.PO_BATCH_ID=B.PO_BATCH_ID AND  (A.PS-A.SS)*100/A.PS BETWEEN 0 AND 50", jTable16);
        getTableData("SELECT ROW_NUMBER() OVER() AS R,C.P_NAME AS PRODUCT_NAME,A.PS-intnotnull(B.SS) AS QUANTITY,A.PO_BATCH_ID AS BATCH_ID FROM (SELECT PO_BATCH_ID,SUM(PO_BOX_QTY) AS PS FROM PURCHASE_ORDER GROUP BY PO_BATCH_ID) AS A LEFT OUTER JOIN (SELECT SO_BATCH_ID,SUM(SO_QTY) AS SS FROM SALES_ORDER GROUP BY SO_BATCH_ID) AS B ON A.PO_BATCH_ID=B.SO_BATCH_ID LEFT OUTER JOIN (SELECT P_NAME,PO_BATCH_ID FROM PURCHASE_ORDER INNER JOIN PRODUCT ON PO_P_ID=P_ID) AS C ON C.PO_BATCH_ID=A.PO_BATCH_ID", jTable17);
        getTableData("SELECT ROW_NUMBER() OVER() AS R,P_NAME,SUM(SO_QTY),SO_BATCH_ID FROM SALES_ORDER INNER JOIN PRODUCT ON SO_P_ID=P_ID GROUP BY SO_BATCH_ID,P_NAME", jTable5);
    }//GEN-LAST:event_required_btnActionPerformed

    private void jButton76ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton76ActionPerformed
        // TODO add your handling code here:
       update_user();
    }//GEN-LAST:event_jButton76ActionPerformed

    private void jButton77ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton77ActionPerformed
        // TODO add your handling code here:
       delete_user();
    }//GEN-LAST:event_jButton77ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
//        String panel_name[]={"purchase_order","sale_to_customer","sales_return","party_management_panel","reports_panel","sale_panel","user_panel","purchase_panel","sale_panel"};
      manage_permission();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton1KeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==KeyEvent.VK_ENTER)
            manage_permission();
    }//GEN-LAST:event_jButton1KeyPressed

    private void jButton78KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton78KeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==KeyEvent.VK_ENTER)reset_user();
    }//GEN-LAST:event_jButton78KeyPressed

    private void jButton75KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton75KeyPressed
        // TODO add your handling code here:
         if(evt.getKeyCode()==KeyEvent.VK_ENTER)add_user();
    }//GEN-LAST:event_jButton75KeyPressed

    private void jButton76KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton76KeyPressed
        // TODO add your handling code here:
         if(evt.getKeyCode()==KeyEvent.VK_ENTER)update_user();
    }//GEN-LAST:event_jButton76KeyPressed

    private void jButton77KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton77KeyPressed
        // TODO add your handling code here:
         if(evt.getKeyCode()==KeyEvent.VK_ENTER)delete_user();
    }//GEN-LAST:event_jButton77KeyPressed

    private void jButton72ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton72ActionPerformed
        // TODO add your handling code here:
       invoice_statement();
    }//GEN-LAST:event_jButton72ActionPerformed

    private void jButton73ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton73ActionPerformed
        // TODO add your handling code here:
       party_due_balance_sheet();
    }//GEN-LAST:event_jButton73ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        // SC_INVOICE_NO,SC_P_ID,SC_MC_ID,SC_HSN_CODE,SC_BATCH_ID,SC_QTY,SC_QTY_FREE,SC_RATE,SC_CGST,SC_SGST,SC_IGST,SC_MRP,SC_EXP_DATE,SC_INVOICE_DATE
        try{  ps=con.prepareStatement("SELECT * FROM SALES_CART");
              rs=ps.executeQuery();
              if(rs.next()){
               if(JOptionPane.showConfirmDialog (null, "Previous Temporary Record exist. Do you want to remove ?","Warning",JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
                ps=con.prepareStatement("INSERT INTO SALES_CART(SC_INVOICE_NO,SC_P_ID,SC_MC_ID,SC_HSN_CODE,SC_BATCH_ID,SC_QTY,SC_QTY_FREE,SC_RATE,SC_CGST,SC_SGST,SC_IGST,SC_MRP,SC_EXP_DATE,SC_INVOICE_DATE) SELECT SO_INVOICE_NO,SO_P_ID,SO_MC_ID,SO_HSN_CODE,SO_BATCH_ID,SO_QTY,SO_QTY_FREE,SO_RATE,SO_CGST,SO_SGST,SO_IGST,SO_MRP,SO_EXP_DATE,SO_INVOICE_DATE FROM SALES_ORDER WHERE SO_INVOICE_NO='"+jTextField17.getText()+"'");
                ps.executeUpdate();
                reset_sales_cart();
                }    
              }
              else{
                ps=con.prepareStatement("INSERT INTO SALES_CART(SC_INVOICE_NO,SC_P_ID,SC_MC_ID,SC_HSN_CODE,SC_BATCH_ID,SC_QTY,SC_QTY_FREE,SC_RATE,SC_CGST,SC_SGST,SC_IGST,SC_MRP,SC_EXP_DATE,SC_INVOICE_DATE) SELECT SO_INVOICE_NO,SO_P_ID,SO_MC_ID,SO_HSN_CODE,SO_BATCH_ID,SO_QTY,SO_QTY_FREE,SO_RATE,SO_CGST,SO_SGST,SO_IGST,SO_MRP,SO_EXP_DATE,SO_INVOICE_DATE FROM SALES_ORDER WHERE SO_INVOICE_NO='"+jTextField17.getText()+"'");
                ps.executeUpdate();
                reset_sales_cart();  
              }
        }catch(Exception e){ e.printStackTrace();
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void pur_expence_amount_txtKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_pur_expence_amount_txtKeyReleased
        // TODO add your handling code here:
       
    }//GEN-LAST:event_pur_expence_amount_txtKeyReleased

    private void purchase_insurance_chargeKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_purchase_insurance_chargeKeyReleased
        // TODO add your handling code here:
       if(!purchase_insurance_charge.getText().isEmpty() && !total_purchase_amount.getText().isEmpty())
       purchase_grand_total.setText("Grand Total : "+DBFunctions.roundOf((Float.parseFloat(purchase_insurance_charge.getText())+Float.parseFloat(total_purchase_amount.getText())), 2));
    }//GEN-LAST:event_purchase_insurance_chargeKeyReleased

    private void puchase_search_invoiceFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_puchase_search_invoiceFocusLost
        // TODO add your handling code here:
        if(puchase_search_invoice.getText().isEmpty()){
            puchase_search_invoice.setText("Search By Invoice Number");
            puchase_search_invoice.setForeground(Color.LIGHT_GRAY);
        }
    }//GEN-LAST:event_puchase_search_invoiceFocusLost

    private void puchase_search_invoiceFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_puchase_search_invoiceFocusGained
        // TODO add your handling code here:
        if(puchase_search_invoice.getText().equals("Search By Invoice Number")){
           puchase_search_invoice.setText("");
           puchase_search_invoice.setForeground(Color.BLACK);
        }
    }//GEN-LAST:event_puchase_search_invoiceFocusGained

    private void pur_expence_amount_txtFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_pur_expence_amount_txtFocusGained
        // TODO add your handling code here:
        if(pur_expence_amount_txt.getText().equals("0"))
            pur_expence_amount_txt.setText("");
    }//GEN-LAST:event_pur_expence_amount_txtFocusGained

    private void purchase_insurance_chargeFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_purchase_insurance_chargeFocusGained
        // TODO add your handling code here:
         if(purchase_insurance_charge.getText().equals("0"))
            purchase_insurance_charge.setText("");
    }//GEN-LAST:event_purchase_insurance_chargeFocusGained

    private void pur_expence_amount_txtFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_pur_expence_amount_txtFocusLost
        // TODO add your handling code here:
        if(pur_expence_amount_txt.getText().isEmpty())
            pur_expence_amount_txt.setText("0");
    }//GEN-LAST:event_pur_expence_amount_txtFocusLost

    private void purchase_insurance_chargeFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_purchase_insurance_chargeFocusLost
        // TODO add your handling code here:
        if(purchase_insurance_charge.getText().isEmpty())
            purchase_insurance_charge.setText("0");
    }//GEN-LAST:event_purchase_insurance_chargeFocusLost

    private void pur_expence_name_txtFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_pur_expence_name_txtFocusGained
        // TODO add your handling code here:
        if(pur_expence_name_txt.getText().equals("N/A"))
            pur_expence_name_txt.setText("");
    }//GEN-LAST:event_pur_expence_name_txtFocusGained

    private void purchase_insurance_nameFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_purchase_insurance_nameFocusGained
        // TODO add your handling code here:
        if(purchase_insurance_name.getText().equals("N/A"))
            purchase_insurance_name.setText("");
    }//GEN-LAST:event_purchase_insurance_nameFocusGained

    private void purchase_insurance_nameFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_purchase_insurance_nameFocusLost
        // TODO add your handling code here:
        if(purchase_insurance_name.getText().isEmpty())
            purchase_insurance_name.setText("N/A");
    }//GEN-LAST:event_purchase_insurance_nameFocusLost

    private void pur_expence_name_txtFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_pur_expence_name_txtFocusLost
        // TODO add your handling code here:
        if(pur_expence_name_txt.getText().isEmpty())
            pur_expence_name_txt.setText("N/A");
    }//GEN-LAST:event_pur_expence_name_txtFocusLost

    private void puchase_search_invoiceKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_puchase_search_invoiceKeyReleased
        // TODO add your handling code here:
        getTableData("SELECT ROW_NUMBER() OVER() AS R,A.SPA_INVOICE_NO,B.TOT_QTY,roundOf(A.SPA_INSURANCE_AMOUNT,2),roundOf(A.SPA_OTHER_EXPENCE,2),roundOf(A.AMOUNT_PAID,2) FROM (SELECT SPA_INVOICE_NO,SPA_INSURANCE_AMOUNT,SPA_OTHER_EXPENCE,roundOf(SPA_SALE_PURCHASE_AMOUNT,2) AS AMOUNT_PAID FROM SALEPURCHASE_ACCOUNT WHERE SPA_INVOICE_NO NOT IN(SELECT DISTINCT SO_INVOICE_NO FROM SALES_ORDER)) AS A, (SELECT PO_INVOICE_NO,SUM(PO_BOX_QTY) as TOT_QTY FROM PURCHASE_ORDER GROUP BY PO_INVOICE_NO) AS B WHERE B.PO_INVOICE_NO=A.SPA_INVOICE_NO AND A.SPA_INVOICE_NO LIKE '%"+puchase_search_invoice.getText()+"%'", jTable1);
        getTableData("SELECT ROW_NUMBER() OVER() AS R,P_NAME,PO_BATCH_ID,monthYear(PO_EXP_DATE),PO_BOX_QTY,PO_FREE_BOX_QTY,roundOf(PO_BOX_RATE,2),PO_BOX_MRP,PO_TAX,roundOf(PO_BOX_QTY*PO_BOX_RATE,2),roundOf(PO_BOX_QTY*PO_BOX_RATE*(1+PO_TAX*0.01),2) FROM PURCHASE_ORDER INNER JOIN PRODUCT ON PO_P_ID=P_ID WHERE PO_INVOICE_NO LIKE '%"+puchase_search_invoice.getText()+"%'", jTable12);
    }//GEN-LAST:event_puchase_search_invoiceKeyReleased

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:
      //  purchase_invoice_delete_btn.setVisible(true);
    }//GEN-LAST:event_jTable1MouseClicked

    private void purchase_invoice_delete_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_purchase_invoice_delete_btnActionPerformed
        // TODO add your handling code here:
        if(jTable1.getSelectedRow()!=-1){
            try{ ps=con.prepareStatement("DELETE FROM PURCHASE_ORDER WHERE PO_INVOICE_NO='"+jTable1.getValueAt(jTable1.getSelectedRow(), 1).toString()+"'");
                 ps.executeUpdate();
                 ps=con.prepareStatement("DELETE FROM SALEPURCHASE_ACCOUNT WHERE SPA_INVOICE_NO='"+jTable1.getValueAt(jTable1.getSelectedRow(), 1).toString()+"'");
                 ps.executeUpdate();
                getTableData("SELECT ROW_NUMBER() OVER() AS R,A.SPA_INVOICE_NO,B.TOT_QTY,A.SPA_INSURANCE_AMOUNT,A.SPA_OTHER_EXPENCE,A.AMOUNT_PAID FROM (SELECT SPA_INVOICE_NO,SPA_INSURANCE_AMOUNT,SPA_OTHER_EXPENCE,roundOf(SPA_SALE_PURCHASE_AMOUNT,2) AS AMOUNT_PAID FROM SALEPURCHASE_ACCOUNT WHERE SPA_INVOICE_NO NOT IN(SELECT DISTINCT SO_INVOICE_NO FROM SALES_ORDER)) AS A, (SELECT PO_INVOICE_NO,SUM(PO_BOX_QTY) as TOT_QTY FROM PURCHASE_ORDER GROUP BY PO_INVOICE_NO) AS B WHERE B.PO_INVOICE_NO=A.SPA_INVOICE_NO", jTable1);
                getTableData("SELECT ROW_NUMBER() OVER() AS R,P_NAME,PO_BATCH_ID,monthYear(PO_EXP_DATE),PO_BOX_QTY,PO_FREE_BOX_QTY,PO_BOX_RATE,PO_BOX_MRP,PO_TAX,PO_BOX_QTY*PO_BOX_RATE,roundOf(PO_BOX_QTY*PO_BOX_RATE*(1+PO_TAX*0.01),2) FROM PURCHASE_ORDER INNER JOIN PRODUCT ON PO_P_ID=P_ID", jTable12);
            }catch(Exception e){e.printStackTrace();}
        }
            
    }//GEN-LAST:event_purchase_invoice_delete_btnActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
         if(jDateChooser6.getDate()!=null && jDateChooser9.getDate()!=null){ 
             createFolder("OtherExpences");
             String fileName=path+"OtherExpences\\OtherExpences_"+new SimpleDateFormat("dd-MM-yyyy").format(new Date())+".xls";
             new CreateExcelFile(con).createFile("SELECT ROW_NUMBER() OVER() AS S_No,A.SPA_INVOICE_NO AS INVOICE_NO,A.SPA_INVOICE_DATE AS INVOICE_DATE,B.TOT_QTY AS TOTAL_QTY,A.SPA_INSURANCE_AMOUNT AS INSURANCE_AMOUNT,A.SPA_OTHER_EXPENCE AS OTHER_EXPENCE,A.AMOUNT_PAID AS AMOUNT_PAID FROM (SELECT SPA_INVOICE_NO,SPA_INVOICE_DATE,SPA_INSURANCE_AMOUNT,SPA_OTHER_EXPENCE,roundOf(SPA_SALE_PURCHASE_AMOUNT,2) AS AMOUNT_PAID FROM SALEPURCHASE_ACCOUNT WHERE SPA_INVOICE_NO NOT IN(SELECT DISTINCT SO_INVOICE_NO FROM SALES_ORDER)) AS A,  (SELECT PO_INVOICE_NO,SUM(PO_BOX_QTY) as TOT_QTY FROM PURCHASE_ORDER GROUP BY PO_INVOICE_NO) AS B WHERE B.PO_INVOICE_NO=A.SPA_INVOICE_NO AND A.SPA_INVOICE_DATE BETWEEN '"+new SimpleDateFormat("yyyy-MM-dd").format(jDateChooser6.getDate())+"' AND '"+new SimpleDateFormat("yyyy-MM-dd").format(jDateChooser9.getDate())+"'", "", fileName, "OTHER_EXPENCE");
           //  new CreateExcelFile(con).updateExcel(fileName,"SELECT 'TOTAL','','','','',SUM(A.TOTAL_QUANTITY),'',SUM(A.TOTAL_AMOUNT),'',SUM(A.CGST_AMOUNT),'',SUM(A.SGST_AMOUNT),'',SUM(A.IGST_AMOUNT),'',SUM(A.GST_AMOUNT),SUM(A.AMOUNT) FROM (SELECT 'TOTAL ','','','','',SUM(SO_QTY) AS TOTAL_QUANTITY,'',SUM(SO_QTY*SO_RATE) AS TOTAL_AMOUNT,'',SUM(SO_QTY*SO_RATE*SO_CGST*0.01) AS CGST_AMOUNT,'',SUM(SO_QTY*SO_RATE*SO_SGST*0.01) AS SGST_AMOUNT,'',SUM(SO_QTY*SO_RATE*SO_IGST*0.01) AS IGST_AMOUNT,'',SUM(SO_QTY*SO_RATE*(SO_CGST+SO_SGST+SO_IGST)*0.01) AS GST_AMOUNT,SUM(SO_QTY*SO_RATE*(1+(SO_CGST+SO_SGST+SO_IGST)*0.01)) AS AMOUNT FROM SALES_ORDER INNER JOIN MANUFACTURER_CUSTOMER ON SO_MC_ID=MC_ID AND SO_INVOICE_DATE BETWEEN '"+new SimpleDateFormat("yyyy-MM-dd").format(jDateChooser6.getDate())+"' AND '"+new SimpleDateFormat("yyyy-MM-dd").format(jDateChooser9.getDate())+"' GROUP BY (SO_CGST+SO_SGST+SO_IGST),SO_HSN_CODE,SO_INVOICE_NO,SO_INVOICE_DATE,MC_GST_NO,MC_NAME )  AS A", "GST_REPORT");
             if(new File(fileName).exists())
                 JOptionPane.showMessageDialog(null, "Excel File Generated !");
         }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void search_by_user_nameFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_search_by_user_nameFocusLost
        // TODO add your handling code here:
        if(search_by_user_name.getText().isEmpty()){
        search_by_user_name.setText("Search By User Name or Invoice No");
        search_by_user_name.setForeground(Color.LIGHT_GRAY);
        }
    }//GEN-LAST:event_search_by_user_nameFocusLost

    private void search_by_user_nameFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_search_by_user_nameFocusGained
        // TODO add your handling code here:
        if(search_by_user_name.getText().equals("Search By User Name or Invoice No")){
            search_by_user_name.setText("");
        }
        search_by_user_name.setForeground(Color.BLACK);
    }//GEN-LAST:event_search_by_user_nameFocusGained

    private void search_by_user_nameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_search_by_user_nameKeyReleased
        // TODO add your handling code here:
        if(jDateChooser14.getDate()!=null && jDateChooser15.getDate()!=null){
         
         // getTableData("SELECT ROW_NUMBER() OVER() AS S_NO,SO_INVOICE_NO,formatDate(SO_INVOICE_DATE),P_NAME,SO_QTY,SO_QTY_FREE,roundOf(SO_RATE,2),SO_MRP,(SO_CGST+SO_SGST+SO_IGST),roundOf(SO_QTY*SO_RATE*(1+(SO_CGST+SO_SGST+SO_IGST)*0.01),2) FROM SALES_ORDER INNER JOIN PRODUCT ON SO_P_ID=P_ID INNER JOIN SALEPURCHASE_ACCOUNT ON SPA_INVOICE_NO=SO_INVOICE_NO AND SPA_SUBMIT_BY='"+search_by_user_name.getText()+"' AND SO_INVOICE_DATE BETWEEN '"+new SimpleDateFormat("yyyy-MM-dd").format(jDateChooser14.getDate())+"' AND '"+new SimpleDateFormat("yyyy-MM-dd").format(jDateChooser15.getDate())+"'", jTable13);
          getTableData("SELECT ROW_NUMBER() OVER() AS S_NO,SO_INVOICE_NO,formatDate(SO_INVOICE_DATE),P_NAME,SO_QTY,SO_QTY_FREE,roundOf(SO_RATE,2),SO_MRP,(SO_CGST+SO_SGST+SO_IGST),roundOf(SO_QTY*SO_RATE*(1+(SO_CGST+SO_SGST+SO_IGST)*0.01),2) FROM SALES_ORDER INNER JOIN PRODUCT ON SO_P_ID=P_ID WHERE SO_INVOICE_NO IN(SELECT SPA_INVOICE_NO FROM SALEPURCHASE_ACCOUNT WHERE UPPER(SPA_SUBMIT_BY) LIKE UPPER('%"+search_by_user_name.getText()+"%') OR UPPER(SPA_INVOICE_NO) LIKE UPPER('%"+search_by_user_name.getText()+"%')) AND SO_INVOICE_DATE BETWEEN '"+new SimpleDateFormat("yyyy-MM-dd").format(jDateChooser14.getDate())+"' AND '"+new SimpleDateFormat("yyyy-MM-dd").format(jDateChooser15.getDate())+"'", jTable13);
        }else
        getTableData("SELECT ROW_NUMBER() OVER() AS S_NO,SO_INVOICE_NO,formatDate(SO_INVOICE_DATE),P_NAME,SO_QTY,SO_QTY_FREE,roundOf(SO_RATE,2),SO_MRP,(SO_CGST+SO_SGST+SO_IGST),roundOf(SO_QTY*SO_RATE*(1+(SO_CGST+SO_SGST+SO_IGST)*0.01),2) FROM SALES_ORDER INNER JOIN PRODUCT ON SO_P_ID=P_ID WHERE SO_INVOICE_NO IN(SELECT SPA_INVOICE_NO FROM SALEPURCHASE_ACCOUNT WHERE UPPER(SPA_SUBMIT_BY) LIKE UPPER('%"+search_by_user_name.getText()+"%') OR UPPER(SPA_INVOICE_NO) LIKE UPPER('%"+search_by_user_name.getText()+"%'))", jTable13);
    }//GEN-LAST:event_search_by_user_nameKeyReleased

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        if(jDateChooser6.getDate()!=null && jDateChooser9.getDate()!=null){
            createFolder("By_User");
        String fileName=path+"By_User\\By_User_"+new SimpleDateFormat("dd-MM-yyyy").format(new Date())+".xls";
        new CreateExcelFile(con).createFile("SELECT ROW_NUMBER() OVER() AS S_NO,SO_INVOICE_NO AS INVOICE_NO,formatDate(SO_INVOICE_DATE) AS INVOICE_DATE,P_NAME,SO_QTY AS QTY,SO_QTY_FREE AS FREE_QTY,SO_RATE AS RATE,SO_MRP AS MRP,(SO_CGST+SO_SGST+SO_IGST) AS GST,roundOf(SO_QTY*SO_RATE*(1+(SO_CGST+SO_SGST+SO_IGST)*0.01),2) AS AMOUNT,SPA_SUBMIT_BY AS INVOICE_BY_USER FROM SALES_ORDER INNER JOIN PRODUCT ON SO_P_ID=P_ID INNER JOIN SALEPURCHASE_ACCOUNT ON SO_INVOICE_NO=SPA_INVOICE_NO AND SPA_SUBMIT_BY is not null", "", fileName, "invoice_by_user");
        if(new File(fileName).exists())
            JOptionPane.showMessageDialog(null, "Excel file generated !");
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton71ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton71ActionPerformed
        // TODO add your handling code here:
        if(jDateChooser14.getDate()!=null && jDateChooser15.getDate()!=null){
           if(search_by_user_name.getText().equals("Search By User Name or Invoice No"))
          getTableData("SELECT ROW_NUMBER() OVER() AS S_NO,SO_INVOICE_NO,formatDate(SO_INVOICE_DATE),P_NAME,SO_QTY,SO_QTY_FREE,roundOf(SO_RATE,2),SO_MRP,(SO_CGST+SO_SGST+SO_IGST),roundOf(SO_QTY*SO_RATE*(1+(SO_CGST+SO_SGST+SO_IGST)*0.01),2) FROM SALES_ORDER INNER JOIN PRODUCT ON SO_P_ID=P_ID AND SO_INVOICE_DATE BETWEEN '"+new SimpleDateFormat("yyyy-MM-dd").format(jDateChooser14.getDate())+"' AND '"+new SimpleDateFormat("yyyy-MM-dd").format(jDateChooser15.getDate())+"'", jTable13);
           else
          getTableData("SELECT ROW_NUMBER() OVER() AS S_NO,SO_INVOICE_NO,formatDate(SO_INVOICE_DATE),P_NAME,SO_QTY,SO_QTY_FREE,roundOf(SO_RATE,2),SO_MRP,(SO_CGST+SO_SGST+SO_IGST),roundOf(SO_QTY*SO_RATE*(1+(SO_CGST+SO_SGST+SO_IGST)*0.01),2) FROM SALES_ORDER INNER JOIN PRODUCT ON SO_P_ID=P_ID INNER JOIN SALEPURCHASE_ACCOUNT ON SPA_INVOICE_NO=SO_INVOICE_NO AND SPA_SUBMIT_BY='"+search_by_user_name.getText()+"' AND SO_INVOICE_DATE BETWEEN '"+new SimpleDateFormat("yyyy-MM-dd").format(jDateChooser14.getDate())+"' AND '"+new SimpleDateFormat("yyyy-MM-dd").format(jDateChooser15.getDate())+"'", jTable13);
        }else
        getTableData("SELECT ROW_NUMBER() OVER() AS S_NO,SO_INVOICE_NO,formatDate(SO_INVOICE_DATE),P_NAME,SO_QTY,SO_QTY_FREE,roundOf(SO_RATE,2),SO_MRP,(SO_CGST+SO_SGST+SO_IGST),roundOf(SO_QTY*SO_RATE*(1+(SO_CGST+SO_SGST+SO_IGST)*0.01),2) FROM SALES_ORDER INNER JOIN PRODUCT ON SO_P_ID=P_ID", jTable13);
    }//GEN-LAST:event_jButton71ActionPerformed

    private void jButton36KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton36KeyReleased
        // TODO add your handling code here:
        if(evt.getKeyCode()==KeyEvent.VK_ENTER)
        reset_purchase_cart();
    }//GEN-LAST:event_jButton36KeyReleased

    private void jButton36ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton36ActionPerformed
        // TODO add your handling code here:
        reset_purchase_cart();
    }//GEN-LAST:event_jButton36ActionPerformed

    private void jButton27KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton27KeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==KeyEvent.VK_ENTER)
        reset_sales_cart();
    }//GEN-LAST:event_jButton27KeyPressed

    private void jButton27ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton27ActionPerformed
        // TODO add your handling code here:

        reset_sales_cart();
    }//GEN-LAST:event_jButton27ActionPerformed

    private void company_profile_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_company_profile_btnActionPerformed
        // TODO add your handling code here:
        hide_panel();
        company_profile.setVisible(true);
        reset_button_color();
        company_profile_btn.setBackground(Color.GRAY);
        //company_profile_reset();
        jLabel131.setText("no path");jLabel132.setText("no path");
        try{ ps=con.prepareStatement("SELECT COMPANY_NAME,COMPANY_SLOGAN,COMPANY_GST_NO,COMPANY_CONTACT_NO,COMPANY_MAIL,COMPANY_ADDRESS,COMPANY_LOGO,COMPANY_QR_CODE,COMPANY_BANK_NAME,COMPANY_BANK_ACC_NO,COMPANY_IFS_CODE,COMPANY_BILL_TITLE,COMPANY_INVOICE_FORMAT,COMPANY_TNC,COMPANY_END_DATE,COMPANY_STATE_NAME FROM COMPANY_PROFILE");
            rs=ps.executeQuery();
            if(rs.next()){
                jTextField25.setText(rs.getString(1));
                jTextField26.setText(rs.getString(2));
                jTextField27.setText(rs.getString(3));
                jTextField28.setText(rs.getString(4));
                jTextField29.setText(rs.getString(5));
                jTextArea5.setText(rs.getString(6));
                jLabel129.setIcon(new ImageIcon(rs.getBytes(7)));
                // jLabel130.setIcon(new ImageIcon(rs.getBytes(8)));
                jTextField30.setText(rs.getString(9));
                jTextField31.setText(rs.getString(10));
                jTextField32.setText(rs.getString(11));
                jTextField33.setText(rs.getString(12));
                jTextField34.setText(rs.getString(13).substring(0,rs.getString(13).lastIndexOf("/")+1));
                jTextField15.setText(rs.getString(13).substring(rs.getString(13).lastIndexOf("/")+1));
                jTextArea6.setText(rs.getString(14));
                jDateChooser2.setDate(rs.getDate(15));
                jComboBox5.setSelectedItem(rs.getString(16));
            }
            rs.close();
        }catch(Exception e){JOptionPane.showMessageDialog(null, e);}
    }//GEN-LAST:event_company_profile_btnActionPerformed

    private void jButton72KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton72KeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==KeyEvent.VK_ENTER)
            invoice_statement();
    }//GEN-LAST:event_jButton72KeyPressed

    private void jButton73KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton73KeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==KeyEvent.VK_ENTER)
            party_due_balance_sheet();
    }//GEN-LAST:event_jButton73KeyPressed

    private void jButton62KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton62KeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==KeyEvent.VK_ENTER)
            party_insert();
    }//GEN-LAST:event_jButton62KeyPressed

    private void jTextField38KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField38KeyTyped
        // TODO add your handling code here:
        consumeAlpha(evt);
    }//GEN-LAST:event_jTextField38KeyTyped

    private void jTextField39KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField39KeyTyped
        // TODO add your handling code here:
        consumeAlpha(evt);
    }//GEN-LAST:event_jTextField39KeyTyped

    private void purchase_insurance_chargeKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_purchase_insurance_chargeKeyTyped
        // TODO add your handling code here:
        consumeAlpha(evt);
    }//GEN-LAST:event_purchase_insurance_chargeKeyTyped

    private void pur_expence_amount_txtKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_pur_expence_amount_txtKeyTyped
        // TODO add your handling code here:
         consumeAlpha(evt);
    }//GEN-LAST:event_pur_expence_amount_txtKeyTyped

    private void jTextField35KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField35KeyTyped
        // TODO add your handling code here:
        consumeAlpha(evt);
    }//GEN-LAST:event_jTextField35KeyTyped

    private void jTextField36KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField36KeyTyped
        // TODO add your handling code here:
        consumeAlpha(evt);
    }//GEN-LAST:event_jTextField36KeyTyped

    private void jTextField56KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField56KeyTyped
        // TODO add your handling code here:
//        consumeAlpha(evt);
    }//GEN-LAST:event_jTextField56KeyTyped

    private void jTextField64KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField64KeyTyped
        // TODO add your handling code here:
        consumeAlpha(evt);
    }//GEN-LAST:event_jTextField64KeyTyped

    private void jTextField65KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField65KeyTyped
        // TODO add your handling code here:
        consumeAlpha(evt);
    }//GEN-LAST:event_jTextField65KeyTyped

    private void jButton49KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton49KeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==KeyEvent.VK_ENTER)
        reset_maufacturer_customer();
    }//GEN-LAST:event_jButton49KeyPressed

    private void jButton49ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton49ActionPerformed
        // TODO add your handling code here:
        reset_maufacturer_customer();
    }//GEN-LAST:event_jButton49ActionPerformed

    private void jButton60ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton60ActionPerformed
        // TODO add your handling code here:
        if(jDateChooser6.getDate()!=null && jDateChooser9.getDate()!=null){
            createFolder("GSTReportBySales");
            String fileName=path+"GSTReportBySales\\GST_REPORT_"+new SimpleDateFormat("dd-MM-yyyy").format(new Date())+".xls";
            new CreateExcelFile(con).createFile("SELECT SO_INVOICE_NO AS INVOICE_NO,FORMATDATE(SO_INVOICE_DATE) AS INVOICE_DATE,MC_GST_NO AS GST_NO,MC_NAME AS LEETY_NAME,SO_HSN_CODE,SUM(SO_QTY) AS QTY,AVG(SO_RATE) AS RATE,SUM(SO_QTY*SO_RATE) AS AMOUNT,AVG(SO_CGST) AS CGST,SUM(SO_QTY*SO_RATE*SO_CGST*0.01) AS CGST_AMOUNT,AVG(SO_SGST) AS SGST, SUM(SO_QTY*SO_RATE*SO_SGST*0.01) AS SGST_AMOUNT,AVG(SO_IGST) AS IGST,SUM(SO_QTY*SO_RATE*SO_IGST*0.01) AS IGST_AMOUNT,AVG(SO_CGST+SO_SGST+SO_IGST) AS GST,SUM(SO_QTY*SO_RATE*(SO_CGST+SO_SGST+SO_IGST)*0.01) AS GST_AMOUNT,SUM(SO_QTY*SO_RATE*(1+(SO_CGST+SO_SGST+SO_IGST)*0.01)) AS GRAND_TOTAL FROM SALES_ORDER INNER JOIN MANUFACTURER_CUSTOMER ON SO_MC_ID=MC_ID AND SO_INVOICE_DATE BETWEEN '"+new SimpleDateFormat("yyyy-MM-dd").format(jDateChooser6.getDate())+"' AND '"+new SimpleDateFormat("yyyy-MM-dd").format(jDateChooser9.getDate())+"' GROUP BY (SO_CGST+SO_SGST+SO_IGST),SO_HSN_CODE,SO_INVOICE_NO,SO_INVOICE_DATE,MC_GST_NO,MC_NAME ", "", fileName, "GST_REPORT");
            new CreateExcelFile(con).updateExcel(fileName,"SELECT 'TOTAL','','','','',SUM(A.TOTAL_QUANTITY),'',SUM(A.TOTAL_AMOUNT),'',SUM(A.CGST_AMOUNT),'',SUM(A.SGST_AMOUNT),'',SUM(A.IGST_AMOUNT),'',SUM(A.GST_AMOUNT),SUM(A.AMOUNT) FROM (SELECT 'TOTAL ','','','','',SUM(SO_QTY) AS TOTAL_QUANTITY,'',SUM(SO_QTY*SO_RATE) AS TOTAL_AMOUNT,'',SUM(SO_QTY*SO_RATE*SO_CGST*0.01) AS CGST_AMOUNT,'',SUM(SO_QTY*SO_RATE*SO_SGST*0.01) AS SGST_AMOUNT,'',SUM(SO_QTY*SO_RATE*SO_IGST*0.01) AS IGST_AMOUNT,'',SUM(SO_QTY*SO_RATE*(SO_CGST+SO_SGST+SO_IGST)*0.01) AS GST_AMOUNT,SUM(SO_QTY*SO_RATE*(1+(SO_CGST+SO_SGST+SO_IGST)*0.01)) AS AMOUNT FROM SALES_ORDER INNER JOIN MANUFACTURER_CUSTOMER ON SO_MC_ID=MC_ID AND SO_INVOICE_DATE BETWEEN '"+new SimpleDateFormat("yyyy-MM-dd").format(jDateChooser6.getDate())+"' AND '"+new SimpleDateFormat("yyyy-MM-dd").format(jDateChooser9.getDate())+"' GROUP BY (SO_CGST+SO_SGST+SO_IGST),SO_HSN_CODE,SO_INVOICE_NO,SO_INVOICE_DATE,MC_GST_NO,MC_NAME )  AS A", "GST_REPORT");
        }
    }//GEN-LAST:event_jButton60ActionPerformed

    private void jButton64KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton64KeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==KeyEvent.VK_ENTER)
        party_remove();
    }//GEN-LAST:event_jButton64KeyPressed

    private void jButton64ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton64ActionPerformed
        // TODO add your handling code here:
        party_remove();
    }//GEN-LAST:event_jButton64ActionPerformed

    private void jTable3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable3MouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_jTable3MouseClicked

    private void jButton30ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton30ActionPerformed
        // TODO add your handling code here:
        if(jTable3.getSelectedRow()!=-1)
        try{con.setAutoCommit(false);
            ps=con.prepareStatement("DELETE FROM SALES_ORDER WHERE SO_INVOICE_NO='"+jTable3.getModel().getValueAt(jTable3.getSelectedRow(), 0).toString()+"'");
            ps.executeUpdate();
            ps=con.prepareStatement("DELETE FROM SALEPURCHASE_ACCOUNT WHERE SPA_INVOICE_NO='"+jTable3.getModel().getValueAt(jTable3.getSelectedRow(), 0).toString()+"'");
            ps.executeUpdate();
            ps=con.prepareStatement("DELETE FROM SO_SPA_JOINED WHERE SSJ_INVOICE_NO='"+jTable3.getModel().getValueAt(jTable3.getSelectedRow(), 0).toString()+"'");
            ps.executeUpdate();
            con.commit();
            getTableData("select SO_INVOICE_NO,MC_NAME,sum(SO_QTY),roundOf(sum(SO_QTY*SO_RATE*(1+(SO_CGST+SO_SGST+SO_IGST)*0.01)),2) from SALES_ORDER INNER JOIN MANUFACTURER_CUSTOMER ON SO_MC_ID=MC_ID group by SO_INVOICE_NO,MC_NAME", jTable3);
        }catch(Exception e){e.printStackTrace();}
    }//GEN-LAST:event_jButton30ActionPerformed

  
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton add_company_btn;
    private javax.swing.JPanel company_profile;
    protected javax.swing.JButton company_profile_btn;
    private javax.swing.JButton exit_btn;
    private javax.swing.JButton hsn_sac_btn;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton24;
    private javax.swing.JButton jButton25;
    private javax.swing.JButton jButton26;
    private javax.swing.JButton jButton27;
    private javax.swing.JButton jButton28;
    private javax.swing.JButton jButton29;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton30;
    private javax.swing.JButton jButton31;
    private javax.swing.JButton jButton33;
    private javax.swing.JButton jButton34;
    private javax.swing.JButton jButton35;
    private javax.swing.JButton jButton36;
    private javax.swing.JButton jButton37;
    private javax.swing.JButton jButton38;
    private javax.swing.JButton jButton39;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton40;
    private javax.swing.JButton jButton41;
    private javax.swing.JButton jButton42;
    private javax.swing.JButton jButton43;
    private javax.swing.JButton jButton45;
    private javax.swing.JButton jButton46;
    private javax.swing.JButton jButton47;
    private javax.swing.JButton jButton48;
    private javax.swing.JButton jButton49;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton50;
    private javax.swing.JButton jButton51;
    private javax.swing.JButton jButton54;
    private javax.swing.JButton jButton55;
    private javax.swing.JButton jButton56;
    private javax.swing.JButton jButton57;
    private javax.swing.JButton jButton58;
    private javax.swing.JButton jButton59;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton60;
    private javax.swing.JButton jButton61;
    private javax.swing.JButton jButton62;
    private javax.swing.JButton jButton63;
    private javax.swing.JButton jButton64;
    private javax.swing.JButton jButton65;
    private javax.swing.JButton jButton66;
    private javax.swing.JButton jButton67;
    private javax.swing.JButton jButton68;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton71;
    private javax.swing.JButton jButton72;
    private javax.swing.JButton jButton73;
    private javax.swing.JButton jButton75;
    private javax.swing.JButton jButton76;
    private javax.swing.JButton jButton77;
    private javax.swing.JButton jButton78;
    private javax.swing.JButton jButton8;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JCheckBox jCheckBox3;
    private javax.swing.JCheckBox jCheckBox4;
    private javax.swing.JCheckBox jCheckBox5;
    private javax.swing.JCheckBox jCheckBox6;
    private javax.swing.JCheckBox jCheckBox7;
    private javax.swing.JCheckBox jCheckBox8;
    private javax.swing.JCheckBox jCheckBox9;
    protected static javax.swing.JComboBox<String> jComboBox10;
    protected javax.swing.JComboBox jComboBox11;
    private javax.swing.JComboBox jComboBox12;
    private javax.swing.JComboBox jComboBox13;
    private javax.swing.JComboBox jComboBox14;
    private javax.swing.JComboBox jComboBox15;
    private javax.swing.JComboBox jComboBox17;
    private javax.swing.JComboBox jComboBox18;
    private javax.swing.JComboBox jComboBox19;
    private javax.swing.JComboBox jComboBox2;
    private javax.swing.JComboBox jComboBox20;
    private javax.swing.JComboBox jComboBox21;
    private javax.swing.JComboBox jComboBox5;
    protected javax.swing.JComboBox jComboBox6;
    private javax.swing.JComboBox jComboBox7;
    protected javax.swing.JComboBox jComboBox8;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private com.toedter.calendar.JDateChooser jDateChooser10;
    private com.toedter.calendar.JDateChooser jDateChooser11;
    private com.toedter.calendar.JDateChooser jDateChooser12;
    private com.toedter.calendar.JDateChooser jDateChooser13;
    private com.toedter.calendar.JDateChooser jDateChooser14;
    private com.toedter.calendar.JDateChooser jDateChooser15;
    private com.toedter.calendar.JDateChooser jDateChooser2;
    private com.toedter.calendar.JDateChooser jDateChooser3;
    private com.toedter.calendar.JDateChooser jDateChooser4;
    private com.toedter.calendar.JDateChooser jDateChooser6;
    protected static com.toedter.calendar.JDateChooser jDateChooser7;
    private com.toedter.calendar.JDateChooser jDateChooser8;
    private com.toedter.calendar.JDateChooser jDateChooser9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel100;
    private javax.swing.JLabel jLabel101;
    private javax.swing.JLabel jLabel102;
    private javax.swing.JLabel jLabel103;
    private javax.swing.JLabel jLabel104;
    private javax.swing.JLabel jLabel105;
    private javax.swing.JLabel jLabel106;
    private javax.swing.JLabel jLabel107;
    private javax.swing.JLabel jLabel108;
    private javax.swing.JLabel jLabel109;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel110;
    private javax.swing.JLabel jLabel111;
    private javax.swing.JLabel jLabel112;
    private javax.swing.JLabel jLabel113;
    private javax.swing.JLabel jLabel114;
    private javax.swing.JLabel jLabel115;
    private javax.swing.JLabel jLabel116;
    private javax.swing.JLabel jLabel117;
    private javax.swing.JLabel jLabel118;
    private javax.swing.JLabel jLabel119;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel120;
    private javax.swing.JLabel jLabel121;
    private javax.swing.JLabel jLabel122;
    private javax.swing.JLabel jLabel123;
    private javax.swing.JLabel jLabel124;
    private javax.swing.JLabel jLabel125;
    private javax.swing.JLabel jLabel126;
    private javax.swing.JLabel jLabel127;
    private javax.swing.JLabel jLabel128;
    private javax.swing.JLabel jLabel129;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel130;
    private javax.swing.JLabel jLabel131;
    private javax.swing.JLabel jLabel132;
    private javax.swing.JLabel jLabel133;
    private javax.swing.JLabel jLabel134;
    private javax.swing.JLabel jLabel135;
    private javax.swing.JLabel jLabel136;
    private javax.swing.JLabel jLabel137;
    private javax.swing.JLabel jLabel138;
    private javax.swing.JLabel jLabel139;
    private javax.swing.JLabel jLabel140;
    private javax.swing.JLabel jLabel141;
    private javax.swing.JLabel jLabel142;
    private javax.swing.JLabel jLabel143;
    private javax.swing.JLabel jLabel144;
    private javax.swing.JLabel jLabel145;
    private javax.swing.JLabel jLabel146;
    private javax.swing.JLabel jLabel147;
    private javax.swing.JLabel jLabel148;
    private javax.swing.JLabel jLabel149;
    private javax.swing.JLabel jLabel150;
    private javax.swing.JLabel jLabel151;
    private javax.swing.JLabel jLabel152;
    private javax.swing.JLabel jLabel153;
    private javax.swing.JLabel jLabel154;
    private javax.swing.JLabel jLabel155;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel72;
    private javax.swing.JLabel jLabel73;
    private javax.swing.JLabel jLabel74;
    private javax.swing.JLabel jLabel75;
    private javax.swing.JLabel jLabel76;
    private javax.swing.JLabel jLabel77;
    private javax.swing.JLabel jLabel78;
    private javax.swing.JLabel jLabel79;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel80;
    private javax.swing.JLabel jLabel81;
    private javax.swing.JLabel jLabel82;
    private javax.swing.JLabel jLabel83;
    private javax.swing.JLabel jLabel84;
    private javax.swing.JLabel jLabel85;
    private javax.swing.JLabel jLabel86;
    private javax.swing.JLabel jLabel87;
    private javax.swing.JLabel jLabel88;
    private javax.swing.JLabel jLabel89;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel90;
    private javax.swing.JLabel jLabel91;
    private javax.swing.JLabel jLabel92;
    private javax.swing.JLabel jLabel93;
    private javax.swing.JLabel jLabel94;
    private javax.swing.JLabel jLabel95;
    private javax.swing.JLabel jLabel96;
    private javax.swing.JLabel jLabel97;
    private javax.swing.JLabel jLabel98;
    private javax.swing.JLabel jLabel99;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private com.toedter.calendar.JMonthChooser jMonthChooser1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JPasswordField jPasswordField1;
    private javax.swing.JPasswordField jPasswordField2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JScrollPane jScrollPane14;
    private javax.swing.JScrollPane jScrollPane15;
    private javax.swing.JScrollPane jScrollPane16;
    private javax.swing.JScrollPane jScrollPane17;
    private javax.swing.JScrollPane jScrollPane18;
    private javax.swing.JScrollPane jScrollPane19;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane20;
    private javax.swing.JScrollPane jScrollPane21;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable10;
    private javax.swing.JTable jTable11;
    private javax.swing.JTable jTable12;
    private javax.swing.JTable jTable13;
    private javax.swing.JTable jTable14;
    private javax.swing.JTable jTable15;
    private javax.swing.JTable jTable16;
    private javax.swing.JTable jTable17;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JTable jTable4;
    private javax.swing.JTable jTable5;
    private javax.swing.JTable jTable6;
    private javax.swing.JTable jTable7;
    private javax.swing.JTable jTable8;
    private javax.swing.JTable jTable9;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JTextArea jTextArea5;
    private javax.swing.JTextArea jTextArea6;
    private javax.swing.JTextField jTextField10;
    private javax.swing.JTextField jTextField11;
    private javax.swing.JTextField jTextField12;
    private javax.swing.JTextField jTextField13;
    private javax.swing.JTextField jTextField14;
    private javax.swing.JTextField jTextField15;
    protected static javax.swing.JTextField jTextField17;
    private javax.swing.JTextField jTextField18;
    private javax.swing.JTextField jTextField19;
    private javax.swing.JTextField jTextField20;
    private javax.swing.JTextField jTextField21;
    private javax.swing.JTextField jTextField22;
    private javax.swing.JTextField jTextField23;
    private javax.swing.JTextField jTextField25;
    private javax.swing.JTextField jTextField26;
    private javax.swing.JTextField jTextField27;
    private javax.swing.JTextField jTextField28;
    private javax.swing.JTextField jTextField29;
    private javax.swing.JTextField jTextField30;
    private javax.swing.JTextField jTextField31;
    private javax.swing.JTextField jTextField32;
    private javax.swing.JTextField jTextField33;
    private javax.swing.JTextField jTextField34;
    private javax.swing.JTextField jTextField35;
    private javax.swing.JTextField jTextField36;
    private javax.swing.JTextField jTextField37;
    private javax.swing.JTextField jTextField38;
    private javax.swing.JTextField jTextField39;
    private javax.swing.JTextField jTextField40;
    private javax.swing.JTextField jTextField41;
    private javax.swing.JTextField jTextField42;
    private javax.swing.JTextField jTextField43;
    private javax.swing.JTextField jTextField44;
    private javax.swing.JTextField jTextField46;
    private javax.swing.JTextField jTextField47;
    private javax.swing.JTextField jTextField49;
    private javax.swing.JTextField jTextField51;
    private javax.swing.JTextField jTextField52;
    private javax.swing.JTextField jTextField53;
    private javax.swing.JTextField jTextField54;
    private javax.swing.JTextField jTextField55;
    private javax.swing.JTextField jTextField56;
    private javax.swing.JTextField jTextField57;
    private javax.swing.JTextField jTextField58;
    private javax.swing.JTextField jTextField59;
    private javax.swing.JTextField jTextField60;
    private javax.swing.JTextField jTextField61;
    private javax.swing.JTextField jTextField62;
    private javax.swing.JTextField jTextField63;
    private javax.swing.JTextField jTextField64;
    private javax.swing.JTextField jTextField65;
    private javax.swing.JTextField jTextField66;
    private javax.swing.JTextField jTextField67;
    private javax.swing.JTextField jTextField68;
    private javax.swing.JTextField jTextField69;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField70;
    private javax.swing.JTextField jTextField71;
    private javax.swing.JTextField jTextField72;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTextField jTextField9;
    private javax.swing.JButton logout_btn;
    private javax.swing.JButton manage_user_btn;
    private javax.swing.JPanel manufacturerOrCustomer;
    private javax.swing.JPanel party_management_panel;
    private javax.swing.JButton party_mng_btn;
    private javax.swing.JPanel product_details;
    private javax.swing.JButton productntype_btn;
    private javax.swing.JTextField puchase_search_invoice;
    private javax.swing.JTextField pur_expence_amount_txt;
    private javax.swing.JLabel pur_expence_name_lbl;
    private javax.swing.JTextField pur_expence_name_txt;
    private javax.swing.JLabel pur_insurance_name_lbl;
    private javax.swing.JButton purchase_btn;
    private javax.swing.JLabel purchase_grand_total;
    private javax.swing.JTextField purchase_hsn_code;
    private javax.swing.JTextField purchase_insurance_charge;
    private javax.swing.JLabel purchase_insurance_charge_lbl;
    private javax.swing.JTextField purchase_insurance_name;
    private javax.swing.JButton purchase_invoice_delete_btn;
    private javax.swing.JPanel purchase_order;
    private javax.swing.JPanel purchase_panel;
    private javax.swing.JButton report_btn;
    private javax.swing.JPanel reports_panel;
    private javax.swing.JButton required_btn;
    private javax.swing.JPanel required_panel;
    private javax.swing.JButton sale_btn;
    private javax.swing.JPanel sale_panel;
    private javax.swing.JPanel sale_to_customer;
    private javax.swing.JPanel sales_return;
    private javax.swing.JButton sales_rtn_btn;
    private javax.swing.JTextField search_by_user_name;
    private javax.swing.JLabel total_purchase_amount;
    private javax.swing.JTextField user_name;
    private javax.swing.JPanel user_panel;
    private javax.swing.JButton view_invoice_btn;
    // End of variables declaration//GEN-END:variables

private void createTable(String query,String table){
    try{ DatabaseMetaData dbmd = con.getMetaData();
         ResultSet rs = dbmd.getTables(null, "SYSTEM", table, null);
            if(!rs.next())
            {ps=con.prepareStatement(query);
            ps.executeUpdate();
            }
    }catch(Exception e){}
}
private void deleteTable(String query){
    try{ 
            ps=con.prepareStatement(query);
            ps.executeUpdate();
            
    }catch(Exception e){}
}


  void listTables(){
    try {
      DatabaseMetaData meta = con.getMetaData();
      ResultSet res = meta.getTables(null,null,null,new String[]{"TABLE"});
      System.out.println("List of tables: "); 
      while (res.next()) {
         System.out.println("   "+res.getString("TABLE_CAT") 
                          + ", "+res.getString("TABLE_SCHEM")
                     + ", "+res.getString("TABLE_NAME")
                     + ", "+res.getString("TABLE_TYPE")
                        + ", "+res.getString("REMARKS")); 
      }
     } catch (Exception e) {
      e.printStackTrace();
    }
}
  public void getTableData(String query,javax.swing.JTable table){
    DefaultTableModel dtm=(DefaultTableModel)table.getModel();
    for(int i=dtm.getRowCount()-1;i>=0;i--)
        dtm.removeRow(i);
    try{ ps=con.prepareStatement(query);
        rs=ps.executeQuery();
     ResultSetMetaData   rsmd=rs.getMetaData();
        LinkedList<String> s=new LinkedList<String>();
            while(rs.next()){
                for(int i=1;i<=rsmd.getColumnCount();i++){      
                s.add(rs.getString(i));}
                Object[] o=s.toArray();
                dtm.addRow(o);
                s.clear();
            }      
    }catch(SQLException e){
          JOptionPane.showMessageDialog(null, e);
    }
}

  void autoComplete(){
    
      AutoCompleteDecorator.decorate(jComboBox10);
      AutoCompleteDecorator.decorate(jComboBox13);
      AutoCompleteDecorator.decorate(jComboBox20);
      AutoCompleteDecorator.decorate(jComboBox18);
      AutoCompleteDecorator.decorate(jComboBox21);
      AutoCompleteDecorator.decorate(jComboBox11);
      AutoCompleteDecorator.decorate(jComboBox6);
      AutoCompleteDecorator.decorate(jComboBox8);
  }
  
 protected void getDataInList(String query,javax.swing.JList list){
  try{ DefaultListModel dlm=new DefaultListModel();
  list.setModel(dlm);
  dlm.removeAllElements();
        ps=con.prepareStatement(query);
        rs=ps.executeQuery();
        while(rs.next()){
            dlm.addElement(rs.getString(1));
        }
     }catch(Exception e){e.printStackTrace();
     }    
}
  
  
  protected void getDataInCombo(String query,javax.swing.JComboBox combo){
  try{ 
  while (combo.getItemCount() > 1) 
            combo.removeItemAt(1); 
        ps=con.prepareStatement(query);
        rs=ps.executeQuery();
        while(rs.next()){
            combo.addItem(rs.getString(1));
        }
     }catch(Exception e){e.printStackTrace();
     }    
}
  private String getId(){
   String id="",pattern="";
   try{ pattern="LEE/"+Calendar.getInstance().get(Calendar.YEAR)%100+"-"+(Calendar.getInstance().get(Calendar.YEAR)%100+1)+"/";
       

        ps=con.prepareStatement("SELECT MAX(CAST(SUBSTR(SUBSTR(SPA_INVOICE_NO,LOCATE('/',SPA_INVOICE_NO)+1),LOCATE('/',SUBSTR(SPA_INVOICE_NO,LOCATE('/',SPA_INVOICE_NO)+1))+1) AS BIGINT)) FROM SALEPURCHASE_ACCOUNT WHERE LOCATE('/',SPA_INVOICE_NO)!=0 AND LOCATE(CAST('/'||CAST(MOD(YEAR(CURRENT_DATE),100) AS CHAR(3)) AS CHAR(3)),SPA_INVOICE_NO)!=0 ");
        rs=ps.executeQuery();
        if(rs.next()){
        if(rs.getInt(1)!=0){ 
            id=id.substring(0, id.lastIndexOf("/")+1);
            if(rs.getInt(1)<10)
                id+="000"+(rs.getInt(1)+1);
            else if(rs.getInt(1)<100)
                id+="00"+(rs.getInt(1)+1);
            else if(rs.getInt(1)<1000)
                id+="0"+(rs.getInt(1)+1);
        }}
       if(id.isEmpty())id="0001"; 
   }catch(Exception e){
   JOptionPane.showMessageDialog(null, e,"Error",0);}
      return pattern+id; 
  }
  private String generateId(){
      String id="0001";
      try{ ps=con.prepareStatement("SELECT MAX(CAST(SPA_INVOICE_NO AS INTEGER)) FROM INVOICE where INVOICE_NO IS NOT NULL");
           rs=ps.executeQuery();
           if(rs.next()){
               if(rs.getInt(1)!=0)
                   if(rs.getInt(1)<10)id="000"+(rs.getInt(1)+1);
                   else if(rs.getInt(1)<100)id="00"+(rs.getInt(1)+1);
                   else if(rs.getInt(1)<1000)id="0"+(rs.getInt(1)+1);
           }
          
      }catch(Exception e){e.printStackTrace();}
      return id;
  }
  
  
   
    
    private void company_profile_reset(){
        jTextField25.setText("");
        jTextField26.setText("");
        jTextField27.setText("");
        jTextField28.setText("");
        jTextField29.setText("");
        jTextArea5.setText("");
        jTextField30.setText("");
        jTextField31.setText("");
        jTextField32.setText("");
        jTextField33.setText("");
        jTextField34.setText("");
        jTextField15.setText("");
        jTextArea6.setText("");
        jLabel129.setIcon(null);
        jLabel130.setIcon(null);
        jLabel131.setVisible(false);
        jLabel132.setVisible(false);
        jLabel131.setText("");
        jLabel132.setText("");
        jDateChooser2.setDate(new Date());
        //COMPANY_NAME,COMPANY_SLOGAN,COMPANY_GST_NO,COMPANY_CONTACT_NO,COMPANY_MAIL,COMPANY_ADDRESS,
        //COMPANY_LOGO,COMPANY_QR_CODE,COMPANY_BANK_NAME,COMPANY_BANK_ACC_NO,COMPANY_IFS_CODE,COMPANY_BILL_TITLE,COMPANY_INVOICE_FORMAT,COMPANY_TNC,COMPANY_VALIDITY_DATE,COMPANY_STATE_NAME
       
    }
    
    void reset_invoice(){
        
        
         //jComboBox6.setSelectedItem(STATE_NAME); 
      
       //  getTableData("SELECT INVOICE_NO,INVOICE_DATE,INV_COM_NAME,SUM(QUANTITY) FROM INVOICE GROUP BY INVOICE_NO,INVOICE_DATE,INV_COM_NAME", jTable5);
    }
    void hide_panel(){
       jPanel5.setVisible(false);
       jPanel6.setVisible(false);
       company_profile.setVisible(false);
      
       jPanel8.setVisible(false);
       sale_to_customer.setVisible(false);
       purchase_order.setVisible(false);
       product_details.setVisible(false);
       manufacturerOrCustomer.setVisible(false);
       sales_return.setVisible(false);
       sale_panel.setVisible(false);
       party_management_panel.setVisible(false);
       reports_panel.setVisible(false);
       purchase_panel.setVisible(false);
      // jButton22.setVisible(false);
       user_panel.setVisible(false);
       required_panel.setVisible(false);
       VISIBLITY=0;
      //  jPanel2.setVisible(false);
     
       
       
    }
    
    public boolean isInteger(String num){
        try{ Integer.parseInt(num);
            
        }catch(Exception e){
        return false;
        }
        
        return true;
    }
    
    private void updateUser(){
        try{ boolean login_status=false;
             ps=con.prepareStatement("SELECT USER_STATUS FROM LOGIN_TABLE WHERE USER_NAME='mujeeb'");
             rs=ps.executeQuery();
             if(rs.next()){
             login_status=rs.getBoolean(1);}
             company_profile_btn.setVisible(false);jMenuItem4.setVisible(false);
            if(login_status){company_profile_btn.setVisible(true);jMenuItem4.setVisible(true);
            ps=con.prepareStatement("UPDATE LOGIN_TABLE SET USER_STATUS=false WHERE USER_NAME='mujeeb'");
            ps.executeUpdate();
            }
        }catch(Exception e){JOptionPane.showMessageDialog(null, e);}
    }
    private void reset_sales_cart(){
        jButton24.setVisible(false);
        jButton25.setVisible(false);
        jTextField17.requestFocusInWindow();
        jTextField42.setText("0");
        jComboBox20.setSelectedIndex(0);
        jLabel100.setText("0");
        jTextField22.setText("0");
        jTextField35.setText("");
        jTextField36.setText("0");
        jTextField67.setText("");
        jTextField68.setText("0");
//        jLabel99.setText("0");
        jLabel147.setText("");
        jLabel149.setText("0");
        exp_date=null;
        getDataInCombo("SELECT DISTINCT P_CODE FROM PRODUCT", jComboBox21);
        getDataInCombo("SELECT DISTINCT P_NAME FROM PRODUCT", jComboBox8);
        getTableData("SELECT SC_ID,P_NAME,SC_BATCH_ID,monthYear(SC_EXP_DATE),SC_MRP,roundOf(SC_RATE,2),SC_QTY,SC_QTY_FREE,(SC_CGST+SC_SGST+SC_IGST),roundOf(SC_RATE*SC_QTY*(1+(SC_CGST+SC_SGST+SC_IGST)*0.01),2) FROM SALES_CART INNER JOIN PRODUCT ON SC_P_ID=P_ID", jTable6);
     }
    private void reset_sales_order(){
       
         jComboBox6.setSelectedIndex(0);
         jTextField17.setText(getId());
         jTextField42.setText("0");
         jLabel56.setText("0");
         jDateChooser7.setDate(new Date());
         getDataInCombo("SELECT DISTINCT MC_NAME FROM MANUFACTURER_CUSTOMER WHERE MC_ID NOT IN(SELECT DISTINCT PO_MC_ID FROM PURCHASE_ORDER)", jComboBox6);
          jLabel53.setVisible(false);
          jLabel3.setVisible(false);
          jLabel56.setVisible(false);
          jTextField42.setVisible(false);
          jTextField68.setVisible(false);
          jButton28.setVisible(false);
          jButton51.setVisible(false);
          jLabel46.setVisible(false);
          jTextField42.setVisible(false);
          user_name.setVisible(false);
          jLabel98.setVisible(false);
          jLabel99.setVisible(false);
           jLabel104.setVisible(false);
           jLabel105.setVisible(false);
           jLabel106.setVisible(false);
           jLabel107.setVisible(false);
           jComboBox7.setVisible(false);
           P_ID=0;
          try{
          ps=con.prepareStatement("TRUNCATE TABLE SALES_CART");
          ps.execute();
           ps=con.prepareStatement("ALTER TABLE SALES_CART ALTER COLUMN SC_ID RESTART WITH 1");
           ps.execute();
             reset_sales_cart();
          }catch(Exception e){JOptionPane.showMessageDialog(null, e);}
    }
    public void report(String query,String path) {
        try { //  System.getProperty("user.dir") + File.separator +path
            InputStream in=getClass().getResourceAsStream(path);
            JasperDesign jd = JRXmlLoader.load(in);
            JRDesignQuery new_query = new JRDesignQuery();
            new_query.setText(query);
            jd.setQuery(new_query);
            JasperReport jr = JasperCompileManager.compileReport(jd);
            JasperPrint jp = JasperFillManager.fillReport(jr, null, con);
            JasperViewer jv = new JasperViewer(jp, false);
            jv.setVisible(true);
//            JasperExportManager.exportReportToPdfFile(jp,"sample_report.pdf");
//            if(new File("sample_report.pdf").exists())
//                Desktop.getDesktop().open(new File("sample_report.pdf"));
        } catch(JRException ex) {
          //  Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
     JOptionPane.showMessageDialog(null, ex);
        }

    }
        public void report1(String query,String path) {
        try { //  System.getProperty("user.dir") + File.separator +path
            HashMap parameters=new HashMap();
            parameters.put("in_no", jTable3.getModel().getValueAt(jTable3.getSelectedRow(), 0).toString());
            JasperPrint jp = JasperFillManager.fillReport(getClass().getResourceAsStream(path), parameters, con);
            JasperViewer jv = new JasperViewer(jp, false);
            jv.setVisible(true);
//            JasperExportManager.exportReportToPdfFile(jp,"sample_report.pdf");
//            if(new File("sample_report.pdf").exists())
//                Desktop.getDesktop().open(new File("sample_report.pdf"));
        } catch(JRException ex) {
          //  Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
     JOptionPane.showMessageDialog(null, ex);
        }

    }
    
public void focusLost1(javax.swing.JComboBox combo,javax.swing.JComponent component){
     JTextField editorCompoent = (JTextField)combo.getEditor().getEditorComponent();
         // JTextField editorCompoent2 = (JTextField)jComboBox2.getEditor().getEditorComponent();
         editorCompoent.addFocusListener(new FocusListener() {

         @Override
         public void focusGained(FocusEvent e) {
             String name=combo.getName();
             System.out.println(name);   
         }

         @Override
         public void focusLost(FocusEvent e) {
            component.requestFocusInWindow();
         }
     });
}
public void focusLost10(){
     JTextField editorCompoent = (JTextField)jComboBox10.getEditor().getEditorComponent();
         // JTextField editorCompoent2 = (JTextField)jComboBox2.getEditor().getEditorComponent();
         editorCompoent.addFocusListener(new FocusAdapter() {
             public void focusGained(FocusEvent e){
             
             }
             @Override
             public void focusLost(FocusEvent e){
         try{if(jComboBox10.getSelectedIndex()!=0){
            MC_NAME=jComboBox10.getSelectedItem().toString();
             ps=con.prepareStatement("SELECT MC_ID,MC_STATE_NC_ID FROM MANUFACTURER_CUSTOMER WHERE MC_NAME='"+jComboBox10.getSelectedItem().toString()+"'");
            rs=ps.executeQuery();
            if(!rs.next() ){    
              new Manufactuer_Company(new JFrame(), true, con).setVisible(true);
            }}
            ps=con.prepareStatement("SELECT MC_ID,MC_STATE_NC_ID FROM MANUFACTURER_CUSTOMER WHERE MC_NAME='"+jComboBox10.getSelectedItem().toString()+"'");
            rs=ps.executeQuery();
            if(rs.next()){
                MC_ID=rs.getInt(1);
                STATE_ID=rs.getInt(2);
            }
             System.out.println("MC ID ="+MC_ID);
         
        }catch(Exception ee){ee.printStackTrace();}
             }
             
});
         
}
public void focusLost6(){
     JTextField editorCompoent = (JTextField)jComboBox6.getEditor().getEditorComponent();
         // JTextField editorCompoent2 = (JTextField)jComboBox2.getEditor().getEditorComponent();
         editorCompoent.addFocusListener(new FocusAdapter() {
         
             public void focusLost(FocusEvent e){
                   System.out.println(jComboBox6.getSelectedItem().toString()+"\t");
                       System.out.println("index="+jComboBox6.getSelectedIndex());
           
     
         try{if(jComboBox6.getSelectedIndex()!=0){
            MC_NAME=jComboBox6.getSelectedItem().toString();
             ps=con.prepareStatement("SELECT MC_ID,MC_STATE_NC_ID FROM MANUFACTURER_CUSTOMER WHERE MC_NAME='"+jComboBox6.getSelectedItem().toString()+"'");
            rs=ps.executeQuery();
            if(!rs.next() ){    
              new Manufactuer_Company(new JFrame(), true, con).setVisible(true);
             
            }
            ps=con.prepareStatement("SELECT MC_ID,MC_STATE_NC_ID,STATE_NAME FROM MANUFACTURER_CUSTOMER INNER JOIN STATE_NC ON MC_STATE_NC_ID=STATE_NC_ID WHERE MC_NAME='"+jComboBox6.getSelectedItem().toString()+"'");
            rs=ps.executeQuery();
            if(rs.next()){
                MC_ID=rs.getInt(1);
                STATE_ID=rs.getInt(2);
                STATE_NAME=rs.getString(3);
                
            }
         }
        }catch(Exception ee){ee.printStackTrace();}
             }
             
});
         
}
public void focusLost11(){
     JTextField editorCompoent = (JTextField)jComboBox11.getEditor().getEditorComponent();
         // JTextField editorCompoent2 = (JTextField)jComboBox2.getEditor().getEditorComponent();
         editorCompoent.addFocusListener(new FocusAdapter() {
             public void focusLost(FocusEvent e){
                   System.out.println(jComboBox11.getSelectedItem().toString()+"\t");
                       System.out.println("index="+jComboBox11.getSelectedIndex());
           
     
         try{if(jComboBox11.getSelectedIndex()!=0){
             P_NAME=jComboBox11.getSelectedItem().toString();
             ps=con.prepareStatement("SELECT P_ID FROM PRODUCT WHERE P_NAME='"+jComboBox11.getSelectedItem().toString()+"'");
            rs=ps.executeQuery();
            if(!rs.next() ){    
              new Product(new JFrame(), true, con).setVisible(true);
            }}
         ps=con.prepareStatement("SELECT P_ID FROM PRODUCT WHERE P_NAME='"+jComboBox11.getSelectedItem().toString()+"'");
            rs=ps.executeQuery();
            if(rs.next())
                P_ID=rs.getInt(1);
//            jDateChooser1.requestFocusInWindow();
        }catch(Exception ee){ee.printStackTrace();}
             }
             
});
         
}
public void focusLost8(){
     JTextField editorCompoent = (JTextField)jComboBox8.getEditor().getEditorComponent();
         // JTextField editorCompoent2 = (JTextField)jComboBox2.getEditor().getEditorComponent();
         editorCompoent.addFocusListener(new FocusAdapter() {
             public void focusLost(FocusEvent e){
                   System.out.println(jComboBox8.getSelectedItem().toString()+"\t");
                       System.out.println("index="+jComboBox8.getSelectedIndex());
           
     
         try{if(jComboBox8.getSelectedIndex()!=0){
              P_NAME=jComboBox8.getSelectedItem().toString();
             ps=con.prepareStatement("SELECT P_ID FROM PRODUCT WHERE P_NAME='"+jComboBox8.getSelectedItem().toString()+"'");
            rs=ps.executeQuery();
            if(!rs.next() ){    
              new Product(new JFrame(), true, con).setVisible(true);
            }
            ps=con.prepareStatement("SELECT P_ID FROM PRODUCT WHERE P_NAME='"+jComboBox8.getSelectedItem().toString()+"'");
            rs=ps.executeQuery();
            if(rs.next())
                P_ID=rs.getInt(1);
           
         }
        }catch(Exception ee){ee.printStackTrace();}
             }
             
});
         
}
public void focusLost13(){
     JTextField editorCompoent = (JTextField)jComboBox13.getEditor().getEditorComponent();
         // JTextField editorCompoent2 = (JTextField)jComboBox2.getEditor().getEditorComponent();
         editorCompoent.addFocusListener(new FocusAdapter() {
             public void focusLost(FocusEvent e){
                 jTextField63.requestFocusInWindow();
             }
             
});
         
}
protected void reset_product(){
    jButton39.setVisible(false);
    jButton40.setVisible(false);
    jTextField51.setText("");
    jTextField52.setText("");
    jTextField53.setText("");
    jTextField54.setText("");
    getTableData("SELECT P_ID,P_NAME,P_CODE,PT_NAME FROM PRODUCT INNER JOIN PRODUCT_TYPE ON P_PT_ID=PT_ID ORDER BY P_ID", jTable8);
}
protected  void reset_maufacturer_customer(){
    jButton42.setVisible(false);
    jButton43.setVisible(false);
    jTextField44.setText("");
    jTextField55.setText("");
    jTextField56.setText("");
    jTextField57.setText("");
    jTextArea1.setText(null);
    jTextField58.setText("");
    jTextField59.setText("");
    jTextField60.setText("");
    jTextField61.setText("");
    jTextField62.setText("");
    jTextField21.setText("");
    jTextField18.setText("");
    jTextField7.setText(null);
        jTextField8.setText(null);
        jTextField9.setText(null);
        jTextField10.setText(null);
        jTextField11.setText(null);
        jTextField12.setText(null);
        jTextField13.setText(null);  
        jTextField13.setText("");  
        jTextArea2.setText(null);
    getTableData("SELECT MC_ID, MC_NAME, MC_CONTACT_NO, MC_GST_NO FROM MANUFACTURER_CUSTOMER ORDER BY MC_ID", jTable9);
}
private void reset_purchase_cart(){
   jButton34.setVisible(false);
    jButton35.setVisible(false);
    jComboBox11.setSelectedIndex(0);
    jTextField37.setText("");
    jTextField38.setText("");
    jTextField39.setText("0");
    jTextField40.setText("");
    jTextField41.setText("");
    jMonthChooser1.setMonth(0);
    jComboBox12.setSelectedIndex(0);
    jTextField43.setText("");
    jTextField46.setText("");
    jTextField47.setText("0");
    purchase_hsn_code.setText("");
    getDataInCombo("SELECT P_NAME FROM PRODUCT", jComboBox11);
    getTableData("SELECT PC_ID,P_NAME,PC_BATCH_ID,PC_BOX_QTY,roundOf(PC_BOX_RATE,2),PC_BOX_MRP,PC_FREE_BOX_QTY,monthYear(PC_EXP_DATE),PC_TAX,roundOf(PC_BOX_QTY*PC_BOX_RATE,2),roundOf(PC_BOX_QTY*PC_BOX_RATE*(1+PC_TAX*0.01),2) FROM PURCHASE_CART INNER JOIN PRODUCT ON PC_P_ID=P_ID", jTable7);
    
}
private void reset_purchase_order(){
    
    try{    jTextField19.setText("");
            jButton37.setVisible(false);
            jLabel65.setVisible(false);
            jLabel66.setVisible(false);
            jComboBox10.setSelectedIndex(0);
            total_purchase_amount.setVisible(false);
            purchase_grand_total.setVisible(false);
            pur_expence_name_lbl.setVisible(false);
            jTextField49.setVisible(false);
            pur_expence_name_txt.setVisible(false);
            jLabel4.setVisible(false);
            pur_expence_amount_txt.setVisible(false);
            pur_expence_name_txt.setVisible(false);
            purchase_insurance_name.setVisible(false);
            pur_insurance_name_lbl.setVisible(false);
            purchase_insurance_charge.setVisible(false);
            purchase_insurance_charge_lbl.setVisible(false);
            jDateChooser1.setDate(new Date());
    getDataInCombo("SELECT DISTINCT MC_NAME FROM MANUFACTURER_CUSTOMER WHERE MC_ID NOT IN(SELECT DISTINCT SO_MC_ID FROM SALES_ORDER)", jComboBox10);
    ps=con.prepareStatement("TRUNCATE TABLE PURCHASE_CART");
    ps.execute();
    ps=con.prepareStatement("ALTER TABLE PURCHASE_CART ALTER COLUMN PC_ID RESTART WITH 1");
    ps.execute();
    reset_purchase_cart();
   
    }catch(Exception e){e.printStackTrace();}
}
public void addListToJTextField(String query,javax.swing.JPanel panel,javax.swing.JTextField field,javax.swing.JList list,java.awt.event.KeyEvent evt){
        DefaultListModel dlm=new DefaultListModel();
        list.setModel(dlm);
        list.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY,2));
        try{ ps=con.prepareStatement(query);
             rs=ps.executeQuery();
             int count=0;
        while(rs.next()){//
            System.out.println(rs.getString(1));
        dlm.addElement(rs.getString(1));
        count++;}
         int x=field.getX();
        int y=field.getY();
        if(!isComponentExists(panel, list)){
        list.setBounds(x+1, y+field.getHeight()-2, field.getWidth()-2,25*count);
        panel.add(list);}System.out.println("after");//System.out.println(dlm.getSize());
        list.setSize(field.getWidth()-2, 20*count+2);
        if(count>0)list.setVisible(true);
        else list.setVisible(false);  
    }catch(Exception e){
        e.printStackTrace();}
        
       listListener(panel,list, field, evt);
        
       //  System.out.println(""+field);
}
public void listListener(javax.swing.JPanel panel,javax.swing.JList list,javax.swing.JTextField field,java.awt.event.KeyEvent evt){
    list.addListSelectionListener((ListSelectionEvent e) -> {
        
      //  if(!list.isVisible())addListToJTextField(panel,field, list);
      //  if(list.getSelectedIndex()!=-1)
        field.setText(list.getSelectedValue().toString());
        
    });
    list.addMouseListener(new MouseListener() {

        @Override
        public void mouseClicked(MouseEvent e) {
            DefaultListModel dlm=(DefaultListModel)list.getModel();
            dlm.removeAllElements();
           list.setVisible(false);
            System.out.println("mouse click");
        }

        @Override
        public void mousePressed(MouseEvent e) {
            
           System.out.println("mouse pressed");
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            System.out.println("mouse released");
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            System.out.println("mouse entered");
        }

        @Override
        public void mouseExited(MouseEvent e) {
            System.out.println("mouse exited");
        }
    });
    list.addFocusListener(new FocusListener() {

        @Override
        public void focusGained(FocusEvent e) {
            
        }

        @Override
        public void focusLost(FocusEvent e) {
           
        }
    });
    list.addKeyListener(new KeyListener() {

        @Override
        public void keyTyped(java.awt.event.KeyEvent e) {
           
        }

        @Override
        public void keyPressed(java.awt.event.KeyEvent e) {
            
        }

        @Override
        public void keyReleased(java.awt.event.KeyEvent e) {
           if(e.getKeyCode()==java.awt.event.KeyEvent.VK_ENTER){
            DefaultListModel dlm=(DefaultListModel)list.getModel();
            dlm.removeAllElements();
            list.setVisible(false);
           }
        }
    });
         if(evt.getKeyCode()==java.awt.event.KeyEvent.VK_DOWN){
                list.requestFocusInWindow();
                list.setSelectedIndex(0);
            }
         if(evt.getKeyCode()==java.awt.event.KeyEvent.VK_UP){
                list.requestFocusInWindow();
                list.setSelectedIndex(list.getLastVisibleIndex());
            }
}
public boolean isComponentExists(javax.swing.JPanel panel,Component c){  
    return java.util.Arrays.asList(panel.getComponents()).contains(c);
}
protected void reset_party(){
    jLabel114.setText("");
    jLabel119.setText("");
    jLabel134.setText("");
    jTextField69.setText("");
    jTextField70.setText("");
    jTextField71.setText("");
     jComboBox18.setSelectedIndex(0);
    jComboBox19.setSelectedIndex(0);
  getDataInCombo("SELECT DISTINCT MC_NAME FROM MANUFACTURER_CUSTOMER WHERE MC_ID IN(SELECT DISTINCT SO_MC_ID FROM SALES_ORDER)", jComboBox18);  
  // SPA_PREV_INVOICE_AMOUNT
  try{
      getTableData("SELECT SPA_ID,SPA_INVOICE_NO,formatDate(SPA_INVOICE_DATE),roundOf(doublenotnull(SPA_SALE_PURCHASE_AMOUNT),2), roundOf(doublenotnull(SPA_PAID_AMOUNT),2), roundOf(doublenotnull(SPA_REMAINING_BAL),2), formatDate(SPA_DATE) FROM SALEPURCHASE_ACCOUNT WHERE LOCATE('LEE',SPA_INVOICE_NO)!=0 ORDER BY SPA_INVOICE_NO DESC,SPA_DATE DESC", jTable11);
  }catch(NullPointerException npe){}
}
private void purchase_cart_update(){
    try{ ps=con.prepareStatement("UPDATE PURCHASE_CART SET PC_MC_ID=?,PC_P_ID=?, PC_INVOICE_DATE=?, PC_BATCH_ID=?, PC_BOX_QTY=?, PC_FREE_BOX_QTY=?, PC_BOX_RATE=?, PC_BOX_MRP=?, PC_EXP_DATE=?, PC_PACK=?, PC_TAX=?, PC_DISCOUNT=?, PC_DATE=? ,PC_HSN_CODE=? WHERE PC_ID="+Integer.parseInt(jTable7.getModel().getValueAt(jTable7.getSelectedRow(), 0).toString()));
             ps.setInt(1, MC_ID);
             ps.setInt(2, P_ID);
             ps.setDate(3, new java.sql.Date(jDateChooser1.getDate().getTime()));
             ps.setString(4, jTextField37.getText());                   System.out.println("1");
             ps.setInt(5, Integer.parseInt(jTextField38.getText()));    System.out.println("2");
             ps.setInt(6, Integer.parseInt(jTextField39.getText()));    System.out.println("3");
             ps.setFloat(7, Float.parseFloat(jTextField40.getText()));  System.out.println("4");
             ps.setFloat(8, Float.parseFloat(jTextField41.getText()));  System.out.println("5");
             String date=jComboBox12.getSelectedItem().toString()+"-"+((jMonthChooser1.getMonth()+1<10)?"0"+(jMonthChooser1.getMonth()+1):(jMonthChooser1.getMonth()+1)+"")+"-"+"01";System.out.println("11");
             ps.setDate(9, new java.sql.Date(new SimpleDateFormat("yyyy-MM-dd").parse(date).getTime()));System.out.println(date);
             ps.setString(10, jTextField43.getText());   System.out.println("6");
             ps.setFloat(11, Float.parseFloat(jTextField46.getText())); System.out.println("9");
             ps.setFloat(12, Float.parseFloat(jTextField47.getText())); System.out.println("10");
             ps.setDate(13, new java.sql.Date(new Date().getTime()));System.out.println("13");
             ps.setString(14, purchase_hsn_code.getText());
             ps.executeUpdate();
             
            ps=con.prepareStatement("SELECT roundOf(SUM(PC_BOX_QTY*PC_BOX_RATE*(1+PC_TAX*0.01)),2) FROM PURCHASE_CART");
            rs=ps.executeQuery();
            if(rs.next()){
                jButton37.setVisible(true);
                jLabel65.setVisible(true);
                
                total_purchase_amount.setVisible(true);
                purchase_grand_total.setVisible(true);
                purchase_insurance_name.setVisible(true);
                pur_insurance_name_lbl.setVisible(true);
                purchase_insurance_charge.setVisible(true);
                purchase_insurance_charge_lbl.setVisible(true);
                pur_expence_name_lbl.setVisible(true);
                pur_expence_amount_txt.setVisible(true);
                jLabel66.setVisible(true);
                jTextField49.setVisible(true);
                pur_expence_name_txt.setVisible(true);
                jLabel4.setVisible(true);
                total_purchase_amount.setText(rs.getFloat(1)+"");
                purchase_grand_total.setText("Grand Total : "+(Float.parseFloat(purchase_insurance_charge.getText())+Float.parseFloat(total_purchase_amount.getText())));
             }
             reset_purchase_cart();
        }catch(Exception e){ e.printStackTrace();
            JOptionPane.showMessageDialog(null, e);}
}
private void purchase_cart_insert(){
     if(!jTextField37.getText().isEmpty())
        try{ ps=con.prepareStatement("SELECT * FROM PURCHASE_CART INNER JOIN PRODUCT ON PC_P_ID=P_ID AND P_NAME='"+jComboBox11.getSelectedItem().toString()+"' AND PC_BATCH_ID='"+jTextField37.getText()+"'");
             rs=ps.executeQuery();
             if(!rs.next()){
             ps=con.prepareStatement("INSERT INTO PURCHASE_CART(PC_INVOICE_NO,PC_INVOICE_DATE,PC_P_ID,PC_MC_ID,PC_BATCH_ID,PC_BOX_QTY,PC_FREE_BOX_QTY,PC_BOX_RATE,PC_BOX_MRP,PC_PACK,PC_TAX,PC_DISCOUNT,PC_EXP_DATE,PC_DATE,PC_HSN_CODE) VALUES(?,?,?,?, ?,?,?,?, ?,?,?,?, ?,?,?)");
             ps.setString(1, jTextField19.getText());
             ps.setDate(2, new java.sql.Date(jDateChooser1.getDate().getTime()));
             ps.setInt(3, P_ID);
             ps.setInt(4, MC_ID);
             ps.setString(5, jTextField37.getText());                   System.out.println("1");
             ps.setInt(6, Integer.parseInt(jTextField38.getText()));    System.out.println("2");
             ps.setInt(7, Integer.parseInt(jTextField39.getText()));    System.out.println("3");
             ps.setFloat(8, Float.parseFloat(jTextField40.getText()));  System.out.println("4");
             ps.setFloat(9, Float.parseFloat(jTextField41.getText()));  System.out.println("5");
             ps.setString(10,jTextField43.getText());   System.out.println("6");
             ps.setFloat(11, Float.parseFloat(jTextField46.getText())); System.out.println("9");
             ps.setFloat(12, Float.parseFloat(jTextField47.getText())); System.out.println("10");
             String date=jComboBox12.getSelectedItem().toString()+"-"+((jMonthChooser1.getMonth()+1<10)?"0"+(jMonthChooser1.getMonth()+1):(jMonthChooser1.getMonth()+1)+"")+"-"+"01";System.out.println("11");
             ps.setDate(13, new java.sql.Date(new SimpleDateFormat("yyyy-MM-dd").parse(date).getTime()));System.out.println(date);
             ps.setDate(14, new java.sql.Date(new Date().getTime()));System.out.println("13");
             ps.setString(15, purchase_hsn_code.getText());
             ps.executeUpdate();
             ps=con.prepareStatement("SELECT roundOf(SUM(PC_BOX_QTY*PC_BOX_RATE*(1+PC_TAX*0.01)),2) FROM PURCHASE_CART");
             rs=ps.executeQuery();
             if(rs.next()){
                jButton37.setVisible(true);
                jLabel65.setVisible(true);
                
                total_purchase_amount.setVisible(true);
                purchase_grand_total.setVisible(true);
                purchase_insurance_name.setVisible(true);
                pur_insurance_name_lbl.setVisible(true);
                purchase_insurance_charge.setVisible(true);
                purchase_insurance_charge_lbl.setVisible(true);
                pur_expence_name_lbl.setVisible(true);
                pur_expence_amount_txt.setVisible(true);
                jLabel66.setVisible(true);
                jTextField49.setVisible(true);
                pur_expence_name_txt.setVisible(true);
                jLabel4.setVisible(true);
                total_purchase_amount.setText(rs.getFloat(1)+"");
                purchase_grand_total.setText("Grand Total : "+(Float.parseFloat(purchase_insurance_charge.getText())+Float.parseFloat(total_purchase_amount.getText())));
             }
             
             reset_purchase_cart();
             }
             else
                 JOptionPane.showMessageDialog(null, "This Product of same Batch ID already contained in list !");
             
        }catch(Exception e){ e.printStackTrace();
            JOptionPane.showMessageDialog(null, e);}
}
private void purchase_cart_remove(){
     if(jTable7.getSelectedRow()!=-1)
            try{ ps=con.prepareStatement("DELETE FROM PURCHASE_CART WHERE PC_ID="+Integer.parseInt(jTable7.getModel().getValueAt(jTable7.getSelectedRow(), 0).toString()));
                 ps.execute();
                 
                try{ 
                ps=con.prepareStatement("SELECT roundOf(SUM(PC_BOX_QTY*PC_BOX_RATE*(1+PC_TAX*0.01)),2) FROM PURCHASE_CART");
                rs=ps.executeQuery();
                if(rs.next()){
                jButton37.setVisible(true);
                jLabel65.setVisible(true);
                
                total_purchase_amount.setVisible(true);
                purchase_grand_total.setVisible(false);
                purchase_insurance_name.setVisible(true);
                pur_insurance_name_lbl.setVisible(true);
                purchase_insurance_charge.setVisible(true);
                purchase_insurance_charge_lbl.setVisible(true);
                pur_expence_name_lbl.setVisible(true);
                pur_expence_amount_txt.setVisible(true);
                jLabel66.setVisible(true);
                jTextField49.setVisible(true);
                pur_expence_name_txt.setVisible(true);
                jLabel4.setVisible(true);
                total_purchase_amount.setText(rs.getFloat(1)+"");
                purchase_grand_total.setText("Grand Total : "+(Float.parseFloat(purchase_insurance_charge.getText())+Float.parseFloat(total_purchase_amount.getText())));
             }
                }catch(Exception e){}
                
                reset_purchase_cart();
            }catch(Exception e){e.printStackTrace();}
}
private void sale_cart_insert(){
    
    if(jComboBox20.getSelectedIndex()!=0)
     try{   int rem_qty=0,sale_qty=0;  
     String s=jComboBox20.getSelectedItem().toString();
        String arr[]=s.split(s.substring(s.lastIndexOf("-"), s.indexOf("[")));
            ps=con.prepareStatement("SELECT SUM(SO_QTY) FROM SALES_ORDER WHERE SO_BATCH_ID='"+arr[0]+"'");
             rs=ps.executeQuery();
             if(rs.next())sale_qty=rs.getInt(1);
            ps=con.prepareStatement("SELECT PO_BOX_QTY FROM PURCHASE_ORDER WHERE PO_BATCH_ID='"+arr[0]+"'");
            rs=ps.executeQuery();
            if(rs.next())
                rem_qty=rs.getInt(1)-sale_qty;
          if(Integer.parseInt(jTextField35.getText())<=rem_qty)  {
          ps=con.prepareStatement("SELECT * FROM SALES_CART INNER JOIN PRODUCT ON SC_P_ID=P_ID AND P_NAME='"+jComboBox11.getSelectedItem().toString()+"' AND SC_BATCH_ID='"+jComboBox20.getSelectedItem().toString()+"'");
          rs=ps.executeQuery();
          if(!rs.next()){
          ps=con.prepareStatement("INSERT INTO SALES_CART(SC_INVOICE_NO,SC_P_ID,SC_MC_ID,SC_HSN_CODE,SC_BATCH_ID,SC_QTY,SC_QTY_FREE,SC_RATE,SC_CGST,SC_SGST,SC_IGST,SC_MRP,SC_EXP_DATE,SC_INVOICE_DATE,SC_PACK)  VALUES(?,?,?,?, ?,?,?,?, ?,?,?,?, ?,?,?)");  
          ps.setString(1, jTextField17.getText());System.out.println("1");
          ps.setInt(2, P_ID);System.out.println("2");
          ps.setInt(3, MC_ID); //MC_ID
          ps.setString(4, jTextField67.getText());System.out.println("3");
          ps.setString(5, arr[0]);System.out.println("4");
          ps.setInt(6, Integer.parseInt(jTextField35.getText()));
          ps.setInt(7,Integer.parseInt(jTextField36.getText()));
          ps.setFloat(8, Float.parseFloat(jTextField22.getText()));System.out.println("8");
          if(STATE_ID==11){
           ps.setFloat(9, Float.parseFloat(jTextField20.getText())/2);
           ps.setFloat(10, Float.parseFloat(jTextField20.getText())/2);
           ps.setFloat(11, 0);
          }
          else{
              ps.setFloat(9, 0);
              ps.setFloat(10, 0);
              ps.setFloat(11, Float.parseFloat(jTextField20.getText()));
          }
          ps.setFloat(12, Float.parseFloat(jLabel100.getText()));System.out.println("12");
          ps.setDate(13, new java.sql.Date(exp_date.getTime()));System.out.println("13");
          ps.setDate(14, new java.sql.Date(jDateChooser7.getDate().getTime()));System.out.println("14");
          ps.setString(15, arr[1]);
          ps.executeUpdate();
          

          
          ps=con.prepareStatement("SELECT roundOf(SUM(SC_RATE*SC_QTY*(1+(SC_CGST+SC_SGST+SC_IGST)*0.01)),2) FROM SALES_CART");
          rs=ps.executeQuery();
          if(rs.next()){
              jLabel53.setVisible(true);
              jLabel3.setVisible(true);
              jLabel56.setVisible(true);
              jLabel46.setVisible(true);
              jTextField42.setVisible(true);
              jLabel98.setVisible(true);
              jLabel99.setVisible(true);
              jTextField68.setVisible(true);
              user_name.setVisible(true);
              jLabel107.setText(rs.getFloat(1)+"");
              jButton28.setVisible(true);
              jButton51.setVisible(true);
              jLabel104.setVisible(true);
              jLabel105.setVisible(true);
              jLabel106.setVisible(true);
              jLabel107.setVisible(true);
              jComboBox7.setVisible(true);
          }
          ps=con.prepareStatement("SELECT MAX(CAST(SUBSTR(SUBSTR(SPA_INVOICE_NO,LOCATE('/',SPA_INVOICE_NO)+1),LOCATE('/',SUBSTR(SPA_INVOICE_NO,LOCATE('/',SPA_INVOICE_NO)+1))+1) AS BIGINT)) FROM SALEPURCHASE_ACCOUNT WHERE SPA_MC_ID="+MC_ID+"");
          rs=ps.executeQuery();
          if(rs.next())
                     if(rs.getInt(1)<Integer.parseInt(jTextField17.getText().substring(jTextField17.getText().lastIndexOf("/")+1)))
                     {ps=con.prepareStatement("SELECT SPA_REMAINING_BAL FROM SALEPURCHASE_ACCOUNT WHERE SPA_MC_ID="+MC_ID+" AND SPA_ID=(SELECT MAX(SPA_ID) FROM SALEPURCHASE_ACCOUNT WHERE SPA_MC_ID="+MC_ID+")");
                      rs=ps.executeQuery();
                      if(rs.next())
                      jLabel105.setText(DBFunctions.roundOf(rs.getFloat(1), 2)+"");else jLabel105.setText("0");
                     }
                     else jLabel105.setText("0");
                 jLabel56.setText(DBFunctions.roundOf(Float.parseFloat(jLabel105.getText())+Float.parseFloat(jLabel107.getText()), 2)+"");
                 jLabel99.setText(DBFunctions.roundOf(Float.parseFloat(jLabel105.getText())+Float.parseFloat(jLabel107.getText()), 2)+"");
           reset_sales_cart();
          }else
              JOptionPane.showMessageDialog(null, "This Product of same BatchID already Contained in list !");
          }else
              JOptionPane.showMessageDialog(null, "Out of Stock !");
        }catch(Exception e){JOptionPane.showMessageDialog(null, e);}
    else
        JOptionPane.showMessageDialog(null, "Select Batch ID");
}
private void sale_cart_update(){
     if(jComboBox20.getSelectedIndex()!=0)
     try{
        String s=jComboBox20.getSelectedItem().toString();
        String arr[]=s.split(s.substring(s.lastIndexOf("-"), s.indexOf("[")));
          ps=con.prepareStatement("UPDATE SALES_CART SET SC_P_ID=?,SC_BATCH_ID=?,SC_MRP=?,SC_RATE=?,SC_QTY=?,SC_QTY_FREE=?,SC_CGST=?,SC_SGST=?,SC_IGST=?,SC_MC_ID=?,SC_PACK=?  WHERE SC_ID="+Integer.parseInt(jTable6.getModel().getValueAt(jTable6.getSelectedRow(), 0).toString()));
          ps.setInt(1, P_ID);
          ps.setString(2, arr[0]);
          ps.setFloat(3, Float.parseFloat(jLabel100.getText()));
          ps.setFloat(4, Float.parseFloat(jTextField22.getText()));
          ps.setInt(5, Integer.parseInt(jTextField35.getText()));
          ps.setInt(6, Integer.parseInt(jTextField36.getText()));
          if(STATE_ID==11){
           ps.setFloat(7, Float.parseFloat(jTextField20.getText())/2);
           ps.setFloat(8, Float.parseFloat(jTextField20.getText())/2);
           ps.setFloat(9, 0);
          }
          else{
              ps.setFloat(7, 0);
              ps.setFloat(8, 0);
              ps.setFloat(9, Float.parseFloat(jTextField20.getText()));
          }
          ps.setInt(10, MC_ID);
          ps.setString(11, arr[1]);
          ps.executeUpdate();
          
          ps=con.prepareStatement("SELECT roundOf(SUM(SC_RATE*SC_QTY*(1+(SC_CGST+SC_SGST+SC_IGST)*0.01)),2) FROM SALES_CART");
          rs=ps.executeQuery();
          if(rs.next()){
              jLabel53.setVisible(true);
              jLabel3.setVisible(true);
              jLabel56.setVisible(true);
              jLabel46.setVisible(true);
              jTextField42.setVisible(true);
              jLabel98.setVisible(true);
              jLabel99.setVisible(true);
              jTextField68.setVisible(true);
              user_name.setVisible(true);
              jLabel107.setText(rs.getFloat(1)+"");
              jButton28.setVisible(true);
              jButton51.setVisible(true);
              jLabel104.setVisible(true);
              jLabel105.setVisible(true);
              jLabel106.setVisible(true);
              jLabel107.setVisible(true);
              jComboBox7.setVisible(true);
          }
                 ps=con.prepareStatement("SELECT roundOf(SPA_REMAINING_BAL,2) FROM SALEPURCHASE_ACCOUNT WHERE SPA_MC_ID="+MC_ID+" AND SPA_INVOICE_NO!='"+jTextField17.getText()+"'");
                 rs=ps.executeQuery();
                 if(rs.next())
                 jLabel105.setText(rs.getFloat(1)+"");else jLabel105.setText("0");
                 jLabel56.setText(Float.parseFloat(jLabel105.getText())+Float.parseFloat(jLabel107.getText())+"");
                 jLabel99.setText(Float.parseFloat(jLabel105.getText())+Float.parseFloat(jLabel107.getText())+"");
                 
        reset_sales_cart();
        }catch(Exception e){JOptionPane.showMessageDialog(null, e);}
     else
        JOptionPane.showMessageDialog(null, "Select Batch ID");
}
private void sale_cart_remove(){
     if(jTable6.getSelectedRow()!=-1)
        try{ ps=con.prepareStatement("DELETE FROM SALES_CART WHERE SC_ID="+Integer.parseInt(jTable6.getModel().getValueAt(jTable6.getSelectedRow(), 0).toString()));
             ps.execute();
              ps=con.prepareStatement("SELECT SUM(SC_RATE*SC_QTY*(1+(SC_CGST+SC_SGST+SC_IGST)*0.01)) FROM SALES_CART");
          rs=ps.executeQuery();
          if(rs.next()){
              jLabel107.setText(rs.getFloat(1)+"");
               jLabel56.setText(Float.parseFloat(jLabel105.getText())+Float.parseFloat(jLabel107.getText())+"");
                 jLabel99.setText(Float.parseFloat(jLabel105.getText())+Float.parseFloat(jLabel107.getText())+"");
          }
           
          
           reset_sales_cart();
          
        }catch(Exception e){e.printStackTrace();}
}
private void sale_order_insert(){
    if(jComboBox7.getSelectedIndex()!=0)
     try{  // set transaction
         con.setAutoCommit(false);
            //  transport section
             ps=con.prepareStatement("SELECT * FROM TRANSPORT WHERE T_INVOICE_NO='"+jTextField17.getText()+"'");
             rs=ps.executeQuery();
             if(!rs.next()){    
            if(JOptionPane.showConfirmDialog (null, "Would You Like to Add Transport Details ?","Warning",JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
                  new TransportDetails(this, true, con).setVisible(true);
             }
             }  
             System.out.println("Deleting record........");
             // DELETE RECORDS FROM SALES_ORDER TABLE IF EXIST
                 ps=con.prepareStatement("SELECT * FROM SO_SPA_JOINED WHERE SSJ_INVOICE_NO='"+jTextField17.getText()+"'");
                 rs=ps.executeQuery();
                 if(rs.next()){
                     ps=con.prepareStatement("DELETE FROM SALES_ORDER WHERE SO_INVOICE_NO='"+jTextField17.getText()+"'");
                     ps.executeUpdate();
                     ps=con.prepareStatement("DELETE FROM SALEPURCHASE_ACCOUNT WHERE SPA_INVOICE_NO='"+jTextField17.getText()+"' AND SPA_DATE='"+new SimpleDateFormat("yyyy-MM-dd").format(rs.getDate("SSJ_DATE"))+"'");
                     ps.executeUpdate();
                     ps=con.prepareStatement("DELETE FROM SO_SPA_JOINED WHERE SSJ_INVOICE_NO='"+jTextField17.getText()+"'");
                     ps.executeUpdate();
                 }
                 System.out.println("Inserting into Sales_Order................");
              // all records inserted into sales_order
                 ps=con.prepareStatement("INSERT INTO SALES_ORDER(SO_INVOICE_NO,SO_P_ID,SO_MC_ID,SO_HSN_CODE,SO_BATCH_ID,SO_QTY,SO_QTY_FREE,SO_RATE,SO_CGST,SO_SGST,SO_IGST,SO_MRP,SO_EXP_DATE,SO_INVOICE_DATE) SELECT SC_INVOICE_NO,SC_P_ID,SC_MC_ID,SC_HSN_CODE,SC_BATCH_ID,SC_QTY,SC_QTY_FREE,SC_RATE,SC_CGST,SC_SGST,SC_IGST,SC_MRP,SC_EXP_DATE,SC_INVOICE_DATE FROM SALES_CART");
                 ps.executeUpdate();
                
                 float avg=0;
                 ps=con.prepareStatement("SELECT AVG(SC_CGST+SC_SGST+SC_IGST) FROM SALES_CART");
                 rs=ps.executeQuery();
                 if(rs.next())avg=rs.getFloat(1);
                 System.out.println("Fetching records from SO_SPA_JOINED..............");
                 ps=con.prepareStatement("SELECT * FROM SO_SPA_JOINED WHERE SSJ_INVOICE_NO='"+jTextField17.getText()+"'");
                 rs=ps.executeQuery();
                 if(!rs.next()){
                     System.out.println("Inserting records into SO_SPA_JOINED..............");
                 // INSERT INTO SO_SPA_JOINED TABLE
                ps=con.prepareStatement("INSERT INTO SO_SPA_JOINED(SSJ_INVOICE_NO,SSJ_INVOICE_DATE,SSJ_DATE) VALUES(?,?,?)");
                ps.setString(1, jTextField17.getText());
                ps.setDate(2, new java.sql.Date(jDateChooser7.getDate().getTime()));
                ps.setDate(3, new java.sql.Date(new Date().getTime()));
                ps.executeUpdate();
                 }
                 System.out.println("Inserting records into SALEPURCHASE_ACCOUNT..............");
                 ps=con.prepareStatement("INSERT INTO SALEPURCHASE_ACCOUNT(SPA_INVOICE_NO,SPA_INVOICE_DATE,SPA_SALE_PURCHASE_AMOUNT,SPA_DISCOUNT,SPA_TOTAL_TAX,SPA_PAID_AMOUNT,SPA_REMAINING_BAL,SPA_PAYMENT_MODE,SPA_DATE,SPA_MC_ID,SPA_STATE_NC_ID,SPA_T_ID,SPA_SUBMIT_BY,SPA_PREV_INVOICE_AMOUNT) VALUES(?,?,?,?, ?,?,?,?, ?,?,?,?, ?,?)");
                 ps.setString(1, jTextField17.getText());
                 ps.setDate(2, new java.sql.Date(jDateChooser7.getDate().getTime()));
                 ps.setFloat(3, Float.parseFloat(jLabel56.getText())); //amount
                 ps.setFloat(4, Float.parseFloat((jTextField68.getText().contains("%"))?jTextField68.getText().substring(0, jTextField68.getText().indexOf("%")):Float.parseFloat(jTextField68.getText())*100/Float.parseFloat(jLabel107.getText())+""));  //discount
                 ps.setFloat(5, avg);  //total tax
                 ps.setFloat(6, Float.parseFloat(jTextField42.getText()));
                 ps.setFloat(7, Float.parseFloat(jLabel99.getText()));   //balance
                 ps.setString(8, jComboBox7.getSelectedItem().toString());
                 ps.setDate(9, new java.sql.Date(new Date().getTime())); 
                 ps.setInt(10, MC_ID);
                 ps.setInt(11, STATE_ID);
                 ps.setInt(12, T_ID);
                 ps.setString(13, user_name.getText());
                 ps.setFloat(14, Float.parseFloat(jLabel105.getText())); //previous invoice amount
                 ps.executeUpdate();
                 con.commit();
                 System.out.println("TRUNCATING SALES_CART..............");
                 ps=con.prepareStatement("TRUNCATE TABLE SALES_CART");
                 ps.execute();
        if(JOptionPane.showConfirmDialog (null, "Would You Like toView Report ?","Warning",JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
          //  report("SELECT ROW_NUMBER() OVER() AS R,SO_INVOICE_NO,SO_INVOICE_DATE,P_NAME,SO_BATCH_ID,SO_EXP_DATE,SO_MRP,SO_RATE,SO_QTY,SO_QTY_FREE,SO_QTY*SO_RATE,SO_CGST,SO_SGST,SO_IGST,SO_QTY*SO_RATE*(1+(SO_CGST+SO_SGST+SO_IGST)*0.01),STATE_NAME,STATE_CODE,MC_NAME,MC_ADDRESS,MC_GST_NO,MC_PAN_NO,MC_LIC1,MC_LIC2,MC_DOC_LIC,MC_EMAIL,MC_CONTACT_NO,T_MODE,T_V_NO,T_CGST,T_SGST,T_IGST,T_AMOUNT,T_STATE_NC_ID,T_CONSIGNEE_NAME,T_CONSIGNEE_CONTACT,T_CONSIGNEE_ADDRESS,STATE_NAME,STATE_CODE FROM SALES_ORDER INNER JOIN PRODUCT ON SO_P_ID=P_ID INNER JOIN MANUFACTURER_CUSTOMER ON SO_MC_ID=MC_ID LEFT OUTER JOIN TRANSPORT ON SO_INVOICE_NO=T_INVOICE_NO INNER JOIN STATE_NC ON MC_STATE_NC_ID=STATE_NC_ID INNER JOIN SALEPURCHASE_ACCOUNT ON SPA_INVOICE_NO=SO_INVOICE_NO AND SO_INVOICE_NO='"+jTextField17.getText()+"'","reports\\invoice.jrxml");
            report("SELECT ROW_NUMBER() OVER() AS R,SO_INVOICE_NO,SO_INVOICE_DATE,P_NAME,SO_HSN_CODE,SO_BATCH_ID,SO_EXP_DATE,roundOf(SO_MRP,2) as MRP,roundOf(SO_RATE,2) as RATE,SO_QTY,SO_QTY_FREE,roundOf(SO_QTY*SO_RATE,2) as AMOUNT,SO_CGST,SO_SGST,SO_IGST,roundOf(SO_QTY*SO_RATE*(1+(SO_CGST+SO_SGST+SO_IGST)*0.01),2) as TOTAL_AMOUNT,STATE_NC.STATE_NAME,STATE_NC.STATE_CODE,MC_NAME,MC_ADDRESS,MC_GST_NO,MC_PAN_NO,MC_LIC1,MC_LIC2,MC_DOC_LIC,MC_EMAIL,MC_CONTACT_NO,MC_FSSAI_CODE,MC_COS_LIC,T_MODE,T_V_NO,T_CGST,T_SGST,T_IGST,T_AMOUNT,T_STATE_NC_ID,T_CONSIGNEE_NAME,T_CONSIGNEE_CONTACT,T_CONSIGNEE_ADDRESS,STATE2.STATE_NAME AS S2,STATE2.STATE_CODE AS SC FROM SALES_ORDER INNER JOIN PRODUCT ON SO_P_ID=P_ID INNER JOIN MANUFACTURER_CUSTOMER ON SO_MC_ID=MC_ID INNER JOIN STATE_NC ON MC_STATE_NC_ID=STATE_NC.STATE_NC_ID INNER JOIN SALEPURCHASE_ACCOUNT ON SPA_INVOICE_NO=SO_INVOICE_NO  AND SO_INVOICE_NO='"+jTextField17.getText()+"' LEFT OUTER JOIN TRANSPORT ON SPA_INVOICE_NO=T_INVOICE_NO LEFT OUTER JOIN STATE_NC AS STATE2 ON STATE2.STATE_NC_ID=T_STATE_NC_ID ","/reports/invoice_1.jrxml");
             }
        reset_sales_order();  
        }catch(Exception e){e.printStackTrace();
        JOptionPane.showMessageDialog(null, e);
            try{
            con.rollback();
            }catch(SQLException roll){
            JOptionPane.showMessageDialog(null, roll);
            }
        }
    else
        JOptionPane.showMessageDialog(null, "Select Payment Mode !");
}
protected void reset_sales_return(){
    jButton46.setVisible(false);
    jButton47.setVisible(false);
    jDateChooser8.setDate(new Date());
    jComboBox13.setSelectedIndex(0);
    jComboBox14.setSelectedIndex(0);
    jComboBox15.setSelectedIndex(0);
    jTextField63.setText("");
    jTextField64.setText("");
    jTextField65.setText("");
    jTextField66.setText("");
    getDataInCombo("SELECT DISTINCT SO_INVOICE_NO FROM SALES_ORDER", jComboBox13);
    //getDataInCombo("SELECT DISTINCT P_NAME FROM PRODUCT", jComboBox14);
    getDataInCombo("SELECT DISTINCT MC_NAME FROM MANUFACTURER_CUSTOMER WHERE MC_ID IN(SELECT DISTINCT SO_MC_ID FROM SALES_ORDER)", jComboBox15);
    getTableData("SELECT SR_INVOICE_NO, SR_BATCH_ID, MC_NAME,formatDate(SR_INVOICE_DATE), SUM(SR_BOX_QTY) FROM SALES_RETURN INNER JOIN MANUFACTURER_CUSTOMER ON SR_MC_ID=MC_ID GROUP BY SR_INVOICE_NO,SR_BATCH_ID,MC_NAME,SR_INVOICE_DATE", jTable10);
    try{ ps=con.prepareStatement("SELECT SUM(SO_QTY*SO_RATE) FROM SALES_ORDER");
    rs=ps.executeQuery();
    if(rs.next())jLabel61.setText(rs.getFloat(1)+"");
    
    ps=con.prepareStatement("SELECT SUM(A.S1) FROM (SELECT SUM(SR_BOX_QTY*SO_RATE) AS S1  FROM SALES_RETURN INNER JOIN SALES_ORDER ON SR_BATCH_ID=SO_BATCH_ID) AS A");
    rs=ps.executeQuery();
    if(rs.next())jLabel151.setText(rs.getFloat(1)+"");
    
    jLabel152.setText(Float.parseFloat(jLabel61.getText())-Float.parseFloat(jLabel151.getText())+"");   
    }catch(Exception e){JOptionPane.showMessageDialog(null, e);}
}
protected void sale_return_insert(){
     try{ ps=con.prepareStatement("INSERT INTO SALES_RETURN(SR_INVOICE_NO,SR_P_ID,SR_MC_ID,SR_BATCH_ID,SR_BOX_QTY,SR_STRIP_QTY,SR_DATE) VALUES(?,?,?,?, ?,?,?)");
             ps.setString(1, jComboBox13.getSelectedItem().toString());
             ps.setInt(2, P_ID);
             ps.setInt(3, MC_ID);
             ps.setString(4, jTextField63.getText());
             ps.setInt(5, Integer.parseInt(jTextField64.getText()));
             ps.setInt(6, Integer.parseInt(jTextField65.getText()));
             ps.setDate(7, new java.sql.Date(jDateChooser8.getDate().getTime()));
             ps.executeUpdate();
            reset_sales_return();
        }catch(Exception e){JOptionPane.showMessageDialog(null, e);}
}
protected void sale_return_update(){
      try{ ps=con.prepareStatement("UPDATE SALES_RETURN SET SR_P_ID=?,SR_MC_ID=?, SR_BATCH_ID=?, SR_BOX_QTY=?, SR_STRIP_QTY=?, SR_DATE=? WHERE SR_INVOICE_NO=?");
             ps.setInt(1, P_ID);
             ps.setInt(2, MC_ID);
             ps.setString(3, jTextField63.getText());
             ps.setInt(4, Integer.parseInt(jTextField64.getText()));
             ps.setInt(5, Integer.parseInt(jTextField65.getText()));
             ps.setDate(6, new java.sql.Date(jDateChooser8.getDate().getTime()));
             ps.setString(7, jComboBox13.getSelectedItem().toString());
             ps.executeUpdate();
            reset_sales_return();
        }catch(Exception e){JOptionPane.showMessageDialog(null, e);}
}
protected void sale_return_remove(){
      try{ ps=con.prepareStatement("DELETE FROM SALES_RETURN WHERE SR_INVOICE_NO='"+jComboBox13.getSelectedItem().toString()+"'");
             ps.execute();
        reset_sales_return();
        }catch(Exception e){JOptionPane.showMessageDialog(null, e);}
}
protected void mc_insert(){
     if(!jTextField55.getText().isEmpty())
        try{ ps=con.prepareStatement("INSERT INTO MANUFACTURER_CUSTOMER(MC_NAME,MC_CONTACT_NO,MC_EMAIL,MC_ADDRESS,MC_GST_NO,MC_LIC1,MC_LIC2,MC_DOC_LIC,MC_STATE_NC_ID,MC_COS_LIC,MC_FSSAI_CODE,MC_PAN_NO) VALUES(?,?,?,?, ?,?,?,?, ?,?,?,?)");
             ps.setString(1, jTextField55.getText());
             ps.setString(2, jTextField56.getText());
             ps.setString(3, jTextField57.getText());
             ps.setString(4, jTextArea1.getText());
             ps.setString(5, jTextField58.getText());
             ps.setString(6, jTextField59.getText());
             ps.setString(7, jTextField60.getText());
             ps.setString(8, jTextField61.getText());
             ps.setInt(9, STATE_ID);
             ps.setString(10, jTextField21.getText());
             ps.setString(11, jTextField18.getText());
             ps.setString(12, jTextField44.getText());
             ps.executeUpdate();
             reset_maufacturer_customer();
           JOptionPane.showMessageDialog(null, "Data Inserted ");
        }catch(Exception e){e.printStackTrace();}
}
protected void mc_update(){
    try{ ps=con.prepareStatement("UPDATE MANUFACTURER_CUSTOMER SET MC_NAME=?,MC_CONTACT_NO=?, MC_EMAIL=?, MC_ADDRESS=?, MC_LIC1=?, MC_LIC2=?, MC_DOC_LIC=?, MC_STATE_NC_ID=?,MC_COS_LIC=?,MC_FSSAI_CODE=?,MC_PAN_NO=? WHERE MC_ID="+MC_ID);
         ps.setString(1, jTextField55.getText());
         ps.setString(2, jTextField56.getText());
         ps.setString(3, jTextField57.getText());
         ps.setString(4, jTextArea1.getText());
         ps.setString(5, jTextField59.getText());
         ps.setString(6, jTextField60.getText());
         ps.setString(7, jTextField61.getText());
         ps.setInt(8, STATE_ID);
         ps.setString(9, jTextField21.getText());
         ps.setString(10, jTextField18.getText());
         ps.setString(11, jTextField44.getText());
         ps.executeUpdate();
        reset_maufacturer_customer();
    }catch(Exception e){JOptionPane.showMessageDialog(null, e);}
}
protected void mc_remove(){
     try{ ps=con.prepareStatement("DELETE  FROM MANUFACTURER_CUSTOMER WHERE MC_ID="+MC_ID);
             ps.execute();
             reset_maufacturer_customer();
        }catch(Exception e){JOptionPane.showMessageDialog(null, e);}
}
protected void product_insert(){
      try{ PT_ID=0; 
            if(!jTextField51.getText().isEmpty()){
             ps=con.prepareStatement("SELECT * FROM PRODUCT_TYPE WHERE PT_NAME='"+jTextField51.getText()+"'");
             rs=ps.executeQuery();
             if(!rs.next()){
                ps=con.prepareStatement("INSERT INTO PRODUCT_TYPE(PT_NAME) VALUES(?)");
                ps.setString(1, jTextField51.getText());
                ps.executeUpdate();
             }
             ps=con.prepareStatement("SELECT PT_ID FROM PRODUCT_TYPE WHERE PT_NAME='"+jTextField51.getText()+"'");
             rs=ps.executeQuery();
             if(rs.next())PT_ID=rs.getInt(1);
             }
            if(!jTextField51.getText().isEmpty() && !jTextField52.getText().isEmpty()){
             ps=con.prepareStatement("INSERT INTO PRODUCT(P_NAME,P_CODE,P_PT_ID) VALUES(?,?,?)");
             ps.setString(1, jTextField52.getText());
             ps.setString(2, jTextField53.getText());
             ps.setInt(3, PT_ID);
             ps.executeUpdate();
             JOptionPane.showMessageDialog(null, "Product Inserted");}
             reset_product();
        }catch(Exception e){JOptionPane.showMessageDialog(null, e);}
}
protected void product_update(){
try{ ps=con.prepareStatement("UPDATE PRODUCT SET P_NAME=?, P_CODE=? WHERE P_ID="+P_ID);
             ps.setString(1, jTextField52.getText());
             ps.setString(2, jTextField53.getText());
             ps.executeUpdate();
             reset_product();
        }catch(Exception e){JOptionPane.showMessageDialog(null, e);}
}
protected void product_remove(){
    try{ps=con.prepareStatement("DELETE FROM PRODUCT WHERE P_ID="+P_ID);
            ps.execute();
             reset_product();
        }catch(Exception e){JOptionPane.showMessageDialog(null, e);}
}
protected void party_insert(){
    STATE_ID=0;T_ID=0;
        Date date=null;
        try{  ps=con.prepareStatement("SELECT SPA_STATE_NC_ID,SPA_T_ID,SPA_INVOICE_DATE FROM SALEPURCHASE_ACCOUNT WHERE SPA_INVOICE_NO='"+jTextField69.getText()+"'");
              rs=ps.executeQuery();
              if(rs.next()){
                STATE_ID=rs.getInt(1);
                T_ID=rs.getInt(2);
                date=rs.getDate(3);
              }
            
            if(jComboBox18.getSelectedIndex()!=0){
             ps=con.prepareStatement("INSERT INTO SALEPURCHASE_ACCOUNT(SPA_MC_ID,SPA_STATE_NC_ID,SPA_T_ID,SPA_INVOICE_NO,SPA_INVOICE_DATE,SPA_SALE_PURCHASE_AMOUNT,SPA_PAID_AMOUNT,SPA_REMAINING_BAL,SPA_DATE,SPA_PAYMENT_MODE) VALUES(?,?,?,?, ?,?,?,?, ?,?)");
             ps.setInt(1, MC_ID);
             ps.setInt(2, STATE_ID);
             ps.setInt(3, T_ID);
             ps.setString(4, jTextField69.getText());
             ps.setDate(5, new java.sql.Date(date.getTime()));
             ps.setFloat(6, Float.parseFloat(jLabel114.getText()));
             ps.setFloat(7, Float.parseFloat(jTextField70.getText()));
             ps.setFloat(8, Float.parseFloat(jLabel134.getText())-Float.parseFloat(jTextField70.getText()));
             ps.setDate(9, new java.sql.Date(new Date().getTime()));
             ps.setString(10, jComboBox19.getSelectedItem().toString());
             ps.executeUpdate();
            }
            else JOptionPane.showMessageDialog(null, "Select Part Name !");
            //getTableData("SELECT SPA_ID,SPA_INVOICE_NO,SPA_INVOICE_DATE,SPA_SALE_PURCHASE_AMOUNT, SPA_PAID_AMOUNT, SPA_REMAINING_BAL, SPA_DATE FROM SALEPURCHASE_ACCOUNT WHERE SPA_MC_ID="+MC_ID, jTable11);
             if(jComboBox18.getSelectedIndex()!=0){
             getTableData("SELECT SPA_ID,SPA_INVOICE_NO,formatDate(SPA_INVOICE_DATE),roundOf(doublenotnull(SPA_SALE_PURCHASE_AMOUNT),2),roundOf(doublenotnull(SPA_PREV_INVOICE_AMOUNT),2), roundOf(doublenotnull(SPA_PAID_AMOUNT),2), roundOf(doublenotnull(SPA_REMAINING_BAL),2), formatDate(SPA_DATE) FROM SALEPURCHASE_ACCOUNT WHERE LOCATE('LEE',SPA_INVOICE_NO)!=0 AND SPA_MC_ID="+MC_ID+" ORDER BY SPA_INVOICE_NO DESC,SPA_DATE DESC", jTable11);
//             else
//             getTableData("SELECT SPA_ID,SPA_INVOICE_NO,formatDate(SPA_INVOICE_DATE),roundOf(doublenotnull(SPA_SALE_PURCHASE_AMOUNT),2), roundOf(doublenotnull(SPA_PAID_AMOUNT),2), roundOf(doublenotnull(SPA_REMAINING_BAL),2), formatDate(SPA_DATE) FROM SALEPURCHASE_ACCOUNT WHERE LOCATE('LEE',SPA_INVOICE_NO)!=0 ORDER BY SPA_INVOICE_NO DESC,SPA_DATE DESC", jTable11);    
             jLabel114.setText("");
    jLabel119.setText("");
    jLabel134.setText("");
    jTextField69.setText("");
    jTextField70.setText("");
    jTextField71.setText("");
    jComboBox19.setSelectedIndex(0);
             }else reset_party();
        }catch(Exception e){ e.printStackTrace();
            JOptionPane.showMessageDialog(null, e);}
}
protected void party_remove(){
     if(JOptionPane.showConfirmDialog (null, "Would You Like to Delete Record ?","Warning",JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
        try{ ps=con.prepareStatement("DELETE FROM SALEPURCHASE_ACCOUNT WHERE SPA_ID="+Integer.parseInt(jTable11.getModel().getValueAt(jTable11.getSelectedRow(), 0).toString()));
             ps.executeUpdate();
           // getTableData("SELECT SPA_ID,SPA_INVOICE_NO,SPA_INVOICE_DATE,SPA_SALE_PURCHASE_AMOUNT, SPA_PAID_AMOUNT, SPA_REMAINING_BAL, SPA_DATE FROM SALEPURCHASE_ACCOUNT WHERE LOCATE('LEE',SPA_INVOICE_NO)!=0 SPA_MC_ID="+MC_ID+" ORDER BY SPA_INVOICE_NO DESC,SPA_DATE DESC", jTable11);
            reset_party();
        }catch(Exception e){JOptionPane.showMessageDialog(null, e);}}
}
protected void reset_user(){
    jButton76.setVisible(false);
    jButton77.setVisible(false);
    jTextField72.setText("");
    jPasswordField1.setText(null);
    jPasswordField2.setText(null);
    jPanel11.setVisible(false);
    jCheckBox1.setSelected(false);
    jCheckBox2.setSelected(false);
    jCheckBox3.setSelected(false);
    jCheckBox4.setSelected(false);
    jCheckBox5.setSelected(false);
    jCheckBox6.setSelected(false);
    jCheckBox7.setSelected(false);
     if(LoginPanel.USER_NAME.equals("admin")||LoginPanel.USER_NAME.equals("mujeeb")){
         
        DefaultTableModel dtm=(DefaultTableModel)jTable14.getModel();
        TableColumnModel model = jTable14.getColumnModel();
        boolean found = false;
        for (int index = 0; index < model.getColumnCount(); index++) {
         if (model.getColumn(index).getIdentifier().equals("Password")) {
        found = true;
        break;
        }            
        }
        if(!found)
        dtm.addColumn("Password");
         getTableData("SELECT USER_NAME,CREATED_ON,CREATED_BY,USER_PASSWORD FROM LOGIN_TABLE WHERE USER_NAME!='mujeeb'", jTable14);   
      
      
     }
     else
    getTableData("SELECT USER_NAME,CREATED_ON,CREATED_BY FROM LOGIN_TABLE WHERE USER_NAME!='mujeeb'", jTable14);
    disable_panel();
}
public void create_DBComponent(String query){
    try{ ps=con.prepareStatement(query);
         ps.execute();
        
    }catch(Exception e){
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, e);}
}
private void submit_into_purchase_order(){
       try{ con.setAutoCommit(false);
            ps=con.prepareStatement("SELECT * FROM SO_SPA_JOINED WHERE SSJ_INVOICE_NO='"+jTextField19.getText()+"'");
            rs=ps.executeQuery();
            if(rs.next()){
               ps=con.prepareStatement("DELETE FROM PURCHASE_ORDER WHERE PO_INVOICE_NO='"+jTextField19.getText()+"'");
               ps.executeUpdate();
               ps=con.prepareStatement("DELETE FROM SALEPURCHASE_ACCOUNT WHERE SPA_INVOICE_NO='"+jTextField19.getText()+"' AND SPA_DATE='"+new SimpleDateFormat("yyyy-MM-dd").format(rs.getDate("SSJ_DATE"))+"'");
               ps.executeUpdate();
               ps=con.prepareStatement("DELETE FROM SO_SPA_JOINED WHERE SSJ_INVOICE_NO='"+jTextField19.getText()+"'");
               ps.executeUpdate();
            }
             
            
            // INSERT INTO PURCHASE_ORDER
             ps=con.prepareStatement("INSERT INTO PURCHASE_ORDER(PO_INVOICE_NO,PO_INVOICE_DATE,PO_P_ID,PO_MC_ID,PO_BATCH_ID,PO_BOX_QTY,PO_FREE_BOX_QTY,PO_BOX_RATE,PO_BOX_MRP,PO_PACK,PO_TAX,PO_DISCOUNT,PO_EXP_DATE,PO_DATE,PO_HSN_CODE) SELECT PC_INVOICE_NO,PC_INVOICE_DATE,PC_P_ID,PC_MC_ID,PC_BATCH_ID,PC_BOX_QTY,PC_FREE_BOX_QTY,PC_BOX_RATE,PC_BOX_MRP,PC_PACK,PC_TAX,PC_DISCOUNT,PC_EXP_DATE,PC_DATE,PC_HSN_CODE FROM PURCHASE_CART");
             ps.executeUpdate();
             
             ps=con.prepareStatement("SELECT * FROM SALEPURCHASE_ACCOUNT WHERE SPA_INVOICE_NO='"+jTextField19.getText()+"'");
             rs=ps.executeQuery();
             if(!rs.next()){
             // INSERT INTO SO_SPA_JOINED TABLE
             ps=con.prepareStatement("INSERT INTO SO_SPA_JOINED(SSJ_INVOICE_NO,SSJ_INVOICE_DATE,SSJ_DATE) VALUES(?,?,?)");
             ps.setString(1, jTextField19.getText());
             ps.setDate(2, new java.sql.Date(jDateChooser1.getDate().getTime()));
             ps.setDate(3, new java.sql.Date(new Date().getTime()));
             ps.executeUpdate();
                 
             // INSERT INTO SALEPURCHASE_ACCOUNT TABLE
             ps=con.prepareStatement("INSERT INTO SALEPURCHASE_ACCOUNT(SPA_INVOICE_NO,SPA_SALE_PURCHASE_AMOUNT,SPA_EXPENCE_NAME,SPA_OTHER_EXPENCE,SPA_SUBMIT_BY,SPA_INSURANCE_NAME,SPA_INSURANCE_AMOUNT,SPA_INVOICE_DATE,SPA_DATE) VALUES(?,?,?,?, ?,?,?,?, ?)");
             ps.setString(1, jTextField19.getText());
             ps.setFloat(2, Float.parseFloat(total_purchase_amount.getText()));
             ps.setString(3, pur_expence_name_txt.getText());
             ps.setFloat(4, Float.parseFloat(pur_expence_amount_txt.getText()));
             ps.setString(5, jTextField49.getText());
             ps.setString(6, purchase_insurance_name.getText());
             ps.setFloat(7, Float.parseFloat(purchase_insurance_charge.getText()));
             ps.setDate(8, new java.sql.Date(jDateChooser1.getDate().getTime()));
             ps.setDate(9, new java.sql.Date(new Date().getTime()));
             ps.executeUpdate();
             }
             con.commit();
             reset_purchase_order();
            JOptionPane.showMessageDialog(null, "Data Inserted");
        }catch(SQLException e){ e.printStackTrace();
            JOptionPane.showMessageDialog(null, e);
            try {
                System.err.print("Transaction is being rolled back");
                con.rollback();
            } catch(SQLException excep) {
               JOptionPane.showMessageDialog(null, excep);
            }
        }
}
private void reset_button_color(){
    company_profile_btn.setBackground(Color.LIGHT_GRAY);
    sale_btn.setBackground(Color.LIGHT_GRAY);
    add_company_btn.setBackground(Color.LIGHT_GRAY);
    exit_btn.setBackground(Color.LIGHT_GRAY);
    logout_btn.setBackground(Color.LIGHT_GRAY);
    hsn_sac_btn.setBackground(Color.LIGHT_GRAY);
    view_invoice_btn.setBackground(Color.LIGHT_GRAY);
    purchase_btn.setBackground(Color.LIGHT_GRAY);
    productntype_btn.setBackground(Color.LIGHT_GRAY);
    sales_rtn_btn.setBackground(Color.LIGHT_GRAY);
    party_mng_btn.setBackground(Color.LIGHT_GRAY);
    report_btn.setBackground(Color.LIGHT_GRAY);
    manage_user_btn.setBackground(Color.LIGHT_GRAY);
    required_btn.setBackground(Color.LIGHT_GRAY);
}
protected void createFolder(String folderName){
    try{ if(!new File(folderName).exists())new File(folderName).mkdir();
        
    }catch(Exception e){e.printStackTrace();}
}
private void updateUserPermission(int user_id,String control,boolean bool){
    try{ ps=con.prepareStatement("SELECT * FROM PERMISSION_TABLE WHERE PMT_USER_ID="+user_id+" AND PMT_CONTROL_NAME='"+control+"'");
         rs=ps.executeQuery();
         if(!rs.next()){
             ps=con.prepareStatement("INSERT INTO PERMISSION_TABLE(PMT_USER_ID,PMT_CONTROL_NAME,PMT_ALLOWED) VALUES(?,?,?)");
             ps.setInt(1, user_id);
             ps.setString(2, control);
             ps.setBoolean(3, bool);
             ps.executeUpdate();
             System.out.println("insert....."+user_id+"\t"+control+"\t"+bool);
         }
         else{
            ps=con.prepareStatement("UPDATE PERMISSION_TABLE SET PMT_ALLOWED=? WHERE PMT_CONTROL_NAME=? AND PMT_USER_ID=?");
            ps.setBoolean(1, bool);
            ps.setString(2, control);
            ps.setInt(3, user_id);
            ps.executeUpdate(); 
            System.out.println("update....."+user_id+"\t"+control+"\t"+bool);
         }
       
    }catch(Exception e){e.printStackTrace();}
}
private void manage_permission(){
     int user_id=0; 
       try{ ps=con.prepareStatement("SELECT USER_ID FROM LOGIN_TABLE WHERE USER_NAME='"+jTable14.getValueAt(jTable14.getSelectedRow(), 0).toString()+"'");
             rs=ps.executeQuery();
             if(rs.next())user_id=rs.getInt(1);
                 
        }catch(Exception e){e.printStackTrace();}
        
        if(jCheckBox1.isSelected())
            updateUserPermission(user_id, "purchase_order", true);
        else
            updateUserPermission(user_id, "purchase_order", false);
        if(jCheckBox2.isSelected())
            updateUserPermission(user_id, "sale_to_customer", true);
        else
            updateUserPermission(user_id, "sale_to_customer", false);
        if(jCheckBox3.isSelected())
            updateUserPermission(user_id, "reports_panel", true);
        else
            updateUserPermission(user_id, "reports_panel", false);
        if(jCheckBox4.isSelected())
            updateUserPermission(user_id, "sales_return", true);
        else
            updateUserPermission(user_id, "sales_return", false);
        if(jCheckBox5.isSelected())
            updateUserPermission(user_id, "party_management_panel", true);
        else
            updateUserPermission(user_id, "party_management_panel", false);
        
        if(jCheckBox6.isSelected())
            updateUserPermission(user_id, "purchase_update", true);
        else
            updateUserPermission(user_id, "purchase_update", false);
        
        if(jCheckBox7.isSelected())
            updateUserPermission(user_id, "sale_update", true);
        else
            updateUserPermission(user_id, "sale_update", false);
        
        if(jCheckBox8.isSelected())
            updateUserPermission(user_id, "add_company", true);
        else
            updateUserPermission(user_id, "add_company", false);
        
        if(jCheckBox9.isSelected())
            updateUserPermission(user_id, "add_product", true);
        else
            updateUserPermission(user_id, "add_product", false);
         reset_user();
}
private void add_user(){
     try{ if(new String(jPasswordField1.getPassword()).equals(new String(jPasswordField2.getPassword()))){
             ps=con.prepareStatement("INSERT INTO LOGIN_TABLE(USER_NAME,USER_PASSWORD,CREATED_BY,CREATED_ON) VALUES(?,?,?,?)");
             ps.setString(1, jTextField72.getText());
             ps.setString(2, new String(jPasswordField1.getPassword()));
             ps.setString(3, LoginPanel.USER_NAME);
             ps.setDate(4, new java.sql.Date(new Date().getTime()));
             ps.executeUpdate();
             reset_user();
        }
        else{
            JOptionPane.showMessageDialog(null, "Password didn't match !");
        }
        }catch(Exception e){JOptionPane.showMessageDialog(null, e);}
}
private void update_user(){
     if(new String(jPasswordField1.getPassword()).equals(new String(jPasswordField2.getPassword())) && !new String(jPasswordField1.getPassword()).isEmpty()){
        try{ ps=con.prepareStatement("UPDATE LOGIN_TABLE SET USER_PASSWORD=?,CREATED_BY=?,CREATED_ON=? WHERE USER_NAME='"+jTextField72.getText()+"'");
             ps.setString(1, new String(jPasswordField1.getPassword()));
             ps.setString(2, LoginPanel.USER_NAME);
             ps.setDate(3, new java.sql.Date(new Date().getTime()));
             ps.executeUpdate();
             reset_user();
        }catch(Exception e){e.printStackTrace();}
        }
        else
            JOptionPane.showMessageDialog(null, "Password didn't match !");
}
private void delete_user(){
     try{ ps=con.prepareStatement("DELETE FROM LOGIN_TABLE WHERE USER_NAME='"+jTable14.getValueAt(jTable14.getSelectedRow(), 0).toString()+"'");
             ps.executeUpdate();
             reset_user();
        }catch(Exception e){e.printStackTrace();}
}
private void fetch_permission(int user_id){
    try{ ps=con.prepareStatement("SELECT * FROM PERMISSION_TABLE WHERE PMT_USER_ID="+user_id);
         rs=ps.executeQuery();
         while(rs.next()){
             if(rs.getString("PMT_CONTROL_NAME").equals("purchase_order"))
                 jCheckBox1.setSelected(rs.getBoolean("PMT_ALLOWED"));
             if(rs.getString("PMT_CONTROL_NAME").equals("sale_to_customer"))
                 jCheckBox2.setSelected(rs.getBoolean("PMT_ALLOWED"));
             if(rs.getString("PMT_CONTROL_NAME").equals("reports_panel"))
                 jCheckBox3.setSelected(rs.getBoolean("PMT_ALLOWED"));
             if(rs.getString("PMT_CONTROL_NAME").equals("sales_return"))
                 jCheckBox4.setSelected(rs.getBoolean("PMT_ALLOWED"));
             if(rs.getString("PMT_CONTROL_NAME").equals("party_management_panel"))
                jCheckBox5.setSelected(rs.getBoolean("PMT_ALLOWED"));
             if(rs.getString("PMT_CONTROL_NAME").equals("purchase_update"))
                jCheckBox6.setSelected(rs.getBoolean("PMT_ALLOWED"));
             if(rs.getString("PMT_CONTROL_NAME").equals("sale_update"))
                jCheckBox7.setSelected(rs.getBoolean("PMT_ALLOWED"));
             if(rs.getString("PMT_CONTROL_NAME").equals("add_company"))
                jCheckBox8.setSelected(rs.getBoolean("PMT_ALLOWED"));
             if(rs.getString("PMT_CONTROL_NAME").equals("add_product"))
                jCheckBox9.setSelected(rs.getBoolean("PMT_ALLOWED"));
         }
        
    }catch(Exception e){e.printStackTrace();}
}
private void disable_panel(){
     purchase_btn.setEnabled(true);
      sale_btn.setEnabled(true);
      report_btn.setEnabled(true);
      sales_rtn_btn.setEnabled(true);
       party_mng_btn.setEnabled(true);
       manage_user_btn.setVisible(false);
       purchase_invoice_delete_btn.setVisible(false);
       jButton30.setVisible(false);
       jButton50.setVisible(true);   // purchase
       jButton2.setVisible(true);  // sale
       add_company_btn.setVisible(true);
       productntype_btn.setVisible(true);
       jLabel40.setVisible(false);
            jLabel100.setVisible(false);
            jLabel148.setVisible(false);
            jLabel149.setVisible(false);
        if(LoginPanel.USER_NAME.equals("admin")||LoginPanel.USER_NAME.equals("mujeeb")){
            manage_user_btn.setVisible(true);
            purchase_invoice_delete_btn.setVisible(true);
            jButton30.setVisible(true);
            jLabel40.setVisible(true);
            jLabel100.setVisible(true);
            jLabel148.setVisible(true);
            jLabel149.setVisible(true);
            
        }
    try{ ps=con.prepareStatement("SELECT * FROM PERMISSION_TABLE WHERE PMT_USER_ID=(SELECT USER_ID FROM LOGIN_TABLE WHERE USER_NAME='"+LoginPanel.USER_NAME+"')");
         rs=ps.executeQuery();
         while(rs.next()){
             System.out.println(rs.getBoolean("PMT_ALLOWED")+"\t"+rs.getString("PMT_CONTROL_NAME")+"\t"+rs.getString("PMT_CONTROL_NAME").equals("purchase_order"));
             if(!rs.getBoolean("PMT_ALLOWED") && rs.getString("PMT_CONTROL_NAME").equals("purchase_order"))
                 purchase_btn.setEnabled(false);
            
                
             
              if(!rs.getBoolean("PMT_ALLOWED") && rs.getString("PMT_CONTROL_NAME").equals("sale_to_customer"))
                 sale_btn.setEnabled(false);
             
                
              
               if(!rs.getBoolean("PMT_ALLOWED") && rs.getString("PMT_CONTROL_NAME").equals("reports_panel"))
                 report_btn.setEnabled(false);
             
                 
               
                if(!rs.getBoolean("PMT_ALLOWED") && rs.getString("PMT_CONTROL_NAME").equals("sales_return"))
                 sales_rtn_btn.setEnabled(false);
                
                 
                
                 if(!rs.getBoolean("PMT_ALLOWED") && rs.getString("PMT_CONTROL_NAME").equals("party_management_panel"))
                 party_mng_btn.setEnabled(false);
                 
                 if(!rs.getBoolean("PMT_ALLOWED") && rs.getString("PMT_CONTROL_NAME").equals("purchase_update"))
                 jButton50.setEnabled(false);
                 
                 if(!rs.getBoolean("PMT_ALLOWED") && rs.getString("PMT_CONTROL_NAME").equals("sale_update"))
                 jButton2.setEnabled(false);
                 
                 if(!rs.getBoolean("PMT_ALLOWED") && rs.getString("PMT_CONTROL_NAME").equals("add_company"))
                 add_company_btn.setEnabled(false);
                 
                 if(!rs.getBoolean("PMT_ALLOWED") && rs.getString("PMT_CONTROL_NAME").equals("add_product"))
                 productntype_btn.setEnabled(false);
              
                
              
                 
             
         }
        
    }catch(Exception e){e.printStackTrace();}
}
private void validate_purchase_cart_insert(){
    
    if(!jTextField19.getText().isEmpty())
     if(jComboBox10.getSelectedIndex()!=0)
        if(jComboBox11.getSelectedIndex()!=0)
        if(!jTextField37.getText().isEmpty())
        if(!jTextField38.getText().isEmpty())
        if(!jTextField39.getText().isEmpty())
        if(!jTextField40.getText().isEmpty())
        if(!jTextField41.getText().isEmpty())
        if(!jTextField43.getText().isEmpty())
        if(!purchase_hsn_code.getText().isEmpty())
        if(!jTextField46.getText().isEmpty())
        if(!jTextField47.getText().isEmpty()){
         purchase_cart_insert();
        }
        else
         JOptionPane.showMessageDialog(null, "Enter Discount !", "Error", 0);
    else
         JOptionPane.showMessageDialog(null, "Enter GST Percent !", "Error", 0);
    else
         JOptionPane.showMessageDialog(null, "Enter HSN Code !", "Error", 0);
    else
         JOptionPane.showMessageDialog(null, "Enter Pack Description !", "Error", 0);
    else
         JOptionPane.showMessageDialog(null, "Enter MRP !", "Error", 0);
    else
         JOptionPane.showMessageDialog(null, "Enter Rate !", "Error", 0);
    else
         JOptionPane.showMessageDialog(null, "Enter Free Quantity !", "Error", 0);
    else
         JOptionPane.showMessageDialog(null, "Enter Quantity !", "Error", 0);
    else
         JOptionPane.showMessageDialog(null, "Enter Batch ID !", "Error", 0);
    else
         JOptionPane.showMessageDialog(null, "Enter Product Name !", "Error", 0);
    else
         JOptionPane.showMessageDialog(null, "Enter Manufacturer/Company Name !", "Error", 0);
    else
         JOptionPane.showMessageDialog(null, "Enter Invoice number !", "Error", 0);
}
private void validate_purchase_cart_update(){
    
    if(!jTextField19.getText().isEmpty())
     if(jComboBox10.getSelectedIndex()!=0)
        if(jComboBox11.getSelectedIndex()!=0)
        if(!jTextField37.getText().isEmpty())
        if(!jTextField38.getText().isEmpty())
        if(!jTextField39.getText().isEmpty())
        if(!jTextField40.getText().isEmpty())
        if(!jTextField41.getText().isEmpty())
        if(!jTextField43.getText().isEmpty())
        if(!purchase_hsn_code.getText().isEmpty())
        if(!jTextField46.getText().isEmpty())
        if(!jTextField47.getText().isEmpty()){
         purchase_cart_update();
        }
         else
         JOptionPane.showMessageDialog(null, "Enter Discount !", "Error", 0);
    else
         JOptionPane.showMessageDialog(null, "Enter GST Percent !", "Error", 0);
    else
         JOptionPane.showMessageDialog(null, "Enter HSN Code !", "Error", 0);
    else
         JOptionPane.showMessageDialog(null, "Enter Pack Description !", "Error", 0);
    else
         JOptionPane.showMessageDialog(null, "Enter MRP !", "Error", 0);
    else
         JOptionPane.showMessageDialog(null, "Enter Rate !", "Error", 0);
    else
         JOptionPane.showMessageDialog(null, "Enter Free Quantity !", "Error", 0);
    else
         JOptionPane.showMessageDialog(null, "Enter Quantity !", "Error", 0);
    else
         JOptionPane.showMessageDialog(null, "Enter Batch ID !", "Error", 0);
    else
         JOptionPane.showMessageDialog(null, "Enter Product Name !", "Error", 0);
    else
         JOptionPane.showMessageDialog(null, "Enter Manufacturer/Company Name !", "Error", 0);
    else
         JOptionPane.showMessageDialog(null, "Enter Invoice number !", "Error", 0);
}
private void validate_submit_into_purchase_order(){
    if(!pur_expence_name_txt.getText().isEmpty())
        if(!pur_expence_amount_txt.getText().isEmpty())
            if(!purchase_insurance_name.getText().isEmpty())
                            if(!purchase_insurance_charge.getText().isEmpty())
                                if(!purchase_insurance_charge.getText().isEmpty()){
                                   submit_into_purchase_order(); 
                                }
                                 else
                                    JOptionPane.showMessageDialog(null, "Enter User Name !");
                            else
                                JOptionPane.showMessageDialog(null, "Enter Insurance Charge !");
            else
                JOptionPane.showMessageDialog(null, "Enter Insurance Name !");                        
        else
            JOptionPane.showMessageDialog(null, "Enter Expence Amount !");    
    else
        JOptionPane.showMessageDialog(null, "Enter Expence Name !");
}
private void validate_sale_cart_insert(){
    if(!jTextField17.getText().isEmpty())
        if(MC_ID!=0)
        if(P_ID!=0)
        if(jComboBox20.getSelectedIndex()!=0)
        if(!jTextField67.getText().isEmpty())
         if(!jTextField35.getText().isEmpty())
         if(!jTextField36.getText().isEmpty())
         if(!jTextField22.getText().isEmpty())
        if(!jTextField17.getText().isEmpty()){
            sale_cart_insert();
        }else
            JOptionPane.showMessageDialog(null, "Fill GST !", "Error", 0);
    else
            JOptionPane.showMessageDialog(null, "Enter Rate ! ", "Error", 0);
    else
            JOptionPane.showMessageDialog(null, "Enter Free Quantity ! ", "Error", 0);
    else
            JOptionPane.showMessageDialog(null, "Enter Quantity Sold ! ", "Error", 0);
    else
            JOptionPane.showMessageDialog(null, "Enter HSN/SAC ! ", "Error", 0);
    else
            JOptionPane.showMessageDialog(null, "Enter BATCH ID ! ", "Error", 0);
    else
            JOptionPane.showMessageDialog(null, "Enter Product Name Properly ! ", "Error", 0);
    else
            JOptionPane.showMessageDialog(null, "Enter Company Name ! ", "Error", 0);
    else
            JOptionPane.showMessageDialog(null, "Enter Invoice No ! ", "Error", 0);
    
}
private void validate_sale_cart_update(){
    if(!jTextField17.getText().isEmpty())
        if(MC_ID!=0)
        if(P_ID!=0)
        if(jComboBox20.getSelectedIndex()!=0)
        if(!jTextField67.getText().isEmpty())
         if(!jTextField35.getText().isEmpty())
         if(!jTextField36.getText().isEmpty())
         if(!jTextField22.getText().isEmpty())
        if(!jTextField17.getText().isEmpty()){
            sale_cart_update();
        }else
            JOptionPane.showMessageDialog(null, "Fill GST !", "Error", 0);
    else
            JOptionPane.showMessageDialog(null, "Enter Rate ! ", "Error", 0);
    else
            JOptionPane.showMessageDialog(null, "Enter Free Quantity ! ", "Error", 0);
    else
            JOptionPane.showMessageDialog(null, "Enter Quantity Sold ! ", "Error", 0);
    else
            JOptionPane.showMessageDialog(null, "Enter HSN/SAC ! ", "Error", 0);
    else
            JOptionPane.showMessageDialog(null, "Enter BATCH ID ! ", "Error", 0);
    else
            JOptionPane.showMessageDialog(null, "Enter Product Name Properly ! ", "Error", 0);
    else
            JOptionPane.showMessageDialog(null, "Enter Company Name ! ", "Error", 0);
    else
            JOptionPane.showMessageDialog(null, "Enter Invoice No ! ", "Error", 0);
    
}
private void invoice_statement(){
     if(jComboBox18.getSelectedIndex()!=0){
            createFolder("INVOICE_STATEMENT");
            String fileName=path+"INVOICE_STATEMENT\\INVOICE_STATE_"+new SimpleDateFormat("dd-MM-yyyy").format(new Date())+".xls";
            new CreateExcelFile(con).createFile("SELECT A.SPA_INVOICE_NO AS INVOICE_NO,A.MC_NAME,A.SPA_INVOICE_DATE AS INVOICE_DATE,B.TOT_QTY AS TOTAL_QTY,A.SPA_PAID_AMOUNT AS TOTAL_PAID_AMOUNT,A.SPA_REMAINING_BAL AS UNPAID_AMOUNT FROM (SELECT SPA_INVOICE_NO,MC_NAME,SPA_INVOICE_DATE,SPA_PAID_AMOUNT,SPA_REMAINING_BAL FROM SALEPURCHASE_ACCOUNT INNER JOIN MANUFACTURER_CUSTOMER ON SPA_MC_ID=MC_ID AND MC_NAME='"+jComboBox18.getSelectedItem().toString()+"') AS A,(SELECT SO_INVOICE_NO,SO_INVOICE_DATE,SUM(SO_QTY) AS TOT_QTY FROM SALES_ORDER  GROUP BY SO_INVOICE_NO,SO_INVOICE_DATE) AS B WHERE A.SPA_INVOICE_NO=B.SO_INVOICE_NO AND A.SPA_INVOICE_DATE=B.SO_INVOICE_DATE", "", fileName, "INVOICE_STATE");
            new CreateExcelFile(con).updateExcel(fileName, "SELECT 'Total ','','',SUM(TOTAL_QTY),SUM(TOTAL_PAID_AMOUNT),SUM(UNPAID_AMOUNT) FROM (SELECT A.SPA_INVOICE_NO AS INVOICE_NO,A.SPA_INVOICE_DATE AS INVOICE_DATE,B.TOT_QTY AS TOTAL_QTY,A.SPA_PAID_AMOUNT AS TOTAL_PAID_AMOUNT,A.SPA_REMAINING_BAL AS UNPAID_AMOUNT FROM (SELECT SPA_INVOICE_NO,MC_NAME,SPA_INVOICE_DATE,SPA_PAID_AMOUNT,SPA_REMAINING_BAL FROM SALEPURCHASE_ACCOUNT INNER JOIN MANUFACTURER_CUSTOMER ON SPA_MC_ID=MC_ID AND MC_NAME='"+jComboBox18.getSelectedItem().toString()+"') AS A,(SELECT SO_INVOICE_NO,SO_INVOICE_DATE,SUM(SO_QTY) AS TOT_QTY FROM SALES_ORDER  GROUP BY SO_INVOICE_NO,SO_INVOICE_DATE) AS B WHERE A.SPA_INVOICE_NO=B.SO_INVOICE_NO AND A.SPA_INVOICE_DATE=B.SO_INVOICE_DATE) AS C", "INVOICE_STATE");
        }else
            JOptionPane.showMessageDialog(null, "Select Party Name !");
}
private void party_due_balance_sheet(){
     if(jDateChooser10.getDate()!=null && jDateChooser11.getDate()!=null){
            createFolder("LEETY DUE BALANCE SHEET");
            String fileName=path+"LEETY DUE BALANCE SHEET\\BALANCE_SHEET_"+new SimpleDateFormat("dd-MM-yyyy").format(new Date())+".xls";
            new CreateExcelFile(con).createFile("SELECT MC_NAME,COUNT(SPA_INVOICE_NO) AS TOTAL_INVOICES,SUM(SPA_SALE_PURCHASE_AMOUNT) AS TOTAL_PURCHASED,SUM(SPA_SALE_PURCHASE_AMOUNT)-SUM(SPA_PAID_AMOUNT) AS BALANCE FROM SALEPURCHASE_ACCOUNT INNER JOIN MANUFACTURER_CUSTOMER ON SPA_MC_ID=MC_ID AND MC_NAME IN(SELECT DISTINCT MC_NAME FROM SALES_ORDER) AND SPA_INVOICE_DATE BETWEEN '"+new SimpleDateFormat("yyyy-MM-dd").format(jDateChooser10.getDate())+"' AND '"+new SimpleDateFormat("yyyy-MM-dd").format(jDateChooser11.getDate())+"' GROUP BY MC_NAME ", "", fileName, "BALANCE_SHEET");
            new CreateExcelFile(con).updateExcel(fileName, "SELECT 'TOTAL ',SUM(A.TOTAL_INVOICES),SUM(A.TOTAL_PURCHASED),SUM(A.BALANCE) FROM (SELECT MC_NAME,COUNT(SPA_INVOICE_NO) AS TOTAL_INVOICES,SUM(SPA_SALE_PURCHASE_AMOUNT) AS TOTAL_PURCHASED,SUM(SPA_SALE_PURCHASE_AMOUNT)-SUM(SPA_PAID_AMOUNT) AS BALANCE  FROM SALEPURCHASE_ACCOUNT INNER JOIN MANUFACTURER_CUSTOMER ON SPA_MC_ID=MC_ID AND MC_NAME IN(SELECT DISTINCT MC_NAME FROM SALES_ORDER) AND SPA_INVOICE_DATE BETWEEN '"+new SimpleDateFormat("yyyy-MM-dd").format(jDateChooser10.getDate())+"' AND '"+new SimpleDateFormat("yyyy-MM-dd").format(jDateChooser11.getDate())+"' GROUP BY MC_NAME ) AS A", "BALANCE_SHEET");
        }
        else
            JOptionPane.showMessageDialog(null, "Select Date Range !");
}

 protected void consumeAlpha(java.awt.event.KeyEvent evt){
            char c = evt.getKeyChar();
             if (!Character.isDigit(c)) {
                evt.consume();
            } 
    }
}
