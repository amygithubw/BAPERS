import javax.swing.*;

public class ViewAllJobs extends JFrame {
    private JPanel viewAllJobsPanel;
    private JTextArea viewAllJobsTextArea;
    private JMenuBar viewAllJobsMenuBar;
    private JMenu logoutMenu;
    private JMenu homeMenu;

    public ViewAllJobs(String title){
        super(title);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(viewAllJobsPanel);
        this.pack();


    }

    public static void main(String[] args){
        JFrame frame = new ViewAllJobs("View All Jobs");
        frame.setVisible(true);
    }

}
