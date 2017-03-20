/*
 *
 */
package las;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Patrick Goodson
 */
public class Issuer extends Search
{
    ArrayList<Item> itemList = null;
    public void Issue(int ID, Item item) throws SQLException
    {
        Member user = searchMemberByID(ID);
        user.issueItem(item);
    }

    public void setItemList(ArrayList<Item> itemList)
    {
        this.itemList = itemList;
    }
    
    public ArrayList<Item> getItemList()
    {
        return itemList;
    }
    
}
