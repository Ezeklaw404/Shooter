package com.neumont.game;

import com.neumont.engine.Collidable;
import com.neumont.engine.Game;
import com.neumont.engine.GameActor;
import com.neumont.engine.GameActorData;

public class Rocket extends GameActor implements Collidable {


    public Rocket(Game game, double positionX, double positionY, GameActorData data) {
        super(game, positionX, positionY, data);
    }

    @Override
    public void onCollisionEnter(GameActor other) {
        if (!(other instanceof Rocket) && !other.getTag().equalsIgnoreCase(getTag())) {
            destroyed = true;
        }
    }
}
