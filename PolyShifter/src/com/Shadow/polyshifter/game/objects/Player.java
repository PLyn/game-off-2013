package com.Shadow.polyshifter.game.objects;

import com.Shadow.polyshifter.game.Assets;
import com.Shadow.polyshifter.utils.Constants;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;


public class Player extends AbstractGameObject{
	public static final String TAG = Player.class.getName();
	
	//Particle
	public ParticleEffect dustParticles = new ParticleEffect();
	
	public boolean isTriangle = false;
	public boolean isSquare = false;
	public boolean isPentagon = false;
	public boolean isStar = false;
	public boolean isCircle = false;
	
	public TextureRegion player;
	
	//boolean for power ups here
	boolean hasDoubleScore = false;
	public float doubleScoreTimeLeft = 0.0f;
	public float immuneTimeLeft = 0.0f;
	boolean hasImmune = false;
	
	
	public Player(){
		init();
	}

	private void init() {
		size.set(25,25);
		origin.set(size.x / 2, size.y / 2); // centers image on object
		position.set(Constants.VIEWPORT_WIDTH / 2, Constants.VIEWPORT_HEIGHT / 2);
		bounds.set(position.x, position.y, size.x, size.y);
		rotation = 270;
		velocity = Constants.PLAYER_SPEED;
		setShape(1);
		
		dustParticles.load(Gdx.files.internal("particle/particle.p"), Gdx.files.internal("particle"));
		
	}

	public void setShape(int shapenumber){
		switch(shapenumber){
			case 1:
				player = Assets.instance.triangle.triangleAsset;
				isTriangle = true; isSquare = false; isPentagon = false; isStar = false; isCircle = false;
				break;
			case 2:
				player = Assets.instance.square.squareAsset;
				isTriangle = false; isSquare = true; isPentagon = false; isStar = false; isCircle = false;
				break;
			case 3:
				player = Assets.instance.pentagon.pentagonAsset;
				isTriangle = false; isSquare = false; isPentagon = true; isStar = false; isCircle = false;
				break;
			case 4:
				player = Assets.instance.star.starAsset;
				isTriangle = false; isSquare = false; isPentagon = false; isStar = true; isCircle = false;
				break;
			case 5:
				player = Assets.instance.circle.circleAsset;
				isTriangle = false; isSquare = false; isPentagon = false; isStar = false; isCircle = true;
				break;
			default:
				break;		
			}
		dustParticles.start();
		}
	
	public void setPowerUp(int power, boolean state){
		switch(power){
		case 0:
			hasDoubleScore = state;
			doubleScoreTimeLeft = 10.0f;
			break;
		case 1:
			hasImmune = state;
			immuneTimeLeft = 10.f;
			break;
		default:
			break;
		}
	}
	public boolean hasPowerUp(int power){
		if(power == 0){
			return hasDoubleScore && doubleScoreTimeLeft > 0;
		}
		else if(power == 1){
			return hasImmune && immuneTimeLeft > 0;
		}
		return false;
		
	}
	@Override
	public void update(float deltaTime)
	{
		velocity = Constants.PLAYER_SPEED;
		bounds.set(position.x, position.y, size.x, size.y);
		
		if(doubleScoreTimeLeft > 0){
			doubleScoreTimeLeft -= deltaTime;
		}
		if(immuneTimeLeft > 0){
			immuneTimeLeft -= deltaTime;
		}
		if(doubleScoreTimeLeft <= 0){
			doubleScoreTimeLeft = 0;
			setPowerUp(Constants.DOUBLESCORE, false);
		}
		if(immuneTimeLeft <= 0){
			immuneTimeLeft = 0;
			setPowerUp(Constants.IMMUNE, false);
		}
		
		dustParticles.update(deltaTime);
	}
	
	@Override
	public void render(SpriteBatch batch) {
		
		TextureRegion rend = null;
		
		rend = player;
		batch.draw(rend, position.x, position.y, origin.x, origin.y, size.x, size.y, scale.x, scale.y,
				rotation, false);
		dustParticles.setPosition(position.x, position.y);
		dustParticles.draw(batch);
	}
}