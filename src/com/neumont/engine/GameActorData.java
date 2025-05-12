package com.neumont.engine;

import java.awt.image.BufferedImage;

public record GameActorData(double velocityX, double velocityY, Graphic graphic, String tag, int lifeSpan) {

}
