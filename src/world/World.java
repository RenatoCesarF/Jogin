package world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import entities.Enemy;
import entities.Consumable;
import main.Game;


public class World {
	
	public static Tile[] tiles;
	public static int WIDTH,HEIGHT;
	public static final int TILE_SIZE = 16;
	
	public int itemIndex;
	
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

					switch(pixelAtual){
					
					case 0xFF000000:{ //Floor
						tiles[xx + (yy * WIDTH)] = new FloorTile(xx*16, yy*16, FloorTile.randomFloor());
						break;
					}
	
					case 0xFFFFFFFF:{//Wall
						
						tiles[xx + (yy * WIDTH)] = new WallTile(xx*16, yy*16, WallTile.randomWall());
						break;
					}
					
					
					case 0xFFff00ea: {//Items
						Random randomItem = new Random();
						int itemIndex = randomItem.nextInt(5);
						
						Consumable items = new Consumable(xx*16, yy* 16, 16,16, Consumable.randomItem(itemIndex));
						//Items.setMask(3, 3, 10, 10);

						Game.itemArray.add(itemIndex);						
						Game.item.add(items);							
						
						break;
					}
				
					case 0xFF00ff05:{//Weapons
						
						Random randomWeapon = new Random();
						int weaponIndex = randomWeapon.nextInt(9);
						
						Consumable Weapons = new Consumable(xx*16, yy*16, 16,16, Consumable.randomWeapon(weaponIndex));
						Weapons.setMask(3, 3, 13, 13);
						
						Game.weaponArray.add(weaponIndex);
						Game.weapon.add(Weapons);
						break;
					}
				
					case 0XFF0008ff: {//player
						
						Game.player.setX(xx*16);
						Game.player.setY(yy*16);
						break;
					}
					
					case 0XFFFF0000: {//Enemy
						Enemy en = new Enemy(xx*16, yy*16,16,16, Enemy.robot);
						Game.entities.add(en);
						Game.enemies.add(en);
						break;
					}
					
					} //End of switch case
				}// End of for
				
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
