package graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import entities.Player;
import main.Game;
import main.Sound;
import world.Camera;

public class UI {
	private BufferedImage[] energySymbol;
	private BufferedImage[] ammoSymbol;
	private BufferedImage[] weaponSymbol;
	private BufferedImage[] shieldSymbol;

	private int animationIndex = 0, animationSpeed = 0;
	public int scale = 4;

	public UI() {
		energySymbol = new BufferedImage[4];
		weaponSymbol = new BufferedImage[8];
		ammoSymbol = new BufferedImage[1];
		shieldSymbol = new BufferedImage[1];
	
		
		//Ammo UI sprites
		ammoSymbol[0] = Game.uiSprite.getSprite(16*0, 16*3, 16, 16);
		
		//Shield UI Symbol
		shieldSymbol[0] = Game.uiSprite.getSprite(16*1, 16*4, 16, 16);
		
		//Energy UI sprites
		for(int i = 0; i < energySymbol.length;i ++) {
			energySymbol[i] = Game.uiSprite.getSprite(16*i, 0, 16, 16);
		}
		
		//Weapons UI sprites
		for(int i = 0; i < weaponSymbol.length; i++) { //frame,start, largura, algura 
			weaponSymbol[i] = Game.uiSprite.getSprite(16*i, 16*2, 16, 16);
		}
	}
	
	public void symbolsRender(Graphics g ) {
		//Energy Symbol
		
		g.drawImage(energySymbol[animationIndex], 0, 4,null);

		animationSpeed ++;
		if(animationSpeed > 20) {
			animationIndex ++;		
			animationSpeed = 0;
		}
		
		if(animationIndex >= 4) {
			animationIndex = 0;
		}
		
		//Ammo symbol
		g.drawImage(ammoSymbol[0], 0, 19,null);
		
		
		//Shield symbol
		int i = 0 ;
		//int horizontalShield = 10;
		while(i< Game.player.shield) {
			g.drawImage(shieldSymbol[0],60+(11*i), 4,null);
			i++;
		}

	

	}
	
	public void weaponRender(Graphics g) {
		int weaponIndex = Game.player.getPlayerWeapon();
		
		switch(weaponIndex) {
		
		case 0:
			g.drawImage(weaponSymbol[weaponIndex], 0, 33,null);
			break;
		case 1:
			g.drawImage(weaponSymbol[weaponIndex], 3, 33,null);
			break;
		case 2:
			g.drawImage(weaponSymbol[weaponIndex], 3, 33,null);
			break;
		case 3:
			g.drawImage(weaponSymbol[weaponIndex], 0, 33,null);
			break;
		case 4:
			g.drawImage(weaponSymbol[weaponIndex], 0, 33,null);
			break;
		case 5:
			g.drawImage(weaponSymbol[weaponIndex], 0, 35,null);
			break;
		case 6:
			g.drawImage(weaponSymbol[weaponIndex], 3, 35,null);
			break;
		case 7:
			g.drawImage(weaponSymbol[weaponIndex], 0, 33,null);
			break;
		}
			
	}
	
	//This render function is to draw strings and HD things, the aboves ones is to draw pixeled things
	public void render(Graphics g) {
		//============= Player Life ==========\\
		//BackGround color
		g.setColor(new Color(139,155,180));
		g.fillRect(13*scale, 8*scale, ((int)(Game.player.maxLife))+45*scale, 8*scale);
		
		//Life color
		g.setColor(new Color(254, 174, 52));
		g.fillRect(13*scale,8*scale, (int)((Game.player.life/Game.player.maxLife)*50*scale), 8*scale);
		
		//String of life
		g.setColor(new Color(58,68,102));
		g.setFont(new Font("arial",Font.BOLD,8*scale));
		g.drawString((int)Game.player.life+"/"+(int)Game.player.maxLife, 34*scale, 15*scale);
		
		
		//=============Mana Stufs ============\\
		//BackGround color
		g.setColor(new Color(139,155,180));
		g.fillRect(180*scale, 8*scale, ((int)(Game.player.maxMana))*4*scale, 8*scale);
		
		//Mana color
		g.setColor(new Color(0,153,219));
		g.fillRect(180*scale, 8*scale, ((int)(Game.player.mana))*4*scale, 8*scale);
		
		//String of Mana
		g.setColor(new Color(58,68,102));
		g.setFont(new Font("arial",Font.BOLD,8*scale));
		g.drawString((int)Game.player.mana+"/"+(int)Game.player.maxMana, 194*scale, 15*scale);
		
		
		//============== AMMO STUFS ==========\\
		//Drawing the Ammo stufs
		g.setFont(new Font("arial", Font.BOLD,7*5));
		g.setColor(new Color(247,118,34));
		g.drawString(Game.player.ammo+"/"+ Game.player.maxAmmo,63, 118);
		

		
		//============== Weapon Mensage Stufs ==========\\
		int playerXPostion = (Game.player.getX() - Camera.x)* scale;
		int playerYPosition = (Game.player.getY() - Camera.y)* scale;
		
		if(Game.player.getAboveWeapon()) {
			Sound.missingSignal.play();
			
			//TODO: particles system to this div
			g.setColor(new Color(254, 174, 52));
			g.fillRoundRect( playerXPostion  - 45,playerYPosition - 40, 40*scale, 10*scale,20,20);
			
			g.setFont(new Font("arial", Font.BOLD,5*scale));
			g.setColor(new Color(24,20,37));
			g.drawString("Press E to pick",playerXPostion -36, playerYPosition - 14);
		}
		
	
	
		
	}




}
