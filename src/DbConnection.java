import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {

    public static Connection connect(){
        Connection con = null;
        try {
            Class.forName("org.sqlite.JDBC");
            String url = "jdbc:sqlite:C:\\Users\\User\\Documents\\AMY\\A. UNI\\Year 2\\Term 2\\team project\\BPS.db";
            con = DriverManager.getConnection(url);
            //con = DriverManager.getConnection("jdbc:sqlite:BAPERS.db");
            //System.out.println("Connection successful.");
        }
        catch (ClassNotFoundException | SQLException e) {
            System.out.println(e + "Connection failed.");
        }

        return con;
    }

}
