 package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.lang.reflect.Array;
import java.util.ArrayList;

import graficos.Spritesheet;
import graficos.UI;
import main.Game;
import world.Camera;
import world.World;

public class Player extends Entity{
	
	private int maskX = 3, maskY = 3, maskW = 10, maskH = 10;
	
	public boolean right, up, left, down;
	public double speed = 0.9;
	
	private int frames = 0,maxFrames = 6, indexHorizontal, indexVertical;
	private int maxIndexHorizontal = 8, maxIndexVertical = 4;
	private boolean movedHorizontal = false;
	
	public boolean isDamaged = false;
	private int damagedFrames = 0;
	
	private int colidingArea = 16;
	
	public double life = 5, maxLife = 5;
	public int ammo = 0, maxAmmo = 40;
	private boolean hasItem = false;
	private int damage = 0;
	
	private BufferedImage[] playerRight;
	private BufferedImage[] playerLeft;
	private BufferedImage[] playerUp;
	private BufferedImage[] playerDown;
	private BufferedImage[] playerStatic;
	
	private BufferedImage playerDameged;
	
	public Player(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		
		playerRight = new BufferedImage[8];
		playerLeft = new BufferedImage[8];
		playerUp = new BufferedImage[4];
		playerDown = new BufferedImage[4];
		playerStatic = new BufferedImage[2];
		
		playerDameged = Game.playerSprite.getSprite(0, 16*5, 16, 16);
	
		
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
	
	public int getColidingArea() {
		return this.colidingArea;
	}
	
	// ============= Life Stufs =============== \\
	public void loseLife(int damage) {
		Game.player.life -= damage;
		Game.player.isDamaged = true;
		System.out.println("new life: " + Game.player.life);
		
		immunity();
		
		
		if(Game.player.life <= 0 ) {
			playderDied();
		}
	}
	
	public void immunity() {
		Thread immunityTime = new Thread(new Runnable() {
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
		
		immunityTime.start();

		
	}
	
	public void playderDied() {
		Game.entities.clear();
		Game.energy.clear();
		Game.ammo.clear();
		Game.entities = new ArrayList<Entity>();
		Game.energy = new ArrayList<Entity>();
		Game.ammo = new ArrayList<Entity>();
		Game.enemies = new ArrayList<Enemy>();
		
		Game.itemsSprite = new Spritesheet("/itens.png"); //itens
		Game.worldSprite = new Spritesheet("/tiles.png"); //tiles
		Game.playerSprite = new Spritesheet("/player.png"); //player
		Game.uiSprite = new Spritesheet("/UI.png");
		
		//Player               16 16
		Game.player = new Player(0,0,1,1,Game.playerSprite.getSprite(0, 0,16,16));
		Game.entities.add(Game.player);
		
		Game.world = new World("/map.png"); //inicializando a classe de desenhar mundo
		
	}

	// ============== Item Stufs ============== \\
	public void checkCollisionWithEnergy() {
		for(int i = 0; i < Game.energy.size(); i++) {
			Entity singleEntity= Game.energy.get(i);

			if(singleEntity instanceof Consumable) {

				if(Entity.isColidding(this, singleEntity)) {
					
					if(Game.player.life < Game.player.maxLife) {
						Game.energy.remove(singleEntity); 
						Game.player.life +=1;
					}
				}
			}
		}
	}
	
	public void checkCollisionWithAmmo() {
		for(int i = 0; i < Game.ammo.size(); i++) {
			Entity singleEntity= Game.ammo.get(i);

			if(singleEntity instanceof Consumable) {

				if(Entity.isColidding(this, singleEntity)) {
					
					if(Game.player.ammo < Game.player.maxAmmo) {
						Game.ammo.remove(singleEntity); 
						this.addAmmmo(10);//We can change how manny ammo you get with a future variable
					}
				}
			}
		}
	}
	
	// ============= Weapon Stufs ============= \\
	public void checkCollisionWithWeapons() {
		for(int i = 0; i < Game.gun.size(); i++) {
			Entity singleEntity= Game.gun.get(i);

			if(singleEntity instanceof Consumable) {

				if(Entity.isColidding(this, singleEntity)) {
					
					int itemIndex =  Game.gun.indexOf(singleEntity);
					int getThisWeapon = Game.weaponArray.get(itemIndex);
					
					
					Game.gun.remove(singleEntity);
					Game.weaponArray.remove(itemIndex);
					
					System.out.println(Game.weaponArray);
					
					Game.player.getGun(getThisWeapon);
				}
			}
		}
	}
	
	public void getGun(int weaponIndex) {
		
		switch(weaponIndex) {
			case 0:{ 
				setDamage(1);
				System.out.println("Equiped the little gun");
				break;
			}
				
			case 1:{
				setDamage(5);
				System.out.println("Equiped the shotgun");
				break;
			}
			
			case 2: {
				setDamage(10);
				System.out.println("Equiped the biggest gun");
				break;
			}
			
			case 3: {
				setDamage(10);
				System.out.println("Equiped the book of water");
				break;
			}	
			
			case 4:{
				setDamage(13);
				System.out.println("Equiped the book of fire");
				break;
			}
			
			case 5: {
				setDamage(13);
				System.out.println("Equiped the Katana");
				break;
			}
			
			case 6: {
				setDamage(13);
				System.out.println("Equiped the sword");
				break;
			}
			
			case 7: {
				setDamage(20);
				System.out.println("Get a grenade");
				break;
			}
		}
		
		
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

	public void addAmmmo(int amount) {
		this.ammo += amount;
	}
	
	
	// ============= Frame Stufs ============== \\
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
		this.checkCollisionWithEnergy();
		this.checkCollisionWithAmmo();
		this.checkCollisionWithWeapons();
		
		//Controll the animation of get damage
		if(isDamaged) {
			Game.player.damagedFrames++;
			if(Game.player.damagedFrames == 10) {
				Game.player.damagedFrames = 0;
				isDamaged = false;
			}
		}
		
		//Camera Follow the player
		Camera.x =  Camera.clamp(this.getX() - (Game.WIDTH/2), 0, World.WIDTH * 16 - Game.WIDTH);
		Camera.y =  Camera.clamp(this.getY() - (Game.HEIGHT/2), 0, World.HEIGHT * 16 - Game.HEIGHT);

		

	}
	
	public void render(Graphics g) {
		if(!isDamaged) {
			
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
			
			else{
				g.drawImage(playerStatic[1], this.getX() - Camera.x, this.getY() - Camera.y,null);
			}
		}
		else {
			g.drawImage(playerDameged, this.getX() - Camera.x, this.getY() - Camera.y, null);
		}

		
		
	}
}
