package world;

import java.awt.image.BufferedImage;

import main.Game;

public class WallTile extends Tile {
	
	public static BufferedImage TileWall = Game.worldSprite.getSprite(0, 16, 16, 16);
	
	public WallTile(int x, int y, BufferedImage sprite) {
		super(x, y, sprite);
		
	}

}
