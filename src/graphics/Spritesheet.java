package graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;

public class Spritesheet {

  private final BufferedImage spritesheet;

  public Spritesheet(String path) {
    try (InputStream is = getClass().getResourceAsStream(path)) {
      if (is == null) {
        throw new IOException("Resource not found: " + path);
      }
      spritesheet = ImageIO.read(is);
    } catch (IOException e) {
      throw new RuntimeException("Failed to load spritesheet: " + path, e);
    }
  }

  public BufferedImage getSprite(int x, int y, int width, int height) {
    return spritesheet.getSubimage(x, y, width, height);
  }
}
