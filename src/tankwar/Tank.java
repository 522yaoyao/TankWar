package tankwar;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Random;

public class Tank {
	public static final int XSPEED=5;
	public static final int YSPEED=5;
	
	public static final int WIDTH=30;
	public static final int HEIGHT=30;
	
	public static int FULL=100;
	
	private int x,y;
	private int oldX,oldY;
	
	private int step=random.nextInt(12)+3;//敌方坦克朝某个方向移动的步数；
	private static Random random=new Random();//定义一个静态变量，所有实例共享；
	
     private int life=FULL;
	
	private boolean live=true;//默认坦克处于存活状态；
	private boolean bL=false, bU=false,bR=false,bD=false;
	private boolean good=true;//判断敌我坦克；
	
	enum Direction{L,U,LU,R,RU,D,RD,LD,STOP};////定义枚举类型；
	Direction dir=Direction.STOP;//坦克默认停止；
	Direction ptDir=Direction.D;//定义炮筒的方向；
	
	TankClient tc;
	
	private BloodBar bb=new BloodBar();
	
	public Tank(int x,int y){
		this.x=x;
		this.y=y;
	}
	public Tank(int x,int y,boolean good,Direction dir,TankClient tc){
		this(x, y);
		this.tc=tc;
		this.good=good;
		this.dir=dir;
	}
	public boolean isGood(){
		return good;
	}
	public void setLive(boolean live){
		this.live=live;
	}
	public boolean isLive(){
		return live;
	}
	public void setLife(int life){
		this.life=life;
	}
	public int getLife(){
		return life;
	}
   public void draw(Graphics g){
	   if(!live){
		   if(!good)
			   tc.tanks.remove(this);
		   return;//坦克死亡移除并且不再画了；
	   }
		  Color c=g.getColor();//取画笔原来的颜色（默认黑色）；
		if(good)
		    g.setColor(Color.RED);//设置画笔现在的颜色（红色）；
		else
			g.setColor(Color.BLUE);
		//画一个实心圆，参数分别为 x,y,w,h(以x,y为左上角，长宽分别为w,h的内切圆)；
		g.fillOval(x, y, WIDTH, HEIGHT);
		g.setColor(c);//把画笔设置成原来的颜色；
		ptMove(g);
		move();
		if(good)
			bb.draw(g);
			
   }
   /**
    * 模拟炮筒，并定义出相应的方向；
    * @param g
    */
   public void ptMove(Graphics g){
	   switch(ptDir){
		 case L:
			g.drawLine(x+WIDTH/2, y+HEIGHT/2, x, y+HEIGHT/2);
			   break;
	      case LU:
	    	g.drawLine(x+WIDTH/2, y+HEIGHT/2, x, y);
	    	   break;
	     case U:
	    		g.drawLine(x+WIDTH/2, y+HEIGHT/2, x+WIDTH/2, y);
		   break;
	case RU:
		g.drawLine(x+WIDTH/2, y+HEIGHT/2, x+WIDTH, y);
		   break;
	case R:
		g.drawLine(x+WIDTH/2, y+HEIGHT/2,  x+WIDTH, y+HEIGHT/2);
		   break;
	case RD:
		g.drawLine(x+WIDTH/2, y+HEIGHT/2, x+WIDTH, y+HEIGHT);
		   break;
	case D:
		g.drawLine(x+WIDTH/2, y+HEIGHT/2,x+WIDTH/2, y+HEIGHT);
		   break;
	case LD:
		g.drawLine(x+WIDTH/2, y+HEIGHT/2, x, y+HEIGHT);
		   break;
	
		   }
   }
   /**
    * 确定坦克移动的方向；
    */
   public void move(){
	   oldX=x;
	   oldY=y;
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
case STOP:
	   break;
	   }
	   /*使炮筒无STOP方向*/
	   if(this.dir!=Direction.STOP)
		   this.ptDir=this.dir;
	   /*使坦克一直在界限里面；*/
	   if(x<0)
		   x=0;
	   if(y<25)
		   y=25;
	   if(x+Tank.WIDTH>TankClient.GAME_WIDTH)
		   x=TankClient.GAME_WIDTH-Tank.WIDTH;
	   if(y+Tank.HEIGHT>TankClient.GAME_HIGH)
		   y=TankClient.GAME_HIGH-Tank.HEIGHT;
	   if(!good){
		   Direction[] dirs=Direction.values();//将一个枚举类转化为数组；
		   //如果步数等于零，则换一个方向移动；
		   if(step==0){
			   step=random.nextInt(12)+3;
			   int rn=random.nextInt(dirs.length);//将在0~dirs.length-1之间产生随机整数；
			   dir=dirs[rn];
			  // step--; 
		   }
		  step--;//放在此处，step的步数开始不等于0；
		  if(random.nextInt(40)>38){
		  this.fire();
		  }
	   }
   }
   /**
    * 键盘释放；
    * @param e
    */
   public void keyReleased(KeyEvent e){
	   int key=e.getKeyCode();//从键盘获得虚拟的键（返回与此事件中的键关联的整数 keyCode）；
		//将获得的键和KeyEvent中的常量进行比较；
		switch(key){
	case KeyEvent.VK_CONTROL:
			fire();//每抬起一次发送一颗炮弹，防止炮弹过于密集；
			break;
		case KeyEvent.VK_RIGHT:
			bR=false;
			break;
		case KeyEvent.VK_LEFT:
			bL=false;
			break;
		case KeyEvent.VK_UP:
			bU=false;
			break;
		case KeyEvent.VK_DOWN:
			bD=false;
			break;
		case KeyEvent.VK_ALT:
		   superFire();
		   break;
		/*case KeyEvent.VK_A:
			if(!this.live){
				System.out.println("1");
			this.live=true;
				this.life=FULL;
			}
			break;*/
		}
		locateDirection();
   }
   public void keyPressed(KeyEvent e){
	   int key=e.getKeyCode();//从键盘获得虚拟的键（返回与此事件中的键关联的整数 keyCode）；
		//将获得的键和KeyEvent中的常量进行比较；
		switch(key){
		//若设置成按下键发射炮弹，一直按键不抬起也会持续发射炮弹，会造成炮弹过于密集的现象；
	/*	case KeyEvent.VK_CONTROL:
			fire();
			break;*/
		
	case KeyEvent.VK_0:
		
			if(!this.live){
//System.out.println("1");
				this.live=true;
				this.life=FULL;
			}
			break;
		case KeyEvent.VK_RIGHT:
			bR=true;
			break;
		case KeyEvent.VK_LEFT:
			bL=true;
			break;
		case KeyEvent.VK_UP:
			bU=true;
			break;
		case KeyEvent.VK_DOWN:
			bD=true;
			break;
		}
		locateDirection();
   }
   /**
    * 设置坦克的方向；
    */
   public void locateDirection(){
	   //按下朝左的键；
	   if(bL&&!bU&&!bR&&!bD)
		   dir=Direction.L;
	   else  if(bL&&bU&&!bR&&!bD)
		   dir=Direction.LU;
	   else  if(!bL&&bU&&!bR&&!bD)
		   dir=Direction.U;
	   else  if(!bL&&!bU&&bR&&!bD)
		   dir=Direction.R;
	   else  if(!bL&&bU&&bR&&!bD)
		   dir=Direction.RU;
	   else  if(!bL&&!bU&&!bR&&bD)
		   dir=Direction.D;
	   else  if(!bL&&!bU&&bR&&bD)
		   dir=Direction.RD;
	   else  if(bL&&!bU&&!bR&&bD)
		   dir=Direction.LD;
	   else  if(!bL&&!bU&&!bR&&!bD)
		   dir=Direction.STOP;
   }
   /**
    * 发射炮弹；
    * @return Missile子弹类的实例；
    */
   public Missile fire(){
	   if(!live)
		   return null;//自己已经死了就不可以发射炮弹了；
	   int x=this.x+Tank.WIDTH/2-Missile.WIDTH/2;
	   int y=this.y+Tank.HEIGHT/2-Missile.HEIGHT/2;
	   /*坦克停止时，炮筒仍然有相应的方向（坦克之前运动的方向、默认方向）*/
	   Missile m=new Missile(x,y,ptDir,this.good,tc);//使炮弹等于炮筒的方向,并将坦克好坏的属性加入；
	   tc.missiles.add(m);
	   return m;
	   }
   public Missile fire(Direction dir){
	   if(!live)
		   return null;//自己已经死了就不可以发射炮弹了；
	   int x=this.x+Tank.WIDTH/2-Missile.WIDTH/2;
	   int y=this.y+Tank.HEIGHT/2-Missile.HEIGHT/2;
	   /*坦克停止时，炮筒仍然有相应的方向（坦克之前运动的方向、默认方向）*/
	   Missile m=new Missile(x,y,dir,this.good,tc);//使炮弹等于炮筒的方向,并将坦克好坏的属性加入；
	 //  tc.missiles.add(m);
	   return m;
   }
   /**
	 * 得到一个碰撞检测类；
	 * @return Rectangle类的实例；
	 */
   public Rectangle getRect(){
 	  return new Rectangle(x,y,WIDTH,HEIGHT);
   }
   /**
    * 坦克与墙相撞，返回上一步，随机改变方向；
    * @param w
    * @return 相撞返回true,未相撞返回false;
    */
   public boolean collidesWithWall(Wall w){
	   if(this.live&&this.getRect().intersects(w.getRect())){
		   this.stay();
		   return true;
	   }
	   return false;
   }
   /**
    * 坦克不可与坦克相撞；
    * @param tanks
    * @return相撞返回true,未相撞返回false;
    */
   public boolean collidesWithTanks(List<Tank> tanks){
	   for(int i=0;i<tanks.size();i++){
		   Tank t=tanks.get(i);
		   if(this!=t){
		   if(this.live&&t.live&&this.getRect().intersects(t.getRect())){
			   this.stay();
			   t.stay();//两个坦克都回到上一步；
			   return true;
		   }
		  
	   }
	 }
	   return false;
  }
   /**
    * 回到上一步；
    */
  public void stay(){
	  x=oldX;
	   y=oldY;
  }
  /**
   * 发射超级炮弹；
   */
  public void superFire(){
	  Direction[] dirs=Direction.values();
	  for(int i=0;i<8;i++){
		  tc.missiles.add(fire(dirs[i]));
	  }
  }
  /**
   * 显示血块的内部类；
   * @author liuyao;
   *
   */
  private class BloodBar{
	  public void draw(Graphics g){
		  Color c=g.getColor();
		  g.setColor(Color.RED);
		  g.drawRect(x, y-10, WIDTH, 10);
		  int w=WIDTH*life/FULL;
		  g.fillRect(x, y-10, w, 10);
		  g.setColor(c);
	  }
  }
  public boolean eat(Blood b){
	  if(this.live&&b.isLive()&&this.getRect().intersects(b.getRect())){
		  this.life=FULL;
		  b.setLive(false);
		  return true;
	  }
	  return false;
  }
}
