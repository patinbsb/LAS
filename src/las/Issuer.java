/*
 *
 */
package las;

/**
 *
 * @author Patrick Goodson
 */
public class Issuer extends Search
{
    public void Issue(int ID, Item item)
    {
        //TODO Implement this method
        // Connect to staff + student database
        // Get staff/student member to be issued
        // Issue item
        // if (StaffInfo.getStaffByID(ID).issueBook(bookID?))
        // return true
        // else
        // return false
        Object user = searchUserByID(ID);
        //user.issueBook();
        
    }
}
