package com.HyperSphere.desktop;
import com.HyperSphere.Manager.Launcher;
import com.HyperSphere.Manager.DI;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Game";
		config.width = 1000;
		config.height = 1000;
		config.resizable = true;
		config.forceExit = true;
		config.useGL30 = false;
		config.vSyncEnabled = true;

		new LwjglApplication(new Launcher(new DI() {
			@Override
			public void sendAnalytic(int code, String s) {
				System.out.println("Message:" + s + " Code:" + code);
			}

			@Override
			public void purchase(String s) {
				System.out.println("Attempted Purchase:" + s);
			}

			@Override
			public void verifyPurchase(String s) {
				System.out.println("Verify Purchase:" + s);
			}
		}), config);
	}
}
