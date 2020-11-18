import java.sql.*;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Main {
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://Full2020_086292:3306/AGLINKA";

    static final String USER = "root";
    static final String PASS = "root";

    static Scanner in = new Scanner( System.in);
    private static final String CREATE_TABLE_USERS = "CREATE TABLE IF NOT EXISTS Users (ID int, NAME varchar(255), SURNAME varchar(255), YEAR varchar(255), TYPE varchar(255) );";
    private static final String SELECT_ALL_FROM_USERS = "SELECT ID, NAME, SURNAME, YEAR, TYPE FROM Users";

    public static void main(String[] args) {

        try(Connection conn = DriverManager.getConnection(DB_URL,USER,PASS);
            Statement stmt = conn.createStatement()) {
            Class.forName("com.mysql.jdbc.Driver");
            TimeUnit.SECONDS.sleep(10);
            System.out.println("Connecting to database...");
            stmt.executeUpdate(CREATE_TABLE_USERS);
            String selectedOperation;
            do
            {
                System.out.println("1. Show DB data\n2. Insert user\n3. Edit user by ID\n4. Delete user by ID\nS. Press E to Exit");
                selectedOperation = in.nextLine();
                switch( selectedOperation )
                {
                    case "1" :
                        getResults(stmt);
                        break;
                    case "2" :
                        insertUser(stmt);
                        break;
                    case "3" :
                        updateUser(stmt);
                        break;
                    case "4" :
                        deleteUserById(stmt);
                        break;
                }
            }while (!selectedOperation.toUpperCase().equals("E"));
        } catch (InterruptedException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void deleteUserById(Statement stmt) throws SQLException {
        ResultSet rsss = stmt.executeQuery(SELECT_ALL_FROM_USERS);
        printOutHeader();
        printOutResult(rsss);
        rsss.close();
        System.out.println("Enter ID to delete");
        final String id = in.nextLine();
        final String deleteSql = " DELETE FROM Users WHERE ID= '"+id+"';";
        stmt.executeUpdate(deleteSql);
    }

    private static void updateUser(Statement stmt) throws SQLException {
        String type;
        String id;
        String name;
        String surname;
        String sql;
        String year;
        ResultSet rss = stmt.executeQuery(SELECT_ALL_FROM_USERS);
        printOutHeader();

        printOutResult(rss);
        rss.close();
        System.out.println("Enter ID to edit");
        id = in.nextLine();

        System.out.println("Name: ");
        name = in.nextLine();

        System.out.println("Surname: ");
        surname = in.nextLine();

        System.out.println("Year:");
        year = in.nextLine();

        System.out.println("Type");
        type = in.nextLine();
        sql = " UPDATE Users SET NAME = '"+name+"' , SURNAME = '"+surname+"', YEAR = '"+year+"',TYPE ='"+type+"' WHERE ID= '"+id+"';";
        stmt.executeUpdate(sql);
    }

    private static void insertUser(Statement stmt) throws SQLException {
        System.out.println("ID");
        final String id = in.nextLine();

        System.out.println("Name:");
        final String name = in.nextLine();

        System.out.println("Surname");
        final String surname = in.nextLine();

        System.out.println("Year:");
        final String year = in.nextLine();

        System.out.println("Type");
        final String type = in.nextLine();
        
        String sql = " INSERT INTO Users (ID, NAME, SURNAME, YEAR,TYPE) VALUES ('"+id+"', '"+name+"', '"+surname+"', '"+year+"','"+type+"')";
        stmt.executeUpdate(sql);
    }

    private static void getResults(Statement stmt) throws SQLException {
        ResultSet rs = stmt.executeQuery(SELECT_ALL_FROM_USERS);
        printOutHeader();
        printOutResult(rs);
        rs.close();
    }

    private static void printOutHeader() {
        System.out.println("ID    NAME    SURNAME    YEAR    TYPE");
    }

    private static void printOutResult(ResultSet rs) throws SQLException {
        int id;
        String first;
        String last;
        String address;
        String city;
        while (rs.next()) {
            id = rs.getInt("ID");
            first = rs.getString("NAME");
            last = rs.getString("SURNAME");
            address = rs.getString("YEAR");
            city = rs.getString("TYPE");

            System.out.println(id + "    " + first + "    " + last + "    " + address + "    " + city);
        }
    }
}
