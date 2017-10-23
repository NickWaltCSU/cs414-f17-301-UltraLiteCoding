package userInterface;

//import model.Game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionAdapter;
import model.Board;
import model.Game;
import model.Token;
import model.Type;
import model.Status;
import model.Tile;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JComponent;

public class BoardComponent extends JComponent {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int squareSize=(int)(80*1.25);;
	
	private int boardWidth = 8 * squareSize;
	
	private int boardHeight = 4 * squareSize;
	

    int boardStartY1 = 2*this.squareSize+10;
    
    int boardStartY2 = 6*this.squareSize+20;
	
	private boolean dragging = false;
	
	private int deltaX, deltaY, oldCX, oldCY;
	

	Game game = new Game();
	
	Token dragToken;
	
	int mouseX,mouseY,mouseX2,mouseY2,mouseDx,mouseDy;
	
	int tileX,tileY,tileX2,tileY2;
	
	public BoardComponent(){
		
		addMouseListener(new MouseAdapter()
				{
					
					
					@Override
					public void mousePressed(MouseEvent me){
						mouseX = me.getX();
                        mouseY = me.getY();
                        
                        
                        //get tile coordinates.
                        tileX=mouseX/squareSize;
                        tileY=(mouseY-boardStartY1)/squareSize;
                        System.out.println(tileX+" "+tileY);
                        //game.getBoard().getTile(tileX+1, tileY+1).getToken().flipToken();
                        dragToken = game.getBoard().getTile(tileX+1, tileY+1).getToken();
                        dragging = true;
                        
                        //repaint();
					}
					
					@Override
					public void mouseReleased(MouseEvent me){
						dragging=false;
						mouseX2=me.getX();
						mouseY2=me.getY();
                        
                        //get tile coordinates.
                        tileX2=mouseX2/squareSize;
                        tileY2=(mouseY2-boardStartY1)/squareSize;
                        
                        if(game.getBoard().getToken(tileX+1, tileY+1)!=null){
                        
                        	game.moveToken(tileX+1, tileY+1, tileX2+1, tileY2+1);
                        
                        }
                        
                        System.out.println(tileX2+" "+tileY2);
                        
                        repaint();
                        
                        
						
						
					}
				}
				);
		
		addMouseMotionListener(new MouseMotionAdapter(){
			
			@Override
			public void mouseDragged(MouseEvent me){
				if(dragging){
					mouseDx=me.getX();
					mouseDy=me.getY();
					repaint();
				}
			}
			
			
				
		});
		
		
		
		
	}
	
	@Override
	   protected void paintComponent(Graphics g){
		
	      /*
	      TokenComponent token = new TokenComponent(Color.red,"KING",true,true);
	      

	      TokenComponent token2 = new TokenComponent(Color.black,"Cannon",true,true);
	      
	      TokenComponent tokenDown = new TokenComponent(Color.black,"Pawn",false,true);
	      
	      TokenComponent tokenInactive = new TokenComponent(Color.black,"Pawn",true,false);
	      
	      TokenComponent tokenActive = new TokenComponent(Color.black,"Pawn",true,true);
	      
	      */
	      //this.squareSize = (int) (token.getDiameter()*1.25);
		
	      paintBanqiBoard(g);
	      
	      
	      
	      Board board = game.getBoard();
	      
	      
	      for(int y=0;y<4;y++){
	      	for(int x=0;x<8;x++){
	      		//Tile tile = board.getTile(x+1, y+1);
	      		Token token = board.getToken(x+1, y+1);
	      		if(token!=null){
		      		TokenComponent gToken = new TokenComponent(javaColor(token.getColor()),stringType(token.getType()),token.isFaceUp(),boolStatus(token.getStatus()));
		      		gToken.draw(g, x*squareSize+squareSize/2, y*squareSize+squareSize/2+boardStartY1);
	      		}
	      	}
	      }
	      
	      ArrayList<Token> graveyard = board.getGraveyard();
	      
	      Iterator<Token> graveyardIterator = graveyard.iterator();
	      int redX,redY,blackX,blackY;
	      redX=redY=blackX=blackY=0;
	      while(graveyardIterator.hasNext()){
	    	  Token token = graveyardIterator.next();
	    	  TokenComponent gToken = new TokenComponent(javaColor(token.getColor()),stringType(token.getType()),token.isFaceUp(),true);
	    	  if(token.getColor()==model.Color.RED){
	    		  if(redX/8==1){
	    			  redX=0;
	    			  redY=1;
	    		  }
	    		  gToken.draw(g, redX*squareSize+squareSize/2, redY*squareSize+squareSize/2);
	    		  redX++;
	    	  }
	    	  if(token.getColor()==model.Color.BLACK){
	    		  if(blackX/8==1){
	    			  blackX=0;
	    			  blackY=1;
	    		  }
	    		  gToken.draw(g, blackX*squareSize+squareSize/2, blackY*squareSize+squareSize/2+boardStartY2);
	    		  blackX++;
	    	  }
	    	  
	      }
	      
	      /*
	      token2.draw(g, 0*squareSize+squareSize/2, 0*squareSize+squareSize/2+boardStartY1);
	      token2.draw(g, 0*squareSize+squareSize/2, 1*squareSize+squareSize/2+boardStartY1);
	      token.draw(g, 1*squareSize+squareSize/2, 1*squareSize+squareSize/2+boardStartY1);

	      token.draw(g, 2*squareSize+squareSize/2, 1*squareSize+squareSize/2+boardStartY1);
	      tokenDown.draw(g, 1*squareSize+squareSize/2, 0*squareSize+squareSize/2+boardStartY1);

	      tokenDown.draw(g, 2*squareSize+squareSize/2, 0*squareSize+squareSize/2+boardStartY1);
	      
	      tokenInactive.draw(g, 3*squareSize+squareSize/2, 0*squareSize+squareSize/2+boardStartY1);
	      tokenActive.draw(g, 4*squareSize+squareSize/2, 1*squareSize+squareSize/2+boardStartY1);
	      */
	      
	      
	      
	      // Draw dragged token last so that it appears over any underlying 
	  // token.
	
	      if (dragging&&dragToken!=null){
	    	  TokenComponent dToken = new TokenComponent(javaColor(dragToken.getColor()),stringType(dragToken.getType()),dragToken.isFaceUp(),boolStatus(dragToken.getStatus()));
	    	  dToken.draw(g, mouseDx, mouseDy);
	      }
	      
	   }
	
	   private void paintBanqiBoard(Graphics g){
	      ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,
	                                        RenderingHints.VALUE_ANTIALIAS_ON);
	      
	      // paint opponent graveYard
	      
	      for (int row = 0; row < 2; row++){
	         g.setColor(Color.BLACK);
	         for (int col = 0; col < 8; col++){
	        	// System.out.println(squareSize);
	            g.drawRect(col * this.squareSize, row * this.squareSize, this.squareSize, this.squareSize);
	         }
	      }

	      // Paint Banqi board.

	      for (int row = 0; row < 4; row++){
	         g.setColor(((row & 1) != 0) ? Color.BLACK : Color.WHITE);
	         for (int col = 0; col < 8; col++){
	        	// System.out.println(squareSize);
	            g.fillRect(col * this.squareSize, row * this.squareSize+ boardStartY1, this.squareSize, this.squareSize);
	            g.setColor((g.getColor() == Color.BLACK) ? Color.WHITE : Color.BLACK);
	         }
	      }
	      
	      // pant your graveYard
	      
	      for (int row = 0; row < 2; row++){
	         g.setColor(Color.BLACK);
	         for (int col = 0; col < 8; col++){
	        	// System.out.println(squareSize);
	            g.drawRect(col * this.squareSize, row * this.squareSize+boardStartY2, this.squareSize, this.squareSize);
	         }
	      }
	      
	   }
	   
	   public int getBoardWidth(){
		   return boardWidth;
	   }
	   
	   public int getBoardHeight(){
		   return boardHeight;
	   }
	   
	   private String stringType(Type type){
				switch(type) {
				case GENERAL: return "GENERAL";
				case ADVISOR: return "ADVISOR";
				case ELEPHANT: return "ELEPHANT";
				case CHARIOT: return "CHARIOT";
				case HORSE: return "HORSE";
				case CANNON: return "CANNON";
				case SOLDIER: return "SOLDIER";
				default: System.out.println("Doesn't have a type"); return "Doesn't have a type";
			}
	   }
	   
	   private boolean boolStatus(Status status){
		   switch(status){
		   case INACTIVE: return false;
		   case ACTIVE: return true;
		   default: System.out.println("Doesn't have status"); return false;
		   }
	   }
	   
	   private Color javaColor(model.Color color){
		   if(color==model.Color.RED){
			   return Color.RED;
		   }else{
			   return Color.BLACK;
		   }
	   }
	
	
}
