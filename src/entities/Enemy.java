package entities;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import main.Game;
import world.Camera;
import world.World;

public class Enemy extends Entity{

	private double speed = 0.5;
	
	private int maskX = 8, maskY = 8, maskW = 8, maskH = 8;
	
	private int frames = 0,maxFrames = 4, index = 0, maxIndex = 3;
	
	public static BufferedImage robot = Game.playerSprite.getSprite(0*16, 16*5, 16, 16);
	
	private BufferedImage[] sprites;
	
	
	public Enemy(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, null);
		
		sprites = new BufferedImage[4];
		
		sprites[0] = Game.playerSprite.getSprite(0*16, 16*5, 16, 16);
		sprites[1] = Game.playerSprite.getSprite(1*16, 16*5, 16, 16);
		sprites[2] = Game.playerSprite.getSprite(2*16, 16*5, 16, 16);
		sprites[3] = Game.playerSprite.getSprite(3*16, 16*5, 16, 16);
		
	}
	
	public void tick() {
			
		if(x <= Game.player.getX() 
			&& World.isFree((int)x+speed, this.getY())
			&& !isColiding((int)(x+speed), this.getY())
			) {
	
			x+=speed;
		}
		
		else if(x >= Game.player.getX() 
				&& World.isFree((int)x-speed, this.getY())
				&& !isColiding((int)(x-speed), this.getY())
				) {
			x-=speed;
		}
		
		if(y <= Game.player.getY()
				&& World.isFree( this.getX(), (int)(y+speed))
				&& !isColiding(this.getX(), (int)(y+speed))
				) {
			y+=speed;
		}
		else if(y >= Game.player.getY()
				&& World.isFree( this.getX(), (int)(y-speed))
				&& !isColiding(this.getX(), (int)(y-speed))
				) {
			y-=speed;
		}
		

		frames ++;
		if(frames == maxFrames) {
			frames = 0;
			index++;
			if(index > maxIndex){
				index = 0;
			}
		}
	
		
	}
	
	public boolean isColiding(int xnext, int ynext) {
		
		Rectangle enemyCurrent= new Rectangle(xnext + maskX, ynext + maskY, maskW, maskH);
		
		for(int i = 0; i< Game.enemies.size(); i++) {
			Enemy e = Game.enemies.get(i);
			
			if(e == this) {
				continue;
			}
			
			Rectangle targetEnemy = new Rectangle(e.getX() + maskX, e.getY() + maskY, maskW, maskH);

			if(enemyCurrent.intersects(targetEnemy)) return true;
			
		}
		return false;
	}

	public void render(Graphics g) {
		g.drawImage(sprites[index], this.getX() - Camera.x ,this.getY() - Camera.y,null);
		
		/*test of the colision
		g.setColor(Color.blue);
		g.fillRect(this.getX() + maskX - Camera.x, this.getY() + maskY - Camera.y, maskW, maskH);
		 */
	}
}
