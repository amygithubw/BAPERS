import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CreateFlexibleDiscount extends JFrame {
    private JPanel createFlexibleDiscountPanel;
    private JTextField lessThan1000Textbox;
    private JTextField thousandToTwoThousandTextbox;
    private JTextField greaterThan2000Textbox;
    private JLabel flexibleDiscountLabel;
    private JLabel lessThan1000Label;
    private JLabel thousandToTwoThousandLabel;
    private JLabel greaterThan2000Label;
    private JButton createDiscountButton;
    private JButton applyToCustomerButton;
    private JComboBox customerIDComboBox;
    private JLabel customerIDLabel;

    public static boolean flexible = false;

    String fixed_id;
    String flexible_id;
    String variable_id;


    public CreateFlexibleDiscount(String title) {
        super(title);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(createFlexibleDiscountPanel);
        this.pack();

        customerIDComboBox.addItem("");


        createFlexibleDiscountPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);

                Connection con = DbConnection.connect();

                String get_valued_customerID = "SELECT Customeraccount_no FROM Valued_Customer";

                try (PreparedStatement ps_get_valued_customerID = con.prepareStatement(get_valued_customerID)) {

                    //con.setAutoCommit(false);

                    ResultSet rs_get_valued_customerID = ps_get_valued_customerID.executeQuery();

                    if(customerIDComboBox.getItemCount() == 1) {
                        while (rs_get_valued_customerID.next()) {
                            customerIDComboBox.addItem(rs_get_valued_customerID.getString(1));
                        }
                    }


                } catch(SQLException ex) {
                    //System.out.println(ex.toString());
                    //JOptionPane.showMessageDialog(createFlexibleDiscountPanel, "Login Details Invalid");
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


        createDiscountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                customerIDComboBox.setSelectedItem("");


                if (!lessThan1000Textbox.getText().equals("") && !thousandToTwoThousandTextbox.getText().equals("") && !greaterThan2000Textbox.getText().equals("")) {

                    String input_rates = (lessThan1000Textbox.getText()+","+thousandToTwoThousandTextbox.getText()+","+greaterThan2000Textbox.getText());

                    Connection con = DbConnection.connect();
                    //PreparedStatement ps = null;
                    //ResultSet rs = null;


                    String get_FlexibleRateCount = "SELECT count(discount_id) FROM Flexible_Discount";
                    String get_FlexibleRate = "SELECT discount_rate FROM Flexible_Discount";
                    String insertFlexibleDiscount = "INSERT INTO Flexible_Discount(discount_id,discount_rate) VALUES(?,?)";


                    try (PreparedStatement ps_get_FlexibleRateCount = con.prepareStatement(get_FlexibleRateCount);
                         PreparedStatement ps_get_FlexibleRate = con.prepareStatement(get_FlexibleRate);
                         PreparedStatement ps_insertFlexibleDiscount = con.prepareStatement(insertFlexibleDiscount)) {

                        con.setAutoCommit(false);

                        ResultSet rs_get_FlexibleRateCount = ps_get_FlexibleRateCount.executeQuery();
                        int flexible_count = rs_get_FlexibleRateCount.getInt(1);
                        flexible_id = ("Fl" + (flexible_count + 1));


                        //ps = con.prepareStatement(selectFixedRate);
                        ResultSet rs_get_FlexibleRate = ps_get_FlexibleRate.executeQuery();
                        ps_get_FlexibleRate.executeQuery();


                        boolean exists = false;
                        while (rs_get_FlexibleRate.next()) {
                            exists = false;
                            if (rs_get_FlexibleRate.getString(1).equals(input_rates)) {
                                JOptionPane.showMessageDialog(createFlexibleDiscountPanel, "Discount already exists.");
                                exists = true;
                            }


                        }

                        if (!exists) {
                            ps_insertFlexibleDiscount.setString(1, flexible_id);
                            ps_insertFlexibleDiscount.setString(2, input_rates);
                            ps_insertFlexibleDiscount.executeUpdate();
                            flexible = true;
                            JOptionPane.showMessageDialog(createFlexibleDiscountPanel, "Discount created.");
                        }


                        con.commit();


                    } catch (SQLException ex) {
                        //System.out.println(ex.toString());
                        JOptionPane.showMessageDialog(createFlexibleDiscountPanel, "Failed to create discount.");
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
                    JOptionPane.showMessageDialog(createFlexibleDiscountPanel, "Please enter all discount rates.");
                }



            }
        });

        applyToCustomerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (!customerIDComboBox.getSelectedItem().equals("")){

                    if (!lessThan1000Textbox.getText().equals("") && !thousandToTwoThousandTextbox.getText().equals("") && !greaterThan2000Textbox.getText().equals("")) {

                        String input_rates = (lessThan1000Textbox.getText()+","+thousandToTwoThousandTextbox.getText()+","+greaterThan2000Textbox.getText());

                        Connection con = DbConnection.connect();

                        //String get_FlexibleRateCount = "SELECT count(discount_id) FROM Flexible_Discount";
                        String get_FlexibleRate = "SELECT discount_rate FROM Flexible_Discount";
                        String insertFlexibleDiscount = "INSERT INTO Flexible_Discount(discount_id,discount_rate) VALUES(?,?)";
                        String get_flexibleID = "SELECT discount_id FROM Flexible_Discount WHERE discount_rate=?";



                        try (
                             PreparedStatement ps_get_FlexibleRate = con.prepareStatement(get_FlexibleRate);
                             PreparedStatement ps_insertFlexibleDiscount = con.prepareStatement(insertFlexibleDiscount);
                             PreparedStatement ps_get_flexibleID = con.prepareStatement(get_flexibleID) ) {

                            con.setAutoCommit(false);

                            ///ResultSet rs_get_FlexibleRateCount = ps_get_FlexibleRateCount.executeQuery();
                            ///int flexible_count = rs_get_FlexibleRateCount.getInt(1);
                            ///flexible_id = ("Fl" + (flexible_count + 1));


                            //ps = con.prepareStatement(selectFixedRate);
                            ResultSet rs_get_FlexibleRate = ps_get_FlexibleRate.executeQuery();
                            ps_get_FlexibleRate.executeQuery();


                            boolean exists = false;
                            while (rs_get_FlexibleRate.next()) {
                                exists = false;
                                if (rs_get_FlexibleRate.getString(1).equals(input_rates)) {
                                    ps_get_flexibleID.setString(1, input_rates);
                                    ResultSet rs_get_flexibleID = ps_get_flexibleID.executeQuery();
                                    flexible_id = rs_get_flexibleID.getString(1);
                                    //System.out.println(flexible_id);
                                    //JOptionPane.showMessageDialog(createFlexibleDiscountPanel, "Discount already exists.");
                                    exists = true;
                                }
                            }

                            if (!exists) {
                                ps_insertFlexibleDiscount.setString(1, flexible_id);
                                ps_insertFlexibleDiscount.setString(2, input_rates);
                                ps_insertFlexibleDiscount.executeUpdate();
                                //JOptionPane.showMessageDialog(createFlexibleDiscountPanel, "Discount created.");
                            }


                            con.commit();


                        } catch (SQLException ex) {
                            //System.out.println(ex.toString());
                            //JOptionPane.showMessageDialog(createFlexibleDiscountPanel, "Failed to create discount.");
                        } finally {
                            try {
                                //rs.close();
                                //ps.close();
                                con.close();
                            } catch (SQLException ex) {
                                System.out.println(ex.toString());
                            }
                        }




                        Connection con2 = DbConnection.connect();

                        String getFlexibleDiscount = "SELECT Flexible_Discountdiscount_id FROM Job WHERE Customeraccount_no=?";
                        String applyFlexibleDiscount = "UPDATE Job SET Flexible_Discountdiscount_id=? WHERE Customeraccount_no=?";

                        try (PreparedStatement ps_getFlexibleDiscount = con2.prepareStatement(getFlexibleDiscount);
                             PreparedStatement ps_applyFlexibleDiscount = con2.prepareStatement(applyFlexibleDiscount)) {

                            ps_getFlexibleDiscount.setString(1, customerIDComboBox.getSelectedItem().toString());
                            ResultSet rs_getFlexibleDiscount = ps_getFlexibleDiscount.executeQuery();
                            //System.out.println(customerIDComboBox.getSelectedItem().toString());


                            boolean flexible_exists = false;
                            while (rs_getFlexibleDiscount.next()) {
                                //System.out.println(rs_getFlexibleDiscount.getString(1));
                                flexible_exists = false;
                                //String get = rs_getFlexibleDiscount.getString(1);
                                //flexible_exists = rs_getFlexibleDiscount.wasNull();
                                if (rs_getFlexibleDiscount.getString(1) != null){
                                    //System.out.println("not null");
                                    int dialogButton = JOptionPane.YES_NO_OPTION;
                                    int dialogResult = JOptionPane.showConfirmDialog(null, "Flexible discount already applied to customer account. Would you like to continue?", "Are you sure?",dialogButton);
                                    flexible_exists = true;
                                    if (dialogResult == JOptionPane.YES_OPTION){

                                        ps_applyFlexibleDiscount.setString(1, flexible_id);
                                        ps_applyFlexibleDiscount.setString(2, customerIDComboBox.getSelectedItem().toString());
                                        ps_applyFlexibleDiscount.executeUpdate();
                                        flexible = true;
                                        CreateVariableDiscount.variable = false;
                                        CreateFixedDiscount.fixed = false;

                                    }
                                } else {
                                    //System.out.println("column = null");
                                    ps_applyFlexibleDiscount.setString(1, flexible_id);
                                    ps_applyFlexibleDiscount.setString(2, customerIDComboBox.getSelectedItem().toString());
                                    ps_applyFlexibleDiscount.executeUpdate();
                                    flexible = true;
                                    CreateVariableDiscount.variable = false;
                                    CreateFixedDiscount.fixed = false;

                                }
                            }


                        } catch (SQLException ex) {
                            //System.out.println(ex.toString());
                            //JOptionPane.showMessageDialog(createFlexibleDiscountPanel, "Failed to create discount.");
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
                        JOptionPane.showMessageDialog(createFlexibleDiscountPanel, "Please enter all discount rates.");
                    }



                } else{
                    JOptionPane.showMessageDialog(createFlexibleDiscountPanel, "Please select customer ID.");
                }

            }
        });
    }


    public static void main(String[] args){
        JFrame frame = new CreateFlexibleDiscount("Create Flexible Discount");
        frame.setVisible(true);
    }


}
