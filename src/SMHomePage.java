import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SMHomePage extends JFrame {
    private JPanel smHomePagePanel;
    private JButton inspectTasksButton;
    private JButton generateReportButton;
    private JButton takePaymentButton;
    private JButton createJobButton;
    private JButton statusEnquiryButton;
    private JButton viewAJobButton;
    private JMenu logoutMenu;

    public SMHomePage(String title){
        super(title);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(smHomePagePanel);
        this.pack();


        inspectTasksButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                InspectTask inspectTask = new InspectTask("Inspect Task");
                inspectTask.setVisible(true);
            }
        });
        takePaymentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TakePayment tp = new TakePayment("Take Payment");
                tp.setVisible(true);
            }
        });
        statusEnquiryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StatusEnquiry se = new StatusEnquiry("Status Enquiry");
                se.setVisible(true);
            }
        });
        generateReportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GenerateReport generateReport = new GenerateReport("Generate Report");
                generateReport.setVisible(true);
            }
        });
        createJobButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CreateJob createJob = new CreateJob("Create Job");
                createJob.setVisible(true);
            }
        });
        viewAJobButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ViewAllJobs viewAllJobs = new ViewAllJobs("View Jobs"); //check if right one
                viewAllJobs.setVisible(true);
            }
        });
    }
    
}
