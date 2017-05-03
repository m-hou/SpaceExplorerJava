package com.example.java;

import java.awt.*;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Created by HOU on 2016/4/30.
 */
public class Handler implements Iterable<GameObject>{

    public LinkedList<GameObject> objects = new LinkedList<GameObject>();
    public Ship ship;

    public Handler (Ship ship) {
        this.ship = ship;
    }

    public void tick() {
        if (Game.getGameState() == STATE.Menu) {
            for (GameObject object : objects) {
                GameObject tempObject = object;

                tempObject.tick();
            }
        } else if (Game.getGameState() == STATE.GameOver) {
            for (GameObject object : objects) {
                GameObject tempObject = object;

                tempObject.tick();
            }
        } else {
            for (GameObject object : this) {
                GameObject tempObject = object;

                tempObject.tick();
            }
            for (GameObject object : objects) {
                ship.detectCollision(object);
            }
        }
    }

    public void render(Graphics g) {
        if (Game.getGameState() == STATE.Menu) {
            for (GameObject object : objects) {
                GameObject tempObject = object;

                tempObject.render(g);
            }
        } else if (Game.getGameState() == STATE.GameOver) {
            for (GameObject object : this) {
                GameObject tempObject = object;

                tempObject.render(g);
            }
        } else {
            for (GameObject object : this) {
                GameObject tempObject = object;

                tempObject.render(g);
            }
        }
    }

    public void addObject(GameObject object) {
        this.objects.add(object);
    }

    public void removeObject(GameObject object) {
        this.objects.remove(object);
    }

    public void reset() {
        objects = new LinkedList<GameObject>();
        ship.reset();
    }

    @Override
    public Iterator<GameObject> iterator() {
        return new GameObjectIterator();
    }



    private class GameObjectIterator implements Iterator<GameObject> {

        private int objectNum;
        private boolean shipReturned;

        public GameObjectIterator() {
            objectNum = 0;
            shipReturned = false;
        }

        @Override
        public boolean hasNext() {
            return !shipReturned;
        }

        @Override
        public GameObject next() {
            if (objects.size()-1 <= objectNum) {
                shipReturned = true;
                return ship;
            } else {
                objectNum++;
                return objects.get(objectNum);
            }
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
