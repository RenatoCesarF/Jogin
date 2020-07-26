package world;

import java.awt.image.BufferedImage;
import java.util.Random;

import main.Game;

public class WallTile extends Tile {
	
	public static BufferedImage randomWall() {
		Random random = new Random();
		int randomWall = random.nextInt(100);
		
		if(randomWall > 80 && randomWall < 100) return TileWall1;//havean
		if(randomWall > 40 && randomWall < 60) return TileWall3; //Tree
		if(randomWall == 80) return TileWall2; //Hole
		
		else return TileWall0; //wood
	}
	
	public static BufferedImage TileWall0 = Game.worldSprite.getSprite(0*16, 16, 16, 16);
	public static BufferedImage TileWall1 = Game.worldSprite.getSprite(1*16, 16, 16, 16);
	public static BufferedImage TileWall2 = Game.worldSprite.getSprite(2*16, 16, 16, 16);
	public static BufferedImage TileWall3 = Game.worldSprite.getSprite(3*16, 16, 16, 16);
	
	public WallTile(int x, int y, BufferedImage sprite) {
		super(x, y, sprite);
		
	}

}
