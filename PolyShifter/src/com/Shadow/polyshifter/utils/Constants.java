package com.Shadow.polyshifter.utils;

public class Constants {
	public static final float VIEWPORT_WIDTH = 800;
	public static final float VIEWPORT_HEIGHT = 480;
	
	//numbers to determine how rare a shape is
	public static final int COMMON = 0;
	public static final int RARE = 1;
	public static final int LEGENDARY  = 2;
	
	//boolean to determine spawn side of shapes
	public static final boolean LEFT_SIDE = true;
	public static final boolean RIGHT_SIDE = false;
	
	//pack and png files for the textures and graphics used
	public static final String TEXTURE_ATLAS = "polyshifter.pack";
	public static final String TEXTURE_ATLAS_BACK ="back/polyshifter-back.pack";
	public static final String TEXTURE_ATLAS_LIBGDX_UI ="uiskin.atlas";
	// Location of description file for skins
	public static final String SKIN_LIBGDX_UI ="uiskin.json";
	public static final String SKIN_POLYSHIFTER_UI ="polyshifter-ui.json";
	
	public static final String PREFERENCES = "settings.prefs";
	
	//multipliers of each shape for score
	public static float SHAPE_MULTIPLIER = 5;
	public static final float RARE_MULTIPLIER = 5;
	public static final float LEGEND_MULTIPLIER = 10;
	
	//speed multipliers for player and shapes
	public static float PLAYER_SPEED;
	public static float TRIANGLE_SPEED;
	public static float SQUARE_SPEED;
	public static float PENTAGON_SPEED;
	public static float STAR_SPEED;
	public static float CIRCLE_SPEED;
	public static float RARE_SPEED;
	public static float LEGEND_SPEED;
	public static float SCORE_SPEED;
	public static float IMMUNE_SPEED;
	
	//powerups
	public static int DOUBLESCORE = 0;
	public static int IMMUNE = 1;
	
	
	
}
