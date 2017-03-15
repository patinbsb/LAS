/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package las;


import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alvinho0304
 */
public class Administration extends Search {

    //Attributes
    String sqlurl = "";

    //Operations
    public void addnewMember(Object mData[]) {
        try {
            /**
             * If Object mData belongs to a staff, connect to staff info system
             * else connect to student info system Add a new member into
             * corresponding info system
             */

            //Connect to the SQL Databases
            Connection con = DriverManager.getConnection(sqlurl, "", "");
            Statement st = con.createStatement();
            if (mData[4].equals("Staff")) {

                //Do the fuctionality of adding new staff into SQL file
                //st.executeUpdate();
            } else {

                //Do the fuctionality of adding new student into SQL file
                //st.executeUpdate();
            }
        } catch (SQLException ex) {
            Logger.getLogger(Administration.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void removeMember(Object mData[]) {
        try {
            /**
             * If Object mData belongs to a staff, connect to staff info system;
             * else connect to student info system; Create for loop for
             * corresponding member type of arraylist if (listdata = mData)
             * remove member;
             */

            //Connect to the SQL Databases
            Connection con = DriverManager.getConnection(sqlurl, "", "");
            Statement st = con.createStatement();
            if (mData[4].equals("Staff")) {

                //Do the fuctionality of remove staff into SQL file
                //st.executeUpdate();
            } else {

                //Do the fuctionality of remove student into SQL file
                //st.executeUpdate();
            }
        } catch (SQLException ex) {
            Logger.getLogger(Administration.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void changeMPrivilege(Object mData[]) {
        try {
            /**
             * If Object mData belongs to a staff, connect to staff info system;
             * else connect to student info system; Create for loop for
             * corresponding member type of arraylist if(listdata = mData)
             * change privilege of member with value stored in mChange;
             */

            //Connect to the SQL Databases
            Connection con = DriverManager.getConnection(sqlurl, "", "");
            Statement st = con.createStatement();
            if (mData[4].equals("Staff") && !mData[5].equals("Staff")) {

                //Do the fuctionality of change privilege staff into SQL file
                //st.executeUpdate();
            } else {

                //Do the fuctionality of adding new student into SQL file
                //st.executeUpdate();
            }
        } catch (SQLException ex) {
            Logger.getLogger(Administration.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
