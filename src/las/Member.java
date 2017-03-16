/*
 * 
 */
package las;

import java.util.ArrayList;

/**
 *
 * @author Patrick Goodson
 */
public class Member
{
    String name;
    private int ID;
    private String email;
    private ArrayList<Item> borrowedItems = new ArrayList<>();
    boolean isStaff;
    private String privilege;
    
    public Member (int ID, String name, String email, String privilege, boolean isStaff)
    {
        this.name = name;
        this.ID = ID;
        //this.borrowedItems = borrowedItems;
        this.email = email;
        this.privilege = privilege;
        this.isStaff = !(privilege.equals("Student") | privilege.equals("External User"));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ArrayList<Item> getBorrowedItems() {
        return borrowedItems;
    }

    public void setBorrowedItems(ArrayList<Item> borrowedItems) {
        this.borrowedItems = borrowedItems;
    }
    
    public void returnItem(Item item)
    {
        for (Item currentItem: borrowedItems)
        {
            if (currentItem.equals(item))
            {
                ItemController.returnItem(currentItem);
                borrowedItems.remove(currentItem);
            }
        }
    }
    
    public void issueItem(Item item)
    {
        if (!borrowedItems.contains(item))
        {
            borrowedItems.add(item);
        }

    }

    public boolean isIsStaff() {
        return isStaff;
    }

    public String getPrivilege() {
        return privilege;
    }

    public void setPrivilege(String privilege) {
        this.privilege = privilege;
    }
}
