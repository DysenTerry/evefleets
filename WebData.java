/* EveMap project.
 * Author: TA Lucas
 */

// http://wiki.eve-id.net/APIv2_Page_Index
// https://api.eveonline.com/map/Jumps.xml.aspx
// https://api.eveonline.com/map/Kills.xml.aspx
// https://api.eveonline.com/map/Sovereignty.xml.aspx
// https://api.eveonline.com/corp/CorporationSheet.xml.aspx?corporationID=1212412770
// https://api.eveonline.com/eve/AllianceList.xml.aspx
// https://api.eveonline.com//eve/CharacterID.xml.aspx?names=Konoko%20Kusanagi,batlu
// https://api.eveonline.com/eve/CharacterInfo.xml.aspx?characterID=90202901
//
// http://eve.battleclinic.com/killboard/combat_record.php?type=corp&name=GalOre+Industries#kills
// https://api.eveonline.com/account/characters.xml.aspx?keyID=1005958&vCode=qnOIHjxPj9hMR0FKu73vIIgX9gX03iHkybo0bitF0QU5CubwQpkrsHzzBvAcOfgB


//http://evewho.com/api.php?type=allilist&id=1354830081
//https://api.eveonline.com/eve/AllianceList.xml.aspx

//eve-kill.net



import java.io.*;
import java.util.*;
import java.net.URL;


public class WebData {

	private String urlName = "";
	private String xmlData = null;
	public WebData(String url){
		urlName = url;
		getHtmlString();
	}

	public String getXMLData(){
		return xmlData;
	}

	public void refresh(){
		getHtmlString();
	}

	private void getHtmlString(){
		String tmpData = null;
		try{
	        URL url = new URL(urlName);
	        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
	        String str;

	        while ((str = in.readLine()) != null){
	            tmpData += str;
	        }
	        in.close();
	    }
	    catch (Exception e){tmpData = null;}
	    if (tmpData != null)
	    	xmlData = tmpData;
	}
}