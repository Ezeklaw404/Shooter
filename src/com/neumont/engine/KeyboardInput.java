package com.neumont.engine;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardInput implements KeyListener {
    boolean[] keyDown = new boolean[255];

    public boolean isKeyDown(int keyCode) {
        return keyDown[keyCode];
    }

    @Override
    public void keyTyped(KeyEvent e) {
        //text entry
    }

    @Override
    public void keyPressed(KeyEvent e) {
        keyDown[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keyDown[e.getKeyCode()] = false;
    }
}
