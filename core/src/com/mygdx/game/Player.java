package com.mygdx.game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
public class Player extends GameObjectDynamic {

        Player(float x, float y, float width, float height) {
            super(x, y, width, height);
        }

        public void commandMoveRight() {
            int widthScreen = Gdx.graphics.getWidth();
            float widthOfGoalie = Assets.kosImage.getWidth();

            position.x += AstronautsGame.SPEED * Gdx.graphics.getDeltaTime();
            if( position.x > widthScreen - widthOfGoalie ) {
                position.x = widthScreen - widthOfGoalie;
            }
        }

        public void commandMoveLeft() {
            position.x -= AstronautsGame.SPEED * Gdx.graphics.getDeltaTime();
            if(position.x < 0) position.x = 0;
        }



    @Override
        public void render(SpriteBatch batch) {
            batch.draw(Assets.kosImage, position.x,position.y);
        }

        @Override
        public void update(float deltaTime) {
            bounds.x = position.x;
            bounds.y = position.y;
        }

    @Override
    public void finish() {

    }


    // @Override
   // public void spawnElement() {
    //}
}




