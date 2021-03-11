import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

                String un = usernameTextbox.getText();
                String pw = passwordTextbox.getText();

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

            }
        });
    }


}
