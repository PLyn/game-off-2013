package com.Shadow.polyshifter.game.screens;

import com.Shadow.polyshifter.game.Assets;
import com.Shadow.polyshifter.utils.Constants;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class GameOverScreen extends AbstractScreenObject{
	private static final String TAG = GameOverScreen.class.getName();
	SpriteBatch batch;
	TextureRegion GameOver;
	int scoreCount = 0;
	private OrthographicCamera camera;
	TextureRegion back;
	int topScore = 0;
	Preferences prefs;
	int score;
	public GameOverScreen(Game game) {
		super(game);
		
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0,0,0,0);
		// Clears the screen
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(back, 0, 0);
		Assets.instance.font.Big.draw(batch, "Game Over!", (Constants.VIEWPORT_WIDTH / 2) - 50, Constants.VIEWPORT_HEIGHT - 140);
		Assets.instance.font.Normal.draw(batch, "You did not survive but don't worry! there is always next time", (Constants.VIEWPORT_WIDTH / 2) - 170, Constants.VIEWPORT_HEIGHT - 170);
		
		Assets.instance.font.Big.draw(batch, "Top Score of all time: " + score, (Constants.VIEWPORT_WIDTH / 2) - 100, Constants.VIEWPORT_HEIGHT - 190);
		
		
		if(scoreCount < GameScreen.finalScore ){
			scoreCount += 20;
			Assets.instance.font.Big.draw(batch, "Score: " + (int)scoreCount, (Constants.VIEWPORT_WIDTH / 2) - 50, Constants.VIEWPORT_HEIGHT - 300);
		}
		if(scoreCount >= GameScreen.finalScore){
		scoreCount = GameScreen.finalScore;
		Assets.instance.font.Big.draw(batch, "Score: " + scoreCount, (Constants.VIEWPORT_WIDTH / 2) - 40, Constants.VIEWPORT_HEIGHT - 300);
		}
		Assets.instance.font.Normal.draw(batch, "Click or Tap to Continue...", (Constants.VIEWPORT_WIDTH / 2) - 50, Constants.VIEWPORT_HEIGHT - 350);
		batch.end();
		if(Gdx.input.justTouched())
			game.setScreen(new MenuScreen(game));
	}

	
	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT);
		back = Assets.instance.backgrounds.end;
		prefs = Gdx.app.getPreferences("score");
		score = prefs.getInteger("score", 0);
		if(GameScreen.finalScore > score){
			prefs.putInteger("score", GameScreen.finalScore);
			score = GameScreen.finalScore;
		}
	}

	@Override
	public void hide() {
		batch.dispose();
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}
}
