package sigh;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;



public class Piece  {
	
	public Image q;
	public Vector position = new Vector();
	int size;
	int stack;
	boolean color;
	String l;
	
	public Piece(Image img, int size, Vector position, boolean c, int d) {
		q=img;
		this.position=position;
		this.size=size;
		color = c;
		stack = d;
		l=img.toString();
	}
	
	public boolean containsPoint(float x, float y) {
		// checks if the given point is within my size & position
		int centerX=(int)position.x+size/2;
		int centerY=(int)position.y+size/2;
		if((x-centerX)*(x-centerX) + (y-centerY)*(y-centerY) <= (size/2)*(size/2))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public void render(Graphics g) {
		g.drawImage(q, (int)position.x, (int)position.y, size, size, null);
		g.setFont(new Font("Times New Roman", Font.BOLD, 20));
		g.setColor(Color.CYAN);
		g.drawString(""+stack, (int)position.x+26, (int)position.y+34);
		// draw image at position with size
	}
	
	public String toString(Piece c)
	{
		return l;
	}
	
}
