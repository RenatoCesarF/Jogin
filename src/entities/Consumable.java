package entities;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import main.Game;

public class Consumable extends Entity{
	 
	//Random Weapons
	public static BufferedImage randomWeapon(int weaponIndex) {

		
		if(weaponIndex == 0) {
			return armor1;
		}
		if(weaponIndex == 1) {
			return armor2;
		}
		if(weaponIndex == 2) {
			return armor3;
		}
		if(weaponIndex == 3) {
			return mage1;
		}
		if(weaponIndex == 4) {
			return mage2;
		}
		if(weaponIndex == 5) {
			return katana;
		}
		if(weaponIndex == 6) {
			return sword;
		}
		if(weaponIndex == 7) {
			return granade;
		}
		
		else return armor1;
	
	}

	
	public static BufferedImage armor1 = Game.itemsSprite.getSprite(16*0, 0, 16, 16);
	public static BufferedImage armor2 = Game.itemsSprite.getSprite(16*1, 0, 16, 16);
	public static BufferedImage armor3 = Game.itemsSprite.getSprite(16*2, 0, 16, 16);
	public static BufferedImage mage1 = Game.itemsSprite.getSprite(16*3, 0, 16, 16);
	public static BufferedImage mage2 = Game.itemsSprite.getSprite(16*4, 0, 16, 16);
	public static BufferedImage katana = Game.itemsSprite.getSprite(16*5 , 0, 16, 16);
	public static BufferedImage sword = Game.itemsSprite.getSprite(16*6 , 0, 16, 16);
	public static BufferedImage granade = Game.itemsSprite.getSprite(16*7 , 0, 16, 16);
	

	
	//=============== Random Itens =============
	
	//Loading to the game the sprites of itens
	public static BufferedImage medKit = Game.itemsSprite.getSprite(16*0, 16, 16, 16);
	public static BufferedImage ammo = Game.itemsSprite.getSprite(16*1, 16, 16, 16);
	public static BufferedImage energy_C = Game.itemsSprite.getSprite(16*2, 16, 16, 16);
	public static BufferedImage shild = Game.itemsSprite.getSprite(16*3, 16, 16, 16);
	public static BufferedImage manapot = Game.itemsSprite.getSprite(16*4, 16, 16, 16);
	


	public static BufferedImage randomItem(int itemIndex) {
		if(itemIndex == 0) return medKit;
		if(itemIndex == 1) return ammo;
		if(itemIndex == 2) return shild;
		if(itemIndex == 3) return manapot;
		if(itemIndex == 4) return energy_C;
		
		else return medKit;
	}

	public Consumable(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
	}
	

}
