package org.delusion.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import org.delusion.game.GameMain;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Test Game";
		config.fullscreen = true;
		config.resizable = false;
		config.useGL30 = true;
		config.width = 1;
		config.height = 1;


		new LwjglApplication(new GameMain(), config);
	}
}
