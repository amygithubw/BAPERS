import javax.swing.*;

public class CustomerReport extends JFrame {
    private JPanel customerReportPanel;
    private JTextField fromTextbox;
    private JTextField toTextbox;
    private JTextField customerIDTextbox;
    private JButton generateButton;
    private JTextArea customerReportTextArea;
    private JButton cancelButton;
    private JButton printButton;
    private JButton autoGenerateButton;
    private JLabel customerIDLabel;
    private JMenuBar customerReportMenuBar;
    private JMenu logoutMenu;
    private JMenu homeMenu;

    public CustomerReport(String title){
        super(title);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(customerReportPanel);
        this.pack();


    }

    public static void main(String[] args){
        JFrame frame = new CustomerReport("Customer Report");
        frame.setVisible(true);
    }

}
