import javax.swing.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CreateFixedDiscount extends JFrame {
    private JPanel createDiscountPanel;
    private JCheckBox fixedCheckBox;
    private JCheckBox flexibleCheckBox;
    private JCheckBox variableCheckBox;
    private JLabel discount_typeLabel;
    private JTextField rateTextbox;
    private JLabel rateLabel;
    private JButton createDiscountButton;
    private JComboBox customerIDComboBox;
    private JLabel customerIDLabel;
    private JButton applyToCustomerButton;

    public static boolean fixed = false;

    String fixed_id;
    String flexible_id;
    String variable_id;


    public CreateFixedDiscount(String title) {
        super(title);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(createDiscountPanel);
        this.pack();

        customerIDComboBox.addItem("");



        createDiscountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                    Connection con = DbConnection.connect();
                    //PreparedStatement ps = null;
                    //ResultSet rs = null;

                    String get_FixedRateCount = "SELECT count(discount_id) FROM Fixed_Discount";
                    String get_FixedRate = "SELECT discount_rate FROM Fixed_Discount";
                    String insertFixedDiscount = "INSERT INTO Fixed_Discount(discount_id,discount_rate) VALUES(?,?)";


                    try (PreparedStatement ps_get_FixedRateCount = con.prepareStatement(get_FixedRateCount);
                         PreparedStatement ps_get_FixedRate = con.prepareStatement(get_FixedRate);
                         PreparedStatement ps_insertFixedDiscount = con.prepareStatement(insertFixedDiscount) )
                    {

                        con.setAutoCommit(false);

                        ResultSet rs_get_FixedRateCount = ps_get_FixedRateCount.executeQuery();
                        int fixed_count = rs_get_FixedRateCount.getInt(1);
                        fixed_id = ("Fi" + (fixed_count + 1));


                        //ps = con.prepareStatement(selectFixedRate);
                        ResultSet rs_get_FixedRate = ps_get_FixedRate.executeQuery();
                        ps_get_FixedRate.executeQuery();


                        boolean exists = false;
                        while (rs_get_FixedRate.next()) {
                            exists = false;
                            if (rs_get_FixedRate.getString(1).equals(rateTextbox.getText())) {
                                JOptionPane.showMessageDialog(createDiscountPanel, "Discount already exists.");
                                rateTextbox.setText("");
                                exists = true;
                            }
                        }

                        if (!exists) {
                            ps_insertFixedDiscount.setString(1, fixed_id);
                            ps_insertFixedDiscount.setString(2, rateTextbox.getText());
                            ps_insertFixedDiscount.executeUpdate();
                            fixed = true;
                            JOptionPane.showMessageDialog(createDiscountPanel, "Discount created.");
                        }

                        con.commit();


                    } catch (SQLException ex) {
                        //System.out.println(ex.toString());
                        JOptionPane.showMessageDialog(createDiscountPanel, "Failed to create discount.");
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


        createDiscountPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);




                    Connection con = DbConnection.connect();

                    String get_valued_customerID = "SELECT Customeraccount_no FROM Valued_Customer";

                    try (PreparedStatement ps_get_valued_customerID = con.prepareStatement(get_valued_customerID)) {

                        //con.setAutoCommit(false);

                        ResultSet rs_get_valued_customerID = ps_get_valued_customerID.executeQuery();

                        if (customerIDComboBox.getItemCount() == 1) {
                            while (rs_get_valued_customerID.next()) {
                                customerIDComboBox.addItem(rs_get_valued_customerID.getString(1));
                            }
                        }


                    } catch (SQLException ex) {
                        //System.out.println(ex.toString());
                        //JOptionPane.showMessageDialog(createFlexibleDiscountPanel, "Login Details Invalid");
                    } finally {
                        try {
                            //rs.close();
                            //ps.close();
                            con.close();
                        } catch (SQLException ex) {
                            System.out.println(ex.toString());
                        }
                    }






            }
        });


        applyToCustomerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                if (customerIDComboBox.getSelectedItem().toString() != "") {


                    Connection con = DbConnection.connect();
                    //PreparedStatement ps = null;
                    //ResultSet rs = null;

                    String get_FixedRateCount = "SELECT count(discount_id) FROM Fixed_Discount";
                    String get_FixedRate = "SELECT discount_rate FROM Fixed_Discount";
                    String insertFixedDiscount = "INSERT INTO Fixed_Discount(discount_id,discount_rate) VALUES(?,?)";

                    String get_dID = "SELECT discount_id FROM Fixed_Discount WHERE discount_rate=?";
                    String insert_fixed = "UPDATE Valued_Customer SET Fixed_Discountdiscount_id=? WHERE Customeraccount_no=?";

                    try (PreparedStatement ps_get_FixedRateCount = con.prepareStatement(get_FixedRateCount);
                         PreparedStatement ps_get_FixedRate = con.prepareStatement(get_FixedRate);
                         PreparedStatement ps_insertFixedDiscount = con.prepareStatement(insertFixedDiscount);
                         PreparedStatement ps_get_dID = con.prepareStatement(get_dID);
                         PreparedStatement ps_insert_fixed = con.prepareStatement(insert_fixed)) {

                        con.setAutoCommit(false);

                        ResultSet rs_get_FixedRateCount = ps_get_FixedRateCount.executeQuery();
                        int fixed_count = rs_get_FixedRateCount.getInt(1);
                        fixed_id = ("Fi" + (fixed_count + 1));


                        //ps = con.prepareStatement(selectFixedRate);
                        ResultSet rs_get_FixedRate = ps_get_FixedRate.executeQuery();
                        ps_get_FixedRate.executeQuery();


                        boolean exists = false;
                        while (rs_get_FixedRate.next()) {
                            //exists = false;
                            if (rs_get_FixedRate.getString(1).equals(rateTextbox.getText())) {
                                //JOptionPane.showMessageDialog(createDiscountPanel, "Discount already exists.");
                                ps_get_dID.setString(1, rateTextbox.getText());
                                ResultSet rs_get_dID = ps_get_dID.executeQuery();
                                fixed_id = rs_get_dID.getString(1);

                                ps_insert_fixed.setString(1, fixed_id);
                                ps_insert_fixed.setString(2, customerIDComboBox.getSelectedItem().toString());
                                ps_insert_fixed.executeUpdate();
                                JOptionPane.showMessageDialog(createDiscountPanel, "Discount applied.");
                                //fixed = true;
                                //CreateFlexibleDiscount.flexible = false;
                                //CreateVariableDiscount.variable = false;
                                exists = true;
                            }
                        }

                        if (!exists) {
                            ps_insertFixedDiscount.setString(1, fixed_id);
                            ps_insertFixedDiscount.setString(2, rateTextbox.getText());
                            ps_insertFixedDiscount.executeUpdate();
                            ps_insert_fixed.setString(1, fixed_id);
                            ps_insert_fixed.setString(2, customerIDComboBox.getSelectedItem().toString());
                            ps_insert_fixed.executeUpdate();
                            //fixed = true;
                            //CreateFlexibleDiscount.flexible = false;
                            //CreateVariableDiscount.variable = false;
                            JOptionPane.showMessageDialog(createDiscountPanel, "Discount applied.");
                        }

                        con.commit();


                    } catch (SQLException ex) {
                        //System.out.println(ex.toString());
                        JOptionPane.showMessageDialog(createDiscountPanel, "Failed to create discount.");
                    } finally {
                        try {
                            //rs.close();
                            //ps.close();
                            con.close();
                        } catch (SQLException ex) {
                            System.out.println(ex.toString());
                        }
                    }


                } else {
                    JOptionPane.showMessageDialog(createDiscountPanel, "Please select customer ID.");
                }


            }
        });
    }



    public static void main(String[] args){
        JFrame frame = new CreateFixedDiscount("Create Discount");
        frame.setVisible(true);
    }


}
