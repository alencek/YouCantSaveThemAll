package com.ishka.goalie_oop;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;

public abstract class GameObjectDynamic extends GameObject {
    private Vector2 velocity;
    private long createTime;

    GameObjectDynamic (float x, float y, float width, float height) {
        super(x, y, width, height);

        bounds.width = 64;
        bounds.height = 64;

        this.velocity = new Vector2();
        this.createTime = TimeUtils.nanoTime();
    }

    public abstract void finish();

    @Override
    public void render(SpriteBatch batch) {}

    public void update(float deltaTime) {
        position.y -= 100 * deltaTime + velocity.y;
//        position.x -= 100* deltaTime + velocity.x;

        bounds.x = position.x;
        bounds.y = position.y;
    }

    public Score updateScore(Score gameObjectScore) {
        gameObjectScore.goalsDefendedScore++;
        gameObjectScore.goalieHealth--;

        return gameObjectScore;
    }

}
