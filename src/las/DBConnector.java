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
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;

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
    private static char separator;

    //DO NOT MODIFY THIS OPERATION!!!
    private DBConnector() throws SQLException, ClassNotFoundException {
    }

    /*  Command operations /common functionalities for database */
    //Getting connection from SQL Database
    public static void connect() throws ClassNotFoundException, SQLException {
        Class.forName(DRIVER);
        DBConnector.conn = DriverManager.getConnection(JDBC_URL, USER, PW);
        if (DBConnector.conn != null) {
            System.out.println("Connected to LAS Database");
        }
    }

    /**
     * Create Table with(example): tableName: ITEMS  <All in capital letters>
     * details: ITEM_ID,NAME,NUMBER_AVAILABLE
     * <All in capital letters and put a dash instead of space>
     */
    public static void createTable(String tableName, String details) throws SQLException {
        DatabaseMetaData dbmd = conn.getMetaData();
        ResultSet rs = dbmd.getTables(null, "LAS", tableName, null);

        if (!rs.next()) {
            String data = "CREATE TABLE " + tableName + "(" + details + ")";
            PreparedStatement pt = conn.prepareStatement(data);
            pt.executeUpdate();
            System.out.println(tableName + " has been created");
        } else {
            System.out.println(tableName + " already exists in LAS Database");
        }
    }

    //Online source with loading CSV into SQLTable : http://viralpatel.net/blogs/java-load-csv-file-to-database/
    /**
     * Load CSV test data into SQL Table
     *
     * example for clearFirst: boolean check =
     * DBConnector.checkDataExistedInTable("MEMBERS");
     *
     * if (check) { DBConnector.loadCSVIntoTable("src/resources/members.csv",
     * "MEMBERS", true); System.out.println("Test data inserted into MEMBERS
     * table"); }
     *
     * ignore createNewReader, since it uses for loadCSVIntoTable, don't modify
     * it
     *
     * Getter and Setter provided for Separator to set your own separator inside
     * your CSV File
     *
     * @param csvFile : src/resources/xxx.csv (put your csv file under this
     * path)
     * @param tableName: TABLENAME (All in capital letters)
     * @param clearFirst true = if data not existed in SQL Table, write test
     * data inside false = if data exisited in SQL Table, don't write again.
     * @throws java.lang.Exception
     */
    public static void loadCSVIntoTable(String csvFile, String tableName,
            boolean clearFirst) throws Exception {

        CSVReader csvReader = null;
        if (null == DBConnector.conn) {
            throw new Exception("Not a valid connection.");
        }
        try {

            csvReader = DBConnector.getInstance().createNewReader(csvFile);

        } catch (ClassNotFoundException | SQLException | FileNotFoundException e) {
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

        String[] nextLine;
        PreparedStatement ps = null;
        try {
            conn.setAutoCommit(false);
            ps = conn.prepareStatement(query);

            if (clearFirst) {
                //delete data from table before loading csv
                conn.createStatement().execute("DELETE FROM " + tableName);
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
            conn.commit();
        } catch (SQLException e) {
            conn.rollback();
            e.printStackTrace();
            throw new Exception(
                    "Error occured while loading data from file to database."
                    + e.getMessage());
        } finally {
            if (null != ps) {
                ps.close();
            }

            csvReader.close();
        }
    }

    public CSVReader createNewReader(String csvFile) throws FileNotFoundException {
        CSVReader csvReader = new CSVReader(new FileReader(csvFile), DBConnector.separator);
        return csvReader;
    }

    public static char getSeparator() {
        return separator;
    }

    public static void setSeparator(char separator) {
        DBConnector.separator = separator;
    }
    //Loading CSV into Table END

    //Check whether data existed or not in specific TABLENAME
    public static boolean checkDataExistedInTable(String tableName) throws SQLException {
        boolean exists = true;
        String check = "SELECT * FROM " + tableName;
        PreparedStatement pt = conn.prepareStatement(check);
        ResultSet rs = pt.executeQuery();
        if (rs.next()) {
            exists = false;
        }
        return exists;
    }

    public static void CreateAndLoadDataIntoTable() {
        try {
            DBConnector.setSeparator(',');

            DBConnector.createTable("MEMBERS",
                    "MEMBER_ID INT NOT NULL GENERATED BY DEFAULT AS IDENTITY(START WITH 1,INCREMENT"
                    + " BY 1), "
                    + "NAME VARCHAR(255) NOT NULL, "
                    + "EMAIL VARCHAR(255), "
                    + "PRIVILEGE VARCHAR(255) NOT NULL, "
                    + "ISSTAFF BOOLEAN NOT NULL, "
                    + "PRIMARY KEY (MEMBER_ID)");

            DBConnector.createTable("ITEMS",
                    "ITEM_ID INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
                    + "TITLE VARCHAR(255) NOT NULL,AUTHOR VARCHAR(255),TYPE VARCHAR(255),"
                    + "AMOUNTLEFT INT NOT NULL,PRIMARY KEY (ITEM_ID)");

            DBConnector.createTable("TRANSACTIONS",
                    "MEMBER_ID INTEGER NOT NULL,ITEM_ID INTEGER NOT NULL,"
                    + "TRANSACTION_TIME TIMESTAMP NOT NULL DEFAULT CURRENT TIMESTAMP,"
                    + "PRIMARY KEY (MEMBER_ID, ITEM_ID),"
                    + "FOREIGN KEY (MEMBER_ID) REFERENCES MEMBERS(MEMBER_ID),"
                    + "FOREIGN KEY (ITEM_ID) REFERENCES ITEMS(ITEM_ID)");

            boolean checkMembers = DBConnector.checkDataExistedInTable("MEMBERS");
            boolean checkItems = DBConnector.checkDataExistedInTable("ITEMS");
            boolean checkTransactions = DBConnector.checkDataExistedInTable("Transactions");
            if (checkMembers) {
                DBConnector.loadCSVIntoTable("src/resources/members.csv", "MEMBERS", true);
                System.out.println("Data inserted into MEMBERS table");
            }
            if (checkItems) {
                DBConnector.loadCSVIntoTable("src/resources/items.csv", "ITEMS", true);
                System.out.println("Data inserted into ITEMS table");
            }
            if (checkTransactions) {
                DBConnector.loadCSVIntoTable("src/resources/transactions.csv", "TRANSACTIONS", true);
                System.out.println("Data inserted into TRANSACTIONS table");
            }

        } catch (SQLException ex) {
            Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /*  Transaction Part Functions */
    public static void insertTransactionIntoTable(Member member, Item item) throws SQLException {
        String data = "INSERT INTO TRANSACTIONS(MEMBER_ID,ITEM_ID)"
                + "Values (?,?)";
        PreparedStatement pt = conn.prepareStatement(data);
        pt.setInt(1, member.getID());
        pt.setInt(2, item.getItemID());
        pt.executeUpdate();
    }

    public static ArrayList<Transaction> getTransactionTable() throws SQLException {
        ArrayList<Transaction> table = new ArrayList<>();
        String data = "SELECT * FROM Transactions";
        PreparedStatement pt = conn.prepareStatement(data);
        ResultSet rs = pt.executeQuery();
        while (rs.next()) {
            table.add(new Transaction(rs.getInt("MEMBER_ID"), rs.getInt("ITEM_ID")));
        }

        return table;
    }

    public static void insertTransactionIntoTable(Transaction transaction) throws SQLException {
        String data = "INSERT INTO TRANSACTIONS(MEMBER_ID, ITEM_ID)"
                + "Values (?,?)";
        PreparedStatement pt = conn.prepareStatement(data);
        pt.setInt(1, transaction.getMemberID());
        pt.setInt(2, transaction.getItemID());
        try
        {
            pt.executeUpdate();
        } catch (Exception DerbySQLIntegrityConstraintViolationException)
        {
            JOptionPane.showMessageDialog(null, "Cannot Issue an item a member already owns!");
        }

    }

    public static void removeTransactionFromTable(Transaction transaction) throws SQLException {
        String data = "DELETE FROM TRANSACTIONS WHERE MEMBER_ID = ?"
                + "AND ITEM_ID = ?";
        PreparedStatement pt = conn.prepareStatement(data);
        pt.setInt(1, transaction.getMemberID());
        pt.setInt(2, transaction.getItemID());
        pt.executeUpdate();
    }

    /*  Item Part Functions */
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
                    rs.getString("type"), rs.getInt("Item_ID"), rs.getInt("amountleft")));
        }

        return table;
    }

    public static void incrementAmountLeft(Item item) throws SQLException {
        String data = "UPDATE ITEMS SET AMOUNTLEFT = ? WHERE ITEM_ID = ?";
        PreparedStatement pt = conn.prepareStatement(data);
        pt.setInt(1, (item.getAmountLeft() + 1));
        pt.setInt(2, item.getItemID());
        pt.executeUpdate();
    }

    public static void decrementAmountLeft(Item item) throws SQLException {
        String data = "UPDATE ITEMS SET AMOUNTLEFT = ? WHERE ITEM_ID = ?";
        PreparedStatement pt = conn.prepareStatement(data);
        pt.setInt(1, (item.getAmountLeft() - 1));
        pt.setInt(2, item.getItemID());
        pt.executeUpdate();
    }

    /*  Member Part Functions   */
    public static void insertMemberIntoTable(Member member) throws SQLException {
        String data = "INSERT INTO MEMBERS(MEMBER_ID,NAME,EMAIL,PRIVILEGE,ISSTAFF)"
                + "Values (?,?,?,?,?)";
        PreparedStatement pt = conn.prepareStatement(data);
        pt.setInt(1, member.getID());
        pt.setString(2, member.getName());
        pt.setString(3, member.getEmail());
        pt.setString(4, member.getPrivilege());
        pt.setBoolean(5, member.isIsStaff());
        pt.executeUpdate();
    }

    public static ArrayList<Member> getMemberTableIntoList() throws SQLException, ClassNotFoundException {
        ArrayList<Member> mtable = new ArrayList<>();
        String data = "SELECT * FROM LAS.MEMBERS";
        PreparedStatement pt = conn.prepareStatement(data);
        ResultSet rs = pt.executeQuery();
        while (rs.next()) {
            mtable.add(new Member(rs.getInt("MEMBER_ID"), rs.getString("NAME"),
                    rs.getString("EMAIL"), rs.getString("PRIVILEGE"), rs.getBoolean("ISSTAFF")));
        }

        return mtable;
    }

}
