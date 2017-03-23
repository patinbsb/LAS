/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package las;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author p2-goodson
 */
public class MemberController {

    public static MemberController memberController;
    static ArrayList<Member> memberList = new ArrayList<>();
    static ArrayList<Member> storedData = new ArrayList<>();
    static int newMemberID = 0;
    static int isStaffindex;
    static boolean bool;
    static boolean boolcheck;
    static ResultSet searchRS;
    static String query = null;
    static PreparedStatement pt;

    private MemberController() {
    }

    // Using singleton pattern
    public static MemberController getInstance() {
        if (memberController == null) {
            memberController = new MemberController();
        }
        return memberController;
    }

    public static void addNewMember(String name, String email, String privilege, boolean isStaff) {
        try {
            //method for getting last member ID then +1 to be a new ID\

            query = "SELECT MAX(MEMBER_ID) FROM LAS.MEMBERS";
            pt = DBConnector.conn.prepareStatement(query);

            try (ResultSet rs = pt.executeQuery()) {
                while (rs.next()) {
                    newMemberID = rs.getInt(1) + 1;
                }
            }
            //Converting a boolean into int for storing representating value 1 and 0 for true and false
            if (isStaff) {
                isStaffindex = 1;
            } else {
                isStaffindex = 0;
            }
            Member nMem = new Member(newMemberID, name, email, privilege, isStaff);
            memberList.add(nMem);
            DBConnector.insertMemberIntoTable(nMem);
            System.out.println("New Member: " + name + " has been inserted into arraylist and table");
        } catch (SQLException ex) {
            Logger.getLogger(MemberController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void removeMember(Member member) {

        try {
            query = "DELETE FROM MEMBERS WHERE MEMBER_ID = ?";
            pt = DBConnector.conn.prepareStatement(query);
            pt.setInt(1, member.getID());
            pt.executeUpdate();
            memberList.remove(member);
        } catch (SQLException ex) {
            Logger.getLogger(MemberController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void changeMPri(int ID, String pri) {
        try {
            //        arg = Member memberTochange, Member newMember
//        int memberPos = memberList.indexOf(memberTochange);
//        memberList.set(memberPos, newMember);
            query = "UPDATE MEMBERS SET PRIVILEGE = ? WHERE MEMBER_ID = ?";
            pt = DBConnector.conn.prepareStatement(query);
            pt.setString(1, pri);
            pt.setInt(2, ID);
            pt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(MemberController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static Member getMemberByID(int ID) {
        for (Member member : memberList) {
            if (member.getID() == ID) {
                return member;
            }
        }
        return null;
    }

    public static ArrayList<Member> searchMember(String keyword, String searchType) {
        try {
            ArrayList<Member> mlist = DBConnector.getMemberTableIntoList();
            storedData.clear();
            for (Member m : mlist) {
                switch (searchType) {
                    case "Member ID":
                        if (m.getID() == Integer.parseInt(keyword)) {
                            storedData.add(m);
                        }
                        break;
                    case "Name":
                        if (m.getName().toLowerCase().contains(keyword.toLowerCase())) {
                            storedData.add(m);
                        }
                        break;
                    case "Email":
                        if (m.getEmail().toLowerCase().contains(keyword.toLowerCase())) {
                            storedData.add(m);
                        }
                        break;
                    case "Privilege":
                        if (m.getPrivilege().toLowerCase().contains(keyword.toLowerCase())) {
                            storedData.add(m);
                        }
                        break;
                    case "IsStaff":
                        switch (keyword) {
                            case "Yes":
                                bool = true;
                                boolcheck = true;
                                break;
                            case "No":
                                bool = false;
                                boolcheck = true;
                                break;
                            case "Default":
                                boolcheck = false;
                                break;

                        }
                        if (m.isIsStaff() == bool && boolcheck) {
                            storedData.add(m);
                        }
                        break;
                }
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(MemberController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return storedData;
    }
}
