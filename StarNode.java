/* EveMap project.
 * Author: TA Lucas
 */

/* The StarNode class is a recursive class that stores info
 * about a star based upon its x-y location address on the
 * 2D grid.  A example star address would be  239:-223
 * Each node has 13 child nodes.  One for each number (0-9),
 * and nodes for (: . -).  A star with an address of 2:6 would
 * be stored like the following example.  This example also
 * displays the star for location 2:5 and the start of star
 * 23:??
 *
 * The advantage of this data structure is that it can retrieve
 * info about a specific star with a minimal amount of reads.
 * In other words.... in Order 1 time.
 *
 * 					*****
 *				   *     *
 *				   *  2  *
 *				   *     *
 *				    *****
 *				      |   \
 *				      |    \
 *				      |     \
 * 					*****    \  *****
 *				   *     *     *     *
 *				   *  :  *     *  3  *
 *				   *     *     *     *
 *				    *****       *****
 *				  /       \
 *				/           \
 * 		*****				 *****
 *	   *     *				*     *
 *	   *  5  *				*  6  *
 *	   *     *				*     *
 *	    *****    			 *****
 */


import java.util.ArrayList;
import java.util.LinkedList;

public class StarNode
{
	private String theSolarSystemID;
	private String theSystem;
	private String theRegion;
	private String theConstellation;
	private int theX;
	private int theY;
	private int theZ;
	private double theSecurity;
	private double theLuminosity;
	private String theStarType;
	private String theFaction;
	private String theOres;
	private String theIces;

	private String iceBelts;
	private String oreBelts;
	private byte arkanor;
	private byte bistot;
	private byte crokite;
	private byte darkOchre;
	private byte gneiss;
	private byte hedbergite;
	private byte hemorphite;
	private byte jaspet;
	private byte kernite;
	private byte mercoxit;
	private byte omber;
	private byte plagioclase;
	private byte pyroxeres;
	private byte scordite;
	private byte spodumain;
	private byte veldspar;
	private byte blueIce;
	private byte clearIcicle;
	private byte whiteGlaze;
	private byte glacialMass;
	private byte glareCrust;
	private byte thickBlueIce;
	private byte gelidus;
	private byte krystallos;
	private byte darkGlitter;
	private byte enrichedClearIcicle;
	private byte pristineWhiteGlaze;
	private byte smoothGlacialMass;

	private int theJumps;
	private int theKills;
	private String theSovAlliance;

	private StarNode zeroNode;
	private StarNode oneNode;
	private StarNode twoNode;
	private StarNode threeNode;
	private StarNode fourNode;
	private StarNode fiveNode;
	private StarNode sixNode;
	private StarNode sevenNode;
	private StarNode eightNode;
	private StarNode nineNode;
	private StarNode colonNode;
	private StarNode periodNode;
	private StarNode dashNode;

	public StarNode()
	{
		theRegion = null;
		theSystem = null;
		theSolarSystemID = "";
		theConstellation = null;
		theX = 0;
		theY = 0;
		theZ = 0;
		theSecurity = 0;
		theLuminosity = 0;
		theStarType = null;
		theFaction = null;
		theOres = null;
		theIces = null;

		iceBelts = null;
		oreBelts = null;
		arkanor = 0;
		bistot = 0;
		crokite = 0;
		darkOchre = 0;
		gneiss = 0;
		hedbergite = 0;
		hemorphite = 0;
		jaspet = 0;
		kernite = 0;
		mercoxit = 0;
		omber = 0;
		plagioclase = 0;
		pyroxeres = 0;
		scordite = 0;
		spodumain = 0;
		veldspar = 0;
		blueIce = 0;
		clearIcicle = 0;
		whiteGlaze = 0;
		glacialMass = 0;
		glareCrust = 0;
		thickBlueIce = 0;
		gelidus = 0;
		krystallos = 0;
		darkGlitter = 0;
		enrichedClearIcicle = 0;
		pristineWhiteGlaze = 0;
		smoothGlacialMass = 0;

		theJumps = 0;
		theKills = 0;
		theSovAlliance = null;

		zeroNode = null;
		oneNode = null;
		twoNode = null;
		threeNode = null;
		fourNode = null;
		fiveNode = null;
		sixNode = null;
		sevenNode = null;
		eightNode = null;
		nineNode = null;
		colonNode = null;
		periodNode = null;
		dashNode = null;
	}

	protected void setStar(String coordPair, String solarSystemID, String system, String region, String constellation, int x, int y, int z, double security, double luminosity, String sunType, String faction, String ores, String ices, String ib, String ob, byte ark, byte bis, byte cro, byte dar, byte gne, byte hed, byte hem, byte jas, byte ker, byte mer, byte omb, byte pla, byte pyr, byte sco, byte spo, byte vel, byte iBlu, byte iCle, byte iWhi, byte iGla, byte iGlr, byte iThi, byte iGel, byte iKry, byte iDar, byte iEnr, byte iPri, byte iSmo)
	{
		String theLetter ="";
		String theLeftOver ="";
		if (coordPair.length()!=0)
		{
			theLetter = coordPair.substring(0,1);
			theLeftOver = coordPair.substring(1,coordPair.length());
			if(theLetter.compareTo("0")==0)
			{
				if(zeroNode == null)
				{
					zeroNode = new StarNode();
				}
				zeroNode.setStar(theLeftOver, solarSystemID, system, region, constellation, x, y, z, security, luminosity, sunType, faction, ores, ices, ib, ob, ark, bis, cro, dar, gne, hed, hem, jas, ker, mer, omb, pla, pyr, sco, spo, vel, iBlu, iCle, iWhi, iGla, iGlr, iThi, iGel, iKry, iDar, iEnr, iPri, iSmo);
			}
			else if(theLetter.compareTo("1")==0)
			{
				if(oneNode == null)
				{
					oneNode = new StarNode();
				}
				oneNode.setStar(theLeftOver, solarSystemID, system, region, constellation, x, y, z, security, luminosity, sunType, faction, ores, ices, ib, ob, ark, bis, cro, dar, gne, hed, hem, jas, ker, mer, omb, pla, pyr, sco, spo, vel, iBlu, iCle, iWhi, iGla, iGlr, iThi, iGel, iKry, iDar, iEnr, iPri, iSmo);
			}
			else if(theLetter.compareTo("2")==0)
			{
				if(twoNode == null)
				{
					twoNode = new StarNode();
				}
				twoNode.setStar(theLeftOver, solarSystemID, system, region, constellation, x, y, z, security, luminosity, sunType, faction, ores, ices, ib, ob, ark, bis, cro, dar, gne, hed, hem, jas, ker, mer, omb, pla, pyr, sco, spo, vel, iBlu, iCle, iWhi, iGla, iGlr, iThi, iGel, iKry, iDar, iEnr, iPri, iSmo);
			}
			else if(theLetter.compareTo("3")==0)
			{
				if(threeNode == null)
				{
					threeNode = new StarNode();
				}
				threeNode.setStar(theLeftOver, solarSystemID, system, region, constellation, x, y, z, security, luminosity, sunType, faction, ores, ices, ib, ob, ark, bis, cro, dar, gne, hed, hem, jas, ker, mer, omb, pla, pyr, sco, spo, vel, iBlu, iCle, iWhi, iGla, iGlr, iThi, iGel, iKry, iDar, iEnr, iPri, iSmo);
			}
			else if(theLetter.compareTo("4")==0)
			{
				if(fourNode == null)
				{
					fourNode = new StarNode();
				}
				fourNode.setStar(theLeftOver, solarSystemID, system, region, constellation, x, y, z, security, luminosity, sunType, faction, ores, ices, ib, ob, ark, bis, cro, dar, gne, hed, hem, jas, ker, mer, omb, pla, pyr, sco, spo, vel, iBlu, iCle, iWhi, iGla, iGlr, iThi, iGel, iKry, iDar, iEnr, iPri, iSmo);
			}
			else if(theLetter.compareTo("5")==0)
			{
				if(fiveNode == null)
				{
					fiveNode = new StarNode();
				}
				fiveNode.setStar(theLeftOver, solarSystemID, system, region, constellation, x, y, z, security, luminosity, sunType, faction, ores, ices, ib, ob, ark, bis, cro, dar, gne, hed, hem, jas, ker, mer, omb, pla, pyr, sco, spo, vel, iBlu, iCle, iWhi, iGla, iGlr, iThi, iGel, iKry, iDar, iEnr, iPri, iSmo);
			}
			else if(theLetter.compareTo("6")==0)
			{
				if(sixNode == null)
				{
					sixNode = new StarNode();
				}
				sixNode.setStar(theLeftOver, solarSystemID, system, region, constellation, x, y, z, security, luminosity, sunType, faction, ores, ices, ib, ob, ark, bis, cro, dar, gne, hed, hem, jas, ker, mer, omb, pla, pyr, sco, spo, vel, iBlu, iCle, iWhi, iGla, iGlr, iThi, iGel, iKry, iDar, iEnr, iPri, iSmo);
			}
			else if(theLetter.compareTo("7")==0)
			{
				if(sevenNode == null)
				{
					sevenNode = new StarNode();
				}
				sevenNode.setStar(theLeftOver, solarSystemID, system, region, constellation, x, y, z, security, luminosity, sunType, faction, ores, ices, ib, ob, ark, bis, cro, dar, gne, hed, hem, jas, ker, mer, omb, pla, pyr, sco, spo, vel, iBlu, iCle, iWhi, iGla, iGlr, iThi, iGel, iKry, iDar, iEnr, iPri, iSmo);
			}
			else if(theLetter.compareTo("8")==0)
			{
				if(eightNode == null)
				{
					eightNode = new StarNode();
				}
				eightNode.setStar(theLeftOver, solarSystemID, system, region, constellation, x, y, z, security, luminosity, sunType, faction, ores, ices, ib, ob, ark, bis, cro, dar, gne, hed, hem, jas, ker, mer, omb, pla, pyr, sco, spo, vel, iBlu, iCle, iWhi, iGla, iGlr, iThi, iGel, iKry, iDar, iEnr, iPri, iSmo);
			}
			else if(theLetter.compareTo("9")==0)
			{
				if(nineNode == null)
				{
					nineNode = new StarNode();
				}
				nineNode.setStar(theLeftOver, solarSystemID, system, region, constellation, x, y, z, security, luminosity, sunType, faction, ores, ices, ib, ob, ark, bis, cro, dar, gne, hed, hem, jas, ker, mer, omb, pla, pyr, sco, spo, vel, iBlu, iCle, iWhi, iGla, iGlr, iThi, iGel, iKry, iDar, iEnr, iPri, iSmo);
			}
			else if(theLetter.compareTo(":")==0)
			{
				if(colonNode == null)
				{
					colonNode = new StarNode();
				}
				colonNode.setStar(theLeftOver, solarSystemID, system, region, constellation, x, y, z, security, luminosity, sunType, faction, ores, ices, ib, ob, ark, bis, cro, dar, gne, hed, hem, jas, ker, mer, omb, pla, pyr, sco, spo, vel, iBlu, iCle, iWhi, iGla, iGlr, iThi, iGel, iKry, iDar, iEnr, iPri, iSmo);
			}
			else if(theLetter.compareTo(".")==0)
			{
				if(periodNode == null)
				{
					periodNode = new StarNode();
				}
				periodNode.setStar(theLeftOver, solarSystemID, system, region, constellation, x, y, z, security, luminosity, sunType, faction, ores, ices, ib, ob, ark, bis, cro, dar, gne, hed, hem, jas, ker, mer, omb, pla, pyr, sco, spo, vel, iBlu, iCle, iWhi, iGla, iGlr, iThi, iGel, iKry, iDar, iEnr, iPri, iSmo);
			}
			else if(theLetter.compareTo("-")==0)
			{
				if(dashNode == null)
				{
					dashNode = new StarNode();
				}
				dashNode.setStar(theLeftOver, solarSystemID, system, region, constellation, x, y, z, security, luminosity, sunType, faction, ores, ices, ib, ob, ark, bis, cro, dar, gne, hed, hem, jas, ker, mer, omb, pla, pyr, sco, spo, vel, iBlu, iCle, iWhi, iGla, iGlr, iThi, iGel, iKry, iDar, iEnr, iPri, iSmo);
			}
		}
		else
		{
			theRegion = region;
			theSystem = system;
			theX = x;
			theY = y;
			theZ = z;
			theSecurity = security;
			theConstellation = constellation;
			theSolarSystemID = solarSystemID;
			theLuminosity = luminosity;
			theStarType = sunType;
			theFaction =  faction;
			theOres = ores;
			theIces = ices;

			iceBelts = ib;
			oreBelts = ob;
			arkanor = ark;
			bistot = bis;
			crokite = cro;
			darkOchre = dar;
			gneiss = gne;
			hedbergite = hed;
			hemorphite = hem;
			jaspet = jas;
			kernite = ker;
			mercoxit = mer;
			omber = omb;
			plagioclase = pla;
			pyroxeres = pyr;
			scordite = sco;
			spodumain = spo;
			veldspar = vel;
			blueIce = iBlu;
			clearIcicle = iCle;
			whiteGlaze = iWhi;
			glacialMass = iGla;
			glareCrust = iGlr;
			thickBlueIce = iThi;
			gelidus = iGel;
			krystallos = iKry;
			darkGlitter = iDar;
			enrichedClearIcicle = iEnr;
			pristineWhiteGlaze = iPri;
			smoothGlacialMass = iSmo;

		}
	}

	protected boolean exists(String coordPair)
	{
		String theLetter ="";
		String theLeftOver ="";
		boolean itExists = false;
		if (coordPair.length()==0)
		{
			if(theSystem != null)
				itExists = true;
		}
		else
		{

			theLetter = coordPair.substring(0,1);
			theLeftOver = coordPair.substring(1,coordPair.length());
			if(theLetter.compareTo("0")==0)
			{
				if(zeroNode == null)
					itExists = false;
				else
					itExists = zeroNode.exists(theLeftOver);
			}
			else if (theLetter.compareTo("1")==0)
			{
				if(oneNode == null)
					itExists = false;
				else
					itExists = oneNode.exists(theLeftOver);
			}
			else if (theLetter.compareTo("2")==0)
			{
				if(twoNode == null)
					itExists = false;
				else
					itExists = twoNode.exists(theLeftOver);
			}
			else if (theLetter.compareTo("3")==0)
			{
				if(threeNode == null)
					itExists = false;
				else
					itExists = threeNode.exists(theLeftOver);
			}
			else if (theLetter.compareTo("4")==0)
			{
				if(fourNode == null)
					itExists = false;
				else
					itExists = fourNode.exists(theLeftOver);
			}
			else if (theLetter.compareTo("5")==0)
			{
				if(fiveNode == null)
					itExists = false;
				else
					itExists = fiveNode.exists(theLeftOver);
			}
			else if (theLetter.compareTo("6")==0)
			{
				if(sixNode == null)
					itExists = false;
				else
					itExists = sixNode.exists(theLeftOver);
			}
			else if (theLetter.compareTo("7")==0)
			{
				if(sevenNode == null)
					itExists = false;
				else
					itExists = sevenNode.exists(theLeftOver);
			}
			else if (theLetter.compareTo("8")==0)
			{
				if(eightNode == null)
					itExists = false;
				else
					itExists = eightNode.exists(theLeftOver);
			}
			else if (theLetter.compareTo("9")==0)
			{
				if(nineNode == null)
					itExists = false;
				else
					itExists = nineNode.exists(theLeftOver);
			}
			else if (theLetter.compareTo(":")==0)
			{
				if(colonNode == null)
					itExists = false;
				else
					itExists = colonNode.exists(theLeftOver);
			}
			else if (theLetter.compareTo(".")==0)
			{
				if(periodNode == null)
					itExists = false;
				else
					itExists = periodNode.exists(theLeftOver);
			}
			else if (theLetter.compareTo("-")==0)
			{
				if(dashNode == null)
					itExists = false;
				else
					itExists = dashNode.exists(theLeftOver);
			}
		}
	return itExists;
	}

	public ArrayList<Object> getStarData(String coordPair) throws NoSuchElementException
	{
		String theLetter ="";
		String theLeftOver ="";
		ArrayList<Object> theAnswer = null;
		if (coordPair.length()==0)
		{
			ArrayList<Object> thisStar = new ArrayList<Object>();
			thisStar.add(theRegion);
			thisStar.add(theSystem);
			thisStar.add(theX);
			thisStar.add(theY);
			thisStar.add(theZ);
			thisStar.add(theSecurity);
			thisStar.add(theOres);
			thisStar.add(theIces);

			thisStar.add(iceBelts);
			thisStar.add(oreBelts);
			thisStar.add(arkanor);
			thisStar.add(bistot);
			thisStar.add(crokite);
			thisStar.add(darkOchre);
			thisStar.add(gneiss);
			thisStar.add(hedbergite);
			thisStar.add(hemorphite);
			thisStar.add(jaspet);
			thisStar.add(kernite);
			thisStar.add(mercoxit);
			thisStar.add(omber);
			thisStar.add(plagioclase);
			thisStar.add(pyroxeres);
			thisStar.add(scordite);
			thisStar.add(spodumain);
			thisStar.add(veldspar);
			thisStar.add(blueIce);
			thisStar.add(clearIcicle);
			thisStar.add(whiteGlaze);
			thisStar.add(glacialMass);
			thisStar.add(glareCrust);
			thisStar.add(thickBlueIce);
			thisStar.add(gelidus);
			thisStar.add(krystallos);
			thisStar.add(darkGlitter);
			thisStar.add(enrichedClearIcicle);
			thisStar.add(pristineWhiteGlaze);
			thisStar.add(smoothGlacialMass);

			thisStar.add(theSolarSystemID);

			return thisStar;
		}
		else
		{
			theLetter = coordPair.substring(0,1);
			theLeftOver = coordPair.substring(1,coordPair.length());
			if(theLetter.compareTo("0")==0)
			{
				if(zeroNode == null)
					throw new NoSuchElementException(coordPair + " Not Found.");
				theAnswer = zeroNode.getStarData(theLeftOver);
			}
			else if (theLetter.compareTo("1")==0)
			{
				if(oneNode == null)
					throw new NoSuchElementException(coordPair + " Not Found.");
				theAnswer = oneNode.getStarData(theLeftOver);
			}
			else if (theLetter.compareTo("2")==0)
			{
				if(twoNode == null)
					throw new NoSuchElementException(coordPair + " Not Found.");
				theAnswer = twoNode.getStarData(theLeftOver);
			}
			else if (theLetter.compareTo("3")==0)
			{
				if(threeNode == null)
					throw new NoSuchElementException(coordPair + " Not Found.");
				theAnswer = threeNode.getStarData(theLeftOver);
			}
			else if (theLetter.compareTo("4")==0)
			{
				if(fourNode == null)
					throw new NoSuchElementException(coordPair + " Not Found.");
				theAnswer = fourNode.getStarData(theLeftOver);
			}
			else if (theLetter.compareTo("5")==0)
			{
				if(fiveNode == null)
					throw new NoSuchElementException(coordPair + " Not Found.");
				theAnswer = fiveNode.getStarData(theLeftOver);
			}
			else if (theLetter.compareTo("6")==0)
			{
				if(sixNode == null)
					throw new NoSuchElementException(coordPair + " Not Found.");
				theAnswer = sixNode.getStarData(theLeftOver);
			}
			else if (theLetter.compareTo("7")==0)
			{
				if(sevenNode == null)
					throw new NoSuchElementException(coordPair + " Not Found.");
				theAnswer = sevenNode.getStarData(theLeftOver);
			}
			else if (theLetter.compareTo("8")==0)
			{
				if(eightNode == null)
					throw new NoSuchElementException(coordPair + " Not Found.");
				theAnswer = eightNode.getStarData(theLeftOver);
			}
			else if (theLetter.compareTo("9")==0)
			{
				if(nineNode == null)
					throw new NoSuchElementException(coordPair + " Not Found.");
				theAnswer = nineNode.getStarData(theLeftOver);
			}
			else if (theLetter.compareTo(":")==0)
			{
				if(colonNode == null)
					throw new NoSuchElementException(coordPair + " Not Found.");
				theAnswer = colonNode.getStarData(theLeftOver);
			}
			else if (theLetter.compareTo(".")==0)
			{
				if(periodNode == null)
					throw new NoSuchElementException(coordPair + " Not Found.");
				theAnswer = periodNode.getStarData(theLeftOver);
			}
			else if (theLetter.compareTo("-")==0)
			{
				if(dashNode == null)
					throw new NoSuchElementException(coordPair + " Not Found.");
				theAnswer = dashNode.getStarData(theLeftOver);
			}
		}
	return theAnswer;
	}
}