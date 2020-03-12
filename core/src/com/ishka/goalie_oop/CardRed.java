package com.ishka.goalie_oop;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

public class CardRed extends Card {
    CardRed (float x, float y, int width, int height) {
        super(x, y, width, height);
    }

//    @Override
    public void getRandomTopPosition(float width, float height) {
        this.position.x = MathUtils.random(0, width - Assets.redCardImage.getWidth());
        this.position.y = height;
    }

    public static void setCreateNextInTime(long createNextInTime) {
        Card.createNextInTime = createNextInTime;
    }

    @Override
    public void finish() {

    }

    @Override
    public void render(SpriteBatch batch){
        batch.draw(Assets.redCardImage, position.x, position.y);
    }
}
