/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package las;


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
                return true;
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
