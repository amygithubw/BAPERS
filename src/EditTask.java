import javax.swing.*;

public class EditTask extends JFrame {
    private JPanel editTaskPanel;
    private JTextField taskIDTextbox;
    private JTextField descriptionTextbox;
    private JTextField locationTextbox;
    private JTextField durationTextbox;
    private JTextField priceTextbox;
    private JLabel taskIDLabel;
    private JLabel descriptionLabel;
    private JLabel locationLabel;
    private JLabel durationLabel;
    private JLabel priceLabel;
    private JButton saveButton;
    private JButton cancelButton;
    private JMenuBar editTaskMenuBar;
    private JMenu logoutMenu;
    private JMenu homeMenu;

    public EditTask(String title){
        super(title);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(editTaskPanel);
        this.pack();
    }

    public static void main(String[] args){
        JFrame frame = new EditTask("Edit Task");
        frame.setVisible(true);
    }

}
