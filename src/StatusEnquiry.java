import javax.swing.*;

public class StatusEnquiry extends JFrame {
    private JPanel statusEnquiryPanel;
    private JButton viewAllJobsButton;
    private JButton viewJobsInProgrssButton;
    private JButton viewStatusOfAButton;
    private JMenuBar statusEnquiryMenuBar;
    private JMenu logoutMenu;
    private JMenu homeMenu;

    public StatusEnquiry(String title){
        super(title);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(statusEnquiryPanel);
        this.pack();


    }

    public static void main(String[] args){
        JFrame frame = new StatusEnquiry("Status Enquiry");
        frame.setVisible(true);
    }

}
