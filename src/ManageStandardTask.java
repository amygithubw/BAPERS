import javax.swing.*;

public class ManageStandardTask extends JFrame {
    private JPanel manageStandardTaskPanel;
    private JButton editRemoveTaskButton;
    private JButton addNewTaskButton;
    private JMenuBar manageStandardTaskMenuBar;
    private JMenu logoutMenu;
    private JMenu homeMenu;

    public ManageStandardTask(String title){
        super(title);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(manageStandardTaskPanel);
        this.pack();


    }

    public static void main(String[] args){
        JFrame frame = new ManageStandardTask("Manager Standard Task");
        frame.setVisible(true);
    }

}
