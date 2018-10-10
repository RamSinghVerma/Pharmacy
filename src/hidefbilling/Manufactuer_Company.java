/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hidefbilling;

import static hidefbilling.MainFrame.con;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.LinkedList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author pc1
 */
public class Manufactuer_Company extends javax.swing.JDialog {

    private Connection con;
    private PreparedStatement ps;
    private ResultSet rs;
    protected static int STATE_ID;
    private int MC_ID;
    
    public Manufactuer_Company(java.awt.Frame parent, boolean modal, Connection con) {
        super(parent, modal);
        initComponents();
        this.con=con;
        reset_maufacturer_customer();
    }
    public Manufactuer_Company(){
        
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

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
        jLabel154 = new javax.swing.JLabel();
        jTextField21 = new javax.swing.JTextField();
        jLabel153 = new javax.swing.JLabel();
        jTextField18 = new javax.swing.JTextField();
        jLabel155 = new javax.swing.JLabel();
        jTextField44 = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

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
        jTextArea1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextArea1KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextArea1KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextArea1KeyTyped(evt);
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
                "Party Name", "Contact No", "GST No"
            }
        ));
        jTable9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable9MouseClicked(evt);
            }
        });
        jScrollPane12.setViewportView(jTable9);

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

        jLabel154.setText("COS. LIC No");

        jTextField21.setText("jTextField21");
        jTextField21.setNextFocusableComponent(jTextField18);

        jLabel153.setText("FSSAI");

        jTextField18.setText("jTextField18");
        jTextField18.setNextFocusableComponent(jTextField44);

        jLabel155.setText("PAN No");

        jTextField44.setText("jTextField44");
        jTextField44.setNextFocusableComponent(jComboBox17);

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jButton41, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel83, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel82, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel81, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel80, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel79, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel78, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel77, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel76, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 118, Short.MAX_VALUE)
                            .addComponent(jLabel96, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel15Layout.createSequentialGroup()
                                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTextField55)
                                    .addComponent(jTextField56)
                                    .addComponent(jTextField57)
                                    .addComponent(jScrollPane11, javax.swing.GroupLayout.DEFAULT_SIZE, 249, Short.MAX_VALUE)
                                    .addComponent(jTextField58)
                                    .addComponent(jTextField59)
                                    .addComponent(jTextField60)
                                    .addComponent(jTextField61))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel15Layout.createSequentialGroup()
                                .addComponent(jButton42, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButton43)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton49))
                            .addComponent(jComboBox17, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18))
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel154, javax.swing.GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE)
                            .addComponent(jLabel153, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel155, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextField21, javax.swing.GroupLayout.DEFAULT_SIZE, 251, Short.MAX_VALUE)
                            .addComponent(jTextField18)
                            .addComponent(jTextField44))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, 494, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addComponent(jLabel84, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jTextField62)))
                .addContainerGap())
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGap(28, 28, 28)
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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel96)
                            .addComponent(jComboBox17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(19, 19, 19)
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton41)
                            .addComponent(jButton42)
                            .addComponent(jButton43)
                            .addComponent(jButton49)))
                    .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, 387, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(32, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField57FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField57FocusLost
        // TODO add your handling code here:
        if(jTextArea1.getText().trim().isEmpty())
        jTextArea1.setText(null);
    }//GEN-LAST:event_jTextField57FocusLost

    private void jTextArea1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextArea1KeyReleased
        // TODO add your handling code here:
//        if(evt.getKeyCode()==KeyEvent.VK_TAB){
//            jTextField58.requestFocusInWindow();
//        }
    }//GEN-LAST:event_jTextArea1KeyReleased

    private void jButton41ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton41ActionPerformed
        // TODO add your handling code here:
        mc_insert();
    }//GEN-LAST:event_jButton41ActionPerformed

    private void jTextField62KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField62KeyReleased
        // TODO add your handling code here:
        new MainFrame(null).getTableData("SELECT MC_NAME, MC_CONTACT_NO, MC_GST_NO FROM MANUFACTURER_CUSTOMER WHERE MC_NAME LIKE '%"+jTextField62.getText()+"%'", jTable9);
    }//GEN-LAST:event_jTextField62KeyReleased

    private void jButton49ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton49ActionPerformed
        // TODO add your handling code here:
        reset_maufacturer_customer();
    }//GEN-LAST:event_jButton49ActionPerformed

    private void jComboBox17ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox17ItemStateChanged
        // TODO add your handling code here:
        STATE_ID=0;
        try{ ps=con.prepareStatement("SELECT STATE_NC_ID FROM STATE_NC WHERE STATE_NAME='"+jComboBox17.getSelectedItem().toString()+"'");
            rs=ps.executeQuery();
            if(rs.next())STATE_ID=rs.getInt(1);
        }catch(Exception e){e.printStackTrace();}
    }//GEN-LAST:event_jComboBox17ItemStateChanged

    private void jTextArea1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextArea1KeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode() == KeyEvent.VK_TAB) {
            System.out.println(evt.getModifiers());
//            if(evt.getModifiers() > 0) jTextField57.transferFocusBackward();
//            else jTextField58.transferFocus(); 
            jTextField58.requestFocusInWindow();
            evt.consume();
        }
    }//GEN-LAST:event_jTextArea1KeyPressed

    private void jTextArea1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextArea1KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextArea1KeyTyped

    private void jButton41KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton41KeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==KeyEvent.VK_ENTER)mc_insert();
    }//GEN-LAST:event_jButton41KeyPressed

    private void jButton42KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton42KeyPressed
        // TODO add your handling code here:
         if(evt.getKeyCode()==KeyEvent.VK_ENTER)mc_update();
    }//GEN-LAST:event_jButton42KeyPressed

    private void jButton42ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton42ActionPerformed
        // TODO add your handling code here:
        mc_update();
    }//GEN-LAST:event_jButton42ActionPerformed

    private void jButton43ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton43ActionPerformed
        // TODO add your handling code here:
        mc_remove();
    }//GEN-LAST:event_jButton43ActionPerformed

    private void jTable9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable9MouseClicked
        // TODO add your handling code here:
          MC_ID=0;
        try{ ps=con.prepareStatement("SELECT * FROM MANUFACTURER_CUSTOMER INNER JOIN STATE_NC ON MC_STATE_NC_ID=STATE_NC_ID WHERE MC_GST_NO='"+jTable9.getModel().getValueAt(jTable9.getSelectedRow(), 2).toString()+"'");
             rs=ps.executeQuery();
         if(rs.next()){MC_ID=rs.getInt("MC_ID");
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
         jComboBox17.setSelectedItem(rs.getString("STATE_NAME")); //MC_PAN_NO
         jButton42.setVisible(true);
         jButton43.setVisible(true);
         }
            
        }catch(Exception e){JOptionPane.showMessageDialog(null, e);}
    }//GEN-LAST:event_jTable9MouseClicked

    private void jButton43KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton43KeyPressed
        // TODO add your handling code here:
         if(evt.getKeyCode()==KeyEvent.VK_ENTER)mc_remove();
    }//GEN-LAST:event_jButton43KeyPressed

    private void jButton49KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton49KeyPressed
        // TODO add your handling code here:
         if(evt.getKeyCode()==KeyEvent.VK_ENTER)reset_maufacturer_customer();
    }//GEN-LAST:event_jButton49KeyPressed

    private void jTextField56KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField56KeyTyped
        // TODO add your handling code here:
      //  new MainFrame(null).consumeAlpha(evt);
    }//GEN-LAST:event_jTextField56KeyTyped

    /**
     * @param args the command line arguments
     */
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
            java.util.logging.Logger.getLogger(Manufactuer_Company.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Manufactuer_Company.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Manufactuer_Company.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Manufactuer_Company.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Manufactuer_Company dialog = new Manufactuer_Company(new javax.swing.JFrame(), true, new DBConnection().dbConnection("hidefDB"));
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton41;
    private javax.swing.JButton jButton42;
    private javax.swing.JButton jButton43;
    private javax.swing.JButton jButton49;
    private javax.swing.JComboBox jComboBox17;
    private javax.swing.JLabel jLabel153;
    private javax.swing.JLabel jLabel154;
    private javax.swing.JLabel jLabel155;
    private javax.swing.JLabel jLabel76;
    private javax.swing.JLabel jLabel77;
    private javax.swing.JLabel jLabel78;
    private javax.swing.JLabel jLabel79;
    private javax.swing.JLabel jLabel80;
    private javax.swing.JLabel jLabel81;
    private javax.swing.JLabel jLabel82;
    private javax.swing.JLabel jLabel83;
    private javax.swing.JLabel jLabel84;
    private javax.swing.JLabel jLabel96;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JTable jTable9;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField18;
    private javax.swing.JTextField jTextField21;
    private javax.swing.JTextField jTextField44;
    private javax.swing.JTextField jTextField55;
    private javax.swing.JTextField jTextField56;
    private javax.swing.JTextField jTextField57;
    private javax.swing.JTextField jTextField58;
    private javax.swing.JTextField jTextField59;
    private javax.swing.JTextField jTextField60;
    private javax.swing.JTextField jTextField61;
    private javax.swing.JTextField jTextField62;
    // End of variables declaration//GEN-END:variables
protected  void reset_maufacturer_customer(){
//    if(new MainFrame(null).jComboBox10.getSelectedIndex()!=0)
    jTextField55.setText(MainFrame.MC_NAME);
//    if(new MainFrame(null).jComboBox6.getSelectedIndex()!=0)
//    jTextField55.setText(new MainFrame(null).jComboBox6.getSelectedItem().toString());
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
    jTextField44.setText("");
    getTableData("SELECT MC_NAME, MC_CONTACT_NO, MC_GST_NO FROM MANUFACTURER_CUSTOMER", jTable9);
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
protected void mc_insert(){
    if(!jTextField55.getText().isEmpty())
        try{ ps=con.prepareStatement("INSERT INTO MANUFACTURER_CUSTOMER(MC_NAME,MC_CONTACT_NO,MC_EMAIL,MC_ADDRESS,MC_GST_NO,MC_LIC1,MC_LIC2,MC_DOC_LIC,MC_STATE_NC_ID,MC_COS_LIC,MC_FSSAI_CODE,MC_PAN_NO) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)");
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
           if(JOptionPane.showConfirmDialog (null, "Data Inserted, Do you want to Close ?","Warning",JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
                this.dispose();
                }
        }catch(Exception e){e.printStackTrace();}
}
protected void mc_update(){
    try{ MC_ID=0; 
        ps=con.prepareStatement("SELECT DISTINCT MC_ID FROM MANUFACTURER_CUSTOMER WHERE MC_NAME='"+jTextField55.getText()+"'");
        rs=ps.executeQuery();
        if(rs.next())
            MC_ID=rs.getInt(1);
         ps=con.prepareStatement("UPDATE MANUFACTURER_CUSTOMER SET MC_NAME=?,MC_CONTACT_NO=?, MC_EMAIL=?, MC_ADDRESS=?, MC_LIC1=?, MC_LIC2=?, MC_DOC_LIC=?, MC_STATE_NC_ID=? WHERE MC_ID="+MC_ID);
         ps.setString(1, jTextField55.getText());
         ps.setString(2, jTextField56.getText());
         ps.setString(3, jTextField57.getText());
         ps.setString(4, jTextArea1.getText());
         ps.setString(5, jTextField59.getText());
         ps.setString(6, jTextField60.getText());
         ps.setString(7, jTextField61.getText());
         ps.setInt(8, STATE_ID);
         ps.executeUpdate();
        reset_maufacturer_customer();
    }catch(Exception e){JOptionPane.showMessageDialog(null, e);}
}
protected void mc_remove(){
     try{ MC_ID=0; 
        ps=con.prepareStatement("SELECT DISTINCT MC_ID FROM MANUFACTURER_CUSTOMER WHERE MC_NAME='"+jTextField55.getText()+"'");
        rs=ps.executeQuery();
        if(rs.next())
            MC_ID=rs.getInt(1); 
             ps=con.prepareStatement("DELETE  FROM MANUFACTURER_CUSTOMER WHERE MC_ID="+MC_ID);
             ps.execute();
             reset_maufacturer_customer();
        }catch(Exception e){JOptionPane.showMessageDialog(null, e);}
}
}
