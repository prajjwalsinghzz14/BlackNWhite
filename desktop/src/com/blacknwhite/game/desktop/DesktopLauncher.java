package com.blacknwhite.game.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.blacknwhite.game.BlackNWhite;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = BlackNWhite.WIDTH;
		config.height = BlackNWhite.HEIGHT;
		config.title = BlackNWhite.TITLE;
		config.addIcon("icon.png", Files.FileType.Internal);

		new LwjglApplication(new BlackNWhite(), config);
	}
}
