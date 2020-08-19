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
	
	public static final Sound missingSignal = new Sound("/h.wav");
	
	
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
