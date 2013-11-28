package com.Shadow.polyshifter;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.Shadow.polyshifter.game.Assets;
import com.Shadow.polyshifter.game.WorldController;
import com.Shadow.polyshifter.game.WorldRenderer;
import com.Shadow.polyshifter.game.screens.MenuScreen;

public class PolyShifter extends Game {
	private static final String TAG = PolyShifter.class.getName();

	@Override
	public void create() {
		// Set Libgdx log level to DEBUG to see errors
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		Assets.instance.init(new AssetManager());
		setScreen(new MenuScreen(this));
	}

	
}
