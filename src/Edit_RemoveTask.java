import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class Edit_RemoveTask extends JFrame {

    private JPanel editRemoveTaskPanel;
    private JButton editButton;
    private JMenuBar editRemoveTaskMenuBar;
    private JMenu logoutMenu;
    private JMenu homeMenu;
    private JButton deleteButton;
    private JTextArea descriptionTextArea;
    private JComboBox roomComboBox;
    private JTextField priceTextbox;
    private JTextField durationTextbox;
    private JComboBox taskIDComboBox;

    String taskID;
    String uneditedDescription;
    String description;
    String uneditedRoom;
    String room;
    double uneditedPrice;
    String price;
    String uneditedDuration;
    String duration;
    boolean buttonClicked = false;


    // **************

    // NEED AN ADD STANDARD TASK FEATURE !!!!!!



    public Edit_RemoveTask(String title){
        super(title);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(editRemoveTaskPanel);
        this.pack();

        taskIDComboBox.addItem("");
        roomComboBox.addItem("");

        //DefaultListModel<String> lm = new DefaultListModel<>();
        //displayArea.setModel(lm);


        editRemoveTaskPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);

                Connection con = DbConnection.connect();

                String get_taskID = "SELECT task_id FROM StandardTask";
                String get_location = "SELECT location FROM StandardTask";

                try (PreparedStatement ps_get_taskID = con.prepareStatement(get_taskID);
                     PreparedStatement ps_get_location = con.prepareStatement(get_location) )
                {


                    con.setAutoCommit(false);

                    //String get_taskID = "SELECT task_id FROM StandardTask";
                    //ps_get_taskID = con.prepareStatement(get_taskID);
                    ResultSet rs_get_taskID = ps_get_taskID.executeQuery();

                    if(taskIDComboBox.getItemCount() == 1) {
                        while (rs_get_taskID.next()) {
                            taskIDComboBox.addItem(rs_get_taskID.getString(1));
                        }
                    }



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
                                uneditedRoom = rs_get_location.getString(1);
                            }
                        }
                    }


                    con.commit();


                } catch(SQLException ex) {
                    //System.out.println(ex.toString());
                    JOptionPane.showMessageDialog(editRemoveTaskPanel, "No tasks stored.");
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


        taskIDComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                buttonClicked = true;

                Connection con = DbConnection.connect();
                PreparedStatement ps = null;
                ResultSet rs = null;

                taskID = taskIDComboBox.getSelectedItem().toString();

                if (taskID != "") {


                    //String get_location = "SELECT location FROM StandardTask WHERE task_id=?";
                    //String get_price = "SELECT price FROM StandardTask WHERE task_id=?";
                    //String get_duration = "SELECT duration FROM StandardTask WHERE task_id=?";

                    try {
                        String get_task = "SELECT task_description,location,price,duration FROM StandardTask WHERE task_id=?";

                        ps = con.prepareStatement(get_task);
                        ps.setString(1, taskID);
                        rs = ps.executeQuery();
                        uneditedDescription = rs.getString(1);
                        descriptionTextArea.setText(uneditedDescription);
                        roomComboBox.setSelectedItem(rs.getString(2));
                        DecimalFormat twoPlaces = new DecimalFormat("0.00");
                        uneditedPrice = rs.getDouble(3);
                        //double price = rs.getDouble(3);
                        String price_format = twoPlaces.format(uneditedPrice);
                        priceTextbox.setText("£" + price_format);
                        uneditedDuration = rs.getString(4);
                        durationTextbox.setText(uneditedDuration);



                        con.commit();


                    } catch (SQLException ex) {
                        //System.out.println(ex.toString());
                        if ((descriptionTextArea.getText() != "") && (buttonClicked == false)) {
                            JOptionPane.showMessageDialog(editRemoveTaskPanel, "No tasks stored.");
                        }
                    } finally {
                        try {
                            //rs.close();
                            //ps.close();
                            con.close();
                        } catch (SQLException ex) {
                            System.out.println(ex.toString());
                        }
                    }


                }


            }
        });
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                buttonClicked = true;

                description = descriptionTextArea.getText();
                room = roomComboBox.getSelectedItem().toString();
                price = priceTextbox.getText().substring(1);
                System.out.println(price);
                duration = durationTextbox.getText();

                if (!durationTextbox.getText().equals(uneditedDuration) && !durationTextbox.getText().equals("")){

                    //System.out.println("duration:"+duration);
                    //System.out.println("uneditedduration"+uneditedDuration);

                    Connection con = DbConnection.connect();
                    String updateDuration = "UPDATE StandardTask Set duration=? WHERE duration=?";
                    //String getCustomerName = "SELECT customer_name FROM Customer WHERE contact_name=?";

                    try (PreparedStatement ps_updateDuration = con.prepareStatement(updateDuration)) {

                        //ps_updateCustomerName = con.prepareStatement(updateCustomerName);
                        ps_updateDuration.setString(1, duration);
                        ps_updateDuration.setString(2, uneditedDuration);
                        ps_updateDuration.executeUpdate();

                    } catch (SQLException ex) {
                        System.out.println(ex.toString());
                        //JOptionPane.showMessageDialog(createJobPanel, "Failed to create job.");
                    } finally {
                        try {
                            //rs.close();
                            //ps.close();
                            con.close();
                        } catch (SQLException ex) {
                            System.out.println(ex.toString());
                        }
                    }

                }




                if (!priceTextbox.getText().equals(uneditedPrice) && !priceTextbox.getText().equals("")){

                    //System.out.println("duration:"+duration);
                    //System.out.println("uneditedduration"+uneditedDuration);

                    Connection con = DbConnection.connect();
                    String updatePrice = "UPDATE StandardTask Set price=? WHERE price=?";
                    //String getCustomerName = "SELECT customer_name FROM Customer WHERE contact_name=?";

                    try (PreparedStatement ps_updatePrice = con.prepareStatement(updatePrice)) {

                        //ps_updateCustomerName = con.prepareStatement(updateCustomerName);
                        ps_updatePrice.setString(1, price);
                        ps_updatePrice.setDouble(2, uneditedPrice);
                        ps_updatePrice.executeUpdate();

                    } catch (SQLException ex) {
                        System.out.println(ex.toString());
                        //JOptionPane.showMessageDialog(createJobPanel, "Failed to create job.");
                    } finally {
                        try {
                            //rs.close();
                            //ps.close();
                            con.close();
                        } catch (SQLException ex) {
                            System.out.println(ex.toString());
                        }
                    }

                }



                if (!descriptionTextArea.getText().equals(uneditedDescription) && !descriptionTextArea.getText().equals("")){

                    //System.out.println("duration:"+duration);
                    //System.out.println("uneditedduration"+uneditedDuration);

                    Connection con = DbConnection.connect();
                    String updateDescription = "UPDATE StandardTask Set task_description=? WHERE task_description=?";
                    //String getCustomerName = "SELECT customer_name FROM Customer WHERE contact_name=?";

                    try (PreparedStatement ps_updateDescription = con.prepareStatement(updateDescription)) {

                        //ps_updateCustomerName = con.prepareStatement(updateCustomerName);
                        ps_updateDescription.setString(1, description);
                        ps_updateDescription.setString(2, uneditedDescription);
                        ps_updateDescription.executeUpdate();

                    } catch (SQLException ex) {
                        System.out.println(ex.toString());
                        //JOptionPane.showMessageDialog(createJobPanel, "Failed to create job.");
                    } finally {
                        try {
                            //rs.close();
                            //ps.close();
                            con.close();
                        } catch (SQLException ex) {
                            System.out.println(ex.toString());
                        }
                    }

                }



                if (!roomComboBox.getSelectedItem().toString().equals(uneditedDescription) && !roomComboBox.getSelectedItem().toString().equals("")){

                    //System.out.println("duration:"+duration);
                    //System.out.println("uneditedduration"+uneditedDuration);

                    Connection con = DbConnection.connect();
                    String updateLocation = "UPDATE StandardTask Set location=? WHERE location=?";
                    //String getCustomerName = "SELECT customer_name FROM Customer WHERE contact_name=?";

                    try (PreparedStatement ps_updateLocation = con.prepareStatement(updateLocation)) {

                        //ps_updateCustomerName = con.prepareStatement(updateCustomerName);
                        ps_updateLocation.setString(1, room);
                        ps_updateLocation.setString(2, uneditedRoom);
                        ps_updateLocation.executeUpdate();

                    } catch (SQLException ex) {
                        System.out.println(ex.toString());
                        //JOptionPane.showMessageDialog(createJobPanel, "Failed to create job.");
                    } finally {
                        try {
                            //rs.close();
                            //ps.close();
                            con.close();
                        } catch (SQLException ex) {
                            System.out.println(ex.toString());
                        }
                    }

                }




            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                buttonClicked = true;

                Connection con = DbConnection.connect();
                String deleteStandardTask = "DELETE FROM StandardTask WHERE task_id=?";

                try (PreparedStatement ps_deleteStandardTask = con.prepareStatement(deleteStandardTask))
                {

                    System.out.println("here");

                    //ps_deleteStandardTask = con.prepareStatement(deleteStandardTask);
                    ps_deleteStandardTask.setString(1, taskIDComboBox.getSelectedItem().toString());
                    ps_deleteStandardTask.executeUpdate();


                    descriptionTextArea.setText("");
                    priceTextbox.setText("");
                    durationTextbox.setText("");
                    taskIDComboBox.removeItem(taskIDComboBox.getSelectedItem().toString());
                    roomComboBox.setSelectedItem("");

                    JOptionPane.showMessageDialog(editRemoveTaskPanel, "Task deleted.");


                } catch (SQLException ex) {
                    //System.out.println(ex.toString());
                    //if ((contactNameTextbox.getText() != "") && (buttonClicked == false)) {
                    JOptionPane.showMessageDialog(editRemoveTaskPanel, "Failed to delete task.");
                    //}
                } finally {
                    try {
                        //rs.close();
                        //ps.close();
                        con.close();
                    } catch (SQLException ex) {
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
