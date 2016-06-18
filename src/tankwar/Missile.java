package tankwar;

import java.awt.*;
import java.util.List;

public class Missile {
	public static final int XSPEED=10;
	public static final int YSPEED=10;
	
	public static final int WIDTH=10;
	public static final int HEIGHT=10;
	
	
      int x,y;
      
      private boolean live=true;//�ж��ڵ���������
      
     Tank.Direction dir;//�����ӵ��ķ���
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
    	  return live;//boolean ���͵�Ҫ���ó�is....();�����������ó�set...();
      }
      public void draw(Graphics g){
    	  if(!live){
    		  //����ڵ��������Ͳ����ˣ�
    		  tc.missiles.remove(this);
    		  return;
    	  }
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
/*���ӵ��ƶ���ɺ��ж��ӵ����������ӵ��Ƿ�Խ�磩*/
    	   if(x<0||y<0||x>TankClient.GAME_WIDTH||y>TankClient.GAME_HIGH){
    		   live=false; 
    		   tc.missiles.remove(this);
    	   }
      }
      public boolean hitTank(Tank t){
    	  if(this.getRect().intersects(t.getRect())&&t.isLive()){
    		  t.setLive(false);
    		  this.live=false;
    		  Explode e=new Explode(x,y,tc);
    		  tc.explodes.add(e);
    		  return true;
    	  }
    	  return false;
    	  
      }
      /**
       * ������ײ���
       * Rectangle ��һ����ײ���ĸ����ࣻ
       * @return
       */
      public Rectangle getRect(){
    	  return new Rectangle(x,y,WIDTH,HEIGHT);
      }
      public boolean hitTanks(List<Tank> tanks){
    	  for(int i=0;i<tanks.size();i++){
    		   if(hitTank(tanks.get(i)))
    			   return true;
    	  }
    	  return false;
      }
}
