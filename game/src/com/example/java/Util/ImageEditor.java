package com.example.java.Util;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.nio.Buffer;

/**
 * Created by HOU on 2016/5/4.
 */
public final class ImageEditor {

    public static Image resize(BufferedImage image, int x, int y) {
        Image resized = image.getScaledInstance(x, y, Image.SCALE_SMOOTH);
        int width = resized.getWidth(null);
        int height = resized.getHeight(null);

        BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics g = newImage.getGraphics();
        g.drawImage(resized, 0, 0, null);
        g.dispose();
        return newImage;
    }

    public static BufferedImage rotateImage(BufferedImage newImage, double theta)
    {
        //http://stackoverflow.com/questions/28755136/how-to-rotate-a-non-square-image-in-java
        double cos = Math.abs(Math.cos(theta));
        double sin = Math.abs(Math.sin(theta));
        double width  = newImage.getWidth();
        double height = newImage.getHeight();
        int w = (int)(width * cos + height * sin);
        int h = (int)(width * sin + height * cos);

        BufferedImage out = new BufferedImage(w, h, newImage.getType());
        Graphics2D g2 = out.createGraphics();
        g2.setPaint(new Color(0, 0, 0, 0.0f));
        g2.fillRect(0,0,w,h);
        double x = w/2;
        double y = h/2;
        AffineTransform at = AffineTransform.getRotateInstance(theta, x, y);
        x = (w - width)/2;
        y = (h - height)/2;
        at.translate(x, y);
        g2.drawRenderedImage(newImage, at);
        g2.dispose();
        return out;
    }
}
