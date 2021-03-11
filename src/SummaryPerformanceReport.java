import javax.swing.*;

public class SummaryPerformanceReport extends JFrame {
    private JPanel summaryPerformanceReportPanel;
    private JTextField fromTextbox;
    private JTextField toTextbox;
    private JTextArea summaryPerformanceReportTextArea;
    private JButton autoGenerateButton;
    private JButton printButton;
    private JButton cancelButton;
    private JLabel fromLabel;
    private JLabel toLabel;
    private JMenuBar summaryPerformanceReportMenuBar;
    private JMenu logoutMenu;
    private JMenu homeMenu;

    public SummaryPerformanceReport(String title){
        super(title);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(summaryPerformanceReportPanel);
        this.pack();


    }

    public static void main(String[] args){
        JFrame frame = new SummaryPerformanceReport("Summary Performance Report");
        frame.setVisible(true);
    }

}
