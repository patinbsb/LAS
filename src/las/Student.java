/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package las;

import java.util.ArrayList;

/**
 *
 * @author alvinho0304
 */
public class Student {

    //Attitbutes
    private String name;

    private int studentID;

    //Alvin : i'm not sure that am i right?
    private ArrayList<String> borrowedItems = new ArrayList<>();

    private String email;

    //Constructor
    public Student(String name, int studentID, ArrayList borrowedItems, String email) {
        this.name = name;
        this.studentID = studentID;
        this.borrowedItems = borrowedItems;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ArrayList getBorrowedItems() {
        return borrowedItems;
    }

    public void setBorrowedItems(ArrayList borrowedItems) {
        this.borrowedItems = borrowedItems;
    }

    public int getStudentID() {
        return studentID;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //Methods
    public void returnItem(Item book) {
        //TODO your implementation here:
    }

    public void issueBook(Item book) {
        //TODO your implementation here:
    }
}
