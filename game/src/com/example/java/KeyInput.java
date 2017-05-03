package com.example.java;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Created by HOU on 2016/4/30.
 */
public class KeyInput extends KeyAdapter {

    private Handler handler;
    private boolean[] keyDown = new boolean[2];

    public KeyInput(Handler handler) {
        this.handler = handler;

        keyDown[0] = false;
        keyDown[1] = false;
    }
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

                for (GameObject object : handler) {
                    GameObject tempObject = object;

                    if(tempObject.getID() == ID.Ship) {
                        Ship shipObject = (Ship) object;
                        if(key == KeyEvent.VK_A) {
                            shipObject.propelLeft();
                            keyDown[0] = true;
                        }
                        if(key == KeyEvent.VK_D) {
                            shipObject.propelRight();
                            keyDown[1] = true;
                        }
            }
        }
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        for (GameObject object : handler) {
            GameObject tempObject = object;

            if(tempObject.getID() == ID.Ship) {
                Ship shipObject = (Ship) object;
                if(key == KeyEvent.VK_A) keyDown[0] = false;
                if(key == KeyEvent.VK_D) keyDown[1] = false;

                if(!keyDown[0] && !keyDown[1]) {
                    shipObject.stopPropel();
                }
            }
        }
    }
}
