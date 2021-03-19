import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ViewAllJobs extends JFrame {
    private JPanel viewAllJobsPanel;
    private JTextArea viewAllJobsTextArea;
    private JMenuBar viewAllJobsMenuBar;
    private JMenu logoutMenu;
    private JMenu homeMenu;
    private JButton viewButton;

    public ViewAllJobs(String title){
        super(title);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(viewAllJobsPanel);
        this.pack();


        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Connection con = DbConnection.connect();

                PreparedStatement ps = null;
                ResultSet rs = null;

                try {

                    /* this is for searching something the user input, in the db
                    String bs = search.getText();
                    String SQL = "select * from BENEFICIARIES where localgovernment = '" +bs+"'";
                    */

                    String sql = "SELECT job_id, status, sub_total, payment_deadline, Customeraccount_no FROM Job ORDER BY job_id";
                    ps = con.prepareStatement(sql);

                    rs = ps.executeQuery();

                    //String job_id = rs.getString(1);
                    //String status = rs.getString(2);
                    //String sub_total = rs.getString(3);
                    //String payment_deadline = rs.getString(4);
                    //String customer_acc_no = rs.getString(5);

                    //display all of these in the viewAllJobsTextArea

                    List<String> list = new ArrayList<String>();
                    while(rs.next()){
                        String job_id = rs.getString("job_id");
                        String status = rs.getString("status");
                        String sub_total = String.valueOf(rs.getInt("sub_total"));
                        String payment_deadline = rs.getString("payment_deadline");
                        String customer_acc_no = rs.getString("Customeraccount_no");
                        System.out.println(job_id + status + sub_total + payment_deadline + customer_acc_no);
                        String display = job_id + " "+ status +" "+sub_total+" "+payment_deadline+" "+customer_acc_no;
                        list.add(display);

                        //String tbData[] = {job_id, status, sub_total, payment_deadline, customer_acc_no};
                            //DefaultTableModel tblModel = (DefaultTableModel)table1.getModel();
                            //tblModel.addRow(tbData);

                    }
                    String string="";
                    for(String s:list)
                        string+=s+"\n";
                    viewAllJobsTextArea.setText(string);



                    //viewAllJobsTextArea.setRows(10);

                    //viewAllJobsTextArea.setText(rs.getString(1));
                    //viewAllJobsTextArea.setText(rs.getString(2));
                    //viewAllJobsTextArea.setText(rs.getString(3));
                    //viewAllJobsTextArea.setText(rs.getString(4));
                    //viewAllJobsTextArea.setText(rs.getString(5));
                    // ^^ only prints out customer account of first job??



                    /*
                    while (rs.next()) {
                        viewAllJobsTextArea.setText(rs.getString("job_id"));
                        viewAllJobsTextArea.setText(rs.getString("status"));
                        viewAllJobsTextArea.setText(rs.getString("sub_total"));
                        viewAllJobsTextArea.setText(rs.getString("payment_deadline"));
                        viewAllJobsTextArea.setText(rs.getString("Customeraccount_no"));
                    }
                    */


                } catch(SQLException ex) {
                    //System.out.println(ex.toString());
                    JOptionPane.showMessageDialog(viewAllJobsPanel, "No jobs stored.");
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
    }

    public static void main(String[] args){
        JFrame frame = new ViewAllJobs("View All Jobs");
        frame.setVisible(true);
    }

}
