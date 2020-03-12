package com.ishka.goalie_oop;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.TimeUtils;

public abstract class Card extends GameObjectDynamic {
    public static long createNextInTime;
    public long createTime;

    Card(float x, float y, int width, int height) {
        super(x, y, width, height);
//        Card.createNextInTime = createNextInTime;
//        this.createTime = createTime;
    }

    static boolean isTimeToCreateNew() {
        return TimeUtils.nanoTime() % createNextInTime == 0;
    }

    @Override
    public Score updateScore(Score gameObjectScore) {
        gameObjectScore.setGoalieHealth(gameObjectScore.getGoalieHealth() - 1);
        return gameObjectScore;
    }


//    public abstract GameObjectDynamic getRandomTopPosition(float width, float height);

}
