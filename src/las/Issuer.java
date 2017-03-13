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
        Member user = searchMemberByID(ID);
        user.issueItem(item);
    }
    
    
}
