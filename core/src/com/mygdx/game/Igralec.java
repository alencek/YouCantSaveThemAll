package com.mygdx.game;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;
import com.badlogic.gdx.utils.TimeUtils;

import static org.omg.CORBA.ORB.init;

public class Igralec extends GameObjectDynamic  implements Pool.Poolable{

    public static long createNextInTime;
    public float rotate;
    public float rotateSpeed;
    public boolean defended;
    public boolean act= false;
     public static Pool<Igralec> papirPool = new Pool<Igralec>(){

         @Override
         protected Igralec newObject() {
             return  new Igralec(0,0,64,64);
         }
     };

    //public void finish() { poolAsteroids.free(this); }
    @Override
    public void reset() {
        position.set(0,0);
        act = false;

    }
    public void init(float width,float height )
    {
        getRandomTopPosition(width, height);
        act = true;
    }
    @Override
      public  void finish(){
        papirPool.free(this);

    }




    Igralec(float x, float y, int width, int height) {
        super(x, y, width, height);

        Igralec.createNextInTime = 0;
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
        this.position.x = MathUtils.random(0, width - Assets.papirImage.getWidth());
        this.position.y = height;
    }

    @Override
    public void update(float deltaTime) {
        //super.update(deltaTime);
        position.y -= 100 * deltaTime + velocity.y;
//      position.x -= 100* deltaTime + velocity.x;

        bounds.x = position.x;
        bounds.y = position.y;
        rotate += deltaTime * rotateSpeed;
        if (rotate > 360) rotate -= 360; //prevent high numbers
        if (rotate < -360) rotate += 360;
    }

    @Override
    public Score updateScore(Score gameObjectScore) {
        gameObjectScore.score++;
        return gameObjectScore;

    }


    @Override
    public void render(SpriteBatch batch) {
        batch.draw(Assets.papirImage, position.x, position.y, bounds.width / 2, bounds.height / 2, bounds.width, bounds.height, 1, 1, rotate, 0, 0, (int) bounds.width, (int) bounds.height, false, false);
    }





}

