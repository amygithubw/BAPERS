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


    // this needs to be changed a lot
    // "updates status of job by recording completion of current task"
    // if all of the tasks in a job have a start time and time taken then
    // job = completed

    // if one of the tasks in a job has a start time then
    // job = progress


    // i dont think remove task is a thing??
    // "no job cancellations or changes to job"


    // ****
    // taskIDTexbox needs to display whatever was selected from the edit/remove interface in jlist




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
