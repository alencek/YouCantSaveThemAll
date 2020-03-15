package com.ishka.goalie_oop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class EndMsg extends GameObject {
    EndMsg(float x, float y, float width, float height) {
        super(x, y, width, height);
    }

    @Override
    public void render(SpriteBatch batch) {
        Assets.font.setColor(Color.RED);
        Assets.font.draw(batch, "THE END", Gdx.graphics.getHeight() / 2, Gdx.graphics.getHeight() / 2);

    }
}
