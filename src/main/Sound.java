package main;

import java.applet.Applet;
import java.applet.AudioClip;

//

@SuppressWarnings("deprecation")
public class Sound {
	
	/*
	Clip clip;
	
	public void setFile(String soundFileName) {
		try{
			File file = new File(soundFileName);
			AudioInputStream sound = AudioSystem.getAudioInputStream(file);
			clip = AudioSystem.getClip();
			clip.open(sound);
		}catch(Exception e){}
	}
	
	public void play() {
		clip.setFramePosition(0);
		clip.start();
	}
	*/
	
	private AudioClip clip;
	
	//Collecting sounds
	public static final Sound missingSignal = new Sound("/soundEffects/missingSignal.wav");
	public static final Sound getBag = new Sound("/soundEffects/getBag.wav");
	public static final Sound swallow = new Sound("/soundEffects/swallow.wav");
	public static final Sound getShield = new Sound("/soundEffects/getShield.wav");
	public static final Sound getAmmo = new Sound("/soundEffects/getAmmo.wav");
	
	//Get Weapons
	public static final Sound getShotgun = new Sound("/soundEffects/getShotgun.wav");
	public static final Sound getGun= new Sound("/soundEffects/getGun.wav");
	public static final Sound getBook= new Sound("/soundEffects/getBook.wav");
	public static final Sound getSword= new Sound("/soundEffects/getSword.wav");
	public static final Sound getKatana= new Sound("/soundEffects/getKatana.wav");
	public static final Sound getMedKit= new Sound("/soundEffects/getMedKit.wav");
	
	//Weapons been usede
	public static final Sound katanaSwing = new Sound("/soundEffects/katanaSwing.wav");
	
	
	public static final Sound lostShield = new Sound("/soundEffects/lostShield.wav");
	public static final Sound noAmmo = new Sound("/soundEffects/noAmmo.wav");

	private Sound(String name) {
		try {
			clip = Applet.newAudioClip(Sound.class.getResource(name));
		}catch(Throwable e) {}
	}
	
	public void play() {
		try {
			new Thread() {
				public void run() {
					clip.play();
				}
			}.start();
		}catch(Throwable e) {}
	}
	
	public void loop() {
		try {
			new Thread() {
				public void run() {
					clip.loop();
				}
			}.start();
		}catch(Throwable e) {}
	}

}
