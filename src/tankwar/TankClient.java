package tankwar;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class TankClient extends Frame {
	
	int x=50,y=50;
	
	/**
	 * 进行重画，需要重画时，默认调用；
	 */
	@Override
	public void paint(Graphics g){
		Color c=g.getColor();//取画笔原来的颜色（默认黑色）；
		g.setColor(Color.RED);//设置画笔现在的颜色（红色）；
		//画一个实心圆，参数分别为 x,y,w,h(以x,y为左上角，长宽分别为w,h的内切圆)；
		g.fillOval(x, y, 30, 30);
		g.setColor(c);//把画笔设置成原来的颜色；
		y=y+5;
	}
	//继承方法更灵活，可以设置自己的变量和方法；	
		public void lauchFrame() {
			this.setLocation(400, 300);//定义窗口出现的位置，以屏幕的左上角为准；
			this.setSize(800, 600);//设置宽度和高度；
			this.setTitle("TankWar");
			//建立一个匿名类来设置窗口关闭的监听；
			this.addWindowListener(new WindowAdapter(){
				@Override
				public void windowClosing(WindowEvent e){
					System.exit(0);//窗口正常退出；;
				}
				
			});
			this.setBackground(Color.GREEN);
			this.setResizable(false);//设置此窗体是否可由用户调整大小；
			this.setVisible(true);// 根据参数 b 的值显示或隐藏此 Window；
			new Thread(new PaintThread()).start();
		}

		public static void main(String[] args) {
			TankClient tc = new TankClient();
			tc.lauchFrame();
		}
		/*用线程重画均匀*/
private class PaintThread implements Runnable{
	public void run(){
		while(true){
			repaint();//如果此组件是轻量级组件，则此方法会尽快调用此组件的 paint 方法;
			try {
				Thread.sleep(100);//使当前调用的线程sleep一段时间;
			} catch (InterruptedException e) {
			      e.printStackTrace();
			}
		}
	}
}
	}


