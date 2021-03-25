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
    private JComboBox customerNameComboBox;
    private JButton cancelButton;

    String customerName;
    String contactName;
    String address;
    String email;
    String phoneNo;

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

                    if(customerNameComboBox.getItemCount() <= count) {
                    //if (customerNameComboBox.getSelectedItem() == "") {
                        //if (customerNameComboBox.getItemCount() <= count ) {
                        while (rs.next()) {
                            customerNameComboBox.addItem(rs.getString(1));
                        }
                        //}
                    }

                    // outputs all customers twice!?!?!?

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

                //System.out.println("clicked");
                customerNameComboBox.setEditable(true);

                Connection con = DbConnection.connect();
                PreparedStatement ps = null;
                ResultSet rs = null;

                customerName = customerNameComboBox.getSelectedItem().toString();
                System.out.println("customername:" + customerName);




                try {



                    //if (name.equals("")) {
                        //System.out.println("empty");
                        //JOptionPane.showMessageDialog(editCustomerPanel, "Please select customer.");
                    //} else {
                    //customerNameComboBox.setSelectedIndex(1);

                    if (customerNameComboBox.getSelectedItem() != "") {
                        //System.out.println(name);
                        String getCustomer = "SELECT contact_name, address, phone, email FROM Customer WHERE customer_name=?";
                        ps = con.prepareStatement(getCustomer);
                        ps.setString(1, customerName);
                        rs = ps.executeQuery();

                        contactName = rs.getString(1);
                        address = rs.getString(2);
                        phoneNo = rs.getString(3);
                        email = rs.getString(4);

                        contactNameTextbox.setText(contactName);
                        addressTextbox.setText(address);
                        phoneNoTextbox.setText(phoneNo);
                        emailTextbox.setText(email);
                    }



                } catch(SQLException ex) {
                    //System.out.println(ex.toString());
                    JOptionPane.showMessageDialog(editCustomerPanel, "No information stored about selected customer.");
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

        /*
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Connection con = DbConnection.connect();
                PreparedStatement ps = null;
                ResultSet rs = null;

                System.out.println("Edited:" + customerNameComboBox.getEditor().getItem());
                System.out.println("Original:" + customerName); // doesnt work


                // only run the sql update if at least one of the textboxes is different from data in db

                try {
                    String updateCustomerName = " UPDATE Customer Set customer_name=? WHERE customer_name=? ";
                    String updateContactName = " UPDATE Customer Set contact_name=\"Mr George Dave\" WHERE contact_name=\"Mr George Kali\" ";
                    String updateAddress = " UPDATE Customer Set address=\"Mr George Dave\" WHERE address=\"Mr George Kali\" ";
                    String updateEmail = " UPDATE Customer Set email=\"Mr George Dave\" WHERE email=\"Mr George Kali\" ";
                    String updatePhoneNo = " UPDATE Customer Set phone=\"Mr George Dave\" WHERE phone=\"Mr George Kali\" ";

                    ps = con.prepareStatement(updateCustomerName);
                    ps.setString(1, customerNameComboBox.getSelectedItem().toString());
                    ps.setString(2, customerName);
                    ps.executeQuery();



                } catch(SQLException ex) {
                    System.out.println(ex.toString());
                    JOptionPane.showMessageDialog(editCustomerPanel, "Failed to update customer details.");
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
        */

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //customerNameComboBox.setSelectedIndex(0);
                //customerNameComboBox.setSelectedItem("");
                contactNameTextbox.setText(null);
                addressTextbox.setText(null);
                emailTextbox.setText(null);
                phoneNoTextbox.setText(null);
                //customerNameComboBox.setSelectedIndex(0);
                // ^^ this makes a null point exception
                //customerNameComboBox.removeAllItems();

                //customerNameComboBox.setEditable(false);

            }
        });



    }


    public static void main(String[] args){
        JFrame frame = new EditCustomer("Edit Customer");
        frame.setVisible(true);

    }


}
