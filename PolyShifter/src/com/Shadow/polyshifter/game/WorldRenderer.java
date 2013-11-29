package com.Shadow.polyshifter.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;
import com.badlogic.gdx.utils.TimeUtils;
import com.Shadow.polyshifter.utils.Constants;
import com.Shadow.polyshifter.game.WorldController;
import com.Shadow.polyshifter.game.objects.Player;
import com.Shadow.polyshifter.game.objects.Shape;
import com.Shadow.polyshifter.game.screens.GameScreen;


public class WorldRenderer implements Disposable {
	private static final String TAG = WorldRenderer.class.getName();
	
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private WorldController worldController;
	TextureRegion back, score, immune, hud_back;
	
	float elapsedTime = 3.0f;
	String scorePhrase = "";
	
	
	FPSLogger logger = new FPSLogger();
	

	public WorldRenderer (WorldController worldController) {
		this.worldController = worldController;
		init();
	}
	private void init() {
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT);
		back = Assets.instance.backgrounds.ingame;
		score = Assets.instance.score.scoreAsset;
		immune = Assets.instance.immune.immuneAsset;
		hud_back = Assets.instance.backgrounds.hud;
	}
	@Override
	public void dispose () {
		batch.dispose();
	}

	public void render() {
		RenderWorld(batch);
		RenderGUI(batch);
		
	}
	private void RenderGUI(SpriteBatch batch) {
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		if(GameScreen.ispaused){
			Assets.instance.font.Big.draw(batch, "Tap or Click to Resume", Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT);
		}
		if(GameScreen.gameOver){
			RenderGUIGameOver(batch);	
		}
		else{
			RenderHUDBackground(batch);
			RenderToolTip(batch);	
			RenderGUIScore(batch);
			RenderGUICurrentShape(batch);
			RenderGUIFPS(batch);
			RenderGUIFoodProgress(batch);
			RenderSpeedAndScore(batch);
		}
	
		batch.end();
	}

	private void RenderHUDBackground(SpriteBatch batch) {
		batch.draw(hud_back, 0, Constants.VIEWPORT_HEIGHT - 70);
		
	}
	private void RenderSpeedAndScore(SpriteBatch batch) {
		float x = 20;
		float y = (float) (Constants.VIEWPORT_HEIGHT - (Constants.VIEWPORT_HEIGHT * 0.06));
		int spot = Float.toString(worldController.stage.player.velocity).indexOf(".")+ 2;
		Assets.instance.font.Normal.draw(batch, "Speed Multiplier: X " + Float.toString(worldController.stage.player.velocity).substring(0, spot), x , y);
		/*if(worldController.stage.player.hasPowerUp(0)){
			Assets.instance.font.Normal.draw(batch, "Score Multiplier: + " + worldController.ShapeMultiplier * 2, x , y - 15);
		}
		else{
		Assets.instance.font.Normal.draw(batch, "Score Multiplier: + " + worldController.ShapeMultiplier, x , y - 15);
		}*/
		for(int i = 0 ;i <= worldController.stage.player.lives ; i++ ){
		Assets.instance.font.Normal.draw(batch, "Lives: " + worldController.stage.player.lives, x , y - 15);
		}
	}
	private void RenderToolTip(SpriteBatch batch) {
		if(worldController.stage.player.hasPowerUp(Constants.DOUBLESCORE)){
			float x = 225;
			float y = (float) (Constants.VIEWPORT_HEIGHT - (Constants.VIEWPORT_HEIGHT * 0.08));
			
			batch.draw(score, x, y);
			Assets.instance.font.Normal.draw(batch, "" + (int) worldController.stage.player.doubleScoreTimeLeft, x + 10, y - 5);	
		}
		if(worldController.stage.player.hasPowerUp(Constants.IMMUNE)){
			float x = 275;
			float y = (float) (Constants.VIEWPORT_HEIGHT - (Constants.VIEWPORT_HEIGHT * 0.08));
			batch.draw(immune, x, y);
			Assets.instance.font.Normal.draw(batch, "" + (int) worldController.stage.player.immuneTimeLeft, x + 10, y - 5);	
		}
		if(worldController.stage.scoreElapsedTime > 0){
			if(worldController.stage.lastShape == 0){
				scorePhrase = worldController.phrase;
			}
			else if(worldController.stage.lastShape == 1){
				Assets.instance.font.Normal.setColor(Color.ORANGE);
				scorePhrase = worldController.phrases[4];
			}
			else if(worldController.stage.lastShape == 2){
				Assets.instance.font.Normal.setColor(Color.CYAN);
				scorePhrase = worldController.phrases[5];
			}
			else if(worldController.stage.lastShape == 3){
				Assets.instance.font.Normal.setColor(Color.GREEN);
				scorePhrase = worldController.phrases[6];
			}
			else if(worldController.stage.lastShape == 4){
				Assets.instance.font.Normal.setColor(Color.RED);
				scorePhrase = worldController.phrases[7];
			}
			if(worldController.stage.lifeUp){
				Assets.instance.font.Normal.draw(batch, "Life + 1!!", (Constants.VIEWPORT_WIDTH / 2) - 25  , Constants.VIEWPORT_HEIGHT - 100);
				worldController.stage.lifeUp = false;
			}
			Assets.instance.font.Normal.draw(batch, scorePhrase, worldController.stage.lastX - 15, worldController.stage.lastY + 15);
			Assets.instance.font.Normal.draw(batch, "+ " + Integer.toString(worldController.currentScore), worldController.stage.lastX, 
				worldController.stage.lastY);
			Assets.instance.font.Normal.setColor(Color.WHITE);
			worldController.stage.scoreDisplayed = true;
			}
	}
	private void RenderGUIFoodProgress(SpriteBatch batch) {
		float x = (float) (Constants.VIEWPORT_WIDTH - (Constants.VIEWPORT_WIDTH * 0.33));
		float y = (float) (Constants.VIEWPORT_HEIGHT - (Constants.VIEWPORT_HEIGHT * 0.02));
		int counter = 10;
		int shapeCount = 0;
		int progressLimit = 10;
		
		Player hero = worldController.stage.player;
		
		TextureRegion currentShape = new TextureRegion();
		Assets.instance.font.Normal.draw(batch, "Progress to next Shape", x + 50, y);
		
		if(hero.isTriangle){
			currentShape = Assets.instance.triangle.triangleAsset;
			shapeCount = worldController.triangleCount;
		}
		else if(hero.isSquare){
			currentShape = Assets.instance.square.squareAsset;
			shapeCount = worldController.squareCount;
		}
		else if(hero.isPentagon){
			currentShape = Assets.instance.pentagon.pentagonAsset;
			shapeCount = worldController.pentagonCount;
		}
		else if(hero.isStar){
			currentShape = Assets.instance.star.starAsset;
			shapeCount = worldController.starCount;
		}
		else if(hero.isCircle){
			currentShape = Assets.instance.circle.circleAsset;
			shapeCount = worldController.circleCount;
		}
		
		for(int i = 0 ; i < progressLimit ; i++){
			if(i < shapeCount)
				batch.setColor(Color.DARK_GRAY);
			batch.draw(currentShape, x + (i*25), y - 25, 0, 0, 15, 15, 1, 1, 270, false);
			batch.setColor(Color.WHITE);
		}
	}
	private void RenderGUIGameOver(SpriteBatch batch) {
		float x = Constants.VIEWPORT_WIDTH / 2;
		float y = Constants.VIEWPORT_HEIGHT / 2;
			Assets.instance.font.Big.draw(batch, "GAME OVER", x - 100, y);
	}
	private void RenderGUIFPS(SpriteBatch batch) {
		float x = (float) (Constants.VIEWPORT_WIDTH - (Constants.VIEWPORT_WIDTH * 0.08));
		float y = 20;
		int fps = Gdx.graphics.getFramesPerSecond();
		
		BitmapFont fpsFont = Assets.instance.font.Normal;
		if (fps >= 45) {
			// 45 or more FPS show up in green
			fpsFont.setColor(0, 1, 0, 1);
		} else if (fps >= 30) {
			// 30 or more FPS show up in yellow
			fpsFont.setColor(1, 1, 0, 1);
		} else {
			// less than 30 FPS show up in red
			fpsFont.setColor(1, 0, 0, 1);
		}

		fpsFont.draw(batch, "FPS: " + fps, x, y);
		fpsFont.setColor(1, 1, 1, 1); // white

	}
	private void RenderGUICurrentShape(SpriteBatch batch) {
		float x = Constants.VIEWPORT_WIDTH/2;
		float y = (float) (Constants.VIEWPORT_HEIGHT - (Constants.VIEWPORT_HEIGHT * 0.02));
		
		Assets.instance.font.Normal.draw(batch, "Food to Eat", x, y);
		Player hero = worldController.stage.player;
		if(hero.isTriangle)
			batch.draw(Assets.instance.triangle.triangleAsset, x + 20, y - 15, 0, 0, 25, 25, 1, 1, 270, false);
		else if(hero.isSquare)
			batch.draw(Assets.instance.square.squareAsset, x + 20, y - 15, 0, 0, 25, 25, 1, 1, 270, false);
		else if(hero.isPentagon)
			batch.draw(Assets.instance.pentagon.pentagonAsset, x + 20, y - 15, 0, 0, 25, 25, 1, 1, 270, false);
		else if(hero.isStar)
			batch.draw(Assets.instance.star.starAsset, x + 20, y - 15, 0, 0, 25, 25, 1, 1, 270, false);
		else if(hero.isCircle)
			batch.draw(Assets.instance.circle.circleAsset, x + 20, y - 15, 0, 0, 25, 25, 1, 1, 270, false);
		
	}
	private void RenderGUIScore(SpriteBatch batch) {
		float x = 20;
		float y = (float) (Constants.VIEWPORT_HEIGHT - (Constants.VIEWPORT_HEIGHT * 0.03));
		
		Assets.instance.font.Normal.draw(batch, "Score:" + worldController.score, x, y);		
	}
	private void RenderWorld(SpriteBatch batch) {
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		drawBack(batch);
		worldController.stage.render(batch);
		batch.end();
		
	}
	private void drawBack(SpriteBatch batch) {
		batch.draw(back, 0, 0);
		
	}
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}
}
