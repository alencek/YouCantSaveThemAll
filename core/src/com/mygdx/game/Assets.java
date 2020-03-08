package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class Assets {

    public static Texture  PlayerImage;
    public static Texture igralec_image;
    public static Sound crowdSound;
    public static BitmapFont font;

    public static void Load() {
        PlayerImage = new Texture(Gdx.files.internal("trash.png"));
        igralec_image = new Texture(Gdx.files.internal("paper.png"));

        //yellowCardImage = new Texture(Gdx.files.internal("images/yellow-card64.png"));
//        crowdSound = Gdx.audio.newSound(Gdx.files.internal("pick.wav"));
        font = new BitmapFont();
        font.getData().setScale(2);
    }

    public static void Dispose() {
        igralec_image.dispose();

        // yellowCardImage.dispose();
        PlayerImage.dispose();
//        crowdSound.dispose();
        font.dispose();
    }
}