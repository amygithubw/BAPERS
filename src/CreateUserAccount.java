import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreateUserAccount extends JFrame{
    private JPanel createUserAccountPanel;
    private JTextField firstnameTextbox;
    private JTextField surnameTextbox;
    private JTextField emailTextbox;
    private JTextField contactNoTextbox;
    private JTextField usernameTextbox;
    private JTextField passwordTextbox;
    private JTextField staffIDTextbox;
    private JLabel firstnameLabel;
    private JLabel surnameLabel;
    private JLabel emailLabel;
    private JLabel contactNoLabel;
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JLabel staffIDLabel;
    private JLabel accessPrivilegeLabel;
    private JComboBox accessPrivilegeComboBox;
    private JMenuBar createUserAccountMenuBar;
    private JMenu logoutMenu;
    private JMenu homeMenu;
    private JButton saveButton;
    private JButton cancelButton;

    public CreateUserAccount(String title){
        super(title);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(createUserAccountPanel);
        this.pack();
    }

    public static void main(String[] args){
        JFrame frame = new CreateUserAccount("Create User Account");
        frame.setVisible(true);
    }

}
