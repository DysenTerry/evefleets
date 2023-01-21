/* EveMap project.
 * Author: TA Lucas
 */


import java.io.*;
import java.util.*;
import java.awt.Color;

public class Stars {

  	ArrayList<Integer> x = new ArrayList<Integer>();
  	ArrayList<Integer> y = new ArrayList<Integer>();
  	ArrayList<Integer> z = new ArrayList<Integer>();
	ArrayList<String> region = new ArrayList<String>();
	ArrayList<String> solarsystem = new ArrayList<String>();
  	ArrayList<Double> security = new ArrayList<Double>();

  	int currentStar = 0;
  	StarNode universe =  new StarNode();

	Stars() {
		createStars();
	}

	public ArrayList<Object> getNextStar(int colorMode){
		ArrayList<Object> thisStar = new ArrayList<Object>();
		thisStar.add(region.get(currentStar));
		thisStar.add(solarsystem.get(currentStar));
		thisStar.add(x.get(currentStar)); //X
		thisStar.add(y.get(currentStar)); //Y
		thisStar.add(z.get(currentStar)); //Z
		thisStar.add(security.get(currentStar));
		if (colorMode == 1)
			thisStar.add(getColorBySecurity(security.get(currentStar)));
		currentStar ++;
		if (currentStar == x.size())
		{
			resetList();
		}
		return thisStar;
	}

	public int getSize(){
		return x.size();
	}

	public ArrayList<Object> getStarAt(double x, double z) {
		int xRange = (int)x +4;
		int zRange =  (int)z +4;

		if(universe.exists(  (int)x*-1 + ":" + (int)z*-1   ))
		{
			return universe.getStarData((int)x*-1 + ":" + (int)z*-1);
		}
		for (int i = xRange;i>=x;i--)
		{
			for (int j =zRange;j>=z;j--)
			{
				if(universe.exists(  i*-1 + ":" + j*-1   ))
				{
					return universe.getStarData((i*-1 + ":" + j*-1));
				}
			}
		}
		return null;
	}

	public void resetList(){
		currentStar = 0;
	}

	private Color getColorBySecurity(Double security)
	{
		if(security  >=.9)
		{
			return new Color(0, 255, 179);
		}
		else if(security  >=.7)
		{
			return new Color(153,255,51);
		}
		else if(security  >=.6)
		{
			return new Color(204,255,0);
		}
		else if(security  >=.5)
		{
			return new Color(255,255,51);
		}
		else if(security  >=.3)
		{
			return new Color(255,153,0);
		}
		else if(security  >=.1)
		{
			return new Color(255,102,0);
		}
		else if(security  <=0)
		{
			return new Color(255,0,0);
		}
		return new Color(255,0,0);
	}

	private void createStars() {
		String str = "";
		int counter = 0;
		int xRange =0;
		int zRange = 0;
	   	try
	   	{
		    InputStream is = getClass().getResourceAsStream("stars.txt");
		    InputStreamReader isr = new InputStreamReader(is);
		    BufferedReader in = new BufferedReader(isr);

			str = in.readLine();
			while (str != null)
			{
				String[] temp;
				temp = str.split(",");

				x.add(Integer.parseInt(temp[4].trim()));
				y.add(Integer.parseInt(temp[5].trim()));
				z.add(Integer.parseInt(temp[6].trim()));

				region.add(temp[2].trim());
				solarsystem.add(temp[1].trim());
				security.add(Double.parseDouble(temp[7].trim()));


				xRange = Integer.parseInt(temp[4].trim())+4;
				zRange = Integer.parseInt(temp[6].trim())+4;

						universe.setStar( temp[4].trim() + ":" + temp[6].trim(),
						                 temp[0].trim(),
							             temp[1].trim(),
							             temp[2].trim(),
							             temp[3].trim(),
							             Integer.parseInt(temp[4].trim()),
							             Integer.parseInt(temp[5].trim()),
							             Integer.parseInt(temp[6].trim()),
							             Double.parseDouble(temp[7].trim()),
							             Double.parseDouble(temp[8].trim()),
							             temp[9].trim(),
							             temp[10].trim(),
							             temp[11].trim(),
							             temp[12].trim(),
							             temp[13].trim(),
							             temp[14].trim(),

							             Byte.parseByte(temp[15].trim()),
							             Byte.parseByte(temp[16].trim()),
							             Byte.parseByte(temp[17].trim()),
							             Byte.parseByte(temp[18].trim()),
							             Byte.parseByte(temp[19].trim()),
							             Byte.parseByte(temp[20].trim()),
							             Byte.parseByte(temp[21].trim()),
							             Byte.parseByte(temp[22].trim()),
							             Byte.parseByte(temp[23].trim()),
							             Byte.parseByte(temp[24].trim()),
							             Byte.parseByte(temp[25].trim()),
							             Byte.parseByte(temp[26].trim()),
							             Byte.parseByte(temp[27].trim()),
							             Byte.parseByte(temp[28].trim()),
							             Byte.parseByte(temp[29].trim()),
							             Byte.parseByte(temp[30].trim()),
							             Byte.parseByte(temp[31].trim()),
							             Byte.parseByte(temp[32].trim()),
							             Byte.parseByte(temp[33].trim()),
							             Byte.parseByte(temp[34].trim()),
							    		 Byte.parseByte(temp[35].trim()),
							             Byte.parseByte(temp[36].trim()),
							             Byte.parseByte(temp[37].trim()),
							             Byte.parseByte(temp[38].trim()),
							             Byte.parseByte(temp[39].trim()),
							             Byte.parseByte(temp[40].trim()),
							             Byte.parseByte(temp[41].trim()),
							             Byte.parseByte(temp[42].trim()));

				counter ++;
				str = in.readLine();
			}
			in.close();

	   	} catch (IOException e)
	   	{
	   	 	System.out.println("Error loading file.");
	   	}
	}

	public void updateWebData(String webText)
	{
		String temp = "";
		try
	   	{
			BufferedReader reader = new BufferedReader(new StringReader(webText));
			temp = reader.readLine();
			while (temp != null)
			{
				//Insert more code here for processing web data.
				temp = reader.readLine();
			}
	   	}
	   	catch (IOException e)
	   	{
	   	 	System.out.println("Error loading file.");
	   	}
	}
}