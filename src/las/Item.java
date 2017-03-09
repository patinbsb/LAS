/*
 * 
 */
package las;

/**
 *
 * @author Patrick Goodson
 */
public class Item{
    
    private String title;
    
    private String author;
    
    private String Type;
    
    private int itemID;
    
    private int amountLeft;

    public Item(String title, String author, String Type, int itemID, int amountLeft) {
        this.title = title;
        this.author = author;
        this.Type = Type;
        this.itemID = itemID;
        this.amountLeft = amountLeft;
    }
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getType() {
        return Type;
    }

    public void setType(String Type) {
        this.Type = Type;
    }

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public int getAmountLeft() {
        return amountLeft;
    }

    public void setAmountLeft(int amountLeft) {
        this.amountLeft = amountLeft;
    }
    
    
    
}
