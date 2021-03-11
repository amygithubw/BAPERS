import javax.swing.*;

public class InspectTask extends JFrame {
    private JPanel inspectTaskPanel;
    private JCheckBox activeCheckBox;
    private JCheckBox pendingCheckBox;
    private JCheckBox completedCheckBox;
    private JTextArea inspectTaskTextArea;
    private JMenuBar inspectTaskMenuBar;
    private JMenu logoutMenu;
    private JMenu homeMenu;

    public InspectTask(String title){
        super(title);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(inspectTaskPanel);
        this.pack();


    }

    public static void main(String[] args){
        JFrame frame = new InspectTask("Inspect Task");
        frame.setVisible(true);
    }

}
