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
		Color c=g.getColor();//ȡ����ԭ������ɫ��Ĭ�Ϻ�ɫ����
		g.setColor(Color.RED);//���û������ڵ���ɫ����ɫ����
		//��һ��ʵ��Բ�������ֱ�Ϊ x,y,w,h(��x,yΪ���Ͻǣ�����ֱ�Ϊw,h������Բ)��
		g.fillOval(x, y, 30, 30);
		g.setColor(c);//�ѻ������ó�ԭ������ɫ��
   }
   public void keyPressed(KeyEvent e){
	   int key=e.getKeyCode();//�Ӽ��̻������ļ�����������¼��еļ����������� keyCode����
		//����õļ���KeyEvent�еĳ������бȽϣ�
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
