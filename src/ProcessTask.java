import javax.swing.*;

public class ProcessTask extends JFrame {
    private JPanel processTaskPanel;
    private JTextField jobIDTextbox;
    private JTextArea processTaskTextArea;
    private JButton processTaskButton;
    private JMenuBar processTaskMenuBar;
    private JLabel jobIDLabel;
    private JMenu logoutMenu;
    private JMenu homeMenu;

    public ProcessTask(String title){
        super(title);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(processTaskPanel);
        this.pack();


    }

    public static void main(String[] args){
        JFrame frame = new ProcessTask("Process Task");
        frame.setVisible(true);
    }

}
