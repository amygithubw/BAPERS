import javax.swing.*;

public class CardPayment extends JFrame {
    private JPanel cardPaymentPanel;
    private JTextArea dateTextArea;
    private JTextArea totalTextArea;
    private JTextField cardTypeTextArea;
    private JTextField l4dTextArea;
    private JTextArea monthTextArea;
    private JTextArea yearTextArea;
    private JLabel slashLabel;
    private JButton confirmButton;
    private JLabel dateLabel;
    private JLabel totalLabel;
    private JLabel cardTypeLabel;
    private JLabel l4dLabel;
    private JLabel expiryDateLabel;

    public CardPayment(String title){
        super(title);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(cardPaymentPanel);
        this.pack();


    }

    public static void main(String[] args){
        JFrame frame = new CardPayment("Card Payment");
        frame.setVisible(true);
    }
    
}
