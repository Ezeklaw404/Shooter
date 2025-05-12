package com.neumont.engine;

import java.awt.*;

public class GameActor extends GameObject{
    protected String tag = "";
    protected boolean destroyed = false;
    protected int lifeSpan = 0;
    protected double velocityX, velocityY;

    protected Graphic graphic;


    public GameActor(Game game, double positionX, double positionY, GameActorData data) {
        super(game, positionX, positionY, data.graphic().getWidth(), data.graphic().getHeight());
        this.velocityX = data.velocityX();
        this.velocityY = data.velocityY();
        this.graphic = data.graphic();
        this.tag = data.tag();
        this.lifeSpan = data.lifeSpan();
    }

    public String getTag() {
        return tag;
    }

    @Override
    public void update(int deltaTime) {
        if (lifeSpan != 0) {
            lifeSpan -= deltaTime;
            if (lifeSpan <= 0) {
                destroyed = true;
            }
        }

        graphic.update(deltaTime);

        positionX += velocityX;
        positionY += velocityY;
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(graphic.getImage(), (int)positionX, (int)positionY, null);
    }
}
