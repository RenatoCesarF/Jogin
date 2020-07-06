package world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import entities.Enemy;
import entities.Entity;
import entities.Weapon;
import main.Game;

public class World {
	
	private Tile[] tiles;
	public static int WIDTH,HEIGHT;

	
	
	
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
					
					Random randomFloor = new Random();
					int floorIndex = randomFloor.nextInt(39);
					tiles[xx + (yy * WIDTH)] = new FloorTile(xx*16, yy*16, FloorTile.floors[floorIndex]);
					
					if(pixelAtual == 0xFF000000) {
						//floor
						tiles[xx + (yy * WIDTH)] = new FloorTile(xx*16, yy*16, FloorTile.floors[floorIndex]);
	 
					}
					else if (pixelAtual == 0xFFFFFFFF){
						//wall
						tiles[xx + (yy * WIDTH)] = new WallTile(xx*16, yy*16, WallTile.TileWall );
					}
					else if(pixelAtual == 0XFF0008ff) {
						//player
						Game.player.setX(xx*16);
						Game.player.setY(yy*16);
					}
					else if(pixelAtual == 0xFFff00ea) {
						//Items
						Game.entities.add(new Enemy(xx*16, yy* 16, 16,16, Weapon.armor3_en));
					}
					else if(pixelAtual == 0xFF00ff05){
						//Weapons
						//escolher aleatóriamente:
						Random randomItem = new Random();
						int itemIndex = randomItem.nextInt(6);
						Game.entities.add(new Weapon(xx*16, yy*16, 16,16, Weapon.weapons[itemIndex]));
					} 
				}
				
			}
			
			
		}catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void render(Graphics g) {
		for(int xx = 0; xx < WIDTH; xx++) {
			for(int yy = 0; yy < HEIGHT; yy++) {
				Tile tile = tiles[xx+ (yy * WIDTH)];
				tile.render(g);
			}
		}
	}
}
