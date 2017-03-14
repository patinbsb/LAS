/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package las;

import java.sql.*;

/**
 *
 * @author alvinho0304
 */
public class DBConnector {

    //Database name
    private static final String db_name = "LAS";

    //Database username and password
    private static final String USER = "las";
    private static final String PW = "las";

    //DO NOT MODIFY THIS TWO LINES!!!
    private static final String DRIVER = "org.apache.derby.jdbc.ClientDriver";
    private static final String JDBC_URL = "jdbc:derby://localhost:1527/" + db_name + ";create=true";

    Connection conn = null;

    //DO NOT MODIFT THIS OPERATION!!!
    public DBConnector() throws SQLException, ClassNotFoundException {
        Class.forName(DRIVER);
        this.conn = DriverManager.getConnection(JDBC_URL, USER, PW);
        if (this.conn != null) {
            System.out.println("Connected to LAS Database");
        }
    }

    //DO NOT MODIFT THIS OPERATION!!!
    public void createTable(String type, String details) throws SQLException {
        String data = "CREATE TABLE " + type + "(" + details + ")";
        PreparedStatement pt = conn.prepareStatement(data);
        pt.executeUpdate();
    }

    //DO NOT MODIFT THIS OPERATION!!!
    public void insertIntoTable(String type, String details) throws SQLException {
        String data = "INSERT INTO " + type + " Values (" + details + ")";
        PreparedStatement pt = conn.prepareStatement(data);
        pt.executeUpdate();
    }

    //Implementing your own operation with SQL Table below
}
