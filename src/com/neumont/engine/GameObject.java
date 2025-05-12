package com.neumont.engine;

import java.awt.*;

public abstract class GameObject {
    protected Game game;

    protected double positionX;
    protected double positionY;

    protected double sizeX;
    protected double sizeY;

    protected double angle = 0;
    public GameObject(Game game, double positionX, double positionY, double sizeX, double sizeY) {
        this.game = game;
        this.positionX = positionX;
        this.positionY = positionY;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
    }
    public GameObject(Game game, double positionX, double positionY, double sizeX, double sizeY, double angle) {
        this.game = game;
        this.positionX = positionX;
        this.positionY = positionY;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.angle = angle;
    }
    public Rectangle getRectangle() {
        return new Rectangle((int)(positionX - sizeX / 2), (int)(positionY - sizeY / 2), (int)sizeX, (int)sizeY);
    }
    public boolean intersects(GameObject gameObject) {
        return this.getRectangle().intersects(gameObject.getRectangle());
    }
    public abstract void update(int deltaTime);
    public abstract void draw(Graphics g);
}
