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
    private JTextField urgencyTextbox;
    int sub_total = 0;

    public CreateJob(String title) {
        super(title);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(createJobPanel);
        this.pack();


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

                boolean chosen = false;
                // ^^ doesnt work

                for (String line : tasksTextArea.getText().split("\\n")) {
                    if (selectedTask == line){
                        System.out.println("chosen two");
                        // ^^ not working
                    }
                }

                if (selectTaskComboBox.getSelectedItem() == "") {
                    JOptionPane.showMessageDialog(createJobPanel, "Please select a task.");
                } else if (chosen == true) {
                    JOptionPane.showMessageDialog(createJobPanel, "Task already selected, please select another.");
                } else {
                    tasksTextArea.append(selectedTask + "\n");
                }






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
                PreparedStatement ps = null;
                ResultSet rs = null;


                try {
                    con.setAutoCommit(false);
                    // need to count how many rows there are in the job table and then add one to it plus a j for the job_id
                    String getJob_idCount = "SELECT count(job_id) FROM Job";
                    ps = con.prepareStatement(getJob_idCount);
                    int job_id_count = rs.getInt(1);
                    String job_id = ("J" + (job_id_count + 1));

                    // payment_deadline = current date and time + urgency in YYYY-MM-DD HH:MM:SS


                    int surcharge_int = Integer.parseInt(surchargeTextbox.getText().substring(0, surchargeTextbox.getText().length()-1));
                    
                    String insertJob = "INSERT INTO Job(job_id, status, sub_total, payment_deadline, urgency, Customeraccount_no, surcharge) VALUES (?,?,?,?,?,?,?)";
                    ps = con.prepareStatement(insertJob);
                    ps.setString(1, job_id);
                    ps.setString(2, "pending");
                    ps.setInt(3, sub_total);
                    ps.setDate(4, new java.sql.Date(urgency));
                    ps.setString(5, urgencyFormattedTextbox.getText());
                    ps.setString(6, selectCustomerComboBox.getSelectedItem().toString());
                    ps.setInt(7, surcharge_int);



                    for (String line : tasksTextArea.getText().split("\\n")) {

                        String get_task_id = "SELECT task_id FROM StandardTask WHERE task_description=?";
                        ps = con.prepareStatement(get_task_id);
                        ps.setString(1, line);
                        rs = ps.executeQuery(get_task_id);
                        String task_id = rs.getString(1);


                        String insertTask = "INSERT INTO Job_StandardTask(Jobjob_id, StandardTasktask_id) VALUES (?,?)";
                        ps = con.prepareStatement(insertTask);
                        ps.setString(1, job_id);
                        ps.setString(2, task_id);

                    }
                    
                } catch(SQLException ex) {
                    JOptionPane.showMessageDialog(createJobPanel, "Failed to create job.");
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

                // yes/no are you sure pop
                // if yes, then clear all the boxes


            }
        });
    }

    public static void main(String[] args){
        JFrame frame = new CreateJob("Create Job");
        frame.setVisible(true);


    }

}
