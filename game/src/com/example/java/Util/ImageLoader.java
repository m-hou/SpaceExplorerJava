package com.example.java.Util;

import com.example.java.Game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Created by HOU on 2016/5/4.
 */
public class ImageLoader {

    public static BufferedImage loadImage(String path) {
        try {
            BufferedImage image = null;

            InputStream is = ImageLoader.class.getClassLoader().getResourceAsStream(path);
            image = ImageIO.read(is);
            return image;
        } catch (IOException e) {
            System.out.println("could not find asset");
        }
        return null;
    }
}
