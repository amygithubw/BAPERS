public class Main {
    public static void main(String[] args){

        DbConnection.connect();

        LoginPage lp = new LoginPage("Login Page");
        lp.setVisible(true); // add a size relative to screen size
        System.out.println("change");

        //LoginPage.checkLoginDetails();



    }
}
