/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package las;

/**
 *
 * @author Patrick Goodson
 */
public class SystemAdmin extends Search {

    public boolean addMember(Object newmData) {
        // TODO implement this method
        /**
         * If Object mData belongs to a staff, connect to staff info system 
         * else connect to student info system 
         * Add a new member into corresponding info system
         */
        return true;
    }

    public boolean removeMember(Object mData) {
        // TODO implement this method
        /**
         * If Object mData belongs to a staff, connect to staff info system;
         * else connect to student info system; 
         * Create for loop for corresponding member type of arraylist 
         * if (listdata = mData) 
         * remove member;
         */
        return true;
    }

    public boolean changeMember(Object mChange, Object mData) {
        // TODO implement this method
        /**
         * If Object mData belongs to a staff, connect to staff info system;
         * else connect to student info system; 
         * Create for loop for corresponding member type of arraylist
         * if(listdata = mData)
         * change privilege of member with value stored in mChange;
         */
        return true;
    }

}
