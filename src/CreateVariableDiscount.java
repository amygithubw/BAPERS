import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CreateVariableDiscount extends JFrame {
    private JPanel createVariableDiscountPanel;
    private JLabel discountRateLabel;
    private JTextField rateTextbox;
    private JButton OKButton;
    private JLabel taskLabel;
    private JButton restartButton;
    private JButton applyToCustomerButton;
    private JComboBox customerIDComboBox;
    private JLabel customerIDLabel;
    private JButton createCustomerButton;

    public static boolean variable = false;



    int i = 2;
    int no_of_tasks;
    static List<String> list_of_rates = new ArrayList<String>();
    //String [] list_of_rates;
    boolean buttonClicked = false;
    boolean completed = false;
    boolean exists = false;
    String variable_id;
    String job_id;


    public CreateVariableDiscount(String title) {
        super(title);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(createVariableDiscountPanel);
        this.pack();

        customerIDComboBox.addItem("");



        OKButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                buttonClicked = true;

                if (rateTextbox.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(createVariableDiscountPanel, "Please enter discount rate.");


                } else {

                    list_of_rates.add(rateTextbox.getText());
                    rateTextbox.setText("");

                    if (i <= no_of_tasks) {
                        taskLabel.setText("T" + i + ":");
                        i++;
                    } else {
                        completed = true;
                        rateTextbox.setEditable(false);
                        //OKButton.setText("Create Customer");
                        // if plan doesnt already exist, add to db
                        //System.out.println(list_of_rates.toString());
                    }



                }


            }
        });

        createVariableDiscountPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);



                Connection con = DbConnection.connect();

                String get_taskCount = "SELECT count(task_id) FROM StandardTask";
                String get_taskID = "SELECT task_id FROM StandardTask ORDER BY task_id";

                try (PreparedStatement ps_get_taskCount = con.prepareStatement(get_taskCount);
                     PreparedStatement ps_get_taskID = con.prepareStatement(get_taskID))

                {
                    con.setAutoCommit(false);

                    ResultSet rs_get_taskCount = ps_get_taskCount.executeQuery();
                    no_of_tasks = rs_get_taskCount.getInt(1);

                    ResultSet rs_get_taskID = ps_get_taskID.executeQuery();
                    if (!buttonClicked) {
                        taskLabel.setText(rs_get_taskID.getString(1) + ":");
                    }



                    con.commit();


                } catch(SQLException ex) {
                    //System.out.println(ex.toString());
                    //JOptionPane.showMessageDialog(createVariableDiscountPanel, "");
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





        createCustomerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                rateTextbox.setEditable(true);
                i = 2;
                taskLabel.setText("T"+(i-1)+":");

                if (completed) {

                    Connection con = DbConnection.connect();

                    String getVCount = "SELECT count(discount_id) FROM Variable_Discount";
                    String get_VariableRate = "SELECT discount_rate FROM Variable_Discount";
                    String insertVDiscount = "INSERT INTO Variable_Discount(discount_id,discount_rate) VALUES(?,?)";


                    try (PreparedStatement ps_get_VariableRate = con.prepareStatement(get_VariableRate);
                         PreparedStatement ps_insertVDiscount = con.prepareStatement(insertVDiscount);
                         PreparedStatement ps_getVCount = con.prepareStatement(getVCount) )

                    {

                        ResultSet rs_getVCount = ps_getVCount.executeQuery();
                        variable_id = ("V" + (rs_getVCount.getInt(1)+1));

                        ResultSet rs_get_VariableRate = ps_get_VariableRate.executeQuery();

                        boolean exists = false;
                        while (rs_get_VariableRate.next()){
                            exists = false;
                            if (rs_get_VariableRate.getString(1).equals(list_of_rates.toString())){
                                JOptionPane.showMessageDialog(createVariableDiscountPanel, "Discount already exists.");
                                exists = true;
                            }


                        } // while

                        if (!exists){
                            ps_insertVDiscount.setString(1, variable_id);
                            ps_insertVDiscount.setString(2, list_of_rates.toString());
                            ps_insertVDiscount.executeUpdate();
                            list_of_rates.clear();
                            variable = true;
                            JOptionPane.showMessageDialog(createVariableDiscountPanel, "Discount created.");
                        } // if


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
                    } // finally

                } else {
                    JOptionPane.showMessageDialog(createVariableDiscountPanel, "Please begin or complete discount rate plan.");
                }



            }
        });


        applyToCustomerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //System.out.println("clicked");

                rateTextbox.setEditable(true);
                i = 2;
                taskLabel.setText("T"+(i-1)+":");


                if (!customerIDComboBox.getSelectedItem().equals("")) {

                    if (completed) {

                        Connection con = DbConnection.connect();

                        String getVCount = "SELECT count(discount_id) FROM Variable_Discount";
                        String get_VID = "SELECT discount_id FROM Variable_Discount WHERE discount_rate=?";
                        String get_VariableRate = "SELECT discount_rate FROM Variable_Discount";
                        String insertVDiscount = "INSERT INTO Variable_Discount(discount_id,discount_rate,Valued_CustomerCustomeraccount_no) VALUES(?,?,?)";

                        String get_acc = "SELECT Valued_CustomerCustomeraccount_no FROM Variable_Discount WHERE discount_rate=?";
                        String insert_acc = "UPDATE Variable_Discount SET Valued_CustomerCustomeraccount_no=? WHERE discount_rate=?";


                        try (PreparedStatement ps_get_VariableRate = con.prepareStatement(get_VariableRate);
                             PreparedStatement ps_insertVDiscount = con.prepareStatement(insertVDiscount);
                             PreparedStatement ps_get_VID = con.prepareStatement(get_VID);
                             PreparedStatement ps_get_acc = con.prepareStatement(get_acc);
                             PreparedStatement ps_insert_acc = con.prepareStatement(insert_acc);
                             PreparedStatement ps_getVCount = con.prepareStatement(getVCount))
                        {

                            con.setAutoCommit(false);

                            ResultSet rs_get_VariableRate = ps_get_VariableRate.executeQuery();

                            ResultSet rs_getVCount = ps_getVCount.executeQuery();
                            variable_id = ("V" + (rs_getVCount.getInt(1)+1));

                            boolean exists = false;
                            while (rs_get_VariableRate.next()) {
                                exists = false;
                                if (rs_get_VariableRate.getString(1).equals(list_of_rates.toString())) {
                                    ps_get_acc.setString(1, list_of_rates.toString());
                                    ResultSet rs_get_acc = ps_get_acc.executeQuery();
                                    System.out.println("same rates");
                                    //ps_get_VID.setString(1, list_of_rates.toString());
                                    //ResultSet rs_get_VID = ps_get_VID.executeQuery();
                                    //variable_id = rs_get_VID.getString(1);
                                    exists = true;


                                    if (rs_get_acc.getString(1) == null){
                                        System.out.println("exists but null");
                                        ps_insert_acc.setString(1, customerIDComboBox.getSelectedItem().toString());
                                        ps_insert_acc.setString(2, list_of_rates.toString());
                                        ps_insert_acc.executeUpdate();
                                        list_of_rates.clear();
                                        //con.commit();
                                    } else {
                                        System.out.println("set id to existing id");
                                        ps_get_VID.setString(1, list_of_rates.toString());
                                        ResultSet rs_get_VID = ps_get_VID.executeQuery();
                                        variable_id = rs_get_VID.getString(1);
                                        //con.commit();
                                    }


                                }


                            } // while

                            if (!exists) {
                                System.out.println("add whole new row");
                                ps_insertVDiscount.setString(1, variable_id);
                                ps_insertVDiscount.setString(2, list_of_rates.toString());
                                ps_insertVDiscount.setString(3, customerIDComboBox.getSelectedItem().toString());
                                ps_insertVDiscount.executeUpdate();
                                list_of_rates.clear();
                                //con.commit();
                                //JOptionPane.showMessageDialog(createVariableDiscountPanel, "Discount created.");
                            } // if






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
                        } // finally




                        Connection con2 = DbConnection.connect();

                        String get_jobID = "SELECT job_id FROM Job WHERE Customeraccount_no=?";
                        String getVDiscount = "SELECT Variable_Discountdiscount_id FROM Job_StandardTask WHERE Jobjob_id=?";
                        String applyVDiscount = "UPDATE Job_StandardTask SET Variable_Discountdiscount_id=? WHERE Jobjob_id=?";

                        try (PreparedStatement ps_getVDiscount = con2.prepareStatement(getVDiscount);
                             PreparedStatement ps_applyVDiscount = con2.prepareStatement(applyVDiscount);
                             PreparedStatement ps_get_jobID = con2.prepareStatement(get_jobID) ) {

                            ps_get_jobID.setString(1, customerIDComboBox.getSelectedItem().toString());
                            ResultSet rs_get_jobID = ps_get_jobID.executeQuery();
                            while (rs_get_jobID.next()){

                                job_id = rs_get_jobID.getString(1);
                                System.out.println(job_id);
                                ps_getVDiscount.setString(1, job_id);
                                ResultSet rs_getVDiscount = ps_getVDiscount.executeQuery();
                                boolean v_exists = false;
                                while (rs_getVDiscount.next()){
                                    v_exists = false;
                                    if (rs_getVDiscount.getString(1) != null){
                                        int dialogButton = JOptionPane.YES_NO_OPTION;
                                        int dialogResult = JOptionPane.showConfirmDialog(null, "Variable discount already applied to customer account. Would you like to continue?", "Are you sure?",dialogButton);
                                        v_exists = true;
                                        if (dialogResult == JOptionPane.YES_OPTION){
                                            ps_applyVDiscount.setString(1, variable_id);
                                            ps_applyVDiscount.setString(2, job_id);
                                            ps_applyVDiscount.executeUpdate();
                                            list_of_rates.clear();
                                            variable = true;
                                            CreateFlexibleDiscount.flexible = false;
                                            CreateFixedDiscount.fixed = false;
                                        }


                                    } else {
                                        ps_applyVDiscount.setString(1, variable_id);
                                        ps_applyVDiscount.setString(2, job_id);
                                        ps_applyVDiscount.executeUpdate();
                                        list_of_rates.clear();
                                        variable = true;
                                        CreateFlexibleDiscount.flexible = false;
                                        CreateFixedDiscount.fixed = false;
                                    }



                                } // while



                            } // while



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
                        } // finally









                    } else {
                        JOptionPane.showMessageDialog(createVariableDiscountPanel, "Please begin or complete discount rate plan.");
                    }

                } else {
                    JOptionPane.showMessageDialog(createVariableDiscountPanel, "Please select customer ID.");
                }








            }
        });


        createVariableDiscountPanel.addMouseListener(new MouseAdapter() {
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

        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                rateTextbox.setEditable(true);
                i = 2;
                taskLabel.setText("T"+(i-1)+":");
                list_of_rates.clear();


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
    }



    public static void main(String[] args){
        JFrame frame = new CreateVariableDiscount("Create Variable Discount");
        frame.setVisible(true);
    }

}

