package tankwar;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

public class Tank {
	private int x,y;
	public static final int XSPEED=5;
	public static final int YSPEED=5;
	
	private boolean bL=false, bU=false,bR=false,bD=false;
	//定义枚举类型；
	enum Direction{L,U,LU,R,RU,D,RD,LD,STOP};
	Direction dir=Direction.STOP;//坦克默认停止；
	public Tank(int x,int y){
		this.x=x;
		this.y=y;
	}
   public void draw(Graphics g){
		Color c=g.getColor();//取画笔原来的颜色（默认黑色）；
		g.setColor(Color.RED);//设置画笔现在的颜色（红色）；
		//画一个实心圆，参数分别为 x,y,w,h(以x,y为左上角，长宽分别为w,h的内切圆)；
		g.fillOval(x, y, 30, 30);
		g.setColor(c);//把画笔设置成原来的颜色；
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
case STOP:
	   break;
	   }
   }
   public void keyPressed(KeyEvent e){
	   int key=e.getKeyCode();//从键盘获得虚拟的键（返回与此事件中的键关联的整数 keyCode）；
		//将获得的键和KeyEvent中的常量进行比较；
		switch(key){
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
}
