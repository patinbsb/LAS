/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package las;

import java.util.ArrayList;

/**
 *
 * @author p2-goodson
 */
public class MemberController
{
    public static MemberController memberController;
    static ArrayList<Member> memberList = new ArrayList<>();
    static int currentMemberID = 0;
    
    private MemberController()
    {
    }
    
    // Using singleton pattern
    public static MemberController getInstance()
    {
        if (memberController == null)
        {
            memberController = new MemberController();
        }
        return memberController;
    }
    
    public static void addNewMember(String name, ArrayList<Item> borrowedItems,
            String email, int privilege)
    {
        memberList.add(new Member(name, currentMemberID, borrowedItems, email, privilege));
        currentMemberID++;
    }
    
    public static void removeMember(Member member)
    {
        memberList.remove(member);
    }
    
    public static void changeMember(Member memberTochange, Member newMember)
    {
        int memberPos = memberList.indexOf(memberTochange);
        memberList.set(memberPos, newMember);
    }
    
    public static Member getMemberByID(int ID)
    {
        for (Member member: memberList)
        {
            if (member.getID() == ID)
            {
                return member;
            }
        }
        return null;
    }
    
    public static ArrayList<Member> getMemberByName(String name)
    {
        ArrayList<Member> results = new ArrayList<>();
        for (Member member: memberList)
        {
            if (member.getName().equals(name))
            {
                results.add(member);
            }
        }
        return results;
    }
    
}
