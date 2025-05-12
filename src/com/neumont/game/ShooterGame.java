package com.neumont.game;

import com.neumont.engine.*;
import com.neumont.game.Enemy;
import com.neumont.game.Player;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

public class ShooterGame extends Game {
    int highscore = 0;
    int score = 0;
    int lives = 0;
    Font font = null;
    int spawnTimer = 0;
    int stateTimer = 0;

    public ShooterGame(int width, int height, int fps) {
        super(width, height, fps);

        //create font
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, new File("resources/ArcadeClassic.ttf"));
            font = font.deriveFont(Font.PLAIN, 32);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }



    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public void updateState() {
        switch (getState()) {
            case INIT -> {
                //load highscore
                String string = FileIO.readFromFile("resources/game.txt");
                highscore = string.isEmpty() ? 0 :Integer.parseInt(string);

                setState(State.TITLE);
            }
            case TITLE -> {
                if (getKeyboardInput().isKeyDown(KeyEvent.VK_SPACE)) {
                    setState(State.START_GAME);
                }
            }
            case START_GAME -> {
                score = 0;
                lives = 3;

                stateTimer = 1000;
                setState(State.START_LEVEL);
            }
            case START_LEVEL -> {
                stateTimer -=getDeltaTime();

                if (stateTimer <= 0) {
                    //add player actor
                    var image = GameImage.getImage("resources/galaga.png", 109, 1, 16, 16);
                    GameActorData data = new GameActorData(0, 0, new Graphic(image), "player", 0);
                    var player = new Player(this, getWidth() / 2, getHeight() * .9, 5, 250, data);
                    add(player);

                    setState(State.PLAY_GAME);
                }
            }
            case PLAY_GAME -> {
                //spawn enemy
                spawnTimer -= getDeltaTime();
                if (spawnTimer <= 0) {
                    spawnTimer = RNG.range(100, 400); //smaller == more spawn
                    add(spawnEnemy(RNG.bool()));

                    if (RNG.bool()) {
                        add(spawnLowEnemy(RNG.bool()));//for scorpions
                    }
                }

            }
            case PLAYER_DEAD -> {
                stateTimer -= getDeltaTime();
                if (stateTimer <= 0) {
                    lives--;
                    if (lives <= 0) {
                        setState(State.GAME_OVER);
                    } else setState(State.START_LEVEL);
                }
            }
            case GAME_OVER -> {
                stateTimer -= getDeltaTime();
                removeAll(); // also removes the player explosion, does not remove player still
                if (stateTimer <= 0) {
                    setState(State.TITLE);
                }
            }
        }
    }


    @Override
    public void start() {
        timer.start();

        setState(State.INIT);
    }

    @Override
    public void setState(State state) {
        super.setState(state);

        switch (getState()) {
            case START_LEVEL -> {
                stateTimer = 1000;
                removeAll();
            }
            case PLAYER_DEAD -> {
                stateTimer = 1000;
                removePlayer();
            }
            case GAME_OVER -> {
                stateTimer = 1500;
                if (getScore() > highscore) {
                    FileIO.writeToFile("resources/game.txt", Integer.toString(getScore()));
                }
            }
        }
    }

    @Override
    public void update() {
        super.update();

        updateState();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        //draw state
        switch (getState()) {
            case TITLE -> {
                g.setColor(Color.BLUE);
                g.setFont(font);
                g.drawString("The Star War!",(getWidth() / 2) - 90, getHeight() / 2);

            }
            case START_LEVEL -> {
                g.setColor(Color.WHITE);
                g.setFont(font);
                g.drawString("READY!",(getWidth() / 2) - 60, getHeight() / 2);
            }
            case GAME_OVER -> {
                g.setColor(Color.WHITE);
                g.setFont(font);
                g.drawString("GAME OVER!",(getWidth() / 2) - 90, getHeight() / 2);
            }
        }

        g.setColor(Color.WHITE);
        g.setFont(font);
        g.drawString(String.format("%06d", getScore()),40,40);
        g.drawString(String.format("%03d", lives),getWidth() - 100,40);
        g.drawString(String.format("%06d", highscore), getWidth() / 2 - 30, 40);
    }

    public Enemy spawnEnemy(boolean isLeft) {
        double positionX = (isLeft) ? 0 : getWidth();
        double positionY = RNG.range(40,200);
        double speed = RNG.range(3,8);
        double velocityX = (isLeft) ? speed : -speed;
        double velocityY = 0;

        var images = new ArrayList<BufferedImage>();
        images.add(GameImage.getImage("resources/galaga.png", 109, 37,16,16));
        images.add(GameImage.getImage("resources/galaga.png", 127, 37,16,16));

        GameActorData data = new GameActorData(velocityX,velocityY, new Graphic(images, 8), "enemy", 3000);
        var enemy = new Enemy(this, positionX, positionY, speed, 200,500,data);

        return enemy;
    }

    public Enemy spawnLowEnemy(boolean isLeft) {
        double positionX = (isLeft) ? 0 : getWidth();
        double positionY = RNG.range(getHeight() * .4, (getHeight() * .75));
        double speed = RNG.range(3,6);
        double velocityX = (isLeft) ? speed : -speed;
        double velocityY = (speed)/2;

        var images = new ArrayList<BufferedImage>();
        if (isLeft) {
            images.add(GameImage.getImage("resources/galaga.png", 271, 109,16,16));//scorpion
        } else {
            images.add(GameImage.getImage("resources/galaga.png", 271, 91, 16, 16));
        }


        GameActorData data = new GameActorData(velocityX,velocityY, new Graphic(images, 8), "enemy", 3000);
        var enemy = new Enemy(this, positionX, positionY, speed, 3000,3001,data);

        return enemy;
    }


    public Explosion createExplosion(double x, double y){
        var images = new ArrayList<BufferedImage>();
        images.add(GameImage.getImage("resources/galaga.png", 289, 1,32,32));
        images.add(GameImage.getImage("resources/galaga.png", 323, 1,32,32));
        images.add(GameImage.getImage("resources/galaga.png", 357, 1,32,32));
        images.add(GameImage.getImage("resources/galaga.png", 391, 1,32,32));
        images.add(GameImage.getImage("resources/galaga.png", 425, 1,32,32));

        GameActorData data = new GameActorData(0,0, new Graphic(images, 24), "", 400);
        var explosion = new Explosion(this, x, y,data);

        return explosion;
    }

    public Explosion createPlayerExplosion(double x, double y){
        var images = new ArrayList<BufferedImage>();
        images.add(GameImage.getImage("resources/galaga.png", 145, 1,32,32));
        images.add(GameImage.getImage("resources/galaga.png", 179, 1,32,32));
        images.add(GameImage.getImage("resources/galaga.png", 213, 1,32,32));
        images.add(GameImage.getImage("resources/galaga.png", 247, 1,32,32));

        GameActorData data = new GameActorData(0,0, new Graphic(images, 24), "", 400);
        var explosion = new Explosion(this, x, y,data);

        return explosion;
    }
}




