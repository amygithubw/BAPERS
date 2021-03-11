import javax.swing.*;

public class RestoreDatabase extends JFrame {
    private JPanel restoreDatabasePanel;
    private JButton restoreDatabaseButton;
    private JButton automaticBackupConfigurationButton;
    private JMenu logoutMenu;
    private JMenu homeMenu;

    public RestoreDatabase(String title){
        super(title);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(restoreDatabasePanel);
        this.pack();
    }

    public static void main(String[] args){
        JFrame frame = new RestoreDatabase("Restore Database");
        frame.setVisible(true);
    }

}
