import javax.swing.*;
import javax.swing.table.DefaultTableModel;
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

public class ViewAllJobs extends JFrame {
    private JPanel viewAllJobsPanel;
    private JTextArea viewAllJobsTextArea;
    private JMenuBar viewAllJobsMenuBar;
    private JMenu logoutMenu;
    private JMenu homeMenu;

    public ViewAllJobs(String title){
        super(title);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(viewAllJobsPanel);
        this.pack();



        viewAllJobsPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);

                Connection con = DbConnection.connect();

                PreparedStatement ps = null;
                ResultSet rs = null;

                try {

                    String sql = "SELECT job_id, status, sub_total, payment_deadline, Customeraccount_no FROM Job ORDER BY job_id";
                    ps = con.prepareStatement(sql);

                    rs = ps.executeQuery();

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

                    }
                    String string="";
                    for(String s:list)
                        string+=s+"\n";
                    viewAllJobsTextArea.setText(string);


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
