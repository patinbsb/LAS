/*
 * 
 */
package las;

import java.util.ArrayList;

/**
 *
 * @author Patrick Goodson
 */
public class ItemController
{
    public static ItemController itemController;
    static ArrayList<Item> itemList = new ArrayList<>();
    static int currentItemID = 0;

    private ItemController()
    {
    }
    
    // Using singleton pattern as we only want 1 item controller instance
    public static ItemController getInstance()
    {
        if (itemController == null)
        {
            itemController = new ItemController();
        }
        return itemController;
    }
    
    
    public static void addNewItem (String title, String author, String type, int amountLeft)
    {
        Item newItem = new Item(title, author, type, currentItemID, amountLeft);
        itemList.add(newItem);
        currentItemID++;
    }
    
    public static void removeItem (Item item)
    {
            itemList.remove(item);
    }
    
    public static void changeItem (Item itemToChange, Item newItem)
    {
        int itemPos = itemList.indexOf(itemToChange);
        itemList.set(itemPos, newItem);
    }
    
    public static void returnItem(Item itemToReturn)
    {
        for (Item item : itemList)
        {
            if (item.equals(itemToReturn))
            {
                int left = item.getAmountLeft();
                left++;
                item.setAmountLeft(left);
            }
        }
    }
    
    public static void issueItem(Item itemToIssue)
    {
        for(Item item : itemList)
        {
            if (item.equals(itemToIssue))
            {
                int left = item.getAmountLeft();
                if (left == 0)
                {
                    throw new RuntimeException("amount left is zero, cannot issue");
                }
                else
                {
                    left--;
                    item.setAmountLeft(left);
                }
            }
        }
    }
    
    public static ArrayList<Item> getItemByTitle(String title)
    {
        ArrayList<Item> results = new ArrayList<>();
        for (Item item : itemList)
        {
            if (item.getTitle().equals(title))
            {
                results.add(item);
            }
        }
        return results;
    }
    
    public static ArrayList<Item> getItemByAuthor(String author)
    {
        ArrayList<Item> results = new ArrayList<>();
        for (Item item : itemList)
        {
            if (item.getAuthor().equals(author))
            {
                results.add(item);
            }
        }
        return results;
    }
    
    public static ArrayList<Item> getItemByType(String type)
    {
        ArrayList<Item> results = new ArrayList<>();
        for (Item item : itemList)
        {
            if (item.getType().equals(type))
            {
                results.add(item);
            }
        }
        return results;
    }
    
    public static Item getItemByID(int ID)
    {
        for (Item item : itemList)
        {
            if (item.getItemID() == ID)
            {
                return item;
            }
        }
        return null;
    }
    
}
