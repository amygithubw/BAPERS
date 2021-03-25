import javax.swing.*;

public class AutoGenerate extends JFrame {
    private JPanel autoGeneratePanel;
    private JComboBox monthsComboBox;
    private JComboBox daysComboBox;
    private JComboBox hoursComboBox;
    private JButton saveButton;
    private JButton cancelButton;
    private JMenuBar autoGenerateMenuBar;
    private JLabel monthsLabel;
    private JLabel daysLabel;
    private JLabel hoursLabel;
    private JMenu logoutMenu;
    private JMenu homeMenu;
    private JLabel everyLabel;
    private JComboBox numbersComboBox;
    private JComboBox wordsComboBox;

    public AutoGenerate(String title){
        super(title);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(autoGeneratePanel);
        this.pack();


    }

    public static void main(String[] args){
        JFrame frame = new AutoGenerate("Auto Generate");
        frame.setVisible(true);
    }

}
