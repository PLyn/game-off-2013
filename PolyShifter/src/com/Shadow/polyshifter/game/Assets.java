package com.Shadow.polyshifter.game;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.utils.Disposable;
import com.Shadow.polyshifter.utils.Constants;

public class Assets implements Disposable, AssetErrorListener {
	public static final String TAG = Assets.class.getName();

	public static final Assets instance = new Assets();
	private AssetManager assetManager;
	
	public AssetPlayer character;
	public AssetTriangle triangle;
	public AssetSquare square;
	public AssetPentagon pentagon;
	public AssetStar star;
	public AssetCircle circle;
	public AssetFonts font;
	public AssetRare rare;
	public AssetLegend legend;
	public AssetScore score;
	public AssetImmune immune;
	public AssetBack backgrounds;
	public AssetEffect effect; 
	// singleton: prevent instantiation from other classes
	private Assets () {
	}
	
	public void init (AssetManager assetManager) 
	{
		this.assetManager = assetManager;
		// set asset manager error handler
		assetManager.setErrorListener(this);
		// load texture atlas
		assetManager.load(Constants.TEXTURE_ATLAS, TextureAtlas.class);
		assetManager.load(Constants.TEXTURE_ATLAS_BACK, TextureAtlas.class);
		// start loading assets and wait until finished
		assetManager.finishLoading();
			
		TextureAtlas atlas = assetManager.get(Constants.TEXTURE_ATLAS);
		TextureAtlas atlas_back = assetManager.get(Constants.TEXTURE_ATLAS_BACK);
		// enable texture filtering for pixel smoothing
		for (Texture t : atlas.getTextures()) {
				t.setFilter(TextureFilter.Linear, TextureFilter.Linear);
				}
		// create game resource objects
		font = new AssetFonts();
		character = new AssetPlayer(atlas);
		triangle = new AssetTriangle(atlas);
		square = new AssetSquare(atlas);
		pentagon = new AssetPentagon(atlas);
		star = new AssetStar(atlas);
		circle = new AssetCircle(atlas);
		rare = new AssetRare(atlas);
		legend = new AssetLegend(atlas);
		score = new AssetScore(atlas);
		immune = new AssetImmune(atlas);
		backgrounds = new AssetBack(atlas_back);
		effect = new AssetEffect(atlas);
	}
	public class AssetEffect{
		public final AtlasRegion scoreEffectAsset;
		public final AtlasRegion immuneEffectAsset;
		public AssetEffect(TextureAtlas atlas){
			scoreEffectAsset = atlas.findRegion("scoreeffect");
			immuneEffectAsset = atlas.findRegion("immuneeffect");
		}
	}
	public class AssetScore{
		public final AtlasRegion scoreAsset;
		public AssetScore(TextureAtlas atlas){
			scoreAsset = atlas.findRegion("score");
		}
	}
	public class AssetImmune{
		public final AtlasRegion immuneAsset;
		public AssetImmune(TextureAtlas atlas){
			immuneAsset = atlas.findRegion("shield");
		}
	}
	
	public class AssetBack{
		public final AtlasRegion intro;
		public final AtlasRegion ingame;
		public final AtlasRegion end;
		public final AtlasRegion title;
		public final AtlasRegion start;
		public final AtlasRegion hud;
		public AssetBack(TextureAtlas atlas){
			intro = atlas.findRegion("intro-back");
			ingame = atlas.findRegion("ingame-back");
			end = atlas.findRegion("end-back");
			title = atlas.findRegion("title");
			start = atlas.findRegion("start");
			hud = atlas.findRegion("hud-back");
		}
	}
	
	public class AssetFonts{
		public final BitmapFont Small;
		public final BitmapFont Normal;
		public final BitmapFont Big;

		public AssetFonts () {
			// create three fonts using Libgdx's 15px bitmap font
			Small = new BitmapFont();
			Normal = new BitmapFont();
			Big = new BitmapFont();
			// set font sizes
			Small.setScale(0.75f);
			Normal.setScale(1.0f);
			Big.setScale(1.5f);
			// enable linear texture filtering for smooth fonts
			Small.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
			Normal.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
			Big.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		}
	}

	
	//load assets for player
	public class AssetPlayer {
		public final AtlasRegion playerAsset;
		public AssetPlayer(TextureAtlas atlas){
			playerAsset = atlas.findRegion("hero");
		}
	}
	
	//load assets for triangle
	public class AssetTriangle{
		public final AtlasRegion triangleAsset;
		public AssetTriangle(TextureAtlas atlas){
			triangleAsset = atlas.findRegion("triangle");
		}
	}
	
	//load assets for square
	public class AssetSquare{	
		public final AtlasRegion squareAsset;
		public AssetSquare(TextureAtlas atlas){
			squareAsset = atlas.findRegion("square");
		}
	}
	//load assets for pentagon
	public class AssetPentagon{
		public final AtlasRegion pentagonAsset;
		public AssetPentagon(TextureAtlas atlas){
			pentagonAsset = atlas.findRegion("pentagon"); 
		}
	}
	//load asset for star
	public class AssetStar{
		public final AtlasRegion starAsset;
		public AssetStar(TextureAtlas atlas){
			starAsset = atlas.findRegion("star");
		}
	}
	//load asset for circle
	public class AssetCircle{
		public final AtlasRegion circleAsset;
		public AssetCircle(TextureAtlas atlas){
			circleAsset = atlas.findRegion("circle");
		}
	}
	
	//load asset for rare shape
	public class AssetRare{
		public final AtlasRegion rareAsset;
		public AssetRare(TextureAtlas atlas){
			rareAsset = atlas.findRegion("rare");
		}
	}
	
	//load asset for legend shape
	public class AssetLegend{
		public final AtlasRegion legendAsset;
		public AssetLegend(TextureAtlas atlas){
			legendAsset = atlas.findRegion("legend");
		}
	}
	
/*	@Override
	public void error(String fileName, Class type, Throwable throwable) {
		Gdx.app.error(TAG, "Couldn't load asset '" + fileName + "'", (Exception)throwable);
		
	}*/

	@Override
	public void dispose() {
		assetManager.dispose();
		font.Small.dispose();
		font.Normal.dispose();
		font.Big.dispose();

	}

@Override
public void error(AssetDescriptor asset, Throwable throwable) {
	// TODO Auto-generated method stub
	
}


}
