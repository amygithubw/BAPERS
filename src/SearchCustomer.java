import javax.swing.*;

public class SearchCustomer extends JFrame {

    private JPanel searchCustomerPanel;
    private JTextField customerIDTextbox;
    private JButton downgradeAccountButton;
    private JButton applyFixedDiscountButton;
    private JButton applyVariableDiscountButton;
    private JButton applyFlexibleDiscountButton;
    private JLabel customerIDLabel;
    private JComboBox searchedCustomersComboBox;
    private JMenuBar searchCustomerMenuBar;
    private JMenu logoutMenu;
    private JMenu homeMenu;

    public SearchCustomer(String title){
        super(title);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(searchCustomerPanel);
        this.pack();

    }

    public static void main(String[] args){
        JFrame frame = new SearchCustomer("Search Customer");
        frame.setVisible(true);
    }

}
