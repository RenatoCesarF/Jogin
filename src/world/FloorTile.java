package world;

import java.awt.image.BufferedImage;
import java.util.Random;

import main.Game;

public class FloorTile extends Tile {
	public FloorTile(int x, int y, BufferedImage sprite) {
		super(x, y, sprite);
		
	}
	
	public static BufferedImage randomFloor() {
		Random randomFloor = new Random();
		int floorIndex = randomFloor.nextInt(1000);
		
		//Normal Dirty 
		if(floorIndex > 0 && floorIndex < 200 ) return TileFloor1;
		if(floorIndex > 200 && floorIndex < 400) return TileFloor2;
		if(floorIndex < 400 && floorIndex > 600) return TileFloor3;
		if(floorIndex < 600 && floorIndex > 800) return TileFloor4;
		
		
		if(floorIndex > 800 && floorIndex < 810) return TileFloor5; //little grass
		if(floorIndex > 810 && floorIndex < 820) return TileFloor6; //crack on the floor
		if(floorIndex > 820 && floorIndex < 830) return TileFloor7; //hole in the floor
		if(floorIndex > 830 && floorIndex < 840) return TileFloor8; //crack with plants
		if(floorIndex > 840 && floorIndex < 850) return TileFloor9; //little knif
		if(floorIndex > 850 && floorIndex < 860) return TileFloor10; //bullets
		if(floorIndex > 860 && floorIndex < 870) return TileFloor11; //skul
		if(floorIndex > 870 && floorIndex < 880) return TileFloor12; //2 rocks	
		if(floorIndex > 880 && floorIndex < 890) return TileFloor13; //crack with a lag
		if(floorIndex == 900) return TileFloor14; //shild stuck undergrond
	
		else return TileFloor0;

		
	}
	//Tiles dos chãos diferentes
	public static BufferedImage TileFloor0 = Game.worldSprite.getSprite(16*0, 0, 16, 16);
	public static BufferedImage TileFloor1 = Game.worldSprite.getSprite(16*1, 0, 16, 16);
	public static BufferedImage TileFloor2 = Game.worldSprite.getSprite(16*2, 0, 16, 16);
	public static BufferedImage TileFloor3 = Game.worldSprite.getSprite(16*3, 0, 16, 16);
	public static BufferedImage TileFloor4 = Game.worldSprite.getSprite(16*4, 0, 16, 16);
	public static BufferedImage TileFloor5 = Game.worldSprite.getSprite(16*5, 0, 16, 16);
	public static BufferedImage TileFloor6 = Game.worldSprite.getSprite(16*6, 0, 16, 16);
	public static BufferedImage TileFloor7 = Game.worldSprite.getSprite(16*7, 0, 16, 16);
	public static BufferedImage TileFloor8 = Game.worldSprite.getSprite(16*8, 0, 16, 16);
	public static BufferedImage TileFloor9 = Game.worldSprite.getSprite(16*9, 0, 16, 16);
	public static BufferedImage TileFloor10 = Game.worldSprite.getSprite(16*10, 0, 16, 16);
	public static BufferedImage TileFloor11 = Game.worldSprite.getSprite(16*11, 0, 16, 16);
	public static BufferedImage TileFloor12 = Game.worldSprite.getSprite(16*12, 0, 16, 16);
	public static BufferedImage TileFloor13 = Game.worldSprite.getSprite(16*13, 0, 16, 16);
	public static BufferedImage TileFloor14 = Game.worldSprite.getSprite(16*14, 0, 16, 16);
	
}
