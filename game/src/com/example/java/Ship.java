package com.example.java;


import com.example.java.Util.ImageEditor;
import com.example.java.Util.ImageLoader;
import com.example.java.Util.MathHelper;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;

/**
 * Created by HOU on 2016/4/30.
 */
public class Ship extends GameObject {

    public static final double YLOC = 300;

    private final int ROCKETSIZE = 64;
    private final double VELODECAY = 0.98;
    private final double SPEED = 2;
    private final double PROPULSION = 0.12;
    private final int MAXCOLLISIONPIXELS = 100;

    private double accel, velo;

    private Rectangle collision;

    private BufferedImage explode;

    private double imageshiftx = 0;
    private double imageshifty = 0;

    public Ship(ID id) {
        super(0, 0, 0, id);

        filePath = "Resources/rocket.png";
        width = ROCKETSIZE;
        height = ROCKETSIZE * 2;

        setUpSprite(angle, width, height);

        explode = ImageLoader.loadImage("Resources/Explosion.png");
        explode = (BufferedImage) ImageEditor.resize(explode, 128, 128);
        updateCollBounds();

        x = (Game.WIDTH - rotatedImage.getWidth())/2;
    }

    public void tick() {
        velo += accel;
        velo *= VELODECAY;
        angle += Math.toRadians(velo);
        x += Math.sin(angle) * SPEED;

        y += Math.cos(angle) * SPEED;
        yPos = y;

        int prevWidth = rotatedImage.getWidth();
        int prevHeight = rotatedImage.getHeight();
        rotatedImage = ImageEditor.rotateImage(resizedImage, angle);
        imageshiftx += (rotatedImage.getWidth() - prevWidth)/2;
        imageshifty += (rotatedImage.getHeight() - prevHeight)/2;
        x = MathHelper.modulo(Math.round(Math.round(x)), Game.WIDTH, Math.round(Math.round(imageshiftx - rotatedImage.getWidth()/2)));
        updateCollBounds();
    }

    public void render(Graphics g) {
      if (Game.getGameState() == STATE.Game) {
          g.drawImage(rotatedImage, Math.round(Math.round(x - imageshiftx)), Game.HEIGHT - (int) YLOC - (int) imageshifty, null);
          g.setColor(Color.blue);
          g.drawRect(Math.round(Math.round(collBounds.getX())), Math.round(Math.round(collBounds.getY())), Math.round(Math.round(collBounds.getWidth())), Math.round(Math.round(collBounds.getHeight())));
      } else if (Game.getGameState() == STATE.GameOver) {
          g.drawImage(explode, Math.round(Math.round(x - imageshiftx)), Game.HEIGHT - (int) YLOC - (int) imageshifty, null);
      }
    }

    @Override
    public void updateCollBounds() {
        collBounds.setSize(rotatedImage.getWidth(), rotatedImage.getHeight());
        collBounds.setLocation(Math.round((Math.round(x - imageshiftx))), Game.HEIGHT -  (int) YLOC - (int) imageshifty);
    }

    public void propelLeft() {
        accel = -PROPULSION;
    }

    public void propelRight() {
        accel = PROPULSION;
    }

    public void stopPropel() {
        accel = 0;
    }


    public void detectCollision(GameObject other) {
        //http://stackoverflow.com/questions/23332096/how-to-detect-if-two-images-collide-in-java
        collision = null;
        int collisionCount = 0;
        // Check if the boundaries intersect
        if (collBounds.intersects(other.getCollBounds())) {
            // Calculate the collision overlay
            Rectangle bounds = getCollision(this.collBounds, other.getCollBounds());
            if (!bounds.isEmpty()) {
                // Check all the pixels in the collision overlay to determine
                // if there are any non-alpha pixel collisions...
                for (int x = bounds.x; x < bounds.x + bounds.width; x++) {
                    for (int y = bounds.y; y < bounds.y + bounds.height; y++) {
                        if (collision(x, y, other)) {
                            collisionCount++;
                            System.out.println(collisionCount);
                        }
                        if (collisionCount > MAXCOLLISIONPIXELS) {
                            collision = bounds;
                            if (other.getID() == ID.Asteroid) {
                                Game.setGameState(STATE.GameOver);
                            } else if (other.getID() == ID.Planet) {
                                Planet explored = (Planet) other;
                                explored.setPlanetstate(Planet.PLANETSTATE.Explored);
                            }
                            break;
                        }
                    }
                }
            }
        }
    }

    protected Rectangle getCollision(Rectangle rect1, Rectangle rect2) {
        Area a1 = new Area(rect1);
        Area a2 = new Area(rect2);
        a1.intersect(a2);
        return a1.getBounds();
    }

    protected boolean collision(int x, int y, GameObject other) {
        boolean collision = false;
        int thisPixel = this.rotatedImage.getRGB(x - collBounds.x, y - collBounds.y);
        int otherPixel = other.rotatedImage.getRGB(x - other.collBounds.x, y - other.collBounds.y);
        // 255 is completely transparent, you might consider using something
        // a little less absolute, like 225, to give you a sligtly
        // higher hit right, for example...
        if ((thisPixel >> 24) != 0x00 && (otherPixel >> 24) != 0x00) {
            if ((thisPixel >> 24) != 0xff && (otherPixel >> 24) != 0xff) {
                collision = true;
            }
        }
        return collision;
    }

    public void reset() {
        y = 0;
        yPos = 0;
        angle = 0;
        velo = 0;
        accel = 0;
        imageshiftx = 0;
        imageshiftx = 0;
        imageshifty = 0;
        rotatedImage = ImageEditor.rotateImage(resizedImage, angle);
        x = (Game.WIDTH - rotatedImage.getWidth())/2;
    }

}
