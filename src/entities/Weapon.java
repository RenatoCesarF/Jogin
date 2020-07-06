package entities;

import java.awt.image.BufferedImage;

import main.Game;

public class Weapon extends Entity{
	//Wapons
	public static BufferedImage armor1_en = Game.itemsSprite.getSprite(16*0, 0, 16, 16);
	public static BufferedImage armor2_en = Game.itemsSprite.getSprite(16*1, 0, 16, 16);
	public static BufferedImage armor3_en = Game.itemsSprite.getSprite(16*2, 0, 16, 16);
	public static BufferedImage mage1_en = Game.itemsSprite.getSprite(16*3, 0, 16, 16);
	public static BufferedImage mage2_en = Game.itemsSprite.getSprite(16*4, 0, 16, 16);
	public static BufferedImage sword_en = Game.itemsSprite.getSprite(16*5 , 0, 16, 16);
	public static BufferedImage granade_en = Game.itemsSprite.getSprite(16*5 , 0, 16, 16);
	
	//colocando todas as armas em um array para seleciona-las aleatóriamente
	public static BufferedImage[] weapons = {armor1_en,armor2_en,armor3_en,mage1_en,mage2_en,sword_en,granade_en};
	
	public Weapon(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
	}

}
