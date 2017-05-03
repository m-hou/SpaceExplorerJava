package com.example.java;

import java.util.LinkedList;
import java.util.Random;

/**
 * Created by HOU on 2016/5/5.
 */
public class Spawn {

    private static final int ASTEROIDXPADDING = 50;

    private final int PLANETSPAWNVARIENCE = 5;
    private final int PLANETMINSPAWN = 22;
    private final int PLANETXPADDING = 100;

    private Handler handler;

    private Random r;

    private double spawnHeight;
    private int spawnCount;
    private int nextPlanet;

    public Spawn(Handler handler) {
        this.handler = handler;

        r = new Random();

        spawnHeight = 0;
        spawnCount = 0;
        nextPlanet = r.nextInt(PLANETSPAWNVARIENCE) + PLANETMINSPAWN;
    }

    public void tick() {
        spawn();
        despawn();
        if (Game.getGameState() == STATE.Loading) {
            Game.setGameState(STATE.Menu);
        }
    }

    private double randomDouble(double min, double max) {
        double randomValue = min + (max - min) * r.nextDouble();
        return randomValue;
    }

    public void spawn() {
        while (spawnHeight < GameObject.yPos + Game.HEIGHT) {
            int remainingBeforePlanet = nextPlanet - spawnCount;
            if (remainingBeforePlanet <= 0) {
                spawnHeight += 1 * (r.nextInt(Asteroid.SPACINGVARIANCE) + Asteroid.SPACING);
                handler.addObject(new Planet(r.nextInt(Game.WIDTH - 2 * PLANETXPADDING), Ship.YLOC - spawnHeight, ID.Planet, 100, r.nextInt(2)));
                spawnCount = 0;
            } else if (remainingBeforePlanet == 1) {
                spawnHeight += 1 * (r.nextInt(Asteroid.SPACINGVARIANCE) + Asteroid.SPACING);
                spawnCount++;
            } else if (spawnCount == 0) {
                spawnCount++;
            } else {
                spawnHeight += 1 * (r.nextInt(Asteroid.SPACINGVARIANCE) + Asteroid.SPACING);
                handler.addObject(new Asteroid(r.nextInt(Game.WIDTH - 2 * ASTEROIDXPADDING), Ship.YLOC - spawnHeight, ID.Asteroid,
                        randomDouble(Math.toRadians(0.1), Math.toRadians(0.2)), r.nextDouble(), r.nextInt(Asteroid.MAXSIZEVARIANCE) + Asteroid.MAXSIZEVARIANCE, r.nextInt(2)));
                spawnCount++;
            }
        }
    }

    private void despawn() {
        LinkedList<GameObject> objectsToRemove = new LinkedList<GameObject>();
        for (GameObject object : handler) {
            if (GameObject.yPos + object.getY() > Game.HEIGHT && object.getID() != ID.Ship) {
                objectsToRemove.add(object);
            }
        }
        for (GameObject object : objectsToRemove) {
            handler.removeObject(object);
        }
    }

    public void reset() {
        spawnHeight = 0;
        spawnCount = 0;
        nextPlanet = r.nextInt(PLANETSPAWNVARIENCE) + PLANETMINSPAWN;
    }
}
