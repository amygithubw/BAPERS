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

public class ViewJobsInProgress extends JFrame {
    private JPanel viewJobsInProgressPanel;
    private JTextArea viewJobsInProgressTextArea;
    private JMenuBar viewJobsInProgressMenuBar;
    private JMenu logoutMenu;
    private JMenu homeMenu;

    public ViewJobsInProgress(String title){
        super(title);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(viewJobsInProgressPanel);
        this.pack();


        viewJobsInProgressPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);

                Connection con = DbConnection.connect();

                PreparedStatement ps = null;
                ResultSet rs = null;

                try {

                    String sql = "SELECT job_id, status, sub_total, payment_deadline, Customeraccount_no FROM Job WHERE status=\"progress\" ORDER BY job_id;";
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
                    viewJobsInProgressTextArea.setText(string);


                } catch(SQLException ex) {
                    //System.out.println(ex.toString());
                    JOptionPane.showMessageDialog(viewJobsInProgressPanel, "No jobs in progress.");
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
        JFrame frame = new ViewJobsInProgress("View Jobs In Progress");
        frame.setVisible(true);
    }

}
