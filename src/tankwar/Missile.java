package tankwar;

import java.awt.*;
import java.util.List;

public class Missile {
	public static final int XSPEED=10;
	public static final int YSPEED=10;
	
	public static final int WIDTH=10;
	public static final int HEIGHT=10;
	
	
      int x,y;
      
      private boolean live=true;//判断炮弹的生死；
      private boolean good;//判断子弹的好坏；
      
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
      public Missile(int x,int y, Tank.Direction dir,boolean good, TankClient tc){
    	  this(x,y,dir);
    	  this.tc=tc;
    	  this.good=good;
      }
      
      
      public boolean isLive(){
    	  return live;//boolean 类型的要设置成is....();其他类型设置成set...();
      }
      public void draw(Graphics g){
    	  if(!live){
    		  //如果炮弹死亡，就不画了；
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
/*在子弹移动完成后判断子弹的生死（子弹是否越界）*/
    	   if(x<0||y<0||x>TankClient.GAME_WIDTH||y>TankClient.GAME_HIGH){
    		   live=false; 
    		   tc.missiles.remove(this);
    	   }
      }
      public boolean hitTank(Tank t){
    	  if(this.live&&this.getRect().intersects(t.getRect())&&t.isLive()&&this.good!=t.isGood()){
 //主坦克多次才可打死；
    		  if(t.isGood()){
    			  t.setLife(t.getLife()-20);
    			  if(t.getLife()<=0)
    				  t.setLive(false);  
    		  }
 //敌方坦克一次就打死；
    		  else
    			  t.setLive(false);
    			  this.live=false;
    		if(t.isLive()==false){
    		  Explode e=new Explode(x,y,tc);
    		  tc.explodes.add(e);
    		}
    		  return true;
    	  }
    	  return false;
    	  
      }
      /**
       * 进行碰撞检测
       * Rectangle 是一个碰撞检测的辅助类；
       * @return   Rectangle类的实例；
       */
      public Rectangle getRect(){
    	  return new Rectangle(x,y,WIDTH,HEIGHT);
      }
      /**
       * 依次检测List中的坦克是否被击中；
       * @param tanks
       * @return 相撞返回true,未相撞返回false;
       */
      public boolean hitTanks(List<Tank> tanks){
    	  for(int i=0;i<tanks.size();i++){
    		   if(hitTank(tanks.get(i)))
    			   return true;
    	  }
    	  return false;
      }
      /**
       * 进行与墙的碰撞检测；
       * @param w
       * @return 相撞返回true,未相撞返回false;
       */
      public boolean hitWall(Wall w){
    	  if(this.live&&this.getRect().intersects(w.getRect())){
    		  this.live=false;
    		  return true;
    	  }
    	  return false;
    	}
}
