package tankwar;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

public class Tank {
	int x,y;
	
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
   }
   public void keyPressed(KeyEvent e){
	   int key=e.getKeyCode();//从键盘获得虚拟的键（返回与此事件中的键关联的整数 keyCode）；
		//将获得的键和KeyEvent中的常量进行比较；
		switch(key){
		case KeyEvent.VK_RIGHT:
			x+=5;
			break;
		case KeyEvent.VK_LEFT:
			x-=5;
			break;
		case KeyEvent.VK_UP:
			y-=5;
			break;
		case KeyEvent.VK_DOWN:
			y+=5;
			break;
		}
   }
}
