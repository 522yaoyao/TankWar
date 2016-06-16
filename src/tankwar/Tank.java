package tankwar;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

public class Tank {
	public static final int XSPEED=5;
	public static final int YSPEED=5;
	
	public static final int WIDTH=30;
	public static final int HEIGHT=30;
	
	
	private int x,y;
	private boolean bL=false, bU=false,bR=false,bD=false;
	enum Direction{L,U,LU,R,RU,D,RD,LD,STOP};////定义枚举类型；
	Direction dir=Direction.STOP;//坦克默认停止；
	Direction ptDir=Direction.D;//定义炮筒的方向；
	
	TankClient tc;
	
	public Tank(int x,int y){
		this.x=x;
		this.y=y;
	}
	public Tank(int x,int y,TankClient tc){
		this(x, y);
		this.tc=tc;
	}
   public void draw(Graphics g){
		Color c=g.getColor();//取画笔原来的颜色（默认黑色）；
		g.setColor(Color.RED);//设置画笔现在的颜色（红色）；
		//画一个实心圆，参数分别为 x,y,w,h(以x,y为左上角，长宽分别为w,h的内切圆)；
		g.fillOval(x, y, WIDTH, HEIGHT);
		g.setColor(c);//把画笔设置成原来的颜色；
		ptMove(g);
		move();
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
case STOP:
	   break;
	   }
	   /*使炮筒无STOP方向*/
	   if(this.dir!=Direction.STOP)
		   this.ptDir=this.dir;
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
   public Missile fire(){
	   int x=this.x+Tank.WIDTH/2-Missile.WIDTH/2;
	   int y=this.y+Tank.HEIGHT/2-Missile.HEIGHT/2;
	   /*坦克停止时，炮筒仍然有相应的方向（坦克之前运动的方向、默认方向）*/
	   Missile m=new Missile(x,y,ptDir,this.tc);//使炮弹等于炮筒的方向
	   tc.missiles.add(m);
	   return m;
	   }
}
