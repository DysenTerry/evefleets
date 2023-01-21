/* EveMap project.
 * Author: TA Lucas
 */


import java.awt.geom.Point2D;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import java.util.ArrayList;
import javax.swing.JTextArea;

public class MapPopupMenu extends JPanel {
	protected JPopupMenu popup;
	protected ArrayList<Object> starData;
	protected Point2D.Double screenCoord;
	protected Point2D.Double mapCoord;
	protected Campaign campaign;
	protected EveMap papa;
	protected int optionSelected = 12;
	protected ActionListenerMenu myMenuListener;

	//Constructor
	public MapPopupMenu(EveMap parent, Campaign theCampaign, ArrayList<Object> starInfo, Point2D.Double p, Point2D.Double p2) {
		campaign = theCampaign;
		papa=parent;
		popup = new JPopupMenu();
		starData = starInfo;
		screenCoord = p;
		mapCoord = p2;
		myMenuListener = new ActionListenerMenu(this);

		JMenuItem item;

		// Menu item 0
		popup.add(item = new JMenuItem("F1 - Toggle Wormhole Systems"));
		item.setHorizontalTextPosition(JMenuItem.RIGHT);
		item.addActionListener(myMenuListener);

		// Menu item 1
		popup.add(item = new JMenuItem("F2 - Find System"));
		item.setHorizontalTextPosition(JMenuItem.RIGHT);
		item.addActionListener(myMenuListener);

		// Menu item 2 separator ------------------------
		popup.addSeparator();

		// Menu item 3
		popup.add(item = new JMenuItem("Add Fleet Marker"));
		item.setHorizontalTextPosition(JMenuItem.RIGHT);
		item.addActionListener(myMenuListener);

		// Menu item 4
		popup.add(item = new JMenuItem("Remove Fleet Marker"));
		item.setHorizontalTextPosition(JMenuItem.RIGHT);
		item.addActionListener(myMenuListener);

		// Menu item 5
		popup.add(item = new JMenuItem("F5 - Toggle Fleets"));
		item.setHorizontalTextPosition(JMenuItem.RIGHT);
		item.addActionListener(myMenuListener);

		// Menu item 6
		popup.add(item = new JMenuItem("F6 - Clear All Markers"));
		item.setHorizontalTextPosition(JMenuItem.RIGHT);
		item.addActionListener(myMenuListener);

		// Menu item 7 separator ------------------------
		popup.addSeparator();

		// Menu item 8
		popup.add(item = new JMenuItem("F7 - Set Connection"));
		item.setHorizontalTextPosition(JMenuItem.RIGHT);
		item.addActionListener(myMenuListener);

		// Menu item 9 separator ------------------------
		popup.addSeparator();

		// Menu item 10
		popup.add(item = new JMenuItem("F8 - About"));
		item.setHorizontalTextPosition(JMenuItem.RIGHT);
		item.addActionListener(myMenuListener);
	}

	// makes the menu visible
	public void addFleet(String name, String systemID, boolean blue, boolean green, boolean red, JTextArea theFleetNotes){
		String fleetContents ="";
		int algn = 10;
		int theColor = 0;
		fleetContents += "";

		if (blue){
			algn = 10;
			theColor = 1;}

		if (green)
		{
			algn = 0;
			theColor = 2;}

		if (red)
		{
			theColor = 3;
			algn = -10;}

		campaign.addItem(name, (int)mapCoord.getX(), 0, (int)mapCoord.getY(), theFleetNotes.getText(), systemID, Integer.valueOf(algn),
			             fleetContents, theColor,
			             "", "", "","", "", 0, 0);
		campaign.addGroupFleet(name, (int)mapCoord.getX(), (int)mapCoord.getY(), Integer.valueOf(algn), theFleetNotes.getText());
	}

	public int getOptionSelected()
	{
		return optionSelected;
	}

	public void showPopup(int x, int y){
		popup.show(MapPopupMenu.this, x, y);
	}

	public void setInfo(ArrayList<Object> starInfo) {
		starData = starInfo;
	}

	public void setScreenCoord(Point2D.Double p) {
		screenCoord = p;
	}

	public void setMapCoord(Point2D.Double p) {
		mapCoord = p;
	}

	public void setToggleWormhole(boolean value) {
		popup.getComponent(0).setEnabled(value);
	}

	public void setLoadFindSystem(boolean value) {
		popup.getComponent(1).setEnabled(value);
	}

	public void setAddFleetMarker(boolean value) {
		popup.getComponent(3).setEnabled(value);
	}

	public void setRemoveFleetMarker(boolean value) {
		popup.getComponent(4).setEnabled(value);
	}

	public void setToggleFleets(boolean value) {
		popup.getComponent(5).setEnabled(value);
	}

	public void setClearAllMarkers(boolean value) {
		popup.getComponent(6).setEnabled(value);
	}

	public void setLoadConnection(boolean value) {
		popup.getComponent(8).setEnabled(value);
	}

	public void setLoadAbout(boolean value) {
		popup.getComponent(9).setEnabled(value);
	}
}