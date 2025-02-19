import java.sql.*;


public class main {

    //create var.
    private static final String url = "jdbc:mysql://localhost:3306/mybd";

    private static final String username = "root";

    private static final  String password = "1234";


    public static void main(String[] args) {

        //if class not found.
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
        catch (ClassNotFoundException e){
            System.out.println(e.getMessage());
        }

        //connection to db.
        try{
            Connection connection = DriverManager.getConnection(url,username,password);
            Statement statement = connection.createStatement();//create statement.
            String query = "select * from student";//creating query.
            ResultSet resultSet = statement.executeQuery(query);//create resultSet.
            while(resultSet.next()){//it will print the result set till the last row and column.
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                Double marks = resultSet.getDouble("marks");
                System.out.println("ID: " + id);
                System.out.println("Name: " + name);
                System.out.println("Marks :" + marks);


            }
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }

    }
}
