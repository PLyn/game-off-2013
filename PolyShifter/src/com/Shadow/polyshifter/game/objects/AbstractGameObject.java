package com.Shadow.polyshifter.game.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class AbstractGameObject {
	public Vector2 position; //where on screen the object it
	public Vector2 size; //width and height of object
	public Vector2 origin; //where the screen starts
	public Vector2 scale; // scale of object or magnification
	public float rotation; // self explanatory
	public float velocity; // speed at which it is moving
	public Vector2 terminalVelocity; // min and max speed it can move
	public Vector2 friction; // slows down velocity when moving
	public Rectangle bounds; // hit boxes for objects 
	
	public AbstractGameObject(){
		position = new Vector2();
		size = new Vector2(1, 1);
		origin = new Vector2();
		scale = new Vector2(1, 1);
		rotation = 0;
		velocity = 0.0f;
		terminalVelocity = new Vector2(1, 1);
		friction = new Vector2();
		bounds = new Rectangle();
	}
	public void update (float deltaTime){
	}
	//updating the position for x and y axis to come
	public abstract void render(SpriteBatch batch);
}
