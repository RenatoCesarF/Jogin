package entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.Game;

public class Player extends Entity{

	public boolean right, up, left, down;
	public double speed = 0.9;
	
	private int frames = 0,maxFrames = 7, indexHorizontal, indexVertical;
	private int maxIndexHorizontal = 8, maxIndexVertical = 4;
	private boolean movedHorizontal = false;
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
		for(int i = 0; i < 2; i++) {			
			playerStatic[i] = Game.spritesheet.getSprite(0+(i*16), 0, 16, 16);
		}
		//sprite player moving for right
		for(int i = 0; i < 8; i++) {			
			playerRight[i] = Game.spritesheet.getSprite(0+(i*16), 48, 16, 16);
		}
		//sprite player moving for left
		for(int i = 0; i <8; i++) {
			playerLeft[i] = Game.spritesheet.getSprite(0+(i*16), 64, 16, 16);
		}
		//sprite player moving for up
		for(int i = 0; i < 4; i++) {
			playerUp[i] = Game.spritesheet.getSprite(0+(i*16),32, 16, 16);
		}
		//sprite player moving down
		for(int i = 0; i < 4; i++) {
			playerDown[i] = Game.spritesheet.getSprite(0+(i*16), 16, 16, 16);
		}
	}
	
	public void tick(){
		movedHorizontal = false;
		if(up) {
			y -= speed;
		}
		else if(down){
			y += speed;
		}
		if(right) {
			movedHorizontal = true;
			x += speed;
		}
		else if(left){
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
	}
	public void render(Graphics g) {
		if(right) {
			g.drawImage(playerRight[indexHorizontal],this.getX(),this.getY(),null);
		}
		else if(left) {
			g.drawImage(playerLeft[indexHorizontal],this.getX(),this.getY(),null);
		}
		else if(up) {			
			g.drawImage(playerUp[indexVertical],this.getX(),this.getY(),null);
		}
		else if(down) {
			g.drawImage(playerDown[indexVertical],this.getX(),this.getY(),null);
		}
		
		else {
			g.drawImage(playerStatic[1], this.getX(), this.getY(), null);
		}
	}
}
