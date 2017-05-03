package com.example.java;

import com.example.java.Util.ImageEditor;
import com.example.java.Util.ImageLoader;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by HOU on 2016/5/3.
 */
public class Planet extends GameObject {


    BufferedImage explored;
    PLANETSTATE planetstate;

    public enum PLANETSTATE {
        Explored,
        Unexplored
    }
    public Planet(double x, double y, ID id, int size, int type) {
        super(x, y, 0, id);
        planetstate = PLANETSTATE.Unexplored;

        if (type == 0) {
            filePath = "Resources/Planeta2.png";
            width = size * 2;
            height = size;
        } else {
            filePath = "Resources/Planetb.png";
            width = size;
            height = size;
        }

        setUpSprite(angle, width, height);

        if (type == 0) {
            explored = ImageLoader.loadImage("Resources/Planeta2flag.png");
            int widthtemp = explored.getWidth();
            int heighttemp = explored.getHeight();
            explored = (BufferedImage) ImageEditor.resize(explored, size * 2 * widthtemp / image.getWidth(), size * heighttemp / image.getHeight());
        } else {
            explored = ImageLoader.loadImage("Resources/Planetbflag.png");
            int widthtemp = explored.getWidth();
            int heighttemp = explored.getHeight();
            explored = (BufferedImage) ImageEditor.resize(explored, size * widthtemp / image.getWidth(), size * heighttemp / image.getHeight());
        }

        updateCollBounds();
    }



    @Override
    public void tick() {
        updateCollBounds();
    }

    @Override
    public void render(Graphics g) {
        if (planetstate == PLANETSTATE.Unexplored) {
            int imageshifty = explored.getHeight() - rotatedImage.getHeight();
            g.drawImage(rotatedImage, Math.round(Math.round(x)), Math.round(Math.round(y + imageshifty + yPos)), null);
        } else if (planetstate == PLANETSTATE.Explored) {
            g.drawImage(explored, Math.round(Math.round(x)), Math.round(Math.round(y + yPos)), null);
        }
    }

    public void setPlanetstate(PLANETSTATE planetstate) {
        this.planetstate = planetstate;
    }
}
