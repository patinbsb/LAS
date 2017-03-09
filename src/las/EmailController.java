/*
 * 
 */
package las;

import java.util.ArrayList;

/**
 *
 * @author Patrick Goodson
 */
public class EmailController
{
    public static EmailController emailController;
    static ArrayList<String> emailList = new ArrayList<>();
    
    // Using singleton as we only want 1 email controller instance
    private EmailController()
    {
    }
    
    public static EmailController getInstance()
    {
        if (emailController == null)
        {
            emailController = new EmailController();
        }
        return emailController;
    }
    
    public static void sendEmail(String contents)
    {
        // TODO implement this method
        System.out.println(contents);
    }
}
