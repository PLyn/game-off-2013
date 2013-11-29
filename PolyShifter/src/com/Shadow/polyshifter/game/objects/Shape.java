package com.Shadow.polyshifter.game.objects;

import java.util.Random;

import com.Shadow.polyshifter.game.Assets;

import com.Shadow.polyshifter.utils.Constants;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool.Poolable;

public class Shape extends AbstractGameObject{
	public static final String TAG = Shape.class.getName();

	Random rand = new Random();
	
	public boolean alive;
	
	
	
	public static int maxOnScreen = 2;
	public static int minOnScreen = 0;
	public static int numberOnScreenLimit = 0;
	public static int rarity = 0 ;  // 0 - common 1 - rare 2- legendary
	public static int probability = 0;
	
	public static int triangleChance = 0;
	public static int squareChance = 0;
	public static int pentagonChance = 0;
	public static int starChance = 0;
	public static int circleChance = 0;
	public static int rareChance = 0;
	public static int legendChance = 0;
	public static int doubleChance = 0;
	public static int immuneChance = 0;
	
	public static int[] chances = {50, 50, 50, 50, 50, 15, 5, 20, 20};
	
	public static int probabilityChange = 200;
	
	public boolean isTriangle = false;
	public boolean isSquare = false;
	public boolean isPentagon = false;
	public boolean isStar = false;
	public boolean isCircle = false;
	public boolean isRare = false;
	public boolean isLegend = false;
	public boolean isDouble = false;
	public boolean isImmune = false;
	
	public boolean collected;
	public boolean spawnside;
	public float shapeSpeed = 0;
	
	public TextureRegion shapes;
	
	Player hero = new Player();
	
	
	public Shape(Player play){
		init(play);
	}

	private void init(Player play) {
		size.set(25, 25);
		spawnside = getSpawnSide();
		if(spawnside){
			position.set(-10, MathUtils.random(5, 380));
		}
		else{
			position.set(810, MathUtils.random(5, 380));
		}
		bounds.set(position.x, position.y, size.x, size.y);
		origin.set(size.x / 2, size.y / 2);
		shapeProbability(chances);
		setShape(MathUtils.random(1, immuneChance));
		
		collected = false;
	}
	
	@Override
	public void update(float deltaTime){
		super.update(deltaTime);
		if(maxOnScreen > 9){
			maxOnScreen = 9;
		}
		if(minOnScreen > 8){
			minOnScreen = 8;
		}
		if(isTriangle)
			shapeSpeed = Constants.TRIANGLE_SPEED;
		else if(isSquare)
			shapeSpeed = Constants.SQUARE_SPEED;
		else if(isPentagon)
			shapeSpeed = Constants.PENTAGON_SPEED;
		else if(isStar)
			shapeSpeed = Constants.STAR_SPEED;
		else if(isCircle)
			shapeSpeed = Constants.CIRCLE_SPEED;
		else if(isRare)
			shapeSpeed = Constants.RARE_SPEED;
		else if(isLegend)
			shapeSpeed = Constants.LEGEND_SPEED;
		else if(isDouble)
			shapeSpeed = Constants.SCORE_SPEED;
		else if(isImmune)
			shapeSpeed = Constants.IMMUNE_SPEED;
		
		if(spawnside)
			position.x += shapeSpeed;
		else
			position.x -= shapeSpeed;
		rotation += 0.5%360;
		bounds.set(position.x, position.y, size.x, size.y);
	}

	@Override
	public void render(SpriteBatch batch) {
		TextureRegion poly = null;
		
		poly = shapes;
		
		batch.draw(poly, position.x, position.y, origin.x, origin.y, size.x, size.y, scale.x, scale.y,
				rotation, false);
		
	} 
	public int getFoodLimit(){
		return MathUtils.random(minOnScreen, maxOnScreen);
	}
	public static void probabilityForShape(Player hero) {
		if(hero.isTriangle){
			//increase probability of shape
			changeProbability(4, 50);
			changeProbability(0, probabilityChange);
			shapeProbability(chances);
		}
		else if(hero.isSquare){
			//increase probability of shape
			changeProbability(0, 50);
			changeProbability(1, probabilityChange);
			shapeProbability(chances);
		}
		else if(hero.isPentagon){
			//increase probability of shape
			changeProbability(1, 50);
			changeProbability(2, probabilityChange);
			shapeProbability(chances);
		}
		else if(hero.isStar){
			//increase probability of shape
			changeProbability(2, 50);
			changeProbability(3, probabilityChange);
			shapeProbability(chances);
		
		}
		else if(hero.isCircle){
			//increase probability of shape
			changeProbability(3, 50);
			changeProbability(4, probabilityChange);
			shapeProbability(chances);
		}	
	}
	public static void shapeProbability(int[] chance)
	{
		triangleChance = chance[0];
		squareChance = triangleChance + chance[1];
		pentagonChance = squareChance + chance[2];
		starChance = pentagonChance + chance[3];
		circleChance = starChance + chance[4];
		rareChance = circleChance + chance[5];
		legendChance = rareChance + chance[6];
		doubleChance = legendChance + chance[7];
		immuneChance = doubleChance + chance[8];
	}
	public static void changeProbability(int pos, int amount)
	{
		chances[pos] = amount;
	}
	
	
	public void setShape(int shapenumber){

		if(shapenumber >= 0 && shapenumber <= triangleChance){
			shapes = Assets.instance.triangle.triangleAsset;
			currentShape(0);	
			//set speed of shape
			shapeSpeed = Constants.TRIANGLE_SPEED; 
		}
		else if(shapenumber > triangleChance && shapenumber <= squareChance){
			shapes = Assets.instance.square.squareAsset;
			currentShape(1);	
			//set speed of shape
			shapeSpeed = Constants.SQUARE_SPEED; 
			}
		else if(shapenumber > squareChance && shapenumber <= pentagonChance){
			shapes = Assets.instance.pentagon.pentagonAsset;
			currentShape(2);
			//set speed of shape
			shapeSpeed = Constants.PENTAGON_SPEED; 
		}
		else if(shapenumber > pentagonChance && shapenumber <= starChance){
			shapes = Assets.instance.star.starAsset;
			currentShape(3);
			//set speed of shape
			shapeSpeed = Constants.STAR_SPEED; 
		}
		else if(shapenumber > starChance && shapenumber <= circleChance){
			shapes = Assets.instance.circle.circleAsset;
			currentShape(4);
			//set speed of shape
			shapeSpeed = Constants.CIRCLE_SPEED; 
		}
		else if(shapenumber > circleChance && shapenumber <= rareChance){
			shapes = Assets.instance.rare.rareAsset;
			currentShape(5);
			//set speed of shape
			shapeSpeed = Constants.RARE_SPEED; 	
		}
		else if(shapenumber > rareChance && shapenumber <= legendChance){
			shapes = Assets.instance.legend.legendAsset;
			currentShape(6);
			//set speed of shape
			shapeSpeed = Constants.LEGEND_SPEED; 	
		}
		else if(shapenumber > legendChance && shapenumber <= doubleChance){
			shapes = Assets.instance.score.scoreAsset;
			currentShape(7);
			//set speed of shape
			shapeSpeed = Constants.SCORE_SPEED; 
		}
		else if(shapenumber > doubleChance && shapenumber <= immuneChance){
			shapes = Assets.instance.immune.immuneAsset;
			currentShape(8);
			//set speed of shape
			shapeSpeed = Constants.IMMUNE_SPEED; 	
		}
		}
	public boolean getSpawnSide(){
		return MathUtils.randomBoolean();		
	}

	public void currentShape(int shape){
		switch(shape){
		case 0:
			isTriangle = true; 
			isSquare = false; 
			isPentagon = false; 
			isStar = false; 
			isCircle = false; 
			isRare = false; 
			isLegend = false; 
			isDouble = false;
			isImmune = false;
			break;
		case 1:
			isTriangle = false; 
			isSquare = true; 
			isPentagon = false; 
			isStar = false; 
			isCircle = false; 
			isRare = false; 
			isLegend = false; 
			isDouble = false;
			isImmune = false;
			break;
		case 2:
			isTriangle = false; 
			isSquare = false; 
			isPentagon = true; 
			isStar = false; 
			isCircle = false; 
			isRare = false; 
			isLegend = false; 
			isDouble = false;
			isImmune = false;
			break;
		case 3:
			isTriangle = false; 
			isSquare = false; 
			isPentagon = false; 
			isStar = true; 
			isCircle = false; 
			isRare = false; 
			isLegend = false; 
			isDouble = false;
			isImmune = false;
			break;
		case 4:
			isTriangle = false; 
			isSquare = false; 
			isPentagon = false; 
			isStar = false; 
			isCircle = true; 
			isRare = false; 
			isLegend = false; 
			isDouble = false;
			isImmune = false;;
			break;
		case 5:
			isTriangle = false; 
			isSquare = false; 
			isPentagon = false; 
			isStar = false; 
			isCircle = false; 
			isRare = true; 
			isLegend = false; 
			isDouble = false;
			isImmune = false;
			break;
		case 6:			
			isTriangle = false; 
			isSquare = false; 
			isPentagon = false; 
			isStar = false; 
			isCircle = false; 
			isRare = false; 
			isLegend = true; 
			isDouble = false;
			isImmune = false;
			break;
		case 7:
			isTriangle = false; 
			isSquare = false; 
			isPentagon = false; 
			isStar = false; 
			isCircle = false; 
			isRare = false; 
			isLegend = false; 
			isDouble = true;
			isImmune = false;
			break;
		case 8:
			isTriangle = false; 
			isSquare = false; 
			isPentagon = false; 
			isStar = false; 
			isCircle = false; 
			isRare = false; 
			isLegend = false; 
			isDouble = false;
			isImmune = true;
			break;
		default:
			break;
		}
	}

	

}
