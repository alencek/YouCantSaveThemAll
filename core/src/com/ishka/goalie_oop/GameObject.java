package com.ishka.goalie_oop;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class GameObject {
    Vector2 position;
    Rectangle bounds;

    GameObject (float x, float y, float width, float height) {

        // position in the center of the object
        this.position = new Vector2(x, y);
        // bounds half the width and height from the center
        this.bounds = new Rectangle(x, y, width, height);
    }

    public abstract void render(SpriteBatch batch);
}
