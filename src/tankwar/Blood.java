package tankwar;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Blood {
	private int  x, y,w,h;
	TankClient tc;
	
	private int step=0;
	
	private boolean live=true;
	
	int[][] pos={{350,310},{380,310},{400,310},{420,310},{440,310},{420,310},{400,310}
			
	};
	
	public Blood (){
		x=pos[0][0];
		y=pos[0][1];
		w=h=15;
		
	}
	
   public boolean isLive(){
	   return live;
   }
   public void setLive(boolean live){
	   this.live=live;
   }
	/**
	 * »­Ñª¿é£»
	 * @param g
	 */
	public void draw(Graphics g){
		if(!this.live)
			return;
		Color c=g.getColor();
		g.setColor(Color.MAGENTA);
		g.fillRect(x, y, w, h);
        g.setColor(c);	
        
        move();
	}
   public void move(){
	   step++;
	   if(step==pos.length)
		   step=0;
		x=pos[step][0];
		y=pos[step][1];
   }
   public Rectangle getRect(){
	   return  new Rectangle(x,y,w,h);
   }
}
