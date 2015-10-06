package com.mygdx.game.mario.sprite;

import com.badlogic.gdx.graphics.g2d.Batch;

public interface IDrawable {

	public void updateAnimation(float deltaTime);
	
	public void move(float deltaTime);
	
	public void render(Batch batch);
}
