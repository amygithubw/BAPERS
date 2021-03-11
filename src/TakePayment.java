import javax.swing.*;

public class TakePayment extends JFrame {
    private JPanel takePaymentPanel;
    private JTextField customerIDTextbox;
    private JComboBox jobIDComboBox;
    private JButton addJobButton;
    private JTextArea paymentDetailsTextArea;
    private JTextArea dateTextArea;
    private JTextArea totalTextArea;
    private JButton cashButton;
    private JButton cancelButton;
    private JButton cardButton;
    private JLabel customerIDLabel;
    private JLabel jobIDLabel;
    private JLabel totalLabel;
    private JLabel dateLabel;
    private JMenuBar takePaymentMenuBar;
    private JMenu logoutMenu;
    private JMenu homeMenu;

    public TakePayment(String title){
        super(title);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(takePaymentPanel);
        this.pack();


    }

    public static void main(String[] args){
        JFrame frame = new TakePayment("Take Payment");
        frame.setVisible(true);
    }

}
