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
    
    public static Item getItemByTitle(String title)
    {
        for (Item item : itemList)
        {
            if (item.getTitle().equals(title))
            {
                return item;
            }
        }
        return null;
    }
    
    public static Item getItemByAuthor(String author)
    {
        for (Item item : itemList)
        {
            if (item.getAuthor().equals(author))
            {
                return item;
            }
        }
        return null;
    }
    
    public static Item getItemByType(String type)
    {
        for (Item item : itemList)
        {
            if (item.getType().equals(type))
            {
                return item;
            }
        }
        return null;
    }
    
    
}
