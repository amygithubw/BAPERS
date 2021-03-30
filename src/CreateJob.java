import javax.swing.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

public class CreateJob extends JFrame {


    private JPanel createJobPanel;
    private JComboBox selectCustomerComboBox;
    private JButton createCustomerButton;
    private JComboBox selectTaskComboBox;
    private JButton addTaskButton;

    private JTextField surchargeTextbox;
    private JTextArea subTotalTextArea;
    private JButton cancelButton;
    private JButton saveButton;
    private JMenuBar createJobMenuBar;
    private JLabel selectCustomerLabel;
    private JLabel selectTaskLabel;
    private JLabel urgencyLabel;
    private JLabel surchargeLabel;
    private JLabel subTotalLabel;
    private JMenu logoutMenu;
    private JMenu homeMenu;
    private JScrollPane scrollPane;
    private JFormattedTextField urgencyFormattedTextbox;
    private JButton OKButton;
    private JList taskList;
    private JButton removeSelectedButton;
    private JComboBox urgencyComboBox;
    private JTextField paymentDeadlineTextbox;
    private JLabel paymentDeadlineLabel;
    private JLabel formatLabel;
    private JTextField urgencyTextbox;
    private double tot;
    private static DecimalFormat df = new DecimalFormat("#.##");
    private String s;
    private int urgency = 0;
    private String account_no;
    private double surcharge;
    private double subtot;



    int flexible_discount = 0;
    int apply_flexible_discount = 0;
    int next_apply_flexible_discount = 0;

    public CreateJob(String title) {
        super(title);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(createJobPanel);
        this.pack();
        DefaultListModel<String> lm = new DefaultListModel<>();
        taskList.setModel(lm);
        for (int i = 1; i < 25; i++) {
            urgencyComboBox.addItem(i);
        }

        selectCustomerComboBox.addItem("");
        selectTaskComboBox.addItem("");



        createJobPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                Connection con = DbConnection.connect();
                PreparedStatement ps = null;
                ResultSet rs = null;

                // displays account id's of customer in the Customer combo box
                try {


                    String sql = "SELECT customer_name FROM Customer ";
                    ps = con.prepareStatement(sql);
                    rs = ps.executeQuery();

                    if(selectCustomerComboBox.getItemCount() == 1) {
                        while (rs.next()) {

                            selectCustomerComboBox.addItem(rs.getString(1));
                        }
                    }


                } catch (
                        SQLException ex) {
                    System.out.println(ex.toString());
                    //JOptionPane.showMessageDialog(createJobPanel, "Please select task(s).");
                }



                // displays tasks available in the Task combo box
                //Connection con2 = DbConnection.connect();
                try {

                    String sql = "SELECT task_description FROM StandardTask";
                    ps = con.prepareStatement(sql);
                    rs = ps.executeQuery();

                    if(selectTaskComboBox.getItemCount() == 1) {
                        while (rs.next()) {

                            selectTaskComboBox.addItem(rs.getString(1));
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
        selectCustomerComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // *********************************************
                // needs to be a global variable from the login page
                account_no ="";
                //  ^^^^^^ MAKE SURE THIS IS THE CURRENT USER!!!!!

                Connection con = DbConnection.connect();
                PreparedStatement ps = null;
                ResultSet rs = null;


                subTotalTextArea.setText(null);
                lm.removeAllElements();
                if(!selectCustomerComboBox.getSelectedItem().toString().equals("")) {
                    try {
                        String sql = "SELECT account_no  FROM Customer  WHERE Customer_name = ?;";
                        ps = con.prepareStatement(sql);
                        //ps.setString(1,customerIDTextbox.getText());
                        ps.setString(1, selectCustomerComboBox.getSelectedItem().toString());
                        rs = ps.executeQuery();
                        account_no = rs.getString(1);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }finally {
                        try {
                            rs.close();
                            ps.close();
                            con.close();
                        } catch (SQLException ex) {
                            System.out.println(ex.toString());
                        }

                    }

                }
                System.out.println(account_no);



            }
        });
        addTaskButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tot =0;


                String s = selectTaskComboBox.getSelectedItem().toString();
                Connection con = DbConnection.connect();
                PreparedStatement ps = null;
                ResultSet rs = null;


                if (!lm.contains(s) && !selectTaskComboBox.getSelectedItem().toString().equals("") && !selectCustomerComboBox.getSelectedItem().toString().equals("")) {
                    lm.addElement(s);


                    try {


                        String sql = "SELECT price FROM StandardTask WHERE task_description=?";

                        for (int i = 0; i < lm.getSize(); i++) {
                            String x = lm.getElementAt(i);
                            ps = con.prepareStatement(sql);
                            ps.setString(1, x);
                            rs = ps.executeQuery();
                            tot += rs.getDouble(1);


                        }
                        ////subTotalTextArea.append("Sub-Total: £" + sub_total);
                        //System.out.println(sub_total);

                        // ^^ doesnt work!!!!

                    } catch (SQLException ex) {

                    } finally {
                        try {
                            rs.close();
                            ps.close();
                            con.close();
                        } catch (SQLException ex) {
                            System.out.println(ex.toString());
                        }
                    }
                    subTotalTextArea.setText("£" + df.format(abs(tot)));

                }
                else if(lm.contains(s)){
                    JOptionPane.showMessageDialog(createJobPanel, "Task already selected");

                }
                else if(selectCustomerComboBox.getSelectedItem().toString() ==""){
                    JOptionPane.showMessageDialog(createJobPanel, "Please select a customer.");
                }
                else{
                    JOptionPane.showMessageDialog(createJobPanel, "Please select a task.");
                }

            }






            //tasksTextArea.setText(selectedTask);




        });
        selectTaskComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                surchargeTextbox.setText("");
                surcharge = 0;
                subTotalTextArea.setText("£" + df.format(abs(tot)));


                //tasksTextArea.setText(selected);


            }
        });



        OKButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                urgency = Integer.parseInt(urgencyComboBox.getSelectedItem().toString());
                if(tot > 0) {



                    Connection con = DbConnection.connect();

                    String get_valued_customers = "SELECT Customeraccount_no FROM Valued_Customer";
                    String get_fixedID = "SELECT Fixed_Discountdiscount_id FROM Valued_Customer WHERE Customeraccount_no=?";
                    String get_fixed_rate = "SELECT discount_rate FROM Fixed_Discount WHERE discount_id=?";

                    String get_flexible_jobs = "SELECT SUM(sub_total) FROM Job WHERE Customeraccount_no=? GROUP BY payment_deadline";
                    String get_flexibleID = "SELECT Flexible_Discountdiscount_id FROM Job WHERE Customeraccount_no=?";
                    String get_flexible_rate = "SELECT discount_rate FROM Flexible_Discount WHERE discount_id=?";

                    String get_v_ID = "SELECT Variable_Discountdiscount_id FROM Job_StandardTask WHERE Jobjob_id=?";
                    String get_job_id = "SELECT job_id FROM Job WHERE Customeraccount_no=?";
                    String get_v_rate = "SELECT discount_rate FROM Variable_Discount WHERE discount_id=?";

                    String get_task = "SELECT StandardTasktask_id FROM Job_StandardTask WHERE Jobjob_id=?";

                    try (PreparedStatement ps_get_valued_customers = con.prepareStatement(get_valued_customers);
                         PreparedStatement ps_get_fixedID = con.prepareStatement(get_fixedID);
                         PreparedStatement ps_get_fixed_rate = con.prepareStatement(get_fixed_rate);
                         PreparedStatement ps_get_flexible_jobs = con.prepareStatement(get_flexible_jobs);
                         PreparedStatement ps_get_flexible_rate = con.prepareStatement(get_flexible_rate);
                         PreparedStatement ps_get_flexibleID = con.prepareStatement(get_flexibleID);
                         PreparedStatement ps_get_job_id = con.prepareStatement(get_job_id);
                         PreparedStatement ps_get_v_ID = con.prepareStatement(get_v_ID);
                         PreparedStatement ps_get_v_rate = con.prepareStatement(get_v_rate);
                         PreparedStatement ps_get_task = con.prepareStatement(get_task) )
                    {
                        con.setAutoCommit(false);


                        ResultSet rs_get_valued_customers = ps_get_valued_customers.executeQuery();

                        //boolean valued = false;
                        while (rs_get_valued_customers.next()){
                            if (rs_get_valued_customers.getString(1).equals(account_no)){
                                //System.out.println("is valued");

                                Connection con2 = DbConnection.connect();
                                PreparedStatement ps = null;
                                ResultSet rs = null;
                                try {
                                    String fixed = "SELECT Fixed_Discountdiscount_id FROM Valued_Customer WHERE Customeraccount_no=?";

                                    ps.setString(1, account_no);
                                    ps = con2.prepareStatement(fixed);
                                    rs = ps.executeQuery();

                                    if (rs.getString(1) != null){


                                        ps_get_fixedID.setString(1, account_no);
                                        ResultSet rs_get_fixedID = ps_get_fixedID.executeQuery();

                                        ps_get_fixed_rate.setString(1, rs_get_fixedID.getString(1));
                                        ResultSet rs_get_fixed_rate = ps_get_fixed_rate.executeQuery();
                                        int fixed_discount = rs_get_fixed_rate.getInt(1);
                                        System.out.println(fixed_discount);

                                        //int d = tot*(fixed_discount/100);
                                        //int x = (tot * d);
                                        tot = (tot - (tot*(fixed_discount/100)));






                                    }

                                } catch (SQLException ex) {

                                } finally {
                                    try {
                                        //rs.close();
                                        //ps.close();
                                        con.close();
                                    } catch (SQLException ex) {
                                        System.out.println(ex.toString());
                                    }
                                }



                                try {
                                    String flexible = "SELECT Flexible_Discountdiscount_id FROM Job WHERE Customeraccount_no=?";

                                    ps.setString(1, account_no);
                                    ps = con2.prepareStatement(flexible);
                                    rs = ps.executeQuery();

                                    if (rs.getString(1) != null){



                                        ps_get_flexibleID.setString(1, account_no);
                                        ResultSet rs_get_flexibleID = ps_get_flexibleID.executeQuery();

                                        ps_get_flexible_rate.setString(1, rs_get_flexibleID.getString(1));
                                        ResultSet rs_get_flexible_rate = ps_get_flexible_rate.executeQuery();

                                        ps_get_flexible_jobs.setString(1, account_no);
                                        ResultSet rs_get_flexible_jobs = ps_get_flexible_jobs.executeQuery();

                                        if (rs_get_flexible_jobs.getInt(1) < 1000){
                                            int st = rs_get_flexible_jobs.getInt(1);
                                            int rate = rs_get_flexible_rate.getInt(1);

                                            flexible_discount = (st * (rate/100));

                                            if (flexible_discount > 0){
                                                next_apply_flexible_discount = flexible_discount;

                                                // if there is already a discount from previously,
                                                // store it elsewhere to apply it this time
                                                // and store this discount ready for next time
                                            }


                                        }





                                    }

                                } catch (SQLException ex) {

                                } finally {
                                    try {
                                        //rs.close();
                                        //ps.close();
                                        con.close();
                                    } catch (SQLException ex) {
                                        System.out.println(ex.toString());
                                    }
                                }




                                String job_id = "SELECT job_id FROM Job WHERE Customeraccount_no=?";
                                String variable = "SELECT Variable_Discountdiscount_id FROM Job_StandardTask WHERE Jobjob_id=?";

                                try (PreparedStatement ps_job_id = con2.prepareStatement(job_id);
                                PreparedStatement ps_variable = con2.prepareStatement(variable) )
                                {

                                    con.setAutoCommit(false);

                                    ps_job_id.setString(1, account_no);
                                    ResultSet rs_job_id = ps_job_id.executeQuery();

                                    ps_variable.setString(1, rs_job_id.getString(1));
                                    ResultSet rs_variable = ps_variable.executeQuery();

                                    con.commit();


                                    if (rs_variable.getString(1) != null){



                                        ps_get_job_id.setString(1, account_no);
                                        ResultSet rs_get_job_id = ps_get_job_id.executeQuery();

                                        ps_get_v_ID.setString(1, rs_get_job_id.getString(1));
                                        ResultSet rs_get_v_ID = ps_get_v_ID.executeQuery();

                                        ps_get_v_rate.setString(1, rs_get_v_ID.getString(1));
                                        ResultSet rs_get_v_rate = ps_get_v_rate.executeQuery();


                                        String rates = rs_get_v_rate.getString(1);
                                        String[] rates_array = rates.split("\\s*,\\s*");
                                        //System.out.println(rates_array);

                                        ps_get_task.setString(1, rs_get_job_id.getString(1));
                                        ResultSet rs_get_task = ps_get_task.executeQuery();
                                        while (rs_get_task.next()){

                                            int num = Integer.parseInt(rs_get_task.getString(1).substring(1));
                                            String t = rates_array[num];
                                            tot = (tot - (tot*(Integer.parseInt(t)/100)));

                                        } // while







                                    }

                                } catch (SQLException ex) {

                                } finally {
                                    try {
                                        //rs.close();
                                        //ps.close();
                                        con.close();
                                    } catch (SQLException ex) {
                                        System.out.println(ex.toString());
                                    }
                                }








                                /*
                                if (CreateFixedDiscount.fixed){

                                    ps_get_fixedID.setString(1, account_no);
                                    ResultSet rs_get_fixedID = ps_get_fixedID.executeQuery();

                                    ps_get_fixed_rate.setString(1, rs_get_fixedID.getString(1));
                                    ResultSet rs_get_fixed_rate = ps_get_fixed_rate.executeQuery();
                                    int fixed_discount = rs_get_fixed_rate.getInt(1);
                                    System.out.println(fixed_discount);

                                    //int d = tot*(fixed_discount/100);
                                    //int x = (tot * d);
                                    tot = (tot - (tot*(fixed_discount/100)));




                                } else if (CreateFlexibleDiscount.flexible == true){

                                    ps_get_flexibleID.setString(1, account_no);
                                    ResultSet rs_get_flexibleID = ps_get_flexibleID.executeQuery();

                                    ps_get_flexible_rate.setString(1, rs_get_flexibleID.getString(1));
                                    ResultSet rs_get_flexible_rate = ps_get_flexible_rate.executeQuery();

                                    ps_get_flexible_jobs.setString(1, account_no);
                                    ResultSet rs_get_flexible_jobs = ps_get_flexible_jobs.executeQuery();

                                    if (rs_get_flexible_jobs.getInt(1) < 1000){
                                        int st = rs_get_flexible_jobs.getInt(1);
                                        int rate = rs_get_flexible_rate.getInt(1);

                                        if (flexible_discount > 0){
                                            apply_flexible_discount = flexible_discount;
                                            // if there is already a discount from previously,
                                            // store it elsewhere to apply it this time
                                            // and store this discount ready for next time
                                        }

                                        flexible_discount = (st * (rate/100));
                                    }



                                } else if (CreateVariableDiscount.variable == true){


                                    ps_get_job_id.setString(1, account_no);
                                    ResultSet rs_get_job_id = ps_get_job_id.executeQuery();

                                    ps_get_v_ID.setString(1, rs_get_job_id.getString(1));
                                    ResultSet rs_get_v_ID = ps_get_v_ID.executeQuery();

                                    ps_get_v_rate.setString(1, rs_get_v_ID.getString(1));
                                    ResultSet rs_get_v_rate = ps_get_v_rate.executeQuery();


                                    String rates = rs_get_v_rate.getString(1);
                                    String[] rates_array = rates.split("\\s*,\\s*");
                                    //System.out.println(rates_array);

                                    ps_get_task.setString(1, rs_get_job_id.getString(1));
                                    ResultSet rs_get_task = ps_get_task.executeQuery();
                                    while (rs_get_task.next()){

                                        int num = Integer.parseInt(rs_get_task.getString(1).substring(1));
                                        String t = rates_array[num];
                                        tot = (tot - (tot*(Integer.parseInt(t)/100)));

                                    } // while



                                }

                                */




                            } // if ==
                        } // while

/*
                        if (valued){

                            if (CreateFixedDiscount.fixed == true){

                                ps_get_fixedID.setString(1, account_no);
                                ResultSet rs_get_fixedID = ps_get_fixedID.executeQuery();

                                ps_get_fixed_rate.setString(1, rs_get_fixedID.getString(1));
                                ResultSet rs_get_fixed_rate = ps_get_fixed_rate.executeQuery();
                                int fixed_discount = rs_get_fixed_rate.getInt(1);
                                System.out.println(fixed_discount);


                            } else if (CreateFlexibleDiscount.flexible == true){




                            } else if (CreateVariableDiscount.variable == true){




                            }

                        }
                        */

                        con.commit();


                    } catch (SQLException ex) {

                    } finally {
                        try {
                            //rs.close();
                            //ps.close();
                            con.close();
                        } catch (SQLException ex) {
                            System.out.println(ex.toString());
                        }
                    }






                    // begins here
                    //System.out.println(urgency);
                    if (urgency == 3) {
                        surcharge = 2;
                        // you also have to check whether the times for the tasks
                        // are less than the urgency, if so, display message
                        // that urgency needs to be higher as the tasks will take longer


                        surchargeTextbox.setText("100%");
                    } else if (urgency == 2) {
                        surcharge = 2.5;
                        surchargeTextbox.setText("150%");

                    } else if (urgency == 1) {
                        surchargeTextbox.setText("200%");
                        surcharge = 3;

                    } else {
                        surcharge = 1;
                        surchargeTextbox.setText("0%");

                    }

                    if (apply_flexible_discount > 0){
                        subtot = subtot - apply_flexible_discount;
                        apply_flexible_discount = next_apply_flexible_discount;
                    } else {

                    }

                    subtot = (tot * surcharge) * 1.2;

                    subTotalTextArea.setText("£" + df.format(subtot));
                }
                else{
                    JOptionPane.showMessageDialog(createJobPanel, "No tasks added!");


                }

                //System.out.println("correct");
                //surchargeTextbox.setText("" + urgency);


                System.out.println(urgency);
            }


            // once urgency has been input, display surcharge in that textbox

            ///Connection con = DbConnection.connect();
            ///PreparedStatement ps = null;
            //ResultSet rs = null;

            // firstly, get the subtotal of all of the tasks

            // DONT ALLOW MORE THAN ONE TASK BE ADDED!!!!! check the line

            //int sub_total = 0;



        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // add jobs and tasks and everything to database
                // use transaction
                // isolation levels??
                if (tot > 0 && !lm.isEmpty() && urgency > 0) {


                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    LocalDateTime ldt = LocalDateTime.now();
                    LocalDateTime new_ldt = ldt.plusHours(urgency);


                    Connection con = DbConnection.connect();
                    //PreparedStatement ps = null;
                    //ResultSet rs = null;

                    String getJob_idCount = "SELECT count(job_id) FROM Job";
                    String insertJob = "INSERT INTO Job(job_id, status, sub_total, payment_deadline, urgency, Customeraccount_no, surcharge) VALUES (?,?,?,?,?,?,?)";
                    String get_task_id = "SELECT task_id FROM StandardTask WHERE task_description=?";
                    String insertTask = "INSERT INTO Job_StandardTask(Jobjob_id, StandardTasktask_id, Staffstaff_no) VALUES (?,?)";


                    try (PreparedStatement ps_getJob_idCount = con.prepareStatement(getJob_idCount);
                         PreparedStatement ps_insertJob = con.prepareStatement(insertJob);
                         PreparedStatement ps_get_task_id = con.prepareStatement(get_task_id);
                         PreparedStatement ps_insertTask = con.prepareStatement(insertTask)) {
                        con.setAutoCommit(false);
                        // need to count how many rows there are in the job table and then add one to it plus a j for the job_id

                        //ps = con.prepareStatement(getJob_idCount);
                        ResultSet rs_getJob_idCount = ps_getJob_idCount.executeQuery();
                        int job_id_count = rs_getJob_idCount.getInt(1);
                        String job_id = ("J" + (job_id_count + 1));

                        // payment_deadline = current date and time + urgency in YYYY-MM-DD HH:MM:SS


                        int surcharge_int = Integer.parseInt(surchargeTextbox.getText().substring(0, surchargeTextbox.getText().length() - 1));


                        //ps = con.prepareStatement(insertJob);
                        ps_insertJob.setString(1, job_id);
                        ps_insertJob.setString(2, "pending");
                        ps_insertJob.setDouble(3, subtot);
                        //ps_insertJob.setDate(4, new java.sql.Date(urgency));
                        ps_insertJob.setString(4, paymentDeadlineTextbox.getText());
                        // ^^ this isnt right, this adds the urgency to the payment_deadline column
                        // needs to add the current date and time + the urgency
                        //ps_insertJob.setInt(5, urgency);
                        ps_insertJob.setString(6, account_no);
                        ps_insertJob.setInt(7, surcharge_int);
                        //ResultSet rs_insertJob = ps_insertJob.executeQuery();
                        ps_insertJob.executeUpdate();


                        for (int i = 0; i < lm.getSize(); i++) {


                            //ps = con.prepareStatement(get_task_id);
                            ps_get_task_id.setString(1, lm.getElementAt(i));
                            ResultSet rs_get_task_id = ps_get_task_id.executeQuery();
                            String task_id = rs_get_task_id.getString(1);


                            //ps = con.prepareStatement(insertTask);
                            ps_insertTask.setString(1, job_id);
                            ps_insertTask.setString(2, task_id);
                            //ResultSet rs_insertTask = ps_insertTask.executeQuery();
                            ps_insertTask.executeUpdate();


                        }
                        con.commit();

                    } catch (SQLException ex) {
                        System.out.println(ex.toString());
                        JOptionPane.showMessageDialog(createJobPanel, "Failed to create job.");
                    } finally {
                        try {
                            //rs.close();
                            //ps.close();
                            con.close();
                        } catch (SQLException ex) {
                            System.out.println(ex.toString());
                        }
                    }
                    lm.removeAllElements();
                    subTotalTextArea.setText("");
                    tot = 0;
                    subtot=0;

                }
                else if(lm.isEmpty()){
                    JOptionPane.showMessageDialog(createJobPanel, "Please select a task");
                }
                else{
                    JOptionPane.showMessageDialog(createJobPanel, "Please select a urgency");
                }

            }




        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                // yes/no are you sure pop
                // if yes, then clear all the boxes


            }
        });

        removeSelectedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = taskList.getSelectedIndex();
                Connection con = DbConnection.connect();
                PreparedStatement ps = null;
                ResultSet rs = null;
                System.out.println(tot);
                if(!taskList.isSelectionEmpty()) {
                    System.out.println("this" + urgency);


                    try {
                        String sql = "SELECT price FROM StandardTask WHERE task_description=? ";


                        String x = lm.getElementAt(index);
                        ps = con.prepareStatement(sql);
                        ps.setString(1, x);
                        rs = ps.executeQuery();
                        tot -= rs.getDouble(1);
                        subTotalTextArea.setText(null);


                        System.out.println(lm.getSize());


                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(createJobPanel, "Please select a task.");
                    } finally {
                        try {

                            rs.close();
                            ps.close();
                            con.close();
                        } catch (SQLException ex) {
                            System.out.println(ex.toString());
                        }
                    }


                    if (urgency == 0){
                        lm.removeElementAt(index);
                        subTotalTextArea.setText("£" + df.format(abs(tot)));
                    }
                    else if (urgency > 0){
                        lm.removeElementAt(index);
                        urgency =0;
                        subTotalTextArea.setText("£" + df.format(abs(tot)));
                        surcharge = 0;
                        surchargeTextbox.setText("");

                    }
                }
                else{
                    JOptionPane.showMessageDialog(createJobPanel, "You have not selected an item to remove ");

                }

            }
        });
    }

    public static void main(String[] args){
        JFrame frame = new CreateJob("Create Job");
        frame.setVisible(true);


    }

}