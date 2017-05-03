package com.example.java;

import com.example.java.Util.ImageLoader;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class Game extends Canvas implements Runnable {

    public static final int WIDTH = 640, HEIGHT = WIDTH / 9 * 12;

    public Thread thread;
    private boolean running = false;


    private Handler handler;
    private Spawn spawner;
    private Menu menu;

    ;

    private static STATE gameState = STATE.Loading;

    BufferedImage background;

    public Game() {
        handler = new Handler(new Ship(ID.Ship));
        menu = new Menu(this);
        this.addKeyListener(new KeyInput(handler));
        this.addMouseListener(menu);
        new Window(WIDTH, HEIGHT, "Space Explorer", this);
        spawner = new Spawn(handler);
        background = ImageLoader.loadImage("Resources/background2.png");
    }

    public synchronized void start() {
        thread = new Thread(this);
        thread.start();
        running = true;
    }

    public synchronized void stop() {
        try {
            thread.join();
            running = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        this.requestFocus();
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while(delta >= 1) {
                tick();
                delta--;
            }
            if (running)
                render();
            frames++;

            if(System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                System.out.println("FPS: " + frames);
                frames = 0;
            }
        }
        stop();
    }

    private void tick() {
            handler.tick();
            spawner.tick();
            menu.tick();
    }

    private void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();

        g.drawImage(background, 0, 0, null);

        handler.render(g);
        menu.render(g);

        g.dispose();
        bs.show();
    }

    public void reset() {
        spawner.reset();
        handler.reset();
    }

    public static void main(String[] args) {
        new Game();
    }

    public static void setGameState(STATE state) {
        gameState = state;
    }

    public static STATE getGameState() {
        return gameState;
    }

}
