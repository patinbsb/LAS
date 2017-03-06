/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package las;

import java.util.ArrayList;

/**
 *
 * @author jeinel
 */
public class Staff {

    private String name;
    private int staffID;
    private String email;
    private int privilege;
    private ArrayList<String> borrowedItems = new ArrayList<String>();

    public Staff(String name, int staffID, ArrayList borrowedItems, String email, int privilege) {
        this.name = name;
        this.staffID = staffID;
        this.borrowedItems = borrowedItems;
        this.email = email;
        this.privilege = privilege;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStaffID() {
        return staffID;
    }

    public void setStaffID(int staffID) {
        this.staffID = staffID;
    }

    public void setBorrowedItems(ArrayList<String> borrowedItems) {
        this.borrowedItems = borrowedItems;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPrivilege() {
        return privilege;
    }

    public void setPrivilege(int privilege) {
        this.privilege = privilege;
    }

    public void returnItem(Item book) {
        //TODO implement method
        
    }    
    public void issueBook(Item book) {
        //TODO implement method
    
}

}
            
            
            
    
            
    

