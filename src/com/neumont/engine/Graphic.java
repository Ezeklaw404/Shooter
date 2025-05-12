package com.neumont.engine;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;

public class Graphic {
    private int fps;
    private int frame = 0;
    private int frameTime = 0;
    private int frameTimer = 0;
    private ArrayList<BufferedImage> images = new ArrayList<>();

    public int getFrame() {
        return frame;
    }

    public void setFrame(int frame) {
        if (frame >= 0 && frame < images.size()) this.frame = frame;
    }

    public int getNumFrames() {
        return images.size();
    }

    public int getWidth() {
        return (images.size() > 0) ? images.get(0).getWidth() : 0;
    }

    public int getHeight() {
        return (images.size() > 0) ? images.get(0).getHeight() : 0;
    }

    public Graphic(BufferedImage image) {
        images.add(image);
    }

    public Graphic(BufferedImage[] images, int fps) {
        Collections.addAll(this.images, images);
        this.fps = fps;
        frameTime = 1000 / fps;
        frameTimer = frameTime;
    }

    public Graphic(ArrayList<BufferedImage> images, int fps) {
        this.images = images;
        this.fps = fps;
        frameTime = 1000 / fps;
        frameTimer = frameTime;
    }

    public Image getImage(int frame) {
        return (frame >= 0 && frame < images.size()) ? images.get(frame) : null;
    }

    public Image getImage() {
        return getImage(frame);
    }

    public void update(int deltaTime) {
        if (getNumFrames() == 1) return;

        frameTimer -= deltaTime;
        if (frameTimer <= 0) {
            frameTimer = frameTime;
            frame++;
            frame = frame % getNumFrames();
        }
    }
}
