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
import graphics.Spritesheet;
import graphics.UI;
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
	
	public static List<Entity> item;
	public static List<Integer> itemArray = new ArrayList<Integer>();
	
	public static List<Entity> weapon;
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


		image = new BufferedImage(WIDTH, HEIGHT,BufferedImage.TYPE_INT_RGB);
		entities = new ArrayList<Entity>();

		item = new ArrayList<Entity>();
		weapon = new ArrayList<Entity>();
		enemies = new ArrayList<Enemy>();
		
		itemsSprite = new Spritesheet("/itens.png"); //itens
		worldSprite = new Spritesheet("/tiles.png"); //tiles
		playerSprite = new Spritesheet("/player.png"); //player
		uiSprite = new Spritesheet("/UI.png");
		
		ui = new UI();
		
		//Player              
		player = new Player(0,0,16,16,playerSprite.getSprite(0, 0,16,16));
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

		//Rendering the item sprite
		for(int i = 0; i< item.size();i++) {
			Entity e = item.get(i);
			e.render(g);
		}
		
		//Rendering the weapon sprite
		for(int i = 0; i< weapon.size();i++) {
			Entity e = weapon.get(i);
			e.render(g);
		}
		
		
		
		/***/
		ui.weaponRender(g);
		ui.throwableRender(g);
		ui.symbolsRender(g);
		
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
		
       if(e.getKeyCode() == KeyEvent.VK_E) {
            Game.player.confirm = true;
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
		
		
       if(e.getKeyCode() == KeyEvent.VK_E) {
            Game.player.confirm = false;
       }
    

	}

	@Override
	public void keyTyped(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_E) {
			
		}
		if(e.getKeyCode() == KeyEvent.VK_Q) {
			System.out.println("Trocar de arma");
		}
		
	}		
}