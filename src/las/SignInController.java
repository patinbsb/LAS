/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package las;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Patrick Goodson
 */
public class SignInController {

    static private int userType;
    static private String password;

    public SignInController(int userType, String password) {
        SignInController.userType = userType;
        SignInController.password = password;
    }

    public boolean Authenticate() {
        //TODO Load passwords from user config file?

        switch (userType) {
            case 0:
                if (password.equals("system admin"))// Sys admin
                {
            try {
                DBConnector.createTable("STUDENTS", "Student_ID VARCHAR(255), Student_Name VARCHAR(255)");
                System.out.println("STUDENTS Table created");
                DBConnector db = DBConnector.getInstance();
                db.setSeprator(',');
                db.loadCSVIntoTable("src/resources/Students.csv", "STUDENTS", true);
                System.out.println("Data inserted into STUDENTS table");
                return true;
            } catch (SQLException ex) {
                Logger.getLogger(SignInController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(SignInController.class.getName()).log(Level.SEVERE, null, ex);
            }
                }
                break;
            case 1:
                if (password.equals("reports manager"))// Reports manager
                {
                    return true;
                }
                break;
            case 2:
                if (password.equals("cataloguer")) // Cataloguer
                {
                    return true;
                }
                break;
            case 3:
                if (password.equals("issuer")) // Issuer
                {
                    return true;
                }
                break;
            case 4:
                return true;
            default:
                return false;
        }
        return false;

    }
}
