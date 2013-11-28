package com.Shadow.polyshifter.game.screens;

import com.Shadow.polyshifter.game.Assets;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;

public abstract class AbstractScreenObject implements Screen {
	protected Game game;

	public AbstractScreenObject(Game game)
	{
		this.game = game;
	}

	public abstract void render(float delta);
	public abstract void resize(int width, int height);
	public abstract void show();
	public abstract void hide();
	public abstract void pause();

	@Override
	public void resume() {
		Assets.instance.init(new AssetManager());
	}
	
	@Override
	public void dispose() {
		Assets.instance.dispose();
		
	}

}
