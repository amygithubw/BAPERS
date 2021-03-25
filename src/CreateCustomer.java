import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CreateCustomer extends JFrame {
    private JPanel createCustomerPanel;
    private JTextField customerNameTextbox;
    private JTextField contactNameTextbox;
    private JTextField emailTextbox;
    private JTextField phoneNoTextbox;
    private JTextField addressTextbox;
    private JButton saveButton;
    private JButton cancelButton;
    private JMenuBar createCustomerMenuBar;
    private JLabel customerNameLabel;
    private JLabel contactNameLabel;
    private JLabel emailLabel;
    private JLabel phoneNoLabel;
    private JLabel addressLabel;
    private JMenu logoutMenu;
    private JMenu homeMenu;

    public CreateCustomer(String title){
        super(title);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(createCustomerPanel);
        this.pack();


        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Connection con = DbConnection.connect();

                String insertCustomer = "INSERT INTO Customer(account_no, customer_name, contact_name, address, phone, email) VALUES (?,?,?,?,?,?)";
                String get_customerID = "SELECT count(account_no) FROM Customer";

                try (PreparedStatement ps_insertCustomer = con.prepareStatement(insertCustomer);
                    PreparedStatement ps_get_customerID = con.prepareStatement(get_customerID) )

                {
                    con.setAutoCommit(false);

                    ResultSet rs_get_customerID = ps_get_customerID.executeQuery();
                    int num = rs_get_customerID.getInt(1);
                    String formatted = String.format("%04d", (num+1));

                    ps_insertCustomer.setString(1, ( "ACC" + formatted) ) ;
                    ps_insertCustomer.setString(2, customerNameTextbox.getText());
                    ps_insertCustomer.setString(3, contactNameTextbox.getText());
                    ps_insertCustomer.setString(4, addressTextbox.getText());
                    ps_insertCustomer.setString(5, phoneNoTextbox.getText());
                    ps_insertCustomer.setString(6, emailTextbox.getText());
                    ps_insertCustomer.executeUpdate();

                    con.commit();

                    // if the phone number input is the same as any of the already stored numbers, error message
                    // if any of the textboxes are left blank, error message
                    // email address in correct format ???


                } catch(SQLException ex) {
                    //System.out.println(ex.toString());
                    JOptionPane.showMessageDialog(createCustomerPanel, "Failed to create customer.");
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

        // once the customer has been created, take the user back to their homepage

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                customerNameTextbox.setText(null);
                contactNameTextbox.setText(null);
                addressTextbox.setText(null);
                emailTextbox.setText(null);
                phoneNoTextbox.setText(null);

            }
        });
    }

    public static void main(String[] args){
        JFrame frame = new CreateCustomer("Create Customer");
        frame.setVisible(true);
    }

}
