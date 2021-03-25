import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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


        activeCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Connection con = DbConnection.connect();


                PreparedStatement ps = null;
                ResultSet rs = null;



                try {

                    String sql = "SELECT Job_StandardTask.Jobjob_id, StandardTask.task_description, Job_StandardTask.start_time, Job_StandardTask.time_taken FROM StandardTask\n" +
                            "LEFT JOIN Job_StandardTask ON Job_StandardTask.StandardTasktask_id = StandardTask.task_id\n" +
                            "WHERE Job_StandardTask.start_time IS NOT NULL AND Job_StandardTask.time_taken IS NOT NULL\n" +
                            "ORDER BY Job_StandardTask.Jobjob_id\n";
                    //^^ Not sure how to implement long SQL statements
                    ps = con.prepareStatement(sql);


                    rs = ps.executeQuery();
                    List<String> stringList = new ArrayList<String>();
                    while (rs.next()){
                        String jobID = rs.getString("Jobjob_id");
                        String taskDescription = rs.getString("task_description");
                        String startTime = rs.getTime("start_time").toString();
                        String timeTaken = rs.getTime("time_taken").toString();
                        String toShow = jobID + " " + taskDescription + " " + startTime + " " + timeTaken;
                        stringList.add(toShow);
                        System.out.print("");


                    }

                    for (String s: stringList){
                        inspectTaskTextArea.append(s + "\n");
                    }

                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(inspectTaskPanel, "Cannot Inspect Tasks");
                } finally {
                    try {
                        rs.close();
                        ps.close();
                        con.close();
                    } catch(SQLException ex) {
                        System.out.println(ex.toString());
                    }
                }


            }
        });



        pendingCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // check database to see if there is a username
                // see if the password matches the username
                // send the user to the home page of the system
                Connection con = DbConnection.connect();


                PreparedStatement ps = null;
                ResultSet rs = null;

                try {

                    String pending_sql = "SELECT Job_StandardTask.Jobjob_id, Job_StandardTask.StandardTasktask_id, StandardTask.task_description FROM StandardTask LEFT JOIN Job_StandardTask ON Job_StandardTask.StandardTasktask_id = StandardTask.task_id WHERE Job_StandardTask.start_time IS NULL AND Job_StandardTask.time_taken IS NULL";

                    String sql = "SELECT task_id FROM StandardTask";
                    ps = con.prepareStatement(pending_sql);

                    ArrayList<String> stringList = new ArrayList<String>();
                    rs = ps.executeQuery();
                    while (rs.next()){
                        String jobID = rs.getString("Jobjob_id");
                        String taskDescription = rs.getString("task_description");
                        String startTime = rs.getTime("start_time").toString();
                        String timeTaken = rs.getTime("time_taken").toString();
                        String toShow = jobID + " " + taskDescription + " " + startTime + " " + timeTaken;
                        stringList.add(toShow);
                        System.out.print("");


                    }

                    for (String y: stringList){
                        inspectTaskTextArea.append(y);
                    }

                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(inspectTaskPanel, "Cannot Inspect Tasks");
                } finally {
                    try {
                        rs.close();
                        ps.close();
                        con.close();
                    } catch(SQLException ex) {
                        System.out.println(ex.toString());
                    }
                }


            }
        });



        completedCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // check database to see if there is a username
                // see if the password matches the username
                // send the user to the home page of the system
                Connection con = DbConnection.connect();


                PreparedStatement ps = null;
                ResultSet rs = null;

                try {

                    String sql = "SELECT task_id FROM StandardTask";
                    ps = con.prepareStatement(sql);
                    ArrayList<String> stringList = new ArrayList<String>();
                    rs = ps.executeQuery();
                    while (rs.next()){
                        String jobID = rs.getString("Jobjob_id");
                        String taskDescription = rs.getString("task_description");
                        String startTime = rs.getTime("start_time").toString();
                        String timeTaken = rs.getTime("time_taken").toString();
                        String toShow = jobID + " " + taskDescription + " " + startTime + " " + timeTaken;
                        stringList.add(toShow);
                        System.out.print("");
                    }

                    for (String x: stringList){
                        inspectTaskTextArea.append(x + "\n");
                    }

                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(inspectTaskPanel, "Cannot Inspect Tasks");
                } finally {
                    try {
                        rs.close();
                        ps.close();
                        con.close();
                    } catch(SQLException ex) {
                        System.out.println(ex.toString());
                    }
                }


            }
        });


    }

    public static void main(String[] args){
        JFrame frame = new InspectTask("Inspect Task");
        frame.setVisible(true);
    }

}
