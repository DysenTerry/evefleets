/* EveMap project.
 * Author: TA Lucas
 */


import javax.swing.JTextField;
import javax.swing.JFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JRadioButton;

// This monitors for the Add button when adding a new fleet to the map.
class ActionListenerFleetAddFrame implements ActionListener {
    private final JFrame frame;
    private final MapPopupMenu menu;
    private final JTextField name;
    private final JRadioButton myBlue;
    private final JRadioButton myGreen;
    private final JRadioButton myRed;
    private final JTextArea myNotes;

    ActionListenerFleetAddFrame(JFrame frame, MapPopupMenu menu, JTextField txtNameText,
    					    JRadioButton blueFleet, JRadioButton greenFleet, JRadioButton redFleet, JTextArea fleetNotes) {
        this.frame = frame;
        this.menu = menu;
        this.name= txtNameText;
        this.myBlue = blueFleet;
        this.myGreen = greenFleet;
        this.myRed = redFleet;
        this.myNotes = fleetNotes;
    }

    public void actionPerformed(ActionEvent e) {
    	String uni = "";
		if(e.getActionCommand()== "Add"){
			String theName = name.getText();
			String theNotes = myNotes.getText();

   			String pattern= "^[-_a-zA-Z0-9.\\s]*$";
        	if(!theNotes.matches(pattern)){
            	JOptionPane.showMessageDialog(frame,"Please use valid alphanumeric characters in the Notes field.");
            	return;
        	}

			if (theName.indexOf(",") >= 0)
			{
				JOptionPane.showMessageDialog(frame,"Fleet name cannot contain commas.");
				return;
			}
			if (theName.indexOf(" ") >= 0)
			{
				JOptionPane.showMessageDialog(frame,"Fleet name cannot contain spaces.");
				return;
			}
			if (theName.length()==0)
			{
				JOptionPane.showMessageDialog(frame,"Fleet name cannot be blank.");
				return;
			}


			uni = "0";
			menu.addFleet(theName, uni, myBlue.isSelected(), myGreen.isSelected(), myRed.isSelected(), myNotes);
			frame.dispose();
			menu.papa.canvas.repaint();
		}
    }
}