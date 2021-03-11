import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ReceptionistHomePage extends JFrame {
    private JPanel receptionistHomePagePanel;
    private JButton createJobButton;
    private JButton takePaymentButton;
    private JMenuBar receptionistHomePageMenuBar;
    private JMenu logoutMenu;

    public ReceptionistHomePage(String title){
        super(title);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(receptionistHomePagePanel);
        this.pack();


        createJobButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CreateJob createJob = new CreateJob("Create Job");
                createJob.setVisible(true);
            }
        });
        takePaymentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TakePayment tp = new TakePayment("Take Payment");
                tp.setVisible(true);
            }
        });
    }

}
