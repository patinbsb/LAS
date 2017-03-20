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
        ArrayList<Item> results = new ArrayList<>();
        switch (searchType)
        {
            case "Title":
                results = ItemController.getItemByTitle(entry);
                break;
            case "Author":
                results = ItemController.getItemByAuthor(entry);
                break;
            case "Item Type":
                results = ItemController.getItemByType(entry);
                break;
            default:
                throw new IndexOutOfBoundsException("Search is not of type: "
                + "Title, Author or Item Type");
        }
        return results;
    }
    
    public ArrayList<Member> searchMemberByName(String name)
    {
        ArrayList<Member> memberResults = MemberController.getMemberByName(name);
        return memberResults;
    }
    
    public Member searchMemberByID(int ID) throws SQLException, ClassNotFoundException
    {
        ArrayList<Member> memberList = DBConnector.getMemberTableIntoList();
        for (Member member : memberList)
        {
            if (member.getID() == ID)
            {
                return member;
            }
        }
        return null;
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
