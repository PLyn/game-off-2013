package com.Shadow.polyshifter;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.tools.imagepacker.TexturePacker2;
import com.badlogic.gdx.tools.imagepacker.TexturePacker2.Settings;

public class Main {
	
	private static boolean rebuildAtlas = true; //rebuilds atlas if set to true
	private static boolean drawDebugOutline = false; //draw debug lines around textures
	public static void main (String[] args) {
	if (rebuildAtlas) {
		Settings settings = new Settings();
		settings.maxWidth = 2048;
		settings.maxHeight = 2048;
		settings.debug = drawDebugOutline;
		TexturePacker2.process(settings, "assets-raw/images",
		"../PolyShifter-android/assets", 
		"polyshifter.pack");
		TexturePacker2.process(settings, "assets-raw/background",
				"../PolyShifter-android/assets/back", 
				"polyshifter-back.pack");
	}
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "PolyShifter";
		cfg.useGL20 = true;
		cfg.width = 800;
		cfg.height = 480;
		
		new LwjglApplication(new PolyShifter(), cfg);
	}
}
