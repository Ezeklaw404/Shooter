package com.neumont.engine;

import com.neumont.engine.Game;

import javax.swing.*;

public class Window extends JFrame {
    Game game = null;
    public Window(Game game, String title, int x, int y, boolean fullscreen){
        this.game = game;


        setTitle(title);
        if (title.isEmpty()) setUndecorated(true);
        if  (fullscreen) setExtendedState(MAXIMIZED_BOTH);

        setLocation(x,y);
        setResizable(false);

        add(game);
        pack();
        setDefaultCloseOperation(EXIT_ON_CLOSE);


        setVisible(true);
    }

}
