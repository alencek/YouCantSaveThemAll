package com.mygdx.game;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;
import com.badlogic.gdx.utils.TimeUtils;

import static org.omg.CORBA.ORB.init;

public abstract class GameObjectDynamic extends GameObject{
    public Vector2 velocity;
    private long createTime;




    GameObjectDynamic (float x, float y, float width, float height) {
        super(x, y, width, height);

        bounds.width = 64;
        bounds.height = 64;

        this.velocity = new Vector2();
        this.createTime = TimeUtils.nanoTime();
    }

    public abstract void update(float deltaTime);

    public Score updateScore(Score gameObjectScore) {
        gameObjectScore.score++;
        gameObjectScore.health--;

        return gameObjectScore;
    }
    public abstract void finish();

  //  public abstract void  spawnElement();


}
