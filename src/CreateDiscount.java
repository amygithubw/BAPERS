import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CreateDiscount extends JFrame {
    private JPanel createDiscountPanel;
    private JCheckBox fixedCheckBox;
    private JCheckBox flexibleCheckBox;
    private JCheckBox variableCheckBox;
    private JLabel discount_typeLabel;
    private JTextField rateTextbox;
    private JLabel rateLabel;
    private JButton createDiscountButton;

    String fixed_id;
    String flexible_id;
    String variable_id;
    boolean exists = false;

    public CreateDiscount(String title) {
        super(title);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(createDiscountPanel);
        this.pack();



        fixedCheckBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {

                if (fixedCheckBox.isSelected()){
                    flexibleCheckBox.setSelected(false);
                    variableCheckBox.setSelected(false);
                }

            }
        });


        flexibleCheckBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {

                if (flexibleCheckBox.isSelected()){
                    fixedCheckBox.setSelected(false);
                    variableCheckBox.setSelected(false);
                }

            }
        });


        variableCheckBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {

                if (variableCheckBox.isSelected()){
                    fixedCheckBox.setSelected(false);
                    flexibleCheckBox.setSelected(false);
                }

            }
        });


        createDiscountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (!fixedCheckBox.isSelected() || !flexibleCheckBox.isSelected() || !variableCheckBox.isSelected()){
                    JOptionPane.showMessageDialog(createDiscountPanel, "Please select discount type.");
                }

                if (fixedCheckBox.isSelected() && (rateTextbox.getText().equals(""))){

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




                        while (rs_get_FixedRate.next()) {

                            if (rs_get_FixedRate.getString(1).equals(rateTextbox.getText())) {
                                JOptionPane.showMessageDialog(createDiscountPanel, "Discount already exists.");
                                exists = true;
                            }
                        }

                        if (!exists) {
                            ps_insertFixedDiscount.setString(1, fixed_id);
                            ps_insertFixedDiscount.setString(2, rateTextbox.getText());
                            ps_insertFixedDiscount.executeUpdate();
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

                if (flexibleCheckBox.isSelected() && (rateTextbox.getText().equals(""))){

                    Connection con = DbConnection.connect();
                    //PreparedStatement ps = null;
                    //ResultSet rs = null;

                    String get_FlexibleRateCount = "SELECT count(discount_id) FROM Flexible_Discount";
                    String get_FlexibleRate = "SELECT discount_rate FROM Flexible_Discount";
                    String insertFlexibleDiscount = "INSERT INTO Flexible_Discount(discount_id,discount_rate) VALUES(?,?)";


                    try (PreparedStatement ps_get_FlexibleRateCount = con.prepareStatement(get_FlexibleRateCount);
                         PreparedStatement ps_get_FlexibleRate = con.prepareStatement(get_FlexibleRate);
                         PreparedStatement ps_insertFlexibleDiscount = con.prepareStatement(insertFlexibleDiscount) )
                    {

                        con.setAutoCommit(false);

                        ResultSet rs_get_FlexibleRateCount = ps_get_FlexibleRateCount.executeQuery();
                        int flexible_count = rs_get_FlexibleRateCount.getInt(1);
                        flexible_id = ("Fl" + (flexible_count + 1));


                        //ps = con.prepareStatement(selectFixedRate);
                        ResultSet rs_get_FlexibleRate = ps_get_FlexibleRate.executeQuery();
                        ps_get_FlexibleRate.executeQuery();




                        while (rs_get_FlexibleRate.next()) {

                            if (rs_get_FlexibleRate.getString(1).equals(rateTextbox.getText())) {
                                JOptionPane.showMessageDialog(createDiscountPanel, "Discount already exists.");
                                exists = true;
                            }
                        }

                        if (!exists) {
                            ps_insertFlexibleDiscount.setString(1, flexible_id);
                            ps_insertFlexibleDiscount.setString(2, rateTextbox.getText());
                            ps_insertFlexibleDiscount.executeUpdate();
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
                if (variableCheckBox.isSelected() && (rateTextbox.getText().equals(""))){

                    Connection con = DbConnection.connect();
                    //PreparedStatement ps = null;
                    //ResultSet rs = null;

                    String get_VariableRateCount = "SELECT count(discount_id) FROM Variable_Discount";
                    String get_VariableRate = "SELECT discount_rate FROM Variable_Discount";
                    String insertVariableDiscount = "INSERT INTO Variable_Discount(discount_id,discount_rate) VALUES(?,?)";


                    try (PreparedStatement ps_get_VariableRateCount = con.prepareStatement(get_VariableRateCount);
                         PreparedStatement ps_get_VariableRate = con.prepareStatement(get_VariableRate);
                         PreparedStatement ps_insertVariableDiscount = con.prepareStatement(insertVariableDiscount) )
                    {

                        con.setAutoCommit(false);

                        ResultSet rs_get_VariableRateCount = ps_get_VariableRateCount.executeQuery();
                        int variable_count = rs_get_VariableRateCount.getInt(1);
                        variable_id = ("V" + (variable_count + 1));


                        //ps = con.prepareStatement(selectFixedRate);
                        ResultSet rs_get_VariableRate = ps_get_VariableRate.executeQuery();
                        ps_get_VariableRate.executeQuery();




                        while (rs_get_VariableRate.next()) {

                            if (rs_get_VariableRate.getString(1).equals(rateTextbox.getText())) {
                                JOptionPane.showMessageDialog(createDiscountPanel, "Discount already exists.");
                                exists = true;
                            }
                        }

                        if (!exists) {
                            ps_insertVariableDiscount.setString(1, variable_id);
                            ps_insertVariableDiscount.setString(2, rateTextbox.getText());
                            ps_insertVariableDiscount.executeUpdate();
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






            }
        });


    }



    public static void main(String[] args){
        JFrame frame = new CreateDiscount("Create Discount");
        frame.setVisible(true);
    }


}
