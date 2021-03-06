package com.ishka.goalie_oop;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Pool;

public class Corona extends Virus implements Pool.Poolable {
    Corona(float x, float y, int width, int height) {
        super(x, y, width, height);
    }

    public static Pool<Corona> yellowCardPool = new Pool<Corona>() {
        @Override
        protected Corona newObject() {
            return new Corona(0, 0, 64, 64);
        }
    };

    @Override
    public void finish() {
        yellowCardPool.free(this);
    }

    //    @Override
    public void getRandomTopPosition(float width, float height) {
        this.position.x = MathUtils.random(0, width - Assets.yellowCardImage.getWidth());
        this.position.y = height;

    }

    public static void setCreateNextInTime(long createNextInTime) {
        Virus.createNextInTime = createNextInTime;
    }

    @Override
    public void render(SpriteBatch batch){
        batch.draw(Assets.yellowCardImage, position.x, position.y);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
    }

    @Override
    public void reset() {
        position.set(0,0);
    }
}
