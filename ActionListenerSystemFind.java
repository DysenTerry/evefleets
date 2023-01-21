/* EveMap project.
 * Author: TA Lucas
 */


import javax.swing.JTextField;
import javax.swing.JFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import java.util.ArrayList;



// This monitors for the Add button when adding a new fleet to the map.
class ActionListenerSystemFind implements ActionListener {
    private final JFrame frame;
    private final MapPopupMenu menu;
    private final JTextField system;
    private boolean found;

    ActionListenerSystemFind(JFrame frame, MapPopupMenu menu, JTextField txtSystemFind) {
        this.frame = frame;
        this.menu = menu;
        this.system= txtSystemFind;
    }

    public void actionPerformed(ActionEvent e) {
    	String uni = "";
		if(e.getActionCommand()== "Find"){
			String theSystem = system.getText();
			found = false;

			if (theSystem.length() == 0)
			{
				frame.dispose();
				menu.papa.canvas.repaint();

			}
			else
			{
				Stars myStars = new Stars();
				for (int i=0;i<myStars.getSize();i++)
				{
					ArrayList<Object> thisStar = myStars.getNextStar(1);
					String temp1 = (String)thisStar.get(1);
					if (temp1.toLowerCase().compareTo(system.getText().toLowerCase()) == 0)
					{
						//JOptionPane.showMessageDialog(frame,thisStar.get(2) + " " + thisStar.get(3) + " " + thisStar.get(4));
						found = true;
						menu.papa.windowSize = menu.papa.frame.getSize();
						menu.papa.canvas.translateX = ((Integer.parseInt(thisStar.get(2).toString()) *-1)*menu.papa.canvas.scale + menu.papa.windowSize.width/2);
						menu.papa.canvas.translateY = ((Integer.parseInt(thisStar.get(4).toString()) *-1)*menu.papa.canvas.scale + menu.papa.windowSize.height/2);
						menu.papa.offsetX = (menu.papa.canvas.translateX - menu.papa.windowSize.width/2)/menu.papa.canvas.scale;
						menu.papa.offsetY = (menu.papa.canvas.translateY - menu.papa.windowSize.height/2)/menu.papa.canvas.scale;
						frame.dispose();
						menu.papa.canvas.repaint();
					}
				}
				myStars.resetList();
				if (found == false)
					JOptionPane.showMessageDialog(frame,"System Not Found");
			}
		}
    }
}