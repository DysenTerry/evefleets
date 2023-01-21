/* EveMap project.
 * Author: TA Lucas
 */

import java.io.*;
import java.util.*;
//import java.util.ArrayList.*;
//import java.lang.Integer.*;
import java.net.URL;
import java.util.Timer;
//import java.util.TimerTask;


/*
 * Constructor initializes everything to empty.
 * And loads campaign from file.
 */
public class Campaign  {

	// Common details
  	ArrayList<String> itemName = new ArrayList<String>();
  	ArrayList<Integer> x = new ArrayList<Integer>();
  	ArrayList<Integer> y = new ArrayList<Integer>();
  	ArrayList<Integer> z = new ArrayList<Integer>();
  	ArrayList<String> itemDescNotes = new ArrayList<String>();
  	ArrayList<String> systemID = new ArrayList<String>();
  	ArrayList<Integer> alignment = new ArrayList<Integer>();

  	// Fleet Specific details
  	ArrayList<String> fleetSelectedShips = new ArrayList<String>();
  	ArrayList<Integer> fleetColor = new ArrayList<Integer>();

	// POS specific details
	ArrayList<String> posOwner = new ArrayList<String>();
	ArrayList<String> posCorp = new ArrayList<String>();
	ArrayList<String> posAlliance = new ArrayList<String>();
	ArrayList<String> posPlanet = new ArrayList<String>();
	ArrayList<String> posMoon = new ArrayList<String>();
	ArrayList<Integer> posType = new ArrayList<Integer>();
	ArrayList<Integer> posSize = new ArrayList<Integer>();

  	int currentItem = 0;
  	int selectedItem = 0;
  	int toggleItem = 0;
  	boolean locked = false;
  	boolean pauseGet = false;

  	String myConnection = "";
  	String myChannel = "";
  	String myPass = "";
  	protected String urlName = "";
  	String connectionMessage = "";

  	protected EveMap papa;

  	protected static Timer timer;


	Campaign(EveMap parent) {
		papa=parent;
		getGroupCampaign();
		timer = new Timer();
	}

	public ArrayList<Object> getNextFleet(){
		ArrayList<Object> thisFleet = new ArrayList<Object>();
		thisFleet.add(itemName.get(currentItem));
		thisFleet.add(x.get(currentItem));
		thisFleet.add(y.get(currentItem));
		thisFleet.add(z.get(currentItem));
		thisFleet.add(itemDescNotes.get(currentItem));
		thisFleet.add(systemID.get(currentItem));
		thisFleet.add(alignment.get(currentItem));

		thisFleet.add(fleetSelectedShips.get(currentItem));
		thisFleet.add(fleetColor.get(currentItem));

		thisFleet.add(posOwner.get(currentItem));
		thisFleet.add(posCorp.get(currentItem));
		thisFleet.add(posAlliance.get(currentItem));
		thisFleet.add(posPlanet.get(currentItem));
		thisFleet.add(posMoon.get(currentItem));
		thisFleet.add(posType.get(currentItem));
		thisFleet.add(posSize.get(currentItem));

		currentItem ++;

		if (currentItem == itemName.size())
			resetList();

		return thisFleet;
	}

	public ArrayList<Object> getFleetAt(double x1, double z1) {
		ArrayList<Object> thisFleet = new ArrayList<Object>();
		int xx = (int)(x1 *-1)-4;
		int zz  = (int)(z1 *-1) -5;
		int xRange = (int)xx +4;
		int zRange =  (int)zz +4;

		for (int i = xRange;i>=xx;i--)
		{
			for (int j =zRange;j>=zz;j--)
			{
				for (int k = 0;  k < x.size(); k++)
				{
					if (i==x.get(k) && j==z.get(k))
					{
						selectedItem = k;
						thisFleet.add(itemName.get(k));
						if (Integer.parseInt(alignment.get(k).toString()) ==10)
							thisFleet.add("Friendly");
						else if(Integer.parseInt(alignment.get(k).toString()) ==0)
							thisFleet.add("Neutral");
						else
							thisFleet.add("Enemy");
						thisFleet.add(itemDescNotes.get(k));
						thisFleet.add(k);
						return thisFleet;
					}
				}
			}
		}
		return null;
	}

	public void updateSelectedPosition(int newX, int newZ, boolean updateServer)
	{
		if (itemName.size() > 0)
		{
			x.set(selectedItem, (newX*-1)-2);
			z.set(selectedItem, (newZ*-1)-2);
			setPause(true);
			if (updateServer)
			{
				Thread thread = new Thread() {
	            public void run() {
			    	updateGroupFleet();
			    	setPause(false);
			    	connectionMessage = " Fleet Moved: " + itemName.get(selectedItem).toString();
			    	papa.frame.setTitle("EVEFleets" + getConnectionMessage());
	            }
	        	};
	        	thread.start();
	        	thread.setName("updateSelectedPosition");

	        	Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
	        	Thread[] threadArray = threadSet.toArray(new Thread[threadSet.size()]);
	        	System.out.println(threadArray[0].getName());


			}
		}
	}

	private void updateGroupFleet()
	{
		String pattern= "^[a-zA-Z0-9]*$";
		String temp = "";
		//String tempFleetName = "";
		//int tempX = 0;
		//int tempZ = 0;
		//String tempNotes = "";
		//String tempTag = "";
		//Integer tempAllignment = new Integer("0");
		//int updated = 0;

		try{
			//The first line of config.txt is the server URL
			BufferedReader in = new BufferedReader(new FileReader("config.txt"));
			String str = in.readLine();
			if (str != null)
				myConnection = str;
			else
			{
				myConnection = "";
				connectionMessage = "    Not Connected...Enter an EVEFleets server URL.";
				in.close();
				return;
			}


			//The second line of the config.txt is the channel name.
  			str = in.readLine();
  			if (str == null || str.compareTo("")==0)
  			{
  				connectionMessage = "    Not Connected...Enter a channel name.";
				in.close();
				return;
  			}
    		if (!str.matches(pattern))
  			{
  				connectionMessage = "    Not Connected...Channel contains invalid characters.  a-Z and 0-9 allowed.";
				in.close();
				return;
  			}
  			myChannel = str;


			//The thrid line of the config.txt is the channel password
  			str = in.readLine();
  			if (str == null || str.compareTo("")==0)
  			{
  				connectionMessage = "    Not Connected...Enter a password.";
				in.close();
				return;
  			}
    		if (!str.matches(pattern))
  			{
  				connectionMessage = "    Not Connected...Password contains invalid characters.  a-Z and 0-9 allowed.";
				  in.close();
				  return;
  			}
  			myPass = str;

			in.close();

			urlName = myConnection + "/edit_fleet.php?cn=" + myChannel + "&pw=" + myPass + "&fn=" + itemName.get(selectedItem).toString()
				+ "&xx=" + x.get(selectedItem).toString() + "&yy=" + z.get(selectedItem).toString();
			URL url = new URL(urlName);
			BufferedReader webpage = new BufferedReader(new InputStreamReader(url.openStream()));

			while ((str = webpage.readLine()) != null){
				temp = str;
	            if (temp.compareTo("Success")==0)
	            {

	            	return;
	            }


	        }
		}
		catch (IOException e){
			connectionMessage = "     Cannot connect to server.  Contact your system admin.";
		}
	}

	// This grabs the most recent fleet activity from the web.
	public void getGroupCampaign ()
	{
		if (pauseGet)
			return;
		String pattern= "^[a-zA-Z0-9]*$";
		String temp = "";
		String tempFleetName = "";
		int tempX = 0;
		int tempZ = 0;
		String tempNotes = "";
		String tempTag = "";
		Integer tempAllignment = Integer.valueOf("0");
		int updated = 0;

		try{
			//The first line of config.txt is the server URL
			BufferedReader in = new BufferedReader(new FileReader("config.txt"));
			String str = in.readLine();
			if (str != null)
				myConnection = str;
			else
			{
				myConnection = "";
				connectionMessage = "    Not Connected...Enter an EVEFleets server URL.";
				in.close();
				return;
			}


			//The second line of the config.txt is the channel name.
  			str = in.readLine();
  			if (str == null || str.compareTo("")==0)
  			{
  				connectionMessage = "    Not Connected...Enter a channel name.";
				in.close();
				return;
  			}
    		if (!str.matches(pattern))
  			{
  				connectionMessage = "    Not Connected...Channel contains invalid characters.  a-Z and 0-9 allowed.";
				in.close();
				return;
  			}
  			myChannel = str;


			//The thrid line of the config.txt is the channel password
  			str = in.readLine();
  			if (str == null || str.compareTo("")==0)
  			{
  				connectionMessage = "    Not Connected...Enter a password.";
				in.close();
				return;
  			}
    		if (!str.matches(pattern))
  			{
  				connectionMessage = "    Not Connected...Password contains invalid characters.  a-Z and 0-9 allowed.";
				in.close();
				return;
  			}
  			myPass = str;

			in.close();

			urlName = myConnection + "/get.php?channelname=" + myChannel + "&password=" + myPass;
			URL url = new URL(urlName);
			BufferedReader webpage = new BufferedReader(new InputStreamReader(url.openStream()));

			while ((str = webpage.readLine()) != null){
				temp = str; //.replaceAll("\\s","");
	            if (temp.compareTo("ERR:Password")==0)
	            {
	            	connectionMessage = "    Not Connected...Channel or Password is incorrect.";
	            	return;
	            }
	            if (temp.compareTo("ERR:Channel")==0)
	            {
	            	connectionMessage = "    Not Connected...Channel or Password is incorrect.";
	            	return;
	            }
	            if (temp.compareTo("ERR:System")==0)
	            {
	            	connectionMessage = "    Unable to connect to Database.  Contact your system admin.";
	            	return;
	            }

				connectionMessage = "     Connected to: " + myConnection + "     channel: " + myChannel;
				/*  -----------------------------------------------   */


				if (temp.compareTo("FleetStart")==0)
				{

				}
				else if (temp.compareTo("FleetEnd")==0)
				{
					updated ++;
					if (updated == 1)
						clearList(false);
					addItem(tempFleetName, tempX, 0, tempZ, tempNotes, "0", tempAllignment, "", 0, "", "", "","", "", 0, 0);
					papa.canvas.repaint();
					tempFleetName = "";
					tempX=0;
					tempZ=0;
					tempNotes="";
					tempAllignment=0;
				}
				else
				{
					if (temp.compareTo("NameStart")==0)
					{
						tempTag = "NameStart";
						continue;
					}
					if (temp.compareTo("NameEnd")==0)
					{
						tempTag = "";
						continue;
					}
					if(tempTag.compareTo("NameStart")==0)
						tempFleetName += temp;

					if (temp.compareTo("XStart")==0)
					{
						tempTag = "XStart";
						continue;
					}
					if (temp.compareTo("XEnd")==0)
					{
						tempTag = "";
						continue;
					}
					if(tempTag.compareTo("XStart")==0)
						tempX = Integer.parseInt(temp);

					if (temp.compareTo("YStart")==0)
					{
						tempTag = "YStart";
						continue;
					}
					if (temp.compareTo("YEnd")==0)
					{
						tempTag = "";
						continue;
					}
					if(tempTag.compareTo("YStart")==0)
						tempZ = Integer.parseInt(temp);

					if (temp.compareTo("AlignStart")==0)
					{
						tempTag = "AlignStart";
						continue;
					}
					if (temp.compareTo("AlignEnd")==0)
					{
						tempTag = "";
						continue;
					}
					if(tempTag.compareTo("AlignStart")==0)
						tempAllignment = Integer.valueOf(temp);

					if (temp.compareTo("NotesStart")==0)
					{
						tempTag = "NotesStart";
						continue;
					}
					if (temp.compareTo("NotesEnd")==0)
					{
						tempTag = "";
						continue;
					}
					if(tempTag.compareTo("NotesStart")==0)
						tempNotes += temp; // + "\n";
				}
			/*  -----------------------------------------------   */
	        }

		}
		catch (IOException e){
			connectionMessage = "     Cannot connect to server.  Contact your system admin.";
		}
	}

	private void deleteGroupFleet(String fleetToDelete)
	{
		String pattern= "^[a-zA-Z0-9]*$";
		String temp = "";
		//String tempFleetName = "";
		//int tempX = 0;
		//int tempZ = 0;
		//String tempNotes = "";
		//String tempTag = "";
		//Integer tempAllignment = new Integer("0");
		//int updated = 0;

		try{
			//The first line of config.txt is the server URL
			BufferedReader in = new BufferedReader(new FileReader("config.txt"));
			String str = in.readLine();
			if (str != null)
				myConnection = str;
			else
			{
				myConnection = "";
				connectionMessage = "    Not Connected...Enter an EVEFleets server URL.";
				in.close();
				return;
			}


			//The second line of the config.txt is the channel name.
  			str = in.readLine();
  			if (str == null || str.compareTo("")==0)
  			{
  				connectionMessage = "    Not Connected...Enter a channel name.";
				in.close();
				return;
  			}
    		if (!str.matches(pattern))
  			{
  				connectionMessage = "    Not Connected...Channel contains invalid characters.  a-Z and 0-9 allowed.";
				in.close();
				return;
  			}
  			myChannel = str;


			//The thrid line of the config.txt is the channel password
  			str = in.readLine();
  			if (str == null || str.compareTo("")==0)
  			{
  				connectionMessage = "    Not Connected...Enter a password.";
				in.close();
				return;
  			}
    		if (!str.matches(pattern))
  			{
  				connectionMessage = "    Not Connected...Password contains invalid characters.  a-Z and 0-9 allowed.";
				in.close();
				return;
  			}
  			myPass = str;

			in.close();

			urlName = myConnection + "/delete_fleet.php?cn=" + myChannel + "&pw=" + myPass + "&fn=" + fleetToDelete;
			URL url = new URL(urlName);
			BufferedReader webpage = new BufferedReader(new InputStreamReader(url.openStream()));

			while ((str = webpage.readLine()) != null){
				temp = str;
	            if (temp.compareTo("Success")==0)
	            {

	            	return;
	            }


	        }
		}
		catch (IOException e){
			connectionMessage = "     Cannot connect to server.  Contact your system admin.";
		}
	}

	protected void addGroupFleet(String theFleetName, int theX, int theY, Integer theAlign, String theNotes)
	{
		String pattern= "^[a-zA-Z0-9]*$";
		String temp = "";
		//String tempFleetName = "";
		//int tempX = 0;
		//int tempZ = 0;
		//String tempNotes = "";
		//String tempTag = "";
		//Integer tempAllignment = new Integer("0");
		//int updated = 0;

		try{
			//The first line of config.txt is the server URL
			BufferedReader in = new BufferedReader(new FileReader("config.txt"));
			String str = in.readLine();
			if (str != null)
				myConnection = str;
			else
			{
				myConnection = "";
				connectionMessage = "    Not Connected...Enter an EVEFleets server URL.";
				in.close();
				return;
			}


			//The second line of the config.txt is the channel name.
  			str = in.readLine();
  			if (str == null || str.compareTo("")==0)
  			{
  				connectionMessage = "    Not Connected...Enter a channel name.";
				  in.close();
				return;
  			}
    		if (!str.matches(pattern))
  			{
  				connectionMessage = "    Not Connected...Channel contains invalid characters.  a-Z and 0-9 allowed.";
				in.close();
				return;
  			}
  			myChannel = str;


			//The thrid line of the config.txt is the channel password
  			str = in.readLine();
  			if (str == null || str.compareTo("")==0)
  			{
  				connectionMessage = "    Not Connected...Enter a password.";
				  in.close();
				return;
  			}
    		if (!str.matches(pattern))
  			{
  				connectionMessage = "    Not Connected...Password contains invalid characters.  a-Z and 0-9 allowed.";
				  in.close();
				return;
  			}
  			myPass = str;

			in.close();

			urlName = myConnection + "/create_fleet.php?cn=" + myChannel + "&pw=" + myPass + "&fn=" + theFleetName + "&xx=" + theX
				+ "&yy=" + theY + "&al=" + theAlign.toString() + "&no=" + theNotes;
			URL url = new URL(urlName);
			BufferedReader webpage = new BufferedReader(new InputStreamReader(url.openStream()));

			while ((str = webpage.readLine()) != null){
				temp = str;
	            if (temp.compareTo("Success")==0)
	            {

	            	return;
	            }


	        }
		}
		catch (IOException e){
			connectionMessage = "     Cannot connect to server.  Contact your system admin.";
		}
	}

	// getSize locks the record until it is unlocked by resetList
	// Whenever calling getSize...include it in a loop that ends with resetList()
	public int getSize(){
		locked = true;
		return itemName.size();
	}

	public void resetList(){
		locked = false;
		currentItem = 0;
	}

	public void setPause(boolean bool){
		pauseGet = bool;
	}

	public String getConnectionMessage(){
		return connectionMessage;
	}

	// This will rotate between all of the fleets and set focus to each in turn
	// If no fleets exist it will set focus to the center of the map x=0 y=0
	public ArrayList<Object> toggleNextFleet(){
		ArrayList<Object> toggleFleet = new ArrayList<Object>();
		if (x.size()==0)  //No fleets exist, go to center of the map
		{
			toggleFleet.add(Integer.valueOf(0));
			toggleFleet.add(Integer.valueOf(0));
		}
		else
		{
			if (toggleItem >= x.size())
			{
				toggleItem = 0;
			}
			toggleFleet.add(x.get(toggleItem));
			toggleFleet.add(z.get(toggleItem));
			toggleItem ++;
		}
		return toggleFleet;
	}

	// This will remove an individual fleet
	public void removeFleet(int removeFleet){
		if(removeFleet < itemName.size())
		{
			final String theFleet = itemName.get(removeFleet).toString();
			setPause(true);
			new Thread() {
            public void run() {
		    	deleteGroupFleet(theFleet);
		    	setPause(false);
		    	connectionMessage = " Fleet Removed: " + theFleet;
		    	papa.frame.setTitle("EVEFleets" + getConnectionMessage());
            }
        	}.start();

			itemName.remove(removeFleet);
			x.remove(removeFleet);
			y.remove(removeFleet);
			z.remove(removeFleet);
			itemDescNotes.remove(removeFleet);
			systemID.remove(removeFleet);
			alignment.remove(removeFleet);

			fleetSelectedShips.remove(removeFleet);
			fleetColor.remove(removeFleet);

			posOwner.remove(removeFleet);
			posCorp.remove(removeFleet);
			posAlliance.remove(removeFleet);
			posPlanet.remove(removeFleet);
			posMoon.remove(removeFleet);
			posType.remove(removeFleet);
			posSize.remove(removeFleet);

		}
	}

	// This will clear all fleets in the list.
	public void clearList(boolean updateServer){

		int size = itemName.size();
		if (updateServer)
		{
			for (int i=0;i<size;i++)
			{
				removeFleet(0);
			}
		}


		itemName.clear();
		x.clear();
		y.clear();
		z.clear();
		itemDescNotes.clear();
		systemID.clear();
		alignment.clear();

	  	fleetSelectedShips.clear();
	  	fleetColor.clear();

		posOwner.clear();
		posCorp.clear();
		posAlliance.clear();
		posPlanet.clear();
		posMoon.clear();
		posType.clear();
		posSize.clear();

	}

	// This will add a fleet.
	public void addItem(String theitemName, int theX, int theY, int theZ, String theNotes, String theSystemID, Integer theAlignment,
		                String fleetContents,  int theColor,
		                String theOwner, String theCorp, String theAlliance, String thePlanet, String theMoon, int theType, int theSize){

		//addGroupFleet(theitemName, theX, theZ, theAlignment, theNotes);
		itemName.add(theitemName);
		x.add(theX);
		y.add(theY);
		z.add(theZ);
		itemDescNotes.add(theNotes);
		systemID.add(theSystemID);
		alignment.add(theAlignment);

		fleetSelectedShips.add(fleetContents);
		fleetColor.add(theColor);

		posOwner.add(theOwner);
		posCorp.add(theCorp);
		posAlliance.add(theAlliance);
		posPlanet.add(thePlanet);
		posMoon.add(theMoon);
		posType.add(theType);
		posSize.add(theSize);
	}
}