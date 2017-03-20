/*
 * 
 */
package las;

/**
 *
 * @author Patrick Goodson
 */
class Transaction
{
    private int memberID;
    private int itemID;
    Transaction(int memberID, int itemID)
    {
        this.memberID = memberID;
        this.itemID = itemID;
    }

    public int getMemberID()
    {
        return memberID;
    }

    public void setMemberID(int memberID)
    {
        this.memberID = memberID;
    }

    public int getItemID()
    {
        return itemID;
    }

    public void setItemID(int itemID)
    {
        this.itemID = itemID;
    }
    
}
