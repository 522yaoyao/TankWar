package tankwar;

import java.awt.Color;
//import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.ArrayList;


public class TankClient extends Frame {
	public static final int GAME_WIDTH=800;
	public static final int GAME_HIGH=600;
	
	Tank myTank=new Tank(50,50,true,this);//设置自己的坦克；
	Tank  enemyTank =new Tank(100,100,false,this);
	List<Missile> missiles=new ArrayList<Missile>();
	
	  //Missile  missile;
	Image offScreenImage=null;
	
	/**
	 * 进行重画，需要重画时，默认调用；
	 */
	@Override
	public void paint(Graphics g){
  //	g.setFont(new Font("中华彩云",Font.BOLD,20));
		g.drawString(" missile count "+missiles.size(), 10, 40);//显示发出的炮弹数目；
		for(int i=0;i<missiles.size();i++){
			Missile m=missiles.get(i);
		    m.draw(g);
		    
		}
	     myTank.draw(g);
	     enemyTank.draw(g);
		
	}
	/**
	 * 解决屏幕闪烁的问题（用双缓冲解决）；方法调用顺序：repaint();update();paint();
	 */
	@Override

	public void update(Graphics g){
		if(offScreenImage==null){
			offScreenImage=this.createImage(GAME_WIDTH, GAME_HIGH);//创建一个和窗口同样大小的图片；
		}
	//获取该图片的画笔；
		Graphics gOffScreenImage=offScreenImage.getGraphics();
		//每绘画一次，图片重刷一次，用绿色背景覆盖上一次的绘画记录，然后再重新绘画坦克；
		Color c=gOffScreenImage.getColor();
		gOffScreenImage.setColor(Color.GREEN);
		gOffScreenImage.fillRect(0, 0, GAME_WIDTH, GAME_HIGH);
		gOffScreenImage.setColor(c);
		paint(gOffScreenImage);//在此虚拟图片上绘画；
		
		g.drawImage(offScreenImage, 0, 0, null);//将此图片绘画到窗上；
		
	}
	//继承方法更灵活，可以设置自己的变量和方法；	
		public void lauchFrame() {
			this.setLocation(400, 300);//定义窗口出现的位置，以屏幕的左上角为准；
			this.setSize(GAME_WIDTH, GAME_HIGH);//设置宽度和高度；
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
			this.addKeyListener(new KeyMonitor());//添加指定的按键侦听器，以接收发自此组件的按键事件;
			new Thread(new PaintThread()).start();
		//	this.addKeyListener(new KeyMonitor());//添加指定的按键侦听器，以接收发自此组件的按键事件;
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
/*写一个内部类实现键盘监听*/
private class KeyMonitor extends KeyAdapter{
	@Override
	public void keyPressed(KeyEvent e){
//System.out.println("OK");		
	myTank.keyPressed(e);
	}
	@Override
	public void keyReleased(KeyEvent e){
		myTank.keyReleased(e);
	}
}
	}


