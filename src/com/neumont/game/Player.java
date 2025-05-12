package com.neumont.game;

import com.neumont.engine.*;

import javax.sound.sampled.Clip;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class Player extends GameActor implements Collidable {
    private double speed;
    private int fireTimer, fireTime;
    private BufferedImage rocketImage;
    private Clip fireClip;

    public Player(Game game, double positionX, double positionY, double speed, int fireTime, GameActorData data) {
        super(game, positionX, positionY, data);
        this.speed = speed;
        this.fireTime = fireTime;

        rocketImage = GameImage.getImage("resources/galaga.png",312,121,5,10);
        fireClip = GameAudio.getClip("resources/fire.wav");
    }

    @Override
    public void update(int deltaTime) {
        super.update(deltaTime);



        //player movement
        if (game.getKeyboardInput().isKeyDown(KeyEvent.VK_LEFT)) velocityX = -speed;
        else if (game.getKeyboardInput().isKeyDown(KeyEvent.VK_RIGHT)) velocityX = speed;
        else velocityX = 0;

        if (game.getKeyboardInput().isKeyDown(KeyEvent.VK_UP)) velocityY = (-speed)*.8;
        else if (game.getKeyboardInput().isKeyDown(KeyEvent.VK_DOWN)) velocityY = (speed *.8);
        else velocityY = 0;

        if (positionY < sizeY / 2) positionY = (sizeY / 2);
        if (positionY > game.getHeight() - (sizeY)) positionY = game.getHeight() - (sizeY);
        if (positionX < sizeX / 3) positionX = (sizeX / 3);
        if (positionX > game.getWidth() - (sizeX)) positionX = game.getWidth() - (sizeX);


        //player fire
        fireTimer -= deltaTime;
        if (fireTimer <= 0 && game.getKeyboardInput().isKeyDown(KeyEvent.VK_SPACE)) {
            fireTimer = fireTime;
            GameAudio.playSound(fireClip);
            GameActorData data = new GameActorData(0,-10, new Graphic(rocketImage), "player", 3000);
            var rocket = new Rocket(game, (positionX + 6), positionY, data);
            game.add(rocket);
        }

    }

    @Override
    public void onCollisionEnter(GameActor other) {
        if (!destroyed && other.getTag().equalsIgnoreCase("enemy")) {
            game.add(((ShooterGame)game).createPlayerExplosion(positionX, positionY));
            game.setState(Game.State.PLAYER_DEAD);
            destroyed = true;
        }
    }
}
