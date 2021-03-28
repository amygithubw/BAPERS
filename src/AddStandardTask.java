import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AddStandardTask extends JFrame {
    private JPanel addStandardTaskPanel;
    private JTextArea descriptionTextArea;
    private JTextField taskIDTextbox;
    private JComboBox roomComboBox;
    private JTextField priceTextbox;
    private JTextField durationTextbox;
    private JButton addButton;
    private JLabel descriptionLabel;
    private JLabel taskIDLabel;
    private JLabel roomLabel;
    private JLabel priceLabel;
    private JLabel durationLabel;

    String task_id;

    public AddStandardTask(String title) {
        super(title);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(addStandardTaskPanel);
        this.pack();

        roomComboBox.addItem("");


        addStandardTaskPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);

                Connection con = DbConnection.connect();

                String get_taskID_count = "SELECT count(task_id) FROM StandardTask";
                String get_location = "SELECT location FROM StandardTask";

                try (PreparedStatement ps_get_taskID_count = con.prepareStatement(get_taskID_count);
                     PreparedStatement ps_get_location = con.prepareStatement(get_location) )
                {
                    con.setAutoCommit(false);

                    //ps_get_taskID_count = con.prepareStatement(get_taskID_count);
                    ResultSet rs_get_taskID_count = ps_get_taskID_count.executeQuery();
                    int task_id_count = rs_get_taskID_count.getInt(1);
                    task_id = ("T" + (task_id_count + 1));

                    taskIDTextbox.setText(task_id);


                    ResultSet rs_get_location = ps_get_location.executeQuery();

                    if(roomComboBox.getItemCount() == 1) {
                        while (rs_get_location.next()) {
                            boolean exists = false;
                            for (int index = 0; index < roomComboBox.getItemCount() && !exists; index++){
                                if (rs_get_location.getString(1).equals(roomComboBox.getItemAt(index))){
                                    exists = true;
                                }
                            }
                            if (!exists){
                                roomComboBox.addItem(rs_get_location.getString(1));
                            }
                        }
                    }


                    con.commit();


                } catch (SQLException ex) {
                    //System.out.println(ex.toString());
                    //if ((contactNameTextbox.getText() != "") && (buttonClicked == false)) {
                    JOptionPane.showMessageDialog(addStandardTaskPanel, "No tasks stored.");
                    //}
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
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Connection con = DbConnection.connect();

                String addTask = "INSERT INTO StandardTask(task_id,task_description,location,price,duration) VALUES (?,?,?,?,?)";


                try (PreparedStatement ps_addTask = con.prepareStatement(addTask) )

                {
                    con.setAutoCommit(false);

                    ps_addTask.setString(1, task_id);
                    ps_addTask.setString(2, descriptionTextArea.getText());
                    ps_addTask.setString(3, roomComboBox.getSelectedItem().toString());
                    ps_addTask.setString(4, priceTextbox.getText());
                    ps_addTask.setString(5, durationTextbox.getText());
                    ps_addTask.executeUpdate();

                    descriptionTextArea.setText("");
                    roomComboBox.setSelectedItem("");
                    priceTextbox.setText("");
                    durationTextbox.setText("");

                    JOptionPane.showMessageDialog(addStandardTaskPanel, "Task added.");

                    con.commit();

                    // if the phone number input is the same as any of the already stored numbers, error message
                    // if any of the textboxes are left blank, error message
                    // email address in correct format ???


                } catch(SQLException ex) {
                    //System.out.println(ex.toString());
                    JOptionPane.showMessageDialog(addStandardTaskPanel, "Failed to add task.");
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
        JFrame frame = new AddStandardTask("Add Task");
        frame.setVisible(true);
    }


}
