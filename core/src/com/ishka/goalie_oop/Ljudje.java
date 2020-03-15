package com.ishka.goalie_oop;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.TimeUtils;

public class Ljudje extends GameObjectDynamic implements Pool.Poolable {
    public static long createNextInTime;
    public float rotate;
    public float rotateSpeed;
    public boolean defended; // if the goalie has defended the ball
    public boolean active = false;
    public static Pool<Ljudje> ballPool = new Pool<Ljudje>() {
        @Override
        protected Ljudje newObject() {
            return new Ljudje(0, 0, 64, 64);
        }
    };

    @Override
    public void finish() {
        ballPool.free(this);
    }

    Ljudje(float x, float y, int width, int height) {
        super(x, y, width, height);

        Ljudje.createNextInTime = 0;
        this.rotate = rotate;
        this.rotateSpeed = MathUtils.random(-360, 360);
        this.defended = false;
    }

    public static void setCreateNextInTime(long nextInTime) {
        createNextInTime = nextInTime;
    }

    public static boolean isTimeToCreateNew() {
        return TimeUtils.nanoTime() % createNextInTime == 0;
    }

    public void getRandomTopPosition(float width, float height) {
        this.position.x = MathUtils.random(0, width - Assets.ballImage.getWidth());
        this.position.y = height;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        rotate += deltaTime*rotateSpeed;
        if (rotate>360) rotate -= 360; //prevent high numbers
        if (rotate<-360) rotate += 360;
    }

    @Override
    public Score updateScore(Score gameObjectScore) {
        gameObjectScore.goalsDefendedScore++;
        return gameObjectScore;
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(Assets.ballImage, position.x, position.y, bounds.width / 2, bounds.height / 2, bounds.width, bounds.height, 1, 1, rotate, 0, 0, (int)bounds.width, (int)bounds.height, false, false);
    }

    @Override
    public void reset() {
        position.set(0, 0);
        active = false;
    }

    public void init(float width, float height){
        getRandomTopPosition(width, height);
        active = true;
    }
}
