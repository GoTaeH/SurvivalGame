package survivalGame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class SurvivalGame {
	JFrame frame = new JFrame("Survival Game");
	int WIDTH = 450;
	int HEIGHT = 450;
	int SPEED = 10;

	int x = 200;	//중앙 쪽에서 시작
	int y = 200;
	int eX = 0;
	int eY = 0;
	int radius = 10;
	int xStep = 0;
	int yStep = 0;
	double time = 0;

	ArrayList<Enemy> enemys = new ArrayList<>();
	boolean finish = false;

	public SurvivalGame() {
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(WIDTH, HEIGHT);
		frame.setLocation(725, 300);	// frame 위치 지정
		frame.getContentPane().add(new GamePanel());
		
		frame.addWindowListener(new GameWindowListener());
		frame.addKeyListener(new GameKeyListener());
		frame.setResizable(false); //frame 창 크기 고정
		frame.setVisible(true);
		
		enemys.add(new Enemy());
		enemys.add(new Enemy());
		
	}

	public void go() {
		while(!finish) {
			x += xStep;
			y += yStep;

			if(x + radius > WIDTH-(radius*2)-5) {
				xStep -= 1;;
			}if(x < 0) {
				xStep += 1;
			}if(y < 0) {	
				yStep += 1;
			}if(y + radius > HEIGHT-(radius*2)-30) {
				yStep -= 1;
			}// 벽에 닿으면 튕겨짐

			try {
				Thread.sleep(SPEED);
				time += 1;	//time 0.01초 단위로 증가
			}catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			for (Enemy enemy : enemys) {
				if(Math.abs(enemy.eX-x)<10 && Math.abs(enemy.eY-y)<10 ||
						Math.abs(enemy.eX-(x+radius))<10 && Math.abs(enemy.eY-(y+radius))<10) finish = true;
			}
			
			if(time % 200 == 0) {
				enemys.add(new Enemy());
				enemys.add(new Enemy());
			}//2초 지날때마다 enemy 두개씩 추가 생성
			

			frame.repaint();
						
		}
	}

	class GamePanel extends JPanel {
		public void paintComponent(Graphics g) {
			super.paintComponent(g);

			for(Enemy enemy : enemys) {
				g.setColor(Color.RED);
				g.fillOval(enemy.geteX(), enemy.geteY(), 10, 10);
				if(Math.abs(enemy.eX-x)<10 && Math.abs(enemy.eY-y)<10 ||
						Math.abs(enemy.eX-(x+radius))<10 && Math.abs(enemy.eY-(y+radius))<10) {
					g.setFont(new Font("고딕체", Font.BOLD, 40)); //게임이 끝날 때 플레이 시간과 game over 문구가 진하게 표현
					g.drawString("Game Over", 90, 200);
				}
			}
			if(finish == false) {
				if(time / 6000 == 1.00) {
					finish = true;
					JOptionPane.showMessageDialog(null, "Game Clear!", "CLEAR_MESSAGE", JOptionPane.PLAIN_MESSAGE);
					System.exit(0); // 메세지 창에서 확인 버튼 누르면 프레임 모두 종료
				}//60초가 되면 game clear 메세지 창 등장(아이콘 없는 메세지 창)
			}

			g.setColor(Color.DARK_GRAY);
			g.fillRect(x, y, radius*2, radius*2);
			g.setFont(new Font("고딕체", Font.BOLD, 20));
			g.drawString("Time: "+time/100, 30, 40);
		}
	}
	class Enemy {
		int x = 200;
		int y = 200;
		int eX = 0;
		int eY = 0;

		public Enemy() {
			this.eX = (int) (Math.random()* (WIDTH-30)+1);
			this.eY = (int) (Math.random()* (HEIGHT-30)+1);
		}

		public int geteX() {
			return eX;
		}

		public void seteX(int eX) {
			this.eX = eX;
		}

		public int geteY() {
			return eY;
		}

		public void seteY(int eY) {
			this.eY = eY;
		}

		public int getX() {
			return x;
		}

		public void setX(int x) {
			this.x = x;
		}

		public int getY() {
			return y;
		}

		public void setY(int y) {
			this.y = y;
		}

	}
	class GameKeyListener implements KeyListener {

		@Override
		public void keyTyped(KeyEvent e) {			
		}
		@Override
		public void keyPressed(KeyEvent e) {
			switch (e.getKeyCode()) {
			case KeyEvent.VK_UP:
				xStep = 0;
				yStep = -1;
				break;
			case KeyEvent.VK_DOWN:
				xStep = 0;
				yStep = 1;
				break;
			case KeyEvent.VK_LEFT:
				xStep = -1;
				yStep = 0;
				break;
			case KeyEvent.VK_RIGHT:
				xStep = 1;
				yStep = 0;
				break;
			}
		}
		@Override
		public void keyReleased(KeyEvent e) {			
		}

	}
	class GameWindowListener implements WindowListener {

		@Override
		public void windowOpened(WindowEvent e) {			
		}
		@Override
		public void windowClosing(WindowEvent e) {
			System.exit(0);	//frame 종료
		}
		@Override
		public void windowClosed(WindowEvent e) {			
		}
		@Override
		public void windowIconified(WindowEvent e) {			
		}
		@Override
		public void windowDeiconified(WindowEvent e) {			
		}
		@Override
		public void windowActivated(WindowEvent e) {			
		}
		@Override
		public void windowDeactivated(WindowEvent e) {
		}
	}

}
