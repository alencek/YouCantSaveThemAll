package com.mygdx.game;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Score extends GameObject {
    public int score;
    public int health;

    Score(float x, float y, float width, float height) {
        super(x, y, width, height);

        this.score = 0;
        this.health = AstronautsGame.START_HEALTH;
    }

    public boolean isEnd(){
        return (health <= 0);
    }

    @Override
    public void render(SpriteBatch batch) {
        Assets.font.setColor(Color.YELLOW);
        Assets.font.draw(batch, "" + getScore(), bounds.width - 30, bounds.height - 20);
        Assets.font.setColor(Color.RED);
        Assets.font.draw(batch, "" + getHealth(), 20,  bounds.height - 20);
    }


    public int getScore() {
        return score;
    }

    public void setScore(int Score) {
        this.score = Score;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int Health) {
        this.health = Health;
    }


}
