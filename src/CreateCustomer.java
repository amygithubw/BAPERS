import javax.swing.*;

public class CreateCustomer extends JFrame {
    private JPanel createCustomerPanel;
    private JTextField firstNameTextbox;
    private JTextField surnameTextbox;
    private JTextField emailTextbox;
    private JTextField contactNoTextbox;
    private JTextField customerIDTextbox;
    private JButton saveButton;
    private JButton cancelButton;
    private JMenuBar createCustomerMenuBar;
    private JLabel firstNameLabel;
    private JLabel surnameLabel;
    private JLabel emailLabel;
    private JLabel contactNoLabel;
    private JLabel customerIDLabel;
    private JMenu logoutMenu;
    private JMenu homeMenu;

    public CreateCustomer(String title){
        super(title);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(createCustomerPanel);
        this.pack();


    }

    public static void main(String[] args){
        JFrame frame = new CreateCustomer("Create Customer");
        frame.setVisible(true);
    }

}
