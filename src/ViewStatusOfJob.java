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

public class ViewStatusOfJob extends JFrame {
    private JPanel viewStatusOfJobPanel;
    private JTextArea viewStatusOfJobTextArea;
    private JLabel jobIDLabel;
    private JMenuBar viewStatusOfJobMenuBar;
    private JMenu logoutMenu;
    private JMenu homeMenu;
    private JButton viewButton;
    private JComboBox jobIDComboBox;

    public ViewStatusOfJob(String title){
        super(title);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(viewStatusOfJobPanel);
        this.pack();

        jobIDComboBox.addItem("");


        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Connection con = DbConnection.connect();

                PreparedStatement ps = null;
                ResultSet rs = null;

                try {
                    String sql = "SELECT job_id, status FROM Job WHERE job_id=?";
                    ps = con.prepareStatement(sql);
                    //ps.setString(1, job);
                    ps.setString(1, jobIDComboBox.getSelectedItem().toString());
                    rs = ps.executeQuery();

                    String job_id = rs.getString(1);
                    String status = rs.getString(2);

                    if (jobIDComboBox.getSelectedItem().toString().equals(job_id)) {

                        System.out.println(job_id + status);
                        viewStatusOfJobTextArea.setText(status);
                    } else {
                        JOptionPane.showMessageDialog(viewStatusOfJobPanel, "Job does not exist.");
                    }

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


        viewStatusOfJobPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);

                Connection con = DbConnection.connect();
                PreparedStatement ps = null;
                ResultSet rs = null;

                // displays account id's of customer in the Customer combo box
                try {

                    String sql = "SELECT job_id FROM Job";
                    ps = con.prepareStatement(sql);
                    rs = ps.executeQuery();

                    if(jobIDComboBox.getItemCount() == 1) {
                        while (rs.next()) {

                            jobIDComboBox.addItem(rs.getString(1));
                        }
                    }

                } catch (
                        SQLException ex) {
                    System.out.println(ex.toString());
                    //JOptionPane.showMessageDialog(createJobPanel, "Please select task(s).");
                }  finally {
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
    }

    public static void main(String[] args){
        JFrame frame = new ViewStatusOfJob("View Status Of Job");
        frame.setVisible(true);



    }

}
