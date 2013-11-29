package com.Shadow.polyshifter.game;

import java.util.Random;

import com.Shadow.polyshifter.game.objects.AbstractGameObject;
import com.Shadow.polyshifter.game.objects.Player;
import com.Shadow.polyshifter.game.objects.Shape;
import com.Shadow.polyshifter.game.screens.GameScreen;
import com.Shadow.polyshifter.utils.Constants;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class Stage {
	public static final String TAG = Stage.class.getName();
	
	public Player player;
	public Shape shape;
	
	int probability = 0;
	int rarity = 0;
	Random rand = new Random();
	AbstractGameObject obj;
	float lastSpawnTime;
	int foodLimit = 0;
	
	int timeToSpawnAgain = 1000000000;
	
	public static int activeShapes = 0;
	public float lastX = 0;
	public float lastY = 0;
	public float scoreElapsedTime = 0.0f;
	public boolean scoreDisplayed = false;
	
	Array<Shape> shapes = new Array<Shape>();
	public static Array<Float> positions = new Array<Float>();
	
	float lifetime = 0;
	boolean lifeUp = false;
	
	public float time = 0;
	float color = 1;
	
	int lastShape;
	
	public Stage(){
		init();
	}
	
	private void init(){
		
		player = null;
		obj = new Player();
		player = (Player) obj;
		
		shape = null;
		obj = new Shape(player);
		shape = (Shape) obj;

		
		Spawn();
	}
	private void Spawn()
	{
		shape = new Shape(player);

			positions.add(shape.position.x);
			lastSpawnTime = TimeUtils.nanoTime();
			shapes.add(shape);
	}

	public void update (float deltaTime) {
		if(scoreDisplayed){
			scoreElapsedTime -= deltaTime;
		}
			
		player.update(deltaTime);
		Shape.probabilityForShape(player);
		foodLimit = shape.getFoodLimit();
		for(Shape x : shapes)
		{
			x.update(deltaTime);
			if(x.position.x > 810 || x.position.x < -10 ||x.collected == true){
				activeShapes--;
				if(x.collected){
					if(x.isTriangle){
						lastShape = 0;
					}
					else if(x.isSquare){
						lastShape = 0;
					}
					else if(x.isPentagon){
						lastShape = 0;
					}
					else if(x.isStar){
						lastShape = 0;
					}
					else if(x.isCircle){
						lastShape = 0;
					}
					else if(x.isRare){
						lastShape = 1;
					}
					else if(x.isLegend){
						lastShape = 2;
					}
					else if(x.isDouble){
						lastShape = 3;
					}
					else if(x.isImmune){
						lastShape = 4;
					}
				lastX = x.position.x;
				lastY = x.position.y;
				}
				shapes.removeValue(x, true);
			}	
		}

		if(GameScreen.gameOver){
			time += deltaTime;
			//Gdx.app.debug(TAG, Float.toString(time));
			
		}
	}
	
	public void render (SpriteBatch batch) {
		if(GameScreen.gameOver){
			batch.setColor(color , color, color, color);
			color -= 0.01;
			if(color <= 0)
				color = 0;
		}
		player.render(batch);
		for(Shape x : shapes){
			x.render(batch);
		}
			
		if(activeShapes < foodLimit && TimeUtils.nanoTime() - lastSpawnTime > timeToSpawnAgain){
			Spawn();
			activeShapes++ ;
		}
	
		
	}
	

}
