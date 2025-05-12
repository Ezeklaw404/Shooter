package com.neumont.game;

import com.neumont.engine.*;

import javax.sound.sampled.Clip;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class Enemy extends GameActor implements Collidable{
    private double speed;
    private int fireTimer, fireTimeMin, fireTimeMax;
    private BufferedImage rocketImage;
    private Clip fireClip;

    public Enemy(Game game, double positionX, double positionY, double speed, int fireTimeMin, int fireTimeMax, GameActorData data) {
        super(game, positionX, positionY, data);
        this.speed = speed;
        this.fireTimeMin = fireTimeMin;
        this.fireTimeMax = fireTimeMax;

        fireTimer = RNG.range(fireTimeMin, fireTimeMax);

        //rocketImage = GameImage.getImage("resources/galaga.png",307,136,16,16);
        rocketImage = GameImage.getImage("resources/galaga.png",312,139,5,10);
        fireClip = GameAudio.getClip("resources/fire.wav");
    }

    @Override
    public void update(int deltaTime) {
        super.update(deltaTime);

        //destroyed if off-screen
        if (velocityX > 0 && positionX > game.getWidth()) destroyed = true;
        if (velocityX < 0 && positionX < 0) destroyed = true;


        //enemy fire
        fireTimer -= deltaTime;
        if (fireTimer <= 0) {
            fireTimer = RNG.range(fireTimeMin,fireTimeMax);
            GameAudio.playSound(fireClip);
            GameActorData data = new GameActorData(0,7, new Graphic(rocketImage), "enemy", 2000);
            var rocket = new Rocket(game, positionX, positionY, data);
            game.add(rocket);
        }

    }

    @Override
    public void onCollisionEnter(GameActor other) {
        if (other.getTag().equalsIgnoreCase("player")) {
            ((ShooterGame)game).setScore(((ShooterGame)game).getScore() + 100);
            game.add(((ShooterGame)game).createExplosion(positionX, positionY));
            destroyed = true;
        }
    }
}
