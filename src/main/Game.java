package main;


import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;

import entities.Enemy;
import entities.Entity;
import entities.Player;
import graficos.Spritesheet;
import graficos.UI;
import world.World;

public class Game extends Canvas implements Runnable, KeyListener {

	private static final long serialVersionUID = 1L;
	public static JFrame frame;
	private Thread thread;
	private boolean isRunning = true;
	public static final int WIDTH = 240;
	public static final int HEIGHT = 160;
	private final int SCALE = 4;

	public static BufferedImage image;
	
	public static List<Entity> entities;
	public static List<Enemy> enemies; 
	
	//List of items
	public static List<Entity> energy;
	public static List<Entity> ammo;
	public static List<Entity> mana;
	
	public static List<Entity> gun;
	public static List<Integer> weaponArray = new ArrayList<Integer>();
	
	//Sprites
	public static Spritesheet playerSprite;
	public static Spritesheet itemsSprite;
	public static Spritesheet worldSprite;
	public static Spritesheet uiSprite;
	

	
	public static World world;
	
	public static Player player;
	
	public static Random rand; 
	
	public UI ui; 
	
	public Game() {
		
		rand = new Random();
		
		addKeyListener(this);
		setPreferredSize( new Dimension(WIDTH*SCALE,HEIGHT*SCALE));
		initFrame();
		
		//Inicializando objetos

		ui = new UI();
		
		image = new BufferedImage(WIDTH, HEIGHT,BufferedImage.TYPE_INT_RGB);
		entities = new ArrayList<Entity>();
		energy = new ArrayList<Entity>();
		ammo = new ArrayList<Entity>();
		gun = new ArrayList<Entity>();
		enemies = new ArrayList<Enemy>();
		
		itemsSprite = new Spritesheet("/itens.png"); //itens
		worldSprite = new Spritesheet("/tiles.png"); //tiles
		playerSprite = new Spritesheet("/player.png"); //player
		uiSprite = new Spritesheet("/UI.png");
		
		//Player               16 16
		player = new Player(0,0,1,1,playerSprite.getSprite(0, 0,16,16));
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
		//Rendering the energy sprite
		for(int i = 0; i< energy.size();i++) {
			Entity e = energy.get(i);
			e.render(g);
		}
		//Rendering the ammo sprite
		for(int i = 0; i< ammo.size();i++) {
			Entity e = ammo.get(i);
			e.render(g);
		}
		//Rendering the gun sprite
		for(int i = 0; i< gun.size();i++) {
			Entity e = gun.get(i);
			e.render(g);
		}
		
		
		
		/***/
		ui.symblosRender(g);
		g.dispose();
		g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, WIDTH*SCALE, HEIGHT*SCALE, null);
		
		ui.render(g);
		
		bs.show();
		
	}
	
	public void run(){
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		int frames = 0;
		double timer = System.currentTimeMillis();
		requestFocus();
		
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
				//System.out.println("FPS: " + frames);
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