import javax.swing.*;

public class AutoBackupConfig extends JFrame {
    private JPanel autoBackupConfigPanel;
    private JComboBox monthsComboBox;
    private JComboBox daysComboBox;
    private JComboBox hoursComboBox;
    private JLabel monthsLabel;
    private JLabel daysLabel;
    private JLabel hoursLabel;
    private JButton saveButton;
    private JButton cancelButton;
    private JMenuBar autoBackupConfigMenuBar;
    private JMenu logoutMenu;
    private JMenu homeMenu;

    public AutoBackupConfig(String title){
        super(title);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(autoBackupConfigPanel);
        this.pack();


    }

    public static void main(String[] args){
        JFrame frame = new AutoBackupConfig("Auto Backup Configuration");
        frame.setVisible(true);
    }

}
