package tankwar;

import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class TankClient extends Frame {
	//�̳з����������������Լ��ı����ͷ�����	
		public void lauchFrame() {
			this.setLocation(400, 300);//���崰�ڳ��ֵ�λ�ã�����Ļ�����Ͻ�Ϊ׼��
			this.setSize(800, 600);//���ÿ�Ⱥ͸߶ȣ�
			this.setTitle("TankWar");
			//����һ�������������ô��ڹرյļ�����
			this.addWindowListener(new WindowAdapter(){
				@Override
				public void windowClosing(WindowEvent e){
					System.exit(0);//���������˳���;
				}
				
			});
			setResizable(false);
			setVisible(true);// ���ݲ��� b ��ֵ��ʾ�����ش� Window��
		}

		public static void main(String[] args) {
			TankClient tc = new TankClient();
			tc.lauchFrame();
		}

	}


