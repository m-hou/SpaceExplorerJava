package com.example.java;

import com.example.java.Util.ImageEditor;
import com.example.java.Util.ImageLoader;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

/**
 * Created by HOU on 2016/5/5.
 */
public class Menu extends MouseAdapter {

    private Game game;

    private BufferedImage title;
    private final int TITLEWIDTH = 500;
    private final int TITLEHEIGHT = 300;
    private final int TITLEX = (Game.WIDTH - TITLEWIDTH)/2;;
    private final int TITLEY = 200;

    private BufferedImage start;
    private final int STARTWIDTH = 150;
    private final int STARTHEIGHT = 150;
    private final int STARTX = (Game.WIDTH - STARTWIDTH)/2;;
    private final int STARTY = Game.HEIGHT - (int) Ship.YLOC;

    private BufferedImage gameover;
    private final int GAMEOVERWIDTH = 400;
    private final int GAMEOVERHEIGHT = 350;
    private int GAMEOVERX = (Game.WIDTH - GAMEOVERWIDTH)/2;
    private final int GAMEOVERY = 100;

    private BufferedImage back;
    private final int BACKWIDTH = STARTWIDTH;
    private final int BACKHEIGHT = STARTHEIGHT;
    private final int BACKX = STARTX;
    private final int BACKY = STARTY;

    public Menu (Game game) {
        this.game = game;
        loadAssets();
    }

    private void loadAssets() {
        title = ImageLoader.loadImage("Resources/title.png");
        title = (BufferedImage) ImageEditor.resize(title, TITLEWIDTH, TITLEHEIGHT);

        start = ImageLoader.loadImage("Resources/start.png");
        start = (BufferedImage) ImageEditor.resize(start, STARTWIDTH, STARTHEIGHT);

        gameover = ImageLoader.loadImage("Resources/gameover.png");
        gameover = (BufferedImage) ImageEditor.resize(gameover, GAMEOVERWIDTH, GAMEOVERHEIGHT);

        back = ImageLoader.loadImage("Resources/back.png");
        back = (BufferedImage) ImageEditor.resize(back, BACKWIDTH, BACKHEIGHT);
    }

    public void mousePressed(MouseEvent e) {
        int mx = e.getX();
        int my = e.getY();

        if ((mouseOver(mx, my, STARTX, STARTY, STARTWIDTH, STARTHEIGHT)) && game.getGameState() == STATE.Menu) {
            game.setGameState(STATE.Game);
        }

        if ((mouseOver(mx, my, BACKX, BACKY, BACKWIDTH, BACKHEIGHT)) && game.getGameState() == STATE.GameOver) {
            game.reset();
            game.setGameState(STATE.Loading);
        }
    }

    public void mouseReleased (MouseEvent e) {

    }

    private boolean mouseOver(int mx, int my, int x, int y, int width, int height) {
        if (mx > x && mx < x + width) {
            if (my > y && my < y + height) {
                return true;
            }
        }
        return false;
    }

    public void tick() {

    }
    public void render(Graphics g) {
        if (Game.getGameState() == STATE.Loading) {
            g.drawImage(title, TITLEX, TITLEY, null);
        } else if (Game.getGameState() == STATE.Menu) {
            g.drawImage(title, TITLEX, TITLEY, null);
            g.drawImage(start, STARTX, STARTY, null);
        } else if (Game.getGameState() == STATE.GameOver) {
            g.drawImage(gameover, GAMEOVERX, GAMEOVERY, null);
            g.drawImage(back, BACKX, BACKY, null);
        }
    }
}
