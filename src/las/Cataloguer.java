/*
 *
 */
package las;

/**
 *
 * @author Patrick Goodson
 */
public class Cataloguer extends  Search
{
    public boolean addSource (Source newSource, boolean email)
    {
        // TODO implement this method
        // Connect to bibliogrpahic system
        // Add source
        // if (biblio.addNewSource(newSource))
        // emailUsers();
        // return true
        // else
        // return false
        return true;
    }
    
    public boolean addItem (Item newItem, boolean email)
    {
        // TODO implement this method
        // Connect to local library system
        // Add item
        // if (LocalLibrary.addNewItem(newItem))
        // if email == true
        // emailUsers()
        // return true
        // else
        // return false
        return true;
    }
    
    public void addItem (String title, String author, String type,
            int amountLeft, boolean email)
    {
        ItemController.addNewItem(title, author, type, amountLeft);
        if (email)
        {
            //Mail subscribers
            EmailController.sendEmail("new item in the library: " + title);
        }
    }
    
    public boolean removeSource (Source remove, boolean email)
    {
        // TODO implement this method
        return true;
    }
    
    public boolean removeItem(Item remove, boolean email)
    {
        // TODO implement this method
        return true;
    }
    
    public boolean changeSource(Source toChange, Source newSource, boolean email)
    {
        // TODO implement this method
        return true;
    }
    
    public boolean changeItem(Item toChange, Item newItem, boolean email)
    {
        // TODO implement this method
        return true;
    }
    
    public void emailUsers() // TODO figure this out
    {
        // TODO implement this method
    }
}
