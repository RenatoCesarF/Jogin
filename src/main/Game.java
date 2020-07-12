package main;


import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import entities.Entity;
import entities.Player;
import graficos.Spritesheet;
import world.World;

public class Game extends Canvas implements Runnable, KeyListener {

	private static final long serialVersionUID = 1L;
	public static JFrame frame;
	private Thread thread;
	private boolean isRunning = true;
	public static final int WIDTH = 240;
	public static final int HEIGHT = 160;
	private final int SCALE = 4;

	private BufferedImage image;
	
	public static List<Entity> entities; 
	
	//Sprites
	public static Spritesheet playerSprite;
	public static Spritesheet itemsSprite;
	public static Spritesheet worldSprite;
	
	
	public static World world;
	
	public static Player player;
	
	public Game() {
		addKeyListener(this);
		setPreferredSize( new Dimension(WIDTH*SCALE,HEIGHT*SCALE));
		initFrame();
		
		//Inicializando objetos
		image = new BufferedImage(WIDTH, HEIGHT,BufferedImage.TYPE_INT_RGB);
		entities = new ArrayList<Entity>();
		
		itemsSprite = new Spritesheet("/itens.png"); //itens
		worldSprite = new Spritesheet("/ch�o.png"); //ch�o
		playerSprite = new Spritesheet("/player.png"); //player
		
		//Player
		player = new Player(0,0,16,16,playerSprite.getSprite(0, 0, 16,16));
		entities.add(player);
		
		world = new World("/map.png"); //inicializando a classe de desenhar mundo
		
		}
	
	public void initFrame(){
		frame = new JFrame("Jogin");
		frame.add(this);
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	public synchronized void start() {
		thread = new Thread(this);
		isRunning = true;	
		thread.start();
		
	}
	public synchronized void stop() {
		isRunning = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void main(String args[]) {
		Game game = new Game(); 
		game.start();
		
	}
	
	/**/
	public void tick(){
		for(int i = 0; i< entities.size();i++) {
			Entity e = entities.get(i);

			e.tick();
		}
	} 
	
	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if ( bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = image.getGraphics();
		g.setColor(new Color(0,0,0));
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		world.render(g);

		
		
		for(int i = 0; i< entities.size();i++) {
			Entity e = entities.get(i);
			e.render(g);
		
		}
		
		/***/
		g.dispose();
		g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, WIDTH*SCALE, HEIGHT*SCALE, null);
		bs.show();
		
	}
	
	public void run(){
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		int frames = 0;
		double timer = System.currentTimeMillis();
		while(isRunning ) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			
			if(delta >= 1) {
				render();
				tick();
				frames++;
				delta--;
			}
			
			if(System.currentTimeMillis() - timer >= 1000) {
				System.out.println("FPS: " + frames);
				frames = 0;
				timer +=1000;
			}
		
		}
		stop();
	}

	
	public void keyPressed(KeyEvent e) {
		//left and right
		if(e.getKeyCode() == KeyEvent.VK_D) {
			player.right = true;
		}
		
		else if(e.getKeyCode() == KeyEvent.VK_A) {
			player.left = true;
		}
		
		//Forward and back
		if(e.getKeyCode() == KeyEvent.VK_W) {
			player.up = true;
		}
		
		else if(e.getKeyCode() == KeyEvent.VK_S) {
			player.down = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_D) {
			player.right = false;
		}
		
		else if(e.getKeyCode() == KeyEvent.VK_A) {
			player.left = false;
		}
		
		//Forward and back
		if(e.getKeyCode() == KeyEvent.VK_W) {			
			player.up = false;
		}
		
		else if(e.getKeyCode() == KeyEvent.VK_S) {
			player.down = false;
		}
		

	}

	@Override
	public void keyTyped(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_E) {
			System.out.println("Interagir");
		}
		if(e.getKeyCode() == KeyEvent.VK_Q) {
			System.out.println("Trocar de arma");
		}
		
	}		
}