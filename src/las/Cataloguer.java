/*
 *
 */
package las;

import java.sql.SQLException;

/**
 *
 * @author Patrick Goodson
 */
public class Cataloguer extends  Search
{
    public void addItem (String title, String author, String type,
            int amountLeft, boolean email) throws SQLException
    {
        Item newItem = new Item(title, author, type, -1, amountLeft);
        DBConnector.insertItemIntoTable(newItem);
        if (email)
        {
            //Mail subscribers
            EmailController.sendEmail("new item in the library: " + title);
        }
    }
}
