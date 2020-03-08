package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.ApplicationListenerDemo;
import com.mygdx.game.ApplicationListenerDemo;
import com.mygdx.game.AstronautsGame;

public class DesktopLauncherApplicationListenerDemo {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		new LwjglApplication(new ApplicationListenerDemo(), config);
	}
}
