import javax.swing.*;

public class BackupDatabase extends JFrame {
    private JPanel backupDatabasePanel;
    private JButton backupDatabaseButton;
    private JButton automaticBackupConfigurationButton;
    private JMenu logoutMenu;
    private JMenu homeMenu;

    public BackupDatabase(String title){
        super(title);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(backupDatabasePanel);
        this.pack();


    }

    public static void main(String[] args){
        JFrame frame = new BackupDatabase("Backup Database");
        frame.setVisible(true);
    }

}
