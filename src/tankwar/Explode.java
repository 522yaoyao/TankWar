package tankwar;

import java.awt.*;

public class Explode {
	int x,y;
	int step=0;
	
	private boolean live=true;
	
	TankClient tc;
	
	int[] d={4,7,12,18,26,32,49,30,14,6};
	
	public Explode(int x, int y,TankClient tc){
		this.x=x;
		this.y=y;
		this.tc=tc;
	}
	
	public void draw(Graphics g){
		if(!live){
			tc.explodes.remove(this);
			return;
		}
		if(step==d.length){
			live=false;
			step=0;
			return;
		}
		Color c=g.getColor();
		g.setColor(Color.ORANGE);
		g.fillOval(x, y, d[step], d[step]);
		step++;
		g.setColor(c);
	}

}
