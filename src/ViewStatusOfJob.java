import javax.swing.*;

public class ViewStatusOfJob extends JFrame {
    private JPanel viewStatusOfJobPanel;
    private JTextField jobIDTextbox;
    private JTextArea viewStatusOfJobTextArea;
    private JLabel jobIDLabel;
    private JMenuBar viewStatusOfJobMenuBar;
    private JMenu logoutMenu;
    private JMenu homeMenu;

    public ViewStatusOfJob(String title){
        super(title);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(viewStatusOfJobPanel);
        this.pack();


    }

    public static void main(String[] args){
        JFrame frame = new ViewStatusOfJob("View Status Of Job");
        frame.setVisible(true);
    }

}
