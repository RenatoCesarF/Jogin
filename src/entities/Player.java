package entities;

import graphics.Spritesheet;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import main.Game;
import main.Sound;
import world.Camera;
import world.World;

public class Player extends Entity {

  private int maskX = 3, maskY = 3, maskW = 10, maskH = 10;
  private int colidingArea = 16;

  // Check if player is damaged vars
  public boolean isDamaged = false;
  private int damagedFrames = 0;

  // Player Status
  public double life = 5, maxLife = 5;
  public int ammo = 0, maxAmmo = 40;
  public int mana = 0, maxMana = 10;
  public int shield = 0;

  public int myWeapon = -1;
  private int damage = 0;

  public int myThrowable = -1;
  public int throwableDamage = 0;
  public boolean throwing = false;
  public Throwable throwable;

  private boolean aboveWeapon = false;
  public boolean confirm = false;

  // Player Sprites
  private BufferedImage[] playerRight;
  private BufferedImage[] playerLeft;
  private BufferedImage[] playerUp;
  private BufferedImage[] playerDown;
  private BufferedImage[] playerStatic;

  private BufferedImage playerDameged;

  // Player Frame Controll
  private int frames = 0, maxFrames = 6, indexHorizontal, indexVertical;
  private int maxIndexHorizontal = 8, maxIndexVertical = 4;
  private boolean movedHorizontal = false;

  public boolean right, up, left, down;
  public double speed = 0.9;

  public Player(int x, int y, int width, int height, BufferedImage sprite) {
    super(x, y, width, height, sprite);

    playerRight = new BufferedImage[8];
    playerLeft = new BufferedImage[8];
    playerUp = new BufferedImage[4];
    playerDown = new BufferedImage[4];
    playerStatic = new BufferedImage[2];

    playerDameged = Game.playerSprite.getSprite(0, 16 * 5, 16, 16);

    // sprite player static
    for (int i = 0; i < 2; i++) { // frame,start, largura, algura
      playerStatic[i] = Game.playerSprite.getSprite(0 + (i * 16), 0, 16, 16); // 0
    }
    // sprite player moving for right
    for (int i = 0; i < 8; i++) {
      playerRight[i] = Game.playerSprite.getSprite(0 + (i * 16), 48, 16, 16); // 48
    }
    // sprite player moving for left
    for (int i = 0; i < 8; i++) {
      playerLeft[i] = Game.playerSprite.getSprite(0 + (i * 16), 64, 16, 16); // 64
    }
    // sprite player moving for up
    for (int i = 0; i < 4; i++) {
      playerUp[i] = Game.playerSprite.getSprite(0 + (i * 16), 32, 16, 16); // 32
    }
    // sprite player moving down
    for (int i = 0; i < 4; i++) {
      playerDown[i] = Game.playerSprite.getSprite(0 + (i * 16), 16, 16, 16);
    }
  }

  public int getColidingArea() {
    return this.colidingArea;
  }

  // ============= Life Stufs =============== \\
  public void loseLife(int damage) {
    if (Game.player.shield > 0) {
      Game.player.shield--;
      Sound.lostShield.play();
    } else {
      Game.player.life -= damage;
      Game.player.isDamaged = true;
      System.out.println("new life: " + Game.player.life);
    }

    immunity();

    if (Game.player.life <= 0) {
      playderDied();
    }
  }

  public void immunity() {
    Thread immunityTime =
        new Thread(
            new Runnable() {
              public void run() {
                Game.player.colidingArea = 0;
                // system of colorize the player with white here
                try {
                  Thread.sleep(2500);
                } catch (InterruptedException e) {
                  // TODO Auto-generated catch block
                  e.printStackTrace();
                }
                Game.player.colidingArea = 16;
              }
            });

    immunityTime.start();
  }

  public void playderDied() {
    Game.entities.clear();
    Game.item.clear();
    Game.weapon.clear();
    Game.enemies.clear();

    Game.entities = new ArrayList<Entity>();
    Game.item = new ArrayList<Entity>();
    Game.weapon = new ArrayList<Entity>();
    Game.enemies = new ArrayList<Enemy>();

    Game.itemsSprite = new Spritesheet("/itens.png"); // itens
    Game.worldSprite = new Spritesheet("/tiles.png"); // tiles
    Game.playerSprite = new Spritesheet("/player.png"); // player
    Game.uiSprite = new Spritesheet("/UI.png");

    // Player               16 16
    Game.player = new Player(0, 0, 1, 1, Game.playerSprite.getSprite(0, 0, 16, 16));
    Game.entities.add(Game.player);

    Game.world = new World("/map.png"); // inicializando a classe de desenhar mundo
  }

  // ============== Item Stufs ============== \\
  public void checkCollisionWithItem() {
    for (int i = 0; i < Game.item.size(); i++) {
      Entity singleEntity = Game.item.get(i);

      if (singleEntity instanceof Consumable) {

        if (Entity.isColidding(this, singleEntity)) {

          int itemIndex = Game.item.indexOf(singleEntity);
          int getThisItem = Game.itemArray.get(itemIndex);

          switch (getThisItem) {
            case 0: // bag
              addMaxAmmo();
              Sound.getBag.play();
              Game.item.remove(singleEntity);
              Game.itemArray.remove(itemIndex);
              break;

            case 1: // Ammo
              if (!isFullAmmmo()) {
                Sound.getAmmo.play();
                addAmmo(10);
                Game.item.remove(singleEntity);
                Game.itemArray.remove(itemIndex);
              } else return;
              break;

            case 2: // shield
              Sound.getShield.play();
              addShield();
              Game.item.remove(singleEntity);
              Game.itemArray.remove(itemIndex);
              break;

            case 3:
              if (!isFullMana()) {
                Sound.swallow.play();
                addMana(2);
                System.out.println("Get Mana potion");
                Game.item.remove(singleEntity);
                Game.itemArray.remove(itemIndex);
                break;
              }

            case 4:
              if (!isFullLife()) {
                Game.item.remove(singleEntity);
                Game.itemArray.remove(itemIndex);
              } else return;
              break;
          }
        }
      }
    }
  }

  public boolean isFullLife() {
    if (Game.player.life < Game.player.maxLife) return false;
    else return true;
  }

  public boolean isFullAmmmo() {
    if (Game.player.ammo < Game.player.maxAmmo) return false;
    else return true;
  }

  public boolean isFullMana() {
    if (Game.player.mana < Game.player.maxMana) return false;
    else return true;
  }

  public void addLife(int amount) {
    this.life += 1;
    if (this.life > this.maxLife) {
      this.life = this.maxLife;
    }
  }

  public void addMana(int amount) {
    this.mana += amount;
    if (this.mana > this.maxMana) {
      this.mana = this.maxMana;
    }
  }

  public void addAmmo(int amount) {
    this.ammo += amount;
    if (this.ammo > this.maxAmmo) {
      this.ammo = this.maxAmmo;
    }
  }

  public void addMaxAmmo() {
    this.maxAmmo += 5;
  }

  public void addShield() {
    Game.player.shield++;
  }

  // ============= Weapon Stufs ============= \\

  public void checkCollisionWithWeapons() {
    for (int i = 0; i < Game.weapon.size(); i++) {
      Entity singleEntity = Game.weapon.get(i);

      if (singleEntity instanceof Consumable) {

        if (Entity.isColidding(this, singleEntity)) {
          int weaponIndex = Game.weapon.indexOf(singleEntity);
          int getThisWeapon = Game.weaponArray.get(weaponIndex);

          Thread delay =
              new Thread(
                  new Runnable() {
                    public void run() {
                      setAboveWeapon(true);
                      try {
                        Thread.sleep(1000);
                      } catch (InterruptedException e) {

                        e.printStackTrace();
                      }

                      setAboveWeapon(false);
                    }
                  });

          delay.start();

          if (this.confirm) {
            Game.player.getGun(getThisWeapon);
            Game.weapon.remove(singleEntity);
            Game.weaponArray.remove(weaponIndex);
            setAboveWeapon(false);
          }
        }
      }
    }
  }

  public boolean getAboveWeapon() {
    return this.aboveWeapon;
  }

  public void setAboveWeapon(boolean set) {
    this.aboveWeapon = set;
  }

  public void getGun(int weaponIndex) {

    switch (weaponIndex) {
      case 0:
        Sound.getGun.play();
        setWeapon(weaponIndex);
        setDamage(1);
        System.out.println("0");
        break;

      case 1:
        Sound.getShotgun.play();
        setWeapon(weaponIndex);
        setDamage(5);
        System.out.println("1");
        break;

      case 2:
        setWeapon(weaponIndex);
        setDamage(10);
        System.out.println("2");
        break;

      case 3:
        Sound.getBook.play();
        setWeapon(weaponIndex);
        setDamage(10);
        System.out.println("3");
        break;

      case 4:
        Sound.getBook.play();
        setWeapon(weaponIndex);
        setDamage(13);
        System.out.println("4");
        break;

      case 5:
        Sound.getKatana.play();
        setWeapon(weaponIndex);
        setDamage(13);
        System.out.println("5");
        break;

      case 6:
        Sound.getSword.play();
        setWeapon(weaponIndex);
        setDamage(13);
        System.out.println("6");
        break;

      case 7:
        Sound.getMedKit.play();
        this.myThrowable = 2;
        this.throwableDamage = 10;
        setDamage(20);
        System.out.println("7");
        break;
      case 8:
        Sound.getMedKit.play();
        this.myThrowable = 1;
        this.throwableDamage = 5;
        System.out.println("8");
        break;
    }
  }

  public void setWeapon(int weaponIndex) {
    this.myWeapon = weaponIndex;
  }

  public void setDamage(int damage) {
    this.damage = damage;
  }

  public int getPlayerWeapon() {
    return Game.player.myWeapon;
  }

  // ============= Frame Stufs ============== \\
  public void tick() {

    // Player Movement Check

    movedHorizontal = false;
    if (up && World.isFree(this.getX(), (int) (y - speed))) {
      y -= speed;
    } else if (down && World.isFree(this.getX(), (int) (y + speed))) {

      y += speed;
    }
    if (right && World.isFree((int) (x + speed), this.getY())) {
      movedHorizontal = true;
      x += speed;
    } else if (left && World.isFree((int) (x - speed), this.getY())) {
      movedHorizontal = true;
      x -= speed;
    }

    if (movedHorizontal) {
      frames++;
      if (frames == maxFrames) {
        frames = 0;
        indexHorizontal++;
        if (indexHorizontal >= maxIndexHorizontal) {
          indexHorizontal = 0;
        }
      }
    } else {
      frames++;
      if (frames == maxFrames) {
        frames = 0;
        indexVertical++;
        if (indexVertical >= maxIndexVertical) {
          indexVertical = 0;
        }
      }
    }

    // Checking the collision
    this.checkCollisionWithItem();
    this.checkCollisionWithWeapons();

    // Controll the animation of get damage
    if (isDamaged) {
      Game.player.damagedFrames++;
      if (Game.player.damagedFrames == 10) {
        Game.player.damagedFrames = 0;
        isDamaged = false;
      }
    }

    // Controlling if player is throwing
    if (this.throwing && myThrowable > 0) {
      int throwableXDirection = 0;
      int throwableYDirection = 0;

      this.throwing = false;

      // Check the direction of the player
      if (this.right) {
        throwableXDirection = 1;
      } else if (this.left) {
        throwableXDirection = -1;
      }
      if (this.up) {
        throwableYDirection = -1;
      } else if (this.down) {
        throwableYDirection = 1;
      }

      Throwable throwable =
          new Throwable(
              this.getX(), this.getY(), 16, 16, null, throwableXDirection, throwableYDirection);

      Game.throwables.add(throwable);
    }

    // Camera Follow the player
    Camera.x = Camera.clamp(this.getX() - (Game.WIDTH / 2), 0, World.WIDTH * 16 - Game.WIDTH);
    Camera.y = Camera.clamp(this.getY() - (Game.HEIGHT / 2), 0, World.HEIGHT * 16 - Game.HEIGHT);
  }

  public void render(Graphics g) {
    if (!isDamaged) {

      if (right) {
        g.drawImage(
            playerRight[indexHorizontal], this.getX() - Camera.x, this.getY() - Camera.y, null);
      } else if (left) {
        g.drawImage(
            playerLeft[indexHorizontal], this.getX() - Camera.x, this.getY() - Camera.y, null);
      } else if (up) {
        g.drawImage(playerUp[indexVertical], this.getX() - Camera.x, this.getY() - Camera.y, null);
      } else if (down) {
        g.drawImage(
            playerDown[indexVertical], this.getX() - Camera.x, this.getY() - Camera.y, null);
      } else {
        g.drawImage(playerStatic[1], this.getX() - Camera.x, this.getY() - Camera.y, null);
      }
    } else {
      g.drawImage(playerDameged, this.getX() - Camera.x, this.getY() - Camera.y, null);
    }
  }
}
