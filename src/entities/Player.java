 package entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.Game;
import world.Camera;
import world.World;

public class Player extends Entity{
	
	public boolean right, up, left, down;
	public double speed = 0.9;
	
	private int frames = 0,maxFrames = 4, indexHorizontal, indexVertical;
	private int maxIndexHorizontal = 8, maxIndexVertical = 4;
	private boolean movedHorizontal = false;
	
	public static double life = 5, maxLife = 5;
	private int colidingArea = 16;
	
	private BufferedImage[] playerRight;
	private BufferedImage[] playerLeft;
	private BufferedImage[] playerUp;
	private BufferedImage[] playerDown;
	private BufferedImage[] playerStatic;
	
	public Player(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		
		playerRight = new BufferedImage[8];
		playerLeft = new BufferedImage[8];
		playerUp = new BufferedImage[4];
		playerDown = new BufferedImage[4];
		playerStatic = new BufferedImage[2];
		
		//sprite player static
		for(int i = 0; i < 2; i++) {			          //frame,start, largura, algura 
			playerStatic[i] = Game.playerSprite.getSprite(0+(i*16), 0, 16, 16);//0
		}
		//sprite player moving for right
		for(int i = 0; i < 8; i++) {			
			playerRight[i] = Game.playerSprite.getSprite(0+(i*16), 48, 16, 16);//48
		}
		//sprite player moving for left
		for(int i = 0; i <8; i++) {
			playerLeft[i] = Game.playerSprite.getSprite(0+(i*16), 64, 16, 16);//64
		}
		//sprite player moving for up
		for(int i = 0; i < 4; i++) {
			playerUp[i] = Game.playerSprite.getSprite(0+(i*16), 32, 16, 16);// 32
		}
		//sprite player moving down
		for(int i = 0; i < 4; i++) {
			playerDown[i] = Game.playerSprite.getSprite(0+(i*16), 16, 16, 16);
		}
	}
	
	public void loseLife(int damage) {
		Player.life -= damage;
		System.out.println("new life: " + Player.life);
		
		immunity();
		
		if(Player.life <= 0 ) {
			playderDied();
		}
	}
	
	public int getColidingArea() {
		return this.colidingArea;
	}
	
	public void immunity() {
		Thread Teste = new Thread(new Runnable() {
			public void run() {
				Game.player.colidingArea = 0;
				//system of colorize the player with white here
				try {
					Thread.sleep(2500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Game.player.colidingArea = 16;
			}
		});
		
		Teste.start();

		
	}
	
	public void playderDied() {
		System.exit(1);
	}

	
	
	public void tick(){
		movedHorizontal = false;
		if(up && World.isFree(this.getX(), (int)(y-speed))){
			y -= speed;
		}
		else if(down && World.isFree(this.getX() , (int)(y + speed))){
			
			y += speed;
		}
		if(right && World.isFree((int)(x + speed ), this.getY())) {
			movedHorizontal = true;
			x += speed;
		}
		else if(left && World.isFree((int)(x - speed ), this.getY())){
			movedHorizontal = true;
			x -= speed;
		}
		
		
		if(movedHorizontal){
			frames ++;
			if(frames ==maxFrames) {
				frames = 0;
				indexHorizontal++;
				if( indexHorizontal >= maxIndexHorizontal) {
					indexHorizontal = 0;
				}
			}
		}
		
		else{
			frames ++;
			if(frames == maxFrames) {
				frames = 0;
				indexVertical++;
				if( indexVertical >= maxIndexVertical) {
					indexVertical = 0;
				}
			}
		}
		
		//Checking the collision
		this.checkCollisionItem();
		
		//Camera Follow the player
		Camera.x =  Camera.clamp(this.getX() - (Game.WIDTH/2), 0, World.WIDTH * 16 - Game.WIDTH);
		Camera.y =  Camera.clamp(this.getY() - (Game.HEIGHT/2), 0, World.HEIGHT * 16 - Game.HEIGHT);

	}
	
	public void checkCollisionItem() {
		for(int i = 0; i < Game.energy.size(); i++) {
			Entity singleEntity= Game.energy.get(i);

			if(singleEntity instanceof Consumable) {

				if(Entity.isColidding(this, singleEntity)) {
					
					if(Player.life < Player.maxLife) {
						Game.energy.remove(singleEntity); 
						Player.life +=1;
					}
					
					



					
				}
			}
		}
	}
	
	public void render(Graphics g) {
		if(right) {
			g.drawImage(playerRight[indexHorizontal],this.getX() - Camera.x,this.getY() - Camera.y,null);
		}
		else if(left) {
			g.drawImage(playerLeft[indexHorizontal],this.getX() - Camera.x,this.getY() - Camera.y,null);
		}
		else if(up) {			
			g.drawImage(playerUp[indexVertical],this.getX() - Camera.x,this.getY() - Camera.y,null);
		}
		else if(down) {
			g.drawImage(playerDown[indexVertical],this.getX() - Camera.x,this.getY() - Camera.y,null);
		}
		
		else {
			g.drawImage(playerStatic[1], this.getX() - Camera.x, this.getY() - Camera.y,null);
		}
	}
}
