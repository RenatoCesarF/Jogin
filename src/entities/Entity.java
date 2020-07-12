package entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.Game;
import world.Camera;


public class Entity {
	

	protected double x;
	protected double y;
	protected int width;
	protected int height;
	
	private BufferedImage sprite;
	
	//Itens
	public static BufferedImage lifePack_en = Game.itemsSprite.getSprite(0, 16, 16, 16);
	public static BufferedImage ammu_en = Game.itemsSprite.getSprite(16, 16, 16, 16);
	public static BufferedImage energy_en = Game.itemsSprite.getSprite(32, 16, 16, 16);
	public static BufferedImage shild_en= Game.itemsSprite.getSprite(48, 16, 16, 16);
	public static BufferedImage mana_en = Game.itemsSprite.getSprite(64, 16, 16, 16);
	

	public Entity(int x, int y, int width, int height, BufferedImage sprite) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.sprite = sprite;
	}
	

	public void setX(int newX) {
		this.x = newX;
	}
	public void setY(int newY) {
		this.y = newY;
	}
	
	
	public int getX() {
		return (int)this.x;
	}
	public int getY() {
		return (int)this.y;
	}
	public int getWidth() {
		return this.width;
	}
	public int getHeight() {
		return this.height;
	}
	public void tick() {
		
	}
	public void render(Graphics g) {
		g.drawImage(sprite,this.getX() - Camera.x, this.getY() - Camera.y , null);
	}

}
