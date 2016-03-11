package com.tspgame.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.tspgame.TSPGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Team-4x4's Hack 'n Slash";
		config.height = 576;
		config.width = 512;
		new LwjglApplication(new TSPGame(), config);
	}
}