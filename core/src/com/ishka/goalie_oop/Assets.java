package com.ishka.goalie_oop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class Assets {
    public static Texture ballImage;
    public static Texture goalieImage;
    public static Texture redCardImage;
    public static Texture yellowCardImage;
    public static Sound crowdSound;
    public static BitmapFont font;




    public static void Load() {
        goalieImage = new Texture(Gdx.files.internal("images/icons8-syringe-48.png"));
        ballImage = new Texture(Gdx.files.internal("images/boy.png"));
        yellowCardImage = new Texture(Gdx.files.internal("images/coronavirus.png"));
//        crowdSound = Gdx.audio.newSound(Gdx.files.internal("pick.wav"));
        font = new BitmapFont();
        font.getData().setScale(2);
    }

    public static void dispose() {
        ballImage.dispose();

        yellowCardImage.dispose();
        goalieImage.dispose();
        crowdSound.dispose();
        font.dispose();
    }
}
