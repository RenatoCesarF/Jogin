package entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.Game;
import world.Camera;

public class Bullet extends Entity{
	
	private int directionX;
	private int directionY;
	private int bulletSpeed;
	
	private BufferedImage[] bulletSprite;
	
	
	public Bullet(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		// TODO Auto-generated constructor stub
		
		bulletSprite = new BufferedImage[1];
		bulletSprite[0] = Game.particlesSprite.getSprite(16*0, 19, 16, 9);
	}
	
	public void setBulletSpeed(int amount) {
		this.bulletSpeed = amount;
	}
	
	public int getBulletSpeed() {
		return this.bulletSpeed;
	}

	public void tick() {
		x+=directionX * this.getBulletSpeed();
		y+=directionY * this.getBulletSpeed();
	}
	
	public void render(Graphics g ) {
		g.drawImage(bulletSprite[0],this.getX() - Camera.x,this.getY() - Camera.y,null);
	}
	
}
