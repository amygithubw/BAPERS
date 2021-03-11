import javax.swing.*;

public class GenerateReport extends JFrame {
    private JPanel generateReportPanel;
    private JButton customerReportButton;
    private JButton staffIndividualPerformanceReportButton;
    private JButton summaryPerformanceReportButton;
    private JMenu logoutMenu;
    private JMenuBar generateReportMenuBar;
    private JMenu homeMenu;

    public GenerateReport(String title){
        super(title);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(generateReportPanel);
        this.pack();


    }

    public static void main(String[] args){
        JFrame frame = new GenerateReport("Generate Report");
        frame.setVisible(true);
    }

}
