package world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import entities.Enemy;
import entities.Weapon;
import main.Game;


public class World {
	
	public static Tile[] tiles;
	public static int WIDTH,HEIGHT;
	public static final int TILE_SIZE = 16;
	
	
	
	public World(String path){
		try {
			BufferedImage map = ImageIO.read(getClass().getResource(path));
			int[] pixels =  new int[map.getWidth() * map.getHeight()];
			WIDTH = map.getWidth();
			HEIGHT = map.getHeight();
			tiles = new Tile[map.getWidth() * map.getHeight()];
			map.getRGB(0, 0, map.getTileWidth(),map.getHeight(),  pixels, 0, map.getTileWidth());
	
			for( int xx = 0; xx< map.getWidth(); xx++){
				for(int yy = 0; yy< map.getHeight(); yy++) {
					int pixelAtual = pixels[xx +(yy * map.getWidth())];
					
					
					tiles[xx + (yy * WIDTH)] = new FloorTile(xx*16, yy*16, FloorTile.randomFloor());
					
					if(pixelAtual == 0xFF000000) {
						//floor
						tiles[xx + (yy * WIDTH)] = new FloorTile(xx*16, yy*16, FloorTile.randomFloor());
	 
					}
					else if (pixelAtual == 0xFFFFFFFF){
						//wall
						tiles[xx + (yy * WIDTH)] = new WallTile(xx*16, yy*16, WallTile.randomWall());
					}
					else if(pixelAtual == 0XFF0008ff) {
						//player
						Game.player.setX(xx*16);
						Game.player.setY(yy*16);
					}
					else if(pixelAtual == 0xFFff00ea) {
						//Items
						Game.entities.add(new Weapon(xx*16, yy* 16, 16,16, Weapon.randomItem()));
					}
					else if(pixelAtual == 0xFF00ff05){
						//Weapons
						Game.entities.add(new Weapon(xx*16, yy*16, 16,16, Weapon.randomWeapon()));
					}
					else if(pixelAtual == 0XFFFF0000) {
						//Enemy
						Enemy en = new Enemy(xx*16, yy*16,16,16, Enemy.robot);
						Game.entities.add(en);
						Game.enemies.add(en);
					}
				}
				
			}
			
			
		}catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static boolean isFree(double d, int yNext) {
		double x1 = d / TILE_SIZE;
		double y1 = yNext / TILE_SIZE ;
		
		double x2 = (d + TILE_SIZE-1) / TILE_SIZE;
		double y2 = yNext / TILE_SIZE;
		
		double x3 = d / TILE_SIZE;
		double y3 = (yNext + TILE_SIZE-1) / TILE_SIZE;
		
		double x4 = (d + TILE_SIZE-1) / TILE_SIZE;
		double y4 = (yNext + TILE_SIZE-1) / TILE_SIZE;
		
		return !((tiles[(int) (x1 + (y1 * World.WIDTH))] instanceof WallTile)||
				(tiles[(int) (x2 + (y2 * World.WIDTH))] instanceof WallTile) ||
				(tiles[(int) (x3 + (y3 * World.WIDTH))] instanceof WallTile) ||
				(tiles[(int) (x4 + (y4 * World.WIDTH))] instanceof WallTile));
	}
	public void render(Graphics g) {
		int xstart = Camera.x >> 4;
		int ystart = Camera.y >> 4;
		
		int xfinal = xstart + (Game.WIDTH >> 4);
		int yfinal = ystart + (Game.HEIGHT >> 4);
		
		
		for(int xx = xstart; xx <= xfinal ; xx++) {
			for(int yy = ystart; yy <= yfinal; yy++) {
				if( xx < 0|| xx >= WIDTH || yy < 0 || yy >= HEIGHT) {
					continue;
				}
				Tile tile = tiles[xx + (yy * WIDTH)];
				tile.render(g);
			}
		}

	}
}
