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
	
	private int step=random.nextInt(12)+3;//�з�̹�˳�ĳ�������ƶ��Ĳ�����
	private static Random random=new Random();//����һ����̬����������ʵ������
	
     private int life=FULL;
	
	private boolean live=true;//Ĭ��̹�˴��ڴ��״̬��
	private boolean bL=false, bU=false,bR=false,bD=false;
	private boolean good=true;//�жϵ���̹�ˣ�
	
	enum Direction{L,U,LU,R,RU,D,RD,LD,STOP};////����ö�����ͣ�
	Direction dir=Direction.STOP;//̹��Ĭ��ֹͣ��
	Direction ptDir=Direction.D;//������Ͳ�ķ���
	
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
		   return;//̹�������Ƴ����Ҳ��ٻ��ˣ�
	   }
		  Color c=g.getColor();//ȡ����ԭ������ɫ��Ĭ�Ϻ�ɫ����
		if(good)
		    g.setColor(Color.RED);//���û������ڵ���ɫ����ɫ����
		else
			g.setColor(Color.BLUE);
		//��һ��ʵ��Բ�������ֱ�Ϊ x,y,w,h(��x,yΪ���Ͻǣ�����ֱ�Ϊw,h������Բ)��
		g.fillOval(x, y, WIDTH, HEIGHT);
		g.setColor(c);//�ѻ������ó�ԭ������ɫ��
		ptMove(g);
		move();
		if(good)
			bb.draw(g);
			
   }
   /**
    * ģ����Ͳ�����������Ӧ�ķ���
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
    * ȷ��̹���ƶ��ķ���
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
	   /*ʹ��Ͳ��STOP����*/
	   if(this.dir!=Direction.STOP)
		   this.ptDir=this.dir;
	   /*ʹ̹��һֱ�ڽ������棻*/
	   if(x<0)
		   x=0;
	   if(y<25)
		   y=25;
	   if(x+Tank.WIDTH>TankClient.GAME_WIDTH)
		   x=TankClient.GAME_WIDTH-Tank.WIDTH;
	   if(y+Tank.HEIGHT>TankClient.GAME_HIGH)
		   y=TankClient.GAME_HIGH-Tank.HEIGHT;
	   if(!good){
		   Direction[] dirs=Direction.values();//��һ��ö����ת��Ϊ���飻
		   //������������㣬��һ�������ƶ���
		   if(step==0){
			   step=random.nextInt(12)+3;
			   int rn=random.nextInt(dirs.length);//����0~dirs.length-1֮��������������
			   dir=dirs[rn];
			  // step--; 
		   }
		  step--;//���ڴ˴���step�Ĳ�����ʼ������0��
		  if(random.nextInt(40)>38){
		  this.fire();
		  }
	   }
   }
   /**
    * �����ͷţ�
    * @param e
    */
   public void keyReleased(KeyEvent e){
	   int key=e.getKeyCode();//�Ӽ��̻������ļ�����������¼��еļ����������� keyCode����
		//����õļ���KeyEvent�еĳ������бȽϣ�
		switch(key){
	case KeyEvent.VK_CONTROL:
			fire();//ÿ̧��һ�η���һ���ڵ�����ֹ�ڵ������ܼ���
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
	   int key=e.getKeyCode();//�Ӽ��̻������ļ�����������¼��еļ����������� keyCode����
		//����õļ���KeyEvent�еĳ������бȽϣ�
		switch(key){
		//�����óɰ��¼������ڵ���һֱ������̧��Ҳ����������ڵ���������ڵ������ܼ�������
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
    * ����̹�˵ķ���
    */
   public void locateDirection(){
	   //���³���ļ���
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
    * �����ڵ���
    * @return Missile�ӵ����ʵ����
    */
   public Missile fire(){
	   if(!live)
		   return null;//�Լ��Ѿ����˾Ͳ����Է����ڵ��ˣ�
	   int x=this.x+Tank.WIDTH/2-Missile.WIDTH/2;
	   int y=this.y+Tank.HEIGHT/2-Missile.HEIGHT/2;
	   /*̹��ֹͣʱ����Ͳ��Ȼ����Ӧ�ķ���̹��֮ǰ�˶��ķ���Ĭ�Ϸ���*/
	   Missile m=new Missile(x,y,ptDir,this.good,tc);//ʹ�ڵ�������Ͳ�ķ���,����̹�˺û������Լ��룻
	   tc.missiles.add(m);
	   return m;
	   }
   public Missile fire(Direction dir){
	   if(!live)
		   return null;//�Լ��Ѿ����˾Ͳ����Է����ڵ��ˣ�
	   int x=this.x+Tank.WIDTH/2-Missile.WIDTH/2;
	   int y=this.y+Tank.HEIGHT/2-Missile.HEIGHT/2;
	   /*̹��ֹͣʱ����Ͳ��Ȼ����Ӧ�ķ���̹��֮ǰ�˶��ķ���Ĭ�Ϸ���*/
	   Missile m=new Missile(x,y,dir,this.good,tc);//ʹ�ڵ�������Ͳ�ķ���,����̹�˺û������Լ��룻
	 //  tc.missiles.add(m);
	   return m;
   }
   /**
	 * �õ�һ����ײ����ࣻ
	 * @return Rectangle���ʵ����
	 */
   public Rectangle getRect(){
 	  return new Rectangle(x,y,WIDTH,HEIGHT);
   }
   /**
    * ̹����ǽ��ײ��������һ��������ı䷽��
    * @param w
    * @return ��ײ����true,δ��ײ����false;
    */
   public boolean collidesWithWall(Wall w){
	   if(this.live&&this.getRect().intersects(w.getRect())){
		   this.stay();
		   return true;
	   }
	   return false;
   }
   /**
    * ̹�˲�����̹����ײ��
    * @param tanks
    * @return��ײ����true,δ��ײ����false;
    */
   public boolean collidesWithTanks(List<Tank> tanks){
	   for(int i=0;i<tanks.size();i++){
		   Tank t=tanks.get(i);
		   if(this!=t){
		   if(this.live&&t.live&&this.getRect().intersects(t.getRect())){
			   this.stay();
			   t.stay();//����̹�˶��ص���һ����
			   return true;
		   }
		  
	   }
	 }
	   return false;
  }
   /**
    * �ص���һ����
    */
  public void stay(){
	  x=oldX;
	   y=oldY;
  }
  /**
   * ���䳬���ڵ���
   */
  public void superFire(){
	  Direction[] dirs=Direction.values();
	  for(int i=0;i<8;i++){
		  tc.missiles.add(fire(dirs[i]));
	  }
  }
  /**
   * ��ʾѪ����ڲ��ࣻ
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
