import javax.swing.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CreateJob extends JFrame {


    private JPanel createJobPanel;
    private JComboBox selectCustomerComboBox;
    private JButton createCustomerButton;
    private JComboBox selectTaskComboBox;
    private JButton addTaskButton;
    private JTextArea tasksTextArea;
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
    private JButton takepaymentButton;
    private JTextField urgencyTextbox;
    int sub_total = 0;


    public CreateJob(String title) {
        super(title);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(createJobPanel);
        this.pack();


        selectCustomerComboBox.addItem("");
        selectTaskComboBox.addItem("");


        // need to check that the job is being assigned to a customer that exists,
        // if not, error message and dont add to db

        // "receptionist & OM & SM : assign job number "
        // you need to allow them to assign job number!!!!!!!!!
        // as record deadline for completion and any special instructions!!!!!!!







        createJobPanel.addMouseListener(new MouseAdapter() {
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

                    if(selectCustomerComboBox.getItemCount() == 1) {
                        while (rs.next()) {

                            selectCustomerComboBox.addItem(rs.getString(1));
                        }
                    }

                } catch (
                        SQLException ex) {
                    System.out.println(ex.toString());
                    //JOptionPane.showMessageDialog(createJobPanel, "Please select task(s).");
                } /* finally {
                    try {
                        rs.close();
                        ps.close();
                        con.close();
                    } catch (SQLException ex) {
                        System.out.println(ex.toString());
                    }
                }
                */

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
        addTaskButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String selectedTask = selectTaskComboBox.getSelectedItem().toString();
                //System.out.println(selectedTask);

                boolean chosen = false;
                // ^^ doesnt work


                // this should check for multiple selections of tasks, but doesnt work
                for (String line : tasksTextArea.getText().split("\\n")) {
                    //System.out.println(line);
                    if (selectedTask == line){
                        //System.out.println("chosen two");
                        chosen = true;
                        // ^^ not working
                    }
                }

                if (selectTaskComboBox.getSelectedItem() == "") {
                    JOptionPane.showMessageDialog(createJobPanel, "Please select a task.");
                }  else if (chosen) {
                    JOptionPane.showMessageDialog(createJobPanel, "Already selected.");
                }  else {
                    tasksTextArea.append(selectedTask + "\n");
                }


                /*
                // delete this

                Connection con = DbConnection.connect();
                PreparedStatement ps = null;
                ResultSet rs = null;


                try {
                    String sql = "SELECT payment_deadline FROM Job WHERE job_id=?";
                    ps = con.prepareStatement(sql);
                    ps.setString(1, "J1");
                    rs = ps.executeQuery();

                    System.out.println(rs.getDate(1));
                } catch(SQLException ex) {
                        System.out.println(ex.toString());
                } finally {
                    try {
                            rs.close();
                            ps.close();
                            con.close();
                    } catch(SQLException ex) {
                            System.out.println(ex.toString());
                        }
                }

                    
                // ^^ delete this
                */




                //tasksTextArea.setText(selectedTask);



            }
        });
        selectTaskComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (selectCustomerComboBox.getSelectedItem() == "") {
                    JOptionPane.showMessageDialog(createJobPanel, "Please select a customer.");

                } else {
                    //System.out.println(selectTaskComboBox.getSelectedItem());
                }

                //tasksTextArea.setText(selected);


            }
        });



        OKButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // if input is not 1 - 24, show message saying input between 1 and 24 hours
                // this needs to go in the save button action listener

                int urgency = Integer.parseInt(urgencyFormattedTextbox.getText());
                //System.out.println(urgency);

                if (urgency <= 24 && urgency >= 1) {

                    if (urgency == 3) {
                        // you also have to check whether the times for the tasks
                        // are less than the urgency, if so, display message
                        // that urgency needs to be higher as the tasks will take longer
                        surchargeTextbox.setText("100%");
                    } else if (urgency == 2) {
                        surchargeTextbox.setText("150%");
                    } else if (urgency == 1) {
                        surchargeTextbox.setText("200%");
                    } else {
                        surchargeTextbox.setText("0%");
                    }

                    //System.out.println("correct");
                    //surchargeTextbox.setText("" + urgency);
                } else if (urgencyFormattedTextbox.getText() == "") {
                    JOptionPane.showMessageDialog(createJobPanel, "Please enter an urgency.");
                    // think there is an error here

                } else {
                    urgencyFormattedTextbox.setText("");
                    surchargeTextbox.setText("");
                    JOptionPane.showMessageDialog(createJobPanel, "Please enter urgency between 1 and 24 hours.");
                }

                // once urgency has been input, display surcharge in that textbox

                Connection con = DbConnection.connect();
                PreparedStatement ps = null;
                ResultSet rs = null;

                // firstly, get the subtotal of all of the tasks

                // DONT ALLOW MORE THAN ONE TASK BE ADDED!!!!! check the line

                //int sub_total = 0;

                try {

                    String getPriceSQL = "SELECT price FROM StandardTask WHERE task_description=?";

                    for (String line : tasksTextArea.getText().split("\\n")) {

                        ps = con.prepareStatement(getPriceSQL);
                        ps.setString(1, line);
                        rs = ps.executeQuery();

                        //sub_total += Integer.parseInt(rs.getString(1));
                        sub_total += rs.getInt(1);
                        // this doesnt work from string to int
                        
                        subTotalTextArea.append(line + ": £" + rs.getInt(1) + "\n");
                        // output the price in correct format eg not 110.3
                    }

                    ////subTotalTextArea.append("Sub-Total: £" + sub_total);
                    //System.out.println(sub_total);
                    subTotalTextArea.append("Sub-total: £" + sub_total);
                    // ^^ doesnt work!!!!

                } catch(SQLException ex) {
                    JOptionPane.showMessageDialog(createJobPanel, "Please select a task.");
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

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // add jobs and tasks and everything to database
                // use transaction
                // isolation levels??

                int urgency = Integer.parseInt(urgencyFormattedTextbox.getText().toString());

                //System.out.println(java.time.LocalDateTime.now());
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime ldt = LocalDateTime.now();
                LocalDateTime new_ldt = ldt.plusHours(urgency);


                Connection con = DbConnection.connect();
                //PreparedStatement ps = null;
                //ResultSet rs = null;

                String getJob_idCount = "SELECT count(job_id) FROM Job";
                String insertJob = "INSERT INTO Job(job_id, status, sub_total, payment_deadline, urgency, Customeraccount_no, surcharge) VALUES (?,?,?,?,?,?,?)";
                String get_task_id = "SELECT task_id FROM StandardTask WHERE task_description=?";
                String insertTask = "INSERT INTO Job_StandardTask(Jobjob_id, StandardTasktask_id) VALUES (?,?)";


                try (PreparedStatement ps_getJob_idCount = con.prepareStatement(getJob_idCount);
                    PreparedStatement ps_insertJob = con.prepareStatement(insertJob);
                    PreparedStatement ps_get_task_id = con.prepareStatement(get_task_id);
                    PreparedStatement ps_insertTask = con.prepareStatement(insertTask))

                {
                    con.setAutoCommit(false);
                    // need to count how many rows there are in the job table and then add one to it plus a j for the job_id

                    //ps = con.prepareStatement(getJob_idCount);
                    ResultSet rs_getJob_idCount = ps_getJob_idCount.executeQuery();
                    int job_id_count = rs_getJob_idCount.getInt(1);
                    String job_id = ("J" + (job_id_count + 1));

                    // payment_deadline = current date and time + urgency in YYYY-MM-DD HH:MM:SS


                    int surcharge_int = Integer.parseInt(surchargeTextbox.getText().substring(0, surchargeTextbox.getText().length()-1));
                    

                    //ps = con.prepareStatement(insertJob);
                    ps_insertJob.setString(1, job_id);
                    ps_insertJob.setString(2, "pending");
                    ps_insertJob.setInt(3, sub_total);
                    ps_insertJob.setDate(4, new java.sql.Date(urgency));
                    // ^^ this isnt right, this adds the urgency to the payment_deadline column
                    // needs to add the current date and time + the urgency
                    ps_insertJob.setString(5, urgencyFormattedTextbox.getText());
                    ps_insertJob.setString(6, selectCustomerComboBox.getSelectedItem().toString());
                    ps_insertJob.setInt(7, surcharge_int);
                    //ResultSet rs_insertJob = ps_insertJob.executeQuery();
                    ps_insertJob.executeUpdate();



                    for (String line : tasksTextArea.getText().split("\\n")) {


                        //ps = con.prepareStatement(get_task_id);
                        ps_get_task_id.setString(1, line);
                        ResultSet rs_get_task_id = ps_get_task_id.executeQuery();
                        String task_id = rs_get_task_id.getString(1);



                        //ps = con.prepareStatement(insertTask);
                        ps_insertTask.setString(1, job_id);
                        ps_insertTask.setString(2, task_id);
                        //ResultSet rs_insertTask = ps_insertTask.executeQuery();
                        ps_insertTask.executeUpdate();

                        con.commit();

                    }
                    con.commit();
                    
                } catch(SQLException ex) {
                    //System.out.println(ex.toString());
                    JOptionPane.showMessageDialog(createJobPanel, "Failed to create job.");
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

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // yes/no are you sure pop
                // if yes, then clear all the boxes


            }
        });
        createCustomerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CreateCustomer createCustomer = new CreateCustomer("Create Customer");
                createCustomer.setVisible(true);
            }
        });
        takepaymentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                TakePayment tp = new TakePayment("Take Payment");
                tp.setVisible(true);


            }
        });
    }

    public static void main(String[] args){
        JFrame frame = new CreateJob("Create Job");
        frame.setVisible(true);


    }

}
