/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package las;

import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author alvinho0304
 */
public class DBConnector
{
    public static DBConnector dbConnector;
    
    public static DBConnector getInstance() throws ClassNotFoundException, SQLException
    {
        if(dbConnector == null)
        {
            dbConnector = new DBConnector();
        }
        return dbConnector;
    }
    
    //Database name
    private static final String db_name = "LAS";

    //Database username and password
    private static final String USER = "las";
    private static final String PW = "las";

    //DO NOT MODIFY THIS TWO LINES!!!
    private static final String DRIVER = "org.apache.derby.jdbc.ClientDriver";
    private static final String JDBC_URL = "jdbc:derby://localhost:1527/" + db_name + ";create=true";

    static Connection conn = null;
    
    //DO NOT MODIFY THIS OPERATION!!!
    private DBConnector() throws SQLException, ClassNotFoundException
    {
    }
    
    public static void connect() throws ClassNotFoundException, SQLException
    {
        Class.forName(DRIVER);
        DBConnector.conn = DriverManager.getConnection(JDBC_URL, USER, PW);
        if (DBConnector.conn != null)
        {
            System.out.println("Connected to LAS Database");
        }
    }

    //DO NOT MODIFY THIS OPERATION!!!
    public static void createTable(String type, String details) throws SQLException
    {
        String data = "CREATE TABLE " + type + "(" + details + ")";
        PreparedStatement pt = conn.prepareStatement(data);
        pt.executeUpdate();
    }

    
    public static void insertItemIntoTable(Item item) throws SQLException
    {
        String data = "INSERT INTO Items(title, author, type, amountleft)"
                + "Values (?,?,?,?)";
        PreparedStatement pt = conn.prepareStatement(data);
        pt.setString(1, item.getTitle());
        pt.setString(2, item.getAuthor());
        pt.setString(3, item.getType());
        pt.setInt(4, item.getAmountLeft());
        pt.executeUpdate();
    }
    
    public static ArrayList<Item> getItemTable() throws SQLException
    {
        ArrayList<Item> table = new ArrayList<>();
        String data = "SELECT * FROM Items";
        PreparedStatement pt = conn.prepareStatement(data);
        ResultSet rs = pt.executeQuery();
        while(rs.next())
        {
            table.add(new Item(rs.getString("title"), rs.getString("author"),
                    rs.getString("type"), rs.getInt("ItemID"), rs.getInt("amountleft")));
        }
        
        return table;
    }
    
    public static void insertMemberIntoTable(Member member) throws SQLException
    {
        String data = "INSERT INTO MEMBERS(NAME,EMAIL,PRIVILEGE,ISSTAFF)"
                + "Values (?,?,?,?)";
        PreparedStatement pt = conn.prepareStatement(data);
        pt.setString(1, member.getName());
        pt.setString(2, member.getEmail());
        pt.setInt(3, member.getPrivilege());
        pt.setBoolean(4, member.isIsStaff());
        pt.executeUpdate();
    }
}
