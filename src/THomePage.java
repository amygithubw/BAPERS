import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class THomePage extends JFrame {
    private JPanel tHomePagePanel;
    private JButton statusEnquiryButton;
    private JButton processTaskButton;
    private JButton inspectTasksButton;
    private JMenuBar tHomePageMenuBar;
    private JMenu logoutMenu;

    public THomePage(String title){
        super(title);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(tHomePagePanel);
        this.pack();


        statusEnquiryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StatusEnquiry statusEnquiry = new StatusEnquiry("Status Enquiry");
                statusEnquiry.setVisible(true);
            }
        });
        processTaskButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ProcessTask processTask = new ProcessTask("Process Task");
                processTask.setVisible(true);
            }
        });
        inspectTasksButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                InspectTask inspectTask = new InspectTask("Inspect Task");
                inspectTask.setVisible(true);
            }
        });
    }

}
