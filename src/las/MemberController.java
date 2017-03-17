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
    static int newMemberID = 0;
    static int isStaffindex;

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

            String data = "SELECT MAX(MEMBER_ID) FROM LAS.MEMBERS";
            PreparedStatement pt = DBConnector.conn.prepareStatement(data);

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
        memberList.remove(member);
    }

    public static void changeMPri(Member memberTochange, Member newMember) {
        int memberPos = memberList.indexOf(memberTochange);
        memberList.set(memberPos, newMember);
    }

    public static Member getMemberByID(int ID) {
        for (Member member : memberList) {
            if (member.getID() == ID) {
                return member;
            }
        }
        return null;
    }

    public static ArrayList<Member> getMemberByName(String name) {
        ArrayList<Member> results = new ArrayList<>();
        for (Member member : memberList) {
            if (member.getName().equals(name)) {
                results.add(member);
            }
        }
        return results;
    }

}
