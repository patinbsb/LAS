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
public abstract class Search
{
    public ArrayList<Item> searchItems(String entry, String searchType) throws SQLException
    {
        
        ArrayList<Item> itemList = DBConnector.getItemTable();
        ArrayList<Item> result = new ArrayList<>();
        switch (searchType)
        {
            case "Title":
                for(Item item : itemList)
                {
                    if (item.getTitle().toLowerCase().contains(entry.toLowerCase()))
                            {
                                result.add(item);
                            }
                }
                break;
            case "Author":
                for (Item item : itemList)
                {
                    if (item.getAuthor().toLowerCase().contains(entry.toLowerCase()))
                    {
                        result.add(item);
                    }
                }
                break;
            case "Item Type":
                for (Item item : itemList)
                {
                    if (item.getType().toLowerCase().contains(entry.toLowerCase()))
                    {
                        result.add(item);
                    }
                }
                break;
            default:
                throw new IndexOutOfBoundsException("Search is not of type: "
                + "Title, Author or Item Type");
        }
        return result;
    }
    
    public Member searchMemberByID(int ID) throws SQLException, ClassNotFoundException
    {
        Member result = MemberController.getMemberByID(ID);
        return result;
    }
    
    public Source getSourceByID(int ID)
    {
        Source result = new Source();
        // TODO implement this method
        // Connect to Bibliographic system
        // Perform search
        // result = Biblio.getSourceByID(ID)
        return result;
    }
}
