package com.neumont.engine;

import com.neumont.game.Explosion;
import com.neumont.game.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public abstract class Game extends JPanel implements ActionListener {
    public enum State {
        INIT, TITLE, START_GAME, START_LEVEL, PLAY_GAME, PLAYER_DEAD, GAME_OVER
    }

    KeyboardInput keyboardInput;
    private State state;
    protected Timer timer;
    protected int fps;

    BufferedImage image = null;
    ArrayList<GameActor> actors = new ArrayList<>();
    public Game(int width, int height, int fps){
        this.fps = fps;
        setSize(width, height);
        setBackground(Color.BLACK);

        setFocusable(true);
        keyboardInput = new KeyboardInput();
        addKeyListener(keyboardInput);

        timer = new Timer(1000 / fps,this);

    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public KeyboardInput getKeyboardInput() {
        return keyboardInput;
    }

    public abstract void updateState();

    protected void update() {
        if (keyboardInput.isKeyDown(KeyEvent.VK_ESCAPE)) {
            System.exit(0);
        }

        //update actors
        for (int i = 0; i < actors.size(); i++) {
            actors.get(i).update(1000 / fps);
        }

        //check collision
        for (int i = 0; i < actors.size(); i++) {
                var actor1 = actors.get(i);
            for (int j = 0; j < actors.size(); j++) {
                var actor2 = actors.get(j);

                if (actor1 == actor2) continue; //don't check yourself

                //check intersection
                if  (actor1 instanceof Collidable && actor2 instanceof Collidable) {
                    if (actor1.intersects(actor2)) {
                        ((Collidable) actor1).onCollisionEnter(actor2);
                        ((Collidable) actor2).onCollisionEnter(actor1);    //todo player destroys missiles by touch, but also dies
                    }
                }

            }
        }


        //remove destroyed actors
        for (int i = actors.size() - 1; i > 0; i--) {
            if (actors.get(i).destroyed) {
                remove(actors.get(i));
            }
        }

    }
    public int getDeltaTime(){
        return (1000 / fps);
    }

    public abstract void start();

    public void add(GameActor actor){
        actors.add(actor);
    }

    public void remove(GameActor actor){
        actors.remove(actor);
    }

    public void removePlayer(){
        actors.remove(actors.getFirst());
    }

    public void removeAll(){
        actors.removeAll(actors);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        //draw actors
        for (GameActor actor : actors) {
            actor.draw(g);
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(getWidth(), getHeight());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        update();
        repaint();
    }
}
