/*
 * 
 */
package las;

import java.util.ArrayList;

/**
 *
 * @author Patrick Goodson
 */
public abstract class Search
{
    public ArrayList<String> searchItems(String entry, String searchType)
    {
        ArrayList<String> results = new ArrayList<>();
        // TODO implement this method
        // Connect to local library system
        // perform search
        // results = LocaLibrary.search(String entry, String searchType);
        return results;
    }
    
    public ArrayList<String> searchUsersByName(String name)
    {
        ArrayList<String> results = new ArrayList<>();
        // TODO implement this method
        // Connect to Staff AND student info systems
        // Perform search
        // results = StaffInfo.searchName(String name)
        return results;
    }
    
    public ArrayList<String> searchUsersByID(int ID)
    {
        ArrayList<String> results = new ArrayList<>();
        // TODO implement this method
        // Connect to Staff AND student info systems
        // Perform search
        // results = StaffInfo.searchID(int ID)
        return results;
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
