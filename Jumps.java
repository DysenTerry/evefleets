/* EveMap project.
 * Author: TA Lucas
 */


import java.io.*;
import java.util.*;
//import java.util.ArrayList.*;
//import java.lang.Integer.*;

public class Jumps {

  	ArrayList<String> fromName = new ArrayList<String>();
  	ArrayList<Integer> fromX = new ArrayList<Integer>();
  	ArrayList<Integer> fromY = new ArrayList<Integer>();
  	ArrayList<Integer> fromZ = new ArrayList<Integer>();
  	ArrayList<String> toName = new ArrayList<String>();
  	ArrayList<Integer> toX = new ArrayList<Integer>();
  	ArrayList<Integer> toY = new ArrayList<Integer>();
  	ArrayList<Integer> toZ = new ArrayList<Integer>();
  	int currentJump = 0;

	Jumps() {

		fillArray();
	}

	public ArrayList<Object> getNextJump(){
		ArrayList<Object> thisJump = new ArrayList<Object>();
		thisJump.add(fromName.get(currentJump));
		thisJump.add(fromX.get(currentJump));
		thisJump.add(fromY.get(currentJump));
		thisJump.add(fromZ.get(currentJump));
		thisJump.add(toName.get(currentJump));
		thisJump.add(toX.get(currentJump));
		thisJump.add(toY.get(currentJump));
		thisJump.add(toZ.get(currentJump));

		currentJump ++;
		if (currentJump == fromName.size())
		{
			resetList();
		}
		return thisJump;
	}


	public int getSize(){
		return fromName.size();
	}


	public void resetList(){
		currentJump = 0;
	}


	private void fillArray() {
		String str = "";
		int counter = 0;
	   	try
	   	{

		    InputStream is = getClass().getResourceAsStream("jumps.txt");
		    InputStreamReader isr = new InputStreamReader(is);
		    BufferedReader in = new BufferedReader(isr);

			str = in.readLine();
			while (str != null)
			{
				String[] temp;
				temp = str.split(",");

				fromName.add(temp[0].trim());
				fromX.add(Integer.parseInt(temp[1].trim()));
				fromY.add(Integer.parseInt(temp[2].trim()));
				fromZ.add(Integer.parseInt(temp[3].trim()));
				toName.add(temp[4].trim());
				toX.add(Integer.parseInt(temp[5].trim()));
				toY.add(Integer.parseInt(temp[6].trim()));
				toZ.add(Integer.parseInt(temp[7].trim()));

				counter ++;
				str = in.readLine();
			}
			in.close();

	   	} catch (IOException e)
	   	{
	   	 	System.out.println("Error loading file.");
	   	}
	}

}