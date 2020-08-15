package graficos;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import entities.Player;
import main.Game;

public class UI {
	private BufferedImage[] energySymblo;
	private BufferedImage[] ammoSymblo;

	private int animationIndex = 0, animationSpeed = 0;
	public int scale = 4;

	
	public void render(Graphics g) {
		/*============= Player Life ==========*/
		//BackGound color
		g.setColor(new Color(139,155,180));
		g.fillRect(13*scale, 8*scale, ((int)(Player.maxLife))+45*scale, 8*scale);
		
		//Life color
		g.setColor(new Color(254, 174, 52));
		g.fillRect(13*scale,8*scale, (int)((Player.life/Player.maxLife)*50*scale), 8*scale);
		
		//String of lifes
		g.setColor(new Color(58,68,102));
		g.setFont(new Font("arial",Font.BOLD,8*scale));
		g.drawString((int)Player.life+"/"+(int)Player.maxLife, 34*scale, 15*scale);
		
		
		
		//============== AMMO STUFS ==========
		//Drawing the Ammo stufs
		g.setFont(new Font("arial", Font.BOLD,7*5));
		g.setColor(new Color(247,118,34));
		g.drawString(Game.player.ammo+"/"+ Game.player.maxAmmo,60, 120);
		
		
	}
	
	public void symblosRender(Graphics g ) {
		//Energy Symble
		energySymblo = new BufferedImage[4];
		
		for(int i = 0; i < energySymblo.length;i ++) {
			energySymblo[i] = Game.uiSprite.getSprite(0+(16*i), 0, 16, 16);
		}
		
		g.drawImage(energySymblo[animationIndex], 0, 4,null);

		animationSpeed ++;
		if(animationSpeed > 20) {
			animationIndex ++;		
			animationSpeed = 0;
		}
		
		if(animationIndex >=4) {
			animationIndex = 0;
		}
		
		
		//Ammo symnblo
		ammoSymblo = new BufferedImage[1];
		
		ammoSymblo[0] = Game.uiSprite.getSprite(0+(16*0), 16*3, 16, 16);
		
		g.drawImage(ammoSymblo[0], 0, 19,null);
		
	
	}
}
