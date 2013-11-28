package com.Shadow.polyshifter.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.Shadow.polyshifter.game.Assets;
import com.Shadow.polyshifter.utils.Constants;

public class MenuScreen extends AbstractScreenObject{
	private SpriteBatch batch;
	private OrthographicCamera camera;
	TextureRegion back , start, title;
	float color = 1;
	int fadeTime = 0;

	public MenuScreen(Game game) {
		super(game);
	}
	
	@Override
	public void render (float deltaTime) {
	Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
	Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
	
	batch.begin();
	
	batch.draw(back, 0, 0);
	batch.draw(title, (Constants.VIEWPORT_WIDTH / 2) - 200, Constants.VIEWPORT_HEIGHT - 100);
	batch.draw(start, (Constants.VIEWPORT_WIDTH / 2) - 200, 50);
	
	batch.end();
	if(Gdx.input.justTouched()){
		game.setScreen(new TutorialScreen(game));
	}
	} 

	@Override
	public void resize(int width, int height) {
		
	}

	@Override
	public void show() {
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT);
		back = Assets.instance.backgrounds.intro;
		title = Assets.instance.backgrounds.title;
		start = Assets.instance.backgrounds.start;
		
	}

	@Override
	public void hide() {
		batch.dispose();
		//skinLibgdx.dispose();
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

}
