package com.Shadow.polyshifter.game.screens;

import com.Shadow.polyshifter.game.Assets;
import com.Shadow.polyshifter.utils.Constants;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class TutorialScreen extends AbstractScreenObject{

	SpriteBatch batch;
	OrthographicCamera camera;
	
	TextureRegion tri, square, pent, star, cir, rare, leg, score, immune , ingame;
	
	float x,y;
	
	public TutorialScreen(Game game) {
		super(game);
		
	}

	@Override
	public void render(float delta) {
		// Sets the clear screen color to: Cornflower Blue
		Gdx.gl.glClearColor(1,1,1,1);
		// Clears the screen
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		batch.begin();
		batch.draw(ingame, 0, 0);
		Assets.instance.font.Normal.draw(batch, "TUTORIAL", (Constants.VIEWPORT_WIDTH / 2) - 50 , y + 30);
		
		Assets.instance.font.Normal.draw(batch, "You are a shape eating alien who is lost in space and needs to eat to survive", x, y);
		//hero
		batch.draw(tri, x, y - 50);
		Assets.instance.font.Normal.draw(batch, "The Hero's Original Form", x + 50, y - 30);
		//shapes
		batch.draw(tri, x, y - 110);
		batch.draw(square, x + 32, y - 110);
		batch.draw(pent, x + 64, y - 110);
		batch.draw(star, x + 96, y - 110);
		batch.draw(cir, x + 128, y - 110);
		Assets.instance.font.Normal.draw(batch, "The different shapes the hero will consume", x + 160, y - 90);
		//rare
		batch.draw(rare, x, y - 170);
		Assets.instance.font.Normal.draw(batch, "Rare shape, gives 5x current score multiplier.", x + 50, y - 150);
		//legend
		batch.draw(leg, x, y - 230);
		Assets.instance.font.Normal.draw(batch, "Legendary shape, gives 10x current score multiplier.", x + 50, y - 210);
		//double score
		batch.draw(score, x, y - 290);
		Assets.instance.font.Normal.draw(batch, "Double Score powerup, gives you double score for 10 seconds", x + 50, y - 270);
		//immune
		batch.draw(immune, x, y - 350);
		Assets.instance.font.Normal.draw(batch, "Immune powerup, you can eat any shape without dying for 10 seconds", x + 50, y - 330);
		
		Assets.instance.font.Big.draw(batch, "Click or Tap to Start game!", (Constants.VIEWPORT_WIDTH / 2) - 100, y - 360);
		
		batch.end();
		if(Gdx.input.justTouched()){
			game.setScreen(new GameScreen(game));		
			}
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
		
		tri = Assets.instance.triangle.triangleAsset;
		square = Assets.instance.square.squareAsset;
		pent = Assets.instance.pentagon.pentagonAsset;
		star = Assets.instance.star.starAsset;
		cir = Assets.instance.circle.circleAsset;
		rare = Assets.instance.rare.rareAsset;
		leg = Assets.instance.legend.legendAsset;
		score = Assets.instance.score.scoreAsset;
		immune = Assets.instance.immune.immuneAsset;
		ingame = Assets.instance.backgrounds.ingame;
		
		x = 100;
		y = Constants.VIEWPORT_HEIGHT - 50;
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
