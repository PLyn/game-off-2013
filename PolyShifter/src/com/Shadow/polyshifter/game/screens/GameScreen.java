package com.Shadow.polyshifter.game.screens;

import com.Shadow.polyshifter.PolyShifter;
import com.Shadow.polyshifter.game.WorldController;
import com.Shadow.polyshifter.game.WorldRenderer;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.TimeUtils;

public class GameScreen extends AbstractScreenObject{
	private static final String TAG = GameScreen.class.getName();
	
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Texture texture;
	private Sprite sprite;
	
	int timer = 0;
	public static boolean gameOver;
	public static int finalScore = 0;
	private WorldController worldController;
	private WorldRenderer worldRenderer;
	
	private float elapsedTime;
	float color = (float) 0.5;
	
	boolean ispaused;
	public GameScreen(Game game) {
		super(game);
	}

	@Override
	public void render(float delta) {
		if(gameOver){
			if(worldController.stage.time > 3){
				finalScore = worldController.score;
				game.setScreen(new GameOverScreen(game));
			}
			color-= 0.01;
			if (color <= 0) {color = 0;}
		}
		elapsedTime = TimeUtils.nanoTime();
		// Do not update game world when paused.
		if(!ispaused)
		{
			// Update game world by the time that has passed
			// since last rendered frame.
			worldController.update(Gdx.graphics.getDeltaTime());
		}
		// Sets the clear screen color to: Cornflower Blue
		Gdx.gl.glClearColor(color, color, color, color);
		// Clears the screen
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		// Render game world to screen
		worldRenderer.render();
		
		
	}

	@Override
	public void resize(int width, int height) {
		worldRenderer.resize(width, height);
		
	}

	@Override
	public void show() {
		// Initialize controller and renderer
		worldController = new WorldController();
		worldRenderer = new WorldRenderer(worldController);
		ispaused = false;
		Gdx.input.setCatchBackKey(true);
	}

	@Override
	public void hide() {
		worldRenderer.dispose();
		Gdx.input.setCatchBackKey(false);
	}

	@Override
	public void pause() {
		ispaused = true;
	}
	@Override
	public void resume () {
		super.resume();
		// Only called on Android!
		ispaused = false;
	}
	
}
