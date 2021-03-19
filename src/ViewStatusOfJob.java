import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ViewStatusOfJob extends JFrame {
    private JPanel viewStatusOfJobPanel;
    private JTextField jobIDTextbox;
    private JTextArea viewStatusOfJobTextArea;
    private JLabel jobIDLabel;
    private JMenuBar viewStatusOfJobMenuBar;
    private JMenu logoutMenu;
    private JMenu homeMenu;
    private JButton viewButton;

    public ViewStatusOfJob(String title){
        super(title);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(viewStatusOfJobPanel);
        this.pack();


        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String job = jobIDTextbox.getText();

                //String SQL = "select * from BENEFICIARIES where localgovernment = '" +bs+"'";

                Connection con = DbConnection.connect();

                PreparedStatement ps = null;
                ResultSet rs = null;

                try {
                    String sql = "SELECT job_id, status FROM Job WHERE job_id=?";
                    ps = con.prepareStatement(sql);
                    ps.setString(1, job);
                    rs = ps.executeQuery();

                    // change this textbox to an editable combo box
                    // which displays all of the available jobs
                    // and allows user to type in, which also auto completes

                    String job_id = rs.getString(1);
                    String status = rs.getString(2);

                    if (job.equals(job_id)) {

                        System.out.println(job_id + status);
                        //String display = (job_id + " " + status);
                        //list.add(display);
                        viewStatusOfJobTextArea.setText(status);
                    } else {
                        JOptionPane.showMessageDialog(viewStatusOfJobPanel, "Job does not exist.");
                    }

                    //}
                    //String string="";
                    //for(String s:list)
                        //string+=s+"\n";
                    //viewStatusOfJobTextArea.setText(string);


                } catch(SQLException ex) {
                    //System.out.println(ex.toString());
                    JOptionPane.showMessageDialog(viewStatusOfJobPanel, "Job does not exist.");
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
        JFrame frame = new ViewStatusOfJob("View Status Of Job");
        frame.setVisible(true);



    }

}
