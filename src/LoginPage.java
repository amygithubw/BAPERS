import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginPage extends JFrame {
    private JPanel loginPanel;
    private JLabel usernameLabel;
    private JTextField usernameTextbox;
    private JLabel passwordLabel;
    private JButton loginButton;
    private JLabel loginLabel;
    private JPasswordField passwordTextbox;

    public LoginPage(String title){
        super(title);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(loginPanel);
        this.pack();



        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // check database to see if there is a username
                // see if the password matches the username
                // send the user to the home page of the system
                Connection con = DbConnection.connect();


                String un = usernameTextbox.getText();
                String pw = passwordTextbox.getText();

                PreparedStatement ps = null;
                ResultSet rs = null;


                try {
                    String sql = "SELECT username,password,staff_type FROM Staff WHERE username=? AND password=?";
                    ps = con.prepareStatement(sql);
                    ps.setString(1, un);
                    ps.setString(2,pw);
                    rs = ps.executeQuery();


                    String username = rs.getString(1);
                    // ^^ 1st item that we have asked for in the sql query
                    String password = rs.getString(2);
                    //String st = rs.getString(3);
                    //System.out.println(st);
                    //System.out.println(username + password);
                    if ((un.equals(username)) && (pw.equals(password))){
                        //System.out.println("You many enter");

                        System.out.println(rs.getString(3));
                        if (rs.getString(3).equals("A receptionist")){
                            // ^^ this is the 3rd item that we have asked for in the sql query
                            ReceptionistHomePage receptionistHomePage = new ReceptionistHomePage("Home Page");
                            receptionistHomePage.setVisible(true);
                        } else if (rs.getString(3).equals("Office manager")){
                            OMHomePage omHomePage = new OMHomePage("Home Page");
                            omHomePage.setVisible(true);
                        } else if (rs.getString(3).equals("Shift manager")){
                            SMHomePage smHomePage = new SMHomePage("Home Page");
                            smHomePage.setVisible(true);
                        } else if (rs.getString(3).equals("Technician")){
                            THomePage tHomePage = new THomePage("Home Page");
                            tHomePage.setVisible(true);
                        } else {
                            JOptionPane.showMessageDialog(loginPanel, "You do not have access to the system.");
                        }

                    } else {
                        JOptionPane.showMessageDialog(loginPanel, "Login Details Invalid");
                    }

                    /*
                    if(rs.next()) {
                        System.out.println("username correct");
                    } else {
                        System.out.println("username incorrect");
                    }
                    */


                    //ps.close();
                    //rs.close();
                    //con.close();

                } catch(SQLException ex) {
                    //System.out.println(ex.toString());
                    JOptionPane.showMessageDialog(loginPanel, "Login Details Invalid");
                } finally {
                    try {
                        rs.close();
                        ps.close();
                        con.close();
                    } catch(SQLException ex) {
                        System.out.println(ex.toString());
                    }
                }

/*
                if(un.equals("office_manager") && pw.equals("password")){
                    OMHomePage omHomePage = new OMHomePage("Home Page");
                    omHomePage.setVisible(true);
                } else if(un.equals("receptionist") && pw.equals("password")) {
                    ReceptionistHomePage receptionistHomePage = new ReceptionistHomePage("Home Page");
                    receptionistHomePage.setVisible(true);
                } else if(un.equals("shift_manager") && pw.equals("password")) {
                    SMHomePage smHomePage = new SMHomePage("Home Page");
                    smHomePage.setVisible(true);
                } else if(un.equals("technician") && pw.equals("password")) {
                    THomePage tHomePage = new THomePage("Home Page");
                    tHomePage.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(loginPanel, "Invalid");
                }
                */


            }
        });
    }

    /*
    public static void checkLoginDetails() {
        Connection con = DbConnection.connect();

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT username FROM Staff";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            String username = rs.getString(1);
            System.out.println(username);

        } catch(SQLException e) {
            System.out.println(e.toString());
        } finally {
            try {
                rs.close();
                ps.close();
                con.close();
            } catch(SQLException e) {
                System.out.println(e.toString());
            }

        }

     */


}
