package com.ishka.goalie_oop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class Goalie extends GameObjectDynamic {
    Goalie(float x, float y, float width, float height) {
        super(x, y, width, height);
    }

    @Override
    public void finish() {

    }

    public void commandMoveRight() {
        int widthOfScreen = Gdx.graphics.getWidth();
        float widthOfGoalie = Assets.goalieImage.getWidth();

        position.x += GameConfig.SPEED * Gdx.graphics.getDeltaTime();
        if( position.x > widthOfScreen - widthOfGoalie ) {
            position.x = widthOfScreen - widthOfGoalie;
        }
    }

    public void commandMoveLeft() {
        position.x -= GameConfig.SPEED * Gdx.graphics.getDeltaTime();
        if(position.x < 0) position.x = 0;
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(Assets.goalieImage, position.x, position.y);
    }

    @Override
    public void update(float deltaTime) {
        bounds.x = position.x;
        bounds.y = position.y;
    }
}
