package graficos;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import entities.Player;
import main.Game;

public class UI {
	private BufferedImage[] energySymble;

	private int animationIndex = 0, animationSpeed = 0;
	

	
	public void render(Graphics g) {
		/*============= Player Life ==========*/
		//BackGound color
		g.setColor(new Color(139,155,180));
		g.fillRect(13, 8, 50, 8);
		
		//Life color
		g.setColor(new Color(254, 174, 52));
		g.fillRect(13,8, (int)((Player.life/Player.maxLife)*50), 8);
		
		//String of lifes
		g.setColor(new Color(58,68,102));
		g.setFont(new Font("arial",Font.BOLD,8));
		g.drawString((int)Player.life+"/"+(int)Player.maxLife, 28, 15);
		
		//Energy Symble
		energySymble = new BufferedImage[4];
		
		for(int i = 0; i < energySymble.length;i ++) {
			energySymble[i] = Game.uiSprite.getSprite(0+(16*i), 0, 16, 16);

		}

		g.drawImage(energySymble[animationIndex], 0, 4,null);

		animationSpeed ++;
		if(animationSpeed > 20) {
			animationIndex ++;		
			animationSpeed = 0;
		}
		
		if(animationIndex >=4) {
			animationIndex = 0;
		}
		
	}
}
