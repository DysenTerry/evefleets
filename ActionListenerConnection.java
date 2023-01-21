/* EveMap project.
 * Author: TA Lucas
 */


import javax.swing.JTextField;
import javax.swing.JFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import java.util.*;
import java.io.*;



// This monitors for the Add button when adding a new fleet to the map.
class ActionListenerConnection implements ActionListener {
    private final JFrame frame;
    private final MapPopupMenu menu;
    private final JTextField connection;
    private final JTextField channel;
    private final JTextField pass;

    ActionListenerConnection(JFrame frame, MapPopupMenu menu, JTextField txtConnection, JTextField txtChannel, JTextField txtPass) {
        this.frame = frame;
        this.menu = menu;
        this.connection= txtConnection;
        this.channel= txtChannel;
        this.pass= txtPass;
    }

    public void actionPerformed(ActionEvent e) {
    	String uni = "";
    	String pattern= "^[a-zA-Z0-9]*$";
		if(e.getActionCommand()== "Set"){
			if(channel.getText().matches(pattern) && pass.getText().matches(pattern))
			{
				try{
					PrintWriter out = new PrintWriter(new FileOutputStream("config.txt"));
					out.println(connection.getText() );
					out.println(channel.getText() );
					out.println(pass.getText() );
					out.close();
					frame.dispose();
					menu.papa.canvas.repaint();
				}
	    		catch (IOException ex){

	    		}
			}
			else
			{
				JOptionPane.showMessageDialog(frame,"Channel and Password can only contain characters 0-9 and a-Z.");
			}
		}
    }
}