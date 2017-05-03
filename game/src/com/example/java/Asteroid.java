package com.example.java;

import com.example.java.Util.ImageEditor;

import java.awt.*;

/**
 * Created by HOU on 2016/4/30.
 */
public class Asteroid extends GameObject {

    protected static final int MINSIZE = 50;
    protected static final int MAXSIZEVARIANCE = 40;
    protected static final int SPACING = 40;
    protected static final int SPACINGVARIANCE = 20;

    private double velo;

    private double imageshiftx = 0;
    private double imageshifty = 0;

    public Asteroid(double x, double y, ID id, double velo, double angle, int size, int type) {
        super(x, y, angle, id);

        this.velo = velo;

        if (type == 0) {
            filePath = "Resources/Asteroida.png";
        } else {
            filePath = "Resources/Asteroidb.png";
        }
        width = size;
        height = size;

        setUpSprite(angle, width, height);
        updateCollBounds();
    }

    @Override
    public void updateCollBounds() {
        collBounds.setSize(rotatedImage.getWidth(), rotatedImage.getHeight());
        collBounds.setLocation(Math.round((Math.round(x - imageshiftx))),  Math.round(Math.round(y + yPos - imageshifty)));
    }

    @Override
    public void tick() {
        angle += velo;

        int prevWidth = rotatedImage.getWidth();
        int prevHeight = rotatedImage.getHeight();
        rotatedImage = ImageEditor.rotateImage(resizedImage, angle);
        imageshiftx += (rotatedImage.getWidth() - prevWidth)/2;
        imageshifty += (rotatedImage.getHeight() - prevHeight)/2;
        updateCollBounds();
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(rotatedImage, Math.round(Math.round(x + imageshiftx)), Math.round(Math.round(y + yPos + imageshifty)), null);
        g.setColor(Color.blue);
        g.drawRect(Math.round(Math.round(collBounds.getX())), Math.round(Math.round(collBounds.getY())), Math.round(Math.round(collBounds.getWidth())), Math.round(Math.round(collBounds.getHeight())));
    }
}
