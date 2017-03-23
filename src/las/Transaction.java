/*
 * 
 */
package las;

import java.sql.Timestamp;

/**
 *
 * @author Patrick Goodson
 */
public class Transaction
{
    private int memberID;
    private int itemID;
    private Timestamp dateTime;
    public Transaction(int memberID, int itemID)
    {
        this.memberID = memberID;
        this.itemID = itemID;
    }
    public Transaction(int memberID, int itemID, Timestamp dateTime)
    {
        this.memberID = memberID;
        this.itemID = itemID;
        this.dateTime = dateTime;
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

    public Timestamp getDateTime()
    {
        return dateTime;
    }

    public void setDateTime(Timestamp dateTime)
    {
        this.dateTime = dateTime;
    }
    
}
