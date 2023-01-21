/* EveMap project.
 * Author: TA Lucas
 */


/* Bug list & new feature list
 *
 * Add CTRL + F   --key combos that do same as menu
 * Kill fleet add and system find windows on exit
 *
 */

 /* -- A few definitions
  * translateX    =
  * affineX       =
  * screenSize    				= size of the monitor resolution. Example 1600 x 900
  * offsetX       				= is the point under the center of the monitor
  * lastOffsetX   				= is the last position the mouse key was pressed
  * frame.getX()  				= is the X position of the frame(map)
  * frame.getSize().getWidth() 	= the width of the frame(map)
  */
import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionEvent;
import java.awt.geom.*;
import javax.swing.JComponent;
import javax.swing.JFrame;
import java.util.*;
import javax.swing.JLabel;
import java.text.*;
import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import javax.swing.text.html.HTMLEditorKit;
import java.util.Timer;
import java.util.TimerTask;
//import java.io.*;
import javax.swing.JWindow;
//import javax.swing.JScrollPane;
import java.awt.BorderLayout;
//import javax.swing.text.html.HTMLEditorKit;
//import java.net.URL;
import javax.swing.JTextArea;

public class EveMap {
	public static MapBase canvas;

	protected static JFrame frame;
	protected static Stars myStars;
	private static Jumps myJumps;
	protected static Campaign myCampaign;
	//private static WebData jumpsLastHour;
	//private static WebData killsLastHour;
	//private static WebData sovereignty;

	protected static MapPopupMenu myMenu;
	protected static double offsetX;
	protected static double offsetY;
	protected static int clickCount = 0;
	protected static long elapsedTimeMillis;
	protected static long startTimeMillis;
	protected static Dimension screenSize, windowSize;
	protected static boolean campaignDrag = false;
	protected static Timer timer;
	protected static JWindow infoWindow;
	protected static JEditorPane infoPane;
	protected static JScrollPane infoScrollPane;
	protected static int selectedFleet;
	protected static JTextArea txtAreaInfo;

	//protected static int testCounter = 0;
	//protected static int anotherCounter = 0;


	public static void main(String[] args) {
		//Timer accesses the web on regular routine.
		timer = new Timer();

		//Set instance of EveMap.. access to non-static variables.
		EveMap papa = new EveMap();

		//Gets the screen size
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();

		// Create HTML code for loading screen
    	String htmlStart = "<html><body bgcolor=\"#262525\"><font color=\"#6D7C5E9\" size=\"+2\">";
    	String htmlMap = "&nbsp;EVE Fleets version 1.3</font><font color=\"#6D7C5E9\" size=\"+1\">";
    	String htmlStar = "<br>&nbsp;Creating Stars: ";
    	String htmlCamp = "<br>&nbsp;Initializing Campaign: ";
    	String htmlJump = "<br>...Rubicon 1.0.4 93577 build<br><br>&nbsp;Creating Jumps: ";
    	//String htmlCelest = "<br>&nbsp;Adding Celestial Objects: ";
    	//String htmlOnlineJump = "<br>&nbsp;Accessing eveonline...Jumps in the last hour.";
    	//String htmlOnlineKill = "<br>&nbsp;Accessing eveonline...Kills in the last hour.";
    	//String htmlOnlineSov = "<br>&nbsp;Accessing eveonline...System sovereignty.";
    	String htmlEnd = "</font></body></html>";
    	selectedFleet=0;

 		//Creates the loading screen
    	JWindow splash = new JWindow(  );
    	splash.setSize(new Dimension(400, 250));
    	JEditorPane myPane = new JEditorPane();
    	myPane.setEditable(false);
    	JScrollPane editorScrollPane = new JScrollPane(myPane);
    	editorScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    	splash.getContentPane().add(BorderLayout.CENTER, editorScrollPane);
    	myPane.setContentType(new HTMLEditorKit().getContentType());
    	splash.add(myPane);
    	splash.setLocation(screenSize.width/2 -200, screenSize.height/2-125);
    	splash.setVisible(true) ;

		// Builds the main window
		Icon myIcon = new Icon();
		frame = new JFrame();
		frame.setIconImage(myIcon.getIcon());
		frame.setTitle("EVEFleets");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JLabel label = new JLabel(" ", JLabel.CENTER);

		canvas = new MapBase();

		//Initialize jumps
		myJumps = new Jumps();
		myPane.setText(htmlStart + htmlMap
			                     + htmlJump + myJumps.getSize()
			                     + htmlEnd);

		//Initialize stars
		myStars = new Stars();
		myPane.setText(htmlStart + htmlMap
			                     + htmlJump + myJumps.getSize()
			                     + htmlStar + myStars.getSize()
			                     + htmlEnd);

		//Initialize campaign
		myCampaign = new Campaign(papa);
		myPane.setText(htmlStart + htmlMap
			                     + htmlJump + myJumps.getSize()
			                     + htmlStar + myStars.getSize()
			                     + htmlCamp + myCampaign.getSize()
			                     + htmlEnd);


		//Initialize celectial objects
		//myPane.setText(htmlStart + htmlMap  + htmlJump + myJumps.getSize()  + htmlStar + htmlCamp + htmlCelest + htmlEnd);
		//myStars.updateStars();

		//pause to see splash screen
		try{Thread.sleep(4000);}
		catch (Exception e){}


		// Create timer for accessing the web.  Runs in a different thread every three seconds
		timer.scheduleAtFixedRate(new TimerTask() {
		   public void run() {
		    	myCampaign.getGroupCampaign();
		    	frame.setTitle("EVEFleets" + myCampaign.getConnectionMessage());
		   }
		  }, 0, 3000);

		// Kills the timer thread and other windows before exiting
		frame.addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		        timer.cancel();
		        myCampaign.timer.cancel();
		        infoWindow.dispose();
		        if(myMenu.myMenuListener.frameFindSystem != null)
		        	myMenu.myMenuListener.frameFindSystem.dispose();

		        if(myMenu.myMenuListener.frameAddFleet != null)
		        	myMenu.myMenuListener.frameAddFleet.dispose();

		        if(myMenu.myMenuListener.frameConnection != null)
		        	myMenu.myMenuListener.frameConnection.dispose();

		        if(myMenu.myMenuListener.frameViewAbout != null)
		        	myMenu.myMenuListener.frameViewAbout.dispose();
		    }
		});

		// Checks for a function keypress, then callse the appropriate menu option
		frame.addKeyListener(new KeyListener() {
		    public void keyPressed(KeyEvent e) {

		    	if (e.getKeyCode() == 112)
		    		myMenu.myMenuListener.actionPerformed(new ActionEvent((Object)myMenu,1,"F1 - Toggle Wormhole Systems"));

		    	if (e.getKeyCode() == 113)
		    	{
		    		myMenu.setScreenCoord(  new Point2D.Double(screenSize.width/2 -125, screenSize.height/2 -50)    );
		    		myMenu.myMenuListener.actionPerformed(new ActionEvent((Object)myMenu,1,"F2 - Find System"));
		    	}

		    	if (e.getKeyCode() == 116)
		    	{
		    		myMenu.myMenuListener.actionPerformed(new ActionEvent((Object)myMenu,1,"F5 - Toggle Fleets"));
		    		canvas.repaint();
		    	}
		    	if (e.getKeyCode() == 117)
		    	{
		    		myMenu.myMenuListener.actionPerformed(new ActionEvent((Object)myMenu,1,"F6 - Clear All Markers"));
		    		canvas.repaint();
		    	}

		    	if (e.getKeyCode() == 118)
		    	{
		    		myMenu.setScreenCoord(  new Point2D.Double(screenSize.width/2 -190, screenSize.height/2 -125)    );
		    		myMenu.myMenuListener.actionPerformed(new ActionEvent((Object)myMenu,1,"F7 - Set Connection"));
		    	}
		    	if (e.getKeyCode() == 119)
		    	{
		    		myMenu.setScreenCoord(  new Point2D.Double(screenSize.width/2 -250, screenSize.height/2 -150)    );
		    		myMenu.myMenuListener.actionPerformed(new ActionEvent((Object)myMenu,1,"F8 - About"));
		    	}

		    }
		    public void keyReleased(KeyEvent e) { }
		    public void keyTyped(KeyEvent e) { }
		});

		// Get data from eveonline
		//myPane.setText(htmlStart + htmlMap + htmlJump + htmlStar + htmlCelest + htmlOnlineJump + htmlEnd);
		//jumpsLastHour = new WebData("https://api.eveonline.com/map/Jumps.xml.aspx");
		//myStars.updateWebData(jumpsLastHour.getXMLData());
		//myPane.setText(htmlStart + htmlMap + htmlJump + htmlStar + htmlCelest + htmlOnlineJump + htmlOnlineKill + htmlEnd);
		//killsLastHour = new WebData("https://api.eveonline.com/map/Kills.xml.aspx");
		//myPane.setText(htmlStart + htmlMap + htmlJump + htmlStar + htmlCelest + htmlOnlineJump + htmlOnlineKill + htmlOnlineSov + htmlEnd);
		//sovereignty = new WebData("https://api.eveonline.com/map/Sovereignty.xml.aspx");

		//https://api.eveonline.com/corp/CorporationSheet.xml.aspx?corporationID=469434811

		// Builds info window for mouseover of stars
		infoWindow = new JWindow(  );
		infoWindow.setSize(300, 200);
		txtAreaInfo = new JTextArea();
		txtAreaInfo.setLineWrap(true);
		txtAreaInfo.setWrapStyleWord(true);
	    txtAreaInfo.setEditable(false);
		infoPane = new JEditorPane();
		infoPane.setEditable(false);
		infoScrollPane = new JScrollPane(infoPane);
		//infoScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		//infoScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

		//infoScrollPane.setPreferredSize(new Dimension(450, 250));
		infoWindow.getContentPane().add(BorderLayout.CENTER, infoScrollPane);
		infoPane.setContentType(new HTMLEditorKit().getContentType());
		infoWindow.add(infoScrollPane);
		infoPane.setText("");

		//Building the canvas
		myMenu = new MapPopupMenu(papa, myCampaign, null, new Point2D.Double(), new Point2D.Double());

		//canvas = new MapBase();
		MouseDrag translater = new MouseDrag();
		canvas.addMouseListener(translater);
		canvas.addMouseMotionListener(translater);
		canvas.addMouseWheelListener(new Zoom());

		frame.setLayout(new BorderLayout());
		frame.getContentPane().add(canvas, BorderLayout.CENTER);
		frame.getContentPane().add(myMenu,BorderLayout.SOUTH);
		frame.getContentPane().add(label, BorderLayout.SOUTH);

		frame.setSize(screenSize.width - (int)(screenSize.width*.2), screenSize.height - (int)(screenSize.height*.2));
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.validate();
	    frame.setLocationRelativeTo(null);
	    frame.setVisible(true);

	    //Set center of the canvas to the center of the Jframe
	    canvas.scale = 1;
	    canvas.translateX = (screenSize.width - (int)(screenSize.width*.2))/2;
		canvas.translateY =(screenSize.height - (int)(screenSize.height*.2))/2;

		//Display canvas and close splash screen
		splash.dispose();
		canvas.repaint();
	}



	public static class MapBase extends JComponent {
		protected double translateX, translateY, scale;

		protected double affineX, affineY;

		protected double dx, dy, ssYmod, ssXmod;

		protected int ssSunSize, ssSecondSunSize, ssPlanetOrbit, ssPlanetSize, ssMoonSize,
					  ssGateSize, ssBeltSize, ssStationSize;

		protected Color ssGateColor, ssAsteroidColor, ssIceColor, ssUnknownColor, ssSunColor,
			            ssPlanetColor, ssMoonColor, ssStationColor, fleetColor, eFleetColor, gFleetColor, e2FleetColor, fleetColor2;

		MapBase() {
			translateX = 0;
			translateY = 0;
			affineX = 0;
			affineY = 0;
			scale = 1;
			setOpaque(true);
			setDoubleBuffered(true);

			ssXmod = -.005;
			ssYmod = .005;
			ssPlanetSize = 110;
			ssMoonSize	= 1;
			ssGateSize = 1;
			ssBeltSize = 20;
			ssStationSize = 1;
			ssSunSize = 130;
			ssSecondSunSize = 120;
			ssStationColor = new Color(0,51,255);
			ssAsteroidColor = new Color(153,51,0);
			ssIceColor = new Color(51,255,204);
			ssUnknownColor = new Color(51,255,204);
			ssSunColor = Color.WHITE;
			ssPlanetColor = Color.GRAY;
			ssMoonColor = Color.CYAN;
			ssGateColor = Color.RED;
			fleetColor = Color.BLUE;
			eFleetColor = Color.RED;
			e2FleetColor = Color.ORANGE;
			gFleetColor = Color.GREEN;
			fleetColor2 = new Color(51,204,255);
		}

		@Override public void paint(Graphics g) {


			AffineTransform tx = new AffineTransform();
			tx.translate(translateX, translateY);
			tx.scale(scale, scale);
			Graphics2D ourGraphics = (Graphics2D) g;
			ourGraphics.setColor(Color.BLACK);
			ourGraphics.fillRect(0, 0, getWidth(), getHeight());
			ourGraphics.setTransform(tx);
			ourGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			ourGraphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

			// Paint Jumps
			ourGraphics.setColor(new Color(0,51,51));

			for (int i=0;i<myJumps.getSize();i++)
			{

				ArrayList<Object> thisJump = myJumps.getNextJump();
				ourGraphics.drawLine(Integer.parseInt(thisJump.get(1).toString()) +2,
					                 Integer.parseInt(thisJump.get(3).toString()) +2,
					                 Integer.parseInt(thisJump.get(5).toString()) +2,
					                 Integer.parseInt(thisJump.get(7).toString()) +2);
			//testCounter ++;
			}

			myJumps.resetList();


			// Paint Stars
			for (int i=0;i<myStars.getSize();i++)
			{
				ArrayList<Object> thisStar = myStars.getNextStar(1);

				//Double temp2 = (Double)thisStar.get(5);
				//String temp1 = (String)thisStar.get(0);
		/*		if((temp1.compareTo("Cobalt Edge")==0 ||
				   temp1.compareTo("Perrigen Falls")==0 ||
				   temp1.compareTo("Malpais")==0 ||
				   temp1.compareTo("Oasa")==0 ||
				   temp1.compareTo("The Kalevala Expanse")==0 ||
				   temp1.compareTo("Outer Passage")==0 ||
				   	temp1.compareTo("Etherium Reach")==0 ||
				   temp1.compareTo("The Spire")==0
					))
				{
					ourGraphics.setColor(Color.RED);
				}
				else
				{
					ourGraphics.setColor(Color.WHITE);
				} */
				ourGraphics.setColor((Color)thisStar.get(6));
				//ourGraphics.setColor(Color.WHITE);

				ourGraphics.fillOval(Integer.parseInt(thisStar.get(2).toString()), Integer.parseInt(thisStar.get(4).toString()),  4, 4 );

			//testCounter ++;
			}
			myStars.resetList();

			// Paint Campaign
			//int[] fleetStarX;
			//int[] fleetStarY;
			int campaignX = 0;
			int campaignY = 0;
			//double fleetScale = 1.0;

			for (int i=0;i<myCampaign.getSize();i++)
			{
				//testCounter ++;
				ArrayList<Object> thisCampaign = myCampaign.getNextFleet();

				if (thisCampaign.get(5).toString().compareTo("0")==0)
				{
					if (thisCampaign.get(6).toString().compareTo("10")==0)
						ourGraphics.setColor(fleetColor);

					if (thisCampaign.get(6).toString().compareTo("5")==0)
						ourGraphics.setColor(fleetColor2);

					if (thisCampaign.get(6).toString().compareTo("0")==0)
						ourGraphics.setColor(gFleetColor);

					if (thisCampaign.get(6).toString().compareTo("-5")==0)
						ourGraphics.setColor(e2FleetColor);

					if (thisCampaign.get(6).toString().compareTo("-10")==0)
						ourGraphics.setColor(eFleetColor);

					// Paints the Fleet Symbol
					//ourGraphics.fillPolygon(fleetStarX, fleetStarY, 3);
					campaignX = Integer.parseInt(thisCampaign.get(1).toString());
					campaignY = Integer.parseInt(thisCampaign.get(3).toString());

					ourGraphics.fillOval(campaignX, campaignY, 4,4);
					ourGraphics.drawLine(campaignX+2, campaignY-2, campaignX,campaignY);
					ourGraphics.drawLine(campaignX+4, campaignY, campaignX+2, campaignY-2);

					ourGraphics.drawLine(campaignX+2, campaignY-4, campaignX,campaignY-2);
					ourGraphics.drawLine(campaignX+4, campaignY-2, campaignX+2, campaignY-4);

					ourGraphics.drawLine(campaignX+2, campaignY-6, campaignX,campaignY-4);
					ourGraphics.drawLine(campaignX+4, campaignY-4, campaignX+2, campaignY-6);

				}
			}
			myCampaign.resetList();

			affineX = ourGraphics.getTransform().getTranslateX();
			affineY = ourGraphics.getTransform().getTranslateY();

		}
	}



	private static class MouseDrag implements MouseListener, MouseMotionListener {
		private int lastOffsetX;
		private int lastOffsetY;

		public void mousePressed(MouseEvent e) {
			// capture starting point
			windowSize = frame.getSize();
			lastOffsetX = e.getX();
			lastOffsetY = e.getY();
			if(myCampaign.getFleetAt((canvas.affineX - e.getX())/canvas.scale, (canvas.affineY - e.getY())/canvas.scale) == null   )
			{
				campaignDrag = true;
			}
			infoWindow.setVisible(false);
			canvas.repaint();

		}

		public void mouseReleased(MouseEvent e) {
			//offsetX & offsetY is the point under the center of the monitor
			if(!campaignDrag)
			{
				myCampaign.updateSelectedPosition((int)((canvas.affineX - e.getX())/canvas.scale), (int)((canvas.affineY - e.getY())/canvas.scale), true);
				//myCampaign.setPause(false);
			}
			campaignDrag = false;
			windowSize = frame.getSize();
			offsetX = (canvas.translateX - (windowSize.width)/2) /canvas.scale;
			offsetY = (canvas.translateY - (windowSize.height)/2) /canvas.scale;
			canvas.repaint();

		}

		public void mouseDragged(MouseEvent e) {

			if(campaignDrag)
			{
				// new x and y are defined by current mouse location subtracted
				// by previously processed mouse location
				int newX = e.getX() - lastOffsetX;
				int newY = e.getY() - lastOffsetY;

				// increment last offset to last processed by drag event.
				lastOffsetX += newX;
				lastOffsetY += newY;

				// update the canvas locations
				canvas.translateX += newX;
				canvas.translateY += newY;
				//canvas.repaint();
			}
			else
			{
				myCampaign.updateSelectedPosition((int)((canvas.affineX - e.getX())/canvas.scale), (int)((canvas.affineY - e.getY())/canvas.scale), false);
				canvas.repaint();
			}
			canvas.repaint();
		}

		public void mouseMoved(MouseEvent e) {

			JLabel myLabel = (JLabel)frame.getContentPane().getComponent(2);
			DecimalFormat df = new DecimalFormat("#.##");
			String boxText = "";

			if (myStars.getStarAt((canvas.affineX - e.getX())/canvas.scale, (canvas.affineY - e.getY())/canvas.scale) == null   )
			{
				myLabel.setText("x = " + df.format(  ((canvas.affineX - e.getX())/canvas.scale*-1)  ) + " " + "y = " + df.format(  ((canvas.affineY - e.getY())/canvas.scale)*-1   ));
					if(myCampaign.getFleetAt((canvas.affineX - e.getX())/canvas.scale, (canvas.affineY - e.getY())/canvas.scale) != null   )
					{
						ArrayList<Object> campaignData = myCampaign.getFleetAt((canvas.affineX - e.getX())/canvas.scale, (canvas.affineY - e.getY())/canvas.scale);
						boxText = "<html><body bgcolor=\"#262525\">"
		                  + "<font face=\"arial\" color=\"#6D7C5E9\" size=\"+1\">\n&nbsp;Fleet Name: " + (String)campaignData.get(0) + "</font><br>"
		                  + "<font face=\"arial\" color=\"#6D7C5E9\" size=\"+1\">\n&nbsp;Alignment: " + (String)campaignData.get(1) + "</font><br>"
		                  + "<font face=\"arial\" color=\"#6D7C5E9\" size=\"+1\">\n&nbsp;Description: </font><br><font color=\"#6D7C5E9\" size=\"+1\"><pre><span style=\"Color:#6D7C5E9; font-size: 18pt; font-family: arial\">"
		                  + formatEditorPaneText((String)campaignData.get(2))
		                  + "</span></pre></font><br></body></html>";
						selectedFleet = (Integer)campaignData.get(3);
						infoPane.setText(boxText);
						infoWindow.setSize(boxWidth(formatEditorPaneText((String)campaignData.get(2))), boxHeight( formatEditorPaneText((String)campaignData.get(2)) ));
		                infoWindow.setLocation(frame.getX() +15 + e.getX(),frame.getY() +5 +e.getY());
						infoWindow.setVisible(true);
						//canvas.repaint();
					}
					else
					{
						infoWindow.dispose();
						//canvas.repaint();
					}
			}
			else
			{
				ArrayList<Object> starData = myStars.getStarAt((canvas.affineX - e.getX())/canvas.scale, (canvas.affineY - e.getY())/canvas.scale);
				myLabel.setText( "Region: " + (String)starData.get(0) + ",    System: " + (String)starData.get(1) + ",    Security: " + starData.get(5));
				infoWindow.setSize(300, 200);
				String theOres = "";
				String theIces = "";
				if (starData.get(8).toString().compareTo("Y")==0)
					theOres = (String)starData.get(7);
				if (starData.get(9).toString().compareTo("Y")==0)
					theIces = (String)starData.get(6);
				if (starData.get(9).toString().compareTo("Y")==0 && starData.get(8).toString().compareTo("Y")==0)
					theOres =  theOres + " ;";

				infoPane.setText(  "<body bgcolor=\"#262525\">"
                  + "<font color=\"#6D7C5E9\" size=\"+1\">&nbsp;Region: " + (String)starData.get(0) + "</font><br>"
                  + "<font color=\"#6D7C5E9\" size=\"+1\">&nbsp;System: " + (String)starData.get(1) + "</font><br>"
                  + "<font color=\"#6D7C5E9\" size=\"+1\">&nbsp;Security: " + starData.get(5) + "</font><br><br>"
                  + "<font color=\"#6D7C5E9\" size=\"+1\">&nbsp;Potential Ores & Ices: " + theOres + theIces + "</font><br>"
                  + "</body></html>");
                infoWindow.setLocation(frame.getX() +e.getX() -295,frame.getY()+e.getY()-185);
				infoWindow.setVisible(true);
				//canvas.repaint();
			}
		}

		public void mouseClicked(MouseEvent e) {
				// this activates a menu if a right click occured
				if (e.getButton()>1) {
					windowSize = frame.getSize();
					myMenu.setLoadFindSystem(true);
					myMenu.setLoadAbout(true);
					myMenu.setLoadConnection(true);
					myMenu.setToggleWormhole(true);
					myMenu.setScreenCoord(new Point2D.Double(e.getX(), e.getY()));

					// Toggles the menu item View System Stats
					if (myStars.getStarAt((canvas.affineX - e.getX())/canvas.scale, (canvas.affineY - e.getY())/canvas.scale) == null   )
					{
						myMenu.setInfo(null);
						myMenu.setMapCoord(new Point2D.Double((canvas.affineX - e.getX())/canvas.scale *-1, (canvas.affineY - e.getY())/canvas.scale *-1));
						myMenu.setAddFleetMarker(true);
					}
					else
					{
						ArrayList<Object> starData = myStars.getStarAt((canvas.affineX - e.getX())/canvas.scale, (canvas.affineY - e.getY())/canvas.scale);
						myMenu.setInfo(starData);

						myMenu.setMapCoord(new Point2D.Double((canvas.affineX - e.getX())/canvas.scale *-1, (canvas.affineY - e.getY())/canvas.scale *-1));

						//systemCoords = new Point2D.Double((canvas.affineX - e.getX())/canvas.scale,(canvas.affineY - e.getY())/canvas.scale );

						//solarSystem = myStars.getCoAt((canvas.affineX - e.getX())/canvas.scale, (canvas.affineY - e.getY())/canvas.scale);

						myMenu.setAddFleetMarker(false);
					}
					myMenu.setToggleFleets(true);


					if(myCampaign.getFleetAt((canvas.affineX - e.getX())/canvas.scale, (canvas.affineY - e.getY())/canvas.scale) != null   )
					{
						myMenu.setRemoveFleetMarker(true);
					}
					else
					{
						myMenu.setRemoveFleetMarker(false);
					}

					myMenu.showPopup(e.getX(), e.getY()+10);
				}

				if (e.getButton()==1) {

					// Double click code
					if (clickCount ==0)
					{
						startTimeMillis = System.currentTimeMillis();
						clickCount++;
					}
					else
					{
						elapsedTimeMillis = System.currentTimeMillis() - startTimeMillis;
						if (elapsedTimeMillis >  250)
						{
							startTimeMillis = System.currentTimeMillis();
							clickCount = 1;
						}
						else
						{
							//A double click centers the map on the screen
							windowSize = frame.getSize();
							canvas.translateX = (windowSize.width/2) + (canvas.affineX - e.getX());
							canvas.translateY = (windowSize.height/2) + (canvas.affineY - e.getY());
							offsetX = (canvas.translateX - (windowSize.width/2))/canvas.scale;
							offsetY = (canvas.translateY - (windowSize.height/2))/canvas.scale;
							clickCount=0;

						}

					}
				}
				canvas.repaint();
			}
		public void mouseEntered(MouseEvent e) {canvas.repaint();}
		public void mouseExited(MouseEvent e) {canvas.repaint();}

		private int boxWidth (String myText)
		{
			String[] temp;
			int lineLength = 0;
			temp = myText.split("\n");
			for (int i=0; i<temp.length; i++)
			{
				if(temp[i].length() > lineLength)
					lineLength = temp[i].length();
			}
			if (lineLength < 20)
				lineLength = 20;
			return ((int)lineLength * 10) + 40;
		}

		private int boxHeight (String myText)
		{
			String[] temp;
			temp = myText.split("\n");
			return (int)((temp.length+3) *27);

		}


		private String formatEditorPaneText(String myText)
		{
			String[] temp;
			String[] temp2;
			String formatedText = "";
			String formatedTextTemp = "";
			temp = myText.split("\n");

			for (int i=0; i<temp.length; i++)
			{
				if(temp[i].length() <=30)
				{
					formatedText = formatedText + "\n&nbsp;" + temp[i];
				}
				else
				{
					temp2 = temp[i].split(" ");
					formatedTextTemp = temp2[0];
					for (int k=1; k<temp2.length; k++)
					{
						if(formatedTextTemp.length() + temp2[k].length() < 30)
						{
							formatedTextTemp = formatedTextTemp + " " + temp2[k];
						}
						else
						{
							formatedText = formatedText + "\n&nbsp;" + formatedTextTemp;
							formatedTextTemp = temp2[k];
						}

					}
					formatedText = formatedText + "\n&nbsp;" + formatedTextTemp;
				}

			}

			return formatedText;
		}


	}

	private static class Zoom implements MouseWheelListener {
		public void mouseWheelMoved(MouseWheelEvent e) {
			double scrollSpeed = .005;
			double minMap = .05;
			double maxMap = 7.0;

			//Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			windowSize = frame.getSize();
				offsetX = (canvas.translateX - (windowSize.width)/2)/canvas.scale;
				offsetY = (canvas.translateY - (windowSize.height)/2)/canvas.scale;
			if(e.getScrollType() == MouseWheelEvent.WHEEL_UNIT_SCROLL) {

				// sets the speed of scrolling, max & min map size
				canvas.scale += (scrollSpeed * e.getWheelRotation());
				canvas.scale = Math.max(minMap, canvas.scale);
				canvas.scale = Math.min(maxMap, canvas.scale);

				// Zooms on the center of the frame
				if(canvas.scale <= maxMap && canvas.scale >= minMap){
					//old
					windowSize = frame.getSize();
					canvas.translateX = (windowSize.width/2) +(offsetX * canvas.scale);
					canvas.translateY = (windowSize.height/2) +(offsetY * canvas.scale);

				}
			canvas.repaint();
			}
		}
	}
}

