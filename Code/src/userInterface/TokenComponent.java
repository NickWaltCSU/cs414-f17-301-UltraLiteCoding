//Code in this class is paraphrased from the following online swing example of checkers
//<https://www.javaworld.com/article/3014190/learn-java/checkers-anyone.html> 
//author-Jeff Friesen DEC 13, 2015 12:48 PM PT

package userInterface;

import java.awt.Color;
import java.awt.Graphics;

public final class TokenComponent {
	
	private final int diameter = 80;
	
	//token type
	//token face up
	//token color
	//token active
	public boolean faceUp;
	
	public boolean active;
	
	private String name;
	
	private Color color;
	
	public TokenComponent(Color color, String name, boolean faceUp, boolean active){
		this.color = color;
		this.name = name;
		this.faceUp = faceUp;
		this.active = active;
	}
	
	public void draw(Graphics g, int cX, int cY){
		
		int X = cX - diameter / 2;
	    int Y = cY - diameter / 2;
		if(active){
		    if(faceUp){
				g.setColor(this.color);
				g.fillOval(X, Y, diameter, diameter);
				g.setColor(Color.white);
				g.drawOval(X, Y, diameter, diameter);
				g.drawString(this.name, cX-(int)(10*name.length()/2), cY);
		    }else{
		    	g.setColor(Color.white);
		    	g.fillOval(X, Y, diameter, diameter);
		    	g.setColor(Color.black);
		    	g.drawOval(X, Y, diameter, diameter);
		    }
		}
		
	}
	
	public int getDiameter(){
		return this.diameter;
	}
}
