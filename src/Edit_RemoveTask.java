import javax.swing.*;

public class Edit_RemoveTask extends JFrame {

    private JPanel editRemoveTaskPanel;
    private JButton editButton;
    private JButton removeButton;
    private JTextArea editRemoveTextArea;
    private JMenuBar editRemoveTaskMenuBar;
    private JMenu logoutMenu;
    private JMenu homeMenu;

    public Edit_RemoveTask(String title){
        super(title);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(editRemoveTaskPanel);
        this.pack();


    }

    public static void main(String[] args){
        JFrame frame = new Edit_RemoveTask("Edit/Remove Task");
        frame.setVisible(true);
    }

}
