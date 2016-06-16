package tankwar;
import java.awt.*;

public class Missile {
	public static final int XSPEED=10;
	public static final int YSPEED=10;
	
	public static final int WIDTH=10;
	public static final int HEIGHT=10;
	
	
      int x,y;
      private boolean live=true;//判断炮弹的生死；
      
     Tank.Direction dir;//定义子弹的方向；
     TankClient tc;
     
      public Missile(int x,int y,Tank.Direction dir){
    	  this.x=x;
    	  this.y=y;
    	  this.dir=dir;
      }
     /* public Missile(int x,int y,Tank.Direction dir TankClient tc){
    	  this(x,y,dir);
    	  this.tc=tc;
      }*/
      public Missile(int x,int y, Tank.Direction dir, TankClient tc){
    	  this(x,y,dir);
    	  this.tc=tc;
      }
      
      
      public boolean isLive(){
    	  return live;//boolean 类型的要设置成is....();其他类型设置成set...();
      }
      public void draw(Graphics g){
    	  Color c=g.getColor();
    	  g.setColor(Color.BLACK);
    	  g.fillOval(x, y, WIDTH, HEIGHT);
    	  g.setColor(c);
    	  move();
      }
      public void move(){
    	   switch(dir){
    	   case L:
    		   x-=XSPEED;
    		   break;
          case LU:
        	  x-=XSPEED;
        	  y-=YSPEED;
        	   break;
         case U:
           y-=YSPEED;
    	   break;
    case RU:
    	  x+=XSPEED;
    	  y-=YSPEED;
    	   break;
    case R:
    	 x+=XSPEED;
    	   break;
    case RD:
    	  x+=XSPEED;
    	  y+=YSPEED;
    	   break;
    case D:
    	  y+=YSPEED;
    	   break;
    case LD:
    	 x-=XSPEED;
    	  y+=YSPEED;
    	   break;
  
    	   }
/*在子弹移动完成后判断子弹的生死（子弹是否越界）*/
    	   if(x<0||y<0||x>TankClient.GAME_WIDTH||y>TankClient.GAME_HIGH){
    		   live=false;
    		   tc.missiles.remove(this);
    	   }
      }
}
