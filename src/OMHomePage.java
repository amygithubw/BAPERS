import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OMHomePage extends JFrame {
    private JPanel omHomePagePanel;
    private JButton backupDatabaseButton;
    private JButton statusEnquiryButton;
    private JButton editRemoveTaskButton;
    private JButton inspectTasksButton;
    private JButton generateReportButton;
    private JButton processTaskButton;
    private JButton takePaymentButton;
    private JButton createJobButton;
    private JButton createCustomerAccountButton;
    private JButton manageStandardTaskButton;
    private JButton addNewTaskButton;
    private JButton manageCustomerAccountButton;
    private JButton restoreDatabaseButton;
    private JMenu logoutMenu;
    private JMenuBar OMHomePageMenuBar;

    public OMHomePage(String title){
        super(title);

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setContentPane(omHomePagePanel);
        this.pack();


        statusEnquiryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StatusEnquiry se = new StatusEnquiry("Status Enquiry");
                se.setVisible(true);
            }
        });
        inspectTasksButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                InspectTask it = new InspectTask("Inspect Task");
                it.setVisible(true);

            }
        });
        takePaymentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TakePayment tp = new TakePayment("Take Payment");
                tp.setVisible(true);

            }
        });
        addNewTaskButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


            }
        });
        manageCustomerAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //check
            }
        });
        restoreDatabaseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RestoreDatabase rdb = new RestoreDatabase("Restore Database");
                rdb.setVisible(true);
            }
        });
        editRemoveTaskButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Edit_RemoveTask edit_removeTask = new Edit_RemoveTask("edit/remove task");
                edit_removeTask.setVisible(true);
            }
        });
        processTaskButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ProcessTask processTask = new ProcessTask("Process Task");
                processTask.setVisible(true);
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
        createCustomerAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CreateCustomer createCustomer = new CreateCustomer("Create Customer");
                createCustomer.setVisible(true);
            }
        });
        manageStandardTaskButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ManageStandardTask manageStandardTask = new ManageStandardTask(" Manage Task");
                manageStandardTask.setVisible(true);
            }
        });
        backupDatabaseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BackupDatabase backupDatabase = new BackupDatabase("Backup Database");
                backupDatabase.setVisible(true);
            }
        });
        logoutMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //OMHomePage
            }
        });
    }

}
