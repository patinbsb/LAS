/*
 *
 */
package las;

import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author Patrick Goodson
 */
public class Issuer extends Search
{
    private ArrayList<Item> itemList = null;
    private Member selectedMember;
    public void Issue(Member user, Item item) throws SQLException, ClassNotFoundException
    {
        user.issueItem(item);
        JOptionPane.showMessageDialog(null, "Item Issued!");
    }

    public void setItemList(ArrayList<Item> itemList)
    {
        this.itemList = itemList;
    }
    
    public ArrayList<Item> getItemList()
    {
        return itemList;
    }
    
    public Member getSelectedMember()
    {
        return selectedMember;
    }
    
    public void setSelectedMember(Member newMember)
    {
        selectedMember = newMember;
    }
    
    public Member selectMember()
    {
        String[] prioptions =
        {
            "Name", "ID"
        };

        JTextField input = new JTextField();
        JComboBox type = new JComboBox(prioptions);

        final JComponent[] inputs = new JComponent[]
        {
            new JLabel("Search Member:"), input,
            new JLabel("Search Type:"), type
        };

        int sumbit = JOptionPane.showConfirmDialog(null, inputs, "Search member",
                JOptionPane.PLAIN_MESSAGE);
        if (sumbit == JOptionPane.OK_OPTION)
        {
            if (type.getSelectedItem().equals("Name") && !(MemberController.searchMember(input.getText(),"Name")).isEmpty())
            {
                ArrayList<Member> nameList = MemberController.searchMember(input.getText(),"Name");
                DefaultListModel memberList = new DefaultListModel();
                for (Member member : nameList)
                {
                    memberList.addElement(member.name);
                }
                JList membersTemp = new JList(memberList);
                membersTemp.setSelectionMode(0);
                membersTemp.setSelectedIndex(0);
                final JComponent[] selectFromList = new JComponent[]
                {
                    membersTemp
                };
                int memberChoice = JOptionPane.showConfirmDialog(null, selectFromList, "Select member", JOptionPane.PLAIN_MESSAGE);

                if (memberChoice == JOptionPane.OK_OPTION)
                {
                    int index = membersTemp.getSelectedIndex();
                    setSelectedMember(nameList.get(index));
                }
            } else if (type.getSelectedItem().equals("ID"))
            {
                setSelectedMember(MemberController.getMemberByID(Integer.parseInt(input.getText())));
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Could not find a member with that name!");
                selectMember();
            }
        }
        return selectedMember;
    }
}
