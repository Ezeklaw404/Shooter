package com.neumont.engine;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class GameImage {

    public static BufferedImage getImage(String filePath) {
        BufferedImage image = null;
        try {
            File imageFile = new File(filePath);
            if (!imageFile.exists()) throw new Exception("Image " + filePath + " does not exist.");

            image = ImageIO.read(imageFile);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return image;
    }

    public static BufferedImage getImage(String filePath, int x, int y, int w, int h) {
        BufferedImage image = null;
        try {
            File imageFile = new File(filePath);
            if (!imageFile.exists()) throw new Exception("Image " + filePath + " does not exist.");

            image = ImageIO.read(imageFile);
            image = image.getSubimage(x, y, w, h);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return image;
    }
}
