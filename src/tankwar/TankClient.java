package tankwar;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class TankClient extends Frame {
	public static final int GAME_WIDTH=800;
	public static final int GAME_HIGH=600;
	
	Tank myTank=new Tank(50,50);
	
	Image offScreenImage=null;
	
	/**
	 * �����ػ�����Ҫ�ػ�ʱ��Ĭ�ϵ��ã�
	 */
	@Override
	public void paint(Graphics g){
	/*	Color c=g.getColor();//ȡ����ԭ������ɫ��Ĭ�Ϻ�ɫ����
		g.setColor(Color.RED);//���û������ڵ���ɫ����ɫ����
		//��һ��ʵ��Բ�������ֱ�Ϊ x,y,w,h(��x,yΪ���Ͻǣ�����ֱ�Ϊw,h������Բ)��
		g.fillOval(x, y, 30, 30);
		g.setColor(c);//�ѻ������ó�ԭ������ɫ��*/
		myTank.draw(g);
		
	}
	/**
	 * �����Ļ��˸�����⣨��˫������������������˳��repaint();update();paint();
	 */
	@Override

	public void update(Graphics g){
		if(offScreenImage==null){
			offScreenImage=this.createImage(GAME_WIDTH, GAME_HIGH);//����һ���ʹ���ͬ����С��ͼƬ��
		}
	//��ȡ��ͼƬ�Ļ��ʣ�
		Graphics gOffScreenImage=offScreenImage.getGraphics();
		//ÿ�滭һ�Σ�ͼƬ��ˢһ�Σ�����ɫ����������һ�εĻ滭��¼��Ȼ�������»滭̹�ˣ�
		Color c=gOffScreenImage.getColor();
		gOffScreenImage.setColor(Color.GREEN);
		gOffScreenImage.fillRect(0, 0, GAME_WIDTH, GAME_HIGH);
		gOffScreenImage.setColor(c);
		paint(gOffScreenImage);//�ڴ�����ͼƬ�ϻ滭��
		
		g.drawImage(offScreenImage, 0, 0, null);//����ͼƬ�滭�����ϣ�
		
	}
	//�̳з����������������Լ��ı����ͷ�����	
		public void lauchFrame() {
			this.setLocation(400, 300);//���崰�ڳ��ֵ�λ�ã�����Ļ�����Ͻ�Ϊ׼��
			this.setSize(GAME_WIDTH, GAME_HIGH);//���ÿ�Ⱥ͸߶ȣ�
			this.setTitle("TankWar");
			//����һ�������������ô��ڹرյļ�����
			this.addWindowListener(new WindowAdapter(){
				@Override
				public void windowClosing(WindowEvent e){
					System.exit(0);//���������˳���;
				}
				
			});
			this.setBackground(Color.GREEN);
			this.setResizable(false);//���ô˴����Ƿ�����û�������С��
			this.setVisible(true);// ���ݲ��� b ��ֵ��ʾ�����ش� Window��
			this.addKeyListener(new KeyMonitor());//���ָ���İ������������Խ��շ��Դ�����İ����¼�;
			new Thread(new PaintThread()).start();
		//	this.addKeyListener(new KeyMonitor());//���ָ���İ������������Խ��շ��Դ�����İ����¼�;
		}

		public static void main(String[] args) {
		TankClient tc = new TankClient();
		tc.lauchFrame();
		}
		/*���߳��ػ�����*/
private class PaintThread implements Runnable{
	public void run(){
		while(true){
			repaint();//�����������������������˷����ᾡ����ô������ paint ����;
			try {
				Thread.sleep(100);//ʹ��ǰ���õ��߳�sleepһ��ʱ��;
			} catch (InterruptedException e) {
			      e.printStackTrace();
			}
		}
	}
}
/*дһ���ڲ���ʵ�ּ��̼���*/
private class KeyMonitor extends KeyAdapter{
	@Override
	public void keyPressed(KeyEvent e){
//System.out.println("OK");		
	myTank.keyPressed(e);
	}
	
}
	}


