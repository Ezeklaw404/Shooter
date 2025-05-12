package com.neumont.game;

import com.neumont.engine.Game;
import com.neumont.engine.GameActor;
import com.neumont.engine.GameActorData;

public class Explosion extends GameActor {
    public Explosion(Game game, double positionX, double positionY, GameActorData data) {
        super(game, positionX, positionY, data);
    }
}
