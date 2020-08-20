package entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.Game;
import world.Camera;

public class Throwable extends Entity{
	
	private int directionX;
	private int directionY;
	private int speed = 4;
	
	private int maxXDirection = this.getX() + 100;
	private int maxYDirection = this.getY() + 100;
	
	public BufferedImage[] throwableSprite;
	
	
	public Throwable(int x, int y, int width, int height, BufferedImage sprite, int directionX ,int directionY) {
		super(x, y, width, height, sprite);
		
		this.directionX = directionX;
		this.directionY = directionY;
		
		throwableSprite = new BufferedImage[2];
		throwableSprite[0] = Game.itemsSprite.getSprite(16*7, 0, width, height);
		throwableSprite[1] = Game.itemsSprite.getSprite(16*8, 0, width, height);
		
	}
	

	public void tick() {
		if(x > maxXDirection){
			Game.player.myThrowable = -1;
			//TODO: the effects of throwing a granade (explosion)
			Game.throwables.remove(this);
			return;
		}
		else if(x < (-1*maxXDirection)){
			Game.player.myThrowable = -1;
			//TODO: the effects of throwing a granade (explosion)
			Game.throwables.remove(this);
			return;
		}
		else if(y> maxYDirection){
			Game.player.myThrowable = -1;
			//TODO: the effects of throwing a granade (explosion)
			Game.throwables.remove(this);
			return;
		}
		else if(y < (-1*maxYDirection)){
			Game.player.myThrowable = -1;
			//TODO: the effects of throwing a granade (explosion)
			Game.throwables.remove(this);
			return;
		}
		else {
			x += directionX * speed;
			y += directionY * speed;
		}
		
		
		
	}
	
	public void render(Graphics g ) {

		
		if(Game.player.myThrowable == 1) {
			g.drawImage(throwableSprite[1],this.getX() - Camera.x,this.getY() - Camera.y,null);
		}
		if(Game.player.myThrowable == 2) {
			g.drawImage(throwableSprite[0],this.getX() - Camera.x,this.getY() - Camera.y,null);
		}
	}
	
}
