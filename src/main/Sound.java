package main;

import java.io.IOException;
import java.io.InputStream;
import javax.sound.sampled.*;

public class Sound {
  private Clip clip;

  // Sons
  public static final Sound missingSignal = new Sound("/soundEffects/missingSignal.wav");
  public static final Sound getBag = new Sound("/soundEffects/getBag.wav");
  public static final Sound swallow = new Sound("/soundEffects/swallow.wav");
  public static final Sound getShield = new Sound("/soundEffects/getShield.wav");
  public static final Sound getAmmo = new Sound("/soundEffects/getAmmo.wav");

  public static final Sound getShotgun = new Sound("/soundEffects/getShotgun.wav");
  public static final Sound getGun = new Sound("/soundEffects/getGun.wav");
  public static final Sound getBook = new Sound("/soundEffects/getBook.wav");
  public static final Sound getSword = new Sound("/soundEffects/getSword.wav");
  public static final Sound getKatana = new Sound("/soundEffects/getKatana.wav");
  public static final Sound getMedKit = new Sound("/soundEffects/getMedKit.wav");

  public static final Sound katanaSwing = new Sound("/soundEffects/katanaSwing.wav");

  public static final Sound lostShield = new Sound("/soundEffects/lostShield.wav");
  public static final Sound noAmmo = new Sound("/soundEffects/noAmmo.wav");

  private Sound(String path) {
    try (InputStream audioSrc = getClass().getResourceAsStream(path)) {
      if (audioSrc == null) {
        throw new IOException("Sound file not found: " + path);
      }

      // Converte InputStream para AudioInputStream
      try (AudioInputStream ais = AudioSystem.getAudioInputStream(audioSrc)) {
        clip = AudioSystem.getClip();
        clip.open(ais);
      }
    } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
      e.printStackTrace();
    }
  }

  public void play() {
    if (clip != null) {
      new Thread(
              () -> {
                clip.stop();
                clip.setFramePosition(0);
                clip.start();
              })
          .start();
    }
  }

  public void loop() {
    if (clip != null) {
      new Thread(
              () -> {
                clip.stop();
                clip.setFramePosition(0);
                clip.loop(Clip.LOOP_CONTINUOUSLY);
              })
          .start();
    }
  }
}
