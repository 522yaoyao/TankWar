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
      private boolean good;//�ж��ӵ��ĺû���
      
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
      public Missile(int x,int y, Tank.Direction dir,boolean good, TankClient tc){
    	  this(x,y,dir);
    	  this.tc=tc;
    	  this.good=good;
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
    	  if(this.live&&this.getRect().intersects(t.getRect())&&t.isLive()&&this.good!=t.isGood()){
 //��̹�˶�βſɴ�����
    		  if(t.isGood()){
    			  t.setLife(t.getLife()-20);
    			  if(t.getLife()<=0)
    				  t.setLive(false);  
    		  }
 //�з�̹��һ�ξʹ�����
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
       * ������ײ���
       * Rectangle ��һ����ײ���ĸ����ࣻ
       * @return   Rectangle���ʵ����
       */
      public Rectangle getRect(){
    	  return new Rectangle(x,y,WIDTH,HEIGHT);
      }
      /**
       * ���μ��List�е�̹���Ƿ񱻻��У�
       * @param tanks
       * @return ��ײ����true,δ��ײ����false;
       */
      public boolean hitTanks(List<Tank> tanks){
    	  for(int i=0;i<tanks.size();i++){
    		   if(hitTank(tanks.get(i)))
    			   return true;
    	  }
    	  return false;
      }
      /**
       * ������ǽ����ײ��⣻
       * @param w
       * @return ��ײ����true,δ��ײ����false;
       */
      public boolean hitWall(Wall w){
    	  if(this.live&&this.getRect().intersects(w.getRect())){
    		  this.live=false;
    		  return true;
    	  }
    	  return false;
    	}
}
