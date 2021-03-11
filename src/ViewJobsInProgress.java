import javax.swing.*;

public class ViewJobsInProgress extends JFrame {
    private JPanel viewJobsInProgressPanel;
    private JTextArea viewJobsInProgressTextArea;
    private JMenuBar viewJobsInProgressMenuBar;
    private JMenu logoutMenu;
    private JMenu homeMenu;

    public ViewJobsInProgress(String title){
        super(title);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(viewJobsInProgressPanel);
        this.pack();


    }

    public static void main(String[] args){
        JFrame frame = new ViewJobsInProgress("View Jobs In Progress");
        frame.setVisible(true);
    }

}
