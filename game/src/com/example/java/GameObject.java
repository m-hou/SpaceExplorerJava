package com.example.java;

import com.example.java.Util.ImageEditor;
import com.example.java.Util.ImageLoader;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by HOU on 2016/4/30.
 */
public abstract class GameObject {

    protected ID id;
    protected double x, y;
    protected double angle;

    protected static double yPos;

    protected int width;
    protected int height;

    protected String filePath;
    protected BufferedImage image;
    protected BufferedImage resizedImage;
    protected BufferedImage rotatedImage;

    protected Rectangle collBounds;

    public GameObject(double x, double y, double angle, ID id) {
        this.x = x;
        this.y = y;
        this.angle = angle;
        this.id = id;

        collBounds = new Rectangle();
    }

    public abstract void tick();
    public abstract void render(Graphics g);

    protected void setUpSprite(double angle, int width, int height) {
        image = ImageLoader.loadImage(filePath);
        resizedImage = (BufferedImage) ImageEditor.resize(image, width, height);
        rotatedImage = ImageEditor.rotateImage(resizedImage, angle);
    }

    protected void updateCollBounds() {
        collBounds.setSize(rotatedImage.getWidth(), rotatedImage.getHeight());
        collBounds.setLocation(Math.round((Math.round(x))), Math.round(Math.round(y + getyPos())));
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public ID getID() {
        return id;
    }

    public double getyPos() {
        return yPos;
    }

    public Rectangle getCollBounds() {
        return collBounds;
    }
}
