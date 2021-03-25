import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Edit_RemoveTask extends JFrame {

    private JPanel editRemoveTaskPanel;
    private JButton editButton;
    private JMenuBar editRemoveTaskMenuBar;
    private JMenu logoutMenu;
    private JMenu homeMenu;
    private JList displayArea;
    private JButton deleteButton;


    // **************

    // NEED AN ADD STANDARD TASK FEATURE !!!!!!



    public Edit_RemoveTask(String title){
        super(title);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(editRemoveTaskPanel);
        this.pack();

        DefaultListModel<String> lm = new DefaultListModel<>();
        displayArea.setModel(lm);


        editRemoveTaskPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);

                Connection con = DbConnection.connect();
                PreparedStatement ps = null;
                ResultSet rs = null;

                try {

                    String get_StandardTasks = "SELECT task_id, task_description, price FROM StandardTask";
                    ps = con.prepareStatement(get_StandardTasks);
                    rs = ps.executeQuery();

                    String task_id = rs.getString(1);
                    String task_description = rs.getString(2);
                    String price = rs.getString(3);
                    String output = (task_id + " " + task_description + " " + price);

                    lm.addElement(output);


                } catch(SQLException ex) {
                    //System.out.println(ex.toString());
                    JOptionPane.showMessageDialog(editRemoveTaskPanel, "Failed to edit task.");
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
        JFrame frame = new Edit_RemoveTask("Edit/Remove Task");
        frame.setVisible(true);
    }

}
