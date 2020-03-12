package com.ishka.goalie_oop;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameConfig{
    public static int START_HEALTH = 3;

    public static int SPEED = 600; //pixels per second
    public static int SPEED_GOALIE = 200;
    public static int SPEED_BALL = 100;
    public static int SPEED_CARD = 110;
    public static long CREATE_BALL_TIME = 1000000000; // 1 Billion ns
    public static long CREATE_CARD_TIME = 2000000000; // 2 Billion ns
}
