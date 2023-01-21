/* EveMap project.
 * Author: TA Lucas
 */


import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Color;

public class Icon {
	private static BufferedImage bi = new BufferedImage(16, 16, BufferedImage.TYPE_INT_RGB);
	private static Graphics g;

    public Icon() {

        g = bi.getGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 15, 15);
        g.setColor(Color.WHITE);
        g.drawOval(2, 2, 10, 12);
        g.setColor(Color.ORANGE);
        g.fillOval(10, 7, 4, 4);
        g.fillOval(4,6,4,4);
        g.dispose();
    }

	//returns the icon image
    public Image getIcon(){
    	return bi;
    }
}