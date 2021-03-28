import javax.swing.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EditCustomer extends JFrame {
    private JPanel editCustomerPanel;
    private JTextField contactNameTextbox;
    private JTextField addressTextbox;
    private JTextField emailTextbox;
    private JTextField phoneNoTextbox;
    private JButton updateButton;
    private JLabel customerNameLabel;
    private JLabel contactNameLabel;
    private JLabel addressLabel;
    private JLabel emailLabel;
    private JLabel phoneNoLabel;
    private JComboBox<String> customerNameComboBox;
    private JButton cancelButton;

    String[] customer_names = {""};

    String uneditedCustomerName;
    String customerName;
    String uneditedContactName;
    String contactName;
    String uneditedAddress;
    String address;
    String uneditedEmail;
    String email;
    String uneditedPhoneNo;
    String phoneNo;

    boolean buttonClicked = false;

    public EditCustomer(String title) {

        super(title);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(editCustomerPanel);
        this.pack();

        customerNameComboBox.addItem("");


        editCustomerPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);

                //System.out.println("Edited:" + customerName);
                //System.out.println("Original:" + uneditedCustomerName);

                int count = 1;

                Connection con = DbConnection.connect();
                PreparedStatement ps = null;
                ResultSet rs = null;

                try {

                    String getCustomerCount = "SELECT count(account_no) FROM Customer";

                    ps = con.prepareStatement(getCustomerCount);
                    rs = ps.executeQuery();

                    count += rs.getInt(1);



                } catch(SQLException ex) {
                    System.out.println(ex.toString());
                    //JOptionPane.showMessageDialog(editCustomerPanel, "No information stored about selected customer.");
                }


                // displays account id's of customer in the Customer combo box
                try {

                    String sql = "SELECT customer_name FROM Customer";
                    ps = con.prepareStatement(sql);
                    rs = ps.executeQuery();

                    if(customerNameComboBox.getItemCount() == 1) {
                    //if (customerNameComboBox.getSelectedItem() == "") {
                        //if (customerNameComboBox.getItemCount() <= count ) {
                        while (rs.next()) {
                            customerNameComboBox.addItem(rs.getString(1));
                            uneditedCustomerName = rs.getString(1);
                        }
                        //}
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

        customerNameComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                buttonClicked = true;

                //System.out.println("clicked");
                customerNameComboBox.setEditable(true);

                Connection con = DbConnection.connect();
                PreparedStatement ps = null;
                ResultSet rs = null;

                customerName = customerNameComboBox.getSelectedItem().toString();
                //System.out.println("customername:" + customerName);


                if (customerName != "") {

                    try {

                        //if (name.equals("")) {
                        //System.out.println("empty");
                        //JOptionPane.showMessageDialog(editCustomerPanel, "Please select customer.");
                        //} else {
                        //customerNameComboBox.setSelectedIndex(1);


                        //System.out.println(name);
                        String getCustomer = "SELECT contact_name, address, phone, email FROM Customer WHERE customer_name=?";
                        ps = con.prepareStatement(getCustomer);
                        ps.setString(1, customerName);
                        rs = ps.executeQuery();

                        uneditedContactName = rs.getString(1);
                        uneditedAddress = rs.getString(2);
                        uneditedPhoneNo = rs.getString(3);
                        uneditedEmail = rs.getString(4);

                        contactNameTextbox.setText(uneditedContactName);
                        addressTextbox.setText(uneditedAddress);
                        phoneNoTextbox.setText(uneditedPhoneNo);
                        emailTextbox.setText(uneditedEmail);


                    } catch (SQLException ex) {
                        //System.out.println(ex.toString());
                        if ((contactNameTextbox.getText() != "") && (buttonClicked == false)) {
                            JOptionPane.showMessageDialog(editCustomerPanel, "No information stored about selected customer.");
                        }
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




            }
        });


        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                buttonClicked = true;

                contactName = contactNameTextbox.getText();
                address = addressTextbox.getText();
                phoneNo = phoneNoTextbox.getText();
                email = emailTextbox.getText();

                //System.out.println("was:" + uneditedContactName);
                //System.out.println("changed to:" + contactName);

                //Connection con = DbConnection.connect();
                //PreparedStatement ps = null;
                //ResultSet rs = null;

                /*
                System.out.println("Edited:" + customerName);
                System.out.println("Original:" + uneditedCustomerName);
                if (customerName != uneditedCustomerName){
                    System.out.println("not the same");
                }

                 */

                // only run the sql update if at least one of the textboxes is different from data in db

                //if ((customerNameComboBox.getSelectedItem().toString() != uneditedCustomerName) && (customerNameComboBox.getSelectedItem().toString() != "")) {

                if (!customerNameComboBox.getSelectedItem().toString().equals(uneditedCustomerName) && (!customerNameComboBox.getSelectedItem().toString().equals(""))) {
                    System.out.println("customer name changed");
                    Connection con = DbConnection.connect();

                    //System.out.println("here");
                    String updateCustomerName = "UPDATE Customer Set customer_name=? WHERE customer_name=?";

                    try ( PreparedStatement ps_updateCustomerName = con.prepareStatement(updateCustomerName) )
                    {

                        //ps_updateCustomerName = con.prepareStatement(updateCustomerName);
                        ps_updateCustomerName.setString(1, customerNameComboBox.getSelectedItem().toString());
                        ps_updateCustomerName.setString(2, uneditedCustomerName);
                        ps_updateCustomerName.executeUpdate();

                        customerNameComboBox.removeItem(uneditedCustomerName);
                        customerNameComboBox.addItem(customerName);
                        uneditedCustomerName = customerName;
                        contactNameTextbox.setText("");
                        addressTextbox.setText("");
                        emailTextbox.setText("");
                        phoneNoTextbox.setText("");
                        customerNameComboBox.setEditable(false);
                        JOptionPane.showMessageDialog(editCustomerPanel, "Customer details updated.");

                        //customerNameComboBox.setSelectedItem(customerName);

                        //System.out.println("it worked!?!?!?!?!?!?");

                    } catch(SQLException ex) {
                    System.out.println(ex.toString());
                    //JOptionPane.showMessageDialog(createJobPanel, "Failed to create job.");
                } finally {
                    try {
                        //rs.close();
                        //ps.close();
                        con.close();
                    } catch(SQLException ex) {
                        System.out.println(ex.toString());
                    }
                }
                    //String[] changedCustomerName = {"", }

                    //System.out.println("changed");








                    /*
                    try {

                        //String updateContactName = " UPDATE Customer Set contact_name=\"Mr George Dave\" WHERE contact_name=\"Mr George Kali\" ";
                        //String updateAddress = " UPDATE Customer Set address=\"Mr George Dave\" WHERE address=\"Mr George Kali\" ";
                        //String updateEmail = " UPDATE Customer Set email=\"Mr George Dave\" WHERE email=\"Mr George Kali\" ";
                        //String updatePhoneNo = " UPDATE Customer Set phone=\"Mr George Dave\" WHERE phone=\"Mr George Kali\" ";


                        String updateCustomerName = "UPDATE Customer Set customer_name=? WHERE customer_name=?";
                        ps = con.prepareStatement(updateCustomerName);
                        ps.setString(1, customerNameComboBox.getSelectedItem().toString());
                        ps.setString(2, uneditedCustomerName);
                        ps.executeUpdate();


                        // need to then delete all items from combobox so that updated ones can be added


                        //contactNameTextbox.setText("");
                        //addressTextbox.setText("");
                        //emailTextbox.setText("");
                        //phoneNoTextbox.setText("");
                        //customerNameComboBox.setEditable(false);
                        //customerNameComboBox.setSelectedItem(customerName);


                        //System.out.println("updated");


                    } catch (SQLException ex) {
                        System.out.println(ex.toString());
                        JOptionPane.showMessageDialog(editCustomerPanel, "Failed to update customer details.");
                    } finally {
                        try {
                            rs.close();
                            ps.close();
                            con.close();
                        } catch (SQLException ex) {
                            System.out.println(ex.toString());
                        }
                    }

                     */


                    
                }

                //if ((contactNameTextbox.getText() != uneditedContactName) && (contactNameTextbox.getText() != "")) {

                if (!contactNameTextbox.getText().equals(uneditedContactName) && (!contactNameTextbox.getText().equals(""))) {
                    Connection con = DbConnection.connect();

                    System.out.println("contact name changed");
                    String updateContactName = "UPDATE Customer Set contact_name=? WHERE contact_name=?";
                    //String getCustomerName = "SELECT customer_name FROM Customer WHERE contact_name=?";

                    try (PreparedStatement ps_updateContactName = con.prepareStatement(updateContactName)) {

                        //ps_updateCustomerName = con.prepareStatement(updateCustomerName);
                        ps_updateContactName.setString(1, contactName);
                        ps_updateContactName.setString(2, uneditedContactName);
                        ps_updateContactName.executeUpdate();

                        contactNameTextbox.setText("");
                        addressTextbox.setText("");
                        emailTextbox.setText("");
                        phoneNoTextbox.setText("");
                        customerNameComboBox.setEditable(false);

                        //ps_getCustomerName.setString(1, contactName);
                        //ResultSet rs_getCustomerName = ps_getCustomerName.executeQuery();

                        //System.out.println("it worked!?!?!?!?!?!?");

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

                    //customerNameComboBox.removeItem(uneditedContactName);
                    //customerNameComboBox.addItem(contactName);


                }




                //if ((address != uneditedAddress) && (address != "")) {
                if (!address.equals(uneditedAddress) && (!address.equals(""))) {
                    System.out.println("address changed");

                    Connection con = DbConnection.connect();

                    //System.out.println("here");
                    String updateAddress = "UPDATE Customer Set address=? WHERE address=?";

                    try (PreparedStatement ps_updateAddress = con.prepareStatement(updateAddress)) {

                        //ps_updateCustomerName = con.prepareStatement(updateCustomerName);
                        ps_updateAddress.setString(1, address);
                        ps_updateAddress.setString(2, uneditedAddress);
                        ps_updateAddress.executeUpdate();

                        contactNameTextbox.setText("");
                        addressTextbox.setText("");
                        emailTextbox.setText("");
                        phoneNoTextbox.setText("");
                        customerNameComboBox.setEditable(false);

                        //System.out.println("it worked!?!?!?!?!?!?");

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

                    //customerNameComboBox.removeItem(uneditedContactName);
                    //customerNameComboBox.addItem(contactName);


                }



                if (!email.equals(uneditedEmail) && (!email.equals(""))) {
                    System.out.println("email changed");

                    Connection con = DbConnection.connect();

                    //System.out.println("here");
                    String updateEmail = "UPDATE Customer Set email=? WHERE email=?";

                    try (PreparedStatement ps_updateEmail = con.prepareStatement(updateEmail)) {

                        //ps_updateCustomerName = con.prepareStatement(updateCustomerName);
                        ps_updateEmail.setString(1, email);
                        ps_updateEmail.setString(2, uneditedEmail);
                        ps_updateEmail.executeUpdate();

                        contactNameTextbox.setText("");
                        addressTextbox.setText("");
                        emailTextbox.setText("");
                        phoneNoTextbox.setText("");
                        customerNameComboBox.setEditable(false);

                        //System.out.println("it worked!?!?!?!?!?!?");

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

                    //customerNameComboBox.removeItem(uneditedContactName);
                    //customerNameComboBox.addItem(contactName);


                }


                if (!phoneNo.equals(uneditedPhoneNo) && (!phoneNo.equals(""))) {
                    System.out.println("phone no changed");

                    Connection con = DbConnection.connect();

                    //System.out.println("here");
                    String updatePhoneNo = "UPDATE Customer Set phone=? WHERE phone=?";

                    try (PreparedStatement ps_updatePhoneNo = con.prepareStatement(updatePhoneNo)) {

                        //ps_updateCustomerName = con.prepareStatement(updateCustomerName);
                        ps_updatePhoneNo.setString(1, phoneNo);
                        ps_updatePhoneNo.setString(2, uneditedPhoneNo);
                        ps_updatePhoneNo.executeUpdate();

                        contactNameTextbox.setText("");
                        addressTextbox.setText("");
                        emailTextbox.setText("");
                        phoneNoTextbox.setText("");
                        customerNameComboBox.setEditable(false);

                        //System.out.println("it worked!?!?!?!?!?!?");

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


        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //customerNameComboBox.setSelectedIndex(0);
                //customerNameComboBox.setSelectedItem("");
                contactNameTextbox.setText("");
                addressTextbox.setText("");
                emailTextbox.setText("");
                phoneNoTextbox.setText("");

                //customerNameComboBox.removeAllItems();
                //customerNameComboBox.addItem("");
                customerNameComboBox.setSelectedIndex(0);


                //customerNameComboBox.removeAllItems();

                customerNameComboBox.setEditable(false);

            }
        });



    }


    public static void main(String[] args){
        JFrame frame = new EditCustomer("Edit Customer");
        frame.setVisible(true);

    }


}
