package com.ishka.goalie_oop;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Score extends GameObject {
    public int goalsDefendedScore;
    public int goalieHealth;

    Score(float x, float y, float width, float height) {
        super(x, y, width, height);

        this.goalsDefendedScore = 0;
        this.goalieHealth = GameConfig.START_HEALTH;
    }

    public boolean isEnd(){
        return (goalieHealth <= 0);
    }

    @Override
    public void render(SpriteBatch batch) {
        Assets.font.setColor(Color.YELLOW);
        Assets.font.draw(batch, "" + getGoalsDefendedScore(), bounds.width - 30, bounds.height - 20);
        Assets.font.setColor(Color.RED);
        Assets.font.draw(batch, "" + getGoalieHealth(), 20,  bounds.height - 20);
    }


    public int getGoalsDefendedScore() {
        return goalsDefendedScore;
    }

    public void setGoalsDefendedScore(int goalsDefendedScore) {
        this.goalsDefendedScore = goalsDefendedScore;
    }

    public int getGoalieHealth() {
        return goalieHealth;
    }

    public void setGoalieHealth(int goalieHealth) {
        this.goalieHealth = goalieHealth;
    }


}
