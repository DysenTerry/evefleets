/* EveMap project.
 * Author: TA Lucas
 */


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JEditorPane;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JRootPane;
import java.util.*;
import java.io.*;

class ActionListenerMenu implements ActionListener {
    private final MapPopupMenu menu;
    private int wormholeView = 1;
    protected JFrame frameFindSystem;
    protected JFrame frameAddFleet;
    protected JFrame frameConnection;
    protected JFrame frameViewAbout;

	// Constructor
    ActionListenerMenu(MapPopupMenu menu) {
        this.menu = menu;
    }
	public void actionPerformed(ActionEvent event) {
    	if(event.getActionCommand()== "F1 - Toggle Wormhole Systems"){
    		if (wormholeView==0)
    		{
				menu.papa.windowSize = menu.papa.frame.getSize();
				menu.papa.canvas.translateX = ((0 *-1)*menu.papa.canvas.scale + menu.papa.windowSize.width/2);
				menu.papa.canvas.translateY = ((0 *-1)*menu.papa.canvas.scale + menu.papa.windowSize.height/2);
				menu.papa.offsetX = (menu.papa.canvas.translateX - menu.papa.windowSize.width/2)/menu.papa.canvas.scale;
				menu.papa.offsetY = (menu.papa.canvas.translateY - menu.papa.windowSize.height/2)/menu.papa.canvas.scale;
				menu.papa.canvas.repaint();
	        	menu.optionSelected = 0;
	        	wormholeView = 1;
    		}
    		else
    		{
 				menu.papa.windowSize = menu.papa.frame.getSize();
				menu.papa.canvas.translateX = ((11243 *-1)*menu.papa.canvas.scale + menu.papa.windowSize.width/2);
				menu.papa.canvas.translateY = ((-96 *-1)*menu.papa.canvas.scale + menu.papa.windowSize.height/2);
				menu.papa.offsetX = (menu.papa.canvas.translateX - menu.papa.windowSize.width/2)/menu.papa.canvas.scale;
				menu.papa.offsetY = (menu.papa.canvas.translateY - menu.papa.windowSize.height/2)/menu.papa.canvas.scale;
				menu.papa.canvas.repaint();
	        	menu.optionSelected = 0;
	        	wormholeView = 0;
    		}
    	}

    	if(event.getActionCommand()== "F2 - Find System"){

    		// Create icon for the Find System window
    		Icon myIcon = new Icon();

			// Create the Find System window
        	frameFindSystem = new JFrame();
        	frameFindSystem.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        	frameFindSystem.setSize(new Dimension(250, 100));
        	frameFindSystem.setIconImage(myIcon.getIcon());
        	frameFindSystem.setLocation((int)menu.screenCoord.getX(),(int)menu.screenCoord.getY());
        	frameFindSystem.setTitle("Find System");
        	frameFindSystem.setResizable(false);

			//Grid layout for the window
	        GridBagLayout gridBag = new GridBagLayout();
        	GridBagConstraints cons = new GridBagConstraints();
        	cons.fill = GridBagConstraints.BOTH;

        	// Creates a panel for the frame
			JPanel panelFindSystem1 = new JPanel();
			panelFindSystem1.setLayout(gridBag);

			//Text box user fills out
	        JTextField txtSystemText =new JTextField();

			// Listener for the button... passes in textbox, menu, and the window
	        ActionListenerSystemFind myListener = new ActionListenerSystemFind(frameFindSystem, menu, txtSystemText);

	        //Labels
	        JLabel lblSystemName = new JLabel(" Star System");
	        JLabel lblBlank = new JLabel(" ");

			// Add all items to the panels
	        cons.ipady = 50;
	        cons.ipadx = 50;
	        cons.weighty = 1.0;
	        cons.gridx = 0;
	        cons.gridy = 0;
			gridBag.setConstraints(lblSystemName, cons);
			panelFindSystem1.add(lblSystemName);

	        cons.ipady = 30;
	        cons.ipadx = 50;
	        cons.weightx = 0;
	        cons.gridx = 0;
	        cons.gridy = 1;
			gridBag.setConstraints(txtSystemText, cons);
			panelFindSystem1.add(txtSystemText);

	        JButton btnFindSystem = new JButton("Find");
	        btnFindSystem.addActionListener(myListener);
	        cons.ipady = 30;
	        cons.ipadx = 10;
	        cons.gridx = 1;
	        cons.gridy = 1;
			gridBag.setConstraints(btnFindSystem, cons);
			panelFindSystem1.add(btnFindSystem);

	        cons.ipady = 30;
	        cons.ipadx = 50;
	        cons.weightx = 0;
	        cons.gridx = 0;
	        cons.gridy = 2;
			gridBag.setConstraints(lblBlank, cons);
			panelFindSystem1.add(lblBlank);

			JRootPane rootPane = frameFindSystem.getRootPane();
    		rootPane.setDefaultButton(btnFindSystem);

			// Add panels to Frame
			frameFindSystem.add(panelFindSystem1);
        	frameFindSystem.setVisible(true);


        	menu.optionSelected = 1;
    	}

    	if(event.getActionCommand()== "Add Fleet Marker"){
    		// Create icon for the Add Fleet window
    		Icon myIcon = new Icon();

			// Create the Add Fleet window
        	frameAddFleet = new JFrame();
        	frameAddFleet.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        	frameAddFleet.setSize(new Dimension(350, 550));
        	frameAddFleet.setIconImage(myIcon.getIcon());
        	frameAddFleet.setLocation((int)menu.screenCoord.getX(),(int)menu.screenCoord.getY());
        	frameAddFleet.setTitle("Add Fleet");
        	frameAddFleet.setResizable(false);

			//Grid layout for the window
	        GridBagLayout gridBagFleetAdd = new GridBagLayout();
        	GridBagConstraints consFleetAdd = new GridBagConstraints();
        	consFleetAdd.fill = GridBagConstraints.BOTH;

	        //Create the check boxes,radio butons, and labels for the Add Fleet window.
	        JLabel lblFleetName = new JLabel(" Fleet Name");
	        JLabel lblBlank = new JLabel(" ");
	        JTextField txtNameText =new JTextField();
	        JRadioButton radioButtonFleetBlue = new JRadioButton("Blue Fleet");
	        radioButtonFleetBlue.setSelected(false);
	        JRadioButton radioButtonFleetGreen = new JRadioButton("Green Fleet");
	        radioButtonFleetGreen.setSelected(false);
	        JRadioButton radioButtonFleetRed = new JRadioButton("Red Fleet");
	        radioButtonFleetRed.setSelected(false);
	        ButtonGroup buttonGroupFleetColor = new ButtonGroup();
	        buttonGroupFleetColor.add(radioButtonFleetBlue);
	        buttonGroupFleetColor.add(radioButtonFleetGreen);
	        buttonGroupFleetColor.add(radioButtonFleetRed);

			// Add large text area for Add Fleet window
	        JLabel lblFleetNotes = new JLabel(" Fleet Notes");
	        JLabel lblFleetNotes2 = new JLabel(" allowed characters (1-9 . a-Z)");
			JTextArea txtAreaShipNotes = new JTextArea();
	        txtAreaShipNotes.setFont(new Font("Serif", Font.PLAIN, 16));
	        txtAreaShipNotes.setLineWrap(true);
	        txtAreaShipNotes.setWrapStyleWord(true);
	        txtAreaShipNotes.setEditable(true);

			// Scroll pane for large text area
	        JScrollPane scrollPaneShipNotes = new JScrollPane(txtAreaShipNotes);
	        scrollPaneShipNotes.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	        scrollPaneShipNotes.setPreferredSize(new Dimension(450, 250));


			// Action listener to watch for frame action
	        ActionListenerFleetAddFrame myListener = new ActionListenerFleetAddFrame(frameAddFleet, menu, txtNameText,
	        	                                       radioButtonFleetBlue, radioButtonFleetGreen, radioButtonFleetRed, txtAreaShipNotes);

			// Add submit button for Add Fleet window
	        JButton btnAddFleet = new JButton("Add");
	        btnAddFleet.addActionListener(myListener);

			// Creates two panels for the frame
			JPanel panelAddFleet1 = new JPanel();
			JPanel panelAddFleet2 = new JPanel();
			panelAddFleet1.setLayout(gridBagFleetAdd);
			panelAddFleet2.setLayout(gridBagFleetAdd);

			// Add all items to the panels
			consFleetAdd.ipady = 5;
	        consFleetAdd.ipadx = 5;
	        consFleetAdd.weighty = 1.0;
	        consFleetAdd.gridx = 1;
	        consFleetAdd.gridy = 1;
			gridBagFleetAdd.setConstraints(lblBlank, consFleetAdd);
			panelAddFleet1.add(lblBlank);

			consFleetAdd.ipady = 5;
	        consFleetAdd.ipadx = 5;
	        consFleetAdd.weighty = 1.0;
	        consFleetAdd.gridx = 1;
	        consFleetAdd.gridy = 2;
			gridBagFleetAdd.setConstraints(lblBlank, consFleetAdd);
			panelAddFleet1.add(lblBlank);

			consFleetAdd.ipady = 10;
	        consFleetAdd.ipadx = 25;
	        consFleetAdd.weighty = 1.0;
	        consFleetAdd.gridx = 2;
	        consFleetAdd.gridy = 2;
			gridBagFleetAdd.setConstraints(lblFleetName, consFleetAdd);
			panelAddFleet1.add(lblFleetName);

			consFleetAdd.ipady = 10;
	        consFleetAdd.ipadx = 150;
	        consFleetAdd.weighty = 1.0;
	        consFleetAdd.gridx = 3;
	        consFleetAdd.gridy = 2;
	        gridBagFleetAdd.setConstraints(txtNameText, consFleetAdd);
			panelAddFleet1.add(txtNameText);

			consFleetAdd.ipady = 10;
	        consFleetAdd.ipadx = 20;
	        consFleetAdd.weighty = 1.0;
	        consFleetAdd.gridx = 2;
	        consFleetAdd.gridy = 3;
	        gridBagFleetAdd.setConstraints(radioButtonFleetBlue, consFleetAdd);
			panelAddFleet1.add(radioButtonFleetBlue);

			consFleetAdd.ipady = 10;
	        consFleetAdd.ipadx = 20;
	        consFleetAdd.weighty = 1.0;
	        consFleetAdd.gridx = 2;
	        consFleetAdd.gridy = 4;
	        gridBagFleetAdd.setConstraints(radioButtonFleetGreen, consFleetAdd);
			panelAddFleet1.add(radioButtonFleetGreen);

			consFleetAdd.ipady = 10;
	        consFleetAdd.ipadx = 20;
	        consFleetAdd.weighty = 1.0;
	        consFleetAdd.gridx = 2;
	        consFleetAdd.gridy = 5;
	        gridBagFleetAdd.setConstraints(radioButtonFleetRed, consFleetAdd);
			panelAddFleet1.add(radioButtonFleetRed);

			consFleetAdd.ipady = 10;
	        consFleetAdd.ipadx = 20;
	        consFleetAdd.weighty = 1.0;
	        consFleetAdd.gridx = 3;
	        consFleetAdd.gridy = 5;
	        gridBagFleetAdd.setConstraints(btnAddFleet, consFleetAdd);
			panelAddFleet1.add(btnAddFleet);

			consFleetAdd.ipady = 50;
	        consFleetAdd.ipadx = 50;
	        consFleetAdd.weighty = 1.0;
	        consFleetAdd.gridx = 2;
	        consFleetAdd.gridy = 7;
			gridBagFleetAdd.setConstraints(lblBlank, consFleetAdd);
			panelAddFleet1.add(lblBlank);

			consFleetAdd.ipady = 10;
	        consFleetAdd.ipadx = 100;
	        consFleetAdd.weighty = 1.0;
	        consFleetAdd.gridx = 2;
	        consFleetAdd.gridy = 8;
	        gridBagFleetAdd.setConstraints(lblFleetNotes, consFleetAdd);
			panelAddFleet1.add(lblFleetNotes);

			consFleetAdd.ipady = 1;
	        consFleetAdd.ipadx = 100;
	        consFleetAdd.weighty = 1.0;
	        consFleetAdd.gridx = 0;
	        consFleetAdd.gridy = 0;
	        gridBagFleetAdd.setConstraints(lblFleetNotes2, consFleetAdd);
			panelAddFleet2.add(lblFleetNotes2);

			consFleetAdd.ipady = 230;
	        consFleetAdd.ipadx = 315;
	        consFleetAdd.weighty = 1.0;
	        consFleetAdd.gridx = 0;
	        consFleetAdd.gridy = 1;
	        //consFleetAdd.gridwidth = 3;
	        //consFleetAdd.gridheight = 3;
	        gridBagFleetAdd.setConstraints(scrollPaneShipNotes, consFleetAdd);
			panelAddFleet2.add(scrollPaneShipNotes);

			JRootPane rootPane = frameAddFleet.getRootPane();
    		rootPane.setDefaultButton(btnAddFleet);

			// Add panels to Frame
			frameAddFleet.add(panelAddFleet1, BorderLayout.PAGE_START);
			frameAddFleet.add(panelAddFleet2, BorderLayout.CENTER);
        	frameAddFleet.setVisible(true);

        	menu.optionSelected = 3;
    	}

    	if(event.getActionCommand()== "Remove Fleet Marker"){
    		menu.papa.myCampaign.removeFleet( menu.papa.selectedFleet );
    		menu.optionSelected = 4;
    	}

    	if(event.getActionCommand()== "F5 - Toggle Fleets"){

			ArrayList<Object> toggleNext = menu.papa.myCampaign.toggleNextFleet();
			menu.papa.windowSize = menu.papa.frame.getSize();

			menu.papa.canvas.translateX = ((Integer.parseInt(toggleNext.get(0).toString()) *-1)*menu.papa.canvas.scale + menu.papa.windowSize.width/2);
			menu.papa.canvas.translateY = ((Integer.parseInt(toggleNext.get(1).toString()) *-1)*menu.papa.canvas.scale + menu.papa.windowSize.height/2);
			menu.papa.offsetX = (menu.papa.canvas.translateX - menu.papa.windowSize.width/2)/menu.papa.canvas.scale;
			menu.papa.offsetY = (menu.papa.canvas.translateY - menu.papa.windowSize.height/2)/menu.papa.canvas.scale;

        	menu.optionSelected = 5;
    	}



    	if(event.getActionCommand()== "F6 - Clear All Markers"){
    		menu.campaign.clearList(true);
			menu.optionSelected = 7;
    	}
    	if(event.getActionCommand()== "F7 - Set Connection"){


    		// Create icon for the Find System window
    		Icon myIcon = new Icon();

			// Create the Find System window
        	frameConnection = new JFrame();
        	frameConnection.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        	frameConnection.setSize(new Dimension(380, 250));
        	frameConnection.setIconImage(myIcon.getIcon());
        	frameConnection.setLocation((int)menu.screenCoord.getX(),(int)menu.screenCoord.getY());
        	frameConnection.setTitle("Set Connection");
        	frameConnection.setResizable(false);

			//Grid layout for the window
	        GridBagLayout gridBagConnection = new GridBagLayout();
        	GridBagConstraints consConnection = new GridBagConstraints();
        	consConnection.fill = GridBagConstraints.BOTH;

        	// Creates a panel for the frame
			JPanel panelConnection = new JPanel();
			JPanel panelConnection2 = new JPanel();
			panelConnection.setLayout(gridBagConnection);
			panelConnection2.setLayout(gridBagConnection);

			//Text box user fills out
	        JTextField txtConnectionText =new JTextField();
	        JTextField txtChannel =new JTextField();
	        JTextField txtPass =new JTextField();

			// Listener for the button... passes in textbox, menu, and the window
	        ActionListenerConnection myListener = new ActionListenerConnection(frameConnection, menu, txtConnectionText, txtChannel, txtPass);

	        //Labels
	        JLabel lblConnectionName = new JLabel(" Host URL:");
	        JLabel lblChannel = new JLabel(" Channel:");
	        JLabel lblPass = new JLabel(" Password:");
	        JLabel lblBlank = new JLabel(" ");

			// Add all items to the panels

	        consConnection.ipady = 10;
	        consConnection.ipadx = 10;
	        //consConnection.weightx = 1.0;
	        consConnection.gridx = 0;
	        consConnection.gridy = 0;
			gridBagConnection.setConstraints(lblBlank, consConnection);
			panelConnection.add(lblBlank);

	        consConnection.ipady = 10;
	        consConnection.ipadx = 10;
	        //consConnection.weightx = 1.0;
	        consConnection.gridx = 3;
	        consConnection.gridy = 0;
			gridBagConnection.setConstraints(lblBlank, consConnection);
			panelConnection.add(lblBlank);

			//--------------------------------- Connection
	        consConnection.ipady = 15;
	        consConnection.ipadx = 10;
	        //consConnection.weighty = 1.0;
	        consConnection.gridx = 1;
	        consConnection.gridy = 1;
			gridBagConnection.setConstraints(lblConnectionName, consConnection);
			panelConnection.add(lblConnectionName);

	        consConnection.ipady = 15;
	        consConnection.ipadx = 150;
	        //consConnection.weightx = 1.0;
	        consConnection.gridx = 2;
	        consConnection.gridy = 1;
			gridBagConnection.setConstraints(txtConnectionText, consConnection);
			panelConnection.add(txtConnectionText);

			//---------------------------------
	        consConnection.ipady = 10;
	        consConnection.ipadx = 10;
	        consConnection.gridx = 1;
	        consConnection.gridy = 2;
			gridBagConnection.setConstraints(lblBlank, consConnection);
			panelConnection.add(lblBlank);


			//--------------------------------- Channel
	        consConnection.ipady = 15;
	        consConnection.ipadx = 10;
	        //consConnection.weighty = 1.0;
	        consConnection.gridx = 1;
	        consConnection.gridy = 5;
			gridBagConnection.setConstraints(lblChannel, consConnection);
			panelConnection.add(lblChannel);

	        consConnection.ipady = 15;
	        consConnection.ipadx = 150;
	        //consConnection.weightx = 1.0;
	        consConnection.gridx = 2;
	        consConnection.gridy = 5;
			gridBagConnection.setConstraints(txtChannel, consConnection);
			panelConnection.add(txtChannel);

			//---------------------------------
	        consConnection.ipady = 10;
	        consConnection.ipadx = 10;
	        //consConnection.weightx = 1.0;
	        consConnection.gridx = 1;
	        consConnection.gridy = 6;
			gridBagConnection.setConstraints(lblBlank, consConnection);
			panelConnection.add(lblBlank);

			//--------------------------------- Pass
	        consConnection.ipady = 15;
	        consConnection.ipadx = 10;
	        //consConnection.weighty = 1.0;
	        consConnection.gridx = 1;
	        consConnection.gridy = 7;
			gridBagConnection.setConstraints(lblPass, consConnection);
			panelConnection.add(lblPass);

	        consConnection.ipady = 15;
	        consConnection.ipadx = 150;
	        //consConnection.weightx = 1.0;
	        consConnection.gridx = 2;
	        consConnection.gridy = 7;
			gridBagConnection.setConstraints(txtPass, consConnection);
			panelConnection.add(txtPass);



	        JButton btnConnection = new JButton("Set");
	        btnConnection.addActionListener(myListener);
	        consConnection.ipady = 15;
	        consConnection.ipadx = 10;
	        consConnection.gridx = 2;
	        consConnection.gridy = 2;
			gridBagConnection.setConstraints(btnConnection, consConnection);
			panelConnection2.add(btnConnection);



			// Add panels to Frame

			frameConnection.add(panelConnection, BorderLayout.PAGE_START);
			frameConnection.add(panelConnection2, BorderLayout.CENTER);

    		try{
    			BufferedReader in = new BufferedReader(new FileReader("config.txt"));
    			String str = in.readLine();
    			if (str != null)
    			{
    				txtConnectionText.setText(str);
    			}

      			str = in.readLine();
    			if (str != null)
    			{
    				txtChannel.setText(str);
    			}

    			str = in.readLine();
    			if (str != null)
    			{
    				txtPass.setText(str);
    			}
    			in.close();
    		}
    		catch (IOException e){

    		}

			JRootPane rootPane = frameConnection.getRootPane();
    		rootPane.setDefaultButton(btnConnection);

        	frameConnection.setVisible(true);
			menu.optionSelected = 9;
    	}


     	if(event.getActionCommand()== "F8 - About"){
     					// Icon for View System Stats window
    		Icon iconViewSystemStats = new Icon();

			// Frame for View System Stats
        	frameViewAbout = new JFrame();
        	frameViewAbout.setSize(new Dimension(500, 300));
        	frameViewAbout.setIconImage(iconViewSystemStats.getIcon());
        	frameViewAbout.setLocation((int)menu.screenCoord.getX(),(int)menu.screenCoord.getY());

			// This is the HTML box the info shows in
        	JEditorPane myPane = new JEditorPane();
        	myPane.setEditable(false);
        	JScrollPane editorScrollPane = new JScrollPane(myPane);
        	editorScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        	frameViewAbout.getContentPane().add(BorderLayout.CENTER, editorScrollPane);
        	myPane.setContentType(new HTMLEditorKit().getContentType());

			// Create the HTML for the window

        		myPane.setText(  "<body bgcolor=\"#262525\">"
                  + "<p align='center'><font color=\"#6D7C5E9\" size=\"+1\">"
				 + "Copyright &copy; 2013 TA Lucas. All  rights reserved.<br><br>"
				 + "http://evefleets.com/<br><br><br>"
				 + "This program can be downloaded and shared with others<br>"
				 + "free of charge. There are no warranties or <br>"
				 + "committments of updates or future versions.  <br>"
                  + "</font></body></html>"
        	);


			myPane.setCaretPosition(0);
    		frameViewAbout.setVisible(true);
        	frameViewAbout.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    		menu.optionSelected = 12;
    	}

	}
}