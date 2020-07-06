package world;

import java.awt.image.BufferedImage;

import main.Game;

public class FloorTile extends Tile {
	public FloorTile(int x, int y, BufferedImage sprite) {
		super(x, y, sprite);
		
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
	
	//juntar tudo em um array:
	public static BufferedImage [] floors = {
			TileFloor0,TileFloor1,TileFloor2,
			TileFloor0,TileFloor1,TileFloor2,
			TileFloor0,TileFloor1,TileFloor2,
			TileFloor0,TileFloor1,TileFloor2,
			TileFloor0,TileFloor1,TileFloor2,
			TileFloor0,TileFloor1,TileFloor2,
			TileFloor0,TileFloor1,TileFloor2,
			TileFloor0,TileFloor1,TileFloor2,
			TileFloor0,TileFloor1,TileFloor2,
			TileFloor0,TileFloor1,TileFloor2,
			TileFloor3,TileFloor4,TileFloor5,
			TileFloor6,TileFloor7,TileFloor8,
			TileFloor9,TileFloor10,TileFloor11
			};
}
