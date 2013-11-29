package com.Shadow.polyshifter.game;

import com.Shadow.polyshifter.game.objects.Player;
import com.Shadow.polyshifter.game.objects.Shape;
import com.Shadow.polyshifter.game.screens.GameScreen;
import com.Shadow.polyshifter.utils.Constants;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

public class WorldController  extends InputAdapter{
	private static final String TAG = WorldController.class.getName();
	
	public Stage stage;
	
	public Rectangle r1 = new Rectangle();
	public Rectangle r2 = new Rectangle();
	
	//counters of how much of each shape eaten
	int triangleCount = 0;
	int squareCount = 0;
	int pentagonCount = 0;
	int starCount = 0;
	int circleCount = 0;
	
	//score and its multipliers
	public int score = 0;
	public int currentScore = 0;
	float ShapeMultiplier = Constants.SHAPE_MULTIPLIER;
	float rareMultiplier = 1;
	float legendMultiplier = 1;
	
	boolean gameOver;
	
	
	public String phrase;
	public String[] phrases = {"Nice!", "Great!", "Good Job!", "Keep it up!", "Rare!!!", "Legend!!!", "Double Score!", "Invincibility!"};

	public WorldController () {
		init();
	}

	private void init () {
		Gdx.input.setInputProcessor(this);
		stage = new Stage();
		GameScreen.gameOver = false;
		
		Constants.PLAYER_SPEED = 3.0f;
		Constants.TRIANGLE_SPEED = 2.5f;
		Constants.SQUARE_SPEED = 2.5f;
		Constants.PENTAGON_SPEED = 2.5f;
		Constants.STAR_SPEED = 2.5f;
		Constants.CIRCLE_SPEED = 2.5f;
		Constants.RARE_SPEED = 5.0f;
		Constants.LEGEND_SPEED = 6.0f;
		Constants.SCORE_SPEED = 5.0f;
		Constants.IMMUNE_SPEED = 5.0f;
	}
	

	public void update (float deltaTime) {
		if(GameScreen.gameOver){
			stage.update(deltaTime);
		}
		else{
		inputControls();
		collision();
		stage.update(deltaTime);
		transform();
		limitSpeed();
		}
	}
	
	private void limitSpeed() {
		if(Constants.PLAYER_SPEED > 9.0f)
			Constants.PLAYER_SPEED = 9.0f;
		if(Constants.TRIANGLE_SPEED > 9.0f)
			Constants.TRIANGLE_SPEED = 9.0f;
		if(Constants.SQUARE_SPEED > 9.0f)
			Constants.SQUARE_SPEED = 9.0f;
		if(Constants.PENTAGON_SPEED > 9.0f)
			Constants.PENTAGON_SPEED = 9.0f;
		if(Constants.STAR_SPEED > 9.0f)
			Constants.STAR_SPEED = 9.0f;
		if(Constants.CIRCLE_SPEED > 9.0f)
			Constants.CIRCLE_SPEED = 9.0f;
		if(Constants.RARE_SPEED > 9.0f)
			Constants.RARE_SPEED = 9.0f;
		if(Constants.LEGEND_SPEED > 9.0f)
			Constants.LEGEND_SPEED = 9.0f;
		
	}

	//collision and controls to come here
	public void inputControls(){
		
		//player movement
		if(Gdx.input.isKeyPressed(Keys.UP)){
			stage.player.direction = 3;
			stage.player.up.start();
			stage.player.position.y += stage.player.velocity;
		}
		if(Gdx.input.isKeyPressed(Keys.DOWN)){
			stage.player.direction = 1;
			stage.player.down.start();
			stage.player.position.y -= stage.player.velocity;
		}
		if(Gdx.input.isKeyPressed(Keys.LEFT)){
			stage.player.direction = 0;
			stage.player.left.start();
			stage.player.position.x -= stage.player.velocity;
		}
		if(Gdx.input.isKeyPressed(Keys.RIGHT)){
			stage.player.direction = 2;
			stage.player.right.start();
			stage.player.position.x += stage.player.velocity;
			}
	}
	public void collision()
	{
		//keep player in screen
		if(stage.player.position.x < 1){
			stage.player.position.x = 1;
		}
		if(stage.player.position.x > 775){
			stage.player.position.x = 775;
		}
		if(stage.player.position.y < 1){
			stage.player.position.y = 1;
		}
		if(stage.player.position.y > 380){
			stage.player.position.y = 380;
		}
		
		r1.set(stage.player.bounds);
		Player hero = stage.player;
		for (Shape shape : stage.shapes){
			if(shape.collected) return;
			else
			{
				r2.set(shape.bounds);
				if(r2.overlaps(r1)){
					phrase = phrases[MathUtils.random(0, 3)];
					stage.scoreElapsedTime = 0.5f;
					shape.collected = true;
					if(stage.player.hasPowerUp(Constants.IMMUNE)){
						playerEatAnything(shape);
					}
					else{
						playerEatCorrectFood(shape, hero);	
					}
				}
			}	
		}
	}
	private void playerEatAnything(Shape shape) {
		if(shape.isTriangle){
			triangleCount++;
			addScore();
		}
		else if(shape.isSquare){
			squareCount++;
			addScore();
		}
		else if(shape.isPentagon){
			pentagonCount++;
			addScore();
		}
		else if(shape.isStar){
			starCount++;
			addScore();
		}
		else if(shape.isCircle){
			circleCount++;
			addScore();
		}
		else if(shape.isDouble){
			rareMultiplier = Constants.RARE_MULTIPLIER;
			addScore();
			stage.player.setPowerUp(Constants.DOUBLESCORE, true);
			resetBonusMultiplier();
		}
		else if(shape.isImmune){
			rareMultiplier = Constants.RARE_MULTIPLIER;
			addScore();
			stage.player.setPowerUp(Constants.IMMUNE, true);
			resetBonusMultiplier();
		}
		else if(shape.isDouble){
			stage.player.setPowerUp(Constants.DOUBLESCORE, true);
		}
		
	}

	private void addScore() {
		if(stage.player.hasPowerUp(Constants.DOUBLESCORE)){
			score += (int) (1 * ShapeMultiplier * rareMultiplier * legendMultiplier * 2);
			currentScore = (int) (1 * ShapeMultiplier * rareMultiplier * legendMultiplier * 2);
		}
		else{
			score += (int) (1 * ShapeMultiplier * rareMultiplier * legendMultiplier);
			currentScore = (int) (1 * ShapeMultiplier * rareMultiplier * legendMultiplier);
		}
		
		
		//Gdx.app.debug(TAG, "Score: " + score);
	}

	private void playerEatCorrectFood(Shape shape, Player hero) {
		if(hero.isTriangle && shape.isTriangle){
			triangleCount++;
			addScore();
		}
		else if(hero.isSquare && shape.isSquare){
			squareCount++;
			addScore();
		}
		else if(hero.isPentagon && shape.isPentagon){
			pentagonCount++;
			addScore();
		}
		else if(hero.isStar && shape.isStar){
			starCount++;
			addScore();
		}
		else if(hero.isCircle && shape.isCircle){
			circleCount++;
			addScore();
		}
		else if(shape.isRare){
			rareMultiplier = Constants.RARE_MULTIPLIER;
			addScore();
			resetBonusMultiplier();
		}
		else if(shape.isLegend){
			legendMultiplier = Constants.LEGEND_MULTIPLIER;
			addScore();
			resetBonusMultiplier();
		}
		else if(shape.isDouble){
			rareMultiplier = Constants.RARE_MULTIPLIER;
			addScore();
			stage.player.setPowerUp(Constants.DOUBLESCORE, true);
			resetBonusMultiplier();
		}
		else if(shape.isImmune){
			rareMultiplier = Constants.RARE_MULTIPLIER;
			addScore();
			stage.player.setPowerUp(Constants.IMMUNE, true);
			resetBonusMultiplier();
		}
		else{
			GameScreen.gameOver = true;
		}
		
	}

	public void transform(){
		if(triangleCount >= 10){
			stage.player.setShape(2);
			Constants.PLAYER_SPEED += 0.2f;
			resetShapeCount();
			ShapeMultiplier += 10;
		}
		else if(squareCount >= 10){
			stage.player.setShape(3);
			Constants.PLAYER_SPEED += 0.2f;
			resetShapeCount();
			ShapeMultiplier += 10;
			}
		else if(pentagonCount >= 10){
			stage.player.setShape(4);
			Constants.PLAYER_SPEED += 0.2f;
			resetShapeCount();
			ShapeMultiplier += 10;
		}
		else if(starCount >= 10){
			stage.player.setShape(5);
			Constants.PLAYER_SPEED += 0.2f;
			resetShapeCount();
			ShapeMultiplier += 10;
		}
		else if(circleCount >= 10){
			stage.player.setShape(1);
			stage.shape.probabilityChange -= 30;
			if(stage.shape.probabilityChange < 40)
				stage.shape.probabilityChange = 40;
			Constants.PLAYER_SPEED += 0.2f;
			resetShapeCount();
			ShapeMultiplier += 10;
		}
	}
	public void resetShapeCount()
	{
		triangleCount = 0;
		squareCount = 0;
		pentagonCount = 0;
		starCount = 0;
		circleCount = 0;
	}
	public void resetBonusMultiplier(){
		rareMultiplier = 1;
		legendMultiplier = 1;
	}

}
