import javax.swing.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import javax.swing.DefaultListModel;
import javax.swing.JList;


public class TakePayment extends JFrame {
    private JPanel takePaymentPanel;
    private JTextField customerIDTextbox;
    private JComboBox jobIDComboBox;
    private JButton addJobButton;
    private JTextArea totalTextArea;
    private JButton cancelButton;
    private JLabel customerIDLabel;
    private JLabel jobIDLabel;
    private JLabel totalLabel;
    private JMenuBar takePaymentMenuBar;
    private JMenu logoutMenu;
    private JMenu homeMenu;
    private JButton saveButton;
    private JList paymentArea;
    private JButton removeSelectedButton;
    private JComboBox customerIDComboBox;
    private JCheckBox cashCheckBox;
    private JCheckBox cardCheckBox;
    private double tot;
    private static DecimalFormat df = new DecimalFormat("#.##");
    String payment_type;

    boolean isValued = false;





    public TakePayment(String title) {
        super(title);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(takePaymentPanel);


        this.pack();
        DefaultListModel<String> lm = new DefaultListModel<>();
        paymentArea.setModel(lm);

        customerIDComboBox.addItem("");
        jobIDComboBox.addItem("");



/*
        OKButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Connection con = DbConnection.connect();
                PreparedStatement ps = null;
                ResultSet rs = null;
                //String acc = customerIDTextbox.getText();
                String acc = customerIDComboBox.getSelectedItem().toString();
                totalTextArea.setText(null);
                lm.removeAllElements();



                //System.out.println(acc);

                try{
                    String sql = "SELECT Customeraccount_no, job_id FROM Job WHERE status =\"completed\" AND Paymentpayment_id IS NULL AND Customeraccount_no =?";
                    ps = con.prepareStatement(sql);
                    //ps.setString(1,customerIDTextbox.getText());
                    ps.setString(1, customerIDComboBox.getSelectedItem().toString());
                    rs = ps.executeQuery();

                    //System.out.println(rs.getString(1));


                    if (acc.equals(rs.getString(1))) {
                        jobIDComboBox.removeAllItems();
                        jobIDComboBox.addItem("");

                        if (jobIDComboBox.getItemCount() == 1) {
                            while (rs.next()) {
                                jobIDComboBox.addItem(rs.getString(2));
                            }
                        }
                    }

                } catch(SQLException ex) {
                    //System.out.println(ex.toString());
                    //customerIDTextbox.removeAll();
                    //customerIDComboBox.removeAllItems();
                    jobIDComboBox.removeAllItems();

                    JOptionPane.showMessageDialog(takePaymentPanel, " INVALID OR NO JOBS IN PROGRESS");
                } finally {
                    try {
                        rs.close();
                        ps.close();
                        con.close();
                    } catch(SQLException ex) {
                        System.out.println(ex.toString());
                    }
                }

            }
        });
        */






        addJobButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Connection con = DbConnection.connect();
                PreparedStatement ps = null;
                ResultSet rs = null;
                tot = 0;

                String s = jobIDComboBox.getSelectedItem().toString();


                try {
                    String is_valued = "SELECT Customeraccount_no FROM Valued_Customer";
                    ps = con.prepareStatement(is_valued);
                    rs = ps.executeQuery();
                    isValued = false;
                    while (rs.next()){
                        if (rs.getString(1).equals(customerIDComboBox.getSelectedItem().toString())){
                            isValued = true;
                        } // if
                    } // while

                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(takePaymentPanel, "Please select a task.");
                } finally {
                    try {
                        //rs.close();
                        //ps.close();
                        con.close();
                    } catch (SQLException ex) {
                        System.out.println(ex.toString());
                    }
                }
                System.out.println(lm.getSize());



                if (lm.size()==0) {


                            totalTextArea.setText(null);
                            lm.addElement(s);

                            try {
                                String sql = "SELECT sub_total FROM Job WHERE job_id =? ";


                                for (int i = 0; i < lm.getSize(); i++) {
                                    String x = lm.getElementAt(i);
                                    ps = con.prepareStatement(sql);
                                    ps.setString(1, x);
                                    rs = ps.executeQuery();
                                    tot += rs.getDouble(1);

                                }
                                totalTextArea.append("£" + df.format(tot));


                            } catch (SQLException ex) {
                                //JOptionPane.showMessageDialog(takePaymentPanel, "Please select a task.");
                            } finally {
                                try {
                                    rs.close();
                                    ps.close();
                                    con.close();
                                } catch (SQLException ex) {
                                    System.out.println(ex.toString());
                                }
                            }

                } else if (isValued) {
                        if (lm.contains(s)) {
                            JOptionPane.showMessageDialog(takePaymentPanel, "JOB ALREADY SELECTED");


                        } else {

                            totalTextArea.setText(null);
                            lm.addElement(s);

                            try {
                                String sql = "SELECT sub_total FROM Job WHERE job_id =? ";


                                for (int i = 0; i < lm.getSize(); i++) {
                                    String x = lm.getElementAt(i);
                                    ps = con.prepareStatement(sql);
                                    ps.setString(1, x);
                                    rs = ps.executeQuery();
                                    tot += rs.getDouble(1);

                                }
                                totalTextArea.append("£" + df.format(tot));


                            } catch (SQLException ex) {
                                //JOptionPane.showMessageDialog(takePaymentPanel, "Please select a task.");
                            } finally {
                                try {
                                    rs.close();
                                    ps.close();
                                    con.close();
                                } catch (SQLException ex) {
                                    System.out.println(ex.toString());
                                }
                            }


                        }
                } else {
                    JOptionPane.showMessageDialog(takePaymentPanel, "Customer not valued, please only selct 1 job.");
                }




            }

        });

        removeSelectedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = paymentArea.getSelectedIndex();
                Connection con = DbConnection.connect();
                PreparedStatement ps = null;
                ResultSet rs = null;


                if(tot > 0) {

                    try {
                        String sql = "SELECT sub_total FROM Job WHERE job_id =? ";


                        String x = lm.getElementAt(index);
                        ps = con.prepareStatement(sql);
                        ps.setString(1, x);
                        rs = ps.executeQuery();
                        tot -= rs.getDouble(1);
                        totalTextArea.setText(null);


                        totalTextArea.append("£" + df.format(tot));


                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(takePaymentPanel, "Please select a task.");
                    } finally {
                        try {

                            rs.close();
                            ps.close();
                            con.close();
                        } catch (SQLException ex) {
                            System.out.println(ex.toString());
                        }
                    }
                    lm.removeElementAt(index);

                }
            }


        });


        takePaymentPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);

                Connection con = DbConnection.connect();
                PreparedStatement ps = null;
                ResultSet rs = null;

                // displays account id's of customer in the Customer combo box
                try {

                    String sql = "SELECT account_no FROM Customer";
                    ps = con.prepareStatement(sql);
                    rs = ps.executeQuery();

                    if(customerIDComboBox.getItemCount() == 1) {
                        while (rs.next()) {

                            customerIDComboBox.addItem(rs.getString(1));
                        }
                    }

                } catch (
                        SQLException ex) {
                    System.out.println(ex.toString());
                    //JOptionPane.showMessageDialog(createJobPanel, "Please select task(s).");
                } finally {
                    try {
                        rs.close();
                        ps.close();
                        con.close();
                    } catch (SQLException ex) {
                        System.out.println(ex.toString());
                    }
                }

            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {



                if (cashCheckBox.isSelected()){
                    //cardCheckBox.setSelected(false);
                    //System.out.println("cash selected");
                    payment_type = "cash";
                } else if(cardCheckBox.isSelected()){
                    //cashCheckBox.setSelected(false);
                    //System.out.println("card selected");
                    payment_type = "card";
                }

                //String payment_id = "";

                Connection con = DbConnection.connect();

                String get_payment_id = "SELECT count(payment_id) FROM Payment";
                String insertPayment = "INSERT INTO Payment(payment_id, total_amount, payment_type, Customeraccount_no, Staffstaff_no) VALUES (?,?,?,?,?)";
                //String insertJobPaymentID = "UPDATE Job SET Paymentpayment_id = \"P3\" WHERE job_id = \"J3\"";
                //String insertJobPaymentID = "UPDATE Job SET Paymentpayment_id +"payment_id"+ WHERE job_id = +"jobIDComboBox.getSelectedItem()"+";
                String insertJobPaymentID = "UPDATE Job SET Paymentpayment_id =? WHERE job_id =?";
                //String insertJobPaymentID = "UPDATE Job SET Paymentpayment_id =? WHERE job_id =?";



                try ( PreparedStatement ps_get_payment_id = con.prepareStatement(get_payment_id);
                      PreparedStatement ps_insertPayment = con.prepareStatement(insertPayment);
                      PreparedStatement ps_insertJobPaymentID = con.prepareStatement(insertJobPaymentID) )

                {
                    con.setAutoCommit(false);

                    ResultSet rs_get_payment_id = ps_get_payment_id.executeQuery();
                    int payment_id_count = rs_get_payment_id.getInt(1);
                    String payment_id = ("P" + (payment_id_count + 1));

                    ps_insertPayment.setString(1, payment_id);
                    ps_insertPayment.setDouble(2, tot);
                    ps_insertPayment.setString(3, payment_type);
                    ps_insertPayment.setString(4, customerIDComboBox.getSelectedItem().toString());
                    ps_insertPayment.setString(5, "S2");
                    // ^^ this needs to be changed to the current user of the system!!!!!!!

                    ps_insertPayment.executeUpdate();

                    //System.out.println(jobIDComboBox.getSelectedItem().toString());

                    ps_insertJobPaymentID.setString(1, payment_id);
                    ps_insertJobPaymentID.setString(2, jobIDComboBox.getSelectedItem().toString());
                    ps_insertJobPaymentID.executeUpdate();

                    con.commit();




                } catch(SQLException ex) {
                    System.out.println(ex.toString());
                    //JOptionPane.showMessageDialog(createJobPanel, "Failed to create job.");
                } finally {
                    try {
                        //rs.close();
                        //ps.close();
                        con.close();
                    } catch(SQLException ex) {
                        System.out.println(ex.toString());
                    }
                }


            }
        });
        cashCheckBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (cashCheckBox.isSelected() == true); {
                    cardCheckBox.setSelected(false);
                    //cashCheckBox.setSelected(true);
                }
            }
        });

        cardCheckBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (cardCheckBox.isSelected() == true); {
                    cashCheckBox.setSelected(false);
                    //cardCheckBox.setSelected(true);
                }
            }
        });
        customerIDComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Connection con = DbConnection.connect();
                PreparedStatement ps = null;
                ResultSet rs = null;
                //String acc = customerIDTextbox.getText();
                String acc = customerIDComboBox.getSelectedItem().toString();
                totalTextArea.setText(null);
                lm.removeAllElements();



                //System.out.println(acc);

                try{
                    String sql = "SELECT Customeraccount_no, job_id FROM Job WHERE status =\"completed\" AND Paymentpayment_id IS NULL AND Customeraccount_no =?";
                    ps = con.prepareStatement(sql);
                    //ps.setString(1,customerIDTextbox.getText());
                    ps.setString(1, customerIDComboBox.getSelectedItem().toString());
                    rs = ps.executeQuery();

                    //System.out.println(rs.getString(1));


                    if (acc.equals(rs.getString(1))) {
                        jobIDComboBox.removeAllItems();
                        jobIDComboBox.addItem("");

                        if (jobIDComboBox.getItemCount() == 1) {
                            while (rs.next()) {
                                jobIDComboBox.addItem(rs.getString(2));
                            }
                        }
                    }

                } catch(SQLException ex) {
                    //System.out.println(ex.toString());
                    //customerIDTextbox.removeAll();
                    //customerIDComboBox.removeAllItems();
                    jobIDComboBox.removeAllItems();

                    JOptionPane.showMessageDialog(takePaymentPanel, " INVALID OR NO JOBS IN PROGRESS");
                } finally {
                    try {
                        rs.close();
                        ps.close();
                        con.close();
                    } catch(SQLException ex) {
                        System.out.println(ex.toString());
                    }
                }


            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // clear everything

            }
        });
    }






    public static void main(String[] args){
        JFrame frame = new TakePayment("Take Payment");
        frame.setVisible(true);
    }

}