import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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


        viewAllJobsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ViewAllJobs va = new ViewAllJobs("All Jobs");
                va.setVisible(true);
            }
        });
        viewJobsInProgrssButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ViewJobsInProgress jp = new ViewJobsInProgress("Jobs in Progress");
                jp.setVisible(true);
            }
        });
        viewStatusOfAButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ViewStatusOfJob sj = new ViewStatusOfJob("Job Status");
                sj.setVisible(true);
            }
        });
    }

    public static void main(String[] args){
        JFrame frame = new StatusEnquiry("Status Enquiry");
        frame.setVisible(true);
    }

}
