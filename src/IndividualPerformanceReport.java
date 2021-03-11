import javax.swing.*;

public class IndividualPerformanceReport extends JFrame {
    private JPanel individualPerformanceReportPanel;
    private JTextField fromTextbox;
    private JTextField toTextbox;
    private JTextArea individualPerformanceReportTextArea;
    private JButton autoGenerateButton;
    private JButton printButton;
    private JButton cancelButton;
    private JMenuBar individualPerformanceReportMenuBar;
    private JLabel fromLabel;
    private JLabel toLabel;
    private JMenu logoutMenu;
    private JMenu homeMenu;

    public IndividualPerformanceReport(String title){
        super(title);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(individualPerformanceReportPanel);
        this.pack();


    }

    public static void main(String[] args){
        JFrame frame = new IndividualPerformanceReport("Individual Performance Report");
        frame.setVisible(true);
    }

}
