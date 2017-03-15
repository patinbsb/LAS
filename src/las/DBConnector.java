/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package las;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import au.com.bytecode.opencsv.CSVReader;

/**
 *
 * @author alvinho0304
 */
public class DBConnector {

    public static DBConnector dbConnector;

    public static DBConnector getInstance() throws ClassNotFoundException, SQLException {
        if (dbConnector == null) {
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

    //attributes for loading CSV Files into Table
    private static final String SQL_INSERT = "INSERT INTO ${table}(${keys}) VALUES(${values})";
    private static final String TABLE_REGEX = "\\$\\{table\\}";
    private static final String KEYS_REGEX = "\\$\\{keys\\}";
    private static final String VALUES_REGEX = "\\$\\{values\\}";

    static Connection conn = null;
    private char seperator;

    //DO NOT MODIFY THIS OPERATION!!!
    private DBConnector() throws SQLException, ClassNotFoundException {
    }

    public static void connect() throws ClassNotFoundException, SQLException {
        Class.forName(DRIVER);
        DBConnector.conn = DriverManager.getConnection(JDBC_URL, USER, PW);
        if (DBConnector.conn != null) {
            System.out.println("Connected to LAS Database");
        }
    }

    //DO NOT MODIFY THIS OPERATION!!!
    public static void createTable(String type, String details) throws SQLException {
        String data = "CREATE TABLE " + type + "(" + details + ")";
        PreparedStatement pt = conn.prepareStatement(data);
        pt.executeUpdate();
    }

    public static void insertItemIntoTable(Item item) throws SQLException {
        String data = "INSERT INTO Items(title, author, type, amountleft)"
                + "Values (?,?,?,?)";
        PreparedStatement pt = conn.prepareStatement(data);
        pt.setString(1, item.getTitle());
        pt.setString(2, item.getAuthor());
        pt.setString(3, item.getType());
        pt.setInt(4, item.getAmountLeft());
        pt.executeUpdate();
    }

    public static ArrayList<Item> getItemTable() throws SQLException {
        ArrayList<Item> table = new ArrayList<>();
        String data = "SELECT * FROM Items";
        PreparedStatement pt = conn.prepareStatement(data);
        ResultSet rs = pt.executeQuery();
        while (rs.next()) {
            table.add(new Item(rs.getString("title"), rs.getString("author"),
                    rs.getString("type"), rs.getInt("ItemID"), rs.getInt("amountleft")));
        }

        return table;
    }

    public static void insertMemberIntoTable(Member member) throws SQLException {
        String data = "INSERT INTO MEMBERS(NAME,EMAIL,PRIVILEGE,ISSTAFF)"
                + "Values (?,?,?,?)";
        PreparedStatement pt = conn.prepareStatement(data);
        pt.setString(1, member.getName());
        pt.setString(2, member.getEmail());
        pt.setInt(3, member.getPrivilege());
        pt.setBoolean(4, member.isIsStaff());
        pt.executeUpdate();
    }

    //Online source with loading CSV into SQLTable : http://viralpatel.net/blogs/java-load-csv-file-to-database/
    public void loadCSVIntoTable(String csvFile, String tableName,
            boolean truncateBeforeLoad) throws Exception {

        CSVReader csvReader = null;
        if (null == DBConnector.conn) {
            throw new Exception("Not a valid connection.");
        }
        try {

            csvReader = new CSVReader(new FileReader(csvFile), this.seperator);

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Error occured while executing file. "
                    + e.getMessage());
        }

        String[] headerRow = csvReader.readNext();

        if (null == headerRow) {
            throw new FileNotFoundException(
                    "No columns defined in given CSV file."
                    + "Please check the CSV file format.");
        }

        String questionmarks = StringUtils.repeat("?,", headerRow.length);
        questionmarks = (String) questionmarks.subSequence(0, questionmarks
                .length() - 1);

        String query = SQL_INSERT.replaceFirst(TABLE_REGEX, tableName);
        query = query
                .replaceFirst(KEYS_REGEX, StringUtils.join(headerRow, ","));
        query = query.replaceFirst(VALUES_REGEX, questionmarks);

        System.out.println("Query: " + query);

        String[] nextLine;
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = this.conn;
            con.setAutoCommit(false);
            ps = con.prepareStatement(query);

            if (truncateBeforeLoad) {
                //delete data from table before loading csv
                con.createStatement().execute("DELETE FROM " + tableName);
            }

            final int batchSize = 1000;
            int count = 0;
            Date date = null;
            while ((nextLine = csvReader.readNext()) != null) {

                if (null != nextLine) {
                    int index = 1;
                    for (String string : nextLine) {
                        date = DateUtil.convertToDate(string);
                        if (null != date) {
                            ps.setDate(index++, new java.sql.Date(date
                                    .getTime()));
                        } else {
                            ps.setString(index++, string);
                        }
                    }
                    ps.addBatch();
                }
                if (++count % batchSize == 0) {
                    ps.executeBatch();
                }
            }
            ps.executeBatch(); // insert remaining records
            con.commit();
        } catch (Exception e) {
            con.rollback();
            e.printStackTrace();
            throw new Exception(
                    "Error occured while loading data from file to database."
                    + e.getMessage());
        } finally {
            if (null != ps) {
                ps.close();
            }
            if (null != con) {
                con.close();
            }

            csvReader.close();
        }
    }

    public char getSeprator() {
        return seperator;
    }

    public void setSeprator(char seperator) {
        this.seperator = seperator;
    }
    //Loading CSV into Table END
}
